# OurSharedStorage - Personal File Storage Application

**OurSharedStorage** is a Java Spring Boot application designed to provide users with an effortless personal file storage experience. This project establishes the core backend infrastructure, handling data storage, access, and management.

## Features

- **Spring Boot Foundation**: Built on the powerful Spring Boot framework for rapid development, dependency management, and configuration.

- **Database Integration**: Seamlessly integrates with a PostgreSQL database using Spring Data JPA.

- **Database Migration**: Utilizes Flyway for efficient database schema management.

- **File Management**: Offers file upload, download, and organization functionalities.

- **CSV Integration**: Integrates opencsv for convenient CSV file handling.

## Getting Started

1. **Prerequisites**: Ensure Java 17, Maven, and Docker are installed.

2. **Database Setup with Docker**: Create a PostgreSQL instance using Docker:

docker run --name oursharedstorage-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres

Update database connection details in `pom.xml` under `<configuration>` of `flyway-maven-plugin`.

3. **Clone the Repository**: Clone this repository:
   git clone https://github.com/your-username/OurSharedStorage.git
 
4. **Build and Run**: Build and run the application with Maven:
   
mvn spring-boot:run

5. **Access the Application**: Visit [http://localhost:8080](http://localhost:8080) in your web browser.
