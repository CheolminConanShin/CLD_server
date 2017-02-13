name := "NxT_api_proxy"

version := "1.0"

lazy val `test1234` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq( javaJdbc , cache , javaWs,
  "io.swagger" %% "swagger-play2" % "1.5.1" exclude("org.reflections", "reflections"),
  "org.reflections" % "reflections" % "0.9.8" notTransitive (),
  "org.webjars" % "swagger-ui" % "2.1.8-M1",
  "com.google.code.gson" % "gson" % "2.8.0",
  "com.google.inject.extensions" % "guice-testlib" % "4.0" % "test",
  "org.mockito" % "mockito-core" % "2.7.5" % "test",
  "org.assertj" % "assertj-core" % "3.6.2" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)
