package models.services

import java.util.UUID
import java.time.DayOfWeek

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

trait ClassroomService {
  def find(dow: DayOfWeek)(time: Seq[Int])
}
