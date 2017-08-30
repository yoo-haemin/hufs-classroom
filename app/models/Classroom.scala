package models

import java.time.DayOfWeek

//Full room number: Building.toString + room
case class Classroom(building: Building, room: String, freeTime: Map[DayOfWeek, Seq[Int]]) {
  //TODO Needs fix with classroom over 10th floor
  def floor = room.take(1).toInt
}

object Classroom {
  private def getBuildingRoom(room: String): (Building, String) = room match {
    case s if s.length > 4 && Building.fromString(s.take(2)).isDefined =>
      Building.fromString(s.take(2)).get -> s.drop(2)
    case s => Building.fromString(s.head.toString).get -> s.tail
  }

  def fromRoomTime(room: String, freeTime: Map[DayOfWeek, Seq[Int]]): Classroom = {
    val (b, r) = getBuildingRoom(room)
    Classroom(b, r, freeTime)
  }
}
