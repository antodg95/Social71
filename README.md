# social71
This is a Social Network clone developed with Java Spring Boot. 

>The following samples are intended for use in local development environments

## Versions summary
### 0.0.1
Project structure.  
Postgres DB setup with docker-compose and sql scripts  
User and Whisper implementation (Entity, DTO, Repository, Service, Controller).  
Swagger OpenAPI documentation for API v1.  
Unit Tests.

### 0.0.2
Spring Security implementation with custom JDBC Authentication and BCryptPasswordEncoder.  
More sql script for Spring Security Roles/Authorities.  
More API v1 endpoints (findAllWhispers by UserId).  
Secured endpoints. An user with USER_ROLE can update or delete only his resources.

![image](https://user-images.githubusercontent.com/7373284/111064960-68f99200-84b7-11eb-8dc5-8053a017d2b2.png)
![image](https://user-images.githubusercontent.com/7373284/111067698-b250de00-84c5-11eb-8505-64d656af0186.png)

## Getting started

---
These instructions will get you through a running example.

### Prerequisites
- Docker and Docker-compose installed
(https://docs.docker.com/compose/install/)
- Maven installed 
  (https://maven.apache.org/install.html)
  
### Running a sample
1. Move into "docker-compose" directory
2. Run "docker-compose up -d" command, it will create and run a container with a running Postgres image on port 5432.
3. Move into "scripts" directory
4. Run "docker cp -a . postgres-social71:/tmp/" command. It will copy all files in the current directory to the docker container 'postgres-social71'.
5. Run "docker exec -it postgres-social71 bin/bash", we will enter in console move into the postgres-social71 container.
Now your terminal looks like this:
```bash
root@postgres:/# 
```
6. Move into "tmp" directory and run "psql -U social71 -f 'filename'" for each file into tmp directory. Run them in order (001, 002, etc). If password asked: "social71".
7. Run "exit" command to quit the container's console.
8. Return to "social71" main directory
9. Run "mvn spring-boot:run"
10. Enjoy