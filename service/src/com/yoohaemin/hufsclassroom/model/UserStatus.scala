package com.yoohaemin.hufsclassroom.model

import enumeratum._
import enumeratum.EnumEntry.Snakecase

sealed trait UserStatus extends EnumEntry with Snakecase

object UserStatus extends Enum[UserStatus] {

  val values = findValues

  case object MainMenuStep extends UserStatus
  case object DecideNowStep extends UserStatus
  case object DOWStep extends UserStatus
  case object StartTimeStep extends UserStatus
  case object EndTimeStep extends UserStatus
  case object BuildingSelectionStep extends UserStatus
  case object ExecuteStep extends UserStatus
}
