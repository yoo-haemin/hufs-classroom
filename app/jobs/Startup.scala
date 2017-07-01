package jobs

import javax.inject.{Inject, Singleton}
import java.nio.charset.Charset

import java.time.DayOfWeek
import models.{ User, Classroom }
import models.services._
import models.daos._
import shared.Global

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


/**
  * Startup: Reads JSON and creates DB
  */
@Singleton
class Startup @Inject() (
  userService: UserService, classroomService: ClassroomService, classroomDAO: ClassroomDAO) {
  import scala.io.Source

  case class OccupiedClassroom(room: String, dow: DayOfWeek, time: Int)

  case class CourseTime(time: Seq[Int], room: String)

  implicit val courseTimeReads = (
    (__ \ "time").read[Seq[Int]] and
      (__ \ "class").read[String]
  )(CourseTime)

  implicit val courseTimeMapReads: Reads[Map[DayOfWeek, CourseTime]] =
    new Reads[Map[DayOfWeek, CourseTime]] {
      def reads(json: JsValue): JsResult[Map[DayOfWeek, CourseTime]] = {
        JsSuccess(
          Map(
            DayOfWeek.MONDAY    -> (json \ "courseTime" \ "Mon").asOpt[CourseTime],
            DayOfWeek.TUESDAY   -> (json \ "courseTime" \ "Tue").asOpt[CourseTime],
            DayOfWeek.WEDNESDAY -> (json \ "courseTime" \ "Wed").asOpt[CourseTime],
            DayOfWeek.THURSDAY  -> (json \ "courseTime" \ "Thu").asOpt[CourseTime],
            DayOfWeek.FRIDAY    -> (json \ "courseTime" \ "Fri").asOpt[CourseTime],
            DayOfWeek.SATURDAY  -> (json \ "courseTime" \ "Sat").asOpt[CourseTime],
            DayOfWeek.SUNDAY    -> (json \ "courseTime" \ "Sun").asOpt[CourseTime]
          )
            .filter(p => p._2.isDefined)
            .map { case (k, v) => k -> v.get })
      }
    }

  implicit val courseTimeMapSeqReads = __.read[Seq[Map[DayOfWeek, CourseTime]]]

  val sourceUrl = """https://gist.githubusercontent.com/yoo-haemin/a8b8914e41e1e575eba317e31aa3df5b/raw/576cb22400513c81c10dd85101b1626ac28c45f0/2012-02-flat.json"""

  val source = Source.fromURL(sourceUrl, "UTF-8").getLines().reduceLeft(_+_)

  val jsonData = Json.parse(source).as[Seq[Map[DayOfWeek, CourseTime]]].flatten

  val occupied = jsonData
    .flatMap { case (dow, CourseTime(time, room)) => time map { t => OccupiedClassroom(room, dow, t) } }
    .groupBy { case OccupiedClassroom(room, dow, time) => room }
    .map { case (room, xs) =>
      room -> xs
        .groupBy { case OccupiedClassroom(_, dow, _) => dow}
        .map { case (dow, ys) => dow -> ys.map { case OccupiedClassroom(_, _, t) => t + 8 } }
        .toMap
    }

  val classrooms = Global.classroomList.map { room =>
    val fullTime = (Global.baseStartTime until Global.baseEndTime)
    val fullMap = Map(
      DayOfWeek.MONDAY -> fullTime,
      DayOfWeek.TUESDAY -> fullTime,
      DayOfWeek.WEDNESDAY -> fullTime,
      DayOfWeek.THURSDAY -> fullTime,
      DayOfWeek.FRIDAY -> fullTime
    )
    val occupiedStatus = occupied.get(room)
      .fold(fullMap.asInstanceOf[Map[DayOfWeek,Seq[Int]]]) { timeMap =>
        fullMap.map { case (dow, _) =>
          timeMap.get(dow) match {
            case Some(times) => dow -> (fullTime.toSet -- times.toSet).toSeq.sorted
            case None => dow -> fullTime.sorted
          }
        }
      }
    Classroom.fromRoomTime(room, occupiedStatus)
  }

  classrooms.tail.foldLeft(classroomService.add(classrooms.head)) { (acc, v) =>
    acc flatMap (_ => classroomService.add(v))
  }
}
