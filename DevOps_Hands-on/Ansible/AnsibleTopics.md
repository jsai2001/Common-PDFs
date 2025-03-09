# Topic 1: What is Ansible?
**Ansible** is an open-source automation tool used for configuration management, application deployment, and task automation. It’s agentless, meaning it doesn’t require any software to be installed on the nodes it manages, and it uses SSH to communicate with them. Ansible is written in Python and uses YAML for its playbooks, making it simple and human-readable.

# Topic 2: Ansible Playbooks
**Playbooks** are the core of Ansible’s configuration management and automation capabilities. They are written in YAML and define a set of tasks to be executed on remote hosts. A playbook can include variables, roles, and conditions, allowing you to automate complex workflows. For example, a playbook might install a web server, configure it, and deploy an application—all in one run.

# Topic 3: Ansible Roles
**Roles** in Ansible are a way to organize playbooks and related files (like tasks, handlers, templates, and variables) into reusable components. They follow a specific directory structure, making it easier to manage complex projects. For instance, you might have a role called "webserver" that includes tasks to install Nginx, copy configuration files, and restart the service. Roles can be shared and reused across different playbooks or even with the Ansible community via Ansible Galaxy.

# Topic 4: Ansible Inventory
The **inventory** in Ansible is a file (or a dynamic source) that defines the hosts and groups of hosts on which Ansible will execute commands or playbooks. By default, it’s a simple text file (often named `inventory` or `hosts`), where you list IP addresses, hostnames, or group them logically (e.g., `[webservers]` or `[databases]`). You can also use dynamic inventories to pull host data from cloud providers like AWS or Azure, making it highly flexible for managing large or changing environments.

# Topic 5: Ansible Modules
**Modules** are the building blocks of Ansible tasks. They are small programs that Ansible executes on the target hosts to perform specific actions, like installing a package (`yum` or `apt` module), managing files (`copy` or `template` module), or restarting a service (`service` module). Ansible comes with hundreds of built-in modules, and you can also write custom ones in Python. Each task in a playbook typically calls a module with specific parameters to achieve the desired outcome.

# Topic 6: Ansible Variables
**Variables** in Ansible allow you to customize and reuse your playbooks by storing data that can change depending on the environment, host, or use case. They can be defined in several places: in the inventory (host or group vars), in playbooks, in separate variable files, or even passed at runtime using the `-e` flag. For example, you might define a variable like `http_port: 80` and use it in a task to configure a web server, making your playbook adaptable to different scenarios.

# Topic 7: Ansible Facts
**Ansible Facts** are variables automatically gathered from managed hosts when a playbook runs. They provide system information like the operating system, IP addresses, CPU details, memory, and more. You can use facts in your playbooks to make decisions—like installing a specific package version based on the OS (`{{ ansible_os_family }}`)—or simply to log details about your environment. You can also disable fact gathering with `gather_facts: no` if you don’t need them, speeding up execution.

# Topic 8: Ansible Handlers
**Handlers** in Ansible are special tasks that only run when triggered by a change caused by another task. They’re typically used for actions like restarting a service after a configuration file is updated. For example, if a task modifies an Nginx config file, it can notify a handler named "restart nginx," which then executes the restart only if the config actually changed. Handlers are defined in a playbook or role and are a great way to avoid unnecessary actions.

# Topic 9: Ansible Loops
**Loops** in Ansible allow you to repeat a task multiple times with different data, reducing playbook verbosity. You can use the `loop` keyword (or the older `with_items`) to iterate over a list of items. For example, to install multiple packages, you might write:
```yaml
- name: Install web packages
    apt:
        name: "{{ item }}"
        state: present
    loop:
        - nginx
        - apache2
```
This runs the `apt` module once for each item in the list. Loops can also iterate over dictionaries or more complex data structures.

# Topic 10: Ansible Conditionals
**Conditionals** in Ansible let you execute tasks only when certain conditions are met, adding logic to your playbooks. They use the `when` keyword and often rely on facts, variables, or task results. For example:
```yaml
- name: Install Nginx on Debian systems
    apt:
        name: nginx
        state: present
    when: ansible_os_family == "Debian"
```
This task only runs if the target host is a Debian-based system. Conditionals can use operators like `==`, `!=`, `<`, `>`, or even test if a variable is defined (`is defined`).

# Topic 11: Ansible Templates
**Templates** in Ansible are files that use the Jinja2 templating engine to dynamically generate content based on variables or facts. They’re typically stored with a `.j2` extension and are deployed to hosts using the `template` module. For example, you might create an Nginx config template with a variable like `{{ server_port }}`, and Ansible replaces it with the actual value (e.g., 80) when copying the file to the target host. This is ideal for customizing configs across different environments.

# Topic 12: Ansible Vault
**Ansible Vault** is a feature that encrypts sensitive data, like passwords, API keys, or other secrets, within your playbooks or variable files. You can create an encrypted file with `ansible-vault create`, edit it with `ansible-vault edit`, or encrypt an existing file with `ansible-vault encrypt`. When running a playbook that uses vault-encrypted data, you provide the vault password (e.g., via `--ask-vault-pass` or a password file). This ensures security while still allowing automation.

# Topic 13: Ansible Galaxy
**Ansible Galaxy** is a public repository and community hub where users can share and download Ansible roles to simplify automation tasks. You can access it via the `ansible-galaxy` command-line tool—for example, `ansible-galaxy install geerlingguy.nginx` installs a popular Nginx role by Jeff Geerling. Galaxy roles save time by providing pre-built, tested solutions for common tasks like setting up web servers, databases, or Docker, and you can customize them to fit your needs.

# Topic 14: Ansible Ad-Hoc Commands
**Ad-hoc commands** in Ansible let you run quick, one-off tasks on hosts without writing a full playbook. You use the `ansible` command with a module and target hosts from your inventory. For example:
```bash
ansible webservers -m ping
```
This pings all hosts in the webservers group. Or:
```bash
ansible all -m shell -a "uptime"
```
This runs the `uptime` command on all hosts. Ad-hoc commands are great for testing, troubleshooting, or simple tasks like restarting services.

# Topic 15: Ansible Tower / AWX
**Ansible Tower** (or its open-source version, **AWX**) is a web-based GUI and automation platform that extends Ansible’s capabilities. It provides a centralized interface for managing playbooks, inventories, and schedules, along with features like role-based access control, job templates, and detailed logging. Tower/AWX is especially useful for teams or enterprises needing a more user-friendly way to scale Ansible automation, monitor jobs, or integrate with CI/CD pipelines.

# Topic 16: Ansible Tags
**Tags** in Ansible allow you to run or skip specific parts of a playbook selectively. You can assign tags to tasks, blocks, or roles, then use the `--tags` or `--skip-tags` flags when running the playbook. For example:
```yaml
- name: Install Nginx
    apt:
        name: nginx
        state: present
    tags:
        - install
```
Running `ansible-playbook playbook.yml --tags install` executes only tasks tagged "install." This is handy for debugging, partial deployments, or managing large playbooks.

# Topic 17: Ansible Debugging
Ansible provides tools to troubleshoot and debug playbooks when things go wrong. You can increase verbosity with `-v`, `-vv`, or `-vvv` flags when running `ansible-playbook` to see more detailed output (e.g., `ansible-playbook playbook.yml -vvv`). The `debug` module is also useful for printing variables or messages during execution, like:
```yaml
- name: Print a variable
    debug:
        msg: "The value is {{ my_variable }}"
```
Additionally, the `failed_when` or `changed_when` keywords let you customize when a task is considered failed or changed.

# Topic 18: Ansible Blocks
**Blocks** in Ansible group multiple tasks together, allowing you to apply common attributes—like conditionals, loops, or error handling—to all tasks within the block. For example:
```yaml
- block:
        - name: Install Nginx
            apt:
                name: nginx
                state: present
        - name: Start Nginx
            service:
                name: nginx
                state: started
    when: ansible_os_family == "Debian"
```
You can also use `rescue` and `always` sections with blocks to handle errors (like a try-catch) or ensure certain tasks run regardless of success or failure.

# Topic 19: Ansible Delegates
**Delegation** in Ansible lets you run a task on a different host than the one specified in the playbook’s target. You use the `delegate_to` keyword to redirect a task. For example:
```yaml
- name: Update DNS record
    nsupdate:
        key_name: "mykey"
        record: "{{ inventory_hostname }}"
        value: "{{ ansible_host }}"
    delegate_to: dns_server
```
Here, the task runs on `dns_server` instead of the target host. This is useful for tasks like updating a database, notifying a monitoring system, or managing load balancers.

# Topic 20: Ansible Best Practices
**Ansible best practices** help ensure maintainable, efficient automation. Some key tips include:
- Use roles to organize playbooks into reusable, modular components.
- Keep playbooks simple and readable with clear task names and comments.
- Store sensitive data in Ansible Vault, not plaintext.
- Leverage variables and templates for flexibility across environments (e.g., dev, prod).
- Test playbooks in a staging environment before production.
- Use version control (like Git) for playbooks and roles to track changes.

Following these practices makes your Ansible projects scalable and easier to collaborate on.

# Topic 21: Ansible Collections

**Ansible Collections** are a distribution format introduced in Ansible 2.10 to package and share roles, modules, plugins, and documentation together. They replace the older monolithic approach of bundling everything with Ansible core. You can install collections from Ansible Galaxy or Automation Hub using `ansible-galaxy collection install <namespace>.<collection>`, like `ansible-galaxy collection install community.general`. Collections allow for better modularity, versioning, and community contributions—e.g., the `community.crypto` collection for cryptography tasks.

# Topic 22: Ansible Execution Strategies

**Ansible execution strategies** control how tasks are run across hosts. The default strategy is linear, where tasks are executed on all hosts in sequence, one task at a time. You can change this with the `strategy` keyword. For example:  
- **free**: Allows hosts to run tasks as fast as they can, without waiting for others to finish the same task.  
- **host_pinned**: Executes all tasks on one host before moving to the next.

Set it in a playbook like:
```yaml
- hosts: all
    strategy: free
    tasks:
        - name: Do something
            command: sleep 5
```
This is useful for optimizing performance or handling dependencies.

# Topic 23: Ansible Filters

**Filters in Ansible** are Jinja2 functions used to transform or manipulate data within playbooks, often applied to variables. For example, you can use `{{ my_var | default('value') }}` to set a default if `my_var` is undefined, or `{{ list_var | join(',') }}` to convert a list into a comma-separated string. Other common filters include `upper`, `lower`, `length`, and `random`. You can also chain filters, like `{{ my_string | trim | upper }}`, to clean and capitalize a string. Filters are powerful for formatting or conditioning data on the fly.

# Topic 24: Ansible Lookup Plugins

**Lookup plugins in Ansible** retrieve data from external sources and make it available as variables in your playbooks. Unlike modules, lookups run on the control node (where Ansible is executed), not the target hosts. For example:
```yaml
- name: Read a file from the control node
    debug:
        msg: "{{ lookup('file', '/path/to/local/file.txt') }}"
```
Common lookups include `file` (reads file contents), `env` (gets environment variables), `password` (generates random passwords), and `pipe` (runs a command and captures output). They’re great for pulling in dynamic data without hardcoding it.

# Topic 25: Ansible Dynamic Inventory

**Dynamic inventory in Ansible** allows you to generate your inventory programmatically from external sources, rather than maintaining a static file. Ansible supports scripts (e.g., in Python) or plugins for cloud providers like AWS, Azure, or GCP. For example, the `aws_ec2` plugin can fetch EC2 instances:
```yaml
# inventory.yml
plugin: aws_ec2
regions:
    - us-east-1
filters:
    tag:Environment: production
```
Run it with `ansible-inventory -i inventory.yml --list`. This is ideal for managing cloud-based or frequently changing environments automatically.

# Topic 26: Ansible Error Handling

**Ansible provides several ways to handle errors gracefully in playbooks**. Beyond blocks (with rescue and always), you can use:
- `ignore_errors: yes` to continue playbook execution even if a task fails:
```yaml
- name: Try something risky
    command: /bin/false
    ignore_errors: yes
```
- `failed_when`: To define custom failure conditions, e.g., `failed_when: "'ERROR' in result.stdout"`.
- `register`: To capture task output and check it later with conditionals.

This flexibility ensures your automation can recover from or adapt to unexpected issues.

# Topic 27: Ansible Playbook Callbacks

**Callbacks in Ansible** are plugins that extend how Ansible reports or processes events during playbook execution. They can customize output, log results to external systems, or trigger notifications. For example, the `slack` callback sends task status to a Slack channel, while `profile_tasks` shows timing stats for tasks. You enable them in `ansible.cfg`:
```ini
[defaults]
callback_whitelist = profile_tasks, slack
```
Custom callbacks can be written in Python to integrate with tools like monitoring systems or dashboards, enhancing visibility into automation runs.

# Topic 28: Ansible Privilege Escalation

**Privilege escalation in Ansible** lets you run tasks as a different user (e.g., root) on target hosts, often required for system-level changes like installing packages or editing protected files. You enable it with options like `become: yes` in a playbook:
```yaml
- name: Install a package as root
    apt:
        name: nginx
        state: present
    become: yes
    become_user: root
```
Use `--become` (or `-b`) on the command line, and specify the method (e.g., sudo, su) in `ansible.cfg` or with `become_method`. It’s critical for tasks needing elevated permissions.

# Topic 29: Ansible Rolling Updates

**Rolling updates in Ansible** allow you to update a group of hosts (e.g., web servers) in batches, minimizing downtime. You use the `serial` keyword in a play to control how many hosts are processed at once. For example:
```yaml
- hosts: webservers
    serial: 2
    tasks:
        - name: Update app
            command: /usr/bin/update-app
        - name: Restart service
            service:
                name: app
                state: restarted
```
Here, Ansible updates two servers at a time, waiting for them to finish before moving to the next pair. You can also use percentages (e.g., `serial: "50%"`). This is ideal for maintaining service availability.

# Topic 30: Ansible Testing with Molecule

**Molecule is a testing framework designed for Ansible roles**, helping you verify they work as expected across different environments. It automates the creation of test instances (e.g., via Docker or Vagrant), applies your role, and runs tests (like syntax checks or idempotence verification). Basic usage:
```bash
molecule init role my-role
molecule test
```
You define test scenarios in a `molecule.yml` file, specifying platforms (e.g., Ubuntu, CentOS) and test suites (using Testinfra or Ansible itself). Molecule ensures your roles are reliable and portable before deployment.

# Topic 31: Ansible Idempotence

**Idempotence is a core principle in Ansible**, meaning that running a playbook multiple times produces the same result as running it once, without unintended changes. For example, a task using the `apt` module to ensure a package is installed won’t reinstall it if it’s already present:
```yaml
- name: Ensure Nginx is installed
    apt:
        name: nginx
        state: present
```
Ansible modules are designed to check the current state and only act if needed. You can test idempotence by running a playbook twice—tasks should report `changed=0` on the second run unless something external changed the system.

# Topic 32: Ansible Connection Plugins

**Connection plugins in Ansible** define how Ansible communicates with target hosts. The default is `ssh` (using OpenSSH), but others include `paramiko` (Python-based SSH), `local` (runs tasks on the control node), `docker` (manages Docker containers), and `winrm` (for Windows hosts). You set them with the `connection` keyword:
```yaml
- hosts: windows_servers
    connection: winrm
    tasks:
        - name: Check uptime
            win_command: systeminfo
```
You can also configure connection details (e.g., SSH port, user) in the inventory or `ansible.cfg`. This flexibility supports diverse environments.

# Topic 33: Ansible Inventory Plugins

**Inventory plugins in Ansible** extend dynamic inventory capabilities beyond scripts, offering a modular way to fetch host data from various sources. Examples include `aws_ec2` (for AWS instances), `gcp_compute` (Google Cloud), or `yaml` (reads YAML files). You configure them in an inventory file like:
```yaml
# inventory.yml
plugin: aws_ec2
regions:
    - us-west-2
```
Run with `ansible-inventory -i inventory.yml --graph` to see the hosts. Inventory plugins are more maintainable and reusable than custom scripts, integrating seamlessly with modern infrastructure.

# Topic 34: Ansible Task Delegation with delegate_facts

The `delegate_facts` option in Ansible lets you gather facts from a delegated host (using `delegate_to`) instead of the target host. This is useful when a task runs elsewhere but you need its system info. For example:
```yaml
- name: Gather facts from a proxy server
    setup:
    delegate_to: proxy_host
    delegate_facts: yes
- name: Use proxy facts
    debug:
        msg: "Proxy OS: {{ ansible_facts['os_family'] }}"
```
Without `delegate_facts: yes`, facts would still come from the original target. This is handy for multi-tier workflows.

# Topic 35: Ansible Async Tasks

**Asynchronous tasks in Ansible** allow long-running operations to run in the background, freeing up the control node to continue with other tasks. You use `async` (time in seconds) and `poll` (check interval) keywords. For example:
```yaml
- name: Run a long update
    command: apt-get update
    async: 300  # Run for up to 5 minutes
    poll: 10    # Check every 10 seconds
```
Set `poll: 0` to "fire and forget" without waiting for completion. Use the `async_status` module later to check results. This is great for tasks like software upgrades or backups.

# Topic 36: Ansible Playbook Includes and Imports

**Ansible lets you break playbooks into smaller, reusable pieces** using `include` and `import`. The difference is execution timing:
- `import_tasks` (static): Loads tasks at parse time, e.g.:
```yaml
- import_tasks: install_nginx.yml
```
- `include_tasks` (dynamic): Loads tasks at runtime, supporting loops or conditionals, e.g.:
```yaml
- include_tasks: "{{ task_file }}"
    when: ansible_os_family == "Debian"
```
You can also import/include roles or entire playbooks. This modularity improves organization and reuse across projects.

# Topic 37: Ansible Command Module vs. Shell Module

**Ansible’s command and shell modules** both execute commands on target hosts, but they differ in behavior:
- **command**: Runs a command directly, without shell processing (no pipes, redirects, or environment vars like `$HOME`). Safer and faster. Example:
```yaml
- command: ls -l
```
- **shell**: Runs commands through a shell (e.g., `/bin/sh`), supporting pipes (`|`), redirects (`>`), and shell features. Example:
```yaml
- shell: cat file.txt | grep "error" > output.txt
```
Use `command` for simple tasks; `shell` when you need shell capabilities. Both lack idempotence—prefer specific modules when possible.

# Topic 38: Ansible Playbook Variables Precedence

**Ansible has a defined order of precedence for variables**, determining which value wins when the same variable is set in multiple places. From lowest to highest priority:
- Role defaults (`defaults/main.yml`)
- Inventory file or script (e.g., `group_vars`, `host_vars`)
- Playbook vars (defined in the play)
- Task vars (set in a task with `vars`)
- Command-line vars (`-e "var=value"`)

For example, `-e "version=2.0"` overrides a `version: 1.0` in `group_vars`. Understanding this helps you manage variable scope and avoid surprises.

# Topic 39: Ansible Check Mode (Dry Run)

**Check mode in Ansible** lets you simulate a playbook run without making changes, useful for testing or validation. Run it with `--check`:
```bash
ansible-playbook playbook.yml --check
```
Tasks report what would change (e.g., changed or skipped). Some modules (like `command`) need `creates` or `removes` args to work in check mode:
```yaml
- name: Create a file
    command: touch /tmp/test
    creates: /tmp/test
```
Use `--diff` with `--check` to see file content differences. It’s a safe way to preview automation impact.

# Topic 40: Ansible Forks

**Forks in Ansible** control how many hosts are processed in parallel during playbook execution. The default is 5, meaning Ansible runs tasks on up to 5 hosts at once. You can adjust this in `ansible.cfg`:
```ini
[defaults]
forks = 10
```
Or override it on the command line with `-f`:
```bash
ansible-playbook playbook.yml -f 20
```
More forks speed up execution for large inventories but increase resource use on the control node (CPU, memory, SSH connections). Tune it based on your setup and network.

# Topic 41: Ansible Environment Variables

Ansible lets you set environment variables for tasks, useful for commands or scripts that rely on them. Use the `environment` keyword at the play, block, or task level:

```yaml
- name: Run a script with custom env
    shell: echo $MY_VAR
    environment:
        MY_VAR: "Hello"
        PATH: "{{ ansible_env.PATH }}:/usr/local/bin"
```

You can also access the control node’s environment with `ansible_env` (e.g., `ansible_env.HOME`). This is handy for setting things like `JAVA_HOME`, proxies, or API keys without modifying the target system globally.

# Topic 42: Ansible Gathering Facts Optimization

Gathering facts can slow down playbook runs, especially with many hosts or slow networks. You can optimize it by:

- **Disabling facts entirely** with `gather_facts: no` if unused:

```yaml
- hosts: all
    gather_facts: no
    tasks:
        - name: Simple task
            command: uptime
```

- **Using `gather_subset` to limit fact collection**, e.g., only hardware facts:

```yaml
- hosts: all
    gather_subset:
        - '!all'
        - 'hardware'
```

- **Caching facts** with a fact cache (like `jsonfile` or `redis`) to reuse them across runs. This speeds up execution when facts aren’t critical or change infrequently.

# Topic 43: Ansible Fact Caching

Fact caching in Ansible stores gathered facts (from the setup module) in a cache, reducing the need to recollect them on every run. Enable it in `ansible.cfg`:

```ini
[defaults]
fact_caching = jsonfile
fact_caching_timeout = 86400  # 24 hours in seconds
fact_caching_dir = /path/to/cache
```

Options include `jsonfile` (stores in files), `redis`, or `memcached`. Use it with:

```yaml
- hosts: all
    gather_facts: yes
```

Subsequent runs skip fact gathering if the cache is valid, speeding up execution—ideal for stable environments.

# Topic 44: Ansible Playbook Retry Files

When a playbook fails due to unreachable hosts (e.g., network issues), Ansible creates a `.retry` file listing those hosts. You can rerun the playbook against only those failed hosts using the `--limit` flag:

```bash
ansible-playbook playbook.yml --limit @/path/to/playbook.retry
```

This avoids reprocessing successful hosts. To enable retry files, ensure `retry_files_enabled = True` in `ansible.cfg` (default is True). It’s a simple way to recover from partial failures without manual intervention.

# Topic 45: Ansible Playbook Dependencies with Roles

Roles in Ansible can depend on other roles, allowing you to build complex workflows by specifying dependencies in a role’s `meta/main.yml`. For example:

```yaml
# roles/webserver/meta/main.yml
dependencies:
    - role: common
        vars:
            some_var: "value"
    - role: nginx
```

When you run the `webserver` role, Ansible automatically applies `common` and `nginx` first, in order. Dependencies can include variables or conditions, making roles modular yet interconnected—perfect for layered setups like app stacks.

# Topic 46: Ansible Vault Integration with Playbooks

Ansible Vault encrypts sensitive data, and you can integrate it seamlessly into playbooks. For example, store encrypted vars in a file like `vars/secrets.yml`:

```bash
ansible-vault create vars/secrets.yml
```

Add content (e.g., `db_password: secret123`), then use it:

```yaml
- hosts: all
    vars_files:
        - vars/secrets.yml
    tasks:
        - name: Use secret
            debug:
                msg: "Password is {{ db_password }}"
```

Run with `--ask-vault-pass` or `--vault-password-file /path/to/pass.txt`. You can also encrypt inline vars with `!vault` syntax. This keeps secrets secure while accessible to automation.

# Topic 47: Ansible Playbook Handlers with listen

Handlers typically run when notified by a task’s `notify` directive, but the `listen` keyword lets multiple tasks trigger the same handler under a shared name. For example:

```yaml
- name: Update config
    copy:
        src: nginx.conf
        dest: /etc/nginx/nginx.conf
    notify: config_changed

- name: Install package
    apt:
        name: nginx
    notify: config_changed

handlers:
    - name: Restart nginx
        service:
            name: nginx
            state: restarted
        listen: config_changed
```

Both tasks notify `config_changed`, and the handler runs once if either triggers it. This reduces handler duplication for common actions.

# Topic 48: Ansible Playbook Pre- and Post-Tasks

Pre-tasks and post-tasks in Ansible are tasks that run before or after the main tasks and roles in a play, regardless of the play’s structure. Define them with `pre_tasks` or `post_tasks`:

```yaml
- hosts: webservers
    pre_tasks:
        - name: Check system readiness
            ping:
    roles:
        - nginx
    tasks:
        - name: Deploy app
            copy:
                src: app.tar.gz
                dest: /opt/
    post_tasks:
        - name: Log completion
            debug:
                msg: "Play done!"
```

Pre-tasks are great for setup (e.g., validation), while post-tasks handle cleanup or reporting. They execute outside role logic.

# Topic 49: Ansible Playbook Loops with with_ Keywords

While `loop` is the modern way to iterate in Ansible, older playbooks use `with_` keywords for loops, like `with_items`, `with_fileglob`, or `with_dict`. For example:

```yaml
- name: Install packages (older syntax)
    apt:
        name: "{{ item }}"
        state: present
    with_items:
        - nginx
        - curl
```

Or looping over a dictionary:

```yaml
- name: Print users
    debug:
        msg: "{{ item.key }} is {{ item.value }}"
    with_dict:
        alice: admin
        bob: user
```

These are still supported but deprecated—`loop` with filters (e.g., `loop: "{{ my_list }}"`) is preferred in newer Ansible versions.