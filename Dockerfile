FROM openjdk:8u171-jre-alpine3.7
COPY build/libs/gymlog-jvm.jar app.jar
# todo: parametritize config name
ENTRYPOINT java -jar -Dspring.config.name=dev /app.jar