package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json._
import java.time.{ZonedDateTime, ZoneId}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import models.{RequestApi, Step}
import models.services.UserService
import jobs.QueryBuilder

import shared.Global._

/**
  * Main Controller
  */
class HomeController @Inject()(
  cc: ControllerComponents, playBodyParsers: PlayBodyParsers, userService: UserService, queryBuilder: QueryBuilder) extends AbstractController(cc) {

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
        if (messageType != "text") Future.successful(BadRequest(Json.obj("code" -> 100)))
        else for {
          nextStep <- queryBuilder.build(userKey, content)
        } yield {
          if (nextStep.isDefined) Ok(nextStep.get) else BadRequest("")
        }
      }
    )
  }
}
