package com.yoohaemin.hufsclassroom.model

import enumeratum._
import enumeratum.EnumEntry.Snakecase

sealed trait UserStatus extends EnumEntry with Snakecase

object UserStatus extends Enum[UserStatus] {

  val values = findValues

  case object Keyboard extends UserStatus

}
