package com.yoohaemin.hufs_classroom

import cats.effect.IO
import fs2.Stream
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.{ExitCode, StreamApp}
import Services._

object Main extends StreamApp[IO] {
  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] =
    BlazeBuilder[IO]
      .bindHttp(9000, "localhost")
      .mountService(message, "/message")
      .mountService(keyboard, "/keyboard")
      .mountService(friend, "/friend")
      .mountService(chatRoom, "/chat_room")
      .serve

}
