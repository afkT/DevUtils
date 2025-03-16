package afkt.project.feature.other_function.listener

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.bindAdapterLong
import afkt.project.data_model.button.ButtonList.listenerButtonValues
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityCommonTipsBinding
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.telephony.SmsMessage
import android.view.OrientationEventListener
import com.therouter.router.Route
import dev.expand.engine.log.log_dTag
import dev.expand.engine.log.log_eTag
import dev.receiver.*
import dev.receiver.AppStateReceiver.Companion.setListener
import dev.receiver.BatteryReceiver.Companion.setListener
import dev.receiver.NetWorkReceiver.Companion.setListener
import dev.receiver.PhoneReceiver.CallState
import dev.receiver.PhoneReceiver.Companion.setListener
import dev.receiver.SMSReceiver.Companion.setListener
import dev.receiver.ScreenReceiver.Companion.setListener
import dev.receiver.TimeReceiver.Companion.setListener
import dev.receiver.WifiReceiver.Companion.setListener
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.assist.ScreenSensorAssist
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 事件 / 广播监听 ( 网络状态、屏幕旋转等 )
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.ListenerActivity_PATH)
class ListenerActivity : BaseProjectActivity<ActivityCommonTipsBinding, BaseProjectViewModel>(
    R.layout.activity_common_tips, simple_Agile = {
        if (it is ListenerActivity) {
            it.apply {
                val view = ViewUtils.inflate(this, R.layout.base_view_textview)
                ViewHelper.get().setText("单击绑定, 长按注销", view)
                    .setTextColors(ResourceUtils.getColor(R.color.gray), view)
                binding.vidLl.addView(view)

                binding.vidInclude.vidRv.bindAdapterLong(
                    listenerButtonValues, { buttonValue ->
                        when (buttonValue.type) {
                            ButtonValue.BTN_WIFI_LISTENER -> wifiListener(true)
                            ButtonValue.BTN_NETWORK_LISTENER -> netListener(true)
                            ButtonValue.BTN_PHONE_LISTENER -> phoneListener(true)
                            ButtonValue.BTN_SMS_LISTENER -> smsListener(true)
                            ButtonValue.BTN_TIME_LISTENER -> timeListener(true)
                            ButtonValue.BTN_SCREEN_LISTENER -> screenListener(true)
                            ButtonValue.BTN_ROTA_LISTENER -> rotaListener(true)
                            ButtonValue.BTN_ROTA2_LISTENER -> rotaListener2(true)
                            ButtonValue.BTN_BATTERY_LISTENER -> batteryListener(true)
                            ButtonValue.BTN_APP_STATE_LISTENER -> appStateListener(true)
                            else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                        }
                    }, { buttonValue ->
                        when (buttonValue.type) {
                            ButtonValue.BTN_WIFI_LISTENER -> wifiListener(false)
                            ButtonValue.BTN_NETWORK_LISTENER -> netListener(false)
                            ButtonValue.BTN_PHONE_LISTENER -> phoneListener(false)
                            ButtonValue.BTN_SMS_LISTENER -> smsListener(false)
                            ButtonValue.BTN_TIME_LISTENER -> timeListener(false)
                            ButtonValue.BTN_SCREEN_LISTENER -> screenListener(false)
                            ButtonValue.BTN_ROTA_LISTENER -> rotaListener(false)
                            ButtonValue.BTN_ROTA2_LISTENER -> rotaListener2(false)
                            ButtonValue.BTN_BATTERY_LISTENER -> batteryListener(false)
                            ButtonValue.BTN_APP_STATE_LISTENER -> appStateListener(false)
                            else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                        }
                    }
                )
            }
        }
    }
) {

    override fun onDestroy() {
        super.onDestroy()
        // 注销监听
        WifiReceiver.unregister()
        NetWorkReceiver.unregister()
        PhoneReceiver.unregister()
        SMSReceiver.unregister()
        TimeReceiver.unregister()
        ScreenReceiver.unregister()
        BatteryReceiver.unregister()
        AppStateReceiver.unregister()
        screenSensorAssist.stop()
        mOrientationEventListener?.disable()
    }

    // ============
    // = Listener =
    // ============

    /**
     * Wifi 监听
     * @param isBind 是否绑定
     */
    private fun wifiListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销 Wifi 监听成功")
            // 清空回调
            WifiReceiver.setListener(null)
            // 注销监听
            WifiReceiver.unregister()
        } else {
            ToastTintUtils.success("绑定 Wifi 监听成功, 请查看 Logcat")
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
            // 注册监听
            WifiReceiver.register()
        }
    }

    /**
     * 网络监听
     * @param isBind 是否绑定
     */
    private fun netListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销网络监听成功")
            // 清空回调
            NetWorkReceiver.setListener(null)
            // 注销监听
            NetWorkReceiver.unregister()
        } else {
            ToastTintUtils.success("绑定网络监听成功, 请查看 Logcat")
            // 设置监听事件
            setListener(object : NetWorkReceiver.Listener {
                override fun onNetworkState(nType: Int) {
                    var state = ""
                    when (nType) {
                        NetWorkReceiver.NET_WIFI -> state = "Wifi"
                        NetWorkReceiver.NET_MOBILE -> state = "移动网络"
                        NetWorkReceiver.NO_NETWORK -> state = "( 无网络 / 未知 ) 状态"
                    }
                    TAG.log_dTag(
                        message = "网络连接状态 $state"
                    )
                }
            })
            // 注册监听
            NetWorkReceiver.register()
        }
    }

    /**
     * 电话监听
     * @param isBind 是否绑定
     */
    private fun phoneListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销电话监听成功")
            // 清空回调
            PhoneReceiver.setListener(null)
            // 注销监听
            PhoneReceiver.unregister()
        } else {
            ToastTintUtils.success("绑定电话监听成功, 请查看 Logcat")
            // 设置监听事件
            setListener(object : PhoneReceiver.Listener {
                override fun onPhoneStateChanged(
                    callState: CallState,
                    number: String?
                ) {
                    when (callState) {
                        CallState.OUTGOING -> {
                            TAG.log_dTag(
                                message = "播出电话: $number"
                            )
                        }

                        CallState.OUTGOING_END -> {
                            TAG.log_dTag(
                                message = "播出电话结束: $number"
                            )
                        }

                        CallState.INCOMING_RING -> {
                            TAG.log_dTag(
                                message = "接入电话铃响: $number"
                            )
                        }

                        CallState.INCOMING -> {
                            TAG.log_dTag(
                                message = "接入通话中: $number"
                            )
                        }

                        CallState.INCOMING_END -> {
                            TAG.log_dTag(
                                message = "接入通话完毕: $number"
                            )
                        }
                    }
                }
            })
            // 注册监听
            PhoneReceiver.register()
        }
    }

    /**
     * 短信监听
     * @param isBind 是否绑定
     */
    private fun smsListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销短信监听成功")
            // 清空回调
            SMSReceiver.setListener(null)
            // 注销监听
            SMSReceiver.unregister()
        } else {
            ToastTintUtils.success("绑定短信监听成功, 请查看 Logcat")
            // 设置监听事件
            setListener(object : SMSReceiver.Listener {
                override fun onMessage(
                    msg: String?,
                    fromAddress: String?,
                    serviceCenterAddress: String?
                ) {
                    TAG.log_dTag(
                        message = "onMessage\nmsg: %s\nfromAddress: %s\nserviceCenterAddress: %s",
                        args = arrayOf(msg, fromAddress, serviceCenterAddress)
                    )
                }

                override fun onMessage(msg: SmsMessage?) {
                    TAG.log_dTag(
                        message = "onMessage\nSmsMessage: ${msg.toString()}"
                    )
                }
            })
            // 注册监听
            SMSReceiver.register()
        }
    }

    /**
     * 时区、时间监听
     * @param isBind 是否绑定
     */
    private fun timeListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销时区、时间监听成功")
            // 清空回调
            TimeReceiver.setListener(null)
            // 注销监听
            TimeReceiver.unregister()
        } else {
            ToastTintUtils.success("绑定时区、时间监听成功, 请查看 Logcat")
            // 设置监听事件
            setListener(object : TimeReceiver.Listener {
                override fun onTimeZoneChanged() {
                    TAG.log_dTag(
                        message = "onTimeZoneChanged: 时区改变"
                    )
                }

                override fun onTimeChanged() {
                    TAG.log_dTag(
                        message = "onTimeChanged: 时间改变"
                    )
                }

                override fun onTimeTick() {
                    TAG.log_dTag(
                        message = "onTimeTick: 分钟改变"
                    )
                }
            })
            // 注册监听
            TimeReceiver.register()
        }
    }

    /**
     * 屏幕监听
     * @param isBind 是否绑定
     */
    private fun screenListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销屏幕监听成功")
            // 清空回调
            ScreenReceiver.setListener(null)
            // 注销监听
            ScreenReceiver.unregister()
        } else {
            ToastTintUtils.success("绑定屏幕监听成功, 请查看 Logcat")
            // 设置监听事件
            setListener(object : ScreenReceiver.Listener {
                override fun screenOn() {
                    TAG.log_dTag(
                        message = "screenOn: 用户打开屏幕 - 屏幕变亮"
                    )
                }

                override fun screenOff() {
                    TAG.log_dTag(
                        message = "screenOff: 锁屏触发"
                    )
                }

                override fun userPresent() {
                    TAG.log_dTag(
                        message = "userPresent: 用户解锁触发"
                    )
                }
            })
            // 注册监听
            ScreenReceiver.register()
        }
    }

    // 重力传感器辅助类
    private val screenSensorAssist = ScreenSensorAssist()

    // 切屏时间
    private var mOrientationTime = 0L

    /**
     * 屏幕旋转监听 ( 重力传感器 )
     * @param isBind 是否绑定
     */
    @SuppressLint("HandlerLeak")
    private fun rotaListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销屏幕旋转监听 ( 重力传感器 ) 成功")
            // 注销监听
            screenSensorAssist.stop()
        } else {
            ToastTintUtils.success("绑定屏幕旋转监听 ( 重力传感器 ) 成功, 请查看 Logcat")
            // 注册监听
            screenSensorAssist.start(object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    when (msg.what) {
                        ScreenSensorAssist.CHANGE_ORIENTATION_WHAT -> {
                            if (!screenSensorAssist.isAllowChange) { // 如果不允许切屏, 则不显示
                                return
                            } else if (isFinishing) { // 如果页面关闭了
                                return
                            }
                            // 获取触发的方向
                            val orientation = msg.arg1
                            // 判断方向
                            if (orientation == 1) { // 横屏
                                // 当前时间 - 切屏的时间大于 1.5 秒间隔才进行处理
                                if (System.currentTimeMillis() - mOrientationTime >= 1500) {
                                    TAG.log_dTag(
                                        message = "横屏"
                                    )
                                    // 重置时间,防止多次触发
                                    mOrientationTime = System.currentTimeMillis()
                                    // 跳转到横屏, 并且关闭监听
                                    //Intent intent = new Intent(mContext, Activity.class)
                                    //mContext.startActivity(intent)
                                }
                            } else if (orientation == 2) { // 竖屏
                                TAG.log_dTag(
                                    message = "竖屏"
                                )
                            }
                        }
                    }
                }
            })
        }
    }

    // 判断是否竖屏
    private var mPortrait = true

    // 录制角度记录值
    private var mRotationFlag = 90

    // 录制角度旋值
    private var mRotationRecord = 90

    // 旋转监听事件
    private var mOrientationEventListener: OrientationEventListener? = null

    /**
     * 屏幕旋转监听 ( OrientationEventListener )
     * @param isBind 是否绑定
     */
    private fun rotaListener2(isBind: Boolean) {
        if (mOrientationEventListener == null) {
            mOrientationEventListener = object : OrientationEventListener(mActivity) {
                override fun onOrientationChanged(rotation: Int) {
                    if (rotation in 0..30 || rotation >= 330) {
                        TAG.log_dTag(
                            message = "竖屏拍摄"
                        )
                        mPortrait = true
                        // 竖屏拍摄
                        if (mRotationFlag != 0) {
                            // 这是竖屏视频需要的角度
                            mRotationRecord = 90
                            // 这是记录当前角度的 flag
                            mRotationFlag = 0
                        }
                    } else if (rotation in 230..310) {
                        TAG.log_dTag(
                            message = "横屏拍摄"
                        )
                        mPortrait = false
                        // 横屏拍摄
                        if (mRotationFlag != 90) {
                            // 这是正横屏视频需要的角度
                            mRotationRecord = 0
                            // 这是记录当前角度的 flag
                            mRotationFlag = 90
                        }
                    } else if (rotation in 31..134) {
                        TAG.log_dTag(
                            message = "反横屏拍摄"
                        )
                        mPortrait = false
                        // 反横屏拍摄
                        if (mRotationFlag != 270) {
                            // 这是反横屏视频需要的角度
                            mRotationRecord = 180
                            // 这是记录当前角度的 flag
                            mRotationFlag = 270
                        }
                    } else if (rotation in 136..229) {
                        TAG.log_dTag(
                            message = "反竖屏拍摄"
                        )
                        mPortrait = true
                        // 竖屏拍摄
                        if (mRotationFlag != 360) {
                            // 这是竖屏视频需要的角度
                            mRotationRecord = 270
                            // 这是记录当前角度的 flag
                            mRotationFlag = 360
                        }
                    }
                }
            }
        }
        try {
            if (!isBind) { // 取反判断, 方便代码顺序查看
                ToastTintUtils.success("注销屏幕旋转监听 ( OrientationEventListener ) 成功")
                // 注销监听
                mOrientationEventListener?.disable()
            } else {
                ToastTintUtils.success("绑定屏幕旋转监听 ( OrientationEventListener ) 成功, 请查看 Logcat")
                // 注册监听
                mOrientationEventListener?.enable()
            }
        } catch (e: Exception) {
            TAG.log_eTag(
                message = "rotaListener2"
            )
        }
    }

    /**
     * 电量监听
     * @param isBind 是否绑定
     */
    private fun batteryListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销电量监听成功")
            // 清空回调
            BatteryReceiver.setListener(null)
            // 注销监听
            BatteryReceiver.unregister()
        } else {
            ToastTintUtils.success("绑定电量监听成功, 请查看 Logcat")
            // 设置监听事件
            setListener(object : BatteryReceiver.Listener {
                override fun onBatteryChanged(level: Int) {
                    TAG.log_dTag(
                        message = "电量改变通知 level: $level"
                    )
                }

                override fun onBatteryLow(level: Int) {
                    TAG.log_dTag(
                        message = "电量低通知 level: $level"
                    )
                }

                override fun onBatteryOkay(level: Int) {
                    TAG.log_dTag(
                        message = "电量从低变回高通知 level: $level"
                    )
                }

                override fun onPowerConnected(
                    level: Int,
                    isConnected: Boolean
                ) {
                    TAG.log_dTag(
                        message = "充电状态改变通知 level: %s, 是否充电中: %s",
                        args = arrayOf(level, isConnected)
                    )
                }

                override fun onPowerUsageSummary(level: Int) {
                    TAG.log_dTag(
                        message = "电力使用情况总结 level: $level"
                    )
                }
            })
            // 注册监听
            BatteryReceiver.register()
        }
    }

    /**
     * 应用状态监听
     * @param isBind 是否绑定
     */
    private fun appStateListener(isBind: Boolean) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销应用状态监听成功")
            // 清空回调
            AppStateReceiver.setListener(null)
            // 注销监听
            AppStateReceiver.unregister()
        } else {
            ToastTintUtils.success("绑定应用状态监听成功, 请查看 Logcat")
            // 设置监听事件
            setListener(object : AppStateReceiver.Listener {
                override fun onAdded(packageName: String?) {
                    TAG.log_dTag(
                        message = "应用安装 packageName: $packageName"
                    )
                }

                override fun onReplaced(packageName: String?) {
                    TAG.log_dTag(
                        message = "应用更新 packageName: $packageName"
                    )
                }

                override fun onRemoved(packageName: String?) {
                    TAG.log_dTag(
                        message = "应用卸载 packageName: $packageName"
                    )
                }
            })
            // 注册监听
            AppStateReceiver.register()
        }
    }
}