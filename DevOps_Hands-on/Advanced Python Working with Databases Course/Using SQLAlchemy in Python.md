
# SQLAlchemy Notes

## Overview of SQLAlchemy
- **Definition**: SQLAlchemy is a popular Python library that serves as an Object-Relational Mapping (ORM) tool and SQL toolkit for relational databases.
- **Purpose**: Enables Python developers to interact with databases in a Pythonic way, reducing the need to write raw SQL queries.
- **Compatibility**: Works with various web frameworks (e.g., Flask) and databases (e.g., SQLite, MySQL, Postgres).
- **Components**:
  - **SQLAlchemy Core**: Schema-centric, focuses on tables, keys, and SQL concepts using the SQL Expression Language (mild abstraction of SQL).
  - **SQLAlchemy ORM**: Domain model-centric, abstracts database concepts, allowing object-oriented database interactions. Built on top of Core, so both can be used together.
- **Key Features**:
  - ORM abstracts underlying database concepts, making database interactions feel like Python code.
  - Core provides a consistent SQL Expression Language across multiple relational databases.
  - ORM leverages the unit of work pattern to maintain consistency between objects and the database.
- **Trade-offs**:
  - **Pros**: Simplifies data layer interactions, especially for developers unfamiliar with SQL. Fast queries out of the box with ORM.
  - **Cons**: May obscure SQL operations, shifting complexity to application code. Expert SQL users may write more performant queries manually.
- **SQLAlchemy 2.0**:
  - Aims to unify Core and ORM for a more streamlined experience.
  - Reduces multiple ways of performing tasks to simplify the library.
  - First version released, used in the course.

## Executing a SQL Query with SQLAlchemy
- **Setup**:
  - Example uses a SQLite database (`movies.db`) and a Python script (`database.py`).
  - Requires importing SQLAlchemy and creating an engine to connect to the database.
- **Code Example**:
  ```python
  import sqlalchemy

  # Create engine to connect to SQLite database
  engine = sqlalchemy.create_engine("sqlite:///movies.db", echo=True)

  # Establish connection
  with engine.connect() as conn:
      # Define and execute a textual SQL query
      query = "SELECT * FROM Movies"
      result = conn.execute(query)

      # Iterate through results and print
      for row in result:
          print(row)
  ```
- **Key Points**:
  - **Engine**: Created using `create_engine`. Acts as the starting point for SQLAlchemy, managing database connections. Not equivalent to a Python DB API connection.
  - **Connection**: Created via `engine.connect()`. Acts as a proxy for the actual DB API connection.
  - **Query Execution**: Use `conn.execute(query)` to run the query. Results are stored in a result object, which can be iterated to access data.
  - **Logging**: Setting `echo=True` in `create_engine` provides logs of SQLAlchemy’s behind-the-scenes actions (e.g., `BEGIN`, `ROLLBACK`).
  - **Transaction Management**: Implicit `BEGIN` and `ROLLBACK` occur if no changes are committed. No commit was needed in this example since it was a read-only operation.
- **Running the Script**:
  - Activate the virtual environment: `source bin/activate`.
  - Run the script: `python database.py`.
  - Output displays queried data from the `Movies` table, along with SQLAlchemy logs.

## Additional Notes
- **SQLAlchemy Core vs. ORM**:
  - Core is closer to raw SQL, suitable for those comfortable with SQL concepts.
  - ORM is ideal for object-oriented programming, abstracting SQL for ease of use.
- **Use Cases**:
  - Suitable for web applications, data analysis tools, or any Python script interacting with a database.
- **Future Learning**:
  - Course will explore SQL Expression Language and ORM in more detail.
  - Will cover handling implicit statements (e.g., `BEGIN`, `COMMIT`, `ROLLBACK`) later.


# SQLAlchemy and SQLite Notes

## Using SQLAlchemy Expression Language
- **Purpose**: Enhances code readability and maintainability when querying databases.
- **Key Components**:
  - **Metadata Object**: Tracks tables in the database, acting as a dictionary where table names are keys and table objects are values.
  - **Table Object**: Represents a database table, including its name, metadata, and columns.
  - **Connection**: Established using `engine.connect()` to interact with the database.
  - **Expression Language**: Allows writing SQL queries programmatically, e.g., `sqlalchemy.select(movies_table)`.

### Example: Querying Movies Table
```python
from sqlalchemy import create_engine, MetaData, Table, Column, Integer, String
engine = create_engine('sqlite:///movies.db')
metadata = MetaData()

movies_table = Table('Movies', metadata,
                     Column('title', String),
                     Column('director', String),
                     Column('year', Integer))

metadata.create_all(engine)

with engine.connect() as conn:
    select_stmt = sqlalchemy.select(movies_table)
    results = conn.execute(select_stmt)
    for row in results:
        print(row)
```

- **Execution**:
  - Run `python3 database.py` in a terminal with the activated workspace.
  - Output displays each movie entry from the Movies table.
- **Alternative**: Use `sqlalchemy.text()` for specific SQL commands not supported by the expression language, maintaining consistency within SQLAlchemy.

## Challenge: Create an SQLite Database
- **Objective**: Create a `Users` table with columns: `User_Id`, `First_Name`, `Last_Name`, `Email_Address`.
  - Insert 5 entries, with at least 2 having `gmail.com` email addresses.
  - Query to retrieve all email addresses.
- **Options**: Use `sqlite3` module or SQLAlchemy.

## Solution 1: Using sqlite3 Module
- **Setup**: Create a database file `users-sqlite.db` and a table with a primary key `User_Id` that auto-increments.
- **Primary Key**: `User_Id` uniquely identifies records, supports foreign key relationships, and auto-increments for simplicity (not ideal for production due to overhead).

### Code: sqlite3 Implementation
```python
import sqlite3

conn = sqlite3.connect('users-sqlite.db')
cursor = conn.cursor()

cursor.execute('''
    CREATE TABLE IF NOT EXISTS Users (
        User_Id INTEGER PRIMARY KEY AUTOINCREMENT,
        First_Name TEXT,
        Last_Name TEXT,
        Email_Address TEXT
    )
''')

users = [
    ('John', 'Doe', 'john.doe@gmail.com'),
    ('Jane', 'Smith', 'jane.smith@gmail.com'),
    ('Alice', 'Johnson', 'alice.j@example.com'),
    ('Bob', 'Brown', 'bob.b@example.com'),
    ('Emma', 'Davis', 'emma.davis@example.com')
]

cursor.executemany('INSERT INTO Users (First_Name, Last_Name, Email_Address) VALUES (?, ?, ?)', users)
conn.commit()

cursor.execute('SELECT Email_Address FROM Users')
for row in cursor.fetchall():
    print(row[0])

conn.close()
```

- **Execution**:
  - Run in the `version1` directory after activating the workspace.
  - Output shows inserted users and queried email addresses.

## Solution 2: Using SQLAlchemy (Without Expression Language)
- **Setup**: Create an engine to manage the database and define data as a list of dictionaries.
- **Table Creation**: Define the table and insert data without manually handling the primary key.

### Code: SQLAlchemy Implementation
```python
from sqlalchemy import create_engine, MetaData, Table, Column, Integer, String

engine = create_engine('sqlite:///users-sqlite.db')
metadata = MetaData()

users_table = Table('Users', metadata,
                    Column('User_Id', Integer, primary_key=True, autoincrement=True),
                    Column('First_Name', String),
                    Column('Last_Name', String),
                    Column('Email_Address', String))

metadata.create_all(engine)

users = [
    {'First_Name': 'John', 'Last_Name': 'Doe', 'Email_Address': 'john.doe@gmail.com'},
    {'First_Name': 'Jane', 'Last_Name': 'Smith', 'Email_Address': 'jane.smith@gmail.com'},
    {'First_Name': 'Alice', 'Last_Name': 'Johnson', 'Email_Address': 'alice.j@example.com'},
    {'First_Name': 'Bob', 'Last_Name': 'Brown', 'Email_Address': 'bob.b@example.com'},
    {'First_Name': 'Emma', 'Last_Name': 'Davis', 'Email_Address': 'emma.davis@example.com'}
]

with engine.connect() as conn:
    for user in users:
        conn.execute(users_table.insert().values(**user))
    result = conn.execute(users_table.select())
    for row in result:
        print(row)
```

- **Execution**:
  - Run in the `version2` directory.
  - Output shows users with auto-incremented `User_Id`.

## Solution 3: Using SQLAlchemy Expression Language
- **Setup**: Similar to Solution 2, but uses expression language for insert and select operations.
- **Advantages**: More readable and maintainable queries.

### Code: SQLAlchemy Expression Language Implementation
```python
from sqlalchemy import create_engine, MetaData, Table, Column, Integer, String, select, insert

engine = create_engine('sqlite:///users-sqlite.db')
metadata = MetaData()

users_table = Table('Users', metadata,
                    Column('User_Id', Integer, primary_key=True, autoincrement=True),
                    Column('First_Name', String),
                    Column('Last_Name', String),
                    Column('Email_Address', String))

metadata.create_all(engine)

users = [
    {'First_Name': 'John', 'Last_Name': 'Doe', 'Email_Address': 'john.doe@gmail.com'},
    {'First_Name': 'Jane', 'Last_Name': 'Smith', 'Email_Address': 'jane.smith@gmail.com'},
    {'First_Name': 'Alice', 'Last_Name': 'Johnson', 'Email_Address': 'alice.j@example.com'},
    {'First_Name': 'Bob', 'Last_Name': 'Brown', 'Email_Address': 'bob.b@example.com'},
    {'First_Name': 'Emma', 'Last_Name': 'Davis', 'Email_Address': 'emma.davis@example.com'}
]

with engine.connect() as conn:
    conn.execute(insert(users_table), users)
    result = conn.execute(select(users_table))
    for row in result:
        print(row)
```

- **Execution**:
  - Run in the `version3` directory.
  - Output includes the SQL insert statement and selected data.

## Key Takeaways
- **SQLAlchemy Expression Language**: Simplifies and standardizes database queries, improving code maintainability.
- **sqlite3**: Suitable for direct SQL operations, but less abstracted than SQLAlchemy.
- **Primary Key with Auto-Increment**: Simplifies prototyping but may not be optimal for production.
- **Flexibility**: Use `sqlalchemy.text()` for custom SQL when expression language is insufficient, keeping all database interactions within SQLAlchemy.
