
# Securing Your AWS Account

## Overview
- **Objective**: Learn essential steps to secure a new AWS account.
- **Key Focus**: Prioritize security from the moment the account is created to prevent unauthorized access and potential financial loss.

## AWS Root User
- **Definition**: 
  - Created automatically when opening an AWS account.
  - Has full access to all AWS services and resources.
  - Accessed using the email and password used during account creation.
- **Security Recommendation**:
  - **Avoid using the root user** for daily operations.
  - Store root user credentials in a **secure location** (e.g., a password manager or physical safe).
  - Use root credentials only for critical tasks that explicitly require them.

## Risks of Using Root User
- **Potential Issues**:
  - If root credentials are stolen, an attacker can:
    - Change the password, locking you out.
    - Access all services, potentially incurring charges on the linked credit card.
  - Recovery without secure root credential storage can be difficult or impossible.
- **Consequences**:
  - Financial loss due to unauthorized charges.
  - Significant effort required to regain account control.

## Best Practice: Use IAM Users
- **IAM User Overview**:
  - Create an **IAM admin user** with sufficient privileges for daily operations.
  - Use IAM users with specific, limited permissions for other tasks.
- **Benefits of IAM Users**:
  - Reduces reliance on root user, minimizing risk.
  - If IAM admin credentials are compromised:
    - Log in with root user (from secure storage).
    - Delete the compromised IAM user.
    - Create a new IAM admin user to restore control.
- **Outcome**: Limits damage from stolen credentials and simplifies recovery.

## Steps to Secure Your AWS Account
1. **Create an AWS Account**:
   - Requires email, password, credit/debit card, and phone verification.
2. **Secure Root Credentials**:
   - Store securely (e.g., encrypted password manager or physical safe).
   - Example of secure storage setup (conceptual, no actual code needed):
     ```plaintext
     # Example: Store in a password manager
     Root User Email: your-email@example.com
     Root User Password: <strong-random-password>
     Storage: Encrypted vault (e.g., LastPass, 1Password)
     ```
3. **Create an IAM Admin User**:
   - Use the AWS Management Console to create an IAM user with administrative privileges.
   - Example IAM policy for admin access (simplified):
     ```json
     {
       "Version": "2012-10-17",
       "Statement": [
         {
           "Effect": "Allow",
           "Action": "*",
           "Resource": "*"
         }
       ]
     }
     ```
4. **Use IAM Users for Daily Operations**:
   - Assign specific permissions to other IAM users as needed.
   - Example: Create a read-only IAM user for monitoring:
     ```json
     {
       "Version": "2012-10-17",
       "Statement": [
         {
           "Effect": "Allow",
           "Action": [
             "s3:GetObject",
             "s3:ListBucket"
           ],
           "Resource": "*"
         }
       ]
     }
     ```

# Basics of AWS IAM

## Overview
- **Objective**: Understand the core features of AWS Identity and Access Management (IAM) to securely manage access to AWS resources.
- **IAM Definition**: Identity and Access Management, a service that controls access to AWS resources for users and services.
- **Focus**: IAM users, their properties, and how to organize and secure access.

## Key IAM Concepts
- **IAM Users**:
  - Entities representing individuals or applications that interact with AWS resources.
  - Defined by permissions specifying what actions they can perform on which resources.
- **Groups**:
  - Used to organize IAM users for easier permission management.
  - Assign permissions to a group, and all users in the group inherit those permissions.
  - Example: Create a "Developers" group with access to specific services for team members.
- **Policies**:
  - JSON documents defining permissions for users or groups.
  - By default, IAM users and groups have **no permissions** until policies are attached.
  - **Policy Structure**:
    - **Effect**: Specifies if the policy `Allow` or `Deny` an action.
    - **Action**: Defines the API actions permitted (e.g., `dynamodb:GetItem`).
    - **Resource**: Specifies the AWS resource affected (e.g., a specific DynamoDB table).
  - **Best Practice**: Use fine-grained permissions to enhance security.

## Example IAM Policy
Below is a sample IAM policy granting read access to a specific DynamoDB table:
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "dynamodb:GetItem",
      "Resource": "arn:aws:dynamodb:us-east-1:123456789012:table/MyTable"
    }
  ]
}
```
- **Explanation**:
  - `Effect: Allow` permits the action.
  - `Action: dynamodb:GetItem` allows retrieving data from a DynamoDB table.
  - `Resource` specifies the exact table using its ARN (Amazon Resource Name).

## Fine-Grained Permissions
- **Why Fine-Grained?**:
  - Generic permissions (e.g., access to all resources `*`) increase security risks.
  - Specific permissions (e.g., access to one table) limit potential damage from compromised credentials.
- **Example of Generic Policy (Avoid)**:
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "*",
      "Resource": "*"
    }
  ]
}
```
- **Recommendation**: Always specify exact actions and resources.

## Practical Steps
- **Create an IAM User** (covered in the next video):
  - Use the AWS Management Console or AWS CLI.
  - Example CLI command to create a user:
    ```bash
    aws iam create-user --user-name MyAdminUser
    ```
- **Attach a Policy to a User**:
  - Example CLI command to attach a policy:
    ```bash
    aws iam attach-user-policy --user-name MyAdminUser --policy-arn arn:aws:iam::aws:policy/AdministratorAccess
    ```
- **Create a Group**:
  - Example CLI command to create a group and add a user:
    ```bash
    aws iam create-group --group-name Developers
    aws iam add-user-to-group --group-name Developers --user-name MyAdminUser
    ```

## Security Best Practices
- Grant **least privilege** (only necessary permissions).
- Use groups to simplify permission management for multiple users.
- Regularly review and update policies to ensure they remain fine-grained.

# Creating IAM Users

## Overview
- **Objective**: Create an IAM user with console and programmatic access to securely manage AWS resources.
- **Prerequisites**:
  - Active AWS account (requires setup with credit/debit card and phone verification).
  - Familiarity with AWS IAM basics (root user, permissions, groups).
- **Tools**: AWS Management Console, optional CSV file for credential storage.

## Steps to Create an IAM Group
1. **Access IAM Service**:
   - Log in to the AWS Management Console with the root user.
   - Search for "IAM" in the console to navigate to Identity and Access Management.
2. **Create a Group**:
   - Navigate to **User Groups** > **Create Group**.
   - Name the group (e.g., `NewAdmin`).
   - Skip adding users initially (can be added later).
   - Attach a policy (e.g., `AdministratorAccess` for admin privileges).
   - Example policy attachment (conceptual, applied via console):
     ```json
     {
       "Version": "2012-10-17",
       "Statement": [
         {
           "Effect": "Allow",
           "Action": "*",
           "Resource": "*"
         }
       ]
     }
     ```
   - Click **Create Group**.

## Steps to Create an IAM User
1. **Create a User**:
   - Navigate to **Users** > **Create User**.
   - Enter a username (e.g., `LambdaUser`).
   - Enable **Provide user access to the AWS Management Console**.
   - Select **Autogenerated password** (user must reset password on first login).
   - Assign permissions by adding the user to the `NewAdmin` group.
   - Review and click **Create User**.
2. **Save Credentials**:
   - After creation, download the CSV file containing:
     - Sign-in URL.
     - Username.
     - Temporary password.
   - **Important**: This information is shown only once; save the CSV securely.
   - Example CSV content:
     ```csv
     User name,Password,Access key ID,Secret access key,Console login link
     LambdaUser,abc123xyz,,-,https://123456789012.signin.aws.amazon.com/console
     ```

## Sign In with the IAM User
1. **Log Out of Root User**:
   - Sign out from the AWS Management Console.
2. **Access Sign-In URL**:
   - Open the URL from the CSV file.
   - Enter the username (e.g., `LambdaUser`) and temporary password.
3. **Reset Password**:
   - The console prompts for a new password.
   - Set a strong password and sign in.
   - Example of a strong password (conceptual):
     ```plaintext
     Password: MySecureP@ssw0rd2025
     ```

## Configure Programmatic Access
1. **Access User in IAM**:
   - Log in with the IAM user.
   - Navigate to **IAM** > **Users** > `LambdaUser` > **Security Credentials**.
2. **Create Access Key**:
   - Scroll to **Access Keys** > **Create Access Key**.
   - Select use case (e.g., Command Line Interface).
   - Download the CSV file containing:
     - Access Key ID.
     - Secret Access Key.
   - **Important**: Store securely; this is shown only once.
   - Example CSV content:
     ```csv
     Access key ID,Secret access key
     AKIAIOSFODNN7EXAMPLE,wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
     ```
3. **Use Access Key with AWS CLI**:
   - Configure the AWS CLI with the access key:
     ```bash
     aws configure
     ```
   - Input:
     - Access Key ID (from CSV).
     - Secret Access Key (from CSV).
     - Default region (e.g., `us-east-1`).
     - Output format (e.g., `json`).
   - Example CLI configuration file (`~/.aws/credentials`):
     ```ini
     [default]
     aws_access_key_id = AKIAIOSFODNN7EXAMPLE
     aws_secret_access_key = wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
     ```

## Security Best Practices
- **Store Credentials Securely**: Use a password manager or encrypted storage for CSV files.
- **Use Groups for Permissions**: Simplifies management for multiple users.
- **Limit Root User Usage**: Only use root credentials for tasks requiring it.
- **Rotate Access Keys Regularly**: Delete and recreate keys periodically.