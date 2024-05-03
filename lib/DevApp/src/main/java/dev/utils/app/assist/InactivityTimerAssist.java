package dev.utils.app.assist;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * detail: Activity 无操作定时辅助类
 * @author Ttt
 * <pre>
 *     需要在对应的生命周期内, 调用对应的 onPause/onResume/onDestroy 方法
 * </pre>
 */
public final class InactivityTimerAssist {

    // 无操作时间 ( 到时间自动关闭, 默认五分钟 )
    private final long                              mInactivityTime;
    // 对应的页面
    private final WeakReference<Activity>           mActivity;
    // 检查任务
    private       AsyncTask<Object, Object, Object> mInactivityTask;

    // ==========
    // = 构造函数 =
    // ==========

    /**
     * 构造函数
     * @param activity {@link Activity}
     */
    public InactivityTimerAssist(final Activity activity) {
        this(activity, 300000L); // 5 * 60 * 1000L
    }

    /**
     * 构造函数
     * @param activity       {@link Activity}
     * @param inactivityTime 无操作时间间隔 ( 毫秒 )
     */
    public InactivityTimerAssist(
            final Activity activity,
            final long inactivityTime
    ) {
        this.mActivity       = new WeakReference<>(activity);
        this.mInactivityTime = inactivityTime;
        // 关闭任务
        cancel();
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 暂停检测
     * <pre>
     *     Activity 生命周期 onPause 调用
     * </pre>
     */
    public synchronized void onPause() {
        // 取消任务
        cancel();
    }

    /**
     * 回到 Activity 处理
     * <pre>
     *     Activity 生命周期 onResume 调用
     * </pre>
     */
    public synchronized void onResume() {
        // 开始检测
        start();
    }

    /**
     * Activity 销毁处理
     * <pre>
     *     Activity 生命周期 onDestroy 调用
     * </pre>
     */
    public synchronized void onDestroy() {
        cancel();
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 开始计时任务
     */
    private synchronized void start() {
        // 取消任务
        cancel();
        // 注册任务
        mInactivityTask = new InactivityAsyncTask();
        // 开启任务
        mInactivityTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 取消计时任务
     */
    private synchronized void cancel() {
        AsyncTask<?, ?, ?> task = mInactivityTask;
        if (task != null) {
            task.cancel(true);
            // 重置为 null
            mInactivityTask = null;
        }
    }

    // =

    /**
     * detail: 定时检测任务 ( 无操作检测 )
     * @author Ttt
     */
    private class InactivityAsyncTask
            extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... objects) {
            try {
                Thread.sleep(mInactivityTime);
                // 关闭页面
                if (mActivity.get() != null) {
                    mActivity.get().finish();
                }
            } catch (Exception ignored) {
            }
            return null;
        }
    }
}