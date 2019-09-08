#!/bin/bash

# initializing variables
EXPOSED_PORT=$1
APP_NAME=$2

# build jar
# todo enable tests
gradle clean build -x test

# build docker image for app
docker stop $APP_NAME && docker rm $APP_NAME
docker build -t $APP_NAME .
docker run -ti --name $APP_NAME -d -p $EXPOSED_PORT:$EXPOSED_PORT --env-file=env_variables_dev.properties $APP_NAME