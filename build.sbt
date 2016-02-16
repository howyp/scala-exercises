lazy val scalaexercises = (project in file(".")).settings(scalaVersion := "2.11.5")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"