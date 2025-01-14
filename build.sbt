name := "sbt-scoverage"

organization := "eu.epitech.scoverage"

enablePlugins(SbtPlugin)

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

resolvers ++= {
  if (isSnapshot.value) Seq(Resolver.sonatypeRepo("snapshots")) else Nil
}

libraryDependencies += "eu.epitech.scoverage" %% "scalac-scoverage-plugin" % "1.4.1.EPITEST"

publishMavenStyle := true

publishArtifact in Test := false

scriptedLaunchOpts ++= Seq(
  "-Xmx1024M",
  "-Dplugin.version=" + version.value
)

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepCommandAndRemaining("^ test"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("^ publishSigned"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

releaseCrossBuild := false

publishTo := {
  if (isSnapshot.value)
    Some("snapshots" at "https://nexus.epitest.eu/repository/maven-snapshots")
  else
    Some("releases" at "https://nexus.epitest.eu/repository/maven-releases")
}

pomExtra := {
  <url>https://github.com/scoverage/sbt-scoverage</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:scoverage/sbt-scoverage.git</url>
      <connection>scm:git@github.com:scoverage/sbt-scoverage.git</connection>
    </scm>
    <developers>
      <developer>
        <id>sksamuel</id>
        <name>sksamuel</name>
        <url>http://github.com/sksamuel</url>
      </developer>
      <developer>
        <id>gslowikowski</id>
        <name>Grzegorz Slowikowski</name>
        <url>http://github.com/gslowikowski</url>
      </developer>
    </developers>
}

crossSbtVersions := Vector("0.13.18", "1.2.8")

scalariformAutoformat := false
