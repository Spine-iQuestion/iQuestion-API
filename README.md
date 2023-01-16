# Welcome to the iQuestion API
This is a RESTful API built using Spring Boot. It requires initial configuration when running for the first time.

## Getting Started

1. Clone the repository to your local machine.
2. Navigate to the root directory of the project in your terminal.
3. Run `mvn clean install` to build the project.
4. Run `mvn spring-boot:run` to start the server.
5. The API will be available at `http://localhost:8080/`.

## Initialization

When running the app for the first time, you will need to initialize the application by following these steps:
1. Navigate to the `src/main/resources` directory.
2. Create a file called `application.properties` and add the following properties:
    ```
    jwt_secret=o7bjuwk3RMP42@rJ2s@wG9N9*&o3Arr6&d$p7R
    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=
    spring.datasource.username=
    spring.datasource.password=
    spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
    spring.mail.host=
    spring.mail.port=
    spring.mail.username=
    spring.mail.password=
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER
    server.error.include-message=always
    ```
3. Fill out the properties with the correct values provided by the system administrator. 
4. The API will now be fully functional and ready for use.

## Build

Run `mvn clean install` to build the project.

## API documentation

The API documentation is available in Postman, including the endpoints, request and response examples, and any other necessary information.

## Further help

To get more help on the Spring Boot, check out the [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)