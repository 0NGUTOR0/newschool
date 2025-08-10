Newschool - Student Management System Backend
Newschool is a backend application being built for managing educational institutions, built with Spring Boot and MySQL. It provides secure RESTful APIs for registering prime users, adding 
staff with role assignments, and managing student records. The application leverages JWT-based authentication, role-based access control (RBAC), email notifications for staff invitations, 
and file uploads for student data. API documentation is provided via Springdoc OpenAPI for seamless integration.

Features
Prime User Registration: APIs to register privileged users (e.g., prime admins) with secure JWT authentication.
Staff Management: Add staff, assign roles (e.g., ROLE_ADMIN, ROLE_PRIMEADMIN), send email invitations with signup links, and filter staff by name, role, or school.
Student Management: Create student records with details like name, grade, age, sex, address, contact info, nationality, and file uploads (e.g., student image, parent ID). Supports
  filtering and pagination.
Role-Based Access Control: Restricts API access to authorized roles (e.g., ROLE_PRIMEADMIN for adding staff/students).
Email Notifications: Sends signup links to staff via Spring Mail.
File Uploads: Handles image and document uploads for student records, stored on the server.
API Documentation: Auto-generated OpenAPI documentation via Springdoc for easy testing and integration.
Error Handling: Robust validation and error responses for unauthorized access, invalid inputs, and data conflicts (e.g., duplicate emails).

Tech Stack
Backend: Spring Boot 3.1.5, Spring Data JPA, Spring Security, Spring OAuth2 Resource Server
Database: MySQL 8.4.0 (runtime), H2 (testing)
Authentication: JSON Web Tokens (JWT) with JJWT 0.11.5
Email: Spring Mail
API Documentation: Springdoc OpenAPI 2.6.0
Language: Java 17
Tools: Maven, Lombok 1.18.34, Spring Boot DevTools
Testing: Spring Boot Starter Test, Spring Security Test

Setup Instructions
To run the application locally:
Clone the repository: git clone https://github.com/yourusername/newschool.git
Set up MySQL: Ensure MySQL is running on localhost at port 3306. Create an empty database named newschool_db using a MySQL client. Update the src/main/resources/application.properties 
  file with your MySQL credentials, setting the database URL to jdbc:mysql://localhost:3306/newschool_db, your MySQL username, your MySQL password, and spring.jpa.hibernate.ddl-auto to 
  update. Spring Data JPA automatically generates the database schema (e.g., tables for prime users, staff, and students) on startup.
Configure file upload directory: Ensure the directory C:/Uploads/ exists on your system for storing uploaded files (e.g., student images).
Run the application: Execute mvn clean install followed by mvn spring-boot:run. The API will be accessible at http://localhost:8080. Access the API documentation at
http://localhost:8080/swagger-ui.html.

API Endpoints
Authentication:
POST /school/registerprime - Register a new prime user with hashed credentials.
POST /school/staff/login - Authenticate and receive a JWT token.
POST /school/prime/login - Authenticate prime account and receive a JWT token.

Staff Management:
POST /school/staff/addstaff - Add a staff member with details like name, email, role, and verification status (requires ROLE_PRIMEADMIN).
GET /school/staff/all?page={page}&limit={limit}&firstname={firstname}&role={role} - Retrieve a paginated list of staff with optional filters (e.g., firstname, role, school).
DELETE /school/staff/{id} - Delete a staff member (requires ROLE_ADMIN).

Student Management:
POST /school/students/addStudents - Create a student record with details like name, grade, birth year, sex, address, contact info, nationality, and file uploads (requires ROLE_PRIMEADMIN).
GET /school/students?page={page}&limit={limit}&name={name}&grade={grade} - Retrieve a paginated list of students with optional filters (e.g., name, grade, nationality).
GET /school/students/{id} - Retrieve a specific student by ID (requires ROLE_ADMIN or ROLE_PRIMEADMIN).

Project Structure
src/main/java/com/example/school/controllers: Controllers for staff and student management (e.g., StaffController, StudentController).
src/main/java/com/example/school/models: JPA entities for prime users, staff, and students.
src/main/java/com/example/school/services: Services for business logic, including staff signup, email sending, and prime account management.
src/main/resources: Configuration files, including application.properties for database, JWT, and email settings.
src/test: Unit and integration tests for API reliability and security.

Authentication
Newschool uses JWT-based authentication with role-based access control:
Registration: Prime users sign up via /api/auth/register, storing hashed credentials in a users table managed by JPA.
Prime login: Returns a JWT token via /school/prime/login  for accessing protected endpoints.
Staff login: Returns a JWT token via /school/staff/login  for accessing protected endpoints
Protected Endpoints: APIs like /school/staff/addstaff and /school/students/addStudents require a valid JWT in the Authorization header (e.g., Bearer <token>) and specific roles (e.g., ROLE_PRIMEADMIN).

Future Enhancements
Add bulk student and staff imports via CSV uploads.
Implement audit logging for user, staff, and student actions.
Enhance email notifications with templates for student registration and updates.

Contributing
Contributions are welcome! Please fork the repository, create a new branch, and submit a pull request with your changes.

Contact
For questions or feedback, reach out to ngutorugbor1@gmail.com or open an issue on GitHub.

