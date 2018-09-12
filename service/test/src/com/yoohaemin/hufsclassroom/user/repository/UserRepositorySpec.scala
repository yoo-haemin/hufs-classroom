package com.yoohaemin.hufsclassroom.user.repository

import cats.effect.IO
import com.yoohaemin.hufsclassroom.IOAssertion
import com.yoohaemin.hufsclassroom.TestUsers._

class UserRepositorySpec extends RepositorySpec {

  private val repo = new PostgresUserRepository[IO](transactor)

  test("Find a user"){
    IOAssertion {
      for {
        user <- repo.findUser(users.head.username)
      } yield {
        user.fold(fail("User not found")) { u =>
          assert(u.username.value == users.head.username.value)
          assert(u.email.value == users.head.email.value)
        }
      }
    }
  }

  test("NOT find a user"){
    IOAssertion {
      for {
        user <- repo.findUser(users.last.username)
      } yield {
        assert(user.isEmpty)
      }
    }
  }

  test("find user query") {
    check(UserStatement.findUser(users.head.username))
  }

}
