FROM openjdk:15.0.1-jdk
ARG JAR_FILE=target/postit*.jar
COPY ${JAR_FILE} postit.jar
ENTRYPOINT ["java","-jar","/postit.jar"]
