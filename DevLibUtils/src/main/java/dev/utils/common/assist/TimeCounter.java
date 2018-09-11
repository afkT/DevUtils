package dev.utils.common.assist;

/**
 * detail: 时间计时器
 * @author MaTianyu
 */
public class TimeCounter {

    private long t;

    public TimeCounter() {
        start();
    }

    /**
     * Count start.
     */
    public long start() {
        t = System.currentTimeMillis();
        return t;
    }

    /**
     * Get duration and restart.
     */
    public long durationRestart() {
        long now = System.currentTimeMillis();
        long d = now - t;
        t = now;
        return d;
    }

    /**
     * Get duration.
     */
    public long duration() {
        return System.currentTimeMillis() - t;
    }
}