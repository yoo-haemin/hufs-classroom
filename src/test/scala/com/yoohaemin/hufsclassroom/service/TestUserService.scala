package com.yoohaemin.hufsclassroom.service

import cats.effect.IO
import com.yoohaemin.hufsclassroom.TestUsers.users
import com.yoohaemin.hufsclassroom.model.UserName
import com.yoohaemin.hufsclassroom.repository.algebra.UserRepository

object TestUserService {

  private val testUserRepo: UserRepository[IO] =
    (username: UserName) => IO {
      users.find(_.username.value == username.value)
    }

  val service: UserService[IO] = new UserService[IO](testUserRepo)

}
