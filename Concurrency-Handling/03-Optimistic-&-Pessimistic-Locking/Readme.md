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

### Optimistic Locking
In Optimistic locking, we assume that conflicts are rare and instead of locking the row we verify at the time of update either the row is updated or not using version or timestamp.<br>

**Real-life Analogy:** Google Docs editing. You open a document at version 10. Someone updates it to version 11. When you save your changes, system says: Document has changed. Refresh first.<br>

It is use for read heavy application to get a high throughput.

**User-A (Seat status updated as version was 5)**
```sql
UPDATE seats
SET status='BOOKED',
    version=6
WHERE id=10
AND version=5;
```

Now the version is 6 and User-B executes the same command but the status is updated now and status is set to BOOKED which means the transaction is denied.
