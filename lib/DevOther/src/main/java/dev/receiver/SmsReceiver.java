package dev.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsMessage;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 短信监听广播
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.RECEIVE_SMS"/>
 * </pre>
 */
public final class SmsReceiver extends BroadcastReceiver {

    private SmsReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            String originatingAddress = null;
            String serviceCenterAddress = null;
            if (pdus != null) {
                // 消息内容
                String message = "";
                // 循环拼接内容
                for (Object object : pdus) {
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
                    message += sms.getMessageBody(); // 消息内容 - 多条消息, 合并成一条
                    originatingAddress = sms.getOriginatingAddress();
                    serviceCenterAddress = sms.getServiceCenterAddress();
                    // 触发事件
                    if (smsListener != null) {
                        smsListener.onMessage(sms);
                    }
                }
                // 触发事件
                if (smsListener != null) {
                    smsListener.onMessage(message, originatingAddress, serviceCenterAddress);
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "onReceive");
        }
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 获取消息数据
     * @param message {@link SmsMessage}
     * @return 消息数据
     */
    public static String getMessageData(final SmsMessage message) {
        StringBuilder builder = new StringBuilder();
        if (message != null) {
            builder.append("\ngetDisplayMessageBody: " + message.getDisplayMessageBody());
            builder.append("\ngetDisplayOriginatingAddress: " + message.getDisplayOriginatingAddress());
            builder.append("\ngetEmailBody: " + message.getEmailBody());
            builder.append("\ngetEmailFrom: " + message.getEmailFrom());
            builder.append("\ngetMessageBody: " + message.getMessageBody());
            builder.append("\ngetOriginatingAddress: " + message.getOriginatingAddress());
            builder.append("\ngetPseudoSubject: " + message.getPseudoSubject());
            builder.append("\ngetServiceCenterAddress: " + message.getServiceCenterAddress());
            builder.append("\ngetIndexOnIcc: " + message.getIndexOnIcc());
            builder.append("\ngetMessageClass: " + message.getMessageClass());
            builder.append("\ngetUserData: " + new String(message.getUserData()));
        }
        return builder.toString();
    }

    // =

    // 短信监听广播
    private static final SmsReceiver smsReceiver = new SmsReceiver();

    /**
     * 注册短信监听广播
     */
    public static void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            // 短信获取监听
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.setPriority(Integer.MAX_VALUE);
            // 注册广播
            DevUtils.getContext().registerReceiver(smsReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册短信监听广播
     */
    public static void unregisterReceiver() {
        try {
            DevUtils.getContext().unregisterReceiver(smsReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 短信监听事件
    private static SmsListener smsListener;

    /**
     * 设置短信监听事件
     * @param listener {@link SmsListener}
     */
    public static void setSmsListener(final SmsListener listener) {
        SmsReceiver.smsListener = listener;
    }

    /**
     * detail: 短信监听事件
     * @author Ttt
     */
    public static abstract class SmsListener {

        /**
         * 最终触发通知 ( 超过长度的短信消息, 最终合并成一条内容体 )
         * @param message              短信内容
         * @param originatingAddress   短信的原始地址 ( 发件人 )
         * @param serviceCenterAddress 短信服务中心地址
         */
        public abstract void onMessage(String message, String originatingAddress, String serviceCenterAddress);

        /**
         * 收到消息提醒 ( 超过长度的消息变成两条会触发多次 )
         * @param message {@link SmsMessage}
         */
        public void onMessage(SmsMessage message) {
        }
    }
}