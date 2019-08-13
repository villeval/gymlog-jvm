FROM openjdk:8u171-jre-alpine3.7
COPY build/libs/gymlog-jvm.jar app.jar
# todo: parametritize config name
#ENTRYPOINT java -jar -Dspring.config.name=dev --jasypt.encryptor.password=  --jasypt.encryptor.algorithm= /app.jar
ENTRYPOINT java -jar -Dspring.config.name=dev --jasypt.encryptor.password= /app.jar