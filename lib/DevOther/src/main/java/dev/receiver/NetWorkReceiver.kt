package dev.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import dev.utils.LogPrintUtils
import dev.utils.app.AppUtils
import dev.utils.app.NetWorkUtils

/**
 * detail: 网络监听广播
 * @author Ttt
 */
class NetWorkReceiver private constructor() : BroadcastReceiver() {

    // 日志 TAG
    private val TAG = NetWorkReceiver::class.java.simpleName

    @SuppressLint("MissingPermission")
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        // 打印触发的广播
        LogPrintUtils.dTag(TAG, "onReceive Action: %s", action)
        // 网络连接状态改变时通知
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            try {
                // 设置连接类型
                mConnectState = getConnectType()
                // 触发事件
                sListener?.onNetworkState(mConnectState)
            } catch (e: Exception) {
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
         * 网络连接状态改变时通知
         * @param type 通知类型
         */
        fun onNetworkState(type: Int)
    }

    // =============
    // = 对外公开方法 =
    // =============

    companion object {

        private val sReceiver = NetWorkReceiver()

        private var sListener: Listener? = null

        private const val BASE = 202030

        // Wifi
        const val NET_WIFI = BASE + 1

        // 移动网络
        const val NET_MOBILE = BASE + 2

        // ( 无网络 / 未知 ) 状态
        const val NO_NETWORK = BASE + 3

        // 当前连接的状态
        private var mConnectState: Int = NO_NETWORK

        /**
         * 注册广播监听
         */
        fun register() {
            IntentFilter().apply {
                // 网络连接状态改变时通知
                addAction(ConnectivityManager.CONNECTIVITY_ACTION)
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

        /**
         * 是否连接网络
         * @return `true` yes, `false` no
         */
        fun isConnectNetWork(): Boolean {
            return mConnectState == NET_WIFI || mConnectState == NET_MOBILE
        }

        /**
         * 获取连接的网络类型
         * @return 连接的网络类型
         */
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        fun getConnectType(): Int {
            // -1 = 等于未知, 1 = Wifi, 2 = 移动网络
            return when (NetWorkUtils.getConnectType()) {
                1 -> NET_WIFI
                2 -> NET_MOBILE
                else -> NO_NETWORK
            }
        }
    }
}