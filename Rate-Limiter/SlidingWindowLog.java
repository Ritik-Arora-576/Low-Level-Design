import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowLog implements RateLimiter{
    private double capacity;
    private double windowSizeInSeconds;
    private Queue<Instant> logs = new LinkedList<>();

    public SlidingWindowLog(double capacity, double windowSizeInSeconds){
        if(capacity<0) throw new IllegalArgumentException("Max tokens must be greater than 0");
        if(windowSizeInSeconds<0) throw new IllegalArgumentException("Window size must be greater than 0");

        this.capacity = capacity;
        this.windowSizeInSeconds = windowSizeInSeconds;
    }

    public void execute(){
        execute(1); // default tokens utilized
    }

    public synchronized void execute(int tokenExhaust) throws IllegalArgumentException{
        if(tokenExhaust<1) throw new IllegalArgumentException("Token Exhaust must be greater than and equal to 1");

        Instant currentTime = Instant.now();
        
        while(logs.size()>0 && Duration.between(logs.peek(), currentTime).toNanos()/1_000_000_000.0>=windowSizeInSeconds){
            logs.poll();
        }

        if(logs.size() + tokenExhaust > capacity) System.out.println("Tokens are insufficient... Can't process the requet");
        else{
            System.out.println("Request successful");
            for(int i=0;i<tokenExhaust;i++) logs.add(currentTime);
        }
    }
}
