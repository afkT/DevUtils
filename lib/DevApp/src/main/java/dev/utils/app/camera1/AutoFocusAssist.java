package dev.utils.app.camera1;

import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collection;

import dev.utils.LogPrintUtils;

/**
 * detail: 摄像头自动获取焦点辅助类
 * @author Ttt
 * <pre>
 *     对焦模式
 *     @see <a href="https://blog.csdn.net/fulinwsuafcie/article/details/49558001"/>
 * </pre>
 */
public final class AutoFocusAssist
        implements Camera.AutoFocusCallback {

    // 日志 TAG
    private final String TAG = AutoFocusAssist.class.getSimpleName();

    // 设置对焦模式
    public static final Collection<String> FOCUS_MODES;

    static {
        FOCUS_MODES = new ArrayList<>();
        FOCUS_MODES.add(Camera.Parameters.FOCUS_MODE_AUTO); // 自动对焦
        FOCUS_MODES.add(Camera.Parameters.FOCUS_MODE_MACRO); // 微距
    }

    // ========
    // = 变量 =
    // ========

    // 自动对焦时间间隔
    private final long               mInterval;
    // 摄像头对象
    private final Camera             mCamera;
    // 判断摄像头是否使用对焦
    private final boolean            mUseAutoFocus;
    // 判断是否停止对焦
    private       boolean            mStopped;
    // 判断是否对焦中
    private       boolean            mFocusing;
    // 对焦任务
    private       AsyncTask<?, ?, ?> mOutstandingTask;
    // 判断是否需要自动对焦
    private       boolean            mAutoFocus = true;

    // ===========
    // = 构造函数 =
    // ===========

    /**
     * 构造函数
     * @param camera {@link android.hardware.Camera}
     */
    public AutoFocusAssist(final Camera camera) {
        this(camera, 2000L);
    }

    /**
     * 构造函数
     * @param camera   {@link android.hardware.Camera}
     * @param interval 自动对焦时间间隔
     */
    public AutoFocusAssist(
            final Camera camera,
            final long interval
    ) {
        this.mCamera = camera;
        this.mInterval = interval;
        // 防止为 null
        if (camera != null) {
            // 获取对象对焦模式
            String currentFocusMode = camera.getParameters().getFocusMode();
            // 判断是否支持对焦
            mUseAutoFocus = FOCUS_MODES.contains(currentFocusMode);
        } else {
            // 不支持对焦
            mUseAutoFocus = false;
        }
        // 开始任务
        start();
    }

    /**
     * 设置对焦模式
     * @param collection 对焦模式集合
     */
    public static void setFocusModes(final Collection<String> collection) {
        if (collection != null) {
            FOCUS_MODES.clear();
            // 保存对焦模式集合
            FOCUS_MODES.addAll(collection);
        }
    }

    /**
     * 是否允许自动对焦
     * @return {@code true} 自动对焦, {@code false} 非自动对焦
     */
    public boolean isAutoFocus() {
        return mAutoFocus;
    }

    /**
     * 设置是否开启自动对焦
     * @param autoFocus 是否自动对焦
     */
    public void setAutoFocus(final boolean autoFocus) {
        this.mAutoFocus = autoFocus;
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
     * @param theCamera 对焦的 {@link android.hardware.Camera}
     */
    @Override
    public synchronized void onAutoFocus(
            boolean success,
            Camera theCamera
    ) {
        // 对焦结束, 设置非对焦中
        mFocusing = false;
        // 再次自动对焦
        autoFocusAgainLater();
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 再次自动对焦
     */
    private synchronized void autoFocusAgainLater() {
        // 不属于停止, 并且任务等于 null 才处理
        if (!mStopped && mOutstandingTask == null) {
            // 初始化任务
            AutoFocusTask newTask = new AutoFocusTask();
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    // 默认使用异步任务
                    newTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    newTask.execute();
                }
                mOutstandingTask = newTask;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "autoFocusAgainLater");
            }
        }
    }

    /**
     * 开始对焦
     */
    public synchronized void start() {
        // 如果不使用自动对焦, 则不处理
        if (!mAutoFocus) return;
        // 支持对焦才处理
        if (mUseAutoFocus) {
            // 重置任务为 null
            mOutstandingTask = null;
            // 不属于停止 并且 非对焦中
            if (!mStopped && !mFocusing) {
                try {
                    // 设置自动对焦回调
                    mCamera.autoFocus(this);
                    // 表示对焦中
                    mFocusing = true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "start");
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
        mStopped = true;
        // 判断是否支持对焦
        if (mUseAutoFocus) {
            try {
                // 关闭任务
                cancelOutstandingTask();
                // 取消对焦
                mCamera.cancelAutoFocus();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "stop");
            }
        }
    }

    /**
     * 取消对焦任务
     */
    private synchronized void cancelOutstandingTask() {
        if (mOutstandingTask != null) {
            if (mOutstandingTask.getStatus() != AsyncTask.Status.FINISHED) {
                mOutstandingTask.cancel(true);
            }
            mOutstandingTask = null;
        }
    }

    /**
     * detail: 自动对焦任务
     * @author Ttt
     */
    private final class AutoFocusTask
            extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... voids) {
            try {
                // 堵塞时间
                Thread.sleep(mInterval);
            } catch (Exception e) {
            }
            // 开启定时
            start();
            return null;
        }
    }
}