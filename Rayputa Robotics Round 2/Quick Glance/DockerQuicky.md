```markdown
# Docker Prep Guide: 100 Questions Answered

## Docker Basics

### What’s Docker vs. a VM?
- **Docker**: Lightweight containers, shares host OS, fast.  
- **VM**: Full OS emulation, heavy, slower.

### Key Docker architecture components?
- **Daemon**: Manages objects.  
- **Client**: CLI interface.  
- **Images**: Templates.  
- **Containers**: Running instances.  
- **Registry**: Image store.

### Container vs. image?
- **Container**: Running instance.  
- **Image**: Static blueprint.

### Problem Docker solves?
- Consistency across dev/test/prod—no “works on my machine” issues.

### Role of Dockerfile?
- Script to build images—defines base, dependencies, and runtime.

### Difference: `docker run` vs. `start` vs. `exec`?
- **run**: New container + start.  
- **start**: Restart stopped container.  
- **exec**: Run command in running container.

### What’s containerization, Docker’s take?
- App + dependencies in one unit. Docker uses namespaces, cgroups, union FS.

### Docker advantages over virtualization?
- Lightweight, fast, efficient, portable, scalable.

### Default storage driver, why it matters?
- **overlay2** (Linux default)—impacts performance, storage, compatibility.

### Check Docker version?
```bash
docker --version
docker version  # Full client/server info
```

---

## Docker Images

### Create image from Dockerfile?
```bash
docker build -t myapp:1.0 .
```

### Common Dockerfile instructions?
- `FROM`, `RUN`, `CMD`, `ENTRYPOINT`, `COPY`, `ADD`, `WORKDIR`, `EXPOSE`, `ENV`.

### CMD vs. ENTRYPOINT?
- **CMD**: Default args, overridable.  
- **ENTRYPOINT**: Fixed executable.

### Reduce image size?
- Use small base (e.g., alpine), chain `RUN`, clean up, multi-stage builds.

### Multi-stage build, why use it?
- Separate build/runtime—smaller images.
```dockerfile
FROM golang AS builder
RUN go build -o app
FROM alpine
COPY --from=builder /app .
```

### List all images?
```bash
docker images
docker image ls
```

### Purpose of `.dockerignore`?
- Excludes files from build context—faster, cleaner.

### Tag an image, why?
```bash
docker tag myapp myapp:1.0
```
- For versioning and clarity.

### What happens in `docker build`?
- Reads Dockerfile, builds layers, caches, tags image.

### Remove unused images?
```bash
docker image rm <id>
docker image prune -a
```

---

## Docker Containers

### Start container detached?
```bash
docker run -d nginx
```

### View container logs?
```bash
docker logs <id>
docker logs -f <id>  # Real-time logs
```

### Stop vs. kill?
- **stop**: Graceful (SIGTERM).  
- **kill**: Instant (SIGKILL).

### Inspect running container?
```bash
docker inspect <id>
```

### Run with specific name?
```bash
docker run --name myapp nginx
```

### Data when container stops?
- In-memory lost, FS persists till deleted—use volumes.

### Remove container: `rm` vs. `rm -f`?
- **rm**: Stopped only.  
- **rm -f**: Stops + removes.

### Limit CPU/memory?
```bash
docker run --cpus="1" -m 512m nginx
```

### Purpose of `--restart`?
- Auto-restart policy—e.g., `always`, `on-failure:5`.

### Attach to container terminal?
```bash
docker attach <id>
docker exec -it <id> bash
```

---

## Docker Networking

### Types of Docker networks?
- **Bridge** (default), **Host** (no isolation), **Overlay** (multi-host), **None**, **Macvlan**.

### Default bridge network?
- Private IPs, NAT to host, port mapping needed.

### Create custom network?
```bash
docker network create mynet
```

### Host vs. bridge network?
- **Host**: Uses host’s stack, no isolation.  
- **Bridge**: Isolated, NAT’d.

### Container communication?
- Same network: IP or name (custom nets).  
- Port mapping for external.

### Purpose of `--link`, why deprecated?
- Links containers on default bridge—outdated, use custom networks.

### Expose a port?
```bash
docker run -p 8080:80 nginx
```

### EXPOSE vs. `-p`?
- **EXPOSE**: Documents intent.  
- **-p**: Actually binds port.

### Docker DNS resolution?
- Custom networks auto-resolve names—default bridge doesn’t.

### Overlay network, when to use?
- Multi-host communication in Swarm—e.g., distributed apps.

---

## Docker Storage

### Volumes vs. bind mounts?
- **Volumes**: Docker-managed, portable.  
- **Binds**: Host path, precise.

### Create/manage volumes?
```bash
docker volume create myvol
docker volume ls
docker volume rm myvol
docker volume prune
```

### Volume when container deleted?
- Stays—lives in `/var/lib/docker/volumes`.

### Mount local dir?
```bash
docker run -v /host:/container nginx
```

### tmpfs mount, when?
- RAM-based, temp data.
```bash
docker run --tmpfs /tmp
```

### Persistent storage?
- Volumes (`-v myvol:/data`) or binds (`-v /host:/data`).

### `--mount` vs. `--volume`?
- **--mount**: Precise, modern.  
- **-v**: Simple, older.

### List volumes?
```bash
docker volume ls
```

### Storage drivers, how to pick?
- **overlay2** (fast, default), **devicemapper** (enterprise)—match OS/workload.

### Backup volume data?
```bash
docker run -v myvol:/data busybox tar cvf backup.tar /data
```

---

## Docker Compose

### Docker Compose vs. Docker?
- **Compose**: Multi-container orchestration.  
- **Docker**: Single-container runtime.

### Structure of `docker-compose.yml`?
```yaml
version: "3.8"
services:
    web:
        image: nginx
```

### Start/stop Compose services?
```bash
docker-compose up
docker-compose stop
docker-compose down
```

### `up` vs. `up -d`?
- **up**: Foreground.  
- **up -d**: Detached.

### Scale a service?
```bash
docker-compose up -d --scale web=3
```

### Define env vars?
```yaml
environment:
    - KEY=value
```

### Purpose of `depends_on`?
- Sets start order—e.g., `depends_on: [db]`.

### Override Compose settings?
```bash
docker-compose -f base.yml -f override.yml up
```

### Link services in Compose?
- Auto-linked via DNS on default network.

### What’s `docker-compose down`?
- Stops, removes containers/networks—add `--volumes` for full cleanup.

---

## Docker Swarm

### Swarm vs. Kubernetes?
- **Swarm**: Simple, Docker-native.  
- **K8s**: Complex, feature-rich.

### Initialize Swarm?
```bash
docker swarm init
```

### Manager vs. worker node?
- **Manager**: Runs cluster.  
- **Worker**: Runs tasks.

### Deploy Swarm service?
```bash
docker service create --name web -p 80:80 nginx
```

### Stack in Swarm?
- Multi-service YAML.
```bash
docker stack deploy -c stack.yml mystack
```

### Swarm load balancing?
- Built-in routing mesh spreads traffic.

### Scale in Swarm?
```bash
docker service scale web=5
```

### Purpose of `docker service`?
- Manages Swarm services—create, list, update.

### Update service, no downtime?
```bash
docker service update --image nginx:1.19 --update-delay 10s web
```

### Remove Swarm node?
```bash
docker swarm leave  # Worker
docker node rm <id>  # Manager
```

---

## Docker Security

### Secure container best practices?
- Non-root, minimal images, scan, limit resources, secrets.

### Run as non-root?
```dockerfile
RUN useradd -m appuser
USER appuser
```

### Docker Content Trust?
- Ensures signed images.
```bash
DOCKER_CONTENT_TRUST=1
```

### Scan image for vulnerabilities?
```bash
docker scan nginx:latest
```

### Risks of `--privileged`?
- Full host access—huge security hole.

### Resource limits?
```bash
docker run --cpus="1" -m 256m
```

### Namespaces/cgroups role?
- **Namespaces**: Isolate.  
- **Cgroups**: Limit resources.

### Restrict container traffic?
- Custom networks, `--icc=false`, firewall rules.

### Docker secret?
- Secure data.
```bash
docker secret create mysecret data
```

### Prevent host FS access?
- No binds to root, read-only, drop caps.

---

## Advanced Docker

### Docker vs. Podman?
- **Docker**: Daemon-based.  
- **Podman**: Daemonless, rootless.

### Docker logging?
- Captures stdout—drivers: `json-file`, `syslog`, `none`.

### Docker Registry, private setup?
- Stores images.
```bash
docker run -d -p 5000:5000 registry:2
```

### Push to Docker Hub?
```bash
docker push username/myapp:tag
```

### Docker plugins?
- Extend features.
```bash
docker plugin install rexray
```

### Debug failing container?
- Logs, inspect, run interactively.

### Image vs. container layer?
- **Image**: Read-only stack.  
- **Container**: Writable top layer.

### Multi-arch images?
```bash
docker buildx build --platform linux/amd64,linux/arm64
```

### Purpose of HEALTHCHECK?
- Monitors health.
```dockerfile
HEALTHCHECK CMD curl -f http://localhost/
```

### Rollback Swarm service?
```bash
docker service rollback web
```

---

## Practical/Scenario-Based

### Containerize Node.js app?
```dockerfile
FROM node:16
COPY . /app
RUN npm install
CMD ["node", "index.js"]
```

### Dockerfile for Flask app?
```dockerfile
FROM python:3.9-slim
COPY requirements.txt .
RUN pip install -r requirements.txt
COPY . .
CMD ["flask", "run", "--host=0.0.0.0"]
```

### Troubleshoot container exiting?
- Logs, exit code, interactive run—check CMD.

### Migrate app to Docker?
- Dockerfile, build/test, add volumes, deploy.

### Compose for microservices?
```yaml
services:
    frontend:
        image: node:16
        ports: ["3000:3000"]
    backend:
        image: python:3.9
        ports: ["5000:5000"]
    db:
        image: postgres:13
        volumes: [db-data:/var/lib/postgresql/data]
volumes:
    db-data:
```

### CI/CD with Docker?
- Build/push in CI (e.g., GitHub Actions), deploy via Compose/Swarm.

### Container overuses memory?
- Check `docker stats`, cap with `-m`, debug app.

### Zero-downtime update?
- Swarm rolling update—`docker service update`.

### Fix failed image build?
- Check logs, test steps, `--no-cache`.

### Deploy multi-container app with Swarm?
- Stack file, `docker stack deploy`, scale, monitor.
```