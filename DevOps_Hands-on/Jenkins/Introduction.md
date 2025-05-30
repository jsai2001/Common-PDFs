
# Jenkins Essential Training Notes

## Course Overview
- **Instructor**: Michael Jenkins, a computer engineer specializing in process automation, DevOps, and site reliability engineering.
- **Purpose**: Learn advanced Jenkins techniques for DevOps, focusing on automation and software development pipelines.
- **Key Topics**:
  - Why Jenkins is effective for DevOps.
  - Creating pipelines using configuration as code stored in GitHub repositories.
  - Exploring distributed builds, artifact management, and Jenkins security.

## Prerequisites
- **Jenkins Experience**:
  - Intermediate level course.
  - Familiarity with installing plugins and configuring global tools.
  - Experience creating and running freestyle jobs, including parameterized jobs.
  - Recommended: Take "Learning Jenkins" course for foundational knowledge.
- **Git Knowledge**:
  - Comfortable with Git version control and services like GitHub, GitLab, or Bitbucket.
  - Course uses GitHub; familiarity with it is beneficial.
  - Resources available in LinkedIn Learning Library for Git/GitHub.
- **Linux and CLI**:
  - Basic experience with Linux OS and command-line interfaces.
  - No need to be a full system administrator, but familiarity with server commands is helpful.
- **Exercise Files**:
  - Available in a GitHub repository.
  - Contains code, commands, troubleshooting tips, and additional resources.
  - Recommendation: Fork the repository for personal use during the course.

## Version Information
- **Software Versions Used**:
  - Ubuntu 20.04 LTS
  - OpenJDK 11.0.12 (Java 11)
  - Jenkins 2.332.1
- **Notes**:
  - Course content is periodically reviewed and updated for compatibility with newer software versions.
  - Check the course’s Q&A section and exercise files for version discrepancy information.

## Example Code Snippet
Below is an example of a basic Jenkins pipeline configuration as code, typically stored in a `Jenkinsfile` in a GitHub repository:

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building the application...'
                // Add build commands here, e.g., sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                // Add test commands here, e.g., sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying to production...'
                // Add deployment commands here
            }
        }
    }
}
```

- **Explanation**: This `Jenkinsfile` defines a simple pipeline with three stages: Build, Test, and Deploy. It uses Groovy syntax and can be stored in a GitHub repository for version control and automation.

## Additional Notes
- **Distributed Builds**: Learn how to scale Jenkins using distributed builds to handle large projects efficiently.
- **Artifact Management**: Understand how to manage build artifacts for traceability and deployment.
- **Security**: Explore best practices for securing Jenkins instances to protect pipelines and sensitive data.
- **Resources**: Use the exercise files repository for hands-on practice and refer to LinkedIn Learning for supplementary Git and Linux courses.