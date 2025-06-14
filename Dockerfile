# Multi-stage build for optimized production image
FROM eclipse-temurin:21-jdk-alpine AS builder

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
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/ezpark-platform-0.0.1-SNAPSHOT.jar app.jar

# Create non-root user (Alpine Linux)
RUN addgroup -g 1001 -S appuser && \
    adduser -u 1001 -S appuser -G appuser
RUN chown -R appuser:appuser /app
USER appuser

# Expose the port
EXPOSE $PORT

# Run the application with production profile
CMD ["java", "-Dspring.profiles.active=prod", "-Dserver.port=${PORT:-8080}", "-Xmx512m", "-jar", "app.jar"]
