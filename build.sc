import mill._, scalalib._

object service extends ScalaModule {

  def scalaVersion = "2.12.6"
  def scalacOptions = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-Ypartial-unification"
  )

  def ivyDeps = Agg(
    D.catsEffect,
    D.fs2,
    D.http4sBlazeServer,
    D.http4sCirce,
    D.http4sDsl,
    D.circeCore,
    D.circeGeneric,

    D.h2,
    D.flywayCore,
    D.doobieCore,
    D.doobiePostgres,
    D.doobieH2,
    D.doobieScalaTest,

    D.logbackClassic,

    D.scalaTest,
    D.scalaCheck
  )

  object test extends Tests {
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}


object D {
  val V = new {
    val CatsEffect = "1.0.0-RC"
    val Fs2        = "0.10.4"
    val Http4s     = "0.18.11"
    val Circe      = "0.9.2"
    val Doobie     = "0.5.3"
    val H2         = "1.4.196"
    val Flyway     = "5.0.5"
    val Logback    = "1.2.3"
    val ScalaTest  = "3.0.3"
    val ScalaCheck = "1.13.4"
  }
  def http4s(artifact: String) = ivy"org.http4s::http4s-$artifact:${V.Http4s}"
  def circe(artifact: String)  = ivy"io.circe::circe-$artifact:${V.Circe}"
  def doobie(artifact: String) = ivy"org.tpolecat::doobie-$artifact:${V.Doobie}"

  val catsEffect        = ivy"org.typelevel::cats-effect:${V.CatsEffect}"
  val fs2               = ivy"co.fs2::fs2-core:${V.Fs2}"
  val http4sBlazeServer = http4s("blaze-server")
  val http4sCirce       = http4s("circe")
  val http4sDsl         = http4s("dsl")
  val circeCore         = circe("core")
  val circeGeneric      = circe("generic")

  val h2                = ivy"com.h2database:h2:${V.H2}"
  val flywayCore        = ivy"org.flywaydb:flyway-core:${V.Flyway}"
  val doobieCore        = doobie("core")
  val doobiePostgres    = doobie("postgres")
  val doobieH2          = doobie("h2")
  val doobieScalaTest   = doobie("scalatest")

  val logbackClassic    = ivy"ch.qos.logback:logback-classic:${V.Logback}"

  val scalaTest         = ivy"org.scalatest::scalatest:${V.ScalaTest}"
  val scalaCheck        = ivy"org.scalacheck::scalacheck:${V.ScalaCheck}"
}
