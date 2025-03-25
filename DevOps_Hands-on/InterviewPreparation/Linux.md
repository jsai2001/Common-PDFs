## Linux Operating System Concepts Interview Questions

### Basics
- What is Linux, and how does it differ from other operating systems like Windows?
- Explain the difference between a Linux kernel and a Linux distribution.
- What are the key components of the Linux operating system?
- What is the role of the shell in Linux?
- What are some commonly used Linux shells (e.g., Bash, Zsh, Fish)?
- What is the difference between a process and a thread in Linux?
- How does Linux handle file permissions? Explain the `chmod` command with examples.
- What are the basic Linux file types (regular files, directories, symbolic links, etc.)?
- What is the purpose of the root user in Linux?
- Explain the Linux boot process in detail (e.g., BIOS, GRUB, init/systemd).

### File System and Commands
- What is the Linux file system hierarchy (e.g., /etc, /var, /home)?
- How do you check disk space usage in Linux? (e.g., `df`, `du`)
- What does the `lsblk` command do?
- Explain the difference between soft links and hard links in Linux.
- How do you find a file in Linux using the `find` or `locate` command?
- What is the purpose of the `/proc` directory?
- How can you compress and decompress files in Linux? (e.g., `tar`, `gzip`, `zip`)
- What is the difference between `cat`, `more`, and `less` commands?
- How do you redirect output to a file in Linux? Explain `>`, `>>`, and `2>` with examples.
- What is piping in Linux, and how does it work? (e.g., `ls | grep`)

### Processes and Resource Management
- How do you list all running processes in Linux? (e.g., `ps`, `top`, `htop`)
- What is the difference between `kill`, `pkill`, and `killall` commands?
- How do you run a process in the background? What does the `&` operator do?
- What is a zombie process, and how do you identify and remove it?
- How do you check CPU and memory usage in Linux? (e.g., `top`, `free`, `vmstat`)
- What is the purpose of the `nice` and `renice` commands?
- Explain the difference between `fork()` and `exec()` system calls.
- What is a daemon process in Linux, and how is it created?
- How do you schedule tasks in Linux? (e.g., `cron`, `at`)
- What is the `systemctl` command used for in systemd-based systems?

### Networking and Security
- How do you check the network configuration in Linux? (e.g., `ifconfig`, `ip`)
- What is the purpose of the `netstat` and `ss` commands?
- How do you set up a static IP address in Linux?
- Explain the `iptables` command and how it is used to configure firewall rules.
- What is SSH, and how do you use it to connect to a remote server?
- How do you generate and configure SSH keys for secure authentication?
- What is the difference between `sudo` and `su` commands?
- How do you harden a Linux system for security? (e.g., disabling root login, updating packages)
- What is SELinux, and how does it enhance security?
- How do you monitor system logs in Linux? (e.g., `/var/log`, `journalctl`)

### Advanced Topics
- What is the difference between init and systemd?
- How do you compile and install software from source code in Linux?
- What is a kernel module, and how do you load/unload it? (e.g., `modprobe`, `lsmod`)
- How does Linux handle memory management (e.g., virtual memory, swapping)?
- What is the purpose of the udev system in Linux?
- Explain the concept of cgroups and how they are used in resource management.
- What are environment variables in Linux, and how do you set them? (e.g., `export`)
- How do you troubleshoot a Linux system that fails to boot?
- What is the difference between a monolithic kernel and a microkernel?
- How do you configure a Linux system as a server (e.g., web server with Apache)?

1. **What is Linux, and how does it differ from other operating systems like Windows?**  
**Answer:**  
Linux is an open-source, Unix-like operating system originally created by Linus Torvalds in 1991. It is highly customizable, secure, and widely used in servers, embedded systems, and desktops. Unlike Windows, which is a proprietary OS developed by Microsoft, Linux is free to use and modify under the GNU General Public License. Key differences include:  
- **Open Source:** Linux source code is accessible; Windows is closed-source.  
- **Cost:** Linux is free; Windows requires a license.  
- **Customization:** Linux allows extensive modification; Windows is more restrictive.  
- **File System:** Linux uses a hierarchical structure (e.g., ext4), while Windows uses NTFS or FAT.  
- **Command Line:** Linux emphasizes CLI (e.g., Bash), while Windows focuses on GUI with optional PowerShell.

2. **Explain the difference between a Linux kernel and a Linux distribution.**  
**Answer:**  
The Linux kernel is the core component of the OS, responsible for managing hardware, processes, memory, and system resources. It’s developed by Linus Torvalds and contributors. A Linux distribution (distro) is a complete OS built around the kernel, including additional software like utilities, libraries, and a package manager. Examples include Ubuntu, Fedora, and Debian.  
- **Kernel:** Just the core (e.g., version 6.5).  
- **Distribution:** Kernel + user tools (e.g., Ubuntu adds GNOME, apt).  
Distros differ in software choices, update cycles, and target audiences.

3. **What are the key components of the Linux operating system?**  
**Answer:**  
The Linux OS consists of:  
- **Kernel:** Manages hardware, processes, and memory.  
- **Shell:** Interface for user commands (e.g., Bash).  
- **File System:** Organizes data (e.g., ext4, /etc, /home).  
- **User Space Tools:** Utilities like `ls`, `grep`, and package managers (e.g., `apt`, `yum`).  
- **Libraries:** Shared code for programs (e.g., `glibc`).  
- **System Daemons:** Background services (e.g., `sshd`, `cron`).  
- **Bootloader:** Loads the kernel (e.g., GRUB).  
Together, they enable interaction between hardware and users.

4. **What is the role of the shell in Linux?**  
**Answer:**  
The shell is a command-line interface that allows users to interact with the Linux OS by executing commands, running scripts, and managing the system. It acts as a bridge between the user and the kernel, interpreting commands and passing them to the kernel for execution. For example, typing `ls -l` in Bash lists files in long format. The shell also supports scripting for automation. It’s both a user tool and a programming environment.

5. **What are some commonly used Linux shells (e.g., Bash, Zsh, Fish)?**  
**Answer:**  
Common Linux shells include:  
- **Bash (Bourne Again Shell):** Default in most distros, widely used, supports scripting (e.g., `#!/bin/bash`).  
- **Zsh (Z Shell):** Extends Bash with advanced features like better autocompletion and themes (e.g., Oh My Zsh).  
- **Fish (Friendly Interactive Shell):** User-friendly, with autosuggestions and no complex scripting focus.  
- **Sh (Bourne Shell):** Older, lightweight shell; Bash is its successor.  
- **Tcsh:** Enhanced C shell with C-like syntax.  
Bash is the most popular due to its compatibility and ubiquity.

6. **What is the difference between a process and a thread in Linux?**  
**Answer:**  
A process is an independent program in execution, with its own memory space, PID (process ID), and resources (e.g., running Firefox). A thread is a smaller unit within a process, sharing the same memory and resources but executing independently (e.g., a tab in Firefox).  
- **Process:** Isolated, heavier, created via `fork()`.  
- **Thread:** Lightweight, shares memory, created via `pthread_create()`.  
Linux treats threads as "lightweight processes" under the hood, managed by the kernel.

7. **How does Linux handle file permissions? Explain the chmod command with examples.**  
**Answer:**  
Linux uses a permission system with three categories—owner, group, and others—and three types: read (r=4), write (w=2), execute (x=1). Permissions are displayed as `rwxr-xr-x` (owner: rwx, group: rx, others: rx).  
The `chmod` command changes permissions:  
- **Numeric mode:** `chmod 755 file.sh` (owner: rwx=7, group: rx=5, others: rx=5).  
- **Symbolic mode:** `chmod u+x file.sh` (adds execute for owner).  
Example: `chmod 644 file.txt` sets owner to rw (6), group/others to r (4), making it readable by all but writable only by the owner.

8. **What are the basic Linux file types (regular files, directories, symbolic links, etc.)?**  
**Answer:**  
Linux supports several file types:  
- **Regular Files (-):** Text, binaries, or data (e.g., `script.sh`).  
- **Directories (d):** Containers for other files (e.g., `/home`).  
- **Symbolic Links (l):** Pointers to other files (e.g., `ln -s target link`).  
- **Hard Links:** Multiple names for the same inode (e.g., `ln target link`).  
- **Block Devices (b):** Storage like disks (e.g., `/dev/sda`).  
- **Character Devices (c):** Stream devices like terminals (e.g., `/dev/tty`).  
- **Named Pipes (p):** For inter-process communication (e.g., `mkfifo pipe`).  
Use `ls -l` to identify them (first character in output).

9. **What is the purpose of the root user in Linux?**  
**Answer:**  
The root user is the superuser in Linux, with unrestricted access to all system resources, files, and commands. It has a UID (user ID) of 0 and is used for administrative tasks like installing software, modifying system files (e.g., `/etc`), or managing users. Normal users have limited privileges for security. You can access root via `su` (switch user) or `sudo` (execute as root), e.g., `sudo apt update`. Root is critical but must be used cautiously to avoid accidental damage.

10. **Explain the Linux boot process in detail (e.g., BIOS, GRUB, init/systemd).**  
**Answer:**  
The Linux boot process involves:  
- **BIOS/UEFI:** Powers on, runs POST (Power-On Self-Test), and loads the bootloader from the boot sector.  
- **Bootloader (e.g., GRUB):** Displays a menu, loads the kernel and initramfs (initial RAM filesystem) into memory, then hands control to the kernel.  
- **Kernel:** Initializes hardware, mounts the root filesystem (from initramfs), and starts the first process.  
- **Init/Systemd:** The first user-space process (PID 1). In modern systems, systemd replaces init, starting services (e.g., networking, SSH) based on targets.  
- **Login:** System reaches a runlevel/target, offering a login prompt (CLI or GUI).  
Example: GRUB loads `/boot/vmlinuz`, kernel mounts `/`, and systemd starts `getty` for login.

11. **What is the Linux file system hierarchy (e.g., /etc, /var, /home)?**  
**Answer:**  
The Linux file system hierarchy is a standardized structure organizing files and directories, rooted at `/`. Key directories include:  
- **/etc:** Configuration files (e.g., `/etc/passwd` for users).  
- **/var:** Variable data like logs (e.g., `/var/log`) and temporary files.  
- **/home:** User home directories (e.g., `/home/user`).  
- **/bin:** Essential binaries (e.g., `ls`, `cp`).  
- **/usr:** User programs and data (e.g., `/usr/bin`).  
- **/root:** Root user’s home directory.  
- **/tmp:** Temporary files, cleared on reboot.  
- **/proc:** Virtual filesystem for process and kernel info.  
It follows the Filesystem Hierarchy Standard (FHS) for consistency across distributions.

12. **How do you check disk space usage in Linux? (e.g., df, du)**  
**Answer:**  
- **df:** Displays disk space usage for mounted filesystems.  
    Example: `df -h` shows sizes in human-readable format (e.g., GB).  
    Output: Total, used, and available space per filesystem.  
- **du:** Measures space used by specific directories or files.  
    Example: `du -sh /home` shows the total size of `/home` in human-readable format.  
Difference: `df` gives an overview of mounted partitions; `du` drills down into specific directories.

13. **What does the lsblk command do?**  
**Answer:**  
The `lsblk` command lists block devices (e.g., disks, partitions) in a tree-like format. It shows device names (e.g., `sda`), mount points (e.g., `/`), and sizes.  
Example: `lsblk` might output `sda` with `sda1` mounted at `/` and `sda2` as swap.  
Use case: Identify available drives and their structure, especially during partitioning or troubleshooting.

14. **Explain the difference between soft links and hard links in Linux.**  
**Answer:**  
- **Soft Link (Symbolic Link):** A pointer to another file’s path, created with `ln -s target link`.  
    - Breaks if the target is deleted.  
    - Can link across filesystems.  
    - Example: `ln -s /file.txt link.txt`.  
- **Hard Link:** A direct reference to the same inode (data) as the target, created with `ln target link`.  
    - Survives target deletion (same data, different name).  
    - Limited to the same filesystem.  
    - Example: `ln file.txt link.txt`.  
Key difference: Soft links are shortcuts; hard links are additional names for the same file.

15. **How do you find a file in Linux using the find or locate command?**  
**Answer:**  
- **find:** Searches the filesystem in real-time based on criteria.  
    Example: `find /home -name "report.txt"` finds `report.txt` in `/home`.  
    Flexible: `find / -size +10M` (files >10MB).  
- **locate:** Uses a pre-built database (updated via `updatedb`) for faster searches.  
    Example: `locate report.txt` lists all matches.  
    Faster but may miss recent files.  
Use `find` for precision, `locate` for speed.

16. **What is the purpose of the /proc directory?**  
**Answer:**  
The `/proc` directory is a virtual filesystem providing runtime information about processes and the kernel. It’s not stored on disk but generated by the kernel.  
Examples:  
- `/proc/cpuinfo`: CPU details.  
- `/proc/meminfo`: Memory usage.  
- `/proc/[PID]`: Info about a process (e.g., `/proc/1234`).  
Purpose: Monitoring and debugging system state dynamically.

17. **How can you compress and decompress files in Linux? (e.g., tar, gzip, zip)**  
**Answer:**  
- **Compress:**  
    - `tar`: Archives files. `tar -cvf archive.tar dir/` (create archive).  
    - `gzip`: Compresses a file. `gzip file.txt` → `file.txt.gz`.  
    - `tar` + `gzip`: `tar -czvf archive.tar.gz dir/` (archive and compress).  
    - `zip`: `zip archive.zip file.txt` (compresses into .zip).  
- **Decompress:**  
    - `tar`: `tar -xvf archive.tar` (extract).  
    - `gunzip`: `gunzip file.txt.gz` → `file.txt`.  
    - `tar` + `gzip`: `tar -xzvf archive.tar.gz` (extract and decompress).  
    - `unzip`: `unzip archive.zip`.  
`tar` bundles files; `gzip`/`zip` reduce size.

18. **What is the difference between cat, more, and less commands?**  
**Answer:**  
- **cat:** Concatenates and displays file contents entirely.  
    Example: `cat file.txt` (dumps all text).  
    Best for short files or combining files.  
- **more:** Displays file contents page-by-page, scrolling down only.  
    Example: `more file.txt` (press space to continue).  
- **less:** Like `more`, but allows backward scrolling and searching.  
    Example: `less file.txt` (use arrows, `/search`).  
Key difference: `cat` is simple output; `more`/`less` handle large files interactively, with `less` being more versatile.

19. **How do you redirect output to a file in Linux? Explain >, >>, and 2> with examples.**  
**Answer:**  
Redirection sends command output to a file or device:  
- `>`: Overwrites a file with output.  
    Example: `echo "Hello" > file.txt` (creates/overwrites `file.txt`).  
- `>>`: Appends output to a file.  
    Example: `echo "World" >> file.txt` (adds to `file.txt`).  
- `2>`: Redirects standard error (stderr) to a file.  
    Example: `ls nonexistent 2> error.log` (writes error to `error.log`).  
Bonus: `> file 2>&1` combines stdout and stderr to one file.

20. **What is piping in Linux, and how does it work? (e.g., ls | grep)**  
**Answer:**  
Piping (`|`) connects the output of one command to the input of another, enabling chained processing.  
How it works: The left command’s stdout is fed into the right command’s stdin.  
Example: `ls -l | grep "txt"` lists files and filters for lines containing "txt".  
Another example: `cat file.txt | sort | uniq` sorts and removes duplicates.  
Piping enhances flexibility by combining simple tools into powerful workflows.

21. **How do you list all running processes in Linux?**  
**Answer:**  
- `ps`: Lists processes with details.  
    - Example: `ps aux` shows all processes (user, PID, CPU, memory).
- `top`: Interactive, real-time process viewer.  
    - Example: `top` displays running processes, sortable by CPU or memory.
- `htop`: Enhanced top with a user-friendly interface.  
    - Example: `htop` adds colors, scrolling, and process tree view.
- Use `ps` for snapshots, `top`/`htop` for live monitoring.

22. **What is the difference between kill, pkill, and killall commands?**  
**Answer:**  
- `kill`: Terminates a process by PID.  
    - Example: `kill -9 1234` (force-kills PID 1234).
- `pkill`: Kills processes by name or pattern.  
    - Example: `pkill -9 firefox` (terminates all Firefox processes).
- `killall`: Kills all processes matching an exact name.  
    - Example: `killall firefox` (stops all Firefox instances).
- Key difference: `kill` uses PID, `pkill` matches patterns, `killall` targets exact names.

23. **How do you run a process in the background? What does the & operator do?**  
**Answer:**  
- To run a process in the background, append `&` to the command.  
- The `&` operator tells the shell to execute the command without blocking the terminal.  
    - Example: `sleep 100 &` runs sleep in the background, returning the PID (e.g., `[1] 1234`).
- Use `jobs` to list background tasks and `fg` to bring one to the foreground (e.g., `fg %1`).
- It’s useful for long-running tasks while keeping the terminal free.

24. **What is a zombie process, and how do you identify and remove it?**  
**Answer:**  
- A zombie process is a terminated process whose parent hasn’t retrieved its exit status, leaving it in the process table (state Z).  
- Identify: Use `ps aux | grep Z` (look for Z in STAT column) or `top` (zombie count).  
- Remove: Kill the parent process (e.g., `kill -9 <parent_PID>`), as the kernel clears zombies when the parent exits. Alternatively, reboot if persistent.
- Zombies use minimal resources but can clog the process table if many accumulate.

25. **How do you check CPU and memory usage in Linux?**  
**Answer:**  
- `top`: Shows real-time CPU (e.g., %user, %system) and memory usage (e.g., used, free).  
    - Example: `top` (interactive).
- `free`: Displays memory stats (total, used, free, buffers/cache).  
    - Example: `free -h` (human-readable, e.g., GB).
- `vmstat`: Reports virtual memory, CPU, and I/O stats.  
    - Example: `vmstat 1` (updates every second).
- Use `top` for live monitoring, `free` for memory snapshots, `vmstat` for detailed stats.

26. **What is the purpose of the nice and renice commands?**  
**Answer:**  
- `nice`: Sets the priority of a new process (range: -20 high to 19 low, default 0).  
    - Example: `nice -n 10 sleep 100` (runs with lower priority).
- `renice`: Adjusts the priority of a running process.  
    - Example: `renice 15 -p 1234` (lowers priority of PID 1234).
- Purpose: Manage CPU allocation; higher priority (-ve) gets more CPU, lower (+ve) gets less. Root can set negative values.

27. **Explain the difference between fork() and exec() system calls.**  
**Answer:**  
- `fork()`: Creates a new process by duplicating the calling process (child inherits memory, file descriptors).  
    - Returns PID to parent, 0 to child.  
    - Example: Child and parent run the same code initially.
- `exec()`: Replaces the current process image with a new one (loads a new program).  
    - Example: `execvp("ls", args)` runs `ls` in the current process.
- Key difference: `fork()` clones, `exec()` replaces. Often combined: `fork()` to create, `exec()` to run a new program.

28. **What is a daemon process in Linux, and how is it created?**  
**Answer:**  
- A daemon is a background process that runs continuously, often providing services (e.g., sshd, httpd).  
- Characteristics: No controlling terminal, runs as root or system user.  
- Creation:  
    - Fork a child process (`fork()`).  
    - Parent exits, orphaning the child.  
    - Child calls `setsid()` to detach from terminal, creating a new session.  
    - Redirect stdin/stdout/stderr (e.g., to /dev/null).  
    - Run the service loop.
- Example: Web servers like Apache are daemons.

29. **How do you schedule tasks in Linux?**  
**Answer:**  
- `cron`: Schedules recurring tasks via crontab.  
    - Example: `crontab -e`, add `0 2 * * * backup.sh` (runs daily at 2 AM).  
    - Format: minute, hour, day, month, weekday.
- `at`: Schedules a one-time task.  
    - Example: `at 10pm -f script.sh` (runs script.sh at 10 PM).  
    - Use `atq` to list, `atrm` to remove.
- `cron` is for repetition, `at` for single execution.

30. **What is the systemctl command used for in systemd-based systems?**  
**Answer:**  
- The `systemctl` command manages services, units, and system state in systemd-based systems (modern init system).  
- Examples:  
    - `systemctl start sshd` (starts SSH service).  
    - `systemctl stop nginx` (stops Nginx).  
    - `systemctl enable mariadb` (auto-starts on boot).  
    - `systemctl status cron` (checks service status).
- Purpose: Controls daemons, manages boot targets, and monitors system health, replacing older `service` and `chkconfig`.

31. **How do you check the network configuration in Linux?**  
**Answer:**  
- `ifconfig`: Displays network interface details (IP, MAC, status).  
    - Example: `ifconfig` (shows eth0 with IP like 192.168.1.10).  
    - Older tool, may need net-tools package.
- `ip`: Modern replacement for `ifconfig`, more powerful.  
    - Example: `ip addr show` (lists IPs, e.g., inet 192.168.1.10/24).  
    - Additional uses: `ip link` (interface status), `ip route` (routing table).
- Use `ip` for current systems; `ifconfig` is still common but deprecated.

32. **What is the purpose of the netstat and ss commands?**  
**Answer:**  
- `netstat`: Displays network statistics, connections, and routing tables.  
    - Example: `netstat -tuln` (lists listening TCP/UDP ports).  
    - Use case: Check open ports or active connections.
- `ss`: Faster, modern alternative to `netstat`.  
    - Example: `ss -tuln` (shows listening sockets).  
    - Advantage: More detailed output, e.g., `ss -t -a` (all TCP connections).
- Purpose: Both troubleshoot network issues; `ss` is preferred in newer systems for efficiency.

33. **How do you set up a static IP address in Linux?**  
**Answer:**  
- To set a static IP:  
    - Edit the network configuration file (varies by distro):  
        - Ubuntu: `/etc/netplan/01-netcfg.yaml`.  
            - Example: Set `addresses: [192.168.1.100/24]`, `gateway4: 192.168.1.1`, `nameservers: [8.8.8.8]`.  
            - Apply: `sudo netplan apply`.
        - CentOS/RHEL: `/etc/sysconfig/network-scripts/ifcfg-eth0`.  
            - Set `BOOTPROTO=none`, `IPADDR=192.168.1.100`, `NETMASK=255.255.255.0`, `GATEWAY=192.168.1.1`.  
            - Restart: `sudo systemctl restart network`.
    - Verify: `ip addr show`.
- This disables DHCP and assigns a fixed IP.

34. **Explain the iptables command and how it is used to configure firewall rules.**  
**Answer:**  
- `iptables` is a tool to configure and manage firewall rules in the Linux kernel’s netfilter framework. It filters network traffic based on rules.  
- Structure: Rules are organized in chains (e.g., INPUT, OUTPUT, FORWARD).  
- Examples:  
    - `iptables -A INPUT -p tcp --dport 22 -j ACCEPT` (allow SSH).  
    - `iptables -A INPUT -j DROP` (drop all other incoming traffic).  
    - `iptables -L -v` (list rules with details).
- Use case: Block/allow IPs, ports, or protocols. Replaced by `nftables` in newer systems but still widely used.

35. **What is SSH, and how do you use it to connect to a remote server?**  
**Answer:**  
- SSH (Secure Shell) is a protocol for securely accessing and managing remote systems over a network.  
- Usage: `ssh user@hostname` (e.g., `ssh john@192.168.1.10`).  
    - With port: `ssh -p 2222 user@hostname`.  
    - Run command: `ssh user@hostname "ls -l"`.
- Requires an SSH server (e.g., sshd) running on the remote machine.
- It encrypts communication, ensuring secure logins and data transfer.

36. **How do you generate and configure SSH keys for secure authentication?**  
**Answer:**  
- SSH keys provide passwordless, secure authentication:  
    - Generate: `ssh-keygen -t rsa -b 4096` (creates `~/.ssh/id_rsa` private key and `id_rsa.pub` public key).  
        - Press Enter for defaults or set a passphrase.
    - Copy Public Key: `ssh-copy-id user@remote_host` (adds `id_rsa.pub` to remote `~/.ssh/authorized_keys`).  
    - Connect: `ssh user@remote_host` (logs in without password).  
    - Configure: Ensure `~/.ssh` permissions are 700 and `authorized_keys` is 600.
- Keys are more secure than passwords and convenient for automation.

37. **What is the difference between sudo and su commands?**  
**Answer:**  
- `sudo`: Runs a single command as root (or another user) with privileges, requiring user-specific authorization.  
    - Example: `sudo apt update` (prompts for user password).  
    - Configured in `/etc/sudoers`.
- `su`: Switches to another user (default root) for an entire session.  
    - Example: `su -` (prompts for root password, starts a new shell).
- Key difference: `sudo` is per-command and audited; `su` gives full access until exit.

38. **How do you harden a Linux system for security?**  
**Answer:**  
- To harden a Linux system:  
    - Update Packages: `sudo apt update && apt upgrade` (or `yum update`).  
    - Disable Root Login: Edit `/etc/ssh/sshd_config`, set `PermitRootLogin no`, restart sshd.  
    - Use SSH Keys: Replace passwords with keys (see Q36).  
    - Firewall: Configure `iptables` or `ufw` (e.g., `ufw allow 22`, `ufw enable`).  
    - Limit Users: Remove unused accounts, enforce strong passwords.  
    - SELinux/AppArmor: Enable mandatory access controls.  
    - Monitor Logs: Check `/var/log` for suspicious activity.
- Goal: Reduce attack surface and enforce least privilege.

39. **What is SELinux, and how does it enhance security?**  
**Answer:**  
- SELinux (Security-Enhanced Linux) is a kernel module enforcing mandatory access control (MAC) policies, beyond standard permissions.  
- How it works: Assigns security contexts (labels) to processes, files, and ports, restricting actions based on policy (e.g., Targeted mode).  
- Enhancement: Prevents unauthorized access even if a process is compromised (e.g., Apache can’t access `/etc/passwd`).  
    - Example: `ls -Z` shows contexts; `setenforce 0` toggles enforcement.
- It adds a layer of fine-grained control over discretionary access (DAC).

40. **How do you monitor system logs in Linux?**  
**Answer:**  
- `/var/log`: Contains log files.  
    - Examples: `/var/log/syslog` or `/var/log/messages` (system events), `/var/log/auth.log` (authentication).  
    - Use: `cat`, `tail -f /var/log/syslog` (live monitoring).
- `journalctl`: Queries systemd journal logs.  
    - Examples: `journalctl -u sshd` (SSH logs), `journalctl -b` (since last boot).  
    - Advantage: Centralized, persistent logs.
- Combine both for comprehensive monitoring; `journalctl` is modern, `/var/log` is traditional.

41. **What is the difference between init and systemd?**  
**Answer:**  
- **init:** Traditional Unix init system (e.g., SysVinit), starts services sequentially using scripts in `/etc/rcX.d` based on runlevels.  
    - Example: `/etc/init.d/apache2 start`.  
    - Simple but slow and lacks dependency management.
- **systemd:** Modern init system, starts services in parallel, manages dependencies, and uses unit files (e.g., `.service`).  
    - Example: `systemctl start apache2`.  
    - Features: Logging (`journalctl`), cgroups, faster boot.
- **Key difference:** systemd is faster, more feature-rich; init is older, simpler.

42. **How do you compile and install software from source code in Linux?**  
**Answer:**  
To compile and install from source:  
- **Download:** Get the source tarball (e.g., `app.tar.gz`).  
- **Extract:** `tar -xzvf app.tar.gz`.  
- **Configure:** `cd app; ./configure` (checks dependencies, generates Makefile).  
- **Compile:** `make` (builds the software).  
- **Install:** `sudo make install` (copies files to system, e.g., `/usr/local/bin`).  
- **Optional:** `sudo make uninstall` (if supported).  
- **Example:** Compiling nginx from source follows these steps. Ensure build tools (gcc, make) are installed.

43. **What is a kernel module, and how do you load/unload it? (e.g., modprobe, lsmod)**  
**Answer:**  
A kernel module is a loadable piece of code that extends kernel functionality (e.g., drivers) without rebooting.  
- **List:** `lsmod` (shows loaded modules, e.g., `usbcore`).  
- **Load:** `modprobe module_name` (e.g., `modprobe vfat` for FAT filesystem support).  
- **Unload:** `modprobe -r module_name` (e.g., `modprobe -r vfat`).  
- **Manual:** `insmod /path/to/module.ko` (load), `rmmod module_name` (unload).  
- **Note:** `modprobe` is preferred as it handles dependencies automatically.

44. **How does Linux handle memory management (e.g., virtual memory, swapping)?**  
**Answer:**  
Linux manages memory using:  
- **Virtual Memory:** Each process gets a virtual address space, mapped to physical memory via the MMU (Memory Management Unit).  
    - Benefits: Isolation, efficient allocation.
- **Paging:** Memory is divided into pages (e.g., 4KB), swapped between RAM and disk.  
- **Swapping:** When RAM is full, inactive pages move to a swap space (e.g., `/dev/sda2`).  
    - Check: `free -h` (swap usage), `swapon -s` (swap details).
- **Note:** The kernel balances performance and resource use via the page cache and swap.

45. **What is the purpose of the udev system in Linux?**  
**Answer:**  
udev (userspace device manager) dynamically manages device nodes in `/dev` when hardware is added or removed.  
- **Purpose:** Creates device files (e.g., `/dev/sdb` for a USB), applies rules, and triggers actions.  
- **How it works:** Listens to kernel events, uses rules in `/etc/udev/rules.d`.  
- **Example:** Plugging a USB drive triggers udev to create `/dev/sdb1`.  
- **Note:** It ensures plug-and-play functionality and custom device handling.

46. **Explain the concept of cgroups and how they are used in resource management.**  
**Answer:**  
cgroups (control groups) is a kernel feature to limit, isolate, and monitor resource usage (CPU, memory, disk I/O) for groups of processes.  
- **How it works:** Processes are assigned to a cgroup with defined limits (e.g., 1GB RAM).  
- **Use case:** Containers (e.g., Docker) use cgroups for resource isolation.  
- **Example:** `cgcreate -g cpu:/mygroup`, `cgexec -g cpu:/mygroup myapp`.  
- **Tools:** Managed via systemd or cgconfig.  
- **Note:** It enables efficient resource allocation and prioritization.

47. **What are environment variables in Linux, and how do you set them? (e.g., export)**  
**Answer:**  
Environment variables are key-value pairs that configure the shell or programs (e.g., PATH, HOME).  
- **View:** `printenv` or `env`.  
- **Set Temporarily:** `export VAR=value` (e.g., `export PATH=$PATH:/usr/local/bin`).  
- **Set Permanently:** Add to `~/.bashrc` or `/etc/profile` (e.g., `echo 'export MYVAR=test' >> ~/.bashrc`).  
- **Example:** `export JAVA_HOME=/usr/java` for Java apps.  
- **Note:** They customize runtime behavior across sessions.

48. **How do you troubleshoot a Linux system that fails to boot?**  
**Answer:**  
To troubleshoot a boot failure:  
- **Check GRUB:** Press `e` at GRUB menu to edit kernel parameters (e.g., add `single` for single-user mode).  
- **Boot Messages:** Watch for errors or use `dmesg` post-boot.  
- **Rescue Mode:** Boot from a live CD/USB, mount root (`mount /dev/sda1 /mnt`), and fix issues (e.g., `fsck /dev/sda1`).  
- **Logs:** Check `/var/log/boot.log` or `journalctl -b -1` (previous boot).  
- **Initramfs:** Rebuild if corrupt (`mkinitcpio -P` on Arch).  
- **Note:** Identify whether it’s kernel, filesystem, or service-related, then repair.

49. **What is the difference between a monolithic kernel and a microkernel?**  
**Answer:**  
- **Monolithic Kernel:** All core services (file systems, drivers, networking) run in kernel space.  
    - Example: Linux.  
    - Pros: Faster, simpler. Cons: Larger, less modular.
- **Microkernel:** Minimal kernel (e.g., memory, scheduling), with services in user space.  
    - Example: QNX, Minix.  
    - Pros: Modular, stable. Cons: Slower due to IPC (inter-process communication).
- **Note:** Linux uses a hybrid approach, adding modularity (modules) to a monolithic base.

50. **How do you configure a Linux system as a server (e.g., web server with Apache)?**  
**Answer:**  
To set up a web server with Apache:  
- **Install:** `sudo apt install apache2` (Ubuntu) or `sudo yum install httpd` (CentOS).  
- **Start:** `sudo systemctl start apache2` (or httpd).  
- **Enable:** `sudo systemctl enable apache2` (auto-start on boot).  
- **Configure:** Edit `/etc/apache2/sites-available/000-default.conf` (e.g., set `DocumentRoot /var/www/html`).  
- **Test:** Place an `index.html` in `/var/www/html`, access via browser (e.g., `http://server_ip`).  
- **Firewall:** Allow port 80 (`sudo ufw allow 80`).  
- **Note:** Adjust for HTTPS (port 443) with SSL if needed.