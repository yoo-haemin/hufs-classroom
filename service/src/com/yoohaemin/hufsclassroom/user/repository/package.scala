package com.yoohaemin.hufsclassroom.user

import com.yoohaemin.hufsclassroom.model._
import shapeless.tag

package object repository {

  type UserDTO = (String, String)

  implicit class UserConversions(dto: UserDTO) {
    def toUser: User =
      User(
        tag[UserIdTag][String](dto._1),
        UserStatus.withName(dto._2)

      )
  }

}
