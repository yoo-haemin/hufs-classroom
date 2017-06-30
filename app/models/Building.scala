package models

import scala.collection.immutable.BitSet

sealed abstract class Building(no: String, val availableFloor: BitSet) {
  override def toString(): String = this match {
    case Building.MainBuilding => "0"
    case Building.HumanitiesBuilding => "1"
    case Building.TLDBuilding => "2"
    case Building.SocialScienceBuilding => "3"
    case Building.StudentHall => "4"
    case Building.LawSchool => "5"
    case Building.GraduateSchool => "6"
    case Building.ForeignLanguageCenter => "7"
    case Building.InternationalBuilding => "8"
    case Building.Library => "9"
    case Building.FacultyBuilding2 => "11"
    case Building.MinervaSquare => "B"
    case Building.CyberBuilding => "C"
    case Building.HistoricalArchives => "역"
  }

  def name: String = this match {
    case Building.MainBuilding => "본관"
    case Building.HumanitiesBuilding => "인문관"
    case Building.TLDBuilding => "교수학습개발원"
    case Building.SocialScienceBuilding => "사회과학관"
    case Building.StudentHall => "학생회관"
    case Building.LawSchool => "법학대학원"
    case Building.GraduateSchool => "대학원"
    case Building.ForeignLanguageCenter => "외국어연수원"
    case Building.InternationalBuilding => "국제관"
    case Building.Library => "도서관"
    case Building.FacultyBuilding2 => "교수회관"
    case Building.MinervaSquare => "미네르바스퀘어"
    case Building.CyberBuilding => "사이버관"
    case Building.HistoricalArchives => "역사관"
  }
}

object Building {
  final object MainBuilding extends Building("0", BitSet())
  final object HumanitiesBuilding extends Building("1", BitSet())
  final object TLDBuilding extends Building("2", BitSet())
  final object SocialScienceBuilding extends Building("3", BitSet())
  final object StudentHall extends Building("4", BitSet())
  final object LawSchool extends Building("5", BitSet())
  final object GraduateSchool extends Building("6", BitSet())
  final object ForeignLanguageCenter extends Building("7", BitSet())
  final object InternationalBuilding extends Building("8", BitSet())
  final object Library extends Building("9", BitSet())
  final object FacultyBuilding2 extends Building("11", BitSet())
  final object MinervaSquare extends Building("B", BitSet())
  final object CyberBuilding extends Building("C", BitSet())
  final object HistoricalArchives extends Building("역", BitSet())

  def fromString(s: String): Option[Building] = s match {
    case s if s == "0" || s == "본관" => Some(MainBuilding)
    case s if s == "1" || s == "인문관" => Some(HumanitiesBuilding)
    case s if s == "2" || s == "교수학습개발원" => Some(TLDBuilding)
    case s if s == "3" || s == "사회과학관" => Some(SocialScienceBuilding)
    case s if s == "4" || s == "학생회관" => Some(StudentHall)
    case s if s == "5" || s == "법학대학원" => Some(LawSchool)
    case s if s == "6" || s == "대학원" => Some(GraduateSchool)
    case s if s == "7" || s == "외국어연수원" => Some(ForeignLanguageCenter)
    case s if s == "8" || s == "국제관" => Some(InternationalBuilding)
    case s if s == "9" || s == "도서관" => Some(Library)
    case s if s == "11"|| s == "교수회관"  => Some(FacultyBuilding2)
    case s if s == "B" || s == "미네르바스퀘어" => Some(MinervaSquare)
    case s if s == "C" || s == "사이버관" => Some(CyberBuilding)
    case s if s == "역"|| s == "역사관"  => Some(HistoricalArchives)
    case _ => None
  }
}
