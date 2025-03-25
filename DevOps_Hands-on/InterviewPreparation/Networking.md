## Computer Networking Interview Questions

### Basics
- What is a computer network, and why is it important?
- What are the differences between LAN, WAN, and MAN?
- Explain the OSI model and its seven layers.
- What is the TCP/IP model, and how does it differ from the OSI model?
- What is an IP address, and what are the differences between IPv4 and IPv6?
- What is the purpose of a subnet mask?
- What is the difference between a public IP and a private IP?
- What is a MAC address, and how is it different from an IP address?
- What is DNS, and how does it work?
- What is the difference between a hub, switch, and router?

### Protocols
- What is TCP, and how does it ensure reliable communication?
- What is UDP, and when is it preferred over TCP?
- Explain the three-way handshake in TCP.
- What is the difference between HTTP and HTTPS?
- What is DHCP, and how does it assign IP addresses?
- What is ARP, and how does it resolve IP addresses to MAC addresses?
- What is ICMP, and what is its role in networking? (e.g., ping)
- What is FTP, and how does it differ from SFTP?
- What is SNMP, and how is it used in network management?
- Explain the purpose of the BGP protocol in routing.

### Network Devices and Configuration
- What is a firewall, and how does it protect a network?
- What is NAT (Network Address Translation), and why is it used?
- What is a VLAN, and how does it improve network performance?
- What is the difference between a static IP and a dynamic IP?
- How does a load balancer work in a network?
- What is a proxy server, and how does it differ from a VPN?
- How do you troubleshoot network connectivity issues? (e.g., ping, traceroute, netstat)
- What is QoS (Quality of Service), and how is it implemented?
- What is the purpose of a gateway in a network?
- How do you configure a router using command-line tools?

### Security
- What is a DDoS attack, and how can it be mitigated?
- What is SSL/TLS, and how does it secure network communication?
- What is the difference between symmetric and asymmetric encryption?
- What is a VPN, and how does it ensure privacy?
- What is a man-in-the-middle attack, and how can it be prevented?
- What is port scanning, and how can it be detected?
- What is the purpose of a packet sniffer? (e.g., Wireshark)
- Explain the concept of network segmentation and its security benefits.
- What is an intrusion detection system (IDS), and how does it work?
- How do you secure a wireless network? (e.g., WPA3, disabling WPS)

### Advanced Topics
- What is SDN (Software-Defined Networking), and how does it differ from traditional networking?
- What is the difference between unicast, multicast, and broadcast communication?
- Explain the concept of packet switching vs. circuit switching.
- What is latency, and how does it affect network performance?
- What is bandwidth, and how is it different from throughput?
- How does traceroute work, and what does it tell you about a network?
- What is the purpose of the TTL (Time to Live) field in an IP packet?
- What is network congestion, and how can it be managed?
- What is a socket in networking, and how is it used in programming?
- How does a CDN (Content Delivery Network) improve network performance?

1. **What is a computer network, and why is it important?**  
**Answer:**  
A computer network is a collection of interconnected devices (e.g., computers, servers, printers) that communicate to share resources, data, and services.  
**Importance:**  
- Enables resource sharing (e.g., files, internet).  
- Facilitates communication (e.g., email, video calls).  
- Supports distributed systems and scalability (e.g., cloud computing).  
- Critical for businesses, education, and daily life (e.g., IoT, online services).  
Without networks, modern computing and global connectivity would be impossible.

2. **What are the differences between LAN, WAN, and MAN?**  
**Answer:**  
- **LAN (Local Area Network):** Small geographic area (e.g., office). High speed (e.g., 1 Gbps), privately owned, low latency.  
- **WAN (Wide Area Network):** Large area (e.g., country, globe, like the internet). Slower speeds, uses leased lines or public infrastructure, higher latency.  
- **MAN (Metropolitan Area Network):** City-sized (e.g., campus or metro). Medium speed, connects multiple LANs, often used by ISPs or organizations.  
**Key difference:** Scope and scale (LAN < MAN < WAN).

3. **Explain the OSI model and its seven layers.**  
**Answer:**  
The OSI (Open Systems Interconnection) model is a conceptual framework for networking with seven layers:  
- **Physical:** Hardware transmission (e.g., cables, signals).  
- **Data Link:** Node-to-node data transfer, error detection (e.g., Ethernet, MAC).  
- **Network:** Routing and logical addressing (e.g., IP).  
- **Transport:** End-to-end communication, reliability (e.g., TCP, UDP).  
- **Session:** Manages sessions between applications (e.g., connection setup).  
- **Presentation:** Data translation, encryption (e.g., SSL).  
- **Application:** User interface and services (e.g., HTTP, FTP).  
It standardizes network design and troubleshooting.

4. **What is the TCP/IP model, and how does it differ from the OSI model?**  
**Answer:**  
The TCP/IP model is a practical, four-layer framework for internet protocols:  
- **Link:** Physical and data link (e.g., Ethernet).  
- **Internet:** Routing and addressing (e.g., IP).  
- **Transport:** Reliable data transfer (e.g., TCP, UDP).  
- **Application:** User services (e.g., HTTP, DNS).  
**Differences:**  
- OSI: 7 layers, theoretical; TCP/IP: 4 layers, practical.  
- OSI separates Session/Presentation; TCP/IP combines them into Application.  
- TCP/IP predates OSI and drives the internet.

5. **What is an IP address, and what are the differences between IPv4 and IPv6?**  
**Answer:**  
An IP address is a unique identifier for devices on a network.  
- **IPv4:** 32-bit, decimal format (e.g., 192.168.1.1). 4.3 billion addresses, nearly exhausted.  
- **IPv6:** 128-bit, hexadecimal (e.g., 2001:0db8::1). Trillions of addresses, designed for scalability.  
**Differences:**  
- **Size:** IPv4 (32-bit) vs. IPv6 (128-bit).  
- **Format:** IPv4 (dotted decimal) vs. IPv6 (colon-separated hex).  
- **Features:** IPv6 has no NAT, built-in security (IPsec).  
IPv6 addresses the IPv4 shortage.

6. **What is the purpose of a subnet mask?**  
**Answer:**  
A subnet mask defines which part of an IP address is the network portion versus the host portion, enabling efficient IP allocation and routing.  
**Example:** 255.255.255.0 (/24) means the first 24 bits are the network, leaving 8 bits for hosts (254 addresses).  
**Purpose:**  
- Divides networks into subnets.  
- Helps routers determine if traffic stays local or needs forwarding.  
Without it, devices can’t identify network boundaries.

7. **What is the difference between a public IP and a private IP?**  
**Answer:**  
- **Public IP:** Globally unique, routable on the internet. Assigned by ISPs (e.g., 8.8.8.8). Used for external access (e.g., web servers).  
- **Private IP:** Reserved, non-routable on the internet. Ranges: 10.0.0.0–10.255.255.255, 172.16.0.0–172.31.255.255, 192.168.0.0–192.168.255.255. Used in LANs, behind NAT.  
**Key difference:** Public IPs are internet-facing; private IPs are internal, reused across networks.

8. **What is a MAC address, and how is it different from an IP address?**  
**Answer:**  
A MAC (Media Access Control) address is a 48-bit, hardware-level identifier for network interfaces (e.g., 00:1A:2B:3C:4D:5E).  
**Differences:**  
- **Layer:** MAC (Layer 2, Data Link) vs. IP (Layer 3, Network).  
- **Scope:** MAC is local (LAN); IP is routable (network-wide).  
- **Assignment:** MAC is burned into hardware; IP is assigned dynamically or statically.  
- **Format:** MAC (hexadecimal) vs. IP (decimal/hex).  
MAC identifies devices physically; IP locates them logically.

9. **What is DNS, and how does it work?**  
**Answer:**  
DNS (Domain Name System) translates human-readable domain names (e.g., google.com) into IP addresses (e.g., 142.250.190.14).  
**How it works:**  
- User queries a domain (e.g., via browser).  
- Local resolver asks a recursive resolver.  
- Resolver queries root, TLD (e.g., .com), and authoritative servers.  
- Returns IP to the client.  
**Purpose:** Makes the internet user-friendly by avoiding manual IP entry.

10. **What is the difference between a hub, switch, and router?**  
**Answer:**  
- **Hub:** Basic device, broadcasts data to all ports (Layer 1). Example: Collision-prone, inefficient.  
- **Switch:** Intelligent, forwards data only to the intended MAC address (Layer 2). Example: Connects devices in a LAN, reduces collisions.  
- **Router:** Connects networks, routes data based on IP addresses (Layer 3). Example: Links LAN to WAN (e.g., home to ISP).  
**Key difference:** Hub broadcasts, switch filters, router routes.

11. **What is TCP, and how does it ensure reliable communication?**  
**Answer:**  
TCP (Transmission Control Protocol) is a connection-oriented protocol that ensures reliable data transfer between devices.  
**How it ensures reliability:**  
- **Three-way handshake:** Establishes a connection (SYN, SYN-ACK, ACK).  
- **Sequence numbers:** Tracks packet order.  
- **Acknowledgments:** Confirms receipt; retransmits lost packets.  
- **Flow control:** Uses windowing to prevent overload.  
- **Error checking:** Validates data with checksums.  
**Example:** Used in HTTP, email (SMTP), where data integrity matters.

12. **What is UDP, and when is it preferred over TCP?**  
**Answer:**  
UDP (User Datagram Protocol) is a connectionless, lightweight protocol that sends data without guaranteeing delivery.  
**Features:** No handshake, no retransmission, minimal overhead.  
**Preferred over TCP:**  
- When speed is critical (e.g., video streaming, gaming).  
- Where occasional data loss is acceptable (e.g., VoIP).  
- For small, simple transactions (e.g., DNS queries).  
**Example:** UDP sacrifices reliability for lower latency.

13. **Explain the three-way handshake in TCP.**  
**Answer:**  
The three-way handshake establishes a TCP connection:  
- **SYN:** Client sends a synchronization packet with a sequence number (e.g., X) to the server.  
- **SYN-ACK:** Server responds with its own sequence number (e.g., Y) and acknowledges the client’s (ACK = X+1).  
- **ACK:** Client acknowledges the server’s sequence (ACK = Y+1), completing the connection.  
**Purpose:** Ensures both sides are ready and synchronized before data transfer.

14. **What is the difference between HTTP and HTTPS?**  
**Answer:**  
- **HTTP (Hypertext Transfer Protocol):** Transfers web data in plain text (port 80). Insecure, vulnerable to eavesdropping.  
- **HTTPS (HTTP Secure):** HTTP over SSL/TLS (port 443). Encrypts data, authenticates servers with certificates.  
**Difference:**  
- **Security:** HTTPS adds encryption and integrity; HTTP does not.  
- **Use case:** HTTPS for sensitive data (e.g., banking); HTTP for non-critical sites.  
Modern web defaults to HTTPS.

15. **What is DHCP, and how does it assign IP addresses?**  
**Answer:**  
DHCP (Dynamic Host Configuration Protocol) automatically assigns IP addresses to devices on a network.  
**How it works:**  
- **Discover:** Client broadcasts a request for an IP.  
- **Offer:** DHCP server offers an available IP.  
- **Request:** Client requests the offered IP.  
- **Acknowledge:** Server assigns the IP with a lease time.  
**Purpose:** Simplifies network management by avoiding manual IP configuration.

16. **What is ARP, and how does it resolve IP addresses to MAC addresses?**  
**Answer:**  
ARP (Address Resolution Protocol) maps an IP address to a MAC address in a LAN.  
**How it works:**  
- Device broadcasts an ARP request (“Who has 192.168.1.10?”).  
- Target device replies with its MAC address.  
- Sender caches the result in its ARP table (arp -a).  
**Purpose:** Enables Layer 2 communication using Layer 3 addresses.

17. **What is ICMP, and what is its role in networking? (e.g., ping)**  
**Answer:**  
ICMP (Internet Control Message Protocol) is a network diagnostic and error-reporting protocol.  
**Role:**  
- Reports issues (e.g., “destination unreachable”).  
- Tests connectivity and latency (e.g., ping).  
**Example:** ping 8.8.8.8 sends ICMP Echo Requests; replies measure round-trip time.  
It’s not for data transfer but for network troubleshooting.

18. **What is FTP, and how does it differ from SFTP?**  
**Answer:**  
- **FTP (File Transfer Protocol):** Transfers files between systems (ports 20/21). Plain text, no encryption, insecure.  
- **SFTP (SSH File Transfer Protocol):** Secure file transfer over SSH (port 22). Encrypted, authenticated, part of SSH suite.  
**Differences:**  
- **Security:** SFTP encrypts data; FTP does not.  
- **Protocol:** FTP is standalone; SFTP uses SSH.  
Use SFTP for secure transfers.

19. **What is SNMP, and how is it used in network management?**  
**Answer:**  
SNMP (Simple Network Management Protocol) monitors and manages network devices (e.g., routers, switches).  
**How it works:**  
- Agents on devices report data (e.g., traffic, errors).  
- Managers query agents using MIBs (Management Information Bases).  
- Versions: SNMPv1, v2c, v3 (v3 adds security).  
**Use case:** Tracks performance, detects faults (e.g., snmpwalk to poll device stats).  
It’s essential for centralized network oversight.

20. **Explain the purpose of the BGP protocol in routing.**  
**Answer:**  
BGP (Border Gateway Protocol) is an exterior gateway protocol that routes traffic between autonomous systems (ASes) on the internet.  
**Purpose:**  
- Exchanges routing info (e.g., AS paths) between networks.  
- Ensures efficient, loop-free paths across the global internet.  
- Supports policy-based routing (e.g., prefers shorter paths).  
**Example:** ISPs use BGP to connect their networks.  
It’s the backbone of inter-domain routing.

21. **What is a firewall, and how does it protect a network?**  
**Answer:**  
A firewall is a security device or software that monitors and controls network traffic based on predefined rules.  
**How it protects:**  
- Filters packets by IP, port, or protocol (e.g., allow port 80, block others).  
- Blocks unauthorized access (e.g., denies external attacks).  
- Prevents data leaks by restricting outbound traffic.  
**Example:**  
- `iptables` rule to allow SSH: `iptables -A INPUT -p tcp --dport 22 -j ACCEPT`.  
- It acts as a barrier between trusted and untrusted networks.

22. **What is NAT (Network Address Translation), and why is it used?**  
**Answer:**  
NAT (Network Address Translation) modifies IP addresses in packet headers to map multiple private IPs to a single public IP.  
**Why it’s used:**  
- Conserves public IPv4 addresses (e.g., one public IP for a LAN).  
- Hides internal network structure for security.  
- Enables internet access for private IPs via a router.  
**Example:**  
- A router translates `192.168.1.10` to `203.0.113.1`.  
- It’s essential for IPv4 networks with limited address space.

23. **What is a VLAN, and how does it improve network performance?**  
**Answer:**  
A VLAN (Virtual Local Area Network) logically segments a physical network into separate broadcast domains without additional hardware.  
**How it improves performance:**  
- Reduces broadcast traffic (e.g., isolates departments).  
- Enhances security by restricting access between VLANs.  
- Optimizes bandwidth by grouping related devices.  
**Example:**  
- VLAN 10 for sales, VLAN 20 for engineering on one switch.  
- It simplifies management and boosts efficiency.

24. **What is the difference between a static IP and a dynamic IP?**  
**Answer:**  
- **Static IP:** Manually assigned, fixed address.  
    **Example:** `192.168.1.100`, set in config files.  
    **Use:** Servers, devices needing consistent access.  
- **Dynamic IP:** Automatically assigned by DHCP, changes over time.  
    **Example:** `192.168.1.101`, leased for hours/days.  
    **Use:** Clients, home devices.  
- **Difference:** Static is permanent; dynamic is temporary and managed.

25. **How does a load balancer work in a network?**  
**Answer:**  
A load balancer distributes incoming network traffic across multiple servers to ensure availability and performance.  
**How it works:**  
- Receives requests (e.g., HTTP).  
- Uses algorithms (e.g., round-robin, least connections) to pick a server.  
- Forwards traffic, monitors server health.  
**Example:**  
- Balances web traffic across three servers to prevent overload.  
- It improves scalability and reliability.

26. **What is a proxy server, and how does it differ from a VPN?**  
**Answer:**  
- **Proxy Server:** Intermediary for specific traffic (e.g., HTTP).  
    **Forwards requests, can cache data, hides client IP.**  
    **Example:** Web proxy for browsing.  
- **VPN (Virtual Private Network):** Encrypts all traffic over a tunnel.  
    **Secures entire connection, hides IP, spans networks.**  
    **Example:** Remote access to a corporate LAN.  
- **Difference:** Proxy is app-specific, unencrypted; VPN is system-wide, encrypted.

27. **How do you troubleshoot network connectivity issues? (e.g., ping, traceroute, netstat)**  
**Answer:**  
**Steps to troubleshoot:**  
- `ping`: Tests reachability (e.g., `ping 8.8.8.8` checks internet).  
- `traceroute`: Traces packet path to identify failures (e.g., `traceroute google.com`).  
- `netstat`: Checks open ports/connections (e.g., `netstat -tuln`).  
- Additional: `ip addr` (verify IP), `nslookup` (DNS issues).  
**Approach:** Start local (IP, gateway), then external (DNS, target), isolating the problem.

28. **What is QoS (Quality of Service), and how is it implemented?**  
**Answer:**  
QoS (Quality of Service) prioritizes network traffic to ensure performance for critical applications.  
**How it’s implemented:**  
- Classifies traffic (e.g., VoIP vs. file downloads).  
- Uses queuing, bandwidth allocation, or traffic shaping.  
- Tools: Router settings, `tc` in Linux, or protocols like DiffServ.  
**Example:**  
- Prioritize video calls over email traffic.  
- It reduces latency and jitter for key services.

29. **What is the purpose of a gateway in a network?**  
**Answer:**  
A gateway is a device (usually a router) that connects different networks, translating protocols or addressing schemes.  
**Purpose:**  
- Routes traffic between LAN and WAN (e.g., to the internet).  
- Acts as the default exit point (e.g., `192.168.1.1`).  
**Example:**  
- Home router linking devices to ISP.  
- It’s the network’s “door” to external systems.

30. **How do you configure a router using command-line tools?**  
**Answer:**  
Using CLI (e.g., Cisco IOS):  
- **Access:** `ssh admin@router-ip` or console cable.  
- **Enter config mode:** `enable`, then `configure terminal`.  
- **Set IP:** `interface eth0`, `ip address 192.168.1.1 255.255.255.0`.  
- **Default gateway:** `ip default-gateway 203.0.113.1`.  
- **NAT:** `ip nat inside` (LAN), `ip nat outside` (WAN).  
- **Save:** `write memory`.  
- **Verify:** `show running-config`.  
- Adjusts routing, interfaces, and services as needed.

31. **What is a DDoS attack, and how can it be mitigated?**  
**Answer:**  
A DDoS (Distributed Denial-of-Service) attack floods a target (e.g., server) with traffic from multiple sources to overwhelm it and disrupt service.  
**Mitigation:**  
- Use rate limiting or traffic filtering (e.g., via firewalls).  
- Deploy a CDN (e.g., Cloudflare) to absorb traffic.  
- Scale resources with load balancers or cloud services.  
- Block malicious IPs with IDS/IPS.  
- **Goal:** Maintain availability despite the attack.

32. **What is SSL/TLS, and how does it secure network communication?**  
**Answer:**  
SSL (Secure Sockets Layer) and TLS (Transport Layer Security) are protocols that encrypt data between a client and server.  
**How it secures:**  
- **Encryption:** Scrambles data (e.g., HTTPS traffic).  
- **Authentication:** Verifies server identity via certificates.  
- **Integrity:** Ensures data isn’t tampered (via hashing).  
**Example:**  
- Browser padlock for `https://`.  
- TLS (modern version) protects against eavesdropping and spoofing.

33. **What is the difference between symmetric and asymmetric encryption?**  
**Answer:**  
- **Symmetric Encryption:** Uses one key for both encryption and decryption.  
    **Example:** AES (fast, used for bulk data).  
    **Challenge:** Key must be securely shared.  
- **Asymmetric Encryption:** Uses a public key to encrypt and a private key to decrypt.  
    **Example:** RSA (secure key exchange, slower).  
    **Advantage:** No shared secret needed.  
- **Difference:** Symmetric is faster, single-key; asymmetric is secure, dual-key.

34. **What is a VPN, and how does it ensure privacy?**  
**Answer:**  
A VPN (Virtual Private Network) creates an encrypted tunnel between a device and a remote server.  
**How it ensures privacy:**  
- Encrypts traffic (e.g., AES-256) to prevent interception.  
- Masks IP address with the server’s IP.  
- Secures data over public networks (e.g., Wi-Fi).  
**Example:**  
- Accessing a corporate network remotely.  
- It hides user activity from ISPs and attackers.

35. **What is a man-in-the-middle attack, and how can it be prevented?**  
**Answer:**  
A man-in-the-middle (MitM) attack occurs when an attacker intercepts communication between two parties to eavesdrop or alter data.  
**Prevention:**  
- Use encryption (e.g., HTTPS, TLS) to scramble traffic.  
- Verify certificates (avoid “accept untrusted” prompts).  
- Employ VPNs or SSH for secure channels.  
- Avoid public Wi-Fi without protection.  
**Example:**  
- Fake Wi-Fi hotspot stealing login data.  
- Encryption thwarts interception.

36. **What is port scanning, and how can it be detected?**  
**Answer:**  
Port scanning probes a system to identify open ports and services (e.g., using `nmap`).  
**Purpose:** Attackers find vulnerabilities; admins audit security.  
**Detection:**  
- Monitor unusual traffic (e.g., with `tcpdump`).  
- Use IDS/IPS (e.g., Snort) to flag scans.  
- Check logs for repeated connection attempts.  
**Example:**  
- `nmap -sS 192.168.1.1` scans for open ports.  
- Early detection prevents exploitation.

37. **What is the purpose of a packet sniffer? (e.g., Wireshark)**  
**Answer:**  
A packet sniffer captures and analyzes network packets to inspect traffic.  
**Purpose:**  
- Troubleshooting (e.g., diagnose packet loss).  
- Security (e.g., detect malicious activity).  
- Debugging protocols (e.g., HTTP requests).  
**Example:**  
- Wireshark shows packet details (source, destination, payload).  
- It’s a diagnostic tool for admins, but attackers misuse it too.

38. **Explain the concept of network segmentation and its security benefits.**  
**Answer:**  
Network segmentation divides a network into smaller, isolated segments (e.g., VLANs, subnets).  
**Security benefits:**  
- Limits attack spread (e.g., malware stays in one segment).  
- Restricts access (e.g., HR can’t reach servers).  
- Reduces congestion and improves monitoring.  
**Example:**  
- Separate guest Wi-Fi from corporate LAN.  
- It enhances control and containment.

39. **What is an intrusion detection system (IDS), and how does it work?**  
**Answer:**  
An IDS (Intrusion Detection System) monitors network traffic for suspicious activity.  
**How it works:**  
- **Signature-based:** Matches known attack patterns (e.g., malware signatures).  
- **Anomaly-based:** Flags deviations from normal behavior.  
- Alerts admins (e.g., via logs, notifications).  
**Example:**  
- Snort detects port scans or SQL injections.  
- It identifies threats but doesn’t block (unlike IPS).

40. **How do you secure a wireless network? (e.g., WPA3, disabling WPS)**  
**Answer:**  
To secure a wireless network:  
- **Use WPA3:** Strongest encryption (replaces WPA2).  
- **Disable WPS:** Prevents easy PIN-based attacks.  
- **Strong Password:** Long, complex passphrase.  
- **Hide SSID:** Avoid broadcasting network name.  
- **MAC Filtering:** Allow only trusted devices.  
- **Update Firmware:** Patch router vulnerabilities.  
**Example:**  
- Set WPA3 in router settings, disable WPS via admin panel.  
- These steps block unauthorized access.

41. **What is SDN (Software-Defined Networking), and how does it differ from traditional networking?**  
**Answer:**  
SDN (Software-Defined Networking) centralizes network control using software, separating the control plane (decision-making) from the data plane (traffic forwarding).  
**Differences from traditional networking:**  
- **Control:** SDN uses a centralized controller (e.g., OpenFlow); traditional has distributed control in devices.  
- **Flexibility:** SDN is programmable, dynamic; traditional is hardware-dependent, static.  
- **Management:** SDN simplifies updates; traditional requires per-device config.  
**Example:** SDN in data centers for dynamic traffic management.  
It enhances scalability and adaptability.

42. **What is the difference between unicast, multicast, and broadcast communication?**  
**Answer:**  
- **Unicast:** One-to-one communication.  
    **Example:** Sending an email to one recipient.  
- **Multicast:** One-to-many (specific group).  
    **Example:** Streaming to subscribed viewers.  
- **Broadcast:** One-to-all within a network.  
    **Example:** ARP request to all LAN devices.  
**Difference:** Unicast targets one, multicast targets a subset, broadcast targets all.

43. **Explain the concept of packet switching vs. circuit switching.**  
**Answer:**  
- **Packet Switching:** Data is split into packets, sent independently over shared paths, reassembled at the destination.  
    **Example:** Internet (IP), efficient, flexible.  
- **Circuit Switching:** A dedicated path is established for the entire session.  
    **Example:** Traditional phone calls, fixed bandwidth.  
**Difference:** Packet switching optimizes resources, handles congestion; circuit switching guarantees connection but wastes unused capacity.

44. **What is latency, and how does it affect network performance?**  
**Answer:**  
Latency is the delay between sending and receiving data (e.g., measured in milliseconds).  
**Impact on performance:**  
- High latency slows response time (e.g., lag in video calls).  
- Affects real-time apps (e.g., gaming, VoIP).  
- Low latency improves user experience.  
**Example:** 100ms latency doubles page load time.  
It’s a key factor in responsiveness.

45. **What is bandwidth, and how is it different from throughput?**  
**Answer:**  
- **Bandwidth:** Maximum data transfer capacity of a link (e.g., 100 Mbps).  
    Theoretical limit, like pipe width.  
- **Throughput:** Actual data rate achieved (e.g., 80 Mbps).  
    Affected by latency, congestion, protocol overhead.  
**Difference:** Bandwidth is potential; throughput is real-world performance.

46. **How does traceroute work, and what does it tell you about a network?**  
**Answer:**  
Traceroute maps the path packets take to a destination by sending packets with increasing TTL values.  
**How it works:**  
- Sends packet with TTL=1 (first hop replies, TTL expires).  
- Increments TTL (TTL=2, second hop replies), repeats until destination.  
- Records response times and IPs.  
**What it tells:**  
- Hops, latency per hop, points of failure.  
**Example:** `traceroute google.com` shows route and delays.

47. **What is the purpose of the TTL (Time to Live) field in an IP packet?**  
**Answer:**  
The TTL (Time to Live) field limits a packet’s lifespan to prevent infinite loops.  
**Purpose:**  
- Each router decrements TTL by 1; if TTL=0, the packet is discarded.  
- Avoids network congestion from lost packets.  
**Example:** TTL=64, after 64 hops, packet is dropped.  
It ensures packets don’t circulate endlessly.

48. **What is network congestion, and how can it be managed?**  
**Answer:**  
Network congestion occurs when traffic exceeds a network’s capacity, causing delays or packet loss.  
**Management:**  
- **QoS:** Prioritize critical traffic (e.g., VoIP).  
- **Load balancing:** Distribute traffic across servers.  
- **Increase bandwidth:** Upgrade links.  
- **Traffic shaping:** Limit non-essential data.  
**Example:** Slowdowns during peak usage mitigated by QoS.  
It maintains performance under load.

49. **What is a socket in networking, and how is it used in programming?**  
**Answer:**  
A socket is an endpoint for network communication, combining an IP address and port (e.g., 192.168.1.1:80).  
**Use in programming:**  
- Created via APIs (e.g., `socket()` in C).  
- Enables client-server interaction (e.g., TCP connect, send data).  
- **Types:** Stream (TCP), Datagram (UDP).  
**Example:** Python’s `socket.connect(('google.com', 80))` opens an HTTP connection.  
It’s the interface for network I/O.

50. **How does a CDN (Content Delivery Network) improve network performance?**  
**Answer:**  
A CDN (Content Delivery Network) caches content on distributed servers closer to users.  
**How it improves performance:**  
- Reduces latency by serving data from nearby nodes.  
- Offloads origin server traffic.  
- Increases reliability with redundancy.  
**Example:** Netflix uses a CDN to stream from local servers, not central ones.  
It speeds up delivery and scales efficiently.