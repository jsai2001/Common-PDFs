```markdown
Here’s your revised Ansible prep guide—keeping all 115 questions intact, with straightforward, impactful answers that hit the core of each query. Code blocks remain unchanged for clarity and hands-on reference. Let’s dive in.

## Basic Ansible Questions

### What is Ansible, and why is it used?
Ansible automates configuration management, app deployment, and tasks. It’s agentless (uses SSH), simple (YAML-based), and cuts manual errors, ensuring consistency across systems fast.

### How does Ansible differ from other tools like Puppet or Chef?
- **Ansible**: Agentless (SSH), push-based, YAML playbooks.  
- **Puppet/Chef**: Agent-based, pull-based, use DSL/Ruby.  
Ansible’s simpler, lighter, no master-agent complexity.

### What is the architecture of Ansible?
- **Control Node**: Runs Ansible.  
- **Managed Nodes**: Targets, need SSH/Python.  
- **Inventory**: Lists hosts.  
- **Modules**: Task executors.  
- **Playbooks**: YAML workflows.  
Agentless, SSH-driven.

### What is an Ansible Playbook?
YAML file defining tasks and workflows for automation—e.g., install Nginx, start it. It’s the blueprint for what happens on which hosts.

### Difference between Ansible Playbooks and Roles?
- **Playbook**: Full automation script.  
- **Role**: Modular chunk (tasks, vars, templates) reusable across playbooks. Playbooks call roles for structure.

### What are Ansible Modules? How do they work?
Modules: Scripts for tasks (e.g., file ops, package installs). Ansible runs them on the control node, ships them to hosts via SSH, executes, and cleans up. Idempotent by design.

### What’s an Inventory file, and its purpose?
Lists hosts (IPs, names, groups) Ansible targets. Static or dynamic, it’s how Ansible knows what to manage—e.g., `[webservers] web1`.

### How does Ansible connect to remote hosts?
Via SSH by default. Control node authenticates (keys/passwords), sends modules, runs them with Python, and removes them. WinRM for Windows.

### Default transport mechanism in Ansible?
SSH. Secure, agentless, relies on existing SSH setup on hosts.

### Difference between `ansible` and `ansible-playbook`?
- `ansible`: Ad-hoc, single tasks (e.g., `ansible all -m ping`).  
- `ansible-playbook`: Runs full YAML playbooks for structured automation.

### What’s idempotency in Ansible, and why’s it key?
Tasks only change if needed—e.g., skip if package is installed. Ensures consistency, avoids redundant actions, critical for reliability.

### Prerequisites for installing Ansible?
- **Control node**: Linux/macOS, Python 3.8+, SSH client.  
- **Managed nodes**: SSH server, Python 2.6+/3.5+. Network access. No agents.

### How to install Ansible on Linux?
- **Ubuntu**:  
    ```bash
    sudo apt update
    sudo apt install ansible
    ```
- Or via pip:  
    ```bash
    pip install ansible
    ```
- Check: `ansible --version`.

### Purpose of `ansible.cfg` file?
Customizes Ansible—sets inventory path, SSH options, escalation settings. Avoids repetitive CLI flags, ensures consistency.

### Run a single task without a playbook?
Use `ansible` with `-m` (module) and `-a` (args):  
```bash
ansible all -m ping
ansible webservers -m command -a "uptime"
```

## Ansible Inventory Questions

### Types of inventories in Ansible?
- **Static**: Manual host list (file).  
- **Dynamic**: Script/plugin pulls hosts (e.g., AWS).  
Static for small setups, dynamic for scaling.

### Define a static inventory?
- **INI**:  
    ```ini
    [webservers]
    web1.example.com
    [dbservers]
    db1.example.com
    ```
- **YAML**:  
    ```yaml
    all:
        children:
            webservers:
                hosts:
                    web1.example.com:
    ```
Use with `-i hosts`.

### Dynamic inventory vs. static inventory?
- **Dynamic**: Auto-fetches hosts from sources (e.g., AWS) via scripts/plugins.  
- **Static**: Fixed, manual file.  
Dynamic scales, static’s simple.

### Group hosts in an inventory file?
- **INI**:  
    ```ini
    [webservers]
    web1.example.com
    [dbservers]
    db1.example.com
    ```
- **YAML**:  
    ```yaml
    all:
        children:
            webservers:
                hosts:
                    web1.example.com:
    ```
Target groups in playbooks (e.g., `hosts: webservers`).

### Specify variables in an inventory?
- **INI**:  
    ```ini
    web1.example.com ansible_user=admin
    [webservers:vars]
    http_port=80
    ```
- **YAML**:  
    ```yaml
    webservers:
        hosts:
            web1.example.com:
                ansible_user: admin
        vars:
            http_port: 80
    ```
Used in tasks/templates.

### Purpose of `ansible_host` variable?
Sets real IP/hostname for an alias:  
```ini
web1 ansible_host=192.168.1.10
```
Connects to `192.168.1.10` when targeting `web1`.

### Inventory for multiple environments (dev, prod)?
- **Single file**:  
    ```ini
    [dev_webservers]
    dev-web1
    [prod_webservers]
    prod-web1
    ```
Run: `ansible-playbook -i hosts -l dev`.  
Or separate files: `dev_hosts`, `prod_hosts`.

### Test connectivity to inventory hosts?
```bash
ansible all -m ping
```
Checks SSH and Python on all hosts. Use `-i` for custom inventory.

### What’s the `-i` flag?
Specifies inventory file/script:  
```bash
ansible -i my_hosts all -m ping
```
Overrides default `/etc/ansible/hosts`.

### Use a cloud provider for dynamic inventory?
- **AWS example**:  
    ```yaml
    plugin: aws_ec2
    regions:
        - us-east-1
    ```
Run:  
```bash
ansible -i aws_ec2.yml all -m ping
```
Fetches hosts dynamically.

## Ansible Playbooks and YAML

### What’s YAML, and why in Ansible?
YAML: Human-readable format with indentation. Ansible uses it for playbooks—simple, clear for defining automation.

### Structure a basic Playbook?
```yaml
---
- name: Install Nginx
    hosts: webservers
    tasks:
        - name: Install
            apt:
                name: nginx
                state: present
        - name: Start
            service:
                name: nginx
                state: started
```
Targets hosts, lists tasks.

### Key Playbook components?
- `hosts` (targets), `tasks` (actions), `vars` (variables), `name` (labels), `become` (sudo), `handlers` (triggered tasks).

### Run Playbook with verbose output?
```bash
ansible-playbook playbook.yml -v
```
Add `-vv`, `-vvv`, `-vvvv` for more detail.

### Include variables in a Playbook?
- **Inline**:  
    ```yaml
    vars:
        package_name: nginx
    ```
- **File**:  
    ```yaml
    vars_files:
        - vars/main.yml
    ```
- **CLI**:  
    ```bash
    ansible-playbook -e "package_name=nginx"
    ```

### Difference: `vars` vs. `vars_files`?
- `vars`: Inline variables in playbook.  
- `vars_files`: External YAML file.  
`vars` is local, `vars_files` is modular.

### Use conditionals (`when`)?
```yaml
- name: Install on Ubuntu
    apt:
        name: nginx
        state: present
    when: ansible_os_family == "Debian"
```
Runs if condition’s true.

### Purpose of `become`?
Escalates privileges (sudo):  
```yaml
- name: Install Nginx
    apt:
        name: nginx
        state: present
    become: yes
```

### Loop over a list?
```yaml
- name: Install packages
    apt:
        name: "{{ item }}"
        state: present
    loop:
        - nginx
        - git
```
Runs task per item.

### What’s the `handlers` section?
Tasks triggered by `notify`:  
```yaml
- name: Copy config
    copy:
        src: nginx.conf
        dest: /etc/nginx/nginx.conf
    notify: Restart Nginx
handlers:
    - name: Restart Nginx
        service:
            name: nginx
            state: restarted
```
Runs on change.

### Debug Playbook execution?
Use `-v`, add:  
```yaml
- debug:
        msg: "Value: {{ my_var }}"
```
Or `--step`, `--check`.

### What’s `register`?
Captures task output:  
```yaml
- name: Check package
    command: dpkg -l nginx
    register: result
- debug:
        msg: "Installed"
    when: result.rc == 0
```

### Handle errors with `ignore_errors`?
```yaml
- name: Remove file
    file:
        path: /tmp/test.txt
        state: absent
    ignore_errors: yes
```
Continues on failure.

### Difference: `include` vs. `import`?
- `include`: Runtime, dynamic (e.g., `include_tasks: setup.yml`).  
- `import`: Parse-time, static (e.g., `import_tasks: setup.yml`).

### Use tags in Playbook?
```yaml
- name: Install
    apt:
        name: nginx
        state: present
    tags:
        - install
```
Run:  
```bash
ansible-playbook --tags "install"
```

## Ansible Modules

### Commonly used modules?
`command`, `shell`, `file`, `copy`, `yum/apt`, `service`, `template`, `user`, `setup`.

### Use `command` module? Limitations?
```yaml
- name: Run command
    command: uptime
```
No shell features (pipes, redirects)—use `shell` for those.

### Difference: `shell` vs. `command`?
- `command`: Direct, no shell (e.g., `uptime`).  
- `shell`: Via shell, supports pipes (e.g., `ls > file`).

### Use `file` module?
- **Create dir**:  
    ```yaml
    - file:
            path: /opt/app
            state: directory
            mode: '0755'
    ```
- **Delete file**:  
    ```yaml
    - file:
            path: /tmp/test.txt
            state: absent
    ```

### Install package with `yum/apt`?
- **Yum**:  
    ```yaml
    - yum:
            name: httpd
            state: present
    ```
- **Apt**:  
    ```yaml
    - apt:
            name: nginx
            state: present
    ```

### What’s `copy` module for?
Copies files:  
```yaml
- copy:
        src: config.conf
        dest: /etc/app/config.conf
        mode: '0644'
```

### Manage services with `service`?
```yaml
- service:
        name: nginx
        state: started
        enabled: yes
```

### How’s `template` different from `copy`?
- **template**: Dynamic (Jinja2):  
    ```yaml
    - template:
            src: httpd.conf.j2
            dest: /etc/httpd/httpd.conf
    ```
- **copy**: Static, no rendering.

### What’s `lineinfile` for?
Edits single line:  
```yaml
- lineinfile:
        path: /etc/ssh/sshd_config
        regexp: '^PermitRootLogin'
        line: 'PermitRootLogin no'
```
Use for config tweaks.

### Manage users with `user`?
```yaml
- user:
        name: appuser
        password: "{{ 'pass' | password_hash('sha512') }}"
        state: present
```

### Fetch files with `fetch`?
```yaml
- fetch:
        src: /var/log/app.log
        dest: /local/logs/{{ inventory_hostname }}.log
        flat: yes
```

### What’s `setup` module?
Gathers facts:  
```yaml
- setup:
```
Auto-runs unless `gather_facts: no`.

### Write a custom module?
- **Python example**:  
    ```python
    from ansible.module_utils.basic import AnsibleModule
    def main():
            module = AnsibleModule(argument_spec=dict(name=dict(type='str', required=True)))
            module.exit_json(changed=True, msg=f"Hello, {module.params['name']}")
    if __name__ == '__main__':
            main()
    ```
- **Use**:  
    ```yaml
    - my_custom_module:
            name: "world"
    ```
```

```markdown
# Ansible Variables and Facts

## What are Ansible Facts?
- Auto-collected host data (e.g., OS, IP) via the `setup` module at play start.

## Access Facts in Playbook
```yaml
- debug:
    msg: "Host: {{ ansible_hostname }}"
```
- Use `ansible_facts['key']` or dot notation.

## Facts vs. Variables
- **Facts**: Dynamic, from hosts.
- **Variables**: Static, user-defined.

## Define Variables at Different Levels
- **Inventory**: `web1 ansible_user=admin`
- **Playbook**: 
  ```yaml
  vars:
    app: nginx
  ```
- **Role**: `defaults/main.yml`
- **CLI**: `-e "app=nginx"`

## Variable Precedence
- Low to high: Role defaults → Inventory → Playbook vars → CLI `-e`. Higher wins.

## Override Variable During Execution
```bash
ansible-playbook playbook.yml -e "app_version=2.0"
```

## Jinja2 Filters with Variables
- Examples: `{{ var | upper }}`, `{{ list | join(',') }}`, `{{ var | default('n/a') }}`.

## Use `set_fact` for Custom Facts
```yaml
- set_fact:
    custom_ver: "1.5"
- debug:
    msg: "{{ custom_ver }}"
```

## What’s `group_vars`?
- Directory for group variables:
  ```yaml
  group_vars/webservers.yml:
  http_port: 80
  ```

## Encrypt Variables with Vault
- Create:
  ```bash
  ansible-vault create secrets.yml
  ```
  ```yaml
  db_pass: secret
  ```
- Use:
  ```bash
  ansible-playbook --ask-vault-pass
  ```

# Ansible Roles

## What are Roles? Why Useful?
- Modular task/variable bundles (e.g., web setup). Reusable, organized, reduce playbook clutter.

## Create a Role
```bash
ansible-galaxy init my_role
```
- Creates `tasks/`, `vars/`, etc.

## Role Directory Structure
- `tasks/`, `defaults/`, `vars/`, `handlers/`, `templates/`, `files/`, `meta/`, `tests/`.

## Call a Role in Playbook
```yaml
- hosts: webservers
  roles:
    - my_role
```

## Tasks vs. Defaults in Role
- **tasks/**: Actions (e.g., install).
- **defaults/**: Fallback variables.

## Pass Variables to Role
```yaml
- hosts: webservers
  roles:
    - role: my_role
      vars:
        port: 8080
```

## Reuse Roles Across Playbooks
- Store in `roles/`, call with `roles:` or `include_role`.

## Purpose of `meta` Directory
- `meta/main.yml`: Defines dependencies, info:
  ```yaml
  dependencies:
    - role: common
  ```

## Install Roles from Galaxy
```bash
ansible-galaxy install geerlingguy.nginx
```
- Use:
  ```yaml
  - roles:
      - geerlingguy.nginx
  ```

## Debug Role Issues
- Use `-v`, add:
  ```yaml
  - debug:
      var: my_var
  ```
- Use `--check`, tags.

# Ansible Vault

## What’s Ansible Vault?
- Encrypts secrets (e.g., passwords) in files for security.

## Create Encrypted File
```bash
ansible-vault create secrets.yml
```

## Edit Vault-Encrypted File
```bash
ansible-vault edit secrets.yml
```

## Use Vault Variable in Playbook
```yaml
vars_files:
  - secrets.yml
tasks:
  - debug:
      msg: "{{ my_secret }}"
```
- Run with `--ask-vault-pass`.

## Single-File vs. Multi-File Encryption
- **Single**: One file, one password.
- **Multi**: Multiple files, separate passwords/IDs.

## Specify Vault Password
- Use `--ask-vault-pass` or `--vault-password-file pass.txt`.

## Manage Multiple Vault Passwords
- Encrypt:
  ```bash
  ansible-vault encrypt secrets.yml --vault-id dev@prompt
  ```
- Run:
  ```bash
  ansible-playbook --vault-id dev@dev_pass.txt
  ```

# Advanced Ansible Questions

## What’s Ansible Tower/AWX?
- GUI, API, workflow layer over Ansible. Core Ansible: CLI only. Tower adds scheduling, RBAC.

## Configure Ansible with SSH Keys
- Generate key (`ssh-keygen`), copy to hosts (`ssh-copy-id`), set in inventory or `ansible.cfg`.

## Purpose of `--check` Flag
- Dry-run—simulates playbook without changes.

## Limit Playbook to Specific Hosts
```bash
ansible-playbook playbook.yml --limit webservers
```

## What’s `delegate_to`?
- Runs task on another host:
  ```yaml
  - command: script.sh
    delegate_to: localhost
  ```

## Parallel Execution in Ansible
- Forks (default 5) run tasks across hosts concurrently. Tasks per host are sequential.

## What’s `forks`?
- Sets parallel processes. Higher forks (e.g., 20) speeds up large runs, but strains resources.

## Optimize Ansible for Scale
- More forks, pipelining, dynamic inventory, fact caching, Tower/AWX.

## What’s `strategy`?
- Task execution mode: `linear` (waits), `free` (no wait), `debug` (interactive).

## Use Ansible with Docker
```yaml
- docker_container:
    name: nginx
    image: nginx:latest
    state: started
```
- Needs Docker SDK.

## Integrate Ansible with CI/CD (Jenkins)
```groovy
ansiblePlaybook playbook: 'deploy.yml', inventory: 'hosts'
```
- Use Jenkins plugin, credentials.

## What are Collections?
- Bundles of modules/roles vs. standalone roles.
- Install:
  ```bash
  ansible-galaxy collection install
  ```

## Upgrade Ansible
```bash
sudo apt install --only-upgrade ansible
pip install --upgrade ansible
```

## Troubleshoot Failed Task
- Use `-v`, `--check`, add debug, test connectivity (`ping`).

## What’s `ansible-doc`?
- Module docs:
  ```bash
  ansible-doc apt
  ```
- Shows usage, params.

# Scenario-Based Questions

## Deploy Nginx on Multiple Hosts
```yaml
- name: Deploy Nginx
  hosts: webservers
  become: yes
  tasks:
    - apt:
        name: nginx
        state: present
    - service:
        name: nginx
        state: started
  handlers:
    - name: Restart Nginx
      service:
        name: nginx
        state: restarted
```

## Update Packages and Reboot
```yaml
- hosts: all
  become: yes
  tasks:
    - package:
        name: '*'
        state: latest
      register: update
    - reboot:
      when: update.changed
```

## Manage Windows Hosts
- Inventory:
  ```ini
  [windows]
  winserver ansible_connection=winrm ansible_user=Admin ansible_password=Pass
  ```
- Playbook:
  ```yaml
  - hosts: windows
    tasks:
      - win_package:
          path: https://notepad-plus-plus.org/npp.exe
          state: present
  ```

## Handle Playbook Failure Midway
- Check error (`-v`), fix, resume:
  ```bash
  --start-at-task "task_name"
  ```

## Backup Configs from Servers
```yaml
- hosts: all
  tasks:
    - fetch:
        src: /etc/nginx/nginx.conf
        dest: ./backups/{{ inventory_hostname }}-nginx.conf
        flat: yes
```

## Create Users with Roles
- Inventory:
  ```ini
  [admins]
  server1
  ```
- Playbook:
  ```yaml
  - hosts: admins
    roles:
      - admin_users
  ```
- Role:
  ```yaml
  - user:
      name: "{{ admin_user }}"
      groups: sudo
  ```

## Deploy App with Dependencies
```yaml
- hosts: db_servers
  roles:
    - mysql
- hosts: app_servers
  vars:
    db_host: "{{ hostvars[groups['db_servers'][0]].ansible_host }}"
  roles:
    - tomcat
```

## OS-Specific Tasks
```yaml
- hosts: all
  tasks:
    - apt:
        name: nginx
        state: present
      when: ansible_distribution == "Ubuntu"
    - yum:
        name: nginx
        state: present
      when: ansible_distribution == "CentOS"
```

## Multi-Tier Architecture
```yaml
- hosts: load_balancers
  roles:
    - haproxy
- hosts: app_servers
  roles:
    - tomcat
- hosts: db_servers
  roles:
    - mysql
```

## Roll Back Failed Deployment
```yaml
- hosts: app_servers
  tasks:
    - copy:
        src: /opt/app
        dest: /opt/app_backup
        remote_src: yes
    - copy:
        src: new_app.tar.gz
        dest: /opt/app.tar.gz
      register: deploy
      rescue:
        - copy:
            src: /opt/app_backup
            dest: /opt/app
            remote_src: yes
```

# Tricky/Conceptual Questions

## Two Tasks Modify Same File
- Last task wins unless using `lineinfile`/`blockinfile` to append. Design for idempotency.

## Task Runs Once on Delegated Host
```yaml
- command: echo "once"
  delegate_to: localhost
  run_once: true
```

## Manage Network Devices
- Yes, with modules like `ios_config`:
  ```yaml
  - cisco.ios.ios_config:
      lines:
        - hostname NEW_ROUTER
  ```
- Uses `network_cli`, SSH/NETCONF.

## Limitations of Ansible
- Slower (SSH), no real-time, less expressive than Ruby/DSL tools, weaker network support.

## Secrets Beyond Vault
- Use HashiCorp Vault, AWS Secrets Manager, lookups:
  ```yaml
  - debug:
      msg: "{{ lookup('hashi_vault', 'secret/my-key') }}"
  ```

## Difference: `changed` vs. `ok`
- **changed**: System modified.
- **ok**: No change needed.

## Handle Race Conditions
- Sequential per host, `serial` or `run_once` for shared resources.

## Unreachable Host in Inventory
- Marked `UNREACHABLE`, playbook continues unless `any_errors_fatal`.

## Scale Ansible for Thousands
- More forks, dynamic inventory, Tower, caching, optimized playbooks.

## Playbook Fails on CI/CD
- Version mismatch, credentials, inventory, or network diffs. Standardize environment, test with `-v`.
```