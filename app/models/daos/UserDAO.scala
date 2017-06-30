package models.daos

import models._
import scala.concurrent.Future

trait UserDAO {
  def exists(userKey: String): Future[Boolean]

  //Creates a new User if does not exist
  def findByKey(userKey: String): Future[User]

  def save(userKey: String): Future[User]

  def clearStatus(userKey: String): Future[User]

  def update(userKey: String, building: Building): Future[User]

  def update(userKey: String, step: Step): Future[User]

  def size(): Future[Int]
}
