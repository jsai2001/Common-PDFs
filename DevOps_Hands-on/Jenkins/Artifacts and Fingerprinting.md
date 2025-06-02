
# Jenkins Artifacts and Fingerprinting Notes

## Artifacts
- **Definition**: Objects created by a Jenkins job that need to be saved, referred to as artifacts.
- **Examples**: 
  - Compiled binaries (e.g., Docker images, ZIP files).
  - Text files (e.g., reports, documents).
- **Management**: Jenkins provides multiple methods to manage artifacts.

## Archive Artifacts
- **Function**: `archiveArtifacts` is a core Jenkins function used as a build step to identify and save files during or after a build.
- **Placement**: Typically placed in the `post` section of a pipeline, which executes after all other pipeline stages and steps.
- **Example Code**:
  ```groovy
  pipeline {
      agent any
      stages {
          // Pipeline stages
      }
      post {
          always {
              archiveArtifacts artifacts: 'report.txt', fingerprint: true
          }
      }
  }
  ```
  - Archives `report.txt` and creates a fingerprint for tracking.

## Copy Artifact Plugin
- **Purpose**: Allows one job to access artifacts created by another job.
- **Build Step**: Provides a `CopyArtifacts` build step to pull artifacts from another job.
- **Security**: The job creating the artifact must explicitly grant permission to the job that will copy it.
- **Example Code (Create Artifact Job)**:
  ```groovy
  pipeline {
      agent any
      options {
          copyArtifactPermission('read-artifact-job')
      }
      stages {
          stage('Create Report') {
              steps {
                  writeFile file: 'report.txt', text: 'Sample report content'
              }
          }
      }
      post {
          always {
              archiveArtifacts artifacts: 'report.txt', fingerprint: true
          }
      }
  }
  ```
  - Grants permission to `read-artifact-job` and archives `report.txt`.

- **Example Code (Read Artifact Job)**:
  ```groovy
  pipeline {
      agent any
      stages {
          stage('Copy Artifact') {
              steps {
                  copyArtifacts projectName: 'create-artifact-job', selector: specific('lastSuccessful'), target: 'copied_files', fingerprintArtifacts: true
              }
          }
      }
  }
  ```
  - Copies `report.txt` from `create-artifact-job` and fingerprints it for tracking.

## Fingerprinting
- **Purpose**: Tracks artifacts to determine which jobs created or accessed them.
- **Process**: 
  - Jenkins generates an MD5 checksum for each artifact.
  - The checksum and job details are stored in an internal database as the artifact's fingerprint.
- **Usage**: 
  - Enables tracking of artifact creation and usage across jobs.
  - View fingerprints in Jenkins UI by navigating to a build, selecting "See Fingerprints," and clicking the artifact (e.g., `report.txt`).
- **Benefits**: Provides visibility into artifact usage, showing the MD5 value and a list of jobs that used the artifact.

## Demonstration Notes
- **Prerequisites**: Ensure the **Copy Artifacts plugin** is installed for `CopyArtifacts` build step functionality.
- **UI Interaction**:
  - Artifacts are indicated by an icon in the build’s copy page.
  - Hovering over the icon allows downloading or viewing artifact contents.
  - Fingerprint details are accessible via the "See Fingerprints" option in the build details.

## Key Takeaways
- Use `archiveArtifacts` in the `post` section to save and fingerprint artifacts.
- Use the Copy Artifact plugin to securely share artifacts between jobs.
- Fingerprinting ensures traceability of artifact creation and usage across Jenkins jobs.


# Jenkins Test Results and Code Coverage Notes

## Overview of Testing in Jenkins
- **Importance**: Testing is critical in the continuous integration pipeline to identify and fix bugs before production.
- **Tools**: Numerous testing tools exist, many of which use common report formats like JUnit for test results and JaCoCo/Cobertura for code coverage.

## JUnit Reports
- **Definition**: XML documents that detail test results, originally developed for Java but widely adopted across languages.
- **Jenkins JUnit Plugin**:
  - Collects test reports.
  - Publishes reports as graphs.
  - Tracks trends in test results.
- **Usage**: Standard format for test reporting in Jenkins.

## Code Coverage
- **Definition**: Measures lines of code executed during tests to assess test effectiveness.
- **Purpose**: Higher code coverage increases the likelihood of discovering bugs.
- **Formats**:
  - **JaCoCo**: Popular for Java applications, also supported by other languages.
  - **Cobertura**: Another widely used format for code coverage reports.
- **Jenkins Code Coverage API Plugin**:
  - Collects and publishes reports in multiple formats (e.g., JaCoCo, Cobertura).

## Demonstration: Python Project Pipeline
- **Pipeline Structure**: Five stages:
  1. **Setup**: Installs project dependencies.
  2. **Linting**: Checks code for syntax errors.
  3. **Testing**: Runs tests, generates JUnit test report and Cobertura code coverage report.
  4. **Build**: Builds the application.
  5. **Deploy**: Deploys the application.
- **Example Pipeline Code**:
  ```groovy
  pipeline {
      agent any
      stages {
          stage('Setup') {
              steps {
                  sh 'pip install -r requirements.txt'
              }
          }
          stage('Lint') {
              steps {
                  sh 'pylint *.py'
              }
          }
          stage('Test') {
              steps {
                  sh 'pytest --junitxml=report.xml --cov=. --cov-report=xml:coverage.xml'
              }
              post {
                  always {
                      junit 'report.xml'
                      publishCoverage adapters: [coberturaAdapter('coverage.xml')]
                  }
              }
          }
          stage('Build') {
              steps {
                  sh 'python setup.py build'
              }
          }
          stage('Deploy') {
              steps {
                  sh 'echo Deploying application'
              }
          }
      }
  }
  ```
  - **Test Stage**: Runs `pytest` to generate JUnit (`report.xml`) and Cobertura (`coverage.xml`) reports.
  - **Post Section**: Publishes test results using `junit` and coverage using `publishCoverage` with `coberturaAdapter`.

## Prerequisites for Demonstration
- **Environment**:
  - **Ubuntu Server**:
    - Requires Python3 and `python3-venv` for virtual environments.
    - Git typically pre-installed.
  - **Windows Server**:
    - Install Python3 and Git manually.
    - Update Jenkinsfile to use correct paths for Python and PIP.
- **Jenkins Plugins**:
  - **JUnit Plugin**: For test report collection and visualization.
  - **Code Coverage API Plugin**: For publishing JaCoCo and Cobertura reports.
- **Verification**:
  - Check Python3: `python3 --version`.
  - Test virtual environment: `python3 -m venv test && . test/bin/activate`.
  - Confirm plugins in Jenkins: Manage Jenkins > Manage Plugins > Installed tab.

## Jenkins Configuration
- **Repository Setup**:
  - Pipeline pulls from a GitHub repository (exercise files on `main` branch).
  - Update branch specifier to `main` and script path to match Jenkinsfile location.
  - Example Configuration:
    ```groovy
    pipeline {
        agent any
        options {
            git url: 'https://github.com/example/repo.git', branch: 'main', credentialsId: 'github-credentials'
        }
    }
    ```
- **Troubleshooting**: Verify repository settings if errors occur.

## Viewing Reports
- **Test Results**:
  - Displayed as a trend graph on the project page.
  - Detailed view: Click test results link to see passes, failures, and skips.
  - Drill down into packages for specific test details.
- **Code Coverage**:
  - Trend graph on project page.
  - Detailed view: Click "Coverage Report" for overview and package details.
  - File Coverage tab shows line-by-line coverage with source code view.

## Key Takeaways
- Use JUnit plugin for standardized test result reporting and trend tracking.
- Use Code Coverage API plugin for JaCoCo/Cobertura report publishing.
- Ensure proper environment setup (Python3, Git, plugins) for smooth pipeline execution.
- Leverage pipeline stages to integrate testing and coverage reporting effectively.


# Jenkins Test Failure and Pipeline Control Notes

## Overview
- **Purpose**: Demonstrate how a test failure impacts subsequent stages in a Jenkins pipeline and how Jenkins handles build stability.
- **Scenario**: A pipeline similar to a previous lesson is used, but modified to include a deliberately failing test to observe its effect.

## Pipeline Configuration
- **Repository Setup**:
  - Uses exercise files from a GitHub repository (forked for the demonstration).
  - Branch specifier updated to `main` to match the exercise files.
  - Script path updated to point to the correct Jenkinsfile location.
- **Example Configuration**:
  ```groovy
  pipeline {
      agent any
      options {
          git url: 'https://github.com/example/repo.git', branch: 'main', credentialsId: 'github-credentials'
      }
      stages {
          // Pipeline stages
      }
  }
  ```
  - Note: Credentials were required at the time of recording but may not be needed for public exercise files.

## Pipeline Structure
- **Stages**: Similar to the previous lesson, likely including:
  1. Setup (install dependencies).
  2. Linting (check syntax).
  3. Testing (run tests, with one test designed to fail).
  4. Build (build the application).
  5. Deploy (deploy the application).
- **Test Stage**:
  - Contains a test that always fails, triggering a failure in the pipeline.
- **Example Test Stage Code**:
  ```groovy
  pipeline {
      agent any
      stages {
          stage('Test') {
              steps {
                  sh 'pytest --junitxml=report.xml'
              }
              post {
                  always {
                      junit 'report.xml'
                  }
              }
          }
          stage('Build') {
              steps {
                  sh 'python setup.py build'
              }
          }
          stage('Deploy') {
              steps {
                  sh 'echo Deploying application'
              }
          }
      }
  }
  ```
  - The `junit` step publishes test results, capturing the failure.

## Pipeline Behavior on Test Failure
- **Test Stage Failure**:
  - The deliberately failing test causes the test stage to fail.
  - Subsequent stages (Build and Deploy) are skipped due to the failure.
- **Build Status**:
  - The pipeline is marked as **unstable** (indicated by a yellow color in the UI).
  - **Unstable Build**: Occurs when a failure or condition prevents the build from completing successfully.
- **Test Results**:
  - Accessible via the build’s “Test Results” link in the Jenkins UI.
  - Shows the failed test with details, including:
    - Error message.
    - Stack trace pinpointing the specific test failure.

## Viewing Test Results
- **Navigation**:
  - Click into the build in the Jenkins UI.
  - Select “Test Results” to view the report.
  - Follow the link for the failed test to see detailed error information.
- **UI Feedback**:
  - Test stage failure is highlighted.
  - Build and Deploy stages are marked as skipped.
  - Post-action stage reflects the unstable build status.

## Key Takeaways
- A test failure in a Jenkins pipeline halts subsequent stages, preventing build and deployment.
- The pipeline is marked as unstable, indicating an incomplete or problematic build.
- Detailed test failure reports (error messages, stack traces) are available in the Jenkins UI for debugging.
- Proper configuration of the repository and Jenkinsfile is critical to ensure the pipeline runs as expected.


# Jenkins Challenge: Create Artifacts and Reports Notes

## Challenge Overview
- **Objective**: Develop a Jenkins pipeline for a Java application to:
  - Test the application code and generate a JUnit test report.
  - Compile the code into a JAR file and store it as an artifact if tests pass.
  - Use Maven for pipeline stages and ensure the JUnit plugin is installed.
- **Requirements**:
  - Configure Maven as a global tool in Jenkins.
  - Use the JUnit plugin to collect test reports.
  - Ensure pipeline steps (test result collection and artifact archiving) always run and do not fail the build if no artifacts or test results are found.
  - Use the pipeline syntax generator for Maven configuration, JUnit reporting, and artifact archiving.
- **Time Estimate**: 15–20 minutes.

## Solution Steps

### 1. Prerequisites
- **Maven Global Tool Configuration**:
  - Navigate to **Manage Jenkins** > **Global Tool Configuration** > **Maven**.
  - Verify or add a Maven installation (e.g., named "Maven 384").
- **JUnit Plugin**:
  - Confirm the JUnit plugin is installed via **Manage Jenkins** > **Manage Plugins** > **Installed** tab.
- **Pipeline Setup**:
  - Forked exercise files from a GitHub repository (private at recording time, public for users).
  - Update pipeline configuration:
    - Set branch specifier to `main`.
    - Update script path to match the Jenkinsfile location in the exercise files.
  - Example Configuration:
    ```groovy
    pipeline {
        agent any
        options {
            git url: 'https://github.com/example/repo.git', branch: 'main'
        }
    }
    ```

### 2. Configure Maven in Pipeline
- **Use Pipeline Syntax Generator**:
  - Access via **Pipeline Syntax** > **Declarative Directive Generator** > **Tools**.
  - Select Maven (e.g., "Maven 384") and generate the directive.
- **Add to Pipeline**:
  - Place the Maven tool configuration at the top of the pipeline.
  - Example Code:
    ```groovy
    pipeline {
        agent any
        tools {
            maven 'Maven 384'
        }
        stages {
            // Stages will be added here
        }
    }
    ```

### 3. Update Pipeline Stages
- **Uncomment Maven Commands**:
  - Enable shell steps in the pipeline template for Maven commands in the **Clean**, **Test**, and **Package** stages.
- **Example Pipeline Stages**:
  ```groovy
  pipeline {
      agent any
      tools {
          maven 'Maven 384'
      }
      stages {
          stage('Clean') {
              steps {
                  sh 'mvn clean'
              }
          }
          stage('Test') {
              steps {
                  sh 'mvn test'
              }
          }
          stage('Package') {
              steps {
                  sh 'mvn package'
              }
          }
      }
  }
  ```

### 4. Collect Test Results
- **Use Pipeline Syntax Generator**:
  - Access via **Pipeline Syntax** > **Snippet Generator** > **junit**.
  - Specify test report location: `**/TEST-com.learningjenkins.apptest.xml`.
  - Enable "Do not fail build on empty test results".
- **Add to Pipeline**:
  - Place in the `post` section to ensure it runs regardless of build outcome.
- **Example Code**:
  ```groovy
  post {
      always {
          junit testResults: '**/TEST-com.learningjenkins.apptest.xml', allowEmptyResults: true
      }
  }
  ```

### 5. Archive Artifacts
- **Use Pipeline Syntax Generator**:
  - Access via **Pipeline Syntax** > **Snippet Generator** > **archiveArtifacts**.
  - Specify artifact: `**/hello-1.0-SNAPSHOT.jar`.
  - Enable options:
    - Do not fail build if archiving returns nothing.
    - Archive only if the build is successful.
    - Fingerprint the artifact.
- **Add to Pipeline**:
  - Place in the `post` section.
- **Example Code**:
  ```groovy
  post {
      always {
          archiveArtifacts artifacts: '**/hello-1.0-SNAPSHOT.jar', allowEmptyArchive: true, onlyIfSuccessful: true, fingerprint: true
      }
  }
  ```

### 6. Final Pipeline Example
```groovy
pipeline {
    agent any
    tools {
        maven 'Maven 384'
    }
    stages {
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
    post {
        always {
            junit testResults: '**/TEST-com.learningjenkins.apptest.xml', allowEmptyResults: true
            archiveArtifacts artifacts: '**/hello-1.0-SNAPSHOT.jar', allowEmptyArchive: true, onlyIfSuccessful: true, fingerprint: true
        }
    }
}
```

### 7. Verify Results
- **Build Execution**:
  - Run the pipeline; initial builds may take time due to Maven installation.
  - Successful build shows an artifact icon for `hello-1.0-SNAPSHOT.jar`.
- **Test Reports**:
  - Test results appear on the project page as a trend graph.
  - Multiple builds generate additional data points in the trend.
- **Artifacts**:
  - Archived JAR file is accessible via the build’s UI.

## Key Takeaways
- Configure Maven as a global tool and reference it in the pipeline using the `tools` directive.
- Use the JUnit plugin to collect and publish test reports, ensuring empty results do not fail the build.
- Archive JAR artifacts with options to prevent build failure on empty archives and to fingerprint for tracking.
- Leverage the pipeline syntax generator to create accurate snippets for Maven, JUnit, and artifact archiving.
- Ensure repository settings (branch, script path) match the exercise files for successful execution.

The provided code block is a `post` section in a Jenkins pipeline written in Groovy. It defines actions to be executed after the pipeline's main stages complete, regardless of the build outcome (success, failure, etc.). This section is commonly used for cleanup, reporting, or archiving tasks. Below is a detailed explanation of how this specific code block works:

### Code Breakdown
```groovy
post {
    always {
        junit testResults: '**/TEST-com.learningjenkins.apptest.xml', allowEmptyResults: true
        archiveArtifacts artifacts: '**/hello-1.0-SNAPSHOT.jar', allowEmptyArchive: true, onlyIfSuccessful: true, fingerprint: true
    }
}
```

#### 1. **`post` Block**
- The `post` block in a Jenkins pipeline specifies actions to take after all stages in the pipeline have executed.
- It supports conditions like `always`, `success`, `failure`, `unstable`, etc., to control when the enclosed steps run.
- In this case, the `always` condition ensures the steps inside execute regardless of whether the pipeline succeeds, fails, or is unstable.

#### 2. **`junit` Step**
```groovy
junit testResults: '**/TEST-com.learningjenkins.apptest.xml', allowEmptyResults: true
```
- **Purpose**: Publishes JUnit test results to the Jenkins UI, enabling visualization of test outcomes (e.g., passes, failures, skips) and trends.
- **Parameters**:
  - `testResults: '**/TEST-com.learningjenkins.apptest.xml'`
    - Specifies the location of JUnit XML test report files using an Ant-style pattern.
    - The `**/` prefix means Jenkins will recursively search all directories for files matching `TEST-com.learningjenkins.apptest.xml`.
    - This file is typically generated by a testing tool (e.g., Maven’s `mvn test` command in a Java project).
  - `allowEmptyResults: true`
    - Ensures the build does not fail if no test result files are found.
    - Useful for robustness, as missing test reports (e.g., due to a test stage failure) won’t cause the pipeline to fail at this step.
- **Behavior**:
  - Jenkins parses the specified XML file(s) to extract test results.
  - Results are displayed in the Jenkins UI under the build’s “Test Results” section, including a trend graph if multiple builds are run.
  - If no matching files are found, the step completes successfully due to `allowEmptyResults: true`.

#### 3. **`archiveArtifacts` Step**
```groovy
archiveArtifacts artifacts: '**/hello-1.0-SNAPSHOT.jar', allowEmptyArchive: true, onlyIfSuccessful: true, fingerprint: true
```
- **Purpose**: Archives specified files (artifacts) from the build, making them accessible via the Jenkins UI and trackable across builds.
- **Parameters**:
  - `artifacts: '**/hello-1.0-SNAPSHOT.jar'`
    - Specifies the files to archive using an Ant-style pattern.
    - `**/` searches recursively for `hello-1.0-SNAPSHOT.jar`, typically a Java archive (JAR) file generated by a build tool like Maven (e.g., via `mvn package`).
  - `allowEmptyArchive: true`
    - Prevents the build from failing if no matching artifacts are found.
    - Ensures pipeline stability if, for example, the build stage fails to produce the JAR file.
  - `onlyIfSuccessful: true`
    - Archives artifacts only if the pipeline’s overall status is successful (i.e., no stage failures).
    - If any stage fails, this step is skipped, ensuring artifacts are archived only from successful builds.
  - `fingerprint: true`
    - Generates an MD5 checksum for the archived artifact and stores it in Jenkins’ internal database.
    - Enables tracking of the artifact’s usage across jobs (e.g., which jobs created or accessed it).
- **Behavior**:
  - If the pipeline is successful and the JAR file exists, it is archived and accessible via the build’s UI (indicated by an artifact icon).
  - If no JAR file is found, the step completes successfully due to `allowEmptyArchive: true`.
  - If the pipeline fails, no archiving occurs due to `onlyIfSuccessful: true`.
  - The fingerprint allows Jenkins to track the artifact’s history, viewable under the build’s “See Fingerprints” section.

### Execution Flow
1. **After Pipeline Stages**:
   - The `post` block runs after all `stages` in the pipeline (e.g., Clean, Test, Package) have completed.
   - The `always` condition ensures the `junit` and `archiveArtifacts` steps execute regardless of the pipeline’s outcome.

2. **JUnit Step**:
   - Jenkins searches for `TEST-com.learningjenkins.apptest.xml` in the workspace.
   - If found, it processes the file to display test results in the UI.
   - If not found, the step completes without failing the build (`allowEmptyResults: true`).

3. **Archive Artifacts Step**:
   - If the pipeline is successful (`onlyIfSuccessful: true`), Jenkins searches for `hello-1.0-SNAPSHOT.jar`.
   - If found, the JAR is archived, and a fingerprint (MD5 checksum) is created.
   - If not found, the step completes without failing (`allowEmptyArchive: true`).
   - If the pipeline failed, this step is skipped.

### Example Context
This code is part of a Jenkins pipeline for a Java application using Maven, as described in the transcript. The pipeline likely includes:
- A **Test** stage running `mvn test` to generate the JUnit XML report.
- A **Package** stage running `mvn package` to produce the `hello-1.0-SNAPSHOT.jar` file.
- The `post` block ensures test results are always published and artifacts are archived only on success.

### Example Full Pipeline
```groovy
pipeline {
    agent any
    tools {
        maven 'Maven 384'
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
    post {
        always {
            junit testResults: '**/TEST-com.learningjenkins.apptest.xml', allowEmptyResults: true
            archiveArtifacts artifacts: '**/hello-1.0-SNAPSHOT.jar', allowEmptyArchive: true, onlyIfSuccessful: true, fingerprint: true
        }
    }
}
```

### Key Points
- **Robustness**: `allowEmptyResults` and `allowEmptyArchive` ensure the pipeline doesn’t fail due to missing files.
- **Conditional Archiving**: `onlyIfSuccessful: true` ensures artifacts are archived only from successful builds, maintaining quality control.
- **Tracking**: `fingerprint: true` enables artifact traceability across Jenkins jobs.
- **UI Integration**: Test results and artifacts are accessible in the Jenkins UI, with test trends and artifact download options.

In Jenkins, for a job to access artifacts created by another job, the job creating the artifacts must explicitly grant permission to the job that needs to access them. This is a security feature to prevent unauthorized access to artifacts. The process involves configuring permissions in the job that creates the artifacts and using the **Copy Artifact plugin** in the job that retrieves them. Below is a detailed explanation of how this works, based on the context of Jenkins pipelines and the earlier transcripts.

### How Permissions for Artifact Access Work

#### 1. **Granting Permissions in the Artifact-Creating Job**
- **Purpose**: The job that creates an artifact must specify which other jobs are allowed to copy its artifacts.
- **Mechanism**: This is done using the `copyArtifactPermission` option in the pipeline configuration of the artifact-creating job.
- **Configuration**:
  - In the pipeline’s `options` block, add `copyArtifactPermission` with the name(s) of the job(s) allowed to access the artifacts.
  - Example Code (in the artifact-creating job’s Jenkinsfile):
    ```groovy
    pipeline {
        agent any
        options {
            copyArtifactPermission('read-artifact-job')
        }
        stages {
            stage('Create Artifact') {
                steps {
                    writeFile file: 'report.txt', text: 'Sample report content'
                }
            }
        }
        post {
            always {
                archiveArtifacts artifacts: 'report.txt', fingerprint: true
            }
        }
    }
    ```
    - **Explanation**:
      - `copyArtifactPermission('read-artifact-job')` grants permission to a job named `read-artifact-job` to copy artifacts (e.g., `report.txt`) produced by this job.
      - Multiple jobs can be specified as a comma-separated list, e.g., `copyArtifactPermission('job1,job2')`.
      - Wildcards are supported for broader permissions, e.g., `copyArtifactPermission('*')` allows all jobs to access artifacts (use cautiously due to security implications).

#### 2. **Using the Copy Artifact Plugin in the Accessing Job**
- **Purpose**: The job that needs to access the artifact uses the **Copy Artifact plugin** to retrieve artifacts from the permitted job.
- **Configuration**:
  - Install the **Copy Artifact plugin** on the Jenkins server.
  - In the accessing job’s pipeline, use the `copyArtifacts` step to specify the source job and the artifact to copy.
  - Example Code (in the artifact-accessing job’s Jenkinsfile):
    ```groovy
    pipeline {
        agent any
        stages {
            stage('Copy Artifact') {
                steps {
                    copyArtifacts projectName: 'create-artifact-job', 
                                  selector: specific('lastSuccessful'), 
                                  target: 'copied_files', 
                                  fingerprintArtifacts: true
                }
            }
        }
    }
    ```
    - **Parameters**:
      - `projectName: 'create-artifact-job'`: Specifies the name of the job that created the artifact.
      - `selector: specific('lastSuccessful')`: Defines which build to copy from (e.g., the last successful build). Other selectors include `lastCompleted`, `specific(buildNumber)`, etc.
      - `target: 'copied_files'`: Specifies the directory in the workspace where the artifact will be copied (optional; defaults to the workspace root).
      - `fingerprintArtifacts: true`: Fingerprints the copied artifact for tracking its usage.
    - **Requirement**: The job specified in `projectName` (e.g., `create-artifact-job`) must have granted permission to this job via `copyArtifactPermission`.

#### 3. **Security Enforcement**
- **Permission Check**: When the `copyArtifacts` step runs, Jenkins verifies that the accessing job has permission to copy artifacts from the source job.
  - If permission is not granted (i.e., the accessing job’s name is not listed in the source job’s `copyArtifactPermission`), the pipeline fails with an error.
  - Example Error: `ERROR: Permission to copy artifact is missing for create-artifact-job`.
- **Why This Matters**: This ensures that only authorized jobs can access sensitive artifacts, preventing unintended or malicious use.

#### 4. **Fingerprinting for Tracking**
- **Purpose**: Fingerprinting allows Jenkins to track which jobs create or use an artifact.
- **Process**:
  - When an artifact is archived (e.g., via `archiveArtifacts` with `fingerprint: true`), Jenkins generates an MD5 checksum and stores it in an internal database.
  - When a job copies an artifact (e.g., via `copyArtifacts` with `fingerprintArtifacts: true`), Jenkins logs this usage against the artifact’s fingerprint.
  - Example: In the artifact-creating job:
    ```groovy
    archiveArtifacts artifacts: 'report.txt', fingerprint: true
    ```
    In the artifact-accessing job:
    ```groovy
    copyArtifacts projectName: 'create-artifact-job', selector: specific('lastSuccessful'), fingerprintArtifacts: true
    ```
  - **Result**: Jenkins can display the artifact’s fingerprint in the UI (via “See Fingerprints”), showing which jobs created or accessed it, along with the MD5 checksum.

#### 5. **Steps to Implement**
1. **Install the Copy Artifact Plugin**:
   - Go to **Manage Jenkins** > **Manage Plugins** > **Available**, search for “Copy Artifact,” and install it.
   - Verify installation in the **Installed** tab.
2. **Configure the Artifact-Creating Job**:
   - Add `copyArtifactPermission` in the `options` block, specifying the name(s) of the job(s) allowed to access artifacts.
   - Archive the artifact in the `post` block with `archiveArtifacts`.
3. **Configure the Artifact-Accessing Job**:
   - Use the `copyArtifacts` step, specifying the source job and artifact details.
4. **Run and Verify**:
   - Run the artifact-creating job to generate and archive the artifact.
   - Run the artifact-accessing job to copy the artifact.
   - Check the build’s UI for the artifact (via the artifact icon) and fingerprint details (via “See Fingerprints”).

### Example Scenario
- **Job A (create-artifact-job)**:
  - Creates `report.txt` and archives it.
  - Grants permission to `read-artifact-job`.
  - Code:
    ```groovy
    pipeline {
        agent any
        options {
            copyArtifactPermission('read-artifact-job')
        }
        stages {
            stage('Create') {
                steps {
                    writeFile file: 'report.txt', text: 'Sample report'
                }
            }
        }
        post {
            always {
                archiveArtifacts artifacts: 'report.txt', fingerprint: true
            }
        }
    }
    ```
- **Job B (read-artifact-job)**:
  - Copies `report.txt` from `create-artifact-job`.
  - Code:
    ```groovy
    pipeline {
        agent any
        stages {
            stage('Copy') {
                steps {
                    copyArtifacts projectName: 'create-artifact-job', 
                                  selector: specific('lastSuccessful'), 
                                  target: 'copied_files', 
                                  fingerprintArtifacts: true
                }
            }
        }
    }
    ```
- **Result**:
  - Job A creates and archives `report.txt`.
  - Job B successfully copies `report.txt` to the `copied_files` directory in its workspace.
  - Jenkins tracks the artifact’s usage via fingerprints, visible in the UI.

### Key Points
- **Security**: `copyArtifactPermission` ensures only authorized jobs can access artifacts, enforced during the `copyArtifacts` step.
- **Plugin Dependency**: The **Copy Artifact plugin** must be installed for `copyArtifacts` to work.
- **Fingerprinting**: Enhances traceability by logging artifact creation and usage.
- **UI Feedback**: Artifacts and fingerprints are accessible in the Jenkins UI for verification and debugging.

This mechanism ensures secure and controlled sharing of artifacts between Jenkins jobs while maintaining traceability.