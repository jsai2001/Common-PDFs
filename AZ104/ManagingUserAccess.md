### Azure Roles and Role Assignment

#### Learning Objectives
- Understand how to secure access for authenticated users in Azure.
- Explore Azure roles, their types, and their significance.
- Learn how to apply roles to users for resource and identity management.

#### Understanding Azure Roles
1. **Azure IAM Roles (Resource-Centric)**
   - Azure Identity and Access Management (IAM) roles provide granular control over Azure resources (e.g., storage accounts, resource groups, subscriptions, or management groups).
   - Permissions flow hierarchically: assigning a role at a higher level (e.g., resource group) grants access to all objects within it (e.g., read access to all resources in the group).
   - Types of roles:
     - **Built-in Roles**: Predefined roles like Contributor, Reader, or Backup Operator.
     - **Custom Roles**: User-defined roles for specific needs not covered by built-in roles.
   - Actions are divided into:
     - **Action Roles**: Control operations on resources (e.g., read storage account properties).
     - **Data Action Roles**: Control access to data within resources (e.g., read/write data in a storage account).

2. **Entra ID Roles (Identity-Centric)**
   - Previously known as Azure Active Directory (AD) roles, now called Microsoft Entra ID roles.
   - Focused on managing identities and directory services (e.g., user creation, password resets, security group management).
   - Examples of built-in roles:
     - **Global Administrator**: Full control over directory resources.
     - **User Administrator**: Manages user accounts and groups.
     - **Security Administrator**: Handles security-related tasks.
   - Roles are granular, with descriptions detailing specific permissions (e.g., Azure Information Protection Administrator can read policy properties and configure service health).

3. **Key Differences**
   - **Azure IAM Roles**: Manage access to Azure resources (e.g., VMs, storage).
   - **Entra ID Roles**: Manage directory and identity tasks within the organization.

#### Using and Assigning Roles
1. **Navigating Entra ID Roles**
   - Access via Azure Portal: Search for "Microsoft Entra ID" → "Roles and Administrators."
   - Displays a list of roles, some with a "privileged" badge indicating elevated permissions.
   - **Best Practice**: Assign the least privileges necessary for a user’s role to minimize security risks.
   - Privileged access should be limited to specific admin accounts or granted only when needed.
   - Role descriptions provide detailed permissions (e.g., Azure Information Protection Administrator allows reading policy properties).

2. **Navigating Azure IAM Roles**
   - Access via Azure Portal: Navigate to a subscription, resource group, or resource → "Access Control (IAM)" tab.
   - View existing role assignments or add new ones via "Add Role Assignment."
   - Roles like Contributor or Reader have detailed permissions viewable under the "Details" option.
   - Example: The Reader role allows viewing a storage account but not accessing its data unless data action permissions are granted.

3. **Creating Custom Roles**
   - Used when built-in roles don’t meet specific needs (e.g., a role to only start virtual machines).
   - Steps to create a custom role:
     1. Navigate to a subscription → "Access Control (IAM)" → "Add" → "Add Custom Role."
     2. Options:
        - Clone an existing role.
        - Start from scratch.
        - Use a JSON template (e.g., from GitHub).
     3. Define permissions (e.g., search for "compute" to find virtual machine actions like "start").
     4. Set assignable scopes (e.g., subscription, resource group, or management group).
     5. Review the JSON configuration, which includes:
        - Role name and description.
        - Assignable scopes.
        - Permissions (actions, not actions, data actions, not data actions).
     6. Create the role and wait a few minutes for it to take effect.
     7. Assign the custom role to users via "Add Role Assignment" → Select "Custom Roles."

4. **Role Assignment Example**
   - After creating a custom role (e.g., "VM Starter"), assign it to a user to grant specific permissions (e.g., starting virtual machines).
   - Roles are stored as JSON, enabling precise control over permissions.

#### Best Practices
- **Least Privilege Principle**: Assign only the minimum permissions required.
- **Privileged Roles**: Use sparingly and assign to dedicated admin accounts.
- **Granularity**: Use custom roles for specific tasks not covered by built-in roles.
- **Scope Management**: Assign roles at appropriate levels (e.g., resource group vs. subscription) to control access effectively.

---

```json
{
  "properties": {
    "roleName": "VM Starter",
    "description": "Allows starting and restarting virtual machines",
    "assignableScopes": [
      "/subscriptions/<your-subscription-id>"
    ],
    "permissions": [
      {
        "actions": [
          "Microsoft.Compute/virtualMachines/start/action",
          "Microsoft.Compute/virtualMachines/restart/action"
        ],
        "notActions": [],
        "dataActions": [],
        "notDataActions": []
      }
    ]
  }
}
```

---