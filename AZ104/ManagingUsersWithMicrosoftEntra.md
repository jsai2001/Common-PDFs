# Module 2: Azure Identities and Governance Notes

## Module Overview
- Focuses on managing user accounts and structuring resources in an Azure tenant.
- Consists of three lessons:
  1. **Lesson 2: Managing Users with Microsoft Entra**:
     - Set up and manage users and groups.
     - Configure self-service password resets.
     - Understand Azure licenses and their impact.
  2. **Lesson 3: Managing User Access**:
     - Implement Azure and Entra ID roles to control user access and permissions.
  3. **Lesson 4: Managing Subscriptions and Resources**:
     - Use management groups, subscriptions, resource groups, and policies for enterprise-scale user management.

## Learning Objectives
- Secure Azure using **Microsoft Entra ID** (formerly Azure Active Directory).
- Create and manage users and groups.
- Understand Azure licenses and their feature impacts.
- Configure external user accounts.
- Set up self-service password reset for enhanced user experience.

## Creating Users and Groups
### Microsoft Entra ID
- Replaces Azure Active Directory; serves as a database for usernames, passwords, and groups to manage resource access.
- Primary domain created automatically upon signup (e.g., `username@<tenant>.onmicrosoft.com`).
  - Example: `brett@bretthargreaves.onmicrosoft.com` becomes `brett@bretthargreaves.onmicrosoft.com`.
- Supports vanity domains (covered in later lessons).

### Creating a User in the Azure Portal
- Navigate to **Microsoft Entra ID > Users > Create New User**.
- Required fields:
  - **Username**: e.g., `Fred@<tenant>.onmicrosoft.com`.
  - **Display Name**: Optional (e.g., "Fred Smith").
  - **Password**: Auto-generated or manually set.
- Optional fields: First name, last name, job title, company name.
- Assign roles or groups (optional during creation).
- Creation may take a few minutes to reflect in the portal.

### Creating a User via Azure CLI
- Command: `az ad user create` with display name, password, and user principal name.

**Azure CLI Command Example**:
```bash
az ad user create --display-name "Fred Smith" --password "SecurePass123!" --user-principal-name "Fred@bretthargreaves.onmicrosoft.com"
```

- Verifies user creation in the portal under **Users** after refreshing.

### Bulk User Creation
- Useful for creating multiple users (hundreds/thousands) at once.
- Steps:
  1. Go to **Microsoft Entra ID > Users > Bulk Operations > Bulk Create**.
  2. Download the provided CSV template.
  3. Fill in required fields (e.g., display name, user principal name, password).
  4. Upload the CSV file to import users.
- Example CSV format:
  ```csv
  DisplayName,UserPrincipalName,Password
  Imported User,import@bretthargreaves.onmicrosoft.com,SecurePass123!
  ```
- Import time varies based on the number of users.

### Creating Groups
- Navigate to **Microsoft Entra ID > Groups > New Group**.
- Group types:
  - **Microsoft 365**: For Microsoft 365 services.
  - **Security**: For assigning rights/privileges within Azure.
- Configuration:
  - **Name**: e.g., "AzureAdmins".
  - **Description**: Optional.
  - **Owner**: Set for managing group reviews (recommended for privileged groups).
  - **Members**: Add users or other groups.
- Simplifies access management by assigning permissions to groups instead of individual users.

**Azure CLI Command Example for Creating a Group**:
```bash
az ad group create --display-name "AzureAdmins" --mail-nickname "AzureAdmins" --description "Group for Azure administrators"
```

**Add User to Group via Azure CLI**:
```bash
az ad group member add --group "AzureAdmins" --member-id $(az ad user show --id "Fred@bretthargreaves.onmicrosoft.com" --query id --output tsv)
```

# Azure Entra ID Licenses and SSPR Notes

## Managing Premium License Features
### Azure Entra ID License Types
1. **Free Tier**:
   - Ideal for small businesses starting with Azure Entra ID.
   - Features:
     - Basic user and group management.
     - Single sign-on (SSO) for hundreds of SaaS applications.
     - Self-service password reset (SSPR) for cloud users only (no on-premises write-back).
   - Limitations:
     - Limited to 500,000 Active Directory objects (rarely an issue for small setups).
     - No hybrid integration with on-premises directories (requires paid license).
   - Use Case: Pure cloud directory for basic identity management.

2. **Premium P1 Tier**:
   - Builds on Free tier with advanced identity management.
   - Additional Features:
     - Hybrid integrations (sync with on-premises directories).
     - SSPR with write-back to on-premises directories.
     - **Azure AD Conditional Access**: Enforce access policies based on user and device states.
   - Use Case: Enterprises needing robust identity management and security.

3. **Premium P2 Tier**:
   - Most comprehensive, includes all Free and P1 features.
   - Additional Features:
     - **Identity Protection**: Advanced risk detection and remediation.
     - **Privileged Identity Management (PIM)**: Manages, controls, and monitors access to critical resources.
     - **Access Reviews**: Automates reviews of users/groups with privileged roles to ensure least-privilege access.
   - Use Case: Organizations requiring advanced governance and security.

### Licensing for PIM and Access Reviews
- P2 licenses required only for:
  - Administrators setting up/configuring PIM or access reviews.
  - Approvers for access reviews or temporary elevated access in PIM.
- Standard users (not using PIM or access reviews) can use Free tier licenses.
- Example: For 1 admin and 1 approver managing 100 users, only 2 P2 licenses are needed.

**Azure CLI Example: Assign P2 License to a User**:
```bash
# Get user ID
user_id=$(az ad user show --id "user@domain.onmicrosoft.com" --query id --output tsv)

# Assign P2 license (replace <license-sku-id> with actual SKU ID)
az ad user update --id $user_id --add-licenses "[{\"disabledPlans\": [], \"skuId\": \"<license-sku-id>\"}]"
```

## Configuring Self-Service Password Reset (SSPR)
### Overview
- **Purpose**: Allows users to reset their own passwords, reducing help desk calls.
- **Default State**: Disabled; requires configuration for security.
- **Benefits**: Enhances user experience by enabling self-managed password resets.

### Configuration Steps in Azure Portal
1. **Enable SSPR**:
   - Navigate to **Microsoft Entra ID > Password Reset**.
   - Default: Set to "None."
   - Options:
     - **Selected**: Enable for a specific Azure AD group.
     - **All**: Enable for all users in the organization.
   - Example: Select "All" and click **Save**.

2. **Set Authentication Methods**:
   - Choose 1 or 2 methods for user verification.
   - Options:
     - **Email**: Sends a reset link to the user’s registered email.
     - **Mobile Phone**: SMS-based verification.
     - **Mobile App Notification/Code**: Uses an authenticator app.
     - **Office Phone** or **Security Questions**.
   - Users select preferred method during reset if multiple are enabled.
   - Save changes after selecting methods.

3. **Require Registration**:
   - Go to **Password Reset > Registration**.
   - Enable "Require users to register when signing in" to prompt users to provide authentication details (e.g., phone number) on first login.
   - Ensures data (e.g., email, phone) is available for SSPR.

4. **Testing SSPR**:
   - Open a private browser window and navigate to `aka.ms/ssprsetup`.
   - Sign in as a user, enter a new password, and provide required authentication details (if registration is enforced).

**Azure CLI Example: Enable SSPR for All Users**:
```bash
az ad policy password-reset update --self-service-password-reset-enabled true --scope All
```