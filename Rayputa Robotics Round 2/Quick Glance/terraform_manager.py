from python_terraform import Terraform

def run_terraform_apply(working_dir):
    tf = Terraform(working_dir=working_dir)
    try:
        return_code, stdout, stderr = tf.init()
        if return_code != 0:
            print(f"Terraform init failed: {stderr}")
            return
        return_code, stdout, stderr = tf.apply(auto_approve=True)
        if return_code == 0:
            print("Terraform apply successful")
        else:
            print(f"Terraform apply failed: {stderr}")
    except Exception as e:
        print(f"Error running Terraform: {e}")

if __name__ == "__main__":
    run_terraform_apply("./terraform_configs")