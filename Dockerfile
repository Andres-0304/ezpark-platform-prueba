# Use OpenJDK 24 as base image
FROM openjdk:24-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn .mvn

# Copy source code
COPY src ./src

# Build the application
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Expose the port
EXPOSE $PORT

# Run the application
CMD ["java", "-Dserver.port=${PORT:-8080}", "-jar", "target/ezpark-platform-0.0.1-SNAPSHOT.jar"]
