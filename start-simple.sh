#!/bin/bash
set -e

echo "=== EzPark Platform Startup ==="
echo "Environment: ${SPRING_PROFILES_ACTIVE:-gcp}"
echo "Port: ${PORT:-10000}"

# Check Java
if command -v java &> /dev/null; then
    echo "Java found:"
    java -version 2>&1 | head -1
else
    echo "ERROR: Java not found in PATH"
    echo "PATH: $PATH"
    exit 1
fi

# Start application
echo "Starting Spring Boot application..."
exec java -Dserver.port=${PORT:-10000} \
          -Xmx512m \
          -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-gcp} \
          -jar target/ezpark-platform-0.0.1-SNAPSHOT.jar
