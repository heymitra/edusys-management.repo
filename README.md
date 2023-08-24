# EduSys Management System

Welcome to the EduSys Management System repository! This README provides an overview of the project's functionalities, database structure, layers, and instructions for usage.

## Project Overview

The EduSys Management System is designed to manage students, professors, and employees within an educational institution. This repository contains the source code for the system, organized into different layers to promote modularity and maintainability.

## Functionalities

The EduSys Management System offers a range of functionalities tailored to specific user roles:

### Student

- **View Profile**: Access personal information.
- **Browse Courses**: Explore a list of available courses.
- **Select Courses**: Enroll in courses for the current semester.
- **Track Progress**: Check taken courses and corresponding grades.
- **Logout**: Securely log out.

### Professor

- **View Profile**: Access personal details and information.
- **Grade Students**: Record and manage student grades.
- **Payroll Information**: Access payroll information.
- **Logout**: Securely log out.

### Employee

- **Student Records**: Access and manage student information.
- **Professor Records**: Manage professor-related details.
- **Staff Information**: Administer staff records and data.
- **Course List**: View and manage course offerings.
- **Payroll Management**: Access payroll details.
- **Logout**: Securely log out.

## Database Structure

The project's database is structured around the following tables:

1. **User Credential**: Stores user credentials, including usernames and passwords.
2. **User Info**: Contains personal details of users such as name, surname, role, professor position, and student semester.
3. **Available Courses**: Includes course information and the respective assigned professors.
4. **Selected Courses**: Serves as an intermediary table between available courses and user info, storing details about selected courses, grades, and evaluation status.

## Layers

The EduSys Management System employs a layered architecture to promote separation of concerns and maintainability:

1. **Base Layer**: Provides core components shared across the project.
2. **Entity Layer**: Defines entities representing domain concepts and their database mappings.
3. **Repository Layer**: Handles database interactions, including queries and data persistence.
4. **Service Layer**: Orchestrates business logic and communication between UI and repositories.
5. **UI Layer**: Presents the user interface, menus, and input/output handling.
6. **Util Layer**: Contains utility components for application context, security, and constants.
7. **Validation Layer**: Ensures data correctness and enforces business rules.

---

Thank you for exploring the EduSys Management System repository!
