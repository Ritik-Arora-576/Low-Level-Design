public interface RateLimiter{
    void execute();
    void execute(int tokenExhaust);
}