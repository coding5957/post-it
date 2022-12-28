FROM amazoncorretto:19.0.1
ARG JAR_FILE=target/postit*.jar
COPY ${JAR_FILE} postit.jar
ENTRYPOINT ["java","-jar","/postit.jar"]
