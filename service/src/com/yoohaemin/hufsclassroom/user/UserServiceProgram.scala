package com.yoohaemin.hufsclassroom.user

import cats.effect.Sync
import com.yoohaemin.hufsclassroom.model.{ UserId, UserStatus }
import com.yoohaemin.hufsclassroom.user.algebra.UserService

class UserServiceProgram[F[_]: Sync] extends UserService[F] {

  override def getStatus(userId: UserId): F[UserStatus] = ???

  override def create(userId: UserId): F[Unit] = ???

}
