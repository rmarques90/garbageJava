import java.util.concurrent.TimeUnit;

/**
 * Created by fox on 03/10/17.
 */
public class Timer {
    long start;
    long stop;

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        stop = System.nanoTime();
    }

    public long getTime() {
        return TimeUnit.SECONDS.convert(stop - start, TimeUnit.NANOSECONDS);
    }
}
