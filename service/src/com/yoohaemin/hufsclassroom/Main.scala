package com.yoohaemin.hufsclassroom

import cats.effect.{ ConcurrentEffect, ContextShift, IO, IOApp, ExitCode }
import org.http4s.server.blaze.BlazeBuilder

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    new Server[IO].stream.compile.drain.map(_ => ExitCode.Success)
  }
}

class Server[F[_]: ContextShift: ConcurrentEffect] {
  private val module = new Module[F]
  val stream = BlazeBuilder[F]
      .bindHttp(2929)
//      .mountService(module.userHttpEndpoint, "/users")
      .serve
}