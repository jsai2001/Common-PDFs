
# Jenkins Security Notes

## Overview
- **Default Security**: Jenkins is locked by default upon initial launch, requiring an admin password to log in, preventing unauthorized control.
- **User Accounts**: Supports creation of user accounts with usernames and passwords for access control.
- **Security Realms**: Determines how users are authenticated. Default is Jenkins' built-in user database. Other options include LDAP or Unix-style users/groups.
- **Authorization**: Manages user permissions. By default, any logged-in user has full administrative access, which is not ideal for shared systems.

## Key Concepts
- **Security Realms**: Controls authentication. Default realm is Jenkins’ internal user database, created during installation.
- **Authorization**: Defines what authenticated users can do. Default setting allows all logged-in users to perform any action.
- **Matrix-Based Security**: Uses the Matrix Authorization Strategy plugin (commonly installed) to assign specific permissions to individual users.

## Best Practices
- Limit administrative access to 1-2 users in shared systems.
- Assign specific permissions to other users based on their roles.
- **Caution**: Always assign admin permissions to your own account to avoid being locked out. Instructions for disabling access control are available if locked out.

## Configuring Matrix-Based Security
1. **Access Manage Jenkins**:
   - Navigate to **Manage Jenkins** > **Manage Users** to create new users.
   - Example: Create a user (e.g., Nicole) with username, password, name, and email.

2. **Configure Global Security**:
   - Go to **Manage Jenkins** > **Configure Global Security**.
   - Default setting: "Logged-in users can do anything."
   - Select **Matrix-based security** to customize permissions.

3. **Assign Permissions**:
   - Add a user (e.g., your admin account) and grant full permissions by selecting the "Administer" checkbox.
   - Add other users (e.g., Nicole) and assign specific permissions, such as:
     - **Overall Read**: Allows interaction with the Jenkins interface.
     - **Job Permissions**: Build, cancel, and read jobs/workspace.
   - Save changes to apply.

4. **Testing Permissions**:
   - Log out and log in as the new user (e.g., Nicole).
   - Verify restricted interface (e.g., no access to management menu, limited job actions like create/delete).

## Example Workflow
- **Admin User Setup**:
  ```bash
  # Navigate to Manage Jenkins > Manage Users
  # Click "Create User"
  # Enter details: username (e.g., admin), password, name, email
  # Save user
  ```
- **Matrix-Based Security Configuration**:
  ```bash
  # Navigate to Manage Jenkins > Configure Global Security
  # Select "Matrix-based security"
  # Add user (e.g., admin), check "Administer" for full permissions
  # Add user (e.g., Nicole), check specific permissions (e.g., Overall: Read, Job: Build, Cancel, Read)
  # Save changes
  ```

## Notes
- **Interface Changes**: Non-admin users see a restricted interface (e.g., no management menu, limited job actions).
- **Documentation**: Refer to exercise files for additional details on securing Jenkins.

## Warnings
- Ensure your admin account has full permissions before saving matrix-based security settings.
- Locking yourself out is common; follow Jenkins documentation to regain access if needed.


# Jenkins Project-Based Permissions and Credentials Notes

## Project-Based Permissions
- **Overview**: Unlike global matrix-based authorization, project-based authorization allows permissions to be set for specific jobs or folders, providing granular control.
- **Configuration**:
  - Access **Manage Jenkins** > **Configure Global Security**.
  - Switch from **Matrix-based security** to **Project-based Matrix Authorization Strategy** by selecting the radio button.
  - Recreate the permission matrix:
    - Add admin user and grant **Overall: Administer** permissions.
    - Add other users (e.g., Nicole) with **Overall: Read** permissions for basic interface access.
  - Save changes.
- **Job-Specific Permissions**:
  - Navigate to a specific job (e.g., `complex-pipeline`) > **Configure**.
  - Enable **Project-based security**.
  - Add users (e.g., Nicole) and assign specific permissions (e.g., Build, Delete) for that job only.
  - Save changes.
- **Outcome**:
  - Users (e.g., Nicole) only see and interact with jobs they have permissions for.
  - Example: Nicole has admin-like permissions (Build, Delete) for `complex-pipeline` but no access to other jobs.

### Example Workflow
```bash
# Switch to Project-based Matrix Authorization Strategy
# Navigate to Manage Jenkins > Configure Global Security
# Select "Project-based Matrix Authorization Strategy"
# Add admin user, grant Overall: Administer
# Add user (e.g., Nicole), grant Overall: Read
# Save changes

# Configure job-specific permissions
# Navigate to job (e.g., complex-pipeline) > Configure
# Enable Project-based security
# Add user (e.g., Nicole), grant permissions (e.g., Job: Build, Delete)
# Save changes
```

## Credentials and Secrets
- **Overview**: Jenkins manages sensitive information (referred to as **credentials** or **secrets**) such as usernames/passwords, SSH keys, files, or text strings (e.g., API keys, security tokens).
- **Credential Types**:
  - Username and Password
  - SSH Keys
  - Files
  - Text Strings (ideal for API keys, tokens)
- **Accessing Credentials in Pipelines**:
  - **Credentials Function**:
    - Assigns sensitive values to environment variables using the credential’s ID.
    - For username/password credentials:
      - Returns one variable with `username:password`.
      - Creates two additional variables: `<variable>_USR` (username) and `<variable>_PSW` (password).
    - Example:
      ```groovy
      pipeline {
          agent any
          stages {
              stage('Example') {
                  steps {
                      script {
                          def creds = credentials('my-credential-id')
                          env.MY_CRED = creds // Contains username:password
                          env.MY_CRED_USR = creds_USR // Username
                          env.MY_CRED_PSW = creds_PSW // Password
                          echo "Username: ${env.MY_CRED_USR}"
                      }
                  }
              }
          }
      }
      ```
  - **withCredentials Build Step**:
    - Retrieves a secret and assigns it to a variable.
    - Only works with **secret string** credentials.
    - Steps inside the `withCredentials` block can access the secret.
    - Example:
      ```groovy
      pipeline {
          agent any
          stages {
              stage('Example') {
                  steps {
                      withCredentials([string(credentialsId: 'my-api-key', variable: 'API_KEY')]) {
                          sh 'echo $API_KEY' // Access secret in shell command
                      }
                  }
              }
          }
      }
      ```

## Notes
- **Project-Based Permissions**:
  - Ideal for restricting access to specific jobs/folders in shared Jenkins environments.
  - Users with limited permissions see a restricted interface and only authorized jobs.
- **Credentials**:
  - Use `credentials` function for username/password or other credential types.
  - Use `withCredentials` for secret strings in pipeline steps.
  - Avoid hardcoding sensitive information in pipelines; always use Jenkins credentials.
- **Best Practices**:
  - Recreate global permissions when switching to project-based security to avoid lockout.
  - Test permissions by logging in as restricted users to verify access.

## Warnings
- Recreating the permission matrix is required when switching to project-based security, as global settings do not carry over.
- Ensure admin permissions are set to avoid lockout.
- Use `withCredentials` only with secret strings to avoid pipeline errors.


# Jenkins Users and Permissions Challenge Notes

## Challenge Overview
- **Objective**: Secure a Jenkins server for two teams (Engineering and Finance) by limiting access to specific folders. Demonstrate that the `engineering` user cannot access or manage items in the `finance jobs` folder.
- **Tasks**:
  1. Create two users: `engineering` and `finance`.
  2. Enable **Project-based Matrix Authorization Strategy**.
  3. Assign **Overall: Read** permission to both users.
  4. Create two folders: `Engineering Jobs` and `Finance Jobs`.
  5. Assign admin permissions to each user for their respective folder.
  6. Demonstrate that the `engineering` user cannot access the `Finance Jobs` folder.

## Solution Steps
1. **Create Users**:
   - Navigate to **Manage Jenkins** > **Manage Users** > **Create User**.
   - Create `engineering` user:
     - Username: `engineering`
     - Password: Set a secure password
     - Full Name: `Engineering`
     - Email: Provide an email (e.g., `engineering@example.com`)
   - Create `finance` user:
     - Username: `finance`
     - Password: Set a secure password
     - Full Name: `Finance`
     - Email: Provide an email (e.g., `finance@example.com`)

2. **Configure Project-Based Authorization**:
   - Go to **Manage Jenkins** > **Configure Global Security**.
   - Select **Project-based Matrix Authorization Strategy**.
   - Add admin user:
     - Click **Add user**, enter `admin`, and grant **Overall: Administer** (select all permissions).
   - Add `engineering` and `finance` users:
     - Click **Add user**, enter `engineering`, grant **Overall: Read**.
     - Click **Add user**, enter `finance`, grant **Overall: Read**.
   - Save changes.
   - **Note**: A warning may appear about system user permissions. Dismiss it for this challenge (remediation is out of scope).

3. **Create and Configure Folders**:
   - Create `Engineering Jobs` folder:
     - From the dashboard, click **New Item**.
     - Name: `Engineering Jobs`, Type: **Folder**, click **OK**.
     - Enable **Project-based security**.
     - Add `engineering` user, grant all permissions (admin access for this folder).
     - Save changes.
   - Create `Finance Jobs` folder:
     - From the dashboard, click **New Item**.
     - Name: `Finance Jobs`, Type: **Folder**, click **OK**.
     - Enable **Project-based security**.
     - Add `finance` user, grant all permissions (admin access for this folder).
     - Save changes.

4. **Demonstrate Limited Access**:
   - Log out of the admin account.
   - Log in as `engineering` user with their password.
   - Verify:
     - Only the `Engineering Jobs` folder is visible.
     - No access to **Manage Jenkins** or `Finance Jobs` folder.
     - Inside `Engineering Jobs`, the user can configure and add items (admin permissions).
   - Log out and repeat for `finance` user to confirm similar restrictions.

## Example Workflow
```bash
# Create users
# Navigate to Manage Jenkins > Manage Users > Create User
# For engineering: Username=engineering, Password=<password>, Full Name=Engineering, Email=engineering@example.com
# For finance: Username=finance, Password=<password>, Full Name=Finance, Email=finance@example.com

# Configure Project-based Authorization
# Navigate to Manage Jenkins > Configure Global Security
# Select "Project-based Matrix Authorization Strategy"
# Add user: admin, grant Overall: Administer
# Add user: engineering, grant Overall: Read
# Add user: finance, grant Overall: Read
# Save changes

# Create Engineering Jobs folder
# Dashboard > New Item > Name: Engineering Jobs, Type: Folder > OK
# Enable Project-based security
# Add user: engineering, grant all permissions
# Save changes

# Create Finance Jobs folder
# Dashboard > New Item > Name: Finance Jobs, Type: Folder > OK
# Enable Project-based security
# Add user: finance, grant all permissions
# Save changes
```

## Verification
- **Admin View**: Can see and manage both `Engineering Jobs` and `Finance Jobs` folders.
- **Engineering User View**:
  - Only sees `Engineering Jobs` folder.
  - Can configure/add items in `Engineering Jobs` but cannot access `Finance Jobs` or **Manage Jenkins**.
- **Finance User View**:
  - Only sees `Finance Jobs` folder.
  - Can configure/add items in `Finance Jobs` but cannot access `Engineering Jobs` or **Manage Jenkins**.

## Notes
- **Importance of Admin Permissions**: Always assign **Overall: Administer** to the admin account to avoid lockout.
- **Overall: Read**: Required for users to access the Jenkins interface; without it, users cannot see any content.
- **Folder-Level Permissions**: Project-based security ensures users only manage their designated folders, ideal for securing sensitive pipelines (e.g., finance applications).
- **Warning Dismissal**: System user permission warnings can be ignored for this challenge but should be reviewed for production environments.

## Best Practices
- Test permissions by logging in as restricted users to verify access limitations.
- Use descriptive folder names to clearly distinguish team-specific areas.
- Regularly back up Jenkins configurations to recover from potential lockouts.
