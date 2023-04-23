# Discovery Intersellar Application

This is a brief description of Intersellar Spring Boot application.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java Development Kit (JDK) 8 or later
- Spring Boot ~> 2.5.2
- Apache Maven 3.6.0 or later
- Your favorite IDE (e.g. IntelliJ IDEA, Eclipse, VS Code)

## Database
- H2
- Derby 

### Project structure
This project consists of 1 deploy-able artefacts:

### Deploy-able
- evansChikuni-1.0.0_SNAPSHOT

### Installing

```
+-- evansChikuni
+-- .idea
+-- .mvn
|   +-- src/
|   +-- .gitignore
|   +-- pom.xml
+-- .gitignore
+-- pom.xml
+-- README.md
```
---


### Local development
Run the below commands from the project root:
1. Build the project
```
mvn -B  clean install --file pom.xml
```

2. Open the dev environment application file using an editor of choice
```
vi application.yml
```
3. credentials for the h2 database under the datasource section
```
datasource:
    username: interstellar
    password: p@55w0rd2
    url: jdbc:h2:mem:interstellar_gateway
    
```

4. Run the application
```
mvn spring-boot:run
```
5. Navigate into the evansChikuni/src/main/resources/
```
cd evansChikuni/src/main/resources/
```
6. Update dev profile with credentials for the H2 database under the datasource section
```
datasource:
    username: interstellar
    password: p@55ssw0rd2
    url: jdbc:h2:mem:interstellar_gateway
```
7. Navigate into evansChikuni from the root directory in order to run the service
```
cd evansChikuni
```
8. Run the application
```
mvn spring-boot:run
```

9. Access the application H2 database on the browser using the below url
```
localhost:9090/h2-console/

```

localhost:9090/h2-console/

**Notes:**
- The service is exposed on `http://localhost:9090` by default.
- Spring Boot uses the `application-*.yml` file(s).

**Improvements:**
- Improvements would be to deploy the jar in docker file
- Expose usage of the Actuator to expose endpoints for tracing and checking endpoints health
- Implement OpenAI document generation using SwaggerUI
- Deploy application in AWS using ECR and managed through EKS
- Store file or files in S3 Bucket

---

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
- [Maven](https://maven.apache.org/) - Dependency Management
- [MySQL](https://www.mysql.com/) - Database Management System

## Authors

- **Evans K F Chikuni** - [Your website or Github Profile](https://github.com/vance04real)

## License

This project is licensed under the Discovery License - see the [Discovery.md](LICENSE.md) file for details



