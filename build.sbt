import sbt.Keys._

lazy val GatlingTest = config("gatling") extend Test

scalaVersion in ThisBuild := "2.11.11"

libraryDependencies ++= Seq(
  guice,
  "org.joda" % "joda-convert" % "1.8",
  "net.logstash.logback" % "logstash-logback-encoder" % "4.9",
  "com.typesafe.play" %% "play-json" % "2.6.0",

  "com.netaporter" %% "scala-uri" % "0.4.16",
  "net.codingwell" %% "scala-guice" % "4.1.0",

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0-M3" % Test,
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % Test,
  "io.gatling" % "gatling-test-framework" % "2.2.2" % Test
)

lazy val root = (project in file("."))
  .enablePlugins(Common, PlayScala, GatlingPlugin)
  .configs(GatlingTest)
  .settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  .settings(
    name := """hufs-classroom-finder""",
    scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation"
  )
