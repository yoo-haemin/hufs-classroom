package com.yoohaemin.hufs_classroom

import com.yoohaemin.hufs_classroom.models.kakao_api.{Buttons, Keyboard, ResponseMessage, Text}
import io.circe._
//import io.circe.syntax._

package object services {
  implicit val keyboardEncoder = new Encoder[Keyboard] {
    override final def apply(kbd: Keyboard): Json = kbd match {
      case Buttons(b) =>
        Json.obj(
          "type" -> Json.fromString("buttons"),
          "buttons" -> Json.fromValues(b.map(b => Json.fromString(b)).toList)
        )
      case Text =>
        Json.obj("type" -> Json.fromString("text"))
    }
  }

  implicit val responseMessageEncoder = new Encoder[ResponseMessage] {
    override final def apply(m: ResponseMessage): Json = {
//      Json.fromValues(
//
//      )

      ???
      //m match { case }
    }
  }
}



