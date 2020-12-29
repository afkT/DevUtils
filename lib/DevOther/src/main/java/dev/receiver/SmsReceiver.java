package dev.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsMessage;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: 短信监听广播
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.RECEIVE_SMS"/>
 * </pre>
 */
public final class SmsReceiver
        extends BroadcastReceiver {

    private SmsReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {
        try {
            Object[] pdus                 = (Object[]) intent.getExtras().get("pdus");
            String   originatingAddress   = null;
            String   serviceCenterAddress = null;
            if (pdus != null) {
                // 消息内容
                StringBuilder builder = new StringBuilder();
                // 循环拼接内容
                for (Object object : pdus) {
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
                    builder.append(sms.getMessageBody()); // 消息内容 ( 多条消息合并成一条 )
                    originatingAddress = sms.getOriginatingAddress();
                    serviceCenterAddress = sms.getServiceCenterAddress();
                    // 触发事件
                    if (sListener != null) {
                        sListener.onMessage(sms);
                    }
                }
                // 触发事件
                if (sListener != null) {
                    sListener.onMessage(builder.toString(), originatingAddress, serviceCenterAddress);
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "onReceive");
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取消息数据
     * @param message {@link SmsMessage}
     * @return 消息数据
     */
    public static String getMessageData(final SmsMessage message) {
        StringBuilder builder = new StringBuilder();
        if (message != null) {
            builder.append("\ngetDisplayMessageBody: ").append(message.getDisplayMessageBody());
            builder.append("\ngetDisplayOriginatingAddress: ").append(message.getDisplayOriginatingAddress());
            builder.append("\ngetEmailBody: ").append(message.getEmailBody());
            builder.append("\ngetEmailFrom: ").append(message.getEmailFrom());
            builder.append("\ngetMessageBody: ").append(message.getMessageBody());
            builder.append("\ngetOriginatingAddress: ").append(message.getOriginatingAddress());
            builder.append("\ngetPseudoSubject: ").append(message.getPseudoSubject());
            builder.append("\ngetServiceCenterAddress: ").append(message.getServiceCenterAddress());
            builder.append("\ngetIndexOnIcc: ").append(message.getIndexOnIcc());
            builder.append("\ngetMessageClass: ").append(message.getMessageClass());
            builder.append("\ngetUserData: ").append(new String(message.getUserData()));
        }
        return builder.toString();
    }

    // =

    // 短信监听广播
    private static final SmsReceiver sReceiver = new SmsReceiver();

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
            AppUtils.registerReceiver(sReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册短信监听广播
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(sReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 短信监听事件
    private static SmsListener sListener;

    /**
     * 设置短信监听事件
     * @param listener {@link SmsListener}
     * @return {@link SmsReceiver}
     */
    public static SmsReceiver setSmsListener(final SmsListener listener) {
        SmsReceiver.sListener = listener;
        return sReceiver;
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
        public abstract void onMessage(
                String message,
                String originatingAddress,
                String serviceCenterAddress
        );

        /**
         * 收到消息提醒 ( 超过长度的消息变成两条会触发多次 )
         * @param message {@link SmsMessage}
         */
        public void onMessage(SmsMessage message) {
        }
    }
}