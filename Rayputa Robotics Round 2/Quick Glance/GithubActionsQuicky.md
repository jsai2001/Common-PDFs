Here’s a revised version of the GitHub Actions interview preparation questions with concise, straightforward, and impactful answers. All questions remain intact, and code blocks are preserved for clarity where provided. The goal is to make the content digestible without losing depth.

## Basic Concepts

### What are GitHub Actions, and how do they differ from traditional CI/CD tools like Jenkins or CircleCI?
GitHub Actions is a built-in GitHub tool for automating workflows (build, test, deploy) using YAML files triggered by repo events. Unlike Jenkins (standalone, plugin-heavy, flexible) or CircleCI (cloud-focused, orb-based), it’s tightly integrated with GitHub, simpler to set up, and uses a marketplace for reusable actions.

### What is a workflow in GitHub Actions, and how is it defined?
A workflow is an automated process with jobs triggered by events. It’s defined in a YAML file in `.github/workflows`, specifying name, triggers (`on`), and jobs.

### What is the purpose of the `.github/workflows` directory?
It holds all workflow YAML files, acting as the hub for GitHub to find and run automation scripts when events occur.

### What are the key components of a GitHub Actions workflow file?  
- **name**: Workflow label.  
- **on**: Event triggers (e.g., push).  
- **jobs**: Tasks to run, with `runs-on` (runner type) and steps (actions).

### What is an "event" in GitHub Actions, and examples of common ones?
An event triggers a workflow. Examples: `push` (code push), `pull_request` (PR activity), `schedule` (cron-based), `workflow_dispatch` (manual).

### What’s the difference between a "job" and a "step"?
A job is a group of steps running on one runner; steps are individual tasks (e.g., checkout, run script) within a job. Jobs can run in parallel, steps run sequentially.

### What are "actions" in GitHub Actions, and how do you use pre-built ones?
Actions are reusable task units. Pre-built ones from the Marketplace (e.g., `actions/checkout@v4`) are used with `uses` in steps:
```yaml
steps:
    - uses: actions/checkout@v4
```

### What’s the role of runners, and what types are available?
Runners execute workflows. Types: GitHub-hosted (`ubuntu-latest`, `windows-latest`, `macos-latest`) or self-hosted (custom machines).

### How does GitHub Actions handle secrets, and why is it important?
Secrets are encrypted variables (e.g., `${{ secrets.API_KEY }}`) set in GitHub Settings. They’re vital to securely handle sensitive data like API keys without exposure.

### What file format is used for workflows, and why?
YAML—readable, simple, and widely used in CI/CD for structured automation.

## Workflow Syntax and Configuration

### How do you specify events to trigger a workflow?
Use `on`:
```yaml
on:
    push:
    pull_request:
    schedule:
        - cron: '0 0 * * *'
```

### What does `runs-on` do, and common values?
Sets the runner type. Common: `ubuntu-latest`, `windows-latest`, `macos-latest`, or `self-hosted`.

### How do you define environment variables?
Use `env` at workflow, job, or step level:
```yaml
env:
    GLOBAL: "value"
jobs:
    build:
        env:
            JOB: "job-value"
        steps:
            - env:
                    STEP: "step-value"
                run: echo $STEP
```

### What’s the `if` conditional, and how’s it used?
Controls execution with expressions:
```yaml
steps:
    - if: github.event_name == 'push'
        run: echo "Push only"
```

### How do you set up a matrix strategy?
Run jobs across configs with `strategy.matrix`:
```yaml
jobs:
    test:
        runs-on: ${{ matrix.os }}
        strategy:
            matrix:
                os: [ubuntu-latest, windows-latest]
                node: [14, 16]
```

### What’s the `needs` keyword for?
Ensures job dependencies:
```yaml
jobs:
    build:
    deploy:
        needs: build
```

### How do you pass outputs between steps?
Set outputs with `$GITHUB_OUTPUT`:
```yaml
steps:
    - id: step1
        run: echo "output=hello" >> $GITHUB_OUTPUT
    - run: echo ${{ steps.step1.outputs.output }}
```

### What’s the `env` context vs. `github` or `secrets`?
- **env**: Custom variables (`${{ env.VAR }}`).  
- **github**: Event data (`${{ github.sha }}`).  
- **secrets**: Secure data (`${{ secrets.KEY }}`).

### How do you run a workflow on a specific branch or tag?
Filter with `on`:
```yaml
on:
    push:
        branches:
            - main
    release:
        tags:
            - 'v*'
```

### What’s `timeout-minutes`, and why use it?
Cancels jobs/steps after a time limit:
```yaml
jobs:
    build:
        timeout-minutes: 10
```
Prevents hangs.

## Practical Use Cases

### Workflow to run tests on push to main?
```yaml
on:
    push:
        branches:
            - main
jobs:
    test:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: npm install && npm test
```

### Deploy to AWS?
```yaml
on:
    push:
        branches:
            - main
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - uses: aws-actions/configure-aws-credentials@v4
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: us-east-1
            - run: aws s3 sync ./dist/ s3://my-bucket/
```

### Build and push Docker image?
```yaml
on:
    push:
        branches:
            - main
jobs:
    build:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - uses: docker/login-action@v3
                with:
                    username: ${{ secrets.DOCKER_USERNAME }}
                    password: ${{ secrets.DOCKER_PASSWORD }}
            - uses: docker/build-push-action@v5
                with:
                    push: true
                    tags: user/app:latest
```

### Automate GitHub Release?
```yaml
on:
    push:
        tags:
            - 'v*'
jobs:
    release:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - uses: actions/create-release@v1
                env:
                    GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
                with:
                    tag_name: ${{ github.ref_name }}
                    release_name: Release ${{ github.ref_name }}
```

### Lint and comment on PR?
```yaml
on: pull_request
jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: npm install
            - id: lint
                run: npm run lint -- --output-file eslint-report.txt || echo "LINT_FAILED=true" >> $GITHUB_ENV
            - if: env.LINT_FAILED == 'true'
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

### Schedule a daily task?
```yaml
on:
    schedule:
        - cron: '0 0 * * *'
jobs:
    task:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: ./my-script.sh
```

### Run only on `.py` file changes?
```yaml
on:
    push:
        paths:
            - '**/*.py'
jobs:
    check:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: python -m py_compile *.py
```

### Manual workflow trigger?
```yaml
on:
    workflow_dispatch:
        inputs:
            message:
                required: true
jobs:
    run:
        runs-on: ubuntu-latest
        steps:
            - run: echo ${{ github.event.inputs.message }}
```

### Integration tests across Node.js versions?
```yaml
on: push
jobs:
    test:
        runs-on: ubuntu-latest
        strategy:
            matrix:
                node-version: [14, 16, 18]
        steps:
            - uses: actions/checkout@v4
            - uses: actions/setup-node@v4
                with:
                    node-version: ${{ matrix.node-version }}
            - run: npm install && npm run integration-tests
```

### Send Slack notifications?
```yaml
on: push
jobs:
    notify:
        runs-on: ubuntu-latest
        steps:
            - uses: actions/checkout@v4
            - run: echo "Running"
            - if: success() || failure()
                uses: slackapi/slack-github-action@v1.26.0
                with:
                    slack-bot-token: ${{ secrets.SLACK_BOT_TOKEN }}
                    channel-id: 'my-channel'
                    text: 'Workflow ${{ job.status }}!'
```

## Advanced Topics

### What are composite actions, and how to create one?
Composite actions bundle steps into a reusable unit. Create in `.github/actions/my-action/action.yml`:
```yaml
name: 'My Action'
runs:
    using: 'composite'
    steps:
        - run: echo "Step 1"
            shell: bash
        - run: echo "Step 2"
            shell: bash
```
Use: `uses: ./.github/actions/my-action`.

### Reusable workflow and how to call it?
Define in `.github/workflows/reusable.yml`:
```yaml
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
Call:
```yaml
jobs:
    call:
        uses: ./.github/workflows/reusable.yml
        with:
            param: "Hello"
```

### GITHUB_TOKEN vs. PAT?
- **GITHUB_TOKEN**: Auto-generated, repo-scoped, expires after run.  
- **PAT**: User-created, customizable scope, persistent.

# GitHub Actions Workflow Guide

## Debugging a Failing Workflow
- **Steps to debug**: Check logs, add `echo` steps, enable `ACTIONS_STEP_DEBUG`, re-run, or test locally with `act`.

## Optimizing Workflow Performance
- **Tips**:
    - Cache dependencies.
    - Parallelize jobs.
    - Skip steps with `if`.
    - Optimize commands:
        ```yaml
        - uses: actions/cache@v3
            with:
                path: ~/.npm
                key: npm-${{ hashFiles('package-lock.json') }}
        ```

## Cache Action Use Cases
- **How it works**: Stores files using a key; restores if matched.
- **Example**:
    ```yaml
    - uses: actions/cache@v3
        with:
            path: ~/.m2/repository
            key: maven-${{ hashFiles('pom.xml') }}
    ```
- **Use cases**: Cache npm, Maven, etc.

## Securing a Workflow
- **Best practices**:
    - Limit permissions.
    - Pin actions.
    - Secure secrets.
    - Control concurrency:
        ```yaml
        permissions:
            contents: read
        ```

## Permissions Key
- **Purpose**: Sets `GITHUB_TOKEN` scope.
- **Example**:
    ```yaml
    permissions:
        contents: read
        issues: write
    ```

## Self-Hosted Runners
- **Setup**: Go to `Settings > Runners`, use `runs-on: self-hosted`.
- **Pros**: Custom setup, internal access.
- **Cons**: Maintenance, no GitHub support.

## Handling Long-Running Workflows
- **Tips**:
    - Increase `timeout-minutes`.
    - Split jobs.
    - Use self-hosted runners.
    - Optimize:
        ```yaml
        jobs:
            long:
                timeout-minutes: 720
        ```

## Troubleshooting and Edge Cases
- **"No runner available" fix**: Check `runs-on` value, ensure self-hosted runners are online, or verify quotas.
- **Intermittent network failures**: Add retries:
    ```yaml
    - run: |
            for i in {1..3}; do curl http://example.com && break || sleep 5; done
    ```
- **Two workflows triggered at once**: Use concurrency:
    ```yaml
    concurrency:
        group: ${{ github.ref }}
        cancel-in-progress: true
    ```
- **Skip workflow for commits**: Add `[skip ci]` in commit message or:
    ```yaml
    if: "!contains(github.event.head_commit.message, '[skip ci]')"
    ```
- **"Missing permissions" fix**: Add permissions:
    ```yaml
    permissions:
        issues: write
    ```
- **Troubleshoot no expected output**: Check logs, add debug steps, validate logic:
    ```yaml
    - run: echo "Output: $(cat file.txt)"
    ```
- **Secret exposed in logs**: Rotate secret, use `env` to avoid printing:
    ```yaml
    - run: deploy.sh
        env:
            KEY: ${{ secrets.KEY }}
    ```
- **External service down**: Retry, use `continue-on-error`, or notify:
    ```yaml
    - continue-on-error: true
        run: curl http://service || echo "Down"
    ```

## Continue-on-Error
- **Definition**: Ignores step failure.
- **Example**:
    ```yaml
    - continue-on-error: true
        run: exit 1
    ```
- **Use case**: Optional steps.

## Testing Workflow Locally
- **Tool**: Use `act`: `act -W .github/workflows/my-workflow.yml`.

## Integration and Ecosystem
- **GitHub Packages integration**:
    ```yaml
    - uses: actions/setup-node@v4
        with:
            registry-url: https://npm.pkg.github.com
    - run: npm publish
        env:
            NODE_AUTH_TOKEN: ${{ secrets.GITHUB_TOKEN }}
    ```
- **Deploy to GitHub Pages**:
    ```yaml
    on: push
    jobs:
        deploy:
            runs-on: ubuntu-latest
            steps:
                - uses: actions/checkout@v4
                - run: npm run build
                - uses: actions/upload-pages-artifact@v3
                    with:
                        path: ./dist
                - uses: actions/deploy-pages@v4
            permissions:
                pages: write
                id-token: write
    ```
- **Work with Terraform**:
    ```yaml
    - uses: hashicorp/setup-terraform@v3
    - run: terraform init && terraform apply -auto-approve
        env:
            AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}
    ```

## Popular Marketplace Actions
- `actions/checkout`: Clones repo.
- `actions/setup-node`: Sets up Node.js.
- `actions/cache`: Caches files.

## Enforcing Code Quality
- **ESLint example**:
    ```yaml
    on: pull_request
    jobs:
        lint:
            runs-on: ubuntu-latest
            steps:
                - uses: actions/checkout@v4
                - run: npm install && npx eslint .
    ```

## Multi-Language Support
- **Example**:
    ```yaml
    steps:
        - uses: actions/setup-node@v4
        - run: npm test
        - uses: actions/setup-python@v5
        - run: pytest
    ```

## Monorepo Setup
- **Example**:
    ```yaml
    on:
        push:
            paths:
                - 'packages/app/**'
    jobs:
        app:
            steps:
                - run: cd packages/app && npm test
    ```

## Dependabot Integration
- **Example**:
    ```yaml
    on: pull_request
    jobs:
        test:
            if: github.actor == 'dependabot[bot]'
            steps:
                - run: npm test
    ```

## Code/Secret Scanning
- **CodeQL example**:
    ```yaml
    - uses: github/codeql-action/analyze@v3
    ```

## Open-Source Role
- **Purpose**: Automates testing, PR validation, and releases for contributors.

## Scenario-Based Questions
- **Speed up a 20-min workflow**: Cache, parallelize, skip steps:
    ```yaml
    - uses: actions/cache@v3
        with:
            path: ~/.npm
            key: npm-${{ hashFiles('package-lock.json') }}
    ```
- **Secret committed**: Remove from history, rotate, use `env`:
    ```yaml
    - run: deploy.sh
        env:
            API_KEY: ${{ secrets.API_KEY }}
    ```
- **PR vs. push steps**:
    ```yaml
    steps:
        - if: github.event_name == 'pull_request'
            run: npm test
        - if: github.event_name == 'push'
            run: npm run deploy
    ```
- **Deprecated action**: Update to newer version or replicate manually:
    ```yaml
    - uses: actions/setup-node@v4
    ```
- **Deploy after tests**:
    ```yaml
    jobs:
        test:
            steps:
                - run: npm test
        deploy:
            needs: test
            steps:
                - run: npm run deploy
    ```
- **Auto-bump version**:
    ```yaml
    on: workflow_dispatch
    jobs:
        release:
            steps:
                - run: npm version patch && git push --follow-tags
                - uses: actions/create-release@v1
                    env:
                        GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
    ```
- **Jenkins to GitHub Actions challenges**: YAML learning, runner setup, plugin migration, cost for private repos.
- **PR tests before merge**: Workflow + branch protection:
    ```yaml
    on: pull_request
    jobs:
        test:
            steps:
                - run: npm test
    ```
- **Multi-stage deployment**:
    ```yaml
    jobs:
        test:
            steps:
                - run: npm test
        deploy-dev:
            needs: test
            if: github.ref == 'refs/heads/dev'
            steps:
                - run: echo "Deploy dev"
    ```
- **Windows + Linux compatibility**:
    ```yaml
    strategy:
        matrix:
            os: [ubuntu-latest, windows-latest]
    steps:
        - run: echo "Cross-platform"
            shell: bash
    ```
```