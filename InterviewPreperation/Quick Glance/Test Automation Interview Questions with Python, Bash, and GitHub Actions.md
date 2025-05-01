# Test Automation Interview Questions with Python, Bash, and GitHub Actions (Shortened Answers)

## Conceptual Questions

1. **What is test automation, and why is it important?**  
   Automates test execution, comparison, and reporting. Saves time, improves reliability, supports CI/CD, enables frequent testing.

2. **Difference between manual and automated testing?**  
   Manual: Human-executed, good for exploratory/UI tests, slow. Automated: Scripted, ideal for repetitive/regression tests, fast.

3. **Key components of a test automation framework?**  
   Test scripts, data, libraries (e.g., pytest), reporting, config management, CI/CD integration, test runner.

4. **How do Python, Bash, and GitHub Actions complement each other in test automation?**  
   Python: Robust test scripts. Bash: Environment setup, CLI tasks. GitHub Actions: Orchestrates workflows, triggers tests.

5. **Advantages of Python for test automation?**  
   Rich libraries (pytest, Selenium), readable, cross-platform, flexible, strong community.

6. **How can Bash scripts be used effectively in test automation?**  
   Setup environments, run CLI tests, clean resources, validate system state.

7. **Role of GitHub Actions in automating testing workflows?**  
   Triggers tests on events, runs in isolated runners, integrates tools, provides reports.

8. **Difference between unit, integration, and end-to-end tests?**  
   Unit: Tests single components. Integration: Tests module interactions. End-to-end: Tests entire app flow.

9. **Common Python testing frameworks and their use cases?**  
   pytest: Flexible, feature-rich, all test types. unittest: Basic unit tests, built-in. nose2: Legacy unittest projects.

10. **Ensure test automation scripts are maintainable and scalable?**  
    Modular design, clear naming, organized structure, fixtures, documentation, parameterization.

11. **Purpose of mocking and implementation in Python?**  
    Mocks simulate dependencies for isolated tests. Use `unittest.mock`.  
    ```python
    from unittest.mock import patch
    @patch('requests.get')
    def test_api(mock_get):
        mock_get.return_value.json.return_value = {'key': 'value'}
        assert get_data() == {'key': 'value'}
    ```

12. **Handle flaky tests in automated pipelines?**  
    Identify cause, isolate tests, retry (pytest-rerunfailures), mock dependencies, stabilize data.

13. **Benefits of running tests in CI/CD with GitHub Actions?**  
    Ensures quality, fast feedback, parallel execution, deployment automation, report generation.

14. **Test coverage and measurement in Python?**  
    Measures code tested. Use `pytest-cov`: `pytest --cov=./ --cov-report=html`.

15. **Decide which tests to automate vs. manual?**  
    Automate: Repetitive, frequent, precise tests. Manual: Exploratory, usability, one-off tests.

16. **Role of assertions in Python test automation?**  
    Verify actual vs. expected output, fail tests if unmet.  
    ```python
    assert 1 + 1 == 2
    ```

17. **Manage test data in automated testing?**  
    Use fixtures, external files (JSON/CSV), synthetic data (faker), cleanup post-test.

18. **Best practices for robust Bash scripts in test automation?**  
    Use `set -e`, validate inputs, modular functions, log outputs, handle errors.

19. **Secure sensitive data in test automation scripts?**  
    Use GitHub Secrets, avoid hardcoding, restrict access, encrypt local files.

20. **Limitations of GitHub Actions for test automation?**  
    Limited free minutes, 6-hour job limit, artifact storage caps, GitHub dependency, less orchestration flexibility.

## Technical Questions (Python)

21. **Set up a Python project for pytest?**  
    Install pytest (`pip install pytest`), create `tests/`, name files `test_*.py`, run `pytest`.

22. **Pytest test case for square function?**  
    ```python
    def square(num): return num * num
    def test_square():
        assert square(4) == 16
        assert square(-2) == 4
    ```

23. **Use fixtures in pytest for setup/teardown?**  
    Use `@pytest.fixture` for reusable setup/teardown.  
    ```python
    @pytest.fixture
    def temp_file():
        with open("test.txt", "w") as f: f.write("data")
        yield "test.txt"
        os.remove("test.txt")
    ```

24. **Use `unittest.mock` to mock API calls?**  
    Use `patch` to mock functions.  
    ```python
    from unittest.mock import patch
    @patch('requests.get')
    def test_get_data(mock_get):
        mock_get.return_value.json.return_value = {'key': 'value'}
        assert get_data() == {'key': 'value'}
    ```

25. **Generate test coverage report with pytest and coverage.py?**  
    Install `pytest-cov`, run `pytest --cov=./ --cov-report=html`.

26. **Parametrize tests in pytest?**  
    Use `@pytest.mark.parametrize`.  
    ```python
    @pytest.mark.parametrize("input,expected", [(2, 4), (-3, 9)])
    def test_square(input, expected):
        assert input * input == expected
    ```

27. **Python script for browser testing with Selenium?**  
    ```python
    from selenium.webdriver.common.by import By
    def test_google_search(browser):
        browser.get("https://google.com")
        browser.find_element(By.NAME, "q").send_keys("test").submit()
        assert "test" in browser.title
    ```

28. **Handle async operations in Python tests?**  
    Use `pytest-asyncio`, mark tests `@pytest.mark.asyncio`.  
    ```python
    @pytest.mark.asyncio
    async def test_async():
        await asyncio.sleep(1)
        assert True
    ```

29. **Integrate Python tests with GitHub Actions?**  
    ```yaml
    name: Python Tests
    on: [push]
    jobs:
      test:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: actions/setup-python@v4
          with: { python-version: '3.10' }
        - run: pip install pytest
        - run: pytest
    ```

30. **Use `pytest-xdist` for parallel tests?**  
    Install `pytest-xdist`, run `pytest -n auto`.

31. **Test REST API response with `requests`?**  
    ```python
    def test_api():
        response = requests.get("https://api.example.com/data")
        assert response.status_code == 200
        assert response.json()["key"] == "value"
    ```

32. **Skip tests in pytest based on conditions?**  
    Use `@pytest.mark.skipif`.  
    ```python
    @pytest.mark.skipif(sys.platform == "win32", reason="Unix only")
    def test_unix():
        assert True
    ```

33. **Use Python’s `logging` for test execution?**  
    ```python
    import logging
    logging.basicConfig(level=logging.INFO)
    logger = logging.getLogger(__name__)
    def test_log():
        logger.info("Test started")
        assert True
    ```

34. **Test file I/O (CSV read/write)?**  
    ```python
    def test_csv():
        with open("test.csv", "w") as f:
            f.write("name,age\nAlice,30")
        with open("test.csv") as f:
            assert f.read() == "name,age\nAlice,30"
        os.remove("test.csv")
    ```

35. **Use `doctest` for testing documentation?**  
    ```python
    def square(num):
        """ >>> square(4) \n 16 """
        return num * num
    if __name__ == "__main__":
        import doctest
        doctest.testmod()
    ```

## Technical Questions (Bash)

36. **Bash script to check web server status?**  
    ```bash
    URL="http://example.com"
    curl -s -f -I "$URL" && echo "Server running" || { echo "Server down"; exit 1; }
    ```

37. **Automate test environment setup with Bash?**  
    ```bash
    set -e
    sudo apt-get update
    sudo apt-get install -y python3 python3-pip
    pip3 install pytest
    ```

38. **Validate command output in Bash?**  
    ```bash
    OUTPUT=$(echo "test")
    [ "$OUTPUT" = "test" ] && echo "Pass" || { echo "Fail"; exit 1; }
    ```

39. **Handle errors in Bash test scripts?**  
    Use `set -e`, `trap`, check `$?`.  
    ```bash
    set -e
    trap 'echo "Error at $LINENO"; exit 1' ERR
    false && echo "This won't run"
    ```

40. **Clean up temporary files in Bash?**  
    ```bash
    TEMP_DIR="./test_temp"
    [ -d "$TEMP_DIR" ] && rm -rf "$TEMP_DIR"/* || echo "No temp dir"
    ```

41. **Integrate Bash script in GitHub Actions?**  
    ```yaml
    steps:
    - uses: actions/checkout@v3
    - run: bash scripts/test.sh
    ```

42. **Parse JSON in Bash?**  
    Use `jq`.  
    ```bash
    JSON='{"status":"success"}'
    STATUS=$(echo "$JSON" | jq -r '.status')
    [ "$STATUS" = "success" ] || exit 1
    ```

43. **Run Python tests in Bash, exit on failure?**  
    ```bash
    set -e
    for TEST in test_*.py; do
      pytest "$TEST" || exit 1
    done
    ```

44. **Check required tools in Bash?**  
    ```bash
    for TOOL in python3 pytest; do
      command -v "$TOOL" || { echo "$TOOL missing"; exit 1; }
    done
    ```

45. **Schedule Bash tests in GitHub Actions?**  
    ```yaml
    on:
      schedule:
        - cron: '0 0 * * *'
    steps:
    - uses: actions/checkout@v3
    - run: bash scripts/test.sh
    ```

## Technical Questions (GitHub Actions)

46. **Run Python tests with pytest in GitHub Actions?**  
    ```yaml
    name: Tests
    on: [push]
    jobs:
      test:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: actions/setup-python@v4
          with: { python-version: '3.10' }
        - run: pip install pytest
        - run: pytest
    ```

47. **Matrix builds for multiple Python versions?**  
    ```yaml
    strategy:
      matrix:
        python-version: ['3.8', '3.9', '3.10']
    steps:
    - uses: actions/setup-python@v4
      with: { python-version: ${{ matrix.python-version }} }
    ```

48. **Cache Python dependencies in GitHub Actions?**  
    ```yaml
    - uses: actions/cache@v3
      with:
        path: ~/.cache/pip
        key: ${{ runner.os }}-pip-${{ hashFiles('requirements.txt') }}
    ```

49. **Run Python and Bash tests in GitHub Actions?**  
    ```yaml
    steps:
    - uses: actions/checkout@v3
    - uses: actions/setup-python@v4
      with: { python-version: '3.10' }
    - run: pip install pytest
    - run: pytest
    - run: bash scripts/test.sh
    ```

50. **Upload test reports as artifacts?**  
    ```yaml
    - uses: actions/upload-artifact@v3
      with:
        name: coverage-report
        path: coverage.xml
    ```

51. **Secure environment variables in GitHub Actions?**  
    ```yaml
    env:
      API_KEY: ${{ secrets.API_KEY }}
    ```

52. **Trigger tests manually or on schedule?**  
    ```yaml
    on:
      workflow_dispatch:
      schedule:
        - cron: '0 0 * * *'
    ```

53. **Handle test failures in GitHub Actions?**  
    ```yaml
    - run: pytest
      continue-on-error: true
    - run: echo "Log failure"
      if: failure()
    ```

54. **Integrate flake8 linter in GitHub Actions?**  
    ```yaml
    steps:
    - uses: actions/setup-python@v4
      with: { python-version: '3.10' }
    - run: pip install flake8
    - run: flake8 .
    ```

55. **Debug failing tests in GitHub Actions?**  
    Enable `ACTIONS_STEP_DEBUG`, add verbose output, upload logs.  
    ```yaml
    - run: pytest -v
    - uses: actions/upload-artifact@v3
      if: failure()
      with: { name: logs, path: test_output.log }
    ```

## Scenario-Based Questions

56. **Python tests fail due to race conditions. Diagnose and fix?**  
    Log timing, use `pytest --pdb`, add waits/mocks, split tests.  
    ```python
    import time
    def test_e2e():
        time.sleep(1)
        assert True
    ```

57. **GitHub Actions workflow too slow. Optimize?**  
    Cache dependencies, parallelize, run affected tests, use faster runners.  
    ```yaml
    - uses: actions/cache@v3
      with: { path: ~/.cache/pip, key: pip-${{ hashFiles('requirements.txt') }}
    ```

58. **Bash script fails due to missing dependency. Resolve?**  
    Install dependency, use Docker, check tools.  
    ```yaml
    - run: sudo apt-get install -y jq
    - run: bash scripts/test.sh
    ```

59. **Ensure PRs pass tests before merging?**  
    Enable branch protection, require test workflow status.  
    ```yaml
    on: [pull_request]
    steps:
    - run: pytest
    ```

60. **Test script exposes credentials in logs. Mitigate?**  
    Use secrets, mask logs, audit scripts.  
    ```yaml
    env: { DB_PASS: ${{ secrets.DB_PASS }} }
    ```

61. **Set up test database in GitHub Actions?**  
    Use service container (e.g., PostgreSQL).  
    ```yaml
    services:
      postgres:
        image: postgres
        env: { POSTGRES_DB: test }
        ports: ['5432:5432']
    ```

62. **Automate daily test report?**  
    Schedule workflow, run Python report script, upload artifact.  
    ```yaml
    on:
      schedule:
        - cron: '0 0 * * *'
    steps:
    - run: python generate_report.py
    - uses: actions/upload-artifact@v3
      with: { name: report, path: report.txt }
    ```

63. **Flaky tests due to API rate limits. Address?**  
    Mock APIs, add retries, throttle requests.  
    ```python
    @patch('requests.get')
    def test_api(mock_get):
        mock_get.return_value.json.return_value = {'status': 'ok'}
        assert True
    ```

64. **Bash script fails on Windows runners. Fix?**  
    Use portable commands, check OS, use Unix runners.  
    ```bash
    [ "$OSTYPE" = "msys" ] && dir || ls
    ```

65. **Monorepo: Run tests for specific directories?**  
    Use `paths` filters.  
    ```yaml
    on:
      push:
        paths: ['frontend/**']
    jobs:
      frontend:
        steps:
        - run: npm test --prefix frontend
    ```

66. **Pipeline consumes too many GitHub Actions minutes. Reduce?**  
    Cache dependencies, parallelize, skip steps, use self-hosted runners.  
    ```yaml
    - uses: actions/cache@v3
      with: { path: ~/.cache/pip }
    ```

67. **Enforce test order in pytest?**  
    Use `pytest-order`.  
    ```python
    @pytest.mark.order(1)
    def test_first(): assert True
    ```

68. **Workflow not triggering on PRs. Troubleshoot?**  
    Check `on: pull_request`, branch rules, file path, permissions.  
    ```yaml
    on: [pull_request]
    ```

69. **Custom test environment in GitHub Actions?**  
    Use Docker container or self-hosted runner.  
    ```yaml
    container: custom-image:latest
    ```

70. **Test fails due to dependency version mismatch. Ensure consistency?**  
    Pin versions in `requirements.txt`, cache, use fixed Python version.  
    ```yaml
    - uses: actions/setup-python@v4
      with: { python-version: '3.10' }
    - run: pip install -r requirements.txt
    ```