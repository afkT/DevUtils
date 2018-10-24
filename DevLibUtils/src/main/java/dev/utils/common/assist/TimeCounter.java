package dev.utils.common.assist;

/**
 * detail: 时间计时器
 * Created by MaTianyu
 * Update to Ttt
 */
public class TimeCounter {

    private long t;

    public TimeCounter() {
        start();
    }

    /**
     * 开始计时
     */
    public long start() {
        t = System.currentTimeMillis();
        return t;
    }

    /**
     * 获取持续的时间并重新启动。
     * @return
     */
    public long durationRestart() {
        long now = System.currentTimeMillis();
        long d = now - t;
        t = now;
        return d;
    }

    /**
     * 获取持续的时间
     * @return
     */
    public long duration() {
        return System.currentTimeMillis() - t;
    }
}