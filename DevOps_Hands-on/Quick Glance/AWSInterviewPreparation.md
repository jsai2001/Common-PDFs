Preparing for an AWS interview requires technical knowledge, hands-on experience, and understanding AWS services, architecture, and best practices. Below is a comprehensive list of interview preparation questions categorized by topic.

1. General Cloud Computing Concepts
- What is cloud computing, and how does it differ from traditional on-premises infrastructure?
- What are the key benefits of using AWS over other cloud providers?
- Explain the differences between IaaS, PaaS, and SaaS with AWS examples.
- What is elasticity in AWS, and how does it work?
- What is the shared responsibility model in AWS?
- How does AWS ensure high availability and fault tolerance?
- Differences between scalability and elasticity?
- What is a Region, Availability Zone, and Edge Location in AWS?
- How does pricing work in AWS, and what are some cost optimization strategies?
- What is the AWS Well-Architected Framework, and what are its five pillars?

2. AWS Core Services
- Compute
    - What is Amazon EC2, and what are its key features?
    - Differences between EC2 instance types (e.g., t2.micro vs. m5.large).
    - What are AMIs, and how are they used?
    - What is an Auto Scaling Group, and how does it work?
    - How do you secure an EC2 instance?
    - What is AWS Lambda, and when would you use it instead of EC2?
    - How does Elastic Beanstalk differ from EC2 and Lambda?
    - What is ECS, and how does it compare to EKS?
    - What is AWS Fargate, and how does it simplify container management?
    - How do you monitor the performance of an EC2 instance?
- Storage
    - Differences between Amazon S3, EBS, and EFS?
    - What is an S3 bucket, and what are some common use cases?
    - How does versioning work in Amazon S3?
    - What are S3 storage classes, and when would you use each one?
    - How do you secure data in an S3 bucket?
    - What is Amazon EBS, and how does it integrate with EC2?
    - Difference between SSD and HDD volumes in EBS?
    - How does Amazon EFS work, and when would you use it over S3?
    - What is AWS Snowball, and how is it used for data migration?
    - How do you enable encryption for S3, EBS, and EFS?
- Networking
    - What is a VPC, and how do you set one up?
    - Differences between a public subnet and a private subnet?
    - What is a Route Table, and how does it work in a VPC?
    - What is a NAT Gateway, and why is it used?
    - How does an Internet Gateway differ from a NAT Gateway?
    - What is AWS Direct Connect, and when would you use it?
    - What is Elastic Load Balancer (ELB), and what are its types?
    - How does Amazon Route 53 work, and what is its role in DNS management?
    - What is a Security Group, and how does it differ from a Network ACL?
    - How do you troubleshoot connectivity issues in a VPC?
- Databases
    - What is Amazon RDS, and what databases does it support?
    - How do you set up a Multi-AZ deployment in RDS for high availability?
    - What is Amazon Aurora, and how does it differ from traditional RDS?
    - What is Amazon DynamoDB, and when would you use it over RDS?
    - How does DynamoDB handle scaling and partitioning?
    - What is Amazon Redshift, and what are its use cases?
    - How do you migrate an on-premises database to AWS?
    - Difference between SQL and NoSQL databases in AWS?
    - How do you secure a database in AWS?
    - What is Amazon ElastiCache, and how does it improve application performance?

3. Security and Identity
- What is AWS IAM, and how does it manage permissions?
- What are IAM policies, roles, and users? How do they differ?
- How do you implement least privilege access in AWS?
- What is MFA in AWS, and how do you enable it?
- What is AWS KMS, and how does it work?
- How do you rotate encryption keys in AWS?
- What is AWS Shield, and how does it protect against DDoS attacks?
- What is AWS WAF, and how does it differ from a traditional firewall?
- How do you audit and monitor security in AWS?
- What is AWS Secrets Manager, and how does it differ from KMS?

4. Monitoring, Logging, and Management
- What is Amazon CloudWatch, and what can it monitor?
- How do you set up CloudWatch alarms?
- What is AWS CloudTrail, and how does it differ from CloudWatch?
- How do you use AWS Systems Manager to manage EC2 instances?
- What is AWS Trusted Advisor, and what does it check?
- How do you automate tasks in AWS using AWS CLI or SDKs?
- What is AWS Config, and how does it help with compliance?
- How do you troubleshoot a failed deployment in AWS?
- Difference between a metric and a log in CloudWatch?
- How do you set up centralized logging in AWS?

5. Architecture and Design
- How would you design a highly available application on AWS?
- Difference between horizontal and vertical scaling in AWS?
- How do you design a cost-effective architecture on AWS?
- What is a microservices architecture, and how does AWS support it?
- How would you migrate a monolithic application to AWS?
- What are some common anti-patterns in AWS architecture?
- How do you implement disaster recovery in AWS?
- Difference between stateless and stateful applications in AWS?
- How do you use AWS Global Accelerator to improve performance?
- How would you design a serverless architecture using AWS services?

6. DevOps and CI/CD
- What is AWS CodePipeline, and how does it integrate with other AWS services?
- How do you set up a CI/CD pipeline using AWS CodeBuild and CodeDeploy?
- Difference between blue/green deployment and rolling deployment in AWS?
- How do you use AWS CloudFormation to manage infrastructure as code?
- What are the benefits of using AWS OpsWorks?
- How do you roll back a failed deployment in AWS?
- What is the role of Docker in AWS, and how does ECS use it?
- How do you automate infrastructure provisioning in AWS?
- What is AWS CodeStar, and how does it simplify development?
- How do you handle secrets in a CI/CD pipeline?

7. Scenario-Based Questions
- How would you migrate a 10TB on-premises database to AWS with minimal downtime?
- Design an architecture for a web application that expects 1 million users daily.
- How would you troubleshoot an EC2 instance that is unreachable?
- A customer reports slow application performance—how do you diagnose and fix it?
- How would you secure an application that uses S3 and RDS?
- What steps would you take if an S3 bucket is accidentally made public?
- How would you handle a sudden traffic spike on your application?
- Design a backup and recovery strategy for an AWS-based application.
- How would you reduce costs for an overprovisioned AWS environment?
- How do you set up a multi-region architecture for disaster recovery?

8. Behavioral Questions (Aligned with Amazon Leadership Principles)
- Tell me about a time you took ownership of a project. (Ownership)
- Describe a situation where you had to dive deep into a technical problem. (Dive Deep)
- How do you handle disagreements with a team member about an AWS solution? (Disagree and Commit)
- Give an example of a time you delivered a solution faster than expected. (Deliver Results)
- How do you ensure customer satisfaction in a cloud project? (Customer Obsession)

Tips for Preparation
- Hands-On Practice: Set up a free-tier AWS account and experiment with services like EC2, S3, RDS, Lambda, and VPC.
- Certifications: Review materials for AWS Certified Solutions Architect, AWS Certified Developer, or AWS Certified SysOps Administrator.
- Documentation: Familiarize yourself with AWS Whitepapers (e.g., Well-Architected Framework, Security Best Practices).
- Mock Scenarios: Practice designing architectures and explaining them aloud.

The 101 questions provided cover a broad range of topics for an AWS interview, including core services, security, architecture, DevOps, and behavioral aspects. They should give you a solid foundation for preparation, especially for roles like Solutions Architect, Cloud Engineer, or DevOps Engineer. However, AWS is vast, and depending on the role, you might encounter more niche or advanced questions.

1. **What is cloud computing, and how does it differ from traditional on-premises infrastructure?**
    - Cloud computing is a model where computing resources like servers, storage, and applications are delivered over the internet, managed by a third-party provider. Instead of owning physical hardware, you rent what you need and access it remotely. With traditional on-premises infrastructure, you buy and maintain your own servers, networking equipment, and software in a local data center. The key differences are: cloud computing offers flexibility and scalability—you can scale resources up or down instantly—while on-premises requires upfront investment and manual upgrades. Cloud also reduces maintenance overhead since the provider handles hardware and updates, whereas on-premises gives you more control but at the cost of higher management effort.

2. **What are the key benefits of using AWS over other cloud providers?**
    - AWS stands out because of its massive global infrastructure, with more regions and availability zones than most competitors, which means better reliability and lower latency for users worldwide. It offers a huge range of services—over 200—covering compute, storage, AI, and more, so you can build almost anything. Another benefit is its pay-as-you-go pricing, which helps control costs compared to fixed pricing models. AWS also has a strong ecosystem, with tools like CloudFormation for automation and a big community for support. Plus, it’s known for security, with compliance certifications like SOC and GDPR, giving businesses confidence in their data protection.

3. **Explain the differences between IaaS, PaaS, and SaaS with AWS examples.**
    - IaaS, PaaS, and SaaS are three service models in cloud computing. IaaS, or Infrastructure as a Service, gives you raw infrastructure like virtual machines and storage that you manage—like Amazon EC2 for compute or S3 for storage. You’re responsible for the OS, applications, and updates. PaaS, Platform as a Service, takes it a step further by providing a platform to develop and deploy apps without worrying about the underlying infrastructure—an example is AWS Elastic Beanstalk, where you upload code, and AWS handles scaling and servers. SaaS, Software as a Service, is fully managed software you just use, like Amazon WorkMail for email—no setup or maintenance needed. So, IaaS is the most flexible but hands-on, PaaS simplifies development, and SaaS is ready-to-use.

4. **What is elasticity in AWS, and how does it work?**
    - Elasticity in AWS is the ability to automatically scale resources up or down based on demand, ensuring you only use what you need. For example, with Auto Scaling for EC2, if traffic spikes, AWS launches more instances to handle the load, and when demand drops, it terminates them to save costs. It works through services like Auto Scaling Groups, which monitor metrics like CPU usage via CloudWatch, and adjust capacity using predefined rules. Elasticity keeps applications responsive and cost-efficient, unlike static setups where you’d overprovision or risk downtime.

5. **What is the shared responsibility model in AWS?**
    - The shared responsibility model in AWS defines who’s accountable for what in terms of security and operations. AWS handles the "security OF the cloud"—things like physical data centers, hardware, and the virtualization layer. For example, they ensure servers are secure and patched at the infrastructure level. The customer is responsible for "security IN the cloud," which includes configuring services, managing data, and setting up access—like securing an S3 bucket with encryption or IAM policies. It’s a partnership: AWS provides the foundation, and I configure and protect what I build on it.

6. **How does AWS ensure high availability and fault tolerance?**
    - AWS ensures high availability and fault tolerance through its global infrastructure and redundant design. High availability means your application stays up even if something fails, and fault tolerance means it can recover quickly. They do this with Regions and Availability Zones—each Region has multiple isolated AZs with separate power and cooling. For example, I can deploy EC2 instances across AZs using an Auto Scaling Group and pair it with an Elastic Load Balancer to distribute traffic. Services like RDS Multi-AZ or S3’s cross-region replication also help by automatically failing over or syncing data, so there’s no single point of failure.

7. **Differences between scalability and elasticity?**
    - Scalability and elasticity are related but distinct. Scalability is the ability to handle increased load by adding resources, either manually or planned—like upgrading an EC2 instance size, called vertical scaling, or adding more instances, called horizontal scaling. Elasticity goes further—it’s automatic and dynamic, adjusting resources in real-time based on demand. For instance, with AWS Auto Scaling, if traffic spikes, it adds EC2 instances, then scales back when demand drops. Scalability is about capacity planning; elasticity is about responsiveness and efficiency.

8. **What is a Region, Availability Zone, and Edge Location in AWS?**
    - In AWS, a Region is a geographic area, like US-East-1 in Virginia, with multiple data centers for low-latency access and compliance. Each Region has Availability Zones, or AZs—isolated locations within that Region, like US-East-1a and 1b, with their own power and network, so if one fails, the others keep running. An Edge Location is part of the CloudFront CDN, a separate network of sites closer to users—like in a city near me—to cache content and reduce latency. Regions are for core services, AZs for redundancy, and Edge Locations for fast delivery.

9. **How does pricing work in AWS, and what are some cost optimization strategies?**
    - AWS uses a pay-as-you-go model—you only pay for what you use, with no upfront costs for most services. Pricing varies by service: EC2 charges per hour or second, S3 by storage and data transfer, Lambda by execution time. There are also free tiers for testing. For cost optimization, I’d use Reserved Instances for predictable workloads to save up to 75%, or Spot Instances for flexible tasks at a lower rate. Other strategies include right-sizing instances with tools like AWS Cost Explorer, setting budgets with AWS Budgets, and using S3 Intelligent-Tiering to move unused data to cheaper storage. It’s about matching resources to needs and monitoring usage.

10. **What is the AWS Well-Architected Framework, and what are its five pillars?**
     - The AWS Well-Architected Framework is a set of best practices to design and run workloads effectively on AWS. It helps me build secure, efficient, and reliable systems. It has five pillars: Operational Excellence—focusing on automation and monitoring, like using CloudWatch; Security—protecting data and systems with IAM and encryption; Reliability—ensuring uptime with Multi-AZ setups; Performance Efficiency—optimizing resources, like choosing the right EC2 type; and Cost Optimization—minimizing spend with tools like Reserved Instances. Together, they guide me to architect solutions that meet business goals while leveraging AWS strengths.

11. **What is Amazon EC2, and what are its key features?**
Amazon EC2 (Elastic Compute Cloud) provides resizable virtual servers in the cloud. It allows you to launch virtual machines (instances) to run applications or workloads. Key features include:
- Flexibility to choose instance types based on CPU, memory, and storage needs.
- Scalability to add or remove instances as demand changes.
- Pay-as-you-go pricing.
- Integration with other AWS services like S3 and VPC.
- Tools like Elastic IP addresses and security groups for managing access and connectivity.

12. **Differences between EC2 instance types (e.g., t2.micro vs. m5.large)**
EC2 instance types vary in compute, memory, and storage capabilities for different workloads:
- **t2.micro**: Small, low-cost instance from the T-family, suitable for light workloads like testing or small websites. Features burstable performance with 1 vCPU and 1 GB of memory.
- **m5.large**: General-purpose instance from the M-family, ideal for web servers or business apps. Provides 2 vCPUs, 8 GB of memory, and consistent performance without bursting.

13. **What are AMIs, and how are they used?**
AMIs (Amazon Machine Images) are pre-configured templates for launching EC2 instances. They include the OS, application software, and custom settings. AMIs can be AWS-provided or custom-created. They ensure consistency across environments and are used for scaling or disaster recovery. Stored in S3, AMIs can be shared or made public.

14. **What is an Auto Scaling Group, and how does it work?**
An Auto Scaling Group manages a collection of EC2 instances to maintain application performance and availability. It adjusts the number of instances based on demand, within defined minimum and maximum limits. Auto Scaling integrates with Elastic Load Balancers to distribute traffic, ensuring responsiveness and cost-efficiency.

15. **How do you secure an EC2 instance?**
To secure an EC2 instance:
- Use Security Groups as virtual firewalls to allow specific traffic.
- Configure IAM roles for least-privilege access.
- Enable EBS volume encryption for data at rest.
- Use VPC private subnets to keep instances off the public internet.
- Regularly update OS and software with patches.
- Monitor for threats using CloudTrail or third-party solutions.

16. **What is AWS Lambda, and when would you use it instead of EC2?**
AWS Lambda is a serverless compute service for running code without managing servers. It handles execution, scaling, and maintenance, charging only for runtime. Use Lambda for short, event-based tasks like processing file uploads or running cron jobs. Use EC2 for long-running, resource-heavy applications needing full server control.

17. **How does Elastic Beanstalk differ from EC2 and Lambda?**
Elastic Beanstalk is a Platform-as-a-Service that simplifies deploying and managing applications. Unlike EC2, it automates server, storage, and networking configuration. Compared to Lambda, which runs event-driven code, Elastic Beanstalk supports full applications needing persistent environments. It offers more control than Lambda and less manual work than EC2.

18. **What is ECS, and how does it compare to EKS?**
ECS (Elastic Container Service) is AWS’s managed container orchestration service for Docker containers. EKS (Elastic Kubernetes Service) is AWS’s managed Kubernetes service. ECS is simpler and AWS-native, suitable for basic container workloads. EKS offers Kubernetes’ flexibility and portability, ideal for complex, multi-cloud setups.

19. **What is AWS Fargate, and how does it simplify container management?**
AWS Fargate is a serverless compute engine for containers, used with ECS or EKS. It eliminates the need to manage EC2 instances, handling patching, scaling, and infrastructure maintenance. Define your container’s CPU and memory needs, and Fargate manages the rest. Ideal for lightweight, sporadic workloads.

20. **How do you monitor the performance of an EC2 instance?**
Use Amazon CloudWatch to monitor EC2 instance performance. It collects metrics like CPU utilization, memory usage, disk I/O, and network traffic. Set up custom dashboards and alarms for performance thresholds. Install the CloudWatch Agent for additional metrics and use CloudTrail for API call logs. Systems Manager can check instance health and run diagnostics.

21. **What are the differences between Amazon S3, EBS, and EFS?**  
Amazon S3, EBS, and EFS are all storage services in AWS, but they serve different purposes. S3 (Simple Storage Service) is object storage designed for scalability and durability, ideal for storing backups, media files, or static websites, accessed over the internet via HTTP. EBS (Elastic Block Store) provides block storage, like a virtual hard drive for EC2 instances, offering fast and low-latency performance, perfect for databases or applications needing consistent I/O performance. EFS (Elastic File System) is a file storage service that acts like a shared drive, mountable across multiple EC2 instances and scales automatically, making it great for shared workloads like content management systems. In summary, S3 is for objects, EBS is for blocks tied to one instance, and EFS is for shared files across many instances.

22. **What is an S3 bucket, and what are some common use cases?**  
An S3 bucket is a container in Amazon S3 where you store objects, like files or data. It’s globally unique by name and tied to a specific AWS region for data residency. Common use cases include hosting static websites (like HTML or CSS files), storing backups or archives due to its durability, serving as a data lake for analytics with tools like Athena, or distributing large files via CloudFront for content delivery. It’s versatile because it can handle anything from a few kilobytes to petabytes of data.

23. **How does versioning work in Amazon S3?**  
Versioning in S3 is a feature you enable on a bucket to keep multiple versions of an object. Once enabled, every time you upload or modify an object with the same key, S3 creates a new version instead of overwriting the old one, assigning each a unique version ID. You can retrieve or restore previous versions if needed, which is useful for backup, auditing, or undoing changes. However, it does increase storage costs since all versions are retained until you delete them manually or set up lifecycle rules.

24. **What are S3 storage classes, and when would you use each one?**  
S3 offers several storage classes based on access patterns and cost. Standard is the default, offering high durability and low latency, great for frequently accessed data like active websites or app content. Infrequent Access (IA) is cheaper for data you access less often, like backups, with a minimum storage duration. One Zone-IA is even lower cost but stores data in one Availability Zone, making it riskier and suitable for non-critical, replaceable data. Glacier is for long-term archival, being super cheap but slow to retrieve, ideal for compliance data. Glacier Deep Archive is the cheapest, for data you rarely need, like legal records with retrieval times of hours. Intelligent-Tiering automatically moves objects between tiers based on access patterns, perfect if usage is unpredictable. Choose based on how often you need the data and your budget.

25. **How do you secure data in an S3 bucket?**  
To secure an S3 bucket, start with access control using IAM policies to define who can access it and bucket policies to set permissions at the bucket level, like denying public access. Enable encryption with server-side encryption using S3-managed keys (SSE-S3) for simplicity, or AWS KMS for more control over keys. Turn on versioning to protect against accidental deletes and use MFA Delete for extra security on critical operations. Blocking public access settings is essential to prevent leaks, and monitor access with CloudTrail logs or S3 access logs. Use VPC endpoints for private access without hitting the public internet.

26. **What is Amazon EBS, and how does it integrate with EC2?**  
Amazon EBS (Elastic Block Store) is a block storage service that provides persistent storage for EC2 instances, like attaching a hard drive to a virtual machine. It integrates with EC2 by being mounted as a volume to an instance, acting as the root device for the OS or additional storage for data. Create an EBS volume in the same Availability Zone as your EC2 instance, attach it via the AWS console or CLI, and then format and mount it like a filesystem. It’s tightly coupled with EC2 for low-latency performance, and you can take snapshots of it for backups or to create new volumes.

27. **What is the difference between SSD and HDD volumes in EBS?**  
EBS offers SSD and HDD volume types for different performance needs. SSD volumes, like gp3 or io2, are faster with higher IOPS, great for workloads needing quick access, like databases or boot volumes. Gp3 is general-purpose and cost-effective, while io2 is optimized for high-performance apps with low latency. HDD volumes, like st1 and sc1, are slower but cheaper and designed for throughput-intensive tasks. St1 is for streaming workloads like big data, and sc1 is cold storage for infrequently accessed data. Use SSD for speed and HDD for cost savings on large, sequential data.

28. **How does Amazon EFS work, and when would you use it over S3?**  
Amazon EFS (Elastic File System) is a managed file storage service that provides a shared filesystem across multiple EC2 instances. It works by letting you mount it to instances in different Availability Zones within a region using NFS protocol. It scales automatically as you add or remove files, so you don’t manage capacity. Use EFS over S3 when you need a traditional filesystem for shared access, like for a web app where multiple servers need to read and write to the same files, or for DevOps tools like Jenkins. S3 is better for object storage, like backups or static assets, while EFS shines for dynamic, multi-instance file sharing.

29. **What is AWS Snowball, and how is it used for data migration?**  
AWS Snowball is a physical device for transferring large amounts of data to and from AWS, like a rugged, portable hard drive with up to 80 terabytes of capacity. For data migration, order a Snowball from AWS, it arrives at your location, and you copy your data onto it using a client tool. Then ship it back to AWS, and they upload it to your S3 bucket. It’s used when transferring data over the internet would be too slow or impractical, like moving terabytes or petabytes from an on-premises data center to AWS. There’s also Snowball Edge with compute capabilities for pre-processing data locally.

30. **How do you enable encryption for S3, EBS, and EFS?**  
For S3, enable encryption by setting default server-side encryption on the bucket with SSE-S3 for simplicity, or SSE-KMS if you need key management via AWS KMS. Configure this in bucket properties or via a policy. For EBS, enable encryption when creating a volume, choosing either AWS-managed keys or a custom KMS key. It’s automatic for all data and snapshots once set. For EFS, turn on encryption at rest during filesystem creation, using a KMS key, which encrypts all files as they’re stored. In all cases, use IAM to control access to the keys and monitor usage with CloudTrail.

31. **What is a VPC, and how do you set one up?**
    A VPC, or Virtual Private Cloud, is a logically isolated section of the AWS cloud where you can launch resources like EC2 instances, databases, or load balancers. It gives you control over your network environment, including IP ranges, subnets, and routing.

    To set one up, you start by going to the AWS Management Console and selecting the VPC service. Click 'Create VPC,' then define a name and a CIDR block—like 10.0.0.0/16—to set the IP range. After that, you create subnets within the VPC, associating them with Availability Zones for high availability. You also attach an Internet Gateway for public access and configure route tables to direct traffic. Optionally, you can add security groups and network ACLs to control access. That’s the basic setup!

32. **What are the differences between a public subnet and a private subnet?**
    A public subnet is a subnet that has a route to the Internet through an Internet Gateway, meaning resources in it—like an EC2 instance—can be accessed from the outside world if configured that way. It’s typically used for web servers or anything public-facing.

    A private subnet, on the other hand, doesn’t have a direct route to the Internet Gateway. It’s isolated from inbound public traffic and is ideal for backend resources like databases or application servers that don’t need direct Internet exposure. To let private subnets access the Internet—like for software updates—you’d use a NAT Gateway in a public subnet to route outbound traffic securely. So, the key difference is the route to the Internet Gateway.

33. **What is a Route Table, and how does it work in a VPC?**
    A Route Table is like a set of traffic rules in a VPC that determines where network traffic goes. It contains entries—called routes—that map destination IP addresses to targets like an Internet Gateway, NAT Gateway, or another subnet.

    In a VPC, you create a route table and associate it with one or more subnets. For example, in a public subnet’s route table, you’d add a route like 0.0.0.0/0 pointing to an Internet Gateway for Internet access. In a private subnet, that route might point to a NAT Gateway instead. When traffic leaves a resource, the VPC checks the route table tied to its subnet and directs it accordingly. You can have multiple route tables to customize traffic flow across your VPC.

34. **What is a NAT Gateway, and why is it used?**
    A NAT Gateway, or Network Address Translation Gateway, allows resources in a private subnet to access the Internet—like for downloading updates—while keeping them hidden from inbound public traffic. It acts as a middleman.

    You place it in a public subnet with an Internet Gateway, assign it an Elastic IP, and update the private subnet’s route table to send outbound traffic—like 0.0.0.0/0—to the NAT Gateway. It’s used because private resources need Internet access for things like patches or APIs, but you don’t want them exposed. It’s secure, managed by AWS, and handles high bandwidth, unlike a NAT instance, which is an older, manual alternative.

35. **How does an Internet Gateway differ from a NAT Gateway?**
    An Internet Gateway and a NAT Gateway both deal with Internet traffic, but they’re quite different. An Internet Gateway is a VPC component that enables direct, two-way communication between your VPC and the public Internet. You attach it to your VPC and add a route—like 0.0.0.0/0—in a subnet’s route table to make it public. It’s for resources that need to be reachable, like web servers.

    A NAT Gateway, though, is for one-way outbound traffic from private subnets. It sits in a public subnet, uses the Internet Gateway to reach the Internet, and lets private resources initiate connections—like for updates—without being accessible from outside. So, the Internet Gateway is about full access, while the NAT Gateway is about controlled, outbound-only access.

36. **What is AWS Direct Connect, and when would you use it?**
    AWS Direct Connect is a service that provides a dedicated, private network connection between your on-premises data center and AWS. Instead of using the public Internet, it uses a physical fiber-optic link to an AWS Direct Connect location, giving you consistent, low-latency bandwidth.

    You’d use it when you need reliable, high-speed data transfer—like for hybrid cloud setups, large data migrations, or latency-sensitive applications. For example, if you’re moving terabytes of data to S3 or running real-time analytics between on-prem servers and AWS, Direct Connect beats the unpredictability of Internet connections. It’s also great for compliance, as it keeps traffic off the public Internet.

37. **What is Elastic Load Balancer (ELB), and what are its types?**
    An Elastic Load Balancer, or ELB, is a managed service that automatically distributes incoming traffic across multiple targets—like EC2 instances—to improve availability and scalability. It sits in front of your application and balances load based on rules you set.

    There are three types: First, the Application Load Balancer (ALB), which works at Layer 7—HTTP/HTTPS—and is great for web apps with features like path-based routing. Second, the Network Load Balancer (NLB), at Layer 4—TCP/UDP—handles high-performance, low-latency traffic, like for gaming or streaming. Third, the Classic Load Balancer (CLB), an older option for basic Layer 4 or 7 balancing, but it’s less feature-rich. You pick the type based on your app’s needs—ALB for web, NLB for speed, CLB for legacy.

38. **How does Amazon Route 53 work, and what is its role in DNS management?**
    Amazon Route 53 is AWS’s scalable DNS service that translates domain names—like example.com—into IP addresses so users can reach your resources. It’s also a domain registrar and supports health checks and routing policies.

    It works by you creating a hosted zone for your domain in Route 53, then adding records—like A records for IPs, CNAMEs for aliases, or MX for email. When someone types your domain, Route 53’s global network of DNS servers responds with the right IP based on your setup. Its role in DNS management is to ensure fast, reliable name resolution, plus extras like latency-based routing or failover to keep your app available. It’s tightly integrated with AWS services like ELB or S3.

39. **What is a Security Group, and how does it differ from a Network ACL?**
    A Security Group is a virtual firewall that controls traffic to and from resources like EC2 instances at the instance level. It’s stateful, meaning if you allow inbound traffic, the return traffic is automatically allowed. You set rules—like allowing port 80 for HTTP—and apply them to specific instances.

    A Network ACL, or NACL, works at the subnet level and is stateless, so you have to explicitly allow both inbound and outbound traffic. It’s evaluated before Security Groups and uses numbered rules—like a traditional firewall. The big difference is scope—Security Groups are for instances, NACLs are for subnets—and behavior: stateful versus stateless. You’d use both together for layered security.

40. **How do you troubleshoot connectivity issues in a VPC?**
    To troubleshoot connectivity in a VPC, I’d start by checking the basics. First, verify the route table for the subnet—is there a route to the destination, like 0.0.0.0/0 to an Internet Gateway for public access? If it’s a private subnet, check the NAT Gateway setup and its Elastic IP.

    Next, I’d look at Security Groups—are the ports open, like 80 for HTTP, for both inbound and outbound? Then, check the Network ACLs—since they’re stateless, ensure rules allow traffic both ways. I’d also use VPC Flow Logs to see where traffic’s dropping, and ping or SSH to test reachability. Finally, confirm the resource—like an EC2 instance—has a public IP or is in the right subnet. It’s a step-by-step process to isolate the issue.

41. **What is Amazon RDS, and what databases does it support?**
    - Amazon RDS (Relational Database Service) is a managed database service that simplifies setting up, operating, and scaling relational databases in the cloud. It handles tasks like backups, patching, and scaling, so you don’t have to manage the underlying infrastructure. RDS supports six database engines: MySQL, PostgreSQL, MariaDB, Oracle, Microsoft SQL Server, and Amazon Aurora, which is AWS’s high-performance database.

42. **How do you set up a Multi-AZ deployment in RDS for high availability?**
    - To set up a Multi-AZ deployment in RDS for high availability, start by launching an RDS instance through the AWS Management Console or CLI. During setup, enable the Multi-AZ option, which automatically creates a primary database in one Availability Zone and a standby replica in another. AWS synchronously replicates data from the primary to the standby. If the primary fails, RDS automatically fails over to the standby with minimal downtime. Ensure the VPC has at least two subnets in different AZs, and test failover to confirm it works as expected.

43. **What is Amazon Aurora, and how does it differ from traditional RDS?**
    - Amazon Aurora is a high-performance, MySQL- and PostgreSQL-compatible relational database built by AWS. Unlike traditional RDS, which relies on standard database engines, Aurora separates compute from storage, using a distributed storage layer that auto-scales up to 128 terabytes. It’s faster—up to five times faster than MySQL and three times faster than PostgreSQL—because of its architecture and features like read replicas. It also offers better durability with six copies of data across three Availability Zones, while traditional RDS typically uses one primary and an optional standby.

44. **What is Amazon DynamoDB, and when would you use it over RDS?**
    - Amazon DynamoDB is a fully managed NoSQL database service designed for key-value and document data models. It’s highly scalable, offers single-digit millisecond performance, and doesn’t require you to manage servers. Use DynamoDB over RDS when you need a database for applications with unpredictable or massive scale, like gaming, IoT, or real-time analytics, where horizontal scaling and low latency are critical. RDS is better for structured, relational data with complex queries, while DynamoDB excels at simple, fast key-value lookups.

45. **How does DynamoDB handle scaling and partitioning?**
    - DynamoDB handles scaling and partitioning automatically. For scaling, it offers two modes: on-demand, where it adjusts capacity based on traffic without you specifying limits, and provisioned, where you set read and write capacity units and can enable auto-scaling to adjust them. For partitioning, DynamoDB splits data across partitions using a partition key you define. As data or traffic grows, it adds more partitions to distribute the load, ensuring performance stays consistent. Design the key to avoid hot partitions.

46. **What is Amazon Redshift, and what are its use cases?**
    - Amazon Redshift is a fully managed data warehouse service optimized for analyzing large datasets using SQL. It’s based on a columnar storage architecture, which makes it fast for complex queries and aggregations. Use Redshift for use cases like business intelligence, reporting, and data analytics—such as analyzing sales trends or customer behavior from terabytes of data. It integrates with tools like Amazon QuickSight and supports massive parallel processing, making it ideal for big data workloads.

47. **How do you migrate an on-premises database to AWS?**
    - To migrate an on-premises database to AWS, use the AWS Database Migration Service (DMS). First, assess the source database to ensure compatibility—such as MySQL or Oracle—with the target, like RDS or Aurora. Set up a replication instance in DMS, configure the source and target endpoints, and create a task to migrate the data. For minimal downtime, use continuous replication to sync changes, then switch the application to the AWS database once caught up. Test connectivity, validate data, and optimize performance post-migration.

48. **What’s the difference between SQL and NoSQL databases in AWS?**
    - In AWS, SQL databases, like RDS and Aurora, store structured data in tables with predefined schemas, using relational models. They’re great for complex joins and transactions—think e-commerce order systems. NoSQL databases, like DynamoDB, handle unstructured or semi-structured data with flexible schemas, using key-value or document models. They’re built for horizontal scaling and high-speed access, ideal for apps like social media or real-time analytics. SQL is about consistency; NoSQL prioritizes scalability and speed.

49. **How do you secure a database in AWS?**
    - To secure a database in AWS, start with IAM to control access, creating roles and policies with least privilege. Enable encryption at rest using AWS KMS for services like RDS or DynamoDB, and encryption in transit with SSL/TLS. Place the database in a private subnet within a VPC, using security groups to restrict traffic—such as only from an application server. Turn on logging with CloudTrail or RDS logs, set up backups with retention, and use Multi-Factor Authentication for admin access.

50. **What is Amazon ElastiCache, and how does it improve application performance?**
    - Amazon ElastiCache is a managed in-memory caching service that supports Redis and Memcached. It stores frequently accessed data—like query results or session info—in memory, reducing the load on databases like RDS or DynamoDB. This cuts latency from milliseconds to microseconds, speeding up applications. For example, in a web app, cache user profiles to avoid repeated database hits. It’s easy to scale, and you can configure it for high availability with replication, boosting performance and reliability.

51. **What is AWS IAM, and how does it manage permissions?**
    AWS IAM (Identity and Access Management) is a service that controls access to AWS resources. It manages permissions by allowing the creation of users, groups, and roles, and attaching policies to them. These policies are JSON documents that define specific actions, such as launching an EC2 instance or reading an S3 bucket. IAM enforces these policies across the AWS account to ensure security and organization.

52. **What are IAM policies, roles, and users? How do they differ?**
    IAM policies are JSON rules that define permissions, such as allowing S3 access. Users are individual identities with credentials to log in and act based on attached policies. Roles are temporary identities that services or users assume, such as an EC2 instance running a task. Users are for people with long-term access, roles are for short-term tasks or services, and policies are the rules applied to both.

53. **How do you implement least privilege access in AWS?**
    To implement least privilege in AWS, ensure every user, role, or service only gets the permissions they need. Create specific IAM policies tailored to exact tasks, such as "read-only S3" instead of full access. Assign these policies to users or roles, test them to confirm they work, and regularly review them with tools like IAM Access Analyzer to remove unnecessary permissions.

54. **What is MFA in AWS, and how do you enable it?**
    MFA (Multi-Factor Authentication) adds an extra layer of security by requiring a second factor, such as a code from a phone, beyond just a password. To enable it, go to the IAM console, select a user, and click "Manage MFA." Choose a virtual device, scan the QR code with an app like Google Authenticator, and enter two consecutive codes to activate it.

55. **What is AWS KMS, and how does it work?**
    AWS KMS (Key Management Service) is a managed service for creating and controlling encryption keys. It allows the generation of keys to encrypt data, such as in S3 or RDS, and stores them securely. KMS handles key operations without exposing the key itself and integrates with other AWS services for seamless encryption.

56. **How do you rotate encryption keys in AWS?**
    To rotate encryption keys in AWS KMS, enable automatic key rotation for a customer-managed key. In the KMS console, select the key and turn on "Rotate key every year." KMS generates a new key version annually while keeping the old ones for decryption. For manual rotation, create a new key, update applications to use it, and archive the old one.

57. **What is AWS Shield, and how does it protect against DDoS attacks?**
    AWS Shield is a managed service that protects against Distributed Denial of Service (DDoS) attacks. The standard version is free and automatically guards AWS resources, such as ELB or CloudFront, against common attacks. Shield Advanced, a paid tier, adds extra protection for larger attacks, with 24/7 support and cost protection. It detects and mitigates traffic floods to keep applications online during an attack.

58. **What is AWS WAF, and how does it differ from a traditional firewall?**
    AWS WAF (Web Application Firewall) protects web applications from threats like SQL injection or cross-site scripting. It is used with services like CloudFront or ALB, setting rules to filter malicious traffic, such as blocking specific IPs or patterns. Unlike a traditional firewall that controls network-level access, WAF focuses on application-layer (Layer 7) threats, providing fine-tuned control over HTTP traffic.

59. **How do you audit and monitor security in AWS?**
    To audit and monitor security in AWS, use CloudTrail to log all API calls and review them for suspicious activity. Pair it with CloudWatch to set alarms for security events, such as failed logins. AWS Config tracks resource changes for compliance, and IAM Access Analyzer flags risky permissions. Together, they provide a comprehensive view of security posture and help respond quickly.

60. **What is AWS Secrets Manager, and how does it differ from KMS?**
    AWS Secrets Manager stores and manages sensitive data, such as API keys or passwords, and rotates them automatically if needed. It securely retrieves secrets in applications without hardcoding them. KMS is for creating and managing encryption keys, not storing secrets. Secrets Manager uses KMS to encrypt the secrets, so they work together—KMS handles the keys, while Secrets Manager manages the data.

61. **What is Amazon CloudWatch, and what can it monitor?**
    Amazon CloudWatch is a monitoring service that collects and tracks metrics, logs, and events from AWS resources and applications. It can monitor things like EC2 instances for CPU usage, RDS for database performance, Lambda for invocation counts, or S3 for request rates. It also lets you set alarms and visualize data with dashboards, so you can keep an eye on your entire AWS environment in real time.

62. **How do you set up CloudWatch alarms?**
    To set up CloudWatch alarms, go to the CloudWatch console, select "Alarms," and click "Create Alarm." Choose a metric—like EC2 CPU utilization—set a threshold, say 80% for 5 minutes, and define an action, like sending a notification to an SNS topic or triggering an Auto Scaling event. After configuring the period and conditions, name it and save it. The alarm then monitors and alerts you if the threshold is breached.

63. **What is AWS CloudTrail, and how does it differ from CloudWatch?**
    AWS CloudTrail is a service that logs all API calls and activities in your AWS account, like who created an S3 bucket or stopped an EC2 instance. It’s about auditing and tracking user actions. CloudWatch, on the other hand, focuses on performance monitoring—like metrics and logs from resources. So, CloudTrail tells you what happened, while CloudWatch shows how your systems are performing. They complement each other for security and ops.

64. **How do you use AWS Systems Manager to manage EC2 instances?**
    Use AWS Systems Manager to manage EC2 instances by installing the SSM Agent on them, which lets you remotely run commands, patch software, or automate tasks. In the SSM console, use "Run Command" to execute scripts—like updating packages—or "State Manager" to enforce configurations. You can also use "Session Manager" for secure shell access without SSH keys, making it easier to manage fleets of instances efficiently.

65. **What is AWS Trusted Advisor, and what does it check?**
    AWS Trusted Advisor is a tool that gives real-time recommendations to optimize your AWS environment. It checks five areas: cost optimization—like spotting idle resources; performance—like underused EBS volumes; security—like open S3 buckets; fault tolerance—like Multi-AZ setups; and service limits—like nearing VPC quotas. It’s like a health check that helps you follow best practices and save money.

66. **How do you automate tasks in AWS using AWS CLI or SDKs?**
    To automate tasks in AWS, use the AWS CLI or SDKs by writing scripts. For example, with the CLI, you could run `aws ec2 start-instances --instance-ids i-123456` to start an EC2 instance from a cron job. With an SDK—like Python’s Boto3—write code to, say, stop idle instances based on CloudWatch metrics. Both let you automate repetitive tasks like backups, scaling, or deployments without manual clicks.

67. **What is AWS Config, and how does it help with compliance?**
    AWS Config is a service that tracks the configuration history of your AWS resources—like VPC settings or IAM policies—and records changes over time. It helps with compliance by letting you audit resources against rules you define, like ensuring S3 buckets aren’t public. If something drifts from a compliant state, it flags it, so you can fix it and prove adherence to standards like HIPAA or PCI.

68. **How do you troubleshoot a failed deployment in AWS?**
    To troubleshoot a failed deployment in AWS, start by checking CloudWatch logs for errors—like in CodeDeploy or Lambda logs. Look at CloudTrail to see what API calls failed or who made changes. If it’s an EC2 issue, verify security groups, IAM roles, and instance health. Roll back using CloudFormation or CodePipeline if needed, test in a staging environment, and fix the root cause—like a misconfigured parameter—before redeploying.

69. **Difference between a metric and a log in CloudWatch?**
    In CloudWatch, a metric is a numerical data point—like CPU usage or request latency—tracked over time for analysis or alarms. A log, though, is detailed text output—like error messages or app events—stored as log streams for debugging. Metrics give you trends, like “CPU spiked to 90%,” while logs tell you why, like “app crashed at 2 PM.” Use both to monitor and troubleshoot effectively.

70. **How do you set up centralized logging in AWS?**
    To set up centralized logging in AWS, use CloudWatch Logs to collect logs from resources like EC2, Lambda, or RDS. Create a log group as the central hub, then configure agents—like the CloudWatch Logs Agent on EC2—to send logs there. For cross-account or cross-region setups, use a subscription filter to stream logs to a central S3 bucket or Kinesis, and maybe analyze them with Elasticsearch or Athena for a unified view.

71. **How would you design a highly available application on AWS?**
    To design a highly available application on AWS, I’d deploy it across multiple Availability Zones in a region. I’d use an Elastic Load Balancer to distribute traffic to EC2 instances in an Auto Scaling group, ensuring it scales with demand and replaces failed instances. For data, I’d use Amazon RDS with Multi-AZ for automatic failover, or DynamoDB for global replication. I’d add Route 53 for DNS health checks and failover routing. Monitoring with CloudWatch would keep everything in check, minimizing downtime.

72. **Difference between horizontal and vertical scaling in AWS?**
    Horizontal scaling means adding more instances—like more EC2 servers—to handle load, which AWS supports with Auto Scaling and load balancers. It’s great for distributing traffic and fault tolerance. Vertical scaling means increasing an instance’s size—like upgrading from t2.micro to m5.large—which is simpler but has limits and downtime risks. In AWS, horizontal scaling is preferred for elasticity, while vertical scaling works for quick, temporary boosts.

73. **How do you design a cost-effective architecture on AWS?**
    For a cost-effective architecture, I’d use the right-sized EC2 instances with Reserved Instances or Savings Plans for predictable workloads, and Spot Instances for flexible tasks. I’d leverage serverless services like Lambda to pay only for usage, and store infrequently accessed data in S3 Glacier instead of Standard. Auto Scaling would match resources to demand, and I’d use Cost Explorer and Trusted Advisor to monitor and optimize spending, avoiding overprovisioning.

74. **What is a microservices architecture, and how does AWS support it?**
    A microservices architecture breaks an application into small, independent services that communicate via APIs—think user authentication as one service, payments as another. AWS supports it with ECS or EKS for containerized services, Lambda for serverless functions, and API Gateway for managing APIs. Services like SQS and SNS handle messaging between them, while CloudWatch and X-Ray provide monitoring and tracing, making it easy to scale and update each piece separately.

75. **How would you migrate a monolithic application to AWS?**
    To migrate a monolith to AWS, I’d start with a “lift and shift” using EC2 to get it running as-is, minimizing changes. Then, I’d refactor it incrementally—breaking it into microservices. I’d use AWS Application Migration Service to move the app, RDS for the database, and Elastic Beanstalk for initial deployment. Over time, I’d replace parts with Lambda or ECS, testing each step, and use Route 53 to switch traffic smoothly once it’s stable.

76. **What are some common anti-patterns in AWS architecture?**
    Common anti-patterns include overprovisioning—like using oversized EC2 instances instead of scaling dynamically—or treating AWS like an on-premises setup, ignoring elasticity. Another is neglecting security, like leaving S3 buckets public, or not using Multi-AZ for availability. Hardcoding resources instead of using CloudFormation, or skipping monitoring with CloudWatch, also hurts scalability and resilience. I’d avoid these by following the Well-Architected Framework.

77. **How do you implement disaster recovery in AWS?**
    For disaster recovery, I’d use a multi-region strategy. I’d replicate data with S3 Cross-Region Replication and RDS read replicas in a backup region. I’d use Route 53 with failover routing to switch traffic if the primary region fails. For compute, I’d keep AMIs and CloudFormation templates ready to spin up EC2 instances quickly. Regular backups with EBS snapshots and testing with Chaos Engineering would ensure recovery works, meeting RTO and RPO goals.

78. **Difference between stateless and stateful applications in AWS?**
    Stateless applications don’t retain data between requests—like a web server using Lambda or EC2 with no session memory—making them easy to scale horizontally. Stateful applications store data—like a database or user session on an instance—requiring sticky sessions or external storage like ElastiCache. In AWS, stateless apps pair well with Auto Scaling, while stateful ones need RDS or DynamoDB to offload state, balancing complexity and scalability.

79. **How do you use AWS Global Accelerator to improve performance?**
    AWS Global Accelerator improves performance by routing user traffic to the nearest AWS edge location using the AWS global network, reducing latency. I’d set it up by creating an accelerator, adding endpoints like ALBs or EC2 instances, and assigning weights for traffic distribution. It uses anycast IP addresses for faster routing and offers failover, so if an endpoint fails, it shifts traffic automatically—perfect for global apps needing speed and reliability.

80. **How would you design a serverless architecture using AWS services?**
    For a serverless architecture, I’d use Lambda for compute, triggered by events from API Gateway for HTTP requests or S3 for file uploads. I’d store data in DynamoDB for scalability or S3 for static files, and use Step Functions to orchestrate workflows—like processing an order. SNS or SQS would handle messaging between components, and CloudWatch would monitor it all. It’s cost-efficient, auto-scaling, and frees me from managing servers.

81. **What is AWS CodePipeline, and how does it integrate with other AWS services?**
    AWS CodePipeline is a fully managed continuous integration and continuous deployment service that automates the build, test, and deploy stages of a release pipeline. It integrates with services like CodeCommit for source control, CodeBuild for compiling and testing code, and CodeDeploy for deploying to EC2, Lambda, or ECS. It also works with S3 for artifact storage and CloudWatch for monitoring, making it a central hub to streamline my DevOps workflow.

82. **How do you set up a CI/CD pipeline using AWS CodeBuild and CodeDeploy?**
    To set up a CI/CD pipeline with CodeBuild and CodeDeploy, I’d start in CodePipeline and create a new pipeline. I’d connect it to a source like CodeCommit or GitHub. Then, I’d add a build stage using CodeBuild—defining a buildspec.yml file to compile code, run tests, and produce artifacts. Next, I’d add a deploy stage with CodeDeploy, linking it to an application and deployment group—like EC2 instances or Lambda. I’d save the pipeline, commit code to trigger it, and monitor the process in the console.

83. **Difference between blue/green deployment and rolling deployment in AWS?**
    In AWS, a blue/green deployment creates two identical environments—blue is live, green is new. I’d deploy the update to green, test it, then switch traffic (like via Route 53 or ELB) to green with no downtime. If it fails, I switch back to blue. A rolling deployment updates instances gradually—like with CodeDeploy—replacing old versions one by one, keeping some capacity live. Blue/green is faster and safer but costs more; rolling is cheaper but risks partial outages.

84. **How do you use AWS CloudFormation to manage infrastructure as code?**
    I use AWS CloudFormation to manage infrastructure as code by writing templates in JSON or YAML that define resources—like VPCs, EC2 instances, or S3 buckets. I’d upload the template to CloudFormation, which provisions and configures everything automatically. For updates, I’d modify the template and apply a stack update, and CloudFormation handles the changes or rolls back if needed. It’s repeatable, versionable, and keeps my infrastructure consistent across environments.

85. **What are the benefits of using AWS OpsWorks?**
    AWS OpsWorks gives me a managed configuration management service using Chef or Puppet, so I can automate server setup, deployment, and scaling. It benefits me by simplifying complex app deployments—like multi-tier web apps—across EC2 instances or on-premises servers. It supports auto-scaling, patching, and monitoring out of the box, saving time compared to manual setups. Plus, it integrates with other AWS services like CloudWatch, making it easier to manage operations.

86. **How do you roll back a failed deployment in AWS?**
    To roll back a failed deployment in AWS, I’d depend on the service. With CodeDeploy, if it’s a blue/green setup, I’d switch traffic back to the original environment via the load balancer or Route 53. For rolling deployments, I’d trigger a redeployment with the last successful version from CodePipeline. With CloudFormation, I’d revert the stack to a previous template version. I’d also check CloudWatch logs to debug the failure before retrying, ensuring a smooth rollback.

87. **What is the role of Docker in AWS, and how does ECS use it?**
    Docker in AWS lets me package applications into containers with code, dependencies, and configs, ensuring consistency across environments. AWS ECS, or Elastic Container Service, uses Docker to run and manage these containers at scale. I’d push my Docker images to Amazon ECR, then define tasks in ECS to launch containers on EC2 or Fargate. ECS handles scheduling, scaling, and load balancing, making it easy to deploy and manage containerized apps.

88. **How do you automate infrastructure provisioning in AWS?**
    I automate infrastructure provisioning in AWS using tools like CloudFormation or Terraform for templates, defining resources like EC2 or RDS. I’d pair it with CodePipeline to trigger provisioning on code changes, using the AWS CLI or SDKs for scripting. For dynamic scaling, I’d set up Auto Scaling Groups or Lambda triggers. I’d also use Systems Manager to automate patching or config updates, keeping everything hands-off and repeatable.

89. **What is AWS CodeStar, and how does it simplify development?**
    AWS CodeStar is a service that simplifies development by providing a unified interface to set up and manage a full DevOps pipeline. I’d pick a template—like for a web app— and it auto-configures CodeCommit, CodeBuild, CodeDeploy, and hosting like EC2 or Lambda. It includes a dashboard to track progress and integrates with tools like Jira. It speeds up my workflow by reducing manual setup and letting me focus on coding.

90. **How do you handle secrets in a CI/CD pipeline?**
    To handle secrets in a CI/CD pipeline, I’d use AWS Secrets Manager or Parameter Store to securely store credentials, API keys, or passwords. In CodePipeline, I’d fetch these secrets using IAM roles with least privilege—no hardcoding. For CodeBuild, I’d reference them in the buildspec.yml via environment variables. I’d enable encryption with KMS and audit access with CloudTrail, ensuring secrets stay safe and aren’t exposed in logs or code.

91. **How would you migrate a 10TB on-premises database to AWS with minimal downtime?**
    To migrate a 10TB on-premises database to AWS with minimal downtime, I’d use a hybrid approach. First, I’d choose a target like Amazon RDS or Aurora. I’d start by transferring the bulk data offline using AWS Snowball—shipping 10TB physically avoids network bottlenecks. Once loaded into AWS, I’d set up AWS Database Migration Service (DMS) to replicate ongoing changes from the on-premises database to the AWS target in real-time. I’d test the setup, validate data consistency, then switch the application to the AWS database during a short maintenance window, keeping downtime to minutes.

92. **Design an architecture for a web application that expects 1 million users daily.**
    For a web app with 1 million daily users, I’d design a scalable, highly available architecture. I’d use an Elastic Load Balancer (ELB) to distribute traffic across multiple EC2 instances in an Auto Scaling group, spanning at least two Availability Zones. The app code would run on EC2, with Amazon RDS Multi-AZ for the database to handle relational data. I’d add Amazon ElastiCache for caching to speed up reads, and Amazon S3 for static assets like images. Route 53 would handle DNS with latency-based routing, and CloudWatch would monitor performance, scaling EC2 as traffic grows.

93. **How would you troubleshoot an EC2 instance that is unreachable?**
    If an EC2 instance is unreachable, I’d start by checking the instance status in the AWS Console—looking for stopped or terminated states. Next, I’d verify the security group rules to ensure port 22 (SSH) or 3389 (RDP) is open from my IP. I’d check the VPC subnet’s route table for an Internet Gateway and confirm the instance has a public IP or Elastic IP. Then, I’d review CloudWatch logs for errors and use the EC2 System Log to spot boot issues. If it’s a hardware failure, I’d stop and restart the instance to move it to new hardware.

94. **A customer reports slow application performance—how do you diagnose and fix it?**
    For slow application performance, I’d first use CloudWatch to check metrics like CPU usage, memory, and latency on EC2 instances or RDS. I’d look at ELB request latency to spot bottlenecks. If the database is slow, I’d check for unoptimized queries using RDS Performance Insights and add indexes or scale up the instance. For app-level issues, I’d enable detailed logging or use X-Ray to trace requests. To fix it, I might scale out with Auto Scaling, add caching with ElastiCache, or offload static content to S3 and CloudFront.

95. **How would you secure an application that uses S3 and RDS?**
    To secure an app using S3 and RDS, I’d start with IAM—creating roles with least privilege for app access to S3 and RDS. For S3, I’d enable bucket encryption with KMS, set a strict bucket policy, and block public access. For RDS, I’d put it in a private subnet, enable encryption at rest and in transit with SSL, and restrict security group access to only the app’s EC2 instances. I’d also turn on CloudTrail for auditing and use VPC endpoints for private S3 access, keeping everything locked down.

96. **What steps would you take if an S3 bucket is accidentally made public?**
    If an S3 bucket is accidentally public, I’d immediately check the bucket permissions in the AWS Console and remove any public access—revoking ACLs or policies allowing “AllUsers” access. I’d enable the “Block Public Access” settings at the bucket or account level. Then, I’d use CloudTrail to audit who made the change and what data was accessed. I’d notify stakeholders, assess any data exposure, and update IAM policies to prevent future mistakes. Finally, I’d review all buckets for similar issues.

97. **How would you handle a sudden traffic spike on your application?**
    For a sudden traffic spike, I’d rely on Auto Scaling to handle it. I’d have an Auto Scaling group set up with EC2 instances behind an ELB, configured to scale out based on CloudWatch metrics like CPU usage or request count. I’d ensure the database—like RDS—can handle the load, maybe by scaling up or adding read replicas. For static content, I’d offload to CloudFront and S3. If it’s serverless, I’d use Lambda, which scales automatically. Post-spike, I’d analyze the cause with logs and adjust capacity.

98. **Design a backup and recovery strategy for an AWS-based application.**
    For backup and recovery, I’d start with EC2—using EBS snapshots for block storage, scheduled via AWS Backup, and AMIs for full instance recovery. For RDS, I’d enable automated backups with a 7-day retention and take manual snapshots before changes. S3 data would use versioning and cross-region replication for redundancy. I’d store critical backups in Glacier for cost savings. For recovery, I’d test restoring snapshots to a new RDS instance or launching EC2 from an AMI, ensuring a recovery point objective (RPO) and time objective (RTO) meet business needs.

99. **How would you reduce costs for an overprovisioned AWS environment?**
    To reduce costs in an overprovisioned AWS environment, I’d use AWS Cost Explorer to identify underused resources. For EC2, I’d right-size instances—say, switching from m5.large to t3.medium—and use Spot Instances or Savings Plans for savings. I’d stop or terminate unused instances and set up Auto Scaling to match demand. For RDS, I’d downgrade to a smaller instance type if metrics show low usage. I’d move infrequently accessed S3 data to Glacier, and enable Trusted Advisor to flag waste, cutting costs without impacting performance.

100. **How do you set up a multi-region architecture for disaster recovery?**
     For a multi-region disaster recovery setup, I’d deploy the app across two regions—like us-east-1 and us-west-2. I’d use Route 53 with failover routing to switch traffic if the primary region fails. EC2 instances would run in Auto Scaling groups in both regions, with AMIs replicated via cross-region copy. For RDS, I’d set up a read replica in the secondary region and promote it if needed. S3 would use cross-region replication, and I’d sync DynamoDB with global tables. CloudFormation would keep infrastructure consistent, and I’d test failover regularly.

101. **Tell me about a time you took ownership of a project.** *(Ownership)*  
In my last role, our team was tasked with migrating a legacy app to AWS, but the project stalled due to unclear responsibilities. I took ownership by volunteering to lead it. I mapped out the app’s architecture, planned the migration using RDS and EC2, and coordinated with the team to set deadlines. I also handled unexpected downtime by troubleshooting a database issue myself. As a result, we completed the migration two weeks early, and the app ran 30% faster on AWS, earning praise from the client.

102. **Describe a situation where you had to dive deep into a technical problem.** *(Dive Deep)*  
Once, our application started crashing under heavy load, and the team couldn’t pinpoint why. I dove deep by analyzing CloudWatch logs and EC2 metrics, noticing CPU spikes tied to a misconfigured Auto Scaling policy. I dug into the app code, found an inefficient query hitting RDS, and worked with the developer to optimize it. After testing, I adjusted the scaling thresholds. The crashes stopped, and performance stabilized, saving us hours of downtime during peak traffic.

103. **How do you handle disagreements with a team member about an AWS solution?** *(Disagree and Commit)*  
In a recent project, a teammate insisted on using EC2 for a small, event-driven app, while I favored Lambda for cost and simplicity. I explained my reasoning—Lambda’s serverless scaling and lower overhead—but he worried about cold starts. We debated, but time was tight, so I committed to his EC2 approach. To make it work, I optimized the setup with Auto Scaling and spot instances. It launched on time, and I later showed him Lambda benchmarks for future projects, keeping us aligned moving forward.

104. **Give an example of a time you delivered a solution faster than expected.** *(Deliver Results)*  
At my previous job, a client needed a reporting dashboard in two weeks, pulling data from S3. I used AWS Glue to crawl the data, set up an Athena query, and built the dashboard in QuickSight—all in five days. I worked late to test it thoroughly and delivered it a week early. The client was thrilled with the speed and started using it immediately, which led to more projects for our team.

105. **How do you ensure customer satisfaction in a cloud project?** *(Customer Obsession)*  
In a cloud migration project, the customer worried about downtime and costs. I started by meeting with them to understand their app’s needs, then designed a solution using RDS Multi-AZ for reliability and tagged resources for cost tracking. I kept them updated with weekly demos and adjusted based on their feedback—like adding CloudWatch alerts for visibility. Post-launch, I followed up to ensure it met their goals. They were happy with zero downtime and a 20% cost reduction, strengthening our relationship.