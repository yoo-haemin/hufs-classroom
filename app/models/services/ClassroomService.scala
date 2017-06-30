package models.services

import java.util.UUID
import java.time.DayOfWeek

import scala.concurrent.Future
import scala.concurrent.duration._

import models._

trait ClassroomService {
  def add(classroom: Classroom): Future[Classroom]

  def find(dow: DayOfWeek, startTime: Int, endTime: Int, building: Option[Building]): Future[Seq[Classroom]]
}
