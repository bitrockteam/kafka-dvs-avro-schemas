import Dependencies._
import ReleaseTransformations._

lazy val compileSettings = Seq(
  Compile / compile := (Compile / compile)
    .dependsOn(
      Compile / scalafmtSbt
    )
    .value,
  scalaVersion := Versions.Scala,
  Compile / sourceGenerators += (Compile / avroScalaGenerateSpecific).taskValue
)

lazy val dependenciesSettings = Seq(
  libraryDependencies ++= prodDeps
)

lazy val publishSettings = Seq(
  credentials ++= Seq(
    baseDirectory.value / ".sbt" / ".credentials",
    Path.userHome / ".sbt" / ".credentials.bitrock"
  ).collect {
    case c if c.exists => Credentials(c)
  },
  publishTo := Some(CustomRepositories.BitrockNexus),
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)

lazy val root = (project in file("."))
  .settings(
    name := "kafka-dvs-avro-schemas",
    organization := "it.bitrock.dvs"
  )
  .settings(compileSettings: _*)
  .settings(dependenciesSettings: _*)
  .settings(publishSettings: _*)
