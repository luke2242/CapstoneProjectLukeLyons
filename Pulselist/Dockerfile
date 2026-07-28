# Docker setup for Render deployment

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
RUN chmod +x mvnw && ./mvnw -q -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -q -DskipTests clean package

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/Pulselist-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8081} -jar /app/app.jar"]
