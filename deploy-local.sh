#!/bin/bash

# init variables
EXPOSED_PORT=8081
APP_NAME=gymlog

# build the app and image
gradle clean build

# deploy docker stack
docker-compose rm --stop --force -v
docker-compose -f docker-compose.yml up --build
