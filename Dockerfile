FROM openjdk:8u171-jre-alpine3.7
COPY build/libs/gymlog-jvm.jar app.jar
ENTRYPOINT java -jar /app.jar --spring.config.name=${ENV_CONFIG} --jasypt.encryptor.password=${ENC_PASSWORD} --jasypt.encryptor.algorithm=${ENC_ALGORITHM}