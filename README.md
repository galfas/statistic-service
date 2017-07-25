# This project provides a statistic services, 
 
###This project should be used for external api's, in order to understand how is their behavior. It will store and summarise the api statistics.

###tecnology
This project is built in java 8 with spring boot and use an in-memory repository.

## Testing
The project comes ready with an instance of Gradle wrapper. 
To run the tests for the project execute the following command from the project's root directory:

```
./gradlew clean test integrationTest testAcceptanceLocal -i
```
* `test` - executes unit tests
* `integrationTest` - executes spring-boot tests
* `testAcceptanceLocal` - executes cucumber tests 

## Running
This is a Spring Boot application, so it is packed as a jar. 
To start the app, execute:
```
java -Dspring.profiles.active=<profile(s)> -Dserver.port=<port> -jar <pathToJar>.jar
```
Or run the main class(Application.java) from any IDE.

## Used design Patterns: ##
1. Builder
2. Strategy
3. Singleton

## Next steps:
1. Create a docker image with the application and use docker to deploy.
2. Insert documentation for the API e.g. swagger.
3. Insert a mechanism to separate the Summary by application, in order to avoid data conflict.
4. Insert authentication in the API. 