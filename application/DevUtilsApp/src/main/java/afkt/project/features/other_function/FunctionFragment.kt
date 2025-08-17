package afkt.project.features.other_function

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionFunctionBinding
import android.view.View
import dev.utils.app.FlashlightUtils

/**
 * detail: 铃声、震动、通知栏等功能
 * @author Ttt
 */
class FunctionFragment : AppFragment<FragmentOtherFunctionFunctionBinding, FunctionViewModel>(
    R.layout.fragment_other_function_function, BR.viewModel
) {

    override fun onDestroy() {
        super.onDestroy()
        // 关闭手电筒
        FlashlightUtils.getInstance().setFlashlightOff()
        FlashlightUtils.getInstance().unregister()
    }
}

class FunctionViewModel : AppViewModel() {

    // 震动
    val clickVibrate = View.OnClickListener { view ->
    }

    // 铃声 - 播放一小段音频
    val clickBEEP = View.OnClickListener { view ->
    }

    // 是否存在通知权限
    val clickNotification_check = View.OnClickListener { view -> }

    // 开启通知权限
    val clickNotification_open = View.OnClickListener { view -> }

    // 通知消息
    val clickNotification = View.OnClickListener { view -> }

    // 移除消息
    val clickNotification_remove = View.OnClickListener { view -> }

    // 回到桌面
    val clickHOME = View.OnClickListener { view -> }

    // 打开手电筒
    val clickFlashLight_open = View.OnClickListener { view -> }

    // 关闭手电筒
    val clickFlashLight_close = View.OnClickListener { view -> }

    // 是否创建桌面快捷方式
    val clickShortcut_check = View.OnClickListener { view -> }

    // 创建桌面快捷方式
    val clickShortcut_create = View.OnClickListener { view -> }

    // 删除桌面快捷方式
    val clickShortcut_delete = View.OnClickListener { view -> }

    // 打印内存信息
    val clickMemory_print = View.OnClickListener { view -> }

    // 打印设备信息
    val clickDevice_print = View.OnClickListener { view -> }

    // 跳转到 APP 设置详情页面
    val clickAppDetails_settings = View.OnClickListener { view -> }

    // 打开 GPS 设置界面
    val clickGPS_settings = View.OnClickListener { view -> }

    // 打开网络设置界面
    val clickWireless_settings = View.OnClickListener { view -> }

    // 跳转到系统设置页面
    val clickSYS_settings = View.OnClickListener { view -> }

    private fun asd() {
        // 获取操作结果
        var result: Boolean
        when (buttonValue.type) {
            ButtonValue.BTN_FUNCTION_VIBRATE -> {
                result = VibrationUtils.vibrate(200)
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_BEEP -> {
                val mediaPlayer = BeepVibrateAssist.buildMediaPlayer(
                    ResourceUtils.openFd("beep.ogg"), 0.1F
                )
                // 表示不要震动、使用本地或者 raw 文件
                result = BeepVibrateAssist(mActivity, mediaPlayer).setVibrate(false)
                    .playBeepSoundAndVibrate()
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_NOTIFICATION_check -> {
                result = NotificationUtils.isNotificationEnabled()
                showToast(result, "通知权限已开启", "通知权限未开启")
            }

            ButtonValue.BTN_FUNCTION_NOTIFICATION_open -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    result = AppUtils.startActivity(
                        IntentUtils.getLaunchAppNotificationSettingsIntent(AppUtils.getPackageName())
                    )
                    showToast(result)
                } else {
                    showToast(false)
                }
            }

            ButtonValue.BTN_FUNCTION_NOTIFICATION -> {
                result = NotificationUtils.notify(
                    12, NotificationUtils.createNotification(
                        NotificationUtils.Params(R.mipmap.icon_launcher, "标题", "内容")
                    )
                )
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_NOTIFICATION_remove -> {
                result = NotificationUtils.cancel(12)
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_HOME -> {
                result = ActivityUtils.startHomeActivity()
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_FLASHLIGHT_open -> {
                permission_request(
                    permissions = arrayOf(
                        Manifest.permission.CAMERA
                    ),
                    callback = object : IPermissionEngine.Callback {
                        override fun onGranted() {
                            // 非传入 Camera 方式需要注册
                            FlashlightUtils.getInstance().register()
                            val result = FlashlightUtils.getInstance().setFlashlightOn()
                            showToast(result)
                        }

                        override fun onDenied(
                            grantedList: List<String>,
                            deniedList: List<String>,
                            notFoundList: List<String>
                        ) {
                            ToastTintUtils.warning("打开手电筒需摄像头权限")
                        }
                    }
                )
            }

            ButtonValue.BTN_FUNCTION_FLASHLIGHT_close -> {
                result = FlashlightUtils.getInstance().setFlashlightOff()
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_SHORTCUT_check -> {
                result = ShortCutUtils.hasShortcut("Dev 快捷方式")
                showToast(result, "存在快捷方式", "不存在快捷方式")
            }

            ButtonValue.BTN_FUNCTION_SHORTCUT_create -> {
                permission_request(
                    permissions = arrayOf(
                        Manifest.permission.INSTALL_SHORTCUT
                    ),
                    callback = object : IPermissionEngine.Callback {
                        override fun onGranted() {
                            val result = ShortCutUtils.addShortcut(
                                MainActivity::class.java,
                                "Dev 快捷方式",
                                R.mipmap.icon_launcher_round
                            )
                            showToast(result)
                        }

                        override fun onDenied(
                            grantedList: List<String>,
                            deniedList: List<String>,
                            notFoundList: List<String>
                        ) {
                            ToastTintUtils.warning("创建快捷方式需要该权限")
                        }
                    }
                )
            }

            ButtonValue.BTN_FUNCTION_SHORTCUT_delete -> {
                result = ShortCutUtils.deleteShortcut(
                    MainActivity::class.java,
                    "Dev 快捷方式"
                )
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_MEMORY_print -> {
                val memoryInfo = MemoryUtils.printMemoryInfo()
                ToastTintUtils.info(memoryInfo)
                TAG.log_dTag(
                    message = memoryInfo
                )
            }

            ButtonValue.BTN_FUNCTION_DEVICE_print -> {
                val deviceInfo =
                    DeviceUtils.handlerDeviceInfo(DeviceUtils.getDeviceInfo(), "")
                ToastTintUtils.info(deviceInfo)
                TAG.log_dTag(
                    message = deviceInfo
                )
            }

            ButtonValue.BTN_FUNCTION_APP_DETAILS_settings -> {
                result = AppUtils.launchAppDetailsSettings()
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_GPS_settings -> {
                result = AppUtils.openGpsSettings()
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_WIRELESS_settings -> {
                result = AppUtils.openWirelessSettings()
                showToast(result)
            }

            ButtonValue.BTN_FUNCTION_SYS_settings -> {
                result = AppUtils.startSysSetting()
                showToast(result)
            }

            else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
        }
    }
}