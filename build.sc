import mill._, scalalib._
import coursier.maven.MavenRepository

object service extends ScalaModule {

  def repositories = super.repositories ++ Seq(
    MavenRepository("https://oss.sonatype.org/content/repositories/snapshots")
  )

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
    D.http4sBlazeServer,
    D.http4sCirce,
    D.http4sDsl,
    D.circeCore,
    D.circeGeneric,
    D.enumeratum,

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
//    val shapeless  = ""
    val http4s     = "0.19.0-M2"
    val circe      = "0.9.2"
    val enumeratum = "1.5.13"
    val doobie     = "0.5.3"
    val h2         = "1.4.196"
    val flyway     = "5.0.5"
    val logback    = "1.2.3"
    val scalaTest  = "3.0.3"
    val scalaCheck = "1.13.4"
  }
  def http4s(artifact: String) = ivy"org.http4s::http4s-$artifact:${V.http4s}"
  def circe(artifact: String)  = ivy"io.circe::circe-$artifact:${V.circe}"
  def doobie(artifact: String) = ivy"org.tpolecat::doobie-$artifact:${V.doobie}"

//  val shapeless         = ivy"org.chuusai::shapeless:${V.shapeless}"
  val http4sBlazeServer = http4s("blaze-server")
  val http4sCirce       = http4s("circe")
  val http4sDsl         = http4s("dsl")
  val circeCore         = circe("core")
  val circeGeneric      = circe("generic")
  val enumeratum        = ivy"com.beachape::enumeratum:${V.enumeratum}"

  val h2                = ivy"com.h2database:h2:${V.h2}"
  val flywayCore        = ivy"org.flywaydb:flyway-core:${V.flyway}"
  val doobieCore        = doobie("core")
  val doobiePostgres    = doobie("postgres")
  val doobieH2          = doobie("h2")
  val doobieScalaTest   = doobie("scalatest")

  val logbackClassic    = ivy"ch.qos.logback:logback-classic:${V.logback}"

  val scalaTest         = ivy"org.scalatest::scalatest:${V.scalaTest}"
  val scalaCheck        = ivy"org.scalacheck::scalacheck:${V.scalaCheck}"
}
