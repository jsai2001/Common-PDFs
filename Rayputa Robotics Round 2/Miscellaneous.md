## Authentication

### Conceptual Questions:
- What is authentication in the context of server-side development, and how does it differ from authorization?
- Explain the difference between session-based authentication and token-based authentication.
- What are the common security risks associated with authentication, and how can they be mitigated?
- What is multi-factor authentication (MFA), and how would you implement it in a Python-based server application?

### Practical Questions (Python-focused):
- How would you implement a basic username/password authentication system using Flask or Django?
- Write a Python function to hash a password securely using bcrypt or argon2.
- How would you store and verify user credentials in a database securely?
- Design an authentication middleware in Python to protect certain API endpoints.
- How would you handle JWT (JSON Web Token) generation and validation in a Python application?

### Scenario-Based Questions:
- A user’s session token has been compromised. How would you detect and handle this situation on the server side?
- How would you implement a "remember me" feature securely in a Python web app?

## OAuth

### Conceptual Questions:
- What is OAuth, and how does it differ from traditional authentication methods?
- Explain the roles of the client, resource owner, authorization server, and resource server in OAuth 2.0.
- What are the different OAuth 2.0 grant types (e.g., Authorization Code, Implicit, Client Credentials), and when would you use each?
- What is the purpose of an access token and a refresh token in OAuth?

### Practical Questions (Python-focused):
- How would you implement OAuth 2.0 authentication in a Python Flask app using a provider like Google?
- Write a Python script to exchange an authorization code for an access token using the requests library.
- How would you securely store OAuth client secrets in a Python application?
- Implement a basic OAuth flow (e.g., Authorization Code flow) using Python and a library like authlib.

### Scenario-Based Questions:
- Your application needs to integrate with a third-party API using OAuth. How would you handle token expiration and refresh?
- A user reports they can’t log in via OAuth. How would you debug this issue in your Python server?

## Caching

### Conceptual Questions:
- What is caching, and why is it important in server-side development?
- What are the differences between client-side caching and server-side caching?
- Explain the concepts of cache invalidation and cache eviction. What strategies can be used for each?
- What are the pros and cons of using an in-memory cache like Redis versus a file-based cache?

### Practical Questions (Python-focused):
- How would you implement a simple in-memory cache in Python using a dictionary with a TTL (time-to-live)?
- Write a Python function to cache API responses using Redis.
- How would you integrate caching into a Flask or FastAPI application to improve performance?
- Implement a decorator in Python to cache the results of a function call.

### Scenario-Based Questions:
- Your server is experiencing high latency due to repeated database queries. How would you use caching to optimize this?
- How would you handle cache consistency when data updates frequently in a Python application?

## Asynchronous Programming Using Task Queues

### Conceptual Questions:
- What is asynchronous programming, and how does it differ from synchronous programming?
- What are task queues, and how do they improve server-side performance?
- Explain the differences between asyncio in Python and task queues like Celery or RQ.
- What are the benefits of offloading long-running tasks to a task queue?

### Practical Questions (Python-focused):
- Write an asynchronous Python function using asyncio to fetch data from multiple APIs concurrently.
- How would you set up a Celery task queue with Redis as the broker in a Python application?
- Implement a Python script to send emails asynchronously using Celery.
- Write a task in RQ (Redis Queue) to process a file upload in the background.

### Scenario-Based Questions:
- Your application needs to process a large CSV file uploaded by a user without blocking the main thread. How would you handle this with a task queue in Python?
- A task in your queue keeps failing silently. How would you debug and ensure reliability in a Python-based system?
- How would you scale a task queue system to handle thousands of tasks per minute?

## General Server-Side Development Questions

### Design and Architecture:
- How would you design a RESTful API in Python that incorporates authentication, caching, and asynchronous tasks?
- What considerations would you take into account when choosing between synchronous and asynchronous approaches for an endpoint?
- How do you ensure scalability in a Python-based server application?

### Security:
- How would you protect sensitive data (e.g., API keys, user info) in a Python server application?
- What steps would you take to prevent common attacks like SQL injection or CSRF in a Flask/Django app?

### Performance:
- How would you profile a Python server application to identify bottlenecks?
- What techniques would you use to optimize a slow API endpoint that relies on database queries?

## Coding Challenges

### Authentication:
- Write a Flask route that authenticates a user with JWT and returns a token only if credentials are valid.
- Implement a password reset functionality with a time-limited token sent via email.

### OAuth:
- Create a Python script that logs a user into a service via Google OAuth and retrieves their profile information.

### Caching:
- Build a Python class that implements an LRU (Least Recently Used) cache with a fixed size.
- Write a Flask endpoint that caches database query results for 5 minutes using Redis.

### Asynchronous Programming:
- Write a Celery task that processes an image file and saves the result to a database.
- Implement an async Python function that downloads multiple files concurrently and reports progress.

## Behavioral and Thought-Provoking Questions
- Have you worked on a project where authentication or caching significantly improved performance? Explain your approach.
- How do you decide when to use a task queue versus handling a task synchronously in Python?
- What challenges have you faced with OAuth implementation, and how did you overcome them?
- How do you stay updated on best practices for server-side development in Python?

# Authentication

## Conceptual Questions

### What is authentication in the context of server-side development, and how does it differ from authorization?

**Answer:** Authentication is the process of verifying a user’s identity—ensuring they are who they claim to be (e.g., via username/password or tokens). On the server side, it involves validating credentials and establishing a session or token. Authorization, on the other hand, determines what an authenticated user is allowed to do (e.g., access certain endpoints or resources). In short, authentication answers "Who are you?" while authorization answers "What can you do?" For example, a server might authenticate a user with a JWT and then check their role to authorize access to admin features.

### Explain the difference between session-based authentication and token-based authentication.

**Answer:** Session-based authentication relies on the server storing a session ID (usually in memory or a database) after a user logs in, which is tied to a cookie sent to the client. The server validates the session ID on each request. Token-based authentication (e.g., JWT) generates a self-contained token client-side after login, which includes user info and a signature. The server verifies the token’s validity without storing session state, making it stateless and scalable. Session-based is simpler for small apps, while token-based suits distributed systems or APIs.

### What are the common security risks associated with authentication, and how can they be mitigated?

**Answer:** Common risks include:
- **Brute force attacks:** Guessing passwords. **Mitigation:** Rate limiting login attempts and enforcing strong password policies.
- **Credential stuffing:** Reusing stolen credentials. **Mitigation:** Use MFA and monitor unusual login patterns.
- **Session hijacking:** Stealing session IDs or tokens. **Mitigation:** Use HTTPS, secure cookies with HttpOnly and Secure flags, and short token expiration times.
- **Weak passwords:** **Mitigation:** Hash passwords with strong algorithms (e.g., bcrypt) and enforce complexity rules.

In Python, libraries like Flask-Security or django-allauth can help implement these mitigations.

### What is multi-factor authentication (MFA), and how would you implement it in a Python-based server application?

**Answer:** MFA requires users to provide two or more verification factors (e.g., password + OTP) to authenticate, enhancing security. To implement it in Python (e.g., Flask):
1. Store user credentials securely (hashed passwords in a DB).
2. Use a library like pyotp to generate time-based OTPs (TOTP).
3. After password validation, send an OTP via email/SMS (e.g., using smtplib or Twilio) and prompt the user.
4. Verify the OTP server-side before granting access.

Example: Combine Flask-Login for session management with pyotp for OTP generation and validation.

## Practical Questions (Python-focused)

### How would you implement a basic username/password authentication system using Flask or Django?

**Answer:** I’ll explain with Flask:
1. Use Flask-SQLAlchemy for a User model with id, username, and password_hash.
2. Hash passwords with bcrypt during registration.
3. On login, verify the password and create a session with Flask-Login.

Example:
```python
from flask import Flask, request, redirect
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager, UserMixin, login_user
import bcrypt

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///users.db'
db = SQLAlchemy(app)
login_manager = LoginManager(app)

class User(db.Model, UserMixin):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True)
    password_hash = db.Column(db.String(120))

@app.route('/login', methods=['POST'])
def login():
    username = request.form['username']
    password = request.form['password']
    user = User.query.filter_by(username=username).first()
    if user and bcrypt.checkpw(password.encode(), user.password_hash):
        login_user(user)
        return redirect('/dashboard')
    return 'Invalid credentials'
```

In Django, I’d use the built-in User model and authenticate() function.

### Write a Python function to hash a password securely using bcrypt or argon2.

**Answer:** Using bcrypt:
```python
import bcrypt

def hash_password(password: str) -> bytes:
    salt = bcrypt.gensalt()  # Generate a random salt
    hashed = bcrypt.hashpw(password.encode('utf-8'), salt)
    return hashed

def verify_password(password: str, hashed: bytes) -> bool:
    return bcrypt.checkpw(password.encode('utf-8'), hashed)

# Usage
pwd = "mysecretpassword"
hashed_pwd = hash_password(pwd)
print(verify_password(pwd, hashed_pwd))  # True
```

argon2 is similar but uses the argon2.PasswordHasher class from the argon2-cffi library.

### How would you store and verify user credentials in a database securely?

**Answer:** 
- **Storage:** Never store plain passwords. Hash them with a strong algorithm like bcrypt or argon2 (which include salts automatically) and save the hash in the database (e.g., SQLite, PostgreSQL). Use a VARCHAR or BINARY column for the hash.
- **Verification:** On login, retrieve the stored hash, use the hashing library’s verification function (e.g., bcrypt.checkpw), and compare it with the user-provided password.

Example: See the User model and verify_password function above.

Use environment variables (e.g., python-dotenv) to hide DB credentials.

### Design an authentication middleware in Python to protect certain API endpoints.

**Answer:** Using Flask:
```python
from flask import Flask, request, jsonify
import jwt

app = Flask(__name__)
SECRET_KEY = "mysecretkey"

def auth_middleware(f):
    def wrapper(*args, **kwargs):
        token = request.headers.get('Authorization')
        if not token:
            return jsonify({"error": "Token missing"}), 401
        try:
            jwt.decode(token.replace("Bearer ", ""), SECRET_KEY, algorithms=["HS256"])
        except jwt.InvalidTokenError:
            return jsonify({"error": "Invalid token"}), 401
        return f(*args, **kwargs)
    return wrapper

@app.route('/protected', methods=['GET'])
@auth_middleware
def protected_route():
    return jsonify({"message": "This is protected"})
```

This checks for a valid JWT in the Authorization header before allowing access.

### How would you handle JWT (JSON Web Token) generation and validation in a Python application?

**Answer:** Using the PyJWT library:
```python
import jwt
from datetime import datetime, timedelta

SECRET_KEY = "mysecretkey"

def generate_jwt(user_id: int) -> str:
    payload = {
        "user_id": user_id,
        "exp": datetime.utcnow() + timedelta(minutes=30),  # Expires in 30 mins
        "iat": datetime.utcnow()  # Issued at
    }
    return jwt.encode(payload, SECRET_KEY, algorithm="HS256")

def validate_jwt(token: str) -> dict:
    try:
        return jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
    except jwt.ExpiredSignatureError:
        raise Exception("Token expired")
    except jwt.InvalidTokenError:
        raise Exception("Invalid token")

# Usage
token = generate_jwt(1)
print(validate_jwt(token))  # {'user_id': 1, ...}
```

Store the SECRET_KEY securely (e.g., in environment variables).

## Scenario-Based Questions

### A user’s session token has been compromised. How would you detect and handle this situation on the server side?

**Answer:** 
- **Detection:** Look for anomalies like multiple logins from different IPs, unusual activity (e.g., rapid requests), or token use after logout. Use server logs or a monitoring tool.
- **Handling:** 
  - Invalidate the compromised token by maintaining a blacklist (e.g., in Redis) or shortening token expiration times.
  - Force a logout by updating a user’s session version in the DB and checking it on each request.
  - Notify the user via email and require re-authentication (possibly with MFA).

For JWTs, since they’re stateless, use short-lived tokens and refresh tokens to minimize damage.

### How would you implement a "remember me" feature securely in a Python web app?

**Answer:** 
- On login, if "remember me" is checked, generate a long-lived token (e.g., 30 days) alongside a short-lived session token.
- Store the token hash in the DB with an expiration date and associate it with the user.
- Send the token as a secure cookie (HttpOnly, Secure, SameSite=Strict).
- On subsequent requests, verify the token and regenerate a session if valid.

Example in Flask:
```python
from flask import make_response
import secrets

@app.route('/login', methods=['POST'])
def login():
    # Authenticate user...
    if request.form.get('remember_me'):
        token = secrets.token_hex(16)
        # Store hash in DB with user ID and expiry
        resp = make_response(redirect('/dashboard'))
        resp.set_cookie('remember_token', token, max_age=2592000, httponly=True, secure=True)
        return resp
```

Mitigate risks by allowing users to revoke remembered sessions from their account.

# OAuth

## Conceptual Questions

### What is OAuth, and how does it differ from traditional authentication methods?

**Answer:** OAuth is an authorization framework that allows a third-party application to access a user’s resources (e.g., profile data) without sharing their credentials. It uses tokens issued by an authorization server after user consent. Unlike traditional authentication (e.g., username/password checked by the server), OAuth delegates identity verification to an external provider (like Google) and provides an access token for resource access. This improves security by keeping credentials off your server and enhances user experience with single sign-on.

### Explain the roles of the client, resource owner, authorization server, and resource server in OAuth 2.0.

**Answer:**
- **Client:** The application (e.g., your Python app) requesting access to a user’s resources.
- **Resource Owner:** The user who owns the data and grants permission to the client.
- **Authorization Server:** The server (e.g., Google’s OAuth server) that authenticates the user and issues tokens after consent.
- **Resource Server:** The server hosting the protected resources (e.g., Google’s API), which accepts and validates access tokens from the client.

Example: My Flask app (client) asks a user (resource owner) to log in via Google (authorization server) to access their profile (resource server).

### What are the different OAuth 2.0 grant types (e.g., Authorization Code, Implicit, Client Credentials), and when would you use each?

**Answer:**
- **Authorization Code:** A two-step flow where the client gets a code, then exchanges it for an access token. Used for secure server-side apps (e.g., Flask web apps) because it keeps tokens off the client-side.
- **Implicit:** Directly returns an access token to the client (e.g., via URL fragment). Used for single-page apps or mobile apps with no backend, but less secure due to token exposure.
- **Client Credentials:** Grants a token based on the client’s own credentials (no user involved). Used for machine-to-machine communication, like a server accessing an API.
- **Password:** Exchanges a user’s username/password for a token. Rare, used only in trusted apps due to security risks.

I’d use Authorization Code for a web app, Implicit for a JavaScript app, and Client Credentials for a cron job.

### What is the purpose of an access token and a refresh token in OAuth?

**Answer:** An access token is a short-lived credential (e.g., expires in 1 hour) used by the client to access protected resources on the resource server. A refresh token is a longer-lived credential used to obtain a new access token when the old one expires, without requiring user re-authentication. This balances security (short access token lifespan) with convenience (no frequent logins). For example, my app uses the access token for API calls and the refresh token to maintain the session.

## Practical Questions (Python-focused)

### How would you implement OAuth 2.0 authentication in a Python Flask app using a provider like Google?

**Answer:** I’d use Flask with the authlib library for Google OAuth:
1. Register my app in Google Cloud Console to get a client ID and secret.
2. Set up login and callback routes.

Example:
```python
from flask import Flask, redirect, url_for
from authlib.integrations.flask_client import OAuth

app = Flask(__name__)
app.secret_key = "randomsecret"
oauth = OAuth(app)

google = oauth.register(
    name='google',
    client_id='YOUR_CLIENT_ID',
    client_secret='YOUR_CLIENT_SECRET',
    authorize_url='https://accounts.google.com/o/oauth2/auth',
    token_url='https://oauth2.googleapis.com/token',
    userinfo_endpoint='https://www.googleapis.com/oauth2/v3/userinfo',
    client_kwargs={'scope': 'openid email profile'}
)

@app.route('/login')
def login():
    redirect_uri = url_for('authorize', _external=True)
    return google.authorize_redirect(redirect_uri)

@app.route('/authorize')
def authorize():
    token = google.authorize_access_token()
    user_info = google.parse_id_token(token)
    # Store user_info or token in session/DB
    return f"Logged in as {user_info['email']}"
```

This uses the Authorization Code flow, redirecting users to Google for consent, then fetching their profile.

### Write a Python script to exchange an authorization code for an access token using the requests library.

**Answer:**
```python
import requests

def exchange_code_for_token(auth_code: str, client_id: str, client_secret: str, redirect_uri: str) -> dict:
    url = 'https://oauth2.googleapis.com/token'  # Google’s token endpoint
    payload = {
        'code': auth_code,
        'client_id': client_id,
        'client_secret': client_secret,
        'redirect_uri': redirect_uri,
        'grant_type': 'authorization_code'
    }
    response = requests.post(url, data=payload)
    response.raise_for_status()
    return response.json()

# Usage
auth_code = "received-auth-code-from-google"
token_data = exchange_code_for_token(
    auth_code, 'YOUR_CLIENT_ID', 'YOUR_CLIENT_SECRET', 'http://localhost:5000/authorize'
)
print(token_data['access_token'])  # Use this to access resources
```

This sends a POST request to swap the code for tokens.

### How would you securely store OAuth client secrets in a Python application?

**Answer:** I’d avoid hardcoding secrets in the source code. Instead:
- Store them in environment variables using a .env file and the python-dotenv library.

Example:
```python
from dotenv import load_dotenv
import os

load_dotenv()
CLIENT_ID = os.getenv('OAUTH_CLIENT_ID')
CLIENT_SECRET = os.getenv('OAUTH_CLIENT_SECRET')
```

In production, use a secrets management service (e.g., AWS Secrets Manager or HashiCorp Vault) and fetch secrets at runtime. Ensure the .env file is excluded from version control (e.g., via .gitignore).

### Implement a basic OAuth flow (e.g., Authorization Code flow) using Python and a library like authlib.

**Answer:** Here’s a Flask example with authlib:
```python
from flask import Flask, redirect, url_for, session
from authlib.integrations.flask_client import OAuth

app = Flask(__name__)
app.secret_key = "randomsecret"
oauth = OAuth(app)

google = oauth.register(
    name='google',
    client_id='YOUR_CLIENT_ID',
    client_secret='YOUR_CLIENT_SECRET',
    authorize_url='https://accounts.google.com/o/oauth2/auth',
    token_url='https://oauth2.googleapis.com/token',
    userinfo_endpoint='https://www.googleapis.com/oauth2/v3/userinfo',
    client_kwargs={'scope': 'openid email profile'}
)

@app.route('/login')
def login():
    redirect_uri = url_for('callback', _external=True)
    return google.authorize_redirect(redirect_uri)

@app.route('/callback')
def callback():
    token = google.authorize_access_token()
    session['token'] = token
    user_info = google.get('https://www.googleapis.com/oauth2/v3/userinfo').json()
    return f"Welcome, {user_info['name']}"
```

The flow: User clicks login, gets redirected to Google, consents, then returns to /callback with a code that’s exchanged for tokens.

## Scenario-Based Questions

### Your application needs to integrate with a third-party API using OAuth. How would you handle token expiration and refresh?

**Answer:** I’d store both the access token and refresh token in a secure database (e.g., hashed or encrypted). Steps:
1. Before each API call, check if the access token is expired (using its expires_in value or timestamp).
2. If expired, use the refresh token to request a new access token:
```python
import requests

def refresh_token(refresh_token: str, client_id: str, client_secret: str) -> dict:
    response = requests.post('https://provider.com/token', data={
        'grant_type': 'refresh_token',
        'refresh_token': refresh_token,
        'client_id': client_id,
        'client_secret': client_secret
    })
    new_tokens = response.json()
    return new_tokens['access_token'], new_tokens.get('refresh_token', refresh_token)
```

Update the stored tokens and retry the API call. I’d implement this lazily (on 401 errors) or proactively (scheduled checks).

### A user reports they can’t log in via OAuth. How would you debug this issue in your Python server?

**Answer:** I’d follow these steps:
1. Check logs: Look for errors in Flask logs (e.g., logging module) or server logs for failed requests.
2. Verify configuration: Ensure client ID, secret, and redirect URI match the OAuth provider’s settings.
3. Test the flow: Manually trigger the login route and inspect the authorization URL and callback response.
4. Inspect tokens: Print or log the token response (e.g., token = google.authorize_access_token()) to check for errors like invalid_grant.
5. Network issues: Use requests with debug logging (import logging; logging.basicConfig(level=logging.DEBUG)) to trace HTTP calls.

Common fixes: Expired client secret, mismatched redirect URI, or user denying consent. I’d inform the user if it’s a consent issue.

# Caching

## Conceptual Questions

### What is caching, and why is it important in server-side development?
**Answer:** Caching is storing frequently accessed data in a fast-access location (e.g., memory) to reduce the time and resources needed to fetch it repeatedly. In server-side development, it’s vital because it lowers latency (e.g., faster API responses), reduces load on databases or external services, and improves scalability by handling more requests efficiently. For example, caching user profiles avoids redundant DB queries, enhancing performance.

### What are the differences between client-side caching and server-side caching?
**Answer:** Client-side caching stores data on the user’s device (e.g., browser cache via HTTP headers like Cache-Control), reducing server requests but relying on client capabilities. Server-side caching stores data on the server (e.g., in Redis or memory), speeding up backend operations and serving multiple clients consistently. Client-side is great for static assets; server-side excels for dynamic data like query results.

### Explain the concepts of cache invalidation and cache eviction. What strategies can be used for each?
**Answer:**
- **Cache Invalidation:** Removing or updating cached data when it becomes outdated. Strategies:
    - Time-based: Set a TTL (e.g., 5 minutes) to expire data automatically.
    - Event-based: Invalidate on data updates (e.g., clear cache when a user updates their profile).
- **Cache Eviction:** Removing data from the cache when it’s full. Strategies:
    - LRU (Least Recently Used): Evict the least recently accessed items.
    - LFU (Least Frequently Used): Evict the least-used items.
    - FIFO (First In, First Out): Evict the oldest items.
    - In Python, Redis supports TTL for invalidation and LRU eviction out of the box.

### What are the pros and cons of using an in-memory cache like Redis versus a file-based cache?
**Answer:**
- **In-memory (Redis):**
    - Pros: Extremely fast (microsecond access), scalable across servers, supports advanced features (e.g., TTL, pub/sub).
    - Cons: Limited by RAM (costly), data lost on restart unless persisted.
- **File-based:**
    - Pros: Persistent (survives restarts), cheap for large datasets, no memory limit.
    - Cons: Slower (disk I/O), harder to scale or share across servers.
- I’d use Redis for high-speed needs (e.g., API caching) and files for static, infrequently accessed data.

## Practical Questions (Python-focused)

### How would you implement a simple in-memory cache in Python using a dictionary with a TTL (time-to-live)?
**Answer:**
```python
from time import time
from typing import Any

class SimpleCache:
        def __init__(self):
                self.cache = {}  # {key: (value, expiry_time)}

        def set(self, key: str, value: Any, ttl: int) -> None:
                self.cache[key] = (value, time() + ttl)

        def get(self, key: str) -> Any:
                if key in self.cache:
                        value, expiry = self.cache[key]
                        if time() < expiry:
                                return value
                        del self.cache[key]  # Expired
                return None

# Usage
cache = SimpleCache()
cache.set("user:1", {"name": "Alice"}, 10)  # 10-second TTL
print(cache.get("user:1"))  # {"name": "Alice"}
```
This uses a dictionary with timestamps to enforce TTL.

### Write a Python function to cache API responses using Redis.
**Answer:**
```python
import redis
import requests
import json

redis_client = redis.StrictRedis(host='localhost', port=6379, db=0)

def get_cached_api_response(url: str, ttl: int = 300) -> dict:
        cache_key = f"api:{url}"
        cached = redis_client.get(cache_key)
        if cached:
                return json.loads(cached)
        response = requests.get(url)
        data = response.json()
        redis_client.setex(cache_key, ttl, json.dumps(data))  # TTL in seconds
        return data

# Usage
data = get_cached_api_response("https://api.example.com/data")
print(data)
```
`setex` sets the key with a TTL (e.g., 300s = 5 minutes).

### How would you integrate caching into a Flask or FastAPI application to improve performance?
**Answer:** In Flask:
Use Redis or `flask-caching` to cache expensive operations (e.g., DB queries).
Example with `flask-caching`:
```python
from flask import Flask
from flask_caching import Cache

app = Flask(__name__)
cache = Cache(app, config={'CACHE_TYPE': 'redis', 'CACHE_REDIS_URL': 'redis://localhost:6379/0'})

@app.route('/data')
@cache.cached(timeout=60)  # Cache for 60 seconds
def get_data():
        # Expensive DB query or API call
        return {"data": "expensive_result"}
```
In FastAPI, I’d use a dependency with Redis directly or a library like `fastapi-cache`. This reduces latency by serving cached responses.

### Implement a decorator in Python to cache the results of a function call.
**Answer:**
```python
from functools import wraps
from time import time

def cache_with_ttl(ttl: int):
        def decorator(func):
                cache = {}  # {args: (result, expiry)}
                @wraps(func)
                def wrapper(*args):
                        key = str(args)
                        if key in cache:
                                result, expiry = cache[key]
                                if time() < expiry:
                                        return result
                        result = func(*args)
                        cache[key] = (result, time() + ttl)
                        return result
                return wrapper
        return decorator

# Usage
@cache_with_ttl(5)  # 5-second TTL
def expensive_calc(x):
        print(f"Calculating for {x}")
        return x * x

print(expensive_calc(4))  # Calculates: 16
print(expensive_calc(4))  # Cached: 16
```
This caches results based on arguments with a TTL.

## Scenario-Based Questions

### Your server is experiencing high latency due to repeated database queries. How would you use caching to optimize this?
**Answer:** I’d:
1. Identify slow queries (e.g., user profile fetches) using profiling tools (e.g., Flask-DebugToolbar).
2. Cache results in Redis with a key like `user:{user_id}` and a TTL (e.g., 10 minutes).
3. Check the cache before querying the DB:
```python
def get_user(user_id):
        key = f"user:{user_id}"
        cached = redis_client.get(key)
        if cached:
                return json.loads(cached)
        user = db.query(User).get(user_id)
        redis_client.setex(key, 600, json.dumps(user.to_dict()))
        return user
```
This reduces DB load and speeds up responses.

### How would you handle cache consistency when data updates frequently in a Python application?
**Answer:** I’d use a write-through or cache-aside with invalidation strategy:
- **Write-through:** Update the cache and DB simultaneously on writes.
- **Cache-aside with invalidation:** Invalidate the cache (e.g., `redis_client.delete(key)`) when data changes, then fetch fresh data on the next request.
Example:
```python
def update_user(user_id, new_name):
        db.query(User).filter_by(id=user_id).update({"name": new_name})
        redis_client.delete(f"user:{user_id}")  # Invalidate
```
For high-frequency updates, I’d shorten TTLs or use pub/sub to notify cache updates.

# Asynchronous Programming Using Task Queues

## Conceptual Questions

### What is asynchronous programming, and how does it differ from synchronous programming?
**Answer:** Asynchronous programming allows tasks to run independently, not blocking the main thread while waiting (e.g., for I/O). Synchronous programming executes tasks sequentially, waiting for each to complete. In Python, `asyncio` enables async operations (e.g., fetching APIs concurrently), improving efficiency over synchronous blocking calls (e.g., `requests.get`).

### What are task queues, and how do they improve server-side performance?
**Answer:** Task queues offload time-consuming tasks (e.g., file processing) to background workers, freeing the server to handle requests immediately. They improve performance by preventing blocking, enabling parallelism with multiple workers, and enhancing responsiveness. For example, Celery with Redis lets my app return a response while processing a task asynchronously.

### Explain the differences between asyncio in Python and task queues like Celery or RQ.
**Answer:**
- **asyncio:** Runs async tasks within a single process using an event loop, ideal for I/O-bound tasks (e.g., API calls). It’s lightweight but doesn’t scale across machines.
- **Task Queues (Celery/RQ):** Distribute tasks to separate processes or machines via a broker (e.g., Redis), perfect for CPU-bound or long-running tasks. They’re heavier but offer durability and scalability.
- I’d use `asyncio` for quick concurrent API calls, Celery for heavy background jobs.

### What are the benefits of offloading long-running tasks to a task queue?
**Answer:** Benefits include:
- Responsiveness: Users get immediate feedback instead of waiting.
- Scalability: Add workers to handle more tasks.
- Reliability: Tasks retry on failure (with Celery/RQ configs).
- Example: Offloading image processing to Celery keeps my Flask app snappy.

## Practical Questions (Python-focused)

### Write an asynchronous Python function using asyncio to fetch data from multiple APIs concurrently.
**Answer:**
```python
import asyncio
import aiohttp

async def fetch_url(session, url):
        async with session.get(url) as response:
                return await response.json()

async def fetch_multiple_apis(urls):
        async with aiohttp.ClientSession() as session:
                tasks = [fetch_url(session, url) for url in urls]
                return await asyncio.gather(*tasks)

# Usage
urls = ["https://api.example.com/1", "https://api.example.com/2"]
result = asyncio.run(fetch_multiple_apis(urls))
print(result)
```
`asyncio.gather` runs tasks concurrently.

### How would you set up a Celery task queue with Redis as the broker in a Python application?
**Answer:**
1. Install: `pip install celery redis`.
2. Configure in `celery_app.py`:
```python
from celery import Celery

app = Celery('myapp', broker='redis://localhost:6379/0', backend='redis://localhost:6379/0')

@app.task
def add(x, y):
        return x + y
```
3. Run worker: `celery -A celery_app worker --loglevel=info`.
4. Call task: `add.delay(2, 3)` from your app.
Redis acts as the message broker and stores results.

### Implement a Python script to send emails asynchronously using Celery.
**Answer:**
```python
from celery import Celery
import smtplib
from email.mime.text import MIMEText

app = Celery('email_app', broker='redis://localhost:6379/0')

@app.task
def send_email(to: str, subject: str, body: str):
        msg = MIMEText(body)
        msg['Subject'] = subject
        msg['From'] = 'sender@example.com'
        msg['To'] = to
        with smtplib.SMTP('smtp.example.com') as server:
                server.login('username', 'password')
                server.send_message(msg)
        return f"Email sent to {to}"

# Usage
send_email.delay('user@example.com', 'Test', 'Hello from Celery!')
```
This sends emails in the background.

### Write a task in RQ (Redis Queue) to process a file upload in the background.
**Answer:**
```python
from rq import Queue
from redis import Redis
import os

redis_conn = Redis(host='localhost', port=6379)
q = Queue(connection=redis_conn)

def process_upload(file_path: str):
        # Simulate processing
        with open(file_path, 'r') as f:
                data = f.read()
        os.remove(file_path)  # Clean up
        return f"Processed {file_path} with content: {data}"

# Enqueue task
job = q.enqueue(process_upload, '/tmp/upload.txt')
print(job.id)  # Track with job ID
```
Run worker: `rq worker`. The file processes asynchronously.

## Scenario-Based Questions

### Your application needs to process a large CSV file uploaded by a user without blocking the main thread. How would you handle this with a task queue in Python?
**Answer:** I’d use Celery:
Save the file temporarily and enqueue a task:
```python
from celery import Celery
import csv

app = Celery('myapp', broker='redis://localhost:6379/0')

@app.task
def process_csv(file_path):
        with open(file_path, 'r') as f:
                reader = csv.DictReader(f)
                for row in reader:
                        # Process row (e.g., save to DB)
                        print(row)
        return "Done"

@app.route('/upload', methods=['POST'])
def upload():
        file = request.files['file']
        file_path = f"/tmp/{file.filename}"
        file.save(file_path)
        task = process_csv.delay(file_path)
        return {"task_id": task.id}
```
The user gets a task ID instantly while Celery processes the file.

### A task in your queue keeps failing silently. How would you debug and ensure reliability in a Python-based system?
**Answer:**
- Debug: Check worker logs (`celery -A myapp worker --loglevel=DEBUG`) or RQ dashboard for errors. Add logging to the task:
```python
import logging
@app.task
def my_task():
        logging.info("Starting task")
        # Task logic
```
- Reliability: Use retries (`@app.task(max_retries=3`)), set a timeout, and store task results in the backend to track failures. Test with `task.get()` in development.

### How would you scale a task queue system to handle thousands of tasks per minute?
**Answer:** I’d:
1. Add more workers (e.g., `celery -A myapp worker -c 10` for 10 concurrent tasks per worker).
2. Use a robust broker like Redis or RabbitMQ with clustering for high throughput.
3. Split tasks into multiple queues (e.g., high_priority, low_priority) and assign workers accordingly.
4. Monitor with tools like Flower (Celery) or RQ Dashboard and auto-scale workers on a cloud platform (e.g., AWS ECS).

## General Server-Side Development Questions

### Design and Architecture

**How would you design a RESTful API in Python that incorporates authentication, caching, and asynchronous tasks?**

**Answer:** I’d use Flask or FastAPI for flexibility. Here’s the design:

**Authentication:** Use JWT (JSON Web Tokens) for stateless auth. Implement a `/login` endpoint to issue tokens and a middleware to protect routes (e.g., check Authorization header).

```python
from flask import Flask, request, jsonify
import jwt

app = Flask(__name__)
SECRET_KEY = "mysecret"

def auth_middleware(f):
    def wrapper(*args, **kwargs):
        token = request.headers.get('Authorization', '').replace('Bearer ', '')
        try:
            jwt.decode(token, SECRET_KEY, algorithms=['HS256'])
        except:
            return jsonify({"error": "Unauthorized"}), 401
        return f(*args, **kwargs)
    return wrapper

@app.route('/protected', methods=['GET'])
@auth_middleware
def protected():
    return jsonify({"message": "Success"})
```

**Caching:** Use Redis to cache frequent queries (e.g., user data) with a TTL:

```python
import redis

redis_client = redis.StrictRedis(host='localhost', port=6379)

@app.route('/user/<user_id>')
def get_user(user_id):
    key = f"user:{user_id}"
    cached = redis_client.get(key)
    if cached:
        return jsonify(cached.decode())
    user = db.get_user(user_id)  # DB call
    redis_client.setex(key, 300, user.to_json())  # 5-min TTL
    return jsonify(user.to_json())
```

**Asynchronous Tasks:** Offload slow tasks (e.g., email sending) to Celery with Redis as the broker:

```python
from celery import Celery

celery_app = Celery('myapp', broker='redis://localhost:6379/0')

@celery_app.task
def send_email(to, subject):
    # Email logic
    pass

@app.route('/notify', methods=['POST'])
def notify():
    send_email.delay(request.json['to'], "Hello")
    return jsonify({"status": "Email queued"})
```

**Structure:** Use blueprints in Flask for modularity, with routes like `/auth`, `/users`, and `/tasks`.

**What considerations would you take into account when choosing between synchronous and asynchronous approaches for an endpoint?**

**Answer:** I’d consider:

- **Task Type:** Synchronous for CPU-bound tasks (e.g., data processing) or quick operations (e.g., simple DB reads). Asynchronous for I/O-bound tasks (e.g., API calls, file operations).
- **Latency:** Async reduces wait time for I/O (e.g., asyncio for concurrent API fetches), while sync is simpler for fast responses.
- **Scalability:** Async handles more concurrent requests with fewer threads (e.g., FastAPI), but sync may suffice for low traffic.
- **Complexity:** Sync is easier to implement and debug; async requires event loops or queues (e.g., Celery).

**Example:** I’d use sync Flask for a quick `/status` check, but async FastAPI with aiohttp for a `/fetch-multi` endpoint hitting multiple APIs.

**How do you ensure scalability in a Python-based server application?**

**Answer:** I’d:

- **Horizontal Scaling:** Deploy multiple instances behind a load balancer (e.g., Nginx, AWS ELB) using a WSGI server like Gunicorn.
- **Stateless Design:** Use JWT or Redis for sessions, avoiding server-specific state.
- **Database Optimization:** Use connection pooling (e.g., SQLAlchemy) and caching (e.g., Redis).
- **Asynchronous Tasks:** Offload heavy work to Celery/RQ with multiple workers.
- **Monitoring:** Use tools like Prometheus and Grafana to track performance and scale based on load.

**Example:** Gunicorn with `gunicorn -w 4 myapp:app` for 4 workers, plus Redis for shared caching.

### Security

**How would you protect sensitive data (e.g., API keys, user info) in a Python server application?**

**Answer:**

- **API Keys:** Store in environment variables with python-dotenv:

```python
from dotenv import load_dotenv
import os

load_dotenv()
API_KEY = os.getenv('API_KEY')
```

- **User Info:** Hash passwords with bcrypt, encrypt sensitive data (e.g., PII) with cryptography before storing in the DB.
- **Transmission:** Use HTTPS (e.g., via Flask with SSL or a reverse proxy like Nginx).
- **Access:** Restrict with RBAC (role-based access control) and secure cookies (HttpOnly, Secure).

In production, I’d use a secrets manager like AWS Secrets Manager.

**What steps would you take to prevent common attacks like SQL injection or CSRF in a Flask/Django app?**

**Answer:**

- **SQL Injection:**
  - Use an ORM (e.g., SQLAlchemy in Flask, Django ORM) with parameterized queries instead of raw SQL.
  - **Example:** `User.query.filter_by(username=username).first()` instead of string concatenation.

- **CSRF:**
  - In Flask, use Flask-WTF with CSRF tokens:

```python
from flask_wtf import FlaskForm
from wtforms import StringField

class MyForm(FlaskForm):
    name = StringField('Name')

@app.route('/submit', methods=['POST'])
def submit():
    form = MyForm()
    if form.validate_on_submit():
        return "Success"
    return "CSRF Error", 403
```

- In Django, enable CsrfViewMiddleware and use `{% csrf_token %}` in templates.
- Additional steps: Validate/sanitize inputs, use HTTPS, and limit request rates.

### Performance

**How would you profile a Python server application to identify bottlenecks?**

**Answer:** I’d:

- Use cProfile or line_profiler for code-level profiling:

```python
import cProfile

def profile():
    cProfile.run('my_function()')
```

- Use Flask-DebugToolbar or django-debug-toolbar for request-level insights (e.g., slow queries).
- Monitor with `time.time()` or logging to time specific blocks:

```python
import time

start = time.time()
# Slow operation
print(f"Duration: {time.time() - start}")
```

- Check system resources (CPU, memory) with psutil or external tools like New Relic. Focus on DB queries, API calls, or loops.

**What techniques would you use to optimize a slow API endpoint that relies on database queries?**

**Answer:** I’d:

- **Caching:** Cache results in Redis:

```python
def get_user(user_id):
    key = f"user:{user_id}"
    cached = redis_client.get(key)
    if cached:
        return json.loads(cached)
    user = db.query(User).get(user_id)
    redis_client.setex(key, 300, json.dumps(user.to_dict()))
    return user
```

- **Query Optimization:** Add indexes to frequently queried columns (e.g., username), use EXPLAIN to analyze queries, and fetch only needed fields (select_related in Django).
- **Batching:** Reduce round-trips by fetching multiple records at once.
- **Async:** Offload slow DB writes to Celery if they don’t need immediate results.
- **Load Testing:** Use locust to simulate traffic and identify limits.

### Coding Challenges

**Authentication**

**Write a Flask route that authenticates a user with JWT and returns a token only if credentials are valid.**

**Answer:**

```python
from flask import Flask, request, jsonify
import jwt
import bcrypt
from datetime import datetime, timedelta

app = Flask(__name__)
SECRET_KEY = "mysecretkey"

# Simulated user database
users = {"testuser": bcrypt.hashpw("password123".encode(), bcrypt.gensalt())}

@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    username = data.get('username')
    password = data.get('password', '').encode()

    if username in users and bcrypt.checkpw(password, users[username]):
        token = jwt.encode({
            'username': username,
            'exp': datetime.utcnow() + timedelta(minutes=30)  # 30-min expiry
        }, SECRET_KEY, algorithm='HS256')
        return jsonify({'token': token})
    return jsonify({'error': 'Invalid credentials'}), 401

if __name__ == '__main__':
    app.run(debug=True)
```

**Explanation:** The `/login` route checks the username and password against a mock DB (hashed with bcrypt). If valid, it generates a JWT with a 30-minute expiry and returns it.

**Implement a password reset functionality with a time-limited token sent via email.**

**Answer:**

```python
from flask import Flask, request, jsonify
import jwt
import smtplib
from email.mime.text import MIMEText
from datetime import datetime, timedelta

app = Flask(__name__)
SECRET_KEY = "mysecretkey"
EMAIL = "sender@example.com"
EMAIL_PASSWORD = "password"

@app.route('/reset-password', methods=['POST'])
def request_reset():
    email = request.get_json().get('email')
    token = jwt.encode({
        'email': email,
        'exp': datetime.utcnow() + timedelta(minutes=15)  # 15-min expiry
    }, SECRET_KEY, algorithm='HS256')

    # Send email
    msg = MIMEText(f"Reset your password: http://example.com/reset?token={token}")
    msg['Subject'] = 'Password Reset'
    msg['From'] = EMAIL
    msg['To'] = email
    with smtplib.SMTP('smtp.example.com') as server:
        server.login(EMAIL, EMAIL_PASSWORD)
        server.send_message(msg)

    return jsonify({'message': 'Reset email sent'})

@app.route('/reset', methods=['POST'])
def reset_password():
    data = request.get_json()
    token = data.get('token')
    new_password = data.get('password')
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=['HS256'])
        email = payload['email']
        # Update password in DB (simulated)
        print(f"Password for {email} reset to {new_password}")
        return jsonify({'message': 'Password reset successful'})
    except jwt.ExpiredSignatureError:
        return jsonify({'error': 'Token expired'}), 400
    except jwt.InvalidTokenError:
        return jsonify({'error': 'Invalid token'}), 400

if __name__ == '__main__':
    app.run(debug=True)
```

**Explanation:** `/reset-password` generates a 15-minute JWT and emails it. `/reset` validates the token and simulates a password update.

**OAuth**

**Create a Python script that logs a user into a service via Google OAuth and retrieves their profile information.**

**Answer:**

```python
from authlib.integrations.requests_client import OAuth
```

### Behavioral and Thought-Provoking Questions

**Have you worked on a project where authentication or caching significantly improved performance? Explain your approach.**

**Answer:** Yes, in a recent project—a Flask-based e-commerce API—caching dramatically improved performance. We had an endpoint fetching product listings from a database, which was slow due to repeated queries under high traffic. I implemented Redis caching to store the top 100 products, updated hourly, since they rarely changed. My approach was:

- Profiled the endpoint with cProfile to confirm the DB query was the bottleneck.
- Added a Redis layer: check the cache first, fall back to the DB if missed, and store results with a 1-hour TTL.
- Used a write-through strategy to update the cache on product changes.

This cut response times from 500ms to under 50ms and reduced DB load by 70%. For authentication, we used JWTs, which kept the system stateless and performant by avoiding session lookups, further boosting scalability. The combination made the API much faster and more reliable.

**How do you decide when to use a task queue versus handling a task synchronously in Python?**

**Answer:** My decision hinges on task characteristics and user experience:

- **Task Duration:** If a task takes more than a few seconds (e.g., file processing, email sending), I use a task queue like Celery to avoid blocking the main thread and keep the API responsive.
- **I/O vs. CPU:** For I/O-bound tasks (e.g., API calls), I might use asyncio synchronously if quick, but offload to a queue if it’s long-running or needs retries. CPU-bound tasks (e.g., image resizing) always go to a queue for parallel processing.
- **User Feedback:** If immediate results are critical (e.g., a search query), I keep it synchronous. If delayed completion is acceptable (e.g., generating a report), I use a queue and return a task ID.

For example, in a reporting tool, I handled quick DB queries synchronously but queued PDF generation with Celery, balancing speed and scalability.

**What challenges have you faced with OAuth implementation, and how did you overcome them?**

**Answer:** In a project integrating Google OAuth for user login, I faced two main challenges:

- **Redirect URI Mismatch:** The callback URL in my Flask app didn’t match Google’s registered URI due to a local vs. production mismatch. I debugged by logging the OAuth flow, fixed the URI in Google Console, and used environment variables to dynamically set it in code (`os.getenv('REDIRECT_URI')`).
- **Token Expiration Handling:** Users got 401 errors after tokens expired. I added refresh token logic using requests to fetch new access tokens silently, storing both tokens in a secure DB and refreshing them proactively before API calls.

These fixes ensured seamless logins and taught me to test OAuth flows thoroughly across environments and handle edge cases like token management upfront.

**How do you stay updated on best practices for server-side development in Python?**

**Answer:** I stay current by combining hands-on learning and community engagement:

- **Reading:** I follow blogs like Real Python, PyCon talks on YouTube, and the Python Weekly newsletter for updates on libraries and trends.
- **Documentation & Repos:** I dive into official docs (e.g., Flask, FastAPI) and GitHub issues for tools I use to catch new features or best practices.
- **Community:** I participate in forums like Stack Overflow and Reddit’s r/Python, and attend local Python meetups when possible.
- **Practice:** I build side projects (e.g., a REST API with caching) to test new ideas, like asyncio or PEP 8 updates.

Recently, I explored FastAPI’s async capabilities after reading its docs, which improved my approach to high-concurrency endpoints. This mix keeps me sharp and adaptable.