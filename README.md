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

### 0.0.3
Support for AWS RDS PostgreSQL DB.  
Implemented GitHub Actions, they will trigger:  
&nbsp;&nbsp; develop branch -> mvn tests  
&nbsp;&nbsp; master branch -> build and push docker image to DockerHub

### 0.0.4
Support for Prometheus and Grafana  
Custom Prometheus/Micrometer metrics  
Custom Grafana dashboards  
Grafana Discord alert when error logs are above 1  
New docker-compose to run all services under containers  

![basic-stats](https://user-images.githubusercontent.com/7373284/111827710-83ce6b00-88ea-11eb-87b0-ed8994813990.png)
![user](https://user-images.githubusercontent.com/7373284/111827830-ab253800-88ea-11eb-9171-d412d7f91f6d.png)
![whisper](https://user-images.githubusercontent.com/7373284/111827862-b5473680-88ea-11eb-8ddc-cfd73b0a7536.png)


## Getting started

These instructions will get you through a running example.

### Prerequisites
- Docker and Docker-compose installed
  (https://docs.docker.com/compose/install/)

### Running a sample
1. Move into "docker-compose" directory
2. Run "docker-compose up -d". It will create social71, PostgreSQL, Prometheus and Grafana containers.  
3. You can reach "localhost:8080" for the main app and "localhost:3000" for Grafana.
> social71 test account -> username:"antodg", password:"passwd". This acc has ADMIN role.  
> Grafana main account -> username:"admin", password:"social71"
4. Enjoy

_NB. This instructions will work from version "0.0.4". If you want to run a previous version, refer to 
[OLD GETTING STARTED](old-getting-started.md)_