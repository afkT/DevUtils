package afkt.project.util

import android.annotation.SuppressLint
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Message
import dev.engine.log.DevLogEngine
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
    private var isStop = false

    // 是否停止开启检查
    private var isCheck = false

    // 是否线程检查热点连接状态
    private var isThreadCheckHot = false

    // 热点 SSID
    private var hotSSID: String? = null

    // 热点密码
    private var hotPwd: String? = null

    // 操作接口
    var operate: Operate? = null

    private val hotHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            // 如果属于需要销毁, 则全部不处理
            operate?.let {
                if (it.isFinishing) {
                    isStop = true
                    isCheck = false
                    isThreadCheckHot = false
                    return
                }
            }
            when (msg.what) {
                CLOSE_WIFI_SUCCESS -> {
                    // 打印日志
                    DevLogEngine.getEngine().dTag(TAG, "hotHandler 关闭 Wifi 成功, 开启热点中")
                    // 停止线程检查
                    setWifiCheck(false)
                    // 开始进行线程检查
                    setWifiApCheck(true)
                    // 开启热点
                    Thread(startWifiSpotThread).start()
                }
                START_WIFISPOT_SUCCESS -> {
                    // 打印日志
                    DevLogEngine.getEngine().dTag(TAG, "hotHandler 开启热点成功")
                    // 停止线程检查
                    setWifiApCheck(false)
                    // 需要检查连接状态
                    isThreadCheckHot = true
                    // 开启线程检查
                    Thread(hotCheckThread).start()
                }
                CHECK_HOT_CONN -> {
                    // 打印日志
                    DevLogEngine.getEngine().dTag(TAG, "hotHandler 检查是否连接热点")
                    // 判断是否存在设备连接热点
                    val isConnectHot = wifiHotUtils.isConnectHot
                    // 如果存在, 则尝试连接
                    if (isConnectHot && isThreadCheckHot) {
                        // 表示不需要进行检查了
                        isThreadCheckHot = false
                        // 通过获取是否存在其他设备 - 手机连接上该热点
                        // 打印日志
                        DevLogEngine.getEngine().dTag(TAG, "存在设备连接热点")
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
            isStop = false
        } else {
            // 停止线程检查
            isStop = true
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
            this.isCheck = false
        } else {
            // 停止线程检查
            this.isCheck = true
            // 删除上一个任务, 并且重新绑定任务
            hotHandler.removeCallbacks(startWifiSpotThread)
        }
    }

    /**
     * 关闭 Wifi 检查线程
     */
    private val closeWifiThread: Thread = object : Thread() {
        override fun run() {
            if (isStop) return
            // 如果属于需要销毁, 则全部不处理
            operate?.let {
                if (it.isFinishing) {
                    isStop = true
                    isCheck = false
                    isThreadCheckHot = false
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
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 已打开、正在打开")
                    wifiUtils.closeWifi() // 关闭 Wifi
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    isPostDelayed = false
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 已关闭")
                }
                WifiManager.WIFI_STATE_DISABLING -> {
                    isPostDelayed = true
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 正在关闭")
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
            if (isCheck) return
            // 如果属于需要销毁, 则全部不处理
            operate?.let {
                if (it.isFinishing) {
                    isStop = true
                    isCheck = false
                    isThreadCheckHot = false
                    return
                }
            }
            // 是否延时检查
            val isPostDelayed = true
            when (wifiHotUtils.wifiApState) {
                WifiHotUtils.WIFI_AP_STATE_DISABLING -> {
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 热点正在关闭")
                }
                WifiHotUtils.WIFI_AP_STATE_DISABLED -> {
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 热点已关闭")
                    // 开启热点
                    val wifiConfiguration = WifiHotUtils.createWifiConfigToAp(hotSSID, hotPwd)
                    wifiHotUtils.startWifiAp(wifiConfiguration)
                }
                WifiHotUtils.WIFI_AP_STATE_ENABLING -> {
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 热点正在打开")
                }
                WifiHotUtils.WIFI_AP_STATE_ENABLED -> {
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 热点已打开")
                    val wifiap = "ssid: ${wifiHotUtils.apWifiSSID}, pwd: ${wifiHotUtils.apWifiSSID}"
                    DevLogEngine.getEngine().dTag(TAG, wifiap)
                }
                WifiHotUtils.WIFI_AP_STATE_FAILED -> {
                    DevLogEngine.getEngine().dTag(TAG, "Wifi热点状态未知")
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
            while (isThreadCheckHot) {
                // 如果属于需要销毁, 则全部不处理
                operate?.let {
                    if (it.isFinishing) {
                        isStop = true
                        isCheck = false
                        isThreadCheckHot = false
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
        // 打印日志
        DevLogEngine.getEngine().dTag(TAG, "openHotspot 开启热点 ssid: %s, pwd: %s", ssid, pwd)
        hotSSID = ssid
        hotPwd = pwd
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
        isStop = true
        isCheck = false
        isThreadCheckHot = false
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