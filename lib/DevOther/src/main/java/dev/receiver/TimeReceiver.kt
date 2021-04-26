package dev.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dev.utils.LogPrintUtils
import dev.utils.app.AppUtils

/**
 * detail: 时间监听广播
 * @author Ttt
 */
class TimeReceiver private constructor() : BroadcastReceiver() {

    // 日志 TAG
    private val TAG = TimeReceiver::class.java.simpleName

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        // 打印触发的广播
        LogPrintUtils.dTag(TAG, "onReceive Action: %s", action)
        // 判断类型
        when (action) {
            // 每分钟调用
            Intent.ACTION_TIME_TICK -> sListener?.onTimeTick()
            // 设置时间
            Intent.ACTION_TIME_CHANGED -> sListener?.onTimeChanged()
            // 时区改变
            Intent.ACTION_TIMEZONE_CHANGED -> sListener?.onTimeZoneChanged()
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
         * 每分钟调用
         */
        fun onTimeTick()

        /**
         * 设置时间
         */
        fun onTimeChanged()

        /**
         * 时区改变
         */
        fun onTimeZoneChanged()
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    companion object {

        private val sReceiver = TimeReceiver()

        private var sListener: Listener? = null

        /**
         * 注册广播监听
         */
        fun register() {
            IntentFilter().apply {
                // 每分钟调用
                addAction(Intent.ACTION_TIME_TICK)
                // 设置时间
                addAction(Intent.ACTION_TIME_CHANGED)
                // 时区改变
                addAction(Intent.ACTION_TIMEZONE_CHANGED)
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
}