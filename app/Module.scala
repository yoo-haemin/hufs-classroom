import javax.inject._

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import play.api.{Configuration, Environment}

import jobs._
import models.daos._
import models.services._

/**
  * Sets up custom components for Play.
  */
class Module(environment: Environment, configuration: Configuration)
    extends AbstractModule
    with ScalaModule {

  override def configure() = {
    bind[UserDAO].to[UserDAOImpl]
    bind[UserService].to[UserServiceImpl]
    bind[ClassroomDAO].to[ClassroomDAOImpl]
    bind[ClassroomService].to[ClassroomServiceImpl]

    bind[Startup].asEagerSingleton()
  }
}
