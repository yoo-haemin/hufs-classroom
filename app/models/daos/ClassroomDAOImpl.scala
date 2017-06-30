package models.daos

import javax.inject.Inject
import models.{ Building, Classroom }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.time.DayOfWeek

class ClassroomDAOImpl @Inject() () extends ClassroomDAO {
  import ClassroomDAOImpl._

  def add(classroom: Classroom): Future[Classroom] = Future {
    data = data + classroom
    classroom
  }

  def getEmptyRooms(dow: DayOfWeek, time: Int): Future[Seq[Classroom]]

  def getEmptyRooms(dow: DayOfWeek, time: Int, building: Building): Future[Seq[Classroom]]
}

object ClassroomDAOImpl {
  var data = Set[Classroom]()
}
