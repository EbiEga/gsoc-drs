#!/usr/bin/env bash

# Clean all outdated data: volumes, containers
echo "Clean outdated data"
docker-compose down --volumes --rmi local --remove-orphans

# Compile project and build jar
echo "Compile project"
mvn clean install -DskipTests #TODO exclude only jpa connectivity test

# make sure that containers updated
echo "Rebuild containers"
docker-compose build

# Start services
echo "Start services"
docker-compose up
