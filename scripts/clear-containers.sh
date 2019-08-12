APP_NAME=$1

docker stop $APP_NAME && docker rm $APP_NAME