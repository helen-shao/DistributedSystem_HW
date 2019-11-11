package Metrics;

public class RuntimeStatistics {

    private static long totalLatency = 0;
    private static long count = 0;
    private static long max = 0;

    public synchronized void updateStats(long latency) {
        totalLatency += latency;
        count += 1;
        max = Math.max(max, latency);
    }

    public synchronized long getMax() {
        return max;
    }
    public synchronized double getMean() {
        return (double) totalLatency/ (double)count;
    }
}
