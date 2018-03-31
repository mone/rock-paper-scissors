import mill._
import mill.scalalib._

// scalastyle:off
object core extends ScalaModule {
  def scalaVersion = "2.12.4"

  object test extends Tests {
    override def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:3.0.5",
      ivy"org.scalacheck::scalacheck:1.13.4"
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}