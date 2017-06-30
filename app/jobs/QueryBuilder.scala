package jobs

import javax.inject.{ Singleton, Inject }
import play.api.libs.json._
import java.time.{ ZonedDateTime, ZoneId, DayOfWeek }
import java.time.DayOfWeek._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import shared.Global._
import shared.Messages
import models.services.{ UserService, ClassroomService }
import models.{ User, Step, Building, Classroom }

@Singleton
class QueryBuilder @Inject() (userService: UserService, classroomService: ClassroomService) {
  import Step._

  def build(userKey: String, content: String): Future[Option[JsObject]] = for {
    user @ User(userKey, step, building, dow, startTime, endTime) <- userService.findByKey(userKey)
    stepOpt <- nextStep(user, content)
    msg <- stepOpt map {
      s => s match {
        case ExecuteStep =>
          //Fetch data and add to message
          classroomService.find(dow.get, startTime.get, endTime.get, building)
            .map { roomSeq =>
              roomSeq.groupBy(_.building).foldLeft("") { case (acc, (b, r)) =>
                acc + b.name + ":\n" + r.map(_.room).reduceLeft(_+_) + "\n\n"
              }
            }

        case _ =>
          //echo message
          Future(content + " 선택")
      }
    } match {
      case Some(f) => f.map(Some(_))
      case None => Future.successful(None)
    }

    button = stepOpt map {
      _ match {
        case EndTimeStep => EndTimeStep.buttonsWithStart(startTime.get)
        case s: Step => s.buttons
        case _ => Seq("ButtonError")
      }
    }

  } yield for {
    btnGet <- button
    msgGet <- msg
  } yield responseTemplate(msgGet, btnGet)


  //No relation with the OS
  //Updates user using DAO and returns the next step to be performed
  def nextStep(user: User, content: String): Future[Option[Step]] = user.step match {
    case MainMenuStep =>
      val step = if (content == Messages.timeFirst) Some(StartTimeStep)
                 else if (content == Messages.buildingFirst) Some(BuildingSelectionStep)
                 else None

      if (step.isDefined) for {
        _ <- userService.updateStep(user.userKey, step.get)
        _ <- userService.clearStatus(user.userKey)
      } yield step
      else Future(None)

    case StartTimeStep =>
      if (content == Messages.now) {
        val step = Some(EndTimeStep)
        for {
          _ <- userService.updateStep(user.userKey, step.get)
          time = ZonedDateTime.now(ZoneId.of(ZONEID))
          dow = time.getDayOfWeek()
          hour = time.getHour()
          _ <- userService.updateDowStartTime(user.userKey, dow, hour)
        } yield step
      } else if (content == Messages.setStartTime) {
        val step = Some(StartDOWStep)
        for {
          _ <- userService.updateStep(user.userKey, step.get)
        } yield step
      } else Future(None)

    case StartDOWStep =>
      val dowRegex = """(월|화|수|목|금)요일""".r
      content match {
        case dowRegex(s) =>
          val dowOpt = if      (s == "월") Some(MONDAY)
                       else if (s == "화") Some(TUESDAY)
                       else if (s == "수") Some(WEDNESDAY)
                       else if (s == "목") Some(THURSDAY)
                       else if (s == "금") Some(FRIDAY)
                       else None

          dowOpt.map { dow =>
            for {
              _ <- userService.updateDow(user.userKey, dow)
            } yield StartDOWTimeStep
          } match {
            case Some(f) => f.map(Some(_))
            case None => Future.successful(None)
          }
        case _ => Future(None)
      }

    case StartDOWTimeStep =>
      val dowTimeRegex = """(\d{1,2})시부터""".r
      content match {
        case dowTimeRegex(s) =>
          val time = s.toInt
          if (time < 9 || time >= 17) Future(None)
          else for {
            _ <- userService.updateStartTime(user.userKey, time)
          } yield Some(EndTimeStep)
        case _ => Future(None)
      }

    case EndTimeStep =>
      val endTimeRegex = """(\d{2})시까지""".r
      content match {
        case endTimeRegex(s) =>
          val time = s.toInt
          if (time < 10 || time >= 18) Future(None)
          else for {
            u <- userService.updateEndTime(user.userKey, time)
            rooms <- classroomService.find(u.dow.get, u.startTime.get, u.endTime.get, u.building)
          } yield if (rooms.length > SHOW_LIMIT) Some(BuildingSelectionStep) else Some(ExecuteStep)
      }

    case BuildingSelectionStep =>
      Building.fromString(content) match {
        case None => Future(None)
        case Some(building) =>
          Future(Some(user.startTime.fold(StartTimeStep.asInstanceOf[Step])(_ => ExecuteStep)))
      }

    case ExecuteStep =>
      content match {
        case s if s == Messages.changeStartDow => Future(Some(StartDOWStep))
        case s if s == Messages.changeStartTime => Future(Some(StartDOWTimeStep))
        case s if s == Messages.changeEndTime => Future(Some(EndTimeStep))
        case s if s == Messages.changeBuilding => Future(Some(BuildingSelectionStep))
        case s if s == Messages.finish => Future(Some(MainMenuStep))
        case _ => Future(None)
      }

    case _ => Future(None)
  }
}
