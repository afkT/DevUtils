package dev.utils.app.camera1;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.RejectedExecutionException;

import dev.utils.LogPrintUtils;

/**
 * detail: 自动获取焦点 辅助类
 * @author Ttt
 * <pre>
 *      对焦模式
 *      @see <a href="https://blog.csdn.net/fulinwsuafcie/article/details/49558001"/>
 * </pre>
 */
public final class AutoFocusAssist implements Camera.AutoFocusCallback {

    // 日志 TAG
    private final String TAG = AutoFocusAssist.class.getSimpleName();
    // 设置对焦模式
    public static final Collection<String> FOCUS_MODES_CALLING_AF;

    static {
        FOCUS_MODES_CALLING_AF = new ArrayList<>();
        FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_AUTO); // 自动对焦
        FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_MACRO); // 微距
    }

    // ========
    // = 变量 =
    // ========

    // 间隔获取焦点时间
    private long interval = 2000L;
    // 摄像头对象
    private final Camera mCamera;
    // 判断摄像头是否使用对焦
    private final boolean useAutoFocus;
    // 判断是否停止对焦
    private boolean stopped;
    // 判断是否对焦中
    private boolean focusing;
    // 对焦任务
    private AsyncTask<?, ?, ?> outstandingTask;
    // 判断是否需要自动对焦
    private boolean autoFocus = true;

    // ============
    // = 构造函数 =
    // ============

    public AutoFocusAssist(final Camera camera) {
        this(camera, 2000L);
    }

    public AutoFocusAssist(final Camera camera, final long interval) {
        this.mCamera = camera;
        this.interval = interval;
        // 防止为null
        if (camera != null) {
            // 获取对象对焦模式
            String currentFocusMode = camera.getParameters().getFocusMode();
            // 判断是否(使用/支持)对焦
            useAutoFocus = FOCUS_MODES_CALLING_AF.contains(currentFocusMode);
        } else {
            // 不支持对焦
            useAutoFocus = false;
        }
        // 开始任务
        start();
    }

    /**
     * 设置对焦模式
     * @param collection
     */
    public static void setFocusModes(final Collection<String> collection) {
        // 清空旧的
        FOCUS_MODES_CALLING_AF.clear();
        // 防止为null
        if (collection != null) {
            FOCUS_MODES_CALLING_AF.addAll(collection);
        }
    }

    /**
     * 是否允许自动对焦
     * @return {@code true} 自动对焦, {@code false} 非自动对焦
     */
    public boolean isAutoFocus() {
        return autoFocus;
    }

    /**
     * 设置是否开启自动对焦
     * @param autoFocus
     */
    public void setAutoFocus(final boolean autoFocus) {
        this.autoFocus = autoFocus;
        // 判断是否开启自动对焦
        if (autoFocus) {
            start();
        } else {
            stop();
        }
    }

    /**
     * 对焦回调 {@link Camera.AutoFocusCallback} 重写方法
     * @param success   是否对焦成功
     * @param theCamera 对焦的摄像头
     */
    @Override
    public synchronized void onAutoFocus(boolean success, Camera theCamera) {
        // 对焦结束, 设置非对焦中
        focusing = false;
        // 再次自动对焦
        autoFocusAgainLater();
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 再次自动对焦
     */
    @SuppressLint("NewApi")
    private synchronized void autoFocusAgainLater() {
        // 不属于停止, 并且任务等于null, 才处理
        if (!stopped && outstandingTask == null) {
            // 初始化任务
            AutoFocusTask newTask = new AutoFocusTask();
            try {
                if (Build.VERSION.SDK_INT >= 11) {
                    // 默认使用异步任务
                    newTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    newTask.execute();
                }
                outstandingTask = newTask;
            } catch (RejectedExecutionException ree) {
                LogPrintUtils.eTag(TAG, ree, "autoFocusAgainLater");
            }
        }
    }

    /**
     * 开始对焦
     */
    public synchronized void start() {
        // 如果不使用自动对焦, 则不处理
        if (!autoFocus) {
            return;
        }
        // 支持对焦才处理
        if (useAutoFocus) {
            // 重置任务为null
            outstandingTask = null;
            // 不属于停止 并且 非对焦中
            if (!stopped && !focusing) {
                try {
                    // 设置自动对焦回调
                    mCamera.autoFocus(this);
                    // 表示对焦中
                    focusing = true;
                } catch (RuntimeException re) {
                    LogPrintUtils.eTag(TAG, re, "start");
                    // Try again later to keep cycle going
                    autoFocusAgainLater();
                }
            }
        }
    }

    /**
     * 停止对焦
     */
    public synchronized void stop() {
        // 表示属于停止
        stopped = true;
        // 判断是否支持对焦
        if (useAutoFocus) {
            // 关闭任务
            cancelOutstandingTask();
            try {
                // 取消对焦
                mCamera.cancelAutoFocus();
            } catch (RuntimeException re) {
                LogPrintUtils.eTag(TAG, re, "stop");
            }
        }
    }

    /**
     * 取消对焦任务
     */
    private synchronized void cancelOutstandingTask() {
        if (outstandingTask != null) {
            if (outstandingTask.getStatus() != AsyncTask.Status.FINISHED) {
                outstandingTask.cancel(true);
            }
            outstandingTask = null;
        }
    }

    /**
     * detail: 自动对焦任务
     * @author Ttt
     */
    private final class AutoFocusTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... voids) {
            try {
                // 堵塞时间
                Thread.sleep(interval);
            } catch (InterruptedException e) {
            }
            // 开启定时
            start();
            return null;
        }
    }
}
