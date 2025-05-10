
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

# Setting Up Flask Run Configuration & Testing with Postman

## Setting Up a Run Configuration
- **Flask Development Server**:
  - Flask includes a built-in development web server (not suitable for production due to fragility).
  - Convenient for local development and testing.
- **Running the Server**:
  - **Option 1: Terminal**:
    - Open Terminal in PyCharm (Bash on Mac, PowerShell on Windows).
    - Virtual environment is auto-activated in PyCharm (indicated by `(venv)` in prompt).
    - Command to run:
      ```bash
      python app.py
      ```
    - Starts server on `localhost:5000`.
    - Stop server with `Ctrl+C`.
  - **Option 2: Run Configuration in PyCharm**:
    - PyCharm auto-creates a default Run Configuration (e.g., `planetary-api`).
    - Create a custom Run Configuration:
      1. Click the dropdown next to the Run button, select **Edit Configurations**.
      2. Click `+` and choose **Flask_server**.
      3. Name it (e.g., `Run API Project`).
      4. Set **Target type** to **Script path** and select `app.py`.
      5. Click **Apply** and **OK**.
    - Run by selecting the configuration and clicking the green arrow.
    - Output: Server runs on `localhost:5000`, visible in PyCharm’s Run window.
- **Testing the Server**:
  - Access `http://localhost:5000/` in a browser to see `Hello, World!`.
  - Access `http://localhost:5000/super_simple` to see `Hello from the Planetary API.`.
  - Note: Red text in PyCharm’s dark theme is normal (not an error).
- **Stopping the Server**:
  - Use the **Stop** button in PyCharm’s Run window or toolbar.

## Testing with Postman
- **Why Postman?**:
  - Browser testing is limited to GET requests.
  - APIs use multiple HTTP verbs (GET, POST, PUT, DELETE) for CRUD operations (Create, Read, Update, Delete).
  - Postman is a robust tool for testing all HTTP verbs.
- **Using Postman**:
  - Launch Postman and dismiss the splash dialog.
  - Select **GET** from the verb dropdown.
  - Ensure the Flask server is running (start via PyCharm’s green arrow).
  - Enter URL (e.g., `http://localhost:5000/`) and click **Send**.
  - Verify response:
    - Body: `Hello, World!`
    - Status: `200 OK`
    - Additional info: Response time, data size.
  - Test the `/super_simple` endpoint:
    - URL: `http://localhost:5000/super_simple`
    - Response: `Hello from the Planetary API.`
- **Server Logs**:
  - PyCharm’s Run window shows request details:
    - HTTP verb, route, HTTP version (e.g., 1.1), status code (e.g., `200`).
  - Errors are visible in both Postman and PyCharm’s Run window.

## Restarting the Server
- **Manual Restart**:
  - Changes to `app.py` (e.g., updating `super_simple` response to `Hello from the Planetary API. Boo yah!`) require a server restart.
  - Example code change:
    ```python
    @app.route('/super_simple')
    def super_simple():
        return 'Hello from the Planetary API. Boo yah!'
    ```
  - Save changes (`Ctrl+S`), then restart:
    - Click the **Restart** button in PyCharm’s Run window.
    - Retest in Postman to confirm updated response.
- **Auto-Reloading with Flask**:
  - Flask’s development server (powered by **Werkzeug**) supports auto-reloading.
  - Enable by setting environment variables:
    1. Stop the server.
    2. Go to **Edit Configurations** in PyCharm.
    3. In the Run Configuration, set:
       - `FLASK_ENV=development` (default for development server).
       - `FLASK_DEBUG=True` (checkbox to enable debug mode).
    4. Click **Apply** and **OK**.
  - Effect: Server auto-reloads on code changes (lazy reloading).
  - Test:
    - Remove `Boo yah!` from `super_simple` function and save.
    - Server auto-restarts (visible in Run window).
    - Retest in Postman to confirm updated response (`Hello from the Planetary API.`).
- **Environment Variables**:
  - Used for configuration (e.g., database strings, API keys).
  - Set in PyCharm’s Run Configuration for easy management.

## Key Takeaways
- Flask’s development server is ideal for local testing but not production.
- PyCharm’s Run Configurations simplify server management compared to Terminal commands.
- Postman is essential for testing non-GET HTTP verbs and verifying API responses.
- Auto-reloading via `FLASK_DEBUG` improves development efficiency.
- Monitor server logs in PyCharm for request details and debugging.