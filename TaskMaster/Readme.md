# TaskMaster API Documentation

TaskMaster is a RESTful API built using FastAPI and SQLAlchemy, designed to manage users and their tasks efficiently. It supports user authentication via JWT (JSON Web Tokens) and provides CRUD (Create, Read, Update, Delete) operations for tasks. The application uses SQLite as its database for simplicity, though it can be adapted to other databases like PostgreSQL. Below is a detailed breakdown of the project structure, components, and functionality.

---

## Project Overview

TaskMaster allows users to:
- Register and log in securely with email and password.
- Create, read, update, and delete tasks associated with their accounts.
- Manage task details such as title, description, category, due date, and status.

The project is structured across multiple files:
- **`main.py`**: The core FastAPI application file containing endpoints and logic.
- **`database.py`**: Database configuration and session management.
- **`models.py`**: SQLAlchemy ORM models for users and tasks.
- **`utils.py`**: Utility functions for password hashing and JWT token management.
- **`schemas.py`**: Pydantic models for request/response validation.

The database is SQLite (`taskmaster.db`), stored in the same directory as the application.

---

## Project Dependencies

- **FastAPI**: A modern, fast web framework for building APIs with Python.
- **SQLAlchemy**: An ORM (Object-Relational Mapping) tool for database interactions.
- **Passlib**: For secure password hashing using bcrypt.
- **PyJWT**: For generating and validating JWT tokens.
- **Pydantic**: For data validation and serialization.
- **Uvicorn**: An ASGI server to run the FastAPI application.

Install dependencies via:
```bash
pip install fastapi sqlalchemy passlib[bcrypt] pyjwt pydantic uvicorn
```

---

## File Breakdown and Detailed Explanation

### 1. `database.py` - Database Configuration

This file sets up the database connection using SQLAlchemy.

#### Code:
```python
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

SQLALCHEMY_DATABASE_URL = "sqlite:///./taskmaster.db"
engine = create_engine(
    SQLALCHEMY_DATABASE_URL, connect_args={"check_same_thread": False}
)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
```

#### Explanation:
- **`SQLALCHEMY_DATABASE_URL`**: Specifies the database URI. Here, SQLite is used with the database file `taskmaster.db` in the project root.
- **`create_engine`**: Creates a SQLAlchemy engine instance. The `connect_args={"check_same_thread": False}` is specific to SQLite to allow multi-threaded access (though not recommended for production).
- **`SessionLocal`**: A factory for creating database sessions, configured to bind to the engine.

#### Purpose:
This module ensures the application can connect to and interact with the database.

---

### 2. `models.py` - Database Models

This file defines the database schema using SQLAlchemy ORM.

#### Code:
```python
from sqlalchemy import create_engine, Column, Integer, String, ForeignKey, DateTime
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from datetime import datetime

Base = declarative_base()

class User(Base):
    __tablename__ = "users"
    id = Column(Integer, primary_key=True, index=True)
    username = Column(String, unique=True, index=True, nullable=False)
    email = Column(String, unique=True, index=True, nullable=False)
    hashed_password = Column(String, nullable=False)
    tasks = relationship("Task", back_populates="owner")

class Task(Base):
    __tablename__ = "tasks"
    id = Column(Integer, primary_key=True, index=True)
    title = Column(String, nullable=False)
    description = Column(String)
    category = Column(String)
    due_date = Column(DateTime)
    status = Column(String, default="pending")
    owner_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    owner = relationship("User", back_populates="tasks")

DATABASE_URL = "sqlite:///./taskmaster.db"
engine = create_engine(DATABASE_URL)
Base.metadata.create_all(bind=engine)
```

#### Explanation:
- **`Base`**: A declarative base class for defining models.
- **`User` Model**:
  - `id`: Primary key, auto-incremented.
  - `username`: Unique string, indexed for faster lookups.
  - `email`: Unique string, indexed.
  - `hashed_password`: Stores the hashed password (not plain text).
  - `tasks`: One-to-many relationship with the `Task` model.
- **`Task` Model**:
  - `id`: Primary key.
  - `title`: Required string.
  - `description`, `category`: Optional strings.
  - `due_date`: DateTime field for task deadlines.
  - `status`: String with default value "pending" (e.g., "pending", "in_progress", "completed").
  - `owner_id`: Foreign key linking to the `users` table.
  - `owner`: Many-to-one relationship with the `User` model.
- **`Base.metadata.create_all`**: Creates the tables in the database if they don’t exist.

#### Purpose:
Defines the structure of the `users` and `tasks` tables and their relationships.

---

### 3. `utils.py` - Utility Functions

This file contains helper functions for authentication.

#### Code:
```python
from passlib.context import CryptContext
import jwt
from datetime import datetime, timedelta
from fastapi import HTTPException, status

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")
SECRET_KEY = "your-secret-key"  # Replace with a strong, random secret
ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 30

credentials_exception = HTTPException(
    status_code=status.HTTP_401_UNAUTHORIZED,
    detail="Could not validate credentials",
    headers={"WWW-Authenticate": "Bearer"},
)

def hash_password(password: str):
    return pwd_context.hash(password)

def verify_password(plain_password: str, hashed_password: str):
    return pwd_context.verify(plain_password, hashed_password)

def create_access_token(data: dict):
    to_encode = data.copy()
    expire = datetime.utcnow() + timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES)
    to_encode_GENERAL.update({"exp": expire})
    encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
    return encoded_jwt
```

#### ⎯Explanation:
- **`pwd_context`**: Configures bcrypt for password hashing.
- **`SECRET_KEY`**: A secret key for JWT encoding (should be securely generated and stored in production).
- **`ALGORITHM`**: JWT algorithm (HS256).
- **`ACCESS_TOKEN_EXPIRE_MINUTES`**: Token validity duration (30 minutes).
- **`credentials_exception`**: Reusable exception for authentication failures.
- **`hash_password`**: Hashes a plain password using bcrypt.
- **`verify_password`**: Verifies a plain password against a hashed one.
- **`create_access_token`**: Generates a JWT token with an expiration time.

#### Purpose:
Handles password security and JWT-based authentication.

---

### 4. `schemas.py` - Data Validation Models

This file defines Pydantic models for request/response validation.

#### Code:
```python
from pydantic import BaseModel, EmailStr
from typing import Optional
from datetime import date

class UserCreate(BaseModel):
    username: str
    email: EmailStr
    password: str

class UserLogin(BaseModel):
    email: EmailStr
    password: str

class UserOut(BaseModel):
    id: int
    username: str
    email: EmailStr
    class Config:
        from_attributes = True

class TaskBase(BaseModel):
    title: str
    description: Optional[str] = None
    category: Optional[str] = None
    due_date: Optional[str] = None

class TaskCreate(TaskBase):
    pass

class TaskUpdate(TaskBase):
    status: Optional[str] = None

class TaskOut(TaskBase):
    id: int
    status: str
    owner_id: int
    class Config:
        from_attributes = True

class Token(BaseModel):
    access_token: str
    token_type: str

class TokenData(BaseModel):
    id: Optional[int] = None
```

#### Explanation:
- **`UserCreate`**: Schema for user registration (username, email, password).
- **`UserLogin`**: Schema for login (email, password).
- **`UserOut`**: Response schema for user data (excludes password).
- **`TaskBase`**: Base schema for tasks (title, description, category, due_date).
- **`TaskCreate`**: Inherits from `TaskBase` for task creation.
- **`TaskUpdate`**: Adds optional `status` for task updates.
- **`TaskOut`**: Response schema for tasks, including `id`, `status`, and `owner_id`.
- **`Token`**: Schema for JWT token response.
- **`TokenData`**: Internal schema for token payload.
- **`Config.from_attributes`**: Allows Pydantic to map SQLAlchemy objects to schemas.

#### Purpose:
Ensures data consistency and validation for API requests and responses.

---

### 5. `main.py` - FastAPI Application

This is the main file containing the API endpoints and logic.

#### Code Highlights:
```python
from fastapi import FastAPI, Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer
from sqlalchemy.orm import Session
from typing import List
import jwt
from datetime import datetime, timedelta
import models, schemas, utils
from database import SessionLocal, engine

app = FastAPI()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="login")

def get_current_user(token: str = Depends(oauth2_scheme), db: Session = Depends(get_db)):
    # JWT validation logic
    ...

@app.post("/register", response_model=schemas.UserOut)
def register_user(user: schemas.UserCreate, db: Session = Depends(get_db)):
    # User registration logic
    ...

@app.post("/login", response_model=schemas.Token)
def login_user(user_credentials: schemas.UserLogin, db: Session = Depends(get_db)):
    # User login logic
    ...

@app.post("/tasks/", response_model=schemas.TaskOut)
def create_task(task: schemas.TaskCreate, db: Session = Depends(get_db), current_user: models.User = Depends(get_current_user)):
    # Task creation logic
    ...

@app.get("/tasks/", response_model=List[schemas.TaskOut])
def read_tasks(db: Session = Depends(get_db), current_user: models.User = Depends(get_current_user)):
    # Fetch all tasks for the user
    ...

# Other endpoints: read_task, update_task, delete_task
```

#### Key Components:
- **`get_db`**: Dependency to provide a database session.
- **`oauth2_scheme`**: OAuth2 password bearer for token authentication.
- **`get_current_user`**: Validates JWT token and retrieves the current user.
- **`format_task_due_date`**: Helper function to serialize `due_date` as ISO format.

#### Endpoints:
1. **`POST /register`**:
   - Registers a new user.
   - Returns user details (excluding password).
   - Raises 400 if username/email already exists.
2. **`POST /login`**:
   - Authenticates a user and returns a JWT token.
   - Raises 403 for invalid credentials.
3. **`POST /tasks/`**:
   - Creates a new task for the authenticated user.
   - Requires JWT token.
4. **`GET /tasks/`**:
   - Retrieves all tasks for the authenticated user.
5. **`GET /tasks/{task_id}`**:
   - Fetches a specific task by ID.
   - Raises 404 if not found.
6. **`PUT /tasks/{task_id}`**:
   - Updates a task’s details.
   - Raises 404 if not found.
7. **`DELETE /tasks/{task_id}`**:
   - Deletes a task.
   - Returns 204 (No Content) on success.

#### Running the App:
```python
if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)
```
- Runs the app locally at `http://127.0.0.1:8000` with hot reload enabled.

---

## How to Use TaskMaster API

### Setup:
1. Ensure all dependencies are installed.
2. Place all files in the same directory.
3. Update `SECRET_KEY` in `utils.py` with a secure value.
4. Run the app:
   ```bash
   python main.py
   ```

### Example Requests:

#### 1. Register a User:
```bash
curl -X POST "http://127.0.0.1:8000/register" -H "Content-Type: application/json" -d '{"username": "john_doe", "email": "john@example.com", "password": "securepass123"}'
```
Response:
```json
{"id": 1, "username": "john_doe", "email": "john@example.com"}
```

#### 2. Login:
```bash
curl -X POST "http://127.0.0.1:8000/login" -H "Content-Type: application/json" -d '{"email": "john@example.com", "password": "securepass123"}'
```
Response:
```json
{"access_token": "<jwt-token>", "token_type": "bearer"}
```

#### 3. Create a Task:
```bash
curl -X POST "http://127.0.0.1:8000/tasks/" -H "Authorization: Bearer <jwt-token>" -H "Content-Type: application/json" -d '{"title": "Finish report", "due_date": "2025-04-10"}'
```
Response:
```json
{"id": 1, "title": "Finish report", "description": null, "category": null, "due_date": "2025-04-10T00:00:00", "status": "pending", "owner_id": 1}
```

#### 4. Get All Tasks:
```bash
curl -X GET "http://127.0.0.1:8000/tasks/" -H "Authorization: Bearer <jwt-token>"
```

---

## Security Considerations

- **JWT Secret**: Replace `SECRET_KEY` with a cryptographically secure value and store it in an environment variable.
- **Password Hashing**: Uses bcrypt, which is secure by default.
- **Database**: SQLite is used for simplicity; switch to PostgreSQL/MySQL for production with proper connection pooling.

---

## Future Improvements

- Add task filtering (e.g., by status or category).
- Implement user roles (e.g., admin vs. regular user).
- Add pagination for the `/tasks/` endpoint.
- Include refresh tokens for long-lived sessions.

---

This documentation provides a comprehensive overview of the TaskMaster API, its structure, and how to use it. Let me know if you need further clarification!