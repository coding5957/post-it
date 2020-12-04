# Post-it Note web app


Java, SpringBoot, SpringMVC, Hibernate, file based H2 db


To build locally...


Preqs
1. JDK 11 (https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_windows-x64_bin.zip)
2. Maven (https://mirrors.gethosted.online/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip)
3. Gitscm (https://git-scm.com/download/win)



Steps

- Run the maven build

	`mvn clean install`

- Execute

	`java -jar target\postit-0.0.1-SNAPSHOT.jar` 


\
Point a browser at `http://localhost:8080`


\
NOTE: *The h2 file based db post-it will be located in the current directory*
	









