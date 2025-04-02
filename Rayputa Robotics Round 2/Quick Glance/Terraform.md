# Terraform Prep Guide

Revised with all 110 questions intact. Answers are concise, direct, and impactful, with code blocks unchanged for clarity. Overwhelm tackled—focus sharpened. Let’s go.

## Terraform Basics

### What is Terraform, and how does it differ from other IaC tools like Ansible or CloudFormation?
- **Terraform**: Declarative IaC tool for provisioning infrastructure across clouds.
- **Ansible**: Procedural, excels at config management.
- **CloudFormation**: AWS-only, less readable JSON/YAML vs. Terraform’s HCL.

### What is Infrastructure as Code (IaC), and why is it important?
- **IaC**: Managing infra with code.
- **Key**: Consistency, scalability, automation, version control.

### Explain the key components of Terraform.
- **Providers**: Cloud APIs.
- **Resources**: Infra bits.
- **Modules**: Reusable configs.
- **State**: Tracks reality.
- **Variables**: Inputs.
- **Outputs**: Results.

### What’s the purpose of `terraform init`?
- Sets up project—downloads providers, configures backend, grabs modules.
```bash
terraform init
```

### What does `terraform plan` do, and why is it useful?
- Shows what’ll change—compares desired vs. current state. Catch errors early, review safely.
```bash
terraform plan
```

### How does `terraform apply` work?
- Executes plan—creates/updates/deletes resources, updates state. Prompts unless `-auto-approve`.
```bash
terraform apply
```

### What’s the Terraform state file, and why’s it critical?
- Maps config to real infra. Lose it, Terraform’s blind—duplicates or errors ensue.

### What’s a Terraform provider, and how do you configure one?
- API bridge to clouds/services. Define it:
```hcl
provider "aws" {
    region = "us-east-1"
}
```

### Difference between `terraform.tfvars` and `variables.tf`?
- **`variables.tf`**: Declares vars (type, default).
- **`terraform.tfvars`**: Sets values.

### How do you define variables and assign values in Terraform?
- **Define**:
```hcl
variable "size" { 
    type = string 
    default = "t2.micro" 
}
```
- **Assign**: Default, tfvars, `-var`, `TF_VAR_`, prompt.

### Purpose of `terraform destroy`?
- Deletes all managed resources. Cleanup tool.
```bash
terraform destroy
```

### Supported backends in Terraform, and why use a remote one?
- **Local**, **S3**, **Terraform Cloud**, etc.
- **Remote**: Team access, locking, durability.

### What’s HCL, and how’s it compare to JSON in Terraform?
- **HCL**: Readable, comment-friendly config language.
- **JSON**: Verbose, machine-oriented. Both work—HCL’s human-first.

### How do you install Terraform, and what’s needed?
- Download binary, add to PATH, verify:
```bash
terraform -version
```
- **Needs**: OS, internet, optional Git.

### Difference between a resource and a data source?
- **Resource**: Managed infra (creates/deletes).
- **Data source**: Read-only lookup (fetches existing).

## Terraform Workflow and Commands

### Typical Terraform workflow?
- `init` (setup), `plan` (preview), `apply` (execute), `destroy` (teardown).

### What if you apply without plan?
- Terraform auto-plans and applies. Risky—no preview.

### Preview changes without applying?
```bash
terraform plan
```

### What does `terraform refresh` do?
- Syncs state with real infra, no changes made.

### Validate a Terraform config?
```bash
terraform validate
```

### Purpose of `terraform fmt`?
- Formats code consistently.
```bash
terraform fmt
```

### Import existing infra into Terraform?
- Define resource, then:
```bash
terraform import aws_instance.example i-1234567890abcdef0
```

### What’s `terraform taint`, and when to use it?
- Marks resource for recreation. Use: Corrupted or stuck resources.
```bash
terraform taint aws_instance.example
```

### Roll back changes if something fails?
- Revert config, apply. No auto-rollback—use Git, backups.

### What does `terraform output` do?
- Shows output vars post-apply.
```bash
terraform output
```

## Variables and Outputs

### Define a variable with a default?
```hcl
variable "region" { 
    type = string 
    default = "us-east-1" 
}
```

### Variable types in Terraform?
- String, number, bool, list, map, set, object, tuple.

### Pass variables via command line?
```bash
terraform apply -var="size=t3.large"
```

### Input vs. local variables?
- **Input**: External, customizable (`variable`).
- **Local**: Internal, computed (`locals`).

### Reference output from another module?
```hcl
vpc_id = module.vpc.vpc_id
```

### Secure sensitive variables?
- Use tfvars (gitignored), env vars (`TF_VAR_`), Vault/Secrets Manager.

### What’s the `sensitive` attribute?
- Hides var/output in logs. Use for secrets.
```hcl
output "pass" { 
    value = "secret" 
    sensitive = true 
}
```

### Use `count` with resources?
- Multiplies resource:
```hcl
resource "aws_instance" "server" { 
    count = 2 
}
```

### What’s `for_each`, and how’s it differ from `count`?
- Iterates over map/set with keys. `count`: Numeric index only.
```hcl
for_each = { "a" = "val1", "b" = "val2" }
```

### Conditionally create resources?
- Use `count`:
```hcl
count = var.create ? 1 : 0
```

## Modules

### What’s a Terraform module?
- Reusable config bundle. Simplifies, standardizes infra.

### Create a reusable module?
- Dir with `main.tf`, `variables.tf`, `outputs.tf`. Parameterize it.

### Root vs. child module?
- **Root**: Main dir, runs commands.
- **Child**: Called submodule, reusable.

### Pass variables to a module?
```hcl
module "vpc" { 
    source = "./vpc" 
    cidr = "10.0.0.0/16" 
}
```

### What are module sources?
- Local (`./`), Git, Registry. Remote:
```hcl
source = "terraform-aws-modules/vpc/aws"
```

### Version Terraform modules?
- Git tags (`v1.0.0`), Registry `version = "2.0"`.

### What’s the Terraform Module Registry?
- HashiCorp’s module hub. Use:
```hcl
module "s3" { 
    source = "terraform-aws-modules/s3-bucket/aws" 
}
```

### Debug module issues?
- Logs (`TF_LOG=DEBUG`), plan, temp outputs.

### Nest modules? Depth limit?
- Yes, call modules in modules. No hard limit—keep it shallow (2-3).

### Update module without breaking infra?
- Test new version, pin it, plan for no recreation.

## State Management

### Why’s the state file key, and what if it’s corrupted?
- Tracks infra. Corrupted: Duplicates, errors. Restore from backup.

### Configure remote backend (S3, Terraform Cloud)?
```hcl
terraform { 
    backend "s3" { 
        bucket = "state" 
        key = "tfstate" 
    } 
}
```

### Benefits of remote state?
- Collab, locking, safety, versioning.

### Lock the state file? Why?
- Auto with remote (e.g., DynamoDB). Prevents race conditions.

### What’s state drift, and how to fix?
- Real infra vs. state mismatch. Detect: `plan`. Fix: `apply` or update config.

### Move resource between state files?
```bash
terraform state mv -state-out=../new.tfstate aws_instance.example aws_instance.example
```

### What’s `terraform state` and subcommands?
- Manages state. `mv` (move), `rm` (remove), `list`, `show`.

### Share state across team?
- Remote backend, access control, locking.

### Two simultaneous `terraform apply` runs?
- **Local**: Overwrites, chaos.
- **Remote with lock**: One fails.

### Recover lost state file?
- Restore backup or rebuild with import.

## Providers

### Configure multiple providers?
```hcl
provider "aws" { 
    region = "us-east-1" 
}
provider "aws" { 
    alias = "west" 
    region = "us-west-2" 
}
```

### What’s provider aliasing?
- Names variants (e.g., multi-region).
```hcl
resource "aws_instance" "west" { 
    provider = aws.west 
}
```

### Specify provider version?
```hcl
terraform { 
    required_providers { 
        aws = { version = "~> 4.0" } 
    } 
}
```

### No provider version specified?
- Grabs latest—risks inconsistency, breaks.

### Authenticate with AWS/Azure/GCP?
- **AWS**: Env vars (`AWS_ACCESS_KEY_ID`).
- **Azure**: CLI (`az login`).
- **GCP**: Key file (`GOOGLE_CREDENTIALS`).

### Official vs. community provider?
- **Official**: HashiCorp-backed, stable.
- **Community**: Third-party, variable quality.

### Troubleshoot provider errors?
- Logs (`TF_LOG`), check creds, config, versions.

## Best Practices

### Organize Terraform code for big projects?
- Modules, naming, env dirs, remote state, DRY.

### Handle secrets?
- Vars (`sensitive`), Vault, env vars—not hardcoded.

### Structure Terraform files?
- `main.tf`, `variables.tf`, `outputs.tf`, `providers.tf`, `locals.tf`.

### Avoid hardcoding?
- Vars, data sources, locals, modules.

### Manage Terraform in teams?
- Remote state, workspaces, Git, CI/CD.

### Ensure idempotency?
- Declarative, no provisioners, unique IDs.

### Role of version control?
- Tracks, collaborates, reproduces code.

### Handle resource dependencies?
- **Implicit**: References.
- **Explicit**: `depends_on`.

### Common Terraform pitfalls?
- Hardcoding secrets, manual changes, no plan, version mismatches.

## Advanced Terraform

### What’s a Terraform workspace?
- Multi-state in one dir.
```bash
terraform workspace new dev
```

### Manage multiple envs (dev, prod)?
- Workspaces, dirs, or vars (`-var env=prod`).

### What’s `depends_on`?
- Forces order when implicit fails.
```hcl
depends_on = [aws_vpc.main]
```

### Handle dynamic blocks?
- Loops nested configs:
```hcl
dynamic "ingress" { 
    for_each = var.rules 
    content { 
        from_port = ingress.value.port 
    } 
}
```

### What’s a provisioner? Avoid when?
- Runs scripts post-create. Skip for non-idempotent tasks—use Ansible.

### Use Terraform with CI/CD?
```yaml
run: terraform init && terraform apply -auto-approve
```
- Automate plan/apply, secure creds.

### Difference: `apply` vs. `-auto-approve`?
- **`apply`**: Prompts.
- **`-auto-approve`**: No prompt, auto-executes.

### Handle large-scale infra?
- Modules, split state, parallel runs, `-target`.

### What’s Terraform Sentinel?
- Policy-as-code for Terraform Cloud—blocks non-compliant applies.

### Integrate with Ansible/Chef?
- Terraform provisions, Ansible configures. Pass outputs (e.g., IPs).

## Troubleshooting and Debugging

### Enable detailed logging?
```bash
export TF_LOG=DEBUG
```

### What’s `TF_LOG`?
- Sets log level (`TRACE`, `DEBUG`, etc.) for debugging.

### Fix "resource already exists" error?
- Import:
```bash
terraform import aws_instance.example i-1234567890abcdef0
```

### "Provider config not present" error?
- Add missing provider block, re-init.

### Resolve dependency cycle errors?
- Refactor refs, use data sources, check `terraform graph`.

### Why does `terraform plan` fail?
- Syntax, provider issues, conflicts, drift.

### Terraform crashes mid-run?
- Logs, check state, retry apply.

### Debug silent module failure?
- Logs, isolate, outputs, plan.

### What’s `terraform graph`?
- Maps dependencies.
```bash
terraform graph | dot -Tpng > graph.png
```

## Scenario-Based Questions

### Deploy AWS VPC with subnets?
```hcl
module "vpc" { 
    source = "./vpc" 
    public_subnet_cidrs = ["10.0.1.0/24"] 
}
```

### Migrate to Terraform Cloud?
- Add backend, init, push state:
```hcl
backend "remote" { 
    organization = "my-org" 
}
```

### Fix manually deleted resource?
- `apply` to recreate, or `state rm` to forget.

### HA app across regions?
- Multi-provider aliases, Route 53 failover.

### Automate Kubernetes cluster?
- EKS module, CI/CD:
```hcl
resource "aws_eks_cluster" "example" { 
    name = "my-cluster" 
}
```

### Handle rate limit failure?
- Lower `-parallelism`, retry, split config.

### Config works in dev, fails in prod?
- Compare vars, logs, perms—standardize.

### 50 EC2s with unique names?
```hcl
resource "aws_instance" "example" { 
    count = 50 
    tags = { 
        Name = "instance-${count.index + 1}" 
    } 
}
```

### Conditional deploy by env?
```hcl
count = var.env == "prod" ? 2 : 1
```

### Replicate on-prem infra?
- Map to cloud (VPCs, VMs), start with POC.

## Terraform with Cloud Providers

### Configure Terraform for AWS?
```hcl
provider "aws" { 
    region = "us-east-1" 
}
resource "aws_vpc" "main" { 
    cidr_block = "10.0.0.0/16" 
}
```

### IAM roles: Terraform vs. Console?
- **Terraform**: Code, repeatable.
- **Console**: Manual, quick.

### Azure resource group and VNet?
```hcl
resource "azurerm_resource_group" "rg" { 
    name = "my-rg" 
}
resource "azurerm_virtual_network" "vnet" { 
    name = "my-vnet" 
}
```

### GCP Compute Engine instance?
```hcl
resource "google_compute_instance" "vm" { 
    name = "my-vm" 
    machine_type = "e2-medium" 
}
```

### Secrets in AWS SM or Vault?
```hcl
data "aws_secretsmanager_secret_version" "secret" { 
    secret_id = "my-secret" 
}
```

## Open-Ended Questions

### Stay updated with Terraform?
- Docs, GitHub, blogs, labs.

### Most complex infra deployed?
- Multi-region HA app—drift, deps, secrets.

### Explain Terraform to a newbie?
- “Blueprints for cloud, auto-built.”

### Terraform limitations?
- State fragility, no real-time—remote state, drift checks.

### Terraform vs. other IaC?
- Multi-cloud, declarative—vs. Ansible (config), CloudFormation (AWS-only).

---

All 110 questions, tight answers, code preserved. Overwhelm gone—grind ready. Hit it!
```