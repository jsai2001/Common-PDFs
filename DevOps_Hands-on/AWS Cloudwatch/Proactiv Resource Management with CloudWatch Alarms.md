# CloudWatch Alarms Notes

## Overview
- **Purpose**: Automate monitoring and notification processes in AWS CloudWatch, reducing manual checks.
- **Functionality**: Set thresholds on metrics to trigger actions or notifications when issues arise.

## Key Features
- **Metric Monitoring**:
  - Alarms can be set on any CloudWatch metric to monitor various aspects of the cloud environment.
  - Example: Monitor API failure requests and trigger notifications when a threshold is exceeded.
- **Proactive Actions**:
  - Beyond notifications, alarms can execute actions like shutting down unused resources.
  - Example: Scale EC2 Auto Scaling group based on CPU usage thresholds.
    - **Scale Up**: Add instances when CPU usage exceeds a threshold.
    - **Scale Down**: Remove instances when CPU usage falls below a threshold.
- **Benefits**:
  - Ensures swift response to issues.
  - Optimizes performance and cost by scaling resources dynamically.
  - Eliminates the need for periodic manual checks of the CloudWatch console.

## Best Practices
- Always use alarms to stay informed about application performance continuously.
- Configure alarms to monitor critical metrics and take proactive actions for resource optimization.

## Example Code Snippet
Below is an example of creating a CloudWatch alarm for CPU utilization of an EC2 Auto Scaling group using AWS SDK for Python (Boto3):

```python
import boto3

cloudwatch = boto3.client('cloudwatch')

# Create an alarm for high CPU utilization
cloudwatch.put_metric_alarm(
    AlarmName='HighCPUUtilization',
    ComparisonOperator='GreaterThanThreshold',
    EvaluationPeriods=2,
    MetricName='CPUUtilization',
    Namespace='AWS/EC2',
    Period=300,
    Statistic='Average',
    Threshold=70.0,
    ActionsEnabled=True,
    AlarmActions=['arn:aws:sns:us-east-1:123456789012:MyTopic'],  # SNS topic ARN
    AlarmDescription='Alarm when CPU exceeds 70% for 10 minutes',
    Dimensions=[
        {
            'Name': 'AutoScalingGroupName',
            'Value': 'my-auto-scaling-group'
        },
    ]
)
```

## Notes
- **Thresholds**: Define specific metric thresholds to trigger alarms (e.g., CPU > 70%).
- **Actions**: Common actions include sending notifications via SNS or triggering Auto Scaling policies.
- **Scalability**: Alarms help balance performance and cost by scaling resources based on demand.


# Creating CloudWatch Alarms Notes

## Overview
- **Purpose**: Create a CloudWatch alarm to monitor API performance and notify administrators of excessive failed requests.
- **Scenario**: Monitor a Lambda function for failed API requests and trigger notifications when failures exceed a threshold.

## Steps to Create a CloudWatch Alarm
1. **Initiate Baseline Requests**:
   - Generate API requests (e.g., 100 requests, with one failing every three requests) to establish a baseline for monitoring.
2. **Navigate to CloudWatch Console**:
   - Go to **Alarms** > **All alarms** > **Create alarm**.
3. **Select Metric**:
   - Find the metric under **AWS Namespaces** > **Lambda** > **By Function Name**.
   - Choose the target function (e.g., `putitemFunction`) and select the **Errors** metric to track failed requests.
4. **Configure Metric Settings**:
   - Change the statistic from **Average** to **Sum** for accurate error counting.
   - Set the evaluation period (e.g., 1 minute) for faster alarm triggering.
   - Example: Baseline shows ~33 failed requests per minute.
5. **Set Conditions**:
   - Choose **Static** threshold type (over **Anomaly Detection**).
   - Configure condition: Trigger alarm if errors > 20 in a 1-minute period.
   - Rationale: API makes ~1 request per second, with ~20 failures per minute based on the failure rate.
6. **Configure Notifications**:
   - Create a new SNS topic (e.g., `apiadmins`) and add email addresses for notifications (separate multiple emails with commas).
   - Set notifications for both **Alarm** state (issue detected) and **OK** state (issue resolved).
7. **Name and Create Alarm**:
   - Name the alarm (e.g., `apiadmins`) and create it.
   - Initial state: **Insufficient Data** until enough data is collected.
8. **Test the Alarm**:
   - Modify API requests to fail all requests (e.g., check "Fail all requests" in the API app).
   - Monitor state changes:
     - Transitions to **OK** state once data is collected.
     - Transitions to **Alarm** state when errors exceed the threshold (e.g., >20 errors/minute).
   - Notifications are sent for state changes (Insufficient Data → OK → Alarm).
9. **Verify and Respond**:
   - Check CloudWatch console to confirm the alarm state.
   - Administrators receive notifications to investigate and fix issues.
   - When resolved, the alarm returns to **OK** state, notifying others to avoid redundant actions.

## Key Observations
- **Alarm States**:
  - **Insufficient Data**: Initial state until enough data is collected.
  - **OK**: Normal operation, errors below threshold.
  - **Alarm**: Errors exceed threshold, triggering notifications.
- **Notification Workflow**:
  - Ensures all administrators are informed of issues and resolutions, preventing duplicate efforts.

## Example Code Snippet
Below is an example of creating a CloudWatch alarm for Lambda function errors using AWS SDK for Python (Boto3):

```python
import boto3

cloudwatch = boto3.client('cloudwatch')

# Create a CloudWatch alarm for Lambda function errors
cloudwatch.put_metric_alarm(
    AlarmName='apiadmins',
    ComparisonOperator='GreaterThanThreshold',
    EvaluationPeriods=1,
    MetricName='Errors',
    Namespace='AWS/Lambda',
    Period=60,  # 1 minute
    Statistic='Sum',
    Threshold=20.0,  # Trigger if errors > 20 in 1 minute
    ActionsEnabled=True,
    AlarmActions=['arn:aws:sns:us-east-1:123456789012:apiadmins'],  # SNS topic ARN
    OKActions=['arn:aws:sns:us-east-1:123456789012:apiadmins'],  # Notify when OK
    AlarmDescription='Alarm when Lambda function errors exceed 20 per minute',
    Dimensions=[
        {
            'Name': 'FunctionName',
            'Value': 'putitemFunction'
        },
    ]
)
```

## Best Practices
- Use **Sum** statistic for counting errors instead of **Average** for precise monitoring.
- Set short evaluation periods (e.g., 1 minute) for rapid issue detection.
- Configure notifications for both **Alarm** and **OK** states to keep all stakeholders informed.
- Test alarms by simulating failures to ensure correct configuration and notification delivery.


# Configuring Actions for CloudWatch Alarms Notes

## Overview
- **Purpose**: Configure CloudWatch alarms to automatically scale an EC2 Auto Scaling group based on CPU usage.
- **Objective**: Add instances when CPU usage exceeds a threshold (scale up) and remove instances when it falls below (scale down).

## Steps to Configure CloudWatch Alarms for Auto Scaling

1. **Create an Auto Scaling Group**:
   - Navigate to **EC2 Dashboard** > **Auto Scaling Groups** > **Create Auto Scaling Group**.
   - Name the group (e.g., `scalingdemo`).
   - Select or create a **Launch Template**:
     - Name: `simplegroup`.
     - OS: Amazon Linux (Quick Start).
     - Instance Type: Leave blank or specify (e.g., t2.micro).
     - Key Pair: Select or create one for SSH access.
     - Network: Use default VPC and subnet, enable auto-assign public IP.
     - Create the template with default settings.
   - Configure Auto Scaling Group:
     - Select the created template (`simplegroup`).
     - Choose default VPC and subnet.
     - Set capacity: Minimum 1, Maximum 2, Desired 1.
     - Create the group.

2. **Create Scaling Policies**:
   - In the Auto Scaling group, go to **Automatic Scaling** > **Create Dynamic Scaling Policy**.
   - **Scale Up Policy**:
     - Policy Type: Simple Scaling.
     - Name: `ScaleUp`.
     - Action: Add 1 capacity unit (instance).
     - Cooldown: 300 seconds (default).
   - **Scale Down Policy**:
     - Policy Type: Simple Scaling.
     - Name: `ScaleDown`.
     - Action: Remove 1 capacity unit.
     - Cooldown: 300 seconds (default).
   - Create both policies.

3. **Create CloudWatch Alarm**:
   - Navigate to **CloudWatch Console** > **Alarms** > **Create Alarm**.
   - Select Metric:
     - Under **AWS Namespaces** > **EC2** > **By Auto Scaling Group** > Select `scalingdemo`.
     - Choose metric: `CPUUtilization`.
     - Statistic: Average, Period: 1 minute.
   - Set Conditions:
     - Threshold Type: Static.
     - Condition: Greater than 70% CPU usage.
   - Configure Actions:
     - **Alarm State**: Select Auto Scaling group (`scalingdemo`), Action: `ScaleUp`.
     - **OK State**: Select Auto Scaling group (`scalingdemo`), Action: `ScaleDown`.
     - Optional: Add notification (e.g., use existing SNS topic `apiadmins` or skip).
   - Name the alarm: `ScaleUpDown`.
   - Create the alarm.

4. **Test the Alarm**:
   - Update Auto Scaling group maximum capacity to 2 (default was 1).
   - Connect to an EC2 instance in the group via **EC2 Instance Connect**.
   - Install `stress-ng` to simulate CPU usage:
     ```bash
     sudo yum install -y stress-ng
     stress-ng --cpu 4 --timeout 60s
     ```
   - Monitor CloudWatch console:
     - CPU usage increases (e.g., from ~37% to above 70%).
     - Alarm transitions to **In Alarm** state, triggering `ScaleUp` (adds 1 instance).
   - After ~60 seconds, CPU usage drops, alarm returns to **OK** state, triggering `ScaleDown` (removes 1 instance).
   - Verify in **Auto Scaling Group** > **Instance Management**: Instance count increases to 2, then decreases to 1.

## Key Observations
- **Alarm States**:
  - **In Alarm**: CPU usage > 70%, triggers `ScaleUp`.
  - **OK**: CPU usage drops, triggers `ScaleDown`.
- **Scaling Behavior**:
  - Adds/removes instances based on demand, optimizing performance and cost.
  - Cooldown period (300s) prevents rapid scaling actions.
- **Testing**:
  - Use tools like `stress-ng` to simulate load.
  - Adjust parameters if CPU usage doesn’t trigger the alarm as expected.

## Example Code Snippet
Below is an example of creating a CloudWatch alarm with Auto Scaling actions using AWS SDK for Python (Boto3):

```python
import boto3

cloudwatch = boto3.client('cloudwatch')

# Create a CloudWatch alarm for EC2 Auto Scaling
cloudwatch.put_metric_alarm(
    AlarmName='ScaleUpDown',
    ComparisonOperator='GreaterThanThreshold',
    EvaluationPeriods=1,
    MetricName='CPUUtilization',
    Namespace='AWS/EC2',
    Period=60,  # 1 minute
    Statistic='Average',
    Threshold=70.0,  # Trigger if CPU > 70%
    ActionsEnabled=True,
    AlarmActions=['arn:aws:autoscaling:us-east-1:123456789012:scalingPolicy:policy-id:scale-up'],  # ScaleUp policy ARN
    OKActions=['arn:aws:autoscaling:us-east-1:123456789012:scalingPolicy:policy-id:scale-down'],  # ScaleDown policy ARN
    AlarmDescription='Alarm to scale EC2 Auto Scaling group based on CPU usage',
    Dimensions=[
        {
            'Name': 'AutoScalingGroupName',
            'Value': 'scalingdemo'
        },
    ]
)
```

## Best Practices
- Ensure the Auto Scaling group’s maximum capacity allows scaling (e.g., >1).
- Use short evaluation periods (e.g., 1 minute) for rapid response.
- Set appropriate cooldown periods to avoid excessive scaling actions.
- Test scaling policies under simulated load to verify functionality.
- Optionally add notifications to monitor scaling events.
