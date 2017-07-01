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
  import shared.Messages._

  private def errorMessage(step: Step, message: String) =
    s"Unrecognized message in $step: $message"

  object MainMenuStep extends Step {
    override def toString = "MainMenuStep"

    override def buttons =
      Seq(timeFirst, buildingFirst)

    override def process(user: User, request: String): Either[String, User] = request match {
      case Messages.timeFirst =>
        Right(user.copy(step = DecideNowStep))
      case Messages.buildingFirst =>
        Right(user.copy(step = BuildingSelectionStep))
      case _ =>
        Left(errorMessage(user.step, request))
    }

    override def message = "먼저 "
  }

  object DecideNowStep extends Step {
    override def toString = "DecideNowStep"

    override def buttons =
      Seq(now, setStartTime)

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
  }

  object DOWStep extends Step {
    override def toString = "DOWStep"

    override def buttons = (1 to 5).map(i => dowMsg(DayOfWeek.of(i)))

    //TODO can be made cleaner
    override def process(user: User, request: String) = request match {
      case s if s == dowMsg(DayOfWeek.of(1)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(1)), step = StartTimeStep))
      case s if s == dowMsg(DayOfWeek.of(2)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(2)), step = StartTimeStep))
      case s if s == dowMsg(DayOfWeek.of(3)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(3)), step = StartTimeStep))
      case s if s == dowMsg(DayOfWeek.of(4)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(4)), step = StartTimeStep))
      case s if s == dowMsg(DayOfWeek.of(5)) =>
        Right(user.copy(dow = Some(DayOfWeek.of(5)), step = StartTimeStep))
      case _ =>
        Left(errorMessage(user.step, request))
    }
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
  }

  object BuildingSelectionStep extends Step {
    override def toString = "BuildingSelectionStep"

    override def buttons = Seq("1", "2", "3", "C", "0").map(Building.fromString(_).get.name)

    override def process(user: User, request: String) = Building.fromString(request) match {
      case Some(building) =>
        user.endTime match {
          case Some(t) => Right(user.copy(step = ExecuteStep, building = Some(building)))
          case None => Right(user.copy(step = DecideNowStep))
        }
      case None =>
        Left(errorMessage(user.step, request))
    }
  }

  object ExecuteStep extends Step {
    override def toString = "ExecuteStep"
    override def buttons = Seq(changeStartDow, changeStartTime, changeEndTime, changeBuilding, finish)
    override def process(user: User, request: String) = request match {
      case Messages.changeStartDow =>
        Right(user.copy(step = DOWStep))

      case Messages.changeStartTime =>
        Right(user.copy(step = StartTimeStep))

      case Messages.changeEndTime =>
        Right(user.copy(step = EndTimeStep))

      case Messages.changeBuilding =>
        Right(user.copy(step = BuildingSelectionStep))

      case Messages.finish =>
        Right(user.copy(step = MainMenuStep))

      case _ =>
        Left(errorMessage(user.step, request))

    }

    override def message = ???
  }
}
