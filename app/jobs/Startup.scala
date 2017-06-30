package jobs

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

import models.{ User }
import models.services.UserService

import play.api.libs.concurrent.Execution.Implicits._

/**
 * Startup: Reads JSON and creates DB
 */
@Singleton
class Startup @Inject() (userService: UserService) {

}
