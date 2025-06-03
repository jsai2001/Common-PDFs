
# AWS CloudWatch Overview and Environment Setup Notes

## What is CloudWatch?
- **Purpose**: A monitoring and observability service for AWS resources, applications, and services.
- **Target Audience**: DevOps engineers, developers, IT managers, security specialists.
- **Key Features**:
  - **Comprehensive Monitoring**:
    - Collects logs, metrics, and events for a unified view of AWS resources.
    - Example: Track CPU usage, memory, and network metrics for EC2 instances.
  - **Infrastructure Monitoring and Troubleshooting**:
    - Tracks key metrics and visualizes infrastructure performance.
    - Links metrics with logs for deep insights into performance issues.
    - Integrates with AWS X-Ray for end-to-end visibility across the application stack.
      ```python
      # Example: Enable AWS X-Ray for a Lambda function
      from aws_xray_sdk.core import xray_recorder
      from aws_xray_sdk.ext.flask import XRay

      @xray_recorder.capture('my_endpoint')
      def my_function():
          # Application logic here
          pass
      ```
  - **Proactive Resource Optimization**:
    - Smart alarms monitor metrics against thresholds using machine learning to detect anomalies.
    - Automatically triggers actions, e.g., scaling virtual machines based on CPU usage.
      ```python
      # Example: Create a CloudWatch alarm for auto-scaling
      import boto3

      cloudwatch = boto3.client('cloudwatch')
      cloudwatch.put_metric_alarm(
          AlarmName='HighCPUUtilization',
          MetricName='CPUUtilization',
          Namespace='AWS/EC2',
          Threshold=80.0,
          ComparisonOperator='GreaterThanThreshold',
          Period=300,
          EvaluationPeriods=2,
          Statistic='Average',
          AlarmActions=['arn:aws:autoscaling:region:account-id:scalingPolicy:policy-id']
      )
      ```
  - **Log Analytics**:
    - Real-time log analysis and visualization.
    - Purpose-built query language to navigate logs and pinpoint root causes.
      ```python
      # Example: Query CloudWatch Logs Insights
      # Query syntax for CloudWatch Logs Insights
      fields @timestamp, @message
      | filter @message like /ERROR/
      | sort @timestamp desc
      | limit 100
      ```

- **Benefits**:
  - Reduces time to resolution with quick log visualization.
  - Enhances application performance through proactive monitoring.
  - Provides a consolidated view for seamless operations and consistent improvements.

## Preparing Your Environment
- **Objective**: Set up AWS services to generate logs and metrics for CloudWatch monitoring.
- **Steps to Create a Serverless API**:
  1. **Access AWS Lambda**:
     - In the AWS Console, search for "Lambda" and navigate to "Applications" in the left navigation bar.
  2. **Create a New Application**:
     - Click "Create application" and select "Serverless API backend".
     - Components created: API Gateway, DynamoDB, Lambda Functions.
     - Name the application, e.g., `cloudwatchcourseapi`.
     - Choose runtime: Node.js.
     - Select source control: Use AWS CodeCommit (default repository name: `cloudwatchcourseapi`).
     - Enable permissions: Check "Create roles and permissions boundary".
     - Click "Create" to deploy the application.
  3. **Generate Logs**:
     - Copy the API endpoint from the AWS Console.
     - Use a sample application to make requests to the API and generate logs.
       ```bash
       # Install dependencies for the sample application
       npm install
       ```
       ```bash
       # Run the sample application
       node server.js
       ```
  4. **Configure the Sample Application**:
     - Open the sample application in Visual Studio Code:
       ```bash
       # Open VS Code in the current directory
       code .
       ```
     - Update the `.env` file with the API endpoint:
       ```env
       API_ENDPOINT="your-api-endpoint-from-aws"
       ```
     - Run the application to simulate requests (default: 2 successful, 1 failed per 3 requests).
     - Optional: Configure all requests to fail for error logging.
  5. **Generate Metrics**:
     - Run the application with a high number of requests (e.g., 1000 or 10,000) to generate sufficient metrics.
     - Example: Set request count and click "Initiate Requests" in the demo app UI.

- **Next Steps**:
  - Leave the application running for a few minutes to generate logs and metrics.
  - Access the CloudWatch console to view and analyze the generated metrics.

## Additional Notes
- Ensure the API endpoint is correctly configured in the `.env` file to avoid missing logs.
- Use CloudWatch Logs Insights to query and analyze logs for troubleshooting.
- Monitor the generated metrics in the next session to explore CloudWatch dashboards and alarms.


# AWS CloudWatch Metrics and Custom Metrics Notes

## Exploring CloudWatch Metrics
- **Definition**: Metrics are time-ordered data points pushed to CloudWatch, representing the values of a monitored variable over time.
  - Example: API request latency or Lambda function invocations.
- **Accessing Metrics**:
  - Navigate to AWS CloudWatch Console via the search bar.
  - Go to **Metrics** > **All metrics** in the left navigation.
  - Select a **namespace** (e.g., AWS/ApiGateway, AWS/Lambda) to view metrics.
- **Namespaces**:
  - **AWS Namespaces**: Metrics from AWS services like API Gateway, Lambda, DynamoDB.
  - **Custom Namespaces**: User-defined metrics for specific applications.
- **Example: Viewing API Gateway Metrics**:
  - Select **ApiGateway** namespace > **By Api Name** > `cloudwatchcourseapi`.
  - Choose metrics (e.g., Latency) and view in **Graphed metrics**.
  - Adjust granularity (e.g., from 5-minute to 1-second periods for finer detail).
    ```python
    # Example: Python code to monitor API Gateway latency (conceptual)
    import boto3

    cloudwatch = boto3.client('cloudwatch')
    response = cloudwatch.get_metric_data(
        MetricDataQueries=[
            {
                'Id': 'm1',
                'MetricStat': {
                    'Metric': {
                        'Namespace': 'AWS/ApiGateway',
                        'MetricName': 'Latency',
                        'Dimensions': [
                            {'Name': 'ApiName', 'Value': 'cloudwatchcourseapi'}
                        ]
                    },
                    'Period': 1,  # 1-second granularity
                    'Stat': 'Average'
                }
            }
        ],
        StartTime=datetime.utcnow() - timedelta(minutes=60),
        EndTime=datetime.utcnow()
    )
    ```
- **Example: Viewing Lambda Metrics**:
  - Select **Lambda** namespace > **By Function Name** > `putItemFunction`.
  - Monitor metrics like **Invocations** and **Errors**.
  - Adjust aggregation (e.g., from Average to Sum) to see total errors over a period.
    ```python
    # Example: Query Lambda function errors
    import boto3
    from datetime import datetime, timedelta

    cloudwatch = boto3.client('cloudwatch')
    response = cloudwatch.get_metric_statistics(
        Namespace='AWS/Lambda',
        MetricName='Errors',
        Dimensions=[{'Name': 'FunctionName', 'Value': 'putItemFunction'}],
        StartTime=datetime.utcnow() - timedelta(minutes=60),
        EndTime=datetime.utcnow(),
        Period=300,  # 5-minute period
        Statistics=['Sum']
    )
    print(response['Datapoints'])
    ```
- **Visualization**:
  - Metrics are displayed in graphs with adjustable time periods (e.g., 5 minutes, 1 second).
  - Clear previous metrics in **Graphed metrics** to avoid appending old data.
- **Key Insight**: Metrics are straightforward to navigate; select the right namespace and metric to visualize performance.

## Working with Custom Metrics
- **Purpose**: Built-in metrics provide a foundation, but custom metrics capture unique business-specific data points.
- **Why Custom Metrics?**:
  - Address specific business objectives not covered by AWS default metrics.
  - Enable consolidated visualization of built-in and custom metrics in a single dashboard.
- **Publishing Custom Metrics**:
  - **Methods**:
    - AWS CLI
    - CloudWatch API
  - **Example: Publishing a custom metric via AWS SDK**:
    ```python
    # Example: Push a custom metric to CloudWatch
    import boto3
    from datetime import datetime

    cloudwatch = boto3.client('cloudwatch')
    cloudwatch.put_metric_data(
        Namespace='MyApp/CustomMetrics',
        MetricData=[
            {
                'MetricName': 'OrderProcessingTime',
                'Value': 150.0,
                'Unit': 'Milliseconds',
                'Timestamp': datetime.utcnow(),
                'StorageResolution': 1  # High-resolution (1-second granularity)
            }
        ]
    )
    ```
- **Resolution Types**:
  - **Standard Resolution**: 1-minute granularity (default for AWS service metrics).
  - **High Resolution**: 1-second granularity, ideal for time-sensitive metrics.
    - Specify `StorageResolution: 1` when publishing high-resolution metrics.
- **Benefits**:
  - Tailor monitoring to specific business needs.
  - Gain actionable insights for a competitive edge.
  - Analyze custom metrics in the AWS Management Console alongside built-in metrics.
- **Next Steps**:
  - Explore pushing custom metrics to CloudWatch in the next session.
  - Create dashboards to visualize both built-in and custom metrics for comprehensive monitoring.

## Additional Notes
- Ensure the application (e.g., `cloudwatchcourseapi`) runs long enough to generate sufficient metrics.
- Use CloudWatch dashboards to combine metrics for a unified view.
- High-resolution metrics are useful for real-time monitoring but may not always be necessary.


# AWS CloudWatch Custom Metrics Collection Notes

## Overview
- **Objective**: Collect and publish custom metrics from an application to AWS CloudWatch using the AWS SDK.
- **Method**: Use the CloudWatch API to integrate custom metrics into the demo application.
- **Prerequisites**:
  - Download exercise files containing the demo application.
  - Ensure the application (e.g., `cloudwatchcourseapi`) is set up as per previous sessions.

## Step-by-Step Setup

### 1. Install Dependencies
- **Required Packages**:
  - `@aws-sdk/client-cloudwatch`: For interacting with CloudWatch.
  - `@aws-sdk/credential-provider-node`: For handling AWS credentials.
- **Command**:
  ```bash
  npm install @aws-sdk/client-cloudwatch @aws-sdk/credential-provider-node
  ```
- **Note**: Use the same package versions as shown in the demo to ensure compatibility.

### 2. Configure AWS IAM for CloudWatch Access
- **Create a Policy**:
  - Navigate to AWS IAM > Policies > Create Policy.
  - Select **CloudWatch** service and **PutMetricData** under Write access.
  - Name the policy, e.g., `cloudwatchcustommetric`.
- **Create a User**:
  - Navigate to IAM > Users > Add User.
  - Name the user, e.g., `pushcustommetrics`.
  - Do not enable AWS Management Console access.
  - Attach the `cloudwatchcustommetric` policy (filter by customer-managed policies).
  - Create an access key for local code use:
    - Name: `custommetrics`.
    - Copy the **Access Key ID** and **Secret Access Key**.
- **Update Environment File**:
  - Open the `.env` file in the demo application.
  - Add the following:
    ```env
    AWS_ACCESS_KEY_ID=your-access-key-id
    AWS_SECRET_ACCESS_KEY=your-secret-access-key
    AWS_REGION=us-east-1
    NAMESPACE=CloudWatchApiCourse
    ```
  - Ensure the correct AWS region is specified (e.g., `us-east-1`).

### 3. Implement Custom Metrics in Code
- **File**: `cloudwatchMetrics.js`
- **Steps**:
  - Import required AWS SDK modules.
  - Configure the CloudWatch client with region and credentials.
  - Define a function to publish custom metrics.
- **Code Example**:
  ```javascript
  // cloudwatchMetrics.js
  const { CloudWatchClient, PutMetricDataCommand } = require('@aws-sdk/client-cloudwatch');
  const { fromNodeProviderChain } = require('@aws-sdk/credential-provider-node');

  // Initialize CloudWatch client
  const cloudwatchClient = new CloudWatchClient({
    region: process.env.AWS_REGION,
    credentials: fromNodeProviderChain()
  });

  // Function to publish custom metric
  async function publishMetric(metricName, value, unit, dimensions) {
    try {
      const params = {
        Namespace: process.env.NAMESPACE,
        MetricData: [{
          MetricName: metricName,
          Dimensions: dimensions,
          Timestamp: new Date(),
          Value: value,
          Unit: unit
        }]
      };
      const command = new PutMetricDataCommand(params);
      await cloudwatchClient.send(command);
      console.log('Successfully published metric to CloudWatch');
    } catch (error) {
      console.error('Error publishing metric:', error);
    }
  }

  module.exports = { publishMetric };
  ```
- **Explanation**:
  - **Namespace**: Groups metrics in CloudWatch (e.g., `CloudWatchApiCourse`).
  - **Metric Data**: Includes metric name, dimensions (e.g., environment, endpoint, HTTP method), timestamp, value, and unit (e.g., Milliseconds).
  - **Error Handling**: Uses try-catch to log errors if metric publishing fails.

### 4. Integrate Metrics in Application
- **File**: `server.js`
- **Steps**:
  - Import the `publishMetric` function from `cloudwatchMetrics.js`.
  - Measure response time for API requests and publish as a custom metric.
- **Code Example**:
  ```javascript
  // server.js (partial)
  const { publishMetric } = require('./cloudwatchMetrics');
  const axios = require('axios');

  async function makeApiRequest() {
    const startTime = Date.now();
    try {
      const response = await axios.get(process.env.API_ENDPOINT);
      const endTime = Date.now();
      const responseTime = endTime - startTime;

      const dimensions = [
        { Name: 'Environment', Value: 'Production' },
        { Name: 'Endpoint', Value: '/api' },
        { Name: 'HttpMethod', Value: 'GET' }
      ];

      await publishMetric('ResponseTime', responseTime, 'Milliseconds', dimensions);
      return response.data;
    } catch (error) {
      console.error('API request failed:', error);
    }
  }
  ```
- **Explanation**:
  - Measures the time taken for an API request.
  - Publishes the response time as a custom metric with dimensions for better querying.

### 5. Run the Application
- **Command**:
  ```bash
  node server.js
  ```
- **Action**:
  - Reload the application in the browser.
  - Send requests (e.g., via the demo app UI) to generate custom metrics.
  - Check the console for success messages or errors.
- **Troubleshooting**:
  - Verify `.env` file values (Access Key, Secret Key, Region, Namespace).
  - Check console logs for errors during metric publishing.

### 6. View Custom Metrics in CloudWatch
- **Steps**:
  - Navigate to AWS CloudWatch Console > Metrics > All metrics.
  - Find the custom namespace: `CloudWatchApiCourse/Performance`.
  - Select dimensions (e.g., Endpoint, Environment, HttpMethod).
  - Choose the metric `ResponseTime`.
  - In **Graphed metrics**, adjust to:
    - Statistic: Sum (for small values).
    - Period: 5 seconds (to see recent data).
- **Example Output**:
  - Graph displays response time data for the API requests.
  - Use dimensions to filter and analyze specific metrics.

## Key Insights
- **Custom Metrics Benefits**:
  - Collect business-specific data for better decision-making.
  - Combine with built-in metrics for a unified dashboard.
- **Dimensions**:
  - Add context to metrics (e.g., environment, endpoint) for detailed analysis.
- **Granularity**:
  - Custom metrics can be standard (1-minute) or high-resolution (1-second).
  - Use high-resolution for time-sensitive metrics.

## Next Steps
- Create additional custom metrics based on business needs.
- Build CloudWatch dashboards to visualize custom and built-in metrics.
- Use CloudWatch Logs Insights to correlate metrics with logs for deeper analysis.


# AWS CloudWatch Metrics Streaming Notes

## Overview
- **Purpose**: Stream CloudWatch metrics to various AWS services for advanced processing and in-depth analytics beyond monitoring.
- **Benefits**:
  - Enables deeper business insights and informed decision-making.
  - Customizes data analytics workflows to meet specific business needs.
  - Supports integration with multiple destinations for flexible data processing.

## Metrics Streaming in AWS CloudWatch
- **Concept**: Streams metrics to external services for further analysis, extending their utility beyond CloudWatch monitoring.
- **Supported Destinations**:
  - Amazon S3 (via Kinesis Data Firehose)
  - Amazon Redshift (for data warehousing)
  - HTTP Endpoints
  - Third-party services (e.g., Logz.io, MongoDB Cloud)
- **Key Feature**: Allows streaming of all namespaces or specific ones, with the option to exclude certain namespaces.

## Setting Up a Metric Stream
- **Navigation**:
  - In AWS CloudWatch Console, go to **Metrics** > **Streams** > **Create metric stream**.
- **Steps**:
  1. **Select Metrics to Stream**:
     - Choose **All namespaces** or specific namespaces (e.g., `CloudWatchApiCourse`).
     - Optionally exclude specific namespaces to filter out unwanted metrics.
  2. **Configure Destination**:
     - **Quick S3 Setup**: Automatically creates a Kinesis Data Firehose and an S3 bucket.
       - Example bucket name: `metrics-streams-streamdemo`.
       - Automatically creates necessary IAM roles and Firehose configuration.
     - **Existing Firehose**: Select a pre-existing Kinesis Data Firehose stream.
     - **Custom Firehose Setup**:
       - Source: **Direct PUT** (required for CloudWatch to send metrics).
       - Destination: Choose from S3, Redshift, HTTP Endpoint, or third-party services.
  3. **Create the Stream**:
     - Name the stream, e.g., `streamdemo`.
     - Click **Create metric stream** to initialize.
- **Example Configuration (Conceptual)**:
  ```python
  # Example: Create a CloudWatch metric stream using AWS SDK (for reference)
  import boto3

  cloudwatch = boto3.client('cloudwatch')
  response = cloudwatch.create_metric_stream(
      Name='streamdemo',
      IncludeFilters=[{'Namespace': 'CloudWatchApiCourse'}],
      FirehoseArn='arn:aws:firehose:us-east-1:account-id:deliverystream/metrics-streams-streamdemo',
      RoleArn='arn:aws:iam::account-id:role/CloudWatch-Metrics-Stream-Role',
      OutputFormat='json'
  )
  print(response)
  ```

## Generating and Verifying Streamed Data
- **Generate Metrics**:
  - Use the demo application (e.g., `cloudwatchcourseapi`) to make requests (e.g., 100 requests).
  - Example: In the local application UI, initiate requests to generate metrics.
- **Check Stream Status**:
  - Navigate to the CloudWatch Console > **Streams** > Select `streamdemo`.
  - Verify the destination (e.g., S3).
- **Check Kinesis Data Firehose**:
  - Navigate to **Amazon Kinesis Data Firehose** > Select the delivery stream.
  - Monitor metrics like **Incoming Bytes**, **Put Requests**, **Incoming Records**, and **Data Sent to S3**.
- **Check S3 Bucket**:
  - Navigate to the S3 bucket (e.g., `metrics-streams-streamdemo`).
  - Refresh after a few minutes to see batched data.
  - Note: Data may take 1-2 minutes to appear due to Firehose activation and batching.
- **Troubleshooting**:
  - Wait at least 5 minutes for the Firehose to become fully active.
  - Ensure the application is running to generate metrics.
  - Verify Firehose status in the Kinesis Data Firehose console.

## Key Insights
- **Streaming Benefits**:
  - Enables customized data analytics workflows.
  - Supports multiple destinations for tailored solutions.
  - Maximizes data value by integrating with analytics platforms like Redshift.
- **Batching**: Metrics are batched, so data may not appear instantly in the destination.
- **Use Cases**:
  - Stream to S3 for long-term storage and analysis.
  - Stream to Redshift for data warehousing and complex queries.
  - Stream to third-party services for specialized analytics.

## Next Steps
- Query streamed data in S3 using services like Amazon Athena for business insights.
- Integrate with Redshift or third-party services for advanced analytics.
- Monitor Firehose metrics to ensure consistent data delivery.

## Additional Notes
- Ensure the correct IAM roles and permissions are set up for Firehose and S3.
- Test with a small number of requests initially to verify streaming setup.
- Use CloudWatch dashboards to visualize streamed metrics alongside other data.