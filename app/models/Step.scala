package models

import java.time.DayOfWeek

sealed abstract class Step {
  def buttons: Seq[String]
}

object Step {
  import shared.Messages._

  object MainMenuStep extends Step {
    override def buttons = Seq(timeFirst, buildingFirst)
  }

  object StartTimeStep extends Step {
    override def buttons = Seq(now, buildingFirst)
  }

  object StartDOWStep extends Step {
    override def buttons = (1 to 5).map(i => dowMsg(DayOfWeek.of(i)))
  }


  object StartDOWTimeStep extends Step {
    override def buttons = (9 to 16).map(_.toString + "시부터")
  }

  object EndTimeStep extends Step {
    override def buttons = buttonsWithStart(10)
    def buttonsWithStart(startTime: Int) = (startTime to 17).map(_.toString + "시까지")
  }

  object BuildingSelectionStep extends Step {
    override def buttons = Seq("1", "2", "3", "C").map(Building.fromString(_).get.name)
  }

  object ExecuteStep extends Step {
    override def buttons = Seq(changeStartDow, changeStartTime, changeEndTime, changeBuilding, finish)
  }
}
