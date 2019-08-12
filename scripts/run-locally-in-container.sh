#!/bin/bash

# initializing variables
EXPOSED_PORT=$1
APP_NAME=$2
DB_PORT=$3
DB_NAME=$4
DB_MOUNT_LOCATION=$5
DB_USER=$6

# config and start mysql
DB_NAME=$DB_NAME
docker stop $DB_NAME && docker rm $DB_NAME
docker build -t $DB_NAME --build-arg user=$DB_USER --build-arg database=$DB_NAME --build-arg volume_mount=$DB_MOUNT_LOCATION ./mysql
docker run --name $DB_NAME -p $DB_PORT:$DB_PORT -d $DB_NAME

# build jar
gradle clean build

# build docker image for app
docker stop $APP_NAME && docker rm $APP_NAME
docker build -t $APP_NAME .
docker run -ti --name $APP_NAME -d -p $EXPOSED_PORT:$EXPOSED_PORT $APP_NAME