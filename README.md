# Tennis Matches Service

This Restful service exposes an API to display the details of licensed tennis matches for a customer.


## Development Environment Setup

In order to be able to build, run and execute the tests you need to follow this guide. First, clone this project and 
then install the following software and configure certain things.

### Java 11

Install AdoptOpenJDK 11.0.6 HotSpot (Oracle JDK isn't free for commercial use anymore) if you don't have it. You can download the 
binaries for your OS from the [Prebuilt AdoptOpenJDK Hotspot 11 Archive](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=hotspot)
page.

#### For macOS

Download the macOS binary and run the installer. Once it has finished, run `/usr/libexec/java_home -V`. 
You should get the following output:

```
Matching Java Virtual Machines (X):
...
11.0.6, x86_64:	"AdoptOpenJDK 11"	/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home
...
```

Copy the path displayed. You're going to need it for Maven set up.

**Note:** It is recommended this Java version is also configured as your default Java for your environment. However,
if that isn't possible, the steps below configure Maven to use JDK 11.0.6 for compilation and tests, regardless of what
version of Java your machine uses by default. 

### Maven

We use Maven as the project's build tool. Install version 3.5.0 or later.

Configure the `settings.xml` located in `~/.m2` maven repository. You need to specify the path to JDK 11 by creating a
new profile and setting the `JAVA_11_HOME` variable. It should look like this:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">

  <profiles>
    <profile>
      <id>tennis-matches</id>

      <properties>
        <JAVA_11_HOME>${path_to_adoptopenjdk_11_0_6_home}</JAVA_11_HOME>
      </properties>
    </profile>
  </profiles>

  <activeProfiles>
    <activeProfile>tennis-matches</activeProfile>
  </activeProfiles>
</settings>
```

## Build and Run this Project

Once you have your brand new MariaDB container up and running, run the scripts found in `db` directory: first the 
`schema.sql` and then the `initial_data.sql`.

### Build

From the console, create the 'fat' JAR by running `mvn clean verify`. This will compile the code, run unit and integration tests,
and finally package our application service into a fat jar. 

### Tests and Coverage

In order to run all the tests, both unit and integration and produce the test coverage report, 
you need to run **`mvn clean verify`**.

The JaCoCo maven plugin has been configured to run alongside the tests and provide us **test coverage** for 
our production code. You can check the generated report by opening `target/test-coverage/all-tests-report/index.html` on 
your favourite browser.


### Run the Application Service Locally

Assuming you've already built this project following the previous instructions and you're on this project's root directory, 
boot up the Restful service by executing the following command: 

> `java -jar target/tennis-matches-service-1.0-SNAPSHOT.jar --spring.profiles.active=dev`

Enter `http://localhost:8080/tennis-matches` in the URL of your web browser. You should get a JSON back with all the 
matches that were inserted as part of the `db-changelog-master.xml` liquibase migration script. 

In order to retrieve matches purchased by specific customers, you need to specify the HTTP Header `User-Id` with one of the following values:

* 1234: no purchase.
* 5678: purchased a single match.
* 2904: purchased a tournament
* 8736: purchased a single match.

## Tech Stack

The production code uses these key frameworks:

* Java 11.
* Spring Boot 2:
    * Spring MVC for exposing REST endpoints.
    * Spring Data/JPA 2.2/Hibernate trio for the ORM layer.
* Jackson for JSON marshalling and unmarshalling.
* JSON as the data format representation for our API.
* HSQLDB as the persistence store.
* Liquibase for data and schema migrations.

The test code uses these main libraries:

* Spring Test.
* AssertJ.
* Junit 4.13.
* Mockito.
* JsonUnit (Fluent): for asserting on JSON strings.

## Design Decisions

* This project follows the ideas and principles of a concentric circle architecture as explained by Uncle Bob in [The Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) article. The packages of this project are organised in such a way as to reflect said architecture and avoid cyclic dependencies.
    * **`domain`**: encircles the core of the service. It contains the entities that model it, as well as its business logic, in the form of use cases.
    * **`endpoint`**: in here you'll find all the endpoints, i.e. the REST API exposed to the public. This package can only depend on `dto` and `domain`. In Clean Architecture, they are also referred as controllers.
    * **`persistence`**: a gagteway. Contains all the repositories that persist domain entities. Changes on the ORM or any persistence-related library may affect classes and interfaces found here. They cannot depend on use cases, `dto`s nor gateways to other external services. They can depend on entities.
    * **`dto`**: stands for "Data Transfer Objects". As the name implies, it contains classes that are meant for
transferring data from the domain in and out of the application. Therefore, they're coupled to the de/serialisation frameworks. These classes must only have fields and getters. They must not contain any business logic. They cannot depend on neither `domain`, `endpoint` nor `persistence`.

* This service assumes that there's a API Gateway - Security duo that populates a *user profile info* header from the access token header provided by the client, be it web
or mobile app. For the purposes of this exercise and in order to keep things simple, **User-Id** acts as the user profile info header. Another alternative API design considered was `/customer/{id}/tennis-matches` but this path couples the `match` resource with the customers and their purchases domain, which limits its reusability.
* The column `tennis_match.start_date_time` is a timestamp without timezone. All the dates stored are assumed to be in UTC time.

## Improvements

* Implement a more sophisticated `summary` with the time, as suggested in the exercise.
* Create an entity called `Player`, rather than having `playerA` and `playerB` as simple Strings.
* Create a `Customer` entity.
* A `purchaseStatus` query param that can be used to retrieve not only a customer's purchased matches but
also list the ones that are available for purchase, or the entire set (purchased and non-purchased).
* Separate the liquibase changeset out of the master file and into a different one. Then have master import this changeset file and any future ones. This helps keeping the master file small.
