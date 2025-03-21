## Docker

### Basic Commands
- **Run a container:** `docker run <image-name>`  
    (e.g., `docker run nginx` - runs an Nginx container)
- **Run with options:** `docker run -d -p 8080:80 <image-name>`  
    (-d = detached, -p = port mapping: host:container)
- **List running containers:** `docker ps`
- **List all containers (incl. stopped):** `docker ps -a`
- **Stop a container:** `docker stop <container-id>`
- **Remove a container:** `docker rm <container-id>`
- **Remove an image:** `docker rmi <image-name>`

### Image Management
- **Pull an image:** `docker pull <image-name>`  
    (e.g., `docker pull ubuntu`)
- **List images:** `docker images`
- **Build an image from Dockerfile:** `docker build -t <name:tag> .`  
    (e.g., `docker build -t myapp:1.0 .`)

### Container Interaction
- **Access container shell:** `docker exec -it <container-id> /bin/bash`  
    (-it = interactive terminal)
- **View container logs:** `docker logs <container-id>`
- **Copy files to/from container:**  
    To container: `docker cp <local-path> <container-id>:<container-path>`  
    From container: `docker cp <container-id>:<container-path> <local-path>`

### Docker Compose (if applicable)
- **Start services:** `docker-compose up`
- **Start in background:** `docker-compose up -d`
- **Stop services:** `docker-compose down`
- **View services:** `docker-compose ps`

### Cleanup
- **Remove all stopped containers:** `docker container prune`
- **Remove unused images:** `docker image prune`
- **Remove everything unused:** `docker system prune`

### Tips
- **Get container ID:** Use `docker ps` to find it (first few chars are enough).
- **Help:** Add `--help` to any command (e.g., `docker run --help`).

---

## Shell scripting

### Basics
- **Shebang:** Start script with `#!/bin/bash`
- **Make executable:** `chmod +x script.sh`
- **Run script:** `./script.sh` or `bash script.sh`

### Variables
- **Set variable:** `name="value"` (no spaces around `=`)
- **Use variable:** `echo $name` or `echo ${name}`
- **Read input:** `read -p "Prompt: " var`

### Common Commands
- **Print:** `echo "text"`
- **Current directory:** `pwd`
- **List files:** `ls` (Linux) or `dir` (some environments)
- **Change directory:** `cd /path`
- **Check file exists:** `[ -f "file.txt" ] && echo "Exists"`

### Conditionals
- **If statement:**
    ```bash
    if [ "$var" == "value" ]; then
            echo "Match"
    elif [ "$var" != "other" ]; then
            echo "Not other"
    else
            echo "No match"
    fi
    ```
- **Test operators:**  
    Strings: `=`, `!=`, `-z` (empty), `-n` (not empty)  
    Numbers: `-eq`, `-ne`, `-lt`, `-gt`, `-le`, `-ge`  
    Files: `-f` (file), `-d` (dir), `-e` (exists)

### Loops
- **For loop:**
    ```bash
    for i in 1 2 3; do
            echo "Number: $i"
    done
    ```
- **While loop:**
    ```bash
    count=0
    while [ $count -lt 3 ]; do
            echo "Count: $count"
            ((count++))
    done
    ```

### Functions
- **Define function:**
    ```bash
    myfunc() {
            echo "Args: $1 $2"
    }
    ```
- **Call function:** `myfunc arg1 arg2`

### File Operations
- **Redirect output:** `echo "text" > file.txt` (overwrite), `>>` (append)
- **Read file line-by-line:**
    ```bash
    while read line; do
            echo "$line"
    done < file.txt
    ```
- **Pipe output:** `ls | grep "pattern"`

### Useful Tricks
- **Check last command status:** `if [ $? -eq 0 ]; then echo "Success"; fi`
- **Comment:** `# This is a comment`
- **Exit script:** `exit 1` (1 = error, 0 = success)
- **Sleep:** `sleep 5` (pauses 5 seconds)

### Quick Example Script
```bash
#!/bin/bash
echo "Enter name:"
read name
if [ -z "$name" ]; then
        echo "No name provided"
else
        echo "Hello, $name!"
fi
```

### Tips
- **Debug:** Run with `bash -x script.sh` to trace execution.
- **Quotes matter:** Use `"$var"` to avoid issues with spaces.
- **Focus on:** Variables, if, and loops—they’ll get you far in an hour.

---

## Git

### Setup
- **Configure user:**
    ```bash
    git config --global user.name "Your Name"
    git config --global user.email "your.email@example.com"
    ```
- **Check config:** `git config --list`

### Basic Workflow
- **Initialize repo:** `git init`
- **Clone repo:** `git clone <url>`
- **Check status:** `git status`
- **Add files:**
    ```bash
    git add <file>  # specific file
    git add .       # all changes
    ```
- **Commit changes:** `git commit -m "message"`
- **Push to remote:** `git push origin <branch>` (e.g., `git push origin main`)
- **Pull updates:** `git pull origin <branch>`

### Branching
- **List branches:** `git branch`
- **Create branch:** `git branch <branch-name>`
- **Switch branch:** `git checkout <branch-name>`
- **Create & switch:** `git checkout -b <branch-name>`
- **Merge branch:** `git merge <branch-name>` (from target branch)
- **Delete branch:** `git branch -d <branch-name>`

### History & Logs
- **View log:** `git log` (full history)
- **Short log:** `git log --oneline`
- **View changes:** `git diff` (uncommitted changes)
- **View staged changes:** `git diff --staged`

### Undo & Reset
- **Unstage file:** `git restore --staged <file>` or `git reset <file>`
- **Discard changes:** `git restore <file>` or `git checkout -- <file>`
- **Revert commit:** `git revert <commit-hash>`
- **Reset to previous state:**
    ```bash
    # Soft: keeps changes
    git reset --soft <commit-hash>
    # Hard: discards changes
    git reset --hard <commit-hash>
    ```

### Remote Management
- **Add remote:** `git remote add origin <url>`
- **View remotes:** `git remote -v`
- **Fetch changes:** `git fetch origin` (no merge)

### Stashing
- **Save uncommitted changes:** `git stash`
- **List stashes:** `git stash list`
- **Apply stash:** `git stash apply` (keeps stash)
- **Drop stash:** `git stash drop`

### Quick Tips
- **Commit hash:** Use `git log` to find it (first 7 chars are enough).
- **Fix last commit:** `git commit --amend` (edit message or add files).
- **Help:** `git <command> --help` (e.g., `git add --help`).

### Example Workflow
```bash
git init
echo "Hello" > file.txt
git add file.txt
git commit -m "Initial commit"
git branch new-feature
git checkout new-feature
echo "World" >> file.txt
git add .
git commit -m "Add feature"
git checkout main
git merge new-feature
git push origin main
```

Focus on `git add`, `git commit`, `git push`, and `git branch` to get rolling in an hour. Good luck!

---

## API automation

### Core Concepts
- **HTTP Methods:**
    - GET: Retrieve data
    - POST: Send data
    - PUT: Update data
    - DELETE: Remove data
- **Status Codes:**
    - 200: Success
    - 201: Created
    - 400: Bad request
    - 401: Unauthorized
    - 404: Not found
    - 500: Server error
- **Headers:** Key-value pairs (e.g., `Content-Type: application/json`)
- **Body:** Data sent (e.g., JSON payload)

### cURL (Command Line)
- **GET request:**
    ```bash
    curl -X GET "https://api.example.com/data"
    ```
- **POST request with JSON:**
    ```bash
    curl -X POST "https://api.example.com/data" -H "Content-Type: application/json" -d '{"key": "value"}'
    ```
- **Add headers:**
    ```bash
    curl -H "Authorization: Bearer <token>" "https://api.example.com/data"
    ```
- **Save response:**
    ```bash
    curl "https://api.example.com/data" > output.json
    ```

### Python (with requests)
- **Install:** `pip install requests`
- **Basic GET:**
    ```python
    import requests
    response = requests.get("https://api.example.com/data")
    print(response.status_code)  # 200
    print(response.json())       # JSON response
    ```
- **POST with JSON:**
    ```python
    payload = {"key": "value"}
    headers = {"Content-Type": "application/json"}
    response = requests.post("https://api.example.com/data", json=payload, headers=headers)
    print(response.text)
    ```
- **With authentication:**
    ```python
    headers = {"Authorization": "Bearer <token>"}
    response = requests.get("https://api.example.com/data", headers=headers)
    ```

### Postman
- **New Request:**
    - Select method (GET, POST, etc.)
    - Enter URL (e.g., `https://api.example.com/data`)
- **Add Headers:** Go to “Headers” tab, e.g., Key: `Content-Type`, Value: `application/json`
- **Send Body:** Go to “Body” tab, select “raw” > JSON, enter: `{"key": "value"}`
- **Test Response:** Check status code and response body
- **Scripting (Tests tab):**
    ```javascript
    pm.test("Status is 200", function () {
            pm.response.to.have.status(200);
    });
    pm.test("Check value", function () {
            var jsonData = pm.response.json();
            pm.expect(jsonData.key).to.eql("value");
    });
    ```
- **Save & Run:** Save request, use “Collection Runner” for multiple tests

### Automation Workflow
- **Identify Endpoint:** Get API URL and method from docs (e.g., GET /users).
- **Authenticate:** Use API key, token, or credentials (e.g., in headers).
- **Send Request:** Use cURL, Python, or Postman.
- **Validate Response:** Check status code and data.
- **Script It:** Chain requests or loop for bulk testing.

### Quick Python Script Example
```python
import requests

# API details
url = "https://api.example.com/users"
headers = {"Authorization": "Bearer <token>"}
payload = {"name": "John", "age": 30}

# Send POST request
response = requests.post(url, json=payload, headers=headers)

# Check response
if response.status_code == 201:
        print("Success:", response.json())
else:
        print("Failed:", response.status_code, response.text)
```

### Tips
- **Debug:** Use `-v` with cURL for verbose output or `response.text` in Python.
- **Environment Variables (Postman):** Set `{{base_url}}` for reusable URLs.
- **Rate Limits:** Check API docs for limits (e.g., 100 requests/hour).
- **JSON Parsing:** Use `jq` with cURL (`curl ... | jq .`) or Python’s `response.json()`.

Focus on sending a GET and POST request with one tool (e.g., Postman or Python) in your hour. Test a public API like `https://jsonplaceholder.typicode.com` to practice. Good luck!