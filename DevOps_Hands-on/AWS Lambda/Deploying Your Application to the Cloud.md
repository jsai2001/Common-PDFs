
# Deploying an AWS SAM Project to the Cloud - Course Notes

## Overview
- **Objective**: Deploy an AWS SAM project containing Lambda functions and API Gateway to the AWS cloud using the SAM CLI.
- **Prerequisites**: SAM project created (e.g., `sam-first-project`), SAM CLI configured, Docker installed, and AWS credentials set up.

## Steps to Deploy the SAM Project
1. **Build the Project**:
   - Run the following command in the project directory:
     ```bash
     sam build
     ```
   - **Purpose**:
     - Copies source files to a temporary subdirectory (`.aws-sam`).
     - Installs dependencies for Lambda functions.
     - Excludes test code and development dependencies.
   - **Output**: Creates a build directory (`.aws-sam`) with function code and a transformed `template.yaml`.
   - **Requirement**: Docker must be running.

2. **Deploy to the Cloud**:
   - Use the guided deployment command for new projects:
     ```bash
     sam deploy --guided
     ```
   - **Guided Configuration**:
     - **Stack Name**: `sam-first-project` (name for the CloudFormation stack).
     - **Region**: e.g., `eu-west-1` (Ireland).
     - **Confirm Changes Before Deploy**: `No` (skip changeset review).
     - **Allow SAM to Create IAM Roles**: `Yes` (auto-create necessary roles).
     - **Disable Rollback**: `No` (enable rollback on failure).
     - **Authentication**: Confirm open APIs (no authentication for learning purposes).
     - **Save Configuration**: Save settings to `samconfig.toml` for future deployments.
   - **Process**:
     - Creates an S3 bucket to store function code.
     - Transforms SAM YAML into CloudFormation template.
     - Deploys resources via CloudFormation.
   - **Output**:
     - Displays CloudFormation events in the terminal.
     - Shows outputs defined in `template.yaml` (e.g., API endpoint URL, function ARN, role ARN).

3. **Monitor Deployment in AWS Console**:
   - Navigate to the **CloudFormation** service in the AWS Management Console.
   - Search for the stack (e.g., `sam-first-project`) in the chosen region.
   - View events, resources, and outputs:
     - **Resources**: Links to created Lambda functions (e.g., `HelloWorldFunction`, `RollADiceFunction`), API Gateway, and IAM roles.
     - **Outputs**: API endpoint, function ARN, and role ARN.
   - Example `template.yaml` outputs:
     ```yaml
     Outputs:
       HelloWorldApi:
         Description: API Gateway endpoint URL
         Value:
           Fn::Sub: https://${ServerlessRestApi}.execute-api.${AWS::Region}.amazonaws.com/Prod/hello/
       HelloWorldFunction:
         Description: Lambda Function ARN
         Value:
           Fn::GetAtt: HelloWorldFunction.Arn
       RollADiceFunction:
         Description: RollADice Function ARN
         Value:
           Fn::GetAtt: RollADiceFunction.Arn
     ```

## Key Considerations
- **Build Before Deploy**: Always run `sam build` to ensure the latest code and dependencies are packaged.
- **S3 Bucket**: SAM creates an S3 bucket for storing Lambda code during deployment.
- **CloudFormation**: SAM uses CloudFormation under the hood, allowing monitoring via the AWS Console.
- **Open APIs**: Lack of authentication is acceptable for learning but not recommended for production.
- **Configuration File**: `samconfig.toml` stores deployment settings for faster future deployments:
  ```toml
  [default.deploy.parameters]
  stack_name = "sam-first-project"
  region = "eu-west-1"
  capabilities = "CAPABILITY_IAM"
  ```

## Best Practices
- **Use Guided Deployment Initially**: Simplifies configuration for new projects.
- **Monitor CloudFormation**: Check the AWS Console for detailed deployment status and error troubleshooting.
- **Secure APIs**: Add authentication (e.g., Cognito, Lambda authorizers) for production.
- **Version Control**: Commit build and deployment changes:
  ```bash
  git add .
  git commit -m "Deployed SAM project to AWS"
  ```
- **Review Outputs**: Save API endpoint URLs and ARNs for testing and integration.


# Running and Testing AWS Lambda Function in the Cloud

## Overview
- **Objective**: Test a deployed AWS SAM Lambda function (`RollADiceFunction`) in the cloud using its API Gateway endpoint.
- **Prerequisites**: AWS SAM project (`sam-first-project`) deployed to the cloud with a Lambda function and API Gateway trigger.

## Steps to Run and Test the Function
1. **Access the Deployed Function**:
   - Navigate to the AWS Lambda console in the AWS Management Console.
   - Select the `RollADiceFunction` (dice-rolling function created in the SAM project).
2. **Locate the API Gateway Endpoint**:
   - In the Lambda console, find the **API Gateway** trigger in the function’s configuration.
   - Click the API Gateway link to retrieve the endpoint URL (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/Prod/dice`).
   - Alternatively, use the endpoint from the SAM `template.yaml` outputs (replace `/hello` with `/dice` as defined in the `RollADiceAPI` event).
   - Example `template.yaml` snippet:
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
     Outputs:
       RollADiceApi:
         Description: API Gateway endpoint URL for dice roll
         Value:
           Fn::Sub: https://${ServerlessRestApi}.execute-api.${AWS::Region}.amazonaws.com/Prod/dice/
     ```
3. **Test the Endpoint with Postman**:
   - Copy the API endpoint URL (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/Prod/dice`).
   - In Postman:
     - Create a new **GET** request.
     - Paste the endpoint URL.
     - Send the request multiple times.
   - **Results**:
     - Responses return random dice rolls (e.g., `{"message": 3}`, `{"message": 5}`, `{"message": 6}`, etc.).
     - Confirms the function is working as expected in the cloud.
   - Example response:
     ```json
     {
       "message": 4
     }
     ```

## Key Considerations
- **Endpoint Path**: The `/dice` path matches the `Path` defined in the SAM `template.yaml` for the `RollADiceAPI` event.
- **No Authentication**: The API is open (no authentication), suitable for testing but not recommended for production.
- **Random Output**: The `RollADiceFunction` generates random numbers (1-6), so responses vary.

## Example Lambda Code
- Reference to the `roll-dice/app.mjs` used in the function:
  ```javascript
  export const lambdaHandler = async (event) => {
      console.log('Roll Dice Run');
      try {
          const result = rollDice(6);
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

## Best Practices
- **Verify Endpoint**: Ensure the correct API path (`/dice`) is used in requests.
- **Test Multiple Times**: Run several requests to validate random output consistency.
- **Secure APIs**: Add authentication (e.g., Cognito, Lambda authorizers) for production use.
- **Monitor Logs**: Check CloudWatch Logs for debugging if issues arise.

# Viewing Lambda Function Logs with AWS SAM

## Overview
- **Objective**: Use the AWS SAM CLI to view and tail CloudWatch logs for a deployed Lambda function (`RollADiceFunction`) directly from the terminal.
- **Purpose**: Streamline debugging by accessing logs within the development environment, avoiding the AWS Lambda console.

## Accessing Logs with SAM CLI
1. **Check Metrics in AWS Console**:
   - Navigate to the Lambda console, select `RollADiceFunction`, and go to the **Monitoring** tab to view metrics (e.g., invocations, duration, errors).
   - CloudWatch logs are available but less convenient when coding.
2. **Use SAM CLI to View Logs**:
   - Run the `sam logs` command to fetch logs for a specific function:
     ```bash
     sam logs --name RollADiceFunction --stack-name sam-first-project --region eu-west-1
     ```
   - **Parameters**:
     - `--name RollADiceFunction`: Function name from `template.yaml`.
     - `--stack-name sam-first-project`: Stack name from `samconfig.toml` or deployment configuration.
     - `--region eu-west-1`: AWS region (e.g., Ireland).
   - **Output**:
     - Displays CloudWatch logs, including `START`, `END`, `REPORT`, and custom logs (e.g., `Roll Dice Run!`).
     - Example log output:
       ```plaintext
       START RequestId: ...
       Roll Dice Run!
       END RequestId: ...
       REPORT Duration: ... ms Billed Duration: ... ms Memory Size: 128 MB Max Memory Used: ... MB
       ```

3. **Tail Logs for Real-Time Monitoring**:
   - Add the `--tail` flag to continuously stream new logs:
     ```bash
     sam logs --name RollADiceFunction --stack-name sam-first-project --region eu-west-1 --tail
     ```
   - **Behavior**:
     - Logs refresh in the terminal as new invocations occur (e.g., after sending requests via Postman or `curl`).
     - Useful for debugging while testing the function in parallel.
   - **Example Workflow**:
     - Run the tail command in one terminal.
     - Send `GET` requests to the API Gateway endpoint (e.g., `https://<api-id>.execute-api.eu-west-1.amazonaws.com/Prod/dice`) in Postman.
     - Observe new logs appearing in the terminal.

## Key Files and References
1. **template.yaml**:
   - Contains the function name (`RollADiceFunction`).
   - Example snippet:
     ```yaml
     Resources:
       RollADiceFunction:
         Type: AWS::Serverless::Function
         Properties:
           CodeUri: roll-dice/
           Handler: app.lambdaHandler
           Runtime: nodejs18.x
     ```
2. **samconfig.toml**:
   - Stores deployment settings, including stack name and region.
   - Example:
     ```toml
     [default.deploy.parameters]
     stack_name = "sam-first-project"
     region = "eu-west-1"
     ```
3. **Function Code (roll-dice/app.mjs)**:
   - Generates logs like `Roll Dice Run!`:
     ```javascript
     export const lambdaHandler = async (event) => {
         console.log('Roll Dice Run');
         try {
             const result = rollDice(6);
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

## Key Considerations
- **Log Source**: Logs are retrieved from CloudWatch, but `sam logs` keeps you in the terminal, improving workflow focus.
- **Parameter Accuracy**: Ensure function name, stack name, and region match `template.yaml` and `samconfig.toml`.
- **Tailing Efficiency**: Use `--tail` for real-time debugging, especially with a dual-screen setup (logs on one side, testing on the other).

## Best Practices
- **Consistent Naming**: Verify function and stack names to avoid log retrieval errors.
- **Enhance Logs**: Add more `console.log` statements for detailed debugging.
- **Version Control**: Commit any log-related code changes:
  ```bash
  git add roll-dice/app.mjs
  git commit -m "Enhanced logging for RollADiceFunction"
  ```
- **Monitor Metrics**: Combine log tailing with console metrics for comprehensive monitoring.

# AWS SAM Challenge: Dynamic Dice Roll Function

## Challenge Overview
- **Objective**: Create and deploy a new AWS Lambda function with an API Gateway trigger that rolls a dice with a variable number of sides, specified via an HTTP GET request query parameter, and test it in the cloud.
- **Time Limit**: 15-20 minutes.
- **Requirements**:
  - Trigger the function with an HTTP GET request using API Gateway.
  - Accept a query string parameter (`sides`) for the number of dice sides.
  - Return the result of rolling a dice with the specified sides.
  - Default to a 6-sided dice if no `sides` parameter is provided or if the input is not a valid number.
  - Example Requests/Responses:
    - Request: `GET <URL>/roll?sides=4` → Response: `"The result is 2"`
    - Request: `GET <URL>/roll` → Response: `"The result is 4"` (6-sided dice default)

## Solution Approach
1. **Update SAM Template**:
   - Add a new Lambda function to `template.yaml` with an API Gateway trigger.
   - Example `template.yaml` snippet:
     ```yaml
     Resources:
       RollVariableDiceFunction:
         Type: AWS::Serverless::Function
         Properties:
           CodeUri: roll-variable-dice/
           Handler: app.lambdaHandler
           Runtime: nodejs18.x
           Events:
             RollVariableDiceAPI:
               Type: Api
               Properties:
                 Path: /roll
                 Method: get
     Outputs:
       RollVariableDiceApi:
         Description: API Gateway endpoint URL for variable dice roll
         Value:
           Fn::Sub: https://${ServerlessRestApi}.execute-api.${AWS::Region}.amazonaws.com/Prod/roll/
     ```

2. **Create Function Code**:
   - Create a new directory `roll-variable-dice` in the SAM project.
   - Add `app.mjs` with logic to parse the `sides` query parameter, validate it, and roll a dice.
   - Example `app.mjs`:
     ```javascript
     export const lambdaHandler = async (event) => {
         console.log('Roll Variable Dice Run');
         try {
             const queryParams = event.queryStringParameters || {};
             let sides = 6; // Default to 6-sided dice
             if (queryParams.sides) {
                 const parsedSides = parseInt(queryParams.sides);
                 if (!isNaN(parsedSides) && parsedSides > 0) {
                     sides = parsedSides;
                 }
             }
             const result = rollDice(sides);
             return {
                 statusCode: 200,
                 body: JSON.stringify(`The result is ${result}`)
             };
         } catch (error) {
             console.error('Error:', error);
             return {
                 statusCode: 500,
                 body: JSON.stringify('Error rolling dice')
             };
         }
     };

     function rollDice(sides) {
         return Math.floor(Math.random() * sides) + 1;
     }
     ```

3. **Build and Deploy**:
   - Build the project:
     ```bash
     sam build
     ```
   - Deploy to the cloud:
     ```bash
     sam deploy --guided
     ```
     - Use existing `samconfig.toml` or configure stack name (e.g., `sam-first-project`), region (e.g., `eu-west-1`), and other settings.

4. **Test in the Cloud**:
   - Retrieve the API endpoint from the deployment outputs (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/Prod/roll`).
   - Test with Postman or `curl`:
     - `GET <URL>/roll?sides=4` → Expected: `"The result is <1-4>"` (e.g., `"The result is 2"`)
     - `GET <URL>/roll` → Expected: `"The result is <1-6>"` (e.g., `"The result is 4"`)
     - `GET <URL>/roll?sides=abc` → Expected: `"The result is <1-6>"` (defaults to 6 sides)
     ```bash
     curl https://<api-id>.execute-api.<region>.amazonaws.com/Prod/roll?sides=4
     ```

## Key Considerations
- **Input Validation**: Ensure `sides` is a positive integer; default to 6 if invalid or absent.
- **API Path**: The `/roll` path is defined in the `Events` section of `template.yaml`.
- **Open API**: No authentication for simplicity; add authorizers for production.
- **Logging**: Use `console.log` for debugging in CloudWatch.

## Best Practices
- **Validate Query Parameters**: Handle edge cases (non-numeric, negative, or missing inputs).
- **Version Control**: Commit changes:
  ```bash
  git add template.yaml roll-variable-dice/
  git commit -m "Added RollVariableDiceFunction for challenge"
  ```
- **Test Extensively**: Verify multiple inputs (valid, invalid, absent) to ensure robustness.
- **Monitor Logs**: Use `sam logs --name RollVariableDiceFunction --stack-name sam-first-project --region <region> --tail` for real-time debugging.


# AWS SAM Challenge Solution: Dynamic Dice Roll Function

## Overview
- **Objective**: Implement a Lambda function (`RollADiceWithSidesFunction`) with an API Gateway trigger to roll a dice with a variable number of sides, deploy it to the cloud, and test it.
- **Requirements**:
  - HTTP GET request with query parameter `sides`.
  - Default to 6-sided dice if `sides` is missing or invalid.
  - Return a message like `"The result is <number>. You rolled a dice of <sides> sides."`

## Solution Steps
1. **Update template.yaml**:
   - Add a new Lambda function resource in `template.yaml`.
   - Example:
     ```yaml
     Resources:
       RollADiceWithSidesFunction:
         Type: AWS::Serverless::Function
         Properties:
           CodeUri: roll-dice-sides/
           Handler: app.lambdaHandler
           Runtime: nodejs18.x
           Events:
             RollADiceAPI:
               Type: Api
               Properties:
                 Path: /roll
                 Method: get
     Outputs:
       RollADiceApi:
         Description: API Gateway endpoint URL
         Value:
           Fn::Sub: https://${ServerlessRestApi}.execute-api.${AWS::Region}.amazonaws.com/Prod/roll/
     ```

2. **Create Function Code**:
   - Create a directory `roll-dice-sides` in the project.
   - Add `app.mjs` with logic to handle the `sides` query parameter and roll the dice.
   - Example:
     ```javascript
     export const lambdaHandler = async (event) => {
         console.log('Roll Dice with Sides run!');
         try {
             let sides = 6; // Default to 6 sides
             const queryParams = event.queryStringParameters || {};
             if (queryParams.sides) {
                 const parsedSides = parseInt(queryParams.sides);
                 if (!isNaN(parsedSides) && parsedSides > 0) {
                     sides = parsedSides;
                 }
             }
             const result = rollDice(sides);
             const message = `The result is ${result}. You rolled a dice of ${sides} sides.`;
             return {
                 statusCode: 200,
                 body: JSON.stringify(message)
             };
         } catch (error) {
             console.error('Error:', error);
             return {
                 statusCode: 500,
                 body: JSON.stringify('Error rolling dice')
             };
         }
     };

     function rollDice(sides) {
         return Math.floor(Math.random() * sides) + 1;
     }
     ```

3. **Test Locally**:
   - Build the project:
     ```bash
     sam build
     ```
   - Start a local API server:
     ```bash
     sam local start-api
     ```
   - Test with `curl` in another terminal:
     ```bash
     curl http://localhost:3000/roll
     curl http://localhost:3000/roll?sides=4
     curl http://localhost:3000/roll?sides=20
     ```
   - Example responses:
     - `The result is 3. You rolled a dice of 6 sides.`
     - `The result is 2. You rolled a dice of 4 sides.`
     - `The result is 15. You rolled a dice of 20 sides.`

4. **Deploy to the Cloud**:
   - Deploy using existing configuration:
     ```bash
     sam deploy
     ```
   - **Notes**:
     - If `samconfig.toml` exists (from previous deployments), `--guided` is unnecessary.
     - Example `samconfig.toml`:
       ```toml
       [default.deploy.parameters]
       stack_name = "sam-first-project"
       region = "eu-west-1"
       capabilities = "CAPABILITY_IAM"
       ```
   - Monitor deployment in the AWS CloudFormation console under the `sam-first-project` stack.

5. **Test in the Cloud**:
   - Retrieve the API endpoint from CloudFormation outputs (e.g., `https://<api-id>.execute-api.<region>.amazonaws.com/Prod/roll`).
   - Test with Postman:
     - `GET <URL>/roll` → `The result is 4. You rolled a dice of 6 sides.`
     - `GET <URL>/roll?sides=20` → `The result is 15. You rolled a dice of 20 sides.`
     - `GET <URL>/roll?sides=4` → `The result is 3. You rolled a dice of 4 sides.`
   - Check logs with:
     ```bash
     sam logs --name RollADiceWithSidesFunction --stack-name sam-first-project --region eu-west-1 --tail
     ```

## Key Considerations
- **Input Validation**: Ensures `sides` is a positive integer; defaults to 6 if invalid.
- **Reusing API Gateway**: The same API Gateway is used for multiple functions (`/hello`, `/roll`).
- **Logging**: `console.log` helps verify execution in CloudWatch.

## Best Practices
- **Validate Inputs**: Handle invalid or missing query parameters robustly.
- **Version Control**: Commit changes:
  ```bash
  git add template.yaml roll-dice-sides/
  git commit -m "Implemented RollADiceWithSidesFunction"
  ```
- **Test Locally First**: Use `sam local start-api` to validate before deploying.
- **Monitor Deployment**: Watch CloudFormation events for errors.