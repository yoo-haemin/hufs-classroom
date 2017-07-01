import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import play.api.test.CSRFTokenHelper._

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest {

  "HomeController" should {

    "render the keyboard json" in {
      val request = FakeRequest(GET, "/keyboard").withHeaders(HOST -> "localhost:9000").withCSRFToken
      val keyboard = route(app, request).get
      contentAsString(keyboard) must include ("시간 먼저")
    }

  }

}
