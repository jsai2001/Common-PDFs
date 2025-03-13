Practical demonstration combining all the listed topics—What is Ansible?, Ansible Inventory, Ansible Ad-Hoc Commands, and Ansible Idempotence—into a cohesive program. 
## Program 1: Ansible Basics and Core Workflow

### Topics Included:

#### What is Ansible? (Core concepts, agentless, YAML, SSH)
Ansible is an open-source automation tool that uses an agentless architecture (no software installed on managed nodes), relies on SSH for communication, and uses YAML for configuration files like playbooks and inventory.

#### Ansible Inventory (Static vs. dynamic, host grouping)
The inventory defines the hosts Ansible manages. It can be static (a simple file) or dynamic (script-generated), with hosts organized into groups.

#### Ansible Ad-Hoc Commands (Basic usage, quick tasks)
Quick, one-line commands to perform tasks on hosts without writing a full playbook (e.g., `ansible all -m ping`).

#### Ansible Idempotence (Understanding the concept)
Ansible ensures tasks are idempotent, meaning running them multiple times produces the same result without unintended changes.

### Why Grouped Together?
This program introduces Ansible’s foundational concepts and demonstrates basic operations. It ties together inventory setup, ad-hoc commands, and idempotence using Ansible’s agentless, SSH-based approach, giving you a hands-on starting point.

#### Step 1: Set Up a Static Inventory
Create a file named `hosts.ini` to define managed hosts and groups.

```ini
# File: hosts.ini
[webservers]
web1.example.com
web2.example.com

[dbservers]
db1.example.com

[all:vars]
ansible_user=admin
ansible_ssh_private_key_file=~/.ssh/id_rsa
```

**Explanation:** 
- `[webservers]` and `[dbservers]` are host groups.
- `ansible_user` and `ansible_ssh_private_key_file` are variables for SSH access, showcasing Ansible’s agentless nature (uses SSH, no agents needed).

#### Step 2: Run an Ad-Hoc Command
Use an ad-hoc command to check host uptime.

```bash
ansible -i hosts.ini all -m command -a "uptime"
```

**Output (example):**
```
web1.example.com | SUCCESS | rc=0 >>
 14:35:23 up 5 days,  3:12,  1 user,  load average: 0.10, 0.15, 0.20
web2.example.com | SUCCESS | rc=0 >>
 14:35:23 up 3 days, 10:45,  2 users,  load average: 0.05, 0.08, 0.12
db1.example.com | SUCCESS | rc=0 >>
 14:35:23 up 7 days,  1:23,  1 user,  load average: 0.25, 0.30, 0.35
```

**Explanation:**
- `-i hosts.ini`: Specifies the inventory file.
- `all`: Targets all hosts in the inventory.
- `-m command`: Uses the command module to execute `uptime`.

This demonstrates quick tasks without a playbook and Ansible’s SSH-based communication.

#### Step 3: Demonstrate Idempotence
Run a simple idempotent ad-hoc command multiple times.

```bash
ansible -i hosts.ini webservers -m file -a "path=/tmp/test.txt state=touch"
```

**First Run Output:**
```
web1.example.com | CHANGED => {"changed": true, "path": "/tmp/test.txt"}
web2.example.com | CHANGED => {"changed": true, "path": "/tmp/test.txt"}
```

**Second Run Output:**
```
web1.example.com | SUCCESS => {"changed": false, "path": "/tmp/test.txt"}
web2.example.com | SUCCESS => {"changed": false, "path": "/tmp/test.txt"}
```

**Explanation:**
- The `file` module with `state=touch` creates `/tmp/test.txt` if it doesn’t exist.
- First run: File is created (`changed: true`).
- Second run: File already exists, so no change (`changed: false`), proving idempotence.

#### Step 4: Tie It Together
Ansible’s core concepts are shown:
- **Agentless:** No software installed on `web1`, `web2`, or `db1`; SSH handles everything.
- **YAML:** Inventory uses a simple, readable format (though not strict YAML here, it’s YAML-compatible).
- **SSH:** Connection relies on SSH keys defined in the inventory.

### Key Takeaways for Notes:
- Inventory organizes hosts and groups for targeting.
- Ad-hoc commands are fast, playbook-free ways to manage systems.
- Idempotence ensures consistent results, a core Ansible principle.

### Execution Command:
```bash
# Check connectivity
ansible -i hosts.ini all -m ping
# Run uptime command
ansible -i hosts.ini all -m command -a "uptime"
# Test idempotence
ansible -i hosts.ini webservers -m file -a "path=/tmp/test.txt state=touch"
```
## Program 2: Ansible Playbooks and Task Management

### Topics Included:
- **Ansible Playbooks** (Structure, purpose, basic syntax)
    - Playbooks are YAML files defining a series of tasks to automate workflows.
- **Ansible Modules** (Common modules like command, shell, copy, service, package, file, template)
    - Modules are reusable units of work (e.g., copy for files, service for managing services).
- **Ansible Loops** (loop, basic iteration)
    - Loops allow repeating tasks over a list of items.
- **Ansible Conditionals** (when, basic operators)
    - Conditionals control task execution based on conditions (e.g., OS type).
- **Ansible Tags** (Purpose, usage, running specific tasks)
    - Tags label tasks for selective execution.
- **Ansible Blocks** (Basic usage, grouping tasks)
    - Blocks group related tasks for better organization or error handling.
- **Ansible Command Module vs. Shell Module** (Differences, when to use which)
    - `command`: Runs simple commands without shell features.
    - `shell`: Runs commands with shell capabilities (e.g., pipes).

#### Step 1: Create a Playbook
Create a file named `setup_webserver.yml`.

```yaml
# File: setup_webserver.yml
---
- name: Set up a basic web server
    hosts: webservers
    tasks:
        # Block for package installation
        - name: Install required packages
            block:
                - name: Install httpd and unzip
                    ansible.builtin.package:
                        name: "{{ item }}"
                        state: present
                    loop:
                        - httpd
                        - unzip
                    tags: install

        # Task with conditional
        - name: Copy index.html to web server
            ansible.builtin.copy:
                src: ./files/index.html
                dest: /var/www/html/index.html
                mode: '0644'
            when: ansible_os_family == "RedHat"
            tags: configure

        # Task comparing command vs shell
        - name: Check httpd version with command module
            ansible.builtin.command: httpd -v
            register: httpd_version_cmd
            tags: check

        - name: Check disk usage with shell module
            ansible.builtin.shell: df -h | grep /dev
            register: disk_usage
            tags: check

        # Service management
        - name: Ensure httpd is running
            ansible.builtin.service:
                name: httpd
                state: started
                enabled: yes
            tags: service
```

**Explanation:**
- **Playbook Structure:** Starts with `---`, defines a play targeting `webservers`.
- **Modules:** Uses `package`, `copy`, `command`, `shell`, and `service`.
- **Loops:** Installs multiple packages (`httpd`, `unzip`) with loop.
- **Conditionals:** Copies `index.html` only on RedHat-based systems.
- **Tags:** Labels tasks as `install`, `configure`, `check`, or `service`.
- **Blocks:** Groups package installation tasks.
- **Command vs. Shell:** `command` runs `httpd -v` (no shell needed); `shell` runs `df -h | grep /dev` (needs pipe).

#### Step 2: Prepare Supporting Files
Create a simple `index.html` file in a `files/` directory.

```html
<!-- File: files/index.html -->
<h1>Welcome to My Web Server</h1>
```

#### Step 3: Use an Inventory
Reuse the `hosts.ini` from Program 1 (assuming `webservers` group exists).

```ini
# File: hosts.ini
[webservers]
web1.example.com
web2.example.com

[all:vars]
ansible_user=admin
ansible_ssh_private_key_file=~/.ssh/id_rsa
```

#### Step 4: Run the Playbook
Execute the full playbook:

```bash
ansible-playbook -i hosts.ini setup_webserver.yml
```

Run specific tagged tasks:

```bash
ansible-playbook -i hosts.ini setup_webserver.yml --tags "install,configure"
```

**Output (example):**
```
TASK [Install httpd and unzip] ************
changed: [web1.example.com] => (item=httpd)
changed: [web1.example.com] => (item=unzip)
TASK [Copy index.html to web server] ******
changed: [web1.example.com]
TASK [Check httpd version with command module] ****
changed: [web1.example.com]
TASK [Check disk usage with shell module] ****
changed: [web1.example.com]
TASK [Ensure httpd is running] ************
changed: [web1.example.com]
```

#### Step 5: Verify Results
Check outputs stored in register:
Add a debug task (optional) to see `httpd_version_cmd` and `disk_usage`:

```yaml
- name: Debug outputs
    ansible.builtin.debug:
        var: httpd_version_cmd.stdout

- name: Debug disk usage
    ansible.builtin.debug:
        var: disk_usage.stdout
```

Rerun to see idempotence (most tasks show `changed: false` on second run).

### Key Takeaways for Notes:
- **Playbooks:** Automate multi-step workflows in YAML.
- **Modules:** Building blocks for tasks (e.g., copy for files, service for daemons).
- **Loops:** Simplify repetitive tasks.
- **Conditionals:** Add logic to adapt to environments.
- **Tags:** Enable selective task execution.
- **Blocks:** Organize related tasks.
- **Command vs. Shell:** Use `command` for simple tasks, `shell` for complex shell features.

### Execution Commands:

```bash
# Run full playbook
ansible-playbook -i hosts.ini setup_webserver.yml

# Run only installation and configuration
ansible-playbook -i hosts.ini setup_webserver.yml --tags "install,configure"

# Run checks only
ansible-playbook -i hosts.ini setup_webserver.yml --tags "check"
```

## Program 3: Advanced Playbook Features and Reusability

### Topics Included:
- **Ansible Roles (Organization, reusability, basic structure)**
    - Roles organize tasks, variables, and files into reusable units.
- **Ansible Variables (Types, scope, usage)**
    - Variables store dynamic data (e.g., package names) with different scopes (play, role, host).
- **Ansible Facts (Purpose, usage, basic facts)**
    - Facts are system details (e.g., OS, IP) gathered from managed nodes.
- **Ansible Handlers (Purpose, usage, notify)**
    - Handlers are tasks triggered by notify when changes occur (e.g., restart a service).
- **Ansible Templates (Jinja2, basic usage)**
    - Templates use Jinja2 to generate dynamic files (e.g., config files).
#### Step 1: Set Up a Role Structure
Create a role named `webserver` with the standard directory layout.
```bash
mkdir -p roles/webserver/{tasks,handlers,templates,vars,files}
```
**Explanation:** Roles organize code into `tasks/` (main logic), `handlers/` (triggered tasks), `templates/` (dynamic files), `vars/` (variables), and `files/` (static files).

#### Step 2: Define Role Components

**Main Tasks (roles/webserver/tasks/main.yml):**
```yaml
---
- name: Install web server package
    ansible.builtin.package:
        name: "{{ web_package }}"
        state: present
    notify: Restart web service

- name: Copy static index.html
    ansible.builtin.copy:
        src: index.html
        dest: "{{ web_doc_root }}/index.html"
        mode: '0644'

- name: Generate httpd.conf from template
    ansible.builtin.template:
        src: httpd.conf.j2
        dest: /etc/httpd/conf/httpd.conf
        mode: '0644'
    notify: Restart web service

- name: Ensure web service is running
    ansible.builtin.service:
        name: "{{ web_service }}"
        state: started
        enabled: yes
```

**Variables (roles/webserver/vars/main.yml):**
```yaml
---
web_package: httpd
web_service: httpd
web_doc_root: /var/www/html
```

**Handlers (roles/webserver/handlers/main.yml):**
```yaml
---
- name: Restart web service
    ansible.builtin.service:
        name: "{{ web_service }}"
        state: restarted
```

**Template (roles/webserver/templates/httpd.conf.j2):**
```plaintext
Listen {{ ansible_default_ipv4.address }}:80
ServerName {{ ansible_hostname }}
DocumentRoot "{{ web_doc_root }}"
<Directory "{{ web_doc_root }}">
        AllowOverride All
        Require all granted
</Directory>
```

**Static File (roles/webserver/files/index.html):**
```html
<h1>Hello from {{ ansible_hostname }}!</h1>
```

#### Step 3: Create a Playbook to Use the Role
Create `deploy_web.yml`:
```yaml
---
- name: Deploy web server using role
    hosts: webservers
    pre_tasks:
        - name: Gather facts
            ansible.builtin.setup:
        - name: Debug OS and IP
            ansible.builtin.debug:
                msg: "Running on {{ ansible_os_family }} with IP {{ ansible_default_ipv4.address }}"
    roles:
        - webserver
```
**Explanation:**
- **Roles:** The `webserver` role is applied to `webservers`.
- **Variables:** `web_package`, `web_service`, etc., are defined in the role’s `vars/`.
- **Facts:** `ansible_os_family`, `ansible_hostname`, and `ansible_default_ipv4.address` are used dynamically.
- **Handlers:** Notified when the package or config changes.
- **Templates:** `httpd.conf.j2` uses Jinja2 to insert facts like IP and hostname.

#### Step 4: Use an Inventory
Reuse `hosts.ini` from previous programs:
```ini
# File: hosts.ini
[webservers]
web1.example.com
web2.example.com

[all:vars]
ansible_user=admin
ansible_ssh_private_key_file=~/.ssh/id_rsa
```

#### Step 5: Run the Playbook
Execute the playbook:
```bash
ansible-playbook -i hosts.ini deploy_web.yml
```

**Output (example):**
```
TASK [Debug OS and IP] ************
ok: [web1.example.com] => {
        "msg": "Running on RedHat with IP 192.168.1.10"
}
TASK [webserver : Install web server package] ****
changed: [web1.example.com]
TASK [webserver : Copy static index.html] ****
changed: [web1.example.com]
TASK [webserver : Generate httpd.conf from template] ****
changed: [web1.example.com]
TASK [webserver : Ensure web service is running] ****
changed: [web1.example.com]
HANDLER [webserver : Restart web service] ****
changed: [web1.example.com]
```

#### Step 6: Verify Results
On `web1.example.com`, check:
- `curl http://192.168.1.10`: Should show "Hello from web1!" (hostname from facts).
- `/etc/httpd/conf/httpd.conf`: Contains the IP and hostname from the template.

### Key Takeaways for Notes:
- **Roles:** Modularize tasks for reusability (e.g., `webserver` role can be reused across projects).
- **Variables:** Define constants (e.g., `web_package`) in `vars/` for flexibility.
- **Facts:** Automatically gather system info (e.g., `ansible_hostname`) for dynamic configs.
- **Handlers:** Trigger actions (e.g., service restart) only when needed.
- **Templates:** Use Jinja2 to create dynamic files based on facts and variables.

### Execution Command:
```bash
ansible-playbook -i hosts.ini deploy_web.yml
```

## Program 4: Security and Operational Control

### Topics Included:
- **Ansible Vault** (Basic encryption, usage)
    - Vault encrypts sensitive data (e.g., passwords) in files.
- **Ansible Privilege Escalation** (become, become_user)
    - `become` escalates privileges (e.g., to root) for tasks requiring elevated access.
- **Ansible Check Mode** (Dry Run) (--check)
    - Check mode simulates tasks without making changes.
- **Ansible Best Practices** (Organization, security, readability)
    - Best practices include clear naming, modular structure, and secure handling of secrets.

#### Step 1: Encrypt Sensitive Data with Ansible Vault
Create an encrypted file `secrets.yml` for sensitive variables.
```bash
ansible-vault create secrets.yml
```
Enter a vault password (e.g., `mypassword`) when prompted, then add:
```yaml
# File: secrets.yml
db_password: "securepass123"
```
**Explanation:** Vault encrypts `secrets.yml` to protect `db_password`.

#### Step 2: Create a Playbook with Security Features
Create `secure_setup.yml`:
```yaml
---
- name: Securely set up a database server
    hosts: dbservers
    vars_files:
        - secrets.yml  # Include encrypted variables
    tasks:
        - name: Install MariaDB package
            ansible.builtin.package:
                name: mariadb-server
                state: present
            become: yes  # Escalate privileges to root
            become_user: root
            tags: install

        - name: Ensure MariaDB service is running
            ansible.builtin.service:
                name: mariadb
                state: started
                enabled: yes
            become: yes
            become_user: root
            tags: service

        - name: Set database root password
            ansible.builtin.shell: mysqladmin -u root password "{{ db_password }}"
            when: ansible_os_family == "RedHat"
            become: yes
            become_user: root
            tags: configure
            no_log: true  # Hide sensitive output (best practice)
```
**Explanation:**
- **Vault:** `secrets.yml` provides `db_password`.
- **Privilege Escalation:** `become: yes` and `become_user: root` allow installing packages and managing services.
- **Check Mode:** Can be tested with `--check`.
- **Best Practices:**
    - Clear task names (e.g., "Install MariaDB package").
    - `no_log: true` hides sensitive data in logs.
    - Modular structure with tags (install, service, configure).

#### Step 3: Use an Inventory
Reuse or adapt `hosts.ini`:
```ini
# File: hosts.ini
[dbservers]
db1.example.com
[all:vars]
ansible_user=admin
ansible_ssh_private_key_file=~/.ssh/id_rsa
```

#### Step 4: Run the Playbook
**Dry Run (Check Mode):**
```bash
ansible-playbook -i hosts.ini secure_setup.yml --check --ask-vault-pass
```
Enter the vault password (`mypassword`) when prompted.

**Output (example):**
```
TASK [Install MariaDB package] ************
ok: [db1.example.com] => (skipped, in check mode)
TASK [Ensure MariaDB service is running] ****
ok: [db1.example.com] => (skipped, in check mode)
TASK [Set database root password] *********
ok: [db1.example.com] => (skipped, in check mode)
```

**Full Execution:**
```bash
ansible-playbook -i hosts.ini secure_setup.yml --ask-vault-pass
```
**Output (example):**
```
TASK [Install MariaDB package] ************
changed: [db1.example.com]
TASK [Ensure MariaDB service is running] ****
changed: [db1.example.com]
TASK [Set database root password] *********
changed: [db1.example.com]
```

#### Step 5: Verify Results
On `db1.example.com`:
- Check if `mariadb-server` is installed (`rpm -q mariadb-server`).
- Verify MariaDB is running (`systemctl status mariadb`).
- Test the root password (`mysql -u root -p` with `securepass123`).

### Key Takeaways for Notes:
- **Vault:** Encrypts sensitive data (e.g., `db_password`) for security.
- **Privilege Escalation:** `become` ensures tasks requiring root access succeed.
- **Check Mode:** `--check` previews changes without applying them.
- **Best Practices:**
    - Use descriptive names and tags.
    - Hide sensitive output with `no_log`.
    - Store secrets in Vault, not plaintext.

### Execution Commands:
```bash
# Create/edit Vault file
ansible-vault edit secrets.yml --ask-vault-pass
# Dry run
ansible-playbook -i hosts.ini secure_setup.yml --check --ask-vault-pass
# Full run
ansible-playbook -i hosts.ini secure_setup.yml --ask-vault-pass
```

## Program 5: Debugging and Validation

### Topics Included:
- **Ansible Debugging** (Basic techniques, `-v`, `debug` module)
    - Debugging tools like verbose mode (`-v`) and the `debug` module help troubleshoot issues.
- **Ansible Check Mode (Dry Run)** (`--check`)
    - Check mode simulates playbook execution without applying changes.
#### Step 1: Create a Playbook for Debugging
Create `debug_validate.yml`:
```yaml
---
- name: Debug and validate system setup
    hosts: webservers
    tasks:
        - name: Gather facts
            ansible.builtin.setup:
            tags: facts

        - name: Debug system OS and memory
            ansible.builtin.debug:
                msg: "OS: {{ ansible_os_family }}, Free Memory: {{ ansible_memfree_mb }} MB"
            tags: debug

        - name: Install httpd package
            ansible.builtin.package:
                name: httpd
                state: present
            register: install_result  # Store task output
            tags: install

        - name: Debug installation result
            ansible.builtin.debug:
                var: install_result
            when: install_result is defined
            tags: debug

        - name: Ensure httpd is running
            ansible.builtin.service:
                name: httpd
                state: started
            register: service_result
            tags: service

        - name: Debug service status
            ansible.builtin.debug:
                msg: "Service changed: {{ service_result.changed }}, State: {{ service_result.state }}"
            when: service_result is defined
            tags: debug
```

**Explanation:**
- **Debugging:** Uses `debug` module to print facts (e.g., OS, memory) and task results.
- **Check Mode:** Can simulate package installation and service management.
- **Register:** Captures task outputs (`install_result`, `service_result`) for inspection.

#### Step 2: Use an Inventory
Reuse `hosts.ini` from previous programs:
```ini
# File: hosts.ini
[webservers]
web1.example.com

[all:vars]
ansible_user=admin
ansible_ssh_private_key_file=~/.ssh/id_rsa
```

#### Step 3: Run the Playbook with Debugging
**Verbose Mode (Basic):**
```bash
ansible-playbook -i hosts.ini debug_validate.yml -v
```

**Verbose Mode (Detailed):**
```bash
ansible-playbook -i hosts.ini debug_validate.yml -vvv
```

**Output (example with `-v`):**
```
TASK [Debug system OS and memory] ************
ok: [web1.example.com] => {
        "msg": "OS: RedHat, Free Memory: 2048 MB"
}
TASK [Install httpd package] ************
changed: [web1.example.com] => {"changed": true, "name": "httpd"}
TASK [Debug installation result] ************
ok: [web1.example.com] => {
        "install_result": {"changed": true, "name": "httpd", "state": "present"}
}
```

**Explanation:**
- `-v` shows task outputs; `-vvv` adds detailed execution info (e.g., SSH commands).

#### Step 4: Run in Check Mode
Simulate execution:
```bash
ansible-playbook -i hosts.ini debug_validate.yml --check
```

**Output (example):**
```
TASK [Debug system OS and memory] ************
ok: [web1.example.com] => {
        "msg": "OS: RedHat, Free Memory: 2048 MB"
}
TASK [Install httpd package] ************
ok: [web1.example.com] => (skipped, in check mode)
TASK [Debug installation result] ************
skipping: [web1.example.com]  # Skipped because install_result isn’t set in check mode
TASK [Ensure httpd is running] ************
ok: [web1.example.com] => (skipped, in check mode)
```

**Explanation:** Check mode runs debug tasks but skips changes (e.g., package install).

#### Step 5: Verify Debugging Output
Rerun with tags to focus on debugging:
```bash
ansible-playbook -i hosts.ini debug_validate.yml --tags "debug" -v
```

**Output (example):**
```
TASK [Debug system OS and memory] ************
ok: [web1.example.com] => {
        "msg": "OS: RedHat, Free Memory: 2048 MB"
}
TASK [Debug installation result] ************
ok: [web1.example.com] => {
        "install_result": {"changed": false, "name": "httpd"}
}
TASK [Debug service status] ************
ok: [web1.example.com] => {
        "msg": "Service changed: false, State: started"
}
```

### Key Takeaways for Notes:
- **Debugging:**
    - `-v` to `-vvv`: Increases verbosity for troubleshooting.
    - `debug` module: Prints variables, facts, or task results (e.g., `ansible_memfree_mb`).
- **Check Mode:** `--check` validates playbook logic without altering systems.
- Combine `register` with `debug` to inspect task outcomes.

### Execution Commands:
```bash
# Run with basic verbosity
ansible-playbook -i hosts.ini debug_validate.yml -v
# Run with maximum verbosity
ansible-playbook -i hosts.ini debug_validate.yml -vvv
# Run in check mode
ansible-playbook -i hosts.ini debug_validate.yml --check
# Run debug tasks only
ansible-playbook -i hosts.ini debug_validate.yml --tags "debug"
```

### Program 6: Ansible Ecosystem and Reusable Content Management

**Topics Included:**
- Ansible Galaxy (Purpose, usage, finding roles)
- Ansible Collections (Purpose, benefits, basic usage)
- Ansible Playbook Includes and Imports (Differences, usage)

**Rationale:**
Ansible Galaxy and Ansible Collections are both part of Ansible's ecosystem for managing reusable content (roles and collections). Galaxy is a hub for finding roles, while Collections extend this concept with modular, reusable code including roles, modules, and plugins. Playbook Includes and Imports tie into this by allowing you to integrate Galaxy roles or Collection content into your playbooks dynamically (import_role, include_tasks) or statically.
s
**Program Example:**
A playbook that pulls a role from Galaxy (e.g., configuring an Nginx server), uses a Collection for additional utilities (e.g., community.general), and demonstrates import_role vs. include_tasks for modularity.

**Revision Notes Program:**
```yaml
- name: Deploy Nginx using Galaxy Role and Collections
    hosts: webservers
    tasks:
        - name: Import Nginx role from Galaxy
            ansible.builtin.import_role:
                name: geerlingguy.nginx  # Fetched via ansible-galaxy
        - name: Use Collection module for additional setup
            community.general.package_facts:
                manager: apt
        - name: Include dynamic tasks
            ansible.builtin.include_tasks: setup_firewall.yml
```

### Program 7: Data Manipulation and Dynamic Playbooks

**Topics Included:**
- Ansible Filters (Basic usage, data manipulation)
- Ansible Lookup Plugins (Basic understanding, usage)
- Ansible Playbook Variables Precedence (Understanding the order)

**Rationale:**
Filters and Lookup Plugins are tools for manipulating and retrieving data dynamically within playbooks. Filters transform data (e.g., | json_query), while Lookups fetch external data (e.g., lookup('file', 'path')). Variables Precedence is critical here because it determines how variables (used in filters or lookups) are overridden or prioritized (e.g., playbook vars vs. role vars).

**Program Example:**
A playbook that reads data from a file using a lookup, manipulates it with filters, and respects variable precedence for customization.

**Revision Notes Program:**
```yaml
- name: Process server data dynamically
    hosts: all
    vars:
        default_port: 80
    tasks:
        - name: Read config from file using lookup
            ansible.builtin.set_fact:
                config_data: "{{ lookup('file', 'config.json') | from_json }}"
        - name: Filter and transform data
            ansible.builtin.debug:
                msg: "Server: {{ config_data.servers | map(attribute='name') | join(', ') }}"
        - name: Show variable precedence (playbook vars override defaults)
            ansible.builtin.debug:
                msg: "Port: {{ port | default(default_port) }}"
```

### Program 8: Robust Automation with Error Handling and Scaling

**Topics Included:**
- Ansible Dynamic Inventory (Basic concept, benefits)
- Ansible Error Handling (ignore_errors, failed_when)
- Ansible Forks (Basic understanding)

**Rationale:**
Dynamic Inventory allows Ansible to adapt to changing environments (e.g., cloud instances), which pairs well with Forks for parallel execution across multiple hosts. Error Handling ensures robustness by managing failures (e.g., ignoring non-critical errors or defining custom failure conditions).

**Program Example:**
A playbook that uses a dynamic inventory (e.g., AWS EC2), handles errors gracefully, and scales with forks.

**Revision Notes Program:**
```yaml
- name: Manage cloud servers with error handling
    hosts: all
    # Dynamic inventory assumed (e.g., ec2.py script)
    forks: 10  # Parallel execution
    tasks:
        - name: Install package with error handling
            ansible.builtin.package:
                name: httpd
                state: present
            ignore_errors: yes  # Continue despite failures
        - name: Check service status
            ansible.builtin.command: systemctl status httpd
            register: result
            failed_when: "'running' not in result.stdout"  # Custom failure condition
        - name: Debug result
            ansible.builtin.debug:
                msg: "Service is {{ 'up' if 'running' in result.stdout else 'down' }}"
```

### Program 9: Controlled Deployment with Rolling Updates

**Topics Included:**
- Ansible Rolling Updates (serial)

**Rationale:**
Rolling Updates (serial) is a standalone but critical concept for managing deployments in production environments, ensuring minimal downtime by updating hosts in batches. This can be a dedicated program as it’s often used independently or combined with other features (e.g., error handling from Set 3).

**Program Example:**
A playbook that updates a web application across multiple servers in batches.

**Revision Notes Program:**
```yaml
- name: Perform rolling update on web servers
    hosts: webservers
    serial: 2  # Update 2 hosts at a time
    tasks:
        - name: Update application package
            ansible.builtin.package:
                name: myapp
                state: latest
        - name: Restart service
            ansible.builtin.service:
                name: myapp
                state: restarted
        - name: Verify application
            ansible.builtin.uri:
                url: "http://{{ inventory_hostname }}/health"
                status_code: 200
```

### Summary of Segregation

**Program 6: Ansible Ecosystem and Reusable Content Management**  
Ansible Galaxy, Ansible Collections, Playbook Includes/Imports

**Program 7: Data Manipulation and Dynamic Playbooks**  
Filters, Lookup Plugins, Variables Precedence

**Program 8: Robust Automation with Error Handling and Scaling**  
Dynamic Inventory, Error Handling, Forks

**Program 9: Controlled Deployment with Rolling Updates**  
Rolling Updates (serial)