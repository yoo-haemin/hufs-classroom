name := "hufs-classroom"

version := "1.0.0"

scalaVersion in ThisBuild := "2.12.4"

scalacOptions ++= Seq(
	"-encoding", "UTF-8",   // source files are in UTF-8
	"-deprecation",         // warn about use of deprecated APIs
	"-unchecked",           // warn about unchecked type parameters
	"-feature",             // warn about misused language features
	"-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
	"-Xlint",               // enable handy linter warnings
	"-Xfatal-warnings",     // turn compiler warnings into errors
	"-Ypartial-unification" // allow the compiler to unify type constructors of different arities
)

libraryDependencies ++= {
	val http4sVersion = "0.18.0-M5"
	val circeVersion = "0.9.0-M2"
	val doobieVersion = "0.5.0-M9"

	Seq(
		"org.http4s" %% "http4s-blaze-server" % http4sVersion,
		"org.http4s" %% "http4s-dsl" % http4sVersion,
		"org.http4s" %% "http4s-circe" % http4sVersion,
		"org.http4s" %% "http4s-twirl" % http4sVersion,
		"io.circe" %% "circe-core" % circeVersion,
		"io.circe" %% "circe-generic" % circeVersion,
		"io.circe" %% "circe-parser" % circeVersion,
		"org.tpolecat" %% "doobie-core" % doobieVersion,
		//"org.typelevel" %% "cats-effect" % "0.5",
    "co.fs2" %% "fs2-core" % "0.10.0-M8",
		"co.fs2" %% "fs2-io" % "0.10.0-M8",
		"org.xerial" % "sqlite-jdbc" % "3.21.0",
		"ch.qos.logback" % "logback-classic" % "1.2.3"
	)
}
