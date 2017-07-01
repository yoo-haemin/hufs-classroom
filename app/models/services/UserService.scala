package models.services

import javax.inject.Inject
import models._
import java.time.DayOfWeek
import scala.concurrent.Future

trait UserService {

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
  def updateDowStartTime(userKey: String, dayOfWeek: DayOfWeek, startTime: Int): Future[User]

  def size(): Future[Int]
}
