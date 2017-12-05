package com.yoohaemin.hufs_classroom

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._

object Controllers {
  val keyboard = HttpService[IO] {
    case GET -> Root =>
      Ok(???)
  }

  val friend = HttpService[IO] {
    case GET -> Root / userKey =>
      Ok(???)
    case DELETE -> Root / userKey =>
      Ok(???)
  }

  val chatRoom = HttpService[IO] {
    case DELETE -> Root / userKey =>
      Ok(???)
  }

  val message = HttpService[IO] {
    case POST -> Root =>
      Ok(???)
  }
}
