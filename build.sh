#!/bin/bash
# Build script for Render
echo "Building EzPark Platform with Maven..."
./mvnw clean package -DskipTests=true
echo "Build completed successfully!"
