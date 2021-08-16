package dev.utils.app.timer;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * detail: 定时器
 * @author Ttt
 */
public class DevTimer {

    // 定时器 Tag
    private final String mTag;
    // 延迟时间 ( 多少毫秒后开始执行 )
    private final long   mDelay;
    // 循环时间 ( 每隔多少毫秒执行一次 )
    private final long   mPeriod;
    // 触发次数上限 ( 负数为无限循环 )
    private final int    mTriggerLimit;

    private DevTimer(final Builder builder) {
        mTag          = builder.tag;
        mDelay        = builder.delay;
        mPeriod       = builder.period;
        mTriggerLimit = builder.limit;
    }

    /**
     * detail: 定时器构建类
     * @author Ttt
     */
    public static final class Builder {

        // 定时器 Tag
        private String tag;
        // 延迟时间 ( 多少毫秒后开始执行 )
        private long   delay;
        // 循环时间 ( 每隔多少毫秒执行一次 )
        private long   period;
        // 触发次数上限 ( 负数为无限循环 )
        private int    limit = -1;

        public Builder(long period) {
            this.period = period;
        }

        public Builder(
                long delay,
                long period
        ) {
            this.delay  = delay;
            this.period = period;
        }

        public Builder(
                long delay,
                long period,
                int limit
        ) {
            this.delay  = delay;
            this.period = period;
            this.limit  = limit;
        }

        public Builder(
                long delay,
                long period,
                int limit,
                String tag
        ) {
            this.delay  = delay;
            this.period = period;
            this.limit  = limit;
            this.tag    = tag;
        }

        public Builder(Builder builder) {
            if (builder != null) {
                tag    = builder.tag;
                delay  = builder.delay;
                period = builder.period;
                limit  = builder.limit;
            }
        }

        public String getTag() {
            return tag;
        }

        public Builder setTag(final String tag) {
            this.tag = tag;
            return this;
        }

        public long getDelay() {
            return delay;
        }

        public Builder setDelay(final long delay) {
            this.delay = delay;
            return this;
        }

        public long getPeriod() {
            return period;
        }

        public Builder setPeriod(final long period) {
            this.period = period;
            return this;
        }

        public int getLimit() {
            return limit;
        }

        public Builder setLimit(final int limit) {
            this.limit = limit;
            return this;
        }

        public DevTimer build() {
            return new DevTimer(this);
        }
    }

    /**
     * detail: 回调接口
     * @author Ttt
     */
    public interface Callback {

        /**
         * 触发回调方法
         * @param timer    定时器
         * @param number   触发次数
         * @param end      是否结束
         * @param infinite 是否无限循环
         */
        void callback(
                DevTimer timer,
                int number,
                boolean end,
                boolean infinite
        );
    }

    // =============
    // = 对外公开方法 =
    // =============

    // uuid ( 一定程度上唯一 )
    private final int           mUUID          = UUID.randomUUID().hashCode();
    // 触发次数
    private final AtomicInteger mTriggerNumber = new AtomicInteger();
    // 定时器是否运行中
    private       boolean       mRunning;
    // 状态标识 ( 是否标记清除 )
    private       boolean       mMarkSweep;
    // UI Handler
    private       Handler       mHandler;
    // 回调方法
    private       Callback      mCallback;
    // 定时器
    private       Timer         mTimer;
    // 定时器任务
    private       TimerTask     mTimerTask;

    /**
     * 获取 TAG
     * @return TAG
     */
    public String getTag() {
        return mTag;
    }

    /**
     * 获取 UUID HashCode
     * @return UUID HashCode
     */
    public int getUUID() {
        return mUUID;
    }

    /**
     * 获取延迟时间 ( 多少毫秒后开始执行 )
     * @return 延迟时间 ( 多少毫秒后开始执行 )
     */
    public long getDelay() {
        return mDelay;
    }

    /**
     * 获取循环时间 ( 每隔多少毫秒执行一次 )
     * @return 循环时间 ( 每隔多少毫秒执行一次 )
     */
    public long getPeriod() {
        return mPeriod;
    }

    // =

    /**
     * 判断是否运行中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRunning() {
        return mRunning;
    }

    /**
     * 是否标记清除
     * @return {@code true} yes, {@code false} no
     */
    public boolean isMarkSweep() {
        return mMarkSweep;
    }

    /**
     * 获取已经触发的次数
     * @return 已经触发的次数
     */
    public int getTriggerNumber() {
        return mTriggerNumber.get();
    }

    /**
     * 获取允许触发的上限次数
     * @return 允许触发的上限次数
     */
    public int getTriggerLimit() {
        return mTriggerLimit;
    }

    /**
     * 是否触发结束 ( 到达最大次数 )
     * <pre>
     *     如果属于无限循环, 该方法永远返回 false, 可用 {@link #isRunning()} 组合判断
     * </pre>
     * @return {@code true} yes, {@code false} no
     */
    public boolean isTriggerEnd() {
        return (mTriggerLimit >= 0 && mTriggerNumber.get() >= mTriggerLimit);
    }

    /**
     * 是否无限循环
     * @return {@code true} yes, {@code false} no
     */
    public boolean isInfinite() {
        return (mTriggerLimit <= -1);
    }

    /**
     * 设置 UI Handler
     * <pre>
     *     如果没设置 Handler 回调方法则属于非 UI 线程
     * </pre>
     * @param handler {@link Handler}
     * @return {@link DevTimer}
     */
    public DevTimer setHandler(final Handler handler) {
        mHandler = handler;
        return this;
    }

    /**
     * 设置回调事件
     * @param callback {@link Callback}
     * @return {@link DevTimer}
     */
    public DevTimer setCallback(final Callback callback) {
        mCallback = callback;
        return this;
    }

    // ==========
    // = 核心方法 =
    // ==========

    /**
     * 运行定时器
     * @return {@link DevTimer}
     */
    public DevTimer start() {
        // 标记状态 ( 不需要回收 )
        mMarkSweep = false;
        TimerManager.addContainsChecker(this);
        return startTimer();
    }

    /**
     * 关闭定时器
     * @return {@link DevTimer}
     */
    public DevTimer stop() {
        // 标记状态 ( 需要回收 )
        mMarkSweep = true;
        return cancelTimer();
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 执行定时器
     * @return {@link DevTimer}
     */
    private DevTimer startTimer() {
        // 先关闭旧的定时器
        cancelTimer();
        // 表示运行定时器中
        mRunning = true;
        // 每次重置触发次数
        mTriggerNumber.set(0);
        // 开启定时器
        mTimer = new Timer(); // 每次重新 new 防止被取消
        // 重新生成定时器, 防止出现 TimerTask is scheduled already 同一个定时器只能被放置一次
        mTimerTask = new java.util.TimerTask() {
            @Override
            public void run() {
                // 表示运行定时器中
                mRunning = true;
                // 累计触发次数
                int _number = mTriggerNumber.incrementAndGet();
                // 是否结束
                boolean _end = isTriggerEnd();
                // 是否无限循环
                boolean _infinite = isInfinite();
                // 关闭定时器, 进行标记需要回收
                if (_end) stop();

                if (mCallback != null) {
                    // 判断是否 UI 线程通知
                    if (mHandler != null) {
                        mHandler.post(() -> mCallback.callback(
                                DevTimer.this, _number, _end, _infinite));
                    } else {
                        mCallback.callback(DevTimer.this, _number, _end, _infinite);
                    }
                }
            }
        };
        try {
            // xx 毫秒后执行, 每隔 xx 毫秒再执行一次
            mTimer.schedule(mTimerTask, mDelay, mPeriod);
        } catch (Exception e) {
            // 表示非运行定时器中
            mRunning = false;
            // 关闭定时器, 进行标记需要回收
            stop(); // 启动失败, 则进行标记需要回收
        }
        return this;
    }

    /**
     * 取消定时器
     * @return {@link DevTimer}
     */
    private DevTimer cancelTimer() {
        // 表示非运行定时器中
        mRunning = false;
        try {
            // 取消定时器
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            if (mTimerTask != null) {
                mTimerTask.cancel();
                mTimerTask = null;
            }
        } catch (Exception ignored) {
        }
        return this;
    }
}