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
