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

###Docker 
 It is possible to launch the application as a container, in this case you need to have docker installed in your server,
  and then you just need to execute the next commands in the project root:
  
 1. docker build -t statistics:0.0.1 .
 2. docker run -p 8080:8080 statistics:0.0.1

###Terminal 
This is a Spring Boot application, so it is packed as a jar. 
To start the app, execute:
```
java -jar <pathToJar>.jar

```
- after run the command "./gradlew build", you can find the jar on "/build/libs/statistc-service-0.0.1.jar" from the project root
###IDE
Run the main class(Application.java) from any IDE.


## Used design Patterns: ##
1. Builder
2. Strategy
3. Singleton

## Next steps:
1. Insert authentication in the API.
3. Insert a mechanism to separate Summary by client application, in order to avoid data conflict.
2. Insert documentation for the API e.g. swagger.
4. Improve the docker image.