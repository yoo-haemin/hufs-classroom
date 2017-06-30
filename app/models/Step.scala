package models

sealed class Step {
  def buttons: Seq[String]
}

object Step {
  object MainMenuStep extends Step {
    override def buttons = Seq()
  }

  object StartTimeStep extends Step
  object StartDOWStep extends Step
  object StartDOWTimeStep extends Step
  object EndTimeStep extends Step
  object BuildingSelectionStep extends Step
  object ExecuteStep extends Step
}
