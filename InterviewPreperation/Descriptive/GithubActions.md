Here’s a comprehensive list of interview preparation questions for GitHub Actions, covering fundamental concepts, practical applications, and advanced scenarios. These questions are designed to help you prepare for a range of topics that might come up in an interview, from basic understanding to real-world troubleshooting and optimization.

### Basic Concepts
- What are GitHub Actions, and how do they differ from traditional CI/CD tools like Jenkins or CircleCI?
- What is a workflow in GitHub Actions, and how is it defined?
- What is the purpose of the `.github/workflows` directory in a repository?
- What are the key components of a GitHub Actions workflow file (e.g., name, on, jobs)?
- What is an "event" in GitHub Actions, and can you give examples of common events that trigger workflows?
- What is the difference between a "job" and a "step" in a workflow?
- What are "actions" in GitHub Actions, and how can you use pre-built actions from the GitHub Marketplace?
- What is the role of runners in GitHub Actions, and what types of runners are available?
- How does GitHub Actions handle secrets, and why is this important?
- What file format is used for GitHub Actions workflows, and why was it chosen?

### Workflow Syntax and Configuration
- How do you specify which events trigger a workflow (e.g., push, pull_request, schedule)?
- What does the `runs-on` keyword do, and what are some common values for it?
- How can you define environment variables in a GitHub Actions workflow?
- What is the `if` conditional in GitHub Actions, and how can it be used to control job or step execution?
- How do you set up a matrix strategy to run a job with multiple configurations (e.g., different OS or software versions)?
- What is the purpose of the `needs` keyword in a workflow, and how does it manage job dependencies?
- How can you pass outputs from one step to another in a job?
- What is the `env` context, and how does it differ from `github` or `secrets` contexts?
- How do you configure a workflow to run on a specific branch or tag?
- What is the `timeout-minutes` property, and why might you use it?

### Practical Use Cases
- How would you set up a workflow to automatically run tests on every push to the main branch?
- How can you configure a workflow to deploy an application to a cloud provider (e.g., AWS, Azure, or GCP)?
- How would you create a workflow to build and publish a Docker image to a registry like Docker Hub?
- How can you use GitHub Actions to automate the release process for a project (e.g., creating a GitHub Release)?
- How would you set up a workflow to lint code and report errors as a pull request comment?
- How can you use GitHub Actions to schedule a task, such as running a script every day at midnight?
- How would you configure a workflow to run only when specific files (e.g., `.py` files) are changed?
- How can you trigger a workflow manually using the `workflow_dispatch` event?
- How would you set up a workflow to run integration tests across multiple Node.js versions?
- How can you use GitHub Actions to send notifications (e.g., to Slack or email) on workflow success or failure?

### Advanced Topics
- What are composite actions, and how do you create one?
- How can you create a reusable workflow and call it from another workflow?
- What is the difference between `GITHUB_TOKEN` and a personal access token (PAT) in GitHub Actions?
- How do you debug a failing GitHub Actions workflow?
- What are some ways to optimize the performance of a GitHub Actions workflow (e.g., caching)?
- How does the cache action work, and what are some common use cases for it (e.g., caching dependencies)?
- How can you secure a GitHub Actions workflow to prevent unauthorized access or misuse?
- What is the `permissions` key in a workflow, and how does it enhance security?
- How can you use self-hosted runners, and what are the pros and cons compared to GitHub-hosted runners?
- How do you handle long-running workflows that exceed GitHub’s default limits?

### Troubleshooting and Edge Cases
- What does it mean when a workflow fails with "No runner available"? How would you resolve it?
- How do you handle a situation where a step fails intermittently due to network issues?
- What happens if two workflows are triggered simultaneously for the same event? How does concurrency work?
- How can you skip a workflow run for certain commits (e.g., `[skip ci]`)?
- What does the error "Job is missing required permissions" indicate, and how do you fix it?
- How would you troubleshoot a workflow that runs successfully but doesn’t produce the expected output?
- What happens if a secret is accidentally exposed in a workflow log? How can you prevent this?
- How do you handle workflows that depend on external services that might be down?
- What does the `continue-on-error` property do, and when might you use it?
- How can you test a GitHub Actions workflow locally before pushing it to the repository?

### Integration and Ecosystem
- How does GitHub Actions integrate with GitHub Packages for publishing artifacts?
- How can you use GitHub Actions with GitHub Pages to deploy a static site?
- How does GitHub Actions work with third-party tools like Terraform or Ansible?
- What are some popular actions from the GitHub Marketplace, and what do they do?
- How can you use GitHub Actions to enforce code quality checks (e.g., with ESLint, RuboCop)?
- How does GitHub Actions support multi-language projects (e.g., Python and JavaScript in the same repo)?
- How can you integrate GitHub Actions with a monorepo setup?
- How do you configure GitHub Actions to work with Dependabot for dependency updates?
- How can GitHub Actions be used in conjunction with GitHub’s code scanning or secret scanning features?
- What role does GitHub Actions play in an open-source project’s contribution workflow?

### Scenario-Based Questions
- You’re tasked with speeding up a workflow that takes 20 minutes to run. What steps would you take?
- A team member accidentally commits a secret to the repo, triggering a workflow. How do you respond?
- Your workflow needs to run different steps based on whether it’s a pull request or a push. How would you design it?
- A workflow fails because a third-party action is deprecated. How do you handle this?
- You need to ensure that a deployment workflow only runs after tests pass. How do you enforce this?
- How would you set up a workflow to automatically bump a version number and tag a release?
- A company wants to migrate from Jenkins to GitHub Actions. What challenges might they face?
- Your team wants to enforce that all PRs have passing tests before merging. How do you configure this?
- How would you design a workflow to handle a multi-stage deployment (e.g., dev, staging, prod)?
- A workflow needs to run on both Windows and Linux runners. How do you ensure compatibility?

1. **What are GitHub Actions, and how do they differ from traditional CI/CD tools like Jenkins or CircleCI?**
    - **GitHub Actions** is a platform integrated into GitHub that automates workflows for building, testing, and deploying code directly within a repository. It allows developers to define custom CI/CD pipelines using YAML files triggered by repository events like pushes or pull requests.
    - **Differences from Jenkins/CircleCI:**
      - **Integration:** GitHub Actions is natively built into GitHub, reducing setup overhead, while Jenkins and CircleCI are standalone tools requiring separate hosting and configuration.
      - **Event-Driven:** GitHub Actions is tightly coupled with GitHub events (e.g., push, pull_request), whereas Jenkins relies on polling or webhooks, and CircleCI uses its own configuration tied to Git events.
      - **Ecosystem:** GitHub Actions leverages a marketplace of pre-built actions, making it easier to reuse community solutions, while Jenkins uses plugins and CircleCI has orbs, but both require more setup for integration.
      - **Simplicity:** GitHub Actions is more lightweight and repo-centric, while Jenkins offers greater flexibility for complex, enterprise-level pipelines, and CircleCI balances cloud-hosted ease with customization.

2. **What is a workflow in GitHub Actions, and how is it defined?**
    - A workflow in GitHub Actions is an automated process consisting of one or more jobs that run in response to specific events. It defines the steps to execute tasks like testing, building, or deploying code.
    - **How it’s defined:** Workflows are written in YAML files stored in the `.github/workflows` directory of a repository. Each file specifies the workflow’s name, triggering events (via `on`), and jobs with their steps, executed on designated runners.

3. **What is the purpose of the .github/workflows directory in a repository?**
    - The `.github/workflows` directory is where all GitHub Actions workflow files are stored. It serves as the centralized location for defining and managing automation processes tied to the repository. When an event occurs (e.g., a push), GitHub scans this directory for relevant YAML files to execute, making it the entry point for all CI/CD or automation logic.

4. **What are the key components of a GitHub Actions workflow file (e.g., name, on, jobs)?**
    - A workflow file typically includes:
      - `name:` (Optional) A descriptive name for the workflow, visible in the GitHub UI (e.g., "CI Pipeline").
      - `on:` Specifies the events that trigger the workflow (e.g., push, pull_request, schedule).
      - `jobs:` Defines one or more jobs to run. Each job contains:
         - A unique ID (e.g., build).
         - `runs-on:` The type of runner (e.g., ubuntu-latest).
         - `steps:` A sequence of tasks (e.g., checkout code, run tests).
      - Other optional components include `env` (environment variables), `defaults`, and `permissions`.

5. **What is an "event" in GitHub Actions, and can you give examples of common events that trigger workflows?**
    - An "event" is an activity in the repository that triggers a workflow to run, defined in the `on` section of the YAML file.
    - **Examples:**
      - `push:` Triggered when code is pushed to a branch.
      - `pull_request:` Triggered when a PR is opened, updated, or closed.
      - `schedule:` Runs on a cron schedule (e.g., `0 0 * * *` for midnight daily).
      - `workflow_dispatch:` Allows manual triggering via the GitHub UI.
      - `release:` Triggered when a release is created or published.

6. **What is the difference between a "job" and a "step" in a workflow?**
    - **Job:** A job is a collection of steps that run together on the same runner. Jobs can run in parallel (by default) or sequentially (with dependencies via `needs`). Example: A build job.
    - **Step:** A step is an individual task within a job, such as checking out code, installing dependencies, or running a script. Steps run sequentially within a job.
    - **Key Difference:** Jobs are higher-level units that can depend on each other, while steps are the granular actions within a job.

7. **What are "actions" in GitHub Actions, and how can you use pre-built actions from the GitHub Marketplace?**
    - "Actions" are reusable units of code that perform specific tasks (e.g., checking out a repository, setting up a language runtime). They can be custom scripts or pre-built solutions shared by the community.
    - **Using Pre-built Actions:** You can find actions in the GitHub Marketplace (e.g., `actions/checkout@v4` to clone a repo). In your workflow, reference them in a step with the `uses` keyword:
      ```yaml
      steps:
         - uses: actions/checkout@v4
      ```
      - You can also specify versions and pass inputs to customize their behavior.

8. **What is the role of runners in GitHub Actions, and what types of runners are available?**
    - Runners are the virtual machines or environments that execute workflows. They host the jobs and steps defined in the workflow file.
    - **Types of Runners:**
      - **GitHub-Hosted Runners:** Managed by GitHub, available in OS flavors like `ubuntu-latest`, `windows-latest`, `macos-latest`. They’re pre-configured and ideal for most use cases.
      - **Self-Hosted Runners:** Custom environments set up by users on their own hardware or cloud instances. Useful for specific requirements (e.g., custom OS, hardware, or internal network access).

9. **How does GitHub Actions handle secrets, and why is this important?**
    - GitHub Actions manages secrets via encrypted key-value pairs stored at the repository, organization, or environment level. You define them in the GitHub UI (Settings > Secrets and variables > Actions) and reference them in workflows using the `secrets` context (e.g., `${{ secrets.MY_SECRET }}`).
    - **Importance:** Secrets (e.g., API keys, passwords) are critical for secure automation (e.g., deployments). Encryption prevents exposure in logs or unauthorized access, ensuring sensitive data remains confidential.

10. **What file format is used for GitHub Actions workflows, and why was it chosen?**
     - GitHub Actions workflows use YAML (Yet Another Markup Language).
     - **Why YAML?**
        - **Readability:** YAML’s human-readable syntax makes workflows easy to write and understand.
        - **Simplicity:** It supports structured data (key-value pairs, lists) with minimal syntax, ideal for defining workflows.
        - **Compatibility:** YAML is widely adopted in CI/CD tools (e.g., Travis CI), making it familiar to developers and portable across systems.

11. **How do you specify which events trigger a workflow (e.g., push, pull_request, schedule)?**
     - You specify events using the `on` keyword in the workflow YAML file. It can take a single event or a list of events.
     - **Example:**
        ```yaml
        on:
          push:              # Runs on any push
          pull_request:      # Runs on PR events
          schedule:
             - cron: '0 0 * * *'  # Runs daily at midnight
        ```
     - You can also filter events (e.g., branches, paths) to narrow the trigger scope.

12. **What does the runs-on keyword do, and what are some common values for it?**
     - The `runs-on` keyword specifies the type of runner (virtual machine) a job executes on.
     - **Common Values:**
        - `ubuntu-latest:` Latest Ubuntu version (e.g., 22.04 as of now).
        - `windows-latest:` Latest Windows Server version.
        - `macos-latest:` Latest macOS version.
        - Self-hosted labels (e.g., `self-hosted`, `[self-hosted, linux]`).
     - **Example:**
        ```yaml
        jobs:
          build:
             runs-on: ubuntu-latest
        ```

13. **How can you define environment variables in a GitHub Actions workflow?**
     - Environment variables are defined using the `env` keyword at different scopes:
        - **Workflow level:** Applies to all jobs.
        - **Job level:** Applies to all steps in a job.
        - **Step level:** Applies only to that step.
     - **Example:**
        ```yaml
        env:
          GLOBAL_VAR: "value"  # Workflow-wide
        jobs:
          build:
             env:
                JOB_VAR: "job-value"  # Job-wide
             steps:
                - run: echo $GLOBAL_VAR
                - env:
                     STEP_VAR: "step-value"  # Step-specific
                  run: echo $STEP_VAR
        ```

14. **What is the if conditional in GitHub Actions, and how can it be used to control job or step execution?**
     - The `if` conditional uses expressions to determine whether a job or step runs, based on contexts like `github`, `env`, or `secrets`.
     - **Example:**
        ```yaml
        jobs:
          test:
             if: github.event_name == 'push'  # Runs only on push events
             runs-on: ubuntu-latest
             steps:
                - if: ${{ contains(github.ref, 'main') }}  # Runs only on main branch
                  run: echo "Running on main"
        ```
     - It’s useful for conditional logic (e.g., skipping steps, running on specific branches).

15. **How do you set up a matrix strategy to run a job with multiple configurations (e.g., different OS or software versions)?**
     - The `strategy.matrix` keyword defines multiple configurations for a job, running it in parallel across combinations.
     - **Example:**
        ```yaml
        jobs:
          test:
             runs-on: ${{ matrix.os }}
             strategy:
                matrix:
                  os: [ubuntu-latest, windows-latest]
                  node: [14, 16, 18]
             steps:
                - run: node --version
        ```
     - This runs the job 6 times (2 OSes × 3 Node versions). You can exclude specific combinations with `exclude`.

16. **What is the purpose of the needs keyword in a workflow, and how does it manage job dependencies?**
     - The `needs` keyword specifies that a job depends on the successful completion of other jobs.
     - **Example:**
        ```yaml
        jobs:
          build:
             runs-on: ubuntu-latest
             steps:
                - run: echo "Building"
          deploy:
             needs: build  # Runs only if build succeeds
             runs-on: ubuntu-latest
             steps:
                - run: echo "Deploying"
        ```
     - It ensures sequential execution and prevents downstream jobs (e.g., deployment) from running if prerequisites fail.

17. **How can you pass outputs from one step to another in a job?**
     - Outputs are set in one step using the `::set-output` command (or `outputs` in newer syntax) and accessed in later steps via the `steps` context.
     - **Example:**
        ```yaml
        steps:
          - id: step1
             run: echo "output=hello" >> $GITHUB_OUTPUT
          - run: echo ${{ steps.step1.outputs.output }}  # Prints "hello"
        ```
     - Note: `$GITHUB_OUTPUT` is the modern way, replacing `::set-output` (deprecated in late 2022).

18. **What is the env context, and how does it differ from github or secrets contexts?**
     - **env Context:** Accesses environment variables defined in the workflow (via `env` keyword). Example: `${{ env.MY_VAR }}`.
     - **github Context:** Provides metadata about the event/run (e.g., `${{ github.event_name }}`, `${{ github.sha }}`).
     - **secrets Context:** Accesses encrypted secrets (e.g., `${{ secrets.API_KEY }}`).
     - **Difference:** `env` is for custom variables, `github` is for event/run info, and `secrets` is for secure data.

19. **How do you configure a workflow to run on a specific branch or tag?**
     - You use filters under the `on` keyword with branches or tags.
     - **Example:**
        ```yaml
        on:
          push:
             branches:
                - main
                - 'feature/*'  # Wildcard for feature branches
          pull_request:
             branches:
                - develop
          release:
             types: [published]
             tags:
                - 'v*.*.*'  # Runs on version tags like v1.0.0
        ```
     - This restricts the workflow to specific refs.

20. **What is the timeout-minutes property, and why might you use it?**
     - The `timeout-minutes` property sets a maximum duration (in minutes) for a job or step to run before it’s automatically canceled.
     - **Example:**
        ```yaml
        jobs:
          build:
             runs-on: ubuntu-latest
             timeout-minutes: 10  # Cancels after 10 minutes
             steps:
                - run: ./long-script.sh
                  timeout-minutes: 5  # Step-specific timeout
        ```
     - **Use Case:** Prevents hung processes or infinite loops from consuming resources, especially for external commands or tests.

21. **How would you set up a workflow to automatically run tests on every push to the main branch?**

You’d create a workflow triggered by push events to the main branch, running test steps on a runner.

Example:
```yaml
name: Run Tests
on:
    push:
        branches:
            - main
jobs:
    test:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - name: Set up environment
                run: npm install  # Example for Node.js
            - name: Run tests
                run: npm test
```
This checks out the code, sets up dependencies, and runs tests automatically.

22. **How can you configure a workflow to deploy an application to a cloud provider (e.g., AWS, Azure, or GCP)?**

You’d use cloud-specific actions and secrets to authenticate and deploy.

Example (AWS S3 Deployment):
```yaml
name: Deploy to AWS S3
on:
    push:
        branches:
            - main
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - name: Configure AWS credentials
                uses: aws-actions/configure-aws-credentials@v4
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: us-east-1
            - name: Deploy to S3
                run: aws s3 sync ./dist/ s3://my-bucket/
```
Secrets are stored in GitHub Settings, and the workflow uses a Marketplace action for AWS authentication.

23. **How would you create a workflow to build and publish a Docker image to a registry like Docker Hub?**

You’d build the image and push it using Docker actions and credentials.

Example:
```yaml
name: Publish Docker Image
on:
    push:
        branches:
            - main
jobs:
    build-and-push:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - name: Log in to Docker Hub
                uses: docker/login-action@v3
                with:
                    username: ${{ secrets.DOCKER_USERNAME }}
                    password: ${{ secrets.DOCKER_PASSWORD }}
            - name: Build and push
                uses: docker/build-push-action@v5
                with:
                    context: .
                    push: true
                    tags: user/app:latest
```
Secrets ensure secure login, and the image is tagged and pushed to Docker Hub.

24. **How can you use GitHub Actions to automate the release process for a project (e.g., creating a GitHub Release)?**

You’d trigger on tags and use an action to create a release.

Example:
```yaml
name: Create Release
on:
    push:
        tags:
            - 'v*'
jobs:
    release:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - name: Create GitHub Release
                uses: actions/create-release@v1
                with:
                    tag_name: ${{ github.ref_name }}
                    release_name: Release ${{ github.ref_name }}
                    body: "Release notes here"
                env:
                    GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```
The GITHUB_TOKEN is automatically provided to authenticate the release creation.

25. **How would you set up a workflow to lint code and report errors as a pull request comment?**

You’d run a linter and use an action to post results as a PR comment.

Example (ESLint for JavaScript):
```yaml
name: Lint Code
on:
    pull_request:
jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: npm install
            - name: Run ESLint
                id: lint
                run: npm run lint -- --output-file eslint-report.txt || echo "LINT_FAILED=true" >> $GITHUB_ENV
            - name: Comment on PR
                if: env.LINT_FAILED == 'true'
                uses: actions/github-script@v6
                with:
                    script: |
                        const fs = require('fs');
                        const report = fs.readFileSync('eslint-report.txt', 'utf8');
                        github.rest.issues.createComment({
                            issue_number: context.issue.number,
                            owner: context.repo.owner,
                            repo: context.repo.repo,
                            body: `Linting failed:\n\`\`\`\n${report}\n\`\`\``
                        });
                env:
                    GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```
This posts lint errors as a PR comment if the lint step fails.

26. **How can you use GitHub Actions to schedule a task, such as running a script every day at midnight?**

Use the schedule event with a cron expression.

Example:
```yaml
name: Daily Task
on:
    schedule:
        - cron: '0 0 * * *'  # Midnight UTC daily
jobs:
    task:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: ./my-script.sh
```
The cron syntax (minute hour day month day-of-week) schedules the task.

27. **How would you configure a workflow to run only when specific files (e.g., .py files) are changed?**

Use the paths filter under the on event.

Example:
```yaml
name: Python Check
on:
    push:
        paths:
            - '**/*.py'  # Matches all .py files
jobs:
    check:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: python -m py_compile *.py
```
This triggers only when Python files change.

28. **How can you trigger a workflow manually using the workflow_dispatch event?**

Add workflow_dispatch to the on section.

Example:
```yaml
name: Manual Workflow
on:
    workflow_dispatch:
        inputs:
            message:
                description: 'Custom message'
                required: true
jobs:
    run:
        runs-on: ubuntu-latest
        steps:
            - run: echo ${{ github.event.inputs.message }}
```
Users can trigger it from the GitHub UI under "Actions," optionally providing inputs.

29. **How would you set up a workflow to run integration tests across multiple Node.js versions?**

Use a matrix strategy to test across versions.

Example:
```yaml
name: Integration Tests
on:
    push:
jobs:
    test:
        runs-on: ubuntu-latest
        strategy:
            matrix:
                node-version: [14, 16, 18]
        steps:
            - uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: ${{ matrix.node-version }}
            - run: npm install
            - run: npm run integration-tests
```
This runs tests for each Node.js version in parallel.

30. **How can you use GitHub Actions to send notifications (e.g., to Slack or email) on workflow success or failure?**

Use a notification action in a conditional step.

Example (Slack):
```yaml
name: Notify Slack
on:
    push:
jobs:
    notify:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: echo "Running workflow"
            - name: Slack Notification
                if: success() || failure()
                uses: slackapi/slack-github-action@v1.26.0
                with:
                    slack-bot-token: ${{ secrets.SLACK_BOT_TOKEN }}
                    channel-id: 'my-channel'
                    text: 'Workflow ${{ job.status }}!'
```
This sends a Slack message on success or failure, using a secret for authentication.

31. **What are composite actions, and how do you create one?**

Composite actions are reusable actions that combine multiple steps into a single custom action. They’re defined in a repository and can be used like Marketplace actions.

How to Create:
Create an action.yml file in a directory (e.g., .github/actions/my-action/).
Define runs with using: composite and list steps.

Example:
```yaml
# .github/actions/my-action/action.yml
name: 'My Composite Action'
runs:
    using: 'composite'
    steps:
        - run: echo "Step 1"
            shell: bash
        - run: echo "Step 2"
            shell: bash
```
Use it in a workflow:
```yaml
steps:
    - uses: ./.github/actions/my-action
```

32. **How can you create a reusable workflow and call it from another workflow?**

Reusable workflows are defined in a YAML file and called using the uses keyword with inputs/outputs.

Create Reusable Workflow:
```yaml
# .github/workflows/reusable.yml
name: Reusable Workflow
on:
    workflow_call:
        inputs:
            param:
                required: true
jobs:
    run:
        runs-on: ubuntu-latest
        steps:
            - run: echo ${{ inputs.param }}
```
Call It:
```yaml
name: Caller Workflow
on: push
jobs:
    call:
        uses: ./.github/workflows/reusable.yml
        with:
            param: "Hello"
```
This promotes modularity and reduces duplication.

33. **What is the difference between GITHUB_TOKEN and a personal access token (PAT) in GitHub Actions?**

- **GITHUB_TOKEN**: A temporary token automatically provided by GitHub for each workflow run. It’s scoped to the repository and has limited permissions (e.g., read/write repo access). Expires after the run.
- **Personal Access Token (PAT)**: A user-generated token with customizable permissions (e.g., repo, org, admin). It’s stored as a secret and doesn’t expire unless revoked.

**Key Difference**: GITHUB_TOKEN is ephemeral and repo-specific, while a PAT is persistent and broader in scope, often used for cross-repo or elevated access.

34. **How do you debug a failing GitHub Actions workflow?**

- **Check Logs**: Review step logs in the GitHub UI under "Actions" for errors or outputs.
- **Add Debug Steps**: Insert `run: echo` or env dumps (e.g., `run: env`) to inspect variables.
- **Enable Debug Mode**: Set the `ACTIONS_STEP_DEBUG` secret to true in repo settings for verbose logs.
- **Re-run Workflow**: Use the "Re-run job" feature to test fixes.
- **Test Locally**: Use tools like `act` to simulate workflows locally.

Example debug step:
```yaml
- run: echo "DEBUG: ${{ github.event_name }}"
```

35. **What are some ways to optimize the performance of a GitHub Actions workflow (e.g., caching)?**

- **Caching**: Cache dependencies (e.g., npm, pip) to avoid re-downloading.
- **Parallel Jobs**: Use matrix strategies or split jobs to run tasks concurrently.
- **Skip Unnecessary Steps**: Use if conditions or paths filters to skip irrelevant runs.
- **Optimize Commands**: Minimize setup time (e.g., use prebuilt images).
- **Reuse Actions**: Leverage composite or reusable workflows for efficiency.

Example with caching:
```yaml
- uses: actions/cache@v3
    with:
        path: ~/.npm
        key: npm-${{ hashFiles('package-lock.json') }}
```

36. **How does the cache action work, and what are some common use cases for it (e.g., caching dependencies)?**

The cache action (actions/cache) stores files or directories between workflow runs, using a key to identify the cache.

**How It Works**:
- If the key matches a previous cache, it restores the files.
- If no match, it runs the job and saves a new cache on success.
- A restore-keys fallback can partially match older caches.

Example:
```yaml
- uses: actions/cache@v3
    with:
        path: ~/.m2/repository
        key: maven-${{ hashFiles('pom.xml') }}
        restore-keys: maven-
```
**Use Cases**: Caching npm/pip packages, Maven/Gradle artifacts, or build tools.

37. **How can you secure a GitHub Actions workflow to prevent unauthorized access or misuse?**

- **Limit Permissions**: Use the `permissions` key to restrict token access.
- **Secure Secrets**: Store sensitive data in encrypted secrets, not plaintext.
- **Pin Actions**: Use specific versions (e.g., `actions/checkout@v4`) to avoid malicious updates.
- **Review PR Workflows**: Use `pull_request_target` cautiously and validate code.
- **Concurrency Control**: Prevent overlapping runs with `concurrency`.

Example:
```yaml
permissions:
    contents: read
```

38. **What is the permissions key in a workflow, and how does it enhance security?**

The `permissions` key defines the scope of access for the GITHUB_TOKEN in a workflow or job.

Example:
```yaml
permissions:
    contents: read      # Read-only repo access
    issues: write       # Can write to issues
jobs:
    example:
        runs-on: ubuntu-latest
        steps:
            - run: echo "Limited access"
```
**Security Benefit**: Reduces the risk of unintended actions (e.g., deleting repos) by explicitly setting least-privilege permissions.

39. **How can you use self-hosted runners, and what are the pros and cons compared to GitHub-hosted runners?**

**How to Use**:
- Go to repo Settings > Actions > Runners.
- Add a self-hosted runner by downloading and configuring the runner software on your machine.
- Reference it in workflows with `runs-on: self-hosted` or custom labels.

Example:
```yaml
jobs:
    build:
        runs-on: self-hosted
        steps:
            - run: ./build.sh
```
**Pros**:
- Custom hardware/OS.
- Access to internal networks.
- Cost-effective for heavy usage.

**Cons**:
- Requires maintenance (updates, scaling).
- No GitHub support.
- Setup complexity vs. GitHub-hosted simplicity.

40. **How do you handle long-running workflows that exceed GitHub’s default limits?**

GitHub’s default job timeout is 6 hours (360 minutes).

**Solutions**:
- **Increase Timeout**: Set `timeout-minutes` explicitly (up to GitHub’s hard limit).
```yaml
jobs:
    long-job:
        timeout-minutes: 720  # 12 hours
```
- **Split Jobs**: Break into smaller, dependent jobs using `needs`.
- **Self-Hosted Runners**: Avoid GitHub’s time limits entirely.
- **Optimize**: Reduce runtime with caching or parallelization.
- **External Trigger**: Offload to an external service and trigger via API.

41. **What does it mean when a workflow fails with "No runner available"? How would you resolve it?**
    - **Meaning:** No runner (GitHub-hosted or self-hosted) matches the `runs-on` label or is available to execute the job.
    - **Resolution:**
      - **GitHub-Hosted:** Ensure the `runs-on` value is valid (e.g., `ubuntu-latest`, not a typo). Check GitHub’s status page for outages.
      - **Self-Hosted:** Verify the runner is online, registered, and has the correct labels. Restart or re-register it via `Settings > Actions > Runners`.
      - **Quota:** For free tiers, confirm you haven’t exceeded usage limits.

42. **How do you handle a situation where a step fails intermittently due to network issues?**
    - **Retry Logic:** Use an action like `retry-step` or add a shell retry loop.
      ```yaml
      - name: Fetch data
         run: |
            for i in {1..3}; do curl http://example.com && break || sleep 5; done
      ```
    - **Timeout Increase:** Extend `timeout-minutes` for the step.
    - **Fallback:** Use `continue-on-error` and handle failure downstream.
    - **Cache Results:** Cache network-dependent artifacts to reduce fetches.

43. **What happens if two workflows are triggered simultaneously for the same event? How does concurrency work?**
    - **Default Behavior:** GitHub runs workflows in parallel, which can cause conflicts (e.g., race conditions).
    - **Concurrency Control:** Use the `concurrency` keyword to limit runs:
      ```yaml
      concurrency:
         group: ${{ github.workflow }}-${{ github.ref }}
         cancel-in-progress: true
      ```
    - **Effect:** Only one workflow runs per group (e.g., branch); others queue or cancel.
    - **Fix:** Define unique groups or disable cancellation based on needs.

44. **How can you skip a workflow run for certain commits (e.g., [skip ci])?**
    - **Commit Message Keywords:** Use `[skip ci]`, `[ci skip]`, or `[skip actions]` in commit messages.
      ```bash
      git commit -m "Update docs [skip ci]"
      ```
    - **Alternative:** Use `if` conditions:
      ```yaml
      on: push
      jobs:
         build:
            if: "!contains(github.event.head_commit.message, '[skip ci]')"
            runs-on: ubuntu-latest
      ```

45. **What does the error "Job is missing required permissions" indicate, and how do you fix it?**
    - **Indication:** The `GITHUB_TOKEN` lacks permissions for an action (e.g., writing to issues).
    - **Fix:**
      - **Add Permissions:** Use the `permissions` key:
         ```yaml
         permissions:
            issues: write
         jobs:
            example:
              runs-on: ubuntu-latest
              steps:
                 - run: echo "Needs issue access"
         ```
      - **PAT:** Use a personal access token with broader scope as a secret:
         ```yaml
         env:
            TOKEN: ${{ secrets.MY_PAT }}
         ```

46. **How would you troubleshoot a workflow that runs successfully but doesn’t produce the expected output?**
    - **Check Logs:** Inspect step outputs for silent failures or skipped logic.
    - **Add Debugging:** Insert `run: echo` or `ls -la` to verify state.
      ```yaml
      - run: echo "Output: $(cat file.txt)"
      ```
    - **Validate Assumptions:** Confirm environment variables, file paths, or command success (e.g., `|| exit 1`).
    - **Test Locally:** Replicate with `act` or manually.
    - **Review Conditions:** Ensure `if` clauses or filters aren’t skipping steps.

47. **What happens if a secret is accidentally exposed in a workflow log? How can you prevent this?**
    - **What Happens:** The secret becomes visible in logs, compromising security. GitHub masks known secrets, but only if referenced correctly (e.g., `${{ secrets.MY_SECRET }}`).
    - **Prevention:**
      - **Avoid Echoing:** Don’t use `echo $SECRET` or `run: echo ${{ secrets.MY_SECRET }}`.
      - **Use Env:** Pass secrets via `env` to commands:
         ```yaml
         - run: my-script.sh
            env:
              MY_SECRET: ${{ secrets.MY_SECRET }}
         ```
    - **Post-Incident:** Rotate the secret immediately in GitHub Settings and the external service.

48. **How do you handle workflows that depend on external services that might be down?**
    - **Retries:** Add retry logic (manual or via actions).
    - **Fallbacks:** Use `continue-on-error` and default values.
      ```yaml
      - name: Call API
         continue-on-error: true
         run: curl http://external-service || echo "Service down"
      ```
    - **Status Check:** Pre-check service availability:
      ```yaml
      - run: curl -f http://external-service/health || exit 1
      ```
    - **Notifications:** Alert the team (e.g., Slack) on failure.

49. **What does the continue-on-error property do, and when might you use it?**
    - **Function:** Allows a step or job to fail without failing the entire workflow.
    - **Example:**
      ```yaml
      steps:
         - name: Optional step
            continue-on-error: true
            run: exit 1
         - run: echo "Still runs"
      ```
    - **Use Cases:**
      - Non-critical steps (e.g., linting in a build pipeline).
      - External service calls that might fail intermittently.
      - Reporting failures without halting deployment.

50. **How can you test a GitHub Actions workflow locally before pushing it to the repository?**
    - **Tool:** Use a tool like `act` to simulate workflows locally:
      - **Install:** `brew install act` (Mac) or download from GitHub releases.
      - **Run:** `act -W .github/workflows/my-workflow.yml` in your repo root.
      - **Mock Secrets:** Use `-s MY_SECRET=value` or a `.secrets` file.
      - **Specify Event:** `act push` for `on: push`.
    - **Limitations:** Some GitHub-specific features (e.g., `GITHUB_TOKEN`) may need workarounds, but it’s great for syntax and logic testing.

51. **How does GitHub Actions integrate with GitHub Packages for publishing artifacts?**
    - **Integration:** Automates the build and publish process using workflows. Authenticate with the `GITHUB_TOKEN` and use package-specific commands (e.g., `npm publish`).
    - **Example (NPM Package):**
      ```yaml
      name: Publish to GitHub Packages
      on: push
      jobs:
         publish:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - uses: actions/setup-node@v4
                 with:
                    node-version: 18
                    registry-url: https://npm.pkg.github.com
              - run: npm ci
              - run: npm publish
                 env:
                    NODE_AUTH_TOKEN: ${{ secrets.GITHUB_TOKEN }}
      ```

52. **How can you use GitHub Actions with GitHub Pages to deploy a static site?**
    - **Process:** Build a static site and deploy it to the `gh-pages` branch using the `actions/deploy-pages` action.
    - **Example:**
      ```yaml
      name: Deploy to GitHub Pages
      on:
         push:
            branches:
              - main
      jobs:
         deploy:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - run: npm install && npm run build  # Build static site
              - uses: actions/upload-pages-artifact@v3
                 with:
                    path: ./dist  # Output directory
              - uses: actions/deploy-pages@v4
            permissions:
              pages: write
              id-token: write
      ```

53. **How does GitHub Actions work with third-party tools like Terraform or Ansible?**
    - **Integration:** Runs their CLI commands in steps, often using setup actions or custom scripts.
    - **Example (Terraform):**
      ```yaml
      name: Terraform Apply
      on: push
      jobs:
         terraform:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - uses: hashicorp/setup-terraform@v3
                 with:
                    terraform_version: 1.5.0
              - run: terraform init
              - run: terraform apply -auto-approve
                 env:
                    AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
      ```

54. **What are some popular actions from the GitHub Marketplace, and what do they do?**
    - **Popular Actions:**
      - `actions/checkout`: Clones the repository into the runner (e.g., `uses: actions/checkout@v4`).
      - `actions/setup-node`: Sets up a Node.js environment (e.g., for JavaScript projects).
      - `actions/cache`: Caches dependencies to speed up workflows (e.g., npm cache).
      - `docker/build-push-action`: Builds and pushes Docker images to a registry.
      - `slackapi/slack-github-action`: Sends notifications to Slack channels.
    - **Purpose:** Simplify common tasks like setup, caching, and integrations.

55. **How can you use GitHub Actions to enforce code quality checks (e.g., with ESLint, RuboCop)?**
    - **Process:** Run linters in a workflow and fail the job on errors.
    - **Example (ESLint):**
      ```yaml
      name: Lint Code
      on: pull_request
      jobs:
         lint:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - uses: actions/setup-node@v4
                 with:
                    node-version: 18
              - run: npm install
              - run: npx eslint .
      ```
    - **Example (RuboCop):**
      ```yaml
      - uses: ruby/setup-ruby@v1
         with:
            ruby-version: 3.2
      - run: bundle install
      - run: bundle exec rubocop
      ```

56. **How does GitHub Actions support multi-language projects (e.g., Python and JavaScript in the same repo)?**
    - **Process:** Use setup actions for each language and define jobs or steps for each.
    - **Example:**
      ```yaml
      name: Multi-Language CI
      on: push
      jobs:
         test:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - uses: actions/setup-node@v4
                 with:
                    node-version: 18
              - run: npm install && npm test  # JavaScript
              - uses: actions/setup-python@v5
                 with:
                    python-version: '3.11'
              - run: pip install -r requirements.txt && pytest  # Python
      ```
    - **Matrix Strategies:** Can also test across versions of both languages.

57. **How can you integrate GitHub Actions with a monorepo setup?**
    - **Process:** Use paths filters or separate jobs to target specific monorepo packages.
    - **Example:**
      ```yaml
      name: Monorepo CI
      on:
         push:
            paths:
              - 'packages/app/**'
      jobs:
         app:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - run: cd packages/app && npm install && npm test
         api:
            runs-on: ubuntu-latest
            if: contains(github.event.head_commit.message, 'api')
            steps:
              - uses: actions/checkout@v4
              - run: cd packages/api && npm install && npm test
      ```

58. **How do you configure GitHub Actions to work with Dependabot for dependency updates?**
    - **Process:** Dependabot updates dependencies and triggers workflows via PRs. Configure a workflow to test updates.
    - **Example:**
      ```yaml
      name: Test Dependabot PRs
      on:
         pull_request:
            branches:
              - main
      jobs:
         test:
            runs-on: ubuntu-latest
            if: github.actor == 'dependabot[bot]'
            steps:
              - uses: actions/checkout@v4
              - run: npm install && npm test
      ```
    - **Dependabot Configuration:**
      ```yaml
      version: 2
      updates:
         - package-ecosystem: npm
            directory: "/"
            schedule:
              interval: daily
      ```

59. **How can GitHub Actions be used in conjunction with GitHub’s code scanning or secret scanning features?**
    - **Code Scanning:** Run code scanning (CodeQL) and leverage secret scanning alerts.
    - **Example (CodeQL):**
      ```yaml
      name: Code Scanning
      on: push
      jobs:
         codeql:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - uses: github/codeql-action/init@v3
                 with:
                    languages: javascript
              - uses: github/codeql-action/analyze@v3
      ```
    - **Secret Scanning:** Runs automatically; workflows can use secrets context safely, as GitHub masks detected secrets in logs.

60. **What role does GitHub Actions play in an open-source project’s contribution workflow?**
    - **Automation:** Automates testing, linting, and deployment for contributions.
    - **PR Validation:** Runs tests/linters on PRs to ensure quality.
    - **Releases:** Automates tagging and publishing (e.g., to npm, PyPI).
    - **Community Feedback:** Posts results as PR comments.
    - **Consistency:** Enforces standards across contributors.
    - **Example:** A PR test workflow ensures code quality, freeing maintainers to focus on reviews.

61. **You’re tasked with speeding up a workflow that takes 20 minutes to run. What steps would you take?**
    - **Analyze Logs:** Identify slow steps (e.g., dependency installs, tests) using the GitHub Actions UI.
    - **Cache Dependencies:** Use `actions/cache` to avoid re-downloading (e.g., npm/pip packages).
    - **Parallelize Jobs:** Split tasks into concurrent jobs or use a matrix strategy.
    - **Optimize Commands:** Reduce setup time (e.g., use prebuilt Docker images).
    - **Skip Unnecessary Steps:** Add paths filters or if conditions to run only what’s needed.
    - **Example: Cache npm:**
      ```yaml
      - uses: actions/cache@v3
         with:
            path: ~/.npm
            key: npm-${{ hashFiles('package-lock.json') }}
      ```
    - **Goal:** Reduce runtime to under 10 minutes by targeting bottlenecks.

62. **A team member accidentally commits a secret to the repo, triggering a workflow. How do you respond?**
    - **Immediate Action:** Delete the secret from the commit history (e.g., `git filter-branch` or BFG Repo-Cleaner) and force-push.
    - **Rotate Secret:** Generate a new secret in the external service and update GitHub Secrets.
    - **Check Logs:** Review workflow logs; if exposed, GitHub masks known secrets, but assume compromise.
    - **Prevention:** Educate the team, enforce secrets via secrets context, and use `.gitignore` for sensitive files.
    - **Example: Use env safely:**
      ```yaml
      - run: deploy.sh
         env:
            API_KEY: ${{ secrets.API_KEY }}
      ```

63. **Your workflow needs to run different steps based on whether it’s a pull request or a push. How would you design it?**
    - **Use if conditions with the `github.event_name` context.**
    - **Example:**
      ```yaml
      name: Conditional Workflow
      on: [push, pull_request]
      jobs:
         build:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - name: Test (PR only)
                 if: github.event_name == 'pull_request'
                 run: npm test
              - name: Deploy (Push only)
                 if: github.event_name == 'push'
                 run: npm run deploy
      ```
    - **This separates PR testing from push deployment logic.**

64. **A workflow fails because a third-party action is deprecated. How do you handle this?**
    - **Identify Issue:** Check logs for deprecation warnings or errors.
    - **Find Alternative:** Search GitHub Marketplace or the action’s repo for a replacement (e.g., newer version or fork).
    - **Update Workflow:** Replace the action and test (e.g., `actions/setup-node@v1` → `v4`).
    - **Custom Logic:** If no alternative, replicate the action’s steps manually.
    - **Example Fix:**
      ```yaml
      - uses: actions/setup-node@v4  # Updated from v1
         with:
            node-version: 18
      ```

65. **You need to ensure that a deployment workflow only runs after tests pass. How do you enforce this?**
    - **Use the `needs` keyword to create job dependencies.**
    - **Example:**
      ```yaml
      name: Test and Deploy
      on: push
      jobs:
         test:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - run: npm test
         deploy:
            needs: test  # Runs only if test succeeds
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - run: npm run deploy
      ```
    - **If test fails, deploy is skipped.**

66. **How would you set up a workflow to automatically bump a version number and tag a release?**
    - **Use an action to bump the version and create a tag, triggered manually or on a condition.**
    - **Example:**
      ```yaml
      name: Bump Version and Release
      on:
         workflow_dispatch:
      jobs:
         release:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - name: Bump version
                 run: |
                    git config user.name "GitHub Action"
                    git config user.email "action@github.com"
                    npm version patch -m "Bump to %s"
                    git push --follow-tags
              - uses: actions/create-release@v1
                 with:
                    tag_name: ${{ github.ref_name }}
                    release_name: Release ${{ github.ref_name }}
                 env:
                    GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
      ```
    - **This bumps the patch version and tags a release.**

67. **A company wants to migrate from Jenkins to GitHub Actions. What challenges might they face?**
    - **Learning Curve:** Adapting to YAML syntax and GitHub’s event-driven model vs. Jenkins’ UI/pipeline scripts.
    - **Self-Hosted Runners:** Replicating Jenkins’ on-prem agents requires setup and maintenance.
    - **Plugin Migration:** Replacing Jenkins plugins with Marketplace actions or custom steps.
    - **Complex Pipelines:** Converting intricate Jenkins jobs (e.g., multi-branch) to workflows.
    - **Cost:** GitHub Actions minutes may incur costs for private repos, unlike self-hosted Jenkins.
    - **Mitigation:** Start with simple workflows, use self-hosted runners, and leverage community actions.

68. **Your team wants to enforce that all PRs have passing tests before merging. How do you configure this?**
    - **Combine a workflow with branch protection rules.**
    - **Workflow:**
      ```yaml
      name: PR Tests
      on:
         pull_request:
            branches:
              - main
      jobs:
         test:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - run: npm test
      ```
    - **Branch Protection (Settings > Branches):**
      - Enable "Require status checks to pass before merging."
      - Add the test job as a required check.
    - **This blocks merging until tests pass.**

69. **How would you design a workflow to handle a multi-stage deployment (e.g., dev, staging, prod)?**
    - **Use separate jobs with dependencies and environment-specific triggers.**
    - **Example:**
      ```yaml
      name: Multi-Stage Deployment
      on:
         push:
            branches:
              - dev
              - staging
              - main
      jobs:
         test:
            runs-on: ubuntu-latest
            steps:
              - uses: actions/checkout@v4
              - run: npm test
         deploy-dev:
            needs: test
            if: github.ref == 'refs/heads/dev'
            runs-on: ubuntu-latest
            steps:
              - run: echo "Deploy to dev"
         deploy-staging:
            needs: test
            if: github.ref == 'refs/heads/staging'
            runs-on: ubuntu-latest
            steps:
              - run: echo "Deploy to staging"
         deploy-prod:
            needs: test
            if: github.ref == 'refs/heads/main'
            runs-on: ubuntu-latest
            steps:
              - run: echo "Deploy to prod"
      ```
    - **This ensures tests pass before deploying to the respective environment.**

70. **A workflow needs to run on both Windows and Linux runners. How do you ensure compatibility?**
    - **Use a matrix strategy and write cross-platform commands.**
    - **Example:**
      ```yaml
      name: Cross-Platform CI
      on: push
      jobs:
         build:
            runs-on: ${{ matrix.os }}
            strategy:
              matrix:
                 os: [ubuntu-latest, windows-latest]
            steps:
              - uses: actions/checkout@v4
              - name: Run script
                 run: |
                    if [ "${{ matrix.os }}" = "ubuntu-latest" ]; then
                      echo "Linux"
                    else
                      echo "Windows"
                    fi
                 shell: bash  # Use bash for consistency
      ```
    - **Tips:** Avoid OS-specific paths (e.g., `/` vs `\`), test scripts locally, and use conditional logic or portable tools (e.g., Node.js).