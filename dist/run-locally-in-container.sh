#!/bin/bash

# initializing variables
EXPOSED_PORT=$1
APP_NAME=$2

# build jar
gradle clean build

# build docker image
docker build -t $APP_NAME .
docker run -ti --name $APP_NAME -d -p $EXPOSED_PORT:$EXPOSED_PORT $APP_NAME