from fastapi import FastAPI, Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer
from sqlalchemy.orm import Session
from sqlalchemy.exc import IntegrityError
from typing import List, Optional
import jwt
import sys, os
from datetime import datetime, timedelta
sys.path.append(os.path.dirname(os.path.abspath(__file__)))
import models
import schemas
import utils
from database import SessionLocal, engine

models.Base.metadata.create_all(bind=engine)  # Ensure tables are created

app = FastAPI()

# Dependency to get DB session
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="login")  # tokenUrl should match your login endpoint

# --- Utility functions ---
def get_current_user(token: str = Depends(oauth2_scheme), db: Session = Depends(get_db)):
    try:
        payload = jwt.decode(token, utils.SECRET_KEY, algorithms=[utils.ALGORITHM])
        user_id: int = payload.get("user_id")
        if user_id is None:
            raise utils.credentials_exception
        token_data = schemas.TokenData(id=user_id)
    except jwt.PyJWTError:
        raise utils.credentials_exception
    user = db.query(models.User).filter(models.User.id == token_data.id).first()
    if user is None:
        raise utils.credentials_exception
    return user

# --- Helper function ---
def format_task_due_date(task: models.Task):
    if task.due_date:
        task.due_date = task.due_date.isoformat()
    return task

# --- Endpoints ---

@app.post("/register", response_model=schemas.UserOut, status_code=status.HTTP_201_CREATED)
def register_user(user: schemas.UserCreate, db: Session = Depends(get_db)):
    hashed_password = utils.hash_password(user.password)
    db_user = models.User(username=user.username, email=user.email, hashed_password=hashed_password)
    try:
        db.add(db_user)
        db.commit()
        db.refresh(db_user)
    except IntegrityError:
        db.rollback()
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Username or email already exists")
    return db_user

@app.post("/login", response_model=schemas.Token)
def login_user(user_credentials: schemas.UserLogin, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(models.User.email == user_credentials.email).first()
    if not user or not utils.verify_password(user_credentials.password, user.hashed_password):
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail="Invalid credentials")
    access_token = utils.create_access_token(data={"user_id": user.id})
    return {"access_token": access_token, "token_type": "bearer"}

@app.post("/tasks/", response_model=schemas.TaskOut, status_code=status.HTTP_201_CREATED)
def create_task(task: schemas.TaskCreate, db: Session = Depends(get_db), current_user: models.User = Depends(get_current_user)):
    due_date = datetime.strptime(task.due_date, "%Y-%m-%d") if task.due_date else None
    db_task = models.Task(title=task.title, description=task.description, category=task.category, due_date=due_date, owner_id=current_user.id)
    db.add(db_task)
    db.commit()
    db.refresh(db_task)
    return format_task_due_date(db_task)

@app.get("/tasks/", response_model=List[schemas.TaskOut])
def read_tasks(db: Session = Depends(get_db), current_user: models.User = Depends(get_current_user)):
    tasks = db.query(models.Task).filter(models.Task.owner_id == current_user.id).all()
    return [format_task_due_date(task) for task in tasks]

@app.get("/tasks/{task_id}", response_model=schemas.TaskOut)
def read_task(task_id: int, db: Session = Depends(get_db), current_user: models.User = Depends(get_current_user)):
    task = db.query(models.Task).filter(models.Task.id == task_id, models.Task.owner_id == current_user.id).first()
    if not task:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Task not found")
    return format_task_due_date(task)

@app.put("/tasks/{task_id}", response_model=schemas.TaskOut)
def update_task(task_id: int, task: schemas.TaskUpdate, db: Session = Depends(get_db), current_user: models.User = Depends(get_current_user)):
    db_task = db.query(models.Task).filter(models.Task.id == task_id, models.Task.owner_id == current_user.id).first()
    if not db_task:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Task not found")
    if task.title:
        db_task.title = task.title
    if task.description:
        db_task.description = task.description
    if task.category:
        db_task.category = task.category
    if task.due_date:
        db_task.due_date = datetime.strptime(task.due_date, "%Y-%m-%d")
    if task.status:
        db_task.status = task.status
    db.commit()
    db.refresh(db_task)
    return format_task_due_date(db_task)

@app.delete("/tasks/{task_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_task(task_id: int, db: Session = Depends(get_db), current_user: models.User = Depends(get_current_user)):
    task = db.query(models.Task).filter(models.Task.id == task_id, models.Task.owner_id == current_user.id).first()
    if not task:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Task not found")
    db.delete(task)
    db.commit()
    return None

# At the bottom of main.py
if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)