
# SQLite Notes

## What is SQLite?
- **Definition**: SQLite is a relational database management system (RDBMS) that is lightweight in setup, administration, and resource usage.
- **Integration with Python**: Utilizes the `sqlite3` module, compliant with the Python Database API specification.
- **Key Features**:
  - **Serverless**: Operates without a server, integrating directly with the application locally, with database files stored on disk.
  - **Fast Access**: Direct disk storage results in rapid data access and manipulation.
  - **No Drivers Needed**: Requires no additional SQLite drivers or server process configuration.
  - **Self-Contained**: Minimal reliance on external libraries or operating system support, making it portable across platforms (e.g., mobile phones, gaming consoles).
  - **ACID Compliance**: All transactions are atomic, consistent, isolated, and durable, ensuring no partial transaction states even during crashes.
  - **Prototyping**: Ideal for prototyping applications, with the option to port to larger databases later.
  - **Production Note**: Use the same database in local and production environments to avoid unexpected errors.

## Creating an SQLite Database in Python
- **Steps**:
  1. Import the `sqlite3` module to access database functionality.
  2. Use the `connect` function to create or connect to a database (e.g., `movies.db`).
  3. Obtain a cursor object to execute SQL commands.
  4. Create a table using a `CREATE TABLE IF NOT EXISTS` SQL command.
  5. Commit changes with `connection.commit()` and close the connection.
  6. Run the script to generate the database file.

- **Example Code**:
```python
import sqlite3

# Connect to or create a database
connection = sqlite3.connect("movies.db")

# Create a cursor object
cursor = connection.cursor()

# Create a table
cursor.execute("""
    CREATE TABLE IF NOT EXISTS Movies (
        title TEXT,
        director TEXT,
        year INT
    )
""")

# Commit changes and close connection
connection.commit()
connection.close()
```

- **Table Schema**:
  - Table Name: `Movies`
  - Columns:
    - `title`: TEXT
    - `director`: TEXT
    - `year`: INT (used instead of a datetime type for simplicity)
  - Notes:
    - Schema could be improved by linking to a separate directors table to avoid repetition or to support multiple directors.
    - Current design prioritizes simplicity for learning purposes.

- **Running the Script**:
  - Save the Python file (e.g., `app.py`).
  - Run using `python3 app.py` in the terminal (Mac).
  - Output: Creates `movies.db` in the local directory, visible via `ls` or Finder.
  - Optional: Specify a full path in `connect` to store the database elsewhere.

- **Considerations**:
  - SQLite lacks a dedicated datetime data type; use TEXT, REAL, or INT (INT used here for year).
  - Focus is on using databases in Python, not advanced database modeling.


# SQLite Database Notes

## Inserting a Single Record

- **Objective**: Add a single movie entry to the SQLite database.
- **Table**: Movies (columns: title, director, year).
- **Example Entry**: "Taxi Driver", Martin Scorsese, 1976.
- **Steps**:
  1. Use `cursor.execute()` with an `INSERT` SQL command.
  2. Commit changes using `connection.commit()`.
  3. Close the connection with `connection.close()`.
- **Retrieve Data**:
  - Use `cursor.execute("SELECT * FROM Movies")` to select data.
  - Options to fetch data:
    - `fetchone()`: Retrieves a single row.
    - `fetchall()`: Retrieves all rows.
    - Iterate over cursor results.
  - Example using `fetchone()`:
    ```python
    cursor.execute("SELECT * FROM Movies")
    print(cursor.fetchone())  # Outputs: ('Taxi Driver', 'Martin Scorsese', 1976)
    ```
- **Prevent Duplicates**:
  - Comment out the insert command after the first run.
  - Alternatively, use an `IF NOT EXISTS` check before inserting.
- **Execution**:
  - Run the script (`python3 app.py`) in the same directory as the database file.
  - Output confirms the entry: `('Taxi Driver', 'Martin Scorsese', 1976)`.

## Adding Multiple Records

- **Objective**: Insert multiple movie entries in one operation.
- **Data Structure**:
  - Create a list of tuples, each containing movie data (title, director, year).
  - Example:
    ```python
    famous_films = [
        ("Pulp Fiction", "Quentin Tarantino", 1994),
        ("Back to the Future", "Robert Zemeckis", 1985),
        ("Moonrise Kingdom", "Wes Anderson", 2012)
    ]
    ```
- **Insertion**:
  - Use `cursor.executemany()` with an `INSERT` SQL command.
  - The command uses placeholders (`?`) for values:
    ```python
    cursor.executemany("INSERT INTO Movies VALUES (?, ?, ?)", famous_films)
    ```
  - Each tuple in `famous_films` is inserted as a separate record.
- **Retrieve Data**:
  - Use `cursor.execute("SELECT * FROM Movies")` to select all entries.
  - Use `fetchall()` to retrieve all results:
    ```python
    cursor.execute("SELECT * FROM Movies")
    print(cursor.fetchall())  # Outputs all movies, including Taxi Driver
    ```
  - Expected output includes all inserted movies plus the existing "Taxi Driver" entry.
- **Cursor Behavior**:
  - The cursor acts as a pointer; after iterating with `fetchall()`, it cannot be reused until reset by another `execute` or `executemany`.
  - Example: Post-`fetchall()`, `cursor.fetchone()` returns `None`.
- **Execution**:
  - Run the script (`python3 app.py`) in the same directory as the database file.
  - Output confirms all entries, e.g.:
    ```
    [
        ('Taxi Driver', 'Martin Scorsese', 1976),
        ('Pulp Fiction', 'Quentin Tarantino', 1994),
        ('Back to the Future', 'Robert Zemeckis', 1985),
        ('Moonrise Kingdom', 'Wes Anderson', 2012)
    ]
    ```


# SQLite Filtering Notes

## Filtering Records in SQLite
- Objective: Filter movies in an SQLite database by release year (e.g., 1985).
- Key Steps:
  1. Define a variable for the filter criteria (e.g., release year).
  2. Use the `cursor.execute()` function to run a SQL `SELECT` query with a `WHERE` clause.
  3. Use a placeholder (`?`) and a tuple to safely pass the filter value.
  4. Retrieve results using `cursor.fetchall()`.

## Why Use a Tuple?
- A tuple is used to pass values safely to the SQL query.
- Prevents SQL injection attacks, which can occur when using string operations to build queries.

## Code Snippet: Filtering Movies by Release Year
```python
import sqlite3

# Connect to the database
conn = sqlite3.connect('movies.db')
cursor = conn.cursor()

# Define the release year as a tuple
release_year = (1985,)

# Execute the SELECT query with a WHERE clause
cursor.execute("SELECT * FROM movies WHERE year = ?", release_year)

# Fetch and print all results
results = cursor.fetchall()
print(results)  # Example output: [('Back to the Future', 1985)]

# Close the connection
conn.close()
```

## Why Avoid String Operations?
- **Security Risk**: String concatenation (e.g., `"SELECT * FROM movies WHERE year = " + str(year)`) is vulnerable to SQL injection.
- **Safe Practice**: Use `?` placeholders and pass values as a tuple in `execute()` or `executemany()`.

## Additional Notes
- **Fetching Data**: Use `cursor.fetchall()` to retrieve all matching records.
- **Other SQL Commands**: Similar techniques apply to `UPDATE`, `DELETE`, and other SQL statements.
- **Improving Code**:
  - Break down commonly used SQL queries into reusable functions with error handling.
  - Consider using an ORM like SQLAlchemy to avoid hard-coded SQL strings entirely.

## Example Output
- Running the above code with a movies database containing "Back to the Future" yields:
  ```
  [('Back to the Future', 1985)]
  ```

## Best Practices
- Always use parameterized queries (`?` with tuples) for safety.
- Close database connections after use to free resources.
- Modularize SQL operations into functions for reusability and maintainability.