package com.yoohaemin.hufsclassroom.user

import com.yoohaemin.hufsclassroom.model.{UserId, UserStatus}

object algebra {

  trait UserService[F[_]] {

    def create(userId: UserId): F[Unit]

    def getStatus(userId: UserId): F[UserStatus]

  }

}
