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

