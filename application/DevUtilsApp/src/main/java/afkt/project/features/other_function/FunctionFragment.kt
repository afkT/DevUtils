package afkt.project.features.other_function

import afkt.project.BR
import afkt.project.MainActivity
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionFunctionBinding
import android.Manifest
import android.os.Build
import android.view.View
import dev.engine.extensions.log.log_dTag
import dev.engine.extensions.permission.permission_request
import dev.engine.extensions.toast.toast_showShort
import dev.engine.permission.IPermissionEngine
import dev.utils.app.*
import dev.utils.app.assist.BeepVibrateAssist

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
        // 需要震动权限并且开启了通知权限
        val result = VibrationUtils.vibrate(200)
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 铃声 - 播放一小段音频
    val clickBEEP = View.OnClickListener { view ->
        val activity = ActivityUtils.getActivity(view)
        val mediaPlayer = BeepVibrateAssist.buildMediaPlayer(
            ResourceUtils.openFd("beep.ogg"), 0.1F
        )
        // 表示不要震动、使用本地或者 raw 文件
        val result = BeepVibrateAssist(
            activity, mediaPlayer
        ).setVibrate(false).playBeepSoundAndVibrate()
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 是否存在通知权限
    val clickNotification_check = View.OnClickListener { view ->
        val result = NotificationUtils.isNotificationEnabled()
        toast_showShort(text = if (result) "通知权限已开启" else "通知权限未开启")
    }

    // 开启通知权限
    val clickNotification_open = View.OnClickListener { view ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val result = AppUtils.startActivity(
                IntentUtils.getLaunchAppNotificationSettingsIntent(
                    AppUtils.getPackageName()
                )
            )
            toast_showShort(text = if (result) "操作成功" else "操作失败")
        } else {
            toast_showShort(text = "操作失败")
        }
    }

    // 通知消息
    val clickNotification = View.OnClickListener { view ->
        val result = NotificationUtils.notify(
            12, NotificationUtils.createNotification(
                NotificationUtils.Params(
                    R.mipmap.icon_launcher, "标题", "内容"
                )
            )
        )
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 移除消息
    val clickNotification_remove = View.OnClickListener { view ->
        val result = NotificationUtils.cancel(12)
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 回到桌面
    val clickHOME = View.OnClickListener { view ->
        val result = ActivityUtils.startHomeActivity()
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 打开手电筒
    val clickFlashLight_open = View.OnClickListener { view ->
        val activity = ActivityUtils.getActivity(view)
        activity.permission_request(
            permissions = arrayOf(
                Manifest.permission.CAMERA
            ),
            callback = object : IPermissionEngine.Callback {
                override fun onGranted() {
                    // 非传入 Camera 方式需要注册
                    FlashlightUtils.getInstance().register()
                    val result = FlashlightUtils.getInstance().setFlashlightOn()
                    toast_showShort(text = if (result) "操作成功" else "操作失败")
                }

                override fun onDenied(
                    grantedList: List<String>,
                    deniedList: List<String>,
                    notFoundList: List<String>
                ) {
                    toast_showShort(text = "打开手电筒需摄像头权限")
                }
            }
        )
    }

    // 关闭手电筒
    val clickFlashLight_close = View.OnClickListener { view ->
        val result = FlashlightUtils.getInstance().setFlashlightOff()
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 是否创建桌面快捷方式
    val clickShortcut_check = View.OnClickListener { view ->
        val result = ShortCutUtils.hasShortcut("Dev 快捷方式")
        toast_showShort(text = if (result) "存在快捷方式" else "不存在快捷方式")
    }

    // 创建桌面快捷方式
    val clickShortcut_create = View.OnClickListener { view ->
        val activity = ActivityUtils.getActivity(view)
        activity.permission_request(
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
                    toast_showShort(text = if (result) "操作成功" else "操作失败")
                }

                override fun onDenied(
                    grantedList: List<String>,
                    deniedList: List<String>,
                    notFoundList: List<String>
                ) {
                    toast_showShort(text = "创建快捷方式需要该权限")
                }
            }
        )
    }

    // 删除桌面快捷方式
    val clickShortcut_delete = View.OnClickListener { view ->
        val result = ShortCutUtils.deleteShortcut(
            MainActivity::class.java,
            "Dev 快捷方式"
        )
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 打印内存信息
    val clickMemory_print = View.OnClickListener { view ->
        val memoryInfo = MemoryUtils.printMemoryInfo()
        TAG.log_dTag(message = memoryInfo)
        toast_showShort(text = "数据已打印, 请查看 Logcat")
    }

    // 打印设备信息
    val clickDevice_print = View.OnClickListener { view ->
        val deviceInfo = DeviceUtils.handlerDeviceInfo(
            DeviceUtils.getDeviceInfo(), ""
        )
        TAG.log_dTag(message = deviceInfo)
        toast_showShort(text = "数据已打印, 请查看 Logcat")
    }

    // 跳转到 APP 设置详情页面
    val clickAppDetails_settings = View.OnClickListener { view ->
        val result = AppUtils.launchAppDetailsSettings()
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 打开 GPS 设置界面
    val clickGPS_settings = View.OnClickListener { view ->
        val result = AppUtils.openGpsSettings()
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 打开网络设置界面
    val clickWireless_settings = View.OnClickListener { view ->
        val result = AppUtils.openWirelessSettings()
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }

    // 跳转到系统设置页面
    val clickSYS_settings = View.OnClickListener { view ->
        val result = AppUtils.startSysSetting()
        toast_showShort(text = if (result) "操作成功" else "操作失败")
    }
}