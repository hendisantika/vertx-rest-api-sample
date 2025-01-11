# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:23
LABEL authors="hendisantika"

# Set the working directory in the container
WORKDIR /app

# Copy the project’s jar file to the container
COPY target/vertx-rest-api-sample-1.0.0.jar /app/book-app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "/app/book-app.jar"]
