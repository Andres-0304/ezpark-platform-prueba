#!/bin/bash
set -e

echo "Starting EzPark Platform..."

# Try different Java paths
if [ -f ~/.sdkman/candidates/java/21.0.1-tem/bin/java ]; then
    JAVA_CMD=~/.sdkman/candidates/java/21.0.1-tem/bin/java
    echo "Using SDKMAN Java: $JAVA_CMD"
elif command -v java &> /dev/null; then
    JAVA_CMD=java
    echo "Using system Java: $JAVA_CMD"
else
    echo "ERROR: Java not found!"
    exit 1
fi

echo "Java version:"
$JAVA_CMD -version

echo "Starting application on port ${PORT:-10000}..."
exec $JAVA_CMD -Dserver.port=${PORT:-10000} \
               -Xmx512m \
               -Dspring.profiles.active=gcp \
               -jar target/ezpark-platform-0.0.1-SNAPSHOT.jar
