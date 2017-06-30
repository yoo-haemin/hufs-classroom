package shared

import play.api.libs.json._
import models.Building

object Global {
  final val ZONEID = "Asia/Seoul"

  def responseTemplate(message: String, buttons: Seq[String]) = Json.obj(
    "message" -> message,
    "keyboard" -> keyboardTemplate(buttons)
  )


  def keyboardTemplate(buttons: Seq[String]) = Json.obj(
    "type" -> "buttons",
    "buttons" -> buttons
  )

  //def mainMenu(canLookupNow: Boolean) =
  //  responseTemplate(
  //    (if (canLookupNow) Seq() else Seq()))

  //def timeMenu =
  //  responseTemplate((1 to 9).map(_.toString + "교시"))

  //def buildingMenu =
  //  responseTemplate(
  //    Seq("1", "2", "3", "C").map(Building.fromString(_).get.name)
  //  )

  //def floorMenu(building: Building) =
  //  responseTemplate(
  //    building.availableFloor.map(_.toString + " 층").toSeq
  //  )

  val SHOW_LIMIT = 40

  val TIME_FIRST = "시간 먼저"
  val BUILDING_FIRST = "건물 먼저"

  val NOW = "지금부터"
  val SET_START_TIME = "시작시간 설정"

  val START_TIMES = (9 to 16).map(_.toString + "시부터")
  val END_TIMES = (10 to 17).map(_.toString + "시까지")

}
/*
 "1202" -> (Building.fromString("1") -> 2)
 "1203" -> (Building.fromString("1") -> 2)
 "1204" -> (Building.fromString("1") -> 2)
 "1205" -> (Building.fromString("1") -> 2)
 "1206" -> (Building.fromString("1") -> 2)
 "1207" -> (Building.fromString("1") -> 2)
 "1208" -> (Building.fromString("1") -> 2)
 "1210" -> (Building.fromString("1") -> 2)
 "1211" -> (Building.fromString("1") -> 2)
 "1301" -> (Building.fromString("1") -> 3)
 "1302" -> (Building.fromString("1") -> 3)
 "1303" -> (Building.fromString("1") -> 3)
 "1304" -> (Building.fromString("1") -> 3)
 "1305" -> (Building.fromString("1") -> 3)
 "1306" -> (Building.fromString("1") -> 3)
 "1307" -> (Building.fromString("1") -> 3)
 "1308" -> (Building.fromString("1") -> 3)
 "1309" -> (Building.fromString("1") -> 3)
 "1310" -> (Building.fromString("1") -> 3)
 "1311" -> (Building.fromString("1") -> 3)
 "1401" -> (Building.fromString("1") -> 4)
 "1402" -> (Building.fromString("1") -> 4)
 "1403" -> (Building.fromString("1") -> 4)
 "1404" -> (Building.fromString("1") -> 4)
 "1405" -> (Building.fromString("1") -> 4)
 "1406" -> (Building.fromString("1") -> 4)
 "1407" -> (Building.fromString("1") -> 4)
 "1408" -> (Building.fromString("1") -> 4)
 "1409" -> (Building.fromString("1") -> 4)
 "1501" -> (Building.fromString("1") -> 5)
 "1502" -> (Building.fromString("1") -> 5)
 "1503" -> (Building.fromString("1") -> 5)
 "1504" -> (Building.fromString("1") -> 5)
 "1505" -> (Building.fromString("1") -> 5)
 "1506" -> (Building.fromString("1") -> 5)
 "1507" -> (Building.fromString("1") -> 5)
 "1508" -> (Building.fromString("1") -> 5)
 "1509" -> (Building.fromString("1") -> 5)
 "1601" -> (Building.fromString("1") -> 6)
 "1602" -> (Building.fromString("1") -> 6)
 "1603" -> (Building.fromString("1") -> 6)
 "1604" -> (Building.fromString("1") -> 6)
 "1605" -> (Building.fromString("1") -> 6)
 "1606" -> (Building.fromString("1") -> 6)
 "1607" -> (Building.fromString("1") -> 6)
 "1608" -> (Building.fromString("1") -> 6)
 "1609" -> (Building.fromString("1") -> 6)
 "2201" -> (Building.fromString("2") -> 2)
 "2202" -> (Building.fromString("2") -> 2)
 "2203" -> (Building.fromString("2") -> 2)
 "2204" -> (Building.fromString("2") -> 2)
 "2205" -> (Building.fromString("2") -> 2)
 "2207" -> (Building.fromString("2") -> 2)
 "2208" -> (Building.fromString("2") -> 2)
 "2210" -> (Building.fromString("2") -> 2)
 "2401" -> (Building.fromString("2") -> 4)
 "2402" -> (Building.fromString("2") -> 4)
 "2403" -> (Building.fromString("2") -> 4)
 "2404" -> (Building.fromString("2") -> 4)
 "2405" -> (Building.fromString("2") -> 4)
 "2407" -> (Building.fromString("2") -> 4)
 "2408" -> (Building.fromString("2") -> 4)
 "2409" -> (Building.fromString("2") -> 4)
 "2501" -> (Building.fromString("2") -> 5)
 "2502" -> (Building.fromString("2") -> 5)
 "2503" -> (Building.fromString("2") -> 5)
 "2504" -> (Building.fromString("2") -> 5)
 "2506" -> (Building.fromString("2") -> 5)
 "2507" -> (Building.fromString("2") -> 5)
 "2508" -> (Building.fromString("2") -> 5)
 "2509" -> (Building.fromString("2") -> 5)
 "3201" -> (Building.fromString("3") -> 2)
 "3203" -> (Building.fromString("3") -> 2)
 "3205" -> (Building.fromString("3") -> 2)
 "3206" -> (Building.fromString("3") -> 2)
 "3301" -> (Building.fromString("3") -> 3)
 "3302" -> (Building.fromString("3") -> 3)
 "3303" -> (Building.fromString("3") -> 3)
 "3304" -> (Building.fromString("3") -> 3)
 "3305" -> (Building.fromString("3") -> 3)
 "3306" -> (Building.fromString("3") -> 3)
 "3307" -> (Building.fromString("3") -> 3)
 "3308" -> (Building.fromString("3") -> 3)
 "3401" -> (Building.fromString("3") -> 4)
 "3402" -> (Building.fromString("3") -> 4)
 "3403" -> (Building.fromString("3") -> 4)
 "3404" -> (Building.fromString("3") -> 4)
 "3405" -> (Building.fromString("3") -> 4)
 "3406" -> (Building.fromString("3") -> 4)
 "3407" -> (Building.fromString("3") -> 4)
 "3408" -> (Building.fromString("3") -> 4)
 "3409" -> (Building.fromString("3") -> 4)
 "3501" -> (Building.fromString("3") -> 5)
 "3502" -> (Building.fromString("3") -> 5)
 "3503" -> (Building.fromString("3") -> 5)
 "3504" -> (Building.fromString("3") -> 5)
 "3505" -> (Building.fromString("3") -> 5)
 "3506" -> (Building.fromString("3") -> 5)
 "3507" -> (Building.fromString("3") -> 5)
 "3508" -> (Building.fromString("3") -> 5)
 "3509" -> (Building.fromString("3") -> 5)
 "C301" -> (Building.fromString("C") -> 3)
 "C302" -> (Building.fromString("C") -> 3)
 "C304" -> (Building.fromString("C") -> 3)
 "C305" -> (Building.fromString("C") -> 3)
 "C306" -> (Building.fromString("C") -> 3)
 "C307" -> (Building.fromString("C") -> 3)
 "C308" -> (Building.fromString("C") -> 3)
 "C309" -> (Building.fromString("C") -> 3)
 "C310" -> (Building.fromString("C") -> 3)
 "C311" -> (Building.fromString("C") -> 3)
 "C401" -> (Building.fromString("C") -> 4)
 "C402" -> (Building.fromString("C") -> 4)
 "C405" -> (Building.fromString("C") -> 4)
 "C406" -> (Building.fromString("C") -> 4)
 "C407" -> (Building.fromString("C") -> 4)
 "C408" -> (Building.fromString("C") -> 4)
 "C409" -> (Building.fromString("C") -> 4)
 "C410" -> (Building.fromString("C") -> 4)
 "C411" -> (Building.fromString("C") -> 4)
 "C412" -> (Building.fromString("C") -> 4)
 "C413" -> (Building.fromString("C") -> 4)
 "C501" -> (Building.fromString("C") -> 5)
 "C509" -> (Building.fromString("C") -> 5)
 "C510" -> (Building.fromString("C") -> 5)
 "C513" -> (Building.fromString("C") -> 5)
 "C514" -> (Building.fromString("C") -> 5)
 "C515" -> (Building.fromString("C") -> 5)
 "C606" -> (Building.fromString("C") -> 6)
 "C614" -> (Building.fromString("C") -> 6)
 "C615" -> (Building.fromString("C") -> 6)
 "C616" -> (Building.fromString("C") -> 6)
 */
