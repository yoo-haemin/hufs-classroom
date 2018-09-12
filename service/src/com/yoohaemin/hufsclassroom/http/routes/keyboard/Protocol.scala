package com.yoohaemin.hufsclassroom.http.routes.keyboard

import cats.data.NonEmptyList
import shapeless.tag.@@
import io.circe.{ Encoder, Json }

object Protocol {

  type ButtonTag
  type Button = String @@ ButtonTag

  sealed trait Response
  case class Buttons(buttons: NonEmptyList[Button]) extends Response

  implicit val responseEncoder: Encoder[Response] = {
    case Buttons(buttons) =>
      Json.obj(
        "type" -> Json.fromString("buttons"),
        "buttons" -> Json.arr(buttons.map(Json.fromString).toList: _*)
      )
  }
}
