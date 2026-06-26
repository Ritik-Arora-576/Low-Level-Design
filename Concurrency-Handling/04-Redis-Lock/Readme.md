### Redis Lock

If an application is running on multiple servers then Redis Lock which is also a distributed lock ensures that only one server can execute the critical section of code or access the shared resource while other servers must wait in order to handle concurrency. While mutex is responsible to block critical section of code within a single application server.<br>

It is generally use by movie booking application where application is running on multiple instances.<br>

**Mutex (synchronized/ReentrantLock):** One key inside one house (JVM).<br>
**Redis Lock:** One key shared by the entire neighborhood (all application servers).<br>
**Database Row Lock:** The database itself locks the record so no other transaction can modify it until the current transaction finishes.<br>

#### Why Redis is so fast?
Redis is so fast because it is in-memory database and due to its single thread it eliminates synchronization time (context switching among multiple threads), avoid overhead of locks and executes commands squentially.

<img width="1200" height="783" alt="image" src="https://github.com/user-attachments/assets/972c9130-9607-4846-ad55-f5217b83e121" />
