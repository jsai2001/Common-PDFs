
# CloudWatch Logs Insights Notes

## Overview
- **Purpose**: Use CloudWatch Logs Insights to query logs and diagnose application issues.
- **Difference from Metrics**: Metrics show *how often* something happens; logs reveal *what* happened.
- **Access**: Navigate to CloudWatch Console > Logs > Log Insights.

## Steps to Query Logs
1. **Select Timeframe**:
   - Default: Last 1 hour.
   - Adjust timeframe based on when the error occurred (use "Custom" for non-default options).
2. **Select Log Group**:
   - Each AWS service sends logs to a specific log group (e.g., `CloudWatchcourseAPI` for CodeBuild, Lambda).
   - Example: Choose the log group for the "Put Item Function" (Lambda).
3. **Write and Run Query**:
   - Use the query editor to specify fields, filters, and aggregations.
   - Limit results to reduce query costs.

## Common Query Operations
### 1. Displaying Fields
- **Purpose**: Select specific fields to display in query results.
- **Query Example**:
  ```plaintext
  fields @message
  ```
  - Displays the `@message` field from the logs.
- **Best Practice**: Use `limit` to restrict the number of results (e.g., `limit 100`).
  ```plaintext
  fields @message | limit 100
  ```

### 2. Filtering Logs
- **Purpose**: Narrow down logs to find specific errors (e.g., by error message).
- **Query Example**:
  ```plaintext
  fields @message
  | filter @message like /exception/
  ```
  - Filters logs containing the word "exception" in the `@message` field.
  - Example result: `Validation exception: One or more parameter values were invalid, missing key ID in the payload`.
- **Details**:
  - Use `|` (pipe) to chain operations.
  - Combine with `like` to search for keywords in the message.
  - Expand results to view error details, including stack traces for precise error location.

### 3. Aggregating Data
- **Purpose**: Perform calculations (e.g., sum, average) on log data.
- **Query Example (Count Errors)**:
  ```plaintext
  fields @message
  | filter @message like /exception/
  | stats count(*) as error_count
  ```
  - Counts the total number of logs with "exception" (e.g., `error_count: 66`).
- **Grouping by Time**:
  - Group results into time intervals (e.g., 5-minute bins) for better visualization.
  - **Query Example**:
    ```plaintext
    fields @message
    | filter @message like /exception/
    | stats count(*) as error_count by bin(5m)
    ```
    - Groups error counts by 5-minute intervals, returning timestamps and counts (e.g., 5 rows with timestamps and error counts).

## Practical Tips
- **Generate Test Data**: If no results appear, use local testing tools to initiate requests (successful and failed) to populate logs.
- **Cost Management**: Limit query results to reduce costs, as more results increase query expenses.
- **Error Diagnosis**:
  - Use stack traces in expanded log results to pinpoint error locations.
  - Filter by specific error messages (e.g., "validation exception") to focus on relevant issues.
- **Visualization**: Grouping by time intervals (e.g., `bin(5m)`) enhances visualization of error trends.

## Key Commands
- **Fields**: `fields @message` (select fields to display).
- **Filter**: `filter @message like /keyword/` (filter by keyword).
- **Stats**: `stats count(*) as error_count` (aggregate data).
- **Bin**: `by bin(5m)` (group by time intervals).

## Use Case
- **Scenario**: Application bug causes a "validation exception" due to missing ID parameter.
- **Action**:
  - Query logs with `filter @message like /exception/`.
  - Use stack traces to identify the error source.
  - Aggregate errors over time (`stats count(*) by bin(5m)`) to analyze error frequency.

## Next Steps
- Explore additional Log Insights commands (e.g., `sum`, `avg`, `max`, `min`) for advanced analysis.
- Integrate log queries with dashboards for ongoing monitoring.


# CloudWatch Log Metrics Notes

## Overview
- **Purpose**: Convert application logs into CloudWatch metrics to visualize and analyze error occurrences over time.
- **Benefit**: Metrics provide a quicker, more visual way to track errors compared to querying logs repeatedly.
- **Use Case**: Track specific errors (e.g., `ValidationException`) in an API to understand application performance.

## Steps to Create Log-Based Metrics
1. **Navigate to Log Groups**:
   - In CloudWatch Console, go to Logs > Log Groups.
   - Select the relevant log group (e.g., for a Lambda function like `putItem`).
2. **Verify Log Data**:
   - Check log streams for recent events.
   - If no events exist, use the demo app to generate requests (e.g., enable "Fail all requests" to produce errors).
3. **Create a Metric Filter**:
   - In the selected log group, go to "Metric Filters" > Create Metric Filter.
4. **Define Filter Pattern**:
   - Specify a pattern to match log events (e.g., errors with `ValidationException`).
   - Options:
     - Match terms (e.g., `error`, `warning`).
     - Match JSON properties (e.g., `errorType = ValidationException`).
     - Match status codes (e.g., `statusCode = 500`).
   - **Example Pattern**:
     ```plaintext
     { $.errorType = "ValidationException" }
     ```
     - Matches logs where `errorType` is `ValidationException`.
5. **Test the Pattern**:
   - Copy a sample log entry containing the error (e.g., from log streams).
   - Paste into the "Test Pattern" section, replacing demo data.
   - Verify at least one row matches the pattern.
6. **Configure Metric Details**:
   - **Filter Name**: e.g., `ValidationExceptions`.
   - **Namespace**: Create or select a namespace (e.g., `MetricFilters`).
   - **Metric Name**: e.g., `ValidationExceptions`.
   - **Metric Value**: Specify the value to increment when the filter matches (default: `1`).
     - Use `1` for counting occurrences.
     - Other values possible based on metric type.
   - **Default Value**: Optional (e.g., `0`); if unset, no value is pushed when no match occurs (cost-efficient).
   - **Unit**: Optional (e.g., `Seconds`); leave unset if not applicable.
   - **Dimensions**: Optional; leave as default for simple metrics.
7. **Review and Create**:
   - Review settings and create the metric filter.
8. **Generate and Verify Metric Data**:
   - Use the demo app to generate requests (e.g., 100 requests with errors).
   - Navigate to CloudWatch > Metrics > Namespace (e.g., `MetricFilters`) to view the metric (e.g., `ValidationExceptions`).

## Example Filter Pattern
```plaintext
{ $.errorType = "ValidationException" }
```
- **Explanation**:
  - Matches JSON log events where the `errorType` property equals `ValidationException`.
  - Used to count occurrences of validation errors in the `putItem` Lambda function.

## Key Points
- **Log Groups**: Each AWS service sends logs to a specific log group (e.g., Lambda functions have dedicated groups).
- **Filter Logic**: The filter acts like an `if` statement, incrementing the metric value when a log matches the pattern.
- **Testing**: Always test the filter pattern with real or copied log data to ensure accuracy.
- **Metric Value**: Default to `1` for counting errors; adjust for other use cases (e.g., duration in seconds).
- **Cost Efficiency**: Avoid setting a default value unless necessary to reduce costs.
- **Visualization**: Use the created metric in graphs or dashboards to monitor application performance.

## Practical Tips
- **Error Generation**: Use the demo app’s "Fail all requests" option to generate errors for testing.
- **Naming**: Use meaningful names for filters and metrics in production (e.g., `APIValidationErrors` instead of `ValidationExceptions`).
- **Namespace Organization**: Group related metrics in a custom namespace for easier management.
- **Metric Integration**: Combine log-based metrics with other CloudWatch metrics for comprehensive analysis.

## Next Steps
- Add the metric to a CloudWatch dashboard for persistent monitoring.
- Explore advanced filter patterns (e.g., matching multiple error types or status codes).
- Use metrics in alarms to trigger notifications for high error rates.


# CloudWatch Agent Notes

## Overview
- **Purpose**: Collect logs and system-level metrics from Amazon EC2 instances and on-premise servers.
- **Use Cases**:
  - Monitor cloud-based virtual machines (e.g., EC2 instances).
  - Monitor on-premise servers in hybrid or non-AWS environments.

## Key Features
1. **System-Level Metrics Collection**:
   - Collects detailed system metrics from EC2 instances and on-premise servers.
   - Supports multiple operating systems (Linux and Windows Server).
2. **Custom Metrics**:
   - Retrieves custom metrics from applications/services using:
     - **StatsD**: Supported on Linux and Windows Server.
     - **CollectD**: Supported only on Linux servers.
3. **Log Collection**:
   - Collects logs from EC2 instances and on-premise servers (Linux or Windows Server).

## Supported Environments
- **Amazon EC2 Instances**: Collect logs and metrics from cloud-based virtual machines.
- **On-Premise Servers**: Collect logs and metrics from servers in hybrid or non-AWS managed environments.
- **Operating Systems**:
  - Linux: Supports StatsD and CollectD.
  - Windows Server: Supports StatsD only.

## Implementation
- **Installation and Configuration**:
  - Install the CloudWatch agent on both Windows and Linux machines.
  - Configure the agent to collect desired metrics and logs.
- **Steps** (general outline, detailed configuration not provided in transcript):
  1. Install the CloudWatch agent on the target server (EC2 or on-premise).
  2. Configure the agent to use StatsD or CollectD for custom metrics (based on OS).
  3. Specify log sources for collection.
  4. Send collected data to CloudWatch for monitoring and analysis.

## Key Points
- **Flexibility**: Supports both cloud and on-premise environments, enabling hybrid monitoring.
- **Protocol Limitations**:
  - Use StatsD for cross-platform compatibility (Linux and Windows).
  - Use CollectD for Linux-specific environments requiring advanced metric collection.
- **Use Case**: Ideal for monitoring system performance and application logs in diverse infrastructures.

## Next Steps
- Review detailed installation guides for the CloudWatch agent on AWS documentation.
- Configure StatsD or CollectD for custom metric collection based on server OS.
- Integrate collected metrics and logs into CloudWatch dashboards for visualization.


# Setting Up CloudWatch Agent on Linux Notes

## Overview
- **Purpose**: Install and configure the AWS CloudWatch Agent on a Linux machine to collect system metrics and logs and send them to CloudWatch.
- **Environment**: Amazon EC2 instance running Linux, accessible via SSH.

## Prerequisites
1. **Linux Machine**:
   - Ensure a Linux EC2 instance is running and accessible via SSH.
2. **IAM Role**:
   - Create an IAM role (e.g., `AgentDemo`) with necessary permissions:
     - `CloudWatchFullAccess`: To send data to CloudWatch.
     - `EC2FullAccess`: To retrieve instance metadata.
     - `AmazonSSMFullAccess`: To store configuration in SSM Parameter Store.
   - **Note**: In production, use fine-grained permissions instead of full access.
   - Attach the role to the EC2 instance:
     - Navigate to EC2 > Instances > Select instance > Actions > Security > Modify IAM Role > Choose `AgentDemo` > Update.

## Installation and Configuration Steps
1. **Connect to the EC2 Instance**:
   - Use EC2 Instance Connect or SSH to access the Linux machine.
2. **Install the CloudWatch Agent**:
   - Run the installation command (available in exercise files):
     ```bash
     sudo yum install amazon-cloudwatch-agent
     ```
     - Confirm installation by selecting `yes`.
3. **Run the Configuration Wizard**:
   - Start the wizard:
     ```bash
     sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-config-wizard
     ```
   - **Wizard Options**:
     - **OS**: Select `Linux`.
     - **Environment**: Confirm instance is on EC2.
     - **User**: Use default user.
     - **StatsD**:
       - Enable StatsD daemon: `yes`.
       - Use default port.
       - Set collect interval: e.g., `10 seconds` or `60 seconds` (adjust based on use case).
     - **CollectD**:
       - Enable CollectD: `yes` (requires separate installation).
     - **Host Metrics**: Monitor host and CPU metrics: `yes`.
     - **EC2 Dimensions**:
       - Add EC2 dimensions: `yes`.
       - Aggregate by instance ID: `yes`.
     - **Metric Resolution**:
       - Choose normal (60 seconds, default) or high resolution (more expensive).
       - Use default for cost efficiency when starting.
     - **Metric Configuration**: Use default configuration.
     - **Log Monitoring**:
       - Monitor log files: `no` (if no local app logs are generated).
     - **SSM Parameter Store**:
       - Store configuration in SSM: `yes`.
       - Parameter name: `AmazonCloudWatch-linux` (default).
       - Region: Confirm or set correct region (e.g., `us-east-1`).
       - Use SDK to send configuration: Select first option.
4. **Install CollectD** (if enabled):
   - Install CollectD for Linux-specific metric collection:
     ```bash
     sudo yum install collectd
     ```
     - Confirm installation by selecting `yes`.
5. **Start the CloudWatch Agent**:
   - Start the agent with the SSM configuration:
     ```bash
     sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -c ssm:AmazonCloudWatch-linux -s
     ```
     - Replace `AmazonCloudWatch-linux` with custom parameter name if changed.
6. **Verify Agent Status**:
   - Check if the agent is running:
     ```bash
     sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a status
     ```
     - Expected output: Agent is configured and running.
7. **Verify Metrics in CloudWatch**:
   - Navigate to CloudWatch > Metrics > Custom Namespaces > `CWAgent`.
   - View collected metrics (e.g., CPU, host metrics) after a few minutes.

## Key Points
- **IAM Role**: Ensure the EC2 instance has a role with sufficient permissions for CloudWatch, EC2, and SSM.
- **StatsD and CollectD**:
  - StatsD: Enabled for custom metrics, configurable interval (e.g., 10 or 60 seconds).
  - CollectD: Linux-only, requires separate installation.
- **Metric Resolution**:
  - Normal (60 seconds): Cost-effective, default choice.
  - High resolution: More granular but more expensive.
- **Log Monitoring**: Optional; specify log file paths if monitoring local app logs.
- **SSM Parameter Store**:
  - Stores agent configuration (e.g., `AmazonCloudWatch-linux`).
  - Verify in AWS Systems Manager > Parameter Store.
- **Cost Consideration**: Use normal resolution and avoid unnecessary log collection to minimize costs.

## Practical Tips
- **Exercise Files**: Use provided commands for installation and configuration.
- **Region Check**: Ensure the correct AWS region is set in the configuration wizard.
- **Production**: Fine-tune IAM permissions and collection intervals based on use case.
- **Troubleshooting**:
  - If no metrics appear, wait a few minutes or verify agent status.
  - Ensure the IAM role is correctly attached and the configuration is stored in SSM.

## Next Steps
- Monitor collected metrics in CloudWatch dashboards.
- Configure log collection for applications generating local logs.
- Explore advanced StatsD/CollectD configurations for custom metrics.

# Setting Up CloudWatch Agent on Windows Notes

## Overview
- **Purpose**: Install and configure the AWS CloudWatch Agent on a Windows Server EC2 instance to collect system metrics, logs, and Windows event logs, and send them to CloudWatch.
- **Environment**: Windows Server EC2 instance, accessible via Remote Desktop Protocol (RDP).

## Prerequisites
1. **Windows EC2 Instance**:
   - Launch a Windows Server instance in EC2.
   - Ensure connectivity via RDP (download RDP file, provide password or retrieve using key pair).
2. **IAM Role**:
   - Create an IAM role (e.g., `agentdemo`) with the following permissions:
     - `CloudWatchFullAccess`: To send data to CloudWatch.
     - `AmazonEC2FullAccess`: To retrieve instance metadata for EC2 dimensions.
     - `AmazonSSMFullAccess`: To store configuration in SSM Parameter Store.
   - **Note**: Use fine-grained permissions in production instead of full access.
   - Attach the role to the EC2 instance:
     - Navigate to EC2 > Instances > Select instance > Actions > Security > Modify IAM Role > Select `agentdemo` > Update IAM Role.

## Installation and Configuration Steps
1. **Connect to the Windows EC2 Instance**:
   - Use RDP client: Download the RDP file from EC2 > Instances > Connect > RDP Client.
   - Log in with the instance password (retrieve via "Get Password" using the key pair if needed).
2. **Download the CloudWatch Agent**:
   - Run the following PowerShell command to download the agent installer to the user’s Downloads folder:
     ```powershell
     Invoke-WebRequest -Uri https://s3.amazonaws.com/amazoncloudwatch-agent/windows/amd64/latest/amazon-cloudwatch-agent.msi -OutFile $env:USERPROFILE\Downloads\amazon-cloudwatch-agent.msi
     ```
3. **Install the CloudWatch Agent**:
   - Install the agent using the downloaded MSI file:
     ```powershell
     msiexec /i $env:USERPROFILE\Downloads\amazon-cloudwatch-agent.msi
     ```
4. **Run the Configuration Wizard**:
   - Start the wizard to configure the agent:
     ```powershell
     & "C:\Program Files\Amazon\AmazonCloudWatchAgent\amazon-cloudwatch-agent-config-wizard.exe"
     ```
   - **Wizard Options**:
     - **Operating System**: Auto-detected as Windows.
     - **Environment**: Confirm instance is on EC2.
     - **StatsD Daemon**:
       - Enable StatsD: `yes`.
       - Use default port.
       - Aggregation interval: `60 seconds` (default).
     - **Existing Log Agent**: No migration needed (select `no`).
     - **Host Metrics**: Collect host and CPU metrics per core: `yes`.
     - **EC2 Dimensions**:
       - Add EC2 dimensions: `yes` (requires `AmazonEC2FullAccess` permission).
       - Aggregate by instance ID: `yes`.
     - **Metric Resolution**:
       - Choose standard metrics (60 seconds, default) over high-resolution (more expensive).
     - **Configuration Level**: Use basic configuration (default).
     - **Windows Event Logs**:
       - Monitor event logs: `yes`.
       - Log group name: `system`.
       - Log stream name: Use instance ID.
       - Log levels: Information, Warning, Error, Critical (verbose for demo; adjust for production).
       - Log format: XML.
       - Retention: Use default retention period.
     - **Additional Logs**: No additional log files for demo (select `no`).
     - **SSM Parameter Store**:
       - Store configuration: `yes`.
       - Parameter name: `AmazonCloudWatch-windows`.
       - Region: Verify auto-detected region (e.g., `us-east-1`).
       - Credentials: Use SDK (leverages instance role).
5. **Verify Configuration in SSM**:
   - Navigate to AWS Systems Manager > Parameter Store.
   - Check for the `AmazonCloudWatch-windows` parameter containing the agent configuration.
   - If missing, troubleshoot the wizard setup or IAM permissions.
6. **Start the CloudWatch Agent**:
   - Start the agent using the SSM configuration:
     ```powershell
     & "C:\Program Files\Amazon\AmazonCloudWatchAgent\amazon-cloudwatch-agent-ctl.ps1" -a fetch-config -m ec2 -c ssm:AmazonCloudWatch-windows -s
     ```
7. **Verify Agent Status**:
   - Confirm the agent is running:
     ```powershell
     Get-Service -Name AmazonCloudWatchAgent
     ```
     - Expected output: Service status is `Running`.
8. **Verify Data in CloudWatch**:
   - Navigate to CloudWatch > Metrics > Custom Namespaces > `CWAgent`.
   - Check for metrics (e.g., CPU, host metrics) and logs (e.g., Windows event logs in the `system` log group) after a few minutes.

## Key Points
- **IAM Role**: Essential for CloudWatch, EC2, and SSM access; ensure proper permissions.
- **StatsD**: Enabled for custom metrics; CollectD is not supported on Windows.
- **Metric Resolution**:
  - Standard (60 seconds): Cost-effective, default choice.
  - High-resolution: Avoid unless necessary due to higher costs.
- **Windows Event Logs**:
  - Configurable to monitor system logs (Information, Warning, Error, Critical).
  - Stored in CloudWatch under the specified log group (e.g., `system`).
- **SSM Parameter Store**:
  - Stores configuration as `AmazonCloudWatch-windows`.
  - Verify region and credentials match the instance setup.
- **Cost Consideration**: Use standard metrics and limit log levels to reduce costs in production.

## Practical Tips
- **Exercise Files**: Use provided commands for downloading, installing, and configuring the agent.
- **RDP Access**: Ensure the correct password is used; retrieve via EC2 console if needed.
- **Production**:
  - Use fine-grained IAM permissions.
  - Limit event log levels to avoid excessive data collection.
- **Troubleshooting**:
  - If no configuration appears in SSM, verify IAM role permissions and region.
  - If no metrics/logs appear in CloudWatch, check agent status and wait a few minutes.

## Next Steps
- Monitor collected metrics and logs in CloudWatch dashboards.
- Customize log collection for application-specific logs if needed.
- Explore StatsD for advanced custom metric collection on Windows.