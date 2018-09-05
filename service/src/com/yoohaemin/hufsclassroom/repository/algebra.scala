package com.yoohaemin.hufsclassroom.repository

import com.yoohaemin.hufsclassroom.model.{User, UserName}

object algebra {

  trait UserRepository[F[_]] {
    def findUser(username: UserName): F[Option[User]]
  }

}
