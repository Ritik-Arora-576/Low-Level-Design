### Semaphores
Semaphore is a mechanism which limit the threads to access the resoure.<br>

#### Database Connection
Lets say a database can have maximum 10 connections and 20 requests are trying to connect with database simultaneously. Without semaphore 10 connections will get established successdfully while other 10 connections got failed. With semaphore remaining 10 connection will wait. If 1 connection get disconnected then free connection will be connected by waited threads/connection.<br>

### Rate limiter
Lets say an external API can process 20 requests/minute then if 100 requests come within a minute then 80 requests will get 429 response without semaphore. With semaphore remaining requests will wait in order to process the request.<br>

### Real life analogy
Lets say there is a restaurent which has maximum sitting capacity of 20 people. If 50 people came together then restaurent staff can allow 20 people at a time while other 30 people needs to wait. When tables got empty after few people done with the eating then waiting people will get the sitting in restaurent based on the avaialblity.<br>
**People:** Threads <br>
**Restaurent:** Resource <br>
**Restaurent Staff:** Semaphore (who is allocating and make people wait) <br>

```java
// maximum 10 connections
Semaphore semaphore = new Semaphore(10);

// Decrements the counter. (Wait)
// If the counter becomes negative (or is zero, depending on implementation), the thread blocks until another thread releases the semaphore.
semaphore.acquire(); // Atomically checks and decrements

// Increments the counter. (Signal)
// If any threads are waiting, one of them is awakened.
semaphore.release(); // Atomically increments and wakes a waiting thread
```
