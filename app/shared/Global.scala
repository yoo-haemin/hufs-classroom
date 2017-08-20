package shared

import play.api.libs.json._
import java.time.{ ZonedDateTime, ZoneId }

//Constants or constnat builders that are not messages
object Global {
  final val ZONEID = "Asia/Seoul"

  val baseStartTime = 9
  val baseEndTime = 17

  val classroomList = ("1202" :: "1203" :: "1204" :: "1205" :: "1206" :: "1207" :: "1208" :: Nil) ++
    ("1210" :: "1211" :: "1301" :: "1302" :: "1303" :: "1304" :: "1305" :: "1306" :: "1307" :: Nil) ++
    ("1308" :: "1309" :: "1310" :: "1311" :: "1401" :: "1402" :: "1403" :: "1404" :: "1405" :: Nil) ++
    ("1406" :: "1407" :: "1408" :: "1409" :: "1501" :: "1502" :: "1503" :: "1504" :: "1505" :: Nil) ++
    ("1506" :: "1507" :: "1508" :: "1509" :: "1601" :: "1602" :: "1603" :: "1604" :: "1605" :: Nil) ++
    ("1606" :: "1607" :: "1608" :: "1609" :: "2201" :: "2202" :: "2203" :: "2204" :: "2205" :: Nil) ++
    ("2207" :: "2208" :: "2210" :: "2401" :: "2402" :: "2403" :: "2404" :: "2405" :: "2407" :: Nil) ++
    ("2408" :: "2409" :: "2501" :: "2502" :: "2503" :: "2504" :: "2506" :: "2507" :: "2508" :: Nil) ++
    ("2509" :: "3201" :: "3203" :: "3205" :: "3206" :: "3301" :: "3302" :: "3303" :: "3304" :: Nil) ++
    ("3305" :: "3306" :: "3307" :: "3308" :: "3401" :: "3402" :: "3403" :: "3404" :: "3405" :: Nil) ++
    ("3406" :: "3407" :: "3408" :: "3409" :: "3501" :: "3502" :: "3503" :: "3504" :: "3505" :: Nil) ++
    ("3506" :: "3507" :: "3508" :: "3509" :: "C301" :: "C302" :: "C304" :: "C305" :: "C306" :: Nil) ++
    ("C307" :: "C308" :: "C309" :: "C310" :: "C311" :: "C401" :: "C402" :: "C405" :: "C406" :: Nil) ++
    ("C407" :: "C408" :: "C409" :: "C410" :: "C411" :: "C412" :: "C413" :: "C501" :: "C509" :: Nil) ++
    ("C510" :: "C513" :: "C514" :: "C515" :: "C606" :: "C614" :: "C615" :: "C616" :: Nil) ++
    ("0109" :: "0115" :: "0116" :: "0117" :: "0118" :: "0221" :: "0225" :: "0328" :: "0329" :: "0330" :: "0338" :: Nil)


  def keyboardTemplate(buttons: Seq[String]) = Json.obj(
    "type" -> "buttons",
    "buttons" -> buttons
  )

  def responseTemplate(message: String, buttons: Seq[String]) = Json.obj(
    "message" -> Map(
      "text" -> message
    ),
    "keyboard" -> keyboardTemplate(buttons)
  )

  def nowIsApplicable() = {
    val time = ZonedDateTime.now(ZoneId.of(ZONEID))
    val dow = time.getDayOfWeek.getValue()
    val hour = time.getHour()
    dow <= 5 && dow >= 1 && hour >= 9 && hour < 17
  }
}
