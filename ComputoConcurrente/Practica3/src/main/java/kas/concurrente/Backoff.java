package kas.concurrente;

import java.util.Random;

public class Backoff {

    final int minDelay, maxDelay;
    int limit;
    final Random random;

    public Backoff(int min, int max) {
        this.minDelay = min;
        this.maxDelay = max;
        this.limit = this.minDelay;
        this.random = new Random();
    }

    public int getMinDelay() {
        return minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Random getRandom() {
        return random;
    }

    public void backoff() throws InterruptedException {
        int delay = random.nextInt(limit);
        limit = Math.min(maxDelay, 2 * limit);
        Thread.sleep(delay);
    }

    @Override
    public String toString() {
        String s = String.format("Backoff [minDelay= %s maxDelay= %s limit= %s random= %s]", minDelay, maxDelay, limit, random);
        return s;
    }
}
