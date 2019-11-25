package dev.utils.app;

import android.os.Handler;
import android.os.Looper;

/**
 * detail: Handler 工具类
 * @author Ttt
 */
public final class HandlerUtils {

    private HandlerUtils() {
    }

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
    public static void postRunnable(final Runnable runnable, final long delayMillis) {
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
    public static void postRunnable(final Runnable runnable, final long delayMillis, final int number, final long interval) {
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
    public static void postRunnable(final Runnable runnable, final long delayMillis, final int number, final long interval, final OnEndListener onEndListener) {
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
        void onEnd(long delayMillis, int number, long interval);
    }
}