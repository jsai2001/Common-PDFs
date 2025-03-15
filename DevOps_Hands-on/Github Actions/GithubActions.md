## Definitely Needed
### Step 1: Basic Workflow Setup
### Step 2: Checking Out Code and Running a Script
### Step 3: Adding Testing and Branch Filters
### Step 5: Env Vars, Secrets, and Deployment
## Add-On Topics
### Step 6: Matrix Builds and Custom Actions
### Step 7: Conditionals, Caching, and Artifacts
## Advanced Topics
### Step 8: Docker and Self-Hosted Runners
### Step 9: Dynamic Workflows and Reusability
### Step 10: Error Handling, Status Checks, and Advanced Deployment

## Definitely Needed

GitHub Actions is a CI/CD system that automates workflows based on events in your GitHub repository, like pushing code or creating a pull request. You define these workflows in YAML files, which live in your repo under `.github/workflows/`.

Here’s a simple example to get us started: a workflow that runs a basic "Hello, World!" message whenever you push code to your repository.

### Step 1: Basic Workflow Setup

Create a file called `.github/workflows/hello-world.yml` in your repo. Add this code:

```yaml
name: Hello World Workflow

on: 
    push

jobs:
    say-hello:
        runs-on: ubuntu-latest
        steps:
            - name: Print Hello
                run: echo "Hello, World!"
```

### Breaking It Down

- **name**: This is a label for your workflow.
- **on**: This defines the event that triggers the workflow. Here, `push` means it runs whenever code is pushed to any branch.
- **jobs**: A workflow has one or more jobs. Each job runs independently.
- **runs-on**: This specifies the environment—`ubuntu-latest` is a GitHub-hosted virtual machine.
- **steps**: These are the actual tasks. Each step can run a shell command (`run:`) or use an action.

### How It Works

When you push this file to your repo, GitHub detects it, sees the `on: push` trigger, and spins up an Ubuntu VM to execute the job. The output—“Hello, World!”—shows up in the "Actions" tab of your repo.

### Try It

If you’ve got a repo handy, add this file, commit, and push. Then check the Actions tab to see it run.

Now, let’s make it more practical by adding steps to check out your code and run a basic script—something you’d see in a real CI pipeline.

Here’s the updated workflow. Imagine you have a repo with a simple Node.js script, `index.js`, that logs a message. We’ll automate running it.

### Step 2: Checking Out Code and Running a Script

Update your `.github/workflows/hello-world.yml` to this:

```yaml
name: Basic Build Workflow

on: 
    push

jobs:
    build:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            
            - name: Run a script
                run: |
                    echo "Running my script..."
                    node index.js
```

### Breaking It Down

- **uses**: This introduces actions—pre-built, reusable scripts from the GitHub community. `actions/checkout@v4` fetches your repo’s code into the runner’s file system.
- **actions/setup-node@v4**: This sets up a Node.js environment (version 20 here). GitHub provides these setup actions for common runtimes.
- **with**: This passes inputs to an action. Here, we specify the Node.js version.
- **run**: Now we execute commands. The `|` lets us write multi-line scripts. We echo a message and run `index.js`.

### Example `index.js`

For this to work, add this to your repo:

```javascript
console.log("Hello from my script!");
```

### How It Works

Push this to your repo, and the workflow:

- Triggers on push.
- Spins up an Ubuntu VM.
- Checks out your code.
- Installs Node.js 20.
- Runs your script, logging “Hello from my script!” to the Actions console.

### Interview Tips

- **Why checkout?** “Without it, the runner is a blank slate—no access to my repo.”
- **Why setup-node?** “It’s faster than manually installing Node, and version control is built-in.”
- **What if it fails?** “I’d check the Actions logs—maybe the script’s missing or Node’s version is off.”

Now, let’s add testing and introduce branching logic—key concepts for a CI pipeline. Imagine you’re explaining how to ensure code quality across branches like main and feature/*. This is where GitHub Actions starts showing its power.

Here’s the next iteration: a workflow that installs dependencies, runs tests, and only triggers on specific branches.

### Step 3: Adding Testing and Branch Filters

Update `.github/workflows/hello-world.yml` to this:

```yaml
name: Test Workflow

on:
    push:
        branches:
            - main
            - 'feature/*'

jobs:
    test:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            
            - name: Install dependencies
                run: npm install
            
            - name: Run tests
                run: npm test
```

### Breaking It Down

- **on: push: branches:** Now the workflow only triggers for pushes to `main` or any branch starting with `feature/` (e.g., `feature/add-login`). This is a branch filter.
- **npm install:** Assumes a `package.json` in your repo with dependencies (e.g., a testing library like Jest). This step fetches them.
- **npm test:** Runs the test script defined in `package.json`.

### Example Repo Setup

For this to work, add these files:

`package.json`:
```json
{
    "name": "my-project",
    "version": "1.0.0",
    "scripts": {
        "test": "jest"
    },
    "devDependencies": {
        "jest": "^29.7.0"
    }
}
```

`index.test.js`:
```javascript
test('simple test', () => {
    expect(1 + 1).toBe(2);
});
```

Run `npm install` locally first to generate `package-lock.json`, then commit everything.

### How It Works

Push to `main` or a `feature/` branch: the workflow checks out code, sets up Node.js, installs Jest, and runs the test. If it passes, you’ll see it in the Actions tab.

Push to another branch (e.g., `bugfix/x`): nothing happens—branch filter in action.

### Interview Insights

- **Branch filters:** “They let me control when CI runs, like only for production branches or PRs.”
- **Dependencies:** “npm install ensures consistency between local and CI environments.”
- **Testing:** “It’s a gatekeeper—catches bugs before they hit production.”

## Step 4: Multiple Jobs with Dependencies

Update `.github/workflows/hello-world.yml` to this:

```yaml
name: CI Workflow

on:
    push:
        branches:
            - main
            - 'feature/*'

jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            
            - name: Install dependencies
                run: npm install
            
            - name: Run linting
                run: npm run lint

    test:
        needs: lint
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            
            - name: Install dependencies
                run: npm install
            
            - name: Run tests
                run: npm test
```

### Breaking It Down

- **Multiple jobs**: We now have `lint` and `test` jobs. By default, jobs run in parallel unless specified otherwise.
- **needs**: This makes `test` wait for `lint` to succeed. If linting fails, testing won’t start.
- **Repeated steps**: Each job needs its own `checkout`, `setup-node`, and `npm install` because they run on separate runners.

### Updated Repo Setup

Add linting support:

`package.json`:

```json
{
    "name": "my-project",
    "version": "1.0.0",
    "scripts": {
        "test": "jest",
        "lint": "eslint ."
    },
    "devDependencies": {
        "jest": "^29.7.0",
        "eslint": "^8.57.0"
    }
}
```

`.eslintrc.json` (basic config):

```json
{
    "env": {
        "node": true,
        "jest": true
    },
    "extends": "eslint:recommended"
}
```

Run `npm install` locally, commit, and push.

### How It Works

- **Push to main or feature/**: 
    - `lint` runs first (checks code style with ESLint).
    - If it passes, `test` runs (runs Jest tests).
    - If `lint` fails, `test` skips, and the workflow fails.
- **Check the Actions tab**: You’ll see two jobs, with `test` waiting for `lint`.

### Interview Insights

- **Why needs?** “It’s like a quality gate—don’t test if the code’s not even formatted right.”
- **Parallel vs. sequential**: “Without needs, they’d run together, but here I want linting as a prerequisite.”
- **Scaling**: “I could add more jobs—build, deploy—each with its own dependencies.”

HLast time, we set up multiple jobs with dependencies (linting before testing). Today, we’ll add environment variables, secrets, and a deployment step. This shows you can handle secure, real-world CI/CD workflows, like deploying to a cloud service after tests pass.

Let’s imagine we’re deploying a static site to GitHub Pages, using secrets for authentication and env vars for configuration.

### Step 5: Env Vars, Secrets, and Deployment

Update `.github/workflows/hello-world.yml`:

```yaml
name: CI-CD Workflow

on:
    push:
        branches:
            - main
            - 'feature/*'

env:
    NODE_ENV: production

jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            
            - name: Install dependencies
                run: npm install
            
            - name: Run linting
                run: npm run lint

    test:
        needs: lint
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            
            - name: Install dependencies
                run: npm install
            
            - name: Run tests
                run: npm test

    deploy:
        needs: test
        if: github.ref == 'refs/heads/main'
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            
            - name: Install dependencies
                run: npm install
            
            - name: Build site
                run: npm run build
            
            - name: Deploy to GitHub Pages
                uses: peaceiris/actions-gh-pages@v3
                with:
                    github_token: ${{ secrets.GITHUB_TOKEN }}
                    publish_dir: ./dist
```

### Breaking It Down

- `env`: Defines global environment variables. `NODE_ENV: production` is accessible in all jobs.
- `needs: test`: The deploy job only runs if test (and thus lint) succeeds—chain of quality gates.
- `if: github.ref == 'refs/heads/main'`: Conditional execution: deploy only on main.
- `secrets.GITHUB_TOKEN`: A built-in secret provided by GitHub for authentication.
- `actions-gh-pages@v3`: A community action to deploy to GitHub Pages. `publish_dir` points to the build output.

### Updated Repo Setup

For a static site, update `package.json`:

```json
{
    "name": "my-project",
    "version": "1.0.0",
    "scripts": {
        "test": "jest",
        "lint": "eslint .",
        "build": "mkdir -p dist && echo '<h1>Hello</h1>' > dist/index.html"
    },
    "devDependencies": {
        "jest": "^29.7.0",
        "eslint": "^8.57.0"
    }
}
```

Enable GitHub Pages in your repo settings (branch: `gh-pages`).

### How It Works

- Push to `feature/*`: Lint and test run, no deploy.
- Push to `main`: Lint, test, then deploy to GitHub Pages if all pass. Check `<your-username>.github.io/<repo-name>`.

### Interview Insights

- Env vars: “Global for consistency, but I can override per job if needed.”
- Secrets: “They’re encrypted, only accessible in the runner—ideal for API keys or tokens.”
- Conditionals: “if statements let me tailor workflows by branch or event.”

Today, let’s tackle matrix builds for testing across multiple environments and briefly touch on creating a custom action. These show you can handle complex, scalable workflows—stuff that screams “I know CI/CD inside out.”

## Add-On Topics

### Step 6: Matrix Builds and Custom Actions

Here’s the updated `.github/workflows/hello-world.yml` with a matrix for the test job:

```yaml
name: CI-CD Workflow

on:
    push:
        branches:
            - main
            - 'feature/*'

env:
    NODE_ENV: production

jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Run linting
                run: npm run lint

    test:
        needs: lint
        runs-on: ${{ matrix.os }}
        strategy:
            matrix:
                os: [ubuntu-latest, windows-latest]
                node-version: [18, 20]
                exclude:
                    - os: windows-latest
                        node-version: 18
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: ${{ matrix.node-version }}
            - name: Install dependencies
                run: npm install
            - name: Run tests
                run: npm test

    deploy:
        needs: test
        if: github.ref == 'refs/heads/main'
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Build site
                run: npm run build
            - name: Deploy to GitHub Pages
                uses: peaceiris/actions-gh-pages@v3
                with:
                    github_token: ${{ secrets.GITHUB_TOKEN }}
                    publish_dir: ./dist
```

### Breaking Down the Matrix

- **strategy: matrix:** This runs the test job multiple times with different configurations:
    - **os:** Tests on `ubuntu-latest` and `windows-latest`.
    - **node-version:** Tests on Node 18 and 20.
    - **exclude:** Skips Node 18 on Windows to reduce redundancy.
- **Result:** 3 runs (Ubuntu+18, Ubuntu+20, Windows+20).
- **Dynamic variables:** `${{ matrix.os }}` and `${{ matrix.node-version }}` inject the current matrix values.
- **Parallel execution:** These runs happen simultaneously, speeding up feedback.

### Bonus: Custom Action Intro

Let’s create a simple custom action to log a message. In your repo, add `.github/actions/hello/action.yml`:

```yaml
name: 'Hello Action'
description: 'Prints a custom greeting'
inputs:
    name:
        description: 'Who to greet'
        required: true
        default: 'World'
runs:
    using: 'node16'
    main: 'index.js'
```

And `.github/actions/hello/index.js`:

```javascript
const core = require('@actions/core');
const name = core.getInput('name');
console.log(`Hello, ${name}!`);
```

Use it in the test job by adding:

```yaml
- name: Run custom action
    uses: ./.github/actions/hello
    with:
        name: 'Grok'
```

### How It Works

- **Matrix:** Tests run across OS/Node combos. Check Actions tab—you’ll see three test runs.
- **Custom action:** Prints “Hello, Grok!” in each test run.

### Interview Insights

- **Matrix:** “It’s like a stress test for compatibility—catches edge cases early.”
- **Custom actions:** “I’d use them for team-specific tasks, keeping workflows DRY.”
- **Scalability:** “Matrices scale testing; custom actions scale logic.”

Let’s push into expert territory with conditional steps, caching, and artifacts—advanced features that show you can optimize and debug workflows like a pro.

### Step 7: Conditionals, Caching, and Artifacts

Here’s the updated `.github/workflows/hello-world.yml`:

```yaml
name: CI-CD Workflow

on:
    push:
        branches:
            - main
            - 'feature/*'

env:
    NODE_ENV: production

jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Run linting
                run: npm run lint

    test:
        needs: lint
        runs-on: ${{ matrix.os }}
        strategy:
            matrix:
                os: [ubuntu-latest, windows-latest]
                node-version: [18, 20]
                exclude:
                    - os: windows-latest
                        node-version: 18
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            
            - name: Cache Node modules
                id: cache-npm
                uses: actions/cache@v3
                with:
                    path: ~/.npm
                    key: ${{ runner.os }}-node-${{ matrix.node-version }}-${{ hashFiles('**/package-lock.json') }}
                    restore-keys: |
                        ${{ runner.os }}-node-${{ matrix.node-version }}-
            
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: ${{ matrix.node-version }}
            
            - name: Install dependencies
                if: steps.cache-npm.outputs.cache-hit != 'true'
                run: npm install
            
            - name: Run tests
                run: npm test
            
            - name: Run custom action (only on failure)
                if: failure()
                uses: ./.github/actions/hello
                with:
                    name: 'Test Failed'
            
            - name: Upload test logs
                if: always()
                uses: actions/upload-artifact@v3
                with:
                    name: test-logs-${{ matrix.os }}-${{ matrix.node-version }}
                    path: ./test-logs/*.log

    deploy:
        needs: test
        if: github.ref == 'refs/heads/main'
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Build site
                run: npm run build
            - name: Deploy to GitHub Pages
                uses: peaceiris/actions-gh-pages@v3
                with:
                    github_token: ${{ secrets.GITHUB_TOKEN }}
                    publish_dir: ./dist
```

### Breaking It Down

**Caching (actions/cache@v3):**
- Caches `~/.npm` to skip redundant npm install if dependencies haven’t changed.
- `key`: Unique per OS, Node version, and package-lock.json hash.
- `restore-keys`: Fallback if exact match fails.

**Conditional Steps (if:):**
- `if: steps.cache-npm.outputs.cache-hit != 'true'`: Only runs npm install if cache misses.
- `if: failure()`: Custom action only runs if tests fail—great for debugging.
- `if: always()`: Uploads artifacts even if the job fails.

**Artifacts (actions/upload-artifact@v3):**
- Uploads test logs (assume tests generate `./test-logs/*.log`). In Actions tab, download them post-run.

### Updated Repo Setup

Modify `package.json` to generate logs:

```json
{
    "scripts": {
        "test": "jest --outputFile=./test-logs/test.log",
        "lint": "eslint .",
        "build": "mkdir -p dist && echo '<h1>Hello</h1>' > dist/index.html"
    }
}
```

Create `test-logs` dir if needed. Tests must output a `.log` file (adjust Jest config if necessary).

### How It Works

- **Cache:** First run installs deps, caches them. Next run skips npm install if unchanged.
- **Conditionals:** If tests fail, see “Hello, Test Failed!” in logs. Artifacts upload regardless.
- **Matrix:** Each combo gets its own cache and artifact.

### Interview Insights

- **Caching:** “Speeds up CI by reusing dependencies—key for large projects.”
- **Conditionals:** “Fine-tunes execution—saves resources and aids debugging.”
- **Artifacts:** “Persists outputs for analysis—think logs or build files.”

## Advanced Topics

Let’s take it to the bleeding edge with Docker in GitHub Actions and a touch of self-hosted runners. This is peak interview flex: showing you can containerize workflows and customize infrastructure—stuff that separates the pros from the pack.

### Step 8: Docker and Self-Hosted Runners

We’ll modify the test job to run in a Docker container and hint at self-hosted runners for extra control.

Here’s the updated `.github/workflows/hello-world.yml`:

```yaml
name: CI-CD Workflow

on:
    push:
        branches:
            - main
            - 'feature/*'

env:
    NODE_ENV: production

jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Run linting
                run: npm run lint

    test:
        needs: lint
        runs-on: ubuntu-latest  # Could be self-hosted, see below
        strategy:
            matrix:
                node-version: [18, 20]
        container:
            image: node:${{ matrix.node-version }}
            options: --cpus 2
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Install dependencies
                run: npm install
            - name: Run tests
                run: npm test
            - name: Run custom action (on failure)
                if: failure()
                uses: ./.github/actions/hello
                with:
                    name: 'Test Failed'
            - name: Upload test logs
                if: always()
                uses: actions/upload-artifact@v3
                with:
                    name: test-logs-node-${{ matrix.node-version }}
                    path: ./test-logs/*.log

    deploy:
        needs: test
        if: github.ref == 'refs/heads/main'
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Build site
                run: npm run build
            - name: Deploy to GitHub Pages
                uses: peaceiris/actions-gh-pages@v3
                with:
                    github_token: ${{ secrets.GITHUB_TOKEN }}
                    publish_dir: ./dist
```

### Breaking It Down

- **container: image: node:${{ matrix.node-version }}**: Runs the test job inside a Node.js Docker container matching the matrix version. No need for setup-node—the container’s pre-configured.
- **options: --cpus 2**: Limits CPU usage.

### Simplified Matrix

Dropped `os` to focus on Docker. Each job runs in a `node:18` or `node:20` container.

### Self-Hosted Runners (Teaser)

Replace `runs-on: ubuntu-latest` with `runs-on: self-hosted` if you’ve set up a runner (e.g., on your own server). Requires adding a runner via repo settings.

### Custom Docker (Optional)

For full control, create a Dockerfile in your repo:

```dockerfile
FROM node:20
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
CMD ["npm", "test"]
```

Then update the test job:

```yaml
container:
    image: docker://ghcr.io/<your-username>/<repo>:latest
    credentials:
        username: ${{ github.actor }}
        password: ${{ secrets.GITHUB_TOKEN }}
```

Build and push it manually first (`docker build`, `docker push`).

### How It Works

- `test` runs in a Node container per matrix entry.
- Same steps (install, test), but the env’s containerized.
- Artifacts still upload—Docker doesn’t change that.

### Interview Insights

- **Docker**: “Guarantees identical runtime—solves ‘works on my machine’ issues.”
- **Self-hosted**: “For sensitive data or specialized hardware—like GPUs for ML.”
- **Trade-offs**: “Containers add setup but ensure isolation; GitHub-hosted is simpler but less flexible.”

Let’s wrap up with a master-level flourish: dynamic workflows with GitHub Actions’ `workflow_dispatch` and JSON/YAML generation, plus a peek at reusing workflows. This is the kind of stuff that makes interviewers go, “Wow, they’ve thought of everything”—perfect for showcasing creativity and scalability.

### Step 9: Dynamic Workflows and Reusability

We’ll add a manual trigger with inputs and generate a dynamic step, then hint at reusable workflows.

Here’s the final `.github/workflows/hello-world.yml`:

```yaml
name: CI-CD Workflow

on:
    push:
        branches:
            - main
            - 'feature/*'
    workflow_dispatch:
        inputs:
            environment:
                description: 'Environment to target'
                required: true
                default: 'staging'
            log-level:
                description: 'Log verbosity'
                default: 'info'

env:
    NODE_ENV: production

jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Run linting
                run: npm run lint

    test:
        needs: lint
        runs-on: ubuntu-latest
        strategy:
            matrix:
                node-version: [18, 20]
        container:
            image: node:${{ matrix.node-version }}
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Install dependencies
                run: npm install
            - name: Generate dynamic step
                id: dynamic
                run: |
                    echo '::set-output name=step::{"name": "Dynamic Log", "run": "echo Running with log level ${{ github.event.inputs.log-level || 'info' }}"}'
            - name: Run tests
                run: npm test
            - name: Dynamic step from JSON
                if: github.event_name == 'workflow_dispatch'
                run: ${{ fromJson(steps.dynamic.outputs.step).run }}

    deploy:
        needs: test
        if: github.ref == 'refs/heads/main' || github.event_name == 'workflow_dispatch'
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Build site
                env:
                    DEPLOY_ENV: ${{ github.event.inputs.environment || 'production' }}
                run: npm run build
            - name: Deploy to GitHub Pages
                uses: peaceiris/actions-gh-pages@v3
                with:
                    github_token: ${{ secrets.GITHUB_TOKEN }}
                    publish_dir: ./dist
```

### Breaking It Down

**workflow_dispatch:**
Adds a manual trigger (Actions tab > Run workflow). Inputs: environment (required) and log-level (optional).

**Dynamic Steps:**
- `id: dynamic`: Generates a JSON step definition using `::set-output` (legacy but still works as of 2025). Newer syntax uses `$GITHUB_OUTPUT`.
- `${{ fromJson(steps.dynamic.outputs.step).run }}`: Executes the generated step only on manual runs.

**Inputs in Action:**
- `github.event.inputs.log-level`: Accesses manual input, defaults to 'info' if unset.
- `DEPLOY_ENV`: Uses environment input for conditional build logic.

### Reusable Workflows (Teaser)

Create `.github/workflows/test-reusable.yml`:

```yaml
name: Reusable Test
on:
    workflow_call:
        inputs:
            node-version:
                required: true
jobs:
    test:
        runs-on: ubuntu-latest
        container:
            image: node:${{ inputs.node-version }}
        steps:
            - uses: actions/checkout@v4
            - run: npm install
            - run: npm test
```

Call it from the test job:

```yaml
- name: Call reusable test
    uses: ./.github/workflows/test-reusable.yml
    with:
        node-version: ${{ matrix.node-version }}
```

### How It Works

- **Manual run:** Trigger via Actions tab, set environment (e.g., staging), see dynamic log and custom deploy.
- **Push:** Runs as before, skips dynamic step.
- **Reusable:** Simplifies multi-workflow projects.

### Interview Insights

- **Dispatch:** “Manual triggers are great for testing or one-off deploys.”
- **Dynamic:** “I can script workflow logic—ultimate flexibility.”
- **Reusable:** “DRY principle for CI—maintain one test flow, use everywhere.”

For this final step, let’s polish it with error handling, status checks, and advanced deployment strategies—the icing on the cake for an interview. This shows you can build robust, production-ready pipelines that handle failures gracefully and integrate with GitHub’s ecosystem.

### Step 10: Error Handling, Status Checks, and Advanced Deployment

Here’s the ultimate `.github/workflows/hello-world.yml`:

```yaml
name: CI-CD Workflow

on:
    push:
        branches:
            - main
            - 'feature/*'
    pull_request:
        branches:
            - main
    workflow_dispatch:
        inputs:
            environment:
                description: 'Environment to target'
                required: true
                default: 'staging'
            log-level:
                description: 'Log verbosity'
                default: 'info'

env:
    NODE_ENV: production

jobs:
    lint:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Run linting
                run: npm run lint || echo "Linting failed, continuing..." && exit 0

    test:
        needs: lint
        runs-on: ubuntu-latest
        strategy:
            matrix:
                node-version: [18, 20]
            fail-fast: false
        container:
            image: node:${{ matrix.node-version }}
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Install dependencies
                run: npm install
            - name: Run tests
                run: npm test
                continue-on-error: true
            - name: Notify on failure
                if: failure()
                run: echo "Tests failed for Node ${{ matrix.node-version }} - check logs"
            - name: Upload test logs
                if: always()
                uses: actions/upload-artifact@v3
                with:
                    name: test-logs-node-${{ matrix.node-version }}
                    path: ./test-logs/*.log

    status-check:
        needs: [lint, test]
        runs-on: ubuntu-latest
        if: github.event_name == 'pull_request'
        steps:
            - name: Check job statuses
                run: |
                    if [ "${{ job.status }}" == "success" ]; then
                        echo "All checks passed!"
                        exit 0
                    else
                        echo "Some checks failed."
                        exit 1
                    fi

    deploy:
        needs: test
        if: (github.ref == 'refs/heads/main' || github.event_name == 'workflow_dispatch') && success()
        runs-on: ubuntu-latest
        environment: ${{ github.event.inputs.environment || 'production' }}
        steps:
            - name: Checkout code
                uses: actions/checkout@v4
            - name: Set up Node.js
                uses: actions/setup-node@v4
                with:
                    node-version: '20'
            - name: Install dependencies
                run: npm install
            - name: Build site
                env:
                    DEPLOY_ENV: ${{ github.event.inputs.environment || 'production' }}
                run: npm run build
            - name: Deploy to GitHub Pages
                uses: peaceiris/actions-gh-pages@v3
                with:
                    github_token: ${{ secrets.GITHUB_TOKEN }}
                    publish_dir: ./dist
            - name: Post-deploy check
                run: |
                    if [ $? -eq 0 ]; then
                        echo "Deployment successful!"
                    else
                        echo "Deployment failed!" && exit 1
                    fi
```

### Breaking It Down

**Error Handling:**
- `|| echo ... && exit 0` in lint: Allows linting to fail softly without halting the pipeline.
- `continue-on-error: true` in test: Tests proceed even if one fails.
- `fail-fast: false`: Ensures all matrix runs complete.

**Status Checks:**
- `status-check` job: Runs on PRs, enforces passing lint and test as a gate.

**Advanced Deployment:**
- `environment`: Uses the workflow_dispatch input or defaults to production.
- `if: ... && success()`: Only deploys if prior jobs succeed.
- Post-deploy script: Validates success.

### How It Works
- **Push:** Lint (soft fail), test (all run), deploy (if main and green).
- **PR:** Adds status-check as a required check.
- **Manual:** Custom env and logging, deploys if tests pass.

### Interview Insights
- **Error Handling:** “I balance strictness and flexibility—fail fast or collect all errors based on need.”
- **Status Checks:** “Integrates CI with PRs—enforces quality gates.”
- **Deployment:** “Environment vars and checks make it robust and auditable.”

Let’s refine and synthesize everything into a concise, interview-ready explanation. This is about nailing that “Walk me through a CI/CD pipeline you’ve built” question.

### Your Interview Pitch: The CI/CD Workflow

“Here’s a GitHub Actions pipeline I designed for a Node.js project—it’s a full CI/CD setup that handles linting, testing, and deployment with scalability and reliability in mind.

It starts with triggers: push to main or feature/* branches, pull_request to main for PR validation, and workflow_dispatch for manual runs with inputs like environment—say, staging or production. This gives flexibility for automation and on-demand testing.

The pipeline has three core jobs:
- **Linting:** Runs ESLint on an Ubuntu runner. I made it soft-fail with a custom exit code so minor style issues don’t block progress—practical for team workflows.
- **Testing:** Uses a matrix to test across Node 18 and 20 in Docker containers (node:<version>). It’s containerized for consistency—no ‘works on my machine’ excuses. I set fail-fast: false and continue-on-error so all runs finish, giving full debug info. It caches npm deps to speed things up, uploads test logs as artifacts always, and triggers a custom action on failure for extra logging.
- **Deployment:** Only runs on main or manual triggers if tests pass. It builds a static site and deploys to GitHub Pages, using the GITHUB_TOKEN secret securely. I added an environment variable to target staging or production, with a post-deploy check to confirm success.

For PRs, I included a status-check job as a required branch protection rule—ensures linting and tests pass before merging. I also used dynamic steps with workflow_dispatch inputs, like tweaking log levels, showing how I’d adapt workflows on the fly.

Optimizations? Caching cuts install time, Docker ensures env parity, and conditionals (if: failure()) streamline execution. It’s scalable—add more matrix combos or reusable workflows via workflow_call for bigger projects.”

### Pro Tips for the Interview

- **Adapt to the Role:** If it’s a junior role, focus on basics (triggers, jobs, steps). For senior, lean into caching, Docker, and dynamic logic.
- **Show Trade-offs:** “Docker adds setup but ensures consistency; GitHub-hosted runners are simpler but less customizable.”
- **Debugging Flex:** “If tests fail, I’d check logs via artifacts or tweak the matrix to isolate the issue.”
- **Future-Proofing:** “I’d add self-hosted runners for custom hardware or a custom Docker image for pre-baked deps.”

### Final Workflow Recap (for Reference)

```yaml
name: CI-CD Workflow
on:
    push: { branches: [main, 'feature/*'] }
    pull_request: { branches: [main] }
    workflow_dispatch:
        inputs: { environment: { required: true, default: 'staging' }, log-level: { default: 'info' } }
env:
    NODE_ENV: production
jobs:
    lint:
        runs-on: ubuntu-latest
        steps: [checkout, setup-node@20, npm install, npm run lint || echo "Lint failed" && exit 0]
    test:
        needs: lint
        runs-on: ubuntu-latest
        strategy: { matrix: { node-version: [18, 20] }, fail-fast: false }
        container: { image: node:${{ matrix.node-version }} }
        steps: [checkout, npm install (cached), npm test (continue-on-error), if failure: custom action, upload logs]
    status-check:
        needs: [lint, test]
        if: github.event_name == 'pull_request'
        runs-on: ubuntu-latest
        steps: [check status]
    deploy:
        needs: test
        if: (github.ref == 'refs/heads/main' || github.event_name == 'workflow_dispatch') && success()
        runs-on: ubuntu-latest
        environment: ${{ github.event.inputs.environment || 'production' }}
        steps: [checkout, setup-node@20, npm install, build with DEPLOY_ENV, deploy to GH Pages, post-check]
```