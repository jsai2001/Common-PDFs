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
    due_date = Column(DateTime)  # Using DateTime for proper date handling
    status = Column(String, default="pending")  # e.g., "pending", "in_progress", "completed"
    owner_id = Column(Integer, ForeignKey("users.id"), nullable=False)

    owner = relationship("User", back_populates="tasks")

# Database setup (for initial setup, you might move this later)
DATABASE_URL = "sqlite:///./taskmaster.db"  # Using SQLite for simplicity
engine = create_engine(DATABASE_URL)
Base.metadata.create_all(bind=engine)