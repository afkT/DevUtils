package afkt.project.feature.other_function.wifi

import dev.kotlin.engine.log.log_dTag
import android.annotation.SuppressLint
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Message
import dev.utils.app.wifi.WifiHotUtils
import dev.utils.app.wifi.WifiUtils

/**
 * detail: 封装 Wifi 热点快捷操作工具类
 * @author Ttt
 * 开启热点前必须获取到所需权限
 */
class QuickWifiHotUtils(
    // Wifi 工具类
    private val wifiUtils: WifiUtils,
    // Wifi 热点工具类
    private val wifiHotUtils: WifiHotUtils
) {
    // 日志 TAG
    private val TAG = QuickWifiHotUtils::class.java.simpleName

    // 是否停止线程检查
    private var mStop = false

    // 是否停止开启检查
    private var mCheck = false

    // 是否线程检查热点连接状态
    private var mThreadCheckHot = false

    // 热点 SSID
    private var mHotSSID: String? = null

    // 热点密码
    private var mHotPwd: String? = null

    // 操作接口
    var mOperate: Operate? = null

    @SuppressLint("HandlerLeak")
    private val hotHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            // 如果属于需要销毁, 则全部不处理
            mOperate?.let {
                if (it.isFinishing) {
                    mStop = true
                    mCheck = false
                    mThreadCheckHot = false
                    return
                }
            }
            when (msg.what) {
                CLOSE_WIFI_SUCCESS -> {
                    log_dTag(
                        tag = TAG,
                        message = "hotHandler 关闭 Wifi 成功, 开启热点中"
                    )
                    // 停止线程检查
                    setWifiCheck(false)
                    // 开始进行线程检查
                    setWifiApCheck(true)
                    // 开启热点
                    Thread(startWifiSpotThread).start()
                }
                START_WIFISPOT_SUCCESS -> {
                    log_dTag(
                        tag = TAG,
                        message = "hotHandler 开启热点成功"
                    )
                    // 停止线程检查
                    setWifiApCheck(false)
                    // 需要检查连接状态
                    mThreadCheckHot = true
                    // 开启线程检查
                    Thread(hotCheckThread).start()
                }
                CHECK_HOT_CONN -> {
                    log_dTag(
                        tag = TAG,
                        message = "hotHandler 检查是否连接热点"
                    )
                    // 判断是否存在设备连接热点
                    val isConnectHot = wifiHotUtils.isConnectHot
                    // 如果存在, 则尝试连接
                    if (isConnectHot && mThreadCheckHot) {
                        // 表示不需要进行检查了
                        mThreadCheckHot = false
                        // 通过获取是否存在其他设备 - 手机连接上该热点
                        log_dTag(
                            tag = TAG,
                            message = "存在设备连接热点"
                        )
                    }
                }
            }
        }
    }

    /**
     * 设置 Wifi 线程检查状态
     * @param isCheck 是否检查
     */
    private fun setWifiCheck(isCheck: Boolean) {
        if (isCheck) {
            // 需要进行线程检查
            mStop = false
        } else {
            // 停止线程检查
            mStop = true
            // 删除上一个任务, 并且重新绑定任务
            hotHandler.removeCallbacks(closeWifiThread)
        }
    }

    /**
     * 设置 Wifi 热点线程检查状态
     * @param isCheck 是否检查
     */
    private fun setWifiApCheck(isCheck: Boolean) {
        if (isCheck) {
            // 需要进行线程检查
            this.mCheck = false
        } else {
            // 停止线程检查
            this.mCheck = true
            // 删除上一个任务, 并且重新绑定任务
            hotHandler.removeCallbacks(startWifiSpotThread)
        }
    }

    /**
     * 关闭 Wifi 检查线程
     */
    private val closeWifiThread: Thread = object : Thread() {
        override fun run() {
            if (mStop) return
            // 如果属于需要销毁, 则全部不处理
            mOperate?.let {
                if (it.isFinishing) {
                    mStop = true
                    mCheck = false
                    mThreadCheckHot = false
                    return
                }
            }

            // 是否延时检查
            var isPostDelayed = false
            when (wifiUtils.wifiState) {
                WifiManager.WIFI_STATE_ENABLED,
                WifiManager.WIFI_STATE_ENABLING -> {
                    // case WifiManager.WIFI_STATE_UNKNOWN: // 未知
                    isPostDelayed = true
                    log_dTag(
                        tag = TAG,
                        message = "Wifi 已打开、正在打开"
                    )
                    wifiUtils.closeWifi() // 关闭 Wifi
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    isPostDelayed = false
                    log_dTag(
                        tag = TAG,
                        message = "Wifi 已关闭"
                    )
                }
                WifiManager.WIFI_STATE_DISABLING -> {
                    isPostDelayed = true
                    log_dTag(
                        tag = TAG,
                        message = "Wifi 正在关闭"
                    )
                }
            }
            // 判断是否延时 0.4 秒进行开启热点
            if (isPostDelayed) {
                // 删除上一个任务, 并且重新绑定任务
                hotHandler.removeCallbacks(this)
                hotHandler.postDelayed(this, 400)
            } else { // 开启热点
                hotHandler.sendEmptyMessage(CLOSE_WIFI_SUCCESS)
            }
        }
    }

    /**
     * 开启 Wifi 热点线程
     */
    private val startWifiSpotThread: Thread = object : Thread() {
        @SuppressLint("MissingPermission")
        override fun run() {
            if (mCheck) return
            // 如果属于需要销毁, 则全部不处理
            mOperate?.let {
                if (it.isFinishing) {
                    mStop = true
                    mCheck = false
                    mThreadCheckHot = false
                    return
                }
            }
            // 是否延时检查
            val isPostDelayed = true
            when (wifiHotUtils.wifiApState) {
                WifiHotUtils.WIFI_AP_STATE_DISABLING -> {
                    log_dTag(
                        tag = TAG,
                        message = "Wifi 热点正在关闭"
                    )
                }
                WifiHotUtils.WIFI_AP_STATE_DISABLED -> {
                    log_dTag(
                        tag = TAG,
                        message = "Wifi 热点已关闭"
                    )
                    // 开启热点
                    val wifiConfiguration = WifiHotUtils.createWifiConfigToAp(mHotSSID, mHotPwd)
                    wifiHotUtils.startWifiAp(wifiConfiguration)
                }
                WifiHotUtils.WIFI_AP_STATE_ENABLING -> {
                    log_dTag(
                        tag = TAG,
                        message = "Wifi 热点正在打开"
                    )
                }
                WifiHotUtils.WIFI_AP_STATE_ENABLED -> {
                    log_dTag(
                        tag = TAG,
                        message = "Wifi 热点已打开"
                    )
                    log_dTag(
                        tag = TAG,
                        message = "ssid: ${wifiHotUtils.apWifiSSID}, pwd: ${wifiHotUtils.apWifiSSID}"
                    )
                }
                WifiHotUtils.WIFI_AP_STATE_FAILED -> {
                    log_dTag(
                        tag = TAG,
                        message = "Wifi热点状态未知"
                    )
                }
            }
            // 判断是否延时 0.4 秒进行开启热点
            if (isPostDelayed) {
                // 删除上一个任务, 并且重新绑定任务
                hotHandler.removeCallbacks(this)
                hotHandler.postDelayed(this, 400)
            } else { // 开启热点成功
                hotHandler.sendEmptyMessage(START_WIFISPOT_SUCCESS)
            }
        }
    }

    /**
     * 检查热点连接线程
     */
    private val hotCheckThread: Thread = object : Thread() {
        override fun run() {
            while (mThreadCheckHot) {
                // 如果属于需要销毁, 则全部不处理
                mOperate?.let {
                    if (it.isFinishing) {
                        mStop = true
                        mCheck = false
                        mThreadCheckHot = false
                        return
                    }
                }
                // 检查是否连接热点
                hotHandler.sendEmptyMessage(CHECK_HOT_CONN)
                try {
                    sleep(500)
                } catch (e: Exception) {
                }
            }
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 开启热点
     * @param ssid Wifi 名
     * @param pwd  Wifi 密码
     */
    fun openHotspot(
        ssid: String?,
        pwd: String?
    ) {
        log_dTag(
            tag = TAG,
            message = "openHotspot 开启热点 ssid: $ssid, pwd: $pwd"
        )
        mHotSSID = ssid
        mHotPwd = pwd
        // 如果开启了 Wifi 则进行关闭 Wifi
        if (wifiUtils.isOpenWifi) {
            // 开始进行线程检查
            setWifiCheck(true)
            // 开启线程
            Thread(closeWifiThread).start()
        } else { // 开启热点
            hotHandler.sendEmptyMessage(CLOSE_WIFI_SUCCESS)
        }
    }

    /**
     * 关闭热点
     */
    fun closeHotspot() {
        // 全部不处理
        mStop = true
        mCheck = false
        mThreadCheckHot = false
        // 关闭热点
        wifiHotUtils.closeWifiAp()
    }

    /**
     * detail: 操作接口
     * @author Ttt
     */
    interface Operate {
        /**
         * 是否需要销毁
         * @return `true` yes, `false` no
         */
        val isFinishing: Boolean
    }

    companion object {

        // 成功关闭 Wifi 准备开启热点
        private const val CLOSE_WIFI_SUCCESS = 103

        // 开启热点成功
        private const val START_WIFISPOT_SUCCESS = 104

        // 检查是否连接热点
        private const val CHECK_HOT_CONN = 105
    }
}