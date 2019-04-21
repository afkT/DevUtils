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
 *      需要注意的是, 需要在对应的生命周期内，调用对应的 onXXX 方法
 * </pre>
 */
public final class InactivityTimerAssist {

    // 无操作时间(到时间自动关闭) - 默认五分钟
    private long inactivityTime = 5 * 60 * 1000L;
    // 对应的页面
    private Activity activity;
    // 电池广播(充电中, 则不处理, 主要是为了省点)
    private BroadcastReceiver powerStatusReceiver;
    // 检查任务
    private AsyncTask<Object, Object, Object> inactivityTask;

    // ============
    // = 构造函数 =
    // ============

    public InactivityTimerAssist(final Activity activity) {
        this(activity, 5 * 60 * 1000L);
    }

    public InactivityTimerAssist(final Activity activity, final long inactivityTime) {
        this.activity = activity;
        this.inactivityTime = inactivityTime;
        // 电池广播监听
        powerStatusReceiver = new PowerStatusReceiver();
        // 关闭任务
        cancel();
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 开始计时任务
     */
    public synchronized void start() {
        // 取消任务
        cancel();
        // 注册任务
        inactivityTask = new InactivityAsyncTask();
        // 开启任务
        if (Build.VERSION.SDK_INT >= 11) {
            inactivityTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            inactivityTask.execute();
        }
    }

    /**
     * 取消计时任务
     */
    private synchronized void cancel() {
        AsyncTask<?, ?, ?> task = inactivityTask;
        if (task != null) {
            task.cancel(true);
            // 重置为null
            inactivityTask = null;
        }
    }

    // =

    /**
     * 暂停检测
     */
    public synchronized void onPause() {
        // 取消任务
        cancel();
        try {
            // 取消注册广播
            activity.unregisterReceiver(powerStatusReceiver);
        } catch (Exception e) {
        }
    }

    /**
     * 回到 Activity/xx 处理
     */
    public synchronized void onResume() {
        try {
            // 注册广播
            activity.registerReceiver(powerStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        } catch (Exception e) {
        }
        // 开始检测
        start();
    }

    /**
     * 页面销毁处理
     */
    public void onDestroy() {
        cancel();
    }

    // =

    /**
     * detail: 电池监听广播
     * @author Ttt
     */
    private class PowerStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                // 0 indicates that we're on battery
                boolean onBatteryNow = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) <= 0;
                if (onBatteryNow) { // 属于非充电才进行记时
                    InactivityTimerAssist.this.start();
                } else { // 充电中, 则不处理
                    InactivityTimerAssist.this.cancel();
                }
            }
        }
    }

    /**
     * detail: 定时检测任务
     * @author Ttt
     */
    private class InactivityAsyncTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... objects) {
            try {
                Thread.sleep(inactivityTime);
                // 关闭页面
                if (activity != null) {
                    activity.finish();
                }
            } catch (InterruptedException e) {
            }
            return null;
        }
    }
}
