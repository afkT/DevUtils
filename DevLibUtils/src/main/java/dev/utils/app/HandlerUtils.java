package dev.utils.app;

import android.os.Handler;
import android.os.Looper;

/**
 * detail: Handler 工具类, 默认开启一个 Handler，方便在各个地方随时执行主线程任务
 * @author Ttt
 */
public final class HandlerUtils {

    private HandlerUtils() {
    }

    // 主线程 Handler
    private static Handler mMainHandler;

    /**
     * 获取主线程 Handler
     * @return 主线程 Handler
     */
    public static Handler getMainHandler() {
        if (mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
        return mMainHandler;
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
     * @param times       轮询次数
     * @param interval    轮询时间
     */
    public static void postRunnable(final Runnable runnable, final long delayMillis, final int times, final int interval) {
        if (runnable != null) {
            Runnable loop = new Runnable() {
                private int mTimes;

                @Override
                public void run() {
                    if (mTimes < times) {
                        if (runnable != null) {
                            try {
                                runnable.run();
                            } catch (Exception e) {
                            }
                        }
                        getMainHandler().postDelayed(this, interval);
                    }
                    mTimes++;
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
}
