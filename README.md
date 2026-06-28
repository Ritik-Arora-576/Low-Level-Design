### Low Level Design

1. **More problems:** https://github.com/ashishps1/awesome-low-level-design
2. Rate Limiter
3. SQL queries
4. Java Basics

### Important Design Patterns to cover
1. Singleton Design Pattern
2. Factory Design Pattern
3. Factory vs Abstract Factory Design Pattern
4. Builder Design Pattern
5. Strategy Design Pattern
6. Template Method Design Pattern
7. Chain of Responsiblity Design Pattern
8. Mediator Design Pattern
9. Observer Design Pattern
10. State Design Pattern
11. Momento Design Pattern
12. Proxy Design Pattern
13. Adapter Design Pattern
14. Decorator Design Pattern

### Important Questions to cover
1. Design Parking Lot
2. Design Chess
3. Movie booking system
4. ATM
5. Design Elevator
6. Apply coupon on Shopping Cart
7. LRU cache
8. LFU cache

### Pro tips
1. Use **synchronized** keyword whenever doing CRUD operation for thread safety.
2. Can use **ConcurrentHashmap** for ensuring thread safety.
3. Make System class **Singleton** for insuring single instance.
4. **Volatile:** If a thread writes to a volatile variable, any other thread that subsequently reads that variable is guaranteed to see the latest written value, not a stale cached value. We can use this keyword for a field where we want to make sure about the latest value. When the variable is readed frequently but updated infrequently then we use Volatile keyword for the field.<br>

We can use it for **isRunning** field in Elevator system<br>
```java
class Elevator implements Runnable {

    private volatile boolean isRunning = true;

    // run by thread-1
    @Override
    public void run() {
        while (isRunning) {
            move();
        }
    }

    // run by thread-2
    /* if this executes then thread-1 immediately got to know that the
    value is updated to false which makes run function stops */
    public void stop() {
        isRunning = false;
    }
}
```

5. **Atomic:** for increament/decrement operation or field update operation using synchronized would be heavy operation instead we use atomic classes to make it light weight and more efficient.<br>

**Without thread-safety to update isAvailale in Parking lot system**
```java
if (isAvailable) {
    isAvailable = false;
}
```

If two threads try to access this section of code then it may reserves the same slot instead we can use **synchroized** for thread-safety but it has overhead and inefficient way. Instead we use **Atomic class** which is thread safe and also light weight.<br>

```java
AtmoicBooelan isAvailable = new AtomicBoolean(true);
isAvailable.compareAndSet(true, false);
```
