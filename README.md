# REST API for a real estate registry for buildings

## Working with:
- Spring Boot (Java 8, packaging: jar), JUnit & Mockito for tests;
- Database: H2db in memory;
- IntelliJ IDEA.

## All REST API documentation can be found in swagger when project is running:
URL: http://localhost:8080/swagger-ui.html
![swagger](https://user-images.githubusercontent.com/56863735/86446887-376fda80-bd1d-11ea-86d7-c0caf84a01ed.PNG)

## How to run the application:

### 1. Click run project:
![run](https://user-images.githubusercontent.com/56863735/86447368-eca29280-bd1d-11ea-807c-9d4dcf56cd63.png)

### 2.Open the H2db in browser:
![h2 db](https://user-images.githubusercontent.com/56863735/86447469-0e037e80-bd1e-11ea-8020-ef86368820d2.PNG)

### 3. Postman is used for working with data in this project:
![postman 1](https://user-images.githubusercontent.com/56863735/86448787-f0cfaf80-bd1f-11ea-92b2-5842f0a55f62.PNG)
![postman 2](https://user-images.githubusercontent.com/56863735/86448789-f0cfaf80-bd1f-11ea-91a7-658a207fe8bc.PNG)
![postman 3](https://user-images.githubusercontent.com/56863735/86448785-ef9e8280-bd1f-11ea-9a56-bb7c8a4947f7.PNG)

### 4. Whole project is covered by unit tests (JUnit & Mockito):
![test1](https://user-images.githubusercontent.com/56863735/86447622-4b680c00-bd1e-11ea-9547-7f4be30d5aba.PNG)
![test2](https://user-images.githubusercontent.com/56863735/86447624-4c00a280-bd1e-11ea-9800-1b2cbd1158db.PNG)
![test3](https://user-images.githubusercontent.com/56863735/86447618-4a36df00-bd1e-11ea-9f5f-2df67c7e2b28.PNG)

### 5. Example in browser when project is running:
- Owners: http://localhost:8080/api/v1/owners
- Property types: http://localhost:8080/api/v2/properties
- Building records: http://localhost:8080/api/v3/records
- Tax calculate by owner id 1: http://localhost:8080/api/v3/taxes/1
- Tax calculate by owner id 3: http://localhost:8080/api/v3/taxes/3

## Dependencies:
```
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'org.springframework.boot:spring-boot-devtools'
  implementation 'org.springframework.boot:spring-boot-starter-test'
  implementation 'javax.persistence:persistence-api'
  implementation 'com.fasterxml.jackson.core:jackson-annotations'
  implementation 'io.springfox:springfox-swagger2'
  implementation 'io.springfox:springfox-swagger-ui'
  runtimeOnly 'h2:com.h2database'
}
```
