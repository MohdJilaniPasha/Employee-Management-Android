# Employee Management System

## Project Overview
The Employee Management System is an Android application developed using Room Database to manage departments, employees, and employee details. The application supports CRUD operations and maintains relationships between database tables.

## Features

### Department Management
- Add, view, edit, and delete departments.
- Validate mandatory fields.
- Handle departments associated with employees.

### Employee Management
- Add, view, edit, and delete employees.
- Select a department from a dropdown.
- Display employee details along with department names.
- Search employees by name.
- Validate mandatory fields.

### Employee Details Management
- Add designation, salary, and joining date.
- View employee details along with employee and department names.
- Edit and delete employee details.

## Technologies Used
- Kotlin / Java
- Room Database
- MVVM Architecture
- Android Studio
- XML Layouts / Jetpack Compose
- RecyclerView / LazyColumn
- LiveData / Kotlin Flow
- Repository Pattern

## Database Design

The application contains three separate database tables.

**1. Department**
- departmentId – Primary Key, auto-generated
- departmentName – Required
- description

**2. Employee**
- employeeId – Primary Key, auto-generated
- employeeName – Required
- email – Required
- phoneNumber
- departmentId – Foreign Key referencing Department

**3. EmployeeDetails**
- detailsId – Primary Key, auto-generated
- employeeId – Foreign Key referencing Employee
- designation
- salary
- joiningDate

## Database Relationships
- One Department can have multiple Employees.
- One Employee can have one EmployeeDetails record.
- Foreign keys maintain referential integrity.
- JOIN queries or Room relationships are used to retrieve related data.

## Architecture

The application follows the MVVM architecture.

- **UI:** Displays data and handles user interactions.
- **ViewModel:** Manages UI-related data.
- **Repository:** Handles data operations.
- **DAO:** Defines database queries and CRUD operations.
- **Room Database:** Manages local data storage.

## How to Run the Project

1. Clone this repository.
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Run the application on an Android emulator or physical device.

## Data Validation and Integrity
- Validate mandatory fields.
- Display appropriate messages for empty lists.
- Show confirmation dialogs before deletion.
- Maintain database relationships using foreign keys.
- Handle related records appropriately when deleting departments or employees.

## Learning Outcomes
- Implementing Room Database in Android.
- Designing relational database tables.
- Implementing CRUD operations.
- Using MVVM architecture and the Repository Pattern.
- Managing foreign keys and database relationships.
- Implementing employee search and input validation.

## Author

**Mohammad Jilani Pasha**

Android Developer | Kotlin | Java | Room Database | MVVM
