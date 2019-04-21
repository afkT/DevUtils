package dev.utils.app.assist.manager;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import dev.utils.LogPrintUtils;

/**
 * detail: 定时器工具类
 * @author Ttt
 * <pre>
 *      主要是为了控制整个项目的定时器,防止定时器混乱,或者导致忘记关闭等情况,以及减少初始化等操作代码
 *      主要实现是 AbsTimer、TimerTask 这两个类,
 *      AbsTimer -> 定时器抽象类,对外提供该类对象,以及内部方法,便于内部实现方法的隐藏,以及达到对定时器任务的控制处理
 *      TimerTask -> 内部私有类,实现了具体的定时器操作,以及代码控制等,防止外部直接new,导致定时器混乱
 *      <p></p>
 *      如果外部想要实现定时器,但是通过内部 ArrayList 控制,也可以通过 实现AbsTimer接口,内部的startTimer()、closeTimer() 进行了对AbsTimer的保存，标记等操作
 *      需要注意的是,实现start(close)Timer() 方法,必须保留 super.start(close)Timer(); -> 内部 ArrayList 进行了操作,而不对外开放(不需要主动调用)
 *      <p></p>
 *      startTimer() -> 主要进行添加到 ArrayList, 并且标记不需要回收
 *      closeTimer() -> 不直接操作remove,防止出现ConcurrentModificationException 异常, 而是做一个标记,便于后续回收
 * </pre>
 */
public final class TimerManager {

    private TimerManager() {
    }

    // 日志 TAG
    private static final String TAG = TimerManager.class.getSimpleName();
    // 内部保存定时器对象,防止忘记关闭等其他情况,以及便于控制处理
    private static final List<AbsTimer> listAbsTimers = new ArrayList<>();

    // ============================
    // = ArrayList 对外公开的方法 =
    // ============================

    /**
     * 回收资源
     */
    public static void gc() {
        synchronized (listAbsTimers) {
            // 临时数据源
            List<AbsTimer> lists = new ArrayList<>(listAbsTimers);
            // 清空旧的数据
            listAbsTimers.clear();
            // 开始删除无用资源
            Iterator<AbsTimer> iterator = lists.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().markSweep) { // 需要回收,则进行回收
                    iterator.remove();
                }
            }
            // 把不需要回收的保存回去
            listAbsTimers.addAll(lists);
            // 移除旧的
            lists.clear();
            lists = null;
        }
    }

    /**
     * 获取全部任务总数
     * @return 全部任务总数
     */
    public static int timerSize() {
        return listAbsTimers.size();
    }

    /**
     * 获取属于对应字符串标记的定时器任务(优先获取符合的)
     * @param markStr
     * @return 获取对应标记的定时任务 {@link AbsTimer} 对象
     */
    public static AbsTimer getTimer(final String markStr) {
        if (markStr == null) return null;
        try {
            for (int i = 0, size = listAbsTimers.size(); i < size; i++) {
                AbsTimer absTimer = listAbsTimers.get(i);
                // 判断是否符合标记 , 原本标记不为null,并且符合条件的
                if (!TextUtils.isEmpty(absTimer.getMarkStr()) && absTimer.getMarkStr().equals(markStr)) {
                    return absTimer;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getTimer");
        }
        return null;
    }

    /**
     * 获取属于标记id的定时器任务(优先获取符合的)
     * @param markId
     * @return 获取对应标记的定时任务 {@link AbsTimer} 对象
     */
    public static AbsTimer getTimer(final int markId) {
        try {
            for (int i = 0, size = listAbsTimers.size(); i < size; i++) {
                AbsTimer absTimer = listAbsTimers.get(i);
                // 判断是否符合标记 , 原本标记不为null,并且符合条件的
                if (absTimer.getMarkId() == markId) {
                    return absTimer;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getTimer");
        }
        return null;
    }

    /**
     * 关闭全部任务
     */
    public static void closeAll() {
        try {
            for (int i = 0, size = listAbsTimers.size(); i < size; i++) {
                listAbsTimers.get(i).closeTimer(); // 关闭定时器
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeAll");
        }
    }

    /**
     * 关闭所有未运行的任务
     */
    public static void closeNotRunTask() {
        try {
            for (int i = 0, size = listAbsTimers.size(); i < size; i++) {
                AbsTimer absTimer = listAbsTimers.get(i);
                // 判断是否运行中
                if (!absTimer.isRunTimer()) {
                    absTimer.closeTimer(); // 关闭定时器
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeNotRunTask");
        }
    }

    /**
     * 关闭所有无限循环的任务
     */
    public static void closeInfiniteTask() {
        try {
            for (int i = 0, size = listAbsTimers.size(); i < size; i++) {
                AbsTimer absTimer = listAbsTimers.get(i);
                // 判断是否无限运行
                if (absTimer.isInfinite()) {
                    absTimer.closeTimer(); // 关闭定时器
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeInfiniteTask");
        }
    }

    /**
     * 关闭所有符合对应的字符串标记的定时器任务
     * @param markStr
     */
    public static void closeMark(final String markStr) {
        if (markStr == null) return;
        try {
            for (int i = 0, size = listAbsTimers.size(); i < size; i++) {
                AbsTimer absTimer = listAbsTimers.get(i);
                // 判断是否符合标记 , 原本标记不为null,并且符合条件的
                if (!TextUtils.isEmpty(absTimer.getMarkStr()) && absTimer.getMarkStr().equals(markStr)) {
                    absTimer.closeTimer(); // 关闭定时器
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeMark");
        }
    }

    /**
     * 关闭所有符合对应的标记id的定时器任务
     * @param markId
     */
    public static void closeMark(final int markId) {
        try {
            for (int i = 0, size = listAbsTimers.size(); i < size; i++) {
                AbsTimer absTimer = listAbsTimers.get(i);
                if (absTimer.getMarkId() == markId) {
                    absTimer.closeTimer(); // 关闭定时器
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeMark");
        }
    }

    // ==========================================================
    // = 对外公开初始化AbsTimer方法(内部控制对 TimerTask的生成) =
    // ==========================================================

    /**
     * 创建定时器 => 立即执行,无限循环,通知默认what
     * @param handler 通知的Handler
     * @param period  循环时间 - 每隔多少秒执行一次
     * @return 定时器抽象对象
     */
    public static AbsTimer createTimer(final Handler handler, final long period) {
        return createTimer(handler, AbsTimer.TIMER_NOTIFY_WHAT, 0l, period, -1);
    }

    /**
     * 创建定时器 => 无限循环,通知默认what
     * @param handler 通知的Handler
     * @param delay   延迟时间 - 多少毫秒后开始执行
     * @param period  循环时间 - 每隔多少秒执行一次
     * @return 定时器抽象对象
     */
    public static AbsTimer createTimer(final Handler handler, final long delay, final long period) {
        return createTimer(handler, AbsTimer.TIMER_NOTIFY_WHAT, delay, period, -1);
    }

    /**
     * 创建定时器 => 立即执行,通知默认what
     * @param handler      通知的Handler
     * @param period       循环时间 - 每隔多少秒执行一次
     * @param triggerLimit 触发次数上限(-1,表示无限循环)
     * @return 定时器抽象对象
     */
    public static AbsTimer createTimer(final Handler handler, final long period, final int triggerLimit) {
        return createTimer(handler, AbsTimer.TIMER_NOTIFY_WHAT, 0l, period, triggerLimit);
    }

    /**
     * 创建定时器 => 立即执行,无限循环
     * @param handler 通知的Handler
     * @param what    通知的what
     * @param period  循环时间 - 每隔多少秒执行一次
     * @return 定时器抽象对象
     */
    public static AbsTimer createTimer(final Handler handler, final int what, final long period) {
        return createTimer(handler, what, 0l, period, -1);
    }

    /**
     * 创建定时器 => 无限循环
     * @param handler 通知的Handler
     * @param what    通知的what
     * @param delay   延迟时间 - 多少毫秒后开始执行
     * @param period  循环时间 - 每隔多少秒执行一次
     * @return 定时器抽象对象
     */
    public static AbsTimer createTimer(final Handler handler, final int what, final long delay, final long period) {
        return createTimer(handler, what, delay, period, -1);
    }

    /**
     * 创建定时器 => 立即执行
     * @param handler      通知的Handler
     * @param what         通知的what
     * @param period       循环时间 - 每隔多少秒执行一次
     * @param triggerLimit 触发次数上限(-1,表示无限循环)
     * @return 定时器抽象对象
     */
    public static AbsTimer createTimer(final Handler handler, final int what, final long period, final int triggerLimit) {
        return createTimer(handler, what, 0l, period, triggerLimit);
    }

    /**
     * 创建定时器
     * @param handler      通知的Handler
     * @param what         通知的what
     * @param delay        延迟时间 - 多少毫秒后开始执行
     * @param period       循环时间 - 每隔多少秒执行一次
     * @param triggerLimit 触发次数上限(-1,表示无限循环)
     * @return 定时器抽象对象
     */
    public static AbsTimer createTimer(final Handler handler, final int what, final long delay, final long period, final int triggerLimit) {
        return new TimerTask(handler, what, delay, period, triggerLimit);
    }

    // =====================================
    // = 定时器抽象类,开放对定时器操作方法 =
    // =====================================

    /**
     * detail: 定时器抽象类,主要对内部Timer参数进行控制,以及防止外部直接new TimerTask, 照成不必要的失误
     * @author Ttt
     * <pre>
     *      @TODO 推荐使用 {@link TimerManager#createTimer} 创建定时任务, 如果需要自己实现 AbsTimer, 则参考 {@link TimerManager.TimerTask} 实现
     *      @TODO {@link TimerManager.AbsTimer} 只是提供了常见的方法, 以及变量等, 便于定时任务控制, 具体实现在 {@link TimerManager.TimerTask}
     * </pre>
     */
    public static abstract class AbsTimer {

        // 默认通知的what
        public static final int TIMER_NOTIFY_WHAT = 50000;
        // 状态标识 - 是否标记清除
        private boolean markSweep = true;
        // int 标记
        private int markId = -1;
        // String 标记
        private String markStr = null;

        /**
         * 获取标记id
         * @return
         */
        public final int getMarkId() {
            return markId;
        }

        /**
         * 获取标记字符串
         * @return
         */
        public final String getMarkStr() {
            return markStr;
        }

        /**
         * 设置标记id
         * @param markId
         * @return 定时器抽象对象
         */
        public final AbsTimer setMarkId(final int markId) {
            this.markId = markId;
            return this;
        }

        /**
         * 设置标记字符串
         * @param markStr
         * @return 定时器抽象对象
         */
        public final AbsTimer setMarkStr(final String markStr) {
            this.markStr = markStr;
            return this;
        }

        // ===========================
        // = 定时器任务,功能实现方法 =
        // ===========================

        /**
         * 运行定时器
         */
        public void startTimer() { // 如果外部通过了createTimer或者直接new AbsTimer 初始化了对象，没有调用startTimer,都不会保存到 listAbsTimers 并不影响对定时器的控制
            //  标记状态 - 不需要回收
            this.markSweep = false;
            synchronized (listAbsTimers) {
                // 不存在才进行添加
                if (!listAbsTimers.contains(this)) {
                    listAbsTimers.add(this);
                }
            }
        }

        /**
         * 关闭定时器
         */
        public void closeTimer() {
            //  标记状态 - 需要回收
            this.markSweep = true;
        }

        /**
         * 判断是否运行中
         */
        public abstract boolean isRunTimer();

        /**
         * 获取已经触发的次数
         */
        public abstract int getTriggerNumber();

        /**
         * 获取允许触发的上限次数
         */
        public abstract int getTriggerLimit();

        /**
         * 是否触发结束(到达最大次数)
         */
        public abstract boolean isTriggerEnd();

        /**
         * 是否无限循环
         */
        public abstract boolean isInfinite();

        /**
         * 设置通知的Handler
         * @param handler
         * @return 定时器抽象对象
         */
        public abstract AbsTimer setHandler(Handler handler);

        /**
         * 设置通知的What
         * @param notifyWhat
         * @return 定时器抽象对象
         */
        public abstract AbsTimer setNotifyWhat(int notifyWhat);

        /**
         * 设置通知的Obj
         * @param notifyObj
         * @return 定时器抽象对象
         */
        public abstract AbsTimer setNotifyObject(Object notifyObj);

        /**
         * 设置时间
         * @param delay  延迟时间 - 多少毫秒后开始执行
         * @param period 循环时间 - 每隔多少秒执行一次
         * @return 定时器抽象对象
         */
        public abstract AbsTimer setTime(long delay, long period);

        /**
         * 设置触发次数上限
         * @param triggerLimit
         * @return 定时器抽象对象
         */
        public abstract AbsTimer setTriggerLimit(int triggerLimit);
    }

    // =================================
    // = 定时器 具体实现类(不对外开放) =
    // =================================

    /**
     * detail: 定时器内部封装类 - 定时器任务类
     * @author Ttt
     * <pre>
     *      便于快捷使用, 并且防止外部new, 从而达到对整个项目定时器的控制
     * </pre>
     */
    private static final class TimerTask extends AbsTimer {

        // 定时器
        private Timer timer;
        // 定时器任务栈
        private java.util.TimerTask timerTask;
        // 通知Handler
        private Handler handler;
        // 通知的数据
        private Object notifyObj = null;
        // 通知类型
        private int notifyWhat = AbsTimer.TIMER_NOTIFY_WHAT;
        // 延迟时间 - 多少毫秒后开始执行
        private long delay;
        // 循环时间 - 每隔多少秒执行一次
        private long period;
        // 触发次数上限
        private int triggerLimit = 1;
        // 触发次数
        private int triggerNumber = 0;
        // 定时器是否运行中
        private boolean running = false;

        public TimerTask(final Handler handler, final int what, final long delay, final long period, final int triggerLimit) {
            this.handler = handler;
            this.notifyWhat = what;
            this.delay = delay;
            this.period = period;
            this.triggerLimit = triggerLimit;
        }

        /**
         * 开始定时器任务
         */
        private void start() {
            // 先关闭旧的定时器
            close();
            // 表示运行定时器中
            running = true;
            // 每次重置触发次数
            triggerNumber = 0;
            // 开启定时器
            timer = new Timer(); // 每次重新new 防止被取消
            // 重新生成定时器 防止出现TimerTask is scheduled already 所以同一个定时器任务只能被放置一次
            timerTask = new java.util.TimerTask() {
                @Override
                public void run() {
                    // 表示运行定时器中
                    running = true;
                    // 累积触发次数
                    triggerNumber++;
                    // 进行通知
                    if (handler != null) {
                        // 从Message池中返回一个新的Message实例 - 通知what, arg1 = 触发次数, arg2 = 触发上限, obj = notifyObj
                        Message msg = handler.obtainMessage(notifyWhat, triggerNumber, triggerLimit, notifyObj);
                        handler.sendMessage(msg);
                    }
                    // 如果大于触发次数,则关闭
                    if (triggerLimit >= 0 && triggerNumber >= triggerLimit) {
                        // 关闭任务,进行标记需要回收
                        closeTimer();
                    }
                }
            };
            try {
                // xx毫秒后执行,每隔xx毫秒再执行一次
                timer.schedule(timerTask, delay, period);
            } catch (Exception e) {
                // 表示非运行定时器中
                running = false;
                // 关闭任务,进行标记需要回收
                closeTimer(); // 启动失败,则进行标记,标记需要回收
            }
        }

        /**
         * 关闭定时器任务
         */
        private void close() {
            // 表示非运行定时器中
            running = false;
            // 取消定时器任务
            try {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (timerTask != null) {
                    timerTask.cancel();
                    timerTask = null;
                }
            } catch (Exception e) {
            }
        }

        // ==================
        // = 实现抽象类方法 =
        // ==================

        @Override
        public void startTimer() {
            super.startTimer(); // 必须保留这句话
            // 开始定时器任务
            start();
        }

        @Override
        public void closeTimer() {
            super.closeTimer(); // 必须保留这句话
            // 关闭定时器任务
            close();
        }

        @Override
        public boolean isRunTimer() {
            return running;
        }

        @Override
        public int getTriggerNumber() {
            return triggerNumber;
        }

        @Override
        public int getTriggerLimit() {
            return triggerLimit;
        }

        @Override
        public boolean isTriggerEnd() { // 如果为无限触发,则会返回true ,因为触发次数大于 -1
            return (triggerNumber >= triggerLimit);
            //return (triggerLimit >= 0 && triggerNumber >= triggerLimit);
        }

        @Override
        public boolean isInfinite() {
            return (triggerLimit <= -1);
        }

        @Override
        public AbsTimer setHandler(final Handler handler) {
            this.handler = handler;
            return this;
        }

        @Override
        public AbsTimer setNotifyWhat(final int notifyWhat) {
            this.notifyWhat = notifyWhat;
            return this;
        }

        @Override
        public AbsTimer setNotifyObject(final Object notifyObj) {
            this.notifyObj = notifyObj;
            return this;
        }

        @Override
        public AbsTimer setTime(final long delay, final long period) {
            this.delay = delay;
            this.period = period;
            return this;
        }

        @Override
        public AbsTimer setTriggerLimit(final int triggerLimit) {
            this.triggerLimit = triggerLimit;
            return this;
        }
    }
}
