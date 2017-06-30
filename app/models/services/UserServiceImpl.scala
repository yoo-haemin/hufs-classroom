package models.services

import javax.inject.Inject
import models.{ User, Building }
import scala.concurrent.Future
import models.daos.UserDAO

class UserServiceImpl @Inject()(userDAO: UserDAO) extends UserService {
  import scala.concurrent.ExecutionContext.Implicits.global

  def exists(userKey: String): Future[Boolean] = userDAO.exists(userKey)

  //Creates a new User if does not exist
  def findByKey(userKey: String): Future[User] = userDAO.findByKey(userKey)

  def save(userKey: String): Future[User] = userDAO.save(userKey)

  def clearStatus(userKey: String): Future[User] = userDAO.clearStatus(userKey)

  def update(userKey: String, building: Building): Future[User] = userDAO.update(userKey, building)

  def size(): Future[Int] = userDAO.size()
}
