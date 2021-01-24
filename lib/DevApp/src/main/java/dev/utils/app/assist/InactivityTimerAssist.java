package dev.utils.app.assist;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;

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
    private final Activity                          mActivity;
    // 电池广播 ( 充电中, 则不处理, 主要是为了省电 )
    private final BroadcastReceiver                 mPowerStateReceiver;
    // 检查任务
    private       AsyncTask<Object, Object, Object> mInactivityTask;

    // ===========
    // = 构造函数 =
    // ===========

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
        this.mActivity = activity;
        this.mInactivityTime = inactivityTime;
        // 电池广播监听
        mPowerStateReceiver = new PowerStateReceiver();
        // 关闭任务
        cancel();
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 暂停检测
     * <pre>
     *     Activity 生命周期 onPause 调用
     * </pre>
     */
    public synchronized void onPause() {
        // 取消任务
        cancel();
        try {
            // 取消注册广播
            mActivity.unregisterReceiver(mPowerStateReceiver);
        } catch (Exception e) {
        }
    }

    /**
     * 回到 Activity 处理
     * <pre>
     *     Activity 生命周期 onResume 调用
     * </pre>
     */
    public synchronized void onResume() {
        try {
            // 注册广播
            mActivity.registerReceiver(mPowerStateReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        } catch (Exception e) {
        }
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

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 开始计时任务
     */
    private synchronized void start() {
        // 取消任务
        cancel();
        // 注册任务
        mInactivityTask = new InactivityAsyncTask();
        // 开启任务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mInactivityTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            mInactivityTask.execute();
        }
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
     * detail: 电池监听广播
     * @author Ttt
     */
    private class PowerStateReceiver
            extends BroadcastReceiver {
        @Override
        public void onReceive(
                Context context,
                Intent intent
        ) {
            if (intent != null && Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                // 0 indicates that we're on battery
                boolean isBatteryNow = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) <= 0;
                if (isBatteryNow) { // 属于非充电才进行记时
                    InactivityTimerAssist.this.start();
                } else { // 充电中, 则不处理
                    InactivityTimerAssist.this.cancel();
                }
            }
        }
    }

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
                if (mActivity != null) {
                    mActivity.finish();
                }
            } catch (Exception e) {
            }
            return null;
        }
    }
}