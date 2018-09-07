package dev.utils.app;

import android.content.Context;
import android.os.Handler;

/**
 * detail: Handler 工具类, 默认开启一个 Handler，方便在各个地方随时执行主线程任务
 * Created by Ttt
 */
public final class HandlerUtils {

    private HandlerUtils() {
    }

    /** 主线程 Handler */
    private static Handler mainHandler;

    /**
     * 初始化操作
     * @param context
     */
    public static void init(Context context) {
        if (mainHandler == null) mainHandler = new Handler(context.getMainLooper());
    }

    /**
     * 获取主线程 Handler
     * @return 主线程 Handler
     */
    public static Handler getMainHandler() {
        return mainHandler;
    }

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     */
    public static void postRunnable(Runnable runnable) {
        getMainHandler().post(runnable);
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable 可执行的任务
     * @param delay 延迟时间
     */
    public static void postRunnable(Runnable runnable, long delay) {
        getMainHandler().postDelayed(runnable, delay);
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable 可执行的任务
     * @param delay 延迟时间
     * @param times 轮询次数
     * @param interval 轮询时间
     */
    public static void postRunnable(final Runnable runnable, long delay, final int times, final int interval) {
        Runnable loop = new Runnable() {
            private int mTimes;
            @Override
            public void run() {
                if (mTimes < times) {
                    runnable.run();
                    getMainHandler().postDelayed(this, interval);
                }
                mTimes++;
            }
        };
        getMainHandler().postDelayed(loop, delay);
    }

    /**
     * 在主线程 Handler 中清除任务
     * @param runnable 需要清除的任务
     */
    public static void removeRunnable(Runnable runnable) {
        getMainHandler().removeCallbacks(runnable);
    }
}
