ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "Modul-323-Project"
  )

val slickVersion = "3.6.0"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick"           % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp"  % slickVersion,
  "com.h2database"      % "h2"              % "1.4.197",
  "org.slf4j"           % "slf4j-nop"       % "1.6.4",
  "org.xerial"          % "sqlite-jdbc"     % "3.7.2"
)
