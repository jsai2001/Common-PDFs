from github import Github

def trigger_workflow(repo_name, workflow_id, github_token):
    try:
        g = Github(github_token)
        repo = g.get_repo(repo_name)
        workflow = repo.get_workflow(workflow_id)
        workflow.create_dispatch("main")
        print(f"Workflow {workflow_id} triggered in {repo_name}")
    except Exception as e:
        print(f"Error triggering workflow: {e}")

if __name__ == "__main__":
    trigger_workflow("my-org/my-repo", "ci.yml", "your-github-token")