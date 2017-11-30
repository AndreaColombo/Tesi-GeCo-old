name := "UMLS"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.7"
libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1"
)
val json4sJackson = "org.json4s" %% "json4s-jackson" % "3.6.0-M1"
