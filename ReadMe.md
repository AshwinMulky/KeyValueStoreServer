# Key Value Store Server

A thread-safe in-memory key-value service and operations audit service **spring boot** application.

## Prerequisites:
- java 8
- gradle

## Run

- `./gradlew` **or** `gradle bootRun`
- refer https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html
- Recommended to use Intellij Idea IDE to `build`, `test` and `bootRun`

## End-point URL
- `http://localhost:8080`
- Example: `GET http://localhost:8080/api/key-values`

## Api Documentations

- To view api documentation navigate to the URL `http://localhost:8080/swagger-ui.html`
- You can also get the json representation of apis at `http://localhost:8080/v2/api-docs`
