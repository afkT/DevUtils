package dev.utils.app;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
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
 *     <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE"/>
 *     <p></p>
 *     // 如果允许服务监听按键操作, 该方法是按键事件的回调, 需要注意这个过程发生在系统处理按键事件之前
 *     AccessibilityService#onKeyEvent(KeyEvent event)
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
            accessibilityEnabled = Settings.Secure.getInt(
                    ResourceUtils.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED
            );
        } catch (Settings.SettingNotFoundException e) {
            LogPrintUtils.eTag(
                    TAG, e,
                    "isAccessibilitySettingsOn - Settings.Secure.ACCESSIBILITY_ENABLED"
            );
        }
        if (accessibilityEnabled == 1) {
            try {
                String services = Settings.Secure.getString(
                        ResourceUtils.getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                );
                if (services != null) {
                    return services.toLowerCase().contains(packageName.toLowerCase());
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(
                        TAG, e,
                        "isAccessibilitySettingsOn - Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES"
                );
            }
        }
        return false;
    }

    // ===========
    // = Service =
    // ===========

    /**
     * 禁用无障碍服务
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean disableSelf() {
        return disableSelf(sService);
    }

    /**
     * 禁用无障碍服务
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean disableSelf(final AccessibilityService service) {
        if (service == null) return false;
        try {
            service.disableSelf();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "disableSelf");
        }
        return false;
    }

    /**
     * 获取无障碍服务信息
     * @return AccessibilityServiceInfo
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static AccessibilityServiceInfo getServiceInfo() {
        return getServiceInfo(sService);
    }

    /**
     * 获取无障碍服务信息
     * @param service {@link AccessibilityService}
     * @return AccessibilityServiceInfo
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static AccessibilityServiceInfo getServiceInfo(final AccessibilityService service) {
        if (service == null) return null;
        try {
            return service.getServiceInfo();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getServiceInfo");
        }
        return null;
    }

    /**
     * 设置无障碍服务信息 ( 动态配置方式 )
     * @param service {@link AccessibilityService}
     * @param info    无障碍服务信息
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setServiceInfo(
            final AccessibilityService service,
            final AccessibilityServiceInfo info
    ) {
        if (service == null || info == null) return false;
        try {
            service.setServiceInfo(info);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setServiceInfo");
        }
        return false;
    }

    // ==========
    // = 节点获取 =
    // ==========

    /**
     * 获取根节点
     * @return 根节点
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static AccessibilityNodeInfo getRootInActiveWindow() {
        return getRootInActiveWindow(sService);
    }

    /**
     * 获取根节点
     * @param service {@link AccessibilityService}
     * @return 根节点
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static AccessibilityNodeInfo getRootInActiveWindow(final AccessibilityService service) {
        if (service == null) return null;
        try {
            return service.getRootInActiveWindow();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getRootInActiveWindow");
        }
        return null;
    }

    // =

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
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow(service);
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
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow(service);
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
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow(service);
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
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow(service);
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
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow(service);
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
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow(service);
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

    // =========
    // = 包装类 =
    // =========

    /**
     * detail: 无障碍节点操作包装类
     * @author Ttt
     */
    public static final class Operation {

        // 无障碍节点
        private AccessibilityNodeInfo mNode;

        private Operation(final AccessibilityNodeInfo node) {
            this.mNode = node;
        }

        /**
         * 获取 Operation
         * @param node {@link AccessibilityNodeInfo}
         * @return {@link Operation}
         */
        public static Operation operation(final AccessibilityNodeInfo node) {
            return new Operation(node);
        }

        // =============
        // = 对外公开方法 =
        // =============

        /**
         * 获取无障碍节点
         * @return {@link AccessibilityNodeInfo}
         */
        public AccessibilityNodeInfo getNode() {
            return mNode;
        }

        // =

        /**
         * 模拟对应 Action 操作
         * @param action 操作意图
         * @return {@code true} success, {@code false} fail
         */
        public boolean performAction(final int action) {
            return performAction(mNode, action);
        }

        /**
         * 模拟对应 Action 操作
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @param action   操作意图
         * @return {@code true} success, {@code false} fail
         */
        public boolean performAction(
                final AccessibilityNodeInfo nodeInfo,
                final int action
        ) {
            if (nodeInfo != null) {
                try {
                    return nodeInfo.performAction(action);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "performAction");
                }
            }
            return false;
        }

        // =======
        // = 操作 =
        // =======

        // =======
        // = 点击 =
        // =======

        /**
         * 点击指定节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick() {
            return performClick(mNode);
        }

        /**
         * 点击指定节点
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick(final boolean clickParent) {
            return performClick(mNode, clickParent);
        }

        /**
         * 点击指定节点
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @param clickAll    判断是否点击全部节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick(
                final boolean clickParent,
                final boolean clickAll
        ) {
            return performClick(mNode, clickParent, clickAll);
        }

        // =

        /**
         * 点击指定节点
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick(final AccessibilityNodeInfo nodeInfo) {
            if (nodeInfo != null && nodeInfo.isClickable()) {
                return performAction(nodeInfo, AccessibilityNodeInfo.ACTION_CLICK);
            }
            return false;
        }

        /**
         * 点击指定节点
         * @param nodeInfo    {@link AccessibilityNodeInfo}
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick(
                final AccessibilityNodeInfo nodeInfo,
                final boolean clickParent
        ) {
            return performClick(nodeInfo, clickParent, false);
        }

        /**
         * 点击指定节点
         * @param nodeInfo    {@link AccessibilityNodeInfo}
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @param clickAll    判断是否点击全部节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick(
                final AccessibilityNodeInfo nodeInfo,
                final boolean clickParent,
                final boolean clickAll
        ) {
            if (nodeInfo == null) return false;
            if (clickParent) {
                if (nodeInfo.isClickable()) {
                    return performAction(nodeInfo, AccessibilityNodeInfo.ACTION_CLICK);
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

        // =======
        // = 长按 =
        // =======

        /**
         * 长按指定节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performLongClick() {
            return performLongClick(mNode);
        }

        /**
         * 长按指定节点
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performLongClick(final boolean clickParent) {
            return performLongClick(mNode, clickParent);
        }

        /**
         * 长按指定节点
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @param clickAll    判断是否点击全部节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performLongClick(
                final boolean clickParent,
                final boolean clickAll
        ) {
            return performLongClick(mNode, clickParent, clickAll);
        }

        // =

        /**
         * 长按指定节点
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performLongClick(final AccessibilityNodeInfo nodeInfo) {
            if (nodeInfo != null && nodeInfo.isClickable()) {
                return performAction(nodeInfo, AccessibilityNodeInfo.ACTION_LONG_CLICK);
            }
            return false;
        }

        /**
         * 长按指定节点
         * @param nodeInfo    {@link AccessibilityNodeInfo}
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performLongClick(
                final AccessibilityNodeInfo nodeInfo,
                final boolean clickParent
        ) {
            return performLongClick(nodeInfo, clickParent, false);
        }

        /**
         * 长按指定节点
         * @param nodeInfo    {@link AccessibilityNodeInfo}
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @param clickAll    判断是否点击全部节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performLongClick(
                final AccessibilityNodeInfo nodeInfo,
                final boolean clickParent,
                final boolean clickAll
        ) {
            if (nodeInfo == null) return false;
            if (clickParent) {
                if (nodeInfo.isClickable()) {
                    return performAction(nodeInfo, AccessibilityNodeInfo.ACTION_LONG_CLICK);
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
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 模拟全局对应 Action 操作
     * @param action 操作意图
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performGlobalAction(final int action) {
        return performGlobalAction(sService, action);
    }

    /**
     * 模拟全局对应 Action 操作
     * <pre>
     *     可自行传入滑动手势
     *     如:
     *     向上滑动
     *     {@link AccessibilityService#GESTURE_SWIPE_UP}
     * </pre>
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
            try {
                return service.performGlobalAction(action);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "performGlobalAction");
            }
        }
        return false;
    }

    // =

    /**
     * 触发返回键
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performActionBack() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_BACK);
    }

    /**
     * 触发返回键
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performActionBack(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_BACK);
    }

    /**
     * 触发 Home 键
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performActionHome() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_HOME);
    }

    /**
     * 触发 Home 键
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performActionHome(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_HOME);
    }

    /**
     * 启动长按电源按钮 Dialog
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean performActionPowerDialog() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
    }

    /**
     * 启动长按电源按钮 Dialog
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean performActionPowerDialog(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
    }

    /**
     * 锁定屏幕 ( 非锁屏 )
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static boolean performActionLockScreen() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
    }

    /**
     * 锁定屏幕 ( 非锁屏 )
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static boolean performActionLockScreen(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
    }

    /**
     * 截屏
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static boolean performActionTakeScreenshot() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
    }

    /**
     * 截屏
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static boolean performActionTakeScreenshot(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
    }

    /**
     * 打开通知栏
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performActionNotifications() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }

    /**
     * 打开通知栏
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performActionNotifications(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }

    /**
     * 最近打开应用列表
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performActionRecents() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    /**
     * 最近打开应用列表
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performActionRecents(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    /**
     * 打开设置
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean performActionQuickSettings() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
    }

    /**
     * 打开设置
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean performActionQuickSettings(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
    }

    /**
     * 分屏
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean performActionSplitScreen() {
        return performGlobalAction(sService, AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
    }

    /**
     * 分屏
     * @param service {@link AccessibilityService}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean performActionSplitScreen(final AccessibilityService service) {
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
    }

    // ==========
    // = 打印方法 =
    // ==========

    /**
     * detail: 无障碍日志打印
     * @author Ttt
     */
    public static final class Print {

        /**
         * 拼接 AccessibilityEvent 信息日志
         * @param event {@link AccessibilityEvent}
         * @return AccessibilityEvent 信息日志
         */
        public static String logEvent(final AccessibilityEvent event) {
            if (event == null) return null;

            try {
                StringBuilder builder = new StringBuilder();

                // 响应事件的应用包名
                builder.append("packageName: ").append(event.getPackageName());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // 事件类型
                int eventType = event.getEventType();
                builder.append("eventType: ").append(eventType);
                builder.append(" ( ").append(AccessibilityEvent.eventTypeToString(eventType));
                builder.append(" )");
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // 事件时间
                builder.append("eventTime: ").append(event.getEventTime());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // 响应事件窗口
                builder.append("windowId: ").append(event.getWindowId());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    builder.append("movementGranularity: ").append(event.getMovementGranularity());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    builder.append("action: ").append(event.getAction());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    int contentChangeTypes = event.getContentChangeTypes();
                    builder.append("contentChangeTypes: ").append(contentChangeTypes);
                    builder.append(" ( ").append(contentChangeTypesToString(contentChangeTypes));
                    builder.append(" )");
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    int windowChanges = event.getWindowChanges();
                    builder.append("windowChanges: ").append(windowChanges);
                    builder.append(" ( ").append(windowChangeTypesToString(windowChanges));
                    builder.append(" )");
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                for (CharSequence text : event.getText()) {
                    // 输出当前事件包含的文本信息
                    builder.append("text: ").append(text);
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                // 事件源的类名, 如 android.widget.TextView
                builder.append("source class: ").append(event.getClassName());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // 事件源信息
                builder.append("source: ").append(event.getSource());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                return builder.toString();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "Print.logEvent");
                return null;
            }
        }

        // =

        /**
         * copy AccessibilityEvent singleContentChangeTypeToString
         * @param type Event ContentChangeTypes
         * @return type String
         */
        public static String contentChangeTypesToString(final int type) {
            switch (type) {
                case AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION:
                    return "CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION";
                case AccessibilityEvent.CONTENT_CHANGE_TYPE_STATE_DESCRIPTION:
                    return "CONTENT_CHANGE_TYPE_STATE_DESCRIPTION";
                case AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE:
                    return "CONTENT_CHANGE_TYPE_SUBTREE";
                case AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT:
                    return "CONTENT_CHANGE_TYPE_TEXT";
                case AccessibilityEvent.CONTENT_CHANGE_TYPE_PANE_TITLE:
                    return "CONTENT_CHANGE_TYPE_PANE_TITLE";
                case AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED:
                    return "CONTENT_CHANGE_TYPE_UNDEFINED";
                case AccessibilityEvent.CONTENT_CHANGE_TYPE_PANE_APPEARED:
                    return "CONTENT_CHANGE_TYPE_PANE_APPEARED";
                case AccessibilityEvent.CONTENT_CHANGE_TYPE_PANE_DISAPPEARED:
                    return "CONTENT_CHANGE_TYPE_PANE_DISAPPEARED";
                default:
                    return Integer.toHexString(type);
            }
        }

        /**
         * copy AccessibilityEvent singleWindowChangeTypeToString
         * @param type Event WindowChanges
         * @return type String
         */
        public static String windowChangeTypesToString(final int type) {
            switch (type) {
                case AccessibilityEvent.WINDOWS_CHANGE_ADDED:
                    return "WINDOWS_CHANGE_ADDED";
                case AccessibilityEvent.WINDOWS_CHANGE_REMOVED:
                    return "WINDOWS_CHANGE_REMOVED";
                case AccessibilityEvent.WINDOWS_CHANGE_TITLE:
                    return "WINDOWS_CHANGE_TITLE";
                case AccessibilityEvent.WINDOWS_CHANGE_BOUNDS:
                    return "WINDOWS_CHANGE_BOUNDS";
                case AccessibilityEvent.WINDOWS_CHANGE_LAYER:
                    return "WINDOWS_CHANGE_LAYER";
                case AccessibilityEvent.WINDOWS_CHANGE_ACTIVE:
                    return "WINDOWS_CHANGE_ACTIVE";
                case AccessibilityEvent.WINDOWS_CHANGE_FOCUSED:
                    return "WINDOWS_CHANGE_FOCUSED";
                case AccessibilityEvent.WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED:
                    return "WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED";
                case AccessibilityEvent.WINDOWS_CHANGE_PARENT:
                    return "WINDOWS_CHANGE_PARENT";
                case AccessibilityEvent.WINDOWS_CHANGE_CHILDREN:
                    return "WINDOWS_CHANGE_CHILDREN";
                case AccessibilityEvent.WINDOWS_CHANGE_PIP:
                    return "WINDOWS_CHANGE_PIP";
                default:
                    return Integer.toHexString(type);
            }
        }
    }
}