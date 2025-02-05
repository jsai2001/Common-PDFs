# Arrays in Python (Comprehensive Notes)

## 1. Introduction to Arrays
Arrays are data structures that store multiple values of the same type. In Python, arrays can be implemented using lists or the array module.

## 2. Creating Arrays
### Using Lists:
```python
arr = [1, 2, 3, 4, 5]
```
### Using the array Module:
```python
import array as arr
a = arr.array('i', [1, 2, 3, 4, 5])
```

## 3. Accessing Array Elements
Access elements by index:
```python
print(arr[0])  # Output: 1
print(a[1])    # Output: 2
```

## 4. Modifying Array Elements
Modify elements by index:
```python
arr[0] = 10
a[1] = 20
```

## 5. Array Operations
### Appending Elements:
```python
arr.append(6)
a.append(7)
```
### Inserting Elements:
```python
arr.insert(2, 15)
a.insert(3, 25)
```
### Removing Elements:
```python
arr.remove(15)
a.remove(25)
```
### Popping Elements:
```python
arr.pop()
a.pop()
```
### Finding Index:
```python
index = arr.index(10)
index_a = a.index(20)
```
### Counting Elements:
```python
count = arr.count(10)
count_a = a.count(20)
```

## 6. Array Slicing
Slicing arrays:
```python
sub_arr = arr[1:4]
sub_a = a[1:3]
```

## 7. Array Traversal
Using loops to traverse arrays:
```python
for element in arr:
    print(element)

for element in a:
    print(element)
```

## 8. Array Sorting
Sorting arrays:
```python
arr.sort()
a = sorted(a)
```

## 9. Array Reversal
Reversing arrays:
```python
arr.reverse()
a.reverse()
```

## 10. Array Length
Finding the length of arrays:
```python
length = len(arr)
length_a = len(a)
```

## 11. Multi-dimensional Arrays
Creating and manipulating 2D and 3D arrays:
```python
# 2D array using lists
matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]

# Accessing elements
print(matrix[0][1])  # Output: 2
```

## 12. Array Libraries
Using libraries like NumPy for advanced array operations:
```python
import numpy as np

# Creating a NumPy array
np_array = np.array([1, 2, 3, 4, 5])

# Multi-dimensional NumPy array
np_matrix = np.array([
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
])
```

## 13. Array Methods and Functions
Common array methods and functions in NumPy:
```python
# NumPy array operations
np_array = np.array([1, 2, 3, 4, 5])
np_array_sum = np.sum(np_array)
np_array_mean = np.mean(np_array)
np_array_max = np.max(np_array)
np_array_min = np.min(np_array)
```

## 14. Array Reshaping
Reshaping arrays using NumPy:
```python
np_array = np.array([1, 2, 3, 4, 5, 6])
np_array_reshaped = np_array.reshape(2, 3)
```

## 15. Array Broadcasting
Understanding broadcasting in NumPy:
```python
np_array = np.array([1, 2, 3])
np_array_broadcasted = np_array + 1  # Output: [2, 3, 4]
```

## 16. Array Concatenation and Splitting
Concatenating and splitting arrays:
```python
np_array1 = np.array([1, 2, 3])
np_array2 = np.array([4, 5, 6])
concatenated_array = np.concatenate((np_array1, np_array2))  # Output: [1, 2, 3, 4, 5, 6]

split_array = np.split(concatenated_array, 2)  # Output: [array([1, 2, 3]), array([4, 5, 6])]
```

## Complex Program Covering All Topics
```python
import array as arr
import numpy as np

# Creating arrays
list_array = [1, 2, 3, 4, 5]
array_module_array = arr.array('i', [1, 2, 3, 4, 5])

# Accessing elements
print("Accessing elements:")
print(list_array[0])  # Output: 1
print(array_module_array[1])  # Output: 2

# Modifying elements
list_array[0] = 10
array_module_array[1] = 20

# Appending elements
list_array.append(6)
array_module_array.append(7)

# Inserting elements
list_array.insert(2, 15)
array_module_array.insert(3, 25)

# Removing elements
list_array.remove(15)
array_module_array.remove(25)

# Popping elements
list_array.pop()
array_module_array.pop()

# Finding index
index_list = list_array.index(10)
index_array = array_module_array.index(20)

# Counting elements
count_list = list_array.count(10)
count_array = array_module_array.count(20)

# Slicing arrays
sub_list_array = list_array[1:4]
sub_array_module_array = array_module_array[1:3]

# Traversing arrays
print("Traversing list_array:")
for element in list_array:
    print(element)

print("Traversing array_module_array:")
for element in array_module_array:
    print(element)

# Sorting arrays
list_array.sort()
array_module_array = arr.array('i', sorted(array_module_array))

# Reversing arrays
list_array.reverse()
array_module_array.reverse()

# Finding length
length_list = len(list_array)
length_array = len(array_module_array)

# Multi-dimensional arrays
matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]
print("2D array element:", matrix[0][1])  # Output: 2

# NumPy arrays
np_array = np.array([1, 2, 3, 4, 5])
np_matrix = np.array([
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
])

# NumPy array operations
np_array_sum = np.sum(np_array)
np_array_mean = np.mean(np_array)
np_array_max = np.max(np_array)
np_array_min = np.min(np_array)

# Reshaping NumPy arrays
np_array_reshaped = np_array.reshape(2, 3)

# Broadcasting in NumPy
np_array_broadcasted = np_array + 1  # Output: [2, 3, 4, 5, 6]

# Concatenating and splitting NumPy arrays
np_array1 = np.array([1, 2, 3])
np_array2 = np.array([4, 5, 6])
concatenated_array = np.concatenate((np_array1, np_array2))  # Output: [1, 2, 3, 4, 5, 6]
split_array = np.split(concatenated_array, 2)  # Output: [array([1, 2, 3]), array([4, 5, 6])]

# Display final arrays
print("Final list_array:", list_array)
print("Final array_module_array:", array_module_array)
print("NumPy array:", np_array)
print("NumPy matrix:", np_matrix)
print("Sum of NumPy array:", np_array_sum)
print("Mean of NumPy array:", np_array_mean)
print("Max of NumPy array:", np_array_max)
print("Min of NumPy array:", np_array_min)
print("Reshaped NumPy array:", np_array_reshaped)
print("Broadcasted NumPy array:", np_array_broadcasted)
print("Concatenated NumPy array:", concatenated_array)
print("Split NumPy array:", split_array)
```

## Conclusion
This comprehensive guide covers all essential topics related to arrays in Python, including creation, access, modification, operations, slicing, traversal, sorting, reversal, multi-dimensional arrays, and advanced concepts using NumPy. The provided complex program demonstrates these concepts in action, ensuring you have a solid understanding of arrays for your interview preparation.

### Strings

Comprehensive Notes on Strings in Python

1. **Introduction to Strings**  
Strings are sequences of characters enclosed in single quotes ('), double quotes ("), or triple quotes (''' or """).

2. **Creating Strings**  
Single-line strings:
```python
single_quote_str = 'Hello'
double_quote_str = "World"
```
Multi-line strings:
```python
multi_line_str = '''This is
a multi-line
string.'''
```

3. **Accessing String Elements**  
Access elements by index:
```python
str = "Hello"
print(str[0])  # Output: H
print(str[-1])  # Output: o
```

4. **String Slicing**  
Slicing strings:
```python
str = "Hello, World!"
print(str[0:5])  # Output: Hello
print(str[7:])  # Output: World!
print(str[:5])  # Output: Hello
print(str[-6:])  # Output: World!
```

5. **String Concatenation and Repetition**  
Concatenation:
```python
str1 = "Hello"
str2 = "World"
result = str1 + " " + str2  # Output: Hello World
```
Repetition:
```python
str = "Hello"
result = str * 3  # Output: HelloHelloHello
```

6. **String Methods**  
Common string methods:
```python
str = "Hello, World!"
print(str.lower())  # Output: hello, world!
print(str.upper())  # Output: HELLO, WORLD!
print(str.title())  # Output: Hello, World!
print(str.strip())  # Removes leading and trailing whitespace
print(str.replace("World", "Python"))  # Output: Hello, Python!
print(str.split(","))  # Output: ['Hello', ' World!']
```

7. **String Formatting**  
Using `%` operator:
```python
name = "John"
age = 30
print("My name is %s and I am %d years old." % (name, age))
```
Using `str.format()` method:
```python
print("My name is {} and I am {} years old.".format(name, age))
```
Using f-strings (Python 3.6+):
```python
print(f"My name is {name} and I am {age} years old.")
```

8. **String Searching**  
Finding substrings:
```python
str = "Hello, World!"
print(str.find("World"))  # Output: 7
print(str.startswith("Hello"))  # Output: True
print(str.endswith("!"))  # Output: True
```

9. **String Immutability**  
Strings are immutable, meaning their elements cannot be changed.
```python
str = "Hello"
# str[0] = 'h'  # This will raise an error
```

10. **Advanced String Operations**  
Regular Expressions:
```python
import re

pattern = r"\b[a-zA-Z]{5}\b"
text = "Hello, World! This is a test."
matches = re.findall(pattern, text)
print(matches)  # Output: ['Hello', 'World']
```
String Encoding and Decoding:
```python
str = "Hello, World!"
encoded_str = str.encode("utf-8")
print(encoded_str)  # Output: b'Hello, World!'
decoded_str = encoded_str.decode("utf-8")
print(decoded_str)  # Output: Hello, World!
```

**Complex Program Covering All Topics**
```python
import re

# Creating strings
single_quote_str = 'Hello'
double_quote_str = "World"
multi_line_str = '''This is
a multi-line
string.'''

# Accessing elements
print("Accessing elements:")
print(single_quote_str[0])  # Output: H
print(double_quote_str[-1])  # Output: d

# String slicing
print("String slicing:")
str = "Hello, World!"
print(str[0:5])  # Output: Hello
print(str[7:])  # Output: World!
print(str[:5])  # Output: Hello
print(str[-6:])  # Output: World!

# String concatenation and repetition
print("String concatenation and repetition:")
str1 = "Hello"
str2 = "World"
result = str1 + " " + str2
print(result)  # Output: Hello World
result = str1 * 3
print(result)  # Output: HelloHelloHello

# String methods
print("String methods:")
str = "  Hello, World!  "
print(str.lower())  # Output: hello, world!
print(str.upper())  # Output: HELLO, WORLD!
print(str.title())  # Output: Hello, World!
print(str.strip())  # Output: Hello, World!
print(str.replace("World", "Python"))  # Output: Hello, Python!
print(str.split(","))  # Output: ['  Hello', ' World!  ']

# String formatting
print("String formatting:")
name = "John"
age = 30
print("My name is %s and I am %d years old." % (name, age))
print("My name is {} and I am {} years old.".format(name, age))
print(f"My name is {name} and I am {age} years old.")

# String searching
print("String searching:")
str = "Hello, World!"
print(str.find("World"))  # Output: 7
print(str.startswith("Hello"))  # Output: True
print(str.endswith("!"))  # Output: True

# Regular expressions
print("Regular expressions:")
pattern = r"\b[a-zA-Z]{5}\b"
text = "Hello, World! This is a test."
matches = re.findall(pattern, text)
print(matches)  # Output: ['Hello', 'World']

# String encoding and decoding
print("String encoding and decoding:")
str = "Hello, World!"
encoded_str = str.encode("utf-8")
print(encoded_str)  # Output: b'Hello, World!'
decoded_str = encoded_str.decode("utf-8")
print(decoded_str)  # Output: Hello, World!
```

**Conclusion**  
This comprehensive guide covers all essential topics related to strings in Python, including creation, access, slicing, concatenation, repetition, methods, formatting, searching, immutability, regular expressions, and encoding/decoding. The provided complex program demonstrates these concepts in action, ensuring you have a solid understanding of strings for your interview preparation.

# Dynamic Programming in Python (Comprehensive Notes)

## 1. Introduction to Dynamic Programming

Dynamic Programming (DP) is a method for solving complex problems by breaking them down into simpler subproblems. It is applicable when the problem can be divided into overlapping subproblems that can be solved independently.

## 2. Key Concepts

- **Overlapping Subproblems**: The problem can be broken down into subproblems which are reused several times.
- **Optimal Substructure**: The optimal solution to the problem can be constructed from the optimal solutions of its subproblems.

## 3. Approaches to Dynamic Programming

- **Top-Down Approach (Memoization)**: Solve the problem recursively and store the results of subproblems to avoid redundant computations.
- **Bottom-Up Approach (Tabulation)**: Solve the problem iteratively by solving all subproblems first and using their solutions to build up the solution to the original problem.

## 4. Common Dynamic Programming Problems

- Fibonacci Sequence
- Longest Common Subsequence (LCS)
- Knapsack Problem
- Coin Change Problem
- Longest Increasing Subsequence (LIS)
- Edit Distance

## Detailed Examples

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

## Conclusion

This comprehensive guide covers essential topics related to dynamic programming in Python, including key concepts, approaches, and common problems. The provided complex programs demonstrate these concepts in action, ensuring you have a solid understanding of dynamic programming for your interview preparation.

# Greedy Algorithms in Python (Comprehensive Notes)

## 1. Introduction to Greedy Algorithms

Greedy algorithms build up a solution piece by piece, always choosing the next piece that offers the most immediate benefit. They are used for optimization problems where the goal is to find the best solution among many possible ones.

## 2. Key Concepts

- **Greedy Choice Property**: A global optimum can be arrived at by selecting a local optimum.
- **Optimal Substructure**: An optimal solution to the problem contains an optimal solution to subproblems.

## 3. Common Greedy Algorithms Problems

- Activity Selection Problem
- Fractional Knapsack Problem
- Huffman Coding
- Prim's Minimum Spanning Tree
- Kruskal's Minimum Spanning Tree
- Dijkstra's Shortest Path Algorithm
- Job Sequencing Problem

## Detailed Examples

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

### 3. Huffman Coding

**Problem**: Given a set of characters and their frequencies, construct a Huffman Tree and print the Huffman codes.

**Solution**:
```python
import heapq

class Node:
    def __init__(self, freq, symbol, left=None, right=None):
        self.freq = freq
        self.symbol = symbol
        self.left = left
        self.right = right
        self.huff = ''

    def __lt__(self, other):
        return self.freq < other.freq

def huffman_coding(symbols, frequencies):
    heap = [Node(frequencies[i], symbols[i]) for i in range(len(symbols))]
    heapq.heapify(heap)

    while len(heap) > 1:
        left = heapq.heappop(heap)
        right = heapq.heappop(heap)
        left.huff = '0'
        right.huff = '1'
        newNode = Node(left.freq + right.freq, left.symbol + right.symbol, left, right)
        heapq.heappush(heap, newNode)

    huffman_tree = heap[0]
    codes = {}
    def generate_codes(node, current_code=''):
        if node is None:
            return
        if node.left is None and node.right is None:
            codes[node.symbol] = current_code
            return
        generate_codes(node.left, current_code + '0')
        generate_codes(node.right, current_code + '1')

    generate_codes(huffman_tree)
    return codes

symbols = ['A', 'B', 'C', 'D', 'E', 'F']
frequencies = [5, 9, 12, 13, 16, 45]
print(huffman_coding(symbols, frequencies))  # Output: {'F': '0', 'C': '100', 'D': '101', 'A': '1100', 'B': '1101', 'E': '111'}
```

### 4. Prim's Minimum Spanning Tree

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

### 5. Kruskal's Minimum Spanning Tree

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

### 6. Dijkstra's Shortest Path Algorithm

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

### 7. Job Sequencing Problem

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

## Conclusion

This comprehensive guide covers essential topics related to greedy algorithms in Python, including key concepts, common problems, and detailed examples. The provided complex programs demonstrate these concepts in action, ensuring you have a solid understanding of greedy algorithms for your interview preparation.

# Linked Lists in Python (Comprehensive Notes)

## 1. Introduction to Linked Lists

A linked list is a linear data structure where each element is a separate object, called a node. Each node contains two items: data and a reference (or link) to the next node in the sequence.

## 2. Types of Linked Lists

- **Singly Linked List**: Each node points to the next node.
- **Doubly Linked List**: Each node points to both the next and the previous node.
- **Circular Linked List**: The last node points back to the first node.

## 3. Detailed Examples

### 3.1 Singly Linked List

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

**Example usage:**

```python
sll = SinglyLinkedList()
sll.append("A")
sll.append("B")
sll.append("C")
sll.prepend("D")
sll.print_list()  # Output: D -> A -> B -> C -> None
sll.delete_node("B")
sll.print_list()  # Output: D -> A -> C -> None
```

### 3.2 Doubly Linked List

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

**Example usage:**

```python
dll = DoublyLinkedList()
dll.append("A")
dll.append("B")
dll.append("C")
dll.prepend("D")
dll.print_list()  # Output: D <-> A <-> B <-> C <-> None
dll.delete_node("B")
dll.print_list()  # Output: D <-> A <-> C <-> None
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

**Example usage:**

```python
cll = CircularLinkedList()
cll.append("A")
cll.append("B")
cll.append("C")
cll.prepend("D")
cll.print_list()  # Output: D -> A -> B -> C -> None
cll.delete_node("B")
cll.print_list()  # Output: D -> A -> C -> None
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

### 4.3 Merge Two Sorted Linked Lists

**Merge Function:**

```python
def merge_sorted(self, llist):
    p = self.head
    q = llist.head
    s = None

    if not p:
        return q
    if not q:
        return p

    if p and q:
        if p.data <= q.data:
            s = p
            p = s.next
        else:
            s = q
            q = s.next
        new_head = s
    while p and q:
        if p.data <= q.data:
            s.next = p
            s = p
            p = s.next
        else:
            s.next = q
            s = q
            q = s.next
    if not p:
        s.next = q
    if not q:
        s.next = p
    return new_head
```

*Add this method to the SinglyLinkedList class*

## Conclusion

This comprehensive guide covers essential topics related to linked lists in Python, including singly linked lists, doubly linked lists, and circular linked lists. It also includes advanced concepts such as reversing a linked list, detecting a cycle, and merging two sorted linked lists. The provided complex programs demonstrate these concepts in action, ensuring you have a solid understanding of linked lists for your interview preparation.

# Graphs in Python (Comprehensive Notes)

## 1. Introduction to Graphs
A graph is a collection of nodes (vertices) and edges connecting pairs of nodes. Graphs can be directed or undirected, weighted or unweighted.

## 2. Graph Representation
- **Adjacency Matrix**: A 2D array where `matrix[i][j]` indicates the presence (and possibly weight) of an edge between vertices `i` and `j`.
- **Adjacency List**: A list where each element represents a vertex and contains a list of its adjacent vertices.

## 3. Graph Traversal
- **Depth-First Search (DFS)**: Explores as far as possible along each branch before backtracking.
- **Breadth-First Search (BFS)**: Explores all neighbors at the present depth before moving on to nodes at the next depth level.

## 4. Graph Algorithms
### Shortest Path Algorithms:
- **Dijkstra's Algorithm**
- **Bellman-Ford Algorithm**

### Minimum Spanning Tree (MST) Algorithms:
- **Prim's Algorithm**
- **Kruskal's Algorithm**

### Other Algorithms:
- **Topological Sorting**: Ordering of vertices in a Directed Acyclic Graph (DAG).
- **Cycle Detection**: Detecting cycles in directed and undirected graphs.

## Detailed Examples

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

# Example usage
g = Graph()
g.add_edge(0, 1)
g.add_edge(0, 2)
g.add_edge(1, 2)
g.add_edge(2, 0)
g.add_edge(2, 3)
g.add_edge(3, 3)
g.print_graph()
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

### 7. Topological Sorting
**Topological Sorting Implementation:**
```python
def topological_sort_util(v, visited, stack, graph):
    visited.add(v)
    for neighbor in graph[v]:
        if neighbor not in visited:
            topological_sort_util(neighbor, visited, stack, graph)
    stack.append(v)

def topological_sort(graph):
    visited = set()
    stack = []

    for vertex in graph:
        if vertex not in visited:
            topological_sort_util(vertex, visited, stack, graph)

    return stack[::-1]

# Example usage
graph = {
    'A': ['C'],
    'B': ['C', 'D'],
    'C': ['E'],
    'D': ['F'],
    'E': ['H', 'F'],
    'F': ['G'],
    'G': [],
    'H': []
}
print(topological_sort(graph))
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

## Conclusion
This comprehensive guide covers essential topics related to graphs in Python, including graph representation, traversal (DFS and BFS), shortest path algorithms (Dijkstra's), minimum spanning tree algorithms (Prim's and Kruskal's), topological sorting, and cycle detection. The provided complex programs demonstrate these concepts in action, ensuring you have a solid understanding of graphs for your interview preparation.

# Trees in Python (Comprehensive Notes)

## 1. Introduction to Trees
A tree is a hierarchical data structure consisting of nodes, with a single node called the root. Each node contains a value and references to its child nodes. Trees are used to represent hierarchical relationships and are fundamental in various algorithms and data structures.

## 2. Types of Trees
- **Binary Tree**: Each node has at most two children.
- **Binary Search Tree (BST)**: A binary tree where the left child contains values less than the parent node, and the right child contains values greater than the parent node.
- **Balanced Trees**: Trees that maintain a balanced height, such as AVL trees and Red-Black trees.
- **Heap**: A special tree-based data structure that satisfies the heap property.
- **Trie**: A tree used for efficient retrieval of keys in a dataset of strings.

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

**Example usage:**
```python
bt = BinaryTree()
bt.insert(10)
bt.insert(5)
bt.insert(20)
bt.insert(3)
bt.insert(7)
bt.inorder_traversal(bt.root)  # Output: 3 5 7 10 20
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

**Example usage:**
```python
bst = BST()
bst.insert(10)
bst.insert(5)
bst.insert(20)
bst.insert(3)
bst.insert(7)
bst.inorder_traversal(bst.root)  # Output: 3 5 7 10 20
print(bst.search(7))  # Output: <__main__.Node object at ...>
print(bst.search(15))  # Output: None
bst.delete(7)
bst.inorder_traversal(bst.root)  # Output: 3 5 10 20
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

**Example usage:**
```python
avl = AVLTree()
root = None
keys = [10, 20, 30, 40, 50, 25]
for key in keys:
    root = avl.insert(root, key)
avl.inorder_traversal(root)  # Output: 10 20 25 30 40 50
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

**Example usage:**
```python
min_heap = MinHeap()
min_heap.insert(3)
min_heap.insert(2)
min_heap.insert(15)
min_heap.insert(5)
min_heap.insert(4)
min_heap.insert(45)
min_heap.print_heap()  # Output: [2, 3, 15, 5, 4, 45]
print(min_heap.extract_min())  # Output: 2
min_heap.print_heap()  # Output: [3, 4, 15, 5, 45]
```

### 5. Trie
**Trie Class:**
```python
class TrieNode:
    def __init__(self):
        self.children = {}
        self.is_end_of_word = False

class Trie:
    def __init__(self):
        self.root = TrieNode()

    def insert(self, word):
        node = self.root
        for char in word:
            if char not in node.children:
                node.children[char] = TrieNode()
            node = node.children[char]
        node.is_end_of_word = True

    def search(self, word):
        node = self.root
        for char in word:
            if char not in node.children:
                return False
            node = node.children[char]
        return node.is_end_of_word

    def starts_with(self, prefix):
        node = self.root
        for char in prefix:
            if char not in node.children:
                return False
            node = node.children[char]
        return True
```

**Example usage:**
```python
trie = Trie()
trie.insert("apple")
print(trie.search("apple"))  # Output: True
print(trie.search("app"))    # Output: False
print(trie.starts_with("app"))  # Output: True
trie.insert("app")
print(trie.search("app"))    # Output: True
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

## Conclusion
This comprehensive guide covers essential topics related to trees in Python, including binary trees, binary search trees, balanced trees (AVL trees), heaps (min-heaps), and tries. It also includes advanced concepts such as tree traversal, level order traversal, height calculation, diameter calculation, and deleting a node. The provided complex programs demonstrate these concepts in action, ensuring you have a solid understanding of trees for your interview preparation.