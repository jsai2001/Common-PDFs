
# Creating a Flask Project in PyCharm & Simple API Example

## Creating a New Flask Project in PyCharm
- **PyCharm Setup**:
  - Requires **PyCharm Professional Edition** for Flask-specific tooling (not available in free Community Edition).
  - Alternative: Use any text editor and borrow project files from exercise files if Professional Edition is unavailable.
- **Steps to Create Project**:
  1. Open PyCharm and select **Create New Project**.
  2. Choose **Flask** from the project type list.
  3. Name the project (e.g., `planetary-api`).
  4. Set project location (default: PyCharm projects folder).
  5. Configure a **virtual environment**:
     - Best practice to isolate project dependencies.
     - Select **New environment** using **VirtualENV** (standard tool for Python virtual environments).
     - Alternative: **Conda** for scientific or mathematically intensive projects.
     - Location: Defaults to `venv` folder within project directory.
  6. Select **base interpreter** (Python version):
     - Default: Python 3.7 (versions as old as 3.4 are compatible).
     - Install newer Python versions from python.org if needed (quit PyCharm, run installer, restart).
  7. Templating language settings:
     - Not applicable for API projects (no HTML generation, only JSON output).
  8. Click **Create** to set up the virtual environment and project structure.
- **Project Generation**:
  - PyCharm automatically creates a basic Flask project with one file (`app.py`) and installs dependencies.
  - May take a few minutes to download packages.
- **Why Flask?**:
  - Simplicity: Minimal boilerplate compared to Java or C# (e.g., C# projects in Visual Studio generate thousands of files).
  - Course focuses on working primarily with a single file.

## Making a Super-Simple API Example
- **Overview**:
  - Create a functional API endpoint in minutes using Flask.
  - Endpoint: A URL processed by the application (e.g., `/super_simple`).
- **Initial Project File (`app.py`)**:
  - PyCharm generates a default "Hello World" Flask app.
  - Structure:
    ```python
    from flask import Flask

    app = Flask(__name__)

    @app.route('/')
    def hello_world():
        return 'Hello, World!'

    if __name__ == '__main__':
        app.run()
    ```
  - **Explanation**:
    - `from flask import Flask`: Imports Flask library.
    - `app = Flask(__name__)`: Creates Flask app instance, `__name__` uses script name (can be hardcoded).
    - `@app.route('/')`: Decorator defining the root URL endpoint.
    - `hello_world()`: Function returning "Hello, World!" for the root URL.
    - `if __name__ == '__main__'`: Entry point to run the app.
- **Creating a New Endpoint**:
  - Add a new route for `/super_simple`.
  - Code example:
    ```python
    @app.route('/super_simple')
    def super_simple():
        return 'Hello from the Planetary API.'
    ```
  - **Steps**:
    1. Add decorator `@app.route('/super_simple')`.
    2. Define function `super_simple()` returning a string.
    3. Ensure proper spacing (two blank lines between functions, enforced by PyCharm’s linter).
  - **PyCharm Linter**:
    - Warns about formatting issues (e.g., missing blank lines).
    - Helps avoid syntax errors and ensures PEP 8 compliance.
  - Save changes (PyCharm auto-saves, but manual save with Ctrl+S is a good habit).
- **Next Steps**:
  - Run the Flask app (covered in the next video).
  - Test endpoints using tools like Postman.

## Key Takeaways
- PyCharm Professional simplifies Flask project setup with virtual environment configuration.
- Flask’s minimal setup enables rapid API development.
- Python’s decorator syntax (`@app.route`) is used to define API endpoints.
- Proper spacing and formatting are critical in Python (enforced by PyCharm’s linter).
- The Planetary API project starts with a single file, keeping complexity low.

