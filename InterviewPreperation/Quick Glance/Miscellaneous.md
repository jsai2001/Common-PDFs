### Condensed Server-Side Development Interview Questions

#### Authentication

**Conceptual Questions**

1. **Authentication vs. authorization?**  
   Authentication verifies user identity (e.g., login). Authorization defines permissions (e.g., access endpoints). Auth: "Who are you?" Authz: "What can you do?"

2. **Session-based vs. token-based authentication?**  
   Session-based: Server stores session ID, client uses cookie. Token-based (e.g., JWT): Client holds self-contained token, server verifies statelessly. Session for small apps, tokens for APIs.

3. **Authentication security risks and mitigations?**  
   Risks: Brute force, credential stuffing, session hijacking, weak passwords. Mitigate: Rate limit logins, use MFA, HTTPS, secure cookies, hash passwords (bcrypt).

4. **MFA and Python implementation?**  
   MFA: Multiple verification factors (e.g., password + OTP). Implement: Use Flask-Login for sessions, pyotp for TOTP, send OTP via email/SMS, verify before access.

**Practical Questions (Python-focused)**

5. **Basic username/password auth in Flask/Django?**  
   Flask: Use SQLAlchemy for User model, bcrypt for password hashing, Flask-Login for sessions. Login route verifies credentials, creates session. Django: Use built-in User model, `authenticate()`.

6. **Hash password with bcrypt/argon2?**  
   ```python
   import bcrypt
   def hash_password(pwd): return bcrypt.hashpw(pwd.encode(), bcrypt.gensalt())
   def verify_password(pwd, hashed): return bcrypt.checkpw(pwd.encode(), hashed)
   ```

7. **Store/verify credentials securely?**  
   Hash passwords (bcrypt) in DB (VARCHAR). Verify with library’s check function. Use `.env` for DB credentials.

8. **Authentication middleware for APIs?**  
   ```python
   from flask import request, jsonify
   import jwt
   def auth_middleware(f):
       def wrapper(*args, **kwargs):
           token = request.headers.get('Authorization', '').replace('Bearer ', '')
           try: jwt.decode(token, 'secret', algorithms=['HS256'])
           except: return jsonify({'error': 'Unauthorized'}), 401
           return f(*args, **kwargs)
       return wrapper
   ```

9. **JWT generation/validation in Python?**  
   ```python
   import jwt
   from datetime import datetime, timedelta
   def generate_jwt(user_id): return jwt.encode({'user_id': user_id, 'exp': datetime.utcnow() + timedelta(minutes=30)}, 'secret')
   def validate_jwt(token): return jwt.decode(token, 'secret', algorithms=['HS256'])
   ```

**Scenario-Based Questions**

10. **Compromised session token?**  
    Detect: Monitor odd activity (e.g., IP changes). Handle: Blacklist token in Redis, force logout via session versioning, notify user, require MFA.

11. **Secure "remember me" feature?**  
    Generate long-lived token, store hash in DB, send as HttpOnly/Secure cookie. Verify on requests, allow revocation.

#### OAuth

**Conceptual Questions**

12. **What is OAuth vs. traditional auth?**  
    OAuth: Delegates auth to providers (e.g., Google) using tokens, avoids sharing credentials. Traditional: Server checks username/password directly.

13. **OAuth 2.0 roles?**  
    Client: App requesting access. Resource owner: User. Authorization server: Issues tokens (e.g., Google). Resource server: Hosts data (e.g., API).

14. **OAuth 2.0 grant types and uses?**  
    Auth Code: Secure server apps. Implicit: SPAs/mobile. Client Credentials: Machine-to-machine. Password: Trusted apps (rare).

15. **Access vs. refresh token?**  
    Access token: Short-lived, for API access. Refresh token: Long-lived, gets new access token without re-auth.

**Practical Questions (Python-focused)**

16. **Google OAuth in Flask?**  
    Use authlib: Register app in Google Console, set login/callback routes.
    ```python
    from authlib.integrations.flask_client import OAuth
    oauth = OAuth(app)
    google = oauth.register('google', client_id='ID', client_secret='SECRET', ...)
    @app.route('/login') def login(): return google.authorize_redirect(url_for('authorize', _external=True))
    @app.route('/authorize') def authorize(): token = google.authorize_access_token(); return token['userinfo']
    ```

17. **Exchange auth code for token?**  
    ```python
    import requests
    def exchange_code(code, client_id, secret, uri):
        resp = requests.post('https://oauth2.googleapis.com/token', data={'code': code, 'client_id': client_id, 'client_secret': secret, 'redirect_uri': uri, 'grant_type': 'authorization_code'})
        return resp.json()
    ```

18. **Store OAuth client secrets?**  
    Use `.env` with python-dotenv:
    ```python
    from dotenv import load_dotenv
    import os
    load_dotenv()
    CLIENT_SECRET = os.getenv('OAUTH_CLIENT_SECRET')
    ```
    In prod, use AWS Secrets Manager.

19. **Basic OAuth flow with authlib?**  
    ```python
    google = oauth.register('google', ...)
    @app.route('/login') def login(): return google.authorize_redirect(url_for('callback', _external=True))
    @app.route('/callback') def callback(): token = google.authorize_access_token(); session['token'] = token; return token['userinfo']['name']
    ```

**Scenario-Based Questions**

20. **Handle OAuth token expiration/refresh?**  
    Store access/refresh tokens in DB. Check expiry before API calls, refresh if needed:
    ```python
    def refresh_token(refresh, client_id, secret):
        resp = requests.post('https://provider.com/token', data={'grant_type': 'refresh_token', 'refresh_token': refresh, 'client_id': client_id, 'client_secret': secret})
        return resp.json()['access_token']
    ```

21. **Debug OAuth login failure?**  
    Check logs, verify client ID/secret/URI, test flow manually, inspect token response, enable debug logging for requests.

#### Caching

**Conceptual Questions**

22. **What is caching and its importance?**  
    Caching stores data for quick access, reducing latency, DB load, and improving scalability in servers.

23. **Client-side vs. server-side caching?**  
    Client-side: Browser cache (e.g., Cache-Control), fewer server hits. Server-side: Redis/memory, faster backend for all clients.

24. **Cache invalidation and eviction?**  
    Invalidation: Remove stale data (TTL, event-based). Eviction: Clear cache when full (LRU, LFU, FIFO).

25. **Redis vs. file-based cache?**  
    Redis: Fast, scalable, RAM-limited, non-persistent. File-based: Persistent, cheap, slow, hard to scale.

**Practical Questions (Python-focused)**

26. **In-memory cache with TTL?**  
    ```python
    from time import time
    class SimpleCache:
        def __init__(self): self.cache = {}
        def set(self, key, value, ttl): self.cache[key] = (value, time() + ttl)
        def get(self, key): if key in self.cache and time() < self.cache[key][1]: return self.cache[key][0]; del self.cache[key]; return None
    ```

27. **Cache API responses with Redis?**  
    ```python
    import redis, requests, json
    redis_client = redis.StrictRedis(host='localhost', port=6379)
    def get_cached(url, ttl=300):
        key = f"api:{url}"
        cached = redis_client.get(key)
        if cached: return json.loads(cached)
        data = requests.get(url).json()
        redis_client.setex(key, ttl, json.dumps(data))
        return data
    ```

28. **Integrate caching in Flask/FastAPI?**  
    Use `flask-caching`:
    ```python
    from flask_caching import Cache
    cache = Cache(app, config={'CACHE_TYPE': 'redis'})
    @app.route('/data')
    @cache.cached(timeout=60)
    def get_data(): return {"data": "result"}
    ```

29. **Cache decorator?**  
    ```python
    from functools import wraps
    from time import time
    def cache_with_ttl(ttl):
        def decorator(func):
            cache = {}
            @wraps(func)
            def wrapper(*args):
                key = str(args)
                if key in cache and time() < cache[key][1]: return cache[key][0]
                result = func(*args)
                cache[key] = (result, time() + ttl)
                return result
            return wrapper
        return decorator
    ```

**Scenario-Based Questions**

30. **Reduce latency from DB queries?**  
    Cache results in Redis:
    ```python
    def get_user(user_id):
        key = f"user:{user_id}"
        cached = redis_client.get(key)
        if cached: return json.loads(cached)
        user = db.get_user(user_id)
        redis_client.setex(key, 600, json.dumps(user))
        return user
    ```

31. **Handle cache consistency?**  
    Invalidate on updates:
    ```python
    def update_user(user_id, name):
        db.update_user(user_id, name)
        redis_client.delete(f"user:{user_id}")
    ```
    Use short TTLs for frequent updates.

#### Asynchronous Programming Using Task Queues

**Conceptual Questions**

32. **Async vs. sync programming?**  
    Async: Non-blocking, ideal for I/O (e.g., API calls). Sync: Sequential, blocks until done. Async improves efficiency.

33. **What are task queues?**  
    Queues offload tasks to workers, improving responsiveness and scalability (e.g., Celery for background jobs).

34. **asyncio vs. Celery/RQ?**  
    asyncio: Single-process, I/O-bound tasks. Celery/RQ: Multi-process, CPU-bound/long-running tasks, durable.

35. **Benefits of task queues?**  
    Faster responses, scalability, reliability (retries), e.g., Celery for image processing.

**Practical Questions (Python-focused)**

36. **Async function for multiple APIs?**  
    ```python
    import asyncio, aiohttp
    async def fetch_url(session, url):
        async with session.get(url) as resp: return await resp.json()
    async def fetch_all(urls):
        async with aiohttp.ClientSession() as session:
            return await asyncio.gather(*[fetch_url(session, url) for url in urls])
    ```

37. **Set up Celery with Redis?**  
    ```python
    from celery import Celery
    app = Celery('myapp', broker='redis://localhost:6379/0')
    @app.task
    def add(x, y): return x + y
    ```
    Run: `celery -A myapp worker`.

38. **Send emails with Celery?**  
    ```python
    from celery import Celery
    import smtplib
    app = Celery('email', broker='redis://localhost:6379/0')
    @app.task
    def send_email(to, subject, body):
        with smtplib.SMTP('smtp.example.com') as s:
            s.login('user', 'pass')
            s.sendmail('from@example.com', to, f'Subject: {subject}\n\n{body}')
    ```

39. **RQ task for file upload?**  
    ```python
    from rq import Queue
    from redis import Redis
    q = Queue(connection=Redis())
    def process_upload(file_path):
        with open(file_path, 'r') as f: return f.read()
    q.enqueue(process_upload, 'upload.txt')
    ```

**Scenario-Based Questions**

40. **Process large CSV without blocking?**  
    ```python
    from celery import Celery
    app = Celery('myapp', broker='redis://localhost:6379/0')
    @app.task
    def process_csv(path):
        with open(path, 'r') as f: [print(row) for row in csv.DictReader(f)]
    @app.route('/upload', methods=['POST'])
    def upload():
        file.save(path := '/tmp/file.csv')
        return {'task_id': process_csv.delay(path).id}
    ```

41. **Debug failing task?**  
    Check worker logs (`--loglevel=DEBUG`), add task logging, use retries (`max_retries=3`), store results for tracking.

42. **Scale task queue for thousands/min?**  
    Add workers (`celery -c 10`), use Redis/RabbitMQ cluster, split queues (e.g., priority), monitor with Flower.

#### General Server-Side Development Questions

**Design and Architecture**

43. **Design REST API with auth/caching/async?**  
    Flask/FastAPI: JWT middleware for auth, Redis for caching, Celery for tasks. Routes: `/auth`, `/users`, `/tasks`. Modular with blueprints.

44. **Sync vs. async for endpoint?**  
    Sync: Fast, CPU-bound (e.g., `/status`). Async: I/O-bound, high concurrency (e.g., `/fetch-multi`). Consider latency, scalability, complexity.

45. **Ensure scalability?**  
    Horizontal scaling (Gunicorn, ELB), stateless JWT, DB pooling, Redis caching, Celery workers, monitor with Prometheus.

**Security**

46. **Protect sensitive data?**  
    Store keys in `.env`/secrets manager, hash passwords (bcrypt), encrypt PII, use HTTPS, secure cookies.

47. **Prevent SQL injection/CSRF?**  
    Use ORM (SQLAlchemy), parameterized queries. Flask-WTF/Django CSRF tokens, sanitize inputs, HTTPS.

**Performance**

48. **Profile server app?**  
    Use cProfile, Flask-DebugToolbar, log timings, monitor CPU/memory with psutil, track with New Relic.

49. **Optimize slow DB endpoint?**  
    Cache in Redis, index columns, fetch only needed fields, offload writes to Celery, test with locust.

**Coding Challenges**

50. **Flask JWT login route?**  
    ```python
    from flask import Flask, request, jsonify
    import jwt, bcrypt
    app = Flask(__name__)
    users = {'user': bcrypt.hashpw(b'pass', bcrypt.gensalt())}
    @app.route('/login', methods=['POST'])
    def login():
        data = request.get_json()
        if bcrypt.checkpw(data['password'].encode(), users.get(data['username'], b'')):
            return jsonify({'token': jwt.encode({'user': data['username']}, 'secret')})
        return jsonify({'error': 'Invalid'}), 401
    ```

51. **Password reset with email token?**  
    ```python
    from flask import Flask, request, jsonify
    import jwt, smtplib
    app = Flask(__name__)
    @app.route('/reset-password', methods=['POST'])
    def reset():
        email = request.get_json()['email']
        token = jwt.encode({'email': email}, 'secret')
        with smtplib.SMTP('smtp.example.com') as s:
            s.login('user', 'pass')
            s.sendmail('from@example.com', email, f'Subject: Reset\n\nToken: {token}')
        return jsonify({'message': 'Sent'})
    @app.route('/reset', methods=['POST'])
    def reset_confirm():
        data = request.get_json()
        try:
            email = jwt.decode(data['token'], 'secret')['email']
            return jsonify({'message': f'Password reset for {email}'})
        except: return jsonify({'error': 'Invalid'}), 400
    ```

52. **Google OAuth profile fetch?**  
    ```python
    from authlib.integrations.requests_client import OAuth2Session
    client = OAuth2Session('CLIENT_ID', 'SECRET', redirect_uri='http://localhost/callback', scope='profile email')
    auth_url, _ = client.create_authorization_url('https://accounts.google.com/o/oauth2/auth')
    print(f"Visit: {auth_url}")
    # After user consent, get code from callback
    code = input("Enter code: ")
    token = client.fetch_token('https://oauth2.googleapis.com/token', code=code)
    profile = client.get('https://www.googleapis.com/oauth2/v3/userinfo').json()
    print(profile)
    ```

53. **LRU cache class?**  
    ```python
    from collections import OrderedDict
    class LRUCache:
        def __init__(self, capacity): self.cache = OrderedDict(); self.capacity = capacity
        def get(self, key):
            if key not in self.cache: return None
            self.cache.move_to_end(key); return self.cache[key]
        def set(self, key, value):
            self.cache[key] = value
            self.cache.move_to_end(key)
            if len(self.cache) > self.capacity: self.cache.popitem(last=False)
    ```

54. **Flask endpoint with Redis cache?**  
    ```python
    from flask import Flask
    import redis
    app = Flask(__name__)
    redis_client = redis.StrictRedis(host='localhost')
    @app.route('/data')
    def data():
        key = 'data'
        cached = redis_client.get(key)
        if cached: return cached.decode()
        result = db.query().execute()  # Simulate DB
        redis_client.setex(key, 300, result)
        return result
    ```

55. **Celery task for image processing?**  
    ```python
    from celery import Celery
    app = Celery('image', broker='redis://localhost:6379/0')
    @app.task
    def process_image(file_path):
        # Simulate processing
        db.save_result(f"Processed {file_path}")
        return "Done"
    ```

56. **Async file downloads?**  
    ```python
    import asyncio, aiohttp
    async def download_file(session, url):
        async with session.get(url) as resp:
            print(f"Downloading {url}: {resp.status}")
            return await resp.read()
    async def download_all(urls):
        async with aiohttp.ClientSession() as session:
            return await asyncio.gather(*[download_file(session, url) for url in urls])
    ```

**Behavioral and Thought-Provoking Questions**

57. **Auth/caching performance improvement?**  
    Cached product listings in Redis (1-hour TTL), cut response time from 500ms to 50ms, reduced DB load by 70%. Used JWT for stateless auth, avoiding session lookups.

58. **Task queue vs. sync decision?**  
    Queue for slow tasks (e.g., PDF generation) to keep API responsive; sync for quick queries (e.g., search). Consider duration, I/O vs. CPU, user needs.

59. **OAuth challenges?**  
    Fixed redirect URI mismatch with env vars, added refresh token logic for expired tokens, tested flows across environments.

60. **Stay updated on Python best practices?**  
    Read Real Python, PyCon talks, docs (Flask/FastAPI), engage on Stack Overflow/r/Python, build side projects to test new ideas (e.g., FastAPI async).