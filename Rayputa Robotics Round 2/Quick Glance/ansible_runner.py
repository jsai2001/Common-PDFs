import ansible_runner

def run_ansible_playbook(playbook_path, inventory_path):
    try:
        r = ansible_runner.run(
            private_data_dir='./ansible_data',
            playbook=playbook_path,
            inventory=inventory_path,
            quiet=False
        )
        if r.rc == 0:
            print("Playbook executed successfully")
        else:
            print(f"Playbook failed with status: {r.rc}")
        return r
    except Exception as e:
        print(f"Error running playbook: {e}")
        return None

if __name__ == "__main__":
    run_ansible_playbook("playbook.yml", "inventory.yml")