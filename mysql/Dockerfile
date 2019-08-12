FROM mysql:latest
ARG user
ARG database
ARG volume_mount
ENV MYSQL_USER=$user
ENV MYSQL_PASSWORD=password
ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_DATABASE=$database
VOLUME $volume_mount:/var/lib/mysql

