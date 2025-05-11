# Adding and Setting Up SQLAlchemy in Flask

## Overview
- APIs typically interact with databases to create, read, update, and delete (CRUD) records.
- Relational databases are the most common choice, with SQLite used here due to its file-based nature, eliminating the need for server-based database software (e.g., SQL Server, MySQL).
- SQLite is lightweight, requires no installation, and is natively supported by Python.
- An Object-Relational Mapper (ORM), specifically SQLAlchemy, is used to simplify database interactions by generating SQL queries behind the scenes, allowing Python objects to represent database records.

## Why Use an ORM (SQLAlchemy)?
- Eliminates the need to write raw SQL queries.
- Provides Python objects with properties and methods for database operations.
- Enables easy database switching (e.g., from SQLite to MySQL).
- Allows database schema management via code, trackable in version control.
- Supports multiple databases for apps distributed in on-premises solutions.

## Installing Flask-SQLAlchemy
1. **Using PyCharm**
   - Navigate to `Preferences > Project Interpreter`.
   - Click the `+` button to add a new package.
   - Search for `Flask-SQLAlchemy` (ensure the package name includes the dash, as `FlaskSQLAlchemy` is a different package).
   - Click `Install Package`.
   - Flask-SQLAlchemy automatically installs the required `SQLAlchemy` dependency.
2. **Using Terminal (Alternative)**
   ```bash
   pip install Flask-SQLAlchemy
   ```

## Setting Up SQLAlchemy in Flask
1. **Import Required Libraries**
   - Import `SQLAlchemy` from `flask_sqlalchemy` for Flask integration.
   - Import column types (`Column`, `Integer`, `String`, `Float`) from `sqlalchemy` for defining database models.
   - Import `os` for file path handling.
   ```python
   from flask import Flask
   from flask_sqlalchemy import SQLAlchemy
   from sqlalchemy import Column, Integer, String, Float
   import os
   ```

2. **Configure the Database Path**
   - Define the base directory to store the SQLite database file in the same folder as the application.
   - Use `os.path.abspath` and `os.path.dirname(__file__)` to get the application's directory.
   ```python
   app = Flask(__name__)
   basedir = os.path.abspath(os.path.dirname(__file__))
   ```

3. **Set Flask Configuration for SQLAlchemy**
   - Configure the `SQLALCHEMY_DATABASE_URI` to point to the SQLite database file.
   - Use `os.path.join` to create an OS-compatible file path for the database file (e.g., `planets.db`).
   - The URI format for SQLite is `sqlite:///` followed by the file path.
   ```python
   app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'planets.db')
   ```
   - **Note**: The key `SQLALCHEMY_DATABASE_URI` must be exact (all caps, no deviations).
   - `os.path.join` ensures compatibility across operating systems (e.g., backslashes on Windows, forward slashes on Linux/Mac).

## Key Points
- SQLite is ideal for development due to its simplicity and lack of server management.
- Flask-SQLAlchemy integrates SQLAlchemy with Flask’s configuration system.
- The configuration step sets the foundation for defining database models (covered in subsequent steps).
- Using `os.path.join` ensures cross-platform compatibility for file paths.

# Creating ORM Model Classes and Seeding the Database in Flask-SQLAlchemy

## Overview
- SQLAlchemy models are Python classes that map to database tables, with attributes representing columns.
- The Flask CLI (Command Line Interface) is used to manage database operations like creating, dropping, and seeding the database.
- Models are defined in the same file for simplicity, but can be modularized into separate files for larger projects.
- The database consists of two tables: `users` and `planets`.

## Initializing the Database
- Initialize SQLAlchemy after configuration to prepare the database for use.
- Example:
  ```python
  from flask_sqlalchemy import SQLAlchemy

  app = Flask(__name__)
  # Configuration (e.g., SQLALCHEMY_DATABASE_URI) goes here
  db = SQLAlchemy(app)
  ```

## Creating ORM Model Classes
1. **User Model**
   - Represents the `users` table.
   - Inherits from `db.Model` to integrate with SQLAlchemy.
   - Defines a custom table name using `__tablename__`.
   - Fields:
     - `id`: Integer, primary key.
     - `first_name`: String.
     - `last_name`: String.
     - `email`: String, unique constraint.
     - `password`: String.
   ```python
   class User(db.Model):
       __tablename__ = 'users'
       id = db.Column(db.Integer, primary_key=True)
       first_name = db.Column(db.String)
       last_name = db.Column(db.String)
       email = db.Column(db.String, unique=True)
       password = db.Column(db.String)
   ```

2. **Planet Model**
   - Represents the `planets` table.
   - Inherits from `db.Model`.
   - Defines a custom table name using `__tablename__`.
   - Fields:
     - `planet_id`: Integer, primary key.
     - `planet_name`: String.
     - `planet_type`: String (e.g., Class D, K, M from Star Trek).
     - `home_star`: String (e.g., Sol).
     - `mass`: Float (in scientific notation, e.g., 3.258e23).
     - `radius`: Float (in miles).
     - `distance`: Float (distance from star in miles, e.g., 35.98e6).
   ```python
   class Planet(db.Model):
       __tablename__ = 'planets'
       planet_id = db.Column(db.Integer, primary_key=True)
       planet_name = db.Column(db.String)
       planet_type = db.Column(db.String)
       home_star = db.Column(db.String)
       mass = db.Column(db.Float)
       radius = db.Column(db.Float)
       distance = db.Column(db.Float)
   ```

## Seeding the Database with Flask CLI
1. **Flask CLI Overview**
   - Introduced in Flask 1.0, the CLI allows running custom commands against the Flask app.
   - Commands are defined using the `@app.cli.command` decorator.

2. **Defining CLI Commands**
   - **Create Database**
     - Uses `db.create_all()` to generate tables from model classes.
     ```python
     @app.cli.command('db_create')
     def db_create():
         db.create_all()
         print('Database created')
     ```
   - **Drop Database**
     - Uses `db.drop_all()` to delete the database file.
     ```python
     @app.cli.command('db_drop')
     def db_drop():
         db.drop_all()
         print('Database dropped')
     ```
   - **Seed Database**
     - Adds initial test data (three planets and one user).
     - Creates instances of `Planet` and `User`, then adds them to the database session.
     ```python
     @app.cli.command('db_seed')
     def db_seed():
         mercury = Planet(
             planet_name='Mercury',
             planet_type='Class D',
             home_star='Sol',
             mass=3.258e23,
             radius=1516,
             distance=35.98e6
         )
         venus = Planet(
             planet_name='Venus',
             planet_type='Class K',
             home_star='Sol',
             mass=4.867e24,
             radius=3760,
             distance=67.24e6
         )
         earth = Planet(
             planet_name='Earth',
             planet_type='Class M',
             home_star='Sol',
             mass=5.972e3,
             radius=3959,
             distance=92.96e6
         )
         test_user = User(
             first_name='William',
             last_name='Herschel',
             email='test@test.com',
             password='P@ssw0rd'
         )
         db.session.add(mercury)
         db.session.add(venus)
         db.session.add(earth)
         db.session.add(test_user)
         db.session.commit()
         print('Database seeded')
     ```

## Key Points
- Models define the database schema using Python classes, with `db.Column` specifying column types and constraints.
- `__tablename__` allows explicit control over table names.
- The Flask CLI simplifies database management with custom commands.
- Seeding populates the database with test data for development and testing.
- Use `db.session.add()` to stage records and `db.session.commit()` to save them to the database.
- The seed script includes realistic data for planets (e.g., mass, radius, distance) and a test user with a non-production password.

# Viewing SQLite Database and Serializing SQLAlchemy Results

## Viewing the Database in DB Browser for SQLite
1. **Fixing the Seed Script**
   - The `db_seed` script requires `db.session.commit()` to save changes to the database.
   - Without `commit`, records are not persisted.
   - Add a confirmation message for consistency.
   ```python
   @app.cli.command('db_seed')
   def db_seed():
       # ... (planet and user creation) ...
       db.session.add(mercury)
       db.session.add(venus)
       db.session.add(earth)
       db.session.add(test_user)
       db.session.commit()
       print('Database seeded')
   ```

2. **Running Flask CLI Commands**
   - Use the terminal in PyCharm (or any terminal with the virtual environment activated).
   - Commands:
     - Create database: `flask db_create`
     - Seed database: `flask db_seed`
     - Drop database: `flask db_drop`
   - PyCharm’s terminal auto-activates the virtual environment (indicated by `(venv)` in the prompt).

3. **Using DB Browser for SQLite**
   - A free, cross-platform tool to view SQLite databases (download from https://sqlitebrowser.org).
   - Steps:
     - Open DB Browser and select `Open Database`.
     - Locate `planets.db` in the project directory (e.g., `PyCharmProjects/planetary_api/planets.db`).
     - Verify tables (`users`, `planets`) and their fields.
     - Use `Browse Data` to view records (e.g., three planets and one user).
     - Switch between tables via the dropdown.
   - **Note**: Refresh the tool manually to see updates, as it does not auto-update.
   - After running `flask db_drop`, the database file may still exist but contains no tables.

## Retrieving a List of Planets from the Database
1. **Creating the Planets Endpoint**
   - Add a new endpoint to retrieve all planets, restricted to GET requests.
   - Use SQLAlchemy’s `query.all()` to fetch all records from the `Planet` table.
   ```python
   from flask import Flask, jsonify

   @app.route('/planets', methods=['GET'])
   def planets():
       planets_list = Planet.query.all()
       return jsonify(data=planets_list)
   ```
   - **Issue**: Direct `jsonify` fails because `Planet` objects are not JSON-serializable, resulting in a `TypeError`.

2. **Error Handling**
   - Running the endpoint in Postman (`http://localhost:5000/planets`) may yield:
     - `500 Internal Server Error` if the database is not recreated/seeded after a drop.
     - `TypeError: Object of type Planet is not JSON serializable` due to non-dictionary data.
   - Fix the database issue by running:
     ```bash
     flask db_create
     flask db_seed
     ```

## Serializing SQLAlchemy Results with Marshmallow
1. **Why Marshmallow?**
   - Marshmallow is a serialization/deserialization library that converts SQLAlchemy objects to JSON-compatible formats.
   - Flask-Marshmallow integrates with Flask-SQLAlchemy for seamless use.
   - Avoids manual iteration or custom JSON conversion methods.

2. **Installing Flask-Marshmallow**
   - In PyCharm: `Preferences > Project Interpreter > + > Search for flask-marshmallow > Install Package`.
   - Alternatively:
     ```bash
     pip install flask-marshmallow
     ```
   - Installs both `flask-marshmallow` and `marshmallow`.

3. **Configuring Marshmallow**
   - Import and initialize Marshmallow after SQLAlchemy.
   ```python
   from flask import Flask
   from flask_sqlalchemy import SQLAlchemy
   from flask_marshmallow import Marshmallow

   app = Flask(__name__)
   # ... (SQLAlchemy config) ...
   db = SQLAlchemy(app)
   ma = Marshmallow(app)
   ```

4. **Creating Schema Classes**
   - Define schemas to specify which fields to serialize.
   - Example for `User` and `Planet`:
   ```python
   class UserSchema(ma.Schema):
       class Meta:
           fields = ('id', 'first_name', 'last_name', 'email', 'password')

   class PlanetSchema(ma.Schema):
       class Meta:
           fields = ('planet_id', 'planet_name', 'planet_type', 'home_star', 'mass', 'radius', 'distance')

   # Instantiate schemas for single and multiple records
   user_schema = UserSchema()
   users_schema = UserSchema(many=True)
   planet_schema = PlanetSchema()
   planets_schema = PlanetSchema(many=True)
   ```

5. **Updating the Planets Endpoint**
   - Use Marshmallow to serialize the `planets_list` before returning.
   ```python
   @app.route('/planets', methods=['GET'])
   def planets():
       planets_list = Planet.query.all()
       result = planets_schema.dump(planets_list)
       return jsonify(result.data)
   ```
   - `planets_schema.dump()` converts the SQLAlchemy result to a JSON-serializable format.
   - Test in Postman (`http://localhost:5000/planets`) to confirm a JSON response with all planet records.

## Key Points
- Always commit database changes with `db.session.commit()` in seed scripts.
- Flask CLI commands simplify database management (`db_create`, `db_seed`, `db_drop`).
- DB Browser for SQLite is useful for verifying database structure and data but requires manual refresh.
- Restrict endpoints to specific HTTP methods (e.g., `methods=['GET']`) for better control.
- Marshmallow automates serialization, making SQLAlchemy results JSON-compatible.
- Use separate schema instances for single (`many=False`) and multiple (`many=True`) records.

