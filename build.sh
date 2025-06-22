#!/bin/bash
set -e

echo "=== Installing Java 21 ==="

# Update package list
apt-get update -qq

# Install Java 21 and Maven
apt-get install -y openjdk-21-jdk maven

# Set Java environment
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Java version:"
java -version

echo "Maven version:"
mvn -version

echo "=== Building application ==="
mvn clean package -DskipTests

echo "=== Build completed successfully ==="
