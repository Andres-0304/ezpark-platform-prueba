#!/bin/bash
set -e

echo "Initializing SDKMAN..."
source ~/.sdkman/bin/sdkman-init.sh

echo "Setting Java version..."
sdk use java 21.0.1-tem

echo "Java version:"
java -version

echo "Starting EzPark Platform on port ${PORT:-10000}..."
exec java -Dserver.port=${PORT:-10000} \
          -Xmx512m \
          -Dspring.profiles.active=gcp \
          -jar target/ezpark-platform-0.0.1-SNAPSHOT.jar
