package dev.utils.app;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;

/**
 * detail: 无障碍功能工具类
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.jianshu.com/p/981e7de2c7be"/>
 *     @see <a href="https://www.jianshu.com/p/f67e950d84f7"/>
 *     @see <a href="https://blog.csdn.net/dd864140130/article/details/51794318"/>
 *     <p></p>
 *     AccessibilityService 在 API < 18 的时候使用 AccessibilityService
 *     所需权限
 *     <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
 * </pre>
 */
public final class AccessibilityUtils {

    private AccessibilityUtils() {
    }

    // 日志 TAG
    private static final String TAG = AccessibilityUtils.class.getSimpleName();

    // AccessibilityService 对象
    private static AccessibilityService sService = null;

    /**
     * 获取 AccessibilityService 对象
     * @return {@link AccessibilityService}
     */
    public static AccessibilityService getService() {
        return sService;
    }

    /**
     * 设置 AccessibilityService 对象
     * @param service {@link AccessibilityService}
     */
    public static void setService(final AccessibilityService service) {
        AccessibilityUtils.sService = service;
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

    // ===========
    // = 打印方法 =
    // ===========

    /**
     * 打印 AccessibilityEvent 信息日志
     * @param event {@link AccessibilityEvent}
     */
    public static void printAccessibilityEvent(final AccessibilityEvent event) {
        printAccessibilityEvent(event, TAG);
    }

    /**
     * 打印 AccessibilityEvent 信息日志
     * @param event {@link AccessibilityEvent}
     * @param tag   日志 TAG
     */
    public static void printAccessibilityEvent(
            final AccessibilityEvent event,
            final String tag
    ) {
        if (event == null || !LogPrintUtils.isPrintLog()) return;

        StringBuilder builder = new StringBuilder();
        builder.append("=========================");
        builder.append(DevFinal.NEW_LINE_STR);

        int eventType = event.getEventType(); // 事件类型
        builder.append("packageName: ").append(event.getPackageName()); // 响应事件的应用包名
        builder.append(DevFinal.NEW_LINE_STR);

        builder.append("source: ").append(event.getSource()); // 事件源信息
        builder.append(DevFinal.NEW_LINE_STR);

        builder.append("source class: ").append(event.getClassName()); // 事件源的类名, 如 android.widget.TextView
        builder.append(DevFinal.NEW_LINE_STR);

        builder.append("event type(int): ").append(eventType);
        builder.append(DevFinal.NEW_LINE_STR);

        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                builder.append("event type: TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED: // 窗体状态改变
                builder.append("event type: TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED: // View 获取到焦点
                builder.append("event type: TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                builder.append("event type: TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                builder.append("event type: TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                builder.append("event type: TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                builder.append("event type: TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                builder.append("event type: TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                builder.append("event type: TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                builder.append("event type: TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
        }
        builder.append(DevFinal.NEW_LINE_STR);

        for (CharSequence txt : event.getText()) {
            // 输出当前事件包含的文本信息
            builder.append("text: ").append(txt);
            builder.append(DevFinal.NEW_LINE_STR);
        }
        builder.append("=========================");

        // 打印日志
        LogPrintUtils.dTag(tag, builder.toString());
    }

    // ===========
    // = 节点获取 =
    // ===========

    /**
     * 查找符合条件的节点
     * @param focus 焦点类型
     * @return 拥有特定焦点类型的节点
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static AccessibilityNodeInfo findFocus(final int focus) {
        return findFocus(sService, focus);
    }

    /**
     * 查找符合条件的节点
     * @param service {@link AccessibilityService}
     * @param focus   焦点类型
     * @return 拥有特定焦点类型的节点
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static AccessibilityNodeInfo findFocus(
            final AccessibilityService service,
            final int focus
    ) {
        if (service == null) return null;
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null) return null;
        // 通过指定的焦点类型找到当前的节点
        return nodeInfo.findFocus(focus);
    }

    // =

    /**
     * 查找符合条件的节点
     * @param focus     焦点类型
     * @param className 节点所属的类 ( 类名 )
     * @return 拥有特定焦点类型的节点
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static AccessibilityNodeInfo findFocus(
            final int focus,
            final String className
    ) {
        return findFocus(sService, focus, className);
    }

    /**
     * 查找符合条件的节点
     * @param service   {@link AccessibilityService}
     * @param focus     焦点类型
     * @param className 节点所属的类 ( 类名 )
     * @return 拥有特定焦点类型的节点
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static AccessibilityNodeInfo findFocus(
            final AccessibilityService service,
            final int focus,
            final String className
    ) {
        if (service == null || className == null) return null;
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null) return null;
        // 通过指定的焦点类型找到当前的节点
        AccessibilityNodeInfo node = nodeInfo.findFocus(focus);
        // 防止为 null
        if (node != null) {
            // 判断是否符合的类型
            if (node.getClassName().equals(className) && node.isEnabled()) {
                return node;
            }
        }
        return null;
    }

    // =

    /**
     * 查找符合条件的节点
     * @param text 文本内容 ( 搜索包含该文本内容的节点 )
     * @return 包含该文本内容的节点集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(final String text) {
        return findAccessibilityNodeInfosByText(sService, text);
    }

    /**
     * 查找符合条件的节点
     * @param service {@link AccessibilityService}
     * @param text    文本内容 ( 搜索包含该文本内容的节点 )
     * @return 包含该文本内容的节点集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(
            final AccessibilityService service,
            final String text
    ) {
        if (service == null || text == null) return null;
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null) return null;
        // 通过文字找到当前的节点
        return nodeInfo.findAccessibilityNodeInfosByText(text);
    }

    // =

    /**
     * 查找符合条件的节点
     * @param text      文本内容 ( 搜索包含该文本内容的节点 )
     * @param className 节点所属的类 ( 类名 )
     * @return 包含该文本内容, 且属于指定类的节点集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(
            final String text,
            final String className
    ) {
        return findAccessibilityNodeInfosByText(sService, text, className);
    }

    /**
     * 查找符合条件的节点
     * @param service   {@link AccessibilityService}
     * @param text      文本内容 ( 搜索包含该文本内容的节点 )
     * @param className 节点所属的类 ( 类名 )
     * @return 包含该文本内容, 且属于指定类的节点集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(
            final AccessibilityService service,
            final String text,
            final String className
    ) {
        if (service == null || text == null || className == null) return null;
        List<AccessibilityNodeInfo> lists = new ArrayList<>();
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null) return lists;
        // 通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 判断是否符合的类型
            if (node.getClassName().equals(className) && node.isEnabled()) {
                // 保存符合条件
                lists.add(node);
            }
        }
        return lists;
    }

    // =

    /**
     * 查找符合条件的节点
     * @param id viewId
     * @return 等于 viewId 的节点集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(final String id) {
        return findAccessibilityNodeInfosByViewId(sService, id);
    }

    /**
     * 查找符合条件的节点
     * @param service {@link AccessibilityService}
     * @param id      viewId
     * @return 等于 viewId 的节点集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(
            final AccessibilityService service,
            final String id
    ) {
        if (service == null || id == null) return null;
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null) return null;
        // 通过 id 找到当前的节点
        return nodeInfo.findAccessibilityNodeInfosByViewId(id);
    }

    // =

    /**
     * 查找符合条件的节点
     * @param id        viewId
     * @param className 节点所属的类 ( 类名 )
     * @return 等于 viewId, 且属于指定类的节点集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(
            final String id,
            final String className
    ) {
        return findAccessibilityNodeInfosByViewId(sService, id, className);
    }

    /**
     * 查找符合条件的节点
     * @param service   {@link AccessibilityService}
     * @param id        viewId
     * @param className 节点所属的类 ( 类名 )
     * @return 等于 viewId, 且属于指定类的节点集合
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(
            final AccessibilityService service,
            final String id,
            final String className
    ) {
        if (service == null || id == null || className == null) return null;
        List<AccessibilityNodeInfo> lists = new ArrayList<>();
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null) return lists;
        // 通过 id 找到当前的节点
        List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByViewId(id);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 判断是否符合的类型
            if (node.getClassName().equals(className) && node.isEnabled()) {
                // 保存符合条件
                lists.add(node);
            }
        }
        return lists;
    }

    // ========
    // = 操作 =
    // ========

    /**
     * 点击指定的节点
     * @param nodeInfo {@link AccessibilityNodeInfo}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean performClick(final AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null && nodeInfo.isClickable()) {
            return preformAction(nodeInfo, AccessibilityNodeInfo.ACTION_CLICK);
        }
        return false;
    }

    /**
     * 点击指定的节点
     * @param nodeInfo    {@link AccessibilityNodeInfo}
     * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     * @return {@code true} success, {@code false} fail
     */
    public static boolean performClick(
            final AccessibilityNodeInfo nodeInfo,
            final boolean clickParent
    ) {
        return performClick(nodeInfo, clickParent, false);
    }

    /**
     * 点击指定的节点
     * @param nodeInfo    {@link AccessibilityNodeInfo}
     * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     * @param clickAll    判断是否点击全部节点
     * @return {@code true} success, {@code false} fail
     */
    public static boolean performClick(
            final AccessibilityNodeInfo nodeInfo,
            final boolean clickParent,
            final boolean clickAll
    ) {
        if (nodeInfo == null) return false;
        if (clickParent) {
            if (nodeInfo.isClickable()) {
                return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            } else {
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                while (parent != null) {
                    if (performClick(parent)) {
                        if (!clickAll) {
                            return true;
                        }
                    }
                    parent = parent.getParent();
                }
                return true;
            }
        } else {
            return performClick(nodeInfo);
        }
    }

    // =

    /**
     * 长按指定的节点
     * @param nodeInfo {@link AccessibilityNodeInfo}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean performLongClick(final AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null && nodeInfo.isClickable()) {
            return preformAction(nodeInfo, AccessibilityNodeInfo.ACTION_LONG_CLICK);
        }
        return false;
    }

    /**
     * 长按指定的节点
     * @param nodeInfo    {@link AccessibilityNodeInfo}
     * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     * @return {@code true} success, {@code false} fail
     */
    public static boolean performLongClick(
            final AccessibilityNodeInfo nodeInfo,
            final boolean clickParent
    ) {
        return performLongClick(nodeInfo, clickParent, false);
    }

    /**
     * 长按指定的节点
     * @param nodeInfo    {@link AccessibilityNodeInfo}
     * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     * @param clickAll    判断是否点击全部节点
     * @return {@code true} success, {@code false} fail
     */
    public static boolean performLongClick(
            final AccessibilityNodeInfo nodeInfo,
            final boolean clickParent,
            final boolean clickAll
    ) {
        if (nodeInfo == null) return false;
        if (clickParent) {
            if (nodeInfo.isClickable()) {
                return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
            } else {
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                while (parent != null) {
                    if (performLongClick(parent)) {
                        if (!clickAll) {
                            return true;
                        }
                    }
                    parent = parent.getParent();
                }
                return true;
            }
        } else {
            return performLongClick(nodeInfo);
        }
    }

    // =

    /**
     * 触发返回键
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionBack() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_BACK);
    }

    /**
     * 触发返回键
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionBack(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_BACK);
    }

    /**
     * 触发 Home 键
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionHome() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_HOME);
    }

    /**
     * 触发 Home 键
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionHome(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_HOME);
    }

    /**
     * 启动长按电源按钮 Dialog
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionPowerDialog() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
    }

    /**
     * 启动长按电源按钮 Dialog
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionPowerDialog(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
    }

    /**
     * 锁定屏幕 ( 非锁屏 )
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionLockScreen() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
    }

    /**
     * 锁定屏幕 ( 非锁屏 )
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionLockScreen(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
    }

    /**
     * 截屏
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionTakeScreenshot() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
    }

    /**
     * 截屏
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionTakeScreenshot(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
    }

    /**
     * 打开通知栏
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionNotifications() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }

    /**
     * 打开通知栏
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionNotifications(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }

    /**
     * 最近打开应用列表
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionRecents() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    /**
     * 最近打开应用列表
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionRecents(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    /**
     * 打开设置
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionQuickSettings() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
    }

    /**
     * 打开设置
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionQuickSettings(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
    }

    /**
     * 分屏
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionSplitScreen() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
    }

    /**
     * 分屏
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionSplitScreen(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
    }

    // ===========
    // = 统一调用 =
    // ===========

    /**
     * 模拟对应 Action 操作
     * @param nodeInfo {@link AccessibilityNodeInfo}
     * @param action   操作意图
     * @return {@code true} success, {@code false} fail
     */
    public static boolean preformAction(
            final AccessibilityNodeInfo nodeInfo,
            final int action
    ) {
        if (nodeInfo != null) {
            return nodeInfo.performAction(action);
        }
        return false;
    }

    /**
     * 模拟全局对应 Action 操作
     * @param service {@link AccessibilityService}
     * @param action  操作意图
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performGlobalAction(
            final AccessibilityService service,
            final int action
    ) {
        if (service != null) {
            return service.performGlobalAction(action);
        }
        return false;
    }

//    // 获取根节点
//    AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//    // 匹配 Text 获取节点
//    List<AccessibilityNodeInfo> list1 = rootNode.findAccessibilityNodeInfosByText("match_text");
//    // 匹配 id 获取节点
//    List<AccessibilityNodeInfo> list2 = rootNode.findAccessibilityNodeInfosByViewId("match_id");
//    // 获取子节点
//    AccessibilityNodeInfo infoNode = rootNode.getChild(index);

//    // 模拟输入内容
//    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//    ClipData clip = ClipData.newPlainText("label", "");
//    clipboard.setPrimaryClip(clip);
//    target.performAction(AccessibilityNodeInfo.ACTION_PASTE);

//    // 向上滑动
//    performGlobalAction(service, AccessibilityService.GESTURE_SWIPE_UP);

//    disableSelf()	// 禁用当前服务, 也就是在服务可以通过该方法停止运行
//    getSystemService(String name)	// 获取系统服务
//    onServiceConnected() // 系统成功绑定该服务时被触发, 也就是当你在设置中开启相应的服务, 系统成功的绑定了该服务时会触发, 通常我们可以在这里做一些初始化操作
//    getServiceInfo() // 获取当前服务的配置信息
//    setServiceInfo(AccessibilityServiceInfo info) // 设置当前服务的配置信息
//    onAccessibilityEvent(AccessibilityEvent event) // 有关 AccessibilityEvent 事件的回调函数, 系统通过 sendAccessibilityEvent() 不断的发送 AccessibilityEvent 到此处
//    performGlobalAction(int action) // 执行全局操作, 比如回到主页、打开最近等操作
//    findFocus(int flag) // 查找拥有特定焦点类型的控件
//    getRootInActiveWindow() // 如果配置能够获取窗口内容, 则会返回当前活动窗口的根结点
//    onKeyEvent(KeyEvent event) // 如果允许服务监听按键操作, 该方法是按键事件的回调, 需要注意, 这个过程发生了系统处理按键事件之前
}