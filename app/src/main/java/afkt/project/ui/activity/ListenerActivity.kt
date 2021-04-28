package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityCommonTipsBinding
import afkt.project.model.item.ButtonList.listenerButtonValues
import afkt.project.model.item.ButtonValue
import afkt.project.ui.adapter.ButtonAdapter
import android.os.Handler
import android.os.Message
import android.telephony.SmsMessage
import android.view.OrientationEventListener
import dev.callback.DevItemClickCallback
import dev.engine.log.DevLogEngine
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
import dev.utils.app.helper.ViewHelper
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 事件 / 广播监听 ( 网络状态、屏幕旋转等 )
 * @author Ttt
 */
class ListenerActivity : BaseActivity<ActivityCommonTipsBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_common_tips

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

    override fun initValue() {
        super.initValue()

        val view = ViewUtils.inflate(R.layout.base_view_textview)
        ViewHelper.get().setText(view, "单击绑定, 长按注销")
            .setTextColor(view, ResourceUtils.getColor(R.color.gray))
        binding.vidActLinear.addView(view)

        // 初始化布局管理器、适配器
        val buttonAdapter = ButtonAdapter(listenerButtonValues)
        binding.vidBaseRecy.vidBvrRecy.adapter = buttonAdapter
        buttonAdapter.itemCallback = object : DevItemClickCallback<ButtonValue>() {
            override fun onItemClick(
                buttonValue: ButtonValue,
                param: Int
            ) {
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
            }

            override fun onItemLongClick(
                buttonValue: ButtonValue,
                param: Int
            ) {
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
        }
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
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 是否打开: %s", isOpenWifi)
                }

                override fun onIntoTrigger() {
                    DevLogEngine.getEngine().dTag(TAG, "触发回调通知 ( 每次进入都通知 )")
                }

                override fun onTrigger(what: Int) {
                    when (what) {
                        WifiReceiver.WIFI_SCAN_FINISH -> {
                            DevLogEngine.getEngine().dTag(TAG, "startScan() 扫描附近 Wifi 结束触发")
                        }
                        WifiReceiver.WIFI_RSSI_CHANGED -> {
                            DevLogEngine.getEngine().dTag(TAG, "已连接的 Wifi 强度发生变化")
                        }
                        WifiReceiver.WIFI_ERROR_AUTHENTICATING -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 认证错误 ( 密码错误等 )")
                        }
                        WifiReceiver.WIFI_ERROR_UNKNOWN -> {
                            DevLogEngine.getEngine().dTag(TAG, "连接错误 ( 其他错误 )")
                        }
                        WifiReceiver.WIFI_STATE_ENABLED -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 已打开")
                        }
                        WifiReceiver.WIFI_STATE_ENABLING -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 正在打开")
                        }
                        WifiReceiver.WIFI_STATE_DISABLED -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 已关闭")
                        }
                        WifiReceiver.WIFI_STATE_DISABLING -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 正在关闭")
                        }
                        WifiReceiver.WIFI_STATE_UNKNOWN -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 状态未知")
                        }
                        WifiReceiver.CONNECTED -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 连接成功")
                        }
                        WifiReceiver.CONNECTING -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 连接中")
                        }
                        WifiReceiver.DISCONNECTED -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 连接失败、断开")
                        }
                        WifiReceiver.SUSPENDED -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 暂停、延迟")
                        }
                        WifiReceiver.UNKNOWN -> {
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 未知")
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
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 成功: %s", ssid)
                        }
                        WifiReceiver.CONNECTING -> {
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 中: %s", ssid)
                        }
                        WifiReceiver.DISCONNECTED -> {
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 断开")
                        }
                        WifiReceiver.SUSPENDED -> {
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 暂停、延迟")
                        }
                        WifiReceiver.UNKNOWN -> {
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 状态未知")
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
                    DevLogEngine.getEngine().dTag(TAG, "网络连接状态 %s", state)
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
                            DevLogEngine.getEngine().dTag(TAG, "播出电话: %s", number)
                        }
                        CallState.OUTGOING_END -> {
                            DevLogEngine.getEngine().dTag(TAG, "播出电话结束: %s", number)
                        }
                        CallState.INCOMING_RING -> {
                            DevLogEngine.getEngine().dTag(TAG, "接入电话铃响: %s", number)
                        }
                        CallState.INCOMING -> {
                            DevLogEngine.getEngine().dTag(TAG, "接入通话中: %s", number)
                        }
                        CallState.INCOMING_END -> {
                            DevLogEngine.getEngine().dTag(TAG, "接入通话完毕: %s", number)
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
                    DevLogEngine.getEngine().dTag(
                        TAG, "onMessage\nmsg: %s\nfromAddress: %s\nserviceCenterAddress: %s",
                        msg, fromAddress, serviceCenterAddress
                    )
                }

                override fun onMessage(msg: SmsMessage?) {
                    DevLogEngine.getEngine().dTag(TAG, "onMessage\nSmsMessage: %s", msg.toString())
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
                    DevLogEngine.getEngine().dTag(TAG, "onTimeZoneChanged: 时区改变")
                }

                override fun onTimeChanged() {
                    DevLogEngine.getEngine().dTag(TAG, "onTimeChanged: 时间改变")
                }

                override fun onTimeTick() {
                    DevLogEngine.getEngine().dTag(TAG, "onTimeTick: 分钟改变")
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
                    DevLogEngine.getEngine().dTag(TAG, "screenOn: 用户打开屏幕 - 屏幕变亮")
                }

                override fun screenOff() {
                    DevLogEngine.getEngine().dTag(TAG, "screenOff: 锁屏触发")
                }

                override fun userPresent() {
                    DevLogEngine.getEngine().dTag(TAG, "userPresent: 用户解锁触发")
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
                                    DevLogEngine.getEngine().dTag(TAG, "横屏")
                                    // 重置时间,防止多次触发
                                    mOrientationTime = System.currentTimeMillis()
                                    // 跳转到横屏, 并且关闭监听
                                    //Intent intent = new Intent(mContext, Activity.class);
                                    //mContext.startActivity(intent);
                                }
                            } else if (orientation == 2) { // 竖屏
                                DevLogEngine.getEngine().dTag(TAG, "竖屏")
                            }
                        }
                    }
                }
            })
        }
    }

    // 判断是否竖屏
    private var isPortrait = true

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
            mOrientationEventListener = object : OrientationEventListener(mContext) {
                override fun onOrientationChanged(rotation: Int) {
                    if (rotation >= 0 && rotation <= 30 || rotation >= 330) {
                        DevLogEngine.getEngine().dTag(TAG, "竖屏拍摄")
                        isPortrait = true
                        // 竖屏拍摄
                        if (mRotationFlag != 0) {
                            // 这是竖屏视频需要的角度
                            mRotationRecord = 90
                            // 这是记录当前角度的 flag
                            mRotationFlag = 0
                        }
                    } else if (rotation >= 230 && rotation <= 310) {
                        DevLogEngine.getEngine().dTag(TAG, "横屏拍摄")
                        isPortrait = false
                        // 横屏拍摄
                        if (mRotationFlag != 90) {
                            // 这是正横屏视频需要的角度
                            mRotationRecord = 0
                            // 这是记录当前角度的 flag
                            mRotationFlag = 90
                        }
                    } else if (rotation > 30 && rotation < 135) {
                        DevLogEngine.getEngine().dTag(TAG, "反横屏拍摄")
                        isPortrait = false
                        // 反横屏拍摄
                        if (mRotationFlag != 270) {
                            // 这是反横屏视频需要的角度
                            mRotationRecord = 180
                            // 这是记录当前角度的 flag
                            mRotationFlag = 270
                        }
                    } else if (rotation > 135 && rotation < 230) {
                        DevLogEngine.getEngine().dTag(TAG, "反竖屏拍摄")
                        isPortrait = true
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
            DevLogEngine.getEngine().eTag(TAG, "rotaListener2")
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
                    DevLogEngine.getEngine().dTag(TAG, "电量改变通知 level: %s", level)
                }

                override fun onBatteryLow(level: Int) {
                    DevLogEngine.getEngine().dTag(TAG, "电量低通知 level: %s", level)
                }

                override fun onBatteryOkay(level: Int) {
                    DevLogEngine.getEngine().dTag(TAG, "电量从低变回高通知 level: %s", level)
                }

                override fun onPowerConnected(
                    level: Int,
                    isConnected: Boolean
                ) {
                    DevLogEngine.getEngine()
                        .dTag(TAG, "充电状态改变通知 level: %s, 是否充电中: %s", level, isConnected)
                }

                override fun onPowerUsageSummary(level: Int) {
                    DevLogEngine.getEngine().dTag(TAG, "电力使用情况总结 level: %s", level)
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
                    DevLogEngine.getEngine().dTag(TAG, "应用安装 packageName: %s", packageName)
                }

                override fun onReplaced(packageName: String?) {
                    DevLogEngine.getEngine().dTag(TAG, "应用更新 packageName: %s", packageName)
                }

                override fun onRemoved(packageName: String?) {
                    DevLogEngine.getEngine().dTag(TAG, "应用卸载 packageName: %s", packageName)
                }
            })
            // 注册监听
            AppStateReceiver.register()
        }
    }
}