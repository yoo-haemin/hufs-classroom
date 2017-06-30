package jobs

import javax.inject.{Singleton, Inject}
import models.services.UserService
import models.{User, Step}
import play.api.libs.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import shared.Global._

import java.time.{ZonedDateTime, ZoneId, DayOfWeek}
import java.time.DayOfWeek._

@Singleton
class QueryBuilder @Inject() (userService: UserService) {
  import Step._

  val mainMenuOption = "시간 먼저" :: "건물 먼저" :: Nil
  val setStartTimeOption = "시작시간 설정" :: "지금부터" :: Nil
  val setDayOfWeek = "오늘" +: Seq("월", "화", "수", "목", "금").map(_ + "요일")
  val setStartTime = (9 to 16).map(_.toString + "시")
  val setEndTime = (1 to 8).map(_.toString + "시간")
  val setBuilding = "인문관" :: "교수학습개발원" :: "사회과학관" :: "사이버관" :: Nil

  def build(userKey: String, content: String): Future[Option[JsObject]] = {
    for {
      user @ User(userKey, step, building, dow, startTime, endTime) <- userService.findByKey(userKey)
      stepOpt <- updateUser(user, content)
      step = stepOpt.map { step =>
        step match {
          case ExecuteStep =>
          case _ =>
        }
      }

    } yield responseTemplate(Seq(""))
  }

  //Updates user using DAO and returns the next step to be performed
  def updateUser(user: User, content: String): Future[Option[Step]] = user.step match {
    case MainMenuStep =>
      val step = if (content == TIME_FIRST) Some(StartTimeStep)
                 else if (content == BUILDING_FIRST) Some(BuildingSelectionStep)
                 else None

      if (step.isDefined) for {
        _ <- userService.updateStep(user.userKey, step.get)
        _ <- userService.clearStatus(user.userKey)
      } yield step
      else Future(None)

    case StartTimeStep =>
      if (content == NOW) {
        val step = Some(EndTimeStep)
        for {
          _ <- userService.updateStep(user.userKey, step.get)
          time = ZonedDateTime.now(ZoneId.of(ZONEID))
          dow = time.getDayOfWeek()
          hour = time.getHour()
          _ <- userService.updateDowStartTime(user.userKey, dow, hour)
        } yield step
      } else if (content == SET_START_TIME) {
        val step = Some(StartDOWStep)
        for {
          _ <- userService.updateStep(user.userKey, step.get)
        } yield step
      } else Future(None)

    case StartDOWStep =>
      val dowRegex = """(월|화|수|목|금)요일""".r
      content match {
        case dowRegex(s) =>
          val dow = if (s == "월") MONDAY
                    else if (s == "화") TUESDAY
                    else if (s == "수") WEDNESDAY
                    else if (s == "목") THURSDAY
                    else if (s == "금") FRIDAY
                    else throw new NoSuchElementException
          for {
            _ <- userService.updateDow(user.userKey, dow)
          } yield Some(StartDOWTimeStep)
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
      val endTimeRegex = """(\d\d)시까지""".r
      content match {
        case endTimeRegex(s) =>
          val time = s.toInt
          if (time < 10 || time >= 18) Future(None)
          else for {
            u <- userService.updateEndTime(user.userKey, time)
          } yield
      }

    case BuildingSelectionStep => 

    case _ => ???
  }


}


/*
 case c if mainMenuOption.exists(_ == c) =>
 val step = if (c == "시간 먼저") StartTimeStep
 else BuildingSelectionStep
 for {
 u <- userService.clearStatus(user.userKey)
 _ <- userService.updateStep(user.userKey, step)
 } yield step

 case c if setStartTimeOption.exists(_ == c) =>



 case c if setStartTime.exists(_ == c) =>
 case c if setDayOfWeek.exists(_ == c) =>
 case c if setStartTime.exists(_ == c) =>
 case c if setEndTime.exists(_ == c) =>
 case c if setBuilding.exists(_ == c) =>

 */
