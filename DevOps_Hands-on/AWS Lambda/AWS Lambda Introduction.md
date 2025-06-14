# AWS Lambda Overview

## Overview
- **Objective**: Understand AWS Lambda, its features, use cases, and pricing model.
- **Definition**: AWS Lambda is a serverless computing service that runs code without provisioning or managing servers.
- **Key Characteristics of Serverless**:
  - **Pay-as-you-go**: Only pay for resources used.
  - **No infrastructure management**: AWS handles servers, scaling, and availability.
  - **Automatic scaling**: Scales up/down based on traffic.

## How AWS Lambda Works
1. **Upload Code**:
   - Upload code to AWS Lambda in supported languages: JavaScript, Python, Java, C#, Go, etc.
   - Multiple upload methods (demonstrated later in the course).
2. **Configure Triggers**:
   - Triggers include state changes, HTTP requests, or AWS service events.
   - Example: Trigger Lambda from an S3 bucket event.
3. **Execution**:
   - AWS Lambda runs the code when triggered.
   - Code can execute business logic, call other AWS services, or external APIs.
   - Example Lambda function in JavaScript:
     ```javascript
     exports.handler = async (event) => {
         console.log('Event:', JSON.stringify(event));
         const response = {
             statusCode: 200,
             body: JSON.stringify('Processed event successfully!')
         };
         return response;
     };
     ```

## Use Cases for AWS Lambda
- **Data Processing**:
  - Enrich data streams or perform real-time analytics.
  - Example: Process incoming data from Kinesis.
- **Web/Mobile Backends**:
  - Replace EC2 instances for handling HTTP requests.
  - Example: API Gateway + Lambda for REST APIs.
- **Infrastructure Management**:
  - Trigger code to manage cloud resources (e.g., turn off unused EC2 instances).
  - Example policy to stop instances:
    ```javascript
    const AWS = require('aws-sdk');
    const ec2 = new AWS.EC2();
    exports.handler = async () => {
        const params = { InstanceIds: ['i-1234567890abcdef0'] };
        await ec2.stopInstances(params).promise();
        return 'Instances stopped';
    };
    ```
- **Event-Driven Architectures**:
  - Fundamental for running business logic in response to events.

## When Not to Use Lambda
- **Extreme Real-Time Requirements**:
  - Unsuitable for single-digit millisecond responses (Lambda typically responds in double-digit milliseconds).
- **Specific Hardware Needs**:
  - Generic hardware may not suit tasks like video rendering requiring specialized hardware.
- **100% Reliability**:
  - Not ideal for critical systems (e.g., life support) due to third-party dependencies.

## AWS Lambda Pricing
- **Model**: Pay-per-use based on:
  - Number of requests.
  - Duration of each request.
  - Memory allocated to the function.
- **Details**: Check the latest pricing on the AWS Lambda website.
- **Example Cost Calculation** (conceptual):
  ```plaintext
  Requests: 1,000,000/month
  Duration: 200ms/request
  Memory: 128MB
  Cost: (1M free requests/month) + ($0.0000002/request * 0) + ($0.00001667/GB-second * 0.2s * 0.125GB * 1M)
  ```

# Exploring the AWS Lambda Console

## Overview
- **Objective**: Navigate and understand the AWS Lambda console using an IAM admin user.
- **Purpose**: Explore key features of the Lambda console, including function management, configuration, and testing.

## Accessing the Lambda Console
1. **Log In**:
   - Use the IAM admin user created previously (not the root user).
   - Access the AWS Management Console.
2. **Find Lambda**:
   - If recently visited, Lambda appears in the "Recently Visited" section.
   - Alternatively, search for "Lambda" in the console search bar.
3. **Dashboard Overview**:
   - Displays the number of Lambda functions in the current account and region.
   - **Regions**: AWS services are region-specific (e.g., Ireland, Virginia). Functions are isolated by region and account.
   - Example: Switching from Ireland (1 function) to Virginia (0 functions) shows region-specific functions.

## Key Concepts
- **Regions**:
  - Geographically distributed data centers.
  - Functions are region-specific; ensure the correct region is selected.
- **Account Concurrency**:
  - Default limit: 1000 concurrent function executions.
  - Can be increased as needed.
- **Metrics**:
  - Account-level metrics available on the dashboard.

## Exploring a Lambda Function
- **Example Function**: `helloWorld`
- **Function Overview**:
  - Displays:
    - **Layers**: Manage dependencies (not covered in this course).
    - **Triggers**: Events that invoke the function (e.g., API Gateway).
    - **ARN**: Amazon Resource Name, a unique identifier for the function.
      ```plaintext
      Example ARN: arn:aws:lambda:eu-west-1:123456789012:function:helloWorld
      ```
- **Code Tab**:
  - Contains the function’s source code.
  - Example JavaScript code for `helloWorld`:
    ```javascript
    exports.handler = async (event) => {
        const response = {
            statusCode: 200,
            body: JSON.stringify('Hello from Lambda!')
        };
        return response;
    };
    ```
  - This function returns a 200 status code with the message "Hello from Lambda!" to an API Gateway trigger (synchronous request).
- **Configuration Tab**:
  - Minimal infrastructure management required.
  - Common configurations:
    - **Memory Allocation**: Adjust memory as needed (affects performance and cost).
    - **Triggers**: Add multiple triggers (e.g., S3, SNS).
    - **Permissions**: Default permissions include CloudWatch Logs access.
      ```json
      {
        "Version": "2012-10-17",
        "Statement": [
          {
            "Effect": "Allow",
            "Action": [
              "logs:CreateLogGroup",
              "logs:CreateLogStream",
              "logs:PutLogEvents"
            ],
            "Resource": "*"
          }
        ]
      }
      ```
  - Additional configurations available (refer to Lambda Developer Guide).
- **Monitoring Tab**:
  - View metrics, logs, and traces for the function.
  - Dedicated video on monitoring later in the course.
- **Test Tab**:
  - Test the function directly in the console.
  - Example: Trigger the `helloWorld` function via API Gateway to display "Hello from Lambda!".

## Testing the Function
1. **Access Trigger**:
   - Navigate to the **Triggers** section.
   - Click the API Gateway endpoint to invoke the function.
2. **Result**:
   - Browser displays: `Hello from Lambda!`.
3. **API Gateway Configuration**:
   - Detailed configuration covered in a later video.

## Best Practices
- **Check Region**: Always verify the correct region to avoid missing functions.
- **Secure Permissions**: Functions start with minimal permissions; add only what’s needed.
- **Use Test Tab**: Validate function behavior before deploying to production.

# AWS Lambda Programming Model

## Overview
- **Objective**: Understand the AWS Lambda programming model, focusing on triggers, handlers, and execution models.
- **Language**: Examples use JavaScript, but concepts apply to other supported languages (e.g., Python, Java, C#, Go).

## Lambda Programming Model Components
1. **Triggers**:
   - Events that invoke a Lambda function.
   - Examples:
     - **Amazon API Gateway**: HTTP requests trigger the function.
     - **Amazon DynamoDB**: Changes in table records (e.g., insert, update).
     - **Amazon S3**: File creation or modification in a bucket.
     - **Amazon SQS**: New messages in a queue.
2. **Handler Function**:
   - The main function invoked when a Lambda function is triggered.
   - **Input Parameters**:
     - **Event Object**: Contains data from the trigger; varies by trigger type (e.g., API Gateway vs. Kinesis).
     - **Context Object**: Provides methods to interact with runtime information (e.g., function name, execution time).
     - **Callback Object** (optional): Used to return data to the invoker (not always available).
   - Example JavaScript handler:
     ```javascript
     exports.handler = async (event, context, callback) => {
         console.log('Event:', JSON.stringify(event));
         const response = {
             statusCode: 200,
             body: JSON.stringify('Hello from Lambda!')
         };
         callback(null, response); // Use callback for synchronous responses
     };
     ```
3. **Function Code**:
   - The specific business logic executed by the handler.
   - Can include API calls, data processing, or interactions with other AWS services.

## Lambda Execution Models
1. **Synchronous**:
   - **Description**: The invoker waits for the Lambda function to complete and return a response (blocking request).
   - **Use Case**: API Gateway handling HTTP requests.
   - **Behavior**: The calling service (e.g., API Gateway) waits for the function’s response.
   - Example:
     ```javascript
     exports.handler = async (event) => {
         return {
             statusCode: 200,
             body: JSON.stringify('Processed HTTP request')
         };
     };
     ```
2. **Asynchronous**:
   - **Description**: Non-blocking; the invoker receives an acknowledgment but does not wait for execution completion.
   - **Use Case**: S3 triggering a Lambda function on file upload.
   - **Behavior**: The triggering service (e.g., S3) continues without waiting for the function to finish.
   - Example:
     ```javascript
     exports.handler = async (event) => {
         console.log('S3 Event:', event.Records[0].s3);
         // Process file, no return needed
         return 'File processed';
     };
     ```
3. **Poll Stream-Based**:
   - **Description**: Lambda polls a stream or queue (e.g., SQS, Kinesis) and invokes the function synchronously when messages are available.
   - **Use Case**: SQS messages triggering a Lambda function.
   - **Behavior**: Lambda pulls messages from the queue and processes them.
   - Example for SQS:
     ```javascript
     exports.handler = async (event) => {
         for (const record of event.Records) {
             console.log('SQS Message:', record.body);
             // Process message
         }
         return 'Messages processed';
     };
     ```

## Key Considerations
- **Event Object Variability**: The structure of the `event` object depends on the trigger (e.g., API Gateway events include HTTP details, SQS events include message data).
  - Example API Gateway event:
    ```json
    {
      "httpMethod": "GET",
      "path": "/example",
      "headers": { ... }
    }
    ```
  - Example SQS event:
    ```json
    {
      "Records": [
        {
          "body": "Message content",
          "messageId": "1234567890"
        }
      ]
    }
    ```
- **Context Object**: Provides runtime details (e.g., `context.functionName`, `context.getRemainingTimeInMillis()`).
- **Callback**: Used in synchronous scenarios to explicitly return results; optional in async/await syntax.

## Best Practices
- **Handle Event Variability**: Parse the `event` object carefully based on the trigger type.
- **Optimize for Execution Model**: Choose synchronous for direct responses, asynchronous for non-blocking tasks, or poll-based for streams/queues.
- **Log Events**: Use `console.log` to debug and monitor event data in CloudWatch.