## Mutex
Mutex (mutual exclusion) is used when multiple threads are trying to access/update the same resource and you want only one thread to enter within a critical section.

Most common mutex mechanisms are:<br>
**1. synchronized** <br>
**2. ReentrantLock** 

### When to use?
When multiple threads are trying to access same resource within a single server and shared memory. It can't handle concurrency for multiple servers within distributed system.

### Real life Analogy:
Lets say there are two persons and only one person can access the room at once. If one person enters into the room then other person must wait until the person which is inside the room left.

### synchronized vs ReentrantLock
1. **synchronized** is simpler to implement as compare to **ReentrantLock**
2. In **synchronized** locks acquired and release automatically while in **ReentrantLock** it would be done manually.
3. In **synchronized** threads could wait for indefinite time while in **ReentrantLock** we can set timeout if thread doesn't get acruired then it will move forward and skip the critical section.
4. In **synchronized** locks newer threads may acquire lock first (no fairness garaunteed)
