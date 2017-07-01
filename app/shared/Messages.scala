package shared

import java.time.DayOfWeek
import java.time.DayOfWeek._
import models.Step.MainMenuStep
import models.Building

object Messages {

  val timeFirst = "시간 먼저"
  val buildingFirst = "건물 먼저"
  val aboutButton = "이 서비스는?"
  val about = MainMenuStep.message + """

뭔가가 잘못되어있다! 혹은 이런 기능은 좋겠다~~ 이런건 haemin@posteo.net으로 부탁드려요.

아쉽지만, 본 서비스가 항상 옳은 정보를 제공한다는 것은 불가능합니다. 본 서비스의 이용으로 인한 피해에 대해 서비스 제작자는 책임지지 않습니다. 다리가 너무너무 아프면 미안합니다 ㅠㅠ

한국외대 서울캠 빈강의실 찾기는 GNU AGPL3 라이선스로 배포되는 자유 소프트웨어입니다.
본 프로그램의 소스코드는 https://github.com/yoo-haemin/hufs-classroom 에 있습니다."""

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

  val cantAccept = "저는 지정된 메세지 이외의 것은 못 받아요 ㅠㅠ 다시 선택해주세요!"

  def selections(dow: DayOfWeek, startTime: Int, endTime: Int, building: Option[Building]) =
    dowMsg(dow) + " " + startTime + "시부터 " + endTime + "시까지" + building.fold("를")(b => s" ${b.name}에 있는 강의실을") + " 기준으로 검색한 결과:\n"
}
