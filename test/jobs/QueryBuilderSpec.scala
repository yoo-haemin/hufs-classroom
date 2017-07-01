
import play.api.inject.guice._
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import play.api.test.CSRFTokenHelper._

import models.{ User, Step }
import models.Step._
import models.daos.UserDAOImpl._
import jobs.QueryBuilder

import scala.concurrent.duration.Duration
import shared.Messages._
import scala.concurrent.{ Future, Await }

class QueryBuilderSpec extends PlaySpec with GuiceOneAppPerTest {
  val application = new GuiceApplicationBuilder().build()
  val queryBuilder = application.injector.instanceOf[QueryBuilder]
  val k = "somekey"

  import queryBuilder._

  "nextStep" should {
    "asdf" in {
      val testUser = User(k)
      val content = timeFirst
      val step = Await.ready(nextStep(testUser, content), Duration.Inf).value.get.toOption.flatten.get

      step must equal (StartTimeStep)
      data.head must equal(User(k, step = StartTimeStep))
      println(data)
    }
  }
}
