# Introduction to Azure Lesson Notes

## Azure 101
### Cloud Service Models
1. **Infrastructure as a Service (IaaS)**:
   - Provides raw compute, storage, and networking.
   - User manages the OS on virtual machines (patching, maintenance), while Azure handles hardware and host OS.
   - Example: Virtual Machines, Azure Storage.
2. **Platform as a Service (PaaS)**:
   - Framework for developers to build applications without managing the underlying OS or infrastructure.
   - Example: Azure App Services (similar to IIS/Apache on a VM, but Azure manages the VM).
   - Costs based on configured RAM and CPU, incurred even when idle.
3. **Serverless Computing (Functions as a Service - FaaS)**:
   - Fully managed by Azure; users focus only on code.
   - Scales resources (CPU, RAM) dynamically, with a minimum that can be zero (no cost when idle).
   - Example: Azure Functions, serverless SQL Azure (scales to zero compute when idle, but storage costs persist as IaaS).

### Azure Core Capabilities
- **Storage**: Blob storage, queue storage (large workloads), disk storage (high-performance block storage).
- **Compute**: Virtual machines, container services, Azure Functions (event-driven serverless compute).
- **Networking**: Azure Virtual Networks, load balancers, firewalls for simple to complex architectures.
- **Security & Compliance**:
  - **Azure Security Center**: Unified security management, advanced threat protection for hybrid cloud workloads.
  - **Azure Monitor**: Collects and analyzes telemetry data from cloud and on-premises environments.
  - **Compliance Offerings**: Ensures applications meet legal and regulatory standards (national, regional, industry-specific).

### Azure Resource Manager (ARM)
- Management layer for creating, updating, and deleting Azure resources.
- Uses **ARM templates** (infrastructure as code) to define infrastructure dependencies in a declarative JSON format.
- Enables repeatable, predictable deployments across regions and accounts.

**Example ARM Template for a Resource Group**:
```json
{
  "$schema": "https://schema.management.azure.com/schemas/2019-04-01/deploymentTemplate.json#",
  "contentVersion": "1.0.0.0",
  "resources": [
    {
      "type": "Microsoft.Resources/resourceGroups",
      "apiVersion": "2021-04-01",
      "name": "RG-MyFirstResourceGroup",
      "location": "uksouth",
      "properties": {}
    }
  ]
}
```

## Introduction to the Azure Portal
- **Access**: Navigate to `portal.azure.com` and sign in with a Microsoft account.
- **Interface**:
  - Displays popular and recently used Azure services.
  - **Burger Menu**: Access favorite services or all services to add new ones (e.g., add "Subscriptions" by starring it).
- **Subscriptions**:
  - All Azure resources are created within a subscription.
  - New users can sign up for a free subscription with $200 credit for 30 days.
  - Steps: Go to Subscriptions > Add > Start Free > Enter credit card and personal info.
- **Creating Resources**:
  - Options: Use "Create a resource" button on the main page, burger menu, or specific service page.
  - **Resource Groups**: Required for all resources (e.g., VMs, storage accounts).
    - Naming convention: Prefix with "RG-" (e.g., "RG-MyFirstResourceGroup").
    - Select a region (e.g., UK South) for resource deployment.
    - Tags (covered later) for organization.
  - Steps: Create > Name resource group > Select region > Review and create.

**Example Azure CLI Command to Create a Resource Group**:
```bash
az group create --name RG-MyFirstResourceGroup --location uksouth
```

**Example PowerShell Command to Create a Resource Group**:
```powershell
New-AzResourceGroup -Name RG-MyFirstResourceGroup -Location uksouth
```

# Azure CLI, PowerShell, and AZ-104 Exam Notes

## Azure Management with Azure CLI and PowerShell
### Overview
- **Azure Portal**: User-friendly, ideal for beginners and creating new resources.
- **Command Line Utilities**: Enable scripting and repeatable resource management.
  - **Azure PowerShell**: Installed on Windows PCs but requires additional module installation.
  - **Azure CLI**: Cross-platform, requires downloading and installing from the Microsoft website.
- **Benefits**: Scripting allows automation and repeatability compared to manual portal actions.

### Installing Azure PowerShell
- Requires three commands to set up:
  1. Install the `PowerShellGet` module.
  2. Set the execution policy to allow module installation.
  3. Install the `Az` module from PSGallery.

**PowerShell Installation Commands**:
```powershell
# Install PowerShellGet
Install-Module -Name PowerShellGet -Force

# Set execution policy
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# Install Az module
Install-Module -Name Az -Repository PSGallery -Force
```

### Azure CLI Installation
- Download and run the installer from the Microsoft website for Windows or Mac.

### Authentication
- **Azure CLI**: Use `az login` to open a browser for authentication.
- **Azure PowerShell**: Use `Connect-AzAccount` for authentication.
- **Cloud Shell**: Available in the Azure portal (top command bar icon), auto-authenticates since it runs within the signed-in portal session.
  - Supports both Azure CLI and PowerShell commands.

### Creating a Resource Group
- **PowerShell Example**:
  - Command: `New-AzResourceGroup -Name <name> -Location <location>`
  - Naming convention: Prefix with "RSG-" (e.g., "RSG-PowerShell").
  - Example location: `uksouth`.

**PowerShell Command**:
```powershell
New-AzResourceGroup -Name RSG-PowerShell -Location uksouth
```

- **Azure CLI Example**:
  - Command: `az group create --name <name> --location <location>`
  - Naming convention: Prefix with "RSG-" (e.g., "RSG-CLI").
  - Example location: `uksouth`.

**Azure CLI Command**:
```bash
az group create --name RSG-CLI --location uksouth
```

- **Verification**: Check created resource groups in the Azure portal under "Resource Groups."

### Cloud Shell Benefits
- Simplifies command-line usage without local installation.
- Auto-authenticated within the portal.
- Supports both Azure CLI and PowerShell.

## What to Expect in the AZ-104 Exam
### Exam Overview
- **Duration**: ~150 minutes (2.5 hours).
- **Questions**: 40–60, mix of multiple-choice and scenario-based.
- **Purpose**: Assesses skills in managing Azure services for the Azure Administrator Associate Certification.

### Exam Domains and Weighting
1. **Manage Azure Identities and Governance** (20–25%):
   - Create users and groups, manage self-service password control.
   - Use Azure and Entra ID roles.
   - Implement governance with Azure Policy and management groups.
2. **Implement and Manage Storage** (15–20%):
   - Manage storage accounts, access, and configuration.
3. **Deploy and Manage Azure Compute Resources** (20–25%):
   - Use Azure Resource Manager, manage VMs, containers, and app services.
4. **Implement and Manage Virtual Networking** (15–20%):
   - Configure Azure networking, connectivity, and secure access.
5. **Monitor and Maintain Azure Resources** (10–15%):
   - Monitor resource health and performance.
   - Implement backup and recovery options.