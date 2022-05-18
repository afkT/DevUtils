package afkt.project.feature.other_function.dev_function

import afkt.project.MainActivity
import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ButtonAdapter
import afkt.project.model.item.ButtonList.functionButtonValues
import afkt.project.model.item.ButtonValue
import afkt.project.model.item.RouterPath
import android.Manifest
import android.os.Build
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.engine.DevEngine
import dev.engine.permission.IPermissionEngine
import dev.kotlin.engine.log.log_dTag
import dev.utils.app.*
import dev.utils.app.assist.BeepVibrateAssist
import dev.utils.app.camera.camera1.FlashlightUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.app.toast.ToastUtils

/**
 * detail: 铃声、震动、通知栏等功能
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.FunctionActivity_PATH)
class FunctionActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onDestroy() {
        super.onDestroy()
        // 关闭手电筒
        FlashlightUtils.getInstance().setFlashlightOff()
        FlashlightUtils.getInstance().unregister()
    }

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        ButtonAdapter(functionButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    // 获取操作结果
                    var result: Boolean
                    when (buttonValue.type) {
                        ButtonValue.BTN_FUNCTION_VIBRATE -> {
                            result = VibrationUtils.vibrate(200)
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_BEEP -> {
                            // 表示不要震动、使用本地或者 raw 文件
                            result = BeepVibrateAssist(mActivity, R.raw.dev_beep).setVibrate(false)
                                .playBeepSoundAndVibrate()
                            result = BeepVibrateAssist(mActivity, "xxx/a.mp3").setVibrate(false)
                                .playBeepSoundAndVibrate()
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_NOTIFICATION_CHECK -> {
                            result = NotificationUtils.isNotificationEnabled()
                            showToast(result, "通知权限已开启", "通知权限未开启")
                        }
                        ButtonValue.BTN_FUNCTION_NOTIFICATION_OPEN -> {
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
                        ButtonValue.BTN_FUNCTION_NOTIFICATION_REMOVE -> {
                            result = NotificationUtils.cancel(12)
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_HOME -> {
                            result = ActivityUtils.startHomeActivity()
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_FLASHLIGHT_OPEN -> {
                            DevEngine.getPermission()?.request(
                                mActivity, arrayOf(
                                    Manifest.permission.CAMERA
                                ), object : IPermissionEngine.Callback {
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
                        ButtonValue.BTN_FUNCTION_FLASHLIGHT_CLOSE -> {
                            result = FlashlightUtils.getInstance().setFlashlightOff()
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_SHORTCUT_CHECK -> {
                            result = ShortCutUtils.hasShortcut("Dev 快捷方式")
                            showToast(result, "存在快捷方式", "不存在快捷方式")
                        }
                        ButtonValue.BTN_FUNCTION_SHORTCUT_CREATE -> {
                            DevEngine.getPermission()?.request(
                                mActivity, arrayOf(
                                    Manifest.permission.INSTALL_SHORTCUT
                                ), object : IPermissionEngine.Callback {
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
                        ButtonValue.BTN_FUNCTION_SHORTCUT_DELETE -> {
                            result = ShortCutUtils.deleteShortcut(
                                MainActivity::class.java,
                                "Dev 快捷方式"
                            )
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_MEMORY_PRINT -> {
                            val memoryInfo = MemoryUtils.printMemoryInfo()
                            ToastUtils.showShort(memoryInfo)
                            log_dTag(
                                tag = TAG,
                                message = memoryInfo
                            )
                        }
                        ButtonValue.BTN_FUNCTION_DEVICE_PRINT -> {
                            val deviceInfo =
                                DeviceUtils.handlerDeviceInfo(DeviceUtils.getDeviceInfo(), "")
                            ToastUtils.showShort(deviceInfo)
                            log_dTag(
                                tag = TAG,
                                message = deviceInfo
                            )
                        }
                        ButtonValue.BTN_FUNCTION_APP_DETAILS_SETTINGS -> {
                            result = AppUtils.launchAppDetailsSettings()
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_GPS_SETTINGS -> {
                            result = AppUtils.openGpsSettings()
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_WIRELESS_SETTINGS -> {
                            result = AppUtils.openWirelessSettings()
                            showToast(result)
                        }
                        ButtonValue.BTN_FUNCTION_SYS_SETTINGS -> {
                            result = AppUtils.startSysSetting()
                            showToast(result)
                        }
                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }).bindAdapter(binding.vidRv)
    }
}