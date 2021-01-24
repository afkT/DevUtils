package dev.utils.app.assist;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * detail: 延迟触发辅助类
 * @author Ttt
 */
public class DelayAssist {

    // 默认延迟时间
    public static final long DELAY_MILLIS = 300L;

    // 主线程 Handler
    private final Handler  mMainHandler;
    // 延迟时间
    private       long     mDelayMillis = DELAY_MILLIS;
    // 搜索回调
    private       Callback mCallback;

    // ===========
    // = 构造函数 =
    // ===========

    public DelayAssist() {
        this(DELAY_MILLIS, null);
    }

    public DelayAssist(long delayMillis) {
        this(delayMillis, null);
    }

    public DelayAssist(Callback callback) {
        this(DELAY_MILLIS, callback);
    }

    public DelayAssist(
            long delayMillis,
            Callback callback
    ) {
        setDelayMillis(delayMillis);
        mCallback = callback;
        mMainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mCallback != null) mCallback.callback(msg.obj);
            }
        };
    }

    /**
     * detail: 回调接口
     * @author Ttt
     */
    public interface Callback {

        /**
         * 回调方法
         * @param object Object
         */
        void callback(Object object);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 移除消息
     */
    public void remove() {
        mMainHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 发送消息 ( 功能由该方法实现 )
     */
    public void post() {
        post(null);
    }

    /**
     * 发送消息 ( 功能由该方法实现 )
     * @param object Object
     */
    public void post(final Object object) {
        mMainHandler.removeCallbacksAndMessages(null);
        Message message = mMainHandler.obtainMessage();
        message.obj = object;
        mMainHandler.sendMessageDelayed(message, mDelayMillis);
    }

    // =

    /**
     * 设置搜索延迟时间
     * @param delayMillis 延迟时间
     * @return {@link DelayAssist}
     */
    public DelayAssist setDelayMillis(final long delayMillis) {
        mDelayMillis = (delayMillis > 0) ? delayMillis : DELAY_MILLIS;
        return this;
    }

    /**
     * 设置搜索回调接口
     * @param callback {@link Callback}
     * @return {@link DelayAssist}
     */
    public DelayAssist setCallback(final Callback callback) {
        mCallback = callback;
        return this;
    }
}