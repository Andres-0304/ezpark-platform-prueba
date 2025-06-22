#!/bin/bash
set -e

echo "=== Starting EzPark Platform ==="

# Set Java environment
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Java version:"
java -version

echo "Environment: ${SPRING_PROFILES_ACTIVE:-gcp}"
echo "Port: ${PORT:-10000}"

echo "=== Starting Spring Boot Application ==="
exec java -Dserver.port=${PORT:-10000} \
          -Xmx512m \
          -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-gcp} \
          -jar target/ezpark-platform-0.0.1-SNAPSHOT.jar
