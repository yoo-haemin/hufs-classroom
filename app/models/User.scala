package models

import java.time.DayOfWeek

case class User(
  userKey: String,
  step: Step,
  building: Option[Building] = None,
  dow: Option[DayOfWeek] = None,
  startTime: Option[Int] = None,
  endTime: Option[Int] = None
)
