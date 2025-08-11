package afkt.project.features.other_function.service

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import dev.utils.LogPrintUtils
import dev.utils.app.NotificationUtils
import dev.utils.app.ServiceUtils

/**
 * detail: 通知栏监听服务
 * @author Ttt
 * @see https://www.jianshu.com/p/981e7de2c7be
 * cancelAllNotifications() 删除系统中所有可被清除的通知
 * getActiveNotifications() 返回当前系统所有通知到 StatusBarNotification[]
 * 所需权限
 * <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"/>
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class NotificationService : NotificationListenerService() {

    // ==========
    // = 通知回调 =
    // ==========

    /**
     * 当系统收到新的通知后触发回调
     * @param sbn [StatusBarNotification]
     */
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (self != null) sListener?.apply { onNotificationPosted(sbn) }
    }

    /**
     * 当系统通知被删掉后触发回调
     * @param sbn [StatusBarNotification]
     */
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        if (self != null) sListener?.apply { onNotificationRemoved(sbn) }
    }

    // ==========
    // = 生命周期 =
    // ==========

    override fun onCreate() {
        super.onCreate()
        LogPrintUtils.dTag(TAG, "onCreate")
        sListener?.onServiceCreated(this)
        self = this
    }

    override fun onDestroy() {
        super.onDestroy()
        LogPrintUtils.dTag(TAG, "onDestroy")
        sListener?.onServiceDestroy()
        sListener = null
        self = null
    }

    override fun onStartCommand(
        intent: Intent,
        flags: Int,
        startId: Int
    ): Int {
        LogPrintUtils.dTag(TAG, "onStartCommand")
        sListener?.let {
            return it.onStartCommand(this, intent, flags, startId)
        }
        return START_STICKY
    }

    // =======
    // = 接口 =
    // =======

    /**
     * detail: 监听回调事件
     * @author Ttt
     */
    interface Listener {

        /**
         * 服务创建通知
         * @param service [NotificationService]
         */
        fun onServiceCreated(service: NotificationService?)

        /**
         * 服务销毁通知
         */
        fun onServiceDestroy()

        /**
         * 触发服务指令
         * @param service [NotificationService]
         * @param intent  [Intent]
         * @param flags   Additional data about this start request.
         * @param startId A unique integer representing this specific request to start.  Use with [stopSelfResult].
         * @return The return value indicates what semantics the system should
         * use for the service's current started state.  It may be one of the
         * constants associated with the [START_CONTINUATION_MASK] bits.
         */
        fun onStartCommand(
            service: NotificationService?,
            intent: Intent?,
            flags: Int,
            startId: Int
        ): Int

        /**
         * 当系统收到新的通知后触发回调
         * @param sbn [StatusBarNotification]
         * 当 API > 18 时, 利用 Notification.extras 来获取通知内容, extras 是在 API 19 时被加入的
         * 当 API = 18 时, 利用反射获取 Notification 中的内容
         */
        fun onNotificationPosted(sbn: StatusBarNotification?)

        /**
         * 当系统通知被删掉后触发回调
         * @param sbn [StatusBarNotification]
         */
        fun onNotificationRemoved(sbn: StatusBarNotification?)
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 取消通知方法
     * @param sbn [StatusBarNotification]
     * cancelNotification(String key) 是 API >= 21 才可以使用的, 利用 StatusBarNotification 的 getKey() 方法来获取 key 并取消通知
     * cancelNotification(String pkg, String tag, int id) 在 API < 21 时可以使用, 在 API >= 21 时使用此方法来取消通知将无效 ( 被废弃 )
     */
    fun cancelNotification(sbn: StatusBarNotification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cancelNotification(sbn.key)
        } else {
            cancelNotification(sbn.packageName, sbn.tag, sbn.id)
        }
    }

    companion object {
        // 日志 TAG
        private val TAG = NotificationService::class.java.simpleName

        /**
         * 获取当前服务所持对象
         * @return [NotificationService]
         */
        // 当前服务所持对象
        var self: NotificationService? = null
            private set

        /**
         * 启动服务
         */
        fun startService() {
            ServiceUtils.startService(NotificationService::class.java)
        }

        /**
         * 停止服务
         */
        fun stopService() {
            ServiceUtils.stopService(NotificationService::class.java)
        }

        // =

        private var sListener: Listener? = null

        /**
         * 设置监听回调事件
         * @param listener [Listener]
         */
        fun setListener(listener: Listener?) {
            sListener = listener
        }

        // =

        /**
         * 检查是否有获取通知栏信息权限并跳转设置页面
         * @return `true` yes, `false` no
         */
        fun checkAndIntentSetting(): Boolean {
            return NotificationUtils.checkAndIntentSetting()
        }

        /**
         * 判断是否有获取通知栏信息权限
         * @return `true` yes, `false` no
         */
        fun isNotificationListenerEnabled(): Boolean {
            return NotificationUtils.isNotificationListenerEnabled()
        }

        /**
         * 判断是否有获取通知栏信息权限
         * @param packageName 应用包名
         * @return `true` yes, `false` no
         */
        fun isNotificationListenerEnabled(packageName: String?): Boolean {
            return NotificationUtils.isNotificationListenerEnabled(packageName)
        }

        /**
         * 跳转到设置页面, 开启获取通知栏信息权限
         */
        fun startNotificationListenSettings() {
            NotificationUtils.startNotificationListenSettings()
        }
    }
}