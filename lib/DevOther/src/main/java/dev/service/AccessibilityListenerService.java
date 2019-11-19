package dev.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.AccessibilityUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.ResourceUtils;

/**
 * detail: 无障碍功能监听服务
 * @author Ttt
 * <pre>
 *     具体配合 {@link AccessibilityUtils} 使用
 *     AccessibilityService 在 API < 18 的时候使用 AccessibilityService
 *     <p></p>
 *     所需权限
 *     <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
 *     <p></p>
 *     动态配置方式, 只能用来配置动态属性: eventTypes、feedbackType、flags、notificaionTimeout、packageNames
 *     AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
 *     serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
 *     serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
 *     serviceInfo.packageNames = new String[]{ "com.dev"" };
 *     serviceInfo.notificationTimeout=100;
 *     setServiceInfo(serviceInfo);
 * </pre>
 */
public final class AccessibilityListenerService extends AccessibilityService {

    // 日志 TAG
    private static final String TAG = AccessibilityService.class.getSimpleName();
    // 当前服务所持对象
    private static AccessibilityListenerService self;

    /**
     * 通过这个函数可以接收系统发送来的 AccessibilityEvent
     * <pre>
     *     接收来的 AccessibilityEvent 是经过过滤的, 过滤是在配置工作时设置的
     * </pre>
     * @param event {@link AccessibilityEvent}
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (accessibilityListener != null) {
            accessibilityListener.onAccessibilityEvent(event, this);
        }
    }

    /**
     * 系统想要中断 AccessibilityService 返给的响应时会调用
     */
    @Override
    public void onInterrupt() {
        LogPrintUtils.dTag(TAG, "onInterrupt");

        if (accessibilityListener != null) {
            accessibilityListener.onInterrupt();
        }
    }

    /**
     * 系统成功绑定该服务时被触发, 也就是当你在设置中开启相应的服务, 系统成功的绑定了该服务时会触发, 通常我们可以在这里做一些初始化操作
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        LogPrintUtils.dTag(TAG, "onServiceConnected");
    }

    // ============
    // = 生命周期 =
    // ============

    @Override
    public void onCreate() {
        super.onCreate();
        LogPrintUtils.dTag(TAG, "onCreate");

        if (accessibilityListener != null) {
            accessibilityListener.onServiceCreated(this);
        }
        self = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogPrintUtils.dTag(TAG, "onDestroy");

        if (accessibilityListener != null) {
            accessibilityListener.onServiceDestroy();
            accessibilityListener = null;
        }
        self = null;
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 获取当前服务所持对象
     * @return {@link AccessibilityListenerService}
     */
    public static AccessibilityListenerService getSelf() {
        return self;
    }

    /**
     * 启动服务
     */
    public static void startService() {
        try {
            AppUtils.startService(new Intent(DevUtils.getContext(), AccessibilityListenerService.class));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startService");
        }
    }

    /**
     * 停止服务
     */
    public static void stopService() {
        try {
            AppUtils.stopService(new Intent(DevUtils.getContext(), AccessibilityListenerService.class));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopService");
        }
    }

    // =

    /**
     * 检查是否开启无障碍功能
     * <pre>
     *     未开启则跳转至辅助功能设置页面
     * </pre>
     * @return {@code true} open, {@code false} close
     */
    public static boolean checkAccessibility() {
        return checkAccessibility(AppUtils.getPackageName());
    }

    /**
     * 检查是否开启无障碍功能
     * <pre>
     *     未开启则跳转至辅助功能设置页面
     * </pre>
     * @param packageName 应用包名
     * @return {@code true} open, {@code false} close
     */
    public static boolean checkAccessibility(final String packageName) {
        if (packageName == null) return false;
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(packageName)) {
            // 跳转至辅助功能设置页面
            AppUtils.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            return false;
        }
        return true;
    }

    /**
     * 判断是否开启无障碍功能
     * @param packageName 应用包名
     * @return {@code true} open, {@code false} close
     */
    public static boolean isAccessibilitySettingsOn(final String packageName) {
        if (packageName == null) return false;
        // 无障碍功能开启状态
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(ResourceUtils.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            LogPrintUtils.eTag(TAG, e, "isAccessibilitySettingsOn - Settings.Secure.ACCESSIBILITY_ENABLED");
        }
        if (accessibilityEnabled == 1) {
            try {
                String services = Settings.Secure.getString(ResourceUtils.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (services != null) {
                    return services.toLowerCase().contains(packageName.toLowerCase());
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isAccessibilitySettingsOn - Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES");
            }
        }
        return false;
    }

    // =

    // 监听事件
    private static AccessibilityListener accessibilityListener;

    /**
     * 设置监听事件
     * @param listener {@link AccessibilityListener}
     */
    public static void setAccessibilityListener(final AccessibilityListener listener) {
        AccessibilityListenerService.accessibilityListener = listener;
    }

    /**
     * detail: 监听事件
     * @author Ttt
     */
    public static abstract class AccessibilityListener {

        /**
         * 通过这个函数可以接收系统发送来的 AccessibilityEvent
         * <pre>
         *     接收来的 AccessibilityEvent 是经过过滤的, 过滤是在配置工作时设置的
         * </pre>
         * @param event   {@link AccessibilityEvent}
         * @param service {@link AccessibilityListenerService}
         */
        public abstract void onAccessibilityEvent(AccessibilityEvent event, AccessibilityListenerService service);

        /**
         * 系统想要中断 AccessibilityService 返给的响应时会调用
         */
        public void onInterrupt() {
        }

        /**
         * 服务创建通知
         * @param service {@link AccessibilityListenerService}
         */
        public void onServiceCreated(AccessibilityListenerService service) {
        }

        /**
         * 服务销毁通知
         */
        public void onServiceDestroy() {
        }
    }
}