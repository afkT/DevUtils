package dev.utils.app.timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: 定时器管理类
 * @author Ttt
 */
public final class TimerManager {

    private TimerManager() {
    }

    // 日志 TAG
    private static final String TAG = TimerManager.class.getSimpleName();

    // 内部保存定时器对象 ( 统一管理 )
    protected static final List<DevTimer> mTimerLists = Collections.synchronizedList(new ArrayList<>());

    /**
     * 添加包含校验
     * @param timer 定时器
     */
    protected static void addContainsChecker(final DevTimer timer) {
        synchronized (mTimerLists) {
            if (!mTimerLists.contains(timer)) {
                mTimerLists.add(timer);
            }
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取全部定时器总数
     * @return 全部定时器总数
     */
    public static int getSize() {
        return mTimerLists.size();
    }

    /**
     * 回收定时器资源
     */
    public static void recycle() {
        synchronized (mTimerLists) {
            try {
                Iterator<DevTimer> iterator = mTimerLists.iterator();
                while (iterator.hasNext()) {
                    DevTimer timer = iterator.next();
                    if (timer == null || timer.isMarkSweep()) {
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "recycle");
            }
        }
    }

    // ===========
    // = 获取定时器 =
    // ===========

    /**
     * 获取对应 TAG 定时器 ( 优先获取符合的 )
     * @param tag 判断 {@link DevTimer#getTag()}
     * @return {@link DevTimer}
     */
    public static DevTimer getTimer(final String tag) {
        if (tag != null) {
            synchronized (mTimerLists) {
                try {
                    for (DevTimer timer : mTimerLists) {
                        if (timer != null && tag.equals(timer.getTag())) {
                            return timer;
                        }
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getTimer");
                }
            }
        }
        return null;
    }

    /**
     * 获取对应 UUID 定时器 ( 优先获取符合的 )
     * @param uuid 判断 {@link DevTimer#getTag()}
     * @return {@link DevTimer}
     */
    public static DevTimer getTimer(final int uuid) {
        synchronized (mTimerLists) {
            try {
                for (DevTimer timer : mTimerLists) {
                    if (timer != null && uuid == timer.getUUID()) {
                        return timer;
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getTimer");
            }
        }
        return null;
    }

    // =

    /**
     * 获取对应 TAG 定时器集合
     * @param tag 判断 {@link DevTimer#getTag()}
     * @return 定时器集合
     */
    public static List<DevTimer> getTimers(final String tag) {
        List<DevTimer> lists = new ArrayList<>();
        if (tag != null) {
            synchronized (mTimerLists) {
                try {
                    for (DevTimer timer : mTimerLists) {
                        if (timer != null && tag.equals(timer.getTag())) {
                            lists.add(timer);
                        }
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getTimers");
                }
            }
        }
        return lists;
    }

    /**
     * 获取对应 UUID 定时器集合
     * @param uuid 判断 {@link DevTimer#getTag()}
     * @return 定时器集合
     */
    public static List<DevTimer> getTimers(final int uuid) {
        List<DevTimer> lists = new ArrayList<>();
        synchronized (mTimerLists) {
            try {
                for (DevTimer timer : mTimerLists) {
                    if (timer != null && uuid == timer.getUUID()) {
                        lists.add(timer);
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getTimers");
            }
        }
        return lists;
    }

    // ============
    // = 关闭定时器 =
    // ============

    /**
     * 关闭全部定时器
     */
    public static void closeAll() {
        synchronized (mTimerLists) {
            try {
                Iterator<DevTimer> iterator = mTimerLists.iterator();
                while (iterator.hasNext()) {
                    DevTimer timer = iterator.next();
                    if (timer != null) {
                        timer.stop();
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeAll");
            }
        }
    }

    /**
     * 关闭所有未运行的定时器
     */
    public static void closeAllNotRunning() {
        synchronized (mTimerLists) {
            try {
                Iterator<DevTimer> iterator = mTimerLists.iterator();
                while (iterator.hasNext()) {
                    DevTimer timer = iterator.next();
                    if (timer != null && !timer.isRunning()) {
                        timer.stop();
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeAllNotRunning");
            }
        }
    }

    /**
     * 关闭所有无限循环的定时器
     */
    public static void closeAllInfinite() {
        synchronized (mTimerLists) {
            try {
                Iterator<DevTimer> iterator = mTimerLists.iterator();
                while (iterator.hasNext()) {
                    DevTimer timer = iterator.next();
                    if (timer != null && timer.isInfinite()) {
                        timer.stop();
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeAllInfinite");
            }
        }
    }

    /**
     * 关闭所有对应 TAG 定时器
     * @param tag 判断 {@link DevTimer#getTag()}
     */
    public static void closeAllTag(final String tag) {
        if (tag != null) {
            synchronized (mTimerLists) {
                try {
                    Iterator<DevTimer> iterator = mTimerLists.iterator();
                    while (iterator.hasNext()) {
                        DevTimer timer = iterator.next();
                        if (timer != null && tag.equals(timer.getTag())) {
                            timer.stop();
                            iterator.remove();
                        }
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "closeAllTag");
                }
            }
        }
    }

    /**
     * 关闭所有对应 UUID 定时器
     * @param uuid 判断 {@link DevTimer#getUUID()}
     */
    public static void closeAllUUID(final int uuid) {
        synchronized (mTimerLists) {
            try {
                Iterator<DevTimer> iterator = mTimerLists.iterator();
                while (iterator.hasNext()) {
                    DevTimer timer = iterator.next();
                    if (timer != null && uuid == timer.getUUID()) {
                        timer.stop();
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeAllUUID");
            }
        }
    }

    // ============
    // = 定时器操作 =
    // ============

    /**
     * 运行定时器
     * @param timer 定时器
     */
    public static void startTimer(final DevTimer timer) {
        if (timer != null) timer.start();
    }

    /**
     * 关闭定时器
     * @param timer 定时器
     */
    public static void stopTimer(final DevTimer timer) {
        if (timer != null) timer.stop();
    }
}