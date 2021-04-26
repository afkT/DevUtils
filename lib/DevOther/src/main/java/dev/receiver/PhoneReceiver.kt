package dev.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dev.utils.LogPrintUtils
import dev.utils.app.AppUtils

/**
 * detail: 手机监听广播
 * @author Ttt
 * 所需权限
 * <uses-permission android:name="android.permission.READ_CALL_LOG"/>
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
 */
class PhoneReceiver private constructor() : BroadcastReceiver() {

    // 日志 TAG
    private val TAG = PhoneReceiver::class.java.simpleName

    // 是否拨号打出
    private var mDialOut = false

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        // 打印触发的广播
        LogPrintUtils.dTag(TAG, "onReceive Action: %s", action)
        // 通话号码
        var number: String?
        // 判断类型
        when (action) {
            // 拨出电话意图
            NEW_OUTGOING_CALL -> {
                // 表示属于拨号
                mDialOut = true
                // 拨出号码
                number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
                // 触发事件
                sListener?.onPhoneStateChanged(CallState.OUTGOING, number)
            }
            // 电话状态监听意图
            PHONE_STATE -> {
                // 通话号码
                number = intent.getStringExtra("incoming_number")
                // 通话状态
                intent.getStringExtra("state")?.let { state ->
                    // 判断状态
                    when (state.toUpperCase()) {
                        RINGING -> { // 未接
                            mDialOut = false
                            // 接入电话铃响
                            sListener?.onPhoneStateChanged(CallState.INCOMING_RING, number)
                        }
                        OFFHOOK -> { // 已接
                            if (!mDialOut) {
                                // 接入通话中
                                sListener?.onPhoneStateChanged(CallState.INCOMING, number)
                            } else {
                            }
                        }
                        IDLE -> { // 挂断
                            if (mDialOut) {
                                // 播出电话结束
                                sListener?.onPhoneStateChanged(CallState.OUTGOING_END, number)
                            } else {
                                // 接入通话完毕
                                sListener?.onPhoneStateChanged(CallState.INCOMING_END, number)
                            }
                        }
                        else -> {
                        }
                    }
                }
            }
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
         * 电话状态改变通知
         * @param state  通话状态
         * @param number 通话号码
         */
        fun onPhoneStateChanged(
            state: CallState,
            number: String?
        )
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    companion object {

        private val sReceiver = PhoneReceiver()

        private var sListener: Listener? = null

        // 电话状态监听意图
        private const val PHONE_STATE = "android.intent.action.PHONE_STATE"

        // 拨出电话意图
        private const val NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL"

        // 未接
        private const val RINGING = "RINGING"

        // 已接
        private const val OFFHOOK = "OFFHOOK"

        // 挂断
        private const val IDLE = "IDLE"

        /**
         * 注册广播监听
         */
        fun register() {
            IntentFilter().apply {
                // 电话状态监听意图
                addAction(PHONE_STATE)
                // 拨出电话意图
                addAction(NEW_OUTGOING_CALL)
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
    }

    /**
     * detail: 通话状态
     * @author Ttt
     */
    enum class CallState {
        OUTGOING,       // 播出电话
        OUTGOING_END,   // 播出电话结束
        INCOMING_RING,  // 接入电话铃响
        INCOMING,       // 接入通话中
        INCOMING_END    // 接入通话完毕
    }
}