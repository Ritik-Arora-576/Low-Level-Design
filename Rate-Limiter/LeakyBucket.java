import java.time.Duration;
import java.time.Instant;

public class LeakyBucket implements RateLimiter{
    private double capacity;
    private double currentTokens;
    private Instant lastLeakTime;
    private double leakPerSecond;

    public LeakyBucket(double capacity, double leakPerSecond){
        if(capacity<0) throw new IllegalArgumentException("Capacity must be greater than 0");
        if(leakPerSecond<0) throw new IllegalArgumentException("Leak Rate must be greater than 0");

        this.capacity = capacity;
        this.leakPerSecond = leakPerSecond;
        this.lastLeakTime = Instant.now();
        this.currentTokens = 0;
    }

    public void execute(){
        execute(1); // default tokens utilized
    }

    public synchronized void execute(int tokenExhaust) throws IllegalArgumentException{
        if(tokenExhaust<1) throw new IllegalArgumentException("Token Exhaust must be greater than and equal to 1");

        Instant currentTime = Instant.now();
        double seconds = Duration.between(lastLeakTime, currentTime).toNanos()/1_000_000_000.0;
        currentTokens = Math.max(0, currentTokens - seconds*leakPerSecond);
        lastLeakTime = currentTime;

        if(currentTokens + tokenExhaust >capacity) System.out.println("Bandwidth is insufficient... Can't process the requet");
        else{
            System.out.println("Request successful");
            currentTokens+=tokenExhaust;
        }
    }
}
