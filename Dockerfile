FROM openjdk:17-jdk-alpine
LABEL authors="samic"
EXPOSE 8001
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]