package afkt.project.features.other_function.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import dev.utils.LogPrintUtils
import dev.utils.app.AccessibilityUtils
import dev.utils.app.ServiceUtils

/**
 * detail: 无障碍功能监听服务
 * @author Ttt
 * 所需权限
 * <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE"/>
 * 具体配合 [AccessibilityUtils] 使用
 * AccessibilityService 在 API < 18 的时候使用 AccessibilityService
 * 动态配置方式, 只能用来配置动态属性: eventTypes、feedbackType、flags、notificationTimeout、packageNames
 * var serviceInfo = AccessibilityServiceInfo()
 * serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
 * serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
 * serviceInfo.packageNames = new String[]{ "afkt.project" }
 * serviceInfo.notificationTimeout=100
 * setServiceInfo(serviceInfo)
 */
class AccessibilityListenerService : AccessibilityService() {

    /**
     * 通过这个函数可以接收系统发送来的 AccessibilityEvent
     * @param event [AccessibilityEvent]
     * 接收来的 AccessibilityEvent 是经过过滤的, 过滤是在配置工作时设置的
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        AccessibilityUtils.setService(this)
        sListener?.onAccessibilityEvent(event, this)
    }

    /**
     * 系统想要中断 AccessibilityService 返给的响应时会调用
     */
    override fun onInterrupt() {
        LogPrintUtils.dTag(TAG, "onInterrupt")
        sListener?.onInterrupt()
    }

    /**
     * 系统成功绑定该服务时被触发, 也就是当你在设置中开启相应的服务
     * 系统成功的绑定了该服务时会触发, 通常我们可以在这里做一些初始化操作
     */
    override fun onServiceConnected() {
        super.onServiceConnected()
        LogPrintUtils.dTag(TAG, "onServiceConnected")
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

    // =======
    // = 接口 =
    // =======

    /**
     * detail: 监听回调事件
     * @author Ttt
     */
    interface Listener {

        /**
         * 通过这个函数可以接收系统发送来的 AccessibilityEvent
         * @param event   [AccessibilityEvent]
         * @param service [AccessibilityListenerService]
         * 接收来的 AccessibilityEvent 是经过过滤的, 过滤是在配置工作时设置的
         */
        fun onAccessibilityEvent(
            event: AccessibilityEvent?,
            service: AccessibilityListenerService?
        )

        /**
         * 系统想要中断 AccessibilityService 返给的响应时会调用
         */
        fun onInterrupt() {}

        /**
         * 服务创建通知
         * @param service [AccessibilityListenerService]
         */
        fun onServiceCreated(service: AccessibilityListenerService?) {}

        /**
         * 服务销毁通知
         */
        fun onServiceDestroy() {}
    }

    // =============
    // = 对外公开方法 =
    // =============

    companion object {
        // 日志 TAG
        private val TAG = AccessibilityService::class.java.simpleName

        /**
         * 获取当前服务所持对象
         * @return [AccessibilityListenerService]
         */
        // 当前服务所持对象
        var self: AccessibilityListenerService? = null
            private set

        /**
         * 启动服务
         */
        fun startService() {
            ServiceUtils.startService(AccessibilityListenerService::class.java)
        }

        /**
         * 停止服务
         */
        fun stopService() {
            ServiceUtils.stopService(AccessibilityListenerService::class.java)
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
         * 检查是否开启无障碍功能
         * @return `true` open, `false` close
         * 未开启则跳转至辅助功能设置页面
         */
        fun checkAccessibility(): Boolean {
            return AccessibilityUtils.checkAccessibility()
        }

        /**
         * 检查是否开启无障碍功能
         * @param packageName 应用包名
         * @return `true` open, `false` close
         * 未开启则跳转至辅助功能设置页面
         */
        fun checkAccessibility(packageName: String?): Boolean {
            return AccessibilityUtils.checkAccessibility(packageName)
        }

        /**
         * 判断是否开启无障碍功能
         * @param packageName 应用包名
         * @return `true` open, `false` close
         */
        fun isAccessibilitySettingsOn(packageName: String?): Boolean {
            return AccessibilityUtils.isAccessibilitySettingsOn(packageName)
        }
    }
}