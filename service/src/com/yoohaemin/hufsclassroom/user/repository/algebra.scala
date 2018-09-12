package com.yoohaemin.hufsclassroom.user.repository

import com.yoohaemin.hufsclassroom.model.{ UserId, UserStatus }

object algebra {

  trait UserRepository[F[_]] {
    def getUserStatus(username: UserId): F[Option[UserStatus]] = ???
  }

}
