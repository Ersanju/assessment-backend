# Project Setup-
### Pre requisite- 
1. Java
2. H2 Database

### Run the command to build project
npm clean install

### Run the command to run the application
npm spring-boot:run

### Run the command to see the test coverage
npm clean verify

Now go to target/site/jacoco/index.html and open index.html file in chrome

### To test APIs in Postman

1. To load data -          GET  "localhost:9999/api/load-users"
2. To get all users -      GET  "localhost:9999/api/all-users"
3. To get sorted users-    GET  "localhost:9999/api/sorted?ascending=true"
4. To get user by role-    GET  "localhost:9999/api/role/admin"
5. To get user by IdOrSsn- GET  "localhost:9999/api/user/2"

### To see the data in the H2-Database

Open URL - http://localhost:8080/h2-console

Set following details -

Driver Class - org.h2.Driver

JDBC URL     - jdbc:h2:mem:assessment

User Name    - root

Password     - root

### Swagger/OpenAI integration for API documentation

Open URL - http://localhost:9999/swagger-ui/index.html