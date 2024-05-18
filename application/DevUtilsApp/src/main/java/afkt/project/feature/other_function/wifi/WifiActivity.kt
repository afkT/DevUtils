package afkt.project.feature.other_function.wifi

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.button.ButtonList.wifiButtonValues
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ButtonAdapter
import android.Manifest
import android.annotation.SuppressLint
import android.net.wifi.WifiConfiguration
import android.os.Build
import android.os.Handler
import android.os.Message
import com.therouter.router.Route
import dev.callback.DevItemClickCallback
import dev.engine.permission.IPermissionEngine
import dev.expand.engine.log.log_dTag
import dev.expand.engine.permission.permission_request
import dev.receiver.WifiReceiver
import dev.receiver.WifiReceiver.Companion.register
import dev.receiver.WifiReceiver.Companion.setListener
import dev.receiver.WifiReceiver.Companion.unregister
import dev.utils.app.AppUtils
import dev.utils.app.IntentUtils
import dev.utils.app.permission.PermissionUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.app.toast.ToastUtils
import dev.utils.app.wifi.WifiHotUtils
import dev.utils.app.wifi.WifiUtils

/**
 * detail: Wifi 相关 ( 热点 )
 * @author Ttt
 * Wifi 热点状态监听等可参考 [QuickWifiHotUtils]
 */
@Route(path = RouterPath.OTHER_FUNCTION.WifiActivity_PATH)
class WifiActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    // Wifi 工具类
    var wifiUtils = WifiUtils()

    // Wifi 热点工具类
    var wifiHotUtils = WifiHotUtils()

    // 热点名、密码
    var wifiHotSSID = "DevWifiAp"
    var wifiHotPwd = "123456789"

    // Android 8.0 开启热点不能多次点击
    var isOpenAPING = false

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onDestroy() {
        super.onDestroy()
        // 注销监听
        unregister()
    }

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        ButtonAdapter(wifiButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                @SuppressLint("MissingPermission")
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    when (buttonValue.type) {
                        ButtonValue.BTN_WIFI_OPEN -> {
                            if (wifiUtils.isOpenWifi) {
                                ToastTintUtils.error("Wifi 已打开")
                            } else {
                                showToast(wifiUtils.openWifi(), "打开成功", "打开失败")
                            }
                        }

                        ButtonValue.BTN_WIFI_CLOSE -> {
                            if (wifiUtils.isOpenWifi) {
                                showToast(wifiUtils.closeWifi(), "关闭成功", "关闭失败")
                            } else {
                                ToastTintUtils.error("Wifi 已关闭")
                            }
                        }

                        ButtonValue.BTN_WIFI_HOT_OPEN -> {
                            if (wifiHotUtils.isOpenWifiAp) {
                                ToastTintUtils.error("Wifi 热点已打开")
                            } else {
                                if (isOpenAPING) {
                                    ToastUtils.showShort("Wifi 热点开启中")
                                    return
                                }
                                // = 8.0 特殊处理 =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    permission_request(
                                        permissions = arrayOf(
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        ),
                                        callback = object : IPermissionEngine.Callback {
                                            override fun onGranted() {
                                                isOpenAPING = true
                                                // 设置热点 Wifi 监听
                                                wifiHotUtils.setOnWifiAPListener(object :
                                                    WifiHotUtils.OnWifiAPListener {
                                                    override fun onStarted(wifiConfiguration: WifiConfiguration) {
                                                        val wifiAp =
                                                            "ssid: ${wifiConfiguration.SSID}, pwd: ${wifiConfiguration.preSharedKey}"
                                                        TAG.log_dTag(
                                                            message = wifiAp
                                                        )
                                                        ToastTintUtils.success(wifiAp)
                                                        // 表示操作结束
                                                        isOpenAPING = false
                                                    }

                                                    override fun onStopped() {
                                                        TAG.log_dTag(
                                                            message = "关闭热点"
                                                        )
                                                        // 表示操作结束
                                                        isOpenAPING = false
                                                    }

                                                    override fun onFailed(reason: Int) {
                                                        TAG.log_dTag(
                                                            message = "热点异常 reason: $reason"
                                                        )
                                                        // 表示操作结束
                                                        isOpenAPING = false
                                                    }
                                                })
                                                // 密码必须大于等于 8 位
                                                val wifiConfiguration =
                                                    WifiHotUtils.createWifiConfigToAp(
                                                        wifiHotSSID, wifiHotPwd
                                                    )
                                                val success =
                                                    wifiHotUtils.startWifiAp(wifiConfiguration)
                                                showToast(success, "打开热点成功", "打开热点失败")
                                            }

                                            override fun onDenied(
                                                grantedList: List<String>,
                                                deniedList: List<String>,
                                                notFoundList: List<String>
                                            ) {
                                                ToastTintUtils.warning("开启热点需要定位权限")
                                            }
                                        }
                                    )
                                    return
                                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) { // 7.0 及以下需要 WRITE_SETTINGS 权限
                                    // 无法进行申请, 只能跳转到权限页面, 让用户开启
                                    // 获取写入设置权限 , 必须有这个权限, 否则无法开启
                                    if (!PermissionUtils.isGranted(Manifest.permission.WRITE_SETTINGS)) {
                                        ToastUtils.showShort("开启热点需要修改系统设置权限")
                                        // 如果没有权限则跳转过去
                                        AppUtils.startActivity(
                                            IntentUtils.getLaunchAppDetailsSettingsIntent(
                                                AppUtils.getPackageName()
                                            )
                                        )
                                        return
                                    }
                                }
                                // 密码必须大于等于 8 位
                                val wifiConfiguration =
                                    WifiHotUtils.createWifiConfigToAp(wifiHotSSID, wifiHotPwd)
//                            // 如果不需要密码, 则设置为非密码
//                            val wifiConfiguration =
//                                WifiHotUtils.createWifiConfigToAp("TttWifiAp1", null)
                                // 开启热点 ( Android 7.1 以上特殊处理 )
                                val success = wifiHotUtils.startWifiAp(wifiConfiguration)
                                showToast(success, "打开热点成功", "打开热点失败")
                            }
                        }

                        ButtonValue.BTN_WIFI_HOT_CLOSE -> {
                            if (wifiHotUtils.isOpenWifiAp) {
                                val success = wifiHotUtils.closeWifiAp()
                                showToast(success, "热点关闭成功", "热点关闭失败")
                            } else {
                                ToastTintUtils.error("Wifi 热点已关闭")
                            }
                        }

                        ButtonValue.BTN_WIFI_LISTENER_REGISTER -> {
                            register()
                            showToast(true, "注册监听成功, 请查看 Logcat")
                        }

                        ButtonValue.BTN_WIFI_LISTENER_UNREGISTER -> {
                            unregister()
                            showToast(true, "注销监听成功")
                        }

                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }).bindAdapter(binding.vidRv)
    }

    override fun initListener() {
        super.initListener()

        // 设置监听事件
        setListener(object : WifiReceiver.Listener {
            override fun onWifiSwitch(isOpenWifi: Boolean) { // Wifi 开关状态
                TAG.log_dTag(
                    message = "Wifi 是否打开: $isOpenWifi"
                )
            }

            override fun onIntoTrigger() {
                TAG.log_dTag(
                    message = "触发回调通知 ( 每次进入都通知 )"
                )
            }

            override fun onTrigger(what: Int) {
                when (what) {
                    WifiReceiver.WIFI_SCAN_FINISH -> {
                        TAG.log_dTag(
                            message = "startScan() 扫描附近 Wifi 结束触发"
                        )
                    }

                    WifiReceiver.WIFI_RSSI_CHANGED -> {
                        TAG.log_dTag(
                            message = "已连接的 Wifi 强度发生变化"
                        )
                    }

                    WifiReceiver.WIFI_ERROR_AUTHENTICATING -> {
                        TAG.log_dTag(
                            message = "Wifi 认证错误 ( 密码错误等 )"
                        )
                    }

                    WifiReceiver.WIFI_ERROR_UNKNOWN -> {
                        TAG.log_dTag(
                            message = "连接错误 ( 其他错误 )"
                        )
                    }

                    WifiReceiver.WIFI_STATE_ENABLED -> {
                        TAG.log_dTag(
                            message = "Wifi 已打开"
                        )
                    }

                    WifiReceiver.WIFI_STATE_ENABLING -> {
                        TAG.log_dTag(
                            message = "Wifi 正在打开"
                        )
                    }

                    WifiReceiver.WIFI_STATE_DISABLED -> {
                        TAG.log_dTag(
                            message = "Wifi 已关闭"
                        )
                    }

                    WifiReceiver.WIFI_STATE_DISABLING -> {
                        TAG.log_dTag(
                            message = "Wifi 正在关闭"
                        )
                    }

                    WifiReceiver.WIFI_STATE_UNKNOWN -> {
                        TAG.log_dTag(
                            message = "Wifi 状态未知"
                        )
                    }

                    WifiReceiver.CONNECTED -> {
                        TAG.log_dTag(
                            message = "Wifi 连接成功"
                        )
                    }

                    WifiReceiver.CONNECTING -> {
                        TAG.log_dTag(
                            message = "Wifi 连接中"
                        )
                    }

                    WifiReceiver.DISCONNECTED -> {
                        TAG.log_dTag(
                            message = "Wifi 连接失败、断开"
                        )
                    }

                    WifiReceiver.SUSPENDED -> {
                        TAG.log_dTag(
                            message = "Wifi 暂停、延迟"
                        )
                    }

                    WifiReceiver.UNKNOWN -> {
                        TAG.log_dTag(
                            message = "Wifi 未知"
                        )
                    }
                }
            }

            override fun onTrigger(
                what: Int,
                message: Message?
            ) { // Wifi 在连接过程的状态返回
                val ssid = message?.obj as? String
                when (what) {
                    WifiReceiver.CONNECTED -> {
                        TAG.log_dTag(
                            message = "连接 Wifi 成功: $ssid"
                        )
                    }

                    WifiReceiver.CONNECTING -> {
                        TAG.log_dTag(
                            message = "连接 Wifi 中: $ssid"
                        )
                    }

                    WifiReceiver.DISCONNECTED -> {
                        TAG.log_dTag(
                            message = "连接 Wifi 断开"
                        )
                    }

                    WifiReceiver.SUSPENDED -> {
                        TAG.log_dTag(
                            message = "连接 Wifi 暂停、延迟"
                        )
                    }

                    WifiReceiver.UNKNOWN -> {
                        TAG.log_dTag(
                            message = "连接 Wifi 状态未知"
                        )
                    }
                }
            }
        })
    }

    // ==========
    // = 检测任务 =
    // ==========

    // 检查热点状态
    private val CHECK_HOTSOPT_STATE = 100

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                // 判断是否打开了热点, 如果开启了则进行关闭
                CHECK_HOTSOPT_STATE -> {
                    if (wifiHotUtils.closeWifiApCheck(true)) {
                        // 进行延迟检查
                        Handler().postDelayed({
                            // 防止出现意外, 再次关闭
                            if (wifiHotUtils.closeWifiApCheck(true)) {
                                try {
                                    // 堵塞 1.5 秒
                                    Thread.sleep(1500)
                                } catch (ignored: Exception) {
                                }
                            }
                            // 防止页面已经关闭
                            if (!isFinishing) {
                                // 打开 Wifi
                                wifiUtils.openWifi()
                            }
                        }, 1500)
                    } else { // 如果没有开启热点, 则判断是否开启 Wifi
                        // 判断是否开启 Wifi
                        if (!wifiUtils.isOpenWifi) {
                            wifiUtils.openWifi()
                        }
                    }
                }
            }
        }
    }
}