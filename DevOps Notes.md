### DevOps Course Summary

#### 1. Introduction to DevOps
DevOps integrates infrastructure, cloud computing, and coding to streamline the software development lifecycle and improve collaboration between development and operations teams.

#### 2. Key DevOps Tools
- **Ansible**: Configuration management.
- **Docker**: Containerization for application isolation.
- **Kubernetes**: Container orchestration for managing multiple containers.
- **Terraform**: Cloud automation and infrastructure as code.

#### 3. Software Development Lifecycle (SDLC) in DevOps
The SDLC in a DevOps environment involves multiple stages, each with specific goals and responsibilities:

1. **Requirement Analysis**:
   - Focus on gathering user requirements, understanding market needs, and identifying key product features.

2. **Planning**:
   - Determine what needs to be built, the associated costs, required resources, and risks involved.

3. **Design**:
   - Architects create a roadmap based on planning inputs and hand over design documents to developers.

4. **Development**:
   - Developers write code based on the design provided by the architects.

5. **Software Testing**:
   - Testing is performed to identify and fix issues before the software moves to production.

6. **Deployment**:
   - Operations teams deploy the software to a production environment, ensuring that it runs smoothly with minimal downtime.

7. **Maintenance**:
   - Monitor system health, performance, and uptime while managing regular changes and updates in the production environment.

#### 4. Overview of the DevOps Approach to SDLC
- **Requirement Analysis**
- **Planning**
- **Design**
- **Development**
- **Testing**
- **Deployment and Maintenance**

This structured process ensures the continuous delivery of high-quality software with an emphasis on collaboration, automation, and efficiency.

### DevOps SDLC Overview

#### 1. Roles in the Software Development Lifecycle (SDLC)
- **Architects**: Design the software.
- **Developers**: Develop the software.
- **Testers**: Test the software.
- **Operations Team**: Deploy and maintain the software.

#### 2. SDLC Models
- **Waterfall**: A linear, one-way model where no working software is available until the end. It takes a long time to produce a final product.
- **Spiral**: Combines iterative development with systematic aspects of the waterfall model.
- **Big Bang**: A development approach without clear planning or structure, often leading to unpredictable results.
- **Agile**: Breaks development into smaller, incremental sprints (2-4 weeks), delivering product features quickly with frequent client feedback.

#### 3. Agile Model Advantages
- Frequent collaboration with clients ensures the product evolves based on customer needs.
- After each sprint, new features are tested and integrated into the application, allowing quick iterations and feature releases.

#### 4. Operations and Deployment Challenges
- Developers regularly request operations teams to publish software on servers.
- Operations teams often face challenges due to unclear instructions from developers, leading to deployment issues and misalignment.
- Developers follow Agile methodologies, making frequent changes, while operations teams are often bound by ITIL processes (focused on stability), leading to conflicts.

#### 5. Miscommunication Between Dev and Ops
- Developers work in agile, making rapid changes.
- Operations teams prioritize stability and follow waterfall-like approaches.
- Miscommunication between teams results in delayed deployments, poor product quality, and unsatisfied customers.

#### 6. The Introduction of DevOps
- **DevOps** was introduced to bridge the gap between development (agile) and operations (waterfall).
- A DevOps engineer combines agile principles with operational stability.

#### 7. Importance of Automation in DevOps
- Automation reduces manual intervention and improves efficiency in:
  - Code builds
  - Code and software testing
  - Infrastructure changes
  - Deployments
- Automated processes speed up the overall development lifecycle, facilitating faster product delivery.

#### 8. Dev vs Ops Conflict
- Developers and operations teams often argue due to misaligned workflows (Agile vs Waterfall), leading to poor customer feedback.
- DevOps helps align these teams by promoting collaboration and automation.

#### 9. DevOps Team Structure
A successful DevOps team includes:
1. **Developers**
2. **Testers**
3. **System Administrators**
4. **Database Administrators**
5. **Build and Release Teams**

Each stage of the development process automates its tasks, integrating tools to streamline the workflow.

#### 10. DevOps Lifecycle
- DevOps automates processes to reduce human intervention, enabling faster feedback loops between operations and development teams.
- This integration of automation tools accelerates the delivery of applications to customers, resulting in a faster and more efficient product lifecycle.

### DevOps: Continuous Integration and Continuous Delivery

#### 1. Continuous Integration (CI)
- **Definition**: CI is an automated DevOps process that enables developers to integrate code changes frequently, ensuring quick and efficient software generation.
- **Key Components**:
  - Developers write code and commit it to a version control system (e.g., GitHub).
  - The code is continuously built, tested, and stored as artifacts (e.g., WAR, JAR, ZIP).
  - Artifacts are deployed on servers, where they undergo additional testing.

#### 2. Continuous Integration Process
1. **Code**: Developers write and commit code.
2. **Fetch**: The latest code is fetched from the repository.
3. **Build**: The code is compiled and built.
4. **Test**: The code is automatically tested.
5. **Notify**: The developer is notified of the results.
6. **Feedback**: Developers receive feedback on the build and test success.

- **Goal**: CI aims to detect errors early, during the development process, by automating build and testing for every code commit.

#### 3. Version Control Systems (VCS)
VCS stores and manages code versions, enabling collaboration:
- **Examples**: Git, SVN, TFS, Perforce.

#### 4. Build Tools
Different tools are used based on the programming language:
- **Examples**: Maven, Gradle, MSBuild, IBM UrbanCode.

#### 5. Software Repository Tools
Used to store and manage artifacts generated during the build process:
- **Examples**: Sonatype Nexus, JFrog Artifactory, Archiva.

#### 6. CI Tools
Automation tools for the CI process:
- **Examples**: Jenkins, CircleCI, TeamCity, Bamboo CI.

#### 7. Continuous Delivery (CD)
- **Definition**: CD automates the process of delivering code changes to servers after CI has completed. It ensures efficient and error-free deployment of applications.
- **Process**: After successful builds and tests, the artifact is automatically deployed to the server with minimal manual intervention. This involves:
  - **Server provisioning**
  - **Dependency installation**
  - **Configuration changes**
  - **Network setup and artifact deployment**

#### 8. Continuous Delivery Process
Every step in the deployment process is automated:
- **Automation Tools**:
  - **System Automation**: Ansible, Puppet, Chef.
  - **Cloud Infrastructure Automation**: Terraform, CloudFormation.
  - **CI/CD Automation**: Jenkins, Octopus Deploy.
  - **Helm Charts**: Used for managing Kubernetes applications.
  - **Code Deploy**: Automates application deployment.

#### 9. Automating Software Testing
- All types of testing (functional, load, database, security, performance) should be automated.
- **Ops team**: Automates deployment.
- **QA team**: Automates testing.
- **Developers**: Perform integrated CI (build, test).

#### 10. Continuous Delivery Lifecycle
- **CI + Ops + QA = Continuous Delivery**
- Continuous delivery automates code integration, testing, and deployment, ensuring faster and more reliable releases.

### DevOps Overview

**What is DevOps?**
DevOps is a philosophy of merging development (Dev) and operations (Ops) teams to streamline collaboration, culture, practices, and tool integration.

---

### Continuous Integration (CI)

**Definition:**
Continuous Integration is a DevOps practice where developers frequently merge their code into a central repository. After each commit, automated builds and tests are triggered to catch errors early in the development cycle.

**CI Process Steps:**
1. Code: Developer writes the code.
2. Fetch: Fetch the latest code before deployment.
3. Build: Build the code.
4. Test: Test the new code for compatibility and functionality.
5. Notify: Notify developers about any errors or issues.
6. Feedback: Provide feedback if the build succeeds, allowing progression to production.

**Version Control Tools:**
- Git
- SVN
- TFS
- Perforce

**CI Build Tools:**
- Maven, Ant, Gradle
- MSBuild, Visual Build
- IBM UrbanCode, Make

**CI Repository Tools:**
- Sonatype Nexus
- JFrog Artifactory
- Archiva

**CI Tools:**
- Jenkins
- CircleCI
- TeamCity
- Bamboo

---

### Continuous Delivery (CD)

**Definition:**
Continuous Delivery automates the process of preparing code changes for release into production environments, ensuring fast and reliable deployments with minimal manual intervention.

**CD Process:**
1. The development team generates an artifact.
2. The artifact is built, tested, and sent to the operations team.
3. Automated steps handle server provisioning, configuration changes, dependency installation, and artifact deployment.
4. Continuous testing is performed automatically (functional, load, DB, security).

**Automation Tools:**
- **System Automation:** Ansible, Puppet, Chef
- **Cloud Infrastructure:** Terraform, CloudFormation
- **CI/CD Automation:** Jenkins, Octopus Deploy
- **Testing Automation:** Functional, Load, Security, Performance

---

### Virtualization

**What is Virtualization?**
Virtualization allows one physical machine to run multiple operating systems as isolated virtual machines (VMs), enabling efficient resource utilization.

**Virtualization Components:**
- **Hypervisor:** A software tool that creates and manages VMs.  
   - **Type 1:** Bare-metal hypervisor (e.g., VMware ESXi, Xen).
   - **Type 2:** Software-based hypervisor for testing (e.g., Oracle VirtualBox, VMware Server).
- **Snapshot:** Backup method for VMs.

**VM Setup:**
- Install OS manually or automate using Vagrant (VirtualBox as hypervisor).

---

### Vagrant for Virtual Machine Setup

**Vagrant Overview:**
Vagrant automates VM setup through predefined configurations called "Vagrant boxes." It manages virtual machines using a configuration file (Vagrantfile).

**Tools Required:**
- Virtual Technology enabled in BIOS
- Vagrant
- Hypervisor (Oracle VirtualBox, etc.)
- Command-line tools (Git Bash, Cygwin)

**Common Vagrant Commands:**
- `vagrant up`: Start the VM.
- `vagrant ssh`: SSH into the VM.
- `vagrant halt`: Stop the VM.
- `vagrant destroy`: Remove the VM.
- `vagrant reload`: Reboot the VM.

---

### Linux Basics for DevOps

**Introduction to Linux:**
Linux is an open-source, Unix-like operating system kernel combined with GNU utilities. It is widely used in DevOps for server and infrastructure management.

**Linux Architecture:**
- **Hardware:** Core component.
- **Kernel:** Manages system resources and interacts with hardware.
- **Shell:** Interface to execute Linux commands.
- **Applications:** Layer around the shell for running software.

**Popular Linux Distributions:**
- **RPM-based:** RHEL, CentOS, Oracle Linux
- **Debian-based:** Ubuntu, Kali Linux

**Linux File System:**
- `/root`: Admin home directory.
- `/bin`, `/usr/bin`: User executables.
- `/sbin`, `/usr/sbin`: System admin executables.
- `/etc`: Configuration files.
- `/var`: Server data (e.g., web server, MySQL data).

---

### Common Linux Commands

**Basic Commands:**
- `pwd`: Print current directory.
- `ls`: List files in the directory.
- `cd /`: Change to the root directory.
- `uptime`: Show system uptime.
- `free -m`: Show memory usage.

**File Operations:**
- `mkdir <dir>`: Create a new directory.
- `touch <file>`: Create a new file.
- `cp <source> <destination>`: Copy files or directories.
- `mv <source> <destination>`: Move or rename files.
- `rm <file>`: Delete a file.

---

These summarized notes provide a structured overview of the core DevOps concepts, including CI/CD practices, virtualization, VM setup using Vagrant, and Linux basics.

### Linux Commands and Utilities

#### Directory and File Operations
- `rm -r *`: Removes everything from the current directory.
- `rm -rf *`: Removes everything from the current directory forcefully.
- `history`: Displays all executed commands in the current session.

#### Vim Editor
- **Installation**: `sudo yum install vim -y`
- **File Creation/Editing**: `vim <filename>` creates or opens a file.
  
##### Vim Modes:
1. **Command Mode**: Default mode when a file is opened.
2. **Insert Mode**: Press `i` to enter, and insert text.
3. **Extended Command Mode**: Access using `:`

##### Vim Commands:
- `:wq`: Save and quit.
- `:q`: Quit without saving.
- `:q!`: Force quit without saving.
- `:se nu`: Display line numbers.
- `G`: Go to the last line.
- `gg`: Go to the first line.
- `yy`: Copy the current line.
- `p`: Paste below.
- `P`: Paste above.
- `u`: Undo.
- `5dd`: Delete the next 5 lines.
- `/word`: Search for a word.

#### File Types in Linux
- Use `file <filename>` to determine the file type.
- **File Type Indicators**: 
  - `d`: Directory
  - `c`: Character file
  - `b`: Block file
  - `l`: Symbolic link

##### Commands:
- `mkdir -p <directory_path>`: Create a directory and any necessary parent directories.
- `ln -s <source_path> <destination_path>`: Create a symbolic link.

#### File Sorting and Hostname Configuration
- `ls -lt`: List files sorted by timestamp (latest first).
- `ls -lrt`: List files sorted by timestamp (latest last).
- Change hostname: Edit `/etc/hostname/` using vim.

### File Search and Filters

#### Grep Command
- `grep <word> <file_path>`: Search for a word in a file.
- `grep -i <word> *`: Case-insensitive search in all files.
- `grep -v <word> <path>`: Exclude lines containing the word.

#### File Content Commands
- `less <file_name>`: View the file contents, better than `cat`.
- `head -n <num_lines> <file_name>`: Display the first n lines.
- `tail -f <file_name>`: Show dynamic content updates of a file.
  
#### Cut and Awk
- `cut -d<delimiter> -f<field> <file>`: Extract specific fields using a delimiter.
- `awk -F'<delimiter>' '{print $<field>}' <file>`: Filter and display specific fields.

#### Text Replacement
- Vim: `:%s/old/new/g` (global replacement).
- Sed: `sed -i 's/old/new/g' <file_name>` (global replacement with save).

### Redirections

#### Output Redirection
- `> filename`: Redirect output to a file (overwrite).
- `>> filename`: Append output to a file.
- `/dev/null`: Discard output (e.g., `yum install vim -y > /dev/null`).

#### Memory and Disk Space
- `free -m`: Display memory usage.
- `df -h`: Display disk partition usage.

#### Error Redirection
- `command 2>> filename`: Capture standard error in a file.

#### Word Count and Pipes
- `wc -l <file_name>`: Count the number of lines in a file.
- `ls | wc -l`: Count the number of files in the current directory.
- `ls | grep <pattern>`: Search for files matching a pattern.

#### Find and Locate Commands
- `find /etc -name host*`: Search for files by name.
- `locate <file_name>`: Find files (requires `mlocate` installation).

### Users and Groups

#### User Types in Linux
1. **Root User** (ID = 0)
2. **Regular User** (ID = 1000-60000)
3. **Service User** (ID = 1-999)

#### Password and Group Files
- `/etc/passwd`: Stores user information.
- `/etc/group`: Stores group information.

#### User Management
- `useradd <new_user>`: Add a new user.
- `groupadd <group_name>`: Add a new group.
- `usermod -aG <group_name> <user>`: Add user to a group (secondary group).
- `passwd <user>`: Set or reset the password.

#### Checking User and Group Information
- `id <user>`: Show user and group information.
- `tail -4 /etc/passwd`: Check the last 4 users added.
- `tail -4 /etc/group`: Check the last 4 groups added.

#### Track User Activity
- `last`: Show recent logins.
- `who`: Display currently logged-in users.
- `lsof -u <user>`: List open files by a user.

#### Deleting Users and Groups
- `userdel -r <user>`: Delete a user along with the home directory.
- `groupdel <group>`: Delete a group.

## DevOps Notes

### 1. **Users and Groups**
- To reset the password for the current user: 
  ```bash
  passwd
  ```

### 2. **File Permissions**
- Every file in Linux has its own permissions, which can be viewed using:
  ```bash
  ls -l <file_path>
  ```
  - The first character indicates the file type ('d' for directory, 'l' for link).
  - Next three characters (rwx) represent the permissions for the user, group, and others.
  - To check directory permissions:
    ```bash
    ls -ld <folder_name>
    ```

- **Changing ownership and permissions**:
  - Change ownership:
    ```bash
    chown user_name:group_name <folder_name>
    ```
  - Change permissions:
    ```bash
    chmod o-x <folder_name>  # Remove execute permission for others
    chmod o-r <folder_name>  # Remove read permission for others
    chmod g+x <folder_name>  # Add execute permission for group
    ```

- **Permission Methods**:
  - **Symbolic Method**: Modify permissions using letters (rwx).
  - **Numeric Method**: Represent permissions using numbers. For example:
    - `4` for read, `2` for write, `1` for execute.

### 3. **Sudo Privileges**
- **Sudo** allows a normal user to execute commands as the root user.
- Common commands:
  ```bash
  sudo yum install <package> -y
  sudo useradd <user_name>
  sudo -i  # Switch to root user
  ```

- **Adding a user to the sudoers file**:
  ```bash
  visudo
  ```
  Add the following line to give a user sudo privileges:
  ```bash
  ansible ALL=(ALL) ALL
  ```

- To allow a user to run sudo commands without a password:
  ```bash
  ansible ALL=(ALL) NOPASSWD: ALL
  ```

### 4. **Package Management**
- Install packages using **yum**:
  ```bash
  yum install <package_name>
  yum remove <package_name>
  ```

- **rpm** commands for installing, verifying, or deleting packages:
  ```bash
  rpm -i <package_file>    # Install package
  rpm -qa | grep <package>  # Check installed packages
  rpm -e <package_name>     # Remove package
  ```

### 5. **Services**
- **httpd Service**: Install, check, and manage services like httpd (web server).
  - To install and check status:
    ```bash
    yum install httpd -y
    systemctl status httpd
    systemctl start httpd  # Start service
    ```

- To enable a service at boot:
  ```bash
  systemctl enable <service_name>
  ```

### 6. **Processes**
- **top**: Shows running processes dynamically.
- **ps**: Shows static process info:
  ```bash
  ps aux
  ```

- **Kill a process**:
  ```bash
  kill <process_id>
  kill -9 <process_id>  # Forcefully kill process
  ```

- Use `awk` and `xargs` to filter and kill multiple processes:
  ```bash
  ps -ef | grep <process> | awk '{print $2}' | xargs kill -9
  ```

### 7. **Archiving**
- **tar** for creating and extracting archives:
  - Create:
    ```bash
    tar -czvf <archive_name> <directory>
    ```
  - Extract:
    ```bash
    tar -xzvf <archive_name>
    ```

- **zip** and **unzip**:
  ```bash
  yum install zip unzip -y
  zip -r <archive_name> <directory>
  unzip <archive_name>
  ```

### 8. **Ubuntu vs CentOS Commands**
- **Package management in Ubuntu** uses `apt`:
  - Update repositories:
    ```bash
    apt update
    apt upgrade
    ```
  - Install package:
    ```bash
    apt install <package_name>
    ```

- **User creation in Ubuntu**:
  - Ubuntu:
    ```bash
    adduser <user_name>
    ```

### 9. **Docker Introduction**
- **Isolation**: Services are isolated using containers, which share the OS but run separately.
- Each service runs in a container with its own binaries, libraries, and dependencies.
- Containers are smaller than VMs and do not require a separate OS.

### Docker Overview

#### Introduction to Containers
- Containers share the host machine's OS kernel, making them lightweight compared to virtual machines.
- Containers package software, including code and dependencies, into a standardized unit for development, shipment, and deployment.
- Unlike virtual machines, containers do not require a separate OS, as they use the host OS for compute resources.
  
#### Isolation vs Virtualization
- **Containers:** Provide OS-level isolation, utilizing the host machine’s OS and sharing resources.
- **Virtual Machines (VMs):** Virtualize hardware, offering separate virtualized environments (e.g., virtual RAM, disk).
- Containers do not require a guest OS, whereas VMs need a fully independent OS.

#### Docker and Containers
- Docker is a platform that manages containers, acting as a container runtime environment.
- While containers can run without Docker, Docker simplifies container management by automating tasks like creating namespaces and c-groups.
- Docker was originally known as DotCloud and used Amazon EC2 to run applications using Linux Containers (LXC). 
- Docker images, which are reusable and customizable, brought containerization into the spotlight, contributing to Docker's success.

### Features of Docker Containers
- **Portability:** Docker images follow a standard, allowing containers to run anywhere with Docker installed.
- **Lightweight:** Containers don’t need an OS, as they share the system's OS.
- **Security:** Docker provides robust isolation capabilities, ensuring applications inside containers remain secure.

### Docker Setup

#### EC2 Instance Setup
1. **Launch EC2 Instance:** Choose an Amazon Machine Image (AMI), instance type (e.g., t2.micro for free-tier), and configure instance details.
2. **Add Storage:** Configure root volume and additional volumes as needed. Instance volumes are temporary and non-persistent.
3. **Add Tags:** Use key-value pairs to identify and manage instances.
4. **Configure Security Groups:** Set inbound and outbound rules for network traffic, typically allowing SSH access from your IP address.
5. **Review and Launch:** After configuring, download the key pair to securely access the instance via SSH.

#### Accessing EC2 Instances
- Use a key pair (private key) to securely SSH into an EC2 instance. 
- Example SSH command:
   ```bash
   ssh -i <path-to-.pem-file> ubuntu@<ip-address>
   ```

#### Installing Docker on Ubuntu
1. Follow official Docker installation steps for Ubuntu [Docker Install Guide](https://docs.docker.com/engine/install/ubuntu/).
2. Verify Docker installation:
   ```bash
   systemctl status docker
   docker images
   ```
3. Allow non-root users to access Docker daemon:
   ```bash
   sudo usermod -aG docker ubuntu
   ```

### Docker Commands and Concepts

#### Working with Docker Containers
- **Run Docker Container:**
  ```bash
  docker run hello-world
  ```
  This creates and runs a container from the `hello-world` image.
  
- **List Containers:**
  - Running containers:
    ```bash
    docker ps
    ```
  - All containers (including stopped ones):
    ```bash
    docker ps -a
    ```

- **Starting and Stopping Containers:**
  - Start container:
    ```bash
    docker start <container-id>
    ```
  - Stop container:
    ```bash
    docker stop <container-id>
    ```

#### Docker Images
- Docker images consist of multiple layers, where each `RUN` command in a Dockerfile creates a new layer.
- Images can be stored in registries like Docker Hub, Google Container Registry (GCR), or Amazon Elastic Container Registry (ECR).
- Images are read-only, and containers are created from these images with an additional writable layer.

#### Docker Networking
- Containers can be run in detached mode (`-d`), allowing background execution, and port mapping can be specified using the `-p` option:
  ```bash
  docker run --name myweb -p 7090:80 -d nginx
  ```
  This maps port 7090 on the host to port 80 inside the container.

#### Executing Commands in Containers
- Run commands inside a container:
  ```bash
  docker exec <container-name> <command>
  ```
- Access the container’s shell:
  ```bash
  docker exec -it <container-name> /bin/bash
  ```

#### Managing Docker Containers and Images
- **Remove container:**
  ```bash
  docker rm <container-id>
  ```
- **Remove image:**
  ```bash
  docker rmi <image-id>
  ```
  (Note: Containers using an image must be stopped before the image can be removed.)

# Docker Commands and Concepts

## Docker Logs
- Command: `docker logs <container-name/container-id>` retrieves container logs.
- For viewing image metadata: Use `docker inspect <image-name>`, which provides output in JSON format.
- In detached mode, logs can be viewed using the `docker logs` command since output isn't visible in the shell.
- Logs only show the output of executed commands inside the container, not application-specific logs.

## Environmental Variables
- Variables can be exported as environmental variables during container runtime.
- Command: `docker run -d -P -e <VARIABLE_NAME>=<variable_value> <image-name>`.

## Docker Volumes
- Docker provides two methods for persistent data storage:
  1. **Volumes**: Managed by Docker, typically located at `/var/lib/docker/volumes/`.
  2. **Bind Mounts**: Stored anywhere on the host system.

### Docker Volume Commands
- Create a volume: `docker volume create mydbdata`.
- List volumes: `docker volume ls`.
- Mounting a volume: `-v hostlocation:remotedirlocation`.

## Accessing Containers
- To enter a running container's shell: `docker exec -it <container-name> /bin/bash`.

## Docker Volume vs. Bind Mounts
- **Bind Mounts**: Used for adding files during container execution.
- **Volumes**: Preferred for data persistence, allowing more control over where data is stored.

## Container Removal
- Remove a container: `docker rm <container-name>`.
- Remove an image: `docker rmi <image_name>`.

# Docker Image Building

## Dockerfile Instructions
- **FROM**: Specifies the base image.
- **LABEL**: Adds metadata.
- **RUN**: Executes commands.
- **ADD/COPY**: Transfers files into the container.
- **CMD**: Specifies the default command to run.
- **ENTRYPOINT**: Defines an executable that will always run.
- **VOLUME**: Defines a mount point for external volumes.
- **EXPOSE**: Declares ports on which the container listens.
- **ENV**: Sets environment variables.
- **USER**: Defines the user.
- **WORKDIR**: Sets the working directory.
- **ARG**: Allows passing build-time variables.
- **ONBUILD**: Adds trigger instructions for later stages.

## Optimizing Dockerfile
- Reduce layers by combining multiple `RUN` commands using `&` to minimize image size.

## Docker Image Example
- Base Image: `ubuntu:latest`.
- Commands:
  1. Install updates and packages (e.g., Apache2).
  2. Set Apache to run in the foreground.
  3. Expose port 80 to the host.
  4. Define a working directory and volume for log data.
  5. Use `docker run -d --name nanowebsite -p 9080:80 nanoimg:V2` to run the image in detached mode, mapping port 9080 on the host to port 80 in the container.

## Pushing Docker Images to Docker Hub
Steps to push an image:
1. Sign up on Docker Hub.
2. Create a repository.
3. Login to Docker: `docker login`.
4. Push image: `docker push <DockerHubUName>/<ImageName>:<VersionName>`.

# Docker Compose

## Overview
Docker Compose is used to run multi-container applications. It defines services in a YAML file and allows running all containers with a single command: `docker compose up`.

## Steps to Run Docker Compose
1. Define environment in a `Dockerfile`.
2. Configure services in `docker-compose.yaml`.
3. Run `docker compose up`.

## Common Commands
- `docker-compose ps`: View running containers.
- `docker compose up -d`: Run in detached mode.
- `docker compose down`: Stop and remove containers.

# Multi-Stage Dockerfile

## Purpose
- Reduces the image size by separating build and runtime stages. Dependencies and source code are built in one stage, while only necessary artifacts are copied to the final image.

## Example
1. First stage downloads dependencies.
2. Second stage copies artifacts, discarding unnecessary data.

---

This summary covers the major Docker concepts, commands, and best practices as discussed in the provided transcript.

## DevOps Notes: Containerization & Kubernetes

### 1. **Containerization: Build & Run Microservice App**

#### **Amazon EC2 Instance Setup**
- When using multiple containers dependent on each other, local machines may not suffice.
- A **t3-medium** instance (chargeable) is recommended for better performance; **t2-micro** is free but insufficient.
- Create a key pair for SSH access and allow HTTP for internet access (e.g., for Nginx).
- Modify storage to 20GB and add necessary Dockerfile code in the **User Data** section during instance creation.

#### **Connecting to EC2 Instance**
- Access the instance via SSH using the downloaded PEM file:  
  ```bash
  ssh -i Downloads/dockerKey.pem ubuntu@<IP-Address>
  ```
- Pre-installed Docker and Docker Compose are ready based on the user data entered earlier.

#### **Building Docker Images**
- Navigate to the project directory and use:
  ```bash
  docker-compose build
  ```
  This command builds all images declared in the `docker-compose.yaml`.

#### **Running Containers**
- To run the built images, use:
  ```bash
  docker-compose up
  ```
- Monitor running containers with:
  ```bash
  docker ps
  ```
- View logs for individual containers:
  ```bash
  docker logs <container-id>
  ```

#### **Updating and Rebuilding Images**
- After modifying the code, rebuild using:
  ```bash
  docker-compose build
  ```
  This ensures only changes are built, not the entire image.

#### **Stopping Containers**
- Stop and remove all running containers:
  ```bash
  docker-compose down
  ```

#### **Cost Control**
- Stop the instance when not in use to avoid charges.

---

### 2. **Kubernetes: Introduction**

#### **What is Kubernetes?**
- Kubernetes is a popular container orchestration tool.
- It manages clusters of Docker engines to ensure high availability.
- If one Docker engine fails, others take over the workload.

#### **Kubernetes Architecture**
- **Nodes**: Docker engines in a cluster.
- **Master Node**: Controls and monitors the worker nodes, redistributing workloads when necessary.
- **Worker Nodes**: Execute tasks as instructed by the master node.

#### **Container Orchestration Tools**
1. Docker Swarm
2. Kubernetes
3. Mesosphere Marathon
4. AWS ECS & EKS
5. Azure Container
6. Google Container Engine
7. CoreOS Fleet
8. OpenShift

#### **Kubernetes Origins**
- Initially created by Google (known as **Borg**).
- Partnered with the Linux Foundation to form the **Cloud Native Computing Foundation (CNCF)**.
- Other tools include **Minikube**, **Kubeadm**, **Kops**, and **Istio** (a load balancer).

---

### 3. **Key Kubernetes Features**

#### **1. Service Discovery & Load Balancing**
- Containers are called **pods** in Kubernetes.
- Kubernetes automatically balances the load across pods and replicates failed pods.

#### **2. Storage Orchestration**
- Supports storage solutions like **SAN**, **NetApp**, **AWS EBS**, **CephFS**, allowing easy rollback for stateful containers.

#### **3. Automated Rollouts & Rollbacks**
- Kubernetes automates application updates and rollbacks.

#### **4. Automated Bin Packing**
- Ensures containers are placed on the appropriate nodes with suitable resources.

#### **5. Self-Healing**
- If a node fails, Kubernetes automatically replaces it with a replica, maintaining service availability.

#### **6. Managing Secrets & Configurations**
- Kubernetes allows secrets and configurations to be managed as variables and volumes, using encoded values.

## DevOps Notes: Kubernetes Architecture

### 1. **Kubernetes Architecture Overview**
- Kubernetes has two types of nodes:
  - **Master Node (Control Plane):** Manages the cluster and worker nodes.
  - **Worker Nodes:** Where Docker engines run the containers.

- Communication with worker nodes is done through the master node. 
- The master node's services communicate with the worker nodes via **YAML files**.

### 2. **Master Node Services**

#### **Kube API Server**
- Handles communication across the Kubernetes cluster.
- Acts as the frontend for the Kubernetes cluster.
- Administrators use **kubectl** to interact with the API server.

#### **ETCD Server**
- A key-value store that holds configuration, state data, and service discovery details of the cluster.
- **Backup is essential** to avoid data loss in case of failure.

#### **Kube Scheduler**
- Assigns newly created pods to appropriate nodes based on factors like resource requirements, hardware/software constraints, and data locality.
- Supports **affinity/anti-affinity** rules to control pod placement.

#### **Controller Manager**
- Manages different controllers (e.g., Node, Replication, Endpoints) in a single process.
- Key controllers:
  - **Node Controller:** Detects and responds to node failures.
  - **Replication Controller:** Ensures the correct number of pods are running.
  - **Endpoints Controller:** Connects services and pods.
  - **Service Account & Token Controllers:** Manages authentication and authorization.

---

### 3. **Worker Node Components**

#### **Kubelet**
- Agent running on each worker node.
- Ensures containers within pods are running by fetching and executing container images as instructed by the scheduler.

#### **Kube Proxy**
- A network proxy running on each worker node.
- Allows custom network configurations such as IP allow/deny lists.

#### **Container Runtime**
- Kubernetes supports various container runtimes (e.g., **Docker**, **containerd**, **cri-o**).
- Unlike Docker Swarm, Kubernetes supports multiple container runtimes.

---

### 4. **Additional Worker Node Components (Add-ons)**
- **DNS**: Provides domain name resolution for containers.
- **WebUI**: User interface for cluster management.
- **Container Resource Monitoring**: Tracks resource usage of containers.
- **Cluster Level Logging**: Logs cluster activity, often sourced from third-party vendors.

---

### 5. **Pod and Container Architecture**

#### **Pod Structure**
- A **pod** contains one or more containers and provides resources for them (similar to how a virtual machine runs processes).
- The container runs inside the pod, and the pod provides isolation, not virtualization.

#### **Pod Communication**
- Each pod has an IP address, enabling communication via the pod's IP and the container’s port number.
- Pods can share **volumes** with containers, providing persistent storage across containers within the same pod.

## DevOps Notes: Kubernetes Tools and Objects

### 1. **Pods and Containers in Kubernetes**
- **Pod Structure**: 
  - A pod can contain one main container and multiple helper or **init containers**.
  - **Main containers** cannot run together in the same pod, e.g., MySQL and Tomcat can't be in one pod.
  - Helper containers assist the main container but have limited roles.

### 2. **Pod Networking and Communication**
- **Worker Node Networking**:
  - Pods across multiple worker nodes can interact using a **subnet** or private network.
  - **Bridge zero** acts like a switch for communication within the same node.
  - **wg0 (router)** forwards requests to the correct node based on IP addresses, allowing pods from different nodes to communicate.

### 3. **Kubernetes Cluster Tools**

#### **Minikube**:
- Used for **testing and learning** purposes with a single-node Kubernetes cluster.
- Runs Kubernetes on a local VM (using VirtualBox).
- Ideal for setting up a local Kubernetes cluster but not suited for production.

#### **Kubeadm**:
- Popular for setting up **production Kubernetes clusters**.
- Supports multiple nodes (both master and worker) on various platforms (VMs, EC2, physical machines).

#### **Kops**:
- Best for **multi-node Kubernetes clusters** on AWS and other cloud platforms like Google Cloud, Digital Ocean, etc.
- Stable for production use.

### 4. **Minikube Setup Process**
- **Installing Minikube**:
  1. Open **PowerShell as Administrator**.
  2. Install Chocolaty, then use it to install Minikube (`choco install minikube kubernetes-cli -y`).
  3. Start Minikube (`minikube start`), which creates a local Kubernetes cluster inside a VM.
  
- **Basic Kubernetes Commands**:
  - `kubectl get nodes`: Check the nodes running in the cluster.
  - `kubectl get pod`: List all available pods.
  - `kubectl expose deployment <deployment-name>`: Expose a deployment.
  - `minikube service <service-name> --url`: Get the URL to access the exposed deployment.

### 5. **Minikube Cluster Maintenance**
- **Stopping and Deleting**:
  - `minikube stop`: Stops the Minikube cluster.
  - `minikube delete`: Deletes the Minikube VM.

### 6. **Kops Setup Process**
- **Kops Setup for AWS**:
  1. Purchase a domain (e.g., **groophy.in**) for Kubernetes DNS records.
  2. Create a **Linux VM** and install **kops, kubectl, ssh keys, and awscli**.
  3. Set up an S3 bucket and configure **IAM user** for AWS CLI.

### 7. **Kubernetes Objects**

#### **Key Kubernetes Objects**:
- **Pod**: The smallest object in Kubernetes. Changes to containers are made via pods.
- **Service**: Provides a **static endpoint** (like a load balancer) for a pod.
- **Replica Set**: Ensures **replication** of pods for scalability.
- **Deployment**: Most commonly used object for rolling out updates or deploying new versions.
- **ConfigMap**: Stores **configuration variables** for applications.
- **Secret**: Used to store **sensitive information**, like credentials, without exposing them in plaintext.
- **Volumes**: Provides persistent storage to pods, similar to **EBS volumes** in AWS.

### 8. **Managing Kubernetes Objects**
- **Using YAML**: 
  - Define multiple objects in a **YAML file** and apply them using `kubectl apply <url-for-yaml>`.
  - Example commands:
    - `kubectl get deployments`: Check if the deployment is created.
    - `kubectl delete deploy <deployment-name>`: Deletes a deployment and its associated pod.

## DevOps Notes: Kubernetes Configuration and Namespace Management

### 1. **Kube Config File**

- **Purpose**: The kube config file helps `kubectl` connect to the Kubernetes cluster and access cluster resources.
  
- **Contents**:
  1. **Cluster Information**: Details about the Kubernetes cluster.
  2. **User Information**: Specifies users accessing the cluster.
  3. **Namespaces**: Grouping of resources in the cluster.
  4. **Authentication Mechanisms**: Similar to SSH, including IP address, username, password, or key-based authentication.
  
- **File Location**: `~/.kube/config` holds details like cluster information, server certificates, and API server details.

- **How `kubectl` Connects**:
  - The kube config file provides the cluster's **API server** address, which connects to the master node.
  - IP addresses of the master node are stored in **Route53** (for AWS setups via Kops) and can be verified in **EC2 Instances**.
  
- **Contexts**:
  - A kube config file may have **multiple contexts** (combinations of clusters and users).
  - **Current-context** defines the active cluster and user configuration that `kubectl` uses when commands are executed.

- **Automation Integration**:
  - Kube config is required for CI/CD tools like **Jenkins** or **Ansible**.

### 2. **Namespaces in Kubernetes**

- **Purpose**: Namespaces allow for resource grouping and isolation within the Kubernetes cluster.

- **Default Namespaces**:
  - **Default**: Standard namespace.
  - **kube-system**: Contains control plane resources (e.g., ETCD manager, KOPS-controller, Kube-Proxy).
  - **kube-public**: Public resources.
  
- **Namespace Usage**:
  - Useful for dividing resources between **projects or environments** (e.g., development, production).
  - Resources need to be unique within a namespace but not across namespaces.
  - Namespace-based scoping applies to namespaced objects (e.g., Deployments, Services), not cluster-wide objects (e.g., Storage classes, Nodes).

- **Namespace Commands**:
  - List all namespaces: `kubectl get ns`
  - List objects in default namespace: `kubectl get all`
  - List objects across all namespaces: `kubectl get all --all-namespaces`
  - Get service object from another namespace: `kubectl get svc -n <namespace-name>`

- **Namespace Creation and Deletion**:
  - Create a namespace: `kubectl create ns <namespace-name>`
  - Create a pod in a specific namespace: `kubectl run <pod-name> --image=<image-name> -n <namespace-name>`
  - Apply objects using a YAML file: `kubectl apply -f <yaml-file>`
  - Delete a namespace and its contents: `kubectl delete ns <namespace-name>`

  ## DevOps Notes: Kubernetes Pods and Services

### 1. **Pods in Kubernetes**

- **Definition**: A pod is the smallest and most basic unit in Kubernetes, encapsulating containers. It represents a process running in the cluster.

- **Structure**:
  - Typically, one container per pod is the common setup, but **multi-container pods** can be created where additional containers serve as helper or init containers.
  - **Pod Abstraction**: Kubernetes manages pods, not containers directly. Commands are executed at the pod level.
  
- **Resource Sharing**: Containers in a multi-container pod share the same resources provided by the pod.

- **Pod Scaling**:
  - **Horizontal Scaling**: Adding more nodes to the cluster.
  - **Vertical Scaling**: Increasing the resources (CPU, memory) of the current node.

- **Pod Definition File**:
  - Pods are defined using YAML files, which typically include:
    - **apiVersion**: Defines the version of the API (e.g., `v1` for Pod, `apps/v1` for Deployment).
    - **kind**: Type of object (Pod, Service, Deployment).
    - **metadata**: Contains information like pod name, labels (key-value pairs).
    - **spec**: Includes container configurations (image, ports, etc.).

- **Important Commands**:
  - Create an object: `kubectl create -f <yaml-file>`
  - Get pod details: `kubectl get pod`
  - Describe pod: `kubectl describe pod <pod-name>`
  - Get pod details in YAML format: `kubectl get pod <pod-name> -o yaml`
  - Delete a pod: `kubectl delete pod <pod-name>`

### 2. **Pod Logging and Troubleshooting**

- **Common Pod States**:
  - **Running**: Pod is working as expected.
  - **ImagePullBackOff**: Indicates an issue with pulling the container image.
  - **CrashLoopBackOff**: Occurs when a container is failing to restart repeatedly.

- **Troubleshooting Commands**:
  - View pod logs: `kubectl logs <pod-name>`
  - Check for errors: `kubectl describe pod <pod-name>`
  - Get pod status with detailed logging: `kubectl get pod -o wide`

### 3. **Services in Kubernetes**

- **Purpose**: A service connects pods and exposes applications running in pods to external or internal networks.

- **Why Use Services?**
  - Pods have **dynamic IPs**, which change upon pod replacement. Services provide a **static endpoint** to ensure consistent communication.

- **Types of Services**:
  - **NodePort**: Exposes the pod on a specific port on the node. Typically used for development or non-production purposes.
  - **ClusterIP**: Provides internal network connectivity within the cluster. Ideal for internal services like communication between Tomcat and MySQL.
  - **LoadBalancer**: Used for production use cases where the service needs to be exposed to the internet. Automatically integrates with cloud providers’ load balancers.

- **Service Structure**:
  - Services connect to pods using **label selectors**, which match the labels of the target pods.
  - Each service has its own **IP address**, independent of the pod IP address.

- **Service Commands**:
  - Create a service: `kubectl create -f <yaml-file>`
  - Get service details: `kubectl get svc`
  - Describe service: `kubectl describe svc <service-name>`
  
## Kubernetes Notes

### 1. **Kubernetes Pods**
- **Pods**: The most basic unit in Kubernetes. A pod runs applications in isolated environments with containers inside them.
  - Pods are wrappers around containers, and Kubernetes manages pods instead of containers directly.
  - A pod represents a process running in the cluster, usually running one container per pod.
  - In multi-container pods, there’s usually one main container, with additional helper containers.

- **Multi-container Pods**: When running an application with components like Tomcat, MySQL, and RabbitMQ, each component should be in its own pod. It is better to avoid putting multiple containers in one pod for these types of applications.

- **Horizontal and Vertical Scaling**:
  - **Horizontal Scaling**: Adding more nodes to the cluster.
  - **Vertical Scaling**: Increasing the capacity (CPU, memory) of existing nodes.

### 2. **Pod YAML Structure**
- **YAML Components**:
  - `apiVersion`: Defines the version of the Kubernetes API.
  - `kind`: Describes the object type (e.g., Pod, Service, Deployment).
  - `metadata`: Includes information like the pod’s name and labels.
  - `spec`: Specifies the containers and their configurations.
  - Common `apiVersion` values:
    - Pod: `v1`
    - Service: `v1`
    - Deployment: `apps/v1`
    - Ingress: `networking.k8s.io/v1beta1`
  
### 3. **Kubectl Commands**
- `kubectl create -f <yaml-file>`: Creates a Kubernetes object from a YAML file.
- `kubectl get pod`: Retrieves details about running pods.
- `kubectl describe pod <pod-name>`: Gets detailed information about a specific pod (similar to `docker inspect`).
- `kubectl edit pod <pod-name>`: Allows partial editing of a pod’s configuration.

### 4. **KOPS Cluster**
- **KOPS Commands**:
  - `kubectl validate`: Validates the cluster, showing the number of master and worker nodes.
  - `kubectl get nodes`: Retrieves information about the cluster nodes.
  - `kubectl describe node <node-name>`: Provides detailed information about a specific node.

### 5. **Pod Logging and Debugging**
- **Common Issues**:
  - `ImagePullBackOff`: Indicates an issue pulling the image due to a wrong name.
  - `CrashLoopBackOff`: Occurs when a container repeatedly fails to start.

- **Key Commands**:
  - `kubectl logs <pod-name>`: Retrieves logs from a container within the pod.
  - `kubectl get pod -o yaml`: Provides the YAML configuration of the pod for debugging.

### 6. **Kubernetes Services**
- **Service Purpose**:
  - Connects external requests to the internal pod network.
  - Exposes applications running inside pods to external users.
  - Provides dynamic IP addresses to avoid issues when replacing pods.
  
- **Service Types**:
  - **NodePort**: Exposes the service on a fixed port number, allowing access from outside the cluster.
  - **ClusterIP**: Internal communication within the cluster, often used for backend services like MySQL.
  - **LoadBalancer**: Exposes services to the internet, typically used for production.

- **NodePort Example**:
  - A service object has frontend and backend ports that map the requests to the correct pods using labels.
  - External port (e.g., `30001`) maps to the internal pod’s container port (e.g., `8080`).

- **LoadBalancer Example**:
  - Automatically creates an Elastic Load Balancer (ELB) in AWS, mapping the service to a pod.
  - Service rules route requests to the correct pods and return responses via the load balancer.

### 7. **ClusterIP Service**
- **Usage**: For internal communication between pods. For example, a pod wanting to access a Tomcat service will interact with the ClusterIP service on port `8080`.

### 8. **General Commands**
- `kubectl get svc`: Retrieves all services running in the cluster.
- `kubectl describe svc <service-name>`: Provides detailed information about a specific service.
- `kubectl get all`: Displays all running objects in the current namespace.

This summary provides an overview of Kubernetes components, including pods, services, scaling, and common commands.

## Kubernetes Notes

### 1. **ReplicaSet**

- **Purpose**: 
  - Ensures the availability of a specified number of identical pods.
  - Automatically replaces a pod if it crashes or goes down, eliminating manual intervention.
  - Distributes pods across worker nodes to balance load and availability.

- **ReplicaSet YAML Structure**:
  - **apiVersion**: Typically `apps/v1`.
  - **spec**: Contains the number of replicas and `matchLabels` to select pods.
  - **matchLabels**: Ensures that new replicas created match the specified pod labels.
  - Example: If `replicas` is set to 3 and `matchLabels` is set to `frontend`, the ReplicaSet will ensure there are always 3 pods running with the `frontend` label.

- **Key Commands**:
  - `kubectl get rs`: Check if the ReplicaSet is created and running.
  - `kubectl get pod`: View all pods created by the ReplicaSet.
  - **Scaling**:
    - `kubectl scale --replicas=<num> <replica-set-name>`: Scale the ReplicaSet up or down.
    - `kubectl edit rs <replica-set-name>`: Modify the ReplicaSet configuration directly.

- **Best Practices**:
  - Avoid direct scaling using commands in production; always modify and apply the YAML file to scale ReplicaSets.
  - Delete a ReplicaSet with `kubectl delete rs <replica-set-name>` when no longer needed.

---

### 2. **Deployment**

- **Purpose**:
  - Automates rolling updates and rollback of applications.
  - Ensures that any changes to the pod (e.g., container image updates) are applied in a controlled manner.
  - Manages and updates ReplicaSets to maintain the desired state of the application.

- **Deployment Workflow**:
  - **Creates ReplicaSet**: A Deployment defines a ReplicaSet that manages the pods.
  - **Desired State**: Deployment defines the desired state (e.g., number of replicas, container image version).
  - **Rolling Updates**: When changes are applied (e.g., updating image versions), pods are updated one by one without downtime.

- **Use Case**:
  - If you're running a pod with version `v1` of an image and want to update to version `v2`, the Deployment will update each pod one at a time, ensuring the application remains available.

- **Key Commands**:
  - **Creating a Deployment**:
    - `kubectl apply -f <deployment-file>`: Creates or updates a Deployment.
  - **Scaling**:
    - `kubectl scale deployment/<deployment-name> --replicas=<num>`: Scale the number of pods in a Deployment.
  - **Updating a Deployment**:
    - Modify the deployment YAML file (e.g., change the container image version) and apply the changes.
    - New pods will be created by the new ReplicaSet, while the old ReplicaSet will be scaled down.
  - **Rolling Back Changes**:
    - `kubectl rollout undo deployment/<deployment-name>`: Roll back to the previous version of the Deployment.
    - `kubectl rollout undo deployment/<deployment-name> --to-revision=<revision_number>`: Roll back to a specific revision.

- **Rollout Management**:
  - `kubectl rollout history deployment/<deployment-name>`: View the history of changes applied to a Deployment.
  - **Rollbacks**:
    - Revert to a previous Deployment version if issues arise.
    - Old ReplicaSets are reinstated, and new ReplicaSets are scaled down.

- **Best Practices**:
  - For updates in production, always define changes in the YAML file and apply them, allowing for controlled rollouts and potential rollbacks.

## Kubernetes Notes

### 1. **Commands and Arguments**

- **Overview**: In Kubernetes, commands and arguments for a container are defined at the pod level. Containers within a pod execute the commands, not the pod itself.
- **CMD and ENTRYPOINT**:
  - **CMD**: Provides default values for the container's execution, with only the last CMD in a Dockerfile being executed.
  - **ENTRYPOINT**: Has higher priority than CMD and can work with CMD to define executables and default parameters.
  - **Kubernetes Execution**: In a pod definition file, commands and arguments are passed using the `command` and `args` fields under the `spec.containers` section. This allows overriding image parameters.

---

### 2. **Volumes**

- **Overview**: Volumes in Kubernetes allow persistent storage for pods. This is essential as pods are temporary and data can be lost when they are deleted or terminated.
- **Types of Volumes**:
  - **HostPath**: Maps a directory from the host node to the pod. Changes in the pod persist in the host.
  - **Persistent Volumes**: Separate storage that persists beyond the lifecycle of the pod, often backed by cloud storage like Azure or AWS.
  
- **HostPath Configuration**:
  - **volumeMounts**: Defines where the volume will be mounted in the container.
  - **volumes**: Defines the source directory for the volume in the worker node, typically using `DirectoryOrCreate` to ensure the directory exists.

---

### 3. **ConfigMap**

- **Overview**: ConfigMaps allow you to inject configuration data into pods without hardcoding it into the container images. It is useful for handling environment variables or configuration files that change frequently.
  
- **Creating ConfigMaps**:
  - **Imperative Command**: 
    - `kubectl create configmap <name> --from-literal=<key>=<value>`.
    - `kubectl get cm`: View all ConfigMaps.
    - `kubectl describe cm <config-map-name>`: Get detailed information about a ConfigMap.
  - **Declarative Format**: Define the ConfigMap in a YAML file under `metadata` and `data`, then apply it using `kubectl`.

- **Using ConfigMaps in Pods**:
  - **Environment Variables**: Use `spec.containers.envFrom.configMapRef` or `spec.containers.env.valueFrom.configMapKeyRef` to map the ConfigMap to a pod.
  - **Volume Mounting**: Use `volumeMounts` and `volumes` sections to mount the ConfigMap as files in a pod. The keys in the ConfigMap can be referenced and stored at specific paths in the container.

- **Limitations**:
  - **Non-confidential Data**: ConfigMaps are not designed for sensitive information. For confidential data, use **Secrets** instead.
  - **Final Output**: After mounting, environment variables or files from the ConfigMap can be accessed within the container.

## Kubernetes Notes

### 1. **Secrets in Kubernetes**

- **Overview**: Secrets in Kubernetes store and manage sensitive data such as passwords, API keys, and tokens. Unlike ConfigMaps, which handle non-sensitive data, Secrets are used to ensure security by encoding or encrypting sensitive information.

---

### 2. **Creating Secrets**

- **Imperative Way**:
  - **Command**: `kubectl create secret generic <secret-name> --from-literal=<key>=<value>`
    - Example: `kubectl create secret generic db-secret --from-literal=MYSQL_ROOT_PASSWORD=somecomplexpassword`
    - Note: The values are **base64-encoded**, not encrypted.
  
- **For Files**:
  - **Command**: `kubectl create secret generic <secret-name> --from-file=<path-to-file>`
    - Example: `kubectl create secret generic db-user-pass --from-file=./username.txt --from-file=./password.txt`

---

### 3. **Base64 Encoding and Decoding**

- **Encoding**: `echo -n 'secretpass' | base64` (Encodes the value to base64)
- **Decoding**: `echo -n 'encodedvalue' | base64 --decode` (Decodes base64 back to its original value)
- Although base64 encoding adds a layer of security, it’s not encryption. The encoded data is visible to Kubernetes control plane, but generally secure unless access to the control plane is compromised.

---

### 4. **Declarative Format for Secrets**

- **Creating Secrets in YAML**:
  - The secret values should be encoded in base64 before being placed in the YAML file under the `data` section.
  - Example:
    ```yaml
    apiVersion: v1
    kind: Secret
    metadata:
      name: db-secret
    data:
      MYSQL_ROOT_PASSWORD: encoded_value_here
    ```

- **Using Secrets in Pods**:
  - Use `spec.containers.envFrom.secretRef` to refer to the secret in the pod's specification.

---

### 5. **Docker Registry Secrets**

- When pulling images from a private repository, Docker credentials are required. These credentials can be stored as a secret of type **docker-registry**.
  - **Command**:
    ```bash
    kubectl create secret docker-registry <secret-name> \
    --docker-email=<email> \
    --docker-username=<username> \
    --docker-password=<password> \
    --docker-server=<registry-url>
    ```
  
- **Using Docker Registry Secrets in Pods**:
  - Specify the secret in `imagePullSecrets` under the pod specification, allowing the pod to pull the image from a private registry without providing credentials each time.

---

### 6. **Retrieving Secrets**

- To view the contents of a secret, use the following command and decode the output:
  - **Command**: `kubectl get secret <secret-name> -o jsonpath='{.data.*}' | base64 -d`

---

### 7. **Running Commands in a Pod with Secrets**

- **Command**: `kubectl exec --stdin --tty <pod-name> -- /bin/bash`
  - This command opens a bash terminal inside a running pod that uses secrets.

### Kubernetes Ingress

#### Definition and Purpose
- **Ingress** is an API object that manages external access to services in a Kubernetes cluster, primarily through HTTP/HTTPS.
- It provides key features such as:
  - Load balancing
  - SSL termination
  - Name-based virtual hosting

#### Functionality
- Ingress exposes HTTP/HTTPS routes from outside the cluster to services within.
- The user interacts with the cluster, and the Ingress routes the request to the correct service based on predefined rules.
- Ingress acts as a **load balancer** and manages external access while providing SSL termination.

#### Ingress Controller
- To use Ingress, an **Ingress Controller** is needed, such as **NGINX**.
- The **NGINX Ingress Controller** works with the NGINX web server and acts as a proxy.
- The Ingress Controller can route requests from users to services within the cluster.

#### NGINX Ingress in AWS
- In AWS, a **Network Load Balancer (NLB)** is used to expose the NGINX Ingress Controller.
- Example commands to interact with namespaces and Ingress:
  - Check if a namespace exists: `kubectl get ns`
  - Check all objects in a namespace: `kubectl get all -n ingress-nginx`
- NGINX Controller pods route requests from the user to the appropriate service.

#### Creating and Configuring Ingress
1. **Create Deployment, Service, and Ingress:**
   - Use YAML files to define deployment, service, and ingress.
   - Ingress connects external users, load balances, and routes to the appropriate service, which accesses the necessary pod.
2. **Load Balancer and DNS Configuration:**
   - Retrieve DNS name from the Load Balancer section in AWS.
   - Create a CNAME record for this DNS name in a domain management tool (e.g., GoDaddy).
   - CNAME settings: `TYPE = CNAME`, `Name = Random`, `Value = DNS Name from AWS`.

#### Important Considerations
- Ensure the service is active and running before creating Ingress.
- Use `kubectl describe svc <service-name>` to find service endpoints and bind them to the Ingress.

#### Sample Ingress Setup Process
1. Set up **Ingress Controller**.
2. Deploy the **Service** (must be working and running).
3. Create a **DNS CNAME** record for the Load Balancer.
4. Create and apply the **Ingress**.
5. Verify Ingress with: `kubectl get ingress`.

#### Routing Types in Ingress
- **Port-based routing**
- **Host-based routing**
- **Path-based routing**

#### Deleting Ingress and Namespaces
- To delete a namespace and its objects: `kubectl delete ns <namespace-name>`.
- Alternatively, use a manifest file: `kubectl delete -f <manifest-link>.yaml`.

#### Documentation Reference
- Always refer to the **Ingress documentation** for more details on routing and deleting objects.

### Kubernetes Kubectl CLI & Cheatsheet

#### Dry Run Command
- The **dry run** is used to generate a template for an object (like a pod) without actually creating it.
- Command example:
  ```bash
  kubectl run <pod-name> --image=<image-name> --dry-run=client -o yaml > ngpod.yaml
  ```
  - This generates a YAML file for a pod with the specified name and image.

#### Workload Balancing Commands
- **kubectl cordon <node>**: Marks a node as unschedulable, preventing new pods from being scheduled on it.
- **kubectl drain <node>**: Removes all pods from the node and transfers them to healthy nodes.
- **kubectl uncordon <node>**: Makes the node schedulable again after maintenance.

### Taints and Tolerations
- **Taints**: Applied to nodes to prevent pods from being scheduled unless they have a matching **toleration**.
- **Toleration**: Declared for pods, allowing them to run on nodes with matching taints.

### Resource Requests and Limits
- **resources.requests**: Minimum amount of resources (CPU/memory) reserved for a container. The pod will only be created if this minimum can be allocated.
- **resources.limits**: Maximum resources a container can consume. The container cannot use more than the defined limits.

### Jobs and CronJobs

#### Jobs
- A **Job** creates one or more pods and retries their execution until a specified number of successful completions is reached.
- Deleting a job cleans up the pods it created.
- Jobs can run multiple pods in parallel or a single pod to completion.

#### CronJobs
- A **CronJob** runs jobs at scheduled times based on a cron pattern (minute, hour, day, month, day of the week).
- Difference between Job and CronJob:
  - **Job**: Runs immediately and completes.
  - **CronJob**: Runs at specific times according to the schedule.

### DaemonSets

#### Definition and Use Cases
- **DaemonSets** ensure that all nodes in a cluster run a copy of a specific pod.
- Use cases include:
  - Running cluster-wide storage, log collection, or monitoring daemons.
  
#### DaemonSet Behavior
- As nodes are added to or removed from the cluster, pods are automatically created or garbage collected.
- Deleting a DaemonSet removes the pods it created.

#### Tolerations in DaemonSets
- Example of a **DaemonSet** that runs on master nodes using tolerations:
  - Tolerations allow pods to run on nodes that are otherwise unschedulable, such as master nodes.

#### Commands Related to DaemonSets
- **kubectl get ds -A**: Lists all DaemonSets across namespaces.
- **kubectl get pod -n <namespace>**: Lists pods created by the DaemonSet in the specified namespace (e.g., `kube-system`).

#### DaemonSets and ReplicaSets
- DaemonSets create pods across nodes, ensuring one pod per node.
- Both DaemonSets and ReplicaSets ensure new pods are created if existing pods are deleted. However, DaemonSets are designed to maintain one pod per node while ReplicaSets maintain a specific number of replicas.

### Kubernetes with Lens

#### Introduction to Lens
- **Lens** is an Integrated Development Environment (IDE) designed for managing Kubernetes clusters across platforms like Mac, Windows, and Linux.
- It offers a graphical interface for deploying and managing clusters directly from the console.

#### Kubernetes Configuration in Lens
- To transfer a **Docker image** between servers without using a repository, **Docker config files** are used.
- For Kubernetes clusters, **kubeconfig files** can be utilized to transfer clusters between servers without a repository.
- Lens allows users to add the kubeconfig and visualize the cluster's usage. It also offers metrics management to monitor cluster performance.

### Networking Essentials for DevOps

#### Core Responsibilities of DevOps in Networking
1. Automating the management of cloud computing environments.
2. Establishing connections between multiple systems.

#### Networking Components Overview
- **Key Topics**:
  1. Components responsible for networking.
  2. OSI Model and its layers.
  3. Classification of networks based on geography.
  4. Networking devices (e.g., routers, switches).
  5. Home networks.
  6. IP addresses and their ranges.
  7. Protocols, DNS, and DHCP services.
  8. Key networking commands.

#### What is a Computer Network?
- A **computer network** is a communication system between two or more devices.
  - Every device on the network has an **IP address**.
  - Network-enabled devices (like IoT devices) communicate through these interfaces.
  - Examples include laptops with ethernet/wireless adapters, smartphones, and IoT devices.

#### Components of a Computer Network
1. **Devices**: Computers, smartphones, IoT devices.
2. **Connection Medium**: Cables or wireless networks linking the devices to network interfaces.
3. **Switches**: Connect multiple network interfaces.
4. **Routers**: Connect multiple networks.
5. **Operating System**: Software that analyzes data received on the network.

### OSI Model

#### Importance of OSI Model
- The **Open Systems Interconnection (OSI)** model provides a standard for communication between all network devices.
- It ensures worldwide compatibility, enabling seamless communication across devices, apps, and operating systems.

#### OSI Model Overview
- Developed by the **International Organization for Standardization (ISO)** in 1984, it is a 7-layer architecture designed for data communication.
- Devices and networks across the world follow this model to ensure standardized communication.

#### OSI Model's Layered Architecture
- **Services**: Actions a layer provides to higher layers.
- **Protocols**: Set of rules for exchanging information between layers.
- **Interfaces**: Communication pathways between layers.

#### Example of OSI Model in Action
- When posting a picture on **Instagram**, the user acts as the sender, and the Instagram server acts as the receiver. The OSI model governs the communication between the two.

### Summary
- Lens provides a convenient way to manage Kubernetes clusters with visual tools.
- Understanding the components of a network and OSI model is essential for a DevOps engineer to manage and automate cloud and network tasks effectively.

### OSI Model Overview

The **OSI Model** (Open Systems Interconnection) defines a framework for standardizing communication between computers over a network. It consists of seven layers, each responsible for specific functions. Below is a detailed breakdown of each layer.

---

### 1. Physical Layer
- **Responsibilities**: Establishes the physical connection between two devices (e.g., computer A and computer B).
- **Data Format**: Data is in bits (1s and 0s).
- **Transmission**: Manages media, signals, and binary transmission.
- **Devices**: Uses cables, hubs, and physical media like Ethernet cables, fiber optics, or wireless networks.

---

### 2. Data Link Layer
- **Function**: Ensures error-free transfer from one node’s physical layer to another's.
- **Data Format**: Data is framed for transmission.
- **Addressing**: Uses **MAC addresses** to identify devices.
- **Key Concepts**: 
  - Every device has a **Network Interface Card (NIC)**.
  - The MAC address is assigned by NIC for physical addressing.
  - Data is transferred to the **Network Layer** after framing.
- **Devices**: Switches and bridges operate at this layer.

---

### 3. Network Layer
- **Function**: Manages the transmission of data between nodes in different networks using **IP addresses**.
- **Data Format**: Data is structured into **packets**.
- **Key Concepts**:
  - Assigns IP addresses to packets and determines the best route for data.
  - Involves protocols like **IP**, **ICMP**, and **IGMP**.
  - Routers and Layer 3 switches manage routing between networks.
- **Devices**: Routers, firewalls.

---

### 4. Transport Layer
- **Function**: Responsible for end-to-end data delivery, ensuring that data is reliably sent between hosts.
- **Key Concepts**:
  - Protocols: Uses **TCP** and **UDP** for data transfer.
  - Manages port numbers for both the sender and receiver.
  - Ensures data is retransmitted in case of failure.
- **Devices**: Gateways handle transport layer operations.

---

### 5. Session Layer
- **Function**: Manages the establishment, maintenance, and termination of communication sessions.
- **Key Concepts**: Involved in security, encryption, and maintaining active connections.
- **Devices/Applications**: Authentication and login management for applications.

---

### 6. Presentation Layer
- **Function**: Ensures that data is in a usable format and may handle encryption/decryption.
- **Key Concepts**: Translates data for the application layer from network formatting.
- **Applications**: Responsible for encoding, compressing, and encrypting data for the application layer.

---

### 7. Application Layer
- **Function**: Provides network services directly to the end-user applications.
- **Key Concepts**:
  - Protocols: **DNS**, **DHCP**, **HTTP**, **HTTPS**, **FTP**, **SSH**, etc.
  - Manages interaction between user applications and lower layers.
- **Devices/Applications**: Web servers, browsers, email clients, etc.

---

### OSI Model Data Flow
- **Sender Side**: Data originates from the **Application Layer**, passes through the lower layers (Transport, Network, Data Link, and Physical) before being sent over the network.
- **Network Layer Routing**: 
  - **Routing Protocol**: Finds the shortest path (e.g., **RIP**, **OSPF**).
  - **Routed Protocol**: Uses IP addresses to send data, guided by routers.
- **Receiver Side**: The process is reversed as the data moves back up the layers, from the Physical to the Application layer.

---

### Key Devices and Protocols by Layer
1. **Physical Layer**: Uses Ethernet cables, hubs, and wireless media.
2. **Data Link Layer**: Uses MAC (ARP, RARP) addresses; devices include switches and bridges.
3. **Network Layer**: Uses IP-based protocols; devices include routers and firewalls.
4. **Transport Layer**: Uses TCP/UDP protocols; gateways manage communication.
5. **Session, Presentation, Application Layers**: Manage user-level communication through protocols like DNS, DHCP, HTTP, FTP, etc.

---

### Error Management in OSI Layers
- **Data Link Layer**: Ensures error-free transmission through **Logical Link Control** and **Media Access Control**.
- **Physical Layer**: Manages the actual transmission media (e.g., cables, wireless).

---

This model standardizes how different types of hardware and software communicate, making global data exchange possible across varied platforms.

# OSI Model Layers and Devices

### Layer 1: Physical Layer
- **Purpose:** Network access connection
- **Protocols:** Ethernet, Token Ring
- **Devices:** Hub, cables, fiber optics, wireless media
- **Note:** This layer involves actual hardware transmission mediums.

### Layer 2: Data Link Layer
- **Purpose:** Network access, ensures error-free delivery
- **Protocols:** MAC (ARP, RARP)
- **Devices:** Bridge, Layer 2 switch
- **Sub-layers:**
  - **Logical Link Control (LLC):** Ensures error-free transmission.
  - **Media Access Control (MAC):** Manages transmission access, adds source and destination MAC addresses.

### Layer 3: Network Layer
- **Purpose:** Internet access, routing, and packet delivery
- **Protocols:** IP, ICMP, IGMP
- **Devices:** Router, Firewall, Layer 3 switch
- **Function:** Determines the path for data packets using routing protocols.

### Layer 4: Transport Layer
- **Purpose:** Host-to-host communication
- **Protocols:** TCP, UDP
- **Devices:** Gateway
- **Function:** Stores port numbers of sender and receiver, responsible for application-to-application delivery.

### Layer 5: Session, Presentation, and Application Layers
- **Purpose:** Application-level data handling (combined in some models)
- **Protocols:** DNS, DHCP, NTP, SNMP, HTTPS, FTP, SSH, Telnet, HTTP, POP3
- **Devices:** Web server, Mail server, Browser, Mail client
- **Function:** Manages application-level protocols and authentication.

---

## Additional Concepts

### Network Layer Protocols
- **Routing Protocol:** Determines the shortest path for packet transfer (e.g., RIP, OSPF).
- **Routed Protocol:** Uses IP addresses to transfer data, assisted by routers.

### Data Link Layer Responsibilities
- Adds source and destination MAC addresses for packet transmission.
- Ensures the next hop delivery is error-free.

### Physical Layer Details
- Involves transmission media such as Ethernet cables, fiber optics, or wireless connections.

---

For further learning on the OSI model, refer to this [video](https://youtu.be/_FmDKQ3hlYs).

## Network Classifications Based on Geography

### Local Area Network (LAN)
- **Description:** Devices are close to each other (e.g., in a room or building).
- **Example:** Connecting computers and servers in a small area using switches.

### Wide Area Network (WAN)
- **Description:** Devices are far apart, like across cities or countries.
- **Example:** Accessing European data centers from a smartphone over the internet.

### Metropolitan Area Network (MAN)
- **Description:** Network spread across a metropolitan area, like a city's network system.
- **Example:** City-wide computer networks or metro train network systems.

### Campus Area Network (CAN)
- **Description:** Network restricted to a campus (e.g., offices, universities).
- **Example:** Office or university campus network, also called an intranet.

### Personal Area Network (PAN)
- **Description:** Small personal network with limited range.
- **Example:** Bluetooth connections or personal Wi-Fi hotspot.

---

## Network Devices

### Switch
- **Function:** Connects multiple computers within the same network.
- **Example:** A Wi-Fi router internally contains a switch to connect devices within a LAN.

### Router
- **Function:** Connects multiple networks together.
- **Example:** Connects two buildings' networks or routes data from one subnet to another.

### Modem
- **Function:** Provides internet access by connecting the router to the internet service provider.

### Firewall
- **Function:** Ensures data protection and security during internet data transmission.

---

## IP Addressing

### Subnets
- **Description:** Group of devices in the same network, connected by switches.
- **Example:** Corporate datacenters have multiple subnets, each with its own IP address.

### IPv4 Address
- **Structure:** Composed of four octets, each of 8 bits, totaling 32 bits.
- **Range:** 0.0.0.0 to 255.255.255.255 (binary representation).

### IP Address Types
- **Public IP Address:** Identifies a device on the wider internet.
- **Private IP Address:** Used within a local network, not exposed to the wider internet.

### Private IP Ranges:
- **Class A:** 10.0.0.0 - 10.255.255.255
- **Class B:** 172.16.0.0 - 172.31.255.255
- **Class C:** 192.168.0.0 - 192.168.255.255
- **Class D & E:** Reserved for research and multicasting (not commonly used).

---

## Network Flow and Communication
- Devices under the same Wi-Fi network communicate through switches, while routers connect different networks to form the global internet.
- Each device, including switches and routers, has its own unique IP address for identification and communication.

#### 1. **Protocols and Ports in Networking**
- **Protocol**: A formal specification that defines procedures for transmitting/receiving data. It includes format, timing, sequence, and error-checking mechanisms.
- **TCP (Transmission Control Protocol)**: 
  - Reliable, connection-oriented protocol.
  - Ensures error-free data transmission using a three-way handshake.
  - Used for applications requiring reliability.
  - Examples: FTP, HTTP, HTTPS.
- **UDP (User Datagram Protocol)**:
  - Unreliable, connectionless protocol.
  - Faster than TCP but without error-checking or feedback mechanisms.
  - Suitable for speed-critical applications.
  - Examples: DNS, DHCP, TFTP, ARP, RARP.

#### 2. **OSI and TCP/IP Layer Mapping**
- **TCP/IP Model Layers**:
  1. **Application Layer**: Telnet, FTP, DHCP, HTTP, SMTP, DNS, SNMP.
  2. **Transport Layer**: TCP, UDP.
  3. **Internet Layer**: ICMP, ARP, RARP, IP.
  4. **Network Interface Layer**.

#### 3. **Networking Concepts**
- **Server Communication**: To connect applications like Tomcat and MySQL, you need to know their IP addresses and port numbers. For example, Tomcat uses port 8080, MySQL uses port 3306.
- **Firewall Permissions**: Services must have appropriate firewall permissions to bypass security and run applications.

#### 4. **Networking Commands**
- **ifconfig**: Displays active network interfaces, loopback addresses (e.g., 127.0.0.1), and IP addresses.
- **ip addr show**: Similar to `ifconfig`, shows network interfaces.
- **ping [IP address]**: Sends ICMP packets to check if the target IP address is reachable and shows packet transmission statistics.
- **tracert [website/IP address]**: Traces the path to a target server, displaying the hops (routers) the data passes through.
- **netstat -antp**: Shows all open TCP ports and the associated process IDs and service names.
- **ps -ef | grep [service]**: Lists processes and allows filtering for specific services like Apache.
- **ss -tunlp**: Displays all information about TCP/UDP sockets, including processes and services.

#### 5. **Advanced Network Commands**
- **nmap**: 
  - Scans networks for hosts and services.
  - Provides detailed information about open ports, services, and vulnerabilities.
  - Example: `nmap localhost` or `nmap [IP address]`.
- **dig [domain]**: Fetches the IP address of a domain via DNS.
- **nslookup [domain]**: Provides DNS details of a domain, including IP addresses.
- **route**: Manages IP/kernel routing tables. Use `route -n` for numeric IPs and `sudo route add default gw [gateway]` to assign a gateway.
- **arp**: Manages ARP (Address Resolution Protocol) tables, mapping IP addresses to MAC addresses.
- **mtr**: Combines ping and traceroute, showing real-time packet exchange and network issues.

#### 6. **Telnet and Connectivity**
- **telnet [IP address]**: Connects to a remote machine via IP and checks if the machine and its port are accessible.

These notes summarize key concepts, protocols, and commands used in DevOps networking, focusing on essential tools and protocols for network management and troubleshooting.

# Introduction to Cloud Computing and AWS

## What is Cloud Computing?

- **Definition**: Cloud computing is the on-demand delivery of IT resources over the Internet with pay-as-you-go pricing.
- **Traditional vs. Cloud**:
  - **Traditional Setup**: Organizations maintain their own data centers with physical servers and virtualization teams. Employees request resources from the virtualization team, which uses hypervisors to create virtual machines.
  - **Cloud Computing**: Users can self-provision virtual machines and other resources via web portals or command-line interfaces, eliminating the need to contact a virtualization team.

## Benefits of Cloud Computing

1. **Agility**
   - Quickly procure resources without the overhead of building infrastructure.
   - Easily release resources when they're no longer needed.
   - Saves on maintenance and establishment costs.
2. **Global Access**
   - Access virtualized resources over the network from anywhere.
   - Manage resources through APIs or portals.
3. **Cost Efficiency**
   - Pay-as-you-go pricing model reduces upfront costs.
   - No need to invest in physical data centers and servers.

## Types of Cloud Computing Services

### 1. Infrastructure as a Service (IaaS)

- **Description**: Provides virtualized computing resources over the internet.
- **User Responsibility**: Manage the operating system and applications on the virtual machines.
- **Example**: Amazon EC2 (Elastic Compute Cloud)
  - Users are given infrastructure components like memory, RAM, and processors.
  - Ideal for users who need control over their computing environment.

### 2. Platform as a Service (PaaS)

- **Description**: Offers hardware and software tools over the internet, allowing users to develop and manage applications without dealing with the underlying infrastructure.
- **User Responsibility**: Focus on deploying and managing applications.
- **Example**: AWS RDS (Relational Database Service) for Oracle Database
  - AWS handles the provisioning and management of the database platform.
  - Users can directly use the database without managing virtual machines.

### 3. Software as a Service (SaaS)

- **Description**: Delivers software applications over the internet on a subscription basis.
- **User Responsibility**: Just use the software; no need to manage infrastructure or platforms.
- **Example**: Web-based email services, CRM applications.
  - Simply subscribe and start using the software.

## Cloud Deployment Models

- **Private Cloud**: Cloud infrastructure dedicated to a single organization.
  - Offers greater control and security.
  - Accessible over a private network.
- **Public Cloud**: Services offered over the public internet and available to anyone.
  - Examples include AWS, Microsoft Azure, and Google Cloud Platform.
  - Users sign up and subscribe to services as needed.

## Key Takeaways

- **Shift from Virtualization to Cloud Computing**: Organizations are moving from traditional virtualization setups to cloud computing for greater flexibility and efficiency.
- **Self-Service Portals and APIs**: Users can create, manage, and maintain virtual resources without relying on a dedicated team.
- **Widespread Adoption**: Approximately 90% of organizations are utilizing cloud computing services.
- **Cost and Resource Efficiency**: Cloud computing reduces the need for physical infrastructure and allows for scalable resource management.

---

By understanding the fundamentals of cloud computing and the various services offered by providers like AWS, DevOps professionals can better manage and deploy applications in modern, scalable environments.

# Introduction to AWS

## Setting Up AWS Free Tier Account

- **Requirements**: 
  - Set up an AWS Free Tier account.
  - Create billing alarms to monitor costs.
  - Configure IAM (Identity and Access Management) users for access control.

## AWS Global Infrastructure

- **AWS Overview**: AWS is the largest public cloud provider, operating across many countries.
- **Regions and Availability Zones (AZs)**:
  - AWS is divided into **regions** (physical locations worldwide).
  - Each region consists of **multiple availability zones** (AZs), which are clusters of data centers.
  - A region can have a minimum of **two AZs** and a maximum of six. Each AZ contains multiple data centers.
  - **As of 2020**, AWS has 77 availability zones across 24 geographic regions.

## Types of AWS Data Centers

- **Availability Zones**: 
  - Each zone is a set of clustered data centers with independent power, cooling, and security systems.
  - AZs are interconnected via low-latency, high-bandwidth networks, ensuring fault tolerance and high availability.
  - Applications spread across multiple AZs are protected from local disasters like power outages or natural disasters.
- **Local Zones**: 
  - These bring AWS services like compute, storage, and databases closer to large populations, reducing latency.
  - Ideal for use cases like media content creation, gaming, and machine learning that require low-latency access.
  
## High Availability and Disaster Recovery

- **High Availability**: 
  - Deploy resources in multiple AZs to ensure continuous operation, even if one AZ goes down.
- **Global Reach**: 
  - Use multiple regions to reduce latency and provide global disaster recovery. If one region fails, data can be backed up or redirected to another region.

## AWS Services

- **Core Services**: 
  - Compute: **Amazon EC2**, **Lambda**, **Elastic Load Balancing**
  - Storage: **Amazon S3**, **Elastic Block Store (EBS)**
  - Databases: **RDS**, **DynamoDB**
  - Networking: **VPC**, **CloudFront**, **Route 53**
  - Developer Tools: **CodeCommit**, **CodeBuild**, **CodeDeploy**
  - Monitoring: **CloudWatch**
  - Security: **IAM**, **Key Management Service (KMS)**, **CloudTrail**

- **Services in Region Launches**:
  - AWS launches core services like **EC2**, **S3**, **RDS**, and **CloudWatch** in every new region.
  - Some services, like **Athena**, **CloudFront**, **EKS**, and **GuardDuty**, are introduced within 12 months of a new region launch.

## AWS Edge Computing Services

- **AWS Wavelength**: 
  - Brings AWS services to the edge of the 5G network, minimizing latency for mobile devices.
  - Useful for applications requiring low-latency performance like gaming, video streaming, AR/VR, and machine learning at the edge.
- **AWS Outposts**: 
  - Extends AWS services to on-premises locations, providing consistent hybrid cloud experience for workloads requiring low latency or local data processing.

## AWS Management Console

- **Console Overview**: 
  - Central hub to access all AWS services.
  - Popular services for DevOps include **EC2** for virtual machines, **S3** for storage, **RDS** for databases, and **VPC** for networking.
  - Tools for monitoring and automation include **CloudWatch**, **CloudTrail**, and **CodePipeline**.
  - DevOps can leverage services such as **Elastic Beanstalk** for app deployment and **ECS** for containerized applications.

## AWS Free Tier Limitations

- **Region Restrictions**: 
  - The AWS Free Tier allows access to certain regions (primarily US-based).
  - To use services in other regions or access additional availability zones, payment is required.

---

By understanding the global infrastructure and key services offered by AWS, DevOps engineers can effectively design and manage cloud environments for high availability, scalability, and fault tolerance.

### AWS EC2 Overview

**Elastic Compute Cloud (EC2)**:  
EC2 is a widely used AWS service that provides virtual machines and related services. It enables users to manage and provision virtual machines in Amazon's cloud infrastructure. EC2 offers scalability, allowing users to adjust resources like CPU, RAM, and storage as needed. 

- **Billing**: Users only pay for what they use, either by the hour or by seconds, and can reserve capacity for long-term usage (1-3 years) to get discounts.
- **Integration**: EC2 integrates seamlessly with other AWS services like S3, EFS, RDS, DynamoDB, and Lambda.

### EC2 Instance Types and Pricing

1. **On-Demand Instances**: Pay on an hourly or seconds basis for virtual machines.
2. **Reserved Instances**: Discounts are available for committing to 1 to 3 years of usage.
3. **Spot Instances**: Purchase unused instances at a lower price. These instances are terminated when the original owner needs them back.
4. **Dedicated Servers**: Expensive, dedicated physical servers for exclusive use.

### Key EC2 Components

1. **Amazon Machine Image (AMI)**:  
   - Ready-made virtual machines, similar to Docker images.
   - Acts as a base image for launching EC2 instances.

2. **Instance Types**:  
   - Determines CPU, RAM, network, and storage specifications for the instance.

3. **Elastic Block Store (EBS)**:  
   - A virtual hard disk used for storing operating systems and data.
   - Default storage is 8GB for Linux and 30GB for Windows, but this can be scaled as needed.

4. **Tags**:  
   - Key-value pairs that help manage, search, and filter resources.
   - Useful for tracking EC2 instances based on projects, environments, or ownership.

5. **Security Groups**:  
   - Virtual firewalls that control inbound and outbound traffic to the instances.

### Accessing EC2 Instances

- **Key Pairs**:  
   - To log in, SSH keys (public and private) are used. 
   - It’s essential to create and download the key pair when creating an EC2 instance.
   - The same key can be used to access Windows instances, though the key is used to generate a password.

### Steps to Create an EC2 Instance

1. **Choose AMI**: Select the base Amazon Machine Image.
2. **Choose Instance Type**: Define the size and computing resources.
3. **Configure Instance**: Set up network configurations, permissions, and scripts.
4. **Add Storage**: Assign storage volumes using EBS.
5. **Tag Instance**: Apply key-value pairs for easy identification and billing.
6. **Configure Security Group**: Create a security group for managing traffic.
7. **Review & Create Instance**: Finalize settings and launch the instance.

By following these steps, users can easily create and manage EC2 instances with the required configurations and security.

### Bash Scripting Overview

Bash scripting is a powerful way to automate tasks on Unix-based systems. However, errors in scripts are often due to typographical mistakes, such as missing or extra spaces. When errors occur, it is essential to carefully review the script.

### Basic Shell Commands in Bash

- **System Uptime**: `uptime`  
- **Memory Utilization**: `free -m`  
- **Disk Utilization**: `df -h`

### Redirecting Output

To suppress the output from a command, redirect it to `/dev/null`:

```bash
command > /dev/null
```

### Variable Declaration in Bash

- Declare a variable using `=` without spaces:

```bash
SKILL="DevOps"
echo $SKILL
```

- Example for package installation:

```bash
PACKAGE="httpd wget unzip"
yum install $PACKAGE -y
```

### File Operations

- **Rename a script**:  
  ```bash
  mv old_script.sh new_script.sh
  ```
- **Copy a script**:  
  ```bash
  cp script.sh copy_script.sh
  ```

### Command Line Arguments

In Bash scripts, you can access command-line arguments as follows:

```bash
./script.sh arg1 arg2 arg3
```

- `$0`: The script name.
- `$1`, `$2`, etc.: Arguments passed to the script.
- `$#`: The number of arguments.
- `$@`: All the arguments.
- `$?`: The exit status of the last process.
- `$$`: The process ID of the script.

### System Variables

Some useful system variables include:

- `$USER`: Current username.
- `$HOSTNAME`: Hostname of the system.
- `$SECONDS`: Time since the script started.
- `$RANDOM`: Generates a random number.
- `$LINENO`: Current line number in the script.

### Quotes in Bash

- **Single Quotes**: Treat everything literally. Variables inside single quotes are not expanded.

  ```bash
  SKILL='DevOps'
  echo 'I have $SKILL skill'  # Output: I have $SKILL skill
  ```

- **Double Quotes**: Variables and special characters inside double quotes are expanded.

  ```bash
  SKILL="DevOps"
  echo "I have $SKILL skill"  # Output: I have DevOps skill
  ```

- To print special characters like `$`, use a backslash `\` to escape them:

  ```bash
  echo "Cost is \$9 million"  # Output: Cost is $9 million
  ```

By understanding these basic concepts, you can effectively write and debug Bash scripts for various DevOps tasks.

### Bash Scripting: Key Concepts

#### 1. Command Substitution
Command substitution allows you to store the output of a command in a variable. This is done using backticks (\`) or the more modern `$()`.

- **Example**:
  ```bash
  UP=$(uptime)
  echo $UP
  ```

- **Alternative**:
  ```bash
  CURRENT_USER=$(who)
  echo $CURRENT_USER
  ```

#### 2. Example with Command Chaining
Using `free`, `grep`, and `awk` to capture specific data from memory usage:
  
- **Command**:
  ```bash
  free -m | grep Mem | awk '{print $4}'
  ```

- **Storing Output in a Variable**:
  ```bash
  FREE_RAM=$(free -m | grep Mem | awk '{print $4}')
  echo $FREE_RAM
  ```

#### 3. Exporting Variables
To make variables available to child processes (shells, scripts), use `export`.

- **Export Example**:
  ```bash
  export VARNAME="VARVALUE"
  ```

Variables can also be made globally persistent by adding them to `.bashrc` or `/etc/profile`.

- **Edit `.bashrc` for user-specific variables**:
  Variables declared here will persist across user sessions.
  
- **Edit `/etc/profile` for system-wide variables**:
  This makes variables available to all users.

#### 4. User Input in Scripts
In bash scripting, user inputs can be taken using the `read` command.

- **Basic User Input**:
  ```bash
  read SKILL
  echo "Your $SKILL skill is in high demand."
  ```

- **Prompting for Input with `-p`**:
  ```bash
  read -p 'Username: ' USR
  ```

- **Password Input (Hidden Characters) with `-sp`**:
  ```bash
  read -sp 'Password: ' pass
  echo "Login Successful"
  ```

#### 5. Avoiding User Interaction in DevOps
In DevOps, avoid manual user interaction in scripts to reduce errors. Automate processes to ensure that applications run seamlessly in the background without needing input during execution.

### Conclusion
These key concepts in bash scripting, from command substitution to exporting variables and handling user input, are essential for automating and managing processes efficiently, especially in DevOps environments.

### Bash Scripting: Decision Making and Loops

#### 1. Decision Making in Bash

##### Conditional Statements (If-Else)
Conditional statements allow decision making in scripts based on user input or system states. Here's a basic example of an `if-else` condition:

- **Example**:
  ```bash
  #!/bin/bash
  read -p "Enter a number: " NUM
  if [ $NUM -gt 100 ]
  then
    echo "We have entered in IF block."
    sleep 3
    echo "Your number is greater than 100."
    date
  else
    echo "You have entered a number less than 100."
  fi
  echo "Script execution completed successfully."
  ```

##### If-Else-If Statements
The `if-else-if` construct checks multiple conditions sequentially.

- **Example**:
  ```bash
  #!/bin/bash
  value=$(ip addr show | grep -v LOOPBACK | grep -ic mtu)
  if [ $value -eq 1 ]
  then
    echo "1 Active Network Interface found."
  elif [ $value -gt 1 ]
  then
    echo "Found Multiple active Interfaces."
  else
    echo "No Active interface found."
  fi
  ```

##### Grep Commands Used in Scripts
- **Command Examples**:
  - `grep -v <Text>`: Excludes lines containing `<Text>`.
  - `grep -ic <Word>`: Counts occurrences of `<Word>`, case-insensitive.

#### 2. Loops in Bash

##### For Loop
The `for` loop iterates over a set of values and performs actions in each iteration.

- **Simple For Loop**:
  ```bash
  #!/bin/bash
  for VARI in java .net python ruby php
  do
    echo "Looping..."
    sleep 1
    echo "########################################"
    echo "Value of VARI is $VARI."
    echo "########################################"
    date
  done
  ```

- **For Loop to Add Users**:
  ```bash
  #!/bin/bash
  MYUSERS="alpha beta gamma"
  for usr in $MYUSERS
  do
    echo "Adding user $usr."
    useradd $usr
    id $usr
    echo "###################################"
  done
  ```

- **For Loop with C-Style Syntax**:
  ```bash
  #!/bin/bash
  for (( c=1; c<=5; c++ ))
  do
    echo "Welcome $c times"
  done
  ```

- **Infinite For Loop**:
  ```bash
  #!/bin/bash
  for (( ;; ))
  do
    echo "Infinite Loop"
  done
  ```

##### While Loop
The `while` loop executes as long as the condition remains true.

- **Basic While Loop**:
  ```bash
  #!/bin/bash
  counter=0
  while [ $counter -lt 5 ]
  do
    echo "Looping..."
    echo "Value of counter is $counter."
    counter=$(( counter + 1 ))
    sleep 1
  done
  echo "Out of the loop"
  ```

- **Infinite While Loop**:
  ```bash
  #!/bin/bash
  counter=2
  while true
  do
    echo "Looping..."
    echo "Value of counter is $counter."
    counter=$(( counter * 2 ))
    sleep 1
  done
  ```

### Conclusion
This guide covers conditional statements (`if-else`, `if-else-if`) and loop structures (`for` and `while`) in Bash scripting. These concepts are essential for automating tasks, making decisions, and performing repeated actions in scripts.

### Bash Scripting: SSH Key Exchange and Redirection

#### 1. SSH Key Exchange

SSH key exchange is used to log in to a remote server without needing to enter a password every time. This process allows for more secure and efficient server management.

##### Steps for SSH Key Exchange:
1. **Generate SSH Keys**:
   - Run `ssh-keygen` to create two files:
     - `id_rsa` (private key)
     - `id_rsa.pub` (public key)
   - Leave the directory and passphrase unchanged (default settings).
   
2. **Copy Public Key to Remote Server**:
   - Copy `id_rsa.pub` to the remote server using the following command:
     ```bash
     ssh-copy-id remote-username@remote-servername
     ```

3. **Passwordless Login**:
   - After copying the public key, you can log in to the remote server without a password using:
     ```bash
     ssh remote-username@remote-servername
     ```

4. **Using SSH Key for Remote Access**:
   - For more specific cases, you may need to specify the private key file:
     ```bash
     ssh -i ~/.ssh/id_rsa remote-username@remote-servername
     ```

##### Key Mechanism:
- **id_rsa**: The private key used on the local machine.
- **id_rsa.pub**: The public key stored on the remote machine.
- SSH login happens when the private key matches the public key, allowing passwordless access.

#### 2. Redirecting Output to `/dev/null`

The `/dev/null` device discards any data sent to it, which is useful for suppressing command outputs.

##### Redirect Standard Output:
```bash
command > /dev/null
```
This redirects the command's standard output to the null device, effectively discarding it.

##### Redirect Standard Output and Error:
```bash
command &> /dev/null
```
This redirects both the standard output and the standard error to the null device.

- In shells that don’t support `&>`, use the following:
  ```bash
  command > /dev/null 2>&1
  ```
  Here, `2>&1` redirects standard error (2) to standard output (1), which is then sent to `/dev/null`.

#### 3. Using SCP for File Transfer

The `scp` command is used to securely copy files between local and remote systems over SSH.

##### SCP Syntax:
- Basic usage:
  ```bash
  scp <filename> remote-username@remote-server:/path-to-directory
  ```
- With SSH key authentication:
  ```bash
  scp -i ~/.ssh/id_rsa <filename> remote-username@remote-server:/path-to-directory
  ```

When SSH key exchange is in place, no password is required during the file transfer.

#### 4. Automating Script Execution on Multiple Servers

A simple script can automate copying and executing scripts on multiple remote servers using `scp` and `ssh`.

##### Example Script:
```bash
#!/bin/bash
USR='devops'
for host in $(cat remhosts)
do
  echo "###############################################################"
  echo "Connecting to $host"
  echo "Pushing Script to $host"
  scp multios_websetup.sh $USR@$host:/tmp/
  echo "Executing Script on $host"
  ssh $USR@$host sudo /tmp/multios_websetup.sh
  ssh $USR@$host sudo rm -rf /tmp/multios_websetup.sh
  echo "###############################################################"
done
```
- `scp` copies the script to the remote server.
- `ssh` logs in and executes the script.
- After execution, the script is removed from the remote server.

### Conclusion
This section of the notes covers essential techniques in Bash scripting, including SSH key exchange for passwordless server access, redirecting command output to `/dev/null` to suppress output, securely copying files with `scp`, and automating tasks across multiple servers.

### GIT: Introduction to Version Control

#### 1. What is Git?
- Git is a version control system (VCS) and a crucial tool for developers.
- As a DevOps engineer, understanding Git is essential for collaborating with developers, managing code changes, and deploying applications.
- Git helps in writing scripts, configuration files, and automation code.

#### 2. Importance of Version Control
- DevOps engineers often modify multiple files simultaneously, making it easy to lose track of changes. 
- Version control solutions provide:
  - **Local backups** of files.
  - An updated version in a Git repository for easy rollback if necessary.
- The primary purpose of Git is to manage and maintain code efficiently.

#### 3. Features of Version Control Systems (VCS)
- VCS tracks the history of files, allowing for:
  - Easy access to previous versions.
  - Simplified collaboration among team members.
  - Efficient merging of changes.
- It enables users to keep track of modifications and collaborate effectively.

#### 4. Types of Version Control Systems
- **Localized Version Control Systems**:
  - Keep local copies of files.
  - Useful for rollback and local backups.
  
- **Centralized Version Control Systems**:
  - Utilize a central server that holds the master repository.
  - Developers have local copies and can make changes before pushing to the central repository.
  - Always recommended to pull changes before pushing to ensure the repository is up-to-date.

#### 5. Centralized vs. Localized VCS
- **Centralized VCS**:
  - A single central repository serves as the official code source.
  - Developers check out files, make changes, and check them back in.
  - Changes are synchronized with the central repository, maintaining a single source of truth.
  
- **Localized VCS**:
  - Developers work with their own copies of the repository.
  - Changes are made and committed locally before being shared with others through push/pull operations.
  - This model supports parallel development on different features, enhancing collaboration.

#### 6. Limitations of VCS
- Both systems have drawbacks:
  - In centralized systems, issues with the server can halt development.
  - In localized systems, problems with individual machines can disrupt work.
- It's important to manage these risks to maintain workflow efficiency.

#### Conclusion
Git is an essential tool for version control in the DevOps landscape, allowing for effective collaboration, code management, and deployment. Understanding both centralized and localized systems, along with their advantages and limitations, is crucial for any DevOps professional.

### GIT: Understanding Version Control Systems

#### 1. Centralized vs. Localized Version Control
- **Conflict Resolution**:
  - In centralized VCS, conflicts occur when multiple users attempt to check in changes to the same file simultaneously. Developers must resolve these conflicts before proceeding.
  - In localized VCS, conflicts can happen anytime two developers modify the same file. Developers can choose to resolve conflicts immediately or defer resolution.

- **Structure**:
  - **Centralized VCS**: A single central repository exists where all users edit and check in changes. Users maintain copies of files they wish to edit.
  - **Localized VCS**: Users have a local copy of the repository, allowing them to make and test changes before pushing to the central repository. Users must pull changes from the central repo to stay updated.

#### 2. Distributed Version Control Systems (DVCS)
- **Multiple Repositories**:
  - DVCS includes a master repository and multiple child or feature branches. This structure allows for flexibility in developing and integrating code.
  - Developers can work on multiple features simultaneously without affecting the main repository.

- **Resilience to Server Issues**:
  - In DVCS, each developer has a complete copy of the repository. Server issues do not disrupt development; changes can be made locally and synced with the central repository when available.

#### 3. Git Overview
- Git is a distributed version control system enabling developers to track code changes and collaborate on projects.
- Each developer maintains a local copy of the entire repository, allowing for local commits before sharing changes.
  
#### 4. Git vs. GitHub
- **Git**: A version control system for tracking changes in code.
- **GitHub**: A web-based platform that hosts Git repositories, providing tools for collaboration like issue tracking, pull requests, and project management.

### Summary
Understanding the differences between centralized, localized, and distributed version control systems is crucial for effective code management in a collaborative environment. Git serves as a powerful tool for tracking changes, while GitHub enhances collaboration and project management capabilities.

# Git Versioning Notes

## Introduction
Git is a version control system that tracks changes in files. It does not track empty directories. The key purpose of Git is to manage versions of files, particularly in collaborative environments. Below are important Git commands and concepts.

---

## Basic Git Commands

### Check Git Installation
- **Command:** `git`
- Checks if Git is installed on the system.

### Check Git Version
- **Command:** `git --version`
- Displays the installed version of Git.

### Initialize a Git Repository
- **Command:** `git init`
- Initializes the current directory as a Git repository by adding a `.git` folder. This folder tracks changes and stores the history of the project.

---

## Git Status & File Tracking

### Git Status
- **Command:** `git status`
- Shows the current state of the working directory and staging area. It lists untracked files, staged files, and informs whether the branch is behind in versions.

### Staging Files
- To stage files before committing, use:
  - **Command:** `git add directory_name/` – stages all files in a directory.
  - **Command:** `git add <file_name>` – stages specific files.

### Committing Files
- **Command:** `git commit -m "commit message"`
- Commits the staged files to the local repository with a descriptive message.

---

## Global Configuration

### Configure Global Username and Email
- Set these details once to help identify the user who makes changes:
  - **Command:** `git config --global user.email "email@example.com"`
  - **Command:** `git config --global user.name "username"`

---

## Working with Remote Repositories

### Cloning a Remote Repository
- **Command:** `git clone <URL>`
- Clones a remote repository (from GitHub, Bitbucket, etc.) to the local machine.

### Adding Remote Repository to Local
- **Commands:**
  1. `git remote add origin <https-link-of-git-repository>`
  2. `git push` – Pushes local changes to the remote repository.
  3. `git pull` – Pulls the latest changes from the remote repository.

### Pushing to a Remote Repository
- **Command:** `git push origin <branch-name>`
- Pushes committed changes from the local branch to the specified branch in the remote repository.

### Pulling from a Remote Repository
- **Command:** `git pull`
- Fetches and integrates changes from the remote repository to the local repository.

---

## Viewing Commit History

### Viewing Git Logs
- **Command:** `git log`
- Displays the commit history with details of each commit.

### Shortened Commit Logs
- **Command:** `git log --oneline`
- Shows a more concise view of the commit history with shortened commit IDs.

### Viewing Changes in Specific Commits
- **Command:** `git show <commit-id>`
- Displays changes made in a specific commit using its ID.

---

## Summary
Git is essential for managing file versions in both local and remote repositories. Understanding how to stage, commit, push, and pull changes ensures smooth collaboration in a team environment. Git’s flexibility in tracking changes makes it an invaluable tool for developers.

# Git Branches, Rollback, and SSH Login Notes

## Branching in Git

### Sub-repositories (Branches)
In Git, branches allow you to create sub-repositories that facilitate parallel development. For instance, feature branches can be created from the main branch, and after changes, they can be merged back into the main branch. This method is useful for team collaboration, especially in Agile development, where work is done in small increments.

### Creating a Branch
1. **Switch to the main branch:**
   - **Command:** `git checkout main`
2. **Create a new branch:**
   - **Command:** `git branch <branch-name>`
3. **List all branches:**
   - **Command:** `git branch -a` – Displays all local and remote branches.
4. **Switch to a branch:**
   - **Command:** `git checkout <branch-name>`

### Moving and Removing Files
- **Remove a file:**  
  - **Command:** `git rm <file-names>` – Removes a file from the repository and stages the change.
- **Move/rename a file:**  
  - **Command:** `git mv <src-filename> <dst-filename>` – Moves or renames a file and stages the change.

### Pushing Changes to a Branch
To push changes to a specific branch:
- **Command:** `git push origin <branch-name>`

### Switching Between Branches
- **Command:** `git checkout <branch-name>` or  
- **Command:** `git switch <branch-name>` – Switches between branches.

### Merging Branches
To merge one branch into another:
1. Switch to the target branch:  
   **Command:** `git checkout <target-branch>`
2. Merge the source branch:  
   **Command:** `git merge <source-branch>`

---

## Rollback and File Management

### Rolling Back Changes
Git allows you to rollback changes in two scenarios:
1. **Before staging:**  
   - To rollback a modified file to the previous committed state:  
     **Command:** `git checkout <filename>`

2. **After staging:**  
   - To un-stage a file that has been staged:  
     **Command:** `git restore --staged <filename>`

### Checking Differences
- **Show unstaged differences:**  
  **Command:** `git diff` – Displays file changes that haven't been committed.
- **Show staged differences:**  
  **Command:** `git diff --cached` – Shows the difference between the staged files and the last commit.

### Comparing Commits
- **Compare two commits:**  
  **Command:** `git diff <prev-commit-id> <curr-commit-id>`

### Reverting Commits
- **Revert the most recent commit:**  
  **Command:** `git revert HEAD`
- **Revert a specific commit:**  
  **Command:** `git revert <commit-id>` – Creates a new commit that undoes the changes.

### Hard Reset
To permanently remove a commit from history (caution: cannot be undone):
- **Command:** `git reset --hard <commit-id>`

---

## SSH Login for Git

### Why Use SSH?
SSH keys provide a secure way to interact with Git repositories without exposing your username or password, especially useful when working with private repositories.

### Setting Up SSH Keys
1. **Generate SSH keys:**  
   **Command:** `ssh-keygen.exe`  
   This generates a pair of keys: `id_rsa` (private) and `id_rsa.pub` (public).
   
2. **Add the public key to GitHub:**
   - Go to: `Settings > SSH and GPG keys > New SSH key`
   - Paste the contents of `id_rsa.pub` and give it a title.

### Using SSH for Git
1. After adding your SSH key to GitHub, you can clone repositories without a password:
   - **Command:** `git clone <ssh-link>`
   
2. **Fingerprint verification:**  
   When cloning for the first time, Git may ask for fingerprint verification. Confirm by entering "yes."

### Configuring SSH Keys
- View Git SSH config:  
  **Command:** `cat .git/config`
  
- Remove SSH keys (if needed):  
  **Command:** `rm -rf .ssh/*`

---

## Summary
Branches in Git enable collaborative development, and rollback options help manage mistakes efficiently. Using SSH keys offers secure, password-free access to repositories, making it a preferred method for managing Git in a team setting.

# Continuous Integration with Jenkins

## Introduction to Continuous Integration (CI)
### What is Continuous Integration?
- Continuous Integration (CI) refers to the practice where developers frequently push code to a centralized version control system after local testing.
- Multiple developers work in parallel, pushing code multiple times a day. Despite testing locally, issues and conflicts can arise when the code is merged.
  
### Why Continuous Integration?
- **Code Conflicts:** When code is pushed but not properly integrated, it can lead to bugs and issues, even after local testing.
- CI integrates multiple commits from different developers and ensures the application works correctly at both the module and application levels.
- CI helps identify compatibility issues between new code and the existing application, allowing developers to focus on fixing integration problems early.

### Automation of Continuous Integration
- Since CI checks code workability for every commit, the process should be automated. This automation is where tools like Jenkins come into play.
- Jenkins automates the process by fetching the code, building, evaluating, and notifying developers of any issues.

### Jenkins as a Continuous Integration Tool
- **Open-source CI Tool:** Jenkins started as a CI tool but now offers much more functionality.
- It supports plugins for version control systems (e.g., Git), build tools (Java, Node.js, etc.), cloud integration, testing, and other DevOps tools.
- Jenkins can be used as a Continuous Delivery tool, for running scripts, cloud automation, or integrating with other development and testing tools.

---

## Installing Jenkins

### Pre-requisites for Jenkins Installation
- **Java:** Jenkins requires Java (JDK or JRE). The recommended version is JDK 11.
- **Additional Tools:** Tools like Maven are often required to run builds.

### Installing Jenkins on Linux
To install Jenkins on a Linux system, follow these steps:
```bash
#!/bin/bash
sudo apt update
sudo apt install openjdk-11-jdk -y
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo tee \
/usr/share/keyrings/jenkins-keyring.asc > /dev/null
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
/etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update
sudo apt-get install jenkins -y
```
This script updates the system, installs JDK 11, adds the Jenkins repository, and installs Jenkins.

### Jenkins Installation on Other Platforms
- Jenkins can also be set up on **Docker**, **Kubernetes**, and other platforms.
- For Fedora OS, Jenkins requires minimal resources (1 GB RAM and disk space), making it lightweight compared to other systems.

---

## Jenkins Installation on Cloud (AWS Example)
### Setting Up Jenkins on AWS
1. **Launch an EC2 Instance:**
   - Choose **Ubuntu Server 20.04 LTS** as the AMI (Amazon Machine Image).
   - Instance type: **t2.micro** (sufficient for basic use but may be slow), or **t2.small** for better performance.

2. **Create a Key Pair:**
   - Name the key pair and choose **RSA** as the key pair type for passwordless login.
   - The private key file should be saved in **.pem** format for OpenSSH or **.ppk** format for PuTTY.

3. **Connect to the Cloud Server:**
   - Use SSH to connect to the instance using the private key file.

By following these steps, you can easily set up Jenkins on a cloud server and begin automating CI tasks.

---

### Summary
Jenkins is a powerful tool for automating Continuous Integration. It integrates multiple developers’ code, ensuring that all commits are continuously checked for compatibility. With its vast plugin ecosystem and ease of installation on various platforms (Linux, Docker, Cloud), Jenkins is a popular choice for DevOps automation.

# Jenkins Installation for Continuous Integration

## Setting Up Security Group for the Jenkins Instance
To configure the security settings of your AWS instance, you need to create a **Security Group** to control inbound and outbound traffic. Here's how you do it:

### Inbound Security Group Rules:
1. **SSH Rule:**
   - **Type:** SSH
   - **Protocol:** TCP
   - **Port Range:** 22
   - **Source Type:** My IP
   - This allows passwordless SSH login to the instance from your specific IP address.

2. **Custom TCP Rule:**
   - **Type:** Custom TCP
   - **Protocol:** TCP
   - **Port Range:** 8080
   - **Source Type:** My IP
   - This allows you to connect to Jenkins through a web browser using port 8080.

### Storage Configuration:
You can configure the storage of your instance in the **Configure Storage** section during instance setup.

### Advanced Details:
In the **User Data** section, you can include initialization scripts to install necessary software upon instance creation, using tools like `yum` or `apt-get`.

---

## Launching the Jenkins Instance

### Steps After Launching:
1. After launching the instance, you will get details like security, storage, networking, and the public IPv4 address.
2. **Accessing Jenkins GUI:** Use the public IPv4 address of the instance followed by port 8080 to access Jenkins through the browser.

---

## Unlocking Jenkins
1. After accessing Jenkins through the browser, you will be prompted to unlock Jenkins. 
   - The initial admin password is located at:
     ```
     /var/lib/jenkins/secrets/initialAdminPassword
     ```
2. **Login via SSH:**
   - SSH into the instance to retrieve the password using the command:
     ```
     ssh -i Downloads/jenkins-key.pem ubuntu@<instance-IP>
     ```
3. **View User Data Logs:**  
   If the instance has any missing software (not installed via the shell script), you can check the script by running:
   ```
   curl http://169.254.169.254/latest/user-data
   ```

### Jenkins Service Status:
- You can check the status of Jenkins using:
  ```
  systemctl status jenkins
  ```
- If Jenkins is not running, there may be an issue with the user-data or bash script.

---

## Jenkins Configuration and Plugins

### Jenkins Files:
- All Jenkins files are stored under `/var/lib/jenkins/`. The initial admin password is located at:
  ```
  /var/lib/jenkins/secrets/initialAdminPassword
  ```

### Plugins:
Jenkins' power comes from its extensive collection of plugins. Some key categories include:
1. **Organization and Administration**
2. **Build Features**
3. **Build Tools**
4. **Build Analysis and Reporting**
5. **Pipelines and Continuous Delivery**
6. **Source Code Management**
7. **Distributed Builds**
8. **User Management and Security**
9. **Notifications and Publishing**
10. **Languages**

---

## Accessing Jenkins After Reboot
- The public IP address of the instance is dynamic and will change after every reboot. However, the Jenkins URL remains tied to the initial IP address when the Jenkins setup was first created.
- Save the Jenkins URL for continued access as the admin, even if the instance IP changes.

---

### Conclusion
After completing these steps and configurations, you'll be greeted with the **"Welcome to Jenkins"** screen, and Jenkins will be ready for use in automating your Continuous Integration tasks.

# Continuous Integration with Jenkins: Freestyle vs Pipeline as Code

## Jobs in Jenkins
In Jenkins, a **Job** refers to the workload or task that Jenkins runs. There are two primary types of jobs:
1. **Freestyle Jobs**
2. **Pipeline as a Code**

### Freestyle Jobs
- Freestyle jobs are graphical and simple, created through Jenkins’ **GUI**.
- You create a job by filling out a form, specifying the job's details, running it, and viewing the output.
- **Best for small projects**: Freestyle jobs are easy to set up but are mainly used for learning or simple use cases.
- **Downside**: 
  - Manually linking multiple jobs to form a pipeline can be cumbersome.
  - Replicating an existing pipeline across projects requires repeating the manual process, which is inefficient.

### Pipeline as a Code
- With **Pipeline as a Code**, the entire job (pipeline) is scripted using **Groovy**.
- Instead of using the GUI, the pipeline code is stored as text, which allows it to be **version-controlled**.
- **Advantages**:
  - It’s scalable and easy to replicate across multiple projects.
  - The pipeline becomes part of the project’s codebase, supporting **DevOps principles** like Infrastructure as Code (IaC).
- **Recommended for real-world applications**: Modern DevOps practices emphasize using code for everything, including pipelines.

---

# Installing Tools in Jenkins

## Setting Up JDK and Maven in Jenkins
For Jenkins to execute tasks, it requires **JDK** and **Maven** to be properly configured.

### Steps to Configure JDK:
1. On the Jenkins Dashboard, navigate to:
   ```
   Jenkins Dashboard > Manage Jenkins > Global Tool Configuration
   ```
2. Add the required JDK by specifying:
   - **JDK Name**: (e.g., JDK 1.8)
   - **Path** to the JDK inside the server's `JAVA_HOME`, not your local machine.

3. To find the correct JDK path inside the Jenkins instance:
   - SSH into your Jenkins server:
     ```
     ssh -i Downloads/jenkins.pem ubuntu@<instance-IP>
     ```
   - Update the server and install JDK:
     ```
     sudo apt update
     sudo apt install openjdk-8-jdk -y
     ```
   - By default, JDK is installed at `/usr/lib/jvm/java-1.8.0-openjdk-amd64`. Copy this path and paste it in **JAVA_HOME**.

### Setting Up Maven:
- Similar to JDK, Maven also needs to be configured in the **Global Tool Configuration**.
- Provide the Maven **name** and **version**.
- These tools will be triggered and installed during the Jenkins build process.

By ensuring that both JDK and Maven are set up, Jenkins can successfully compile and build projects as part of your Continuous Integration pipeline.

# Continuous Integration with Jenkins: First Job

## Creating a Job in Jenkins
To create a job in Jenkins:
1. Go to the Jenkins Dashboard and click on **"Create a Job"**.
2. Name your build and select the type of job, such as **Freestyle mode**.
3. You will be directed to the configuration page where the following sections are available:
   - **General**: General configuration for the job.
   - **Source Code Management**: To pull code from a repository.
   - **Build Triggers**: Conditions that trigger the build.
   - **Build Environment**: Define the environment in which the build will run.
   - **Build Steps**: Commands or scripts to execute during the build.
   - **Post-build Actions**: Actions to take after the build is completed.

## Configuring Source Code Management
In the **Source Code Management** section:
- Select **Git** as the source control tool.
- Enter the **Repository URL** where the code is hosted.
- For public repositories, no credentials are required. For private repositories, credentials need to be provided.
- Specify the **branch** to be used from the repository.

## Build Steps
- The **Build Step** is the entry point for a Jenkins job, and this is where you specify the tasks to be executed.
- You can write the script for the build step using the command-line options such as **Windows terminal**, **Linux terminal**, or others.
- Plugins, such as **Maven plugin**, can also be installed to run Maven commands directly, instead of writing them as general commands. This provides more customization for executing tasks.

## Execution Process
- The typical process for a Jenkins job involves pulling the source code, running the build steps, and then producing an output.
- The **Jenkins Dashboard** offers options such as:
  - **Status**: View the status of the job.
  - **Changes**: Track any changes made.
  - **Workspace**: Store data and dependencies related to the job.
  - **Build Now**: Trigger a new build for the job.
  - **Configure**: Adjust job configurations.
  - **Delete Project**: Remove the project.

## Workspace and Build Data
- **Workspace**: This is where Jenkins stores the directory structure and data related to the repository. Any files downloaded or created during the build process are stored in the workspace.
- **Multiple Builds**: Jenkins allows you to build a job multiple times, and each time the data and build files are stored in the workspace.

## Archiving and Decluttering the Workspace
- To declutter the workspace, you can **archive** specific files after the build is complete.
- You can define patterns for the files to be archived, allowing Jenkins to store only the necessary files and clean up the workspace afterward.

# Continuous Integration with Jenkins: Plugins, Versioning & More

## Workspace and Versioning
- **Workspace**: Jenkins uses the workspace to store all files downloaded during the build process. It acts like a local environment where files and binaries are kept. The workspace is located under:
  - **Dashboard > Build > Workspace**
- **Binary Updates**: For each build, binaries get updated, but if you need to retrieve a previous version, it's important to maintain versioning.
- **Versioning Artifacts**: To track different versions of artifacts, you can create a versioning system using Jenkins build numbers.

## Creating a Versioning Job
- **Job Creation**: You can create a new job (e.g., **Versioning-Builds**) by copying the configuration from an existing job (e.g., **Build** job). The configuration sections include:
  - **General**
  - **Source Code Management**
  - **Build Triggers**
  - **Build Environment**
  - **Build**
  - **Post-build Actions**
  
- **Shell Commands for Versioning**: In the **Build** section, you can add shell commands using the **Add Build Step** option. An example of creating a versioned artifact:
  ```bash
  mkdir -p versions
  cp target/vprofile-v2.war versions/vprofile-V$BUILD_ID.war
  ```
  This creates a new versioned artifact for each build, stored in the `versions` folder with the build number.

## Jenkins Variables
- You can use predefined variables in Jenkins builds. A list of such variables can be found in the [Jenkins variable list](https://www.perforce.com/manuals/jenkins/Content/P4Jenkins/variableexpansion.html).

## Building with Parameters
- **Parameterized Builds**: You can add parameters under **General** by checking **"This project is parameterized"**. This allows you to pass values to the build process, such as version numbers or branch names.
- After defining parameters, use the **Build with Parameters** option on the Job Dashboard to supply values and execute the build.
  
  **Note**: Avoid using **Build with Parameters** for regular builds as it can slow down the CI/CD process due to waiting for input.

## Versioning with Plugins
- **Plugins for Versioning**: A more efficient way to handle versioning is by using plugins like the **Zentimestamp** plugin, which provides variables such as **BUILD_TIMESTAMP**. For example:
  ```bash
  cp target/vprofile-v2.war versions/vprofile-V$BUILD_TIMESTAMP-$BUILD_ID.war
  ```
  This helps in automatically tagging builds with a timestamp for better version control.

## Managing Plugins in Jenkins
- **Manage Plugins**: Plugins are a crucial aspect of Jenkins. You can install or uninstall plugins via **Dashboard > Manage Jenkins > Manage Plugins**.
- Plugins add additional functionality, such as predefined variables and tools that help enhance the CI/CD pipeline.

## Storing Artifacts Outside Jenkins
- Even though Jenkins stores artifacts in its workspace, it's not intended to be a storage space. Therefore, you should shift your artifacts to external storage solutions during the build process. This will be discussed further in later steps.

# Continuous Integration Pipeline with Jenkins

## Overview of the CI Pipeline
In this section, we walk through the flow of a typical **Continuous Integration (CI) pipeline** using Jenkins, integrating various tools such as Git, Maven, SonarQube, and Nexus Sonatype. These tools help automate code building, testing, code analysis, and artifact storage, all while ensuring quality control.

### Flow of the CI Pipeline

1. **Code Development and Local Testing**:
   - **Developer's Role**: Developers write and test code locally. Once satisfied with the changes, they push the code to a centralized repository such as GitHub.
   - **Git Integration**: Developers use Git to push their code, which then integrates with a GitHub repository.

2. **Jenkins Fetches Code**:
   - **Automated Detection**: Jenkins detects changes in the GitHub repository.
   - **Fetching the Code**: Using the Git plugin, Jenkins fetches the latest changes and stores them in the Jenkins workspace.

3. **Code Build Using Maven**:
   - **Build Process**: Jenkins uses **Maven** to build the code. The specific build tool might vary based on the codebase (e.g., Gradle for other languages), but for this example, we use Maven due to the Java code.
   - **Artifact Generation**: After the build, Maven generates artifacts (e.g., WAR or JAR files) for further steps.

4. **Unit Testing**:
   - **Maven Testing Framework**: Unit tests, included in the source code, are executed using Maven. These tests ensure that individual components of the application work as expected.
   - **Report Generation**: Test results are typically stored in XML format.

5. **Code Analysis Using SonarQube**:
   - **SonarQube Scanner**: Jenkins uses the SonarQube scanner to analyze the code for vulnerabilities, bugs, and adherence to best practices. SonarQube generates detailed reports on code quality.
   - **Setting Quality Gates**: SonarQube can be configured with quality gates to halt the pipeline if the code fails to meet defined standards. The reports are uploaded to the SonarQube server for further analysis.

6. **Artifact Verification and Versioning**:
   - **Verified Artifacts**: Once the code passes both unit tests and code analysis, the artifacts are deemed verified.
   - **Artifact Storage in Nexus**: Before deployment, these verified artifacts are versioned and stored in a repository such as **Nexus Sonatype** for easy retrieval during deployment.

### Integration of CI Tools
- **CI Tool**: Jenkins serves as the CI tool in this pipeline, but other CI tools like GitLab CI, CircleCI, or Bamboo could be used with similar steps.
- **Tool Integration**: Regardless of the CI tool used, integration with GitHub (for version control), SonarQube (for code analysis), and Nexus (for artifact storage) is essential.

### Key Stages in CI Pipeline:
1. **Build the Code**: Compile the source code using Maven.
2. **Test the Code**: Run unit tests as part of the build process.
3. **Analyze the Code**: Perform static code analysis to check for vulnerabilities and code quality.
4. **Get Verified Artifacts**: If the code passes all stages, version and store artifacts in Nexus for deployment.

This process ensures continuous feedback on code quality and automation of repetitive tasks, leading to efficient and reliable software delivery.

# Continuous Integration Pipeline Setup with Jenkins, Nexus, and SonarQube

This guide outlines the steps to create a **Continuous Integration (CI) pipeline** using Jenkins, Nexus, and SonarQube. We'll also cover the required plugins, EC2 instance configurations, and security group settings to ensure proper integration and communication between the tools.

## Step 1: Setting Up Jenkins, Nexus, and SonarQube on EC2

### Tools and Instances:
- **Jenkins**: The CI tool for managing the pipeline.
- **Nexus**: Artifact repository for storing built artifacts.
- **SonarQube**: Code analysis tool for ensuring code quality.

### EC2 Instances:
- **Jenkins**: 
  - **OS**: Ubuntu 18
  - **Instance Type**: t2.small
  - **Security Group**: 
    - SSH (Port 22) – My IP
    - Custom TCP (Port 8080) – Anywhere
  - **User Data Script**: Jenkins setup script to automate installation.

- **Nexus**: 
  - **OS**: CentOS 7
  - **Instance Type**: t2.medium
  - **Security Group**: 
    - SSH (Port 22) – My IP
    - Custom TCP (Port 8081) – Anywhere
  - **User Data Script**: Nexus setup script for automated installation.
  - **Jenkins Integration**: Jenkins accesses Nexus on Port 8081.

- **SonarQube**: 
  - **OS**: Ubuntu 18
  - **Instance Type**: t2.medium
  - **Security Group**:
    - SSH (Port 22) – My IP
    - Custom TCP (Ports 80 and 9000) – Anywhere
  - **Ports**: SonarQube generally runs on Port 9000, but Port 80 can be used for integration through Nginx (which forwards requests from Port 80 to 9000).

### Security Group Configuration:
For all instances, create appropriate security groups with rules such as:
- **SSH (Port 22)**: For secure access to the instances.
- **Custom TCP (Ports 80, 8080, 8081, 9000)**: For Jenkins, Nexus, and SonarQube communication.

## Step 2: Integrating Jenkins, Nexus, and SonarQube

### Plugin Installation:
To enable integration between Jenkins, Nexus, and SonarQube, install the necessary plugins on Jenkins:
- **Git Plugin**: For fetching code from version control systems like GitHub.
- **Nexus Plugin**: To store artifacts in Nexus.
- **SonarQube Plugin**: For running code quality checks using SonarQube.
- **Maven Plugin**: To build Java projects using Maven.
- **BuildTimestamp Plugin**: For managing timestamps during builds.
- **Pipeline Utility Steps**: For utilities in Jenkins pipelines.

### How to Install Plugins:
1. Go to **Jenkins Dashboard** > **Manage Jenkins**.
2. Click on **Manage Plugins**.
3. Navigate to the **Available** tab.
4. Search and install the required plugins.

## Step 3: Writing the CI Pipeline Script

### Pipeline Script Overview:
Once the necessary tools and plugins are installed, write a pipeline script in Jenkins to automate the process:
1. **Source Code Fetching**: Use the Git plugin to pull the code from the repository (e.g., GitHub).
2. **Build Process**: Build the project using Maven, which will generate the artifacts.
3. **Code Quality Checks**: Integrate SonarQube to analyze the code for vulnerabilities and best practices.
4. **Artifact Storage**: Store the generated artifacts in Nexus after they pass all tests.

### Notifications:
Set up notifications within the pipeline to alert users if any steps fail during the build process.

## Step 4: Creating User Data Scripts

To automate the setup of Jenkins, Nexus, and SonarQube on EC2 instances, use user data scripts provided in the following GitHub repository:
- [GitHub Repo for Setup Scripts](https://github.com/devopshydclub/vprofile-project/tree/ci-jenkins/userdata)

This repository contains scripts for configuring Jenkins, Nexus, and SonarQube on their respective EC2 instances.

## Conclusion

By setting up Jenkins, Nexus, and SonarQube with the required plugins and using EC2 instances, we can create a fully automated **Continuous Integration Pipeline**. This pipeline enables seamless code integration, building, testing, and storing of artifacts while ensuring code quality.

# Pipeline as Code with Jenkins

In this section, we will discuss how to implement **Pipeline as Code** using a `Jenkinsfile`, which defines the stages of a CI/CD pipeline in a text file format. This approach allows for automation and easier version control of the pipeline configuration.

## Introduction to Pipeline as Code

### What is Pipeline as Code?
- **Automates pipeline setup** by defining the pipeline in a `Jenkinsfile`.
- A `Jenkinsfile` contains the pipeline's stages and steps using **Pipeline DSL Syntax**, which is based on **Groovy**.
- There are two types of pipelines:
  - **Scripted Pipeline**: More flexible but harder to manage.
  - **Declarative Pipeline**: More structured and easier to use.
  
In most cases, we use **Declarative Pipeline** to define the CI/CD process.

## Pipeline Concepts

### Key Components:
1. **Pipeline Block**: The core block where the entire Jenkins pipeline code resides.
2. **Node/Agent**: Specifies where the pipeline should execute (on which node or agent).
3. **Stages**: Represents the phases in the pipeline (e.g., Build, Test, Deploy). 
4. **Steps**: Commands or actions to be executed in each stage (e.g., Maven install, Git pull, upload to Nexus).

### Example Pipeline Structure
```groovy
pipeline {
    agent { label "master" }
    tools { maven "Maven" }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "your-ip-address-here:8081"
        NEXUS_REPOSITORY = "maven-nexus-repo"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
        ARTVERSION = "${env.BUILD_ID}"
    }
    stages {
        stage('Build') {
            steps {
                // Build commands go here
            }
        }
        stage('Test') {
            steps {
                // Testing steps
            }
        }
        stage('Deploy') {
            steps {
                // Deployment commands
            }
        }
    }
}
```

## Pipeline Breakdown

### 1. **Pipeline Block**
The main block of the pipeline where all configurations and stages are defined.
```groovy
pipeline {
    // Configuration and stages go here
}
```

### 2. **Agent Block**
The `agent` block specifies where the pipeline will run. You can choose to run the job on the master node, a specific agent, or any available node.
```groovy
agent {
    label "master"
}
```

### 3. **Tools Block**
The `tools` block defines tools (like Maven, JDK, etc.) to be used, which are pre-configured in Jenkins' global tool configuration.
```groovy
tools {
    maven "Maven"
}
```

### 4. **Environment Variables**
Environment variables are declared to store configuration values such as Nexus server details, repository information, and build numbers. These variables can be reused throughout the pipeline.
```groovy
environment {
    NEXUS_VERSION = "nexus3"
    NEXUS_PROTOCOL = "http"
    NEXUS_URL = "your-ip-address-here:8081"
    NEXUS_REPOSITORY = "maven-nexus-repo"
    NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
    ARTVERSION = "${env.BUILD_ID}"
}
```

### 5. **Stages**
Stages represent the different phases of the CI/CD process, such as **Build**, **Test**, and **Deploy**. Each stage contains multiple steps to execute specific tasks.
```groovy
stages {
    stage('Build') {
        steps {
            // Steps for building the project
        }
    }
    stage('Test') {
        steps {
            // Steps for running tests
        }
    }
    stage('Deploy') {
        steps {
            // Steps for deployment
        }
    }
}
```

### 6. **Steps**
Each stage can have multiple steps. Steps represent the individual tasks to be executed in that stage, such as running commands or scripts.
```groovy
steps {
    sh 'mvn clean install'  // Example of a step to build a Maven project
}
```

## Summary

Using **Pipeline as Code** with a `Jenkinsfile` enables automation and efficient version control of your CI/CD pipelines. The pipeline can be configured declaratively to define:
- **Agents** (where the job runs)
- **Tools** (like Maven, JDK)
- **Environment variables** for reusable configuration
- **Stages** and **steps** for build, testing, and deployment

This approach simplifies CI/CD management and improves maintainability by allowing you to store pipeline configurations in your version control system.

# Continuous Integration with Jenkins: Pipeline as Code

### Introduction to Pipeline as Code
**Pipeline as Code** allows us to automate the setup and execution of CI/CD pipelines by defining them in a `Jenkinsfile`. This file includes all the necessary stages and steps, which are executed automatically by Jenkins. 

Key Features:
- Automates pipeline configuration through a `Jenkinsfile`.
- Supports defining multiple **stages** and **steps** for tasks like cloning code, building projects, publishing artifacts, and performing tests.
- Each pipeline stage can include commands and post-build actions such as archiving artifacts or sending notifications.

### Basic Pipeline Structure
The pipeline begins with a **pipeline block** that contains various stages. Each stage represents a significant phase in the CI/CD process, such as:
- **Cloning code from VCS (Version Control System)**
- **Running Maven build**
- **Publishing artifacts to Nexus Repository**
- **Code analysis or deploying artifacts**

Example:
```groovy
pipeline {
    stages {
        stage("Clone code from VCS") {
            steps {
                // Commands to clone code
            }
        }
        stage("Maven Build") {
            steps {
                sh 'mvn clean install'
            }
        }
        stage("Publish to Nexus Repository Manager") {
            steps {
                // Commands to publish to Nexus
            }
        }
    }
}
```

### Stages and Steps
- **Stages**: The main execution phases where specific tasks are performed. You can define multiple stages in a pipeline.
- **Steps**: Within each stage, steps are defined to execute specific tasks. For instance, in the build stage, Maven commands can be executed.

Each stage can include additional actions:
- **Post-build steps**: These are actions that occur after a stage is completed. For example, archiving artifacts or sending email notifications upon successful builds.

Example of a pipeline with post-build steps:
```groovy
pipeline {
    stage('BuildAndTest') {
        steps {
            sh 'mvn install'
        }
        post {
            success {
                echo 'Now Archiving...'
                archiveArtifacts artifacts: 'target/*.war'
            }
        }
    }
}
```

### Automation in DevOps Pipelines
In DevOps, automation is key. The entire pipeline is defined in the `Jenkinsfile`, enabling consistent and repeatable processes. Automation extends not only to the build process but also to testing, code analysis, artifact publishing, and even notifications.

### Tool Configuration in Jenkins
When specifying tools in the Jenkins pipeline, ensure that they are consistent with those configured in the **Global Tool Configuration** of Jenkins. This ensures proper execution of tasks such as building projects with Maven or deploying to Nexus.

### Summary
- **Pipeline as Code** automates the CI/CD pipeline configuration.
- Stages such as cloning code, building with Maven, and publishing artifacts can be defined in a `Jenkinsfile`.
- Each stage contains steps and can include post-build actions like archiving and notifications.
- Proper tool configuration in Jenkins is essential for successful pipeline execution.

By defining the pipeline in code, DevOps teams can ensure that CI/CD processes are automated, repeatable, and version-controlled.