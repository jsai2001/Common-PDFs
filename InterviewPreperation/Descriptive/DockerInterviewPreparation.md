Here’s a comprehensive list of Docker interview preparation questions, covering a wide range of topics from basic concepts to advanced use cases. These questions are designed to test theoretical knowledge, practical skills, and problem-solving abilities commonly sought in interviews.

### Docker Basics
- What is Docker, and how does it differ from a traditional virtual machine?
- What are the key components of Docker architecture (e.g., Docker daemon, Docker client, Docker images, etc.)?
- What is a Docker container, and how is it different from a Docker image?
- What problem does Docker solve in software development and deployment?
- What is the role of the Dockerfile in Docker?
- Explain the difference between `docker run`, `docker start`, and `docker exec`.
- What is containerization, and how does Docker implement it?
- What are the advantages of using Docker over traditional virtualization?
- What is the default storage driver in Docker, and why does it matter?
- How do you check the version of Docker installed on a system?

### Docker Images
- How do you create a Docker image from a Dockerfile?
- What are the common instructions used in a Dockerfile (e.g., `FROM`, `RUN`, `CMD`, `ENTRYPOINT`, etc.)?
- What’s the difference between `CMD` and `ENTRYPOINT` in a Dockerfile?
- How can you reduce the size of a Docker image?
- What is a multi-stage build in Docker, and why is it useful?
- How do you list all Docker images on your system?
- What is the purpose of the `.dockerignore` file?
- How do you tag a Docker image, and why is tagging important?
- What happens when you run `docker build`?
- How do you remove unused Docker images from your system?

### Docker Containers
- How do you start a container in detached mode?
- How do you view the logs of a running container?
- What is the difference between `docker stop` and `docker kill`?
- How do you inspect the details of a running container?
- How can you run a container with a specific name?
- What happens to a container’s data when it stops?
- How do you remove a container, and what’s the difference between `docker rm` and `docker rm -f`?
- How do you limit the CPU and memory usage of a Docker container?
- What is the purpose of the `--restart` flag in Docker?
- How do you attach to a running container’s terminal?

### Docker Networking
- What are the different types of Docker networks (e.g., bridge, host, overlay)?
- How does the default bridge network work in Docker?
- How do you create a custom Docker network?
- What is the difference between the host network and the bridge network?
- How do containers communicate with each other in Docker?
- What is the purpose of the `--link` flag, and why is it considered deprecated?
- How do you expose a port in a Docker container?
- What is the difference between `EXPOSE` in a Dockerfile and the `-p` flag in `docker run`?
- How does Docker handle DNS resolution for containers?
- What is an overlay network, and when would you use it?

### Docker Storage
- What are Docker volumes, and how do they differ from bind mounts?
- How do you create and manage a Docker volume?
- What happens to a volume when a container is deleted?
- How do you mount a local directory into a Docker container?
- What is a `tmpfs` mount, and when would you use it?
- How does Docker handle persistent data storage?
- What is the difference between `--mount` and `--volume` in Docker commands?
- How do you list all volumes on your Docker host?
- What are storage drivers, and how do you choose the right one?
- How can you back up data stored in a Docker volume?

### Docker Compose
- What is Docker Compose, and how does it differ from Docker?
- What is the structure of a `docker-compose.yml` file?
- How do you start and stop services defined in a Docker Compose file?
- What is the difference between `docker-compose up` and `docker-compose up -d`?
- How do you scale a service using Docker Compose?
- How do you define environment variables in a Docker Compose file?
- What is the purpose of the `depends_on` keyword in Docker Compose?
- How do you override settings in a `docker-compose.yml` file?
- How do you link services in Docker Compose?
- What happens when you run `docker-compose down`?

### Docker Swarm
- What is Docker Swarm, and how does it compare to Kubernetes?
- How do you initialize a Docker Swarm cluster?
- What is the difference between a manager node and a worker node in Docker Swarm?
- How do you deploy a service in Docker Swarm?
- What is a stack in Docker Swarm, and how do you deploy one?
- How does Docker Swarm handle load balancing?
- How do you scale a service in Docker Swarm?
- What is the purpose of the `docker service` command?
- How do you update a running service in Docker Swarm without downtime?
- How do you remove a node from a Docker Swarm cluster?

### Docker Security
- What are some best practices for securing Docker containers?
- How do you run a Docker container as a non-root user?
- What is the purpose of Docker Content Trust (DCT)?
- How do you scan a Docker image for vulnerabilities?
- What are the risks of running Docker with the `--privileged` flag?
- How do you configure resource limits to prevent a container from overloading the host?
- What is the role of namespaces and cgroups in Docker security?
- How do you restrict network traffic between containers?
- What is a Docker secret, and how do you use it?
- How can you prevent a container from accessing the host filesystem?

### Advanced Docker Concepts
- What is the difference between Docker and Podman?
- How does Docker handle logging, and what are the available logging drivers?
- What is the purpose of the Docker Registry, and how do you set up a private one?
- How do you push an image to Docker Hub?
- What are Docker plugins, and how do you use them?
- How do you debug a Docker container that’s failing to start?
- What is the difference between a Docker image layer and a container layer?
- How does Docker handle multi-architecture images (e.g., ARM vs. x86)?
- What is the purpose of the `HEALTHCHECK` instruction in a Dockerfile?
- How do you roll back a service update in Docker Swarm?

### Practical/Scenario-Based Questions
- How would you containerize a simple web application (e.g., Node.js app)?
- Write a Dockerfile for a Python application that uses Flask.
- How would you troubleshoot a container that exits immediately after starting?
- How do you migrate an existing application to Docker?
- Design a Docker Compose file for a microservices architecture with a frontend, backend, and database.
- How would you set up a CI/CD pipeline using Docker?
- What steps would you take if a Docker container is consuming too much memory?
- How do you ensure zero downtime during a Docker container update?
- How would you handle a situation where a Docker image fails to build?
- Explain how you would deploy a multi-container application to production using Docker Swarm.

### Tips for Preparation
- **Hands-On Practice**: Set up Docker on your local machine and experiment with building images, running containers, and using Docker Compose/Swarm.
- **Understand Use Cases**: Be ready to explain how Docker solves real-world problems (e.g., environment consistency, scalability).
- **Review Documentation**: Familiarize yourself with the official Docker docs for commands and configurations.
- **Scenario-Based Thinking**: Practice explaining your thought process for troubleshooting or designing solutions.

Let me know if you’d like detailed answers to any of these questions or help with specific scenarios!

1. **What is Docker, and how does it differ from a traditional virtual machine?**
Docker is an open-source platform that automates the deployment, scaling, and management of applications using containerization technology. It allows applications and their dependencies to be packaged into lightweight, portable containers that can run consistently across different environments. Unlike a traditional virtual machine (VM), which emulates an entire operating system (OS) with a hypervisor, Docker containers share the host OS kernel and run as isolated processes. This makes Docker faster, more lightweight, and resource-efficient compared to VMs, which require a full guest OS and consume more memory and CPU.

2. **What are the key components of Docker architecture?**
Docker’s architecture consists of:
- **Docker Daemon (dockerd):** The background service that manages Docker objects like images, containers, networks, and volumes. It listens for API requests and executes commands.
- **Docker Client:** The command-line interface (CLI) tool (docker) that users interact with to send commands to the daemon.
- **Docker Images:** Read-only templates used to create containers, built from a series of layers defined in a Dockerfile.
- **Docker Containers:** Runnable instances of images, encapsulating an application and its dependencies.
- **Docker Registry:** A storage system (e.g., Docker Hub) for distributing and sharing Docker images.
- **Docker Networking and Storage:** Components that manage container communication and persistent data.

3. **What is a Docker container, and how is it different from a Docker image?**
A Docker container is a lightweight, standalone, and executable instance of a Docker image that runs an application and its dependencies in an isolated environment. It’s created when you run a Docker image. A Docker image, on the other hand, is a static, immutable template made up of layers (e.g., OS, libraries, app code) that defines what the container will run. In short: an image is the blueprint, and a container is the running instance of that blueprint.

4. **What problem does Docker solve in software development and deployment?**
Docker solves the "it works on my machine" problem by ensuring consistency across development, testing, and production environments. It packages an application with its dependencies (libraries, configs, etc.) into a container, eliminating issues caused by differences in OS, software versions, or configurations. It also simplifies deployment, improves scalability, and speeds up development cycles by enabling rapid provisioning and portability across systems.

5. **What is the role of the Dockerfile in Docker?**
A Dockerfile is a script containing instructions to build a Docker image. It defines the base image (e.g., `FROM ubuntu`), sets up the environment (e.g., installing dependencies with `RUN`), copies application code (`COPY`), and specifies how the app should run (e.g., `CMD` or `ENTRYPOINT`). Docker uses the Dockerfile to automate and standardize image creation, ensuring repeatability and consistency.

6. **Explain the difference between `docker run`, `docker start`, and `docker exec`.**
- `docker run:` Creates a new container from an image and starts it in one command. Example: `docker run -it ubuntu bash` starts a new Ubuntu container with an interactive shell.
- `docker start:` Restarts an existing, stopped container using its ID or name. It doesn’t create a new container. Example: `docker start my_container`.
- `docker exec:` Runs a command inside an already running container without stopping it. Example: `docker exec my_container ls` executes `ls` inside `my_container`.

In summary: `run` creates and starts, `start` restarts, and `exec` interacts with a running container.

7. **What is containerization, and how does Docker implement it?**
Containerization is a virtualization method that packages an application and its dependencies into a single unit (container) that runs consistently across different environments. Unlike VMs, containers share the host OS kernel, isolating only the user space. Docker implements this using Linux features like namespaces (for process isolation), cgroups (for resource limits), and a union filesystem (for layered storage). The Docker daemon manages these components to create, run, and orchestrate containers efficiently.

8. **What are the advantages of using Docker over traditional virtualization?**
- **Lightweight:** Containers share the host OS, reducing overhead compared to VMs that need a full guest OS.
- **Speed:** Containers start almost instantly, while VMs take longer to boot.
- **Resource Efficiency:** Uses less CPU, memory, and storage due to no guest OS duplication.
- **Portability:** Containers run consistently across different systems (e.g., dev machines, cloud).
- **Scalability:** Easier to deploy and scale multiple containers compared to VMs.
- **DevOps Friendly:** Simplifies CI/CD pipelines and microservices architectures.

9. **What is the default storage driver in Docker, and why does it matter?**
The default storage driver in Docker depends on the host OS, but it’s typically `overlay2` on modern Linux distributions (e.g., Ubuntu, CentOS). The storage driver manages how Docker stores image layers and container data. It matters because it affects performance, disk usage, and compatibility. For example, `overlay2` is efficient and supports layered filesystems, while older drivers like `devicemapper` might be slower or less flexible. Choosing the right driver depends on the OS, filesystem, and workload requirements.

10. **How do you check the version of Docker installed on a system?**
To check the Docker version, run the command: `docker --version` or `docker -v`. This displays the client version (e.g., "Docker version 20.10.12"). For more details, including the server (daemon) version, use `docker version`, which shows both client and server information, such as API version and build details.

11. **How do you create a Docker image from a Dockerfile?**  
To create a Docker image from a Dockerfile, use the `docker build` command. Navigate to the directory containing the Dockerfile and run: `docker build -t image_name:tag .`. The `-t` flag specifies the image name and tag (e.g., `myapp:1.0`), and the `.` indicates the build context (current directory). Docker reads the Dockerfile, executes its instructions step-by-step, and creates a new image with cached layers for efficiency.

12. **What are the common instructions used in a Dockerfile?**  
Common Dockerfile instructions include:  
- `FROM`: Specifies the base image (e.g., `FROM ubuntu:20.04`).  
- `RUN`: Executes commands during the build process (e.g., `RUN apt-get update`).  
- `CMD`: Defines the default command to run when a container starts (e.g., `CMD ["python", "app.py"]`).  
- `ENTRYPOINT`: Sets the primary executable for the container, often used with `CMD` (e.g., `ENTRYPOINT ["nginx"]`).  
- `COPY`: Copies files from the host to the image (e.g., `COPY . /app`).  
- `ADD`: Similar to `COPY`, but can also extract tar files or fetch URLs.  
- `WORKDIR`: Sets the working directory for subsequent instructions (e.g., `WORKDIR /app`).  
- `EXPOSE`: Documents ports the container listens on (e.g., `EXPOSE 80`).  
- `ENV`: Sets environment variables (e.g., `ENV PORT=8080`).

13. **What’s the difference between CMD and ENTRYPOINT in a Dockerfile?**  
- `CMD`: Specifies the default command or arguments to run when a container starts. It can be overridden by arguments in `docker run`. Example: `CMD ["echo", "hello"]`.  
- `ENTRYPOINT`: Defines the main executable for the container, which runs every time the container starts. Arguments from `CMD` or `docker run` are appended to it. Example: `ENTRYPOINT ["python"]` with `CMD ["app.py"]` runs `python app.py`.

Key difference: `CMD` is replaceable, while `ENTRYPOINT` is fixed unless explicitly overridden with `--entrypoint`. Use `CMD` for defaults, `ENTRYPOINT` for consistent behavior.

14. **How can you reduce the size of a Docker image?**  
To reduce Docker image size:  
- Use a smaller base image (e.g., `alpine` instead of `ubuntu`).  
- Minimize layers by chaining commands (e.g., `RUN apt-get update && apt-get install -y python`).  
- Remove unnecessary files after installation (e.g., `RUN apt-get clean && rm -rf /var/lib/apt/lists/*`).  
- Use multi-stage builds to exclude build-time dependencies.  
- Avoid copying unneeded files by using `.dockerignore`.

Smaller images improve performance, reduce storage, and lower attack surface.

15. **What is a multi-stage build in Docker, and why is it useful?**  
A multi-stage build is a Dockerfile technique that uses multiple `FROM` statements to separate build and runtime environments. For example, one stage builds the app (e.g., compiles code), and another stage copies only the final artifacts into a lightweight image. Example:  
```dockerfile
FROM golang AS builder  
WORKDIR /app  
COPY . .  
RUN go build -o myapp  

FROM alpine  
COPY --from=builder /app/myapp /usr/bin/  
CMD ["myapp"]
```
It’s useful because it reduces image size by excluding build tools and intermediate files, improving security and efficiency.

16. **How do you list all Docker images on your system?**  
To list all Docker images, run: `docker images` or `docker image ls`. This displays a table with columns like repository, tag, image ID, creation time, and size. Add the `-a` flag (`docker images -a`) to include intermediate images, or use `--filter` to refine the output (e.g., `docker images --filter "dangling=true"` for unused images).

17. **What is the purpose of the .dockerignore file?**  
The `.dockerignore` file specifies files and directories to exclude from the build context when running `docker build`. It works like `.gitignore`. For example, listing `node_modules` or `.git` prevents these from being copied into the image, speeding up builds and avoiding conflicts (e.g., local `node_modules` overriding image dependencies). It reduces image size and improves build performance.

18. **How do you tag a Docker image, and why is tagging important?**  
To tag an image, use the `-t` flag with `docker build` (e.g., `docker build -t myapp:1.0 .`) or the `docker tag` command (e.g., `docker tag myapp:1.0 myapp:latest`). Tagging assigns a human-readable name and version to an image (e.g., `myapp:1.0`). It’s important for versioning, tracking, and deployment—allowing you to differentiate between builds (e.g., `latest`, `v1.0`, `dev`) and push specific versions to registries.

19. **What happens when you run docker build?**  
When you run `docker build`, Docker:  
- Reads the Dockerfile from the build context (specified by `.` or a path).  
- Sends the context (files/directories) to the Docker daemon.  
- Executes each instruction in the Dockerfile sequentially, creating intermediate layers.  
- Caches layers to speed up future builds (unless changes occur).  
- Produces a final image with a unique ID, tagged if specified (e.g., `-t myapp:1.0`).

The output shows build steps and any errors encountered.

20. **How do you remove unused Docker images from your system?**  
To remove unused Docker images:  
- Use `docker image rm <image_id>` to delete a specific image by ID or name (e.g., `docker image rm myapp:1.0`).  
- Use `docker image prune` to remove all dangling images (untagged intermediates).  
- Use `docker image prune -a` to delete all unused images not associated with running containers.

Add `-f` to force removal without confirmation. This frees up disk space and keeps the system clean.

21. **How do you start a container in detached mode?**
To start a container in detached mode, use the `-d` flag with `docker run`. For example: `docker run -d nginx` launches an Nginx container in the background, freeing up your terminal. Detached mode means the container runs without attaching to your console, and you can check its status with `docker ps`.

22. **How do you view the logs of a running container?**
To view the logs of a running container, use: `docker logs <container_id>` or `docker logs <container_name>`. For real-time logs, add the `-f` flag (e.g., `docker logs -f my_container`) to follow the output as it’s generated. This is useful for debugging or monitoring container activity.

23. **What is the difference between docker stop and docker kill?**
- `docker stop`: Gracefully stops a container by sending a SIGTERM signal, giving the process time to shut down cleanly (default timeout: 10 seconds). Example: `docker stop my_container`.
- `docker kill`: Forcefully terminates a container immediately by sending a SIGKILL signal, with no cleanup time. Example: `docker kill my_container`.

In short: `stop` is polite, `kill` is abrupt; use `stop` for clean shutdowns, `kill` for emergencies.

24. **How do you inspect the details of a running container?**
To inspect a running container’s details, use: `docker inspect <container_id>` or `docker inspect <container_name>`. This returns a JSON object with metadata like container state, network settings, mounts, environment variables, and more. For specific info, filter with `--format` (e.g., `docker inspect --format '{{.NetworkSettings.IPAddress}}' my_container`).

25. **How can you run a container with a specific name?**
To run a container with a specific name, use the `--name` flag with `docker run`. Example: `docker run --name my_container nginx` names the container `my_container`. If the name is already in use, Docker will throw an error unless you remove or rename the existing container.

26. **What happens to a container’s data when it stops?**
When a container stops, its in-memory data (e.g., runtime state) is lost because containers are ephemeral. However, data written to the container’s filesystem persists until the container is deleted, unless it’s in a temporary location like `/tmp`. Persistent data requires volumes or bind mounts (e.g., `docker run -v /data:/app/data`). Stopped containers retain their filesystem state and can be restarted with `docker start`.

27. **How do you remove a container, and what’s the difference between docker rm and docker rm -f?**
To remove a container, use: `docker rm <container_id>` or `docker rm <container_name>`. The container must be stopped first.
- `docker rm`: Deletes a stopped container. If it’s running, you’ll get an error.
- `docker rm -f`: Forces removal by stopping and deleting the container in one step, even if it’s running.

Example: `docker rm -f my_container` ensures cleanup regardless of state. Use `-f` to save time, but cautiously as it skips graceful shutdown.

28. **How do you limit the CPU and memory usage of a Docker container?**
To limit resources, use flags with `docker run`:
- CPU: `--cpus="1.5"` restricts the container to 1.5 CPU cores, or `--cpu-shares=512` sets a relative weight for CPU sharing.
- Memory: `-m 512m` limits memory to 512 MB (e.g., `docker run -m 512m nginx`).

Example: `docker run -d --cpus="1" -m 256m nginx` caps CPU at 1 core and memory at 256 MB. This prevents resource hogging and ensures fair allocation on the host.

29. **What is the purpose of the --restart flag in Docker?**
The `--restart` flag defines a restart policy for a container when it exits or the Docker daemon restarts. Options include:
- `--restart=no`: Default, no restart.
- `--restart=on-failure:5`: Restarts up to 5 times if the container exits with a non-zero code.
- `--restart=always`: Always restarts, even on clean exit.
- `--restart=unless-stopped`: Restarts unless explicitly stopped by the user.

Example: `docker run -d --restart=always nginx` keeps Nginx running indefinitely. It’s useful for ensuring service availability.

30. **How do you attach to a running container’s terminal?**
To attach to a running container’s terminal, use: `docker attach <container_id>` or `docker attach <container_name>`. This connects your terminal to the container’s stdin/stdout/stderr. For an interactive shell, ensure the container supports it (e.g., started with `-it`). To detach without stopping, press `Ctrl+P`, `Ctrl+Q`. Alternatively, use `docker exec` for a new session (e.g., `docker exec -it my_container bash`).

31. **What are the different types of Docker networks (e.g., bridge, host, overlay)?**

Docker supports several network types:
- **Bridge**: The default network; isolates containers on a private network with NAT to the host (e.g., 172.17.0.0/16).
- **Host**: Removes network isolation, using the host’s network stack directly; no port mapping needed.
- **Overlay**: Connects containers across multiple Docker hosts, ideal for swarm mode or distributed apps.
- **None**: Disables networking entirely for a container.
- **Macvlan**: Assigns a MAC address to a container, making it appear as a physical device on the network.

Each type suits different use cases, from isolation to multi-host communication.

32. **How does the default bridge network work in Docker?**

The default bridge network (named `bridge`) is created automatically when Docker starts. It assigns containers private IP addresses (e.g., 172.17.0.x) on a virtual network. Containers on this network can communicate with each other via IP, and Docker uses NAT to connect them to the host and external networks. Port mapping (e.g., `-p 80:80`) exposes container ports to the host. It’s simple and isolated but lacks advanced features like automatic DNS between containers.

33. **How do you create a custom Docker network?**

To create a custom Docker network, use: `docker network create <network_name>`. Example: `docker network create my_network` creates a bridge network by default. Specify a driver with `--driver` (e.g., `docker network create --driver overlay my_overlay`). Then, run containers on it with `--network` (e.g., `docker run --network my_network nginx`). Custom networks enable features like DNS resolution by container name.

34. **What is the difference between the host network and the bridge network?**

- **Host Network**: The container uses the host’s network stack directly, sharing its IP and ports (e.g., `docker run --network host nginx`). No port mapping is needed, but there’s no isolation, limiting one instance per port.
- **Bridge Network**: The container gets its own network namespace with a private IP, isolated from the host. Port mapping (e.g., `-p 80:80`) connects it to the host. It provides isolation but requires configuration.

Use host for performance, bridge for isolation.

35. **How do containers communicate with each other in Docker?**

Containers communicate via:
- **IP Addressing**: On the same network, they use their assigned IPs (e.g., 172.17.0.2).
- **Container Names**: On user-defined bridge networks, Docker’s DNS resolves container names (e.g., `ping my_container`).
- **Port Mapping**: Expose ports to the host or other containers (e.g., `-p 80:80`).
- **Shared Networks**: Containers on the same custom network (e.g., `docker run --network my_network`) can talk directly.

The default bridge requires manual IP linking, while custom networks simplify it.

36. **What is the purpose of the --link flag, and why is it considered deprecated?**

The `--link` flag connects containers on the default bridge network, allowing them to communicate using aliases (e.g., `docker run --link db_container:db nginx`). It sets environment variables and updates `/etc/hosts` for name resolution. It’s deprecated because custom networks (e.g., `docker network create`) offer better solutions: automatic DNS, scalability, and no reliance on legacy hacks. `--link` is limited to single-host setups and lacks flexibility.

37. **How do you expose a port in a Docker container?**

To expose a port, use the `-p` flag with `docker run` to map a host port to a container port (e.g., `docker run -p 8080:80 nginx` maps host port 8080 to container port 80). Alternatively, use `EXPOSE` in the Dockerfile (e.g., `EXPOSE 80`) to document intent, though it doesn’t publish the port—`-p` or `-P` is still needed to make it accessible externally.

38. **What is the difference between EXPOSE in a Dockerfile and the -p flag in docker run?**

- **EXPOSE**: A Dockerfile instruction (e.g., `EXPOSE 80`) that documents which ports the container listens on. It’s informational and doesn’t publish the port to the host; it’s for inter-container communication or tools like `docker run -P`.
- **-p**: A runtime flag (e.g., `-p 8080:80`) that actively binds a host port to a container port, making it accessible externally.

In short: EXPOSE suggests, `-p` enforces.

39. **How does Docker handle DNS resolution for containers?**

Docker provides DNS resolution within user-defined networks (not the default bridge). Containers on the same custom network (e.g., `docker network create my_net`) can resolve each other’s names automatically (e.g., `ping my_container`). The Docker daemon runs a DNS server, mapping container names to IPs. On the default bridge, you’d need `--link` or manual IP references, but custom networks make it seamless.

40. **What is an overlay network, and when would you use it?**

An overlay network is a Docker network that spans multiple hosts, enabling container communication across a swarm cluster. It uses VXLAN to encapsulate traffic over the underlying network. Create it with `docker network create --driver overlay my_overlay` in swarm mode. Use it for distributed applications, microservices, or multi-host deployments where containers need to talk seamlessly (e.g., a web app and database split across nodes).

41. **What are Docker volumes, and how do they differ from bind mounts?**  
Docker volumes are managed storage areas created by Docker to persist data outside a container’s filesystem. They’re stored in `/var/lib/docker/volumes` on the host. Bind mounts, conversely, map a specific host directory or file (e.g., `/home/user/data`) into the container. Differences:  
- **Management**: Volumes are Docker-managed; bind mounts are user-defined.  
- **Portability**: Volumes are more portable (no absolute host paths).  
- **Flexibility**: Bind mounts offer precise control over host locations.  
Use volumes for simplicity, bind mounts for custom paths.

42. **How do you create and manage a Docker volume?**  
To create a volume: `docker volume create my_volume`. To use it in a container: `docker run -v my_volume:/app nginx`. Manage volumes with:  
- `docker volume ls`: List all volumes.  
- `docker volume inspect my_volume`: View details (e.g., mount point).  
- `docker volume rm my_volume`: Delete a volume (if unused).  
- `docker volume prune`: Remove all unused volumes.  
Volumes persist data independently of containers.

43. **What happens to a volume when a container is deleted?**  
When a container is deleted (e.g., `docker rm my_container`), its associated volumes remain intact unless explicitly removed. Volumes are independent of containers, stored in `/var/lib/docker/volumes`, and persist until deleted with `docker volume rm` or `docker volume prune`. This ensures data isn’t lost unless you choose to clean it up.

44. **How do you mount a local directory into a Docker container?**  
To mount a local directory, use a bind mount with the `-v` or `--mount` flag in `docker run`. Example: `docker run -v /host/path:/container/path nginx` maps `/host/path` to `/container/path` in the container. Ensure the host path exists. This allows direct access to host files, useful for development or sharing data.

45. **What is a tmpfs mount, and when would you use it?**  
A tmpfs mount is a temporary filesystem stored in the host’s memory (RAM), not on disk. Create it with: `docker run --tmpfs /tmp:size=100m nginx`, where `/tmp` is the mount point and `100m` is the size limit. Use it for sensitive data (e.g., secrets) or performance-critical temporary storage, as it’s fast and erased when the container stops.

46. **How does Docker handle persistent data storage?**  
Docker handles persistent storage with:  
- **Volumes**: Managed by Docker, stored in `/var/lib/docker/volumes`, ideal for databases or app data (e.g., `docker run -v my_volume:/data`).  
- **Bind Mounts**: Link host directories to containers for custom persistence (e.g., `-v /data:/app`).  
Data in these persists beyond container lifecycles, unlike the container’s writable layer, which is lost on deletion.

47. **What is the difference between --mount and --volume in Docker commands?**  
- `--volume` (or `-v`): Older syntax, simpler but less explicit (e.g., `-v my_volume:/app` or `-v /host:/app`). Combines volume and bind mount options.  
- `--mount`: Newer, more precise syntax with key-value pairs (e.g., `--mount type=volume,source=my_volume,destination=/app` or `--mount type=bind,source=/host,destination=/app`).  
Key difference: `--mount` offers clarity and flexibility (e.g., read-only with `ro`), while `-v` is concise but less granular. Use `--mount` for modern scripts.

48. **How do you list all volumes on your Docker host?**  
To list all volumes, run: `docker volume ls`. This displays volume names and drivers (e.g., local). Add `-f` to filter (e.g., `docker volume ls -f dangling=true` for unused volumes). Use `docker volume inspect <volume_name>` for detailed info like mount points.

49. **What are storage drivers, and how do you choose the right one?**  
Storage drivers manage how Docker handles image layers and container writable layers. Examples:  
- **overlay2**: Default on modern Linux, efficient with layered filesystems.  
- **aufs**: Older, widely compatible but slower.  
- **devicemapper**: Supports direct-lvm, good for production but complex.  
Choose based on:  
- **OS/Filesystem**: `overlay2` needs modern kernels and ext4/xfs.  
- **Performance**: `overlay2` is fast; `devicemapper` suits enterprise.  
- **Compatibility**: Check host system support (e.g., `docker info --format '{{.StorageDriver}}'`).  
`overlay2` is usually the best default.

50. **How can you back up data stored in a Docker volume?**  
To back up a volume:  
- Run a temporary container with the volume mounted: `docker run --rm -v my_volume:/data busybox tar cvf /backup.tar /data`.  
- Copy the backup to the host: `docker cp <container_id>:/backup.tar ./backup.tar`.  
Alternatively, mount the volume to a host path (e.g., `-v /backup:/data`) and back up directly. Restore by reversing the process: extract the tar into a new volume. This ensures data portability and safety.

51. **What is Docker Compose, and how does it differ from Docker?**  
Docker Compose is a tool for defining and managing multi-container Docker applications using a single YAML file (`docker-compose.yml`). It simplifies running multiple containers with predefined configurations. Docker, by contrast, is the core platform for creating and running individual containers via commands like `docker run`. Compose builds on Docker, automating orchestration of services (e.g., app, database) versus Docker’s single-container focus.

52. **What is the structure of a `docker-compose.yml` file?**  
A `docker-compose.yml` file has:  
- `version`: Specifies the Compose file format (e.g., `version: "3.8"`).  
- `services`: Defines containers (e.g., web, db) with settings like image, ports, volumes, and environment.  
- `networks` (optional): Configures custom networks (e.g., frontend).  
- `volumes` (optional): Declares named volumes (e.g., db-data).

Example:
```yaml
version: "3.8"
services:
    web:
        image: nginx
        ports:
            - "80:80"
    db:
        image: mysql
        volumes:
            - db-data:/var/lib/mysql
volumes:
    db-data:
```

53. **How do you start and stop services defined in a Docker Compose file?**  
- **Start**: Run `docker-compose up` in the directory with `docker-compose.yml`. This builds, creates, and starts all services.  
- **Stop**: Run `docker-compose stop` to halt services without removing them (restart with `docker-compose start`).  
- **Stop and Remove**: Use `docker-compose down` to stop and delete containers, networks, and default volumes.

Example: `docker-compose up` starts, `docker-compose stop` pauses.

54. **What is the difference between `docker-compose up` and `docker-compose up -d`?**  
- `docker-compose up`: Starts services and attaches the terminal to their logs, running in the foreground (exit with Ctrl+C).  
- `docker-compose up -d`: Starts services in detached mode, running in the background, freeing your terminal.

Example: `docker-compose up` for debugging, `docker-compose up -d` for production-like setups.

55. **How do you scale a service using Docker Compose?**  
To scale a service, use the `--scale` flag with `docker-compose up`. Example: `docker-compose up -d --scale web=3` starts three instances of the web service. Ensure ports don’t conflict (e.g., use a load balancer or dynamic port mapping). Scaling is limited to single-host setups; for multi-host, use Docker Swarm or Kubernetes.

56. **How do you define environment variables in a Docker Compose file?**  
Define environment variables in `docker-compose.yml` under a service with:  
- List format: `environment: - NAME=value` (e.g., `- DB_HOST=db`).  
- Key-value format: `environment: { NAME: value }` (e.g., `DB_HOST: db`).  
- File: Use `env_file: .env` to load from a file (e.g., `DB_PASS=secret`).

Example:
```yaml
services:
    web:
        image: nginx
        environment:
            - ENV=production
```

57. **What is the purpose of the `depends_on` keyword in Docker Compose?**  
The `depends_on` keyword specifies service startup order. Example:
```yaml
services:
    web:
        image: nginx
        depends_on:
            - db
    db:
        image: mysql
```
Here, `db` starts before `web`. It ensures dependencies are running but doesn’t guarantee they’re fully ready (e.g., a database might still be initializing). Use health checks for readiness.

58. **How do you override settings in a `docker-compose.yml` file?**  
Override settings using an additional file (e.g., `docker-compose.override.yml`) or a custom file with `-f`. Example:  
- Default `docker-compose.yml` + `docker-compose.override.yml` are merged automatically with `docker-compose up`.  
- Specify files: `docker-compose -f docker-compose.yml -f docker-compose.dev.yml up` combines them, with later files overriding earlier ones.

This separates base config from environment-specific settings (e.g., dev vs. prod).

59. **How do you link services in Docker Compose?**  
In Compose, services on the same default network (created automatically) are linked via DNS. Example:
```yaml
services:
    web:
        image: nginx
    db:
        image: mysql
```
`web` can reach `db` as `http://db`. No explicit `--link` is needed (unlike plain Docker), as Compose handles name resolution. Use networks for custom isolation if required.

60. **What happens when you run `docker-compose down`?**  
Running `docker-compose down` stops and removes all services (containers), networks, and volumes defined in `docker-compose.yml` (unless external). Steps:  
- Stops containers (`docker stop`).  
- Removes containers (`docker rm`).  
- Deletes the default network.

Add `--volumes` (e.g., `docker-compose down --volumes`) to remove named volumes too. Use it to clean up fully after testing.

61. **What is Docker Swarm, and how does it compare to Kubernetes?**  
Docker Swarm is Docker’s native orchestration tool for managing a cluster of Docker nodes, enabling deployment, scaling, and load balancing of containerized applications. Compared to Kubernetes:  
- **Simplicity**: Swarm is easier to set up and use, integrated with Docker CLI; Kubernetes has a steeper learning curve.  
- **Scale**: Kubernetes excels at large-scale, complex deployments; Swarm is lighter, better for smaller setups.  
- **Features**: Kubernetes offers more advanced features (e.g., auto-scaling, self-healing); Swarm is simpler but less extensible.  
- **Ecosystem**: Kubernetes has a broader community and tools; Swarm leverages Docker’s ecosystem.  
Use Swarm for straightforward, Docker-centric projects; Kubernetes for enterprise-scale needs.

62. **How do you initialize a Docker Swarm cluster?**  
To initialize a Swarm cluster, run: `docker swarm init` on the first node (becomes the manager). It generates a join token and outputs a command like:  
`docker swarm join --token <token> <manager-ip>:2377`.  
Run this on other nodes to join as workers. Add `--advertise-addr <ip>` if the manager has multiple IPs (e.g., `docker swarm init --advertise-addr 192.168.1.10`). Verify with `docker node ls` on the manager.

63. **What is the difference between a manager node and a worker node in Docker Swarm?**  
- **Manager Node**: Runs the Swarm control plane, managing the cluster (e.g., scheduling tasks, maintaining state). It can also run workloads. Use `docker swarm init` or `docker node promote` to create one.  
- **Worker Node**: Executes tasks (containers) assigned by managers but doesn’t manage the cluster. Joins via `docker swarm join`.  
Managers handle orchestration; workers focus on execution. Multiple managers ensure high availability.

64. **How do you deploy a service in Docker Swarm?**  
To deploy a service, use: `docker service create`. Example:  
`docker service create --name web --replicas 3 -p 80:80 nginx`  
deploys an Nginx service with 3 replicas, mapping port 80. Swarm schedules these across nodes. Check status with `docker service ls` and `docker service ps web`. Services are Swarm’s unit of deployment, unlike standalone containers.

65. **What is a stack in Docker Swarm, and how do you deploy one?**  
A stack is a group of related services defined in a Compose-like YAML file, deployed as a unit in Swarm. Example `stack.yml`:  
```yaml
version: "3.8"
services:
    web:
        image: nginx
        ports:
            - "80:80"
    db:
        image: mysql
```
Deploy it with: `docker stack deploy -c stack.yml my_stack`. Check with `docker stack ls` or `docker stack ps my_stack`. Stacks simplify multi-service management.

66. **How does Docker Swarm handle load balancing?**  
Swarm provides built-in load balancing:  
- **Ingress Load Balancing**: Routes traffic to service replicas via a published port (e.g., `-p 80:80`). Swarm’s routing mesh distributes requests across all nodes.  
- **Internal**: Uses DNS round-robin for service name resolution within the cluster.  
Example: A web service with 3 replicas on port 80 gets traffic evenly spread. External balancers (e.g., HAProxy) can enhance this.

67. **How do you scale a service in Docker Swarm?**  
To scale a service, use: `docker service scale <service_name>=<replicas>`. Example:  
`docker service scale web=5`  
adjusts the web service to 5 replicas. Alternatively, update with `docker service update --replicas 5 web`. Swarm redistributes tasks across nodes. Check with `docker service ps web`. Scaling is instant and maintains load balancing.

68. **What is the purpose of the docker service command?**  
The `docker service` command manages services in Swarm, abstracting tasks across nodes. Key uses:  
- `docker service create`: Deploys a new service (e.g., `docker service create --name web nginx`).  
- `docker service ls`: Lists running services.  
- `docker service ps`: Shows tasks (containers) of a service.  
- `docker service update`: Modifies a service (e.g., replicas, image).  
It’s for Swarm orchestration, unlike `docker run` for single containers.

69. **How do you update a running service in Docker Swarm without downtime?**  
To update a service without downtime, use `docker service update` with rolling updates. Example:  
`docker service update --image nginx:1.19 --update-delay 10s web`  
updates the web service to Nginx 1.19, replacing containers one-by-one with a 10-second delay. Swarm ensures availability by keeping old instances running until new ones are healthy. Add `--update-parallelism 2` to update two at a time. Verify with `docker service ps web`.

70. **How do you remove a node from a Docker Swarm cluster?**  
To remove a node:  
- **Worker**: On the worker, run `docker swarm leave`. On a manager, demote it first with `docker node demote <node_id>`, then `docker swarm leave`.  
- **Manager Removal**: On a manager, run `docker swarm leave --force` (if it’s the last manager, the swarm dissolves).  
From another manager, remove an offline node with `docker node rm <node_id>`. Check with `docker node ls`. This maintains cluster integrity.

71. **What are some best practices for securing Docker containers?**
    - Run containers as non-root users to limit privilege escalation.
    - Use minimal base images (e.g., alpine) to reduce attack surface.
    - Scan images for vulnerabilities (e.g., docker scan).
    - Limit resource usage with `--cpus` and `-m` flags.
    - Avoid `--privileged` mode unless necessary.
    - Use Docker secrets for sensitive data, not environment variables.
    - Restrict network access with custom networks and firewall rules.
    - Keep Docker and images updated to patch security flaws.
    - These practices enhance isolation and reduce risks.

72. **How do you run a Docker container as a non-root user?**
    To run a container as a non-root user:
    - In the Dockerfile, add: `USER <username>` after creating a user with `RUN useradd -m myuser`.
    - Example:
      ```dockerfile
      FROM ubuntu
      RUN useradd -m appuser
      USER appuser
      CMD ["bash"]
      ```
    - Build and run: `docker build -t myimage .` and `docker run myimage`.
    - This restricts container processes to the specified user’s permissions, reducing damage from breaches.

73. **What is the purpose of Docker Content Trust (DCT)?**
    Docker Content Trust (DCT) ensures image integrity and authenticity by enforcing digital signatures. When enabled (via `export DOCKER_CONTENT_TRUST=1`), Docker only pulls or runs images signed by trusted publishers. It prevents man-in-the-middle attacks or tampered images, verifying the source (e.g., Docker Hub). Use it for secure supply chains in production.

74. **How do you scan a Docker image for vulnerabilities?**
    To scan an image, use: `docker scan <image_name>`. Example: `docker scan nginx:latest` checks for known CVEs using Snyk’s database (requires a Docker Hub account). Alternatively, use tools like Trivy (`trivy image nginx:latest`) or Clair for deeper scans. Integrate scanning into CI/CD to catch issues early.

75. **What are the risks of running Docker with the --privileged flag?**
    The `--privileged` flag gives a container unrestricted access to the host’s resources (e.g., devices, kernel capabilities), bypassing security mechanisms like namespaces and cgroups. Risks:
    - Full host control, enabling root-level attacks.
    - Access to all devices (e.g., /dev).
    - Potential to escape isolation and harm the host.
    - Use it only for trusted, specific needs (e.g., running Docker-in-Docker), not general apps.

76. **How do you configure resource limits to prevent a container from overloading the host?**
    Set limits with `docker run`:
    - CPU: `--cpus="1.5"` (e.g., 1.5 cores) or `--cpu-shares=512` (relative weight).
    - Memory: `-m 512m` (e.g., 512 MB).
    - Example: `docker run -d --cpus="1" -m 256m nginx`. This caps resource usage, preventing a container from starving the host or other containers. Verify with `docker stats`.

77. **What is the role of namespaces and cgroups in Docker security?**
    - Namespaces: Isolate container processes (e.g., PID, network, mount), ensuring they see only their own resources, not the host’s. Example: A container’s PID 1 isn’t the host’s PID 1.
    - Cgroups: Limit resource usage (CPU, memory, I/O), preventing overuse or denial-of-service. Example: `--cpus="1"` enforces a CPU cap.
    - Together, they enforce isolation and containment, key to Docker’s security model.

78. **How do you restrict network traffic between containers?**
    Restrict traffic by:
    - Using custom networks: `docker network create --driver bridge my_net`, then attach specific containers (e.g., `docker run --network my_net`).
    - Setting `--icc=false` on the daemon (disables inter-container communication on the default bridge).
    - Adding firewall rules (e.g., iptables) to block ports or IPs.
    - Using `--network none` for full isolation.
    - This limits exposure and prevents unauthorized access.

79. **What is a Docker secret, and how do you use it?**
    A Docker secret is a secure way to store and manage sensitive data (e.g., passwords, API keys) in Swarm. Create it with: `echo "mysecret" | docker secret create my_secret -`. Use it in a service:
    ```yaml
    services:
      app:
         image: myapp
         secrets:
            - my_secret
    secrets:
      my_secret:
         external: true
    ```
    - The secret is mounted at `/run/secrets/my_secret` in the container, accessible only to authorized services, not exposed in logs or env vars.

80. **How can you prevent a container from accessing the host filesystem?**
    - Avoid bind mounts to sensitive host paths (e.g., `-v /:/host`).
    - Use volumes instead (`-v my_volume:/app`), which are Docker-managed.
    - Run as non-root with `USER` in the Dockerfile.
    - Drop capabilities (e.g., `docker run --cap-drop=ALL --cap-add=NET_BIND_SERVICE`).
    - Use `--read-only` (e.g., `docker run --read-only nginx`) to make the filesystem read-only.
    - Enable user namespaces in the daemon for additional isolation.
    - These steps minimize host access risks.

81. **What is the difference between Docker and Podman?**
    - Architecture: Docker uses a daemon (dockerd) to manage containers; Podman is daemonless, running containers as individual processes.
    - Root: Docker often requires root privileges; Podman supports rootless containers natively.
    - Compatibility: Podman is a drop-in replacement for Docker CLI (e.g., `podman run`); both use OCI standards.
    - Scope: Docker includes Swarm and Compose; Podman focuses on containers, integrating with tools like Buildah.
    - Use Docker for ecosystem simplicity, Podman for security and lightweight setups.

82. **How does Docker handle logging, and what are the available logging drivers?**
    Docker captures container stdout/stderr and routes it via logging drivers. Default is `json-file` (logs stored as JSON in `/var/lib/docker/containers`). Available drivers:
    - `json-file`: Stores logs on disk.
    - `syslog`: Sends to system syslog.
    - `journald`: Uses systemd journal.
    - `none`: Disables logging.
    - `fluentd`, `gelf`: For external log systems.
    - Set with `--log-driver` (e.g., `docker run --log-driver=syslog nginx`) or daemon config. Use `docker logs <container>` to view.

83. **What is the purpose of the Docker Registry, and how do you set up a private one?**
    A Docker Registry stores and distributes Docker images (e.g., Docker Hub is public). A private registry keeps images internal. To set up:
    - Run: `docker run -d -p 5000:5000 --name registry registry:2`.
    - Tag an image: `docker tag myimage localhost:5000/myimage`.
    - Push: `docker push localhost:5000/myimage`.
    - Pull: `docker pull localhost:5000/myimage`.
    - Add `--restart=always` and secure with TLS/passwords for production.

84. **How do you push an image to Docker Hub?**
    - Tag the image: `docker tag myimage username/myimage:tag` (e.g., `johndoe/myapp:1.0`).
    - Log in: `docker login` (enter Docker Hub credentials).
    - Push: `docker push username/myimage:tag`.
    - Example: `docker push johndoe/myapp:1.0`. Ensure the repository exists on Docker Hub or create it first.

85. **What are Docker plugins, and how do you use them?**
    Docker plugins extend functionality (e.g., storage, networking). Examples: volume plugins (e.g., REX-Ray), network plugins (e.g., Weave). To use:
    - Install: `docker plugin install <plugin_name>` (e.g., `docker plugin install rexray/s3fs`).
    - Enable: `docker plugin ls` checks status; `docker plugin enable <plugin>`.
    - Use: Specify in commands (e.g., `docker volume create -d rexray/s3fs myvol`).
    - Plugins enhance Docker’s capabilities beyond defaults.

86. **How do you debug a Docker container that’s failing to start?**
    - Check logs: `docker logs <container_id>` for errors.
    - Inspect: `docker inspect <container_id>` for config or exit codes.
    - Run interactively: `docker run -it <image> bash` to test manually.
    - Verify ports/volumes: Ensure no conflicts (e.g., `docker ps -a`).
    - Check resources: `docker stats` or host logs for CPU/memory issues.
    - Review Dockerfile: Look for missing deps or bad CMD.
    - Start with logs, then escalate to interactive debugging.

87. **What is the difference between a Docker image layer and a container layer?**
    - Image Layer: Read-only, cached layers from Dockerfile instructions (e.g., `RUN apt-get install`). Stacked to form the image, immutable.
    - Container Layer: A writable, temporary layer created when a container runs. Stores runtime changes (e.g., new files); deleted when the container is removed unless committed.
    - Image layers are the base; the container layer adds runtime state.

88. **How does Docker handle multi-architecture images (e.g., ARM vs. x86)?**
    Docker supports multi-architecture images via manifest lists. Build with `docker buildx`:
    - Enable Buildx: `docker buildx create --use`.
    - Build: `docker buildx build --platform linux/amd64,linux/arm64 -t myimage:tag --push .`.
    - Push to registry: The manifest list indexes architecture-specific images.
    - When pulled (e.g., `docker pull myimage:tag`), Docker selects the correct variant (e.g., ARM or x86) for the host.

89. **What is the purpose of the HEALTHCHECK instruction in a Dockerfile?**
    The `HEALTHCHECK` instruction defines how Docker monitors a container’s health. Example:
    ```dockerfile
    HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost/ || exit 1
    ```
    - It runs a command periodically; if it fails (non-zero exit), the container is marked unhealthy. Use it for services (e.g., Swarm) to auto-restart or alert on failures. View with `docker inspect`.

90. **How do you roll back a service update in Docker Swarm?**
    To roll back a service update:
    - Run: `docker service rollback <service_name>` (e.g., `docker service rollback web`).
    - Swarm reverts to the previous service definition (image, replicas, etc.).
    - Example: After `docker service update --image nginx:1.19 web` fails, `docker service rollback web` restores the prior state. Use `--update-failure-action rollback` in `docker service create` to automate this on failure.

91. **How would you containerize a simple web application (e.g., Node.js app)?**

To containerize a Node.js app:

Create a Dockerfile:
```dockerfile
FROM node:16
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["node", "index.js"]
```

Build the image:
```sh
docker build -t mynodeapp:1.0 .
```

Run the container:
```sh
docker run -d -p 3000:3000 mynodeapp:1.0
```

Test: Access `http://localhost:3000`.

Add `.dockerignore` (e.g., `node_modules`) to optimize. This ensures the app and dependencies are portable and isolated.

92. **Write a Dockerfile for a Python application that uses Flask.**

Here’s a Dockerfile for a Flask app:
```dockerfile
FROM python:3.9-slim
WORKDIR /app
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
ENV FLASK_APP=app.py
EXPOSE 5000
CMD ["flask", "run", "--host=0.0.0.0"]
```

Assumes `requirements.txt` lists Flask and `app.py` is the entry point. Build with:
```sh
docker build -t myflaskapp:1.0 .
```

Run with:
```sh
docker run -d -p 5000:5000 myflaskapp:1.0
```

93. **How would you troubleshoot a container that exits immediately after starting?**

- Check exit code: `docker ps -a` (e.g., 0 = normal, 1 = error).
- View logs: `docker logs <container_id>` for error messages.
- Inspect: `docker inspect <container_id>` for config issues (e.g., ports, mounts).
- Run interactively: `docker run -it <image> /bin/sh` to test commands.
- Verify CMD/ENTRYPOINT: Ensure they’re valid in the Dockerfile.
- Check resources: Confirm host has enough CPU/memory.

Start with logs, then debug interactively.

94. **How do you migrate an existing application to Docker?**

- Analyze: Identify app dependencies (e.g., runtime, libraries).
- Dockerfile: Write a Dockerfile (e.g., `FROM node`, install deps, copy code).
- Build/Test: `docker build -t myapp .` and `docker run` locally.
- Persist Data: Use volumes for databases/files (e.g., `-v data:/app/data`).
- Network: Map ports (e.g., `-p 80:80`) or use Compose for multi-service apps.
- Optimize: Minimize image size, secure with non-root user.
- Deploy: Push to a registry and run on target environment.

Test thoroughly at each step.

95. **Design a Docker Compose file for a microservices architecture with a frontend, backend, and database.**

```yaml
version: "3.8"
services:
    frontend:
        image: node:16
        working_dir: /app
        command: npm start
        ports:
            - "3000:3000"
        volumes:
            - ./frontend:/app
    backend:
        image: python:3.9
        working_dir: /app
        command: python main.py
        ports:
            - "5000:5000"
        volumes:
            - ./backend:/app
        depends_on:
            - db
    db:
        image: postgres:13
        environment:
            POSTGRES_USER: user
            POSTGRES_PASSWORD: pass
            POSTGRES_DB: mydb
        volumes:
            - db-data:/var/lib/postgresql/data
volumes:
    db-data:
```

Run with `docker-compose up -d`. Frontend (Node.js) on 3000, backend (Python) on 5000, and Postgres persist data.

96. **How would you set up a CI/CD pipeline using Docker?**

- Code: Store app in a Git repo (e.g., GitHub).
- Build: Use a CI tool (e.g., GitHub Actions):
```yaml
jobs:
    build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: docker build -t myapp:${{ github.sha }} .
        - run: docker push myapp:${{ github.sha }}
```

- Test: Run `docker run` in CI to test.
- Deploy: Push to registry (e.g., Docker Hub), pull on server, and run (e.g., `docker-compose up -d`) or use Swarm/Kubernetes.
- Automate: Trigger on push, use secrets for credentials.

Ensures consistent builds and deployments.

97. **What steps would you take if a Docker container is consuming too much memory?**

- Check usage: `docker stats` to confirm memory hogging.
- Inspect config: `docker inspect <container_id>` for limits.
- Set limits: Restart with `-m` (e.g., `docker run -m 512m`).
- Debug app: `docker exec -it <container_id> bash` to check processes (e.g., `top`).
- Optimize image: Reduce bloat in Dockerfile (e.g., multi-stage build).
- Restart policy: Add `--restart=on-failure` to recover crashes.

Start with limits, then investigate the app.

98. **How do you ensure zero downtime during a Docker container update?**

- Use Swarm: `docker service update --image newimage:tag --update-delay 10s web` for rolling updates.
- Multiple containers: Run replicas (e.g., `--replicas 2`), update one-by-one.
- Health checks: Add `HEALTHCHECK` in Dockerfile; Swarm waits for healthy status.
- Load balancer: Front with Nginx/HAProxy, redirect traffic during updates.
- Blue-green: Deploy new version, test, then switch (e.g., via port mapping).

Rolling updates in Swarm are simplest for zero downtime.

99. **How would you handle a situation where a Docker image fails to build?**

- Check logs: Read `docker build` output for errors (e.g., missing deps).
- Verify Dockerfile: Ensure valid syntax, correct base image, working RUN commands.
- Test steps: Run `docker run -it <base_image> bash` to debug each instruction.
- Network: Confirm internet access for downloads (e.g., `apt-get`).
- Cache: Use `--no-cache` (e.g., `docker build --no-cache .`) to rule out stale layers.
- Dependencies: Update `requirements.txt` or similar.

Iterate from error line upward.

100. **Explain how you would deploy a multi-container application to production using Docker Swarm.**

- Prep: Write a `docker-compose.yml` with services (e.g., web, db).
- Init Swarm: `docker swarm init` on manager node; join workers.
- Convert to Stack: Save as `stack.yml`, deploy with `docker stack deploy -c stack.yml prod`.
- Scale: `docker service scale prod_web=3` for redundancy.
- Network: Use overlay network (e.g., `--network mynet`).
- Update: `docker service update --image newtag prod_web` for zero-downtime updates.
- Monitor: `docker service ls` and `docker service ps prod_web`.

Swarm ensures HA and load balancing.