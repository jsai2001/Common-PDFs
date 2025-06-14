# Creating Your First AWS Lambda Function

## Overview
- **Objective**: Create and test a basic AWS Lambda function using the AWS Management Console.
- **Prerequisites**: Familiarity with the Lambda console and programming model; IAM admin user access.

## Steps to Create a Lambda Function
1. **Access Lambda Console**:
   - Log in to the AWS Management Console with the IAM admin user.
   - Navigate to the Lambda service (search "Lambda" or use "Recently Visited").
2. **Create Function**:
   - Click the **Create Function** button (orange/yellow).
   - Options: Author from scratch, use a blueprint, or use a container image.
   - Select **Author from Scratch**.
   - **Configuration**:
     - **Function Name**: `my-lambda-function`.
     - **Runtime**: Node.js 18.x (other options: Python, Java, etc.; custom runtimes supported).
     - **Architecture**: Choose Intel-based or Graviton (default Intel for this example).
     - Skip advanced settings for now.
   - Click **Create Function**.
3. **Function Overview**:
   - Displays no triggers or layers initially.
   - Shows the Amazon Resource Name (ARN) and basic configuration.

## Modify and Deploy Function Code
1. **Edit Code**:
   - Navigate to the **Code** tab in the Lambda console.
   - Add logging to inspect the event object and a custom message.
   - Example JavaScript code:
     ```javascript
     exports.handler = async (event) => {
         console.log('Event:', JSON.stringify(event));
         console.log('Hello from Lambda!');
         const response = {
             statusCode: 200,
             body: JSON.stringify('Hello from Lambda!')
         };
         return response;
     };
     ```
   - **Notes**:
     - `console.log` outputs to CloudWatch Logs automatically.
     - The `event` object varies by trigger; logs help debug its structure.
2. **Deploy Code**:
   - Click **Deploy** to upload the code to the cloud.
   - Changes are now live in the Lambda function.

## Test the Function
1. **Configure Test Event**:
   - Navigate to the **Test** tab.
   - Create a new test event (e.g., named `test-event`).
   - Use a simple event template (e.g., "hello-world") or customize:
     ```json
     {
       "key1": "value1",
       "key2": "value2",
       "key3": "value3"
     }
     ```
   - Save the test event.
2. **Run Test**:
   - Click **Test** to execute the function with the configured event.
   - **Results**:
     - **Response**: Returns `{"statusCode": 200, "body": "\"Hello from Lambda!\""}`.
     - **Logs** (in CloudWatch):
       - Displays the event object: `{"key1": "value1", "key2": "value2", "key3": "value3"}`.
       - Shows the custom message: `Hello from Lambda!`.
       - Includes metrics: execution duration, billed duration, and memory usage.
     - Example log output:
       ```plaintext
       START RequestId: 12345678-1234-1234-1234-1234567890ab
       Event: {"key1":"value1","key2":"value2","key3":"value3"}
       Hello from Lambda!
       END RequestId: 12345678-1234-1234-1234-1234567890ab
       REPORT Duration: 123.45 ms Billed Duration: 124 ms Memory Size: 128 MB Max Memory Used: 50 MB
       ```

## Key Observations
- **No Trigger Required for Testing**: The test tab allows execution without configuring a trigger.
- **CloudWatch Integration**: Logs and metrics (duration, memory) are automatically sent to CloudWatch.
- **Optimization**: Use log data (duration, memory) to optimize function performance later.


# AWS Lambda Function Triggers

## Overview
- **Objective**: Understand the most common AWS Lambda triggers and their use cases.
- **Purpose**: Learn how to configure Lambda functions to respond to events from various AWS services.

## Common Lambda Triggers
1. **Amazon API Gateway**:
   - **Description**: A service for creating and managing APIs, acting as a "front door" for applications.
   - **Use Case**: Triggers a Lambda function when an HTTP request is made to a specific endpoint (e.g., `GET /hello`).
   - **Application**: Build serverless web application backends.
   - **Example**: Configure API Gateway to trigger a Lambda function:
     ```javascript
     exports.handler = async (event) => {
         console.log('API Gateway Event:', event);
         return {
             statusCode: 200,
             body: JSON.stringify('Hello from API Gateway!')
         };
     };
     ```
     - **Event Structure** (simplified):
       ```json
       {
         "httpMethod": "GET",
         "path": "/hello",
         "headers": { ... }
       }
       ```
2. **Amazon S3**:
   - **Description**: Object storage for storing/retrieving data from websites, mobile apps, IoT devices, etc.
   - **Use Case**: Triggers a Lambda function on file events (e.g., create, update, delete) in a bucket.
   - **Application**: Enrich files or create workflow steps (e.g., process images on upload).
   - **Example**: Process a new file in S3:
     ```javascript
     exports.handler = async (event) => {
         const file = event.Records[0].s3.object.key;
         console.log('New file:', file);
         return `Processed ${file}`;
     };
     ```
     - **Event Structure** (simplified):
       ```json
       {
         "Records": [
           {
             "s3": {
               "bucket": { "name": "my-bucket" },
               "object": { "key": "file.jpg" }
             }
           }
         ]
       }
       ```
3. **Amazon DynamoDB**:
   - **Description**: NoSQL database with reliable performance at scale, featuring DynamoDB Streams.
   - **Use Case**: Triggers a Lambda function when a record is created, updated, or deleted in a table.
   - **Application**: Implement transactions or stored procedures in a NoSQL database.
   - **Example**: Handle DynamoDB stream events:
     ```javascript
     exports.handler = async (event) => {
         for (const record of event.Records) {
             console.log('DynamoDB Record:', record.dynamodb);
         }
         return 'Processed records';
     };
     ```
     - **Event Structure** (simplified):
       ```json
       {
         "Records": [
           {
             "eventName": "INSERT",
             "dynamodb": {
               "NewImage": { "id": { "S": "123" } }
             }
           }
         ]
       }
       ```
4. **Amazon Simple Queue Service (SQS)**:
   - **Description**: Fully managed message queue service for decoupling microservices.
   - **Use Case**: Triggers a Lambda function when a new message arrives in an SQS queue.
   - **Application**: Decouple functions for scalable architectures.
   - **Example**: Process SQS messages:
     ```javascript
     exports.handler = async (event) => {
         for (const record of event.Records) {
             console.log('SQS Message:', record.body);
         }
         return 'Messages processed';
     };
     ```
     - **Event Structure** (simplified):
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
5. **Amazon Kinesis**:
   - **Description**: Service for collecting, processing, and analyzing real-time streaming data.
   - **Use Case**: Triggers a Lambda function when a new event arrives in a Kinesis stream.
   - **Application**: Process high-volume, real-time data (e.g., IoT or analytics).
   - **Example**: Process Kinesis stream events:
     ```javascript
     exports.handler = async (event) => {
         for (const record of event.Records) {
             const data = Buffer.from(record.kinesis.data, 'base64').toString();
             console.log('Kinesis Data:', data);
         }
         return 'Stream processed';
     };
     ```
     - **Event Structure** (simplified):
       ```json
       {
         "Records": [
           {
             "kinesis": {
               "data": "base64-encoded-data"
             }
           }
         ]
       }
       ```

## Key Considerations
- **Trigger-Specific Events**: Each trigger provides a unique `event` object structure; always validate the event format.
- **Scalability**: Triggers like SQS and Kinesis support high-throughput, event-driven architectures.
- **Permissions**: Ensure the Lambda function has IAM permissions to access the triggering service (e.g., `s3:GetObject`, `sqs:ReceiveMessage`).
  - Example IAM policy for S3 trigger:
    ```json
    {
      "Version": "2012-10-17",
      "Statement": [
        {
          "Effect": "Allow",
          "Action": ["s3:GetObject"],
          "Resource": "arn:aws:s3:::my-bucket/*"
        }
      ]
    }
    ```

## Best Practices
- **Log Events**: Use `console.log` to debug event structures in CloudWatch Logs.
- **Fine-Grained Permissions**: Grant only necessary permissions for each trigger.
- **Explore Documentation**: Review the AWS Lambda documentation for additional trigger options.


# Introduction to Amazon API Gateway

## Overview
- **Objective**: Understand Amazon API Gateway as a trigger for AWS Lambda and its role in creating serverless backends.
- **Definition**: A fully managed service for creating, publishing, maintaining, monitoring, and securing APIs at scale.

## Key Features of API Gateway
- **Functionality**:
  - Acts as a "front door" for applications to access backend services (e.g., AWS Lambda, EC2, DynamoDB, Kinesis, or public endpoints).
  - Handles up to 200,000 concurrent API calls.
  - Manages traffic, authorization, access control, monitoring, and API version management.
- **Pricing**:
  - Pay-per-use model: Charged based on API calls received and data transferred out.
  - No minimum fees or startup costs.

## How API Gateway Works
- **Request Flow**:
  - Clients (web/mobile apps, IoT devices, etc.) send HTTP or WebSocket requests to API Gateway endpoints.
  - API Gateway manages traffic, authenticates requests, and forwards them to the appropriate backend service.
- **Supported Backends**:
  - AWS Lambda (serverless functions).
  - AWS EC2 (compute instances).
  - Amazon DynamoDB (NoSQL database).
  - Amazon Kinesis (real-time data streaming).
  - Public HTTP endpoints.

## Use Cases for API Gateway
1. **Triggering Lambda Functions**:
   - Routes HTTP requests to specific Lambda functions based on the endpoint and method (e.g., `GET /hello`).
   - Example Lambda function for API Gateway:
     ```javascript
     exports.handler = async (event) => {
         console.log('API Gateway Request:', event);
         return {
             statusCode: 200,
             body: JSON.stringify('Hello from API Gateway!')
         };
     };
     ```
2. **Real-Time Applications**:
   - Supports WebSockets for serverless real-time chat applications.
   - Example: Use API Gateway, Lambda, and DynamoDB for session management.
3. **Direct DynamoDB Integration**:
   - Use Velocity Template Language (VTL) to perform CRUD operations (create, read, update, delete) on DynamoDB tables without an intermediary Lambda function.
   - Example VTL for a GET request:
     ```vtl
     {
       "TableName": "MyTable",
       "Key": {
         "id": { "S": "$input.params('id')" }
       }
     }
     ```
4. **Authentication and Authorization**:
   - Integrates with Amazon Cognito or Lambda authorizers to authenticate requests.
   - Example Lambda authorizer (simplified):
     ```javascript
     exports.handler = async (event) => {
         const token = event.headers.Authorization;
         // Validate token with Cognito or custom logic
         return {
             principalId: 'user',
             policyDocument: {
                 Version: '2012-10-17',
                 Statement: [
                     {
                         Effect: 'Allow',
                         Action: 'execute-api:Invoke',
                         Resource: event.methodArn
                     }
                 ]
             }
         };
     };
     ```

## Benefits
- **Scalability**: Handles high volumes of API calls efficiently.
- **Security**: Supports authentication and access control.
- **Flexibility**: Connects to multiple AWS services or external endpoints.
- **Cost-Effective**: Pay only for actual usage.


# Adding API Gateway as a Trigger to AWS Lambda

## Overview
- **Objective**: Configure an Amazon API Gateway as a trigger for an existing AWS Lambda function and handle query string parameters.
- **Prerequisites**: AWS Lambda function created (e.g., `my-lambda-function`), familiarity with the Lambda console.

## Steps to Add API Gateway Trigger
1. **Access Lambda Console**:
   - Log in to the AWS Management Console with the IAM admin user.
   - Navigate to the Lambda function (`my-lambda-function`).
2. **Add Trigger**:
   - In the Lambda console, click **Add Trigger**.
   - Select **API Gateway** from the list of trigger sources.
   - **Configuration**:
     - Create a new API.
     - Set security to **Open** (no authentication required for this example).
   - Click **Add** to create the API Gateway trigger.
3. **Verify Trigger**:
   - After adding, click the API Gateway trigger to view its configuration.
   - Note the generated endpoint URL (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/default`).
   - Test the endpoint by clicking it; it returns the Lambda response: `Hello from Lambda!`.
4. **API Gateway Configuration**:
   - Navigate to the API Gateway resource (via the trigger link).
   - Details include:
     - **API Type**: HTTP.
     - **Authorization**: None (open access).
     - **CORS**: Not enabled.
     - **HTTP Methods**: Any (GET, POST, PUT, DELETE, etc.).
     - **Resource Path**: Defines the endpoint path (e.g., `/default`).
     - **Stage**: Manages API deployment stages (e.g., `default`).

## Handling Query String Parameters
- **Goal**: Modify the Lambda function to process query string parameters (e.g., `?name=Marcia`) and return a personalized response.
- **Steps**:
  1. Navigate to the **Code** tab of the Lambda function.
  2. Update the code to extract the `name` parameter from the `event.queryStringParameters`.
  3. Example JavaScript code:
     ```javascript
     exports.handler = async (event) => {
         const queryParams = event.queryStringParameters || {};
         const name = queryParams.name || null;
         let message = 'Hello from Lambda!';
         if (name) {
             message = `Hello ${name}!`;
         }
         return {
             statusCode: 200,
             body: JSON.stringify(message)
         };
     };
     ```
   - **Explanation**:
     - `event.queryStringParameters`: Contains query string data (e.g., `{ "name": "Marcia" }`).
     - Validates if `name` exists; if not, defaults to `Hello from Lambda!`.
     - Returns a JSON response with the message.
  4. Click **Deploy** to update the function in the cloud.
- **Test the Updated Function**:
  - Use the API Gateway endpoint with a query parameter (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/default?name=Marcia`).
  - Expected responses:
    - `?name=Marcia` → `Hello Marcia!`
    - `?name=John` → `Hello John!`
    - No parameter → `Hello from Lambda!`

## Example API Gateway Event Structure
- When API Gateway triggers the Lambda function, the `event` object includes query string parameters:
  ```json
  {
    "queryStringParameters": {
      "name": "Marcia"
    },
    "httpMethod": "GET",
    "path": "/default"
  }
  ```

## Key Considerations
- **Open Security**: The example uses an open API for simplicity; production APIs should use authentication (e.g., Cognito, Lambda authorizers).
- **Multiple Endpoints**: A single API Gateway can trigger multiple Lambda functions using different resource paths.
- **Stage Management**: API Gateway stages (e.g., `prod`, `dev`) allow versioned deployments.

## Best Practices
- **Validate Query Parameters**: Always check for the existence of parameters to avoid errors.
- **Secure APIs**: Enable authentication and CORS for production environments.
- **Log Events**: Use `console.log` to debug the `event` object in CloudWatch Logs.


# Testing AWS Lambda with Postman

## Overview
- **Objective**: Use Postman to test an AWS Lambda function triggered by an API Gateway endpoint, enabling testing of various HTTP methods and configurations.
- **Prerequisites**: A Lambda function with an API Gateway trigger (e.g., `my-lambda-function`), familiarity with the AWS Management Console.

## Why Use Postman?
- **Limitations of Browser Testing**:
  - Browsers are limited to simple `GET` requests, restricting testing of other HTTP methods (e.g., `POST`, `PUT`, `DELETE`).
- **Postman Benefits**:
  - Supports all HTTP methods (`GET`, `POST`, `PUT`, `DELETE`, etc.).
  - Allows adding headers, query parameters, and request bodies.
  - Provides a visual interface for advanced API testing compared to command-line tools like `curl`.

## Setting Up Postman
1. **Download and Install**:
   - Download Postman from the official website (postman.com).
   - Create an account to access the tool.
2. **Key Features**:
   - Select HTTP method (e.g., `GET`, `POST`).
   - Configure query string parameters, headers, authorization, and request bodies.
   - Send requests and view responses.

## Testing the Lambda Function with Postman
1. **Access the API Gateway Endpoint**:
   - Copy the API Gateway endpoint URL from the Lambda console (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/default`).
2. **Test with GET Request**:
   - In Postman, set the method to `GET` and paste the endpoint URL.
   - Send the request.
   - Expected response: `Hello from Lambda!` (based on the Lambda function’s default response).
3. **Test with Query Parameters**:
   - Add a query parameter (e.g., `name=Marcia`) to the URL: `https://<api-id>.execute-api.<region>.amazonaws.com/default?name=Marcia`.
   - Send the request.
   - Expected response: `Hello Marcia!` (based on the Lambda function’s query parameter logic).
   - Example Lambda code handling query parameters:
     ```javascript
     exports.handler = async (event) => {
         const queryParams = event.queryStringParameters || {};
         const name = queryParams.name || null;
         let message = 'Hello from Lambda!';
         if (name) {
             message = `Hello ${name}!`;
         }
         return {
             statusCode: 200,
             body: JSON.stringify(message)
         };
     };
     ```
4. **Test with POST Request**:
   - Set the method to `POST` in Postman.
   - Use the same endpoint URL.
   - Send the request.
   - Expected response: `Hello from Lambda!` (since the API Gateway trigger accepts any HTTP method, but the function doesn’t yet process `POST` body data).
5. **Add Request Body (Optional)**:
   - In Postman, go to the **Body** tab, select `raw` and `JSON`, and add a JSON payload:
     ```json
     {
       "name": "Marcia"
     }
     ```
   - Send the `POST` request.
   - **Note**: The current Lambda function doesn’t process the request body, so the response remains `Hello from Lambda!`.
   - To process the body, update the Lambda function:
     ```javascript
     exports.handler = async (event) => {
         const queryParams = event.queryStringParameters || {};
         let name = queryParams.name || null;
         if (!name && event.body) {
             const body = JSON.parse(event.body);
             name = body.name || null;
         }
         let message = 'Hello from Lambda!';
         if (name) {
             message = `Hello ${name}!`;
         }
         return {
             statusCode: 200,
             body: JSON.stringify(message)
         };
     };
     ```
     - **Event Structure** (for `POST` with body):
       ```json
       {
         "httpMethod": "POST",
         "queryStringParameters": {},
         "body": "{\"name\": \"Marcia\"}"
       }
       ```

## Key Considerations
- **HTTP Method Flexibility**: The API Gateway trigger (configured with `ANY` method) allows testing multiple HTTP methods.
- **Body Processing**: The Lambda function must explicitly parse `event.body` to use POST data.
- **Security**: The example uses an open API; production environments should implement authentication (e.g., Cognito, Lambda authorizers).

## Best Practices
- **Validate Inputs**: Check both `queryStringParameters` and `body` to handle various input scenarios.
- **Log Events**: Use `console.log` to debug the `event` object in CloudWatch Logs.
- **Use Postman for Complex Testing**: Leverage headers, bodies, and authorization for advanced API testing.

# Monitoring AWS Lambda with CloudWatch Metrics 

## Overview
- **Objective**: Learn to monitor an AWS Lambda function using Amazon CloudWatch metrics to understand performance and detect issues.
- **Purpose**: Monitoring and debugging are critical for validating system behavior, detecting bugs, and preventing issues.

## Amazon CloudWatch Overview
- **Definition**: A monitoring and observability service for AWS resources.
- **Features**:
  - Collects logs, metrics, and events from AWS services.
  - Provides dashboards to visualize metrics.
  - Many AWS services, including Lambda, automatically send metrics to CloudWatch without code changes.

## Accessing CloudWatch Metrics for Lambda
1. **Navigate to Lambda Console**:
   - Log in to the AWS Management Console with the IAM admin user.
   - Go to the Lambda function (e.g., `my-lambda-function`).
   - Select the **Monitoring** tab.
2. **View Metrics**:
   - Adjust the timeframe (e.g., last hour) to see recent activity.
   - Metrics are automatically populated for Lambda functions.

## Key CloudWatch Metrics for Lambda
1. **Invocations**:
   - **Description**: Tracks the number of times the Lambda function is triggered.
   - **Example**: Multiple rapid invocations from Postman (e.g., 5-6 requests) are visible as spikes.
   - **Use Case**: Understand usage patterns and traffic volume.
2. **Duration**:
   - **Description**: Measures how long the function takes to process a request and return a response.
   - **Metrics Displayed**:
     - **Minimum Duration** (blue): Shortest execution time.
     - **Average Duration** (orange): Average execution time.
     - **Maximum Duration** (green): Longest execution time.
   - **Use Case**: Identify performance variations and optimize function execution.
   - **Example Visualization** (conceptual, as charts are not generated unless explicitly requested):
     ```plaintext
     Duration (ms) over Time
     Min: 100ms | Avg: 150ms | Max: 200ms
     ```
3. **Error Count and Success Rate**:
   - **Description**: Shows the number of errors and the percentage of successful invocations.
   - **Example**: A 100% success rate with no errors indicates a healthy function.
   - **Use Case**: Monitor function health and take preventive actions if errors increase.

## Example Lambda Code with Logging
- The Lambda function logs data to CloudWatch, which complements metrics:
  ```javascript
  exports.handler = async (event) => {
      console.log('Event:', JSON.stringify(event));
      console.log('Hello from Lambda!');
      const queryParams = event.queryStringParameters || {};
      const name = queryParams.name || null;
      let message = 'Hello from Lambda!';
      if (name) {
          message = `Hello ${name}!`;
      }
      return {
          statusCode: 200,
          body: JSON.stringify(message)
      };
  };
  ```
- **Logs in CloudWatch**:
  - Metrics like duration and invocations are automatically logged.
  - Custom logs (e.g., `console.log`) appear in CloudWatch Logs.

## Best Practices
- **Monitor Regularly**: Use CloudWatch dashboards to track invocations, duration, and errors.
- **Set Alerts**: Configure CloudWatch Alarms for high error rates or long durations (not covered in this video).
- **Optimize Performance**: Use duration metrics to identify bottlenecks and adjust memory allocation if needed.
- **Check Documentation**: Refer to the AWS Lambda Developer Guide for additional metrics.

# Adding CloudWatch Logs for AWS Lambda

## Overview
- **Objective**: Learn how to use Amazon CloudWatch Logs to monitor and debug an AWS Lambda function.
- **Purpose**: Logs provide detailed insights into function execution, complementing CloudWatch metrics.

## CloudWatch Logs Basics
- **Integration with Lambda**:
  - Lambda automatically sends logs to CloudWatch when `console.log` (or equivalent) is used in the function code.
  - Logs include automatic entries (e.g., start/end, execution report) and custom messages.
- **Structure**:
  - **Log Groups**: Contain logs for a specific Lambda function.
  - **Log Streams**: Subdivide logs within a group, created when the function is deployed, after time passes, or when a stream fills up.
  - **Log Events**: Individual log entries within a stream.

## Adding Logs to a Lambda Function
1. **Modify Function Code**:
   - Add `console.log` statements to output custom messages or event data.
   - Example JavaScript code with logging:
     ```javascript
     exports.handler = async (event) => {
         console.log('Event:', JSON.stringify(event));
         console.log('Hello');
         const queryParams = event.queryStringParameters || {};
         const name = queryParams.name || null;
         let message = 'Hello from Lambda!';
         if (name) {
             message = `Hello ${name}!`;
         }
         return {
             statusCode: 200,
             body: JSON.stringify(message)
         };
     };
     ```
   - **Notes**:
     - `console.log('Event:', JSON.stringify(event))`: Logs the API Gateway event object.
     - `console.log('Hello')`: Logs a custom message.
2. **Deploy the Function**:
   - Click **Deploy** in the Lambda console to update the function in the cloud.

## Testing and Viewing Logs
1. **Trigger the Function**:
   - Use Postman to send requests to the API Gateway endpoint (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/default?name=Marcia`).
   - Make multiple requests to generate log entries.
2. **Access Logs in Lambda Console**:
   - Navigate to the Lambda function’s **Monitoring** tab.
   - Select the **Logs** section.
   - Filter logs by time (e.g., last 15 minutes, last hour).
   - Enable **Auto-refresh** to append new logs in real-time.
3. **Access Logs in CloudWatch Console**:
   - From the Lambda **Monitoring** tab, click a log link to open CloudWatch Logs.
   - View the **Log Group** for the function (e.g., `/aws/lambda/my-lambda-function`).
   - Select a **Log Stream** to see individual log events.

## Log Content
- **Automatic Logs** (generated by Lambda):
  - **START**: Marks the beginning of a function invocation, includes runtime and request ID.
  - **END**: Marks the end of the invocation.
  - **REPORT**: Provides execution metrics (duration, billed duration, memory size, max memory used, initialization duration).
- **Custom Logs** (from `console.log`):
  - Event object: Displays the API Gateway event, including `queryStringParameters` (e.g., `{ "name": "Marcia" }`).
  - Custom message: Outputs `Hello` as defined in the code.
- **Example Log Output**:
  ```plaintext
  START RequestId: 12345678-1234-1234-1234-1234567890ab Version: $LATEST
  Event: {"queryStringParameters":{"name":"Marcia"},"httpMethod":"GET","path":"/default"}
  Hello
  END RequestId: 12345678-1234-1234-1234-1234567890ab
  REPORT Duration: 123.45 ms Billed Duration: 124 ms Memory Size: 128 MB Max Memory Used: 50 MB Init Duration: 100.00 ms
  ```

## Key Considerations
- **Event Object Logging**: Essential for debugging trigger-specific data (e.g., API Gateway query parameters).
- **Log Retention**: CloudWatch Logs are stored indefinitely by default; configure retention policies to manage costs.
- **Performance Insights**: Use the REPORT line to analyze duration and memory usage for optimization.

## Best Practices
- **Log Sparingly**: Avoid excessive logging to reduce CloudWatch costs and improve readability.
- **Structure Logs**: Use JSON formatting for complex data (e.g., `JSON.stringify(event)`).
- **Filter Logs**: Use time-based filters or search queries in CloudWatch to focus on relevant logs.
- **Explore Details**: Review the event object structure in logs to understand trigger-specific data.


# AWS Lambda Challenge: Create a Function with API Gateway Trigger

## Challenge Overview
- **Objective**: Create a new AWS Lambda function triggered by an API Gateway that accepts two numbers via a POST request and returns their sum.
- **Time Limit**: 15 minutes.
- **Requirements**:
  - Use API Gateway to trigger the Lambda function.
  - Accept two input numbers as query string parameters in a POST request.
  - Validate inputs and return the sum or an error message.

## Solution Steps
1. **Create a Lambda Function**:
   - Navigate to the Lambda console in the AWS Management Console.
   - Click **Create Function**, select **Author from Scratch**.
   - Configure:
     - **Function Name**: `Challenge1`.
     - **Runtime**: Node.js 18.x (default).
     - **Architecture**: Default (Intel-based).
     - Skip advanced settings.
   - Click **Create Function**.
2. **Add API Gateway Trigger**:
   - In the Lambda console, go to the function’s **Configuration** tab.
   - Click **Add Trigger**, select **API Gateway**.
   - Create a new API, set security to **Open** (no authentication).
   - Click **Add** to configure the trigger.
3. **Update Function Code**:
   - Navigate to the **Code** tab.
   - Modify the code to:
     - Extract two numbers from `event.queryStringParameters`.
     - Validate the presence of both inputs.
     - Parse strings to integers and compute their sum.
     - Return the result or an error message.
   - Example JavaScript code:
     ```javascript
     exports.handler = async (event) => {
         const queryParams = event.queryStringParameters || {};
         const value1 = queryParams.value1;
         const value2 = queryParams.value2;
         let message = 'Value1 and Value2 are needed';
         
         if (value1 && value2) {
             const num1 = parseInt(value1);
             const num2 = parseInt(value2);
             if (!isNaN(num1) && !isNaN(num2)) {
                 message = `Result: ${num1 + num2}`;
             } else {
                 message = 'Invalid numbers provided';
             }
         }
         
         return {
             statusCode: 200,
             body: JSON.stringify(message)
         };
     };
     ```
   - **Notes**:
     - `parseInt` converts string inputs to integers.
     - Validation checks for missing or non-numeric inputs.
     - Response is a JSON string with the sum or error message.
4. **Deploy the Function**:
   - Click **Deploy** to upload the code to the cloud.
5. **Test with Postman**:
   - Copy the API Gateway endpoint URL from the **Configuration** tab (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/default`).
   - In Postman:
     - Create a new **POST** request.
     - Paste the endpoint URL.
     - Test without parameters:
       - Response: `Value1 and Value2 are needed`.
     - Add query parameters (e.g., `?value1=10&value2=14`):
       - URL: `https://<api-id>.execute-api.<region>.amazonaws.com/default?value1=10&value2=14`.
       - Response: `Result: 24`.
     - Test with missing parameter (e.g., `?value1=10`):
       - Response: `Value1 and Value2 are needed`.
     - Test with invalid input (e.g., `?value1=abc&value2=14`):
       - Response: `Invalid numbers provided`.
   - **Example POST Request Event**:
     ```json
     {
       "httpMethod": "POST",
       "queryStringParameters": {
         "value1": "10",
         "value2": "14"
       },
       "path": "/default"
     }
     ```

## Key Considerations
- **Input Validation**: Ensures both parameters exist and are valid numbers.
- **Query Parameters**: Used instead of request body for simplicity in this challenge.
- **Open API**: No authentication for ease; production APIs should use authorizers.
- **Logging**: Add `console.log` for debugging (not shown but recommended):
  ```javascript
  console.log('Event:', JSON.stringify(event));
  ```

## Best Practices
- **Validate All Inputs**: Prevent errors by checking for missing or invalid data.
- **Use Clear Messages**: Return user-friendly error messages.
- **Test Thoroughly**: Use Postman to verify all edge cases (valid, missing, invalid inputs).
- **Secure APIs**: Add authentication for production environments.
