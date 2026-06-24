## Database Row Locking
Database row locking is a way to ensure that only one transaction can modify a specific row at a time, even when requests come from multiple application servers.<br>

Lets say there is movie booking application which is running on multiple servers and there is one seat left for a show and multiple transaction(i.e booking requests) coming from different application servers which is targetting same database. In this case, first transaction will lock that row which contains selected seat and other transactions must wait to update the row until the first transaction is completed.

It is generally use in movie booking system, banking systems, inventory updates etc.

### Example:

### User-A:
```sql
BEGIN;

SELECT *
FROM seats
WHERE seat_id = 101
FOR UPDATE;

UPDATE seats
SET status = 'BOOKED'
WHERE seat_id = 101;

COMMIT;
```

### User-B (at a same time)
```sql
BEGIN;

SELECT *
FROM seats
WHERE seat_id = 101
FOR UPDATE;
```

This query blocks and waits. <br>
**After User A commits:** Lock released <br>
**User B proceeds and sees:** Seat already booked -> Booking fails. <br>
