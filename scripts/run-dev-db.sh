#!/bin/bash

DB_PORT=$1
DB_NAME=$2
DB_MOUNT_LOCATION=$3
DB_USER=$4

# config and start mysql
DB_NAME=$DB_NAME
docker stop $DB_NAME && docker rm $DB_NAME
docker build -t $DB_NAME --build-arg user=$DB_USER \
 --build-arg database=$DB_NAME --build-arg volume_mount=$DB_MOUNT_LOCATION ./mysql
# for /c/ needed for wsl
docker run --name $DB_NAME -p $DB_PORT:$DB_PORT -v $DB_MOUNT_LOCATION:/var/lib/mysql -d $DB_NAME

