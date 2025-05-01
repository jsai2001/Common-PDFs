### Condensed Computer Networking Interview Questions

#### Basics
1. **What is a computer network, and why is it important?**  
   Network: Devices connected to share data/resources. Important for communication (email), resource sharing (files), scalability (cloud), and modern services (IoT).

2. **LAN vs. WAN vs. MAN?**  
   LAN: Small area (office), fast. WAN: Large area (internet), slower. MAN: City-sized, medium speed. Differs in scope/scale.

3. **OSI model and its seven layers?**  
   Physical (cables), Data Link (MAC), Network (IP), Transport (TCP/UDP), Session (connections), Presentation (encryption), Application (HTTP). Standardizes networking.

4. **TCP/IP model vs. OSI model?**  
   TCP/IP: 4 layers (Link, Internet, Transport, Application). Practical, internet-focused. OSI: 7 layers, theoretical, splits Session/Presentation.

5. **IP address? IPv4 vs. IPv6?**  
   IP: Device identifier. IPv4: 32-bit (192.168.1.1), limited. IPv6: 128-bit (2001:0db8::1), vast, no NAT needed.

6. **Purpose of subnet mask?**  
   Splits IP into network/host parts for routing/allocation. E.g., 255.255.255.0 (/24) gives 254 hosts.

7. **Public vs. private IP?**  
   Public: Internet-routable (e.g., 8.8.8.8). Private: LAN-only (e.g., 192.168.x.x), reused, NAT-required.

8. **MAC vs. IP address?**  
   MAC: 48-bit hardware ID (Layer 2). IP: Logical address (Layer 3). MAC local, fixed; IP routable, assignable.

9. **What is DNS and how does it work?**  
   DNS: Maps domains (google.com) to IPs (142.250.190.14). Queries: client → resolver → root → TLD → authoritative servers.

10. **Hub vs. switch vs. router?**  
    Hub: Broadcasts (Layer 1). Switch: Forwards to MAC (Layer 2). Router: Routes by IP (Layer 3).

#### Protocols
11. **What is TCP? Ensures reliability?**  
    TCP: Connection-oriented, reliable. Uses handshake, sequence numbers, ACKs, flow control, checksums (e.g., HTTP).

12. **UDP? Preferred when?**  
    UDP: Connectionless, fast, no guarantees. Used for streaming, gaming, DNS where speed > reliability.

13. **TCP three-way handshake?**  
    SYN (client), SYN-ACK (server), ACK (client). Syncs sequence numbers for connection.

14. **HTTP vs. HTTPS?**  
    HTTP: Plain text (port 80). HTTPS: Encrypted via TLS (port 443). HTTPS secures data.

15. **DHCP and IP assignment?**  
    DHCP: Auto-assigns IPs. Process: Discover → Offer → Request → Acknowledge. Simplifies config.

16. **ARP and IP-to-MAC resolution?**  
    ARP: Maps IP to MAC in LAN via broadcast request/reply. Caches results (arp -a).

17. **ICMP and its role?**  
    ICMP: Diagnostics/errors (e.g., ping). Sends Echo Requests/Replies for connectivity tests.

18. **FTP vs. SFTP?**  
    FTP: Unencrypted file transfer (ports 20/21). SFTP: Encrypted over SSH (port 22).

19. **SNMP in network management?**  
    SNMP: Monitors devices via agents/MIBs. Polls stats (e.g., snmpwalk) for performance/faults.

20. **BGP protocol purpose?**  
    BGP: Routes between ASes on internet, shares paths, ensures loop-free routing.

#### Network Devices and Configuration
21. **Firewall’s protection?**  
    Filters traffic by rules (IP/port). Blocks attacks, limits access (e.g., `iptables -A INPUT -p tcp --dport 22 -j ACCEPT`).

22. **NAT and its use?**  
    NAT: Maps private IPs to public IP. Conserves addresses, hides network, enables internet access.

23. **VLAN and performance?**  
    VLAN: Segments network logically. Reduces broadcasts, boosts security, optimizes bandwidth.

24. **Static vs. dynamic IP?**  
    Static: Fixed, manual (servers). Dynamic: DHCP-assigned, temporary (clients).

25. **How load balancer works?**  
    Distributes traffic across servers (e.g., round-robin). Ensures availability, scalability.

26. **Proxy server vs. VPN?**  
    Proxy: App-specific intermediary (e.g., HTTP). VPN: Encrypts all traffic, system-wide.

27. **Troubleshoot connectivity?**  
    Use `ping` (reachability), `traceroute` (path), `netstat -tuln` (ports). Check IP, DNS, gateway.

28. **QoS and implementation?**  
    QoS: Prioritizes traffic (e.g., VoIP). Uses queuing/shaping on routers (e.g., `tc` in Linux).

29. **Gateway’s purpose?**  
    Connects networks (LAN to WAN), routes traffic (e.g., `192.168.1.1`).

30. **Configure router via CLI?**  
    SSH, `configure terminal`, set IP (`ip address 192.168.1.1 255.255.255.0`), NAT, save (`write memory`).

#### Security
31. **DDoS attack and mitigation?**  
    DDoS: Floods target with traffic. Mitigate: Rate limit, use CDN, scale resources, block IPs.

32. **SSL/TLS and security?**  
    Encrypts data, authenticates servers, ensures integrity (e.g., HTTPS). TLS is modern standard.

33. **Symmetric vs. asymmetric encryption?**  
    Symmetric: One key, fast (AES). Asymmetric: Public/private keys, secure (RSA).

34. **VPN and privacy?**  
    VPN: Encrypts traffic, masks IP via tunnel (e.g., AES-256). Secures public Wi-Fi.

35. **Man-in-the-middle attack prevention?**  
    MitM: Intercepts data. Prevent: Use HTTPS/TLS, VPN, verify certificates, avoid public Wi-Fi.

36. **Port scanning detection?**  
    Probes open ports (e.g., `nmap`). Detect: Monitor traffic (`tcpdump`), use IDS (Snort).

37. **Packet sniffer purpose?**  
    Captures packets (e.g., Wireshark) for troubleshooting, debugging, or security analysis.

38. **Network segmentation benefits?**  
    Isolates segments (VLANs). Limits attack spread, restricts access, reduces congestion.

39. **IDS and how it works?**  
    IDS: Monitors for threats. Signature-based (patterns) or anomaly-based (behavior). Alerts (e.g., Snort).

40. **Secure wireless network?**  
    Use WPA3, disable WPS, strong password, hide SSID, MAC filter, update firmware.

#### Advanced Topics
41. **SDN vs. traditional networking?**  
    SDN: Software-controlled, centralized, flexible. Traditional: Device-based, distributed, static.

42. **Unicast vs. multicast vs. broadcast?**  
    Unicast: One-to-one (email). Multicast: One-to-group (streaming). Broadcast: One-to-all (ARP).

43. **Packet vs. circuit switching?**  
    Packet: Independent packets, flexible (internet). Circuit: Dedicated path, fixed (phone).

44. **Latency’s impact?**  
    Delay in data transfer. High latency slows apps (e.g., gaming, VoIP).

45. **Bandwidth vs. throughput?**  
    Bandwidth: Max capacity (e.g., 100 Mbps). Throughput: Actual rate (e.g., 80 Mbps).

46. **How traceroute works?**  
    Sends packets with increasing TTL, records hops’ IPs/times. Shows path, failures.

47. **TTL field purpose?**  
    Limits packet life. Router decrements; TTL=0 discards. Prevents loops.

48. **Network congestion management?**  
    Overloaded network. Manage: QoS, load balancing, more bandwidth, traffic shaping.

49. **Socket in networking?**  
    IP:port endpoint (e.g., 192.168.1.1:80). Used in code (e.g., `socket.connect()`).

50. **CDN performance boost?**  
    Caches content locally, reduces latency, offloads servers, adds redundancy (e.g., Netflix streaming).