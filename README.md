# PatientHub Service
## Description
PatientHub Service is a Spring Boot application designed to manage and perform CRUD operations on patient data. This service is designed to provide efficient access and manipulation of patient resources, leveraging Spring's robust backend capabilities. Key features include caching, configuration management, custom exception handling, profile-based configuration for different environments, and unit testing. The application is dockerized, including a MySQL database setup, for ease of deployment and testing.

## Features
- CRUD Operations: Create, Read, patient data.
- Caching: Implemented using Spring Cache for efficient data retrieval.
- Configuration Management: Spring Boot's configuration management with externalized property files and environment variables.
- Custom Exception Handling: Custom exception classes to handle scenarios like validation errors or resource not found.
- Profile-Based Configuration: Different configurations for various environments (development, QA, production).
- Unit Testing: Includes minimal unit test cases to ensure functionality.
- Dockerization: Application and MySQL database containerized for easy deployment and scalability.

## Installation and Setup
### Prerequisites
- Docker & Docker Compose
### Clone the Repository
```
git clone https://github.com/Samratmajumdar9748/patientHubService.git
cd patientHubService
```
## Docker-Based Setup
The application and MySQL database can be run using Docker, which means you don't need to install MySQL locally.
- Build and Run with Docker Compose

  ```
  docker-compose up --build
  ```
This command will set up the MySQL database and run the Spring Boot application in containers as defined in your docker-compose.yml.
- Accessing the Application
After the Docker containers are up and running, the PatientHub Service will be accessible at http://localhost:8080/

## Usage
Access the REST API endpoints to manage patient data. For example:
- POST /insertPatient : Insert a new Patient
  Sample Request body:
  ```
  {
    "email": "somneone@example.com",
    "phoneNumber": 1234567890,
    "dateOfBirth": "1990-05-15",
    "address": {
        "state": "California",
        "city": "Los Angeles",
        "zip": 90001,
        "street": "123 Maasdawfin 3St"
    },
    "name": "Samrat Majumdar",
    "medicalHistory": "No signifgaergaerdfargarwgargaregaegaeggaergggggggggggggggggggggggggggggggaicagargrgnt32aefaef medical history"
  }
  ```
- GET /getPatient?patientId=12345 : Get the details of a Patient
- GET /allPatients : Get the details of all the patients
