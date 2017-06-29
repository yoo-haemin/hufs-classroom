package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json._

import java.time.{ZonedDateTime, ZoneId}

/**
  * A very small controller that renders a home page.
  */
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def keyboard = Action { implicit req =>
    val now = ZonedDateTime.now(ZoneId.of(shared.Global.zoneid))
    val nowHour = now.getHour()
    val nowDow = now.getDayOfWeek()
    val canLookupNow = nowHour >= 9 && nowHour < 17 && nowDow.getValue >= 1 && nowDow.getValue <= 5

    Ok(
      Json.obj(
        "type" -> "buttons",
        "buttons" ->
          (if (canLookupNow) Json.arr("지금", "시간지정", "다른학기")
          else Json.arr("시간지정, 다른학기"))
      )
    )
  }

  def message = Action { implicit req =>
    Ok(

    )
  }
}
