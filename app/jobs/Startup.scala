package jobs

import javax.inject.{Inject, Singleton}
import java.time.DayOfWeek

import models.Classroom
import models.services._
import shared.Global

import java.nio.charset.Charset

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

import play.api.Configuration
import play.api.libs.json._
import play.api.Logger

import fun.lambda.coursecrawler._

/**
  * Startup: Reads JSON and creates DB
  */
@Singleton
class Startup @Inject() (config: Configuration, classroomService: ClassroomService) {
  //Fetches json and create list of (occupied) courseTime
  private[this] def currentCourseTime() = {
    val jsonSource = Source.fromURL(config.get[String]("source_url"), "UTF-8").getLines.reduceLeft(_+_)
    val courses = Json.parse(jsonSource).as[Seq[Course]]
    courses.flatMap(t => t.time)
  }

  //Calculate free time from occupied time using global basetime
  private[this] def freeTime(courseTimes: Seq[CourseTime]) = {
    val fullTime: Seq[Int] = (Global.baseStartTime until Global.baseEndTime)
    val fullMap = (1 to 5).map(DayOfWeek.of(_) -> fullTime).toMap

    (fullMap /: courseTimes) { case (acc, CourseTime(dow, times, _)) =>
      acc ++ acc.get(dow).fold(Map.empty[DayOfWeek, Seq[Int]]) { accTimes =>
        Map(dow -> accTimes.filterNot(t => times.exists(_ + 8 == t)))
      }
    }
  }

  //create classroom list
  val classrooms = currentCourseTime()
    .groupBy(_.room)
    .filter(r => Global.classroomList.exists(_ == r._1))
    .map { case (room, courseTimes) =>
      val classroom = Classroom.fromRoomTime(room, freeTime(courseTimes))
      Logger.debug(classroom.toString)
      classroom
    }

  //update classroom
  //TODO backend is not thread safe, so we must do it sequentially
  classrooms.tail.foldLeft(classroomService.add(classrooms.head)) { (acc, v) =>
    acc flatMap (_ => classroomService.add(v))
  }
}
