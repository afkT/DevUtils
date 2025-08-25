package afkt.project.features.other_function.service

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionNotificationServiceBinding
import android.content.Intent
import android.os.Build
import android.service.notification.StatusBarNotification
import android.view.View
import dev.engine.extensions.log.log_dTag
import dev.engine.extensions.toast.toast_showShort
import dev.utils.DevFinal

/**
 * detail: 通知栏监听服务 ( NotificationService )
 * @author Ttt
 * @see https://www.jianshu.com/p/981e7de2c7be
 * 所需权限
 * <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"/>
 */
class NotificationServiceFragment : AppFragment<FragmentOtherFunctionNotificationServiceBinding, NotificationServiceViewModel>(
    R.layout.fragment_other_function_notification_service, BR.viewModel
) {

    override fun onDestroy() {
        super.onDestroy()
        // 注销监听
        NotificationService.setListener(null)
        NotificationService.stopService()
    }

    override fun initListener() {
        super.initListener()
        // 设置监听事件
        NotificationService.setListener(object : NotificationService.Listener {
            override fun onServiceCreated(service: NotificationService?) {
                TAG.log_dTag(message = "服务创建通知")
            }

            override fun onServiceDestroy() {
                TAG.log_dTag(message = "服务销毁通知")
            }

            override fun onStartCommand(
                service: NotificationService?,
                intent: Intent?,
                flags: Int,
                startId: Int
            ): Int {
                // 触发服务指令
                val builder = StringBuilder()
                    .append("onServiceStartCommand")
                    .append("\nintent: ").append(intent)
                    .append("\nflags: ").append(flags)
                    .append("\nstartId: ").append(startId)
                TAG.log_dTag(message = builder.toString())
                return 0
            }

            override fun onNotificationPosted(sbn: StatusBarNotification?) { // 当系统收到新的通知后触发回调
                sbn?.notification?.let {
                    val builder = StringBuilder()
                        .append("onNotificationPosted")
                        .append("\nstatusBarNotification: ").append(sbn)
                        .append("\ntickerText : ").append(it.tickerText)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        val bundle = it.extras
                        for (key in bundle.keySet()) {
                            builder.append(
                                DevFinal.SYMBOL.NEW_LINE + key + ": " + bundle.getString(
                                    key
                                )
                            )
                        }
                    }
                    TAG.log_dTag(message = builder.toString())
                }
            }

            override fun onNotificationRemoved(sbn: StatusBarNotification?) { // 当系统通知被删掉后触发回调
                val builder = StringBuilder()
                    .append("onNotificationRemoved")
                    .append("\nstatusBarNotification: ").append(sbn)
                TAG.log_dTag(message = builder.toString())
            }
        })
    }
}

class NotificationServiceViewModel : AppViewModel() {

    val clickCheck = View.OnClickListener { view ->
        val check = NotificationService.isNotificationListenerEnabled()
        if (check) {
            toast_showShort(text = "已开启服务通知")
        } else {
            toast_showShort(text = "未开启服务通知")
        }
    }

    val clickRegister = View.OnClickListener { view ->
        if (!NotificationService.checkAndIntentSetting()) {
            toast_showShort(text = "请先开启服务通知权限")
        } else {
            toast_showShort(text = "绑定通知栏监听服务成功, 请查看 Logcat")
            // 注册监听
            NotificationService.startService()
        }
    }

    val clickUnRegister = View.OnClickListener { view ->
        toast_showShort(text = "注销通知栏监听服务成功")
        // 注销监听
        NotificationService.stopService()
    }
}