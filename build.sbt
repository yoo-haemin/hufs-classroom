name := "http4s"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.4"

//val http4sVersion = "0.15.9a"
//val circeVersion = "0.7.1"
//val doobieVersion = "0.4.1"

libraryDependencies ++= {
  val http4sVersion = "0.18.0-M5"
  val circeVersion = "0.9.0-M2"
  val doobieVersion = "0.5.0-M9"
  val h2Version = "1.4.196"

  Seq(
	"org.http4s" %% "http4s-blaze-server" % http4sVersion,
	"org.http4s" %% "http4s-dsl" % http4sVersion,
	"org.http4s" %% "http4s-circe" % http4sVersion,
	"org.http4s" %% "http4s-twirl" % http4sVersion,
	"io.circe" %% "circe-core" % circeVersion,
	"io.circe" %% "circe-generic" % circeVersion,
	"io.circe" %% "circe-parser" % circeVersion,
	"org.tpolecat" %% "doobie-core" % doobieVersion,
	"org.tpolecat" %% "doobie-h2" % doobieVersion,
        "com.h2database" % "h2" % h2Version,
	"com.zaxxer" %  "HikariCP" % "2.7.4",
	"ch.qos.logback" % "logback-classic" % "1.2.3"
)
}
