import java.time.Duration;
import java.time.Instant;

public class TokenBucket implements RateLimiter{
    private double capacity;
    private double currentTokens;
    private Instant lastRefillTime;
    private double tokenFillPerSecond;

    public TokenBucket(double capacity, double tokenFillPerSecond){
        if(capacity<0) throw new IllegalArgumentException("Capacity must be greater than 0");
        if(tokenFillPerSecond<0) throw new IllegalArgumentException("Token Filling Rate must be greater than 0");

        this.capacity = capacity;
        this.tokenFillPerSecond = tokenFillPerSecond;
        this.lastRefillTime = Instant.now();
        this.currentTokens = capacity;
    }

    public void execute(){
        execute(1); // default tokens utilized
    }

    public synchronized void execute(int tokenExhaust) throws IllegalArgumentException{
        if(tokenExhaust<1) throw new IllegalArgumentException("Token Exhaust must be greater than and equal to 1");

        Instant currentTime = Instant.now();
        double seconds = Duration.between(lastRefillTime, currentTime).toNanos()/1_000_000_000.0;
        currentTokens = Math.min(capacity, currentTokens + seconds*tokenFillPerSecond);
        lastRefillTime = currentTime;

        if(currentTokens<tokenExhaust) System.out.println("Tokens are insufficient... Can't process the requet");
        else{
            System.out.println("Request successful");
            currentTokens-=tokenExhaust;
        }
    }
}
