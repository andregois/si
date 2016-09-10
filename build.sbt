import play.Play.autoImport._
import PlayKeys._


val main = Project("drivebox-si", file(".")).enablePlugins(play.PlayJava).settings(
  version := "1.0"
)

libraryDependencies ++= Seq(
  "com.adrianhurt" %% "play-bootstrap" % "1.0-P23-B3",
  jdbc,
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  javaEbean,
  "org.webjars" %% "webjars-play" % "2.3.0",
  filters,
  "org.webjars" % "bootstrap" % "3.3.6",
  "org.webjars" % "jquery" % "2.2.3")

//  "org.webjars" % "bootstrap" % "2.3.1",
herokuAppName in Compile := "drivebox-si"