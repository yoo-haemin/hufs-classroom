package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json._
import java.time.{ZonedDateTime, ZoneId}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import models.{RequestApi, Step}
import models.Step._
import models.services.{ UserService, ClassroomService }

import shared.Global._
import shared.Messages

/**
  * Main Controller
  */
class HomeController @Inject()(
  cc: ControllerComponents, playBodyParsers: PlayBodyParsers, userService: UserService, classroomService: ClassroomService) extends AbstractController(cc) {

  def keyboard = Action { implicit req =>
    val now = ZonedDateTime.now(ZoneId.of(ZONEID))
    val nowHour = now.getHour()
    val nowDow = now.getDayOfWeek()
    val canLookupNow = nowHour >= 9 && nowHour < 17 && nowDow.getValue >= 1 && nowDow.getValue <= 5

    Ok(keyboardTemplate(Step.MainMenuStep.buttons))
  }

  def message = Action.async(playBodyParsers.json) { implicit req =>
    req.body.validate[RequestApi].fold(
      error => {
        Future.successful(ServiceUnavailable(Json.obj("code" -> 100)))
      },
      { case RequestApi(userKey, messageType, content) =>
        if (messageType != "text")
          Future.successful(
            Ok(responseTemplate(
                 "저는 지정된 메세지 이외의 것은 못 받아요 ㅠㅠ 다시 선택해주세요!",
                 MainMenuStep.buttons)))

        //About Menu
        else if (content == Messages.aboutButton) for {
          user <- userService.findByKey(userKey)
        } yield Ok(responseTemplate(Messages.about, user.step.buttons))

        else for {
          //If the keyboard is reset(from timeout at kakao side), clear the status
          _ <- (if (content == Messages.timeFirst || content == Messages.buildingFirst)
                  userService.clearStatus(userKey)
                else Future.successful(()))
          initialUser <- userService.findByKey(userKey)
          newUserEither = initialUser.step.process(initialUser, content)
          msgEither <- newUserEither.right map {
            u =>
            //Update user first...
            userService.update(u) flatMap { _ =>

              //Fetch data
              u.step match {
                case ExecuteStep =>
                  //Fetch data and add to message
                  classroomService.find(u.dow.get, u.startTime.get, u.endTime.get, u.building)
                    .map { roomSeq =>

                      Messages.selections(u.dow.get, u.startTime.get, u.endTime.get, u.building) +
                        (roomSeq.distinct.groupBy(_.building) match {
                           case s if s.size > 0 =>
                             s.foldLeft("") { case (acc, (b, r)) =>
                               acc + b.name + ":\n" +
                                 r.map(_.room + "호\n").sorted.reduceLeft(_+_) + "\n\n"
                             }
                           case _ => "아쉽지만 조건에 맞는 강의실이 없습니다. 선택을 바꿔보세요~"
                         })
                    }
                case s =>
                  //give out predefined message
                  Future.successful(s.message)
              }
            }
          } match {
            case Right(f) => f.map(Right(_))
            case Left(err) => Future.successful(Left(err))
          }

          btnEither = newUserEither.right flatMap { u =>
            u.step match {
              case EndTimeStep =>
                val buttons = EndTimeStep.buttonsWithStart(u.startTime.get)
                if (buttons.length > 0) Right(buttons)
                else Left("선택할 수 있는 시간이 없습니다")

              case s: Step => Right(s.buttons)
              case _ => Left("Undefined Step while deciding button")
            }
          }

        } yield (for {
                   u <- newUserEither.right
                   msg <- msgEither.right
                   btn <- btnEither.right
                 } yield (u, msg, btn))
          .fold(
            { err =>
              Ok(responseTemplate(err, MainMenuStep.buttons)) },
            { case (u, msg, btn) =>
              Ok(responseTemplate(msg, btn)) }
          )
      }
    )
  }
}
