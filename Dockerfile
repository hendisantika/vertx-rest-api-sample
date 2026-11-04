# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /workspace

# Cache dependencies first
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:25-jre-alpine
LABEL authors="hendisantika"

WORKDIR /app

# Run as non-root user
RUN addgroup -S app && adduser -S app -G app
USER app

COPY --from=build /workspace/target/vertx-rest-api-sample-1.0.0-fat.jar /app/book-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/book-app.jar"]
