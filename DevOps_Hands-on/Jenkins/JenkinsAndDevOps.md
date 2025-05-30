
# Jenkins and the DevOps Lifecycle Notes

## DevOps Lifecycle Overview
- **Structure**: The DevOps lifecycle is represented as an infinity symbol, indicating a continuous process until system/application decommissioning.
- **Stages**: Divided into two groups:
  - **Development Stages**: Plan, Code, Build, Test
  - **Operational Stages**: Release, Deploy, Operate, Monitor
- **Jenkins Role**: Automates processes in Build, Test, Release, and Deploy stages.

## Jenkins in CI/CD
- **Continuous Integration (CI)**:
  - Tied to Build and Test stages.
  - Goal: Identify and resolve issues early in development.
  - Jenkins automates building and testing, producing artifacts (e.g., container image, Java archive, Windows executable).
  - **Example Process**:
    - Code is built into an artifact.
    - Artifact undergoes automated tests.
    - Successful tests allow the artifact to proceed to the next stage.
- **Continuous Delivery/Deployment (CD)**:
  - Tied to Release and Deploy stages.
  - **Release**: Jenkins uploads artifacts (e.g., container image to a repository or makes a JAR file available).
  - **Deploy**: Can be manual or fully automated (Continuous Deployment).
  - Continuous Deployment involves minimal/no human interaction, with Jenkins executing automated deployment instructions.

## Example Jenkins Pipeline for CI/CD
Below is a sample `Jenkinsfile` demonstrating a CI/CD pipeline:

```groovy
pipeline {
    agent any
    stages {
        stage('Plan') {
            steps {
                echo 'Planning the application...'
                // Define requirements, plan features
            }
        }
        stage('Code') {
            steps {
                echo 'Fetching code from GitHub...'
                git 'https://github.com/example/repo.git'
            }
        }
        stage('Build') {
            steps {
                echo 'Building the artifact...'
                sh 'mvn clean package' // Example for a Java project
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }
        stage('Release') {
            steps {
                echo 'Releasing artifact...'
                // Example: Upload to a repository
                sh 'docker push my-app:latest'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying to production...'
                // Example: Deploy to a cloud service
                sh 'kubectl apply -f deployment.yaml'
            }
        }
    }
}
```

- **Explanation**: This pipeline covers the DevOps lifecycle stages, pulling code from GitHub, building, testing, releasing, and deploying an artifact. Adjust commands based on your project (e.g., `mvn` for Java, `docker` for containers).

## Challenge: Deploy a Jenkins Server
- **Objective**: Set up a Jenkins server for course exercises.
- **Requirements**:
  - Use the latest Ubuntu Server.
  - Install NGINX as a proxy for Jenkins.
  - Install and configure Jenkins.
- **Recommendations**:
  - Prefer a public cloud service (e.g., AWS, Google Cloud, Azure) for public URL accessibility.
  - Public URL needed for webhooks to trigger jobs from a code repository (e.g., GitHub).
  - Local installations (Windows, macOS, Linux) are viable but cannot receive webhooks.
- **AWS-Specific Steps** (as per instructor):
  - Create a key pair for SSH connections.
  - Launch an EC2 instance with a Ubuntu AMI.
  - Assign an Elastic IP for persistent DNS.
  - Use a user data script to automate installation of NGINX and Jenkins.
- **Exercise Files**:
  - Include a script to:
    - Update Ubuntu OS.
    - Install NGINX and Jenkins.
    - Install suggested plugins and skip the Jenkins installation wizard.
  - Available in the course’s GitHub repository.
- **Notes for Non-Ubuntu Systems**:
  - For Windows or macOS, refer to the "Learning Jenkins" course for installation instructions.
- **Estimated Time**: ~15 minutes.

## Example User Data Script for AWS EC2
Below is an example user data script to install Jenkins and NGINX on Ubuntu:

```bash
#!/bin/bash
# Update the system
apt-get update -y
apt-get upgrade -y

# Install Java (required for Jenkins)
apt-get install -y openjdk-11-jdk

# Install Jenkins
wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | apt-key add -
echo "deb https://pkg.jenkins.io/debian binary/" > /etc/apt/sources.list.d/jenkins.list
apt-get update -y
apt-get install -y jenkins

# Install NGINX
apt-get install -y nginx

# Configure NGINX as a reverse proxy for Jenkins
cat <<EOF > /etc/nginx/sites-available/jenkins
server {
    listen 80;
    server_name jenkins.example.com;
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
    }
}
EOF

ln -s /etc/nginx/sites-available/jenkins /etc/nginx/sites-enabled/
systemctl restart nginx
systemctl enable jenkins
systemctl start jenkins
```

- **Explanation**: This script updates Ubuntu, installs Java, Jenkins, and NGINX, and configures NGINX as a reverse proxy to forward requests to Jenkins on port 8080. Adjust `server_name` to your domain or IP.

## Additional Notes
- **CI/CD Benefits**:
  - CI: Early issue detection, faster development cycles.
  - CD: Streamlined releases, automated deployments.
- **Webhook Limitation**: Local Jenkins installations cannot receive GitHub webhooks, limiting CI automation.
- **Resources**: Use exercise files and the GitHub repository for scripts and additional guidance.

# Deploy a Jenkins Server Challenge Notes

## Challenge Overview
- **Objective**: Deploy a Jenkins server for course exercises.
- **Requirements**:
  - Use the latest Ubuntu Server.
  - Install NGINX as a reverse proxy for Jenkins.
  - Install and configure Jenkins.
- **Recommendations**:
  - Prefer a public cloud service (e.g., AWS, Google Cloud, Azure) for public URL accessibility to enable GitHub webhooks for CI.
  - Local installations (Windows, macOS, Linux) are possible but cannot receive webhooks.
- **Estimated Time**: ~15 minutes.
- **Resources**: Exercise files include a script to automate Ubuntu updates, NGINX, and Jenkins installation, including suggested plugins and skipping the setup wizard.

## Solution: Deploying Jenkins on AWS
- **Steps Performed** (using AWS as an example):
  1. **Launch EC2 Instance**:
     - Select Ubuntu Server 20.04 LTS AMI.
     - Choose `t2.micro` instance type (free tier, sufficient CPU/RAM).
     - Increase root volume size to 30GB for NGINX, Jenkins, plugins, and Docker images.
     - Add tag: `Name: Jenkins server`.
  2. **Configure Security Group**:
     - Create a new security group named `Jenkins server`.
     - Allow HTTP traffic on port 80 from any IP (`0.0.0.0/0`) for GitHub and web access.
  3. **Create Key Pair**:
     - Generate an RSA key pair named `Jenkins server`.
     - Download and secure the key file.
  4. **Assign Elastic IP**:
     - Allocate an Elastic IP for persistent DNS.
     - Associate it with the EC2 instance to maintain a fixed IP address.
  5. **SSH into Instance**:
     - Secure the key file with `chmod 600 jenkins-server.pem`.
     - Connect via SSH: `ssh -i jenkins-server.pem ubuntu@<public-dns>`.
     - Elevate to root with `sudo su -`.
  6. **Run Installation Script**:
     - Copy the provided installation script from the GitHub exercise files.
     - Save it as `install.sh` using `vim`.
     - Execute with `source install.sh`.
     - Script automates:
       - Ubuntu updates.
       - NGINX and Jenkins installation.
       - Suggested plugin installation.
       - Skipping Jenkins setup wizard.
       - Outputting admin user credentials.
  7. **Access Jenkins**:
     - Open the server’s public DNS in a browser.
     - Log in with the admin password provided by the script.
     - Optional: Create a new admin user with a memorable password.
     - Accept default Jenkins URL and complete setup.

## Example Installation Script
Below is an example of the `install.sh` script for automating Jenkins and NGINX setup on Ubuntu:

```bash
#!/bin/bash
# Update system
apt-get update -y
apt-get upgrade -y

# Install Java (required for Jenkins)
apt-get install -y openjdk-11-jdk

# Add Jenkins repository and install
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | apt-key add -
echo "deb https://pkg.jenkins.io/debian-stable binary/" > /etc/apt/sources.list.d/jenkins.list
apt-get update -y
apt-get install -y jenkins

# Install NGINX
apt-get install -y nginx

# Configure NGINX as reverse proxy for Jenkins
cat <<EOF > /etc/nginx/sites-available/jenkins
server {
    listen 80;
    server_name jenkins.example.com;
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
    }
}
EOF

# Enable NGINX configuration
ln -s /etc/nginx/sites-available/jenkins /etc/nginx/sites-enabled/
systemctl restart nginx
systemctl enable jenkins
systemctl start jenkins

# Output Jenkins admin password
echo "Jenkins Installation Complete!"
echo "Admin user: admin"
echo "Admin password: $(cat /var/lib/jenkins/secrets/initialAdminPassword)"
echo "Access Jenkins at http://$(curl -s http://169.254.169.254/latest/meta-data/public-hostname)"
```

- **Explanation**: This script updates Ubuntu, installs Java, Jenkins, and NGINX, configures NGINX as a reverse proxy, and outputs the initial admin password and public URL for Jenkins access. Replace `jenkins.example.com` with your server’s public DNS or IP.

## Example Security Group Configuration (AWS CLI)
To automate security group creation:

```bash
# Create security group
aws ec2 create-security-group --group-name JenkinsServer --description "Jenkins server security group"

# Add HTTP rule
aws ec2 authorize-security-group-ingress --group-name JenkinsServer --protocol tcp --port 80 --cidr 0.0.0.0/0

# Add SSH rule (optional, for access)
aws ec2 authorize-security-group-ingress --group-name JenkinsServer --protocol tcp --port 22 --cidr 0.0.0.0/0
```

- **Explanation**: This AWS CLI script creates a security group and allows HTTP (port 80) and SSH (port 22) traffic. Adjust CIDR ranges for stricter access if needed.

## Additional Notes
- **Public URL Importance**: Required for GitHub webhooks to trigger CI jobs.
- **Local Installation Limitation**: Cannot receive webhooks, but still usable for manual job execution.
- **Post-Setup**:
  - Verify NGINX proxies requests to Jenkins on port 8080.
  - Ensure suggested plugins are installed (handled by the script).
  - Optionally create a custom admin user for easier access.
- **Resources**: Refer to the "Learning Jenkins" course for Windows/macOS installation details and use the GitHub exercise files for scripts and guidance.