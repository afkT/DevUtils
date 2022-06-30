package dev.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Message
import android.os.Parcelable
import dev.utils.LogPrintUtils
import dev.utils.app.AppUtils
import dev.utils.app.wifi.WifiUtils

/**
 * detail: Wifi 监听广播
 * @author Ttt
 * 所需权限
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 */
class WifiReceiver private constructor() : BroadcastReceiver() {

    // 日志 TAG
    private val TAG = WifiReceiver::class.java.simpleName

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        // 打印触发的广播
        LogPrintUtils.dTag(TAG, "onReceive Action: %s", action)
        // 触发回调通知 ( 每次进入都通知 )
        sListener?.onIntoTrigger()
        // 内部处理
        innerReceive(context, intent)
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
         * 触发回调通知 ( 每次进入都通知 )
         */
        fun onIntoTrigger() {}

        /**
         * 触发回调通知
         * @param what 触发类型
         */
        fun onTrigger(what: Int)

        /**
         * 触发回调通知 ( Wifi 连接过程的状态 )
         * @param what 触发类型
         * @param msg  触发信息
         */
        fun onTrigger(
            what: Int,
            msg: Message?
        )

        /**
         * Wifi 开关状态
         * @param isOpenWifi 是否打开 Wifi
         */
        fun onWifiSwitch(isOpenWifi: Boolean)
    }

    // =============
    // = 对外公开方法 =
    // =============

    companion object {

        private val sReceiver = WifiReceiver()

        private var sListener: Listener? = null

        /**
         * 注册广播监听
         */
        fun register() {
            IntentFilter().apply {
                // 当调用 WifiManager 的 startScan() 方法, 扫描结束后, 系统会发出改 Action 广播
                addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
                // 当前连接的 Wifi 强度发生变化触发
                addAction(WifiManager.RSSI_CHANGED_ACTION)
                // Wifi 在连接过程的状态返回
                addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
                // 监听 Wifi 的打开与关闭等状态, 与 Wifi 的连接无关
                addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
                // 发送 Wifi 连接的过程信息, 如果出错 ERROR 信息才会收到, 连接 Wifi 时触发, 触发多次
                addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)
                // 判断是否 Wifi 打开了, 变化触发一次
                addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)
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

        // =======
        // = 常量 =
        // =======

        private const val BASE = 302030

        // startScan() 扫描附近 Wifi 结束触发
        const val WIFI_SCAN_FINISH = BASE + 1

        // 已连接的 Wifi 强度发生变化
        const val WIFI_RSSI_CHANGED = BASE + 2

        // Wifi 认证错误 ( 密码错误等 )
        const val WIFI_ERROR_AUTHENTICATING = BASE + 3

        // 连接错误 ( 其他错误 )
        const val WIFI_ERROR_UNKNOWN = BASE + 4

        // Wifi 已打开
        const val WIFI_STATE_ENABLED = BASE + 5

        // Wifi 正在打开
        const val WIFI_STATE_ENABLING = BASE + 6

        // Wifi 已关闭
        const val WIFI_STATE_DISABLED = BASE + 7

        // Wifi 正在关闭
        const val WIFI_STATE_DISABLING = BASE + 8

        // Wifi 状态未知
        const val WIFI_STATE_UNKNOWN = BASE + 9

        // Wifi 连接成功
        const val CONNECTED = BASE + 10

        // Wifi 连接中
        const val CONNECTING = BASE + 11

        // Wifi 连接失败、断开
        const val DISCONNECTED = BASE + 12

        // Wifi 暂停、延迟
        const val SUSPENDED = BASE + 13

        // Wifi 未知
        const val UNKNOWN = BASE + 14
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 收到广播时处理
     * @param context Context
     * @param intent Intent
     */
    @SuppressLint("MissingPermission")
    private fun innerReceive(
        context: Context,
        intent: Intent
    ) {
        // 判断类型
        when (intent.action) {
            // 当调用 WifiManager 的 startScan() 方法, 扫描结束后, 系统会发出改 Action 广播
            WifiManager.SCAN_RESULTS_AVAILABLE_ACTION -> {
                sListener?.onTrigger(WIFI_SCAN_FINISH)
            }
            // 当前连接的 Wifi 强度发生变化触发
            WifiManager.RSSI_CHANGED_ACTION -> {
                sListener?.onTrigger(WIFI_RSSI_CHANGED)
            }
            // 发送 Wifi 连接的过程信息, 如果出错 ERROR 信息才会收到, 连接 Wifi 时触发, 触发多次
            WifiManager.SUPPLICANT_STATE_CHANGED_ACTION -> {
                // 出现错误状态, 则获取错误状态
                val wifiErrorCode = intent.getIntExtra(
                    WifiManager.EXTRA_SUPPLICANT_ERROR, 0
                )
                // 判断错误状态
                when (wifiErrorCode) {
                    // 认证错误, 如密码错误等
                    WifiManager.ERROR_AUTHENTICATING -> {
                        sListener?.onTrigger(WIFI_ERROR_AUTHENTICATING)
                    }
                    // 连接错误 ( 其他错误 )
                    else -> {
                        sListener?.onTrigger(WIFI_ERROR_UNKNOWN)
                    }
                }
            }
            // 监听 Wifi 的打开与关闭等状态, 与 Wifi 的连接无关
            WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                // 获取 Wifi 状态
                val wifiState = intent.getIntExtra(
                    WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN
                )
                when (wifiState) {
                    // 已打开
                    WifiManager.WIFI_STATE_ENABLED -> sListener?.onTrigger(WIFI_STATE_ENABLED)
                    // 正在打开
                    WifiManager.WIFI_STATE_ENABLING -> sListener?.onTrigger(WIFI_STATE_ENABLING)
                    // 已关闭
                    WifiManager.WIFI_STATE_DISABLED -> sListener?.onTrigger(WIFI_STATE_DISABLED)
                    // 正在关闭
                    WifiManager.WIFI_STATE_DISABLING -> sListener?.onTrigger(WIFI_STATE_DISABLING)
                    // 未知
                    WifiManager.WIFI_STATE_UNKNOWN -> sListener?.onTrigger(WIFI_STATE_UNKNOWN)
                }
            }
            // Wifi 在连接过程的状态返回
            WifiManager.NETWORK_STATE_CHANGED_ACTION -> {
                (intent.getParcelableExtra<Parcelable>(WifiManager.EXTRA_NETWORK_INFO) as? NetworkInfo)?.let { networkInfo ->
                    // 获取连接的状态
                    val state = networkInfo.state
                    // 通知消息
                    val msg = Message()
                    // 当前连接的 SSID
                    msg.obj = WifiUtils.getSSID()
                    msg.what = UNKNOWN
                    // 判断连接状态
                    when (state) {
                        // 连接成功
                        NetworkInfo.State.CONNECTED -> msg.what = CONNECTED
                        // 连接中
                        NetworkInfo.State.CONNECTING -> msg.what = CONNECTING
                        // 连接失败、断开
                        NetworkInfo.State.DISCONNECTED -> msg.what = DISCONNECTED
                        // 暂停、延迟
                        NetworkInfo.State.SUSPENDED -> msg.what = SUSPENDED
                        // 未知
                        NetworkInfo.State.UNKNOWN -> msg.what = UNKNOWN
                        else -> msg.what = UNKNOWN
                    }
                    // 触发回调
                    sListener?.onTrigger(msg.what, msg)
                }
            }
            // 判断是否 Wifi 打开了, 变化触发一次
            WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION -> {
                // 判断是否打开 Wifi
                val isOpenWifi = intent.getBooleanExtra(
                    WifiManager.EXTRA_SUPPLICANT_CONNECTED, false
                )
                // 触发回调
                sListener?.onWifiSwitch(isOpenWifi)
            }
        }
    }
}