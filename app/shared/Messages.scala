package shared

import java.time.DayOfWeek
import java.time.DayOfWeek._

object Messages {

  val timeFirst = "시간 먼저"
  val buildingFirst = "건물 먼저"

  val now = "지금부터"
  val setStartTime = "시작시간 설정"

  def dowMsg(dow: DayOfWeek): String = dow match {
    case MONDAY    => "월요일"
    case TUESDAY   => "화요일"
    case WEDNESDAY => "수요일"
    case THURSDAY  => "목요일"
    case FRIDAY    => "금요일"
    case _         => ""
  }

  val startTimes = (9 to 16).zip((9 to 16).map(_.toString + "시부터"))
  def endTimes(start: Int) = (start to 17).zip((start to 17).map(_.toString + "시까지"))

  val changeStartDow = "요일 바꾸기"
  val changeStartTime = "시작시간 바꾸기"
  val changeEndTime = "끝시간 바꾸기"
  val changeBuilding = "건물 바꾸기"
  val finish = "끝내기"
}
