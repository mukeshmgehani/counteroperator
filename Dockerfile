FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-slim
MAINTAINER Mukesh Gehani <mukeshmgehani@gmail.com>
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
WORKDIR /opt
ENV PORT 8080
EXPOSE 8080
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
