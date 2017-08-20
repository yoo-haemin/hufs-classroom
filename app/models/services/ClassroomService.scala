package models.services

import java.time.DayOfWeek

import scala.concurrent.Future

import models._

trait ClassroomService {
  def add(classroom: Classroom): Future[Classroom]

  def find(dow: DayOfWeek, startTime: Int, endTime: Int, building: Option[Building]): Future[Seq[Classroom]]
}
