package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList.notificationServiceButtonValues
import afkt.project.model.item.ButtonValue
import afkt.project.ui.adapter.ButtonAdapter
import android.content.Intent
import android.os.Build
import android.service.notification.StatusBarNotification
import dev.callback.DevItemClickCallback
import dev.engine.log.DevLogEngine
import dev.service.NotificationService
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 通知栏监听服务 ( NotificationService )
 * @author Ttt
 * https://www.jianshu.com/p/981e7de2c7be
 * 所需权限
 * <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"/>
 */
class NotificationServiceActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onDestroy() {
        super.onDestroy()
        // 注销监听
        NotificationService.setListener(null)
        NotificationService.stopService()
    }

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        val buttonAdapter = ButtonAdapter(notificationServiceButtonValues)
        binding.vidBvrRecy.adapter = buttonAdapter
        buttonAdapter.itemCallback = object : DevItemClickCallback<ButtonValue>() {
            override fun onItemClick(
                buttonValue: ButtonValue,
                param: Int
            ) {
                when (buttonValue.type) {
                    ButtonValue.BTN_NOTIFICATION_SERVICE_CHECK -> {
                        val check = NotificationService.isNotificationListenerEnabled()
                        showToast(check, "已开启服务通知", "未开启服务通知")
                    }
                    ButtonValue.BTN_NOTIFICATION_SERVICE_REGISTER -> {
                        if (!NotificationService.checkAndIntentSetting()) {
                            showToast(false, "请先开启服务通知权限")
                            return
                        }
                        showToast(true, "绑定通知栏监听服务成功, 请查看 Logcat")
                        // 注册监听
                        NotificationService.startService()
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

    override fun initListener() {
        super.initListener()

        // 设置监听事件
        NotificationService.setListener(object : NotificationService.Listener {
            override fun onServiceCreated(service: NotificationService?) {
                DevLogEngine.getEngine().dTag(TAG, "服务创建通知")
            }

            override fun onServiceDestroy() {
                DevLogEngine.getEngine().dTag(TAG, "服务销毁通知")
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
                DevLogEngine.getEngine().dTag(TAG, builder.toString())
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
                            builder.append("\n" + key + ": " + bundle.get(key))
                        }
                    }
                    // 打印日志
                    DevLogEngine.getEngine().dTag(TAG, builder.toString())
                }
            }

            override fun onNotificationRemoved(sbn: StatusBarNotification?) { // 当系统通知被删掉后触发回调
                val builder = StringBuilder()
                    .append("onNotificationRemoved")
                    .append("\nstatusBarNotification: ").append(sbn)
                // 打印日志
                DevLogEngine.getEngine().dTag(TAG, builder.toString())
            }
        })
    }
}