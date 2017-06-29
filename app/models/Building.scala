package models

sealed abstract class Building(no: String) {
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
}

object Building {
  final object MainBuilding extends Building("0")
  final object HumanitiesBuilding extends Building("1")
  final object TLDBuilding extends Building("2")
  final object SocialScienceBuilding extends Building("3")
  final object StudentHall extends Building("4")
  final object LawSchool extends Building("5")
  final object GraduateSchool extends Building("6")
  final object ForeignLanguageCenter extends Building("7")
  final object InternationalBuilding extends Building("8")
  final object Library extends Building("9")
  final object FacultyBuilding2 extends Building("11")
  final object MinervaSquare extends Building("B")
  final object CyberBuilding extends Building("C")
  final object HistoricalArchives extends Building("역")

  def fromString(s: String): Option[Building] = s match {
    case "0" => Some(MainBuilding)
    case "1" => Some(HumanitiesBuilding)
    case "2" => Some(TLDBuilding)
    case "3" => Some(SocialScienceBuilding)
    case "4" => Some(StudentHall)
    case "5" => Some(LawSchool)
    case "6" => Some(GraduateSchool)
    case "7" => Some(ForeignLanguageCenter)
    case "8" => Some(InternationalBuilding)
    case "9" => Some(Library)
    case "11" => Some(FacultyBuilding2)
    case "B" => Some(MinervaSquare)
    case "C" => Some(CyberBuilding)
    case "역" => Some(HistoricalArchives)
    case _ => None
  }

}
