
# Jenkinsfile Pipeline Notes

## Overview
- **Pipeline as Code**: Utilizes Jenkinsfile to define pipeline configurations as text-based files.
- **Benefits**:
  - Enables automation and change tracking.
  - Stores pipeline definitions alongside project files in a repository.
  - Facilitates GitOps approach, making the repository the single source of truth.

## Key Concepts
- **Jenkinsfile**:
  - Captures pipeline definitions as code.
  - Can include configurations for tools, project options, triggers, and more.
  - Stored in a repository, allowing version control and team collaboration.
- **GitOps**:
  - Centralizes project components (application code, tool configurations, CI tools like Jenkins) in a repository.
  - Changes to pipelines can be reviewed, discussed, and merged via automated processes.

## Configuring Jenkins with SCM
- **Source Control Management (SCM)**:
  - Jenkins supports multiple SCM systems (e.g., Git, Subversion).
  - Git is the most popular choice, requiring the Git plugin.
- **Setup Steps**:
  1. Select "Pipeline script from SCM" in Jenkins UI.
  2. Choose SCM system (e.g., Git).
  3. Provide repository URL and credentials (if required).
  4. Specify the branch (e.g., change from `master` to `main` if needed).
  5. Define the script path to the Jenkinsfile (typically in the root or a subfolder).
  6. Save and build to execute the pipeline.

## Example Jenkinsfile
Below is a sample Jenkinsfile structure to illustrate pipeline configuration:

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Check out code from the repository
                git branch: 'main', url: 'https://your-repo-url.git'
            }
        }
        stage('Build') {
            steps {
                // Example build step
                echo 'Building the project...'
                // Add build commands here
            }
        }
        stage('Read Logs') {
            steps {
                // Example step to read logs
                echo 'Reading logs...'
                // Add log reading commands here
            }
        }
    }
}
```

## Practical Steps
- **Repository Configuration**:
  - Ensure the Jenkinsfile is in the repository root or specify its path in Jenkins.
  - Update branch specifier to match the repository’s default branch (e.g., `main`).
- **Accessing Repositories**:
  - For private repositories, add credentials in Jenkins.
  - Public repositories (like the exercise files repo) may not require credentials.
- **Building the Pipeline**:
  - Jenkins checks out the repository and uses the Jenkinsfile for project configuration.
  - Pipeline stages (e.g., Checkout, Read Logs) are executed as defined.

## Challenge
- Create a project pipeline using the exercise files repository.
- Review the logs to understand pipeline execution details.


# Connecting Jenkins to GitHub Notes

## Prerequisites
- Publicly accessible Jenkins server
- GitHub account

## Steps to Connect Jenkins to GitHub

### 1. Prepare GitHub Repository
- Create a new GitHub repository or use an existing one.
- Add a `Jenkinsfile` to the repository:
  - Copy the `Jenkinsfile` content (e.g., from exercise files).
  - In the GitHub repo, click **Add file** > **Create new file**.
  - Name the file `Jenkinsfile` and paste the contents.
  - Commit directly to the `main` branch.
- Copy the repository URL:
  - Click the green **Code** button, select the **HTTPS** tab, and copy the URL.

### 2. Configure Jenkins Job
- On the Jenkins server, click **New Item**.
- Enter a job name (e.g., `connect-jenkins-to-github`, matching the repo name).
- Select **Pipeline** and click **OK**.
- Configure the job:
  - **General Section**:
    - (Optional) Add the GitHub repo URL under **GitHub project** for a clickable link in the Jenkins interface.
  - **Build Triggers**:
    - Enable **GitHub hook trigger for GITScm polling** to allow Jenkins to respond to GitHub webhooks.
  - **Pipeline Section**:
    - Select **Pipeline script from SCM**.
    - Set SCM to **Git**.
    - Paste the repository URL.
    - Set **Branch Specifier** to `main`.
    - Keep **Script Path** as default (`Jenkinsfile`).
- Save and click **Build Now** to run an initial successful build (required for Jenkins to read repo configuration).

### 3. Set Up GitHub Webhook
- Copy the Jenkins server URL:
  - Right-click the Jenkins icon (top-left corner) and select **Copy Link Address**.
- In the GitHub repository:
  - Go to **Settings** > **Webhooks** > **Add webhook**.
  - Paste the Jenkins server URL in **Payload URL** and append `/github-webhook/` (e.g., `http://your-jenkins-server/github-webhook/`).
  - Set **Content Type** to `application/json`.
  - Select **Just the push event** for events to trigger the webhook.
  - Click **Add webhook**.
- Verify the webhook connection:
  - GitHub will ping the Jenkins server.
  - A green checkmark next to the webhook indicates a successful connection.
  - If it fails, edit or recreate the webhook and verify settings.

### 4. Test the Integration
- Make a change in the GitHub repo (e.g., edit the `README.md` file and commit to the `main` branch).
- Check Jenkins to confirm a new build is triggered automatically.
- Verify the build log shows the commit (e.g., updated `README.md`).

## Example Jenkinsfile (Reference)
Below is a sample `Jenkinsfile` structure that might be used:

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-username/connect-jenkins-to-github.git'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                // Add build steps here
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                // Add test steps here
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
                // Add deployment steps here
            }
        }
    }
}
```

## Troubleshooting
- Ensure the Jenkins server is publicly accessible.
- Verify the webhook URL ends with `/github-webhook/`.
- Confirm the initial Jenkins build is successful before webhook setup.
- Check GitHub webhook settings if no green checkmark appears.


# Running Scripts from Jenkins Pipeline Notes

## Overview
- Storing pipeline definitions and supporting scripts in a version control system (e.g., GitHub) enhances maintainability.
- Scripts can handle complex build commands, keeping the pipeline definition clear and easier to debug.

## Jenkins Build Steps for Running Scripts
- **sh**: Executes commands or scripts using the default shell on Linux, Unix, or macOS agents.
- **bat**: Executes commands or scripts on Windows systems.
- **dir**: Changes the working directory before executing steps. Uses a path relative to the workspace or an absolute path.
  - Example: `${WORKSPACE}` environment variable can be used for paths relative to the repo’s checkout directory.

## Path Considerations
- **Relative Path**: Use for scripts in the repository, matching the system’s expected format.
- **Absolute Path**: Required for scripts outside the workspace.
- Ensure paths align with the system running the command (e.g., Linux vs. Windows).

## Example Setup
- **Repository Structure**:
  - `Jenkinsfile` at the root of the GitHub repository (default location for Jenkins).
  - Supporting script (e.g., `fibonacci.sh`) in the repo, used to calculate a Fibonacci sequence.
- **Jenkinsfile Configuration**:
  - Pipeline with four stages:
    1. Make the script executable (e.g., `chmod +x`).
    2. Run the script using a relative path.
    3. Run the script using a full path.
    4. Change to the script’s directory using `dir` and run the script.

### Sample Jenkinsfile
```groovy
pipeline {
    agent any
    stages {
        stage('Make Script Executable') {
            steps {
                sh 'chmod +linkplain scripts/fibonacci.sh'
            }
        }
        stage('Run Script with Relative Path') {
            steps {
                sh './scripts/fibonacci.sh 60'
            }
        }
        stage('Run Script with Full Path') {
            steps {
                sh '${WORKSPACE}/scripts/fibonacci.sh 60'
            }
        }
        stage('Run Script in Directory') {
            steps {
                dir('scripts') {
                    sh './fibonacci.sh 60'
                }
            }
        }
    }
}
```

### Sample Script (`fibonacci.sh`)
```bash
#!/bin/bash
n=$1
a=0
b=1
for ((i=0; i<n; i++)); do
    echo $a
    fn=$((a + b))
    a=$b
    b=$fn
done
```

## Configuring the Jenkins Job
- Create a new Jenkins job:
  - Add the GitHub repository URL.
  - Set **Branch Specifier** to `main`.
  - Keep **Script Path** as default (`Jenkinsfile`).
- Run the job once to recognize parameters (if parameterized).
- Use **Build with Parameters** to input values (e.g., Fibonacci sequence number: `60`).
- Verify output for each stage to confirm consistent results.

## Viewing Pipeline Execution
- Check the pipeline steps in Jenkins to see how paths were interpreted:
  - Relative path: `./scripts/fibonacci.sh`
  - Full path: `${WORKSPACE}/scripts/fibonacci.sh`
  - Directory change: `dir('scripts')` followed by `./fibonacci.sh`

## Troubleshooting
- Ensure the script is executable (`chmod +x`).
- Verify paths are correct for the system (relative vs. absolute).
- Confirm the initial build runs successfully to load parameters.
- Check pipeline logs to ensure scripts produce identical outputs across stages.


# Adding a Status Badge to Markdown Files Notes

## Overview
- **Status Badges**: Dynamically generated images that display the build status (passing or failing) of a Jenkins job.
- Common Placement: Typically added to the `README.md` file in a GitHub repository, but can also be embedded in webpages or other online documents.

## Prerequisites
- Jenkins server with administrative access.
- GitHub repository with a `README.md` file (e.g., for the `fibonacci` job from a previous lesson).

## Steps to Add a Status Badge

### 1. Install the Embeddable Build Status Plugin
- Log into the Jenkins server.
- Navigate to **Manage Jenkins** > **Manage Plugins**.
- Select the **Available** tab and search for `embed` in the filter.
- Locate the **Embeddable Build Status Plugin**, select it, and click **Download now and install after restart**.
- Check **Restart Jenkins when installation is complete and no jobs are running** to apply the changes.

### 2. Configure the Status Badge
- After Jenkins restarts, navigate to the job (e.g., `fibonacci` job).
- On the job’s homepage, find the **Embeddable Build Status** option (enabled by the plugin).
- Select the **Markdown** option for embedding in a `README.md` file.
- Choose the **Unprotected** link for maximum viewing flexibility.
- Copy the provided Markdown snippet for the badge.

### 3. Add the Badge to the GitHub Repository
- In the GitHub repository, open the `README.md` file.
- Click the pencil icon to edit the file.
- Paste the Markdown snippet (e.g., below the header for visibility).
- Commit the changes directly to the `main` branch.

### Example Markdown Snippet
```markdown
[![Build Status](http://your-jenkins-server/job/fibonacci/buildStatus)](http://your-jenkins-server/job/fibonacci/)
```

## Verification
- After committing, the `README.md` file in the GitHub repository will display a badge indicating the build status (e.g., passing or failing).
- Ensure the badge reflects the correct status (e.g., a green badge for a passing build).

## Troubleshooting
- Verify the Embeddable Build Status Plugin is installed and Jenkins has restarted.
- Ensure the correct Markdown snippet is copied and pasted into the `README.md`.
- Check that the Jenkins server URL in the badge link is accessible.
- Confirm the job has run successfully to reflect an accurate build status.


# Challenge: Connect Jenkins to GitHub Notes

## Challenge Overview
- **Objective**: Connect a Jenkins pipeline to a GitHub repository to test an algorithm that calculates the value of Pi with every push and display the build status in the repository's `README.md` file.
- **Tasks**:
  1. Create a GitHub repository and add exercise files (including a `Jenkinsfile` and a Pi calculation script).
  2. Set up a Jenkins pipeline project to pull code from the repository and use the `Jenkinsfile`.
  3. Install the Embeddable Build Status Plugin and add a status badge to the `README.md`.
- **Estimated Time**: 15–20 minutes.

## Solution Steps

### 1. Create and Configure GitHub Repository
- Create a new GitHub repository (e.g., `pi-challenge`).
- Add exercise files:
  - `Jenkinsfile` at the root of the repository.
  - Pi calculation script (e.g., `algorithm/pi.sh`).
- Copy the repository URL:
  - Click the **Code** button, select the **HTTPS** tab, and copy the URL.

### 2. Set Up Jenkins Pipeline
- On the Jenkins server, click **New Item**.
- Name the job (e.g., `pi-challenge`, matching the repository name), select **Pipeline**, and click **OK**.
- Configure the job:
  - **General**:
    - (Optional) Add the GitHub repository URL under **GitHub project** for a clickable link.
  - **Build Triggers**:
    - Enable **GitHub hook trigger for GITScm polling** to allow webhook-triggered builds.
  - **Pipeline**:
    - Select **Pipeline script from SCM**.
    - Set SCM to **Git**.
    - Paste the repository URL.
    - Change **Branch Specifier** to `main`.
    - Keep **Script Path** as default (`Jenkinsfile`).
- Save and click **Build Now** to run an initial build (required for Jenkins to recognize the configuration).

### 3. Create GitHub Webhook
- Copy the Jenkins server URL:
  - Right-click the Jenkins icon (top-left corner) and select **Copy Link Address**.
- In the GitHub repository:
  - Go to **Settings** > **Webhooks** > **Add webhook**.
  - Paste the Jenkins server URL and append `/github-webhook/` (e.g., `http://your-jenkins-server/github-webhook/`).
  - Set **Content Type** to `application/json`.
  - Select **Just the push event**.
  - Click **Add webhook**.
- Verify the webhook:
  - GitHub pings the Jenkins server.
  - Refresh the page to confirm a successful connection (green checkmark).

### 4. Test the Build Trigger
- Modify the Pi calculation script (e.g., `algorithm/pi.sh`):
  - Follow instructions in the script (e.g., uncomment a line for a more precise Pi calculation and remove previous `echo` statements).
  - Example change:
    ```bash
    # Original
    echo "Approximate value of Pi: 3.14"
    echo "Another approximation: 22/7"
    # echo "Precise value of Pi: 3.141592653589793"

    # Modified
    echo "Precise value of Pi: 3.141592653589793"
    ```
  - Commit the change to the `main` branch with a message (e.g., "Improve calculation of pi").
- Verify in Jenkins:
  - A new build is triggered automatically.
  - Check the build log to confirm the commit message and updated script output.

### 5. Install Embeddable Build Status Plugin
- Navigate to **Manage Jenkins** > **Manage Plugins**.
- Select the **Available** tab, search for `embed`, and locate the **Embeddable Build Status Plugin**.
- Install the plugin and restart Jenkins (check **Restart Jenkins when installation is complete and no jobs are running**).

### 6. Add Status Badge to README
- In Jenkins, go to the `pi-challenge` job and select **Embeddable Build Status**.
- Copy the **Markdown** snippet (unprotected version) for the status badge.
- In the GitHub repository:
  - Edit `README.md` by clicking the pencil icon.
  - Paste the badge snippet at the top of the file.
  - Example:
    ```markdown
    [![Build Status](http://your-jenkins-server/job/pi-challenge/buildStatus)](http://your-jenkins-server/job/pi-challenge/)
    ```
  - Commit the change to the `main` branch.
- Verify:
  - The `README.md` displays a badge showing the build status (e.g., passing).
  - The commit triggers another Jenkins build.

### Sample Jenkinsfile
```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-username/pi-challenge.git'
            }
        }
        stage('Run Pi Calculation') {
            steps {
                sh 'chmod +x algorithm/pi.sh'
                sh './algorithm/pi.sh'
            }
        }
    }
}
```

### Sample Pi Script (`algorithm/pi.sh`)
```bash
#!/bin/bash
echo "Precise value of Pi: 3.141592653589793"
```

## Verification
- Confirm the webhook triggers a build on every push to the `main` branch.
- Ensure the status badge in `README.md` reflects the latest build status.
- Check Jenkins build logs to verify the Pi calculation script runs correctly.

## Troubleshooting
- Ensure the Jenkins server is publicly accessible for webhook connectivity.
- Verify the webhook URL ends with `/github-webhook/`.
- Confirm the initial Jenkins build is successful.
- Check that the Embeddable Build Status Plugin is installed and the Markdown snippet is correct.
- If the badge doesn’t display or builds don’t trigger, recheck the repository URL, branch specifier, and webhook settings.