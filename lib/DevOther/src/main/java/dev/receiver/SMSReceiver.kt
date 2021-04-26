package dev.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsMessage
import dev.utils.app.AppUtils

/**
 * detail: 短信监听广播
 * @author Ttt
 * 所需权限
 * <uses-permission android:name="android.permission.RECEIVE_SMS"/>
 */
class SMSReceiver private constructor() : BroadcastReceiver() {

    // 日志 TAG
    private val TAG = SMSReceiver::class.java.simpleName

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        (intent.extras?.get("pdus") as? Array<Object>)?.let { pdus ->
            var originatingAddress: String? = null
            var serviceCenterAddress: String? = null
            // 消息内容
            val builder = StringBuilder()
            // 循环拼接内容
            for (it in pdus) {
                val sms = SmsMessage.createFromPdu(it as? ByteArray)
                builder.append(sms.messageBody) // 消息内容 ( 多条消息合并成一条 )
                originatingAddress = sms.originatingAddress
                serviceCenterAddress = sms.serviceCenterAddress
                // 收到消息提醒 ( 超过长度的消息变成两条会触发多次 )
                sListener?.onMessage(sms)
            }
            // 最终触发通知 ( 超过长度的短信消息, 最终合并成一条内容体 )
            sListener?.onMessage(
                builder.toString(), originatingAddress, serviceCenterAddress
            )
        }
    }

    // =======
    // = 接口 =
    // =======

    /**
     * detail: 监听回调事件
     * @author Ttt
     */
    interface Listener {

        /**
         * 最终触发通知 ( 超过长度的短信消息, 最终合并成一条内容体 )
         * @param message              短信内容
         * @param originatingAddress   短信的原始地址 ( 发件人 )
         * @param serviceCenterAddress 短信服务中心地址
         */
        fun onMessage(
            message: String?,
            originatingAddress: String?,
            serviceCenterAddress: String?
        )

        /**
         * 收到消息提醒 ( 超过长度的消息变成两条会触发多次 )
         * @param message [SmsMessage]
         */
        fun onMessage(message: SmsMessage?) {}
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    companion object {

        private val sReceiver = SMSReceiver()

        private var sListener: Listener? = null

        /**
         * 注册广播监听
         */
        fun register() {
            IntentFilter().apply {
                // 短信获取监听
                addAction("android.provider.Telephony.SMS_RECEIVED")
                // 注册广播
                AppUtils.registerReceiver(sReceiver, this)
            }
        }

        /**
         * 取消广播监听
         */
        fun unregister() = AppUtils.unregisterReceiver(sReceiver)

        /**
         * 设置监听回调事件
         * @param listener [Listener]
         */
        fun setListener(listener: Listener?) {
            sListener = listener
        }

        // ===========
        // = 其他方法 =
        // ===========

        /**
         * 获取消息数据
         * @param message [SmsMessage]
         * @return 消息数据
         */
        fun getMessageData(message: SmsMessage?): String {
            val builder = StringBuilder()
            message?.let { it ->
                builder.append("\ngetDisplayMessageBody: ").append(it.displayMessageBody)
                builder.append("\ngetDisplayOriginatingAddress: ")
                    .append(it.displayOriginatingAddress)
                builder.append("\ngetEmailBody: ").append(it.emailBody)
                builder.append("\ngetEmailFrom: ").append(it.emailFrom)
                builder.append("\ngetMessageBody: ").append(it.messageBody)
                builder.append("\ngetOriginatingAddress: ").append(it.originatingAddress)
                builder.append("\ngetPseudoSubject: ").append(it.pseudoSubject)
                builder.append("\ngetServiceCenterAddress: ").append(it.serviceCenterAddress)
                builder.append("\ngetIndexOnIcc: ").append(it.indexOnIcc)
                builder.append("\ngetMessageClass: ").append(it.messageClass)
                builder.append("\ngetUserData: ").append(String(it.userData))
            }
            return builder.toString()
        }
    }
}