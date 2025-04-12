### Condensed Linux Operating System Concepts Interview Questions

#### Basics
1. **What is Linux, and how does it differ from Windows?**  
   Linux is an open-source, Unix-like OS, customizable and free. Unlike proprietary Windows: Linux has open code, no license cost, hierarchical file system (ext4 vs. NTFS), and CLI focus (Bash vs. PowerShell).

2. **Linux kernel vs. distribution?**  
   Kernel: Core OS managing hardware/processes. Distribution: Kernel + tools/software (e.g., Ubuntu, Fedora). Distros vary in packages/managers.

3. **Key Linux components?**  
   Kernel, shell (e.g., Bash), file system (ext4), user tools (`ls`, `grep`), libraries (`glibc`), daemons (`sshd`), bootloader (GRUB).

4. **Role of the shell?**  
   Shell (e.g., Bash) interprets user commands, interacts with kernel, supports scripting for automation.

5. **Common Linux shells?**  
   Bash (default, script-friendly), Zsh (advanced completion), Fish (user-friendly), Sh (lightweight), Tcsh (C-like).

6. **Process vs. thread?**  
   Process: Independent program with own memory (e.g., Firefox). Thread: Lightweight unit within process, shares memory (e.g., browser tab).

7. **Linux file permissions? `chmod` examples?**  
   Permissions: owner/group/others, read (4)/write (2)/execute (1). `chmod 755 file` (rwx for owner, rx for others); `chmod u+x file` (add execute for owner).

8. **Basic Linux file types?**  
   Regular (-), directory (d), symbolic link (l), hard link, block device (b), character device (c), named pipe (p). Check with `ls -l`.

9. **Purpose of root user?**  
   Root (UID 0) has full system access for admin tasks (e.g., `sudo apt update`). Use cautiously to avoid damage.

10. **Linux boot process?**  
    BIOS/UEFI → GRUB loads kernel/initramfs → kernel initializes hardware, mounts root → systemd starts services → login prompt.

#### File System and Commands
11. **Linux file system hierarchy?**  
    Root (`/`): `/etc` (configs), `/var` (logs), `/home` (users), `/bin` (binaries), `/usr` (programs), `/tmp` (temp), `/proc` (kernel info).

12. **Check disk space?**  
    `df -h` (filesystem usage, e.g., GB), `du -sh /dir` (directory size).

13. **What does `lsblk` do?**  
    Lists block devices (disks/partitions) with names, mount points, sizes (e.g., `sda1` at `/`).

14. **Soft vs. hard links?**  
    Soft link (`ln -s`): Path pointer, breaks if target deleted, cross-filesystem. Hard link (`ln`): Same inode, persists if target deleted, same filesystem.

15. **Find files with `find`/`locate`?**  
    `find /home -name "file.txt"` (real-time search); `locate file.txt` (faster, uses database).

16. **Purpose of `/proc`?**  
    Virtual filesystem for kernel/process info (e.g., `/proc/cpuinfo`, `/proc/[PID]`).

17. **Compress/decompress files?**  
    Compress: `tar -czvf file.tar.gz dir`, `zip file.zip file`. Decompress: `tar -xzvf file.tar.gz`, `unzip file.zip`.

18. **Difference between `cat`, `more`, `less`?**  
    `cat`: Dumps full file. `more`: Page-by-page, down only. `less`: Scroll both ways, searchable.

19. **Redirect output? Explain `>`, `>>`, `2>`**  
    `>`: Overwrite (`echo hi > file`). `>>`: Append (`echo hi >> file`). `2>`: Error redirect (`ls bad 2> err.log`).

20. **What is piping?**  
    Pipes (`|`) send one command’s output to another’s input (e.g., `ls | grep txt` filters files).

#### Processes and Resource Management
21. **List running processes?**  
    `ps aux` (all processes), `top` (real-time), `htop` (user-friendly).

22. **Difference between `kill`, `pkill`, `killall`?**  
    `kill -9 PID` (by PID), `pkill -9 name` (by pattern), `killall name` (exact name).

23. **Run process in background? Role of `&`?**  
    Append `&` (e.g., `sleep 100 &`) to run in background, freeing terminal. Use `jobs`, `fg`.

24. **Zombie process? Identify/remove?**  
    Zombie: Terminated, unclaimed process. Find: `ps aux | grep Z`. Remove: Kill parent (`kill -9 parent_PID`).

25. **Check CPU/memory usage?**  
    `top` (live CPU/memory), `free -h` (memory snapshot), `vmstat 1` (detailed stats).

26. **Purpose of `nice`/`renice`?**  
    `nice -n 10 cmd` (set priority, -20 high to 19 low); `renice 15 -p PID` (adjust running process).

27. **Difference between `fork()` and `exec()`?**  
    `fork()`: Clones process. `exec()`: Replaces process with new program. Often used together.

28. **Daemon process? Creation?**  
    Daemon: Background service (e.g., `sshd`). Create: Fork, parent exits, `setsid()`, redirect I/O, run loop.

29. **Schedule tasks?**  
    `cron`: Recurring (`crontab -e`, e.g., `0 2 * * * script`). `at`: One-time (`at 10pm -f script`).

30. **What is `systemctl` for?**  
    Manages systemd services (`systemctl start sshd`, `enable`, `status`).

#### Networking and Security
31. **Check network config?**  
    `ip addr` (modern, shows IPs), `ifconfig` (older, IPs/MAC).

32. **Purpose of `netstat`/`ss`?**  
    `netstat -tuln` (ports/connections), `ss -tuln` (faster, modern alternative).

33. **Set static IP?**  
    Edit `/etc/netplan` (Ubuntu: `addresses: [192.168.1.100/24]`), `/etc/sysconfig/network-scripts` (CentOS), apply (`netplan apply`).

34. **What is `iptables`?**  
    Configures firewall rules (`iptables -A INPUT -p tcp --dport 22 -j ACCEPT` allows SSH).

35. **What is SSH? Usage?**  
    SSH: Secure remote access. `ssh user@host` (e.g., `ssh john@192.168.1.10`).

36. **Generate/configure SSH keys?**  
    `ssh-keygen -t rsa`, `ssh-copy-id user@host`, set permissions (700 for `~/.ssh`).

37. **Difference between `sudo`/`su`?**  
    `sudo`: Per-command root (`sudo cmd`). `su`: Full root session (`su -`).

38. **Harden Linux system?**  
    Update packages, disable root SSH, use keys, set firewall (`ufw`), enable SELinux, monitor logs.

39. **What is SELinux?**  
    Kernel module for mandatory access control, restricts processes via policies (e.g., Targeted mode).

40. **Monitor system logs?**  
    `/var/log` (`tail -f /var/log/syslog`), `journalctl -u sshd` (systemd logs).

#### Advanced Topics
41. **Difference between init and systemd?**  
    Init: Sequential, script-based. Systemd: Parallel, dependency-driven, with logging/cgroups.

42. **Compile/install from source?**  
    Extract (`tar -xzvf`), `./configure`, `make`, `sudo make install`.

43. **Kernel module? Load/unload?**  
    Extends kernel (e.g., drivers). `modprobe module` (load), `modprobe -r` (unload), `lsmod` (list).

44. **Linux memory management?**  
    Virtual memory maps addresses, paging swaps to disk (`free -h` shows swap).

45. **Purpose of udev?**  
    Manages `/dev` nodes dynamically for hardware (e.g., `/dev/sdb` for USB).

46. **What are cgroups?**  
    Limit/isolate resources (CPU/memory) for processes, used in containers (`cgcreate`).

47. **Environment variables? Set them?**  
    Key-value pairs (`PATH`). Set: `export VAR=value`, permanent in `~/.bashrc`.

48. **Troubleshoot boot failure?**  
    Check GRUB (`e` for single mode), logs (`journalctl -b`), use live CD to `fsck`.

49. **Monolithic vs. microkernel?**  
    Monolithic (Linux): All in kernel, fast. Microkernel (QNX): Minimal, modular, slower.

50. **Configure Linux as web server?**  
    Install Apache (`apt install apache2`), start (`systemctl start`), set `/var/www/html`, allow port 80 (`ufw`).