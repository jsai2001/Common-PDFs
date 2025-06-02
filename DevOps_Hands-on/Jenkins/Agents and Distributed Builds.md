
# Jenkins Distributed Builds with Agents

## Overview
- **Jenkins Controller**: Provides a web interface for managing Jenkins server configuration.
- **Best Practice**: Limit jobs on the controller to free up CPU, memory, and disk space for management tasks like job scheduling.
- **Nodes/Agents**: External systems connected to the Jenkins controller over a network, used as compute resources for running jobs.
  - **Node**: A server or system connected to Jenkins.
  - **Agent**: A process on the node that executes job commands and reports status back to the controller.
  - Terms "node" and "agent" are often used interchangeably.

## Types of Nodes/Agents
1. **SSH Node**:
   - Connects to a server using a specific user and SSH key.
   - Secure method, ideal for nodes not on the same network as the controller.
   - Requirements:
     - Accepts SSH connections.
     - Has a user and SSH key for Jenkins.
     - Recent version of Java installed (agent is a Java-based application).
   - Compatible with any OS supporting SSH and Java.

2. **Docker Agents**:
   - Jobs run in a new container for each build, ensuring a fresh and isolated environment.
   - Requires a Docker process running on the node.

## Key Considerations for Pipeline Jobs
1. **Agent Configuration**:
   - Default: `agent any` runs the job on the first available agent.
   - With multiple nodes, use **labels** to specify agents with specific capabilities.
   - Example pipeline snippet for specifying an agent by label:
     ```groovy
     pipeline {
         agent {
             label 'maven-agent'
         }
         stages {
             stage('Build') {
                 steps {
                     echo 'Running on Maven agent'
                 }
             }
         }
     ```

2. **Tool Installation**:
   - Pipelines may need to install specific tools for consistency and availability.
   - Configure tools in **Global Tool Configuration** (e.g., Maven).
   - Example: Maven setup in Jenkins:
     - Navigate to *Manage Jenkins* > *Global Tool Configuration* > *Maven*.
     - Add Maven installation (e.g., `Maven-3.8.4`) with automatic installation enabled.
     - Reference in pipeline:
       ```groovy
       pipeline {
           agent any
           tools {
               maven 'Maven-3.8.4'
           }
           stages {
               stage('Build') {
                   steps {
                       sh 'mvn clean install'
                   }
               }
           }
       ```

3. **Code Checkout**:
   - Initial checkout occurs on the controller to read the Jenkinsfile.
   - Code is not automatically available on the agent.
   - Add a checkout step in the pipeline to ensure code availability on the agent.
   - Example pipeline snippet for code checkout:
     ```groovy
     pipeline {
         agent any
         stages {
             stage('Checkout') {
                 steps {
                     checkout scm
                 }
             }
             stage('Build') {
                 steps {
                     sh 'mvn clean install'
                 }
             }
         }
     ```

## Setup Example
- **Maven Configuration**:
  - Location: *Manage Jenkins* > *Global Tool Configuration* > *Maven*.
  - Name: `Maven-3.8.4`.
  - Set to install automatically.
  - Ensures pipelines can reference Maven by name for consistent builds across agents.


# Adding an SSH Agent to Jenkins

## Prerequisites
- A server reachable from the Jenkins server via SSH.
- A user and SSH key for establishing the SSH connection.
- Example: An EC2 instance running in AWS (refer to *Running Jenkins on AWS* course for setup details).

## Steps to Add an SSH Agent
1. **Navigate to Manage Nodes and Clouds**:
   - From the *Manage Jenkins* screen, select *Manage Nodes and Clouds*.
   - Click *New Node*.

2. **Configure the Node**:
   - **Name**: Enter a name (e.g., `Linux`) to match the SSH key name.
   - **Type**: Select *Permanent Agent*.
   - Click *Create*.

3. **Node Configuration Details**:
   - **Remote root directory**: Specify a directory for Jenkins workspaces (e.g., `/home/ec2-user`).
   - **Label**: Assign a label (e.g., `linux`) to identify the node.
   - **Usage**: Select *Only build jobs with label expressions matching this node* to restrict jobs to this node.
   - **Launch method**: Choose *Launch agents via SSH*.
   - **Host**: Enter the DNS name or IP address of the server.
   - **Credentials**:
     - Click *Add* > *Jenkins*.
     - Select *SSH Username with Private Key*.
     - **ID and Description**: Set to `linux`.
     - **Username**: Set to `ec2-user`.
     - **Private Key**: Select *Enter directly*, paste the SSH key, and click *Add*. (No passphrase in this example.)
     - Select the newly added credential.
   - **Host Key Verification Strategy**: Choose *Non verifying Verification Strategy*.
   - **Availability**: Select *Keep this agent online as much as possible*.
   - Click *Save* to connect and launch the agent.

4. **Verify Connection**:
   - Select the agent icon and go to *Log* to view connection output.
   - Check for errors if the connection fails.
   - Confirm the agent is online.

## Running a Pipeline Job with the SSH Agent
- Create a pipeline job configured to use the SSH agent.
- Specify the agent using the `label` keyword in the pipeline.
- Example pipeline configuration:
  ```groovy
  pipeline {
      agent {
          label 'linux'
      }
      stages {
          stage('Source') {
              steps {
                  git url: 'https://github.com/your-repo/java-app.git', credentialsId: 'github-credentials'
              }
          }
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

## Testing the Pipeline
- Save the pipeline and click *Build Now*.
- Verify the job ran on the SSH agent:
  - Go to the latest build and select *Console Output*.
  - Check the output for a message indicating the job ran on the `linux` agent and the workspace location.

## Notes
- If using private repositories (e.g., forked exercise files), configure credentials in Jenkins for repository access.
- Publicly available exercise files may not require credentials.
- The SSH key must be copied in its entirety from an editor and pasted into Jenkins without errors.


# Adding Docker Agents to Jenkins

## Prerequisites
- **Jenkins Server Requirements**:
  - Sufficient storage for Docker images.
  - Adequate RAM for running Docker processes.
  - Consider increasing resources if the server runs out of space or slows down.
- **Docker Installation**:
  - Docker must be installed as a service on the Jenkins server.
  - Verify Docker availability:
    ```bash
    docker --version
    docker ps
    ```
    - Confirms Docker CLI is accessible and the service is running.
- **Docker Pipeline Plugin**:
  - Must be installed to enable the `docker` agent keyword in pipeline scripts.
  - Verify installation:
    - Go to *Manage Jenkins* > *Manage Plugins* > *Installed* tab.
    - Filter for "Docker Pipeline Plugin".

## Configuring a Pipeline with a Docker Agent
- Use a Docker image to provide a consistent environment for pipeline execution.
- Example pipeline using a Maven Docker image:
  ```groovy
  pipeline {
      agent {
          docker {
              image 'maven:latest'
              args '--name maven-container'
          }
      }
      stages {
          stage('Checkout') {
              steps {
                  git url: 'https://github.com/your-repo/java-app.git', credentialsId: 'github-credentials'
              }
          }
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
- **Agent Configuration**:
  - `image 'maven:latest'`: Uses the latest Maven image from a public registry.
  - Replace `latest` with a specific version (e.g., `maven:3.8.4`) for consistency.
- **Checkout Stage**:
  - Checks out code from a repository (e.g., forked exercise files).
  - Credentials may be needed for private repositories (not required for public exercise files).
- **Subsequent Stages**:
  - Run Maven commands (`clean`, `test`, `package`) inside the Docker container.

## Running and Verifying the Pipeline
- Save the pipeline and click *Build Now*.
- Check the build output:
  - Go to the latest build > *Console Output*.
  - Look for:
    - Confirmation that the job runs on the Jenkins server.
    - Docker commands to start the container (e.g., pulling and running the Maven image).
    - Commands executed inside the container.
    - Docker commands to stop and remove the container at the end.
- **Key Behavior**:
  - Each pipeline run starts a new container, ensuring a fresh environment.
  - Containers are removed after the job completes, but the image may be reused.

## Notes
- Using Docker agents ensures a clean, isolated environment for each build.
- The Docker Pipeline Plugin is essential for integrating Docker with Jenkins pipelines.
- For private repositories, configure credentials in Jenkins; public repositories may not require this.


# Improving a Docker Agent Pipeline for Hugo

## Challenge Overview
- **Objective**: Optimize a Jenkins pipeline using a Docker agent to build and test the Hugo static site generator (written in Go) by implementing a persistent dependency cache in the project workspace.
- **Problem**: The build stage is slow due to downloading dependencies on every pipeline run.
- **Solution**: Move the Go dependency cache to the Jenkins workspace to reuse it across builds.
- **Time Estimate**: 15–20 minutes.

## Prerequisites
- **Jenkins Server Setup**:
  - Ensure sufficient storage and RAM for Docker operations.
  - Docker installed as a service on the Jenkins server.
    ```bash
    docker --version
    docker ps
    ```
    - Confirms Docker CLI is available and the service is running.
- **Docker Pipeline Plugin**:
  - Verify installation in Jenkins:
    - Navigate to *Manage Jenkins* > *Manage Plugins* > *Installed* tab.
    - Filter for "Docker Pipeline Plugin".
- **Exercise Files**:
  - Use provided files to set up a pipeline job for building and testing Hugo.

## Initial Pipeline Configuration
- Uses a `golang` Docker image from a public registry.
- Sets an environment variable for the Go compiler’s cache directory (default: `/tmp`).
- Pipeline stages:
  1. **Checkout**: Downloads Hugo source code.
  2. **Build**: Compiles the application (slow due to dependency downloads).
  3. **Test**: Runs tests on the compiled application.
- Example initial pipeline:
  ```groovy
  pipeline {
      agent {
          docker {
              image 'golang:latest'
              args '--name go-container'
          }
      }
      environment {
          GOCACHE = '/tmp'
      }
      stages {
          stage('Checkout') {
              steps {
                  git url: 'https://github.com/gohugoio/hugo.git', credentialsId: 'github-credentials'
              }
          }
          stage('Build') {
              steps {
                  sh 'go build'
              }
          }
          stage('Test') {
              steps {
                  sh './hugo version'
              }
          }
      }
  }
  ```
- **Initial Build Time**:
  - Build stage takes ~4 minutes due to dependency downloads.
  - Cache in `/tmp` is destroyed with each container, causing repeated downloads.

## Optimization Steps
1. **Identify Issue**:
   - Dependencies are downloaded every run because the cache is stored in `/tmp` inside the container, which is ephemeral.
   - Jenkins mounts the workspace as a volume, allowing persistent storage.

2. **Update Pipeline**:
   - Change the `GOCACHE` environment variable to use the workspace directory (e.g., `/var/jenkins_home/workspace/<job-name>`).
   - Example updated pipeline:
     ```groovy
     pipeline {
         agent {
             docker {
                 image 'golang:latest'
                 args '--name go-container'
             }
         }
         environment {
             GOCACHE = '/var/jenkins_home/workspace/hugo-pipeline'
         }
         stages {
             stage('Checkout') {
                 steps {
                     git url: 'https://github.com/gohugoio/hugo.git', credentialsId: 'github-credentials'
                 }
             }
             stage('Build') {
                 steps {
                     sh 'go build'
                 }
             }
             stage('Test') {
                 steps {
                     sh './hugo version'
                 }
             }
         }
     }
     ```

3. **Run Pipeline**:
   - First run: Builds the cache in the workspace (similar duration to initial build, ~4 minutes).
   - Second run: Reuses the cache, significantly reducing build time (e.g., ~17 seconds).

## Verification
- Check build times:
  - Initial build: ~4 minutes (no cache reuse).
  - Post-optimization (second run): ~17 seconds (cache reused).
- Review console output to confirm:
  - Dependencies are not re-downloaded in the build stage after the cache is created.
  - Docker commands for starting/stopping the container are executed as expected.

## Summary of Steps
1. Verify Docker service availability on the Jenkins server.
2. Confirm Docker Pipeline Plugin is installed.
3. Set up the initial pipeline using the exercise files.
4. Run the pipeline and note the build stage time (~4 minutes).
5. Update `GOCACHE` to use the workspace directory.
6. Run the pipeline to create the cache.
7. Run the pipeline again to confirm reduced build time (~17 seconds).

## Notes
- The workspace path (`/var/jenkins_home/workspace/<job-name>`) may vary; use the path suggested in the Jenkins pipeline configuration or logs.
- Credentials are needed for private repositories; public Hugo repositories may not require them.
- Persistent caching significantly improves build performance for Go-based projects like Hugo.