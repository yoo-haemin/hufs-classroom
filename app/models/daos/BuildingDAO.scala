package models.daos

import models.{ Building, Classroom }
import scala.concurrent.Future

trait BuildingDAO {
  def getAllRooms(building: Building): Future[Seq[Classroom]]
}
