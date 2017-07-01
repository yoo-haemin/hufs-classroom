package models.services

import javax.inject.Inject
import java.time.DayOfWeek
import models.{ User, Building, Step }
import scala.concurrent.Future
import models.daos.UserDAO

class UserServiceImpl @Inject()(userDAO: UserDAO) extends UserService {
  import scala.concurrent.ExecutionContext.Implicits.global

  def exists(userKey: String): Future[Boolean] = userDAO.exists(userKey)

  //Creates a new User if does not exist
  def findByKey(userKey: String): Future[User] = userDAO.findByKey(userKey)

  def save(userKey: String): Future[User] = userDAO.save(userKey)

  def clearStatus(userKey: String): Future[User] = userDAO.clearStatus(userKey)

  def update(user: User): Future[User] =
    userDAO.update(user)

  def updateBuilding(userKey: String, building: Building): Future[User] =
    userDAO.updateBuilding(userKey, building)

  def updateStep(userKey: String, step: Step): Future[User] =
    userDAO.updateStep(userKey, step)

  def updateStartTime(userKey: String, startTime: Int): Future[User] =
    userDAO.updateStartTime(userKey, startTime)

  def updateEndTime(userKey: String, endTime: Int): Future[User] =
    userDAO.updateEndTime(userKey, endTime)

  def updateDow(userKey: String, dayOfWeek: DayOfWeek): Future[User] =
    userDAO.updateDow(userKey, dayOfWeek)

  def updateDowStartTime(userKey: String, dayOfWeek: DayOfWeek, startTime: Int): Future[User] =
    userDAO.updateDow(userKey, dayOfWeek) flatMap (_ => userDAO.updateStartTime(userKey, startTime))

  def size(): Future[Int] = userDAO.size()
}
