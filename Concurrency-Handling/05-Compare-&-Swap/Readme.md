### Compare & Swap
Compare and Swap (CAS) is an atomic CPU instruction used to update a value only if it hasn't changed since you last read it. It is the foundation of many lock-free algorithms and **optimistic concurrency** mechanisms.<br>

**Real life Analogy**<br>
Lets say there is a value 100 written on whiteboard and you left the room. After a while you said to your friend to update the value on whiteboard to 200 if 100 is written on it (which is a last readed value). When your friend reaches to whiteboard then there are 2 possiblities either 100 is written on whiteboard or not. If it is written then it will get updated to 200 otherwise not.

### Java Example

Lets say first thread changes the value<br>
```java
AtomicInteger counter = new AtomicInteger(5);

// 5 is the last read value and 6 is the new value which we want to update to
boolean success = counter.compareAndSet(5, 6);

System.out.println(success); // true
System.out.println(counter.get()); // 6
```

The value remains unchanged as it is updated updated by another thread as value update to 6
```java
AtomicInteger counter = new AtomicInteger(7);

boolean success = counter.compareAndSet(5, 6);

System.out.println(success); // false because value changes to 6 by another thread
```
