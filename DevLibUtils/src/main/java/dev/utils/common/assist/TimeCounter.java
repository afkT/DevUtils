package dev.utils.common.assist;

/**
 * detail: 时间计时器
 * Created by Ttt
 */
public class TimeCounter {

    private long start;

    public TimeCounter() {
        start();
    }

    /**
     * 开始计时
     */
    public long start() {
        start = System.currentTimeMillis();
        return start;
    }

    /**
     * 获取持续的时间并重新启动。
     * @return
     */
    public long durationRestart() {
        long now = System.currentTimeMillis();
        long d = now - start;
        start = now;
        return d;
    }

    /**
     * 获取持续的时间
     * @return
     */
    public long duration() {
        return System.currentTimeMillis() - start;
    }

    /**
     * 获取开始时间
     * @return
     */
    public long getStartTime(){
        return start;
    }
}