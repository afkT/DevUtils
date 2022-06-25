package dev.utils.app;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import dev.DevUtils;
import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.common.StringUtils;

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
        if (service != null) {
            try {
                service.disableSelf();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "disableSelf");
            }
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
        if (service != null) {
            try {
                return service.getServiceInfo();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getServiceInfo");
            }
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
        if (service != null && info != null) {
            try {
                service.setServiceInfo(info);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setServiceInfo");
            }
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
        if (service != null) {
            try {
                return service.getRootInActiveWindow();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getRootInActiveWindow");
            }
        }
        return null;
    }

    // ========
    // = 包装类 =
    // ========

    /**
     * detail: AccessibilityNodeInfo 筛选接口
     * @author Ttt
     */
    public interface NodeFilter {

        /**
         * 是否允许添加
         * @param nodeInfo 待校验 AccessibilityNodeInfo
         * @return {@code true} 允许, {@code false} 不允许
         */
        boolean accept(AccessibilityNodeInfo nodeInfo);
    }

    // =============
    // = Operation =
    // =============

    /**
     * 获取 Operation
     * @param nodeInfo {@link AccessibilityNodeInfo}
     * @return {@link Operation}
     */
    public static Operation operation(final AccessibilityNodeInfo nodeInfo) {
        return new Operation(nodeInfo);
    }

    /**
     * 获取 Operation ( Root Window Node )
     * @return {@link Operation}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Operation operation() {
        return new Operation(getRootInActiveWindow());
    }

    /**
     * 获取 Operation ( Root Window Node )
     * @param service {@link AccessibilityService}
     * @return {@link Operation}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Operation operation(final AccessibilityService service) {
        return new Operation(getRootInActiveWindow(service));
    }

    // ========
    // = Node =
    // ========

    /**
     * 获取 Node
     * @param nodeInfo {@link AccessibilityNodeInfo}
     * @return {@link Node}
     */
    public static Node node(final AccessibilityNodeInfo nodeInfo) {
        return new Node(nodeInfo);
    }

    /**
     * 获取 Node ( Root Window Node )
     * @return {@link Node}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Node node() {
        return new Node(getRootInActiveWindow());
    }

    /**
     * 获取 Node ( Root Window Node )
     * @param service {@link AccessibilityService}
     * @return {@link Node}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Node node(final AccessibilityService service) {
        return new Node(getRootInActiveWindow(service));
    }

    // ============
    // = 包装类实现 =
    // ============

    /**
     * detail: 无障碍节点操作包装类
     * @author Ttt
     */
    public static final class Operation {

        // 无障碍节点包装类
        private Node mNode;

        private Operation(final AccessibilityNodeInfo nodeInfo) {
            this.mNode = AccessibilityUtils.node(nodeInfo);
        }

        // =============
        // = 对外公开方法 =
        // =============

        /**
         * 获取 Node
         * @return {@link Node}
         */
        public Node node() {
            return mNode;
        }

        /**
         * 获取无障碍节点
         * @return {@link AccessibilityNodeInfo}
         */
        public AccessibilityNodeInfo getNodeInfo() {
            return mNode.getNodeInfo();
        }

        // =======
        // = 操作 =
        // =======

        /**
         * 模拟对应 Action 操作
         * @param action 操作意图
         * @return {@code true} success, {@code false} fail
         */
        public boolean performAction(final int action) {
            return mNode.performAction(action);
        }

        /**
         * 模拟对应 Action 操作
         * @param action    操作意图
         * @param arguments 操作参数
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performAction(
                final int action,
                final Bundle arguments
        ) {
            return mNode.performAction(action, arguments);
        }

        // =======
        // = 点击 =
        // =======

        /**
         * 点击指定节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick() {
            return mNode.performClick();
        }

        /**
         * 点击指定节点
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick(final boolean clickParent) {
            return mNode.performClick(clickParent);
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
            return mNode.performClick(clickParent, clickAll);
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
            return mNode.performLongClick();
        }

        /**
         * 长按指定节点
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performLongClick(final boolean clickParent) {
            return mNode.performLongClick(clickParent);
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
            return mNode.performLongClick(clickParent, clickAll);
        }

        // ==========
        // = 输入内容 =
        // ==========

        /**
         * 指定节点输入文本
         * @param text 文本内容
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public boolean inputText(final CharSequence text) {
            return mNode.inputText(text);
        }

        // ==========
        // = 节点获取 =
        // ==========

        /**
         * 查找符合条件的节点
         * @param focus 焦点类型
         * @return Operation
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public Operation findFocus(final int focus) {
            return operation(mNode.findFocus(focus));
        }

        /**
         * 查找符合条件的节点
         * @param focus     焦点类型
         * @param className 节点所属的类 ( 类名 )
         * @return Operation
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public Operation findFocus(
                final int focus,
                final String className
        ) {
            return operation(mNode.findFocus(focus, className));
        }

        /**
         * 查找符合条件的节点
         * @param text 文本内容 ( 搜索包含该文本内容的节点 )
         * @return Operation List
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public List<Operation> findAccessibilityNodeInfosByText(final String text) {
            List<Operation>             lists = new ArrayList<>();
            List<AccessibilityNodeInfo> nodes = mNode.findAccessibilityNodeInfosByText(text);
            for (int i = 0, len = nodes.size(); i < len; i++) {
                lists.add(operation(nodes.get(i)));
            }
            return lists;
        }

        /**
         * 查找符合条件的节点
         * @param text      文本内容 ( 搜索包含该文本内容的节点 )
         * @param className 节点所属的类 ( 类名 )
         * @return Operation List
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public List<Operation> findAccessibilityNodeInfosByText(
                final String text,
                final String className
        ) {
            List<Operation> lists = new ArrayList<>();
            List<AccessibilityNodeInfo> nodes = mNode.findAccessibilityNodeInfosByText(
                    text, className
            );
            for (int i = 0, len = nodes.size(); i < len; i++) {
                lists.add(operation(nodes.get(i)));
            }
            return lists;
        }

        /**
         * 查找符合条件的节点
         * @param id viewId
         * @return Operation List
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public List<Operation> findAccessibilityNodeInfosByViewId(final String id) {
            List<Operation>             lists = new ArrayList<>();
            List<AccessibilityNodeInfo> nodes = mNode.findAccessibilityNodeInfosByViewId(id);
            for (int i = 0, len = nodes.size(); i < len; i++) {
                lists.add(operation(nodes.get(i)));
            }
            return lists;
        }

        /**
         * 查找符合条件的节点
         * @param id        viewId
         * @param className 节点所属的类 ( 类名 )
         * @return Operation List
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public List<Operation> findAccessibilityNodeInfosByViewId(
                final String id,
                final String className
        ) {
            List<Operation> lists = new ArrayList<>();
            List<AccessibilityNodeInfo> nodes = mNode.findAccessibilityNodeInfosByViewId(
                    id, className
            );
            for (int i = 0, len = nodes.size(); i < len; i++) {
                lists.add(operation(nodes.get(i)));
            }
            return lists;
        }

        // ==========
        // = 循环查找 =
        // ==========

        /**
         * 查找全部子节点并进行筛选
         * @param filter AccessibilityNodeInfo 筛选接口
         * @return Operation List
         */
        public List<Operation> findByFilter(final NodeFilter filter) {
            List<Operation>             lists = new ArrayList<>();
            List<AccessibilityNodeInfo> nodes = mNode.findByFilter(filter);
            for (int i = 0, len = nodes.size(); i < len; i++) {
                lists.add(operation(nodes.get(i)));
            }
            return lists;
        }
    }

    /**
     * detail: 无障碍节点包装类
     * @author Ttt
     */
    public static final class Node {

        // 无障碍节点
        private AccessibilityNodeInfo mNodeInfo;
        // 无障碍节点操作包装类
        private Operation             mOperation;

        private Node(final AccessibilityNodeInfo nodeInfo) {
            this.mNodeInfo = nodeInfo;
        }

        // =============
        // = 对外公开方法 =
        // =============

        /**
         * 获取 Operation
         * @return {@link Operation}
         */
        public Operation operation() {
            if (mOperation == null) {
                mOperation = AccessibilityUtils.operation(mNodeInfo);
            }
            return mOperation;
        }

        /**
         * 获取无障碍节点
         * @return {@link AccessibilityNodeInfo}
         */
        public AccessibilityNodeInfo getNodeInfo() {
            return mNodeInfo;
        }

        // =======
        // = 操作 =
        // =======

        /**
         * 模拟对应 Action 操作
         * @param action 操作意图
         * @return {@code true} success, {@code false} fail
         */
        public boolean performAction(final int action) {
            return performAction(mNodeInfo, action);
        }

        /**
         * 模拟对应 Action 操作
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @param action   操作意图
         * @return {@code true} success, {@code false} fail
         */
        private boolean performAction(
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

        /**
         * 模拟对应 Action 操作
         * @param action    操作意图
         * @param arguments 操作参数
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performAction(
                final int action,
                final Bundle arguments
        ) {
            return performAction(mNodeInfo, action, arguments);
        }

        /**
         * 模拟对应 Action 操作
         * @param nodeInfo  {@link AccessibilityNodeInfo}
         * @param action    操作意图
         * @param arguments 操作参数
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private boolean performAction(
                final AccessibilityNodeInfo nodeInfo,
                final int action,
                final Bundle arguments
        ) {
            if (nodeInfo != null && arguments != null) {
                try {
                    return nodeInfo.performAction(action, arguments);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "performAction - arguments");
                }
            }
            return false;
        }

        // =======
        // = 点击 =
        // =======

        /**
         * 点击指定节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick() {
            return performClick(mNodeInfo);
        }

        /**
         * 点击指定节点
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performClick(final boolean clickParent) {
            return performClick(mNodeInfo, clickParent, false);
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
            return performClick(mNodeInfo, clickParent, clickAll);
        }

        // =

        /**
         * 点击指定节点
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private boolean performClick(final AccessibilityNodeInfo nodeInfo) {
            if (nodeInfo != null && nodeInfo.isClickable()) {
                return performAction(nodeInfo, AccessibilityNodeInfo.ACTION_CLICK);
            }
            return false;
        }

        /**
         * 点击指定节点
         * @param nodeInfo    {@link AccessibilityNodeInfo}
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @param clickAll    判断是否点击全部节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private boolean performClick(
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
            return performLongClick(mNodeInfo);
        }

        /**
         * 长按指定节点
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean performLongClick(final boolean clickParent) {
            return performLongClick(mNodeInfo, clickParent, false);
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
            return performLongClick(mNodeInfo, clickParent, clickAll);
        }

        // =

        /**
         * 长按指定节点
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private boolean performLongClick(final AccessibilityNodeInfo nodeInfo) {
            if (nodeInfo != null && nodeInfo.isClickable()) {
                return performAction(nodeInfo, AccessibilityNodeInfo.ACTION_LONG_CLICK);
            }
            return false;
        }

        /**
         * 长按指定节点
         * @param nodeInfo    {@link AccessibilityNodeInfo}
         * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
         * @param clickAll    判断是否点击全部节点
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private boolean performLongClick(
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

        // ==========
        // = 输入内容 =
        // ==========

        /**
         * 指定节点输入文本
         * @param text 文本内容
         * @return {@code true} success, {@code false} fail
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public boolean inputText(final CharSequence text) {
            if (mNodeInfo != null && text != null) {
                Bundle arguments = new Bundle();
                arguments.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text
                );
                return performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
            }
            return false;
        }

        // ==========
        // = 节点获取 =
        // ==========

        /**
         * 查找符合条件的节点
         * @param focus 焦点类型
         * @return 拥有特定焦点类型的节点
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public AccessibilityNodeInfo findFocus(final int focus) {
            if (mNodeInfo != null) {
                try {
                    // 通过指定的焦点类型找到当前的节点
                    return mNodeInfo.findFocus(focus);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "findFocus");
                }
            }
            return null;
        }

        /**
         * 查找符合条件的节点
         * @param focus     焦点类型
         * @param className 节点所属的类 ( 类名 )
         * @return 拥有特定焦点类型的节点
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public AccessibilityNodeInfo findFocus(
                final int focus,
                final String className
        ) {
            if (mNodeInfo != null && className != null) {
                AccessibilityNodeInfo node = findFocus(focus);
                if (node != null && className.equals(node.getClassName())) {
                    return node;
                }
            }
            return null;
        }

        /**
         * 查找符合条件的节点
         * @param text 文本内容 ( 搜索包含该文本内容的节点 )
         * @return 包含该文本内容的节点集合
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(final String text) {
            if (mNodeInfo != null && text != null) {
                try {
                    // 通过文字找到当前的节点
                    List<AccessibilityNodeInfo> nodes = mNodeInfo.findAccessibilityNodeInfosByText(text);
                    if (nodes != null) return nodes;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "findAccessibilityNodeInfosByText");
                }
            }
            return Collections.emptyList();
        }

        /**
         * 查找符合条件的节点
         * @param text      文本内容 ( 搜索包含该文本内容的节点 )
         * @param className 节点所属的类 ( 类名 )
         * @return 包含该文本内容, 且属于指定类的节点集合
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(
                final String text,
                final String className
        ) {
            List<AccessibilityNodeInfo> lists = new ArrayList<>();
            if (mNodeInfo != null && text != null && className != null) {
                List<AccessibilityNodeInfo> nodes = findAccessibilityNodeInfosByText(text);
                for (int i = 0, len = nodes.size(); i < len; i++) {
                    AccessibilityNodeInfo node = nodes.get(i);
                    if (node != null && className.equals(node.getClassName())) {
                        lists.add(node);
                    }
                }
            }
            return lists;
        }

        /**
         * 查找符合条件的节点
         * @param id viewId
         * @return 等于 viewId 的节点集合
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(final String id) {
            if (mNodeInfo != null && id != null) {
                try {
                    // 通过 id 找到当前的节点
                    List<AccessibilityNodeInfo> nodes = mNodeInfo.findAccessibilityNodeInfosByViewId(id);
                    if (nodes != null) return nodes;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "findAccessibilityNodeInfosByViewId");
                }
            }
            return Collections.emptyList();
        }

        /**
         * 查找符合条件的节点
         * @param id        viewId
         * @param className 节点所属的类 ( 类名 )
         * @return 等于 viewId, 且属于指定类的节点集合
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(
                final String id,
                final String className
        ) {
            List<AccessibilityNodeInfo> lists = new ArrayList<>();
            if (mNodeInfo != null && id != null && className != null) {
                List<AccessibilityNodeInfo> nodes = findAccessibilityNodeInfosByViewId(id);
                for (int i = 0, len = nodes.size(); i < len; i++) {
                    AccessibilityNodeInfo node = nodes.get(i);
                    if (node != null && className.equals(node.getClassName())) {
                        lists.add(node);
                    }
                }
            }
            return lists;
        }

        // ==========
        // = 循环查找 =
        // ==========

        /**
         * 查找全部子节点并进行筛选
         * @param filter AccessibilityNodeInfo 筛选接口
         * @return 筛选后的节点集合
         */
        public List<AccessibilityNodeInfo> findByFilter(final NodeFilter filter) {
            LinkedHashSet<AccessibilityNodeInfo> sets = new LinkedHashSet<>();
            if (mNodeInfo != null && filter != null) {
                recursiveNodeChild(mNodeInfo, sets, filter);
            }
            return new ArrayList<>(sets);
        }

        /**
         * 递归 AccessibilityNodeInfo 子节点并进行过滤添加
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @param data     符合条件数据源存储
         * @param filter   AccessibilityNodeInfo 筛选接口
         */
        private void recursiveNodeChild(
                final AccessibilityNodeInfo nodeInfo,
                final LinkedHashSet<AccessibilityNodeInfo> data,
                final NodeFilter filter
        ) {
            if (nodeInfo != null) {
                if (nodeInfo.getChildCount() == 0) {
                    if (filter.accept(nodeInfo)) {
                        data.add(nodeInfo);
                    }
                } else {
                    for (int i = 0, len = nodeInfo.getChildCount(); i < len; i++) {
                        recursiveNodeChild(nodeInfo.getChild(i), data, filter);
                    }
                }
            }
        }
    }

    // =============
    // = 全局操作方法 =
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

    /**
     * 模拟手势操作
     * @param gesture  模拟手势
     * @param callback 操作结果回调
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean dispatchGesture(
            final GestureDescription gesture,
            final AccessibilityService.GestureResultCallback callback
    ) {
        return dispatchGesture(sService, gesture, callback, DevUtils.getHandler());
    }

    /**
     * 模拟手势操作
     * @param gesture  模拟手势
     * @param callback 操作结果回调
     * @param handler  是否通过 Handler 回调
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean dispatchGesture(
            final GestureDescription gesture,
            final AccessibilityService.GestureResultCallback callback,
            final Handler handler
    ) {
        return dispatchGesture(sService, gesture, callback, handler);
    }

    /**
     * 模拟手势操作
     * <pre>
     *     需要设置 android:canPerformGestures="true"
     * </pre>
     * @param service  {@link AccessibilityService}
     * @param gesture  模拟手势
     * @param callback 操作结果回调
     * @param handler  是否通过 Handler 回调
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean dispatchGesture(
            final AccessibilityService service,
            final GestureDescription gesture,
            final AccessibilityService.GestureResultCallback callback,
            final Handler handler
    ) {
        if (service != null) {
            try {
                return service.dispatchGesture(gesture, callback, handler);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "dispatchGesture");
            }
        }
        return false;
    }

    // =============
    // = 全局快捷方法 =
    // =============

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
     * <pre>
     *     该类仅限开发阶段方便调试使用
     *     视情况使用 {@link Print#logComplete(AccessibilityEvent, AccessibilityService)}
     *     防止日志量过多 ( 一直循环子节点 )
     * </pre>
     */
    public static final class Print {

        // ==========================
        // = AccessibilityEvent Log =
        // ==========================

        /**
         * 拼接 AccessibilityEvent 信息日志
         * @param event {@link AccessibilityEvent}
         * @return AccessibilityEvent 信息日志
         */
        public static String logEvent(final AccessibilityEvent event) {
            return logEvent(event, true);
        }

        /**
         * 拼接 AccessibilityEvent 信息日志
         * @param event       {@link AccessibilityEvent}
         * @param printSource 是否打印 Source NodeInfo
         * @return AccessibilityEvent 信息日志
         */
        public static String logEvent(
                final AccessibilityEvent event,
                final boolean printSource
        ) {
            if (event == null) return null;

            try {
                StringBuilder builder = new StringBuilder();

                // 响应事件的应用包名
                builder.append("packageName: ");
                builder.append(event.getPackageName());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // 响应事件类, 如 android.widget.TextView
                builder.append("className: ");
                builder.append(event.getClassName());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // 事件类型
                int eventType = event.getEventType();
                builder.append("eventType: ");
                builder.append(eventType);
                builder.append(" ( ");
                builder.append(AccessibilityEvent.eventTypeToString(eventType));
                builder.append(" )");
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // 事件时间
                builder.append("eventTime: ");
                builder.append(event.getEventTime());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // 响应事件窗口
                builder.append("windowId: 0x");
                builder.append(Long.toHexString(event.getWindowId()));
                builder.append(" ( ");
                builder.append(event.getWindowId());
                builder.append(" )");
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    builder.append("movementGranularity: ");
                    builder.append(event.getMovementGranularity());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    builder.append("action: ");
                    builder.append(event.getAction());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    int contentChangeTypes = event.getContentChangeTypes();
                    builder.append("contentChangeTypes: ");
                    builder.append(contentChangeTypes);
                    builder.append(" ( ");
                    builder.append(contentChangeTypesToString(contentChangeTypes));
                    builder.append(" )");
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    int windowChanges = event.getWindowChanges();
                    builder.append("windowChanges: ");
                    builder.append(windowChanges);
                    builder.append(" ( ");
                    builder.append(windowChangeTypesToString(windowChanges));
                    builder.append(" )");
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                for (CharSequence text : event.getText()) {
                    // 输出当前事件包含的文本信息
                    builder.append("text: ");
                    builder.append(text);
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (printSource) {
                    String sourceLog = logNodeInfo(
                            event.getSource(),
                            StringUtils.appendSpace(2)
                    );
                    if (sourceLog != null) {
                        builder.append("source NodeInfo:");
                        builder.append(DevFinal.SYMBOL.NEW_LINE);
                        builder.append(sourceLog);
                    }
                }
                return builder.toString();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "Print.logEvent");
                return null;
            }
        }

        // =============================
        // = AccessibilityNodeInfo Log =
        // =============================

        /**
         * 拼接 AccessibilityNodeInfo 信息日志
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @return AccessibilityNodeInfo 信息日志
         */
        public static String logNodeInfo(final AccessibilityNodeInfo nodeInfo) {
            return logNodeInfo(nodeInfo, "");
        }

        /**
         * 拼接 AccessibilityNodeInfo 信息日志
         * @param nodeInfo  {@link AccessibilityNodeInfo}
         * @param delimiter 拼接符号
         * @return AccessibilityNodeInfo 信息日志
         */
        public static String logNodeInfo(
                final AccessibilityNodeInfo nodeInfo,
                final String delimiter
        ) {
            if (nodeInfo == null) return null;
            try {
                StringBuilder builder = new StringBuilder();

                builder.append(delimiter);
                builder.append("windowId: 0x");
                builder.append(Long.toHexString(nodeInfo.getWindowId()));
                builder.append(" ( ");
                builder.append(nodeInfo.getWindowId());
                builder.append(" )");
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("childCount: ");
                builder.append(nodeInfo.getChildCount());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                Rect boundsInParent = new Rect();
                nodeInfo.getBoundsInParent(boundsInParent);
                builder.append(delimiter);
                builder.append("boundsInParent: ");
                builder.append(boundsInParent);
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                Rect boundsInScreen = new Rect();
                nodeInfo.getBoundsInScreen(boundsInScreen);
                builder.append(delimiter);
                builder.append("boundsInScreen: ");
                builder.append(boundsInScreen);
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // ==========
                // = 分割开始 =
                // ==========

                builder.append(delimiter);
                builder.append(DevFinal.SYMBOL.HYPHEN);
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // ==========
                // = 分割结束 =
                // ==========

                builder.append(delimiter);
                builder.append("packageName: ");
                builder.append(nodeInfo.getPackageName());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("className: ");
                builder.append(nodeInfo.getClassName());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("text: ");
                builder.append(nodeInfo.getText());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.append(delimiter);
                    builder.append("error: ");
                    builder.append(nodeInfo.getError());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.append(delimiter);
                    builder.append("maxTextLength: ");
                    builder.append(nodeInfo.getMaxTextLength());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    builder.append(delimiter);
                    builder.append("stateDescription: ");
                    builder.append(nodeInfo.getStateDescription());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                builder.append(delimiter);
                builder.append("contentDescription: ");
                builder.append(nodeInfo.getContentDescription());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    builder.append(delimiter);
                    builder.append("tooltipText: ");
                    builder.append(nodeInfo.getTooltipText());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    builder.append(delimiter);
                    builder.append("viewIdResName: ");
                    builder.append(nodeInfo.getViewIdResourceName());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int granularities = nodeInfo.getMovementGranularities();
                    builder.append(delimiter);
                    builder.append("movementGranularities: ");
                    builder.append(getMovementGranularitySymbolicName(granularities));
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                // ==========
                // = 分割开始 =
                // ==========

                builder.append(delimiter);
                builder.append(DevFinal.SYMBOL.HYPHEN);
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                // ==========
                // = 分割结束 =
                // ==========

                builder.append(delimiter);
                builder.append("checkable: ");
                builder.append(nodeInfo.isCheckable());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("checked: ");
                builder.append(nodeInfo.isChecked());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("focusable: ");
                builder.append(nodeInfo.isFocusable());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("focused: ");
                builder.append(nodeInfo.isFocused());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("selected: ");
                builder.append(nodeInfo.isSelected());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("clickable: ");
                builder.append(nodeInfo.isClickable());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("longClickable: ");
                builder.append(nodeInfo.isLongClickable());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    builder.append(delimiter);
                    builder.append("contextClickable: ");
                    builder.append(nodeInfo.isContextClickable());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                builder.append(delimiter);
                builder.append("enabled: ");
                builder.append(nodeInfo.isEnabled());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("password: ");
                builder.append(nodeInfo.isPassword());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                builder.append(delimiter);
                builder.append("scrollable: ");
                builder.append(nodeInfo.isScrollable());
                builder.append(DevFinal.SYMBOL.NEW_LINE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    builder.append(delimiter);
                    builder.append("editable: ");
                    builder.append(nodeInfo.isEditable());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    builder.append(delimiter);
                    builder.append("visible: ");
                    builder.append(nodeInfo.isVisibleToUser());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    builder.append(delimiter);
                    builder.append("canOpenPopup: ");
                    builder.append(nodeInfo.canOpenPopup());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    builder.append(delimiter);
                    builder.append("dismiss: ");
                    builder.append(nodeInfo.isDismissable());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.append(delimiter);
                    builder.append("importantForAccessibility: ");
                    builder.append(nodeInfo.isImportantForAccessibility());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    builder.append(delimiter);
                    builder.append("accessibilityFocused: ");
                    builder.append(nodeInfo.isAccessibilityFocused());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.append(delimiter);
                    builder.append("actions: ");
                    builder.append(nodeInfo.getActionList());
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }
                return builder.toString();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "Print.logNodeInfo");
                return null;
            }
        }

        // =============
        // = 快捷全面方法 =
        // =============

        /**
         * 拼接 AccessibilityEvent、AccessibilityService 完整信息日志
         * <pre>
         *     打印包含
         *     AccessibilityEvent Source Node、Source Node All Child
         *     AccessibilityService RootInActiveWindow or WindowList
         * </pre>
         * @param event   {@link AccessibilityEvent}
         * @param service {@link AccessibilityService}
         * @return AccessibilityEvent、AccessibilityService 完整信息日志
         */
        public static String logComplete(
                final AccessibilityEvent event,
                final AccessibilityService service
        ) {
            if (event == null || service == null) return null;
            StringBuilder builder = new StringBuilder();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (service.getRootInActiveWindow() != null) {
                    builder.append("logComplete - rootInActiveWindow:");
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                    logNodeInfoChild(service.getRootInActiveWindow(), builder);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        List<AccessibilityWindowInfo> windows = service.getWindows();
                        if (windows != null) {
                            for (int i = 0, len = windows.size(); i < len; i++) {
                                builder.append("logComplete - windows child (");
                                builder.append(i).append("):");
                                builder.append(DevFinal.SYMBOL.NEW_LINE);
                                logNodeInfoChild(service.getRootInActiveWindow(), builder);
                            }
                        }
                    }
                }
            }
            builder.append("logComplete - logEvent:");
            builder.append(DevFinal.SYMBOL.NEW_LINE);
            builder.append(logEvent(event, false));
            return builder.toString();
        }

        /**
         * 拼接 AccessibilityNodeInfo 以及 Child 信息日志
         * @param nodeInfo {@link AccessibilityNodeInfo}
         * @param builder  拼接 Builder
         */
        public static void logNodeInfoChild(
                final AccessibilityNodeInfo nodeInfo,
                final StringBuilder builder
        ) {
            logNodeInfoChild(nodeInfo, builder, 0);
        }

        /**
         * 拼接 AccessibilityNodeInfo 以及 Child 信息日志
         * @param nodeInfo    {@link AccessibilityNodeInfo}
         * @param builder     拼接 Builder
         * @param spaceNumber 空格数量
         */
        public static void logNodeInfoChild(
                final AccessibilityNodeInfo nodeInfo,
                final StringBuilder builder,
                final int spaceNumber
        ) {
            if (nodeInfo != null) {
                if (nodeInfo.getChildCount() == 0) {
                    String nodeLog = logNodeInfo(
                            nodeInfo, StringUtils.appendSpace(spaceNumber)
                    );
                    builder.append(nodeLog);
                } else {
                    for (int i = 0, len = nodeInfo.getChildCount(); i < len; i++) {
                        try {
                            logNodeInfoChild(
                                    nodeInfo.getChild(i),
                                    builder, spaceNumber + 2
                            );
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }

        // ==========
        // = 系统方法 =
        // ==========

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

        /**
         * copy AccessibilityNodeInfo getMovementGranularitySymbolicName
         * @param granularity NodeInfo granularity
         * @return granularity String
         */
        public static String movementGranularitiesToString(final int granularity) {
            switch (granularity) {
                case AccessibilityNodeInfo.MOVEMENT_GRANULARITY_CHARACTER:
                    return "MOVEMENT_GRANULARITY_CHARACTER";
                case AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD:
                    return "MOVEMENT_GRANULARITY_WORD";
                case AccessibilityNodeInfo.MOVEMENT_GRANULARITY_LINE:
                    return "MOVEMENT_GRANULARITY_LINE";
                case AccessibilityNodeInfo.MOVEMENT_GRANULARITY_PARAGRAPH:
                    return "MOVEMENT_GRANULARITY_PARAGRAPH";
                case AccessibilityNodeInfo.MOVEMENT_GRANULARITY_PAGE:
                    return "MOVEMENT_GRANULARITY_PAGE";
                default:
                    return null;
            }
        }

        /**
         * 封装 AccessibilityNodeInfo#toString() granularity 拼接代码
         * @param granularities NodeInfo movementGranularities
         * @return movementGranularities 拼接代码
         */
        public static String getMovementGranularitySymbolicName(final int granularities) {
            StringBuilder builder = new StringBuilder();
            int           temp    = granularities;

            builder.append("[");
            while (temp != 0) {
                final int granularity = 1 << Integer.numberOfTrailingZeros(temp);
                temp &= ~granularity;
                builder.append(movementGranularitiesToString(granularity));
                if (temp != 0) {
                    builder.append(", ");
                }
            }
            builder.append("]");
            return builder.toString();
        }
    }
}