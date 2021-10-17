# Project overview

As part of this solution there are 3 Spring Boot projects and 2 external solutions which can be started from docker-compose file.

**Spring boot projects:**
1. **channel.api** project
   * Project that mocks API for sending messages to the person. Project mocks 3 services with corresponding endpoints for SMS, Viber and Whatsapp.
2. **api** project
   * Central project of this solution. 
   * It contains the REST api for sending messages from clients to targeted person. This process consists of selecting lowest cost channel of that person.
   * Also it logs data based on on sent messages. Log data are consisted of company overview data and per message data for clients, users and persons. Data is logged on total and per interval scheme. Logging happens every 5s.
   * It has admin overview for logged data. Admin can observe aggregated data per specified interval or total data at one point in time.
   * This API also communicates with external solutions and prepares data for others.
3. **client** project
   * Client project is simple Spring Boot client project that sends 10 messages per second with randomly selected data (client and person).

**External Solutions:**
1. PostgreSql database
   * Database used for storing common application data
2. Elasticsearch 
   * Search engine used as loggging server
   * Used for storing, searching and aggregating logging data

# How to run

### Prerequirements
* JDK 11
* Free ports 8080, 8081, 8082, 5432, 9200 on your machine
* Docker Desktop 3.6 (*Windows*) or docker engine + docker compose
* Possible additional docker setup for Elasticsearch
  * More on this link https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html 
  * I had issue with vm_max_map_count -> explained https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#docker-prod-prerequisites
* Run docker desktop
### Steps
1. Position yourself in root of this project
2. run: docker compose up
3. Use one of this instructions
    * build all 3 Spring Boot projects - position yourself in their root and run ./mvnw install
    * open your favorite IDE, import as maven projects,update maven and run them in next order (4,5,6) from there -> skip to the testing part
4. Run channel.api -> java -jar {channel_build_name}.jar
5. Run api -> java -jar {api_build_name}.jar
6. Run client -> java -jar {client_build_name}.jar

### Testing
Swagger documentation is here : http://localhost:8080/api/swagger-ui.html

Date format is standard java ISO_DATE_TIME: "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" , for example "2021-10-17T16:22:20.000"

There are 3 valid authentication users in api.
1. Admin
   * has access to admin API with ROLE_ADMIN
   * password: adminPass
   * Testing possible from Swagger documentation with try-it button
2. User
   * has ROLE_USER role
   * password: test
   * can view Swagger documentation
   * **can not** call any api 
3. Client
   * has ROLE_CLIENT
   * password: client_key
   * can only send messages to http://localhost:8080/api/messages/send
