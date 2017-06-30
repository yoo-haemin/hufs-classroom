package models.daos

import models.{ Building, Classroom }
import scala.concurrent.Future
import java.time.DayOfWeek

trait ClassroomDAO {
  def add(classroom: Classroom): Future[Classroom]

  def getEmptyRooms(dow: DayOfWeek, time: Int): Future[Seq[Classroom]]

  def getEmptyRooms(dow: DayOfWeek, time: Int, building: Building): Future[Seq[Classroom]]
}
