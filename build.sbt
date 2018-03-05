import sbt.Keys._

scalaVersion in ThisBuild := "2.12.4"

resolvers += Resolver.bintrayRepo("yoo-haemin", "maven")

libraryDependencies ++= Seq(
  guice,
  "net.logstash.logback" % "logstash-logback-encoder" % "4.11",
  "com.typesafe.play" %% "play-json" % "2.6.8",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "fun.lambda" %% "hufs-course-crawler" % "0.1.0"
)

lazy val root = (project in file("."))
  .enablePlugins(Common, PlayScala)
  .settings(
    name := """hufs-classroom-finder"""
  )
