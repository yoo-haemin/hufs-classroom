package shared

import play.api.libs.json._
import models.Building

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
}
