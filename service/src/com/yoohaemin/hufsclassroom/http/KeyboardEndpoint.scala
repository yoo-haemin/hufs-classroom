package com.yoohaemin.hufsclassroom.http

import cats.effect.Sync
import com.yoohaemin.hufsclassroom.user.algebra.UserService
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

class KeyboardEndpoint[F[_]: Sync](userService: UserService[F]) extends Http4sDsl[F] {

  val service: HttpRoutes[F] = HttpRoutes.of[F] {

    case GET -> Root =>
      Ok("Hello!")

  }

}
