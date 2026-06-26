## Optimistic and Pessimistic Locking

### Pessimistic Locking
In Pessimistic locking, we assume that conflicts are common and in order to avoid conflicts the transaction locks the row/resource first then update the row.<br>

**Real-life Analogy:** While booking a movie ticket when we select the seats and proceed for booking then before payment it will lock those seats and lock will get release when user is done with the payment.<br>

**Pros:** Better technique to avoid conflicts<br>
**Cons:** Reduce throughput of the system and may results into deadlocks if not handled properly<br>

```sql
SELECT * FROM seats
WHERE seat_id = 101
FOR UPDATE; -> locks the row
```
