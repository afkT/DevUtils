package dev.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dev.utils.LogPrintUtils
import dev.utils.app.AppUtils

/**
 * detail: 电量监听广播
 * @author Ttt
 */
class BatteryReceiver private constructor() : BroadcastReceiver() {

    // 日志 TAG
    private val TAG = BatteryReceiver::class.java.simpleName

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        // 打印触发的广播
        LogPrintUtils.dTag(TAG, "onReceive Action: %s", action)
        // 获取当前电量, 范围是 0-100
        val level = intent.getIntExtra("level", 0)
        // 判断类型
        when (action) {
            // 电量状态发送改变
            Intent.ACTION_BATTERY_CHANGED -> sListener?.onBatteryChanged(level)
            // 电量低
            Intent.ACTION_BATTERY_LOW -> sListener?.onBatteryLow(level)
            // 电量从低变回高
            Intent.ACTION_BATTERY_OKAY -> sListener?.onBatteryOkay(level)
            // 连接充电器
            Intent.ACTION_POWER_CONNECTED -> sListener?.onPowerConnected(level, true)
            // 断开充电器
            Intent.ACTION_POWER_DISCONNECTED -> sListener?.onPowerConnected(level, false)
            // 电力使用情况总结
            Intent.ACTION_POWER_USAGE_SUMMARY -> sListener?.onPowerUsageSummary(level)
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
         * 电量改变通知
         * @param level 电量百分比
         */
        fun onBatteryChanged(level: Int)

        /**
         * 电量低通知
         * @param level 电量百分比
         */
        fun onBatteryLow(level: Int)

        /**
         * 电量从低变回高通知
         * @param level 电量百分比
         */
        fun onBatteryOkay(level: Int)

        /**
         * 充电状态改变通知
         * @param level       电量百分比
         * @param isConnected 是否充电连接中
         */
        fun onPowerConnected(
            level: Int,
            isConnected: Boolean
        )

        /**
         * 电力使用情况总结
         * @param level 电量百分比
         */
        fun onPowerUsageSummary(level: Int)
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    companion object {

        private val sReceiver = BatteryReceiver()

        private var sListener: Listener? = null

        /**
         * 注册广播监听
         */
        fun register() {
            IntentFilter().apply {
                // 电量状态发送改变
                addAction(Intent.ACTION_BATTERY_CHANGED)
                // 电量低
                addAction(Intent.ACTION_BATTERY_LOW)
                // 电量从低变回高
                addAction(Intent.ACTION_BATTERY_OKAY)
                // 连接充电器
                addAction(Intent.ACTION_POWER_CONNECTED)
                // 断开充电器
                addAction(Intent.ACTION_POWER_DISCONNECTED)
                // 电力使用情况总结
                addAction(Intent.ACTION_POWER_USAGE_SUMMARY)
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