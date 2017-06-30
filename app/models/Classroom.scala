package models

import java.time.DayOfWeek

case class Classroom(building: Building, floor: Int, room: String, freeTime: Map[DayOfWeek, Seq[Int]])

object Classroom {
  def getBuildingRoom(room: String): (Building, String) = room match {
    case s if s.length > 4 && Building.fromString(s.take(2)).isDefined =>
      Building.fromString(s.take(2)).get -> s.drop(2)
    case s => Building.fromString(s.head.toString).get -> s.tail
  }

//  def fromRoomTime(room: String, freeTime: Map[DayOfWeek, Seq[Int]]): Classroom = {
//    val (b, r) = getBuildingRoom(room)
//    Classroom(b, r, Map.empty[DayOfWeek, Seq[Int]])
//  }
}
