
# Notes on Using Databases to Level Up Python Applications

## Introduction to Databases in Python
- **Purpose**: Databases enable automatic data population and persistence between user sessions in Python applications.
- **Functionality**: Provide an organized structure for easy access, storage, and management of large datasets.
- **Course Focus**:
  - Creating databases using **SQLite**, **MySQL**, and **Postgres**.
  - Interacting with databases using Python modules that implement the **Python Database API**.
  - Utilizing **SQLAlchemy**, an object-relational mapping (ORM) tool for simplified database operations.
- **Instructor**: Kathryn Hodge, a software engineer, guides the course on LinkedIn Learning.

## What is a Database?
- **Definition of Data**: Information such as names, birthdays, sky color, images, computer files, or PDFs.
- **Definition of Database**: An organized collection of data, e.g.:
  - A database of student names, GPAs, and class years.
  - A database of favorite photos, where each photo is a data item.
- **Why Organize Data?**:
  - Enables easy access, management, and updates.
  - Example: Adding a new photo to a photo database or querying students by GPA should be straightforward.
- **Database Capabilities**:
  - Support storage and manipulation of data.
  - Require a query language (e.g., SQL) to add, remove, or access data.
- **Types of Databases**:
  - **Relational Databases**: Organize data in tables with relationships (to be covered later).
  - **Non-Relational Databases**: Organize data differently, e.g., document stores or key-value pairs (to be covered later).

## Example Code Snippet: Connecting to SQLite with Python
Below is a basic example of how to connect to an SQLite database and create a table using Python's `sqlite3` module.

```python
import sqlite3

# Connect to a database (creates a new one if it doesn't exist)
conn = sqlite3.connect('students.db')

# Create a cursor object to execute SQL commands
cursor = conn.cursor()

# Create a table for students
cursor.execute('''
    CREATE TABLE IF NOT EXISTS students (
        id INTEGER PRIMARY KEY,
        name TEXT NOT NULL,
        gpa REAL,
        class_year INTEGER
    )
''')

# Commit the changes and close the connection
conn.commit()
conn.close()
```

## Example Code Snippet: Using SQLAlchemy for ORM
Below is an example of defining a table and inserting data using SQLAlchemy.

```python
from sqlalchemy import create_engine, Column, Integer, String, Float
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

# Create an engine for SQLite
engine = create_engine('sqlite:///students.db', echo=True)

# Create a base class for declarative models
Base = declarative_base()

# Define the Student model
class Student(Base):
    __tablename__ = 'students'
    id = Column(Integer, primary_key=True)
    name = Column(String, nullable=False)
    gpa = Column(Float)
    class_year = Column(Integer)

# Create the table
Base.metadata.create_all(engine)

# Create a session to interact with the database
Session = sessionmaker(bind=engine)
session = Session()

# Add a new student
new_student = Student(name='Alice Smith', gpa=3.8, class_year=2023)
session.add(new_student)
session.commit()
```

## Key Takeaways
- Databases are critical for managing and persisting data in Python applications.
- SQLite, MySQL, and Postgres are popular choices for relational databases.
- Python's database API and SQLAlchemy simplify database interactions.
- Data organization in databases ensures efficient access and manipulation.


# Database Notes

## Relational Databases

- **Definition**: A collection of data with predefined relationships, organized into tables with rows and columns.
- **Structure**:
  - **Columns**: Hold specific types of data (e.g., Name, GPA, Class Year).
  - **Rows**: Represent a set of values for one entity (e.g., a student’s record).
  - Each row is a record and must have a unique identifier (e.g., ID column) to avoid confusion.
- **Example**:
  - Database: `School`
  - Table: `Students`
    - Columns: `ID`, `Name`, `GPA`, `Class Year`
    - Row Example: `ID: 1, Name: Sally, GPA: 3.65, Class Year: 2027`
- **Foreign Key**: Establishes relationships between tables, connecting rows across multiple tables for flexible data access.
- **Key Concept**: Data is identified and accessed in relation to other data (within the same row or across tables).
- **Management**:
  - Managed using a **Relational Database Management System (RDBMS)** like SQLite, MySQL, or Postgres.
  - Uses **SQL (Structured Query Language)** for communication, though syntax varies by RDBMS.
- **Basic SQL Example**:
  - Select all data from the `Students` table:
    ```sql
    SELECT * FROM Students;
    ```
  - Select specific column (e.g., names):
    ```sql
    SELECT Name FROM Students;
    ```

## Non-Relational Databases

- **Definition**: Databases that do not use tables and rows, optimized for specific data types and formats.
- **Purpose**: Designed for big data and unstructured, diverse data that doesn’t fit into rigid rows and columns.
- **Storage Formats**:
  - JSON documents
  - Key-value pairs
  - Graphs (nodes and edges)
- **Types**:
  1. **Document Database**:
     - Stores JSON-like or XML-like data as documents.
     - Flexible, semi-structured, allows varying attributes per document.
     - Example:
       ```json
       {
         "name": "Susie Campbell",
         "cell": "123-456-7890",
         "email": "susie@example.com",
         "birthday": "2000-01-01"
       },
       {
         "name": "Mark Watson",
         "birthday": "1999-05-20"
       }
       ```
     - Popular systems: MongoDB, DynamoDB.
  2. **Columnar Database**:
     - Organized by columns rather than rows.
     - Rows can have different sets of columns.
     - Example: Data stored as `all IDs`, then `all Names`, etc.
     - Popular system: Cassandra (hybrid of key-value and columnar).
  3. **Graph Database**:
     - Models data as nodes (data) and edges (relationships).
     - Ideal for complex, interconnected relationships.
     - Popular system: Neo4j.
- **NoSQL**: Refers to “not only SQL,” using other languages/constructs for querying.
- **Note**: Not covered in the course, but useful for understanding differences from relational databases.


# Python Database API Notes

## Overview
- The Python Database API provides a common specification for Python database modules to ensure consistency and portability across different database management systems.
- Encourages conformity, making code transferable and broadening database system compatibility.
- Most Python database modules adhere to this interface, facilitating easier learning and code understanding across modules.

## Key Components

### Connection Object
- **Purpose**: Facilitates access to the database.
- **Creation**: Each database module implements a `connect` function that returns a connection object.
- **Functionalities**:
  - Close the database connection.
  - Commit pending transactions.
  - Rollback to the start of pending transactions (if supported).
- **Transactions**: A set of all-or-nothing changes to the database.

### Cursor Object
- **Purpose**: Enables interaction with the database.
- **Creation**: Obtained from the connection object.
- **Functionalities**:
  - Execute commands to insert, delete, or manipulate data.
  - Fetch data:
    - `fetchone()`: Retrieves one row.
    - `fetchmany()`: Retrieves multiple rows.
    - `fetchall()`: Retrieves all rows.

### Data Type Handling
- Modules must provide constructors for objects holding special values.
- Ensures correct type detection and binding when data is passed to cursor methods for operations like insertion.

## Example Code Snippet
Below is a basic example of using the Python Database API with SQLite3 to demonstrate connection and cursor usage:

```python
import sqlite3

# Establish a connection
conn = sqlite3.connect('example.db')

# Create a cursor
cursor = conn.cursor()

# Execute a command (e.g., create a table)
cursor.execute('''CREATE TABLE IF NOT EXISTS users
                 (id INTEGER PRIMARY KEY, name TEXT, age INTEGER)''')

# Insert data
cursor.execute("INSERT INTO users (name, age) VALUES (?, ?)", ("Alice", 25))

# Commit the transaction
conn.commit()

# Fetch data
cursor.execute("SELECT * FROM users")
print(cursor.fetchall())

# Close the connection
conn.close()
```

## Additional Resources
- Refer to the Python Enhancement Proposal (PEP) for the full Python Database API specification.
- Next steps: Explore SQLite3’s implementation of the Python Database API.

