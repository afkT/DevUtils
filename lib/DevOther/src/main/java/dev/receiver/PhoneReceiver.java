package dev.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: 手机监听广播
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 *     <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
 * </pre>
 */
public final class PhoneReceiver
        extends BroadcastReceiver {

    private PhoneReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = PhoneReceiver.class.getSimpleName();

    // 电话状态监听意图
    private static final String  PHONE_STATE       = "android.intent.action.PHONE_STATE";
    // 拨出电话意图
    private static final String  NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    // 通话号码
    private              String  mNumber;
    // 是否拨号打出
    private              boolean mIsDialOut;

    // ========
    // = 状态 =
    // ========

    // 未接
    private static final String RINGING = "RINGING";
    // 已接
    private static final String OFFHOOK = "OFFHOOK";
    // 挂断
    private static final String IDLE    = "IDLE";

    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {
        try {
            String action = intent.getAction();
            // 打印当前触发的广播
            LogPrintUtils.dTag(TAG, "onReceive Action: %s", action);
            // 判断类型
            if (NEW_OUTGOING_CALL.equals(action)) {
                // 表示属于拨号
                mIsDialOut = true;
                // 拨出号码
                mNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                // 触发事件
                if (sListener != null) { // 播出电话
                    sListener.onPhoneStateChanged(CallState.Outgoing, mNumber);
                }
            } else if (PHONE_STATE.equals(action)) {
                // 通话号码
                mNumber = intent.getStringExtra("incoming_number");
                // 状态
                String state = intent.getStringExtra("state");
                // 判断状态
                switch (state) {
                    case RINGING: // 未接
                        mIsDialOut = false;
                        if (sListener != null) { // 接入电话铃响
                            sListener.onPhoneStateChanged(CallState.IncomingRing, mNumber);
                        }
                        break;
                    case OFFHOOK: // 已接
                        if (!mIsDialOut && sListener != null) { // 接入通话中
                            sListener.onPhoneStateChanged(CallState.Incoming, mNumber);
                        }
                        break;
                    case IDLE: // 挂断
                        if (mIsDialOut) {
                            if (sListener != null) { // 播出电话结束
                                sListener.onPhoneStateChanged(CallState.OutgoingEnd, mNumber);
                            }
                        } else {
                            if (sListener != null) { // 接入通话完毕
                                sListener.onPhoneStateChanged(CallState.IncomingEnd, mNumber);
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "onReceive");
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    // 电话监听广播
    private static final PhoneReceiver sReceiver = new PhoneReceiver();

    /**
     * 注册电话监听广播
     */
    public static void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            // 电话状态监听
            filter.addAction(PHONE_STATE);
            filter.addAction(NEW_OUTGOING_CALL);
            filter.setPriority(Integer.MAX_VALUE);
            // 注册广播
            AppUtils.registerReceiver(sReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册电话监听广播
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(sReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 电话状态监听事件
    private static PhoneListener sListener;

    /**
     * 设置电话状态监听事件
     * @param listener {@link PhoneListener}
     * @return {@link PhoneReceiver}
     */
    public static PhoneReceiver setPhoneListener(final PhoneListener listener) {
        PhoneReceiver.sListener = listener;
        return sReceiver;
    }

    /**
     * detail: 电话状态监听事件
     * @author Ttt
     */
    public interface PhoneListener {

        /**
         * 电话状态改变通知
         * @param state  通话状态
         * @param number 通话号码
         */
        void onPhoneStateChanged(
                CallState state,
                String number
        );
    }

    /**
     * detail: 通话状态
     * @author Ttt
     */
    public enum CallState {

        Outgoing, // 播出电话
        OutgoingEnd, // 播出电话结束
        IncomingRing, // 接入电话铃响
        Incoming, // 接入通话中
        IncomingEnd // 接入通话完毕
    }
}