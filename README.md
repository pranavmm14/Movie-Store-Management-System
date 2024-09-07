# Movie Store Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle_SQL_Developer-F80000?style=flat&logo=oracle&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-007396?style=flat&logo=java&logoColor=white)
![License: MIT](https://img.shields.io/badge/License-MIT-green?style=flat)

## Overview

The **Movie Store Management System** is a robust application designed to manage movie records efficiently. It provides comprehensive functionality for adding, updating, deleting, and viewing movie details, using Java for the front-end interface and Oracle Database for back-end data management. The system leverages stored procedures and advanced database features to optimize performance and ensure data integrity.

## Tech Stack

- **Front-End:** Java (JDBC)
- **Back-End:** Oracle Database
- **Features:** Stored Procedures, Exception Handling, Cursors

## Features

- **CRUD Operations**: Create, Read, Update, and Delete movie records.
- **Advanced Oracle Features**: Use of stored procedures, functions, and cursors.
- **Exception Handling**: Robust error management for both Java and Oracle.
- **Data Management**: Efficient data retrieval and manipulation with views and cursors.
- **User Interface**: Java-based front-end for interacting with the system.

## Getting Started

### Prerequisites

![Java](https://img.shields.io/badge/Java-JDK--21-orange?style=flat&logo=java) 
![Oracle](https://img.shields.io/badge/Oracle-SQL_Developer-red?style=flat&logo=oracle)

- **Java Development Kit (JDK) 8 or higher**
- **Oracle Database** (Local or Remote)
- **JDBC Driver** for Oracle Database

### Installation

1. **Clone the Repository:**

    ```bash
    git clone https://github.com/pranavmm14/Movie-Store-Management-System.git
    ```

2. **Set Up Oracle Database:**

    - Import the provided SQL scripts to create the necessary tables and stored procedures.
    - Configure the database connection settings in `MoviesDaoImpl.java`.

3. **Build the Project:**

    - Compile the Java files using your preferred IDE or command line.
    - Ensure that the JDBC driver is included in your classpath.

4. **Run the Application:**

    - Execute the `MoviesService` class to start the application and interact with the movie management system.

## Usage

- **Add Movie:** Input movie details to add a new movie record.
- **Update Movie:** Modify existing movie records by specifying the movie ID.
- **Delete Movie:** Remove a movie record from the database using its ID.
- **View Movies:** List all movies, search by director, or view action movies.



## License

![License](https://img.shields.io/github/license/pranavmm14/Movie-Store-Management-System?style=flat)

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
