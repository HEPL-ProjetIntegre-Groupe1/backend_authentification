# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Add Maintainer Info
LABEL maintainer="grebac"

# Make port 8001 available to the world outside this container
EXPOSE 8001

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]