# RustApiSpring

A complete **Student REST API** built using **Spring Boot**, **Spring Data JPA**, **PostgreSQL**, **Lombok**, **ModelMapper**, and **Jakarta Bean Validation**.

This project demonstrates how to build a clean and structured REST API with CRUD operations, DTOs, validation, exception handling, partial updates, database integration, and consistent API responses.

---

## Features

* Create a student
* Get all students
* Get student by ID
* Update student using PUT
* Partially update student using PATCH
* Delete student
* Request validation
* Global exception handling
* Resource-not-found handling
* Duplicate email handling
* PostgreSQL database integration
* Spring Data JPA
* DTO-based request/response handling
* ModelMapper for Entity ↔ DTO conversion
* Lombok
* Consistent success and error response structure
* Postman API testing support

---

## Technologies Used

| Technology         | Purpose                       |
| ------------------ | ----------------------------- |
| Java 21            | Programming Language          |
| Spring Boot 3.3.4  | Backend Framework             |
| Spring Web         | REST API                      |
| Spring Data JPA    | Database Access               |
| Hibernate          | ORM                           |
| PostgreSQL         | Database                      |
| Lombok             | Boilerplate Code Reduction    |
| ModelMapper        | DTO Mapping                   |
| Jakarta Validation | Request Validation            |
| Maven              | Build & Dependency Management |
| Postman            | API Testing                   |

---

## Project Structure

```text
RustApiSpring
│
├── src
│   └── main
│       ├── java
│       │   └── com.spring.RustApi
│       │       │
│       │       ├── config
│       │       │   └── MapperConfig.java
│       │       │
│       │       ├── controller
│       │       │   └── StudentController.java
│       │       │
│       │       ├── dto
│       │       │   ├── AddStudentRequestDto.java
│       │       │   └── StudentDto.java
│       │       │
│       │       ├── entity
│       │       │   └── Student.java
│       │       │
│       │       ├── exception
│       │       │   ├── GlobalExceptionHandler.java
│       │       │   └── ResourceNotFoundException.java
│       │       │
│       │       ├── repository
│       │       │   └── StudentRepository.java
│       │       │
│       │       ├── response
│       │       │   └── ApiResponse.java
│       │       │
│       │       ├── service
│       │       │   ├── StudentService.java
│       │       │   └── impl
│       │       │       └── StudentServiceImpl.java
│       │       │
│       │       └── RustApiApplication.java
│       │
│       └── resources
│           └── application.properties
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

# Architecture

The project follows a layered architecture:

```text
Client / Postman
       │
       ▼
 Controller
       │
       ▼
 Service
       │
       ▼
 Repository
       │
       ▼
 PostgreSQL Database
```

### Controller

Handles HTTP requests and responses.

```text
GET
POST
PUT
PATCH
DELETE
```

### Service

Contains business logic.

### Repository

Uses Spring Data JPA to communicate with the database.

### Entity

Represents the database table.

### DTO

Used to transfer data between the client and application.

### Exception Handler

Provides centralized error handling for the entire application.

---

# Database

This project uses **PostgreSQL**.

Database name:

```text
studentDB
```

The application uses the following database configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentDB
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver
```

JPA configuration:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

The `students` table is automatically created/updated by Hibernate.

---

# Student Entity

The `Student` entity contains:

| Field | Type   | Constraint       |
| ----- | ------ | ---------------- |
| id    | Long   | Primary Key      |
| name  | String | Not Null         |
| email | String | Not Null, Unique |

Example:

```java
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
}
```

---

# API Response Structure

All APIs use a consistent response format.

## Success Response

```json
{
    "success": true,
    "message": "Student fetched successfully",
    "data": {
        "id": 1,
        "name": "Vinay Sharma",
        "email": "vinay@gmail.com"
    }
}
```

## Error Response

```json
{
    "success": false,
    "message": "Student not found: 100",
    "data": null
}
```

This makes the API easier to consume from frontend applications.

---

# Base URL

The application runs on:

```text
http://localhost:8080
```

The application uses `/api` as its context path.

Therefore, the base API URL is:

```text
http://localhost:8080/api
```

Student API:

```text
http://localhost:8080/api/students
```

---

# API Documentation

## 1. Get All Students

### Request

```http
GET http://localhost:8080/api/students
```

### Success

**200 OK**

```json
{
    "success": true,
    "message": "Students fetched successfully",
    "data": [
        {
            "id": 1,
            "name": "Vinay Sharma",
            "email": "vinay@gmail.com"
        },
        {
            "id": 2,
            "name": "Rahul Kumar",
            "email": "rahul@gmail.com"
        }
    ]
}
```

If there are no students:

```json
{
    "success": true,
    "message": "Students fetched successfully",
    "data": []
}
```

---

# 2. Get Student By ID

### Request

```http
GET http://localhost:8080/api/students/1
```

### Success

**200 OK**

```json
{
    "success": true,
    "message": "Student fetched successfully",
    "data": {
        "id": 1,
        "name": "Vinay Sharma",
        "email": "vinay@gmail.com"
    }
}
```

### Student Not Found

**404 NOT FOUND**

```json
{
    "success": false,
    "message": "Student not found: 100",
    "data": null
}
```

---

# 3. Create Student

### Request

```http
POST http://localhost:8080/api/students
```

### Request Body

```json
{
    "name": "Vinay Sharma",
    "email": "vinay@gmail.com"
}
```

### Success

**201 CREATED**

```json
{
    "success": true,
    "message": "Student created successfully",
    "data": {
        "id": 1,
        "name": "Vinay Sharma",
        "email": "vinay@gmail.com"
    }
}
```

---

# 4. Create Student - Validation Failure

### Request Body

```json
{
    "name": "",
    "email": ""
}
```

### Response

**400 BAD REQUEST**

```json
{
    "success": false,
    "message": "Validation failed",
    "data": {
        "name": "Name is required",
        "email": "Email is required"
    }
}
```

---

# 5. Invalid Email

### Request

```http
POST http://localhost:8080/api/students
```

### Request Body

```json
{
    "name": "Vinay Sharma",
    "email": "abc"
}
```

### Response

**400 BAD REQUEST**

```json
{
    "success": false,
    "message": "Validation failed",
    "data": {
        "email": "Email must be valid"
    }
}
```

---

# 6. Duplicate Email

Email is unique in the database.

If:

```text
vinay@gmail.com
```

already exists and another student is created using the same email:

```json
{
    "name": "Another Student",
    "email": "vinay@gmail.com"
}
```

### Response

**409 CONFLICT**

```json
{
    "success": false,
    "message": "Email already exists",
    "data": null
}
```

---

# 7. Update Student - PUT

PUT is used for a complete update.

### Request

```http
PUT http://localhost:8080/api/students/1
```

### Request Body

```json
{
    "name": "Vinay Kumar",
    "email": "vinaykumar@gmail.com"
}
```

### Success

**200 OK**

```json
{
    "success": true,
    "message": "Student updated successfully",
    "data": {
        "id": 1,
        "name": "Vinay Kumar",
        "email": "vinaykumar@gmail.com"
    }
}
```

### Student Not Found

**404 NOT FOUND**

```json
{
    "success": false,
    "message": "Student not found: 100",
    "data": null
}
```

---

# 8. Partial Update - PATCH

PATCH is used when only selected fields need to be updated.

### Update Name

```http
PATCH http://localhost:8080/api/students/1
```

Request body:

```json
{
    "name": "Vinay Singh"
}
```

### Response

**200 OK**

```json
{
    "success": true,
    "message": "Student partially updated successfully",
    "data": {
        "id": 1,
        "name": "Vinay Singh",
        "email": "vinaykumar@gmail.com"
    }
}
```

### Update Email

```json
{
    "email": "vinaysingh@gmail.com"
}
```

---

# 9. PATCH - Invalid Field

If an unsupported field is sent:

```json
{
    "age": 25
}
```

### Response

**400 BAD REQUEST**

```json
{
    "success": false,
    "message": "Unknown field: age",
    "data": null
}
```

---

# 10. Delete Student

### Request

```http
DELETE http://localhost:8080/api/students/1
```

### Success

**200 OK**

```json
{
    "success": true,
    "message": "Student deleted successfully",
    "data": null
}
```

### Student Not Found

```http
DELETE http://localhost:8080/api/students/100
```

**404 NOT FOUND**

```json
{
    "success": false,
    "message": "Student not found: 100",
    "data": null
}
```

---

# API Summary

| Operation         | HTTP Method | Endpoint             | Success Status |
| ----------------- | ----------- | -------------------- | -------------- |
| Get all students  | GET         | `/api/students`      | 200            |
| Get student by ID | GET         | `/api/students/{id}` | 200            |
| Create student    | POST        | `/api/students`      | 201            |
| Update student    | PUT         | `/api/students/{id}` | 200            |
| Partial update    | PATCH       | `/api/students/{id}` | 200            |
| Delete student    | DELETE      | `/api/students/{id}` | 200            |

---

# Error Status Codes

| Status | Meaning                        |
| ------ | ------------------------------ |
| 400    | Bad Request / Validation Error |
| 404    | Student Not Found              |
| 409    | Duplicate Email                |
| 500    | Internal Server Error          |

---

# Validation Rules

### Name

```java
@NotBlank(message = "Name is required")
private String name;
```

Name cannot be:

```text
null
""
"   "
```

### Email

```java
@NotBlank(message = "Email is required")
@Email(message = "Email must be valid")
private String email;
```

Email must be:

* Present
* Non-empty
* Valid email format
* Unique in the database

---

# ModelMapper

ModelMapper is used to convert between:

```text
Student
   ↓
StudentDto
```

and:

```text
AddStudentRequestDto
   ↓
Student
```

Example:

```java
Student student = modelMapper.map(
        dto,
        Student.class
);
```

Response mapping:

```java
StudentDto studentDto =
        modelMapper.map(
                student,
                StudentDto.class
        );
```

---

# Global Exception Handling

The application uses:

```java
@RestControllerAdvice
```

through:

```text
GlobalExceptionHandler
```

It handles:

* Validation errors
* Resource not found
* Invalid PATCH fields
* Duplicate email
* Unexpected exceptions

This keeps error handling centralized instead of writing exception handling separately in every controller.

---

# Testing With Postman

You can test all APIs using Postman.

### Base URL

```text
http://localhost:8080/api/students
```

### Recommended testing order

```text
1. POST
   ↓
2. GET ALL
   ↓
3. GET BY ID
   ↓
4. PUT
   ↓
5. PATCH
   ↓
6. DELETE
   ↓
7. GET BY ID again
```

For POST, PUT and PATCH use:

```text
Body
→ raw
→ JSON
```

Example:

```json
{
    "name": "Vinay Sharma",
    "email": "vinay@gmail.com"
}
```

---

# Setup & Installation

## 1. Clone Repository

```bash
git clone https://github.com/vinayraj010/RustApiSpring.git
```

Go into the project:

```bash
cd RustApiSpring
```

---

## 2. Configure PostgreSQL

Create a PostgreSQL database:

```sql
CREATE DATABASE studentDB;
```

Then configure:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentDB
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

---

## 3. Build the Project

Using Maven Wrapper:

```bash
./mvnw clean
```

Then:

```bash
./mvnw compile
```

---

## 4. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

API:

```text
http://localhost:8080/api/students
```

---

# Configuration

Example `application.properties`:

```properties
spring.application.name=RustApi

server.port=8080
server.servlet.context-path=/api

spring.datasource.url=jdbc:postgresql://localhost:5432/studentDB
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> Do not commit your real database username/password to GitHub. Use environment variables or a local configuration for credentials.

---

# Learning Goals

This project demonstrates practical Spring Boot concepts including:

* REST API development
* CRUD operations
* Dependency Injection
* Spring Boot layered architecture
* Spring Data JPA
* Hibernate
* PostgreSQL integration
* DTO pattern
* Entity mapping
* ModelMapper
* Bean Validation
* Global Exception Handling
* HTTP status codes
* PUT vs PATCH
* API response design
* Repository pattern
* Service layer
* Controller layer
* Postman API testing

---

# Author

**Vinay Raj**

GitHub:

https://github.com/vinayraj010

Repository:

https://github.com/vinayraj010/RESTApi

---

# Support

If you find this project useful for learning Spring Boot and REST API development, consider giving the repository a ⭐.

---

## Project Status

**Status:** Completed / Learning Project

Built with:

```text
Java 21
Spring Boot 3.3.4
Spring Data JPA
Hibernate
PostgreSQL
Lombok
ModelMapper
Jakarta Validation
Maven
Postman
```
