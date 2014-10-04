name := """play-pub-sub"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

val akkaVersion = "2.3.6"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" % "akka-remote_2.11" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion
)

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"