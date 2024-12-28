# Post-it Note web app


Java, SpringBoot, SpringMVC, Hibernate, file based H2 db


![Java CI with Maven](https://github.com/coding5957/post-it/workflows/Java%20CI%20with%20Maven/badge.svg)



## To build locally...


Preqs
1. JDK 23 (https://jdk.java.net/23/)
2. Maven (https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.9.9-bin.zip)
3. Gitscm (https://git-scm.com/download/win)



Steps

- Run the maven build

	`mvn clean install`

- Execute

	`java -jar target\postit-0.0.4-SNAPSHOT.jar` 


\
Point a browser at `http://localhost:8080`


\
NOTE: *The h2 file based db post-it will be located in the current directory*
	


## To build docker image locally...


Preqs

1. As above
2. Docker (https://hub.docker.com/editions/community/docker-ce-desktop-windows/)


Steps

- Run the maven build as above

- Build docker image
	
	`docker build -t post-it .`\
	`docker images`
	
- Run the image in a container

	`docker run -d --name post-it -p 8080:8080 post-it`
	
	








