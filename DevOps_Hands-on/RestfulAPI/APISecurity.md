# JSON Web Tokens and User Registration

## JSON Web Tokens (JWT)

- **Purpose**: Used for user authentication and authorization in APIs, particularly for tracking users and granting privileges.
- **Use Case in Planetary API**:
  - **Login Required**: Registering, editing, or deleting planets.
  - **No Login Required**: Listing planets, viewing planet details, and accessing the registration endpoint.
- **Why JWT?**:
  - Preferred for API projects over session-based plugins like Flask-Login or Flask-User.
  - Session management is avoided in APIs to keep them stateless.
  - JWT is an open standard for secure data exchange, widely supported across languages.
  - Suitable for web apps (e.g., React, Angular), mobile, or desktop apps.
- **Implementation**:
  - Use `Flask-JWT-extended` library in Python.
  - Install via PyCharm: Preferences > Project Interpreter > Add `Flask-JWT-extended`.
  - Protect routes using decorators (to be covered in later sections).
- **Resources**:
  - Learn more about JWT at [jwt.io](https://jwt.io).
  - Can be implemented as a "black box" without deep understanding of internals.

## Registering New Users

- **Objective**: Create a route to handle user registration in the Planetary API.
- **Route Details**:
  - Endpoint: `/register`
  - Method: POST
  - Input: HTML form data (email, first_name, last_name, password).
  - Future consideration: Support for pure JSON POST requests.
- **Logic**:
  - Check if the email already exists in the database.
  - If exists, return a 409 Conflict status with a message.
  - If not, create a new user and save to the database, return 201 Created status.
- **Code Snippet**:
```python
from flask import request, jsonify
from models import User
from app import app, db

@app.route('/register', methods=['POST'])
def register():
    email = request.form['email']
    test = User.query.filter_by(email=email).first()
    
    if test:
        return jsonify(message='That email already exists'), 409
    
    first_name = request.form['first_name']
    last_name = request.form['last_name']
    password = request.form['password']
    
    user = User(
        first_name=first_name,
        last_name=last_name,
        email=email,
        password=password
    )
    
    db.session.add(user)
    db.session.commit()
    
    return jsonify(message='User created successfully'), 201
```
- **Testing in Postman**:
  - **Successful Case**:
    - URL: `http://localhost:5000/register`
    - Method: POST
    - Form Data:
      - `email`: `foo@bar.com`
      - `first_name`: `Galileo`
      - `last_name`: `Galilei`
      - `password`: `test`
    - Response: `{"message": "User created successfully"}`, Status: 201 Created
  - **Failure Case (Existing Email)**:
    - Use an existing email (e.g., `test@test.com` from seed script).
    - Response: `{"message": "That email already exists"}`, Status: 409 Conflict
- **Notes**:
  - Simplistic approach for demo; real-world apps may need approval workflows.
  - Ensure form field names in the client match the API's expectations.
  - Uses SQLAlchemy for database operations (`User.query`, `db.session`).
  - Status codes:
    - 201: Resource created successfully.
    - 409: Conflict due to existing resource.

# Registering New Users with Flask

## Overview
- **Objective**: Implement a user registration system for the Planetary API using Flask and Flask-JWT.
- **Context**: Part of a user management system where registration is the first step.
- **Simplification**: No approval workflow; assumes all users are trustworthy (scientists).
- **Future Consideration**: Support for pure JSON POST requests will be covered later.

## Route Details
- **Endpoint**: `/register`
- **Method**: POST
- **Input**: Expects HTML form data with fields: `email`, `first_name`, `last_name`, `password`.
- **Logic**:
  - Validate if the email already exists in the database.
  - If email exists, return 409 Conflict with an error message.
  - If email is unique, create a new user, save to the database, and return 201 Created with a success message.

## Code Snippet
```python
from flask import request, jsonify
from models import User
from app import app, db

@app.route('/register', methods=['POST'])
def register():
    email = request.form['email']
    test = User.query.filter_by(email=email).first()
    
    if test:
        return jsonify(message='That email already exists'), 409
    
    first_name = request.form['first_name']
    last_name = request.form['last_name']
    password = request.form['password']
    
    user = User(
        first_name=first_name,
        last_name=last_name,
        email=email,
        password=password
    )
    
    db.session.add(user)
    db.session.commit()
    
    return jsonify(message='User created successfully'), 201
```

## Implementation Notes
- **Form Handling**:
  - Assumes the client sends HTML form data.
  - Client must name form fields exactly as expected (`email`, `first_name`, `last_name`, `password`).
- **Database Interaction**:
  - Uses SQLAlchemy for querying (`User.query.filter_by`) and saving (`db.session.add`, `db.session.commit`).
  - No deserialization (e.g., Marshmallow) needed for checking email existence.
- **Error Handling**:
  - Checks for existing email to prevent duplicates.
  - Returns appropriate HTTP status codes:
    - **201 Created**: Successful user creation.
    - **409 Conflict**: Email already exists.
- **Development Environment**:
  - Code written in PyCharm, which provides named parameter suggestions for cleaner code.
  - Emphasizes using named parameters for clarity.

## Testing in Postman
- **Setup**:
  - URL: `http://localhost:5000/register`
  - Method: POST
  - Body: Form-data with key-value pairs.
- **Test Case 1: Successful Registration**:
  - Form Data:
    - `email`: `foo@bar.com`
    - `first_name`: `Galileo`
    - `last_name`: `Galilei`
    - `password`: `test`
  - Response: `{"message": "User created successfully"}`, Status: 201 Created
- **Test Case 2: Existing Email**:
  - Form Data:
    - `email`: `test@test.com` (from seed script, e.g., William Herschel)
    - Other fields: Any values (irrelevant due to email conflict)
  - Response: `{"message": "That email already exists"}`, Status: 409 Conflict

## Additional Notes
- **Status Codes**:
  - 200-range indicates success (201 for resource creation).
  - 409 indicates a conflict (e.g., duplicate resource).
- **Testing Strategy**:
  - Verify both success and failure cases to ensure robust error handling.
  - Seed script data (e.g., `test@test.com`) used to simulate existing users.
- **Extensibility**:
  - Current implementation is minimal; real-world applications may add validation, password hashing, or approval workflows.