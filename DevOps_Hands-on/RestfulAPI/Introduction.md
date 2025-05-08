
# RESTful APIs with Python 3 and Flask - Course Notes

## Introduction to Flask
- **Flask Overview**: Flask is a microframework for building web applications and APIs in Python.
  - Provides essential features without enforcing infrastructure choices.
  - Rich ecosystem of plugins to address various development challenges.
  - Allows rapid development with flexibility in stack choices.
- **Course Instructor**: Bruce Van Horn, a professional software engineer with over 25 years of experience.
- **Course Focus**:
  - Building a RESTful API using Flask and Python 3.
  - Covers authentication, database access, object-relational mapping (ORM), and routing.
  - Introduces Flask plugins to enhance development.
  - Includes strategies for testing and deploying to a production cloud server.

## Prerequisites
- **Python Knowledge**:
  - Basic understanding of Python 3 (e.g., variables, functions).
  - No need for advanced Python expertise.
- **Web Application Basics**:
  - Familiarity with how web applications function, including HTML form posting and request-response workflows.
  - Understanding that APIs receive requests and return responses in JSON format.
  - JSON is preferred over XML or custom formats due to ease of use (no parsing required).
- **Tools**:
  - **PyCharm** (Professional Edition recommended):
    - Built-in tooling for Flask app development.
    - Free Community Edition lacks Flask-specific tools but can be used if familiar with virtual environments and package installation (Pip/EZ Install).
    - Beginners benefit from PyCharm’s user-friendly GUI.
    - Reference: JetBrains PyCharm (jetbrains.com/pycharm).
  - **Postman**:
    - Free tool for testing APIs.
    - Download: getpostman.com.
  - **DB Browser for SQLite**:
    - Free tool to view SQLite database contents.
    - Download: sqlitebrowser.org.
- **Database**:
  - SQLite is used to simplify the learning process (no server setup required).
  - Eliminates complexity of configuring databases like PostgreSQL or MySQL.

## Key Concepts
- **JSON in APIs**:
  - APIs return responses in JavaScript Object Notation (JSON).
  - Supported natively by Python and most programming languages.
  - Example of a simple JSON response:
    ```python
    {
        "id": 1,
        "name": "Example",
        "status": "active"
    }
    ```
- **Flask Workflow**:
  - Handles HTTP requests and routes them to appropriate functions.
  - Example of a basic Flask route:
    ```python
    from flask import Flask

    app = Flask(__name__)

    @app.route('/api/hello', methods=['GET'])
    def hello():
        return {"message": "Hello, Flask API!"}, 200
    ```
- **Testing with Postman**:
  - Postman allows sending HTTP requests (GET, POST, etc.) to test API endpoints.
  - Verify responses, status codes, and data integrity.
- **SQLite Database**:
  - Lightweight, serverless database ideal for learning.
  - Use DB Browser for SQLite to inspect database contents.

## Course Objectives
- Build a functional RESTful API with Flask.
- Implement common API tasks: authentication, database operations, and routing.
- Use Flask plugins to streamline development.
- Test the API using Postman.
- Deploy the API to a production cloud server.

## Additional Notes
- The course is designed to minimize setup complexity, focusing on Flask and Python.
- Suitable for developers new to APIs but familiar/with basic web development concepts.
- Encourages hands-on learning with practical examples and tools.


# Demo Project Overview: Planetary API

## Inspiration and Context
- **Historical Background**:
  - Quote from Ovid’s *Metamorphosis*: *"Caelum certet patet ibimus ilac"* ("The sky is open before us, and that way we shall go").
  - Humanity’s fascination with the stars has driven innovations in navigation, timekeeping, and calendars (e.g., Stonehenge, Pyramids at Giza).
  - Ancient Greeks identified planets as "wanderers" due to their erratic movement compared to stars.
  - **Antikythera Mechanism**: An ancient Greek analog computer for predicting planetary positions, eclipses, and seasons.
- **Modern Astronomy**:
  - Tools like Hubble, Kepler Spacecraft, and TESS (Transiting Exoplanet Survey Satellite) enable frequent discovery of new planets.
  - Need for an application to track and manage planetary discoveries.

## Project Overview
- **Planetary API**:
  - A RESTful API inspired by the Antikythera Mechanism to store and manage planetary discoveries.
  - Focus: Backend API only, no user interface (UI).
  - Purpose: Provide functionality accessible to various platforms (web, mobile, desktop) without tying to a specific presentation layer.
- **Core Features**:
  - List all known planets.
  - Register new users.
  - Authenticate existing users.
  - Add new planetary discoveries to the database.
  - Update planetary information as new data becomes available.
  - Delete planets when necessary (e.g., reclassification, like Pluto).
- **Testing Environment**:
  - All interactions and testing will be performed using **Postman**.
  - No UI development, suitable for developers focused on backend logic.

## Technical Notes
- **API Design**:
  - The API will handle standard CRUD operations (Create, Read, Update, Delete) for planetary data.
  - Authentication ensures secure access to API endpoints.
  - Example of a basic API endpoint to list planets:
    ```python
    from flask import Flask, jsonify

    app = Flask(__name__)

    @app.route('/api/planets', methods=['GET'])
    def list_planets():
        planets = [
            {"id": 1, "name": "Kepler-452b", "status": "confirmed"},
            {"id": 2, "name": "TRAPPIST-1e", "status": "confirmed"}
        ]
        return jsonify(planets), 200
    ```
- **Postman Usage**:
  - Test endpoints by sending HTTP requests (e.g., GET `/api/planets`, POST `/api/planets`).
  - Verify response data, status codes, and authentication workflows.
- **Database**:
  - Likely using SQLite (based on previous context) for simplicity.
  - Store planetary data (e.g., name, discovery date, status) and user information (e.g., username, hashed password).

## Project Goals
- Build a functional API to manage planetary data.
- Ensure flexibility for integration with various client applications.
- Focus on backend logic and API testing without UI concerns.
- Provide a practical introduction to API development for developers.

