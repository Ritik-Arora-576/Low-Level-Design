import java.time.Duration;
import java.time.Instant;

public class SlidingWindowCounter implements RateLimiter{
    private double capacity;
    private double windowSizeInSeconds;
    private double previousWindowCounter;
    private double currentWindowCounter;
    private Instant windowStartTime;

    public SlidingWindowCounter(double capacity, double windowSizeInSeconds){
        if(capacity<0) throw new IllegalArgumentException("Max tokens must be greater than 0");
        if(windowSizeInSeconds<0) throw new IllegalArgumentException("Window size must be greater than 0");

        this.capacity = capacity;
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.windowStartTime = Instant.now();
        this.currentWindowCounter = 0;
        this.previousWindowCounter = 0;
    }

    public void execute(){
        execute(1); // default tokens utilized
    }

    public synchronized void execute(int tokenExhaust) throws IllegalArgumentException{
        if(tokenExhaust<1) throw new IllegalArgumentException("Token Exhaust must be greater than and equal to 1");

        Instant currentTime = Instant.now();
        double elapsedSeconds = Duration.between(windowStartTime, currentTime).toNanos()/1_000_000_000.0;

        // If we've moved to the next window, shift counters
        if(elapsedSeconds >= windowSizeInSeconds){
            previousWindowCounter = currentWindowCounter;
            currentWindowCounter = 0;
            windowStartTime = currentTime;
            elapsedSeconds = 0;
        }

        // Calculate weighted requests considering the sliding window
        // How much of the previous window overlaps into the current time
        double weightOfPreviousWindow = (windowSizeInSeconds - elapsedSeconds) / windowSizeInSeconds;
        double allowedRequests = capacity - (previousWindowCounter * weightOfPreviousWindow);

        if(allowedRequests < tokenExhaust) {
            System.out.println("Tokens are insufficient... Can't process the requet");
        } else {
            System.out.println("Request successful");
            currentWindowCounter += tokenExhaust;
        }
    }
}
