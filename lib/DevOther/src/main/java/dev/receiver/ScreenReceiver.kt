package dev.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dev.utils.LogPrintUtils
import dev.utils.app.AppUtils

/**
 * detail: 屏幕监听广播 ( 锁屏 / 解锁 / 亮屏 )
 * @author Ttt
 */
class ScreenReceiver private constructor() : BroadcastReceiver() {

    // 日志 TAG
    private val TAG = ScreenReceiver::class.java.simpleName

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        // 打印触发的广播
        LogPrintUtils.dTag(TAG, "onReceive Action: %s", action)
        // 判断类型
        when (action) {
            // 用户打开屏幕 ( 屏幕变亮 )
            Intent.ACTION_SCREEN_ON -> sListener?.screenOn()
            // 锁屏触发
            Intent.ACTION_SCREEN_OFF -> sListener?.screenOff()
            // 用户解锁触发
            Intent.ACTION_USER_PRESENT -> sListener?.userPresent()
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
         * 用户打开屏幕 ( 屏幕变亮 )
         */
        fun screenOn()

        /**
         * 锁屏触发
         */
        fun screenOff()

        /**
         * 用户解锁触发
         */
        fun userPresent()
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    companion object {

        private val sReceiver = ScreenReceiver()

        private var sListener: Listener? = null

        /**
         * 注册广播监听
         */
        fun register() {
            IntentFilter().apply {
                // 用户打开屏幕 ( 屏幕变亮 )
                addAction(Intent.ACTION_SCREEN_ON)
                // 锁屏触发
                addAction(Intent.ACTION_SCREEN_OFF)
                // 用户解锁触发
                addAction(Intent.ACTION_USER_PRESENT)
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