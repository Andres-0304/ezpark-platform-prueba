# Multi-stage build for optimized production image
FROM openjdk:24-jdk-slim AS builder

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

# Production stage
FROM openjdk:24-jre-slim

# Set working directory
WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/ezpark-platform-0.0.1-SNAPSHOT.jar app.jar

# Create non-root user
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown -R appuser:appuser /app
USER appuser

# Expose the port
EXPOSE $PORT

# Run the application with production profile
CMD ["java", "-Dspring.profiles.active=prod", "-Dserver.port=${PORT:-8080}", "-Xmx512m", "-jar", "app.jar"]
