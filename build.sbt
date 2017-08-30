import sbt.Keys._

scalaVersion in ThisBuild := "2.12.1"

resolvers += "Lambda Fun Repo" at "https://repo.lambda.fun/artifactory/sbt-dev-local/"

libraryDependencies ++= Seq(
  guice,
  "net.logstash.logback" % "logstash-logback-encoder" % "4.9",
  "com.typesafe.play" %% "play-json" % "2.6.2",

  "net.codingwell" %% "scala-guice" % "4.1.0",

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0-M3" % Test,

  "fun.lambda" %% "hufs-course-crawler" % "0.2.0"
)

lazy val root = (project in file("."))
  .enablePlugins(Common, PlayScala)
  .settings(
    name := """hufs-classroom-finder"""
  )
