# Build stage.
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .

# Retry dependency download with backoff.
RUN mvn dependency:go-offline -B || \
    (sleep 5 && mvn dependency:go-offline -B) || \
    (sleep 10 && mvn dependency:go-offline -B) || \
    mvn clean package -DskipTests --fail-never

COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage.
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/trackmint-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]