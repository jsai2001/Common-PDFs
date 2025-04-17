from kubernetes import client, config

def list_pods(namespace="default"):
    config.load_kube_config()
    v1 = client.CoreV1Api()
    try:
        pods = v1.list_namespaced_pod(namespace)
        for pod in pods.items:
            print(f"Pod: {pod.metadata.name}, Status: {pod.status.phase}")
    except Exception as e:
        print(f"Error listing pods: {e}")

def create_pod(namespace="default", pod_name="my-pod"):
    config.load_kube_config()
    v1 = client.CoreV1Api()
    pod_manifest = {
        "apiVersion": "v1",
        "kind": "Pod",
        "metadata": {"name": pod_name},
        "spec": {
            "containers": [
                {
                    "name": "nginx",
                    "image": "nginx:latest",
                    "ports": [{"containerPort": 80}]
                }
            ]
        }
    }
    try:
        v1.create_namespaced_pod(namespace, pod_manifest)
        print(f"Pod {pod_name} created in namespace {namespace}")
    except Exception as e:
        print(f"Error creating pod: {e}")

if __name__ == "__main__":
    list_pods()
    create_pod()