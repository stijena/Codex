name := "Quiz"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaCore,
  jdbc,
  javaJdbc,
  cache,
  "com.google.inject" % "guice" % "4.0-beta"
)     

// Database
libraryDependencies ++= Seq(
	"mysql" % "mysql-connector-java" % "5.1.18"
)

// csv writer
libraryDependencies ++= Seq(
	"org.apache.commons" % "commons-csv" % "1.1",
	"org.apache.poi" % "poi" % "3.12"
)

// Hibernate
libraryDependencies ++= Seq(
	javaJpa,
	"org.hibernate" % "hibernate-entitymanager" % "4.2.16.Final"
)

resolvers += Resolver.url("Objectify Play Repository", url("http://deadbolt.ws/releases/"))(Resolver.ivyStylePatterns)

// Deadbolt
libraryDependencies ++= Seq(
  "be.objectify" %% "deadbolt-java" % "2.2-RC5"
)

libraryDependencies += "org.julienrf" %% "play-jsmessages" % "1.6.1"

play.Project.playJavaSettings
