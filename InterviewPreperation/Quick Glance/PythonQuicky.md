Here’s a revised version of the Python interview preparation questions with concise, straightforward, and impactful answers. All 125 questions are kept intact, and code blocks are preserved for clarity. The focus is on delivering essential information efficiently to reduce overwhelm while retaining depth.

**1. Python Basics**

**What is Python, and what are its key features?**  
Python is a high-level, interpreted language known for simplicity.  
**Features:** Readable syntax, dynamic typing, extensive libraries, cross-platform, multi-paradigm (procedural, OOP, functional), garbage-collected.

**Explain the difference between Python 2 and Python 3.**  
- Python 2 (EOL 2020) vs. Python 3:  
   - `print` is a function in 3, not a statement.  
   - Python 3 has float division (`5/2 = 2.5`), while Python 2 doesn’t.  
   - Python 3 supports Unicode natively.  
   - `range()` in Python 3 replaces `xrange()` from Python 2.  
   - Python 3 includes modern features like f-strings.

**What are PEP 8 guidelines, and why are they important?**  
PEP 8 is Python’s style guide:  
- 4-space indentation, 79-character lines, snake_case for functions.  
It ensures readable, consistent code for collaboration.

**How is Python an interpreted language?**  
Python runs code line-by-line via an interpreter, converting it to bytecode executed by the Python Virtual Machine (PVM), not compiled to machine code.

**What are the differences between lists, tuples, and sets?**  
- **Lists:** `[1, 2]` - mutable, ordered, duplicates allowed.  
- **Tuples:** `(1, 2)` - immutable, ordered, duplicates allowed.  
- **Sets:** `{1, 2}` - mutable, unordered, no duplicates.

**What is the difference between `==` and `is`?**  
- `==`: Checks value equality.  
- `is`: Checks identity (same memory location).  
```python
a = [1, 2]; b = [1, 2]
print(a == b)  # True
print(a is b)  # False
```

**How does Python handle memory management?**  
Python uses reference counting (frees memory when count hits 0) and a garbage collector for cyclic references via the `gc` module.

**What are mutable and immutable objects in Python? Give examples.**  
- **Mutable:** Can change (e.g., lists `[1, 2]`, dicts `{"a": 1}`, sets `{1, 2}`).  
- **Immutable:** Can’t change (e.g., int `5`, str `"hi"`, tuple `(1, 2)`).

**What is the purpose of the `pass` statement?**  
A no-op placeholder for empty code blocks.  
```python
def func(): pass
```

**How do you swap two variables in Python without a temp variable?**  
Use tuple unpacking:  
```python
a, b = 5, 10; a, b = b, a  # a=10, b=5
```

**What are Python’s built-in data types?**  
- **Numeric:** `int`, `float`, `complex`  
- **Sequence:** `str`, `list`, `tuple`  
- **Mapping:** `dict`  
- **Set:** `set`, `frozenset`  
- **Boolean:** `bool`  
- **None**

**What is the difference between `range()` and `xrange()`?**  
- Python 2: `range()` returns a list, `xrange()` an iterator.  
- Python 3: Only `range()` exists, acting like `xrange()` (iterator).

**What is a Python module, and how do you import one?**  
A file with reusable code. Import with:  
```python
import math; print(math.sqrt(16))  # 4.0
```

**Explain the `if __name__ == "__main__":` idiom.**  
Runs code only if the script is executed directly, not imported.  
```python
if __name__ == "__main__":
      print("Direct run")
```

**What are `*args` and `**kwargs`, and how are they used?**  
- `*args`: Variable positional arguments (tuple).  
- `**kwargs`: Variable keyword arguments (dict).  
```python
def func(*args, **kwargs):
      print(args, kwargs)
func(1, 2, a=3)  # (1, 2) {'a': 3}
```

** 2. Strings and Text Processing**

**How do you reverse a string in Python?**  
Use slicing:  
```python
s = "hello"; print(s[::-1])  # "olleh"
```

**What is string slicing, and how does it work?**  
Extracts substrings with `[start:stop:step]`.  
```python
s = "Python"; print(s[1:4])  # "yth"
```

**How do you check if a string is a palindrome?**  
Compare with its reverse:  
```python
def is_palindrome(s): return s.lower() == s[::-1]
print(is_palindrome("radar"))  # True
```

**Explain `str.join()` vs. `str.format()`.**  
- `join()`: Concatenates iterable with separator.  
- `format()`: Inserts values into placeholders.  
```python
print(" ".join(["a", "b"]))  # "a b"
print("{}-{}".format(1, 2))  # "1-2"
```

**How do you convert a string to lowercase or uppercase?**  
```python
s = "Hello"; print(s.lower())  # "hello"; print(s.upper())  # "HELLO"
```

**What is the difference between `find()` and `index()`?**  
- `find()`: Returns index or `-1` if not found.  
- `index()`: Returns index or raises `ValueError`.  
```python
s = "hello"; print(s.find("x"))  # -1; print(s.index("l"))  # 2
```

**How do you remove leading/trailing whitespace?**  
```python
s = "  hi  "; print(s.strip())  # "hi"
```

**How do you count substring occurrences?**  
```python
s = "hello hello"; print(s.count("hello"))  # 2
```

**What are f-strings, and how do they compare?**  
Concise formatting with `f"...".` Faster, cleaner than `%` or `.format()`.  
```python
name = "Alice"; print(f"Hi, {name}")  # "Hi, Alice"
```

**How do you split a string into a list?**  
```python
s = "hello world"; print(s.split())  # ["hello", "world"]
```

** 3. Lists, Dictionaries, and Sets**

**How do you remove duplicates from a list?**  
```python
lst = [1, 2, 2]; print(list(set(lst)))  # [1, 2]
```

**What is list comprehension? Example.**  
Creates lists concisely:  
```python
squares = [x**2 for x in range(5)]; print(squares)  # [0, 1, 4, 9, 16]
```

**How do you sort a list of dicts by a key?**  
```python
lst = [{"age": 25}, {"age": 20}]; print(sorted(lst, key=lambda x: x["age"]))  # [{"age": 20}, {"age": 25}]
```

**Difference between `append()` and `extend()`?**  
- `append()`: Adds one item.  
- `extend()`: Adds iterable elements.  
```python
lst = [1]; lst.append([2, 3]); print(lst)  # [1, [2, 3]]
lst = [1]; lst.extend([2, 3]); print(lst)  # [1, 2, 3]
```

**How do you merge two dictionaries?**  
```python
d1 = {"a": 1}; d2 = {"b": 2}; print(d1 | d2)  # {"a": 1, "b": 2} (Python 3.9+)
```

**Difference between `pop()` and `remove()`?**  
- `pop()`: Removes by index, returns value.  
- `remove()`: Removes by value.  
```python
lst = [1, 2]; print(lst.pop(0))  # 1; lst.remove(2); print(lst)  # []
```

**How do you check if a key exists in a dict?**  
```python
d = {"a": 1}; print("a" in d)  # True; print(d.get("b", 0))  # 0
```

**What are dict comprehensions? Example.**  
```python
d = {x: x**2 for x in range(3)}; print(d)  # {0: 0, 1: 1, 2: 4}
```

**How do set operations work?**  
```python
s1 = {1, 2}; s2 = {2, 3}
print(s1 | s2)  # {1, 2, 3} (union)
print(s1 & s2)  # {2} (intersection)
print(s1 - s2)  # {1} (difference)
```

**How do you flatten a nested list?**  
```python
nested = [[1, 2], [3, 4]]; flat = [x for sub in nested for x in sub]; print(flat)  # [1, 2, 3, 4]
```

** 4. Functions and Functional Programming**

**What is a lambda function? When to use?**  
Anonymous function for short tasks (e.g., sorting).  
```python
f = lambda x: x+1; print(f(2))  # 3
```

**Difference between `map()`, `filter()`, `reduce()`?**  
- `map()`: Transforms items.  
- `filter()`: Selects items.  
- `reduce()`: Aggregates items.  
```python
print(list(map(lambda x: x*2, [1, 2])))  # [2, 4]
print(list(filter(lambda x: x > 1, [1, 2])))  # [2]
from functools import reduce; print(reduce(lambda x, y: x+y, [1, 2]))  # 3
```

**How do you define default arguments?**  
```python
def func(x=1): print(x); func()  # 1
```

**What is recursion? Example.**  
Function calls itself with a base case.  
```python
def fact(n): return 1 if n == 0 else n * fact(n-1)
print(fact(5))  # 120
```

**How does Python handle variable scope (LEGB)?**  
- Local, Enclosing, Global, Built-in.  
```python
x = "global"; def f(): x = "local"; print(x); f()  # "local"
```

**What are decorators? How to create?**  
Modify functions with `@decorator`.  
```python
def deco(func):
      def wrapper(): print("Before"); func()
      return wrapper
@deco
def hi(): print("Hi")
hi()  # "Before", "Hi"
```

**How do you pass a function as an argument?**  
```python
def call(f): f(); call(lambda: print("Hi"))  # "Hi"
```

**1. Difference Between Shallow and Deep Copy**
- **Shallow Copy**: Copies top-level, references nested objects.
- **Deep Copy**: Copies all levels.

```python
import copy
lst = [[1]]
s = copy.copy(lst)
d = copy.deepcopy(lst)
s[0][0] = 2
print(lst)  # [[2]]
print(d)  # [[1]]
```

**2. Handling Exceptions in a Function**
```python
def div(a, b):
    try:
        return a / b
    except ZeroDivisionError:
        return "Error"

print(div(1, 0))  # "Error"
```

**3. Purpose of the `global` Keyword**
- Modifies global variables.

```python
x = 1
def f():
    global x
    x = 2

f()
print(x)  # 2
```

**4. Object-Oriented Programming (OOP)**
- **Key Principles**: Encapsulation, Inheritance, Polymorphism, Abstraction.
- **Class vs. Object**: Class is a blueprint; Object is an instance.

```python
class C:
    pass

obj = C()
```

- **Inheritance**:
```python
class A:
    def x(self):
        print("A")

class B(A):
    pass

B().x()  # "A"
```

- **Method Overriding**:
```python
class A:
    def x(self):
        print("A")

class B(A):
    def x(self):
        print("B")

B().x()  # "B"
```

- **`self` vs. `cls`**:
  - `self`: Refers to the instance.
  - `cls`: Refers to the class.

```python
class C:
    @classmethod
    def f(cls):
        print(cls)

C.f()  # "<class '__main__.C'>"
```

- **Static vs. Class Methods**:
```python
class C:
    @staticmethod
    def s():
        print("Static")

    @classmethod
    def c(cls):
        print("Class")

C.s()
C.c()
```

- **Polymorphism Example**:
```python
class Dog:
    def sound(self):
        return "Woof"

class Cat:
    def sound(self):
        return "Meow"

for a in [Dog(), Cat()]:
    print(a.sound())  # "Woof", "Meow"
```

- **Encapsulation**:
```python
class C:
    def __init__(self):
        self.__x = 1

    def get(self):
        return self.__x

c = C()
print(c.get())  # 1
```

- **Purpose of `__init__`**:
```python
class C:
    def __init__(self, x):
        self.x = x

c = C(5)
print(c.x)  # 5
```

- **Abstract Base Classes (ABCs)**:
```python
from abc import ABC, abstractmethod

class A(ABC):
    @abstractmethod
    def m(self):
        pass

class B(A):
    def m(self):
        print("B")

B().m()  # "B"
```

**5. Data Structures and Algorithms**
- **Stack Implementation**:
```python
class Stack:
    def __init__(self):
        self.s = []

    def push(self, x):
        self.s.append(x)

    def pop(self):
        return self.s.pop() if self.s else None

s = Stack()
s.push(1)
print(s.pop())  # 1
```

- **Queue Implementation**:
```python
from collections import deque

class Queue:
    def __init__(self):
        self.q = deque()

    def enqueue(self, x):
        self.q.append(x)

    def dequeue(self):
        return self.q.popleft() if self.q else None

q = Queue()
q.enqueue(1)
print(q.dequeue())  # 1
```

- **Binary Search**:
```python
def binary_search(arr, x):
    l, r = 0, len(arr) - 1
    while l <= r:
        m = (l + r) // 2
        if arr[m] == x:
            return m
        elif arr[m] < x:
            l = m + 1
        else:
            r = m - 1
    return -1

print(binary_search([1, 3, 5], 3))  # 1
```

- **Reverse a Linked List**:
```python
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

def reverse(head):
    prev, curr = None, head
    while curr:
        next_n = curr.next
        curr.next = prev
        prev = curr
        curr = next_n
    return prev

head = Node(1)
head.next = Node(2)
head = reverse(head)
print(head.data)  # 2
```

- **Time Complexity of List Operations**:
  - `append`: O(1)
  - `pop()`: O(1)
  - `pop(i)`/`insert`: O(n)
  - `remove`: O(n)
  - `in`: O(n)

- **Find Max Without `max()`**:
```python
def find_max(lst):
    m = lst[0]
    for x in lst:
        m = x if x > m else m
    return m

print(find_max([1, 5, 3]))  # 5
```

- **Hash Table Implementation**:
```python
class HashTable:
    def __init__(self):
        self.t = [[] for _ in range(10)]

    def put(self, k, v):
        i = hash(k) % 10
        self.t[i].append((k, v))

    def get(self, k):
        i = hash(k) % 10
        return next((v for x, v in self.t[i] if x == k), None)

ht = HashTable()
ht.put("a", 1)
print(ht.get("a"))  # 1
```

- **Heap with `heapq`**:
```python
import heapq

h = [3, 1, 4]
heapq.heapify(h)
print(heapq.heappop(h))  # 1
```

- **Detect Cycle in Linked List**:
```python
def has_cycle(head):
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
        if slow == fast:
            return True
    return False
```

- **BFS vs. DFS**:
  - **BFS**: Queue, level-by-level, O(V+E).
  - **DFS**: Stack/recursion, depth-first, O(V+E).

**6. File Handling**
- **Read File Line by Line**:
```python
with open("file.txt") as f:
    [print(line.strip()) for line in f]
```

- **Difference Between `read()`, `readline()`, `readlines()`**:
  - `read()`: Reads all content.
  - `readline()`: Reads one line.
  - `readlines()`: Reads all lines into a list.

```python
with open("file.txt") as f:
    print(f.read())
```

- **Write to a File**:
```python
with open("file.txt", "w") as f:
    f.write("Hi")
```

- **Append to a File**:
```python
with open("file.txt", "a") as f:
    f.write("More")
```

- **Handle File Exceptions**:
```python
try:
    open("file.txt")
except FileNotFoundError:
    print("Not found")
```

- **Read/Write CSV Files**:
```python
import csv

with open("data.csv", "w", newline="") as f:
    csv.writer(f).writerow(["a", 1])
```

- **Work with JSON Files**:
```python
import json

with open("data.json", "w") as f:
    json.dump({"a": 1}, f)
```

- **Binary vs. Text File Modes**:
  - Text: `"r"`/`"w"` (strings).
  - Binary: `"rb"`/`"wb"` (bytes).

```python
with open("file.bin", "wb") as f:
    f.write(b"Hi")
```

- **Delete a File**:
```python
import os
os.remove("file.txt")
```

**7. Modules and Libraries**
- **`import` vs. `from module import *`**:
  - `import`: Namespace-safe.
  - `from *`: Direct access, risks conflicts.

```python
import math
print(math.pi)
```

- **Install Library with `pip`**:
```bash
pip install requests
```

- **Purpose of `sys` Module**:
  - System-level access (e.g., `sys.argv`, `sys.exit()`).

```python
import sys
print(sys.argv)
```

- **Generate Random Numbers**:
```python
import random
print(random.randint(1, 10))  # 1-10
```

- **`datetime` Module Usage**:
```python
from datetime import datetime
print(datetime.now())
```

- **`os` Module Usage**:
```python
import os
print(os.getcwd())
```

- **NumPy vs. Lists**:
  - NumPy: Fast, fixed-type arrays.
  - Lists: Flexible, slower.

```python
import numpy as np
print(np.array([1, 2]) + 1)  # [2, 3]
```

- **Create Virtual Environment**:
```bash
python -m venv env
source env/bin/activate
```

- **Purpose of `collections`**:
  - Specialized containers (e.g., `Counter`).

```python
from collections import Counter
print(Counter("aba"))  # {'a': 2, 'b': 1}
```

- **`re` Module for Regex**:
```python
import re
print(re.findall(r"\d+", "12ab34"))  # ['12', '34']
```

**8. Exception Handling**
- **What is Exception Handling?**
  - Manages runtime errors to prevent crashes, ensuring robustness.

- **`try`, `except`, `else`, `finally` Usage**:
```python
try:
    x = 1 / 0
except ZeroDivisionError:
    print("Error")
else:
    print("OK")
finally:
    print("Done")
```

- **Raise Custom Exception**:
```python
class MyError(Exception):
    pass

raise MyError("Oops")
```

- **Error vs. Exception**:
  - **Error**: Any issue (e.g., syntax).
  - **Exception**: Catchable runtime error.

- **Catch Multiple Exceptions**:
```python
try:
    int("a")
except (ValueError, TypeError):
    print("Error")
```

- **Log Exceptions with `logging`**:
```python
import logging
logging.basicConfig(level=logging.ERROR)

try:
    1 / 0
except:
    logging.error("Fail", exc_info=True)
```

- **Purpose of `assert`**:
  - Raises `AssertionError` if condition fails.

```python
assert 1 == 2, "Wrong"
```

- **Handle `KeyError`**:
```python
d = {"a": 1}
print(d.get("b", 0))  # 0
```

- **`sys.exit()` vs. Raising Exception**:
  - `sys.exit()`: Stops program.
  - Exception: Catchable error.

```python
import sys
sys.exit(1)
```

**9. Advanced Python**
- **What are Generators?**
  - Lazy iterators with `yield`, unlike functions returning all at once.

```python
def gen():
    yield 1
    yield 2

print(next(gen()))  # 1
```

- **Create Generator with `yield`**:
```python
def gen(n):
    for i in range(n):
        yield i

print(list(gen(3)))  # [0, 1, 2]
```

- **What is a Context Manager?**
  - Manages resources with `__enter__`/`__exit__`.

```python
class CM:
    def __enter__(self):
        return self

    def __exit__(self, *args):
        pass

with CM():
    print("Hi")
```

- **What are Metaclasses?**
  - Classes that create classes (customize class creation).

```python
class Meta(type):
    pass

class C(metaclass=Meta):
    pass
```

- **Multithreading and GIL**:
  - GIL limits CPU-bound parallelism in CPython; good for I/O.

```python
import threading
t = threading.Thread(target=lambda: print("Hi"))
t.start()
```

- **Multiprocessing vs. Multithreading**:
  - **Multithreading**: Shared memory, GIL-limited.
  - **Multiprocessing**: Separate memory, no GIL.

```python
from multiprocessing import Process
Process(target=lambda: print("Hi")).start()
```

- **`asyncio` for Async Programming**:
```python
import asyncio

async def f():
    await asyncio.sleep(1)
    print("Hi")

asyncio.run(f())
```

- **What are Closures?**
  - Nested functions retaining outer scope.

```python
def outer(x):
    def inner():
        return x
    return inner

f = outer(5)
print(f())  # 5
```

**Profiling a Python Program**
```python
import cProfile
cProfile.run("sum(range(1000))")
```

**`__str__` vs. `__repr__`**
- `__str__`: User-friendly representation.
- `__repr__`: Developer/debugging representation.

```python
class C:
    def __str__(self): return "Hi"
    def __repr__(self): return "C()"

c = C()
print(str(c))  # "Hi"
print(repr(c))  # "C()"
```

**Coding Problems**

**Factorial of a Number**
```python
def fact(n): 
    return 1 if n <= 0 else n * fact(n-1)

print(fact(5))  # 120
```

**Check if a Number is Prime**
```python
def is_prime(n):
    if n <= 1: return False
    for i in range(2, int(n**0.5)+1):
        if n % i == 0: return False
    return True

print(is_prime(7))  # True
```

**Fibonacci Sequence up to `n` Terms**
```python
def fib(n):
    f = [0, 1]
    for i in range(2, n): 
        f.append(f[-1] + f[-2])
    return f[:n]

print(fib(5))  # [0, 1, 1, 2, 3]
```

**Second Largest in a List**
```python
def second_max(lst):
    if len(lst) < 2: return None
    m1, m2 = max(lst[0], lst[1]), min(lst[0], lst[1])
    for x in lst[2:]:
        m1, m2 = (x, m1) if x > m1 else (m1, x if x > m2 else m2)
    return m2

print(second_max([1, 5, 3]))  # 3
```

**Merge Two Sorted Lists**
```python
def merge(l1, l2):
    r, i, j = [], 0, 0
    while i < len(l1) and j < len(l2):
        if l1[i] < l2[j]: 
            r.append(l1[i])
            i += 1
        else: 
            r.append(l2[j])
            j += 1
    return r + l1[i:] + l2[j:]

print(merge([1, 3], [2, 4]))  # [1, 2, 3, 4]
```

**Remove Vowels from a String**
```python
def no_vowels(s): 
    return "".join(c for c in s if c.lower() not in "aeiou")

print(no_vowels("hello"))  # "hll"
```

**Binary Search Tree**
```python
class Node:
    def __init__(self, v): 
        self.v = v
        self.left = self.right = None

class BST:
    def insert(self, root, v):
        if not root: return Node(v)
        if v < root.v: 
            root.left = self.insert(root.left, v)
        else: 
            root.right = self.insert(root.right, v)
        return root

bst = BST()
root = bst.insert(None, 5)
```

**Find Anagrams in a List**
```python
def anagrams(lst):
    d = {}
    for s in lst: 
        k = "".join(sorted(s))
        d.setdefault(k, []).append(s)
    return [v for v in d.values() if len(v) > 1]

print(anagrams(["eat", "tea", "tan"]))  # [['eat', 'tea']]
```

**Longest Common Substring**
```python
def lcs(s1, s2):
    dp = [[0]*(len(s2)+1) for _ in range(len(s1)+1)]
    m, end = 0, 0
    for i in range(1, len(s1)+1):
        for j in range(1, len(s2)+1):
            if s1[i-1] == s2[j-1]:
                dp[i][j] = dp[i-1][j-1] + 1
                if dp[i][j] > m: 
                    m = dp[i][j]
                    end = i
    return s1[end-m:end]

print(lcs("ABCD", "ABED"))  # "AB"
```

**Rotate List by `k`**
```python
def rotate(lst, k):
    k %= len(lst)
    return lst[-k:] + lst[:-k]

print(rotate([1, 2, 3], 1))  # [3, 1, 2]
```

**Practical/Scenario-Based Questions**

**Optimize Script for Large Datasets**
- Use generators, numpy, or chunking:
```python
def gen_data(n): 
    yield from range(n)
```

**Debug a Python Program**
- Use `print()`, `pdb`, or IDE breakpoints:
```python
import pdb
pdb.set_trace()
```

**Handle Memory Leaks**
- Use `tracemalloc`, close resources:
```python
import tracemalloc
tracemalloc.start()
```

**Design a REST API with Flask**
```python
from flask import Flask, jsonify
app = Flask(__name__)

@app.route("/hi", methods=["GET"])
def hi(): 
    return jsonify({"msg": "Hello"})

app.run()
```

**Unit Test with `unittest`/`pytest`**
```python
def add(a, b): 
    return a + b

# pytest
def test_add(): 
    assert add(2, 3) == 5
```

**Process Large CSV Without Full Load**
```python
import csv
with open("large.csv") as f: 
    [print(row) for row in csv.reader(f)]
```

**Handle Concurrency in Web App**
- Use `asyncio` or `gunicorn`:
```bash
gunicorn -w 4 app:app
```

**Scrape a Website**
```python
import requests, bs4
soup = bs4.BeautifulSoup(requests.get("http://example.com").text, "html.parser")
print(soup.h1.text)
```

**Simple Caching Mechanism**
```python
from functools import lru_cache

@lru_cache
def f(x): 
    return x * x

print(f(5))  # Cached
```

**Deploy Python App to Production**
- Use Docker, gunicorn, virtualenv:
```dockerfile
FROM python:3.9
COPY . .
RUN pip install -r requirements.txt
CMD ["gunicorn", "app:app"]
```