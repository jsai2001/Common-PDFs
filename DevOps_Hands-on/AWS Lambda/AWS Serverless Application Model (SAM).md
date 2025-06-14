# Introduction to AWS SAM

## Overview
- **Objective**: Understand the AWS Serverless Application Model (AWS SAM) for building and deploying serverless applications using Infrastructure as Code (IaC).
- **Purpose**: Streamline the creation, management, and deployment of serverless applications for production environments.

## Why Use AWS SAM?
- **Challenges with Console-Based Development**:
  - Building serverless applications directly in the AWS Management Console is error-prone and tedious.
  - Not suitable for production due to lack of maintainability and replicability across environments (e.g., testing, development, production).
- **Benefits of Infrastructure as Code (IaC)**:
  - Maintainable and reproducible processes for deploying applications across multiple environments.
  - Enables consistent deployments in different AWS accounts.

## AWS SAM Overview
- **Definition**: An open-source framework for building serverless applications, built on top of AWS CloudFormation.
- **Components**:
  1. **SAM Transform Templates**:
     - Use YAML to define serverless resources (e.g., Lambda functions, API Gateway, DynamoDB tables) with simplified syntax.
     - Transforms into CloudFormation templates during deployment.
     - Example SAM template snippet:
       ```yaml
       Resources:
         MyLambdaFunction:
           Type: AWS::Serverless::Function
           Properties:
             Handler: index.handler
             Runtime: nodejs18.x
             CodeUri: .
             Events:
               ApiEvent:
                 Type: Api
                 Properties:
                   Path: /hello
                   Method: GET
       ```
  2. **SAM Command Line Interface (CLI)**:
     - Deploys SAM templates to the cloud.
     - Supports local testing and CI/CD integration.
     - Example CLI command to deploy:
       ```bash
       sam deploy --guided
       ```
- **CloudFormation Integration**:
  - SAM templates extend CloudFormation, allowing use of CloudFormation syntax for additional customization.
  - Ensures maintainability and portability across AWS accounts.

## Key Features of AWS SAM
1. **SAM Local**:
   - Enables local testing and debugging of serverless applications.
   - Emulates AWS services (e.g., Lambda, API Gateway) on your local machine.
   - Example command to test locally:
     ```bash
     sam local invoke MyLambdaFunction -e event.json
     ```
2. **SAM Accelerate**:
   - Speeds up development by enabling parallel builds and updates without full redeployment.
   - Aggregates logs in the terminal for easier debugging.
   - Example command to sync code changes:
     ```bash
     sam sync --stack-name my-stack
     ```
3. **CI/CD Integration**:
   - Integrates with AWS CodeBuild, CodeDeploy, and CodePipeline.
   - Supports creating CI/CD pipelines with the `sam pipeline` command.
   - Example command to initialize a pipeline:
     ```bash
     sam pipeline init
     ```

## Use Cases
- Define and deploy serverless resources (Lambda functions, APIs, databases) as a single entity.
- Test applications locally before deploying to the cloud.
- Automate deployments across multiple environments using CI/CD pipelines.

## Best Practices
- **Use SAM for Production**: Avoid console-based configurations for maintainable, repeatable deployments.
- **Leverage Local Testing**: Use SAM Local to validate functionality before deploying.
- **Integrate with CI/CD**: Automate deployments to ensure consistency across environments.
- **Refer to Documentation**: Check the AWS SAM product page for advanced features and details.

# Why Infrastructure as Code is Important

## Overview
- **Objective**: Understand the importance of Infrastructure as Code (IaC) for building and managing serverless applications in production.
- **Purpose**: Highlight how IaC improves reliability, scalability, and maintainability of cloud infrastructure.

## What is Infrastructure as Code (IaC)?
- **Definition**: Using high-level programming languages (e.g., YAML, JSON) to define and manage IT infrastructure.
- **Key Concept**: Treat infrastructure like application code, applying practices such as testing, code reviews, and version control.

## Advantages of Infrastructure as Code
1. **Programmatic Management**:
   - Eliminates manual configuration in the cloud provider’s console, reducing human errors.
   - Example: Define a Lambda function in AWS SAM YAML instead of clicking through the AWS Console.
2. **Replicability**:
   - Infrastructure scripts can be reused across multiple environments (e.g., development, testing, production).
   - Example SAM template for a Lambda function:
     ```yaml
     Resources:
       MyLambdaFunction:
         Type: AWS::Serverless::Function
         Properties:
           Handler: index.handler
           Runtime: nodejs18.x
           CodeUri: .
     ```
3. **Version Control**:
   - Store infrastructure scripts in a code repository (e.g., Git).
   - Track changes, including who made them and why, improving accountability.
   - Example Git command to commit IaC:
     ```bash
     git add template.yaml
     git commit -m "Added Lambda function definition"
     ```
4. **Scalability**:
   - A single employee can manage large-scale infrastructure using scripts.
   - Reusable components speed up development across projects.
5. **Quality and Security**:
   - Apply code review and automated testing to infrastructure, reducing bugs.
   - Evolve infrastructure with enhanced security practices.
6. **Development Speed**:
   - Reuse existing infrastructure templates, accelerating project setup.

## IaC with AWS SAM
- **Context**: AWS Serverless Application Model (SAM) uses YAML to define serverless resources (e.g., Lambda functions, API Gateway, DynamoDB tables).
- **Benefits**:
  - No need to manually configure resources in the AWS Console.
  - Store SAM templates in a repository for version control and replication across AWS accounts.
  - Example SAM template for a serverless application:
    ```yaml
    AWSTemplateFormatVersion: '2010-09-09'
    Transform: AWS::Serverless-2016-10-31
    Resources:
      MyApi:
        Type: AWS::Serverless::Api
        Properties:
          StageName: prod
          DefinitionBody:
            swagger: '2.0'
            paths:
              /hello:
                get:
                  x-amazon-apigateway-integration:
                    uri:
                      Fn::Sub: arn:aws:apigateway:${AWS::Region}:lambda:path/2015-03-31/functions/${MyLambdaFunction.Arn}/invocations
                    httpMethod: POST
      MyLambdaFunction:
        Type: AWS::Serverless::Function
        Properties:
          Handler: index.handler
          Runtime: nodejs18.x
          CodeUri: .
          Events:
            ApiEvent:
              Type: Api
              Properties:
                RestApiId: !Ref MyApi
                Path: /hello
                Method: GET
    ```
- **Deployment**: Use SAM CLI to deploy the template to the cloud.
  ```bash
  sam deploy --guided
  ```

## Why IaC is Fundamental
- **Use Cases**: Essential for cloud development, microservices, and serverless architectures.
- **Error Reduction**: Minimizes mistakes from manual console configurations.
- **Consistency**: Ensures identical infrastructure across environments.

## Best Practices
- **Store IaC in Repositories**: Use version control systems like Git for tracking and collaboration.
- **Apply Code Reviews**: Validate infrastructure changes before deployment.
- **Test Infrastructure**: Use automated tests to verify IaC scripts.
- **Reuse Templates**: Leverage existing SAM templates to speed up development.


# Installing and Configuring AWS SAM

## Overview
- **Objective**: Install and configure the AWS Serverless Application Model (SAM) CLI to develop serverless applications.
- **Purpose**: Enable local development, testing, and deployment of serverless applications using Infrastructure as Code (IaC).

## Prerequisites
1. **AWS Account**:
   - Active AWS account with an IAM admin user.
2. **IAM Admin User**:
   - Created with access key and secret access key (downloaded as a CSV file in earlier steps).
3. **AWS CLI**:
   - Install the AWS CLI for your operating system (Windows, macOS, Linux).
   - Instructions: Follow the AWS CLI installation guide (available on the AWS website).
   - Verify installation:
     ```bash
     aws --version
     ```
     - Expected output: Displays the installed AWS CLI version (e.g., `aws-cli/2.x.x`).
4. **Configure AWS CLI**:
   - Set up AWS credentials for the IAM admin user.
   - Run:
     ```bash
     aws configure
     ```
   - Input:
     - **Access Key ID**: From the IAM user CSV.
     - **Secret Access Key**: From the IAM user CSV.
     - **Default Region**: e.g., `us-east-1`.
     - **Output Format**: e.g., `json`.
   - Example configuration file (`~/.aws/credentials`):
     ```ini
     [default]
     aws_access_key_id = AKIAIOSFODNN7EXAMPLE
     aws_secret_access_key = wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
     ```
5. **Docker**:
   - Required for SAM Local to emulate AWS services.
   - Install Docker if not already present (instructions vary by operating system).
   - Verify installation:
     ```bash
     docker --version
     ```

## Installing AWS SAM CLI
1. **Follow Installation Instructions**:
   - Refer to the AWS SAM CLI installation guide (linked in the SAM prerequisites webpage).
   - Installation steps vary by operating system:
     - **Windows**: Use MSI installer or package manager.
     - **macOS**: Use Homebrew or manual installation.
     - **Linux**: Use package manager or manual installation.
   - Example for macOS using Homebrew:
     ```bash
     brew tap aws/tap
     brew install aws-sam-cli
     ```
2. **Verify Installation**:
   - Run:
     ```bash
     sam --version
     ```
   - Expected output: Displays the installed SAM CLI version (e.g., `SAM CLI, version 1.x.x`).

## Key Considerations
- **Operating System Variability**: Ensure you follow the correct installation steps for your OS.
- **Credentials Security**: Store access keys securely and avoid sharing them.
- **Docker Requirement**: SAM Local relies on Docker for local emulation; ensure Docker is running.

## Best Practices
- **Validate Prerequisites**: Confirm AWS CLI, credentials, and Docker are set up before installing SAM.
- **Keep Tools Updated**: Regularly check for updates to AWS CLI, SAM CLI, and Docker.
- **Use Version Control**: Store SAM project files in a repository for maintainability.

# Creating an AWS SAM Project

## Overview
- **Objective**: Create a new AWS Serverless Application Model (SAM) project to build a serverless application.
- **Prerequisites**: AWS SAM CLI installed, AWS CLI configured with IAM admin user credentials, and Docker installed.

## Steps to Create a SAM Project
1. **Initialize the Project**:
   - Open a terminal and run:
     ```bash
     sam init
     ```
   - Follow the guided initialization process:
     - **Template Source**: Select option `1` (AWS Quick Start Templates).
     - **Template**: Choose `Hello World Example` (option `1`).
     - **Runtime**: Select `Node.js 18` (option `11`, or choose another runtime like Python, Java, etc.).
     - **Package Type**: Choose `Zip` (option `1`, traditional Lambda packaging, not container image).
     - **Language**: Select `JavaScript` (option `1`, or choose TypeScript for option `2`).
     - **X-Ray Tracing**: Select `No` (disable for simplicity).
     - **CloudWatch Application Insights**: Select `No` (disable for simplicity).
     - **Project Name**: Enter `sam-first-project`.
   - SAM clones the template from GitHub and creates the project directory.
2. **Explore the Project Structure**:
   - Navigate to the project directory:
     ```bash
     cd sam-first-project
     ls
     ```
   - Key files and directories:
     - **README.md**: Contains project details, setup instructions, and usage information.
     - **template.yaml**: Defines AWS resources (e.g., Lambda functions, API Gateway).
     - **hello-world/app.js**: Contains the Lambda function code.

## Key Project Components
1. **README.md**:
   - Provides an overview of the project, including setup, testing, and deployment instructions.
   - Refer to it for additional details not covered in the video.
2. **template.yaml**:
   - Defines the serverless application’s infrastructure using AWS SAM syntax.
   - Key sections:
     - **Description**: Project overview.
     - **Globals**: Global configurations (e.g., function timeout of 3 seconds).
     - **Resources**: Defines a Lambda function (`HelloWorldFunction`) and API Gateway trigger.
     - **Outputs**: Exports the API endpoint URL, Lambda function ARN, and IAM role ARN.
   - Example `template.yaml`:
     ```yaml
     AWSTemplateFormatVersion: '2010-09-09'
     Transform: AWS::Serverless-2016-10-31
     Description: A simple SAM project
     Globals:
       Function:
         Timeout: 3
     Resources:
       HelloWorldFunction:
         Type: AWS::Serverless::Function
         Properties:
           CodeUri: hello-world/
           Handler: app.lambdaHandler
           Runtime: nodejs18.x
           Architectures:
             - x86_64
           Events:
             HelloWorld:
               Type: Api
               Properties:
                 Path: /hello
                 Method: get
     Outputs:
       HelloWorldApi:
         Description: API Gateway endpoint URL
         Value:
           Fn::Sub: https://${ServerlessRestApi}.execute-api.${AWS::Region}.amazonaws.com/Prod/hello/
       HelloWorldFunction:
         Description: Lambda Function ARN
         Value:
           Fn::GetAtt: HelloWorldFunction.Arn
       HelloWorldFunctionIamRole:
         Description: Implicit IAM Role ARN
         Value:
           Fn::GetAtt: HelloWorldFunctionRole.Arn
     ```
3. **hello-world/app.js**:
   - Contains the Lambda function handler.
   - Returns a simple HTTP response with status code 200.
   - Example `app.js`:
     ```javascript
     exports.lambdaHandler = async (event) => {
         const response = {
             statusCode: 200,
             body: JSON.stringify({
                 message: 'Hello from Lambda!'
             })
         };
         return response;
     };
     ```

## Key Considerations
- **SAM Init Customization**: Allows selection of runtime, package type, and additional features (e.g., tracing, monitoring).
- **Template.yaml Structure**: Central to defining resources; extends AWS CloudFormation for flexibility.
- **Local Testing**: The README includes instructions for local testing with `sam local`, to be covered later.
- **Deployment**: Deployment steps (e.g., `sam deploy`) will be discussed in future videos.

## Best Practices
- **Read the README**: Always review the README for project-specific guidance.
- **Version Control**: Initialize a Git repository for the project:
  ```bash
  git init
  git add .
  git commit -m "Initial SAM project setup"
  ```
- **Validate Configuration**: Ensure the selected runtime and package type match your development needs.
- **Keep Templates Simple**: Start with minimal configurations and add complexity as needed.

Since you mentioned wanting to use Python instead of Node.js for the AWS SAM project described in the transcript, I'll update the notes to reflect creating the same SAM project using Python as the runtime. The artifact will retain the same `artifact_id` as the previous one (since it's an update to the same project) and provide a revised SAM project setup with Python-specific details, including updated code snippets for the Lambda function and SAM template.

# Creating an AWS SAM Project with Python

## Overview
- **Objective**: Create a new AWS Serverless Application Model (SAM) project using Python as the runtime to build a serverless application.
- **Prerequisites**: AWS SAM CLI installed, AWS CLI configured with IAM admin user credentials, and Docker installed.

## Steps to Create a SAM Project with Python
1. **Initialize the Project**:
   - Open a terminal and run:
     ```bash
     sam init
     ```
   - Follow the guided initialization process:
     - **Template Source**: Select option `1` (AWS Quick Start Templates).
     - **Template**: Choose `Hello World Example` (option `1`).
     - **Runtime**: Select `python3.9` (or another Python version, e.g., `python3.10`, depending on availability; typically option `3` or similar).
     - **Package Type**: Choose `Zip` (option `1`, traditional Lambda packaging, not container image).
     - **Dependency Manager**: Select `pip` (option `1`, standard for Python).
     - **X-Ray Tracing**: Select `No` (disable for simplicity).
     - **CloudWatch Application Insights**: Select `No` (disable for simplicity).
     - **Project Name**: Enter `sam-first-project`.
   - SAM clones the template from GitHub and creates the project directory.
2. **Explore the Project Structure**:
   - Navigate to the project directory:
     ```bash
     cd sam-first-project
     ls
     ```
   - Key files and directories:
     - **README.md**: Contains project details, setup instructions, and usage information.
     - **template.yaml**: Defines AWS resources (e.g., Lambda functions, API Gateway).
     - **hello_world/app.py**: Contains the Python Lambda function code.
     - **hello_world/requirements.txt**: Lists Python dependencies (empty by default for this template).

## Key Project Components
1. **README.md**:
   - Provides an overview of the project, including setup, testing, and deployment instructions.
   - Refer to it for additional details not covered in the video.
2. **template.yaml**:
   - Defines the serverless application’s infrastructure using AWS SAM syntax.
   - Key sections:
     - **Description**: Project overview.
     - **Globals**: Global configurations (e.g., function timeout of 3 seconds).
     - **Resources**: Defines a Lambda function (`HelloWorldFunction`) and API Gateway trigger.
     - **Outputs**: Exports the API endpoint URL, Lambda function ARN, and IAM role ARN.
   - Example `template.yaml`:
     ```yaml
     AWSTemplateFormatVersion: '2010-09-09'
     Transform: AWS::Serverless-2016-10-31
     Description: A simple SAM project
     Globals:
       Function:
         Timeout: 3
     Resources:
       HelloWorldFunction:
         Type: AWS::Serverless::Function
         Properties:
           CodeUri: hello_world/
           Handler: app.lambda_handler
           Runtime: python3.9
           Architectures:
             - x86_64
           Events:
             HelloWorld:
               Type: Api
               Properties:
                 Path: /hello
                 Method: get
     Outputs:
       HelloWorldApi:
         Description: API Gateway endpoint URL
         Value:
           Fn::Sub: https://${ServerlessRestApi}.execute-api.${AWS::Region}.amazonaws.com/Prod/hello/
       HelloWorldFunction:
         Description: Lambda Function ARN
         Value:
           Fn::GetAtt: HelloWorldFunction.Arn
       HelloWorldFunctionIamRole:
         Description: Implicit IAM Role ARN
         Value:
           Fn::GetAtt: HelloWorldFunctionRole.Arn
     ```
3. **hello_world/app.py**:
   - Contains the Python Lambda function handler.
   - Returns a simple HTTP response with status code 200.
   - Example `app.py`:
     ```python
     import json

     def lambda_handler(event, context):
         return {
             'statusCode': 200,
             'body': json.dumps({
                 'message': 'Hello from Lambda!'
             })
         }
     ```
   - **Notes**:
     - The handler is named `lambda_handler` and located in `app.py` (specified as `app.lambda_handler` in `template.yaml`).
     - Uses `json.dumps` to serialize the response body.
4. **hello_world/requirements.txt**:
   - Lists Python dependencies (empty for this basic example but can include libraries like `boto3` for AWS SDK).
   - Example (if adding dependencies later):
     ```text
     boto3==1.26.0
     ```

## Key Considerations
- **Python Runtime**: Select a supported Python version (e.g., `python3.9`, `python3.10`) compatible with AWS Lambda.
- **SAM Init Customization**: Allows selection of runtime, package type, and dependency manager (pip for Python).
- **Template.yaml Structure**: Defines resources using SAM syntax, extensible with CloudFormation.
- **Local Testing**: The README includes instructions for local testing with `sam local`, to be covered later.
   - Example local test command:
     ```bash
     sam local invoke HelloWorldFunction -e events/event.json
     ```
- **Deployment**: Deployment steps (e.g., `sam deploy`) will be discussed in future videos.
   - Example deployment command:
     ```bash
     sam deploy --guided
     ```

## Best Practices
- **Read the README**: Review the README for project-specific guidance.
- **Version Control**: Initialize a Git repository for the project:
  ```bash
  git init
  git add .
  git commit -m "Initial SAM project setup with Python"
  ```
- **Validate Dependencies**: Ensure `requirements.txt` includes necessary libraries and is compatible with Lambda.
- **Keep Templates Simple**: Start with minimal configurations and add complexity as needed.
- **Test Locally**: Use `sam local` to validate the function before deploying.

AWS SAM is ideal for creating AWS Lambda functions because it offers a simple, serverless-focused YAML syntax, built-in local testing with `sam local`, and seamless AWS integration, making it faster and less error-prone than Terraform, which is better for multi-cloud or complex infrastructure but requires more detailed configuration.

# Lambda Functions with AWS SAM

## Overview
- **Objective**: Build and test an AWS Lambda function locally using AWS SAM, including invoking the function and simulating API Gateway calls.
- **Prerequisites**: AWS SAM project created (e.g., `sam-first-project`), Docker installed and running, SAM CLI configured.

## Building the SAM Application
1. **Run SAM Build**:
   - Execute the following command in the project directory:
     ```bash
     sam build
     ```
   - **Result**: Creates a `.aws-sam` directory containing build artifacts.
   - **Requirement**: Docker must be installed and running to build the Lambda function’s runtime environment.
2. **Purpose**:
   - Prepares the application for local testing or deployment by packaging code and dependencies.

## Local Testing with SAM
1. **Invoke Lambda Function Locally**:
   - Use a test event provided by SAM (located in `events/event.json`).
   - Example `event.json` (simulates an API Gateway event):
     ```json
     {
       "httpMethod": "GET",
       "path": "/hello",
       "queryStringParameters": {}
     }
     ```
   - Run the local invoke command:
     ```bash
     sam local invoke HelloWorldFunction -e events/event.json
     ```
   - **Notes**:
     - `HelloWorldFunction` is the function name defined in `template.yaml`.
     - First invocation builds a Docker container image, which may take time; subsequent invocations are faster.
   - **Output**:
     - Logs similar to AWS CloudWatch, including:
       - Function start/end.
       - Custom logs (e.g., `console.log` output).
       - Execution report (duration, billed duration).
       - Response: `{"statusCode": 200, "body": "{\"message\": \"hello world\"}"}`.

2. **Modify and Retest**:
   - Update the Lambda function code in `hello-world/app.js`:
     ```javascript
     exports.lambdaHandler = async (event) => {
         console.log('Hello');
         const response = {
             statusCode: 200,
             body: JSON.stringify({
                 message: 'hello LinkedIn'
             })
         };
         return response;
     };
     ```
   - Rebuild and reinvoke:
     ```bash
     sam build
     sam local invoke HelloWorldFunction -e events/event.json
     ```
   - **Output**:
     - Logs show `Hello` from `console.log`.
     - Updated response: `{"statusCode": 200, "body": "{\"message\": \"hello LinkedIn\"}"}`.

3. **Simulate API Gateway Locally**:
   - Start a local API server:
     ```bash
     sam local start-api
     ```
   - **Result**: Runs a local server on `http://localhost:3000`.
   - Test the API using `curl` or Postman:
     ```bash
     curl http://localhost:3000/hello
     ```
   - **Expected Response**: `{"message": "hello LinkedIn"}`.
   - **Notes**:
     - The `/hello` path is defined in `template.yaml` under the function’s `Events` section.
     - Enables testing API calls without deploying to AWS.

## Key Files
1. **template.yaml**:
   - Defines the Lambda function and API Gateway trigger.
   - Example snippet:
     ```yaml
     Resources:
       HelloWorldFunction:
         Type: AWS::Serverless::Function
         Properties:
           CodeUri: hello-world/
           Handler: app.lambdaHandler
           Runtime: nodejs18.x
           Events:
             HelloWorld:
               Type: Api
               Properties:
                 Path: /hello
                 Method: get
     ```
2. **hello-world/app.js**:
   - Contains the Lambda handler code (shown above).

## Key Considerations
- **Docker Dependency**: Required for `sam build` and `sam local` to emulate Lambda’s runtime.
- **Event Structure**: The `event.json` mimics real AWS service events (e.g., API Gateway).
- **Local Testing Benefits**: Allows rapid development and testing without incurring AWS costs or deploying to the cloud.

## Best Practices
- **Validate Event Files**: Ensure `event.json` matches the expected trigger format.
- **Use Version Control**: Commit changes to `app.js` and `template.yaml`:
  ```bash
  git add .
  git commit -m "Updated Lambda function for local testing"
  ```
- **Monitor Logs**: Check local logs for debugging, similar to CloudWatch.
- **Test Multiple Scenarios**: Use different `event.json` files to simulate various triggers.


# Creating a Lambda Function and API Gateway with AWS SAM

## Overview
- **Objective**: Create a new AWS Lambda function with an API Gateway trigger from scratch in an AWS SAM project and test it locally.
- **Purpose**: Demonstrate how to extend a SAM project by adding a new function and integrating it with API Gateway.

## Steps to Create a New Lambda Function
1. **Update template.yaml**:
   - Add a new Lambda function resource under `Resources` in `template.yaml`.
   - Example configuration for a dice-rolling function:
     ```yaml
     Resources:
       RollADiceFunction:
         Type: AWS::Serverless::Function
         Properties:
           CodeUri: roll-dice/
           Handler: app.lambdaHandler
           Runtime: nodejs18.x
           Events:
             RollADiceAPI:
               Type: Api
               Properties:
                 Path: /dice
                 Method: get
     ```
   - **Notes**:
     - `CodeUri: roll-dice/`: Specifies the directory containing the function code.
     - `Handler: app.lambdaHandler`: Points to the `lambdaHandler` function in `app.mjs`.
     - `Events`: Defines an API Gateway trigger at `/dice` with `GET` method.
     - SAM automatically creates the API Gateway resource; no separate definition needed for simple integrations.

2. **Create Function Code**:
   - Create a new directory `roll-dice` in the project.
   - Add a file `app.mjs` with the Lambda handler and dice-rolling logic.
   - Example `app.mjs`:
     ```javascript
     export const lambdaHandler = async (event) => {
         console.log('Roll Dice Run');
         try {
             const result = rollDice(6); // 6-sided dice
             return {
                 statusCode: 200,
                 body: JSON.stringify({ message: result })
             };
         } catch (error) {
             console.error('Error:', error);
             return {
                 statusCode: 500,
                 body: JSON.stringify({ message: 'Error rolling dice' })
             };
         }
     };

     function rollDice(sides) {
         return Math.floor(Math.random() * sides) + 1;
     }
     ```
   - **Notes**:
     - `console.log('Roll Dice Run')`: Logs for debugging.
     - `rollDice(6)`: Generates a random number from 1 to 6.
     - Error handling ensures robust execution.
     - Uses ES modules (`export`, `.mjs`) for Node.js compatibility.

3. **Build the Application**:
   - Run:
     ```bash
     sam build
     ```
   - **Result**: Builds the function and creates artifacts in `.aws-sam`.
   - **Requirement**: Docker must be running.

4. **Test Locally with Lambda Invocation**:
   - Use the existing `events/event.json` (API Gateway event):
     ```json
     {
       "httpMethod": "GET",
       "path": "/dice",
       "queryStringParameters": {}
     }
     ```
   - Invoke the function:
     ```bash
     sam local invoke RollADiceFunction -e events/event.json
     ```
   - **Output** (example):
     ```plaintext
     START RequestId: ...
     Roll Dice Run
     END RequestId: ...
     REPORT Duration: ... ms
     {"statusCode":200,"body":"{\"message\":2}"}
     ```
   - **Notes**: The response shows a random dice roll (e.g., 2).

5. **Test Locally with API Gateway**:
   - Start a local API server:
     ```bash
     sam local start-api
     ```
   - Test the `/dice` endpoint using `curl` or Postman:
     ```bash
     curl http://localhost:3000/dice
     ```
   - **Expected Response**: `{"message": <random_number>}` (e.g., `{"message": 4}`).
   - **Notes**: The local server runs on `http://localhost:3000`, and `/dice` matches the path in `template.yaml`.

## Key Considerations
- **YAML Indentation**: Ensure correct indentation in `template.yaml` to avoid parsing errors.
- **Implicit API Gateway**: SAM creates the API Gateway automatically for simple event triggers.
- **Event Reuse**: The default `event.json` works since the function doesn’t process query parameters.
- **Docker Dependency**: Required for building and local testing.

## Best Practices
- **Log for Debugging**: Include `console.log` statements to track execution in CloudWatch or local logs.
- **Error Handling**: Use try-catch to manage runtime errors gracefully.
- **Version Control**: Commit changes:
  ```bash
  git add template.yaml roll-dice/
  git commit -m "Added RollADiceFunction with API Gateway trigger"
  ```
- **Test Multiple Cases**: Run `sam local invoke` multiple times to verify random dice rolls.