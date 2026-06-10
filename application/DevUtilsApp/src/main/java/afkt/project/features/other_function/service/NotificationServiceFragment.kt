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
                "服务创建通知".log_dTag(tag = TAG)
            }

            override fun onServiceDestroy() {
                "服务销毁通知".log_dTag(tag = TAG)
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
                builder.toString().log_dTag(tag = TAG)
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
                    builder.toString().log_dTag(tag = TAG)
                }
            }

            override fun onNotificationRemoved(sbn: StatusBarNotification?) { // 当系统通知被删掉后触发回调
                val builder = StringBuilder()
                    .append("onNotificationRemoved")
                    .append("\nstatusBarNotification: ").append(sbn)
                builder.toString().log_dTag(tag = TAG)
            }
        })
    }
}

class NotificationServiceViewModel : AppViewModel() {

    val clickCheck = View.OnClickListener { view ->
        val check = NotificationService.isNotificationListenerEnabled()
        if (check) {
            "已开启服务通知".toast_showShort()
        } else {
            "未开启服务通知".toast_showShort()
        }
    }

    val clickRegister = View.OnClickListener { view ->
        if (!NotificationService.checkAndIntentSetting()) {
            "请先开启服务通知权限".toast_showShort()
        } else {
            "绑定通知栏监听服务成功, 请查看 Logcat".toast_showShort()
            // 注册监听
            NotificationService.startService()
        }
    }

    val clickUnRegister = View.OnClickListener { view ->
        "注销通知栏监听服务成功".toast_showShort()
        // 注销监听
        NotificationService.stopService()
    }
}