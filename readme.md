### Employee Information Management Service v0.1

> This is a demo service to do CRUD operation to an employee database.
> Below Employee information are managed on this service

| Sl No | Attributes      |
|-------|-----------------|
| 1     | Employee Name   |
| 2     | Employee Salary |
| 3     | Department      |

## Modification Made on this project

| Sl No | Attributes                                                                         |
|-------|------------------------------------------------------------------------------------|
| 1     | Added Unit test                                                                    |
| 2     | Added liquibase migration                                                          |
| 3     | Validation constraints are added for the attributes                                |
| 4     | Caching logic has been added                                                       |
| 5     | Integration test has been added for all of the defined operation                   |
| 6     | Code refactored such that DTO classes has been added                               |
| 7     | Deployment has been dockerized                                                     |
| 8     | Basic spring security has been added(Username and password based authentication)   |
| 9     | Documentation for areas which needs explanation                                    |
| 10    | Git hub actions has been added for building and testing upon each merge and commit |




### How to use this spring-boot project

> This Project can be executed in two ways
> 1. Docker based Deployment(For build and unit test and deployment)
> > > Build And Test: Run `docker-compose build`
>
> > > Deploy: Run `docker-compose up`
>
> > > UnitTest : Run `./mvn clean test`
> 2. Maven deployment without docker
> > > Build: Run `./mvn clean package`
>
> > > Deploy: Run `./mvn spring-boot:run`
>
> > > UnitTest : Run `./mvn clean test`
>
>
> ## List of Environment variables and values to be set before running application
>

| Sl No | Env variable name | Value              | Additional Note                                                     |
|-------|-------------------|--------------------|:--------------------------------------------------------------------|
| 1     | JDBC_URL          | jdbc:h2:mem:testdb | DB Url                                                              |
| 2     | API_USERNAME      | username           | User name for logging into this API (use swagger for api execution) |
| 3     | API_PASSWORD      | password           | Password for accessing the api                                      |
|       |                   |                    |                                                                     |

#### URLs for accessing the APP And Database
- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console
 #### DB Credentials
   
| Sl No | Items       | Values             |
|-------|-------------|--------------------|
| 1     | DB URL      | jdbc:h2:mem:testdb |
| 2     | DB Username | sa                 |
| 3     | DB Password | <Keep it as empty> |


### My experience in Java:
- I have 8 years of backend engineering experience mostly in building and designing
- large scale distributed systems.
- Out of which I have 2 years of experience in spring boot, 
- another three years of experience in core java etc.
- I have built a scalable dynamic pricing module using spring boot, 
- kafka, postgres, mongodb, aws lambda(python) etc

### Further Scope of improvements to this project
(If I get more time I might have implemented the below items)

| Sl No | Items                             | Additional Note                                                            |
|-------|-----------------------------------|----------------------------------------------------------------------------|
| 1.    | Token based authentication        | this can be integrated with either AWS cognito or other identity providers |
| 2.    | Authorization                     | Different levels of user authorization to this API                         |
| 3.    | Changing from REST to GraphQL     | More convenient and less code                                              |
| 4.    | Use redis based cache layer       |                                                                            |
| 5.    | Unit test for each of the classes | Now covered the whole code with integration tests                          |
