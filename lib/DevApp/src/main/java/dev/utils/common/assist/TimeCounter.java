package dev.utils.common.assist;

/**
 * detail: 时间计时辅助类
 * @author Ttt
 */
public class TimeCounter {

    // 开始时间
    private long mStart;

    public TimeCounter() {
        this(true);
    }

    /**
     * 构造函数
     * @param isStart 是否开始计时
     */
    public TimeCounter(final boolean isStart) {
        if (isStart) start();
    }

    /**
     * 开始计时 ( 毫秒 )
     * @return 开始时间 ( 毫秒 )
     */
    public long start() {
        mStart = System.currentTimeMillis();
        return mStart;
    }

    /**
     * 获取持续的时间并重新启动 ( 毫秒 )
     * @return 距离上次开始时间的时间差 ( 毫秒 )
     */
    public long durationRestart() {
        long now  = System.currentTimeMillis();
        long diff = now - mStart;
        mStart = now;
        return diff;
    }

    /**
     * 获取持续的时间 ( 毫秒 )
     * @return 距离开始时间的时间差 ( 毫秒 )
     */
    public long duration() {
        return System.currentTimeMillis() - mStart;
    }

    /**
     * 获取开始时间 ( 毫秒 )
     * @return 开始时间 ( 毫秒 )
     */
    public long getStartTime() {
        return mStart;
    }
}