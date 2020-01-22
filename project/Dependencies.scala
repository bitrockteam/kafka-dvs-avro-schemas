import sbt._

object Dependencies {

  object CustomRepositories {

    lazy val BitrockNexus = "Bitrock Nexus" at "https://nexus.reactive-labs.io/repository/maven-bitrock-public/"

  }

  object Versions {

    lazy val Scala = "2.12.10"
    lazy val Avro  = "1.8.2"

  }

  lazy val prodDeps: Seq[ModuleID] = Seq(
    "org.apache.avro" % "avro" % Versions.Avro
  )

}
