import sbt.Keys.scalaVersion

enablePlugins(DockerPlugin)

lazy val root = project
  .in(file("."))
  .aggregate(`aerospike-reader`, `aerospike-common`, `aerospike-writer`, `kafka-reader`)
  .settings(name := "aerospike-workshop")


lazy val `aerospike-common` = project.in(file("aerospike-common")).settings(projectSettings)
lazy val `aerospike-reader` = project.in(file("aerospike-reader"))
  .dependsOn(`aerospike-common`).settings(projectSettings)
lazy val `aerospike-writer` = project.in(file("aerospike-writer"))
  .dependsOn(`aerospike-common`).settings(projectSettings)
lazy val `kafka-reader` = project.in(file("kafka-reader")).settings(projectSettings)



lazy val projectSettings =
  Seq(
    version := "0.1",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.6.8",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
    )
  )
