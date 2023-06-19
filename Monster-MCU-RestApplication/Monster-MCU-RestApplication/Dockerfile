FROM openjdk:11
EXPOSE 8081
COPY /target/mcu-rest-api-0.0.1-SNAPSHOT.jar mcu-rest-api.jar
ENTRYPOINT [ "java","-jar","mcu-rest-api.jar" ]