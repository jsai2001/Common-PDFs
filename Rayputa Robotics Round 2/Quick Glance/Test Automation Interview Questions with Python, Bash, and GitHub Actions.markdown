# Test Automation Interview Questions with Python, Bash, and GitHub Actions

## Conceptual Questions
1. What is test automation, and why is it important in modern software development?
2. Explain the difference between manual testing and automated testing.
3. What are the key components of a test automation framework?
4. How do Python, Bash, and GitHub Actions complement each other in a test automation pipeline?
5. What are the advantages of using Python for test automation?
6. How can Bash scripts be used effectively in test automation workflows?
7. What is the role of GitHub Actions in automating testing workflows?
8. Explain the difference between unit tests, integration tests, and end-to-end tests.
9. What are some common Python testing frameworks, and when would you use each (e.g., pytest, unittest, nose2)?
10. How do you ensure test automation scripts are maintainable and scalable?
11. What is the purpose of mocking in test automation, and how is it implemented in Python?
12. How do you handle flaky tests in an automated testing pipeline?
13. What are the benefits of running tests in a CI/CD pipeline using GitHub Actions?
14. Explain the concept of test coverage and how it can be measured in Python.
15. How do you decide which tests to automate and which to leave for manual testing?
16. What is the role of assertions in Python test automation?
17. How do you manage test data in automated testing?
18. What are some best practices for writing robust Bash scripts for test automation?
19. How do you secure sensitive data (e.g., API keys, credentials) in test automation scripts?
20. What are the limitations of using GitHub Actions for test automation?

## Technical Questions (Python)
21. How do you set up a Python project to use pytest as the testing framework?
22. Write a simple pytest test case to verify a function that calculates the square of a number.
23. How do you use fixtures in pytest to set up and tear down test environments?
24. Explain how to use the `unittest.mock` library to mock external API calls in Python.
25. How do you generate a test coverage report using pytest and coverage.py?
26. How do you parametrize tests in pytest to run the same test with different inputs?
27. Write a Python script to automate browser testing using Selenium WebDriver.
28. How do you handle asynchronous operations in Python test automation (e.g., using `asyncio`)?
29. How do you integrate Python tests with a GitHub Actions workflow?
30. Explain how to use the `pytest-xdist` plugin to run tests in parallel.
31. How do you write a Python test to validate a REST API response using the `requests` library?
32. How do you configure pytest to skip certain tests based on conditions?
33. How do you use Python’s `logging` module to log test execution details?
34. Write a Python test to validate file I/O operations (e.g., reading/writing a CSV file).
35. How do you use Python’s `doctest` module for testing code examples in documentation?

## Technical Questions (Bash)
36. Write a Bash script to check if a web server is running by sending an HTTP request.
37. How do you use Bash to automate the setup of a test environment (e.g., installing dependencies)?
38. Explain how to write a Bash script to validate the output of a command in a test automation pipeline.
39. How do you handle error conditions in a Bash script used for test automation?
40. Write a Bash script to clean up temporary files created during testing.
41. How do you integrate a Bash script into a GitHub Actions workflow?
42. How do you use Bash to parse JSON output from a command in a test script?
43. Write a Bash script to run a series of Python tests and exit with a non-zero code if any fail.
44. How do you use Bash to check for the presence of required tools before running tests?
45. How do you schedule a Bash script to run tests periodically in a GitHub Actions workflow?

## Technical Questions (GitHub Actions)
46. How do you configure a GitHub Actions workflow to run Python tests using pytest?
47. Explain how to use matrix builds in GitHub Actions to test across multiple Python versions.
48. How do you cache Python dependencies in a GitHub Actions workflow to speed up test execution?
49. Write a GitHub Actions workflow to run both Python and Bash test scripts on push events.
50. How do you configure a GitHub Actions workflow to upload test reports as artifacts?
51. How do you secure environment variables in a GitHub Actions workflow for test automation?
52. Explain how to trigger a test automation workflow manually or on a schedule in GitHub Actions.
53. How do you handle test failures in a GitHub Actions workflow (e.g., continue on error)?
54. How do you integrate a linter (e.g., flake8) into a GitHub Actions workflow for Python code?
55. How do you debug a failing test in a GitHub Actions workflow?

## Scenario-Based Questions
56. **Scenario**: Your Python tests are failing intermittently due to race conditions in an end-to-end test. How would you diagnose and fix this issue?
57. **Scenario**: A GitHub Actions workflow is taking too long to run tests, causing delays in feedback. How would you optimize the workflow?
58. **Scenario**: A Bash script in your test automation pipeline fails because a dependency is missing on some runners. How would you resolve this?
59. **Scenario**: Your team wants to ensure all pull requests pass automated tests before merging. How would you enforce this using GitHub Actions?
60. **Scenario**: A test automation script is exposing sensitive credentials in the logs. How would you mitigate this issue?
61. **Scenario**: Your Python tests require a database to run, but GitHub Actions runners don’t have one pre-installed. How would you set up a test database?
62. **Scenario**: A stakeholder requests a daily report of test results. How would you automate this using GitHub Actions and Python?
63. **Scenario**: Your test suite is producing flaky tests due to external API rate limits. How would you address this?
64. **Scenario**: A Bash script used for testing is failing on Windows runners in GitHub Actions. How would you make it cross-platform compatible?
65. **Scenario**: Your team is adopting a monorepo, and you need to run specific tests based on changes in certain directories. How would you configure this in GitHub Actions?
66. **Scenario**: A test automation pipeline is consuming too many GitHub Actions minutes. How would you analyze and reduce usage?
67. **Scenario**: Your Python tests need to run in a specific order, but pytest runs them randomly. How would you enforce test order?
68. **Scenario**: A new team member reports that a test automation workflow is not triggering on pull requests. How would you troubleshoot this?
69. **Scenario**: Your application requires a custom test environment with specific tools. How would you set this up in GitHub Actions?
70. **Scenario**: A test automation script is failing because of a version mismatch in Python dependencies. How would you ensure consistent environments?

## Example Artifacts

### Example Python Test with Pytest
<xaiArtifact artifact_id="04d9cba4-5096-4db4-9e3f-929cf4bc3cb9" artifact_version_id="489b6346-a4e3-4044-b328-a8344a356219" title="test_square.py" contentType="text/python">
def square(num):
    return num * num

def test_square():
    assert square(4) == 16
    assert square(-2) == 4
    assert square(0) == 0

<xaiArtifact/>

# Answers to Test Automation Interview Questions with Python, Bash, and GitHub Actions

## Conceptual Questions

## 1. **What is test automation, and why is it important in modern software development?**

   **Answer**: Test automation involves using software tools to execute tests, compare actual outcomes with expected results, and report results without manual intervention. It is crucial in modern software development because it:
   - Speeds up testing cycles, enabling faster releases.
   - Improves test coverage and reliability by reducing human error.
   - Supports continuous integration/continuous deployment (CI/CD) by ensuring consistent testing.
   - Saves time and resources for repetitive tasks, allowing testers to focus on exploratory testing.

## 2. **Explain the difference between manual testing and automated testing.**

   **Answer**:
   - **Manual Testing**: Involves human testers executing test cases without automation tools. It is suitable for exploratory, usability, or ad-hoc testing but is time-consuming and prone to errors.
   - **Automated Testing**: Uses scripts and tools to run tests automatically. It is ideal for repetitive, regression, and performance testing, offering speed and consistency but requiring initial setup and maintenance.

## 3. **What are the key components of a test automation framework?**

   **Answer**: A test automation framework typically includes:
   - **Test Scripts**: Code to execute tests.
   - **Test Data**: Input data for tests.
   - **Test Libraries**: Reusable functions or modules (e.g., pytest, Selenium).
   - **Reporting Tools**: Tools to generate test results (e.g., pytest-html).
   - **Configuration Management**: Settings for environments and parameters.
   - **CI/CD Integration**: Tools like GitHub Actions to run tests automatically.
   - **Driver/Runner**: Executes tests (e.g., pytest runner).

## 4. **How do Python, Bash, and GitHub Actions complement each other in a test automation pipeline?**

   **Answer**:
   - **Python**: Provides robust testing frameworks (e.g., pytest, unittest) for writing and executing test scripts, handling complex logic, and integrating with APIs or databases.
   - **Bash**: Useful for automating environment setup, running CLI commands, and scripting simple tasks like file manipulation or dependency installation.
   - **GitHub Actions**: Orchestrates the pipeline by triggering tests on code changes, managing runners, and integrating with Python and Bash scripts for a seamless CI/CD workflow.

## 5. **What are the advantages of using Python for test automation?**

   **Answer**:
   - **Rich Ecosystem**: Libraries like pytest, Selenium, and requests simplify testing.
   - **Readability**: Clear syntax improves script maintainability.
   - **Cross-Platform**: Runs on various operating systems.
   - **Community Support**: Extensive documentation and community resources.
   - **Flexibility**: Supports unit, integration, and end-to-end testing.

## 6. **How can Bash scripts be used effectively in test automation workflows?**

   **Answer**: Bash scripts are effective for:
   - Setting up test environments (e.g., installing dependencies, configuring tools).
   - Running CLI-based tests or tools.
   - Cleaning up resources post-test (e.g., deleting temporary files).
   - Validating system states (e.g., checking service status).
   - Automating repetitive tasks in CI/CD pipelines.

## 7. **What is the role of GitHub Actions in automating testing workflows?**

   **Answer**: GitHub Actions automates testing by:
   - Triggering workflows on events (e.g., push, pull request).
   - Running tests in isolated, customizable environments (runners).
   - Integrating with testing tools (e.g., pytest, Bash scripts).
   - Providing artifacts for test reports.
   - Supporting parallel execution and caching to optimize performance.

## 8. **Explain the difference between unit tests, integration tests, and end-to-end tests.**

   **Answer**:
   - **Unit Tests**: Test individual components or functions in isolation (e.g., a single function). Fast and focused.
   - **Integration Tests**: Test interactions between components or modules (e.g., API calls, database queries). Ensure systems work together.
   - **End-to-End Tests**: Test the entire application flow from start to finish (e.g., user journey in a web app). Comprehensive but slower.

## 9. **What are some common Python testing frameworks, and when would you use each (e.g., pytest, unittest, nose2)?**

   **Answer**:
   - **pytest**: Feature-rich, flexible, and widely used. Ideal for unit, integration, and functional testing due to its simplicity, fixtures, and plugins.
   - **unittest**: Built into Python’s standard library. Suitable for basic unit testing but less flexible than pytest.
   - **nose2**: Extends unittest with additional features. Used for legacy projects or when unittest compatibility is needed, but less popular than pytest.

## 10. **How do you ensure test automation scripts are maintainable and scalable?**

    **Answer**:
    - Use modular design with reusable functions or classes.
    - Follow naming conventions for clarity (e.g., `test_function_name`).
    - Organize tests into directories by feature or type.
    - Use fixtures for setup/teardown to avoid code duplication.
    - Document scripts and maintain version control.
    - Parameterize tests to handle multiple scenarios.
    - Regularly refactor and remove obsolete tests.

## 11. **What is the purpose of mocking in test automation, and how is it implemented in Python?**

    **Answer**: Mocking replaces real objects (e.g., APIs, databases) with simulated ones to isolate tests and control behavior. It ensures tests are independent of external systems. In Python, the `unittest.mock` library is used.

    ```python
    from unittest.mock import patch
    import requests

    def get_data():
        response = requests.get("https://api.example.com/data")
        return response.json()

    def test_get_data():
        with patch("requests.get") as mocked_get:
            mocked_get.return_value.json.return_value = {"key": "value"}
            result = get_data()
            assert result == {"key": "value"}
    ```

## 12. **How do you handle flaky tests in an automated testing pipeline?**

    **Answer**:
    - **Identify Cause**: Analyze logs to find race conditions, network issues, or test dependencies.
    - **Isolate Tests**: Ensure tests are independent and don’t share state.
    - **Add Retries**: Use plugins like `pytest-rerunfailures` to retry flaky tests.
    - **Increase Timeouts**: Allow more time for network-dependent operations.
    - **Mock External Systems**: Replace unreliable dependencies with mocks.
    - **Improve Test Data**: Use consistent, controlled test data.

## 13. **What are the benefits of running tests in a CI/CD pipeline using GitHub Actions?**

    **Answer**:
    - Ensures code quality by running tests on every commit or pull request.
    - Provides immediate feedback to developers.
    - Supports parallel execution for faster results.
    - Integrates with version control for traceability.
    - Automates deployment after successful tests.
    - Generates artifacts for test reports.

## 14. **Explain the concept of test coverage and how it can be measured in Python.**

    **Answer**: Test coverage measures the percentage of code executed by tests. It helps identify untested areas but doesn’t guarantee test quality. In Python, `coverage.py` with pytest measures coverage.

    ```bash
    pip install pytest pytest-cov
    pytest --cov=./ --cov-report=html
    ```

    This generates an HTML report showing line-by-line coverage.

## 15. **How do you decide which tests to automate and which to leave for manual testing?**

    **Answer**:
    - **Automate**:
      - Repetitive tests (e.g., regression tests).
      - Tests requiring high accuracy (e.g., calculations).
      - Tests run frequently in CI/CD.
      - Performance or load tests.
    - **Manual**:
      - Exploratory testing.
      - Usability or UI/UX testing.
      - One-off or ad-hoc tests.
      - Tests with complex setup not worth automating.

## 16. **What is the role of assertions in Python test automation?**

    **Answer**: Assertions verify that the actual output matches the expected output. They are the core of test validation, causing tests to fail if conditions are not met. In Python, `assert` statements or framework-specific assertions (e.g., pytest’s `assert`) are used.

    ```python
    def test_addition():
        assert 1 + 1 == 2, "Addition failed"
    ```

## 17. **How do you manage test data in automated testing?**

    **Answer**:
    - Use fixtures to provide consistent test data.
    - Store test data in separate files (e.g., JSON, CSV) or databases.
    - Generate synthetic data dynamically using libraries like `faker`.
    - Clean up test data after execution to avoid conflicts.
    - Use environment variables for configuration-specific data.

## 18. **What are some best practices for writing robust Bash scripts for test automation?**

    **Answer**:
    - Use `set -e` to exit on errors.
    - Validate inputs and check for required tools.
    - Use functions for modularity.
    - Log outputs for debugging.
    - Handle errors with meaningful messages.
    - Make scripts idempotent to avoid side effects.

    ```bash
    #!/bin/bash
    set -e
    log() { echo "[INFO] $1"; }
    check_tool() {
        if ! command -v "$1" &> /dev/null; then
            log "$1 not found. Exiting..."
            exit 1
        fi
    }
    check_tool curl
    log "Setup complete."
    ```

## 19. **How do you secure sensitive data (e.g., API keys, credentials) in test automation scripts?**

    **Answer**:
    - Store sensitive data in GitHub Actions secrets or environment variables.
    - Use secret management tools (e.g., AWS Secrets Manager).
    - Avoid hardcoding credentials in scripts.
    - Restrict access to secrets using repository permissions.
    - Use encrypted files for local development.

## 20. **What are the limitations of using GitHub Actions for test automation?**

    **Answer**:
    - Limited free minutes for public/private repositories.
    - Execution time limits per job (6 hours max).
    - Limited storage for artifacts.
    - Dependency on GitHub infrastructure (outages affect pipelines).
    - Less flexibility for complex orchestration compared to Jenkins.
    - Resource constraints on GitHub-hosted runners.

## Technical Questions (Python)

## 21. **How do you set up a Python project to use pytest as the testing framework?**

    **Answer**:
    - Install pytest: `pip install pytest`.
    - Create a `tests/` directory for test files.
    - Name test files with `test_` prefix (e.g., `test_example.py`).
    - Write test functions with `test_` prefix.
    - Run tests with `pytest` command.

    ```bash
    mkdir tests
    pip install pytest
    ```

## 22. **Write a simple pytest test case to verify a function that calculates the square of a number.**

    ```python
    # src/math_ops.py
    def square(num):
        return num * num

    # tests/test_math_ops.py
    from src.math_ops import square

    def test_square():
        assert square(4) == 16
        assert square(-2) == 4
        assert square(0) == 0
    ```

## 23. **How do you use fixtures in pytest to set up and tear down test environments?**

    **Answer**: Fixtures are functions that provide reusable setup/teardown logic. Defined with `@pytest.fixture`, they can be passed as arguments to tests.

    ```python
    import pytest

    @pytest.fixture
    def temp_file():
        with open("test.txt", "w") as f:
            f.write("test data")
        yield "test.txt"
        import os
        os.remove("test.txt")

    def test_read_file(temp_file):
        with open(temp_file) as f:
            assert f.read() == "test data"
    ```

## 24. **Explain how to use the `unittest.mock` library to mock external API calls in Python.**

    **Answer**: The `unittest.mock` library creates mock objects to simulate external dependencies. Use `patch` to replace functions or objects during testing.

    ```python
    from unittest.mock import patch
    import requests

    def get_data():
        response = requests.get("https://api.example.com/data")
        return response.json()

    def test_get_data():
        with patch("requests.get") as mocked_get:
            mocked_get.return_value.json.return_value = {"key": "value"}
            result = get_data()
            assert result == {"key": "value"}
            mocked_get.assert_called_with("https://api.example.com/data")
    ```

## 25. **How do you generate a test coverage report using pytest and coverage.py?**

    **Answer**:
    - Install `pytest-cov`: `pip install pytest-cov`.
    - Run tests with coverage: `pytest --cov=./ --cov-report=html`.
    - View the HTML report in `htmlcov/index.html`.

    ```bash
    pip install pytest pytest-cov
    pytest --cov=./ --cov-report=html
    ```

## 26. **How do you parametrize tests in pytest to run the same test with different inputs?**

    **Answer**: Use `@pytest.mark.parametrize` to run a test with multiple inputs.

    ```python
    import pytest

    @pytest.mark.parametrize("input,expected", [(2, 4), (-3, 9), (0, 0)])
    def test_square(input, expected):
        assert input * input == expected
    ```

## 27. **Write a Python script to automate browser testing using Selenium WebDriver.**

    ```python
    from selenium import webdriver
    from selenium.webdriver.common.by import By
    import pytest

    @pytest.fixture
    def browser():
        driver = webdriver.Chrome()
        yield driver
        driver.quit()

    def test_google_search(browser):
        browser.get("https://www.google.com")
        search_box = browser.find_element(By.NAME, "q")
        search_box.send_keys("test automation")
        search_box.submit()
        assert "test automation" in browser.title
    ```

## 28. **How do you handle asynchronous operations in Python test automation (e.g., using `asyncio`)?**

    **Answer**: Use `pytest-asyncio` to test asynchronous code. Mark tests with `@pytest.mark.asyncio`.

    ```python
    import pytest
    import asyncio

    async def async_function():
        await asyncio.sleep(1)
        return "done"

    @pytest.mark.asyncio
    async def test_async_function():
        result = await async_function()
        assert result == "done"
    ```

    Install: `pip install pytest-asyncio`.

## 29. **How do you integrate Python tests with a GitHub Actions workflow?**

    **Answer**: Create a workflow file to run pytest on push/pull requests, set up Python, and install dependencies.

    ```yaml
    name: Python Tests
    on:
      push:
        branches: [main]
      pull_request:
        branches: [main]
    jobs:
      test:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - name: Set up Python
          uses: actions/setup-python@v4
          with:
            python-version: '3.10'
        - name: Install dependencies
          run: pip install pytest
        - name: Run tests
          run: pytest
    ```

## 30. **Explain how to use the `pytest-xdist` plugin to run tests in parallel.**

    **Answer**:
    - Install `pytest-xdist`: `pip install pytest-xdist`.
    - Run tests in parallel: `pytest -n auto` (auto-detects CPU cores).
    - Ensure tests are independent to avoid conflicts.

    ```bash
    pip install pytest-xdist
    pytest -n auto
    ```

## 31. **How do you write a Python test to validate a REST API response using the `requests` library?**

    ```python
    import requests
    import pytest

    def test_api_response():
        response = requests.get("https://api.example.com/data")
        assert response.status_code == 200
        assert response.json()["key"] == "value"
    ```

## 32. **How do you configure pytest to skip certain tests based on conditions?**

    **Answer**: Use `@pytest.mark.skipif` or `pytest.skip`.

    ```python
    import pytest
    import sys

    @pytest.mark.skipif(sys.platform == "win32", reason="Not supported on Windows")
    def test_unix_only():
        assert True

    def test_conditional_skip():
        if some_condition:
            pytest.skip("Condition not met")
        assert True
    ```

## 33. **How do you use Python’s `logging` module to log test execution details?**

    ```python
    import logging
    import pytest

    logging.basicConfig(level=logging.INFO)
    logger = logging.getLogger(__name__)

    def test_with_logging():
        logger.info("Starting test")
        assert 1 + 1 == 2
        logger.info("Test completed")
    ```

## 34. **Write a Python test to validate file I/O operations (e.g., reading/writing a CSV file).**

    ```python
    import pytest
    import csv
    import os

    def test_csv_read_write():
        # Write to CSV
        with open("test.csv", "w", newline="") as f:
            writer = csv.writer(f)
            writer.writerow(["name", "age"])
            writer.writerow(["Alice", 30])

        # Read and validate
        with open("test.csv", "r") as f:
            reader = csv.reader(f)
            rows = list(reader)
            assert rows[0] == ["name", "age"]
            assert rows[1] == ["Alice", "30"]

        # Cleanup
        os.remove("test.csv")
    ```

## 35. **How do you use Python’s `doctest` module for testing code examples in documentation?**

    ```python
    def square(num):
        """
        Calculate the square of a number.

        >>> square(4)
        16
        >>> square(-2)
        4
        """
        return num * num

    if __name__ == "__main__":
        import doctest
        doctest.testmod()
    ```

    Run: `python -m doctest file.py -v`.



# Answers to Test Automation Interview Questions with Python, Bash, and GitHub Actions

# Technical Questions (Bash)

## 36. Write a Bash script to check if a web server is running by sending an HTTP request.

**Answer**: A Bash script can use `curl` to send an HTTP request and check the response status. If the server returns a 200 status code, it is considered running.

```bash
#!/bin/bash
URL="http://example.com"
if curl --output /dev/null --silent --head --fail "$URL"; then
  echo "Web server is running."
  exit 0
else
  echo "Web server is not running."
  exit 1
fi
```

## 37. How do you use Bash to automate the setup of a test environment (e.g., installing dependencies)?

**Answer**: Bash scripts can automate environment setup by installing dependencies, configuring tools, and verifying installations. Use package managers like `apt`, `yum`, or `pip`, and ensure idempotency with checks.

```bash
#!/bin/bash
set -e
echo "Updating package lists..."
sudo apt-get update
echo "Installing Python and pip..."
sudo apt-get install -y python3 python3-pip
echo "Installing pytest..."
pip3 install pytest
echo "Verifying installations..."
python3 --version
pytest --version
```

## 38. Explain how to write a Bash script to validate the output of a command in a test automation pipeline.

**Answer**: To validate command output, capture the output using command substitution (`$()`), then use conditionals to check for expected values. Use `grep`, `awk`, or string comparison for validation.

```bash
#!/bin/bash
OUTPUT=$(python3 -c "print('Hello, World!')")
EXPECTED="Hello, World!"
if [ "$OUTPUT" = "$EXPECTED" ]; then
  echo "Output is correct."
  exit 0
else
  echo "Output is incorrect. Expected: $EXPECTED, Got: $OUTPUT"
  exit 1
fi
```

## 39. How do you handle error conditions in a Bash script used for test automation?

**Answer**: Handle errors using:
- `set -e`: Exit on any error.
- `trap`: Catch errors and perform cleanup.
- Exit codes: Check `$?` to handle command failures.
- Conditional checks: Use `if` to validate conditions before proceeding.

```bash
#!/bin/bash
set -e
trap 'echo "Error occurred on line $LINENO"; exit 1' ERR
command_that_might_fail() {
  return 1
}
if ! command_that_might_fail; then
  echo "Command failed, handling error..."
  exit 1
fi
```

## 40. Write a Bash script to clean up temporary files created during testing.

**Answer**: A cleanup script identifies and deletes temporary files or directories, using patterns or specific paths. Use `find` or `rm` with caution to avoid deleting critical files.

```bash
#!/bin/bash
TEMP_DIR="./test_temp"
echo "Cleaning up temporary files in $TEMP_DIR..."
if [ -d "$TEMP_DIR" ]; then
  rm -rf "$TEMP_DIR"/*
  echo "Cleanup complete."
else
  echo "No temporary directory found."
fi
```

## 41. How do you integrate a Bash script into a GitHub Actions workflow?

**Answer**: Integrate a Bash script by:
- Storing the script in the repository (e.g., `scripts/test.sh`).
- Making it executable (`chmod +x`).
- Calling it in a workflow step using `run: bash scripts/test.sh`.

```yaml
name: Run Bash Script
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run Bash test script
      run: bash scripts/test.sh
```

## 42. How do you use Bash to parse JSON output from a command in a test script?

**Answer**: Use `jq` to parse JSON output in Bash. Install `jq`, capture the command output, and extract fields using `jq` filters.

```bash
#!/bin/bash
JSON_OUTPUT='{"name": "test", "status": "success"}'
if ! command -v jq &> /dev/null; then
  echo "jq is not installed. Installing..."
  sudo apt-get install -y jq
fi
STATUS=$(echo "$JSON_OUTPUT" | jq -r '.status')
if [ "$STATUS" = "success" ]; then
  echo "Test passed."
else
  echo "Test failed."
  exit 1
fi
```

## 43. Write a Bash script to run a series of Python tests and exit with a non-zero code if any fail.

**Answer**: The script runs multiple Python test files using `pytest` and checks exit codes. If any test fails, the script exits with a non-zero code.

```bash
#!/bin/bash
set -e
TESTS=("test_one.py" "test_two.py")
for TEST in "${TESTS[@]}"; do
  echo "Running $TEST..."
  pytest "$TEST" || {
    echo "Test $TEST failed."
    exit 1
  }
done
echo "All tests passed."
```

## 44. How do you use Bash to check for the presence of required tools before running tests?

**Answer**: Use `command -v` to check if tools are installed. Exit with an error if any are missing.

```bash
#!/bin/bash
TOOLS=("python3" "pytest" "curl")
for TOOL in "${TOOLS[@]}"; do
  if ! command -v "$TOOL" &> /dev/null; then
    echo "$TOOL is not installed. Please install it."
    exit 1
  fi
done
echo "All required tools are installed."
```

## 45. How do you schedule a Bash script to run tests periodically in a GitHub Actions workflow?

**Answer**: Use the `schedule` event with a cron expression in the workflow. Call the Bash script in a step.

```yaml
name: Scheduled Tests
on:
  schedule:
    - cron: '0 0 * * *' # Runs daily at midnight UTC
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run scheduled tests
      run: bash scripts/run_tests.sh
```

# Technical Questions (GitHub Actions)

## 46. How do you configure a GitHub Actions workflow to run Python tests using pytest?

**Answer**: Configure a workflow to:
- Use `actions/checkout` to access the code.
- Set up Python with `actions/setup-python`.
- Install dependencies and run `pytest`.

```yaml
name: Python Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install dependencies
      run: pip install pytest
    - name: Run tests
      run: pytest
```

## 47. Explain how to use matrix builds in GitHub Actions to test across multiple Python versions.

**Answer**: Use the `strategy.matrix` configuration to define multiple Python versions. The workflow runs tests for each version in parallel.

```yaml
name: Matrix Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        python-version: ['3.8', '3.9', '3.10']
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python ${{ matrix.python-version }}
      uses: actions/setup-python@v4
      with:
        python-version: ${{ matrix.python-version }}
    - name: Install dependencies
      run: pip install pytest
    - name: Run tests
      run: pytest
```

## 48. How do you cache Python dependencies in a GitHub Actions workflow to speed up test execution?

**Answer**: Use `actions/cache` to cache the `pip` cache directory, keyed by the requirements file hash.

```yaml
name: Cached Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Cache pip dependencies
      uses: actions/cache@v3
      with:
        path: ~/.cache/pip
        key: ${{ runner.os }}-pip-${{ hashFiles('requirements.txt') }}
    - name: Install dependencies
      run: pip install -r requirements.txt
    - name: Run tests
      run: pytest
```

## 49. Write a GitHub Actions workflow to run both Python and Bash test scripts on push events.

**Answer**: The workflow runs Python tests with `pytest` and a Bash test script.

```yaml
name: Python and Bash Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install Python dependencies
      run: pip install pytest
    - name: Run Python tests
      run: pytest
    - name: Run Bash tests
      run: bash scripts/test.sh
```

## 50. How do you configure a GitHub Actions workflow to upload test reports as artifacts?

**Answer**: Use `actions/upload-artifact` to upload test reports (e.g., coverage reports) after running tests.

```yaml
name: Upload Test Reports
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install dependencies
      run: pip install pytest pytest-cov
    - name: Run tests with coverage
      run: pytest --cov=./ --cov-report=xml
    - name: Upload coverage report
      uses: actions/upload-artifact@v3
      with:
        name: coverage-report
        path: coverage.xml
```

## 51. How do you secure environment variables in a GitHub Actions workflow for test automation?

**Answer**: Store sensitive data (e.g., API keys) in GitHub Secrets and reference them in the workflow using `${{ secrets.SECRET_NAME }}`. Avoid hardcoding sensitive values.

```yaml
name: Secure Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    env:
      API_KEY: ${{ secrets.API_KEY }}
    steps:
    - uses: actions/checkout@v3
    - name: Run tests with secure env
      run: python tests/test_with_api.py
```

## 52. Explain how to trigger a test automation workflow manually or on a schedule in GitHub Actions.

**Answer**: Use `workflow_dispatch` for manual triggers and `schedule` with a cron expression for scheduled runs.

```yaml
name: Manual and Scheduled Tests
on:
  workflow_dispatch:
  schedule:
    - cron: '0 0 * * *' # Daily at midnight UTC
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run tests
      run: pytest
```

## 53. How do you handle test failures in a GitHub Actions workflow (e.g., continue on error)?

**Answer**: Use `continue-on-error: true` for steps that should not fail the workflow. Use `if: failure()` to run steps after a failure.

```yaml
name: Handle Test Failures
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run tests
      continue-on-error: true
      run: pytest
    - name: Log failure
      if: failure()
      run: echo "Tests failed, logging details..."
```

## 54. How do you integrate a linter (e.g., flake8) into a GitHub Actions workflow for Python code?

**Answer**: Install `flake8` and run it as a step. Fail the workflow if linting errors are found.

```yaml
name: Lint Python Code
on: [push]
jobs:
  lint:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install flake8
      run: pip install flake8
    - name: Run flake8
      run: flake8 .
```

## 55. How do you debug a failing test in a GitHub Actions workflow?

**Answer**: To debug:
- Enable debug logging (`ACTIONS_STEP_DEBUG: true` in secrets).
- Add verbose output to steps (e.g., `pytest -v`).
- Use `actions/upload-artifact` to upload logs or test outputs.
- Run the workflow locally with `act` for faster iteration.

```yaml
name: Debug Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Run tests with verbose output
      run: pytest -v
    - name: Upload test logs
      if: failure()
      uses: actions/upload-artifact@v3
      with:
        name: test-logs
        path: test_output.log
```

# Scenario-Based Questions

## 56. Scenario: Your Python tests are failing intermittently due to race conditions in an end-to-end test. How would you diagnose and fix this issue?

**Answer**: To diagnose:
- **Reproduce Locally**: Run tests locally with the same setup.
- **Add Logging**: Instrument tests with detailed logs to identify timing issues.
- **Use Debug Tools**: Use `pytest --pdb` to pause on failures.
To fix:
- **Add Synchronization**: Use locks or waits (e.g., `time.sleep`, Selenium’s `WebDriverWait`).
- **Mock Dependencies**: Mock external services to eliminate timing issues.
- **Refactor Tests**: Split tests to isolate components.

```python
import time
def test_end_to_end():
    # Simulate synchronization
    time.sleep(1)  # Wait for resource to be ready
    assert some_condition()
```

## 57. Scenario: A GitHub Actions workflow is taking too long to run tests, causing delays in feedback. How would you optimize the workflow?

**Answer**: Optimize by:
- **Caching Dependencies**: Cache `pip` or `npm` dependencies.
- **Parallelizing Jobs**: Use matrix builds or split tests.
- **Selective Testing**: Run only affected tests using path filters.
- **Faster Runners**: Use self-hosted or premium runners.
- **Pre-built Images**: Use Docker images with pre-installed tools.

```yaml
name: Optimized Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Cache pip dependencies
      uses: actions/cache@v3
      with:
        path: ~/.cache/pip
        key: ${{ runner.os }}-pip-${{ hashFiles('requirements.txt') }}
    - name: Run tests
      run: pytest
```

## 58. Scenario: A Bash script in your test automation pipeline fails because a dependency is missing on some runners. How would you resolve this?

**Answer**: Resolve by:
- **Install Dependencies**: Add a step to install the missing dependency.
- **Use Docker**: Run the script in a container with all dependencies.
- **Check Dependencies**: Add a pre-check for required tools.

```yaml
name: Robust Bash Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Install jq
      run: sudo apt-get install -y jq
    - name: Run Bash script
      run: bash scripts/test.sh
```

## 59. Scenario: Your team wants to ensure all pull requests pass automated tests before merging. How would you enforce this using GitHub Actions?

**Answer**: Use branch protection rules:
- Enable “Require status checks to pass before merging” in repository settings.
- Specify the test workflow’s job name as a required check.
- Configure the workflow to run on pull requests.

```yaml
name: PR Tests
on: [pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run tests
      run: pytest
```

## 60. Scenario: A test automation script is exposing sensitive credentials in the logs. How would you mitigate this issue?

**Answer**: Mitigate by:
- **Use Secrets**: Store credentials in GitHub Secrets.
- **Mask Logs**: Ensure secrets are not logged by using environment variables.
- **Audit Scripts**: Review scripts to avoid printing sensitive data.
- **Use Encrypted Files**: Store credentials in encrypted files.

```yaml
name: Secure Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    env:
      DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
    steps:
    - uses: actions/checkout@v3
    - name: Run tests
      run: python tests/test_db.py
```

## 61. Scenario: Your Python tests require a database to run, but GitHub Actions runners don’t have one pre-installed. How would you set up a test database?

**Answer**: Use a service container (e.g., PostgreSQL) in the workflow. Configure the test script to connect to the container.

```yaml
name: Database Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:latest
        env:
          POSTGRES_USER: test
          POSTGRES_PASSWORD: test
          POSTGRES_DB: test_db
        ports:
          - 5432:5432
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install dependencies
      run: pip install psycopg2 pytest
    - name: Run tests
      env:
        DATABASE_URL: postgresql://test:test@localhost:5432/test_db
      run: pytest
```

## 62. Scenario: A stakeholder requests a daily report of test results. How would you automate this using GitHub Actions and Python?

**Answer**: Create a scheduled workflow that runs a Python script to generate a report and uploads it as an artifact or sends it via email/Slack.

```yaml
name: Daily Test Report
on:
  schedule:
    - cron: '0 0 * * *'
jobs:
  report:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install dependencies
      run: pip install pytest
    - name: Generate report
      run: python scripts/generate_report.py
    - name: Upload report
      uses: actions/upload-artifact@v3
      with:
        name: test-report
        path: report.txt
```

```python
# scripts/generate_report.py
with open("report.txt", "w") as f:
    f.write("Test Report for $(date)\n")
    # Add test results logic here
```

## 63. Scenario: Your test suite is producing flaky tests due to external API rate limits. How would you address this?

**Answer**: Address by:
- **Mock APIs**: Use `unittest.mock` to simulate API responses.
- **Rate Limit Handling**: Add retries or backoff logic.
- **Throttle Requests**: Limit the frequency of API calls in tests.
- **Use Test-Specific Keys**: Use separate API keys for testing with higher limits.

```python
from unittest.mock import patch
def test_api_call():
    with patch('requests.get') as mocked_get:
        mocked_get.return_value.json.return_value = {"status": "success"}
        # Test API call
```

## 64. Scenario: A Bash script used for testing is failing on Windows runners in GitHub Actions. How would you make it cross-platform compatible?

**Answer**: Make the script cross-platform by:
- Using portable commands (e.g., `find` vs. `dir`).
- Checking the OS with `$OSTYPE` or `uname`.
- Using GitHub Actions’ `runs-on` to specify compatible runners (e.g., `ubuntu-latest`).

```bash
#!/bin/bash
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
  echo "Running on Windows"
  dir
else
  echo "Running on Unix-like system"
  ls
fi
```

## 65. Scenario: Your team is adopting a monorepo, and you need to run specific tests based on changes in certain directories. How would you configure this in GitHub Actions?

**Answer**: Use `paths` filters in the `on` section to trigger workflows based on changed files. Use `github.event_name` to handle pull requests.

```yaml
name: Monorepo Tests
on:
  push:
    paths:
      - 'frontend/**'
      - 'backend/**'
jobs:
  frontend:
    if: contains(github.event_name, 'push') && github.event.paths[0] =~ '^frontend/'
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run frontend tests
      run: npm test --prefix frontend
  backend:
    if: contains(github.event_name, 'push') && github.event.paths[0] =~ '^backend/'
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run backend tests
      run: pytest backend
```

## 66. Scenario: A test automation pipeline is consuming too many GitHub Actions minutes. How would you analyze and reduce usage?

**Answer**: Analyze:
- Check usage in the repository’s billing settings.
- Review workflow logs for long-running steps.
Reduce:
- Cache dependencies.
- Parallelize jobs.
- Skip unnecessary steps with conditionals.
- Use self-hosted runners.

```yaml
name: Efficient Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Cache dependencies
      uses: actions/cache@v3
      with:
        path: ~/.cache/pip
        key: ${{ runner.os }}-pip-${{ hashFiles('requirements.txt') }}
    - name: Run tests
      run: pytest
```

## 67. Scenario: Your Python tests need to run in a specific order, but pytest runs them randomly. How would you enforce test order?

**Answer**: Use `pytest-order` or custom markers to enforce order. Alternatively, group tests in a single file and control execution flow.

```bash
pip install pytest-order
```

```python
import pytest
@pytest.mark.order(1)
def test_first():
    assert True
@pytest.mark.order(2)
def test_second():
    assert True
```

## 68. Scenario: A new team member reports that a test automation workflow is not triggering on pull requests. How would you troubleshoot this?

**Answer**: Troubleshoot by:
- Checking the `on` section for `pull_request` event.
- Verifying branch protection rules.
- Ensuring the workflow file is in the correct path (`.github/workflows/`).
- Reviewing GitHub Actions permissions in repository settings.

```yaml
name: PR Tests
on: [pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run tests
      run: pytest
```

## 69. Scenario: Your application requires a custom test environment with specific tools. How would you set this up in GitHub Actions?

**Answer**: Use a custom Docker container or self-hosted runner with the required tools installed.

```yaml
name: Custom Environment Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    container:
      image: my-custom-image:latest
    steps:
    - uses: actions/checkout@v3
    - name: Run tests
      run: pytest
```

## 70. Scenario: A test automation script is failing because of a version mismatch in Python dependencies. How would you ensure consistent environments?

**Answer**: Ensure consistency by:
- Using a `requirements.txt` or `Pipfile` to pin versions.
- Caching dependencies to avoid changes.
- Using a specific Python version in `actions/setup-python`.
- Running tests in a container with a fixed environment.

```yaml
name: Consistent Tests
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install dependencies
      run: pip install -r requirements.txt
    - name: Run tests
      run: pytest
```