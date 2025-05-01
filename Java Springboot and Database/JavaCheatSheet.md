Below is a condensed "cheat sheet" of Java code blocks covering all the concepts from your request—OOP, Collections, Exception Handling, Multithreading, and Java 8+ Features. I've packed them tightly while ensuring they remain functional and representative. This is your quick reference—no explanations, just code.

---

### Object-Oriented Programming (OOP)
```java
// Encapsulation
class Patient { private String id; public String getId() { return id; } public void setId(String id) { this.id = id; } }

// Inheritance & Polymorphism
abstract class Vehicle { String brand; Vehicle(String brand) { this.brand = brand; } abstract void move(); }
class Car extends Vehicle { Car(String brand) { super(brand); } void move() { System.out.println(brand + " drives"); } }
class Bike extends Vehicle { Bike(String brand) { super(brand); } void move() { System.out.println(brand + " rides"); } }
Vehicle v = new Car("Toyota"); v.move(); // Toyota drives

// Abstraction
interface AppointmentManager { void schedule(); }
class Doctor implements AppointmentManager { public void schedule() { System.out.println("Scheduled"); } }
```

---

### Collections Framework
```java
import java.util.*;

// List, Set, Map Basics
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "A"));
Set<String> set = new HashSet<>(list); // [A, B]
Map<String, Integer> map = new HashMap<>(); map.put("A", 1); map.put("B", 2);

// ArrayList vs LinkedList
List<String> arrayList = new ArrayList<>(); arrayList.add("X"); // O(1) access
List<String> linkedList = new LinkedList<>(); linkedList.add("Y"); // O(1) insert

// HashMap Iteration
for (Map.Entry<String, Integer> entry : map.entrySet()) { System.out.println(entry.getKey() + ": " + entry.getValue()); }

// Remove Duplicates
list.clear(); list.addAll(set); // [A, B]

// Most Frequent Element
String mostFrequent(List<String> l) {
    Map<String, Integer> freq = new HashMap<>();
    l.forEach(s -> freq.put(s, freq.getOrDefault(s, 0) + 1));
    return Collections.max(freq.entrySet(), Map.Entry.comparingByValue()).getKey();
}

// Sort Custom Objects
class Patient { String name; int time; Patient(String n, int t) { name = n; time = t; } int getTime() { return time; } }
List<Patient> patients = new ArrayList<>(Arrays.asList(new Patient("A", 2), new Patient("B", 1)));
Collections.sort(patients, Comparator.comparing(Patient::getTime)); // [B:1, A:2]
```

---

### Exception Handling
```java
// Custom Exception
class InvalidIdException extends Exception { InvalidIdException(String msg) { super(msg); } }
void validateId(String id) throws InvalidIdException {
    if (id == null || !id.matches("\\d+")) throw new InvalidIdException("Invalid ID");
}

// Multiple Exceptions
void process(String s) {
    try {
        if (s == null) throw new NullPointerException("Null");
        if (s.isEmpty()) throw new IllegalArgumentException("Empty");
    } catch (NullPointerException | IllegalArgumentException e) { System.out.println(e.getMessage()); }
}

// Retry Logic
void retryOp() throws IOException {
    int retries = 3;
    while (retries > 0) {
        try { throw new IOException("Fail"); } 
        catch (IOException e) { retries--; if (retries == 0) throw e; }
    }
}
```

---

### Multithreading and Concurrency
```java
import java.util.concurrent.*;

// Thread Creation
class MyThread extends Thread { public void run() { System.out.println("Thread"); } }
Runnable r = () -> System.out.println("Runnable");
new Thread(r).start();

// ExecutorService
ExecutorService es = Executors.newFixedThreadPool(5);
for (int i = 0; i < 5; i++) es.submit(() -> System.out.println(Thread.currentThread().getName()));
es.shutdown();

// Thread-Safe Counter
class Counter { private AtomicInteger count = new AtomicInteger(0); void inc() { count.incrementAndGet(); } int get() { return count.get(); } }

// Producer-Consumer
class PC {
    LinkedList<Integer> q = new LinkedList<>(); int cap = 2;
    synchronized void produce(int i) throws InterruptedException { 
        while (q.size() == cap) wait(); q.add(i); notify(); 
    }
    synchronized int consume() throws InterruptedException { 
        while (q.isEmpty()) wait(); int i = q.removeFirst(); notify(); return i; 
    }
}
```

---

### Java 8+ Features
```java
import java.util.*; import java.util.stream.*;

// Lambda
Runnable r = () -> System.out.println("Lambda");
List<String> strs = Arrays.asList("cat", "elephant"); strs.sort((s1, s2) -> s1.length() - s2.length()); // [cat, elephant]

// Streams
List<Integer> nums = Arrays.asList(1, 2, 3);
nums.stream().map(n -> n * 2).forEach(System.out::println); // 2, 4, 6
List<List<Integer>> nested = Arrays.asList(Arrays.asList(1), Arrays.asList(2));
nested.stream().flatMap(Collection::stream).forEach(System.out::println); // 1, 2
double avg = nums.stream().mapToInt(Integer::intValue).average().orElse(0.0); // 2.0

// Optional
String s = null;
String result = Optional.ofNullable(s).orElse("Default"); // Default
Optional.ofNullable(s).ifPresent(System.out::println); // No output
```

---

This cheat sheet squeezes all key Java concepts into concise, executable snippets. Save it, run it, tweak it—good luck with your prep!