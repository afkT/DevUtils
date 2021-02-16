package dev.utils.app;

import android.content.Context;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * detail: UncaughtException 处理工具类
 * @author Ttt
 * <pre>
 *     当程序发生 Uncaught 异常的时候, 由该类来接管程序, 并记录发送错误报告
 * </pre>
 */
public final class CrashUtils
        implements UncaughtExceptionHandler {

    private CrashUtils() {
    }

    // Context
    private                 Context                  mContext;
    // 系统默认的 UncaughtException 处理器
    private                 UncaughtExceptionHandler mDefaultHandler;
    // 捕获异常事件处理
    private                 CrashCatchListener       mCrashCatchListener;
    // CrashUtils 实例
    private static volatile CrashUtils               sInstance;

    /**
     * 获取 CrashUtils 实例
     * @return {@link CrashUtils}
     */
    public static CrashUtils getInstance() {
        if (sInstance == null) {
            synchronized (CrashUtils.class) {
                if (sInstance == null) {
                    sInstance = new CrashUtils();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化方法
     * @param context            {@link Context}
     * @param crashCatchListener {@link CrashCatchListener}
     */
    public void init(
            Context context,
            CrashCatchListener crashCatchListener
    ) {
        this.mContext = context;
        this.mCrashCatchListener = crashCatchListener;
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashUtils 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     * @param thread {@link Thread}
     * @param ex     {@link Throwable}
     */
    @Override
    public void uncaughtException(
            Thread thread,
            Throwable ex
    ) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            if (mCrashCatchListener != null) {
                mCrashCatchListener.uncaughtException(mContext, thread, ex);
            }
        }
    }

    /**
     * 自定义错误处理 ( 收集错误信息、发送错误报告等操作均在此完成 )
     * @param ex {@link Throwable}
     * @return {@code true} 处理该异常信息, {@code false} 未处理该异常信息
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) return false;
        // 触发回调
        if (mCrashCatchListener != null) {
            mCrashCatchListener.handleException(ex);
        }
        return true;
    }

    /**
     * detail: 异常捕获处理
     * @author Ttt
     */
    public interface CrashCatchListener {

        /**
         * 处理异常
         * @param ex {@link Throwable}
         */
        void handleException(Throwable ex);

        /**
         * 处理未捕获的异常
         * @param context {@link Context}
         * @param thread  {@link Thread}
         * @param ex      {@link Throwable}
         */
        void uncaughtException(
                Context context,
                Thread thread,
                Throwable ex
        );
    }
}