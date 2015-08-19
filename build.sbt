name := "scalz-validations"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++=
  Seq(
    "joda-time"           %  "joda-time"      % "2.7",
    "org.scalaz" %% "scalaz-core" % "7.1.3",
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
  )
