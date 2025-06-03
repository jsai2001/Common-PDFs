
# AWS CloudWatch Monitoring Notes

## Course Overview
- **Objective**: Learn to use AWS CloudWatch to monitor and optimize cloud services and applications.
- **Key Learning Points**:
  - Derive insights and perform actions using CloudWatch metrics.
  - Use canaries to monitor website and web API health.
  - Set up AWS X-Ray and RAM for end-to-end backend and proton tracking.
- **Instructor**: Natil
- **Focus**: Monitoring applications in the cloud environment.

## Prerequisites
- **Recommended Knowledge**:
  - Familiarity with AWS Lambda functions and applications.
    - Suggested resource: Watch the course *Building Serverless Apps on AWS* for deeper understanding.
  - Understanding of Amazon EC2 instances and auto-scaling groups.
    - Suggested resource: Watch the course *Amazon EC2 Fundamentals* for better grasp of demos.
- **AWS Services Used**:
  - Lambda functions and applications to generate logs.
    - Example: Use AWS-provided sample Lambda applications for monitoring.
  - Amazon EC2 instances for metrics and alarms.
  - Auto-scaling groups for dynamic resource management.

## Key AWS CloudWatch Features
- **Metrics**: Collect and analyze performance data for cloud resources.
  - Example: Monitor CPU utilization of an EC2 instance.
    ```python
    # Example: Lambda function to log custom metrics to CloudWatch
    import boto3

    def lambda_handler(event, context):
        cloudwatch = boto3.client('cloudwatch')
        cloudwatch.put_metric_data(
            Namespace='MyApp',
            MetricData=[
                {
                    'MetricName': 'RequestLatency',
                    'Value': 200.0,
                    'Unit': 'Milliseconds'
                }
            ]
        )
        return {'statusCode': 200, 'body': 'Metric logged'}
    ```
- **Canaries**: Automated scripts to monitor website and API health.
  - Use case: Simulate user interactions to detect issues in web applications.
- **AWS X-Ray**: Trace requests to debug and analyze application performance.
  - Example: Enable X-Ray for a Lambda function.
    ```python
    from aws_xray_sdk.core import xray_recorder
    from aws_xray_sdk.ext.flask import XRay

    @xray_recorder.capture('my_endpoint')
    def my_function():
        # Function logic here
        pass
    ```
- **RAM (Resource Access Manager)**: Share resources securely for monitoring across accounts.

## Getting Started
- **Setup**:
  - Deploy sample Lambda applications provided by AWS.
  - Configure EC2 instances and auto-scaling groups for monitoring.
- **Next Steps**:
  - Explore CloudWatch dashboards to visualize metrics.
  - Set up alarms for proactive notifications.
    ```python
    # Example: Create a CloudWatch alarm for high CPU utilization
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
        AlarmActions=[sns_topic_arn]
    )
    ```

## Additional Notes
- Use CloudWatch to centralize logs, metrics, and traces for comprehensive monitoring.
- Combine with AWS X-Ray for detailed request tracing and performance analysis.
- Regularly review auto-scaling metrics to optimize resource allocation.