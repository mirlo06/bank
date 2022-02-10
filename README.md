# Kata Bank Account V1

This project was created with Spring Boot 2.5.5 and use an embedded database (h2), and openjdk 8
based on DDD architecture.
## Getting Started

To run this project, please follow those instructions

### Prerequises

Clone this repository:

```text
    git clone git@xxxx.git
```

Then open the project with the IDE of your choice, Right click on the main class named ExpositionApplication, and then press "run ExpositionApplication with local profile".

You can also execute the project with maven:
```text
    mvn package && java -jar -Dspring.profiles.active="local" exposition/target/exposition-0.0.1-SNAPSHOT.jar 
```

To execute the unit tests alone:
```text
    mvn test
```
## Documentation

When the server is running you can access the API Swagger at this URL:

```text
    http://localhost:8080/swagger-ui/index.html
```

## API

With this API, you can register deposit and withdrawal operations on your bank account.

When the API is running, an embedded Apache Tomcat Server will be running at :  

```text
    http://localhost:8080/
```  
You can make POST Request at this URL in order to make a deposit or a withdrawal from account already registered with acount number (123456):
```text
curl -X POST "http://localhost:8080/v1/operations" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"accountNumber\": 123456,  \"operationAmount\": -100}" '
```

You can make GET Request at this URL in order to retrieve your operation history :  

```text
    http://localhost:8080/v1/operations?accountNumber=123456&page=0&size=10
```  

With CURL :  

```CURL
curl -X GET "http://localhost:8080/v1/operations?accountNumber=123456&page=0&size=10" -H  "accept: application/json"
```  


