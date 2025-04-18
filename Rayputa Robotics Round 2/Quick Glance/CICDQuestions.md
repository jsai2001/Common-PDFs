# CI/CD Interview Questions for GitHub Actions (Shortened Answers)

## Conceptual Questions

1. **What is CI/CD, and how does it benefit software development teams?**  
   CI/CD automates code integration, testing, and deployment. Benefits: faster feedback, fewer bugs, better collaboration, quicker releases.

2. **Explain the difference between Continuous Integration, Continuous Delivery, and Continuous Deployment.**  
   - CI: Frequent code merges with automated tests.  
   - Continuous Delivery: CI + automated release, manual deploy.  
   - Continuous Deployment: CI + automated deploy to production.

3. **What are the key components of a CI/CD pipeline?**  
   Source control, build, test, deploy, monitoring.

4. **How does GitHub Actions fit into the CI/CD ecosystem?**  
   GitHub Actions automates CI/CD workflows in GitHub using YAML, integrating with repos and tools like Docker, AWS.

5. **What is a GitHub Actions workflow, and how is it defined?**  
   Workflow: Automated process in YAML (`.github/workflows/`). Includes jobs, steps, triggers.

6. **Explain the role of runners in GitHub Actions.**  
   Runners are VMs/containers executing workflows. GitHub-hosted or self-hosted.

7. **What are the main advantages of using GitHub Actions for CI/CD over other tools like Jenkins or CircleCI?**  
   GitHub integration, easy YAML setup, action marketplace, free tier, scalable runners.

8. **What is the purpose of the `on` keyword in a GitHub Actions workflow file?**  
   `on` defines events (e.g., push, pull_request) triggering the workflow.

9. **How does GitHub Actions handle secrets management?**  
   Secrets stored encrypted in repo settings, accessed via `${{ secrets.NAME }}`, masked in logs.

10. **What are GitHub Actions environments, and how are they used in deployment workflows?**  
    Environments manage deployment targets with protection rules, secrets, variables.

11. **Explain the concept of matrix builds in GitHub Actions.**  
    Matrix builds run jobs with multiple configs (e.g., OS, versions) using `strategy.matrix`.

12. **What are reusable workflows in GitHub Actions, and when would you use them?**  
    Reusable workflows are callable YAML files for shared tasks, used for standardization.

13. **How does GitHub Actions support caching to optimize pipeline performance?**  
    Caches dependencies/artifacts with `actions/cache`, reducing build time.

14. **What is the difference between a job and a step in a GitHub Actions workflow?**  
    Job: Group of steps on one runner. Step: Individual task in a job.

15. **How can you trigger a GitHub Actions workflow manually or on a schedule?**  
    Manual: `workflow_dispatch`. Schedule: `schedule` with cron.

16. **What are GitHub Actions artifacts, and how are they used in pipelines?**  
    Artifacts are files (e.g., builds, reports) uploaded with `actions/upload-artifact` for sharing/debugging.

17. **How does GitHub Actions handle concurrency control in workflows?**  
    `concurrency` limits simultaneous runs, cancels in-progress runs for same context.

18. **What is the purpose of the `needs` keyword in a GitHub Actions workflow?**  
    `needs` ensures a job runs after specified jobs complete successfully.

19. **How can you secure a GitHub Actions workflow to prevent unauthorized access or misuse?**  
    Use secrets, limit `GITHUB_TOKEN`, pin actions, use environments, audit workflows.

20. **What are some common use cases for GitHub Actions beyond CI/CD?**  
    Automation (issue labeling), code review (linting), notifications, scheduled tasks.

## Technical Questions

21. **How do you define a GitHub Actions workflow file, and what is the typical structure of a `.yml` file?**  
    YAML in `.github/workflows/`: `name`, `on` (triggers), `jobs` (with `runs-on`, `steps`).

22. **Write a simple GitHub Actions workflow to build and test a Node.js application.**  
    ```yaml
    name: Node.js CI
    on: [push, pull_request]
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: actions/setup-node@v3
          with: { node-version: '16', cache: 'npm' }
        - run: npm ci
        - run: npm run build
        - run: npm test
    ```

23. **How do you configure a workflow to run on specific branches or tags?**  
    Use `branches` or `tags` in `on`.  
    ```yaml
    on:
      push:
        branches: [main]
        tags: ['v*']
    ```

24. **Explain how to use environment variables in a GitHub Actions workflow.**  
    Set with `env` at workflow/job/step level, access secrets/context.  
    ```yaml
    env: { APP_ENV: prod }
    ```

25. **How can you share data between steps in a GitHub Actions job?**  
    Use files, `GITHUB_ENV`, or artifacts.  
    ```yaml
    - run: echo "VAR=value" >> $GITHUB_ENV
    - run: echo ${{ env.VAR }}
    ```

26. **How do you set up a matrix build to test a project across multiple versions of a programming language?**  
    Use `strategy.matrix` for versions.  
    ```yaml
    strategy:
      matrix:
        python-version: ['3.8', '3.9']
    ```

27. **How do you configure a workflow to deploy to a cloud provider like AWS or Azure?**  
    Use provider actions (e.g., `aws-actions/configure-aws-credentials`) with secrets.  
    ```yaml
    - uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_KEY }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET }}
    ```

28. **What is the `actions/checkout` action, and why is it commonly used in workflows?**  
    Clones repo code to runner, needed for build/test steps.

29. **How can you use Docker containers in a GitHub Actions workflow?**  
    Run steps in a container via `container` or Docker commands.  
    ```yaml
    container: node:16
    ```

30. **Explain how to use the `if` condition in a GitHub Actions workflow to control step execution.**  
    `if` uses expressions for conditional steps/jobs.  
    ```yaml
    - run: echo "main"
      if: github.ref == 'refs/heads/main'
    ```

31. **How do you configure a workflow to run on a self-hosted runner?**  
    Set `runs-on: self-hosted`.  
    ```yaml
    runs-on: self-hosted
    ```

32. **What are the steps to cache dependencies (e.g., npm or pip) in a GitHub Actions workflow?**  
    Use `actions/cache` with `path` and `key`.  
    ```yaml
    - uses: actions/cache@v3
      with:
        path: ~/.npm
        key: ${{ runner.os }}-npm-${{ hashFiles('package-lock.json') }}
    ```

33. **How do you handle failures in a GitHub Actions workflow (e.g., continue on error)?**  
    Use `continue-on-error: true` or `if: failure()`.  
    ```yaml
    - run: exit 1
      continue-on-error: true
    ```

34. **How can you trigger a workflow in one repository from another repository using repository dispatch?**  
    Use `repository_dispatch` and API call.  
    ```yaml
    on:
      repository_dispatch:
        types: [trigger]
    ```

35. **What is the `GITHUB_TOKEN` secret, and how is it used in GitHub Actions?**  
    Auto-generated token for GitHub API, scoped to repo/workflow.  
    ```yaml
    - run: curl -H "Authorization: token ${{ secrets.GITHUB_TOKEN }}"
    ```

36. **How do you configure a workflow to publish a package to a registry like npm or PyPI?**  
    Authenticate with secrets, run publish command.  
    ```yaml
    - run: npm publish
      env: { NODE_AUTH_TOKEN: ${{ secrets.NPM_TOKEN }} }
    ```

37. **How do you set up a workflow to run static code analysis or linting?**  
    Install/run linter (e.g., flake8).  
    ```yaml
    - run: pip install flake8
    - run: flake8 .
    ```

38. **Explain how to use composite actions in GitHub Actions.**  
    Bundle steps in `action.yml` for reuse.  
    ```yaml
    runs:
      using: composite
      steps:
      - run: echo "Step"
    ```

39. **How do you debug a failing GitHub Actions workflow?**  
    Check logs, add debug steps, enable `ACTIONS_STEP_DEBUG`, use `act` locally.

40. **What are the limitations of GitHub Actions (e.g., execution time, storage, concurrency)?**  
    6-hour job limit, 500 MB artifact storage, limited concurrency (free tier), fixed runner specs.

## Scenario-Based Questions

41. **Workflow takes too long, delaying feedback. How to optimize?**  
    Cache dependencies, parallelize jobs, skip steps, use faster runners.  
    ```yaml
    - uses: actions/cache@v3
      with:
        path: ~/.npm
        key: npm-${{ hashFiles('package-lock.json') }}
    ```

42. **Workflow fails intermittently due to network issues downloading dependencies. How to fix?**  
    Cache dependencies, use proxy, retry steps, increase timeouts.  
    ```yaml
    - uses: nick-invision/retry@v2
      with: { command: npm install }
    ```

43. **Set up CI/CD for microservices with separate repos. How to structure workflows?**  
    Standardize reusable workflows, use repo-specific configs, central secrets, cross-repo triggers.  
    ```yaml
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: npm test
    ```

44. **Junior dev pushes credentials to public repo. How to mitigate and prevent?**  
    Remove credentials, rotate secrets, use secrets, enable secret scanning, train team.  
    ```yaml
    env: { API_KEY: ${{ secrets.API_KEY }} }
    ```

45. **Enforce passing tests for PRs, prevent bypassing. How?**  
    Enable branch protection, require status checks, limit merge permissions.  
    ```yaml
    on: pull_request
    jobs:
      test:
        runs-on: ubuntu-latest
        steps:
        - run: npm test
    ```

46. **Prod deployment fails due to outdated branch. How to prevent?**  
    Restrict triggers to `main`, use tags, add approvals.  
    ```yaml
    on:
      push:
        branches: [main]
    ```

47. **Deploy to dev, staging, prod with different configs. How to set up?**  
    Use environments, env-specific secrets, conditional jobs, approvals.  
    ```yaml
    jobs:
      deploy-prod:
        environment: production
        steps:
        - run: ./deploy.sh
    ```

48. **Workflow exceeds free tier minutes. How to analyze/reduce?**  
    Check billing, cache dependencies, limit triggers, use self-hosted runners.  
    ```yaml
    - uses: actions/cache@v3
      with: { path: ~/.npm }
    ```

49. **Auto-release Python package to PyPI on tag push. Steps?**  
    Trigger on tags, store PyPI token, build/publish with twine.  
    ```yaml
    on:
      push:
        tags: ['v*']
    steps:
    - run: twine upload dist/*
      env: { TWINE_PASSWORD: ${{ secrets.PYPI_TOKEN }} }
    ```

50. **Workflow not triggering on PR. How to troubleshoot?**  
    Check `on`, permissions, filters, logs, GitHub status.  
    ```yaml
    on:
      pull_request:
        branches: [main]
    ```

51. **App needs database for tests, not on runners. How to set up?**  
    Use service container (e.g., PostgreSQL), configure connection.  
    ```yaml
    services:
      postgres:
        image: postgres
        env: { POSTGRES_DB: test }
    ```

52. **Run security scan before deployment. How to integrate?**  
    Use Dependabot or tools like Snyk, fail on vulnerabilities.  
    ```yaml
    - run: snyk test
      env: { SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }} }
    ```

53. **Workflow fails due to missing tool version. How to handle?**  
    Install tool, use custom container, or self-hosted runner.  
    ```yaml
    container: custom-image:1.0
    ```

54. **Automate code formatting and push changes. How to set up?**  
    Run formatter, commit with action, avoid loop with `if`.  
    ```yaml
    - uses: stefanzweifel/git-auto-commit-action@v4
      with: { commit_message: "Auto-format" }
    ```

55. **Run jobs based on change type (frontend vs. backend). How?**  
    Use `paths` or `if` with change detection.  
    ```yaml
    on:
      push:
        paths: ['frontend/**']
    ```

56. **Inconsistent test results across runners. How to fix?**  
    Pin versions, use same runner, control test randomness.  
    ```yaml
    container: node:16
    ```

57. **Monorepo: Run workflows for specific directory changes. How?**  
    Use `paths` and conditional jobs.  
    ```yaml
    on:
      push:
        paths: ['services/frontend/**']
    ```

58. **Report successful deployments for past month. How to extract?**  
    Use GitHub API, filter successes, automate report.  
    ```yaml
    - run: curl -H "Authorization: Bearer ${{ secrets.GITHUB_TOKEN }}"
    ```

59. **Workflow interacts with external API needing auth. How to secure?**  
    Store keys in secrets, use `${{ secrets.KEY }}`, mask logs.  
    ```yaml
    env: { API_KEY: ${{ secrets.API_KEY }} }
    ```

60. **Legacy app needs custom build env. How to address?**  
    Use custom Docker image or self-hosted runner.  
    ```yaml
    container: legacy-env:1.0
    ```