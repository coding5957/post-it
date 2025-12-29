FROM amazoncorretto:25
ARG JAR_FILE=target/postit*.jar
COPY ${JAR_FILE} postit.jar
ENTRYPOINT ["java","-jar","/postit.jar"]
