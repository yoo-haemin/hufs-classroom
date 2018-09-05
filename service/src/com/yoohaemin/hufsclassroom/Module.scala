package com.yoohaemin.hufsclassroom

import cats.effect.Effect
import doobie.util.transactor.Transactor
import org.http4s.HttpService
import com.yoohaemin.hufsclassroom.http.{HttpErrorHandler, UserHttpEndpoint}
import com.yoohaemin.hufsclassroom.repository.PostgresUserRepository
import com.yoohaemin.hufsclassroom.repository.algebra.UserRepository
import com.yoohaemin.hufsclassroom.service.UserService

// Custom DI module
class Module[F[_] : Effect] {

  private val xa: Transactor[F] =
    Transactor.fromDriverManager[F](
      "org.postgresql.Driver", "jdbc:postgresql:users", "postgres", "postgres"
    )

  private val userRepository: UserRepository[F] = new PostgresUserRepository[F](xa)

  private val userService: UserService[F] = new UserService[F](userRepository)

  implicit val httpErrorHandler: HttpErrorHandler[F] = new HttpErrorHandler[F]

  val userHttpEndpoint: HttpService[F] = new UserHttpEndpoint[F](userService).service

}
