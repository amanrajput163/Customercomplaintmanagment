# Customer Complaint Management REST API

This is a production-ready RESTful API developed using **Spring Boot** to manage customer complaints efficiently with full CRUD functionality.

## 🚀 Features
- **Secure Authentication:** Implemented JWT-based authentication and Role-Based Access Control (RBAC) using Spring Security.
- **API Optimization:** Enhanced performance using Pagination, Input Validation, and Global Exception Handling.
- **Database Management:** Designed relational schemas in MySQL and integrated persistence using JPA/Hibernate ORM.
- **Interactive Documentation:** Integrated Swagger (OpenAPI) for easy API testing and documentation.

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot, Spring Security, Hibernate, JPA
- **Database:** MySQL
- **Tools:** Maven, Postman, Swagger

## 📖 API Endpoints (Brief)
- `POST /api/auth/login` - User authentication and JWT generation.
- `GET /api/complaints` - Fetch complaints with pagination support.
- `POST /api/complaints` - Register a new customer complaint.

## ⚙️ How to Run
1. Clone this repository: `git clone [Your-Repo-Link]`
2. Update the `src/main/resources/application.properties` file with your MySQL database username and password.
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`
