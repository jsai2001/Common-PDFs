**AWS Internet Monitor**

- **Introduction to AWS Internet Monitor**
  - Applications and services are expected to be available and performing 24/7 in today’s digital world.
  - Internet issues (slow or interrupted connectivity) can negatively impact user experience, leading to dissatisfaction and business losses.
  - Traditional diagnostic methods are time-consuming, often taking days to identify problems, worsening the impact on users and organizations.
  - Without a robust monitoring system, businesses lack visibility into internet performance issues and resolution strategies.
  - **CloudWatch Internet Monitor** is an AWS service that addresses these challenges by:
    - Using AWS’s global networking data to establish a baseline for internet-facing traffic performance and availability.
    - Providing a global view of internet health to identify and resolve issues quickly (minutes instead of days).
    - Offering actionable insights and recommendations to enhance user experience.
    - Integrating seamlessly with other AWS services for monitoring and alerting.

- **Key Features of Internet Monitor**
  - **Real-Time Insights for Better User Experience**
    - Provides a global view of how users experience services across multiple geographic locations.
    - Offers real-time metrics from customer endpoints to AWS infrastructure.
    - Enables proactive issue identification and resolution before escalation, regardless of user location.
  - **Enhancement with AWS CloudWatch**
    - Adds specialized worldwide internet traffic and performance data to existing CloudWatch setups.
    - Delivers a comprehensive health overview of applications globally.
  - **Alerts and Notifications**
    - Provides instant alerts with global context for time-sensitive issues.
    - Identifies whether issues originate on the business side or the customer side, with recommended actions.

- **Summary**
  - Internet Monitor acts as a comprehensive tool to ensure smooth and efficient online services.
  - Functions as a "speedometer" for websites or apps, offering troubleshooting insights.
  - Enhances AWS CloudWatch and serves as an early warning system for issues.

- **Next Steps**
  - The next clip will cover creating the first Internet Monitor instance.

- **Sample Code Snippet for Setting Up Basic CloudWatch Integration**
  This snippet demonstrates how to set up a basic CloudWatch client to interact with Internet Monitor (assuming AWS SDK for Python - Boto3 is used):

```python
import boto3

# Initialize CloudWatch client
cloudwatch = boto3.client('cloudwatch')

# Example: Create a metric alarm (placeholder for Internet Monitor integration)
response = cloudwatch.put_metric_alarm(
    AlarmName='InternetMonitorLatencyAlert',
    MetricName='Latency',
    Namespace='AWS/InternetMonitor',
    Statistic='Average',
    Period=300,
    EvaluationPeriods=1,
    Threshold=200,
    ComparisonOperator='GreaterThanThreshold',
    ActionsEnabled=True,
    AlarmDescription='Alert if latency exceeds 200ms'
)

print("Metric alarm configured:", response)
```

- **Configuring AWS Internet Monitor for Your Environment**
  - Access the CloudWatch console homepage, navigate to application monitoring, and select Internet Monitor.
  - Steps to create a monitor:
    1. Click "Create Monitor."
    2. Provide a monitor name (e.g., "demo2").
    3. Specify resources to monitor (e.g., Amazon VPCs, CloudFront distributions, network load balancers, or Amazon Workspaces directories).
    4. Add resources from the list of monitorable resources (select all if needed).
    5. Set the internet traffic percentage to monitor (e.g., 100% for all traffic if unsure).
    6. Optionally, publish internet measurements to an Amazon S3 bucket (select or create a bucket and add a prefix like "internet_monitor").
    7. Review details and create the monitor.
  - Data population takes time: initial data appears after ~10 minutes, but comprehensive data requires a few hours; no data if no traffic is generated.
  - Pre-existing monitor ("demo") has been running for a day, providing sample data.

- **Generating Traffic for Data**
  - If no traffic exists, set up services to generate data:
    - Run a serverless backend API and use a tool to initiate requests with a large number.
    - Utilize CloudWatch Canaries to call the API and score-keeping application, generating additional traffic.

- **Next Steps**
  - The next clip will cover analyzing the collected data from the Internet Monitor.

- **Sample Code Snippet for Generating API Traffic**
  This snippet demonstrates a simple Python script using `requests` to generate API traffic:

```python
import requests
import time

# Configuration
api_url = "https://your-api-endpoint.com/api"
num_requests = 1000

# Generate traffic
for i in range(num_requests):
    try:
        response = requests.get(api_url)
        print(f"Request {i+1} - Status: {response.status_code}")
    except Exception as e:
        print(f"Request {i+1} failed: {e}")
    time.sleep(0.1)  # Brief delay between requests
```

- **Analyzing and Visualizing AWS Internet Monitor Data**
  - Ensure the Internet Monitor has been running for sufficient time (e.g., 10+ hours) to collect meaningful data; otherwise, data may be limited or unavailable.
  - **Overview Section:**
    - Displays monitored traffic (e.g., 100%), availability score, and performance score (e.g., 100% with low traffic).
    - Status shown as "active."
  - **Traffic Health Scores:**
    - Shows a timeline of health events; no events indicate normal operation (e.g., all green, 100% performance).
    - Actual internet traffic monitoring makes failure simulation difficult, but issues will be visible if they occur.
  - **Internet Traffic Overview:**
    - Map visualizes traffic origins; no health events indicate smooth operation.
    - Identifies regions with bottlenecks or issues if present.
    - Traffic from multiple global locations (e.g., instructor’s local tool calling API) is visible.
  - **Historical Explorer:**
    - Displays traffic trends over time; data depth depends on monitor runtime (e.g., last 12 hours or day with limited data).
    - Includes performance score, availability score, bytes transferred, and round-trip time.
    - Offers filters (AWS location, country, subdivision, metro, city, network providers) for detailed analysis (e.g., filtering by network provider like "Digicom").
  - **Traffic Insights:**
    - Shows total traffic (e.g., 2.5 MB), traffic in/out, and top client locations (e.g., instructor’s location as primary source).
    - Provides traffic optimization suggestions (e.g., moving an EC2 instance from us-east-1 to ca-central for better performance based on traffic location).
  - **Recommendation:**
    - Delete the Internet Monitor when done to avoid incurring charges.

- **Sample Code Snippet for Fetching Internet Monitor Data**
  This snippet uses Boto3 to retrieve Internet Monitor data programmatically:

```python
import boto3
from datetime import datetime, timedelta

# Initialize CloudWatch Internet Monitor client
internet_monitor = boto3.client('internetmonitor')

# Define time range (last 24 hours)
end_time = datetime.utcnow()
start_time = end_time - timedelta(hours=24)

# Fetch monitor data
response = internet_monitor.get_monitor(
    MonitorName='demo2',
    StartTime=start_time,
    EndTime=end_time
)

# Extract and print key metrics
traffic_health = response.get('TrafficHealth', {})
print(f"Availability Score: {traffic_health.get('AvailabilityScore', 'N/A')}%")
print(f"Performance Score: {traffic_health.get('PerformanceScore', 'N/A')}%")
print(f"Bytes Transferred: {traffic_health.get('BytesTransferred', 'N/A')} bytes")

# Filter by network provider (example)
filtered_response = internet_monitor.get_monitor(
    MonitorName='demo2',
    StartTime=start_time,
    EndTime=end_time,
    NetworkProvider='Digicom'
)
print(f"Filtered Performance Score: {filtered_response.get('TrafficHealth', {}).get('PerformanceScore', 'N/A')}%")
```