
---

### Kubernetes Questions

**1. Use `kubectl` to list all pods in the `default` namespace and filter for those in a `Running` state.**  
```bash
kubectl get pods -n default --field-selector=status.phase=Running
```
This lists only pods in the `Running` state in the `default` namespace.

---

**2. How would you check the logs of a specific container inside a multi-container pod using `kubectl`?**  
```bash
kubectl logs <pod-name> -n <namespace> -c <container-name>
```
Example:  
```bash
kubectl logs my-pod -n default -c my-container
```
Use `-c` to specify the container name within the pod.

---

**3. Write a command to scale a deployment named `web-app` to 5 replicas.**  
```bash
kubectl scale deployment web-app --replicas=5 -n <namespace>
```
Example (default namespace):  
```bash
kubectl scale deployment web-app --replicas=5
```

---

**4. A pod is stuck in `Pending` state. What `kubectl` commands would you run to troubleshoot it?**  
1. Check pod status:  
   ```bash
   kubectl get pod <pod-name> -n <namespace>
   ```
2. Describe the pod for detailed info:  
   ```bash
   kubectl describe pod <pod-name> -n <namespace>
   ```
   Look for events like "Insufficient CPU/memory" or "No nodes available."
3. Check cluster nodes:  
   ```bash
   kubectl get nodes
   ```
4. Check resource quotas:  
   ```bash
   kubectl describe quota -n <namespace>
   ```
5. Check scheduler logs (if needed):  
   ```bash
   kubectl logs -n kube-system -l component=kube-scheduler
   ```

---

**5. Create a YAML file for a pod with a single container running `nginx` and expose port 80.**  
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx-pod
  namespace: default
spec:
  containers:
  - name: nginx
    image: nginx:latest
    ports:
    - containerPort: 80
      protocol: TCP
```
Apply it with:  
```bash
kubectl apply -f nginx-pod.yaml
```

---

**6. How do you use `kubectl` to check the events related to a specific pod?**  
```bash
kubectl describe pod <pod-name> -n <namespace>
```
Scroll to the "Events" section at the bottom for pod-specific events. Alternatively, filter all events:  
```bash
kubectl get events -n <namespace> --field-selector involvedObject.name=<pod-name>
```

---

**7. Write a command to delete all pods in a namespace that are in a `CrashLoopBackOff` state.**  
```bash
kubectl delete pod -n <namespace> --field-selector=status.phase!=Running,status.phase!=Succeeded,status.phase!=Failed --force
```
For a more precise approach targeting `CrashLoopBackOff`:  
```bash
kubectl get pods -n <namespace> -o jsonpath='{range .items[*]}{.metadata.name}{"\t"}{.status.containerStatuses[0].state.waiting.reason}{"\n"}{end}' | grep CrashLoopBackOff | awk '{print $1}' | xargs kubectl delete pod -n <namespace>
```

---

**8. How would you use `kubectl` to drain a node for maintenance without disrupting running pods?**  
```bash
kubectl drain <node-name> --ignore-daemonsets --delete-emptydir-data
```
- `--ignore-daemonsets`: Skips DaemonSet-managed pods.
- `--delete-emptydir-data`: Deletes pods using emptyDir volumes.  
After maintenance, uncordon the node:  
```bash
kubectl uncordon <node-name>
```

---

**9. Create a YAML file for a ConfigMap that stores a key-value pair (`env=production`).**  
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
  namespace: default
data:
  env: "production"
```
Apply it with:  
```bash
kubectl apply -f configmap.yaml
```

---

**10. Write a command to exec into a pod named `app-pod` and check its running processes.**  
```bash
kubectl exec -it app-pod -n <namespace> -- /bin/sh -c "ps aux"
```
If the container uses `/bin/bash`:  
```bash
kubectl exec -it app-pod -n <namespace> -- /bin/bash -c "ps aux"
```
This opens an interactive shell and lists running processes.

---

---

**11. How do you use `kubectl` to roll back a deployment to its previous revision?**  
```bash
kubectl rollout undo deployment <deployment-name> -n <namespace>
```
Example:  
```bash
kubectl rollout undo deployment web-app -n default
```
To roll back to a specific revision, use `--to-revision`:  
```bash
kubectl rollout undo deployment web-app --to-revision=2 -n default
```
Check revision history with:  
```bash
kubectl rollout history deployment web-app -n default
```

---

**12. A pod isn’t starting due to an `ImagePullBackOff` error. What steps would you take to debug it live?**  
1. Check pod status:  
   ```bash
   kubectl get pod <pod-name> -n <namespace>
   ```
2. Describe the pod for error details:  
   ```bash
   kubectl describe pod <pod-name> -n <namespace>
   ```
   Look for events like "Failed to pull image" (e.g., wrong image name, tag, or registry access).
3. Verify image name and tag:  
   - Confirm the image exists:  
     ```bash
     docker pull <image-name:tag>
     ```
     Or use `kubectl run` to test:  
     ```bash
     kubectl run test --image=<image-name:tag> --restart=Never -n <namespace>
     ```
4. Check registry access:  
   - If private, ensure `imagePullSecrets` is set in the pod spec and the secret exists:  
     ```bash
     kubectl get secret <secret-name> -n <namespace>
     ```
5. Fix: Correct the image name or add the secret, then recreate the pod.

---

**13. Write a YAML file for a service that exposes a deployment on port 8080 as a `ClusterIP`.**  
```yaml
apiVersion: v1
kind: Service
metadata:
  name: web-service
  namespace: default
spec:
  selector:
    app: web-app  # Matches deployment labels
  ports:
  - protocol: TCP
    port: 8080
    targetPort: 8080
  type: ClusterIP
```
Apply it with:  
```bash
kubectl apply -f service.yaml
```

---

**14. How would you use `kubectl` to check resource usage (CPU/memory) for all pods in a namespace?**  
```bash
kubectl top pods -n <namespace>
```
Example:  
```bash
kubectl top pods -n default
```
Requires Metrics Server to be installed in the cluster.

---

**15. Create a YAML file for a PersistentVolumeClaim requesting 10Gi of storage.**  
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-pvc
  namespace: default
spec:
  accessModes:
  - ReadWriteOnce
  resources:
    requests:
      storage: 10Gi
  storageClassName: standard  # Adjust based on your cluster
```
Apply it with:  
```bash
kubectl apply -f pvc.yaml
```

---

**16. Write a command to taint a node so that no pods can schedule on it unless they tolerate the taint.**  
```bash
kubectl taint nodes <node-name> key=value:NoSchedule
```
Example:  
```bash
kubectl taint nodes node-1 maintenance=planned:NoSchedule
```
Pods without a matching toleration (`tolerations: key: "maintenance", value: "planned", effect: "NoSchedule"`) won’t schedule.

---

**17. How do you use `kubectl` to describe a service and check its endpoints?**  
```bash
kubectl describe service <service-name> -n <namespace>
```
Look for the "Endpoints" field in the output. Alternatively, list endpoints directly:  
```bash
kubectl get endpoints <service-name> -n <namespace>
```

---

**18. A deployment isn’t updating after a change. What `kubectl` commands would you run to investigate?**  
1. Check deployment status:  
   ```bash
   kubectl get deployment <deployment-name> -n <namespace>
   ```
2. Describe the deployment:  
   ```bash
   kubectl describe deployment <deployment-name> -n <namespace>
   ```
   Look for rollout issues or errors.
3. Check ReplicaSet status:  
   ```bash
   kubectl get rs -n <namespace>
   ```
4. View rollout history:  
   ```bash
   kubectl rollout history deployment <deployment-name> -n <namespace>
   ```
5. Check pod events:  
   ```bash
   kubectl describe pod <pod-name> -n <namespace>
   ```
   Fix: Force a rollout if needed:  
   ```bash
   kubectl rollout restart deployment <deployment-name> -n <namespace>
   ```

---

**19. Write a YAML file for a CronJob that runs an `alpine` container every 5 minutes to echo "Hello".**  
```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: hello-cronjob
  namespace: default
spec:
  schedule: "*/5 * * * *"  # Every 5 minutes
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: hello
            image: alpine:latest
            command: ["echo", "Hello"]
          restartPolicy: OnFailure
```
Apply it with:  
```bash
kubectl apply -f cronjob.yaml
```

---

**20. How would you use `kubectl` to patch a deployment to add a new environment variable?**  
```bash
kubectl patch deployment <deployment-name> -n <namespace> --type='json' -p='[{"op": "add", "path": "/spec/template/spec/containers/0/env/-", "value": {"name": "NEW_VAR", "value": "new-value"}}]'
```
Example:  
```bash
kubectl patch deployment web-app -n default --type='json' -p='[{"op": "add", "path": "/spec/template/spec/containers/0/env/-", "value": {"name": "ENV", "value": "prod"}}]'
```

---

**21. Create a YAML file for a Namespace called `staging` with a label `env=staging`.**  
```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: staging
  labels:
    env: staging
```
Apply it with:  
```bash
kubectl apply -f namespace.yaml
```

---

**22. Write a command to cordon a node and reschedule its pods elsewhere.**  
```bash
kubectl cordon <node-name>
kubectl drain <node-name> --ignore-daemonsets --delete-emptydir-data
```
- `cordon` marks the node unschedulable.
- `drain` evicts pods to other nodes.

---

**23. How do you use `kubectl` to check the status of a specific ReplicaSet?**  
```bash
kubectl get rs <replicaset-name> -n <namespace>
kubectl describe rs <replicaset-name> -n <namespace>
```
Find the ReplicaSet name from `kubectl get rs -n <namespace>`.

---

**24. A pod is stuck in `Terminating` state. What commands would you run to force delete it?**  
1. Try normal deletion:  
   ```bash
   kubectl delete pod <pod-name> -n <namespace>
   ```
2. If stuck, force delete:  
   ```bash
   kubectl delete pod <pod-name> -n <namespace> --force --grace-period=0
   ```
3. Check if it’s gone:  
   ```bash
   kubectl get pod <pod-name> -n <namespace>
   ```

---

**25. Write a YAML file for an Ingress resource routing traffic to a service on `/api`.**  
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: api-ingress
  namespace: default
spec:
  rules:
  - http:
      paths:
      - path: /api
        pathType: Prefix
        backend:
          service:
            name: api-service
            port:
              number: 8080
```
Apply it with:  
```bash
kubectl apply -f ingress.yaml
```
Assumes an Ingress controller (e.g., NGINX) is deployed.

---

---

### Ansible Questions

**26. Write an ad-hoc Ansible command to ping all hosts in your inventory.**  
```bash
ansible all -m ping
```
This uses the `ping` module to test connectivity to all hosts in the inventory.

---

**27. Create a playbook to install the `httpd` package on all hosts in the `webservers` group.**  
```yaml
---
- name: Install httpd on webservers
  hosts: webservers
  become: yes  # Run as root
  tasks:
    - name: Install httpd package
      ansible.builtin.yum:
        name: httpd
        state: present
```
Run it with:  
```bash
ansible-playbook install_httpd.yml
```
Note: Use `apt` instead of `yum` for Debian-based systems.

---

**28. How would you use Ansible to copy a file named `config.conf` to `/etc/app/` on all hosts?**  
Ad-hoc command:  
```bash
ansible all -m copy -a "src=config.conf dest=/etc/app/config.conf owner=root group=root mode=0644" --become
```
Or in a playbook:  
```yaml
---
- name: Copy config.conf to all hosts
  hosts: all
  become: yes
  tasks:
    - name: Copy configuration file
      ansible.builtin.copy:
        src: config.conf
        dest: /etc/app/config.conf
        owner: root
        group: root
        mode: "0644"
```

---

**29. Write a playbook to ensure a service named `nginx` is started and enabled on boot.**  
```yaml
---
- name: Manage nginx service
  hosts: all
  become: yes
  tasks:
    - name: Ensure nginx is installed
      ansible.builtin.package:
        name: nginx
        state: present
    - name: Ensure nginx is started and enabled
      ansible.builtin.service:
        name: nginx
        state: started
        enabled: yes
```
Run it with:  
```bash
ansible-playbook nginx_service.yml
```

---

**30. Create an inventory file with two groups: `webservers` and `dbservers`, each with two hosts.**  
```ini
[webservers]
web1.example.com ansible_host=192.168.1.10
web2.example.com ansible_host=192.168.1.11

[dbservers]
db1.example.com ansible_host=192.168.1.20
db2.example.com ansible_host=192.168.1.21
```
Save as `inventory.ini`. Use with:  
```bash
ansible -i inventory.ini all -m ping
```

---

**31. Write an ad-hoc command to check the uptime of all hosts in the `dbservers` group.**  
```bash
ansible dbservers -m command -a "uptime"
```
This runs the `uptime` shell command on all hosts in the `dbservers` group.

---

**32. How would you use Ansible to restart all hosts in the `webservers` group sequentially?**  
Playbook approach (sequential with `serial`):  
```yaml
---
- name: Restart webservers sequentially
  hosts: webservers
  become: yes
  serial: 1  # Process one host at a time
  tasks:
    - name: Restart host
      ansible.builtin.reboot:
        msg: "Reboot initiated by Ansible"
        reboot_timeout: 300
```
Run it with:  
```bash
ansible-playbook reboot_webservers.yml
```

---

**33. Create a playbook to create a user named `devops` with a specific UID on all hosts.**  
```yaml
---
- name: Create devops user
  hosts: all
  become: yes
  tasks:
    - name: Ensure devops user exists with UID 2000
      ansible.builtin.user:
        name: devops
        uid: 2000
        state: present
        shell: /bin/bash
```
Run it with:  
```bash
ansible-playbook create_user.yml
```

---

**34. Write an Ansible task to set a fact variable based on the host’s OS distribution.**  
```yaml
---
- name: Set OS distribution fact
  hosts: all
  tasks:
    - name: Set fact based on distribution
      ansible.builtin.set_fact:
        os_dist: "{{ ansible_facts['distribution'] }}"
    - name: Debug the fact
      ansible.builtin.debug:
        var: os_dist
```
This sets `os_dist` to the value of `ansible_facts['distribution']` (e.g., "CentOS", "Ubuntu").

---

**35. How do you use Ansible to run a shell command and register its output in a variable?**  
In a playbook:  
```yaml
---
- name: Run shell command and register output
  hosts: all
  tasks:
    - name: Run a shell command
      ansible.builtin.shell: "df -h /"
      register: disk_space
    - name: Debug the output
      ansible.builtin.debug:
        var: disk_space.stdout
```
Run it with:  
```bash
ansible-playbook shell_command.yml
```
The `disk_space.stdout` variable contains the command’s output.

---

---

**36. Create a playbook to deploy a template file `app.conf.j2` to `/etc/app.conf`.**  
```yaml
---
- name: Deploy app.conf template
  hosts: all
  become: yes
  tasks:
    - name: Deploy configuration file from template
      ansible.builtin.template:
        src: app.conf.j2
        dest: /etc/app.conf
        owner: root
        group: root
        mode: "0644"
```
Example `app.conf.j2`:  
```
# Managed by Ansible
app_name = {{ app_name | default('myapp') }}
```
Run it with:  
```bash
ansible-playbook deploy_template.yml
```

---

**37. Write an ad-hoc command to update the package cache on all Ubuntu hosts.**  
```bash
ansible all -m apt -a "update_cache=yes" --become -l ubuntu_hosts
```
Assumes `ubuntu_hosts` is a group in your inventory with Ubuntu systems.

---

**38. How would you use Ansible to check if a file exists on all hosts and print a debug message?**  
```yaml
---
- name: Check file existence
  hosts: all
  tasks:
    - name: Check if /etc/app.conf exists
      ansible.builtin.stat:
        path: /etc/app.conf
      register: file_status
    - name: Debug file existence
      ansible.builtin.debug:
        msg: "File /etc/app.conf exists"
      when: file_status.stat.exists
```
Run it with:  
```bash
ansible-playbook check_file.yml
```

---

**39. Create a playbook with a handler to restart `httpd` only if a configuration file changes.**  
```yaml
---
- name: Manage httpd with handler
  hosts: webservers
  become: yes
  tasks:
    - name: Deploy httpd config
      ansible.builtin.copy:
        src: httpd.conf
        dest: /etc/httpd/conf/httpd.conf
        owner: root
        group: root
        mode: "0644"
      notify: Restart httpd
  handlers:
    - name: Restart httpd
      ansible.builtin.service:
        name: httpd
        state: restarted
```
Run it with:  
```bash
ansible-playbook httpd_config.yml
```

---

**40. Write an Ansible task to loop over a list of packages and install them.**  
```yaml
---
- name: Install multiple packages
  hosts: all
  become: yes
  vars:
    packages:
      - nginx
      - git
      - curl
  tasks:
    - name: Install packages
      ansible.builtin.package:
        name: "{{ item }}"
        state: present
      loop: "{{ packages }}"
```
Run it with:  
```bash
ansible-playbook install_packages.yml
```

---

**41. How do you use Ansible to run a task only on hosts with a specific fact (e.g., `ansible_os_family=Debian`)?**  
```yaml
---
- name: Run task on Debian hosts
  hosts: all
  tasks:
    - name: Update package cache on Debian
      ansible.builtin.apt:
        update_cache: yes
      when: ansible_os_family == "Debian"
```
Run it with:  
```bash
ansible-playbook debian_task.yml
```

---

**42. Create a playbook to set up a cron job that runs `backup.sh` daily at midnight.**  
```yaml
---
- name: Set up daily backup cron job
  hosts: all
  become: yes
  tasks:
    - name: Ensure backup cron job exists
      ansible.builtin.cron:
        name: "Daily backup"
        minute: "0"
        hour: "0"
        job: "/usr/local/bin/backup.sh"
        state: present
```
Run it with:  
```bash
ansible-playbook cron_backup.yml
```

---

**43. Write an ad-hoc command to gather facts from all hosts and save them to a file.**  
```bash
ansible all -m setup -a "gather_subset=all" --tree /tmp/facts
```
This saves facts in JSON format to individual files under `/tmp/facts/` for each host.

---

**44. How would you use Ansible to delegate a task to localhost instead of the target host?**  
```yaml
---
- name: Delegate task to localhost
  hosts: all
  tasks:
    - name: Run command on localhost
      ansible.builtin.command: "echo {{ inventory_hostname }} >> /tmp/host_list"
      delegate_to: localhost
```
Run it with:  
```bash
ansible-playbook delegate_task.yml
```

---

**45. Create a playbook to remove a directory `/tmp/old_data` if it exists.**  
```yaml
---
- name: Remove old_data directory
  hosts: all
  become: yes
  tasks:
    - name: Remove /tmp/old_data if it exists
      ansible.builtin.file:
        path: /tmp/old_data
        state: absent
```
Run it with:  
```bash
ansible-playbook remove_dir.yml
```

---

**46. Write an Ansible task to include another task file based on a condition.**  
```yaml
---
- name: Conditional task include
  hosts: all
  tasks:
    - name: Include tasks for Debian
      ansible.builtin.include_tasks:
        file: debian_tasks.yml
      when: ansible_os_family == "Debian"
```
Example `debian_tasks.yml`:  
```yaml
- name: Update apt cache
  ansible.builtin.apt:
    update_cache: yes
```

---

**47. How do you use Ansible to run a playbook with a specific tag (e.g., `deploy`)?**  
```bash
ansible-playbook playbook.yml --tags "deploy"
```
Example playbook with tags:  
```yaml
---
- name: Tagged playbook
  hosts: all
  tasks:
    - name: Install package
      ansible.builtin.package:
        name: nginx
      tags: deploy
    - name: Configure app
      ansible.builtin.copy:
        src: app.conf
        dest: /etc/app.conf
      tags: config
```

---

**48. Create a playbook to ensure a specific line exists in `/etc/hosts` on all hosts.**  
```yaml
---
- name: Update /etc/hosts
  hosts: all
  become: yes
  tasks:
    - name: Ensure line exists in /etc/hosts
      ansible.builtin.lineinfile:
        path: /etc/hosts
        line: "192.168.1.100 app.example.com"
        state: present
```
Run it with:  
```bash
ansible-playbook update_hosts.yml
```

---

**49. Write an ad-hoc command to reboot all hosts and wait for them to come back online.**  
```bash
ansible all -m reboot -a "reboot_timeout=300" --become
```
This reboots hosts and waits up to 300 seconds for them to return.

---

**50. How would you use Ansible to troubleshoot a failed playbook by enabling verbose output?**  
Run the playbook with `-v` (or more verbosity with `-vv` or `-vvv`):  
```bash
ansible-playbook playbook.yml -v
```
- `-v`: Shows basic output.
- `-vv`: Includes more details.
- `-vvv`: Full debug output, including SSH commands.

---

---

### GitHub Actions Questions

**51. Write a GitHub Actions workflow to run a linter on every push to the `main` branch.**  
```yaml
name: Lint Code
on:
  push:
    branches:
      - main
jobs:
  lint:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Set up Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '16'
      - name: Install dependencies
        run: npm install
      - name: Run linter
        run: npm run lint  # Assumes a lint script in package.json
```
Save as `.github/workflows/lint.yml`.

---

**52. How would you configure a workflow to build a Docker image and push it to Docker Hub?**  
```yaml
name: Build and Push Docker Image
on:
  push:
    branches:
      - main
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Log in to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
      - name: Build and push Docker image
        run: |
          docker build -t ${{ secrets.DOCKER_USERNAME }}/my-app:latest .
          docker push ${{ secrets.DOCKER_USERNAME }}/my-app:latest
```
Add `DOCKER_USERNAME` and `DOCKER_PASSWORD` as GitHub Secrets.

---

**53. Create a workflow that runs tests only on pull requests to the `develop` branch.**  
```yaml
name: Run Tests on PR
on:
  pull_request:
    branches:
      - develop
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Set up Python
        uses: actions/setup-python@v4
        with:
          python-version: '3.9'
      - name: Install dependencies
        run: pip install -r requirements.txt
      - name: Run tests
        run: pytest
```
Save as `.github/workflows/test.yml`.

---

**54. Write a job to deploy a static site to GitHub Pages on every tag push.**  
```yaml
name: Deploy to GitHub Pages
on:
  push:
    tags:
      - 'v*'
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Build site
        run: npm install && npm run build  # Assumes a build step
      - name: Deploy to GitHub Pages
        uses: peaceiris/actions-gh-pages@v3
        with:
          github_token: ${{ secrets.GITHUB_TOKEN }}
          publish_dir: ./dist  # Adjust to your build output directory
```
Save as `.github/workflows/deploy.yml`.

---

**55. How do you use GitHub Actions to set an environment variable for all steps in a job?**  
```yaml
name: Set Environment Variable
on:
  push:
    branches:
      - main
jobs:
  build:
    runs-on: ubuntu-latest
    env:
      MY_VAR: "hello-world"  # Set at job level
    steps:
      - name: Use environment variable
        run: echo $MY_VAR
      - name: Use it again
        run: echo $MY_VAR
```
The `env` block at the job level makes `MY_VAR` available to all steps.

---

**56. Create a workflow to run a shell script on an Ubuntu runner every Monday at 9 AM.**  
```yaml
name: Weekly Script
on:
  schedule:
    - cron: '0 9 * * 1'  # Every Monday at 9 AM UTC
jobs:
  run-script:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Run shell script
        run: ./scripts/my_script.sh
```
Ensure `scripts/my_script.sh` exists in the repository.

---

**57. Write a job that checks out a repository and lists its files.**  
```yaml
name: List Files
on:
  push:
    branches:
      - main
jobs:
  list:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: List files
        run: ls -la
```
Save as `.github/workflows/list_files.yml`.

---

**58. How would you configure a workflow to use a matrix strategy for testing on multiple OS versions?**  
```yaml
name: Matrix Test
on:
  push:
    branches:
      - main
jobs:
  test:
    runs-on: ${{ matrix.os }}
    strategy:
      matrix:
        os: [ubuntu-latest, macos-latest, windows-latest]
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Run tests
        run: echo "Testing on ${{ matrix.os }}"
```
This runs the job on three OS versions in parallel.

---

**59. Create a workflow to cancel in-progress runs when a new push occurs on the same branch.**  
```yaml
name: Cancel Previous Runs
on:
  push:
    branches:
      - main
concurrency:
  group: ${{ github.workflow }}-${{ github.ref }}
  cancel-in-progress: true
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Build
        run: echo "Building..."
```
The `concurrency` block cancels any in-progress runs for the same branch.

---

**60. Write a step to upload an artifact (e.g., `build.zip`) after a build job completes.**  
```yaml
name: Upload Artifact
on:
  push:
    branches:
      - main
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Create build artifact
        run: zip -r build.zip .
      - name: Upload artifact
        uses: actions/upload-artifact@v3
        with:
          name: build-artifact
          path: build.zip
```

---

**61. How do you use GitHub Actions to trigger a workflow manually with input parameters?**  
```yaml
name: Manual Workflow
on:
  workflow_dispatch:
    inputs:
      environment:
        description: 'Environment to deploy to'
        required: true
        default: 'staging'
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Use input
        run: echo "Deploying to ${{ github.event.inputs.environment }}"
```
Trigger it manually via the GitHub UI under "Actions".

---

**62. Create a workflow to run a security scan only if a previous job succeeds.**  
```yaml
name: Build and Scan
on:
  push:
    branches:
      - main
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Build
        run: echo "Building..."
  scan:
    needs: build  # Runs only if build succeeds
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Run security scan
        run: echo "Scanning..."  # Replace with actual scan command
```

---

**63. Write a job to use a custom Docker container as the runner environment.**  
```yaml
name: Custom Container Job
on:
  push:
    branches:
      - main
jobs:
  custom:
    runs-on: ubuntu-latest
    container:
      image: node:16  # Custom Docker image
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Run in custom container
        run: node --version
```
The `container` key specifies the custom image.

---

---

**64. How would you configure a workflow to use a secret (e.g., `API_KEY`) in a step?**  
```yaml
name: Use Secret
on:
  push:
    branches:
      - main
jobs:
  run:
    runs-on: ubuntu-latest
    steps:
      - name: Use API key secret
        env:
          API_KEY: ${{ secrets.API_KEY }}  # Reference secret
        run: echo "Using API_KEY: $API_KEY"
```
Add `API_KEY` as a secret in the repository settings under "Secrets and variables > Actions".

---

**65. Create a workflow to send a Slack notification on workflow failure.**  
```yaml
name: Notify on Failure
on:
  push:
    branches:
      - main
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Build (might fail)
        run: exit 1  # Simulate failure
      - name: Send Slack notification on failure
        if: failure()  # Only runs if previous steps fail
        uses: slackapi/slack-github-action@v1.23.0
        with:
          slack-bot-token: ${{ secrets.SLACK_BOT_TOKEN }}
          channel-id: 'general'
          text: 'Workflow failed in ${{ github.repository }}!'
```
Add `SLACK_BOT_TOKEN` as a GitHub Secret.

---

**66. Write a step to cache dependencies (e.g., `node_modules`) between workflow runs.**  
```yaml
name: Cache Dependencies
on:
  push:
    branches:
      - main
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Cache node_modules
        uses: actions/cache@v3
        with:
          path: ~/.npm
          key: ${{ runner.os }}-node-${{ hashFiles('**/package-lock.json') }}
          restore-keys: ${{ runner.os }}-node-
      - name: Install dependencies
        run: npm install
```

---

**67. How do you use GitHub Actions to run jobs conditionally based on a branch name?**  
```yaml
name: Conditional Job
on:
  push:
    branches:
      - '*'
jobs:
  build:
    if: github.ref == 'refs/heads/main'  # Only runs on main branch
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Build
        run: echo "Building on main"
```

---

**68. Create a workflow to build and test a Go application on every push.**  
```yaml
name: Build and Test Go App
on:
  push:
    branches:
      - '*'
jobs:
  build-test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Set up Go
        uses: actions/setup-go@v3
        with:
          go-version: '1.18'
      - name: Build
        run: go build ./...
      - name: Test
        run: go test ./...
```
Save as `.github/workflows/go.yml`.

---

**69. Write a job to run a command only if a specific file (e.g., `README.md`) changes.**  
```yaml
name: Check README Changes
on:
  push:
    branches:
      - main
jobs:
  check:
    runs-on: ubuntu-latest
    if: contains(github.event.commits[0].modified, 'README.md')  # Check if README.md changed
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Run command
        run: echo "README.md was modified!"
```

---

**70. How would you configure a workflow to use a self-hosted runner?**  
```yaml
name: Self-Hosted Runner
on:
  push:
    branches:
      - main
jobs:
  build:
    runs-on: self-hosted  # Use self-hosted runner label
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Run on self-hosted
        run: echo "Running on self-hosted runner"
```
Ensure the self-hosted runner is set up and labeled as `self-hosted` in your repository.

---

**71. Create a workflow to run a linter and upload its output as an artifact.**  
```yaml
name: Lint and Upload
on:
  push:
    branches:
      - main
jobs:
  lint:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Run linter
        run: npm install && npm run lint > lint-output.txt 2>&1
      - name: Upload lint output
        uses: actions/upload-artifact@v3
        with:
          name: lint-report
          path: lint-output.txt
```

---

**72. Write a step to set an output variable from one step and use it in another.**  
```yaml
name: Set and Use Output
on:
  push:
    branches:
      - main
jobs:
  example:
    runs-on: ubuntu-latest
    steps:
      - name: Set output variable
        id: step1
        run: echo "my_output=hello" >> $GITHUB_OUTPUT
      - name: Use output variable
        run: echo "Output from step1: ${{ steps.step1.outputs.my_output }}"
```

---

**73. How do you use GitHub Actions to schedule a workflow to run every 15 minutes?**  
```yaml
name: Scheduled Workflow
on:
  schedule:
    - cron: '*/15 * * * *'  # Every 15 minutes
jobs:
  run:
    runs-on: ubuntu-latest
    steps:
      - name: Run every 15 minutes
        run: echo "Running at $(date)"
```

---

**74. Create a workflow to clean up old artifacts after a successful run.**  
```yaml
name: Clean Artifacts
on:
  push:
    branches:
      - main
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Build
        run: echo "Building..."
      - name: Clean up old artifacts
        if: success()
        uses: actions/delete-package-versions@v4
        with:
          package-type: 'artifact'
          min-versions-to-keep: 1
```

---

**75. Write a job to clone a repository from another GitHub organization and run a script.**  
```yaml
name: Clone External Repo
on:
  push:
    branches:
      - main
jobs:
  clone:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout external repo
        uses: actions/checkout@v3
        with:
          repository: other-org/other-repo
          token: ${{ secrets.PAT }}  # Personal Access Token with repo access
      - name: Run script
        run: ./script.sh
```
Add a Personal Access Token (`PAT`) as a GitHub Secret with access to `other-org/other-repo`.

---

---

**76. Use Ansible to apply a Kubernetes manifest file to a cluster and verify it with `kubectl`.**  
**Ansible Playbook:**  
```yaml
---
- name: Apply Kubernetes manifest and verify
  hosts: localhost  # Assumes kubeconfig is on the control node
  tasks:
    - name: Apply manifest file
      ansible.builtin.command: kubectl apply -f pod.yaml
    - name: Verify pod is running
      ansible.builtin.command: kubectl get pod nginx-pod -o jsonpath='{.status.phase}'
      register: pod_status
    - name: Fail if pod is not running
      ansible.builtin.fail:
        msg: "Pod is not in Running state"
      when: pod_status.stdout != "Running"
```
**Sample `pod.yaml`:**  
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx-pod
spec:
  containers:
  - name: nginx
    image: nginx:latest
```
Run with:  
```bash
ansible-playbook apply_k8s.yml
```

---

**77. Write a GitHub Actions workflow to run an Ansible playbook on every push to `main`.**  
```yaml
name: Run Ansible Playbook
on:
  push:
    branches:
      - main
jobs:
  ansible:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Install Ansible
        run: sudo apt update && sudo apt install -y ansible
      - name: Run Ansible playbook
        run: ansible-playbook playbook.yml -i inventory.ini
```
**Sample `playbook.yml`:**  
```yaml
- name: Test playbook
  hosts: all
  tasks:
    - name: Ping hosts
      ansible.builtin.ping:
```
Ensure `inventory.ini` is in the repository.

---

**78. How would you use `kubectl` to troubleshoot a pod that Ansible deployed but isn’t running?**  
1. Check pod status:  
   ```bash
   kubectl get pod <pod-name> -n <namespace>
   ```
2. Describe the pod for events:  
   ```bash
   kubectl describe pod <pod-name> -n <namespace>
   ```
   Look for issues like `ImagePullBackOff`, `CrashLoopBackOff`, or resource limits.
3. Check logs:  
   ```bash
   kubectl logs <pod-name> -n <namespace>
   ```
4. Verify pod spec (from Ansible manifest):  
   ```bash
   kubectl get pod <pod-name> -n <namespace> -o yaml
   ```
   Fix: Adjust the Ansible playbook (e.g., image name, resources) and reapply.

---

**79. Create an Ansible playbook to install `kubectl` on a host and run a cluster health check.**  
```yaml
---
- name: Install kubectl and check cluster
  hosts: all
  become: yes
  tasks:
    - name: Install kubectl
      ansible.builtin.shell: |
        curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
        chmod +x kubectl
        mv kubectl /usr/local/bin/
      args:
        creates: /usr/local/bin/kubectl
    - name: Check cluster health
      ansible.builtin.command: kubectl cluster-info
      register: cluster_info
    - name: Display cluster info
      ansible.builtin.debug:
        var: cluster_info.stdout
```
Run with:  
```bash
ansible-playbook install_kubectl.yml
```
Assumes kubeconfig is configured on the host.

---

**80. Write a GitHub Actions workflow to scale a Kubernetes deployment based on a tag push.**  
```yaml
name: Scale Kubernetes Deployment
on:
  push:
    tags:
      - 'v*'
jobs:
  scale:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Set up kubectl
        uses: azure/setup-kubectl@v3
        with:
          version: 'latest'
      - name: Configure kubeconfig
        run: echo "${{ secrets.KUBECONFIG }}" > $HOME/.kube/config
      - name: Scale deployment
        run: kubectl scale deployment web-app --replicas=5 -n default
```
Add `KUBECONFIG` as a GitHub Secret.

---

**81. How do you use Ansible to update a Kubernetes ConfigMap and restart affected pods?**  
```yaml
---
- name: Update ConfigMap and restart pods
  hosts: localhost
  tasks:
    - name: Update ConfigMap
      ansible.builtin.command: kubectl apply -f configmap.yaml
    - name: Restart deployment
      ansible.builtin.command: kubectl rollout restart deployment web-app -n default
```
**Sample `configmap.yaml`:**  
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
  namespace: default
data:
  env: "production"
```
Run with:  
```bash
ansible-playbook update_configmap.yml
```

---

**82. Create a workflow to use Ansible to configure a Kubernetes node and validate with `kubectl`.**  
```yaml
name: Configure Kubernetes Node
on:
  push:
    branches:
      - main
jobs:
  configure:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Install Ansible
        run: sudo apt update && sudo apt install -y ansible
      - name: Run Ansible playbook
        run: ansible-playbook -i inventory.ini configure_node.yml
      - name: Set up kubectl
        uses: azure/setup-kubectl@v3
      - name: Configure kubeconfig
        run: echo "${{ secrets.KUBECONFIG }}" > $HOME/.kube/config
      - name: Validate node
        run: kubectl get nodes
```
**Sample `configure_node.yml`:**  
```yaml
- name: Configure node
  hosts: k8s_nodes
  become: yes
  tasks:
    - name: Install kubeadm
      ansible.builtin.package:
        name: kubeadm
        state: present
```
Add `KUBECONFIG` as a secret and ensure `inventory.ini` targets the node.

---

**83. Write an Ansible task to check Kubernetes pod logs and fail if errors are found.**  
```yaml
---
- name: Check pod logs
  hosts: localhost
  tasks:
    - name: Get pod logs
      ansible.builtin.command: kubectl logs <pod-name> -n <namespace>
      register: pod_logs
    - name: Fail if error found
      ansible.builtin.fail:
        msg: "Errors found in pod logs"
      when: "'error' in pod_logs.stdout | lower"
```
Run with:  
```bash
ansible-playbook check_logs.yml
```

---

**84. How would you use GitHub Actions to trigger a Kubernetes rollout restart after a config change?**  
```yaml
name: Restart Deployment
on:
  push:
    branches:
      - main
    paths:
      - 'config/*'
jobs:
  restart:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Set up kubectl
        uses: azure/setup-kubectl@v3
      - name: Configure kubeconfig
        run: echo "${{ secrets.KUBECONFIG }}" > $HOME/.kube/config
      - name: Apply config change
        run: kubectl apply -f config/configmap.yaml
      - name: Restart deployment
        run: kubectl rollout restart deployment web-app -n default
```
Add `KUBECONFIG` as a secret.

---

**85. Create a playbook to deploy a Kubernetes service and use `kubectl` to expose it externally.**  
```yaml
---
- name: Deploy and expose Kubernetes service
  hosts: localhost
  tasks:
    - name: Apply service manifest
      ansible.builtin.command: kubectl apply -f service.yaml
    - name: Expose service externally
      ansible.builtin.command: kubectl expose service web-service --type=LoadBalancer --port=80 --name=web-lb -n default
```
**Sample `service.yaml`:**  
```yaml
apiVersion: v1
kind: Service
metadata:
  name: web-service
  namespace: default
spec:
  selector:
    app: web-app
  ports:
  - port: 80
    targetPort: 8080
  type: ClusterIP
```
Run with:  
```bash
ansible-playbook deploy_service.yml
```

---

---

**1. Deploy a Kubernetes pod on AWS EKS using Terraform and verify it with `kubectl`.**  
**Terraform:**  
```hcl
provider "aws" { region = "us-east-1" }
module "eks" {
  source          = "terraform-aws-modules/eks/aws"
  cluster_name    = "my-eks"
  cluster_version = "1.24"
  subnet_ids      = ["subnet-1", "subnet-2"]
  vpc_id          = "vpc-123"
}
resource "kubernetes_pod" "nginx" {
  metadata { name = "nginx-pod" }
  spec {
    container {
      image = "nginx:latest"
      name  = "nginx"
    }
  }
}
```
Run: `terraform init && terraform apply`  
**Verify:**  
```bash
aws eks update-kubeconfig --name my-eks --region us-east-1
kubectl get pod nginx-pod
```

---

**2. Use Ansible to install Docker on an EC2 instance and deploy a Kubernetes manifest from GitHub Actions.**  
**Ansible Playbook (`install_docker.yml`):**  
```yaml
- name: Install Docker on EC2
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Install Docker
      ansible.builtin.package: { name: docker.io, state: present }
    - name: Start Docker
      ansible.builtin.service: { name: docker, state: started, enabled: yes }
```
**GitHub Actions Workflow (`.github/workflows/deploy.yml`):**  
```yaml
name: Deploy K8s Manifest
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - run: ansible-playbook -i inventory.ini install_docker.yml
      - uses: azure/setup-kubectl@v3
      - run: kubectl apply -f pod.yaml
```
**`pod.yaml`:**  
```yaml
apiVersion: v1
kind: Pod
metadata: { name: "test-pod" }
spec: { containers: [{ name: "test", image: "nginx" }] }
```

---

**3. Set up an AWS S3 bucket with Terraform and configure GitHub Actions to upload files to it.**  
**Terraform:**  
```hcl
resource "aws_s3_bucket" "my_bucket" { bucket = "my-unique-bucket-123" }
```
Run: `terraform apply`  
**GitHub Actions (`.github/workflows/upload.yml`):**  
```yaml
name: Upload to S3
on: push
jobs:
  upload:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: aws-actions/configure-aws-credentials@v2
        with:
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
          aws-region: us-east-1
      - run: aws s3 cp file.txt s3://my-unique-bucket-123/
```

---

**4. Troubleshoot a Kubernetes pod failing on EKS with `kubectl` and update its AWS security group using Terraform.**  
**Troubleshoot:**  
```bash
kubectl get pod <pod-name> -n <namespace>
kubectl describe pod <pod-name> -n <namespace>  # Check events
kubectl logs <pod-name> -n <namespace>  # Check logs
```
**Terraform Update:**  
```hcl
resource "aws_security_group_rule" "allow_pod" {
  type              = "ingress"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = "sg-123"
}
```
Run: `terraform apply`

---

**5. Use Ansible to configure an AWS EC2 instance and deploy a Kubernetes service with Terraform.**  
**Ansible (`configure_ec2.yml`):**  
```yaml
- name: Configure EC2
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Install dependencies
      ansible.builtin.package: { name: "{{ item }}", state: present }
      loop: [curl, unzip]
```
**Terraform:**  
```hcl
resource "kubernetes_service" "web" {
  metadata { name = "web-service" }
  spec {
    selector = { app = "web" }
    port { port = 80, target_port = "8080" }
    type = "LoadBalancer"
  }
}
```
Run: `ansible-playbook configure_ec2.yml && terraform apply`

---

**6. Automate an AWS Lambda deployment with GitHub Actions and monitor it with a Kubernetes pod.**  
**GitHub Actions (`.github/workflows/lambda.yml`):**  
```yaml
name: Deploy Lambda
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: aws-actions/configure-aws-credentials@v2
        with: { aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}, aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}, aws-region: us-east-1 }
      - run: zip -r lambda.zip . && aws lambda update-function-code --function-name my-lambda --zip-file fileb://lambda.zip
```
**K8s Pod (`monitor.yaml`):**  
```yaml
apiVersion: v1
kind: Pod
metadata: { name: "lambda-monitor" }
spec:
  containers:
  - name: monitor
    image: amazon/aws-cli
    command: ["aws", "lambda", "get-function", "--function-name", "my-lambda"]
```

---

**7. Deploy an AWS Auto Scaling group with Terraform and use Ansible to install a Kubernetes agent.**  
**Terraform:**  
```hcl
module "asg" {
  source          = "terraform-aws-modules/autoscaling/aws"
  name            = "my-asg"
  min_size        = 1
  max_size        = 3
  vpc_zone_identifier = ["subnet-1"]
  launch_template = { id = "lt-123" }
}
```
**Ansible (`install_k8s_agent.yml`):**  
```yaml
- name: Install Kubernetes agent
  hosts: asg_hosts
  become: yes
  tasks:
    - name: Install kubeadm
      ansible.builtin.package: { name: kubeadm, state: present }
```

---

**8. Use GitHub Actions to trigger a Terraform apply for an EKS cluster and scale it with `kubectl`.**  
**GitHub Actions (`.github/workflows/eks.yml`):**  
```yaml
name: Deploy EKS
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: hashicorp/setup-terraform@v2
      - run: terraform init && terraform apply -auto-approve
      - uses: azure/setup-kubectl@v3
      - run: |
          aws eks update-kubeconfig --name my-eks --region us-east-1
          kubectl scale deployment web-app --replicas=3
        env: { AWS_ACCESS_KEY_ID: ${{ម: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
```

---

**9. Troubleshoot an AWS RDS connection failure from a Kubernetes pod using `kubectl` and Terraform.**  
**Troubleshoot:**  
```bash
kubectl exec -it <pod-name> -- ping <rds-endpoint>
kubectl describe pod <pod-name>
```
**Terraform Fix (Security Group):**  
```hcl
resource "aws_security_group_rule" "rds_access" {
  type              = "ingress"
  from_port         = 3306
  to_port           = 3306
  protocol          = "tcp"
  source_security_group_id = "sg-pod"
  security_group_id = "sg-rds"
}
```

---

**10. Configure Ansible to update an AWS EC2 instance and deploy a GitHub Actions workflow to test Kubernetes.**  
**Ansible (`update_ec2.yml`):**  
```yaml
- name: Update EC2
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Update packages
      ansible.builtin.yum: { name: "*", state: latest }
```
**GitHub Actions (`.github/workflows/test_k8s.yml`):**  
```yaml
name: Test K8s
on: push
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - run: kubectl get nodes
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```

---

**11. Use Terraform to create an S3 bucket and Kubernetes ConfigMap, then sync them with Ansible.**  
**Terraform:**  
```hcl
resource "aws_s3_bucket" "config" { bucket = "config-bucket-123" }
resource "kubernetes_configmap" "app" {
  metadata { name = "app-config" }
  data { "key" = "value" }
}
```
**Ansible (`sync_s3.yml`):**  
```yaml
- name: Sync ConfigMap to S3
  hosts: localhost
  tasks:
    - name: Export ConfigMap
      ansible.builtin.command: kubectl get configmap app-config -o yaml > config.yaml
    - name: Upload to S3
      amazon.aws.aws_s3:
        bucket: config-bucket-123
        src: config.yaml
        dest: config.yaml
        mode: put
```

---

**12. Set up an AWS ECS cluster with Terraform and use GitHub Actions to deploy a Kubernetes alternative.**  
**Terraform:**  
```hcl
module "ecs" {
  source       = "terraform-aws-modules/ecs/aws"
  cluster_name = "my-ecs"
}
```
**GitHub Actions (`.github/workflows/k8s_alt.yml`):**  
```yaml
name: Deploy K8s Alternative
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - run: kubectl apply -f k8s_deployment.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```

---

**13. Deploy an AWS ALB with Terraform and expose a Kubernetes service through it using `kubectl`.**  
**Terraform:**  
```hcl
resource "aws_lb" "my_alb" {
  name               = "my-alb"
  load_balancer_type = "application"
  subnets            = ["subnet-1", "subnet-2"]
}
```
**Expose with `kubectl`:**  
```bash
kubectl expose service web-service --type=LoadBalancer --name=web-lb --port=80
kubectl annotate service web-lb "service.beta.kubernetes.io/aws-load-balancer-arn=arn:aws:elasticloadbalancing:us-east-1:123:loadbalancer/app/my-alb/abc123"
```

---

**14. Use Ansible to configure an EC2 instance as a Kubernetes node and verify it with GitHub Actions.**  
**Ansible (`configure_node.yml`):**  
```yaml
- name: Configure K8s node
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Install kubelet
      ansible.builtin.package: { name: kubelet, state: present }
```
**GitHub Actions (`.github/workflows/verify.yml`):**  
```yaml
name: Verify Node
on: push
jobs:
  verify:
    runs-on: ubuntu-latest
    steps:
      - run: kubectl get nodes
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```

---

**15. Troubleshoot a Kubernetes pod failing to pull an image from AWS ECR using `kubectl` and Terraform.**  
**Troubleshoot:**  
```bash
kubectl describe pod <pod-name>  # Look for ImagePullBackOff
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <ecr-repo>
kubectl create secret docker-registry ecr-secret --docker-server=<ecr-repo> --docker-username=AWS --docker-password=<password>
```
**Terraform:**  
```hcl
resource "kubernetes_pod" "fix" {
  spec { image_pull_secrets { name = "ecr-secret" } }
}
```

---

**16. Configure GitHub Actions to run Terraform for an AWS VPC and deploy a Kubernetes cluster inside it.**  
**Terraform:**  
```hcl
module "vpc" { source = "terraform-aws-modules/vpc/aws" }
module "eks" {
  source       = "terraform-aws-modules/eks/aws"
  subnet_ids   = module.vpc.private_subnets
  vpc_id       = module.vpc.vpc_id
}
```
**GitHub Actions (`.github/workflows/vpc_eks.yml`):**  
```yaml
name: Deploy VPC and EKS
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: hashicorp/setup-terraform@v2
      - run: terraform init && terraform apply -auto-approve
        env: { AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
```

---

**17. Use Ansible to install `kubectl` on an AWS EC2 instance and deploy a pod from GitHub Actions.**  
**Ansible (`install_kubectl.yml`):**  
```yaml
- name: Install kubectl
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Download and install kubectl
      ansible.builtin.shell: |
        curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
        chmod +x kubectl && mv kubectl /usr/local/bin/
```
**GitHub Actions (`.github/workflows/deploy_pod.yml`):**  
```yaml
name: Deploy Pod
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - run: kubectl apply -f pod.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```

---

**18. Deploy an AWS SNS topic with Terraform and notify a Kubernetes pod failure via GitHub Actions.**  
**Terraform:**  
```hcl
resource "aws_sns_topic" "alerts" { name = "pod-failure-alerts" }
```
**GitHub Actions (`.github/workflows/notify.yml`):**  
```yaml
name: Notify on Failure
on: workflow_run
jobs:
  notify:
    if: failure()
    runs-on: ubuntu-latest
    steps:
      - uses: aws-actions/configure-aws-credentials@v2
        with: { aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}, aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}, aws-region: us-east-1 }
      - run: aws sns publish --topic-arn ${{ secrets.SNS_TOPIC_ARN }} --message "Pod failed in ${{ github.repository }}"
```

---

**19. Use Terraform to create an AWS EKS cluster and Ansible to deploy a monitoring pod with `kubectl`.**  
**Terraform:**  
```hcl
module "eks" {
  source       = "terraform-aws-modules/eks/aws"
  cluster_name = "monitoring-eks"
}
```
**Ansible (`deploy_monitor.yml`):**  
```yaml
- name: Deploy monitoring pod
  hosts: localhost
  tasks:
    - name: Apply monitoring pod
      ansible.builtin.command: kubectl apply -f monitor.yaml
```
**`monitor.yaml`:**  
```yaml
apiVersion: v1
kind: Pod
metadata: { name: "monitor" }
spec: { containers: [{ name: "prometheus", image: "prom/prometheus" }] }
```

---

**20. Troubleshoot an AWS Lambda timeout with Terraform and trigger a Kubernetes job from GitHub Actions.**  
**Troubleshoot Terraform:**  
```hcl
resource "aws_lambda_function" "fix" {
  function_name = "my-lambda"
  timeout       = 60  # Increase from default 3s
  filename      = "lambda.zip"
  handler       = "index.handler"
  runtime       = "nodejs16.x"
  role          = "arn:aws:iam::123:role/lambda-role"
}
```
**GitHub Actions (`.github/workflows/k8s_job.yml`):**  
```yaml
name: Trigger K8s Job
on: push
jobs:
  trigger:
    runs-on: ubuntu-latest
    steps:
      - run: kubectl apply -f job.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```
**`job.yaml`:**  
```yaml
apiVersion: batch/v1
kind: Job
metadata: { name: "lambda-check" }
spec:
  template:
    spec:
      containers:
      - name: check
        image: amazon/aws-cli
        command: ["aws", "lambda", "get-function", "--function-name", "my-lambda"]
      restartPolicy: Never
```

---

---

**21. Set up an AWS CloudWatch alarm with Terraform and use Ansible to alert a Kubernetes pod.**  
**Terraform:**  
```hcl
resource "aws_cloudwatch_metric_alarm" "cpu" {
  alarm_name          = "high-cpu"
  comparison_operator = "GreaterThanThreshold"
  evaluation_periods  = "2"
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = "120"
  statistic           = "Average"
  threshold           = "80"
  alarm_actions       = ["arn:aws:sns:us-east-1:123:alerts"]
}
```
**Ansible (`alert_pod.yml`):**  
```yaml
- name: Alert Kubernetes pod
  hosts: localhost
  tasks:
    - name: Create alert pod
      ansible.builtin.command: kubectl apply -f alert.yaml
```
**`alert.yaml`:**  
```yaml
apiVersion: v1
kind: Pod
metadata: { name: "alert-pod" }
spec: { containers: [{ name: "alert", image: "busybox", command: ["sh", "-c", "echo Alarm triggered && sleep 3600"] }] }
```

---

**22. Use GitHub Actions to deploy Terraform for an AWS RDS and connect it to a Kubernetes app.**  
**Terraform (`rds.tf`):**  
```hcl
resource "aws_db_instance" "db" {
  identifier         = "my-rds"
  engine             = "mysql"
  instance_class     = "db.t3.micro"
  allocated_storage  = 20
  username           = "admin"
  password           = "password123"
}
```
**GitHub Actions (`.github/workflows/rds.yml`):**  
```yaml
name: Deploy RDS and Connect K8s
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: hashicorp/setup-terraform@v2
      - run: terraform init && terraform apply -auto-approve
        env: { AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
      - run: kubectl apply -f app.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```
**`app.yaml`:**  
```yaml
apiVersion: v1
kind: Pod
metadata: { name: "app" }
spec: { containers: [{ name: "app", image: "mysql:5.7", env: [{ name: "MYSQL_HOST", value: "my-rds.endpoint" }] }] }
```

---

**23. Deploy an AWS EC2 instance with Terraform and use Ansible to join it to a Kubernetes cluster.**  
**Terraform:**  
```hcl
resource "aws_instance" "k8s_node" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.medium"
  subnet_id     = "subnet-1"
}
```
**Ansible (`join_k8s.yml`):**  
```yaml
- name: Join EC2 to K8s
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Install kubeadm
      ansible.builtin.package: { name: kubeadm, state: present }
    - name: Join cluster
      ansible.builtin.command: kubeadm join <master-ip>:6443 --token <token> --discovery-token-ca-cert-hash <hash>
```

---

**24. Troubleshoot a Kubernetes pod networking issue on EKS with `kubectl` and adjust AWS VPC with Terraform.**  
**Troubleshoot:**  
```bash
kubectl exec -it <pod-name> -- ping 8.8.8.8  # Test external connectivity
kubectl describe pod <pod-name>  # Check network policies or CNI issues
```
**Terraform Fix:**  
```hcl
resource "aws_vpc" "eks_vpc" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true  # Ensure DNS resolution
}
resource "aws_route" "internet" {
  route_table_id         = "rt-123"
  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = "igw-123"
}
```

---

**25. Use GitHub Actions to run Ansible for an AWS EC2 setup and deploy a Kubernetes ingress.**  
**Ansible (`setup_ec2.yml`):**  
```yaml
- name: Setup EC2
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Install nginx
      ansible.builtin.package: { name: nginx, state: present }
```
**GitHub Actions (`.github/workflows/ingress.yml`):**  
```yaml
name: Setup EC2 and Deploy Ingress
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - run: ansible-playbook -i inventory.ini setup_ec2.yml
      - run: kubectl apply -f ingress.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```
**`ingress.yaml`:**  
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata: { name: "web-ingress" }
spec: { rules: [{ http: { paths: [{ path: "/", pathType: "Prefix", backend: { service: { name: "web", port: { number: 80 } } } } ] } } ] }
```

---

**26. Configure Terraform for an AWS SQS queue and use Kubernetes to process messages with Ansible.**  
**Terraform:**  
```hcl
resource "aws_sqs_queue" "my_queue" { name = "my-queue" }
```
**Ansible (`process_sqs.yml`):**  
```yaml
- name: Deploy SQS processor pod
  hosts: localhost
  tasks:
    - name: Apply processor pod
      ansible.builtin.command: kubectl apply -f processor.yaml
```
**`processor.yaml`:**  
```yaml
apiVersion: v1
kind: Pod
metadata: { name: "sqs-processor" }
spec: { containers: [{ name: "processor", image: "amazon/aws-cli", command: ["sh", "-c", "aws sqs receive-message --queue-url <queue-url>"] }] }
```

---

**27. Deploy an AWS EKS cluster with Terraform and use GitHub Actions to test pod scaling with `kubectl`.**  
**Terraform:**  
```hcl
module "eks" { source = "terraform-aws-modules/eks/aws", cluster_name = "test-eks" }
```
**GitHub Actions (`.github/workflows/scale_test.yml`):**  
```yaml
name: Test Scaling
on: push
jobs:
  scale:
    runs-on: ubuntu-latest
    steps:
      - run: |
          aws eks update-kubeconfig --name test-eks --region us-east-1
          kubectl scale deployment test-app --replicas=5
        env: { AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }}, KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```

---

**28. Use Ansible to update an AWS EC2 instance’s security group and deploy a Kubernetes service from GitHub Actions.**  
**Ansible (`update_sg.yml`):**  
```yaml
- name: Update SG
  hosts: localhost
  tasks:
    - name: Add rule
      amazon.aws.ec2_security_group:
        name: "my-sg"
        rules: [{ proto: "tcp", from_port: 80, to_port: 80, cidr_ip: "0.0.0.0/0" }]
```
**GitHub Actions (`.github/workflows/service.yml`):**  
```yaml
name: Deploy Service
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - run: ansible-playbook update_sg.yml
      - run: kubectl apply -f service.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```

---

**29. Troubleshoot an AWS ELB not routing to Kubernetes pods using `kubectl` and Terraform.**  
**Troubleshoot:**  
```bash
kubectl get svc <service-name> -o wide  # Check external IP
kubectl describe pod <pod-name>  # Verify pod health
```
**Terraform Fix:**  
```hcl
resource "aws_lb_target_group_attachment" "fix" {
  target_group_arn = "arn:aws:elasticloadbalancing:us-east-1:123:targetgroup/my-tg/abc"
  target_id        = "i-123"  # EC2 instance ID if needed
}
```

---

**30. Set up an AWS DynamoDB table with Terraform and use Ansible to populate it from a Kubernetes pod.**  
**Terraform:**  
```hcl
resource "aws_dynamodb_table" "my_table" {
  name           = "my-table"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "id"
  attribute { name = "id", type = "S" }
}
```
**Ansible (`populate_dynamo.yml`):**  
```yaml
- name: Populate DynamoDB
  hosts: localhost
  tasks:
    - name: Apply populator pod
      ansible.builtin.command: kubectl apply -f populator.yaml
```
**`populator.yaml`:**  
```yaml
apiVersion: v1
kind: Pod
metadata: { name: "dynamo-populator" }
spec: { containers: [{ name: "populator", image: "amazon/aws-cli", command: ["aws", "dynamodb", "put-item", "--table-name", "my-table", "--item", "{\"id\": {\"S\": \"1\"}, \"data\": {\"S\": \"test\"}}"] }] }
```

---

**31. Use Terraform to deploy an AWS EKS cluster and Ansible to apply a Kubernetes deployment with GitHub Actions verification.**  
**Terraform:**  
```hcl
module "eks" { source = "terraform-aws-modules/eks/aws", cluster_name = "my-eks" }
```
**Ansible (`deploy_k8s.yml`):**  
```yaml
- name: Deploy K8s app
  hosts: localhost
  tasks:
    - name: Apply deployment
      ansible.builtin.command: kubectl apply -f deployment.yaml
```
**GitHub Actions (`.github/workflows/verify.yml`):**  
```yaml
name: Verify Deployment
on: push
jobs:
  verify:
    runs-on: ubuntu-latest
    steps:
      - run: kubectl get deployment
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```

---

**32. Configure GitHub Actions to deploy an AWS Lambda and use Kubernetes to log its invocations with `kubectl`.**  
**GitHub Actions (`.github/workflows/lambda.yml`):**  
```yaml
name: Deploy Lambda
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - run: aws lambda update-function-code --function-name my-lambda --zip-file fileb://lambda.zip
        env: { AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
      - run: kubectl apply -f logger.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```
**`logger.yaml`:**  
```yaml
apiVersion: v1
kind: Pod
metadata: { name: "lambda-logger" }
spec: { containers: [{ name: "logger", image: "amazon/aws-cli", command: ["aws", "logs", "filter-log-events", "--log-group-name", "/aws/lambda/my-lambda"] }] }
```

---

**33. Troubleshoot an AWS S3 access issue from a Kubernetes pod with `kubectl` and fix IAM with Terraform.**  
**Troubleshoot:**  
```bash
kubectl exec -it <pod-name> -- aws s3 ls s3://my-bucket  # Check access
kubectl describe pod <pod-name>  # Verify IAM role
```
**Terraform Fix:**  
```hcl
resource "aws_iam_role_policy" "s3_access" {
  role = "eks-node-role"
  policy = jsonencode({
    Statement = [{ Effect = "Allow", Action = "s3:*", Resource = "arn:aws:s3:::my-bucket/*" }]
  })
}
```

---

**34. Use Ansible to configure an AWS EC2 instance and deploy a Kubernetes CronJob from GitHub Actions.**  
**Ansible (`setup_ec2.yml`):**  
```yaml
- name: Configure EC2
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Install tools
      ansible.builtin.package: { name: curl, state: present }
```
**GitHub Actions (`.github/workflows/cronjob.yml`):**  
```yaml
name: Deploy CronJob
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - run: kubectl apply -f cronjob.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```
**`cronjob.yaml`:**  
```yaml
apiVersion: batch/v1
kind: CronJob
metadata: { name: "test-cron" }
spec:
  schedule: "*/5 * * * *"
  jobTemplate: { spec: { template: { spec: { containers: [{ name: "test", image: "busybox", command: ["echo", "Hello"] }] } } } }
```

---

**35. Deploy an AWS VPC with Terraform and use Ansible to set up Kubernetes networking with `kubectl`.**  
**Terraform:**  
```hcl
module "vpc" { source = "terraform-aws-modules/vpc/aws", vpc_name = "k8s-vpc" }
```
**Ansible (`setup_networking.yml`):**  
```yaml
- name: Setup K8s networking
  hosts: localhost
  tasks:
    - name: Apply CNI
      ansible.builtin.command: kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
```

---

**36. Configure GitHub Actions to run Terraform for an AWS ALB and expose a Kubernetes app with an Ingress.**  
**Terraform:**  
```hcl
resource "aws_lb" "app_lb" { name = "app-lb", load_balancer_type = "application", subnets = ["subnet-1", "subnet-2"] }
```
**GitHub Actions (`.github/workflows/alb_ingress.yml`):**  
```yaml
name: Deploy ALB and Ingress
on: push
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - run: terraform init && terraform apply -auto-approve
        env: { AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
      - run: kubectl apply -f ingress.yaml
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```
**`ingress.yaml`:**  
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata: { name: "app-ingress", annotations: { "alb.ingress.kubernetes.io/target-type": "ip" } }
spec: { rules: [{ http: { paths: [{ path: "/", pathType: "Prefix", backend: { service: { name: "app", port: { number: 80 } } } } ] } } ] }
```

---

**37. Use Terraform to create an AWS EBS volume and attach it to a Kubernetes pod with Ansible.**  
**Terraform:**  
```hcl
resource "aws_ebs_volume" "data" { availability_zone = "us-east-1a", size = 10 }
resource "aws_volume_attachment" "attach" { device_name = "/dev/xvdf", volume_id = aws_ebs_volume.data.id, instance_id = "i-123" }
```
**Ansible (`attach_ebs.yml`):**  
```yaml
- name: Attach EBS to pod
  hosts: localhost
  tasks:
    - name: Apply PVC
      ansible.builtin.command: kubectl apply -f pvc.yaml
```
**`pvc.yaml`:**  
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata: { name: "data-pvc" }
spec: { accessModes: ["ReadWriteOnce"], resources: { requests: { storage: "10Gi" } } }
```

---

**38. Troubleshoot a Kubernetes pod crash on EKS with `kubectl` and adjust AWS resources with Terraform.**  
**Troubleshoot:**  
```bash
kubectl logs <pod-name>  # Check crash reason
kubectl describe pod <pod-name>  # Look for OOM or resource limits
```
**Terraform Fix:**  
```hcl
resource "aws_eks_node_group" "fix" {
  cluster_name    = "my-eks"
  node_group_name = "fixed-nodes"
  scaling_config { desired_size = 2, max_size = 3, min_size = 1 }
  instance_types  = ["t3.large"]  # Increase instance size
}
```

---

**39. Deploy an AWS CloudFront distribution with Terraform and use GitHub Actions to sync Kubernetes assets.**  
**Terraform:**  
```hcl
resource "aws_cloudfront_distribution" "cdn" {
  origin { domain_name = "my-bucket.s3.amazonaws.com", origin_id = "S3-my-bucket" }
  enabled = true
  default_cache_behavior { target_origin_id = "S3-my-bucket", viewer_protocol_policy = "allow-all" }
}
```
**GitHub Actions (`.github/workflows/sync.yml`):**  
```yaml
name: Sync to CloudFront
on: push
jobs:
  sync:
    runs-on: ubuntu-latest
    steps:
      - run: aws s3 sync ./assets s3://my-bucket/
        env: { AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
```

---

**40. Use Ansible to install a Kubernetes cluster on AWS EC2 instances and verify with GitHub Actions.**  
**Ansible (`install_k8s.yml`):**  
```yaml
- name: Install Kubernetes
  hosts: ec2_hosts
  become: yes
  tasks:
    - name: Install kubeadm, kubelet, kubectl
      ansible.builtin.package: { name: "{{ item }}", state: present }
      loop: [kubeadm, kubelet, kubectl]
    - name: Initialize master
      ansible.builtin.command: kubeadm init --pod-network-cidr=10.244.0.0/16
      when: inventory_hostname == "master"
```
**GitHub Actions (`.github/workflows/verify.yml`):**  
```yaml
name: Verify K8s
on: push
jobs:
  verify:
    runs-on: ubuntu-latest
    steps:
      - run: kubectl get nodes
        env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
```

---