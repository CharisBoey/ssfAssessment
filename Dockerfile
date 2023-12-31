FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /src

COPY src src
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

# compile the Java application
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk

WORKDIR /app

# copy and rename to app.jar
COPY --from=builder /src/target/eventmanagement-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
ENV SPRING_REDIS_HOST=localhost 
ENV SPRING_REDIS_PORT=1234
ENV SPRING_REDIS_USERNAME=default 
ENV SPRING_REDIS_PASSWORD=abc123

EXPOSE $PORT

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar
