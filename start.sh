#!/bin/bash
# Simple startup script without SDKMAN
echo "Starting EzPark Platform..."
echo "Java version:"
java -version
echo "Starting application on port $PORT"
java -Dserver.port=${PORT:-10000} -Xmx512m -Dspring.profiles.active=gcp -jar target/ezpark-platform-0.0.1-SNAPSHOT.jar
