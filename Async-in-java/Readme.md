# Java Concurrency: ExecutorService, Future, and CompletableFuture

## Overview

These three APIs are closely related but serve different purposes.

| API | Purpose |
|------|---------|
| ExecutorService | Manages and executes tasks using a pool of threads. |
| Future | Represents the result of one asynchronous task. |
| CompletableFuture | Builds and coordinates asynchronous workflows. |

---

## 1. ExecutorService

### What is it?

`ExecutorService` is responsible for managing a pool of worker threads.

Instead of creating a new thread for every task, it reuses existing threads.

### Real-life Example

Restaurant analogy:

- Customers = Tasks
- Chefs = Threads
- Kitchen Manager = ExecutorService

Only a limited number of chefs can cook at once. Extra orders wait in a queue.

### Flow

```text
Tasks
   |
   V
ExecutorService
   |
Task Queue
   |
-------------------------
|        |        |
T1       T2       T3
```

### Example

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

executor.submit(() -> {
    System.out.println("Task running");
});

executor.shutdown();
```

### Key Points

- Manages thread pool
- Reuses threads
- Limits concurrent execution
- Queues extra tasks
- Improves performance

---

## 2. Future

### What is it?

A `Future` is a handle to the result of an asynchronous task.

It does **not** execute the task itself.

The task is executed by the `ExecutorService`.

### Real-life Example

Ordering food.

You receive an order token.

The kitchen prepares the food while you do other things.

Later, you show the token to collect your order.

The token is the `Future`.

### Example

```java
ExecutorService executor = Executors.newSingleThreadExecutor();

Future<Integer> future = executor.submit(() -> {
    Thread.sleep(3000);
    return 100;
});

System.out.println("Doing other work...");

Integer result = future.get();

System.out.println(result);

executor.shutdown();
```

### Useful Methods

```java
future.get();
future.isDone();
future.cancel(true);
future.get(2, TimeUnit.SECONDS);
```

### Limitation

Every call to `get()` blocks until the result is available.

Complex workflows become difficult because you repeatedly wait for one task before starting the next.

---

## 3. CompletableFuture

### What is it?

`CompletableFuture` extends the idea of `Future`.

It allows asynchronous tasks to be chained together without manually calling `get()` between every step.

### Real-life Example

Food delivery app.

```
Payment
    ↓
Restaurant accepts
    ↓
Cooking
    ↓
Pickup
    ↓
Delivered
```

Each stage automatically starts after the previous one completes.

### Example

```java
CompletableFuture.supplyAsync(() -> 10)
    .thenApply(x -> x * 2)
    .thenApply(x -> x + 5)
    .thenAccept(System.out::println);
```

Output

```
25
```

---

## Common Methods

### supplyAsync()

Returns a value.

```java
CompletableFuture.supplyAsync(() -> "Hello");
```

---

### runAsync()

No return value.

```java
CompletableFuture.runAsync(() -> {
    System.out.println("Running");
});
```

---

### thenApply()

Transforms the result.

```java
future.thenApply(x -> x * 2);
```

---

### thenAccept()

Consumes the result.

```java
future.thenAccept(System.out::println);
```

---

### thenCompose()

Chains dependent asynchronous operations.

```java
getUser()
    .thenCompose(this::getOrders);
```

---

### thenCombine()

Combines two independent tasks.

```java
userFuture.thenCombine(addressFuture,
    (u, a) -> new Employee(u, a));
```

---

### allOf()

Waits for all tasks.

```java
CompletableFuture.allOf(f1, f2, f3).join();
```

---

### anyOf()

Returns the first completed task.

```java
CompletableFuture.anyOf(f1, f2, f3);
```

---

### Exception Handling

```java
CompletableFuture
    .supplyAsync(() -> 10 / 0)
    .exceptionally(ex -> 0)
    .thenAccept(System.out::println);
```

---

## Relationship

```
Task
   |
   V
ExecutorService
   |
Runs task on worker thread
   |
Returns Future / CompletableFuture
   |
Retrieve result or chain next tasks
```

---

## Comparison

| Feature | ExecutorService | Future | CompletableFuture |
|--------|-----------------|--------|-------------------|
| Purpose | Manage thread pool | Async result | Async workflow |
| Executes task | Yes | No | Uses executor/common pool |
| Returns value | Via submit() | Yes | Yes |
| Blocking | No | get() blocks | Non-blocking chaining |
| Chain tasks | No | No | Yes |
| Combine tasks | No | Difficult | Easy |
| Exception handling | Manual | Limited | Rich support |

---

## Interview Summary

- **ExecutorService** manages worker threads.
- **Future** represents the result of one asynchronous task.
- **Future.get()** blocks if the task is unfinished.
- **CompletableFuture** allows non-blocking chaining, combining, and exception handling.
- `CompletableFuture` uses the common `ForkJoinPool` by default or a custom `ExecutorService` if supplied.

## Memory Trick

- **ExecutorService** → Who executes the work?
- **Future** → How do I get the result later?
- **CompletableFuture** → How do I build asynchronous workflows?

## Real Life Analogy
Imagine you're at a restaurant. The **ExecutorService** is like the kitchen manager who manages a fixed number of chefs (worker threads). When customers place orders (tasks), the manager assigns them to available chefs. If all chefs are busy, new orders wait in a queue until a chef becomes free. <br>
When you place an order, you're given an order token, which represents a **Future**. You can leave the restaurant and do other work while your food is being prepared asynchronously. Later, when you return and present the token (`future.get()`), if the food is ready, you receive it immediately; otherwise, you must wait at the counter until it's ready. This waiting is equivalent to the blocking behavior of `Future.get()`. <br>
Now imagine instead of returning to the restaurant after every step, you hire a personal assistant and give them a complete workflow: "Collect my food, then pick up my laundry, then pay my electricity bill, and if anything goes wrong, inform me." The assistant performs each task automatically in the correct order while you continue with your own work without interruption. This is how **CompletableFuture** works—it lets you define an entire asynchronous workflow that executes automatically, eliminating the need to manually wait (`get()`) after every task.
