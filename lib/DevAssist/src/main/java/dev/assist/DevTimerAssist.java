package dev.assist;

import android.os.Handler;

import java.util.concurrent.atomic.AtomicLong;

import dev.utils.app.timer.DevTimer;

/**
 * detail: 定时器辅助类
 * @author Ttt
 * <pre>
 *     在 {@link DevTimer} 基础上实现, 经过指定时长后进行通知处理
 * </pre>
 */
public class DevTimerAssist {

    // 总时长 ( 毫秒 )
    private final AtomicLong mDuration = new AtomicLong();
    // 循环时间 ( 每隔多少毫秒执行一次 )
    private final long       mPeriod;
    // 定时器 TAG
    private       String     mTag;
    // UI Handler
    private       Handler    mHandler;
    // 回调方法
    private       Callback   mCallback;
    // 当前定时器
    private       DevTimer   mTimer;

    /**
     * 构造函数
     * @param duration 总时长
     */
    public DevTimerAssist(long duration) {
        this(duration, 100L);
    }

    /**
     * 构造函数
     * @param duration 总时长
     * @param period   循环时间 ( 每隔多少毫秒执行一次 )
     */
    public DevTimerAssist(
            long duration,
            long period
    ) {
        this.mDuration.set(duration);
        this.mPeriod = period;
    }

    /**
     * detail: 回调接口
     * @author Ttt
     */
    public interface Callback {

        /**
         * 触发回调方法
         * @param assist   定时器辅助类
         * @param number   触发次数
         * @param end      是否结束
         * @param duration 剩余总时长 ( 毫秒 )
         */
        void callback(
                DevTimerAssist assist,
                int number,
                boolean end,
                long duration
        );
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 设置 TAG
     * @param tag TAG
     * @return {@link DevTimerAssist}
     */
    public DevTimerAssist setTag(final String tag) {
        if (mTimer == null) {
            mTag = tag;
        }
        return this;
    }

    /**
     * 设置 UI Handler
     * <pre>
     *     如果没设置 Handler 回调方法则属于非 UI 线程
     * </pre>
     * @param handler {@link Handler}
     * @return {@link DevTimerAssist}
     */
    public DevTimerAssist setHandler(final Handler handler) {
        if (mTimer == null) {
            mHandler = handler;
        }
        return this;
    }

    /**
     * 设置回调事件
     * @param callback {@link Callback}
     * @return {@link DevTimerAssist}
     */
    public DevTimerAssist setCallback(final Callback callback) {
        if (mTimer == null) {
            mCallback = callback;
        }
        return this;
    }

    /**
     * 获取定时器
     * @return {@link DevTimer}
     */
    public DevTimer getTimer() {
        if (mTimer == null) {
            mTimer = createTimer();
        }
        return mTimer;
    }

    /**
     * 获取剩余总时长 ( 毫秒 )
     * @return 剩余总时长 ( 毫秒 )
     */
    public long getDuration() {
        return mDuration.get();
    }

    // ==========
    // = 核心方法 =
    // ==========

    /**
     * 运行定时器
     * @return {@link DevTimerAssist}
     */
    public DevTimerAssist start() {
        if (isDurationEnd()) return this;
        getTimer().start();
        return this;
    }

    /**
     * 关闭定时器
     * @return {@link DevTimerAssist}
     */
    public DevTimerAssist stop() {
        getTimer().stop();
        return this;
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 内部 Callback
    private final DevTimer.Callback mInnerCallback = new DevTimer.Callback() {
        @Override
        public void callback(
                DevTimer timer,
                int number,
                boolean end,
                boolean infinite
        ) {
            long result = mDuration.addAndGet(-mPeriod);
            if (result <= 0L) {
                timer.stop();
                if (mCallback != null) {
                    mCallback.callback(
                            DevTimerAssist.this, number, true, result
                    );
                }
                return;
            }
            if (mCallback != null) {
                mCallback.callback(
                        DevTimerAssist.this, number, false, result
                );
            }
        }
    };

    /**
     * 创建定时器
     * @return {@link DevTimer}
     */
    private DevTimer createTimer() {
        return new DevTimer.Builder(mPeriod, mPeriod)
                .setTag(mTag).build()
                .setCallback(mInnerCallback)
                .setHandler(mHandler);
    }

    /**
     * 是否总时长已结束
     * @return {@code true} yes, {@code false} no
     */
    private boolean isDurationEnd() {
        long result = mDuration.get();
        if (result <= 0L) {
            getTimer().stop();
            if (mCallback != null) {
                mCallback.callback(this, 0, true, result);
            }
            return true;
        }
        return false;
    }
}