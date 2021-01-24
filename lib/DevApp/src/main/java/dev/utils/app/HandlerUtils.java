package dev.utils.app;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.Map;

import dev.utils.LogPrintUtils;

/**
 * detail: Handler 工具类
 * @author Ttt
 */
public final class HandlerUtils {

    private HandlerUtils() {
    }

    // 日志 TAG
    private static final String TAG = HandlerUtils.class.getSimpleName();

    // 主线程 Handler
    private static Handler sMainHandler;

    /**
     * 获取主线程 Handler
     * @return 主线程 Handler
     */
    public static Handler getMainHandler() {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
        return sMainHandler;
    }

    /**
     * 当前线程是否主线程
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     */
    public static void postRunnable(final Runnable runnable) {
        if (runnable != null) {
            getMainHandler().post(runnable);
        }
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     */
    public static void postRunnable(
            final Runnable runnable,
            final long delayMillis
    ) {
        if (runnable != null) {
            getMainHandler().postDelayed(runnable, delayMillis);
        }
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     */
    public static void postRunnable(
            final Runnable runnable,
            final long delayMillis,
            final int number,
            final long interval
    ) {
        postRunnable(runnable, delayMillis, number, interval, null);
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable      可执行的任务
     * @param delayMillis   延迟时间
     * @param number        轮询次数
     * @param interval      轮询时间
     * @param onEndListener 结束通知
     */
    public static void postRunnable(
            final Runnable runnable,
            final long delayMillis,
            final int number,
            final long interval,
            final OnEndListener onEndListener
    ) {
        if (runnable != null) {
            Runnable loop = new Runnable() {
                private int mNumber;

                @Override
                public void run() {
                    if (mNumber < number) {
                        mNumber++;
                        if (runnable != null) {
                            try {
                                runnable.run();
                            } catch (Exception e) {
                            }
                        }
                        // 判断是否超过次数
                        if (mNumber < number) {
                            getMainHandler().postDelayed(this, interval);
                        }
                    }

                    // 判断是否超过次数
                    if (mNumber >= number) {
                        if (onEndListener != null) {
                            onEndListener.onEnd(delayMillis, number, interval);
                        }
                    }
                }
            };
            getMainHandler().postDelayed(loop, delayMillis);
        }
    }

    /**
     * 在主线程 Handler 中清除任务
     * <pre>
     *     也可使用 {@link Handler#removeCallbacksAndMessages(Object)} 实现
     *     注意: 会将所有的 Callbacks、Messages 全部清除掉
     * </pre>
     * @param runnable 需要清除的任务
     */
    public static void removeRunnable(final Runnable runnable) {
        if (runnable != null) {
            getMainHandler().removeCallbacks(runnable);
        }
    }

    // =

    /**
     * detail: 结束回调事件
     * @author Ttt
     */
    public interface OnEndListener {

        /**
         * 结束通知
         * @param delayMillis 延迟时间
         * @param number      轮询次数
         * @param interval    轮询时间
         */
        void onEnd(
                long delayMillis,
                int number,
                long interval
        );
    }

    // ================
    // = Runnable Map =
    // ================

    // 通过 Key 快捷控制 Runnable, 进行 postDelayed、removeCallbacks
    private static final Map<String, Runnable> sRunnableMaps = new HashMap<>();

    /**
     * 获取 Key Runnable Map
     * @return Key Runnable Map
     */
    public static Map<String, Runnable> getRunnableMaps() {
        return new HashMap<>(sRunnableMaps);
    }

    /**
     * 清空 Key Runnable Map
     */
    public static void clearRunnableMaps() {
        sRunnableMaps.clear();
    }

    /**
     * 判断 Map 是否存储 key Runnable
     * @param key key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean containsKey(final String key) {
        return sRunnableMaps.containsKey(key);
    }

    /**
     * 通过 Key 存储 Runnable
     * @param key      key
     * @param runnable 线程任务
     * @return {@code true} success, {@code false} fail
     */
    public static boolean put(
            final String key,
            final Runnable runnable
    ) {
        if (key != null && runnable != null) {
            try {
                sRunnableMaps.put(key, runnable);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put");
            }
        }
        return false;
    }

    /**
     * 通过 Key 移除 Runnable
     * @param key key
     * @return {@code true} success, {@code false} fail
     */
    public static boolean remove(final String key) {
        if (key != null) {
            try {
                sRunnableMaps.remove(key);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "remove");
            }
        }
        return false;
    }

    /**
     * 执行对应 Key Runnable
     * @param key         key
     * @param delayMillis 延迟时间
     */
    public static void postRunnable(
            final String key,
            final long delayMillis
    ) {
        Runnable runnable = sRunnableMaps.get(key);
        if (runnable != null) {
            removeRunnable(runnable);
            postRunnable(runnable, delayMillis);
        }
    }

    /**
     * 清除对应 Key Runnable
     * @param key key
     */
    public static void removeRunnable(final String key) {
        Runnable runnable = sRunnableMaps.get(key);
        if (runnable != null) removeRunnable(runnable);
    }
}