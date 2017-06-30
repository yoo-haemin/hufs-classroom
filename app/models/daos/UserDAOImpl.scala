package models.daos

import javax.inject.Inject
import models.{ User, Building }
import scala.concurrent.Future

class UserDAOImpl @Inject()() extends UserDAO {
  import scala.concurrent.ExecutionContext.Implicits.global

  def exists(userKey: String): Future[Boolean] =
    Future(UserDAOImpl.data.filter(_.userKey == userKey).size > 0)

  //Creates a new User if does not exist
  def findByKey(userKey: String): Future[User] =
    Future {
      val user = UserDAOImpl.data.filter(_.userKey == userKey)
      if (user.size > 0) user.head
      else User(userKey)
    }

  def save(userKey: String): Future[User] = {
    Future {
      val newUser = User(userKey)
      UserDAOImpl.data = UserDAOImpl.data + newUser
      newUser
    }
  }

  def clearStatus(userKey: String): Future[User] =
    Future {
      val newUser = User(userKey)
      UserDAOImpl.data = UserDAOImpl.data.filterNot(_.userKey == userKey) + newUser
      newUser
    }

  def update(userKey: String, building: Building): Future[User] =
    Future {
      val user = UserDAOImpl.data.filter(_.userKey == userKey).head.copy(building = Some(building))
      UserDAOImpl.data = UserDAOImpl.data.filterNot(_.userKey == userKey) + user
      user
    }

  def size(): Future[Int] = Future(UserDAOImpl.data.size)
}

object UserDAOImpl {
  var data = Set[User]()
}
