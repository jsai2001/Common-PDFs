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

### User Authentication and Email Setup

#### 1. **JWT Authentication Setup**
- **Purpose**: Enable user login by generating JSON Web Tokens (JWT) for authentication.
- **Library**: Use `flask_jwt_extended` for JWT management.
- **Steps**:
  - Import `JWTManager`, `jwt_required`, and `create_access_token` from `flask_jwt_extended`.
  - Configure `JWT_SECRET_KEY` in the Flask app for token signing.
  - Initialize `JWTManager` with the Flask app.
- **Security Note**: Use a secure string (e.g., UUID) for `JWT_SECRET_KEY` in production, not a simple string like "super-secret".

**Code Snippet for JWT Setup**:
```python
from flask_jwt_extended import JWTManager, jwt_required, create_access_token

app.config['JWT_SECRET_KEY'] = 'super-secret'  # Change this in production
jwt = JWTManager(app)
```

#### 2. **Login Route Implementation**
- **Purpose**: Allow users to log in via a `/login` endpoint, supporting both JSON and form data POST requests.
- **Route Details**:
  - Endpoint: `/login`, Method: POST (controversial but common for login).
  - Accepts: JSON (`request.is_json`) or form data (`request.form`).
  - Logic:
    - Extract `email` and `password` from request.
    - Query the `User` table for a matching record using `User.query.filter_by(email=email, password=password).first()`.
    - If a match is found, generate an `access_token` using `create_access_token(identity=email)`.
    - Return JSON with success message and token (200 OK) or error message (401 Unauthorized) if no match.
- **Testing**:
  - Test with Postman using:
    - Form data: `email=test@test.com`, `password=P@sswOrd` (success: 200, token returned).
    - Form data with incorrect password (failure: 401, "bad email or password").
    - JSON: `{"email": "test@test.com", "password": "P@sswOrd"}` (success: 200, token returned).
    - JSON with incorrect password (failure: 401).

**Code Snippet for Login Route**:
```python
from flask import request, jsonify
from models import User

@app.route('/login', methods=['POST'])
def login():
    if request.is_json:
        email = request.json['email']
        password = request.json['password']
    else:
        email = request.form['email']
        password = request.form['password']
    
    test = User.query.filter_by(email=email, password=password).first()
    
    if test:
        access_token = create_access_token(identity=email)
        return jsonify(message='login succeeded', access_token=access_token), 200
    else:
        return jsonify(message='bad email or password'), 401
```

#### 3. **Email Setup with Flask-Mail**
- **Purpose**: Enable password reset by sending emails to users.
- **Library**: Use `flask-mail` for email functionality.
- **Setup**:
  - Install `flask-mail` via PyCharm or pip.
  - Import `Mail` and `Message` from `flask_mail`.
  - Configure mail server settings in `app.config`:
    - `MAIL_SERVER`: Use `smtp.mailtrap.io` (Mailtrap for testing).
    - `MAIL_USERNAME` and `MAIL_PASSWORD`: Use credentials from Mailtrap.
  - Use environment variables (`os.environ`) for sensitive data in production.
- **Mailtrap**:
  - Sign up at `mailtrap.io` (free tier available).
  - Retrieve SMTP credentials (host, ports, username, password) from the Mailtrap dashboard.
  - Allows testing email sending without a real mail server.

**Code Snippet for Email Configuration**:
```python
from flask_mail import Mail, Message
import os

app.config['MAIL_SERVER'] = 'smtp.mailtrap.io'
app.config['MAIL_USERNAME'] = os.environ['MAIL_USERNAME']
app.config['MAIL_PASSWORD'] = os.environ['MAIL_PASSWORD']

mail = Mail(app)
```

---

### Summary
- **JWT Authentication**: Configures `flask_jwt_extended` to manage user login and token generation.
- **Login Route**: Handles POST requests with JSON or form data, validates credentials, and returns a JWT token or error.
- **Email Setup**: Uses `flask-mail` with Mailtrap for testing email functionality, with environment variables for secure configuration.
- **Next Steps**: Implement the password reset endpoint to send emails using the configured `Mail` instance.

**Instructions**:
- Ensure `JWT_SECRET_KEY` is secure in production.
- Store Mailtrap credentials in environment variables for security.
- Test endpoints with Postman to verify functionality.
- Extend the email setup to include a `/reset-password` endpoint for sending password reset emails.

---

### Emailing a Lost Password

#### 1. **Password Retrieval Endpoint Setup**
- **Purpose**: Allow users to retrieve their password by sending it via email using the `/retrieve_password/<email>` endpoint.
- **Route Details**:
  - Endpoint: `/retrieve_password/<string:email>`, Method: GET.
  - The email is passed as a URL parameter.
- **Logic**:
  - Query the `User` table to check if the provided email exists using `User.query.filter_by(email=email).first()`.
  - If a user is found:
    - Create a `Message` object with the password, sender (`admin@planetary-api.com`), and recipient (user's email).
    - Send the email using `mail.send(msg)`.
    - Return JSON with a success message: `"Password sent to <email>"` (200 OK).
  - If no user is found:
    - Return JSON with an error message: `"That email doesn't exist"` (401 Unauthorized).
- **Dependency**: Requires the `Mail` object to be initialized with the Flask app.

**Code Snippet for Mail Initialization**:
```python
from flask_mail import Mail, Message
import os

app.config['MAIL_SERVER'] = 'smtp.mailtrap.io'
app.config['MAIL_USERNAME'] = os.environ['MAIL_USERNAME']
app.config['MAIL_PASSWORD'] = os.environ['MAIL_PASSWORD']

mail = Mail(app)
```

**Code Snippet for Password Retrieval Endpoint**:
```python
from flask import jsonify
from models import User

@app.route('/retrieve_password/<string:email>', methods=['GET'])
def retrieve_password(email):
    user = User.query.filter_by(email=email).first()
    
    if user:
        msg = Message(
            'Your planetary API password is ' + user.password,
            sender='admin@planetary-api.com',
            recipients=[email]
        )
        mail.send(msg)
        return jsonify(message='Password sent to ' + email), 200
    else:
        return jsonify(message='That email doesn’t exist'), 401
```

#### 2. **Environment Variable Configuration**
- **Purpose**: Securely store Mailtrap credentials using environment variables.
- **Setup**:
  - Use `MAIL_USERNAME` and `MAIL_PASSWORD` from Mailtrap's credentials (found in the Mailtrap dashboard).
  - Configure in PyCharm's run configuration:
    - Open "Edit Configurations" in PyCharm.
    - Add `MAIL_USERNAME` and `MAIL_PASSWORD` in the environment variables editor.
  - Avoid hardcoding credentials in the code for production environments.
- **Alternative**: Set environment variables at the operating system level (OS-specific, not covered in the transcript).

#### 3. **Testing in Postman and Mailtrap**
- **Test Cases**:
  - **Success Case**:
    - URL: `http://localhost:5000/retrieve_password/test@test.com`
    - Method: GET
    - Response: `{"message": "Password sent to test@test.com"}` (200 OK)
    - Mailtrap: Email appears in the demo inbox with the password (`P@ssw0rd`).
  - **Failure Case**:
    - URL: `http://localhost:5000/retrieve_password/nonexistent@test.com`
    - Response: `{"message": "That email doesn’t exist"}` (401 Unauthorized)
- **Mailtrap Features**:
  - Inspect email headers, HTML, or text versions in the Mailtrap dashboard.
  - Useful for debugging email content and format.

---

### Summary
- **Password Retrieval**: The `/retrieve_password/<email>` endpoint checks for a valid user and sends their password via email using Flask-Mail.
- **Security**: Environment variables store sensitive Mailtrap credentials, configured in PyCharm for testing.
- **Testing**: Verified functionality with Postman (success and failure cases) and Mailtrap (email receipt).
- **Next Steps**: Enhance the endpoint to send a password reset link instead of the plain password for better security. Consider adding rate limiting to prevent abuse.

**Instructions**:
- Ensure the `Mail` object is initialized before using the endpoint.
- Verify Mailtrap credentials are correctly set in environment variables.
- Test the endpoint with both existing and non-existing email addresses.
- For production, replace the plain password email with a secure reset link workflow.