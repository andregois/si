import play.Project._

name := "hello-play-java"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  javaEbean,
  "org.webjars" %% "webjars-play" % "2.2.2", 
  "org.webjars" % "bootstrap" % "2.3.1")

playJavaSettings
