package shared

import play.api.libs.json._
import models.Building
import java.time.{ ZonedDateTime, ZoneId, DayOfWeek }

object Global {
  final val ZONEID = "Asia/Seoul"

  val SHOW_LIMIT = 40

  def keyboardTemplate(buttons: Seq[String]) = Json.obj(
    "type" -> "buttons",
    "buttons" -> buttons
  )

  def responseTemplate(message: String, buttons: Seq[String]) = Json.obj(
    "message" -> Map(
      "text" -> message
    ),
    "keyboard" -> keyboardTemplate(buttons)
  )

  def nowIsApplicable() = {
    val time = ZonedDateTime.now(ZoneId.of(ZONEID))
    val dow = time.getDayOfWeek.getValue()
    val hour = time.getHour()
    dow <= 5 && dow >= 1 && hour >= 9 && hour < 17
  }
}
