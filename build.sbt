
name := """NxT_api_proxy"""

version := "1.0"

lazy val NxT_api_proxy = (project in file(".")).enablePlugins(PlayJava)
  .configs( ContractTest, UnitTest )
  .settings( inConfig(ContractTest)(Defaults.testTasks) : _* )
  .settings( inConfig(UnitTest)(Defaults.testTasks) : _* )
  .settings(
    testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a"),
    testOptions in ContractTest := Seq(Tests.Filter(contractFilter)),
    testOptions in UnitTest := Seq(Tests.Filter(unitFilter))
  )

def contractFilter(name: String): Boolean = name endsWith "ContractTest"
def unitFilter(name: String): Boolean = (name endsWith "Test") && !contractFilter(name)

lazy val ContractTest = config("contract") extend(Test)
lazy val UnitTest = config("unit") extend(Test)

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator

logLevel in Test := Level.Debug

libraryDependencies += javaJdbc

libraryDependencies += cache

libraryDependencies += javaWs

libraryDependencies ++= Seq(
  "io.swagger" %% "swagger-play2" % "1.5.1" exclude("org.reflections", "reflections"),
  "org.reflections" % "reflections" % "0.9.8" notTransitive (),
  "com.google.code.gson" % "gson" % "2.8.0",
  "org.projectlombok" % "lombok" % "1.16.14",
  "junit" % "junit" % "4.12" % "test",
  "org.mockito" % "mockito-core" % "2.7.5" % "test",
  "org.assertj" % "assertj-core" % "3.6.2" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "org.mbtest.javabank" % "javabank-client" % "0.4.7" % "test"
)


lazy val stopMounteBank = taskKey[Unit] ("Stop MounteBank")
lazy val startMounteBank = taskKey[Unit] ("Start MounteBank")

stopMounteBank := {
  "sh process-kill.sh".run()
}

startMounteBank := {
  "mb --loglevel info".run()
}

(test in UnitTest) <<= (test in UnitTest).dependsOn(stopMounteBank, startMounteBank)

(test in Test) <<= (test in Test).dependsOn(stopMounteBank, startMounteBank)
