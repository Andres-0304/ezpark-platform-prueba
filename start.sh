#!/bin/bash
# Start script for Render
echo "Starting EzPark Platform..."
java -Dspring.profiles.active=prod -jar target/ezpark-platform-0.0.1-SNAPSHOT.jar
