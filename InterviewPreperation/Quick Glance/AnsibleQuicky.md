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

Here's a simple example of an `ansible.cfg` file with commonly used settings:

```ini
[defaults]
inventory = ./inventory
remote_user = ansible
private_key_file = ~/.ssh/id_rsa
host_key_checking = False
retry_files_enabled = False
log_path = ./ansible.log

[privilege_escalation]
become = True
become_method = sudo
become_user = root
become_ask_pass = False
```

### Explanation:
- `[defaults]`:
  - `inventory`: Specifies the path to the inventory file.
  - `remote_user`: The default SSH user for connecting to remote hosts.
  - `private_key_file`: Path to the SSH private key.
  - `host_key_checking`: Disables SSH host key checking for simplicity (not recommended for production).
  - `retry_files_enabled`: Disables creation of .retry files on task failure.
  - `log_path`: Specifies where to save Ansible logs.

- `[privilege_escalation]`:
  - `become`: Enables privilege escalation.
  - `become_method`: Uses `sudo` for escalation.
  - `become_user`: Specifies the user to escalate to (e.g., `root`).
  - `become_ask_pass`: Disables prompting for a sudo password.

Place this file in the same directory as your playbooks or in `~/.ansible.cfg` for global use. Adjust paths and settings based on your environment.

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

In the context of the command `ansible-playbook -i hosts -l dev`, the `-l` option in Ansible is used to **limit** the execution of the playbook to a specific subset of hosts from the inventory.

#### What `-l dev` does:
- The `-l` (or `--limit`) flag tells Ansible to run the playbook only on hosts that match the specified pattern, in this case, `dev`.

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

#### **1. `include`: Runtime, Dynamic**
- **What it means**: `include` (e.g., `include_tasks: setup.yml`) processes the tasks in the included file **at runtime**, meaning the inclusion happens while the playbook is being executed.
- **Dynamic Behavior**:
  - The included tasks are evaluated **dynamically** during execution. This allows for flexibility, such as using variables or conditions that are resolved at runtime.
  - For example, if `setup.yml` has tasks that depend on a variable (e.g., `{{ env }}`), the value of `env` can be determined at runtime, and the tasks in `setup.yml` will be processed accordingly.
- **Use Case**:
  - Useful when you need to include tasks conditionally or loop over a set of tasks dynamically.
  - Example:
    ```yaml
    - name: Include tasks dynamically
      include_tasks: "{{ env }}_setup.yml"
    ```
    Here, if `env` is `dev`, Ansible will include `dev_setup.yml` at runtime.
- **Limitation**:
  - Since it’s dynamic, it can be harder to debug because the tasks aren’t fully resolved until the playbook runs.
  - Note: `include` is considered legacy in newer Ansible versions (post-2.4), and `include_tasks` is often used instead, but the dynamic nature remains.

#### **2. `import`: Parse-time, Static**
- **What it means**: `import` (e.g., `import_tasks: setup.yml`) processes the tasks in the imported file **at parse-time**, meaning the tasks are loaded and evaluated when the playbook is parsed, **before execution starts**.
- **Static Behavior**:
  - The imported tasks are resolved **statically** when Ansible reads the playbook. This means all tasks in `setup.yml` are included in the playbook’s structure before any execution begins.
  - Variables or conditions in the import statement must be resolvable at parse-time. For example, you can’t use a variable that’s defined later in the playbook.
  - Example:
    ```yaml
    - name: Import tasks statically
      import_tasks: setup.yml
    ```
    Here, `setup.yml` is fully loaded into the playbook when Ansible parses it, and no runtime evaluation of the file path or tasks occurs.
- **Use Case**:
  - Ideal for scenarios where you want to ensure all tasks are known and fixed before execution starts, improving predictability.
  - It’s also more efficient for static task sets because Ansible doesn’t have to re-evaluate inclusions during runtime.
- **Limitation**:
  - Less flexible than `include` because you can’t dynamically decide which file to import based on runtime conditions. For example, `import_tasks: "{{ env }}_setup.yml"` will fail if `env` isn’t defined at parse-time.

#### **Key Differences**:
| Aspect            | `include` (e.g., `include_tasks`)      | `import` (e.g., `import_tasks`)      |
|-------------------|----------------------------------------|---------------------------------------|
| **Processing Time** | At runtime (dynamic)                  | At parse-time (static)               |
| **Flexibility**    | Can use runtime variables/conditions  | Variables must be known at parse-time|
| **Performance**    | Slightly slower (runtime evaluation)  | Faster (pre-loaded during parsing)   |
| **Use Case**       | Conditional or dynamic task inclusion | Static, predictable task inclusion   |
| **Debugging**      | Harder (resolved at runtime)          | Easier (resolved before execution)   |

#### **Practical Example**:
##### Using `include_tasks` (Dynamic):
```yaml
- hosts: localhost
  vars:
    env: dev
  tasks:
    - name: Include tasks based on environment
      include_tasks: "{{ env }}_setup.yml"
```
- If `env` is `dev`, it includes `dev_setup.yml` at runtime.
- If `env` changes during execution (e.g., in a loop), it can include different files dynamically.

##### Using `import_tasks` (Static):
```yaml
- hosts: localhost
  tasks:
    - name: Import tasks
      import_tasks: setup.yml
```
- `setup.yml` is loaded when the playbook is parsed, and its tasks are fixed in the playbook structure before execution starts.

#### **Summary**:
- Use `include_tasks` when you need dynamic behavior, such as including tasks based on runtime conditions or variables.
- Use `import_tasks` for static, predictable task inclusion, which is more efficient and easier to debug but less flexible.

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

In Ansible, the `copy` and `template` modules are both used to transfer files to remote hosts, but they serve different purposes depending on whether you need static or dynamic content. Here's the difference with examples:

---

### **1. `copy` Module**
- **Purpose**: The `copy` module is used to transfer a **static file** from the control node (where Ansible is running) to the remote host, exactly as it is, without any modification.
- **Key Characteristics**:
  - The file is copied as-is; no processing or variable substitution occurs.
  - Useful for transferring configuration files, scripts, or any file that doesn’t need dynamic content.
  - You can set permissions, ownership, and other attributes on the destination file.
- **Example**:
  Let’s say you have a static file `config.txt` on your control node with the following content:
  ```
  server_name = webserver
  port = 8080
  ```
  You want to copy this file to a remote host.

  **Playbook**:
  ```yaml
  - name: Copy a static file to remote host
    hosts: dev-web1
    tasks:
      - name: Copy config.txt to remote host
        ansible.builtin.copy:
          src: ./files/config.txt
          dest: /etc/app/config.txt
          owner: appuser
          group: appgroup
          mode: '0644'
  ```
  - **What Happens**:
    - The `config.txt` file is copied from the `files/` directory on the control node to `/etc/app/config.txt` on the remote host (`dev-web1`).
    - The file’s content remains unchanged (`server_name = webserver` and `port = 8080`).
    - The file on the remote host will have the specified owner (`appuser`), group (`appgroup`), and permissions (`0644`).

---

### **2. `template` Module**
- **Purpose**: The `template` module is used to transfer a file with **dynamic content** to the remote host. It processes the file as a Jinja2 template, substituting variables and applying logic before copying it.
- **Key Characteristics**:
  - The source file (a Jinja2 template) can contain variables, loops, and conditionals, which Ansible evaluates and replaces with actual values during playbook execution.
  - The template file typically has a `.j2` extension (e.g., `config.j2`), but this is just a convention.
  - Useful for generating configuration files that need to be customized for each host or environment.
- **Example**:
  Let’s say you have a template file `config.j2` on your control node with the following content:
  ```
  server_name = {{ server_name }}
  port = {{ app_port }}
  ```
  You want to generate a `config.txt` file on the remote host, with `server_name` and `app_port` dynamically filled in.

  **Playbook**:
  ```yaml
  - name: Generate a config file using a template
    hosts: dev-web1
    vars:
      server_name: "webserver_dev"
      app_port: 8080
    tasks:
      - name: Generate config.txt from template
        ansible.builtin.template:
          src: ./templates/config.j2
          dest: /etc/app/config.txt
          owner: appuser
          group: appgroup
          mode: '0644'
  ```
  - **What Happens**:
    - The `config.j2` template is processed by Ansible, and the variables `{{ server_name }}` and `{{ app_port }}` are replaced with `"webserver_dev"` and `8080`, respectively.
    - The resulting file `/etc/app/config.txt` on the remote host will look like this:
      ```
      server_name = webserver_dev
      port = 8080
      ```
    - The file on the remote host will have the specified owner (`appuser`), group (`appgroup`), and permissions (`0644`).

---

### **Key Differences**:
| Aspect             | `copy` Module                        | `template` Module                     |
|--------------------|--------------------------------------|---------------------------------------|
| **File Content**   | Static; copies the file as-is.       | Dynamic; processes the file as a Jinja2 template with variable substitution. |
| **Use Case**       | For files that don’t need changes.   | For files that need dynamic content (e.g., configs tailored per host). |
| **Source File**    | Any file (e.g., `config.txt`).       | A Jinja2 template (e.g., `config.j2`). |
| **Processing**     | No processing; direct copy.          | Processes variables, loops, and conditionals using Jinja2. |
| **Flexibility**    | Less flexible; no dynamic content.   | More flexible; supports dynamic content generation. |

---

### **When to Use**:
- Use `copy` when you have a static file that doesn’t need modification, like a pre-built script, binary, or static config.
- Use `template` when you need to generate a file dynamically, such as a configuration file that varies by host, environment, or variable.

### **Bonus Tip**:
You can use variables in both modules for the `src` and `dest` paths, but only `template` processes variables **inside** the file content. For example, in the `copy` module, the file content remains static even if you use variables in the playbook.

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

The `fetch` module in Ansible is used to **retrieve files from remote hosts** and copy them to the control node (the machine running Ansible). This is the opposite of the `copy` module, which transfers files from the control node to remote hosts. Let’s break down the example and explain its use.

#### **Example Analysis**:
The task shown uses the `fetch` module:
```yaml
- fetch:
    src: /var/log/app.log
    dest: /local/logs/{{ inventory_hostname }}.log
    flat: yes
```

##### **What Each Parameter Does**:
1. **`src: /var/log/app.log`**:
   - This specifies the source file on the **remote host** that you want to fetch.
   - In this case, Ansible will look for the file `/var/log/app.log` on the remote host (e.g., `dev-web1` if that’s the target host).

2. **`dest: /local/logs/{{ inventory_hostname }}.log`**:
   - This specifies the destination path on the **control node** where the fetched file will be saved.
   - `{{ inventory_hostname }}` is an Ansible variable that represents the name of the remote host (e.g., `dev-web1`).
   - The file will be saved as `/local/logs/dev-web1.log` on the control node if the remote host’s name is `dev-web1`.

3. **`flat: yes`**:
   - By default, `fetch` preserves the directory structure of the source file relative to the destination. For example, without `flat: yes`, the file might be saved as `/local/logs/dev-web1/var/log/app.log`, mirroring the source path.
   - Setting `flat: yes` tells Ansible to "flatten" the structure and save the file directly as `/local/logs/dev-web1.log`, ignoring the source directory hierarchy (`/var/log/`).

##### **What Happens When This Task Runs**:
- Ansible connects to the remote host (e.g., `dev-web1`).
- It fetches the file `/var/log/app.log` from the remote host.
- It saves the file on the control node at `/local/logs/dev-web1.log` (because `inventory_hostname` is `dev-web1` and `flat: yes` ensures the file is saved directly without the `/var/log/` path).

#### **Use of the `fetch` Module**:
The `fetch` module is typically used for:
- **Retrieving logs or files for debugging or auditing**: For example, fetching application logs (like `app.log`) from multiple servers to analyze them locally.
- **Backups or snapshots**: Copying configuration files or other important data from remote hosts to the control node for safekeeping.
- **Centralized monitoring**: Collecting files (e.g., status reports, metrics, or logs) from multiple hosts into a single location on the control node.

#### **Key Points**:
- **Direction**: `fetch` pulls files **from the remote host to the control node**. Compare this to the `copy` module, which pushes files **from the control node to the remote host**.
- **Unique Filenames**: Using `{{ inventory_hostname }}` in the `dest` path ensures that files from different hosts don’t overwrite each other. For example, if you fetch from `dev-web1` and `prod-web1`, you’ll get `/local/logs/dev-web1.log` and `/local/logs/prod-web1.log`.
- **Flat Option**: The `flat: yes` option simplifies the destination path, which is useful when you don’t want to replicate the remote directory structure locally.

#### **Practical Use Case**:
Imagine you’re managing multiple web servers (`dev-web1`, `prod-web1`, etc.), and each server has an `app.log` file in `/var/log/`. You want to collect these logs on your control node for analysis. The `fetch` module lets you retrieve all these logs and save them as separate files (e.g., `dev-web1.log`, `prod-web1.log`) in a local directory (`/local/logs/`).

#### **Full Playbook Example**:
```yaml
- name: Fetch logs from remote hosts
  hosts: all
  tasks:
    - name: Fetch app.log from remote host
      ansible.builtin.fetch:
        src: /var/log/app.log
        dest: /local/logs/{{ inventory_hostname }}.log
        flat: yes
```

This playbook will fetch `/var/log/app.log` from all target hosts and save them on the control node with filenames like `/local/logs/dev-web1.log`, `/local/logs/prod-web1.log`, etc.

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

The workflow described in the image outlines how to encrypt a variable using Ansible Vault and then use the encrypted variable in a playbook. Ansible Vault is a feature in Ansible that allows you to encrypt sensitive data (like passwords, API keys, or other secrets) to keep it secure while still using it in playbooks. Let's break down the workflow step by step.

---

### **Step 1: Create an Encrypted File with Ansible Vault**
#### Command:
```
ansible-vault create secrets.yml
```

#### What Happens:
- The `ansible-vault create` command creates a new encrypted file named `secrets.yml`.
- When you run this command, Ansible prompts you to set a **vault password**. This password will be required to decrypt the file later.
- After entering the password, Ansible opens the file in your default text editor (e.g., `vim` or `nano`), where you can add your sensitive data.

#### Example Content of `secrets.yml`:
After the command, you might add the following to `secrets.yml` in the editor:
```yaml
db_pass: secret
```
- You save and exit the editor. Ansible Vault encrypts the file using the password you provided, so the file's contents are not stored in plaintext. The encrypted file might look like this on disk:
  ```
  $ANSIBLE_VAULT;1.1;AES256
  6138323266336230363735636432636239306564333534656635633265633431
  6638666231623437633638376530313166636162356564640a66373232656637
  ...
  ```
  The actual data (`db_pass: secret`) is encrypted and not readable without the vault password.

#### Purpose:
- This step securely stores the sensitive variable `db_pass` with the value `secret` in the `secrets.yml` file.
- The encrypted file can be safely stored in version control (e.g., Git) without exposing sensitive data.

---

### **Step 2: Use the Encrypted Variable in a Playbook**
#### Command:
```
ansible-playbook --ask-vault-pass
```

#### What Happens:
- The `ansible-playbook --ask-vault-pass` command runs a playbook that references the encrypted `secrets.yml` file.
- The `--ask-vault-pass` flag tells Ansible to prompt you for the vault password at runtime. This password must match the one used when creating `secrets.yml`.
- Once the correct password is provided, Ansible decrypts `secrets.yml` in memory, accesses the variables (e.g., `db_pass`), and uses them in the playbook.

#### Example Playbook (`playbook.yml`):
Here’s how you might use the encrypted variable in a playbook:
```yaml
- name: Use encrypted variable in playbook
  hosts: dev-web1
  vars_files:
    - secrets.yml
  tasks:
    - name: Print the database password
      ansible.builtin.debug:
        msg: "The database password is {{ db_pass }}"
```

#### What Happens When You Run the Playbook:
1. You run the command:
   ```
   ansible-playbook playbook.yml --ask-vault-pass
   ```
2. Ansible prompts you for the vault password:
   ```
   Vault password:
   ```
3. You enter the password used to encrypt `secrets.yml`.
4. Ansible decrypts `secrets.yml` in memory, making the variable `db_pass` available as `secret`.
5. The playbook executes, and the `debug` task prints:
   ```
   The database password is secret
   ```

#### Purpose:
- This step allows you to securely use the encrypted variable (`db_pass`) in your playbook without hardcoding sensitive data in plaintext.
- The `--ask-vault-pass` flag ensures that only someone with the vault password can run the playbook and access the encrypted data.

---

### **Overall Workflow Summary**:
1. **Encrypt the Variable**:
   - Use `ansible-vault create secrets.yml` to create an encrypted file.
   - Add sensitive data (e.g., `db_pass: secret`) and save it. The file is encrypted with a vault password.

2. **Use the Encrypted Variable**:
   - Reference the encrypted file in your playbook using `vars_files`.
   - Run the playbook with `ansible-playbook --ask-vault-pass`, providing the vault password to decrypt the file and access the variables.

---

### **Key Points**:
- **Security**: Ansible Vault ensures sensitive data is encrypted on disk, protecting it from unauthorized access.
- **Vault Password**: The password is critical. Without it, you cannot decrypt the file or run the playbook.
- **Alternative to `--ask-vault-pass`**: Instead of entering the password manually, you can:
  - Use a vault password file with `--vault-password-file` (e.g., `ansible-playbook --vault-password-file vault_pass.txt`).
  - Set up a vault ID for more complex workflows.
- **Use Case**: This workflow is ideal for managing sensitive data like database credentials, API keys, or SSH keys in Ansible playbooks.

---

### **Potential Playbook with a Practical Task**:
Here’s an extended example where the `db_pass` is used to configure a database:
```yaml
- name: Configure database with encrypted password
  hosts: dev-web1
  vars_files:
    - secrets.yml
  tasks:
    - name: Ensure database is configured
      ansible.builtin.mysql_user:
        name: app_user
        password: "{{ db_pass }}"
        priv: '*.*:ALL'
        state: present
```

- When you run this playbook with `ansible-playbook --ask-vault-pass`, it will use the `db_pass` (`secret`) from `secrets.yml` to configure a MySQL user on the remote host.

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

---

### **1. Tasks vs. Defaults in a Role**

Ansible roles are a way to organize playbooks into reusable components. A role typically has a directory structure with specific subdirectories like `tasks`, `defaults`, `vars`, `templates`, etc. The image highlights two key components of a role: `tasks` and `defaults`.

#### **Tasks: Actions (e.g., Install)**
- **What It Means**:
  - The `tasks/` directory in a role contains the main actions (or tasks) that the role will perform.
  - These are the actual operations Ansible executes, such as installing packages, copying files, or starting services.
- **Location**: Inside the role directory, tasks are stored in `roles/my_role/tasks/main.yml`.
- **Example**:
  Suppose you have a role named `my_role` that installs and configures a web server. The `tasks/main.yml` might look like this:
  ```yaml
  - name: Install nginx
    ansible.builtin.package:
      name: nginx
      state: present

  - name: Ensure nginx is running
    ansible.builtin.service:
      name: nginx
      state: started
      enabled: yes
  ```
  - **Explanation**: These tasks install the `nginx` package and ensure the `nginx` service is running and enabled on boot. These are the core actions of the role.

#### **Defaults: Fallback Variables**
- **What It Means**:
  - The `defaults/` directory in a role contains default variables that the role uses if no other values are provided.
  - These are fallback values, meaning they can be overridden by variables defined elsewhere (e.g., in the playbook or inventory).
  - Variables in `defaults/` have the **lowest precedence**, so they are overridden by variables from other sources like `vars/` or playbook variables.
- **Location**: Inside the role directory, defaults are stored in `roles/my_role/defaults/main.yml`.
- **Example**:
  For the same `my_role`, the `defaults/main.yml` might look like this:
  ```yaml
  port: 80
  webserver_user: www-data
  ```
  - **Explanation**:
    - The role assumes the web server will run on `port: 80` and use the user `www-data` unless these values are overridden.
    - These defaults ensure the role can function even if no variables are explicitly passed to it.

#### **Tasks vs. Defaults Summary**:
- **Tasks** define what the role does (e.g., install `nginx`, start a service).
- **Defaults** provide fallback values for variables (e.g., `port: 80`) that the tasks can use.
- Together, they make the role reusable and configurable. For example, the tasks might use the `port` variable to configure `nginx` to listen on the specified port:
  ```yaml
  - name: Configure nginx port
    ansible.builtin.template:
      src: nginx.conf.j2
      dest: /etc/nginx/nginx.conf
      owner: root
      group: root
      mode: '0644'
    notify: Restart nginx
  ```
  And the `nginx.conf.j2` template might contain:
  ```
  listen {{ port }};
  ```

---

### **2. Pass Variables to the Role**

This section shows how to call a role in a playbook and pass variables to it.

#### **Playbook Example**:
```yaml
- hosts: webservers
  roles:
    - role: my_role
      vars:
        port: 8080
```

#### **Breakdown**:
1. **`hosts: webservers`**:
   - This specifies the target hosts for the playbook, in this case, the group `webservers` from your inventory (e.g., `dev-web1`, `prod-web1`).

2. **`roles:`**:
   - This section lists the roles to apply to the hosts.
   - Here, the role `my_role` is being applied.

3. **`role: my_role`**:
   - This specifies the role to use (`my_role`).
   - Ansible looks for this role in the `roles/` directory (e.g., `roles/my_role/`).

4. **`vars:`**:
   - This allows you to pass variables to the role.
   - In this case, `port: 8080` is passed, overriding the default `port: 80` defined in `roles/my_role/defaults/main.yml`.

#### **What Happens**:
- Ansible applies the `my_role` role to the `webservers` group.
- The role uses the `port: 8080` value passed in the playbook instead of the default `port: 80`.
- The tasks in `my_role` (e.g., configuring `nginx`) will use `port: 8080`. For example, the `nginx.conf` file might end up with:
  ```
  listen 8080;
  ```

#### **Why Pass Variables?**:
- Passing variables makes the role flexible. For example:
  - You might use `port: 8080` for a development environment (`dev-web1`).
  - For production (`prod-web1`), you might pass `port: 443` to use HTTPS.
- This allows the same role to be reused across different environments with different configurations.

#### **Variable Precedence**:
- Variables passed via `vars` in the playbook (like `port: 8080`) have higher precedence than those in `defaults/main.yml`.
- If you don’t pass `port` in the playbook, the role will fall back to the default `port: 80`.

---

### **3. Reuse Role Across Playbooks**

This section emphasizes that roles are designed for reusability.

#### **Store in `roles/`, Call with `roles:` or `include_role:`**
- **Store in `roles/`**:
  - Roles are typically stored in a `roles/` directory in your Ansible project. For example:
    ```
    ansible_project/
    ├── playbooks/
    │   └── deploy.yml
    ├── roles/
    │   └── my_role/
    │       ├── tasks/
    │       │   └── main.yml
    │       ├── defaults/
    │       │   └── main.yml
    │       └── templates/
    │           └── nginx.conf.j2
    └── inventory
    ```
  - The `my_role` role can be reused by any playbook in your project.

- **Call with `roles:`**:
  - As shown in the example above, you can use the `roles:` keyword in a playbook to apply the role:
    ```yaml
    - hosts: webservers
      roles:
        - role: my_role
          vars:
            port: 8080
    ```
  - This is the standard way to apply roles at the play level.

- **Call with `include_role:`**:
  - Alternatively, you can use the `include_role` module to apply a role dynamically within a task:
    ```yaml
    - name: Apply my_role dynamically
      hosts: webservers
      tasks:
        - name: Include my_role with custom port
          ansible.builtin.include_role:
            name: my_role
          vars:
            port: 8080
    ```
  - **Difference**:
    - `roles:` applies the role at the play level and runs it before any tasks in the play.
    - `include_role:` applies the role as a task, giving you more control over when and how the role is executed (e.g., inside a loop or conditionally).

#### **Why Reuse Roles?**:
- **Modularity**: Roles encapsulate functionality (e.g., installing and configuring `nginx`), making it easy to reuse across playbooks.
- **Consistency**: You can apply the same `my_role` to different environments (dev, prod) with different variables, ensuring consistent behavior.
- **Scalability**: As your Ansible project grows, roles prevent duplication of code. Instead of writing the same tasks in multiple playbooks, you define them once in a role.

#### **Example of Reuse**:
- **Playbook 1: Deploy to Dev**:
  ```yaml
  - hosts: dev_webservers
    roles:
      - role: my_role
        vars:
          port: 8080
  ```
- **Playbook 2: Deploy to Prod**:
  ```yaml
  - hosts: prod_webservers
    roles:
      - role: my_role
        vars:
          port: 443
  ```
- Both playbooks reuse `my_role`, but with different `port` values tailored to their environments.

---

### **Complete Example of the Role Structure**

Let’s put it all together with a complete example of the `my_role` role and how it’s used.

#### **Role Directory Structure**:
```
roles/my_role/
├── defaults/
│   └── main.yml
├── tasks/
│   └── main.yml
└── templates/
    └── nginx.conf.j2
```

- **`defaults/main.yml`**:
  ```yaml
  port: 80
  webserver_user: www-data
  ```
- **`tasks/main.yml`**:
  ```yaml
  - name: Install nginx
    ansible.builtin.package:
      name: nginx
      state: present

  - name: Configure nginx
    ansible.builtin.template:
      src: nginx.conf.j2
      dest: /etc/nginx/nginx.conf
      owner: root
      group: root
      mode: '0644'
    notify: Restart nginx

  - name: Ensure nginx is running
    ansible.builtin.service:
      name: nginx
      state: started
      enabled: yes
  ```
- **`templates/nginx.conf.j2`**:
  ```
  server {
      listen {{ port }};
      server_name example.com;
      root /var/www/html;
      index index.html;
  }
  ```
- **`handlers/main.yml`** (to handle the `Restart nginx` notification):
  ```yaml
  - name: Restart nginx
    ansible.builtin.service:
      name: nginx
      state: restarted
  ```

#### **Playbook Using the Role**:
```yaml
- hosts: webservers
  roles:
    - role: my_role
      vars:
        port: 8080
```

#### **What Happens**:
- The playbook applies `my_role` to the `webservers` group.
- The role:
  - Installs `nginx`.
  - Configures `nginx` to listen on port `8080` (overriding the default `port: 80`).
  - Ensures `nginx` is running.
- If `nginx.conf` changes, the `Restart nginx` handler is triggered.

#### **Reusing in Another Playbook**:
```yaml
- hosts: prod_webservers
  roles:
    - role: my_role
      vars:
        port: 443
```
- This playbook reuses `my_role` but configures `nginx` to listen on port `443` for production.

---

### **Summary**:
- **Tasks vs. Defaults**:
  - `tasks/` contains the actions (e.g., install, configure).
  - `defaults/` provides fallback variables (e.g., `port: 80`).
- **Pass Variables to Role**:
  - Use `vars:` in the playbook to override defaults (e.g., `port: 8080`).
- **Reuse Role Across Playbooks**:
  - Store roles in `roles/`.
  - Call them with `roles:` (play-level) or `include_role:` (task-level).
  - Reuse the same role across playbooks with different variables for flexibility.

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

#### Title: Manage Multiple Vault Passwords
This indicates that the instructions are for handling scenarios where you have multiple encrypted files or secrets in Ansible, each potentially requiring a different vault password for decryption.

#### 1. **Encrypt**
The first command is:

```
ansible-vault encrypt secrets.yml --vault-id dev@prompt
```

##### Explanation:
- **`ansible-vault encrypt`**: This is the Ansible Vault command used to encrypt a file. Here, the file being encrypted is `secrets.yml`, which likely contains sensitive data (e.g., passwords, API keys, etc.).
- **`secrets.yml`**: The target file that will be encrypted. After encryption, its contents will be unreadable without the correct vault password.
- **`--vault-id dev@prompt`**: The `--vault-id` flag allows you to specify a label (`dev`) and a source for the vault password (`prompt`).
  - `dev`: This is a user-defined label to identify the vault password. It’s useful when managing multiple vault passwords (e.g., `dev`, `prod`, etc.).
  - `prompt`: This tells Ansible Vault to prompt you to manually enter the vault password during the encryption process.
  
##### What Happens:
When you run this command, Ansible Vault will:
1. Prompt you to enter a password for the `dev` vault ID.
2. Use that password to encrypt the `secrets.yml` file.
3. The file `secrets.yml` will now be encrypted, and the `dev` label will be associated with the password you provided.

This step is useful when you want to encrypt a file with a specific password and label it for later use (e.g., in a development environment).

---

#### 2. **Run**
The second command is:

```
ansible-playbook --vault-id dev@dev_pass.txt
```

##### Explanation:
- **`ansible-playbook`**: This is the command to run an Ansible playbook, which is a set of automation tasks defined in YAML format.
- **`--vault-id dev@dev_pass.txt`**: This flag tells Ansible how to access the vault password needed to decrypt any encrypted files (like `secrets.yml`) used by the playbook.
  - `dev`: This matches the label used during encryption (`dev` from the earlier step). Ansible will look for the password associated with this label.
  - `dev_pass.txt`: This specifies that the vault password for the `dev` label should be read from a file named `dev_pass.txt` instead of prompting the user.

##### What Happens:
When you run this command, Ansible will:
1. Look for the `dev` vault ID and retrieve the password from the file `dev_pass.txt`.
2. Use that password to decrypt any encrypted files (e.g., `secrets.yml`) that are referenced in the playbook and associated with the `dev` vault ID.
3. Execute the playbook with the decrypted data.

##### Why Use a Password File?
- Storing the password in a file (`dev_pass.txt`) is more convenient for automation, as it avoids the need to manually enter the password each time the playbook runs.
- However, this file should be secured (e.g., restricted file permissions) because it contains the plaintext vault password.

---

#### Overall Workflow:
1. **Encryption Step**: You encrypt a sensitive file (`secrets.yml`) using a password associated with the `dev` vault ID. You’re prompted to enter the password manually during encryption.
2. **Execution Step**: When running a playbook that needs to access the encrypted `secrets.yml`, you provide the vault password via a file (`dev_pass.txt`) instead of entering it manually. The `dev` label ensures Ansible uses the correct password for decryption.

---

#### Additional Notes:
- **Multiple Vault IDs**: Ansible Vault supports multiple vault IDs, so you can encrypt different files with different passwords (e.g., `dev@prompt` for development, `prod@prod_pass.txt` for production). When running a playbook, you can specify multiple `--vault-id` flags (e.g., `--vault-id dev@dev_pass.txt --vault-id prod@prod_pass.txt`).
- **Security Best Practices**:
  - The `dev_pass.txt` file should be stored securely, as it contains the plaintext password. Consider using file permissions (e.g., `chmod 600 dev_pass.txt`) or a secrets management system.
  - Alternatively, you can use `--vault-id dev@prompt` during playbook execution to manually enter the password, but this isn’t ideal for automated workflows.
- **Use Case**: This setup is common in environments where you have different teams or environments (e.g., dev, staging, prod) with separate vault passwords for security.

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

---

#### **Parallel Execution in Ansible**
Ansible automates tasks across multiple hosts, and parallel execution determines how tasks are distributed and executed across these hosts. Let’s break this down.

##### **Forks (default 5) run tasks across hosts concurrently. Tasks per host are sequential.**
- **Explanation**:
  - Forks define how many hosts Ansible processes in parallel. The default is 5, meaning Ansible can handle 5 hosts at once.
  - For example, if you have 10 hosts, Ansible processes the first 5 in parallel, then the next 5.
  - While tasks are executed concurrently *across hosts*, tasks on each individual host are executed sequentially (one after the other).

- **Why it matters**:
  - Forks balance speed and resource usage. More forks speed up execution by processing more hosts simultaneously but increase resource consumption (CPU, memory, network) on the control node (the machine running Ansible).

- **Codable Example**:
  Let’s create a simple playbook to demonstrate forks. First, set up an inventory file with 10 hosts (for simulation, we’ll use localhost with aliases).

  **Inventory file (`inventory.ini`)**:
  ```ini
  [webservers]
  host1 ansible_host=localhost ansible_connection=local
  host2 ansible_host=localhost ansible_connection=local
  host3 ansible_host=localhost ansible_connection=local
  host4 ansible_host=localhost ansible_connection=local
  host5 ansible_host=localhost ansible_connection=local
  host6 ansible_host=localhost ansible_connection=local
  host7 ansible_host=localhost ansible_connection=local
  host8 ansible_host=localhost ansible_connection=local
  host9 ansible_host=localhost ansible_connection=local
  host10 ansible_host=localhost ansible_connection=local
  ```

  **Playbook (`forks_example.yml`)**:
  ```yaml
  - name: Demonstrate forks in Ansible
    hosts: webservers
    tasks:
      - name: Simulate a task with a delay
        ansible.builtin.command: sleep 2
        register: result

      - name: Output the result
        ansible.builtin.debug:
          msg: "Task completed on {{ inventory_hostname }}"
  ```

  **Run with default forks (5)**:
  ```bash
  ansible-playbook -i inventory.ini forks_example.yml
  ```
  - Ansible will process 5 hosts at a time (e.g., host1 to host5), then the next 5 (host6 to host10). Each `sleep 2` task takes 2 seconds, so the first batch takes ~2 seconds, and the second batch takes another ~2 seconds. Total time: ~4 seconds.

  **Run with higher forks (10)**:
  ```bash
  ansible-playbook -i inventory.ini forks_example.yml -f 10
  ```
  - With `forks = 10`, Ansible processes all 10 hosts in parallel. Total time: ~2 seconds, since all hosts are handled at once.

- **Key Observation**:
  - The `-f 10` flag overrides the default forks, speeding up the run by processing more hosts concurrently.
  - On each host, the tasks (`sleep 2` and `debug`) are executed sequentially.

---

##### **Sets parallel processes. Higher forks (e.g., 20) speeds up large runs, but strains resources.**
- **Explanation**:
  - You can increase forks to process more hosts in parallel by setting the `forks` value in `ansible.cfg` or via the command line (`-f`).
  - Higher forks (e.g., 20) speed up execution for large environments but consume more resources on the control node (e.g., more SSH connections, higher memory usage).

- **Codable Example**:
  Let’s modify the `ansible.cfg` file to set forks globally and run the same playbook.

  **Create or edit `ansible.cfg`**:
  ```ini
  [defaults]
  forks = 20
  ```

  **Run the playbook again**:
  ```bash
  ansible-playbook -i inventory.ini forks_example.yml
  ```
  - With `forks = 20` in `ansible.cfg`, Ansible will process up to 20 hosts in parallel. Since we only have 10 hosts, all will be processed at once, taking ~2 seconds.

- **Resource Consideration**:
  - If you had 100 hosts, `forks = 20` would process them in batches of 20, taking ~10 seconds (5 batches × 2 seconds each). However, this opens 20 SSH connections at a time, which could strain the control node if resources are limited.
  - To monitor resource usage, you can use tools like `htop` on the control node while the playbook runs.

---

#### **Optimize Ansible for Scale**
##### **More forks, pipelining, dynamic inventory, fact caching, Tower/AWX.**
- **Explanation**:
  Ansible provides several techniques to scale efficiently for large environments. Let’s explore each with examples.

  1. **More Forks**:
     - Already covered above. Increasing forks is a simple way to scale, but it’s resource-intensive.

  2. **Pipelining**:
     - Pipelining reduces SSH overhead by reusing the same SSH connection for multiple tasks instead of opening a new connection for each task.
     - **Requirement**: Pipelining requires `ControlPersist` in SSH and may not work if `requiretty` is enabled on target hosts (disable it in `/etc/sudoers` if needed).

     **Enable pipelining in `ansible.cfg`**:
     ```ini
     [ssh_connection]
     pipelining = True
     ```

     **Playbook to test pipelining (`pipelining_example.yml`)**:
     ```yaml
     - name: Test pipelining
       hosts: webservers
       tasks:
         - name: Task 1 - Simulate work
           ansible.builtin.command: sleep 1
         - name: Task 2 - Simulate more work
           ansible.builtin.command: sleep 1
         - name: Task 3 - Output
           ansible.builtin.debug:
             msg: "Completed on {{ inventory_hostname }}"
     ```

     **Run without pipelining**:
     - First, disable pipelining in `ansible.cfg` (`pipelining = False`) or comment it out.
     ```bash
     ansible-playbook -i inventory.ini pipelining_example.yml
     ```
     - Ansible opens a new SSH connection for each task (3 tasks = 3 connections per host). This adds overhead, especially for many hosts.

     **Run with pipelining**:
     - Re-enable `pipelining = True` in `ansible.cfg`.
     ```bash
     ansible-playbook -i inventory.ini pipelining_example.yml
     ```
     - Ansible uses a single SSH connection per host for all tasks, reducing overhead and speeding up execution.

  3. **Dynamic Inventory**:
     - Dynamic inventory fetches the host list from an external source (e.g., AWS) at runtime, instead of using a static inventory file.
     - Let’s use the `aws_ec2` plugin to manage EC2 instances (requires `boto3` for AWS API access).

     **Install required packages**:
     ```bash
     pip install boto3
     ```

     **Dynamic inventory file (`aws_inventory.yml`)**:
     ```yaml
     plugin: aws_ec2
     regions:
       - us-east-1
     filters:
       instance-state-name: running
     keyed_groups:
       - key: tags.Role
         separator: ''
     ```

     **Playbook (`aws_playbook.yml`)**:
     ```yaml
     - name: Run on AWS instances
       hosts: all
       tasks:
         - name: Check uptime
           ansible.builtin.command: uptime
           register: uptime_result

         - name: Display uptime
           ansible.builtin.debug:
             msg: "{{ uptime_result.stdout }}"
     ```

     **Run the playbook**:
     ```bash
     ansible-playbook -i aws_inventory.yml aws_playbook.yml
     ```
     - Ansible dynamically discovers EC2 instances in the `us-east-1` region with the state `running` and groups them by the `Role` tag.

  4. **Fact Caching**:
     - Fact caching stores system facts (e.g., OS, IP) to avoid regathering them in every run. We’ll use Redis as the backend.

     **Install Redis**:
     ```bash
     sudo apt install redis-server  # On Ubuntu
     pip install redis  # Python client for Ansible
     ```

     **Enable fact caching in `ansible.cfg`**:
     ```ini
     [defaults]
     fact_caching = redis
     fact_caching_timeout = 86400  # Cache for 24 hours
     ```

     **Playbook (`fact_caching_example.yml`)**:
     ```yaml
     - name: Test fact caching
       hosts: webservers
       tasks:
         - name: Display a fact
           ansible.builtin.debug:
             msg: "OS family is {{ ansible_os_family }}"
     ```

     **Run the playbook twice**:
     ```bash
     ansible-playbook -i inventory.ini fact_caching_example.yml
     ansible-playbook -i inventory.ini fact_caching_example.yml
     ```
     - First run: Ansible gathers facts and caches them in Redis.
     - Second run: Ansible reuses the cached facts, skipping the fact-gathering step, which speeds up execution.

  5. **Tower/AWX**:
     - Ansible Tower (commercial) or AWX (open-source) provides a UI for managing Ansible at scale. It’s not directly codable here, but you can set it up as follows:
       - Install AWX using Docker or Kubernetes (refer to the official AWX GitHub).
       - Use AWX to create projects, inventories, and job templates for your playbooks.
       - Example: In AWX, create a job template for the `forks_example.yml` playbook and schedule it to run daily.

---

##### **Task Execution Mode: linear (waits), free (no wait), debug (interactive).**
- **Explanation**:
  - Ansible’s "strategy" defines how tasks are executed across hosts:
    - `linear`: Waits for all hosts to finish a task before moving to the next (default, synchronous).
    - `free`: Hosts proceed independently (asynchronous, faster).
    - `debug`: Interactive mode for troubleshooting.

- **Codable Examples**:
  Let’s test each strategy with a playbook.

  **Playbook (`strategy_example.yml`)**:
  ```yaml
  - name: Test linear strategy
    hosts: webservers
    strategy: linear
    tasks:
      - name: Task 1 - Simulate variable delay
        ansible.builtin.command: "sleep {{ sleep_time }}"
        vars:
          sleep_time: "{{ 2 if inventory_hostname in ['host1', 'host2'] else 5 }}"
      - name: Task 2 - Output
        ansible.builtin.debug:
          msg: "Task 2 on {{ inventory_hostname }}"

  - name: Test free strategy
    hosts: webservers
    strategy: free
    tasks:
      - name: Task 1 - Simulate variable delay
        ansible.builtin.command: "sleep {{ sleep_time }}"
        vars:
          sleep_time: "{{ 2 if inventory_hostname in ['host1', 'host2'] else 5 }}"
      - name: Task 2 - Output
        ansible.builtin.debug:
          msg: "Task 2 on {{ inventory_hostname }}"

  - name: Test debug strategy
    hosts: webservers
    strategy: debug
    tasks:
      - name: Task 1 - Debug task
        ansible.builtin.debug:
          msg: "Debugging on {{ inventory_hostname }}"
  ```

  **Run the playbook**:
  ```bash
  ansible-playbook -i inventory.ini strategy_example.yml
  ```

  - **Linear Strategy**:
    - Hosts `host1` and `host2` sleep for 2 seconds, while others sleep for 5 seconds.
    - Ansible waits for all hosts to finish Task 1 (5 seconds) before moving to Task 2. Total time for this play: ~5 seconds.
  - **Free Strategy**:
    - `host1` and `host2` finish Task 1 in 2 seconds and move to Task 2 immediately, while others take 5 seconds.
    - Total time depends on the slowest host (5 seconds), but faster hosts don’t wait, improving overall efficiency.
  - **Debug Strategy**:
    - Ansible pauses after each task, allowing you to inspect output and decide whether to continue. Useful for troubleshooting.

---

#### **Summary with Codable Takeaways**
- **Forks**:
  - Set forks in `ansible.cfg` or via `-f` to control parallelism.
  - Example: `ansible-playbook -f 10 playbook.yml`.
- **Optimizing for Scale**:
  - **Pipelining**: Enable in `ansible.cfg` to reduce SSH overhead.
  - **Dynamic Inventory**: Use plugins like `aws_ec2` for auto-discovered hosts.
  - **Fact Caching**: Use Redis to cache facts and speed up runs.
  - **Tower/AWX**: Use for centralized management (setup required).
- **Strategies**:
  - `linear`: Synchronous, waits for all hosts.
  - `free`: Asynchronous, hosts proceed independently.
  - `debug`: Interactive, for troubleshooting.

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