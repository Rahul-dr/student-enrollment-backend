# 🎓 Student Course Enrollment System

A comprehensive **RESTful backend API** built with **Spring Boot**, featuring secure JWT authentication, role-based access control, and interactive Swagger documentation. Perfect for managing student enrollments in educational institutions.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Authentication-red.svg)](https://jwt.io/)
[![Swagger](https://img.shields.io/badge/Swagger-API%20Docs-green.svg)](https://swagger.io/)

---

## 🚀 Features

✅ **Secure Authentication** - JWT-based user registration and login  
✅ **Role-Based Access Control** - Admin and Student roles with specific permissions  
✅ **Course Management** - Full CRUD operations for courses (Admin only)  
✅ **Enrollment System** - Students can enroll/unenroll in courses  
✅ **Profile Management** - View and manage user profiles  
✅ **Interactive API Documentation** - Swagger UI with JWT authorization  
✅ **Exception Handling** - Global error handling with meaningful responses  
✅ **Data Transfer Objects** - Clean API contracts with DTOs  
✅ **Database Integration** - MySQL with Spring Data JPA

---

## 🛠 Tech Stack

| Component | Technology |
|-----------|------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.0+ |
| **Security** | Spring Security + JWT |
| **Database** | MySQL 8.0+ |
| **ORM** | Spring Data JPA / Hibernate |
| **Documentation** | Swagger/OpenAPI 3.1 |
| **Build Tool** | Maven |


---

## 📁 Project Structure

```
src/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
├── util/
├── security/
├── config/
└── exception/
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/student-course-enrollment.git
cd student-course-enrollment
```

### 2. Setup Database
```sql
-- Create database
CREATE DATABASE student_course_db;

-- Create user (optional)
CREATE USER 'student_app'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON student_course_db.* TO 'student_app'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure Application
Edit `src/main/resources/application.properties`:

```properties
spring.application.name=student-enroll

# MySQL DB Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/student_course_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Configuration
app.jwt.secret=mysecretkeymysecretkeymysecretkeymyse
```

### 4. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or using Java
mvn clean package
java -jar target/student-course-enrollment-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

---

## 📘 API Documentation

### Swagger UI
Access the interactive API documentation at:
🔗 **http://localhost:8080/swagger-ui.html**

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and get JWT token |

### Course Management (Admin Only)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/courses` | Get all courses |
| POST | `/api/courses` | Create new course |
| PUT | `/api/courses/{id}` | Update course |
| DELETE | `/api/courses/{id}` | Delete course |

### Student Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students/profile` | Get student profile |
| POST | `/api/students/enroll/{courseId}` | Enroll in course |
| DELETE | `/api/students/unenroll/{courseId}` | Unenroll from course |
| GET | `/api/students/enrolled-courses` | Get enrolled courses |

---

## 🔐 Authentication & Authorization

### JWT Token Usage

1. **Register/Login** to get JWT token
2. **Include token** in requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

### Role-Based Access

| Role | Permissions |
|------|-------------|
| **STUDENT** | • Enroll/unenroll in courses<br>• View available courses<br>• View profile and enrolled courses |
| **ADMIN** | • All student permissions<br>• Create, update, delete courses<br>• View all students and enrollments |

---

## 🧪 Testing the API

### Using Swagger UI
1. Open `http://localhost:8080/swagger-ui.html`
2. Register a new user or login
3. Copy the JWT token from the response
4. Click "Authorize" button in Swagger UI
5. Enter: `Bearer <your-token>`
6. Test the endpoints

### Using cURL Examples

```bash
# Register
curl -X POST "http://localhost:8080/auth/register" \
  -H "Content-Type: application/json" \
  -d '{"username":"john_doe","email":"john@example.com","password":"password123","role":"STUDENT"}'

# Login
curl -X POST "http://localhost:8080/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"john_doe","password":"password123"}'

# Get courses (with token)
curl -X GET "http://localhost:8080/api/courses" \
  -H "Authorization: Bearer <your-jwt-token>"
```

---

## 💾 Database Schema

### Students Table
```sql
CREATE TABLE student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT'
);
```

### Courses Table
```sql
CREATE TABLE course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);
```

### Enrollments Table
```sql
CREATE TABLE enrollment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);
```

---

## 👨‍💼 Admin Setup

To create an admin user, insert directly into the database:

```sql
-- Generate encrypted password first (use BCryptPasswordEncoder)
-- For password "admin123", the encrypted version would be something like:
-- $2a$10$X5wFWFNRWpWFqLDzUHQdZeXzD8p8.XqKVGbDk2EsKcCuVLwKhcJB6

INSERT INTO student (username, password, role) VALUES 
('admin', '$2a$10$X5wFWFNRWpWFqLDzUHQdZeXzD8p8.XqKVGbDk2EsKcCuVLwKhcJB6', 'ADMIN');
```

Or use this Java code to generate the encrypted password:
```java
System.out.println(new BCryptPasswordEncoder().encode("admin123"));
```

---

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
```bash
mvn clean test jacoco:report
```

---

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
```bash
mvn clean test jacoco:report
```

---

## 🎯 Learning Outcomes

This project demonstrates proficiency in:

- **Spring Boot** framework and ecosystem
- **JWT authentication** and authorization
- **RESTful API** design principles
- **Spring Security** configuration
- **Database design** and JPA relationships
- **Exception handling** and validation
- **API documentation** with Swagger
- **Role-based access control**
- **Clean architecture** and separation of concerns

---

## 🙏 Acknowledgments

- Spring Boot Team for the excellent framework
- JWT.io for JWT resources
- Swagger for API documentation tools
- MySQL for the reliable database system

---

## 📞 Contact

**Rahul D R**  
🔗 LinkedIn: https://www.linkedin.com/in/rahul-dr  
🐙 GitHub: https://github.com/Rahul-dr

---

<div align="center">
  <strong>⭐ Star this repository if you found it helpful!</strong>
</div>
