
# Serverless Computing with AWS Lambda - Course Notes

## Overview
- **Objective**: Learn to build and deploy applications to the cloud using AWS Lambda without managing infrastructure.
- **Benefits**: Pay only for used resources, serverless architecture.
- **Instructor**: Marcia Villalba, Principal Developer Advocate for AWS Serverless.
- **Course Content**:
  - Introduction to AWS Lambda.
  - Use of Infrastructure as Code (IaC).
  - AWS SAM (Serverless Application Model) integration.
  - Best practices for building and deploying serverless applications.

## Prerequisites
- **Course Level**: Intermediate.
- **Required Knowledge**:
  - Basic familiarity with **Node.js** and **JavaScript** (project language).
    - Note: Senior developers with experience in other languages can adapt easily.
    - No prior AWS knowledge required.
  - **Tools Needed**:
    - AWS account (requires credit/debit card and phone for verification).
    - Code editor (e.g., Visual Studio Code, any editor compatible with Windows/Linux/Mac).
  - **AWS Free Tier**:
    - Covers all resources used in the course.
    - Generous offerings for serverless services.

## Example Code Snippet (Node.js Lambda Function)
Below is a basic AWS Lambda function example in Node.js to illustrate the structure used in serverless applications:
```javascript
exports.handler = async (event) => {
    const response = {
        statusCode: 200,
        body: JSON.stringify('Hello from AWS Lambda!')
    };
    return response;
};
```

## Setup Instructions
1. **Create an AWS Account**:
   - Visit AWS website, provide payment details, and verify with a phone call.
2. **Install Development Tools**:
   - Use Visual Studio Code (or preferred editor).
   - Ensure Node.js is installed for local development.
3. **AWS SAM CLI** (for local testing and deployment):
   ```bash
   npm install -g aws-sam-cli
   ```
   - Verify installation:
     ```bash
     sam --version
     ```

## Key Tools and Technologies
- **AWS Lambda**: Serverless compute service for running code without provisioning servers.
- **AWS SAM**: Framework for building serverless applications, simplifies deployment.
- **Node.js/JavaScript**: Primary language for course project.
- **Visual Studio Code**: Recommended IDE, cross-platform support.

## Additional Notes
- The course emphasizes **production standards** for serverless applications.
- Cross-platform compatibility ensures flexibility in development environments.
- AWS SAM templates (YAML) may be used for defining serverless resources, e.g.:
  ```yaml
  Resources:
    HelloWorldFunction:
      Type: AWS::Serverless::Function
      Properties:
        Handler: index.handler
        Runtime: nodejs18.x
        CodeUri: .
  ```