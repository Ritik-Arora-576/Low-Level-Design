import java.time.Duration;
import java.time.Instant;

public class FixedWindowCounter implements RateLimiter{
    private double capacity;
    private double currentTokens;
    private Instant lastStartingWindow;
    private double windowSizeInSeconds;

    public FixedWindowCounter(double capacity, double windowSizeInSeconds){
        if(capacity<0) throw new IllegalArgumentException("Max tokens must be greater than 0");
        if(windowSizeInSeconds<0) throw new IllegalArgumentException("Window size must be greater than 0");

        this.capacity = capacity;
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.lastStartingWindow = Instant.now();
        this.currentTokens = capacity;
    }

    public void execute(){
        execute(1); // default tokens utilized
    }

    public synchronized void execute(int tokenExhaust) throws IllegalArgumentException{
        if(tokenExhaust<1) throw new IllegalArgumentException("Token Exhaust must be greater than and equal to 1");

        Instant currentTime = Instant.now();
        double seconds = Duration.between(lastStartingWindow, currentTime).toNanos()/1_000_000_000.0;
        
        if(seconds>windowSizeInSeconds){
            currentTokens = capacity;
            lastStartingWindow = currentTime;
        }

        if(currentTokens<tokenExhaust) System.out.println("Tokens are insufficient... Can't process the requet");
        else{
            System.out.println("Request successful");
            currentTokens-=tokenExhaust;
        }
    }
}
