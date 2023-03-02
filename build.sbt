name := "Akka_Http_Low_Level_Server_Api"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-http" % "10.2.7",
                            "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.7",
                            "com.typesafe.akka" %% "akka-http-testkit" % "10.2.7",
                            "com.typesafe.akka" %% "akka-testkit" % "2.6.17",
                            "com.typesafe.akka" %% "akka-stream" % "2.6.17")
