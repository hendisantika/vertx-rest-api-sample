# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /workspace

# Cache Maven wrapper & dependencies first
COPY mvnw pom.xml ./
COPY .mvn ./.mvn
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline

COPY src ./src
RUN ./mvnw -B -q package -DskipTests

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
