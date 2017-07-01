package models.daos

import java.time.DayOfWeek
import models._
import scala.concurrent.Future

trait UserDAO {
  def exists(userKey: String): Future[Boolean]

  //Creates a new User if does not exist
  def findByKey(userKey: String): Future[User]

  def save(userKey: String): Future[User]

  def clearStatus(userKey: String): Future[User]

  def update(user: User): Future[User]

  def updateBuilding(userKey: String, building: Building): Future[User]

  def updateStep(userKey: String, step: Step): Future[User]

  def updateStartTime(userKey: String, startTime: Int): Future[User]

  def updateEndTime(userKey: String, endTime: Int): Future[User]

  def updateDow(userKey: String, dayOfWeek: DayOfWeek): Future[User]

  def size(): Future[Int]
}
