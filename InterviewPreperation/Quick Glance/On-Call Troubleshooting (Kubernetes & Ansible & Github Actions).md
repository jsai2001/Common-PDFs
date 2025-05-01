### Condensed Kubernetes, Ansible, GitHub Actions, and AWS Questions and Answers

#### Kubernetes Questions

1. **List pods in `default` namespace, `Running` state?**  
   ```bash
   kubectl get pods -n default --field-selector=status.phase=Running
   ```

2. **Check logs of a container in multi-container pod?**  
   ```bash
   kubectl logs <pod-name> -n <namespace> -c <container-name>
   ```

3. **Scale deployment `web-app` to 5 replicas?**  
   ```bash
   kubectl scale deployment web-app --replicas=5 -n <namespace>
   ```

4. **Troubleshoot pod stuck in `Pending`?**  
   ```bash
   kubectl get pod <pod-name> -n <namespace>
   kubectl describe pod <pod-name> -n <namespace>
   kubectl get nodes
   kubectl describe quota -n <namespace>
   kubectl logs -n kube-system -l component=kube-scheduler
   ```

5. **YAML for pod with `nginx`, port 80?**  
   ```yaml
   apiVersion: v1
   kind: Pod
   metadata: { name: nginx-pod }
   spec:
     containers:
     - name: nginx
       image: nginx:latest
       ports: [{ containerPort: 80 }]
   ```
   ```bash
   kubectl apply -f nginx-pod.yaml
   ```

6. **Check pod events?**  
   ```bash
   kubectl describe pod <pod-name> -n <namespace>
   ```

7. **Delete pods in `CrashLoopBackOff`?**  
   ```bash
   kubectl get pods -n <namespace> -o jsonpath='{range .items[*]}{.metadata.name}{"\t"}{.status.containerStatuses[0].state.waiting.reason}{"\n"}{end}' | grep CrashLoopBackOff | awk '{print $1}' | xargs kubectl delete pod -n <namespace>
   ```

8. **Drain node for maintenance?**  
   ```bash
   kubectl drain <node-name> --ignore-daemonsets --delete-emptydir-data
   kubectl uncordon <node-name>
   ```

9. **YAML for ConfigMap (`env=production`)?**  
   ```yaml
   apiVersion: v1
   kind: ConfigMap
   metadata: { name: app-config }
   data: { env: "production" }
   ```
   ```bash
   kubectl apply -f configmap.yaml
   ```

10. **Exec into `app-pod` to check processes?**  
    ```bash
    kubectl exec -it app-pod -n <namespace> -- /bin/sh -c "ps aux"
    ```

11. **Roll back deployment to previous revision?**  
    ```bash
    kubectl rollout undo deployment <deployment-name> -n <namespace>
    ```

12. **Debug `ImagePullBackOff`?**  
    ```bash
    kubectl get pod <pod-name> -n <namespace>
    kubectl describe pod <pod-name> -n <namespace>
    docker pull <image-name:tag>
    kubectl get secret <secret-name> -n <namespace>
    ```

13. **YAML for `ClusterIP` service, port 8080?**  
    ```yaml
    apiVersion: v1
    kind: Service
    metadata: { name: web-service }
    spec:
      selector: { app: web-app }
      ports: [{ port: 8080, targetPort: 8080 }]
      type: ClusterIP
    ```
    ```bash
    kubectl apply -f service.yaml
    ```

14. **Check pod CPU/memory usage?**  
    ```bash
    kubectl top pods -n <namespace>
    ```

15. **YAML for 10Gi PVC?**  
    ```yaml
    apiVersion: v1
    kind: PersistentVolumeClaim
    metadata: { name: my-pvc }
    spec:
      accessModes: [ReadWriteOnce]
      resources: { requests: { storage: 10Gi } }
      storageClassName: standard
    ```
    ```bash
    kubectl apply -f pvc.yaml
    ```

16. **Taint node to block pods?**  
    ```bash
    kubectl taint nodes <node-name> key=value:NoSchedule
    ```

17. **Describe service, check endpoints?**  
    ```bash
    kubectl describe service <service-name> -n <namespace>
    kubectl get endpoints <service-name> -n <namespace>
    ```

18. **Investigate deployment not updating?**  
    ```bash
    kubectl get deployment <deployment-name> -n <namespace>
    kubectl describe deployment <deployment-name> -n <namespace>
    kubectl get rs -n <namespace>
    kubectl rollout history deployment <deployment-name> -n <namespace>
    kubectl rollout restart deployment <deployment-name> -n <namespace>
    ```

19. **YAML for CronJob, `alpine` echoing "Hello" every 5min?**  
    ```yaml
    apiVersion: batch/v1
    kind: CronJob
    metadata: { name: hello-cronjob }
    spec:
      schedule: "*/5 * * * *"
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
    ```bash
    kubectl apply -f cronjob.yaml
    ```

20. **Patch deployment with env variable?**  
    ```bash
    kubectl patch deployment <deployment-name> -n <namespace> --type='json' -p='[{"op": "add", "path": "/spec/template/spec/containers/0/env/-", "value": {"name": "NEW_VAR", "value": "new-value"}}]'
    ```

21. **YAML for `staging` namespace, label `env=staging`?**  
    ```yaml
    apiVersion: v1
    kind: Namespace
    metadata:
      name: staging
      labels: { env: staging }
    ```
    ```bash
    kubectl apply -f namespace.yaml
    ```

22. **Cordon node, reschedule pods?**  
    ```bash
    kubectl cordon <node-name>
    kubectl drain <node-name> --ignore-daemonsets --delete-emptydir-data
    ```

23. **Check ReplicaSet status?**  
    ```bash
    kubectl get rs <replicaset-name> -n <namespace>
    kubectl describe rs <replicaset-name> -n <namespace>
    ```

24. **Force delete pod stuck in `Terminating`?**  
    ```bash
    kubectl delete pod <pod-name> -n <namespace> --force --grace-period=0
    ```

25. **YAML for Ingress routing `/api`?**  
    ```yaml
    apiVersion: networking.k8s.io/v1
    kind: Ingress
    metadata: { name: api-ingress }
    spec:
      rules:
      - http:
          paths:
          - path: /api
            pathType: Prefix
            backend: { service: { name: api-service, port: { number: 8080 } } }
    ```
    ```bash
    kubectl apply -f ingress.yaml
    ```

#### Ansible Questions

26. **Ad-hoc command to ping all hosts?**  
    ```bash
    ansible all -m ping
    ```

27. **Playbook to install `httpd` on `webservers`?**  
    ```yaml
    - name: Install httpd
      hosts: webservers
      become: yes
      tasks:
      - name: Install httpd
        ansible.builtin.yum: { name: httpd, state: present }
    ```
    ```bash
    ansible-playbook install_httpd.yml
    ```

28. **Copy `config.conf` to `/etc/app/`?**  
    ```bash
    ansible all -m copy -a "src=config.conf dest=/etc/app/config.conf owner=root mode=0644" --become
    ```

29. **Playbook to start/enable `nginx`?**  
    ```yaml
    - name: Manage nginx
      hosts: all
      become: yes
      tasks:
      - name: Install nginx
        ansible.builtin.package: { name: nginx, state: present }
      - name: Start nginx
        ansible.builtin.service: { name: nginx, state: started, enabled: yes }
    ```
    ```bash
    ansible-playbook nginx_service.yml
    ```

30. **Inventory with `webservers`, `dbservers` (2 hosts each)?**  
    ```ini
    [webservers]
    web1.example.com ansible_host=192.168.1.10
    web2.example.com ansible_host=192.168.1.11
    [dbservers]
    db1.example.com ansible_host=192.168.1.20
    db2.example.com ansible_host=192.168.1.21
    ```

31. **Check uptime of `dbservers`?**  
    ```bash
    ansible dbservers -m command -a "uptime"
    ```

32. **Restart `webservers` sequentially?**  
    ```yaml
    - name: Restart webservers
      hosts: webservers
      become: yes
      serial: 1
      tasks:
      - name: Restart
        ansible.builtin.reboot:
    ```
    ```bash
    ansible-playbook reboot_webservers.yml
    ```

33. **Playbook for user `devops`, UID 2000?**  
    ```yaml
    - name: Create devops user
      hosts: all
      become: yes
      tasks:
      - name: Create user
        ansible.builtin.user: { name: devops, uid: 2000, state: present }
    ```
    ```bash
    ansible-playbook create_user.yml
    ```

34. **Task to set fact based on OS distribution?**  
    ```yaml
    - name: Set OS fact
      hosts: all
      tasks:
      - name: Set fact
        ansible.builtin.set_fact: { os_dist: "{{ ansible_facts['distribution'] }}" }
      - name: Debug
        ansible.builtin.debug: { var: os_dist }
    ```

35. **Run shell command, register output?**  
    ```yaml
    - name: Run shell
      hosts: all
      tasks:
      - name: Get disk space
        ansible.builtin.shell: "df -h /"
        register: disk_space
      - name: Debug
        ansible.builtin.debug: { var: disk_space.stdout }
    ```
    ```bash
    ansible-playbook shell_command.yml
    ```

36. **Playbook to deploy template `app.conf.j2`?**  
    ```yaml
    - name: Deploy template
      hosts: all
      become: yes
      tasks:
      - name: Deploy
        ansible.builtin.template: { src: app.conf.j2, dest: /etc/app.conf, owner: root, mode: "0644" }
    ```
    ```bash
    ansible-playbook deploy_template.yml
    ```

37. **Update package cache on Ubuntu?**  
    ```bash
    ansible all -m apt -a "update_cache=yes" --become -l ubuntu_hosts
    ```

38. **Check if file exists, debug message?**  
    ```yaml
    - name: Check file
      hosts: all
      tasks:
      - name: Check file
        ansible.builtin.stat: { path: /etc/app.conf }
        register: file_status
      - name: Debug
        ansible.builtin.debug: { msg: "File exists" }
        when: file_status.stat.exists
    ```
    ```bash
    ansible-playbook check_file.yml
    ```

39. **Playbook with handler to restart `httpd`?**  
    ```yaml
    - name: Manage httpd
      hosts: webservers
      become: yes
      tasks:
      - name: Deploy config
        ansible.builtin.copy: { src: httpd.conf, dest: /etc/httpd/conf/httpd.conf, owner: root }
        notify: Restart httpd
      handlers:
      - name: Restart httpd
        ansible.builtin.service: { name: httpd, state: restarted }
    ```
    ```bash
    ansible-playbook httpd_config.yml
    ```

40. **Task to loop over package list?**  
    ```yaml
    - name: Install packages
      hosts: all
      become: yes
      vars: { packages: [nginx, git, curl] }
      tasks:
      - name: Install
        ansible.builtin.package: { name: "{{ item }}", state: present }
        loop: "{{ packages }}"
    ```
    ```bash
    ansible-playbook install_packages.yml
    ```

41. **Run task only on Debian?**  
    ```yaml
    - name: Debian task
      hosts: all
      tasks:
      - name: Update cache
        ansible.builtin.apt: { update_cache: yes }
        when: ansible_os_family == "Debian"
    ```
    ```bash
    ansible-playbook debian_task.yml
    ```

42. **Playbook for daily cron job at midnight?**  
    ```yaml
    - name: Setup cron
      hosts: all
      become: yes
      tasks:
      - name: Create cron
        ansible.builtin.cron: { name: "Daily backup", minute: "0", hour: "0", job: "/usr/local/bin/backup.sh" }
    ```
    ```bash
    ansible-playbook cron_backup.yml
    ```

43. **Gather facts, save to file?**  
    ```bash
    ansible all -m setup -a "gather_subset=all" --tree /tmp/facts
    ```

44. **Delegate task to localhost?**  
    ```yaml
    - name: Delegate to localhost
      hosts: all
      tasks:
      - name: Run locally
        ansible.builtin.command: "echo {{ inventory_hostname }} >> /tmp/host_list"
        delegate_to: localhost
    ```
    ```bash
    ansible-playbook delegate_task.yml
    ```

45. **Playbook to remove `/tmp/old_data`?**  
    ```yaml
    - name: Remove directory
      hosts: all
      become: yes
      tasks:
      - name: Remove
        ansible.builtin.file: { path: /tmp/old_data, state: absent }
    ```
    ```bash
    ansible-playbook remove_dir.yml
    ```

46. **Task to include tasks conditionally?**  
    ```yaml
    - name: Include tasks
      hosts: all
      tasks:
      - name: Include for Debian
        ansible.builtin.include_tasks: { file: debian_tasks.yml }
        when: ansible_os_family == "Debian"
    ```

47. **Run playbook with `deploy` tag?**  
    ```bash
    ansible-playbook playbook.yml --tags "deploy"
    ```

48. **Ensure line in `/etc/hosts`?**  
    ```yaml
    - name: Update hosts
      hosts: all
      become: yes
      tasks:
      - name: Add line
        ansible.builtin.lineinfile: { path: /etc/hosts, line: "192.168.1.100 app.example.com" }
    ```
    ```bash
    ansible-playbook update_hosts.yml
    ```

49. **Reboot hosts, wait for return?**  
    ```bash
    ansible all -m reboot -a "reboot_timeout=300" --become
    ```

50. **Troubleshoot playbook with verbose output?**  
    ```bash
    ansible-playbook playbook.yml -v
    ```

#### GitHub Actions Questions

51. **Workflow to lint on push to `main`?**  
    ```yaml
    name: Lint Code
    on: { push: { branches: [main] } }
    jobs:
      lint:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: actions/setup-node@v3
          with: { node-version: '16' }
        - run: npm install
        - run: npm run lint
    ```

52. **Build/push Docker image to Docker Hub?**  
    ```yaml
    name: Build Docker
    on: { push: { branches: [main] } }
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: docker/login-action@v2
          with: { username: ${{ secrets.DOCKER_USERNAME }}, password: ${{ secrets.DOCKER_PASSWORD }} }
        - run: |
            docker build -t ${{ secrets.DOCKER_USERNAME }}/my-app:latest .
            docker push ${{ secrets.DOCKER_USERNAME }}/my-app:latest
    ```

53. **Run tests on PR to `develop`?**  
    ```yaml
    name: Test PR
    on: { pull_request: { branches: [develop] } }
    jobs:
      test:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: actions/setup-python@v4
          with: { python-version: '3.9' }
        - run: pip install -r requirements.txt
        - run: pytest
    ```

54. **Deploy static site to GitHub Pages on tag?**  
    ```yaml
    name: Deploy Pages
    on: { push: { tags: ['v*'] } }
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: npm install && npm run build
        - uses: peaceiris/actions-gh-pages@v3
          with: { github_token: ${{ secrets.GITHUB_TOKEN }}, publish_dir: ./dist }
    ```

55. **Set env variable for job steps?**  
    ```yaml
    name: Set Env
    on: { push: { branches: [main] } }
    jobs:
      build:
        runs-on: ubuntu-latest
        env: { MY_VAR: "hello-world" }
        steps:
        - run: echo $MY_VAR
    ```

56. **Run script every Monday at 9 AM?**  
    ```yaml
    name: Weekly Script
    on: { schedule: [{ cron: '0 9 * * 1' }] }
    jobs:
      run-script:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: ./scripts/my_script.sh
    ```

57. **Checkout repo, list files?**  
    ```yaml
    name: List Files
    on: { push: { branches: [main] } }
    jobs:
      list:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: ls -la
    ```

58. **Matrix test on multiple OS?**  
    ```yaml
    name: Matrix Test
    on: { push: { branches: [main] } }
    jobs:
      test:
        runs-on: ${{ matrix.os }}
        strategy: { matrix: { os: [ubuntu-latest, macos-latest, windows-latest] } }
        steps:
        - uses: actions/checkout@v3
        - run: echo "Testing on ${{ matrix.os }}"
    ```

59. **Cancel in-progress runs on new push?**  
    ```yaml
    name: Cancel Runs
    on: { push: { branches: [main] } }
    concurrency: { group: ${{ github.workflow }}-${{ github.ref }}, cancel-in-progress: true }
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: echo "Building..."
    ```

60. **Upload artifact after build?**  
    ```yaml
    name: Upload Artifact
    on: { push: { branches: [main] } }
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: zip -r build.zip .
        - uses: actions/upload-artifact@v3
          with: { name: build-artifact, path: build.zip }
    ```

61. **Trigger workflow manually with inputs?**  
    ```yaml
    name: Manual Workflow
    on:
      workflow_dispatch:
        inputs:
          environment: { description: 'Environment', required: true, default: 'staging' }
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - run: echo "Deploying to ${{ github.event.inputs.environment }}"
    ```

62. **Run security scan after build?**  
    ```yaml
    name: Build and Scan
    on: { push: { branches: [main] } }
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: echo "Building..."
      scan:
        needs: build
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: echo "Scanning..."
    ```

63. **Use custom Docker container as runner?**  
    ```yaml
    name: Custom Container
    on: { push: { branches: [main] } }
    jobs:
      custom:
        runs-on: ubuntu-latest
        container: { image: node:16 }
        steps:
        - uses: actions/checkout@v3
        - run: node --version
    ```

64. **Use secret in workflow step?**  
    ```yaml
    name: Use Secret
    on: { push: { branches: [main] } }
    jobs:
      run:
        runs-on: ubuntu-latest
        steps:
        - env: { API_KEY: ${{ secrets.API_KEY }} }
          run: echo "Using API_KEY: $API_KEY"
    ```

65. **Send Slack notification on failure?**  
    ```yaml
    name: Notify Failure
    on: { push: { branches: [main] } }
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: exit 1
        - if: failure()
          uses: slackapi/slack-github-action@v1.23.0
          with: { slack-bot-token: ${{ secrets.SLACK_BOT_TOKEN }}, channel-id: 'general', text: 'Workflow failed!' }
    ```

66. **Cache dependencies between runs?**  
    ```yaml
    name: Cache Dependencies
    on: { push: { branches: [main] } }
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: actions/cache@v3
          with: { path: ~/.npm, key: ${{ runner.os }}-node-${{ hashFiles('**/package-lock.json') }} }
        - run: npm install
    ```

67. **Run jobs conditionally by branch?**  
    ```yaml
    name: Conditional Job
    on: { push: { branches: ['*'] } }
    jobs:
      build:
        if: github.ref == 'refs/heads/main'
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: echo "Building on main"
    ```

68. **Build/test Go app on push?**  
    ```yaml
    name: Build Go
    on: { push: { branches: ['*'] } }
    jobs:
      build-test:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: actions/setup-go@v3
          with: { go-version: '1.18' }
        - run: go build ./...
        - run: go test ./...
    ```

69. **Run command if `README.md` changes?**  
    ```yaml
    name: Check README
    on: { push: { branches: [main] } }
    jobs:
      check:
        if: contains(github.event.commits[0].modified, 'README.md')
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: echo "README.md modified!"
    ```

70. **Use self-hosted runner?**  
    ```yaml
    name: Self-Hosted
    on: { push: { branches: [main] } }
    jobs:
      build:
        runs-on: self-hosted
        steps:
        - uses: actions/checkout@v3
        - run: echo "Running on self-hosted"
    ```

71. **Run linter, upload output?**  
    ```yaml
    name: Lint and Upload
    on: { push: { branches: [main] } }
    jobs:
      lint:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: npm install && npm run lint > lint-output.txt 2>&1
        - uses: actions/upload-artifact@v3
          with: { name: lint-report, path: lint-output.txt }
    ```

72. **Set/use output variable across steps?**  
    ```yaml
    name: Set Output
    on: { push: { branches: [main] } }
    jobs:
      example:
        runs-on: ubuntu-latest
        steps:
        - id: step1
          run: echo "my_output=hello" >> $GITHUB_OUTPUT
        - run: echo "Output: ${{ steps.step1.outputs.my_output }}"
    ```

73. **Schedule workflow every 15 minutes?**  
    ```yaml
    name: Scheduled Workflow
    on: { schedule: [{ cron: '*/15 * * * *' }] }
    jobs:
      run:
        runs-on: ubuntu-latest
        steps:
        - run: echo "Running at $(date)"
    ```

74. **Clean old artifacts after run?**  
    ```yaml
    name: Clean Artifacts
    on: { push: { branches: [main] } }
    jobs:
      build:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: echo "Building..."
        - if: success()
          uses: actions/delete-package-versions@v4
          with: { package-type: 'artifact', min-versions-to-keep: 1 }
    ```

75. **Clone repo from another org, run script?**  
    ```yaml
    name: Clone External
    on: { push: { branches: [main] } }
    jobs:
      clone:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
          with: { repository: other-org/other-repo, token: ${{ secrets.PAT }} }
        - run: ./script.sh
    ```

#### Mixed Questions

76. **Ansible to apply K8s manifest, verify?**  
    ```yaml
    - name: Apply K8s
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f pod.yaml
      - name: Verify
        ansible.builtin.command: kubectl get pod nginx-pod -o jsonpath='{.status.phase}'
        register: pod_status
      - name: Fail if not running
        ansible.builtin.fail: { msg: "Pod not Running" }
        when: pod_status.stdout != "Running"
    ```
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: nginx-pod }
    spec: { containers: [{ name: nginx, image: nginx:latest }] }
    ```

77. **GitHub Actions to run Ansible playbook on push?**  
    ```yaml
    name: Run Ansible
    on: { push: { branches: [main] } }
    jobs:
      ansible:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: sudo apt update && sudo apt install -y ansible
        - run: ansible-playbook playbook.yml -i inventory.ini
    ```
    ```yaml
    - name: Test
      hosts: all
      tasks:
      - name: Ping
        ansible.builtin.ping:
    ```

78. **Troubleshoot Ansible-deployed pod not running?**  
    ```bash
    kubectl get pod <pod-name> -n <namespace>
    kubectl describe pod <pod-name> -n <namespace>
    kubectl logs <pod-name> -n <namespace>
    kubectl get pod <pod-name> -n <namespace> -o yaml
    ```

79. **Ansible to install `kubectl`, check cluster?**  
    ```yaml
    - name: Install kubectl
      hosts: all
      become: yes
      tasks:
      - name: Install
        ansible.builtin.shell: |
          curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
          chmod +x kubectl && mv kubectl /usr/local/bin/
        args: { creates: /usr/local/bin/kubectl }
      - name: Check cluster
        ansible.builtin.command: kubectl cluster-info
        register: cluster_info
      - name: Debug
        ansible.builtin.debug: { var: cluster_info.stdout }
    ```
    ```bash
    ansible-playbook install_kubectl.yml
    ```

80. **GitHub Actions to scale K8s deployment on tag?**  
    ```yaml
    name: Scale K8s
    on: { push: { tags: ['v*'] } }
    jobs:
      scale:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: azure/setup-kubectl@v3
        - run: echo "${{ secrets.KUBECONFIG }}" > $HOME/.kube/config
        - run: kubectl scale deployment web-app --replicas=5 -n default
    ```

81. **Ansible to update ConfigMap, restart pods?**  
    ```yaml
    - name: Update ConfigMap
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f configmap.yaml
      - name: Restart
        ansible.builtin.command: kubectl rollout restart deployment web-app -n default
    ```
    ```yaml
    apiVersion: v1
    kind: ConfigMap
    metadata: { name: app-config }
    data: { env: "production" }
    ```

82. **GitHub Actions with Ansible to configure K8s node, verify?**  
    ```yaml
    name: Configure Node
    on: { push: { branches: [main] } }
    jobs:
      configure:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: sudo apt update && sudo apt install -y ansible
        - run: ansible-playbook -i inventory.ini configure_node.yml
        - uses: azure/setup-kubectl@v3
        - run: echo "${{ secrets.KUBECONFIG }}" > $HOME/.kube/config
        - run: kubectl get nodes
    ```
    ```yaml
    - name: Configure node
      hosts: k8s_nodes
      become: yes
      tasks:
      - name: Install kubeadm
        ansible.builtin.package: { name: kubeadm, state: present }
    ```

83. **Ansible task to check pod logs, fail on errors?**  
    ```yaml
    - name: Check logs
      hosts: localhost
      tasks:
      - name: Get logs
        ansible.builtin.command: kubectl logs <pod-name> -n <namespace>
        register: pod_logs
      - name: Fail on error
        ansible.builtin.fail: { msg: "Errors in logs" }
        when: "'error' in pod_logs.stdout | lower"
    ```

84. **GitHub Actions to restart K8s rollout on config change?**  
    ```yaml
    name: Restart Deployment
    on: { push: { branches: [main], paths: ['config/*'] } }
    jobs:
      restart:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: azure/setup-kubectl@v3
        - run: echo "${{ secrets.KUBECONFIG }}" > $HOME/.kube/config
        - run: kubectl apply -f config/configmap.yaml
        - run: kubectl rollout restart deployment web-app -n default
    ```

85. **Ansible to deploy K8s service, expose externally?**  
    ```yaml
    - name: Deploy service
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f service.yaml
      - name: Expose
        ansible.builtin.command: kubectl expose service web-service --type=LoadBalancer --port=80 --name=web-lb -n default
    ```
    ```yaml
    apiVersion: v1
    kind: Service
    metadata: { name: web-service }
    spec:
      selector: { app: web-app }
      ports: [{ port: 80, targetPort: 8080 }]
      type: ClusterIP
    ```

#### AWS Integration Questions

86. **Deploy K8s pod on EKS with Terraform, verify?**  
    ```hcl
    module "eks" { source = "terraform-aws-modules/eks/aws", cluster_name = "my-eks" }
    resource "kubernetes_pod" "nginx" {
      metadata: { name: "nginx-pod" }
      spec: { container: [{ image: "nginx:latest", name: "nginx" }] }
    }
    ```
    ```bash
    terraform apply
    aws eks update-kubeconfig --name my-eks
    kubectl get pod nginx-pod
    ```

87. **Ansible to install Docker on EC2, GitHub Actions to deploy K8s manifest?**  
    ```yaml
    - name: Install Docker
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.package: { name: docker.io, state: present }
      - name: Start
        ansible.builtin.service: { name: docker, state: started }
    ```
    ```yaml
    name: Deploy K8s
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
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: "test-pod" }
    spec: { containers: [{ name: "test", image: "nginx" }] }
    ```

88. **Terraform for S3 bucket, GitHub Actions to upload files?**  
    ```hcl
    resource "aws_s3_bucket" "my_bucket" { bucket = "my-unique-bucket-123" }
    ```
    ```yaml
    name: Upload S3
    on: push
    jobs:
      upload:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: aws-actions/configure-aws-credentials@v2
          with: { aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}, aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
        - run: aws s3 cp file.txt s3://my-unique-bucket-123/
    ```

89. **Troubleshoot K8s pod on EKS, update AWS SG with Terraform?**  
    ```bash
    kubectl get pod <pod-name>
    kubectl describe pod <pod-name>
    kubectl logs <pod-name>
    ```
    ```hcl
    resource "aws_security_group_rule" "allow_pod" {
      type = "ingress"
      from_port = 80
      to_port = 80
      protocol = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
      security_group_id = "sg-123"
    }
    ```

90. **Ansible to configure EC2, Terraform for K8s service?**  
    ```yaml
    - name: Configure EC2
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.package: { name: curl, state: present }
    ```
    ```hcl
    resource "kubernetes_service" "web" {
      metadata: { name: "web-service" }
      spec:
        selector: { app: "web" }
        port: [{ port: 80, target_port: "8080" }]
        type: "LoadBalancer"
    }
    ```

91. **GitHub Actions for AWS Lambda, K8s pod to monitor?**  
    ```yaml
    name: Deploy Lambda
    on: push
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - uses: aws-actions/configure-aws-credentials@v2
          with: { aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}, aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
        - run: zip -r lambda.zip . && aws lambda update-function-code --function-name my-lambda --zip-file fileb://lambda.zip
    ```
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: "lambda-monitor" }
    spec: { containers: [{ name: "monitor", image: "amazon/aws-cli", command: ["aws", "lambda", "get-function", "--function-name", "my-lambda"] }] }
    ```

92. **Terraform for ASG, Ansible for K8s agent?**  
    ```hcl
    module "asg" {
      source = "terraform-aws-modules/autoscaling/aws"
      name = "my-asg"
      min_size = 1
      max_size = 3
      vpc_zone_identifier = ["subnet-1"]
    }
    ```
    ```yaml
    - name: Install K8s agent
      hosts: asg_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.package: { name: kubeadm, state: present }
    ```

93. **GitHub Actions for Terraform EKS, scale with `kubectl`?**  
    ```yaml
    name: Deploy EKS
    on: push
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - uses: hashicorp/setup-terraform@v2
        - run: terraform init && terraform apply -auto-approve
        - uses: azure/setup-kubectl@v3
        - run: |
            aws eks update-kubeconfig --name my-eks
            kubectl scale deployment web-app --replicas=3
          env: { AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
    ```

94. **Troubleshoot RDS connection from K8s pod, Terraform fix?**  
    ```bash
    kubectl exec -it <pod-name> -- ping <rds-endpoint>
    kubectl describe pod <pod-name>
    ```
    ```hcl
    resource "aws_security_group_rule" "rds_access" {
      type = "ingress"
      from_port = 3306
      to_port = 3306
      protocol = "tcp"
      source_security_group_id = "sg-pod"
      security_group_id = "sg-rds"
    }
    ```

95. **Ansible to update EC2, GitHub Actions to test K8s?**  
    ```yaml
    - name: Update EC2
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Update
        ansible.builtin.yum: { name: "*", state: latest }
    ```
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

96. **Terraform for S3 and ConfigMap, Ansible to sync?**  
    ```hcl
    resource "aws_s3_bucket" "config" { bucket = "config-bucket-123" }
    resource "kubernetes_configmap" "app" {
      metadata: { name: "app-config" }
      data: { key: "value" }
    }
    ```
    ```yaml
    - name: Sync ConfigMap
      hosts: localhost
      tasks:
      - name: Export
        ansible.builtin.command: kubectl get configmap app-config -o yaml > config.yaml
      - name: Upload
        amazon.aws.aws_s3: { bucket: config-bucket-123, src: config.yaml, dest: config.yaml, mode: put }
    ```

97. **Terraform for ECS, GitHub Actions for K8s alternative?**  
    ```hcl
    module "ecs" { source = "terraform-aws-modules/ecs/aws", cluster_name = "my-ecs" }
    ```
    ```yaml
    name: Deploy K8s
    on: push
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - uses: actions/checkout@v3
        - run: kubectl apply -f k8s_deployment.yaml
          env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
    ```

98. **Terraform for ALB, expose K8s service with `kubectl`?**  
    ```hcl
    resource "aws_lb" "my_alb" { name = "my-alb", load_balancer_type = "application", subnets = ["subnet-1", "subnet-2"] }
    ```
    ```bash
    kubectl expose service web-service --type=LoadBalancer --name=web-lb --port=80
    kubectl annotate service web-lb "service.beta.kubernetes.io/aws-load-balancer-arn=arn:aws:elasticloadbalancing:us-east-1:123:loadbalancer/app/my-alb/abc123"
    ```

99. **Ansible to configure EC2 as K8s node, GitHub Actions to verify?**  
    ```yaml
    - name: Configure K8s
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.package: { name: kubelet, state: present }
    ```
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

100. **Troubleshoot K8s pod failing ECR pull, Terraform fix?**  
    ```bash
    kubectl describe pod <pod-name>
    aws ecr get-login-password | docker login --username AWS --password-stdin <ecr-repo>
    kubectl create secret docker-registry ecr-secret --docker-server=<ecr-repo> --docker-username=AWS --docker-password=<password>
    ```
    ```hcl
    resource "kubernetes_pod" "fix" { spec: { image_pull_secrets: [{ name: "ecr-secret" }] } }
    ```

101. **Terraform for EKS, Ansible for monitoring pod?**  
    ```hcl
    module "eks" { source = "terraform-aws-modules/eks/aws", cluster_name = "monitoring-eks" }
    ```
    ```yaml
    - name: Deploy monitor
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f monitor.yaml
    ```
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: "monitor" }
    spec: { containers: [{ name: "prometheus", image: "prom/prometheus" }] }
    ```

102. **Terraform for CloudWatch alarm, Ansible to alert K8s pod?**  
    ```hcl
    resource "aws_cloudwatch_metric_alarm" "cpu" {
      alarm_name = "high-cpu"
      comparison_operator = "GreaterThanThreshold"
      threshold = "80"
      metric_name = "CPUUtilization"
      namespace = "AWS/EC2"
    }
    ```
    ```yaml
    - name: Alert pod
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f alert.yaml
    ```
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: "alert-pod" }
    spec: { containers: [{ name: "alert", image: "busybox", command: ["sh", "-c", "echo Alarm && sleep 3600"] }] }
    ```

103. **GitHub Actions for Terraform RDS, connect K8s app?**  
    ```hcl
    resource "aws_db_instance" "db" { identifier = "my-rds", engine = "mysql", instance_class = "db.t3.micro" }
    ```
    ```yaml
    name: Deploy RDS
    on: push
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - uses: hashicorp/setup-terraform@v2
        - run: terraform init && terraform apply -auto-approve
        - run: kubectl apply -f app.yaml
          env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
    ```
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: "app" }
    spec: { containers: [{ name: "app", image: "mysql:5.7", env: [{ name: "MYSQL_HOST", value: "my-rds.endpoint" }] }] }
    ```

104. **Terraform for EC2, Ansible to join K8s cluster?**  
    ```hcl
    resource "aws_instance" "k8s_node" { ami = "ami-0c55b159cbfafe1f0", instance_type = "t2.medium" }
    ```
    ```yaml
    - name: Join K8s
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.package: { name: kubeadm, state: present }
      - name: Join
        ansible.builtin.command: kubeadm join <master-ip>:6443 --token <token> --discovery-token-ca-cert-hash <hash>
    ```

105. **Troubleshoot K8s pod networking on EKS, Terraform VPC fix?**  
    ```bash
    kubectl exec -it <pod-name> -- ping 8.8.8.8
    kubectl describe pod <pod-name>
    ```
    ```hcl
    resource "aws_vpc" "eks_vpc" { cidr_block = "10.0.0.0/16", enable_dns_hostnames = true }
    resource "aws_route" "internet" { route_table_id = "rt-123", destination_cidr_block = "0.0.0.0/0", gateway_id = "igw-123" }
    ```

106. **GitHub Actions for Ansible EC2, deploy K8s ingress?**  
    ```yaml
    - name: Setup EC2
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.package: { name: nginx, state: present }
    ```
    ```yaml
    name: Deploy Ingress
    on: push
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - run: ansible-playbook -i inventory.ini setup_ec2.yml
        - run: kubectl apply -f ingress.yaml
          env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
    ```
    ```yaml
    apiVersion: networking.k8s.io/v1
    kind: Ingress
    metadata: { name: "web-ingress" }
    spec: { rules: [{ http: { paths: [{ path: "/", pathType: "Prefix", backend: { service: { name: "web", port: { number: 80 } } } } ] } } ] }
    ```

107. **Terraform for SQS, K8s to process with Ansible?**  
    ```hcl
    resource "aws_sqs_queue" "my_queue" { name = "my-queue" }
    ```
    ```yaml
    - name: Deploy processor
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f processor.yaml
    ```
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: "sqs-processor" }
    spec: { containers: [{ name: "processor", image: "amazon/aws-cli", command: ["aws", "sqs", "receive-message", "--queue-url", "<queue-url>"] }] }
    ```

108. **Terraform for EKS, GitHub Actions to test pod scaling?**  
    ```hcl
    module "eks" { source = "terraform-aws-modules/eks/aws", cluster_name = "test-eks" }
    ```
    ```yaml
    name: Test Scaling
    on: push
    jobs:
      scale:
        runs-on: ubuntu-latest
        steps:
        - run: |
            aws eks update-kubeconfig --name test-eks
            kubectl scale deployment test-app --replicas=5
          env: { AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}, AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
    ```

109. **Ansible to update EC2 SG, GitHub Actions for K8s service?**  
    ```yaml
    - name: Update SG
      hosts: localhost
      tasks:
      - name: Add rule
        amazon.aws.ec2_security_group: { name: "my-sg", rules: [{ proto: "tcp", from_port: 80, to_port: 80, cidr_ip: "0.0.0.0/0" }] }
    ```
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

110. **Troubleshoot AWS ELB not routing to K8s pods?**  
    ```bash
    kubectl get svc <service-name> -o wide
    kubectl describe pod <pod-name>
    ```
    ```hcl
    resource "aws_lb_target_group_attachment" "fix" { target_group_arn = "arn:aws:elasticloadbalancing:us-east-1:123:targetgroup/my-tg/abc", target_id = "i-123" }
    ```

111. **Terraform for DynamoDB, Ansible to populate from K8s pod?**  
    ```hcl
    resource "aws_dynamodb_table" "my_table" { name = "my-table", billing_mode = "PAY_PER_REQUEST", hash_key = "id", attribute: [{ name: "id", type: "S" }] }
    ```
    ```yaml
    - name: Populate Dynamo
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f populator.yaml
    ```
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: "dynamo-populator" }
    spec: { containers: [{ name: "populator", image: "amazon/aws-cli", command: ["aws", "dynamodb", "put-item", "--table-name", "my-table", "--item", "{\"id\": {\"S\": \"1\"}, \"data\": {\"S\": \"test\"}}"] }] }
    ```

112. **Terraform for EKS, Ansible for K8s deploy, GitHub Actions verify?**  
    ```hcl
    module "eks" { source = "terraform-aws-modules/eks/aws", cluster_name = "my-eks" }
    ```
    ```yaml
    - name: Deploy K8s
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f deployment.yaml
    ```
    ```yaml
    name: Verify
    on: push
    jobs:
      verify:
        runs-on: ubuntu-latest
        steps:
        - run: kubectl get deployment
          env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
    ```

113. **GitHub Actions for AWS Lambda, K8s to log invocations?**  
    ```yaml
    name: Deploy Lambda
    on: push
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - uses: aws-actions/configure-aws-credentials@v2
          with: { aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}, aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
        - run: aws lambda update-function-code --function-name my-lambda --zip-file fileb://lambda.zip
        - run: kubectl apply -f logger.yaml
          env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
    ```
    ```yaml
    apiVersion: v1
    kind: Pod
    metadata: { name: "lambda-logger" }
    spec: { containers: [{ name: "logger", image: "amazon/aws-cli", command: ["aws", "logs", "filter-log-events", "--log-group-name", "/aws/lambda/my-lambda"] }] }
    ```

114. **Troubleshoot S3 access from K8s pod, Terraform IAM fix?**  
    ```bash
    kubectl exec -it <pod-name> -- aws s3 ls s3://my-bucket
    kubectl describe pod <pod-name>
    ```
    ```hcl
    resource "aws_iam_role_policy" "s3_access" {
      role = "eks-node-role"
      policy = jsonencode({ Statement: [{ Effect: "Allow", Action: "s3:*", Resource: "arn:aws:s3:::my-bucket/*" }] })
    }
    ```

115. **Ansible for EC2, GitHub Actions for K8s CronJob?**  
    ```yaml
    - name: Configure EC2
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.package: { name: curl, state: present }
    ```
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
    ```yaml
    apiVersion: batch/v1
    kind: CronJob
    metadata: { name: "test-cron" }
    spec:
      schedule: "*/5 * * * *"
      jobTemplate: { spec: { template: { spec: { containers: [{ name: "test", image: "busybox", command: ["echo", "Hello"] }] } } } }
    ```

116. **Terraform for VPC, Ansible for K8s networking?**  
    ```hcl
    module "vpc" { source = "terraform-aws-modules/vpc/aws", vpc_name = "k8s-vpc" }
    ```
    ```yaml
    - name: Setup networking
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
    ```

117. **GitHub Actions for Terraform ALB, K8s Ingress?**  
    ```hcl
    resource "aws_lb" "app_lb" { name = "app-lb", load_balancer_type = "application", subnets = ["subnet-1", "subnet-2"] }
    ```
    ```yaml
    name: Deploy ALB
    on: push
    jobs:
      deploy:
        runs-on: ubuntu-latest
        steps:
        - uses: hashicorp/setup-terraform@v2
        - run: terraform init && terraform apply -auto-approve
        - run: kubectl apply -f ingress.yaml
          env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
    ```
    ```yaml
    apiVersion: networking.k8s.io/v1
    kind: Ingress
    metadata: { name: "app-ingress" }
    spec: { rules: [{ http: { paths: [{ path: "/", pathType: "Prefix", backend: { service: { name: "app", port: { number: 80 } } } } ] } } ] }
    ```

118. **Terraform for EBS, Ansible to attach to K8s pod?**  
    ```hcl
    resource "aws_ebs_volume" "data" { availability_zone = "us-east-1a", size = 10 }
    resource "aws_volume_attachment" "attach" { device_name = "/dev/xvdf", volume_id = aws_ebs_volume.data.id, instance_id = "i-123" }
    ```
    ```yaml
    - name: Attach EBS
      hosts: localhost
      tasks:
      - name: Apply
        ansible.builtin.command: kubectl apply -f pvc.yaml
    ```
    ```yaml
    apiVersion: v1
    kind: PersistentVolumeClaim
    metadata: { name: "data-pvc" }
    spec: { accessModes: ["ReadWriteOnce"], resources: { requests: { storage: "10Gi" } } }
    ```

119. **Troubleshoot K8s pod crash on EKS, Terraform resource fix?**  
    ```bash
    kubectl logs <pod-name>
    kubectl describe pod <pod-name>
    ```
    ```hcl
    resource "aws_eks_node_group" "fix" {
      cluster_name = "my-eks"
      node_group_name = "fixed-nodes"
      scaling_config: { desired_size: 2, max_size: 3, min_size: 1 }
      instance_types: ["t3.large"]
    }
    ```

120. **Terraform for CloudFront, GitHub Actions to sync K8s assets?**  
    ```hcl
    resource "aws_cloudfront_distribution" "cdn" {
      origin: { domain_name: "my-bucket.s3.amazonaws.com", origin_id: "S3-my-bucket" }
      enabled: true
    }
    ```
    ```yaml
    name: Sync CloudFront
    on: push
    jobs:
      sync:
        runs-on: ubuntu-latest
        steps:
        - uses: aws-actions/configure-aws-credentials@v2
          with: { aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}, aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
        - run: aws s3 sync ./assets s3://my-bucket/
    ```

121. **Ansible for K8s cluster on EC2, GitHub Actions verify?**  
    ```yaml
    - name: Install K8s
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.package: { name: "{{ item }}", state: present }
        loop: [kubeadm, kubelet, kubectl]
      - name: Init master
        ansible.builtin.command: kubeadm init --pod-network-cidr=10.244.0.0/16
        when: inventory_hostname == "master"
    ```
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

122. **GitHub Actions for Terraform VPC/EKS, deploy K8s cluster?**  
    ```hcl
    module "vpc" { source = "terraform-aws-modules/vpc/aws" }
    module "eks" { source = "terraform-aws-modules/eks/aws", subnet_ids = module.vpc.private_subnets, vpc_id = module.vpc.vpc_id }
    ```
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

123. **Ansible to install `kubectl` on EC2, GitHub Actions to deploy pod?**  
    ```yaml
    - name: Install kubectl
      hosts: ec2_hosts
      become: yes
      tasks:
      - name: Install
        ansible.builtin.shell: |
          curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
          chmod +x kubectl && mv kubectl /usr/local/bin/
    ```
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

124. **Terraform for SNS, GitHub Actions to notify K8s pod failure?**  
    ```hcl
    resource "aws_sns_topic" "alerts" { name = "pod-failure-alerts" }
    ```
    ```yaml
    name: Notify Failure
    on: workflow_run
    jobs:
      notify:
        if: failure()
        runs-on: ubuntu-latest
        steps:
        - uses: aws-actions/configure-aws-credentials@v2
          with: { aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}, aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }} }
        - run: aws sns publish --topic-arn ${{ secrets.SNS_TOPIC_ARN }} --message "Pod failed"
    ```

125. **Terraform for Lambda timeout fix, GitHub Actions for K8s job?**  
    ```hcl
    resource "aws_lambda_function" "fix" { function_name = "my-lambda", timeout = 60, filename = "lambda.zip", handler = "index.handler", runtime = "nodejs16.x" }
    ```
    ```yaml
    name: Trigger Job
    on: push
    jobs:
      trigger:
        runs-on: ubuntu-latest
        steps:
        - run: kubectl apply -f job.yaml
          env: { KUBECONFIG: ${{ secrets.KUBECONFIG }} }
    ```
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
    ```