package com.yoohaemin.hufs_classroom

import com.yoohaemin.hufs_classroom.models.kakao_api.{Buttons, Keyboard, ResponseMessage, Text}
import doobie._
import cats.effect.IO

package object services {
  val xa = Transactor.fromDriverManager[IO](
    "org.sqlite.jdbc", "jdbc:sqlite:hufs_classroom.db"
  )
  import io.circe._

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
    override final def apply(m: ResponseMessage): Json = ???
  }
}



