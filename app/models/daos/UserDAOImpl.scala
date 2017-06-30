package models.daos

import javax.inject.Inject
import java.time.DayOfWeek
import models.{ User, Building, Step }
import scala.concurrent.Future

class UserDAOImpl @Inject()() extends UserDAO {
  import scala.concurrent.ExecutionContext.Implicits.global

  def exists(userKey: String): Future[Boolean] =
    Future(UserDAOImpl.data.filter(_.userKey == userKey).size > 0)

  //Creates a new User if does not exist
  def findByKey(userKey: String): Future[User] =
    Future.successful {
      val user = UserDAOImpl.data.filter(_.userKey == userKey)
      if (user.size > 0) user.head
      else User(userKey)
    }

  def save(userKey: String): Future[User] = {
    Future.successful {
      val newUser = User(userKey)
      UserDAOImpl.data = UserDAOImpl.data + newUser
      newUser
    }
  }

  def clearStatus(userKey: String): Future[User] =
    Future.successful {
      val newUser = User(userKey)
      UserDAOImpl.data = UserDAOImpl.data.filterNot(_.userKey == userKey) + newUser
      newUser
    }

  def updateBuilding(userKey: String, building: Building): Future[User] =
    Future.successful {
      val user = UserDAOImpl.data.filter(_.userKey == userKey).head.copy(building = Some(building))
      UserDAOImpl.data = UserDAOImpl.data.filterNot(_.userKey == userKey) + user
      user
    }

  def updateStep(userKey: String, step: Step): Future[User] =
    Future.successful {
      val user = UserDAOImpl.data.filter(_.userKey == userKey).head.copy(step = step)
      UserDAOImpl.data = UserDAOImpl.data.filterNot(_.userKey == userKey) + user
      user
    }

  def updateStartTime(userKey: String, startTime: Int): Future[User] =
    Future.successful {
      val user = UserDAOImpl.data.filter(_.userKey == userKey).head.copy(startTime = Some(startTime))
      UserDAOImpl.data = UserDAOImpl.data.filterNot(_.userKey == userKey) + user
      user
    }


  def updateEndTime(userKey: String, endTime: Int): Future[User] =
    Future.successful {
      val user = UserDAOImpl.data.filter(_.userKey == userKey).head.copy(endTime = Some(endTime))
      UserDAOImpl.data = UserDAOImpl.data.filterNot(_.userKey == userKey) + user
      user
    }


  def updateDow(userKey: String, dayOfWeek: DayOfWeek): Future[User] =
    Future.successful {
      val user = UserDAOImpl.data.filter(_.userKey == userKey).head.copy(dow = Some(dayOfWeek))
      UserDAOImpl.data = UserDAOImpl.data.filterNot(_.userKey == userKey) + user
      user
    }



  def size(): Future[Int] = Future(UserDAOImpl.data.size)
}

object UserDAOImpl {
  var data = Set[User]()
}
