package models

import scala.util.Try

import shared.Messages
import shared.Global._
import java.time.{ ZonedDateTime, ZoneId, DayOfWeek }

sealed abstract class Step {
  def buttons: Seq[String]
  def process(user: User, request: String): Either[String,User]
  def message: String
}

object Step {

  private def errorMessage(step: Step, message: String) =
    s"Unrecognized message in $step: $message"

  object MainMenuStep extends Step {
    override def toString = "MainMenuStep"

    override def buttons =
      Seq(Messages.timeFirst, Messages.buildingFirst, Messages.aboutButton)

    override def process(user: User, request: String): Either[String, User] = request match {
      case Messages.timeFirst =>
        if (nowIsApplicable()) Right(user.copy(step = DecideNowStep))
        else Right(user.copy(step = DOWStep))
      case Messages.buildingFirst =>
        Right(user.copy(step = BuildingSelectionStep))
      case _ =>
        Left(errorMessage(user.step, request))
    }

    override def message = """한국외대 서울캠 빈 강의실 찾기!! 학교 강의시간표를 기준으로 빈 강의실을 찾아줍니다.
현재 시간표는 2017년 2학기 기준입니다.
강의가 없다고 해서 강의실이 항상 사용가능한 것은 아니라는 거 주의해주세요~""" +
      (if(nowIsApplicable()) """
현재 시간 기준으로 찾기 기능이 사용 가능합니다."""
       else """
현재 시간 기준으로 찾기 기능은 평일 9시부터 17시 전까지만 사용 가능합니다.""")
  }

  object DecideNowStep extends Step {
    override def toString = "DecideNowStep"

    override def buttons =
      Seq(Messages.now, Messages.setStartTime)

    override def process(user: User, request: String) = request match {
      case Messages.now =>
        val time = ZonedDateTime.now(ZoneId.of(ZONEID))
        val dow = time.getDayOfWeek()
        val hour = time.getHour()
        Right(user.copy(step = EndTimeStep, dow = Some(dow), startTime = Some(hour)))

      case Messages.setStartTime =>
        Right(user.copy(step = DOWStep))

      case _ =>
        Left(errorMessage(user.step, request))

    }

    override def message = "시작시간은 어떻게 할까요?"
  }

  object DOWStep extends Step {
    override def toString = "DOWStep"

    override def buttons = (1 to 5).map(i => Messages.dowMsg(DayOfWeek.of(i)))

    override def process(user: User, request: String) = request match {
      case s if s == Messages.dowMsg(DayOfWeek.of(1)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(1)), step = StartTimeStep))
      case s if s == Messages.dowMsg(DayOfWeek.of(2)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(2)), step = StartTimeStep))
      case s if s == Messages.dowMsg(DayOfWeek.of(3)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(3)), step = StartTimeStep))
      case s if s == Messages.dowMsg(DayOfWeek.of(4)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(4)), step = StartTimeStep))
      case s if s == Messages.dowMsg(DayOfWeek.of(5)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(5)), step = StartTimeStep))
      case _ =>
        Left(errorMessage(user.step, request))
    }

    override def message = "요일을 정해주세요"
  }

  object StartTimeStep extends Step {
    override def toString = "StartTimeStep"

    override def buttons = (9 to 16).map(_.toString + "시부터")

    val R = """(\d{1,2})시부터""".r

    override def process(user: User, request: String) = request match {
      case R(timeStr) if Try(timeStr.toInt).isSuccess =>
        val time = timeStr.toInt
        if (time < 9 || time >= 17) Left(s"Wrong time: $timeStr")
        else Right(user.copy(step = EndTimeStep, startTime = Some(time)))

      case _ => Left(errorMessage(user.step, request))
    }

    override def message = "시작시간을 정해주세요"
  }

  object EndTimeStep extends Step {
    override def toString = "EndTimeStep"

    override def buttons = buttonsWithStart(10)

    def buttonsWithStart(startTime: Int) = (startTime + 1 to 17).map(_.toString + "시까지")

    val R = """(\d{2})시까지""".r

    override def process(user: User, request: String) = request match {
      case R(timeStr) if Try(timeStr.toInt).isSuccess =>
        val time = timeStr.toInt
        if (time < 10 || time >= 18) Left(s"Wrong time: $timeStr")
        else Right(user.copy(step = ExecuteStep, endTime = Some(time)))

      case _ => Left(errorMessage(user.step, request))
    }

    override def message = "끝나는 시간을 정해주세요"
  }

  object BuildingSelectionStep extends Step {
    override def toString = "BuildingSelectionStep"

    override def buttons = Seq("1", "2", "3", "C", "0").map(Building.fromString(_).get.name)

    override def process(user: User, request: String) = Building.fromString(request) match {
      case Some(building) =>
        user.endTime match {
          case Some(_) => Right(user.copy(step = ExecuteStep, building = Some(building)))
          case None =>
            if (nowIsApplicable()) Right(user.copy(step = DecideNowStep, building = Some(building)))
            else Right(user.copy(step = DOWStep, building = Some(building)))
        }
      case None =>
        Left(errorMessage(user.step, request))
    }

    override def message = "건물을 정해주세요"
  }

  object ExecuteStep extends Step {
    override def toString = "ExecuteStep"
    override def buttons = Seq(Messages.toMainMenu, Messages.changeBuilding, Messages.changeStartDow, Messages.changeStartTime, Messages.changeEndTime)
    override def process(user: User, request: String) = request match {
      case Messages.changeStartDow =>
        Right(user.copy(step = DOWStep))

      case Messages.changeStartTime =>
        Right(user.copy(step = StartTimeStep))

      case Messages.changeEndTime =>
        Right(user.copy(step = EndTimeStep))

      case Messages.changeBuilding =>
        Right(user.copy(step = BuildingSelectionStep))

      case Messages.toMainMenu =>
        Right(user.copy(step = MainMenuStep))

      case _ =>
        Left(errorMessage(user.step, request))
    }

    override def message = "이걸 보고 계시다면, 서비스에 이상이 있는 겁니다. 제작자에게 어떻게 해서 이걸 보게 되었는지 알려주세요~!"
  }
}
