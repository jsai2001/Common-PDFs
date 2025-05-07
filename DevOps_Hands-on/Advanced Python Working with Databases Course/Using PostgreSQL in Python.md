### PostgreSQL Overview and Database/Table Creation

#### 1. **What is PostgreSQL?**
- **Definition**: PostgreSQL (Postgres) is an **object-relational database** that organizes data into tables with columns and rows.
- **Key Features**:
  - **Object-Relational**: Supports advanced features like **table inheritance** and **function overloading**, typically found in object databases.
  - **Extensibility**: Allows custom data types, functions (via programming languages), and plugin development to replace system components.
  - **Standards Compliance**: Adheres closely to **SQL standards** (more than MySQL).
  - **ACID Compliance**: Ensures data integrity and handles multiple tasks efficiently.
  - **Scalability**: Highly scalable but more complex to work with.
  - **Stability**: Requires minimal maintenance due to its robust design.
- **Trade-offs**:
  - **Performance**: Slower for new client connections (forks a new process, ~10MB memory per connection).
  - **Read-Heavy Applications**: MySQL may perform better for read-heavy workloads.
  - **Popularity**: Less popular than MySQL, resulting in fewer third-party tools and developers.
- **Use Case**: Ideal for platforms/tools that prefer Postgres or require its advanced features.
- **Interaction with Python**: Use modules like **SQLAlchemy** or **psycopg2** (followascopy follows the **Python Database API** specification.

#### 2. **Client-Server Model**
- Postgres operates on a **client-server model**, requiring a driver (e.g., `psycopg2`) to interact with the database.

---

#### 3. **Creating a PostgreSQL Database**
- **Prerequisites**:
  - Install Postgres software (excluding optional tools like pgAdmin 4).
  - Set a memorable password during installation (used for authentication).
- **Configuration**:
  - Add Postgres to the system `PATH` (e.g., for macOS, edit `.zshrc` file):
    - Add: `export PATH=/path/to/Postgres/15/bin:$PATH`
    - Save and apply: `source ~/.zshrc`
  - Note: The configuration file may vary depending on the shell (e.g., `.bashrc` for Bash).
- **Accessing Postgres**:
  - Use the `psql` command to log in with the **root user** and the configured password.
- **Creating the Database**:
  - Command: `CREATE DATABASE red30;`
  - Verify: List databases with `\l` (shows `red30` and default databases).
  - Connect: `\c red30`
- **Purpose**: Reuse the `red30` database from a previous challenge.

---

#### 4. **Creating a Table in Postgres Using Python**
- **Module**: Use `psycopg2` (Python Database API-compliant, similar to `sqlite3` and `mysql-connector`).
- **Setup**:
  - Create and activate a virtual environment.
  - Install `psycopg2-binary` with: `pip install psycopg2-binary`
- **Python Script**:
  - File: `database.py` in the Postgres workspace.
  - Steps:
    1. Import `psycopg2`.
    2. Connect to the `red30` database.
    3. Create a cursor.
    4. Execute an SQL query to create a `sales` table.
    5. Commit changes and close the connection.
- **Execution**:
  - Run: `python3 database.py` in the activated virtual environment.
  - No console output (as expected).
- **Verification**:
  - Deactivate the virtual environment.
  - Log into the Postgres shell (`psql`).
  - Connect to `red30` (`\c red30`).
  - List tables with `\dt` to confirm the `sales` table exists.

---

### Code Snippets

#### 1. **Configuring PATH (macOS, ZSH)**
```bash
# Edit ~/.zshrc
export PATH=/path/to/Postgres/15/bin:$PATH

# Save and apply
source ~/.zshrc
```

#### 2. **Creating the Database (PostgreSQL Shell)**
```sql
CREATE DATABASE red30;
```

#### 3. **Listing and Connecting to Databases (PostgreSQL Shell)**
```sql
-- List databases
\l

-- Connect to red30
\c red30
```

#### 4. **Installing psycopg2 in Virtual Environment**
```bash
# Create and activate virtual environment
python3 -m venv venv
source venv/bin/activate

# Install psycopg2-binary
pip install psycopg2-binary
```

#### 5. **Creating a Table with psycopg2 (database.py)**
```python
import psycopg2

# Connect to red30 database
conn = psycopg2.connect(
    dbname="red30",
    user="root_user",  # Replace with your root user
    password="your_password",  # Replace with your password
    host="localhost",
    port="5432"
)

# Create cursor
cursor = conn.cursor()

# Create sales table
cursor.execute("""
    CREATE TABLE sales (
        id SERIAL PRIMARY KEY,
        product VARCHAR(100),
        order_total FLOAT
    );
""")

# Commit changes
conn.commit()

# Close cursor and connection
cursor.close()
conn.close()
```

#### 6. **Running the Script**
```bash
# In activated virtual environment
python3 database.py

# Deactivate virtual environment
deactivate
```

#### 7. **Verifying Table Creation (PostgreSQL Shell)**
```sql
-- Connect to red30
\c red30

-- List tables
\dt
```

---

### Key Points
- **Postgres vs. MySQL**:
  - Postgres: More extensible, SQL standards-compliant, but complex and slower for new connections.
  - MySQL: Faster for read-heavy apps, more popular, but less extensible.
- **Extensibility**: Custom data types, functions, and plugins make Postgres highly customizable.
- **Setup**:
  - Install Postgres, configure `PATH`, and set a secure password.
  - Use `psql` for shell interactions and `psycopg2` for Python.
- **Database/Table Creation**:
  - Create database with `CREATE DATABASE`.
  - Use Python (`psycopg2`) to create tables and interact with the database.
- **Verification**: Always confirm changes (e.g., table creation) in the Postgres shell.

### Creating a Table and Inserting Data in PostgreSQL Using Python

#### 1. **Creating a Table in Postgres Using Python**
- **Module**: Use `psycopg2`, a Python module compliant with the **Python Database API** (similar to `sqlite3` and `mysql-connector`).
- **Setup**:
  - Create and activate a virtual environment.
  - Install `psycopg2-binary` using: `pip install psycopg2-binary`.
- **Python Script**:
  - File: `database.py` in the Postgres workspace.
  - Steps:
    1. Import `psycopg2`.
    2. Establish a connection to the `red30` database.
    3. Create a cursor.
    4. Execute an SQL query to create a `sales` table.
    5. Commit changes and close the connection.
- **Execution**:
  - Run: `python3 database.py` in the activated virtual environment.
  - No console output (as designed).
- **Verification**:
  - Deactivate the virtual environment.
  - Log into the Postgres shell (`psql`).
  - Connect to `red30` (`\c red30`).
  - List tables with `\dt` to confirm the `sales` table exists.

---

#### 2. **Inserting Data into a Postgres Database**
- **Objective**: Insert sample data into the `sales` table for further manipulation.
- **Compatibility**: Methods used with MySQL (e.g., cursor, SQLAlchemy expression language) are largely compatible with Postgres due to Python’s database tools.
- **Available Methods**:
  - Use `psycopg2` cursor with SQL commands.
  - Use SQLAlchemy expression language.
  - Insert directly in the Postgres shell.
- **Chosen Method**: Use `psycopg2` with the cursor’s `executemany` function.
- **Process**:
  - Define a list of sales data, where each entry represents a row in the `sales` table.
  - Use a parameterized SQL `INSERT` query with placeholders (`%s`) for column values.
  - Execute the query with `cursor.executemany()` to insert all rows in the sales list.
  - Commit changes and close the connection.
- **Execution**:
  - Run: `python3 database.py` in the activated virtual environment.
- **Verification**:
  - Log into the Postgres shell.
  - Connect to `red30` (`\c red30`).
  - Query: `SELECT * FROM sales;` to confirm the inserted data.
- **Limitation**: The example uses hardcoded sales data.
- **Next Steps**: Explore dynamic data insertion (to be covered later).

---

### Code Snippets

#### 1. **Installing psycopg2 in Virtual Environment**
```bash
# Create and activate virtual environment
python3 -m venv venv
source venv/bin/activate

# Install psycopg2-binary
pip install psycopg2-binary
```

#### 2. **Creating a Table with psycopg2 (database.py)**
```python
import psycopg2

# Connect to red30 database
conn = psycopg2.connect(
    dbname="red30",
    user="root_user",  # Replace with your root user
    password="your_password",  # Replace with your password
    host="localhost",
    port="5432"
)

# Create cursor
cursor = conn.cursor()

# Create sales table
cursor.execute("""
    CREATE TABLE sales (
        id SERIAL PRIMARY KEY,
        product VARCHAR(100),
        order_total FLOAT
    );
""")

# Commit changes
conn.commit()

# Close cursor and connection
cursor.close()
conn.close()
```

#### 3. **Inserting Data with psycopg2 (database.py)**
```python
import psycopg2

# Connect to red30 database
conn = psycopg2.connect(
    dbname="red30",
    user="root_user",  # Replace with your root user
    password="your_password",  # Replace with your password
    host="localhost",
    port="5432"
)

# Create cursor
cursor = conn.cursor()

# Sample sales data
sales = [
    ("Product A", 1500.0),
    ("Product B", 1000.0),
    ("Product C", 750.0)
]

# Insert data using executemany
cursor.executemany(
    "INSERT INTO sales (product, order_total) VALUES (%s, %s)",
    sales
)

# Commit changes
conn.commit()

# Close cursor and connection
cursor.close()
conn.close()
```

#### 4. **Running the Script**
```bash
# In activated virtual environment
python3 database.py

# Deactivate virtual environment
deactivate
```

#### 5. **Verifying Table and Data (PostgreSQL Shell)**
```sql
-- Connect to red30
\c red30

-- List tables
\dt

-- Select all data from sales table
SELECT * FROM sales;
```

---

### Key Points
- **psycopg2**: A Python Database API-compliant module for interacting with Postgres, similar to other database connectors.
- **Table Creation**:
  - Use `psycopg2` to execute SQL `CREATE TABLE` queries.
  - Verify table creation with `\dt` in the Postgres shell.
- **Data Insertion**:
  - Use `cursor.executemany` for efficient insertion of multiple rows.
  - Parameterized queries (`%s`) ensure safety and flexibility.
  - Commit changes to persist data.
- **Verification**: Always confirm table creation and data insertion using the Postgres shell.
- **Flexibility**: Postgres supports multiple insertion methods (cursor, SQLAlchemy, shell), leveraging Python’s database compatibility.
- **Future Work**: Dynamic data insertion to replace hardcoded data.

### Interacting with a Postgres Database Using Python

#### Overview
- Focus: Creating a Python function to insert a new sale into a Postgres database with dynamic data.
- Key concepts: Dynamic data insertion, named placeholders, SQL injection prevention, single responsibility design.

#### Function Design
- **Function Purpose**: Inserts a sale record into the Postgres database.
- **Parameters**: Accepts a database cursor (or connection) and sale-related data (e.g., order number, product details).
- **Order Total Calculation**:
  - Formula: `order_total = quantity * price` (apply discount if applicable).
  - Suggestion: Extract calculation into a separate function for single responsibility design.
- **Data Handling**:
  - Uses **named placeholders** with a dictionary (`sale_data`) for dynamic data insertion.
  - Dictionary keys map to query placeholders, ensuring type-safe execution.
  - Approach prevents **SQL injection** by relying on database's parameterized query execution.

#### Implementation Details
- **Sale Data Dictionary**: Contains key-value pairs for query parameters (e.g., `order_number`, `product_number`, `quantity`, `price`, `discount`, `order_total`).
- **SQL Query**:
  - Uses named placeholders (e.g., `%(key)s`) to map dictionary values.
  - Query is executed with `cursor.execute(query, sale_data)`.
- **Main Function**:
  - Establishes database connection.
  - Collects user input: `order_number`, `product_number`, `quantity`, `price`, etc.
  - Optional improvement: Backend service to fetch product details (e.g., price) from another database.
  - Calls `insert_sale` function, commits changes, and closes connection.

#### Example Workflow
1. User inputs:
   - Order number: `123`
   - Product number: (e.g., pencil)
   - Quantity: `2`
   - Price: `9.99`
   - Discount: `None`
2. Order total: `2 * 9.99 = 19.98`
3. Data inserted into database.
4. Verification: Query database to confirm sale record.

#### Potential Improvements
- Separate product database for storing product details (e.g., price, description).
- Auto-generate `order_number` instead of user input.
- Encapsulate additional SQL commands (e.g., `SELECT`, `UPDATE`, `DELETE`) into functions tailored to the application.

#### Code Snippet
```python
import psycopg2

def insert_sale(cursor, order_number, product_number, quantity, price, discount=0):
    # Calculate order total
    order_total = quantity * price * (1 - discount)
    
    # Sale data dictionary for named placeholders
    sale_data = {
        'order_number': order_number,
        'product_number': product_number,
        'quantity': quantity,
        'price': price,
        'discount': discount,
        'order_total': order_total
    }
    
    # SQL query with named placeholders
    query = """
        INSERT INTO sales (order_number, product_number, quantity, price, discount, order_total)
        VALUES (%(order_number)s, %(product_number)s, %(quantity)s, %(price)s, %(discount)s, %(order_total)s)
    """
    
    # Execute query with sale data
    cursor.execute(query, sale_data)

def main():
    # Connect to Postgres database
    conn = psycopg2.connect(
        dbname="your_db",
        user="your_user",
        password="your_password",
        host="localhost"
    )
    cursor = conn.cursor()
    
    # Collect user input
    order_number = input("Enter order number: ")
    product_number = input("Enter product number: ")
    quantity = int(input("Enter quantity: "))
    price = float(input("Enter price: "))
    
    # Insert sale
    insert_sale(cursor, order_number, product_number, quantity, price)
    
    # Commit and close
    conn.commit()
    cursor.close()
    conn.close()

if __name__ == "__main__":
    main()
```

#### Additional Notes
- **Environment**: Run in a virtual environment to manage dependencies (e.g., `psycopg2`).
- **SQL Commands**: Experiment with other commands (e.g., `SELECT`, `UPDATE`) and wrap them in functions for practice.
- **Database Verification**: Post-insertion, query the database to confirm data integrity (e.g., `SELECT * FROM sales WHERE order_number = '123'`).

#### Practice Suggestions
- Create functions for common SQL operations (e.g., retrieve sales by product, update sale details).
- Build a product database and integrate it with the sales system.
- Add error handling for database connections and user inputs.



### Setting up and Manipulating a Postgres Database with SQLAlchemy Core

#### Overview
- **Objective**: Connect to and manipulate a Postgres database using **SQLAlchemy Core**.
- **Context**: Previously used `Psycopg2` for Postgres, `SQLite3` for SQLite, and `mysql-connector` with SQLAlchemy ORM for MySQL.
- **Interfaces**: SQLAlchemy Core, SQLAlchemy ORM, and database-specific modules (`Psycopg2`, etc.) can interact with relational databases (SQLite, MySQL, Postgres).
- **Approach**: Gradual introduction of interfaces to manage learning curve; SQLAlchemy Core abstracts database-specific differences.

#### Setting Up SQLAlchemy Core for Postgres
- **Environment**:
  - Reuse existing Postgres virtual environment or create a new one.
  - Activate virtual environment: `source venv/bin/activate`.
  - Install SQLAlchemy: `pip3 install sqlalchemy`.
- **Database**:
  - Use existing `Red30` database, already initialized with data.
  - No need to create tables; tables are autoloaded from the database.
- **Code Setup**:
  - Import SQLAlchemy components: `create_engine`, `MetaData`, `Table`.
  - Create an **engine** to connect to the Postgres database.
  - Use `MetaData` to manage schema.
  - Autoload existing table with the engine.
  - Bind metadata to engine with `metadata.create_all(engine)`.

##### Code Snippet: Setup
```python
from sqlalchemy import create_engine, MetaData, Table

# Create engine for Postgres database
engine = create_engine('postgresql+psycopg2://user:password@localhost/red30')

# Initialize metadata
metadata = MetaData()

# Autoload existing sales table
sales_table = Table('sales', metadata, autoload_with=engine)

# Bind metadata to engine
metadata.create_all(engine)
```

#### Manipulating Data with SQLAlchemy Core
- **Connection**:
  - Use `engine.connect()` to establish a connection.
  - Perform **CRUD** operations (Create, Read, Update, Delete) within the connection.
- **CRUD Operations**:
  1. **Read**:
     - Select all rows from the `sales` table using `select(sales_table)`.
     - Iterate and print each row.
  2. **Create**:
     - Insert a new sale (e.g., books titled "Understanding Artificial Intelligence").
     - Use `sales_table.insert().values(...)` to specify data.
  3. **Update**:
     - Update a sale (e.g., order number `1105910`) to reflect a refund (e.g., quantity = 2, order total = 39).
     - Use `sales_table.update().where(...).values(...)`.
  4. **Delete**:
     - Delete the inserted sale using `sales_table.delete().where(...)`.
     - Confirm deletion by checking row count (should be 0 if deleted).

##### Code Snippet: CRUD Operations
```python
from sqlalchemy import create_engine, MetaData, Table, select

# Setup engine and table
engine = create_engine('postgresql+psycopg2://user:password@localhost/red30')
metadata = MetaData()
sales_table = Table('sales', metadata, autoload_with=engine)

# Perform CRUD operations
with engine.connect() as connection:
    # Read: Select all rows
    query = select(sales_table)
    result = connection.execute(query)
    for row in result:
        print(row)

    # Create: Insert new sale
    connection.execute(sales_table.insert().values(
        order_number='1105910',
        product_name='Understanding Artificial Intelligence',
        quantity=3,
        order_total=60
    ))

    # Update: Modify sale (refund case)
    connection.execute(sales_table.update()
        .where(sales_table.c.order_number == '1105910')
        .values(quantity=2, order_total=39)
    )

    # Confirm update: Read specific sale
    query = select(sales_table).where(sales_table.c.order_number == '1105910')
    result = connection.execute(query)
    print(result.fetchone())

    # Delete: Remove sale
    connection.execute(sales_table.delete()
        .where(sales_table.c.order_number == '1105910')
    )

    # Confirm deletion: Check row count
    result = connection.execute(query)
    print(f"Row count: {result.rowcount}")
```

#### Execution and Verification
- **Run**: Execute script with `python3 database.py` in the activated virtual environment.
- **Output**:
  - **Read**: Prints all sales records.
  - **Create**: Adds new sale (e.g., quantity = 3, order total = 60).
  - **Update**: Updates sale (e.g., quantity = 2, order total = 39), confirmed via print.
  - **Delete**: Removes sale, confirmed by row count = 0.
- **Validation**: Console output reflects each operation’s success.

#### Key Takeaways
- **Abstraction**: SQLAlchemy Core simplifies database interactions by abstracting database-specific details.
- **Flexibility**: Supports CRUD operations with a consistent interface across relational databases.
- **Best Practices**:
  - Autoload tables when they exist to avoid manual schema definitions.
  - Use context managers (`with` statement) for connection management.
- **Next Steps**:
  - Explore SQLAlchemy ORM for object-oriented database interactions.
  - Compare SQLAlchemy Core vs. ORM vs. `Psycopg2` for performance and usability.



### Notes: Setting up and Manipulating a Postgres Database with SQLAlchemy ORM

#### Overview
- **Objective**: Use SQLAlchemy ORM to connect to and manipulate a Postgres database.
- **Key Difference from Core**: SQLAlchemy ORM uses classes and models to represent data, unlike the table-centric approach of SQLAlchemy Core.
- **Database**: Assumes an existing Postgres database (e.g., `Red30`) with a `sales` table.

#### Setting Up SQLAlchemy ORM for Postgres
- **Environment**:
  - Use an existing virtual environment or create a new one.
  - Ensure SQLAlchemy is installed: `pip3 install sqlalchemy`.
  - Optional: Install `psycopg2` for Postgres connectivity: `pip3 install psycopg2`.
- **Connection**:
  - Create an engine using `create_engine` to connect to the Postgres database.
- **Schema Setup**:
  - **Option 1**: Define the table schema manually in Python (explicit column definitions).
    - Benefit: Clear visibility of table structure during coding.
  - **Option 2**: Autoload the schema using `AutomapBase`.
    - Benefit: Avoid hardcoding every column; dynamically loads schema from the database.
  - Steps for Autoloading:
    - Use `AutomapBase` to create a base class.
    - Call `base.prepare(engine)` to autoload the schema.
    - Access the model (e.g., `sales`) via `base.classes.sales`.

##### Code Snippet: Setup
```python
from sqlalchemy import create_engine
from sqlalchemy.ext.automap import automap_base
from sqlalchemy.orm import Session

# Create engine
engine = create_engine('postgresql+psycopg2://user:password@localhost/red30')

# Set up automap base
Base = automap_base()

# Autoload schema
Base.prepare(engine, reflect=True)

# Access sales model
Sales = Base.classes.sales
```

#### Manipulating Data with SQLAlchemy ORM
- **Session**:
  - Create a session using `Session(engine)` to manage database communication.
  - All CRUD operations (Create, Read, Update, Delete) are performed via the session.
- **CRUD Operations**:
  1. **Read**:
     - Use `session.query(Sales)` to select data.
     - Apply `order_by(Sales.order_total)` to sort by order total.
     - Fetch the smallest sale and print its `order_total`.
  2. **Create**:
     - Instantiate a `Sales` object with sale data (e.g., `order_number`, `quantity`, `order_total`).
     - Add to session with `session.add(sale)`.
     - Commit with `session.commit()` to persist to the database.
  3. **Update**:
     - Modify the sale object’s attributes (e.g., `quantity = 2`, `order_total = 39`).
     - Since the object is bound to the session, changes are tracked.
     - Commit to update the database.
     - Verify by querying the sale with `session.query(Sales).filter_by(order_number=...)`.
  4. **Delete**:
     - Delete the sale object using `session.delete(sale)`.
     - Commit to finalize the deletion.

##### Code Snippet: CRUD Operations
```python
from sqlalchemy import create_engine
from sqlalchemy.ext.automap import automap_base
from sqlalchemy.orm import Session

# Setup engine and autoload schema
engine = create_engine('postgresql+psycopg2://user:password@localhost/red30')
Base = automap_base()
Base.prepare(engine, reflect=True)
Sales = Base.classes.sales

# Create session
session = Session(engine)

# Read: Get smallest sale by order total
smallest_sale = session.query(Sales).order_by(Sales.order_total).first()
print(f"Smallest sale total: {smallest_sale.order_total}")

# Create: Insert new sale
new_sale = Sales(order_number='1105910', quantity=3, order_total=60)
session.add(new_sale)
session.commit()

# Update: Modify sale
new_sale.quantity = 2
new_sale.order_total = 39
session.commit()

# Verify update: Query updated sale
updated_sale = session.query(Sales).filter_by(order_number='1105910').first()
print(f"Updated quantity: {updated_sale.quantity}, total: {updated_sale.order_total}")

# Delete: Remove sale
session.delete(updated_sale)
session.commit()
```

#### Execution and Verification
- **Run**: Execute the script with `python3 database.py` in the activated virtual environment.
- **Output**:
  - **Read**: Displays smallest sale total (e.g., `19.98`).
  - **Create/Update**: Confirms inserted sale with updated values (e.g., `quantity = 2`, `order_total = 39`).
  - **Delete**: Implicitly verified (no explicit check in transcript).
- **Validation**: Output matches expected values for each operation.

#### Key Takeaways
- **ORM Benefits**: Models provide an object-oriented interface, simplifying data manipulation.
- **Autoloading**: Reduces manual schema definitions, ideal for existing databases.
- **Portability**: Minimal code changes needed to switch between databases (e.g., MySQL, SQLite); only update the `create_engine` connection string.
- **Session Management**: Tracks changes to objects, streamlining CRUD operations.
- **Use Case**: ORM is ideal for applications requiring complex relationships and object-oriented design.

#### Additional Notes
- **Migration**: Switching databases (e.g., from Postgres to MySQL) requires only a change in the engine’s connection string, preserving most Python code.
- **Best Practices**:
  - Use autoloading for existing databases to save time.
  - Always commit changes to persist them.
  - Close sessions properly to release resources.
- **Next Steps**:
  - Explore manual schema definitions for finer control.
  - Compare SQLAlchemy ORM with Core and `Psycopg2` for specific use cases.
  - Add error handling for session management and database connectivity.

### Creating a Postgres Database with SQLAlchemy ORM

#### Overview
- **Challenge**: Create a Postgres database (`library`) to store authors, books, and their relationships using three tables: `authors`, `books`, and `AuthorBooks`.
- **Objective**: Implement functionality to add a new book, handling existing/new authors and creating author-book pairings within a transaction.
- **Tool**: SQLAlchemy ORM (alternative options: `psycopg2`, SQLAlchemy Core).
- **Database Structure**:
  - **authors**: Stores author details (`author_id`, `first_name`, `last_name`).
  - **books**: Stores book details (`book_id`, `title`, `number_of_pages`).
  - **AuthorBooks**: Junction table linking authors and books (`bookauthor_id`, `author_id`, `book_id`).
- **Purpose of AuthorBooks**: Enables multiple books per author and multiple authors per book, maintaining relational integrity.

#### Database Setup
- **Create Database**:
  - Use Postgres shell: `CREATE DATABASE library;`.
  - Connect to the database: `\c library`.
- **Environment**:
  - Activate virtual environment with SQLAlchemy and `psycopg2` installed (`pip3 install sqlalchemy psycopg2`).
- **SQLAlchemy ORM Setup**:
  - **Engine**: Connect to `library` database using `create_engine`.
  - **Models**: Define `Author`, `Book`, and `BookAuthor` classes with explicit column definitions (no automapping since tables are created programmatically).
  - **Relationships**: Use foreign keys in `BookAuthor` to link `author_id` and `book_id` to respective tables.
  - **Table Creation**: Use `Base.metadata.create_all(engine)` to create tables.

##### Code Snippet: Database Setup
```python
from sqlalchemy import create_engine, Column, Integer, String, ForeignKey
from sqlalchemy.orm import registry, relationship, Session
from sqlalchemy.ext.declarative import declarative_base

# Create engine
engine = create_engine('postgresql+psycopg2://user:password@localhost/library')

# Create registry and base
mapper_registry = registry()
Base = declarative_base(registry=mapper_registry)

# Define models
class Author(Base):
    __tablename__ = 'authors'
    author_id = Column(Integer, primary_key=True)
    first_name = Column(String)
    last_name = Column(String)

class Book(Base):
    __tablename__ = 'books'
    book_id = Column(Integer, primary_key=True)
    title = Column(String)
    number_of_pages = Column(Integer)

class BookAuthor(Base):
    __tablename__ = 'bookauthors'
    bookauthor_id = Column(Integer, primary_key=True)
    author_id = Column(Integer, ForeignKey('authors.author_id'))
    book_id = Column(Integer, ForeignKey('books.book_id'))
    author = relationship("Author")
    book = relationship("Book")

# Create tables
Base.metadata.create_all(engine)
```

#### Adding a New Book
- **Functionality**:
  - Add a new book to `books` table.
  - Check if the author exists in `authors`; add if new, skip if existing.
  - Create a pairing in `AuthorBooks` to link the book and author.
  - Perform all operations within a transaction to ensure data consistency.
- **Steps**:
  1. Collect user input: Book title, number of pages, author’s first and last name.
  2. Check for existing book by title to avoid duplicates.
  3. Check for existing author by first and last name.
  4. Add new book and/or author if necessary.
  5. Flush session to generate IDs for new records.
  6. Create `BookAuthor` pairing.
  7. Commit transaction to save changes.
- **Transaction Management**:
  - Use `Session` to manage operations.
  - `session.flush()` ensures IDs are generated before creating pairings.
  - `session.commit()` finalizes changes.

##### Code Snippet: Add Book Functionality
```python
from sqlalchemy import create_engine, Column, Integer, String, ForeignKey
from sqlalchemy.orm import registry, relationship, Session
from sqlalchemy.ext.declarative import declarative_base

# Setup (as above)
engine = create_engine('postgresql+psycopg2://user:password@localhost/library')
mapper_registry = registry()
Base = declarative_base(registry=mapper_registry)

# Models (as above)
class Author(Base):
    __tablename__ = 'authors'
    author_id = Column(Integer, primary_key=True)
    first_name = Column(String)
    last_name = Column(String)

class Book(Base):
    __tablename__ = 'books'
    book_id = Column(Integer, primary_key=True)
    title = Column(String)
    number_of_pages = Column(Integer)

class BookAuthor(Base):
    __tablename__ = 'bookauthors'
    bookauthor_id = Column(Integer, primary_key=True)
    author_id = Column(Integer, ForeignKey('authors.author_id'))
    book_id = Column(Integer, ForeignKey('books.book_id'))
    author = relationship("Author")
    book = relationship("Book")

Base.metadata.create_all(engine)

# Function to add a book
def add_book(session, title, pages, first_name, last_name):
    # Check if book exists
    existing_book = session.query(Book).filter_by(title=title).first()
    if existing_book:
        print(f"Book '{title}' already exists.")
        return

    # Check if author exists
    existing_author = session.query(Author).filter_by(
        first_name=first_name, last_name=last_name
    ).first()

    # Create new book
    new_book = Book(title=title, number_of_pages=pages)
    session.add(new_book)

    # Create or use existing author
    if not existing_author:
        new_author = Author(first_name=first_name, last_name=last_name)
        session.add(new_author)
        session.flush()  # Generate author_id
        author = new_author
    else:
        author = existing_author

    session.flush()  # Generate book_id

    # Create book-author pairing
    pairing = BookAuthor(author_id=author.author_id, book_id=new_book.book_id)
    session.add(pairing)
    session.commit()

    print(f"Added book '{title}' by {first_name} {last_name}")

# Main execution
def main():
    session = Session(engine)
    
    # Collect user input
    title = input("Enter book title: ")
    pages = int(input("Enter number of pages: "))
    first_name = input("Enter author's first name: ")
    last_name = input("Enter author's last name: ")
    
    # Add book
    add_book(session, title, pages, first_name, last_name)
    
    session.close()

if __name__ == "__main__":
    main()
```

#### Execution and Verification
- **Run**: Execute script with `python3 database.py` in the activated virtual environment.
- **Example Inputs and Outputs**:
  1. **Book 1**: "The Huntress", 560 pages, Kate Quinn.
     - Adds new book and author.
     - Creates pairing in `bookauthors`.
  2. **Book 2**: "The Institute", 576 pages, Steven King.
     - Adds new book and author.
     - Creates pairing.
  3. **Book 3**: "The Diamond Eye", Kate Quinn.
     - Detects existing author (Kate Quinn, `author_id=3`).
     - Adds new book and creates pairing with existing `author_id=3`.
- **Database Verification** (Postgres shell):
  - `SELECT * FROM authors;`: Shows Kate Quinn (`author_id=3`), Steven King (`author_id=4`).
  - `SELECT * FROM books;`: Lists three books with unique `book_id` values.
  - `SELECT * FROM bookauthors;`: Shows pairings (e.g., `author_id=3`, `book_id=4` for "The Huntress"; `author_id=3`, `book_id=6` for "The Diamond Eye").

#### Key Takeaways
- **Relational Design**: `AuthorBooks` table enables flexible author-book relationships, supporting multiple authors per book and vice versa.
- **Transaction Safety**: Using `session.flush()` and `session.commit()` ensures data consistency and valid foreign key references.
- **Duplicate Prevention**: Queries for existing books/authors avoid redundant entries.
- **ORM Benefits**: Simplifies database operations with model-based interactions and relationship management.
- **Scalability**: Structure supports adding books with multiple authors by creating additional `BookAuthor` entries.

#### Additional Notes
- **Alternatives**: Could use `psycopg2` (raw SQL queries) or SQLAlchemy Core (table-centric approach) for similar functionality.
- **Best Practices**:
  - Use transactions to maintain database integrity.
  - Flush sessions to generate IDs before creating relationships.
  - Validate user input to prevent errors (e.g., invalid page numbers).
- **Next Steps**:
  - Add functionality to handle multiple authors per book.
  - Implement queries to list all books by an author or all authors of a book.
  - Add error handling for database connectivity and invalid inputs.