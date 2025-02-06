# Dynamic Programming

### 1. Fibonacci Sequence

**Problem**: Compute the nth Fibonacci number.

**Top-Down Approach (Memoization)**:
```python
def fibonacci_memo(n, memo={}):
    if n in memo:
        return memo[n]
    if n <= 1:
        return n
    memo[n] = fibonacci_memo(n-1, memo) + fibonacci_memo(n-2, memo)
    return memo[n]

print(fibonacci_memo(10))  # Output: 55
```

**Bottom-Up Approach (Tabulation)**:
```python
def fibonacci_tab(n):
    if n <= 1:
        return n
    fib = [0] * (n + 1)
    fib[1] = 1
    for i in range(2, n + 1):
        fib[i] = fib[i-1] + fib[i-2]
    return fib[n]

print(fibonacci_tab(10))  # Output: 55
```

### 2. Longest Common Subsequence (LCS)

**Problem**: Find the length of the longest subsequence present in both given sequences.

**Bottom-Up Approach (Tabulation)**:
```python
def lcs(X, Y):
    m = len(X)
    n = len(Y)
    L = [[0] * (n + 1) for i in range(m + 1)]

    for i in range(m + 1):
        for j in range(n + 1):
            if i == 0 or j == 0:
                L[i][j] = 0
            elif X[i-1] == Y[j-1]:
                L[i][j] = L[i-1][j-1] + 1
            else:
                L[i][j] = max(L[i-1][j], L[i][j-1])

    return L[m][n]

X = "AGGTAB"
Y = "GXTXAYB"
print(lcs(X, Y))  # Output: 4
```

### 3. Knapsack Problem

**Problem**: Given weights and values of n items, put these items in a knapsack of capacity W to get the maximum total value in the knapsack.

**Bottom-Up Approach (Tabulation)**:
```python
def knapsack(W, wt, val, n):
    K = [[0 for x in range(W + 1)] for x in range(n + 1)]

    for i in range(n + 1):
        for w in range(W + 1):
            if i == 0 or w == 0:
                K[i][w] = 0
            elif wt[i-1] <= w:
                K[i][w] = max(val[i-1] + K[i-1][w-wt[i-1]], K[i-1][w])
            else:
                K[i][w] = K[i-1][w]

    return K[n][W]

val = [60, 100, 120]
wt = [10, 20, 30]
W = 50
n = len(val)
print(knapsack(W, wt, val, n))  # Output: 220
```

### 4. Coin Change Problem

**Problem**: Given a set of coins and a total amount, find the minimum number of coins needed to make the amount.

**Bottom-Up Approach (Tabulation)**:
```python
def coin_change(coins, amount):
    dp = [float('inf')] * (amount + 1)
    dp[0] = 0

    for coin in coins:
        for x in range(coin, amount + 1):
            dp[x] = min(dp[x], dp[x - coin] + 1)

    return dp[amount] if dp[amount] != float('inf') else -1

coins = [1, 2, 5]
amount = 11
print(coin_change(coins, amount))  # Output: 3
```

### 5. Longest Increasing Subsequence (LIS)

**Problem**: Find the length of the longest increasing subsequence in an array.

**Bottom-Up Approach (Tabulation)**:
```python
def lis(arr):
    n = len(arr)
    lis = [1] * n

    for i in range(1, n):
        for j in range(0, i):
            if arr[i] > arr[j] and lis[i] < lis[j] + 1:
                lis[i] = lis[j] + 1

    return max(lis)

arr = [10, 22, 9, 33, 21, 50, 41, 60, 80]
print(lis(arr))  # Output: 6
```

### 6. Edit Distance

**Problem**: Given two strings, find the minimum number of operations required to convert one string into the other.

**Bottom-Up Approach (Tabulation)**:
```python
def edit_distance(str1, str2):
    m = len(str1)
    n = len(str2)
    dp = [[0] * (n + 1) for _ in range(m + 1)]

    for i in range(m + 1):
        for j in range(n + 1):
            if i == 0:
                dp[i][j] = j
            elif j == 0:
                dp[i][j] = i
            elif str1[i-1] == str2[j-1]:
                dp[i][j] = dp[i-1][j-1]
            else:
                dp[i][j] = 1 + min(dp[i][j-1], dp[i-1][j], dp[i-1][j-1])

    return dp[m][n]

str1 = "sunday"
str2 = "saturday"
print(edit_distance(str1, str2))  # Output: 3
```

# Greedy Algorithms

### 1. Activity Selection Problem

**Problem**: Given a set of activities with start and end times, select the maximum number of activities that can be performed without overlapping.

**Solution**:
```python
def activity_selection(activities):
    activities.sort(key=lambda x: x[1])
    selected_activities = [activities[0]]
    last_end_time = activities[0][1]

    for i in range(1, len(activities)):
        if activities[i][0] >= last_end_time:
            selected_activities.append(activities[i])
            last_end_time = activities[i][1]

    return selected_activities

activities = [(1, 3), (2, 4), (3, 5), (0, 6), (5, 7), (8, 9), (5, 9)]
print(activity_selection(activities))  # Output: [(1, 3), (3, 5), (5, 7), (8, 9)]
```

### 2. Fractional Knapsack Problem

**Problem**: Given weights and values of n items, put these items in a knapsack of capacity W to get the maximum total value in the knapsack. Items can be broken into smaller pieces.

**Solution**:
```python
def fractional_knapsack(weights, values, capacity):
    index = list(range(len(values)))
    ratio = [v/w for v, w in zip(values, weights)]
    index.sort(key=lambda i: ratio[i], reverse=True)

    max_value = 0
    fractions = [0] * len(values)
    for i in index:
        if weights[i] <= capacity:
            fractions[i] = 1
            max_value += values[i]
            capacity -= weights[i]
        else:
            fractions[i] = capacity / weights[i]
            max_value += values[i] * fractions[i]
            break

    return max_value, fractions

weights = [10, 20, 30]
values = [60, 100, 120]
capacity = 50
print(fractional_knapsack(weights, values, capacity))  # Output: (240.0, [1, 1, 0.6666666666666666])
```

### 3. Prim's Minimum Spanning Tree

**Problem**: Given a connected, undirected graph, find the minimum spanning tree using Prim's algorithm.

**Solution**:
```python
import heapq

def prims_mst(graph, start):
    mst = []
    visited = set([start])
    edges = [(cost, start, to) for to, cost in graph[start].items()]
    heapq.heapify(edges)

    while edges:
        cost, frm, to = heapq.heappop(edges)
        if to not in visited:
            visited.add(to)
            mst.append((frm, to, cost))
            for to_next, cost in graph[to].items():
                if to_next not in visited:
                    heapq.heappush(edges, (cost, to, to_next))

    return mst

graph = {
    'A': {'B': 1, 'C': 3, 'D': 4},
    'B': {'A': 1, 'C': 2, 'D': 5},
    'C': {'A': 3, 'B': 2, 'D': 6},
    'D': {'A': 4, 'B': 5, 'C': 6}
}
print(prims_mst(graph, 'A'))  # Output: [('A', 'B', 1), ('B', 'C', 2), ('A', 'D', 4)]
```

### 4. Kruskal's Minimum Spanning Tree

**Problem**: Given a connected, undirected graph, find the minimum spanning tree using Kruskal's algorithm.

**Solution**:
```python
class DisjointSet:
    def __init__(self, vertices):
        self.parent = {v: v for v in vertices}
        self.rank = {v: 0 for v in vertices}

    def find(self, item):
        if self.parent[item] == item:
            return item
        else:
            self.parent[item] = self.find(self.parent[item])
            return self.parent[item]

    def union(self, set1, set2):
        root1 = self.find(set1)
        root2 = self.find(set2)

        if root1 != root2:
            if self.rank[root1] > self.rank[root2]:
                self.parent[root2] = root1
            else:
                self.parent[root1] = root2
                if self.rank[root1] == self.rank[root2]:
                    self.rank[root2] += 1

def kruskal_mst(graph):
    edges = sorted(graph['edges'], key=lambda x: x[2])
    ds = DisjointSet(graph['vertices'])
    mst = []

    for edge in edges:
        u, v, weight = edge
        if ds.find(u) != ds.find(v):
            ds.union(u, v)
            mst.append(edge)

    return mst

graph = {
    'vertices': ['A', 'B', 'C', 'D'],
    'edges': [
        ('A', 'B', 1),
        ('B', 'C', 2),
        ('A', 'C', 3),
        ('A', 'D', 4),
        ('B', 'D', 5),
        ('C', 'D', 6)
    ]
}
print(kruskal_mst(graph))  # Output: [('A', 'B', 1), ('B', 'C', 2), ('A', 'D', 4)]
```

### 5. Dijkstra's Shortest Path Algorithm

**Problem**: Given a graph and a source vertex, find the shortest path from the source to all vertices using Dijkstra's algorithm.

**Solution**:
```python
import heapq

def dijkstra(graph, start):
    pq = [(0, start)]
    distances = {vertex: float('infinity') for vertex in graph}
    distances[start] = 0

    while pq:
        current_distance, current_vertex = heapq.heappop(pq)

        if current_distance > distances[current_vertex]:
            continue

        for neighbor, weight in graph[current_vertex].items():
            distance = current_distance + weight

            if distance < distances[neighbor]:
                distances[neighbor] = distance
                heapq.heappush(pq, (distance, neighbor))

    return distances

graph = {
    'A': {'B': 1, 'C': 4},
    'B': {'A': 1, 'C': 2, 'D': 5},
    'C': {'A': 4, 'B': 2, 'D': 1},
    'D': {'B': 5, 'C': 1}
}
print(dijkstra(graph, 'A'))  # Output: {'A': 0, 'B': 1, 'C': 3, 'D': 4}
```

### 6. Job Sequencing Problem

**Problem**: Given a set of jobs with deadlines and profits, find the sequence of jobs that maximizes the total profit.

**Solution**:
```python
def job_sequencing(jobs):
    jobs.sort(key=lambda x: x[2], reverse=True)
    n = len(jobs)
    result = [False] * n
    job_sequence = ['-1'] * n

    for i in range(len(jobs)):
        for j in range(min(n, jobs[i][1]) - 1, -1, -1):
            if not result[j]:
                result[j] = True
                job_sequence[j] = jobs[i][0]
                break

    return job_sequence

jobs = [
    ('a', 2, 100),
    ('b', 1, 19),
    ('c', 2, 27),
    ('d', 1, 25),
    ('e', 3, 15)
]
print(job_sequencing(jobs))  # Output: ['c', 'a', 'e']
```

# Linked Lists

### 1. Singly Linked List

**Node Class:**
```python
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
```

**Singly Linked List Class:**
```python
class SinglyLinkedList:
    def __init__(self):
        self.head = None

    def append(self, data):
        new_node = Node(data)
        if not self.head:
            self.head = new_node
            return
        last_node = self.head
        while last_node.next:
            last_node = last_node.next
        last_node.next = new_node

    def prepend(self, data):
        new_node = Node(data)
        new_node.next = self.head
        self.head = new_node

    def delete_node(self, key):
        cur_node = self.head
        if cur_node and cur_node.data == key:
            self.head = cur_node.next
            cur_node = None
            return
        prev = None
        while cur_node and cur_node.data != key:
            prev = cur_node
            cur_node = cur_node.next
        if cur_node is None:
            return
        prev.next = cur_node.next
        cur_node = None

    def print_list(self):
        cur_node = self.head
        while cur_node:
            print(cur_node.data, end=" -> ")
            cur_node = cur_node.next
        print("None")
```

### 2. Doubly Linked List

**Node Class:**
```python
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
        self.prev = None
```

**Doubly Linked List Class:**
```python
class DoublyLinkedList:
    def __init__(self):
        self.head = None

    def append(self, data):
        new_node = Node(data)
        if not self.head:
            self.head = new_node
            return
        last_node = self.head
        while last_node.next:
            last_node = last_node.next
        last_node.next = new_node
        new_node.prev = last_node

    def prepend(self, data):
        new_node = Node(data)
        if not self.head:
            self.head = new_node
            return
        self.head.prev = new_node
        new_node.next = self.head
        self.head = new_node

    def delete_node(self, key):
        cur_node = self.head
        while cur_node:
            if cur_node.data == key:
                if cur_node.prev:
                    cur_node.prev.next = cur_node.next
                if cur_node.next:
                    cur_node.next.prev = cur_node.prev
                if cur_node == self.head:
                    self.head = cur_node.next
                cur_node = None
                return
            cur_node = cur_node.next

    def print_list(self):
        cur_node = self.head
        while cur_node:
            print(cur_node.data, end=" <-> ")
            cur_node = cur_node.next
        print("None")
```

### 3.3 Circular Linked List

**Node Class:**

```python
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
```

**Circular Linked List Class:**

```python
class CircularLinkedList:
    def __init__(self):
        self.head = None

    def append(self, data):
        new_node = Node(data)
        if not self.head:
            self.head = new_node
            new_node.next = self.head
            return
        cur_node = self.head
        while cur_node.next != self.head:
            cur_node = cur_node.next
        cur_node.next = new_node
        new_node.next = self.head

    def prepend(self, data):
        new_node = Node(data)
        cur_node = self.head
        new_node.next = self.head
        if not self.head:
            new_node.next = new_node
        else:
            while cur_node.next != self.head:
                cur_node = cur_node.next
            cur_node.next = new_node
        self.head = new_node

    def delete_node(self, key):
        if self.head.data == key:
            cur_node = self.head
            while cur_node.next != self.head:
                cur_node = cur_node.next
            if self.head == self.head.next:
                self.head = None
            else:
                cur_node.next = self.head.next
                self.head = self.head.next
            return
        prev = None
        cur_node = self.head
        while cur_node.next != self.head:
            prev = cur_node
            cur_node = cur_node.next
            if cur_node.data == key:
                prev.next = cur_node.next
                cur_node = cur_node.next

    def print_list(self):
        cur_node = self.head
        while cur_node:
            print(cur_node.data, end=" -> ")
            cur_node = cur_node.next
            if cur_node == self.head:
                break
        print("None")
```

## 4. Advanced Concepts

### 4.1 Reverse a Linked List

**Singly Linked List:**

```python
def reverse(self):
    prev = None
    cur = self.head
    while cur:
        nxt = cur.next
        cur.next = prev
        prev = cur
        cur = nxt
    self.head = prev
```

*Add this method to the SinglyLinkedList class*

### 4.2 Detect a Cycle in a Linked List

**Floyd’s Cycle-Finding Algorithm:**

```python
def has_cycle(self):
    slow = self.head
    fast = self.head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
        if slow == fast:
            return True
    return False
```

*Add this method to the SinglyLinkedList class*

# Graphs

### 1. Graph Representation
**Adjacency List Representation:**
```python
class Graph:
    def __init__(self):
        self.graph = {}

    def add_edge(self, u, v):
        if u not in self.graph:
            self.graph[u] = []
        self.graph[u].append(v)

    def print_graph(self):
        for node in self.graph:
            print(node, "->", self.graph[node])
```

### 2. Depth-First Search (DFS)
**DFS Implementation:**
```python
def dfs(graph, start, visited=None):
    if visited is None:
        visited = set()
    visited.add(start)
    print(start, end=' ')
    for next in graph[start] - visited:
        dfs(graph, next, visited)
    return visited

# Example usage
graph = {
    0: {1, 2},
    1: {2},
    2: {0, 3},
    3: {3}
}
dfs(graph, 0)
```

### 3. Breadth-First Search (BFS)
**BFS Implementation:**
```python
def bfs(graph, start):
    visited = set()
    queue = [start]
    visited.add(start)
    while queue:
        vertex = queue.pop(0)
        print(vertex, end=' ')
        for neighbor in graph[vertex]:
            if neighbor not in visited:
                visited.add(neighbor)
                queue.append(neighbor)

# Example usage
graph = {
    0: [1, 2],
    1: [2],
    2: [0, 3],
    3: [3]
}
bfs(graph, 0)
```

### 4. Dijkstra's Algorithm
**Dijkstra's Algorithm Implementation:**
```python
import heapq

def dijkstra(graph, start):
    pq = [(0, start)]
    distances = {vertex: float('infinity') for vertex in graph}
    distances[start] = 0

    while pq:
        current_distance, current_vertex = heapq.heappop(pq)

        if current_distance > distances[current_vertex]:
            continue

        for neighbor, weight in graph[current_vertex].items():
            distance = current_distance + weight

            if distance < distances[neighbor]:
                distances[neighbor] = distance
                heapq.heappush(pq, (distance, neighbor))

    return distances

# Example usage
graph = {
    'A': {'B': 1, 'C': 4},
    'B': {'A': 1, 'C': 2, 'D': 5},
    'C': {'A': 4, 'B': 2, 'D': 1},
    'D': {'B': 5, 'C': 1}
}
print(dijkstra(graph, 'A'))
```

### 5. Prim's Algorithm
**Prim's Algorithm Implementation:**
```python
import heapq

def prims_mst(graph, start):
    mst = []
    visited = set([start])
    edges = [(cost, start, to) for to, cost in graph[start].items()]
    heapq.heapify(edges)

    while edges:
        cost, frm, to = heapq.heappop(edges)
        if to not in visited:
            visited.add(to)
            mst.append((frm, to, cost))
            for to_next, cost in graph[to].items():
                if to_next not in visited:
                    heapq.heappush(edges, (cost, to, to_next))

    return mst

# Example usage
graph = {
    'A': {'B': 1, 'C': 3, 'D': 4},
    'B': {'A': 1, 'C': 2, 'D': 5},
    'C': {'A': 3, 'B': 2, 'D': 6},
    'D': {'A': 4, 'B': 5, 'C': 6}
}
print(prims_mst(graph, 'A'))
```

### 6. Kruskal's Algorithm
**Kruskal's Algorithm Implementation:**
```python
class DisjointSet:
    def __init__(self, vertices):
        self.parent = {v: v for v in vertices}
        self.rank = {v: 0 for v in vertices}

    def find(self, item):
        if self.parent[item] == item:
            return item
        else:
            self.parent[item] = self.find(self.parent[item])
            return self.parent[item]

    def union(self, set1, set2):
        root1 = self.find(set1)
        root2 = self.find(set2)

        if root1 != root2:
            if self.rank[root1] > self.rank[root2]:
                self.parent[root2] = root1
            else:
                self.parent[root1] = root2
                if self.rank[root1] == self.rank[root2]:
                    self.rank[root2] += 1

def kruskal_mst(graph):
    edges = sorted(graph['edges'], key=lambda x: x[2])
    ds = DisjointSet(graph['vertices'])
    mst = []

    for edge in edges:
        u, v, weight = edge
        if ds.find(u) != ds.find(v):
            ds.union(u, v)
            mst.append(edge)

    return mst

# Example usage
graph = {
    'vertices': ['A', 'B', 'C', 'D'],
    'edges': [
        ('A', 'B', 1),
        ('B', 'C', 2),
        ('A', 'C', 3),
        ('A', 'D', 4),
        ('B', 'D', 5),
        ('C', 'D', 6)
    ]
}
print(kruskal_mst(graph))
```

### 8. Cycle Detection
**Cycle Detection in Directed Graph:**
```python
def is_cyclic_util(v, visited, rec_stack, graph):
    visited.add(v)
    rec_stack.add(v)

    for neighbor in graph[v]:
        if neighbor not in visited:
            if is_cyclic_util(neighbor, visited, rec_stack, graph):
                return True
        elif neighbor in rec_stack:
            return True

    rec_stack.remove(v)
    return False

def is_cyclic(graph):
    visited = set()
    rec_stack = set()

    for node in graph:
        if node not in visited:
            if is_cyclic_util(node, visited, rec_stack, graph):
                return True
    return False

# Example usage
graph = {
    'A': ['B'],
    'B': ['C'],
    'C': ['A'],
    'D': ['E'],
    'E': []
}
print(is_cyclic(graph))  # Output: True
```

# Trees

## Detailed Examples

### 1. Binary Tree
**Node Class:**
```python
class Node:
    def __init__(self, key):
        self.left = None
        self.right = None
        self.val = key
```

**Binary Tree Class:**
```python
class BinaryTree:
    def __init__(self):
        self.root = None

    def insert(self, key):
        if self.root is None:
            self.root = Node(key)
        else:
            self._insert(self.root, key)

    def _insert(self, root, key):
        if key < root.val:
            if root.left is None:
                root.left = Node(key)
            else:
                self._insert(root.left, key)
        else:
            if root.right is None:
                root.right = Node(key)
            else:
                self._insert(root.right, key)

    def inorder_traversal(self, root):
        if root:
            self.inorder_traversal(root.left)
            print(root.val, end=' ')
            self.inorder_traversal(root.right)
```

### 2. Binary Search Tree (BST)
**BST Class:**
```python
class BST:
    def __init__(self):
        self.root = None

    def insert(self, key):
        if self.root is None:
            self.root = Node(key)
        else:
            self._insert(self.root, key)

    def _insert(self, root, key):
        if key < root.val:
            if root.left is None:
                root.left = Node(key)
            else:
                self._insert(root.left, key)
        else:
            if root.right is None:
                root.right = Node(key)
            else:
                self._insert(root.right, key)

    def search(self, key):
        return self._search(self.root, key)

    def _search(self, root, key):
        if root is None or root.val == key:
            return root
        if key < root.val:
            return self._search(root.left, key)
        return self._search(root.right, key)

    def delete(self, key):
        self.root = self._delete(self.root, key)

    def _delete(self, root, key):
        if root is None:
            return root
        if key < root.val:
            root.left = self._delete(root.left, key)
        elif key > root.val:
            root.right = self._delete(root.right, key)
        else:
            if root.left is None:
                return root.right
            elif root.right is None:
                return root.left
            temp = self._min_value_node(root.right)
            root.val = temp.val
            root.right = self._delete(root.right, temp.val)
        return root

    def _min_value_node(self, node):
        current = node
        while current.left is not None:
            current = current.left
        return current

    def inorder_traversal(self, root):
        if root:
            self.inorder_traversal(root.left)
            print(root.val, end=' ')
            self.inorder_traversal(root.right)
```

### 3. Balanced Trees (AVL Tree)
**AVL Tree Class:**
```python
class AVLNode:
    def __init__(self, key):
        self.left = None
        self.right = None
        self.val = key
        self.height = 1

class AVLTree:
    def insert(self, root, key):
        if not root:
            return AVLNode(key)
        elif key < root.val:
            root.left = self.insert(root.left, key)
        else:
            root.right = self.insert(root.right, key)

        root.height = 1 + max(self.get_height(root.left), self.get_height(root.right))

        balance = self.get_balance(root)

        if balance > 1 and key < root.left.val:
            return self.right_rotate(root)
        if balance < -1 and key > root.right.val:
            return self.left_rotate(root)
        if balance > 1 and key > root.left.val:
            root.left = self.left_rotate(root.left)
            return self.right_rotate(root)
        if balance < -1 and key < root.right.val:
            root.right = self.right_rotate(root.right)
            return self.left_rotate(root)

        return root

    def left_rotate(self, z):
        y = z.right
        T2 = y.left
        y.left = z
        z.right = T2
        z.height = 1 + max(self.get_height(z.left), self.get_height(z.right))
        y.height = 1 + max(self.get_height(y.left), self.get_height(y.right))
        return y

    def right_rotate(self, z):
        y = z.left
        T3 = y.right
        y.right = z
        z.left = T3
        z.height = 1 + max(self.get_height(z.left), self.get_height(z.right))
        y.height = 1 + max(self.get_height(y.left), self.get_height(y.right))
        return y

    def get_height(self, root):
        if not root:
            return 0
        return root.height

    def get_balance(self, root):
        if not root:
            return 0
        return self.get_height(root.left) - self.get_height(root.right)

    def inorder_traversal(self, root):
        if root:
            self.inorder_traversal(root.left)
            print(root.val, end=' ')
            self.inorder_traversal(root.right)
```

### 4. Heap (Min-Heap)
**Min-Heap Class:**
```python
import heapq

class MinHeap:
    def __init__(self):
        self.heap = []

    def insert(self, key):
        heapq.heappush(self.heap, key)

    def extract_min(self):
        return heapq.heappop(self.heap)

    def get_min(self):
        return self.heap[0]

    def print_heap(self):
        print(self.heap)
```

## Advanced Concepts

### 1. Tree Traversal
**Preorder Traversal:**
```python
def preorder_traversal(root):
    if root:
        print(root.val, end=' ')
        preorder_traversal(root.left)
        preorder_traversal(root.right)
```

**Inorder Traversal:**
```python
def inorder_traversal(root):
    if root:
        inorder_traversal(root.left)
        print(root.val, end=' ')
        inorder_traversal(root.right)
```

**Postorder Traversal:**
```python
def postorder_traversal(root):
    if root:
        postorder_traversal(root.left)
        postorder_traversal(root.right)
        print(root.val, end=' ')
```

### 2. Level Order Traversal
**Level Order Traversal Implementation:**
```python
from collections import deque

def level_order_traversal(root):
    if not root:
        return
    queue = deque([root])
    while queue:
        node = queue.popleft()
        print(node.val, end=' ')
        if node.left:
            queue.append(node.left)
        if node.right:
            queue.append(node.right)
```

### 3. Height of a Tree
**Height Calculation:**
```python
def height(root):
    if not root:
        return 0
    left_height = height(root.left)
    right_height = height(root.right)
    return max(left_height, right_height) + 1
```

### 4. Diameter of a Tree
**Diameter Calculation:**
```python
def diameter(root):
    def height_and_diameter(node):
        if not node:
            return 0, 0
        left_height, left_diameter = height_and_diameter(node.left)
        right_height, right_diameter = height_and_diameter(node.right)
        current_height = max(left_height, right_height) + 1
        current_diameter = max(left_diameter, right_diameter, left_height + right_height + 1)
        return current_height, current_diameter

    return height_and_diameter(root)[1]
```

### 5. Delete a Node in a Tree
**Delete Node Implementation:**
```python
def delete_node(root, key):
    if root is None:
        return root
    if key < root.val:
        root.left = delete_node(root.left, key)
    elif key > root.val:
        root.right = delete_node(root.right, key)
    else:
        if root.left is None:
            return root.right
        elif root.right is None:
            return root.left
        temp = min_value_node(root.right)
        root.val = temp.val
        root.right = delete_node(root.right, temp.val)
    return root

def min_value_node(node):
    current = node
    while current.left is not None:
        current = current.left
    return current
```