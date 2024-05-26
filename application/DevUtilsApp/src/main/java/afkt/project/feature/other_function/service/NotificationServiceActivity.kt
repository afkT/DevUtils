package afkt.project.feature.other_function.service

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.ext.bindAdapter
import afkt.project.data_model.button.ButtonList.notificationServiceButtonValues
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.content.Intent
import android.os.Build
import android.service.notification.StatusBarNotification
import com.therouter.router.Route
import dev.expand.engine.log.log_dTag
import dev.service.NotificationService
import dev.utils.DevFinal
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 通知栏监听服务 ( NotificationService )
 * @author Ttt
 * @see https://www.jianshu.com/p/981e7de2c7be
 * 所需权限
 * <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"/>
 */
@Route(path = RouterPath.OTHER_FUNCTION.NotificationServiceActivity_PATH)
class NotificationServiceActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is NotificationServiceActivity) {
                it.apply {
                    binding.vidRv.bindAdapter(notificationServiceButtonValues) { buttonValue ->
                        when (buttonValue.type) {
                            ButtonValue.BTN_NOTIFICATION_SERVICE_CHECK -> {
                                val check = NotificationService.isNotificationListenerEnabled()
                                showToast(check, "已开启服务通知", "未开启服务通知")
                            }

                            ButtonValue.BTN_NOTIFICATION_SERVICE_REGISTER -> {
                                if (!NotificationService.checkAndIntentSetting()) {
                                    showToast(false, "请先开启服务通知权限")
                                } else {
                                    showToast(true, "绑定通知栏监听服务成功, 请查看 Logcat")
                                    // 注册监听
                                    NotificationService.startService()
                                }
                            }

                            ButtonValue.BTN_NOTIFICATION_SERVICE_UNREGISTER -> {
                                showToast(true, "注销通知栏监听服务成功")
                                // 注销监听
                                NotificationService.stopService()
                            }

                            else -> ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件")
                        }
                    }
                }
            }
        }
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
                TAG.log_dTag(
                    message = "服务创建通知"
                )
            }

            override fun onServiceDestroy() {
                TAG.log_dTag(
                    message = "服务销毁通知"
                )
            }

            override fun onStartCommand(
                service: NotificationService?,
                intent: Intent?,
                flags: Int,
                startId: Int
            ): Int { // 触发服务指令
                val builder = StringBuilder()
                    .append("onServiceStartCommand")
                    .append("\nintent: ").append(intent)
                    .append("\nflags: ").append(flags)
                    .append("\nstartId: ").append(startId)
                TAG.log_dTag(
                    message = builder.toString()
                )
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
                            builder.append(DevFinal.SYMBOL.NEW_LINE + key + ": " + bundle.get(key))
                        }
                    }
                    TAG.log_dTag(
                        message = builder.toString()
                    )
                }
            }

            override fun onNotificationRemoved(sbn: StatusBarNotification?) { // 当系统通知被删掉后触发回调
                val builder = StringBuilder()
                    .append("onNotificationRemoved")
                    .append("\nstatusBarNotification: ").append(sbn)
                TAG.log_dTag(
                    message = builder.toString()
                )
            }
        })
    }
}