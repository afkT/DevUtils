package dev.utils.app;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 无障碍功能工具类
 * Created by Ttt
 * https://www.jianshu.com/p/981e7de2c7be
 * https://www.jianshu.com/p/65afab3d1e2a
 * https://www.jianshu.com/p/f67e950d84f7
 * https://blog.csdn.net/nishitouzhuma/article/details/51584606
 * https://blog.csdn.net/jw_66666/article/details/76571897
 * https://blog.csdn.net/dd864140130/article/details/51794318
 * https://nesscurie.github.io/2017/03/07/2.Android%E8%BE%85%E5%8A%A9%E5%8A%9F%E8%83%BD%E5%A4%A7%E8%87%B4%E8%A7%A3%E6%9E%90,%E9%80%9A%E8%BF%87adb%E8%BF%90%E8%A1%8C%E7%BA%AFjava%E4%BB%A3%E7%A0%81%E6%89%93%E5%BC%80%E5%BA%94%E7%94%A8%E7%9A%84%E8%BE%85%E5%8A%A9%E5%8A%9F%E8%83%BD/
 * ====
 * AccessibilityService 在 API < 18 的时候使用 AccessibilityService
 * <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
 */
public final class AccessibilityUtils {

    private AccessibilityUtils() {
    }

    // 日志TAG
    private static final String TAG = AccessibilityUtils.class.getSimpleName();
    // AccessibilityService 对象
    private static AccessibilityService service = null;

    /**
     * 获取 AccessibilityService 对象
     * @return
     */
    public static AccessibilityService getService() {
        return service;
    }

    /**
     * 设置 AccessibilityService 对象
     * @param service
     */
    public static void setService(AccessibilityService service) {
        AccessibilityUtils.service = service;
    }

    // =

    /**
     * 检查是否开启无障碍功能
     * @return
     */
    public static boolean checkAccessibility(){
        return checkAccessibility(DevUtils.getContext().getPackageName());
    }

    /**
     * 检查是否开启无障碍功能
     * @param pkgName
     * @return
     */
    public static boolean checkAccessibility(String pkgName) {
        // 判断辅助功能是否开启
        if (!AccessibilityUtils.isAccessibilitySettingsOn(pkgName)) {
            // 引导至辅助功能设置页面
            DevUtils.getContext().startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return false;
        }
        return true;
    }

    /**
     * 判断是否开启无障碍功能
     * @param pkgName
     * @return
     */
    public static boolean isAccessibilitySettingsOn(String pkgName) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(DevUtils.getContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            LogPrintUtils.eTag(TAG, e, "isAccessibilitySettingsOn - Settings.Secure.ACCESSIBILITY_ENABLED");
        }
        if (accessibilityEnabled == 1) {
            try {
                String services = Settings.Secure.getString(DevUtils.getContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (services != null) {
                    return services.toLowerCase().contains(pkgName.toLowerCase());
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isAccessibilitySettingsOn - Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES");
            }
        }
        return false;
    }

    // == 快捷方法 ==

    /**
     * 打印 AccessibilityEvent 信息日志
     * @param event
     */
    public static void printAccessibilityEvent(AccessibilityEvent event){
        printAccessibilityEvent(event, TAG);
    }

    /**
     * 打印 AccessibilityEvent 信息日志
     * @param event
     * @param tag
     */
    public static void printAccessibilityEvent(AccessibilityEvent event, String tag){
        if (!LogPrintUtils.isPrintLog()){
            return;
        }
        LogPrintUtils.dTag(tag,"-------------------------------------------------------------");

        int eventType = event.getEventType();//事件类型
        LogPrintUtils.dTag(tag, "packageName:" + event.getPackageName() + "");//响应事件的包名，也就是哪个应用才响应了这个事件
        LogPrintUtils.dTag(tag, "source:" + event.getSource() + "");//事件源信息
        LogPrintUtils.dTag(tag, "source class:" + event.getClassName() + "");//事件源的类名，比如android.widget.TextView
        LogPrintUtils.dTag(tag, "event type(int):" + eventType + "");

        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                LogPrintUtils.dTag(tag, "event type:TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                LogPrintUtils.dTag(tag, "event type:TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED://View获取到焦点
                LogPrintUtils.dTag(tag, "event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                LogPrintUtils.dTag(tag, "event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                LogPrintUtils.dTag(tag, "event type:TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                LogPrintUtils.dTag(tag, "event type:TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                LogPrintUtils.dTag(tag, "event type:TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                LogPrintUtils.dTag(tag, "event type:TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                LogPrintUtils.dTag(tag, "event type:TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                LogPrintUtils.dTag(tag, "event type:TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
        }

        for (CharSequence txt : event.getText()) {
            LogPrintUtils.dTag(tag, "text:" + txt);//输出当前事件包含的文本信息
        }
        LogPrintUtils.dTag(tag, "-------------------------------------------------------------");
    }

    // === 其他处理 ===

    /**
     * 查找符合条件的节点
     * @param text
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text) {
        return findAccessibilityNodeInfosByText(service, text);
    }

    /**
     * 查找符合条件的节点
     * @param service
     * @param text
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(AccessibilityService service, String text) {
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null)
            return null;
        // 通过文字找到当前的节点
        return nodeInfo.findAccessibilityNodeInfosByText(text);
    }

    /**
     * 查找符合条件的节点
     * @param id
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(String id) {
        return findAccessibilityNodeInfosByViewId(service, id);
    }

    /**
     * 查找符合条件的节点
     * @param service
     * @param id
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(AccessibilityService service, String id) {
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null)
            return null;
        // 通过文字找到当前的节点
        return nodeInfo.findAccessibilityNodeInfosByViewId(id);
    }

    /**
     * 查找符合条件的节点
     * @param text
     * @param claName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, String claName) {
        return findAccessibilityNodeInfosByText(service, text, claName);
    }

    /**
     * 查找符合条件的节点
     * @param service
     * @param text
     * @param claName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(AccessibilityService service, String text, String claName) {
        List<AccessibilityNodeInfo> lists = new ArrayList<>();
        // 获取根节点
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (nodeInfo == null)
            return lists;
        // 通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 判断是否符合的类型
            if (node.getClassName().equals(claName) && node.isEnabled()) {
                // 保存符合条件
                lists.add(node);
            }
        }
        return lists;
    }

    // = 操作 =

    /**
     * 点击指定的节点
     * @param nodeInfo
     */
    public static boolean performClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null){
            if (nodeInfo.isClickable()){
                return preformAction(nodeInfo, AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
        return false;
    }

    /**
     * 点击指定的节点
     * 如果当前节点不可点击, 可以尝试往上追溯, 点击父节点, 直到该节点可以点击为止
     * @param nodeInfo
     * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     */
    public static void performClick(AccessibilityNodeInfo nodeInfo, boolean clickParent) {
        performClick(nodeInfo, clickParent, false);
    }

    /**
     * 点击指定的节点
     * 如果当前节点不可点击, 可以尝试往上追溯, 点击父节点, 直到该节点可以点击为止
     * @param nodeInfo
     * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     * @param clickAll 判断是否点击全部
     */
    public static void performClick(AccessibilityNodeInfo nodeInfo, boolean clickParent, boolean clickAll) {
        if (clickParent) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            } else {
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                while (parent != null) {
                    if (performClick(parent)){
                        // 如果
                        if (!clickAll){
                            return;
                        }
                    }
                    parent = parent.getParent();
                }
            }
        } else {
            performClick(nodeInfo);
        }
    }

    // =

    /**
     * 长按指定的节点
     * @param nodeInfo
     */
    public static boolean performLongClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null){
            if (nodeInfo.isClickable()){
                return preformAction(nodeInfo, AccessibilityNodeInfo.ACTION_LONG_CLICK);
            }
        }
        return false;
    }

    /**
     * 长按指定的节点
     * 如果当前节点不可点击, 可以尝试往上追溯, 点击父节点, 直到该节点可以点击为止
     * @param nodeInfo
     * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     */
    public static void performLongClick(AccessibilityNodeInfo nodeInfo, boolean clickParent) {
        performLongClick(nodeInfo, clickParent, false);
    }

    /**
     * 长按指定的节点
     * 如果当前节点不可点击, 可以尝试往上追溯, 点击父节点, 直到该节点可以点击为止
     * @param nodeInfo
     * @param clickParent 如果当前节点不可点击, 是否往上追溯点击父节点, 直到点击成功或没有父节点
     * @param clickAll 判断是否点击全部
     */
    public static void performLongClick(AccessibilityNodeInfo nodeInfo, boolean clickParent, boolean clickAll) {
        if (clickParent) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
            } else {
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                while (parent != null) {
                    if (performLongClick(parent)){
                        // 如果
                        if (!clickAll){
                            return;
                        }
                    }
                    parent = parent.getParent();
                }
            }
        } else {
            performLongClick(nodeInfo);
        }
    }

    // =

    /**
     * 触发返回键
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionBack(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_BACK);
    }

    /**
     * 触发返回键
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionBack(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_BACK);
    }

    /**
     * 触发Home键
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionHome(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_HOME);
    }

    /**
     * 触发Home键
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionHome(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_HOME);
    }

    /**
     * 启动长按电源按钮 Dialog
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionPowerDialog(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
    }

    /**
     * 启动长按电源按钮 Dialog
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionPowerDialog(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
    }

    /**
     * 锁定屏幕 -> 非锁屏
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionLockScreen(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
    }

    /**
     * 锁定屏幕 -> 非锁屏
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionLockScreen(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
    }

    /**
     * 截图
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionTakeScreenshot(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
    }

    /**
     * 截图
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionTakeScreenshot(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
    }

    /**
     * 打开通知栏
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionNotifications(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }

    /**
     * 打开通知栏
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionNotifications(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }

    /**
     * 最近打开应用列表
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionRecents(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    /**
     * 最近打开应用列表
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionRecents(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    /**
     * 打开设置
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionQuickSettings(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
    }

    /**
     * 打开设置
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionQuickSettings(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
    }

    /**
     * 分屏
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionSplitScreen(){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
    }

    /**
     * 分屏
     * @param service
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean preformActionSplitScreen(AccessibilityService service){
        return performGlobalAction(service, AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
    }


    // == 内部封装 ==

    /**
     * 模拟对应 Action 操作
     * @param nodeInfo
     */
    public static boolean preformAction(AccessibilityNodeInfo nodeInfo, int action) {
        if (nodeInfo != null){
            return nodeInfo.performAction(action);
        }
        return false;
    }

    /**
     * 模拟全局对应 Action 操作
     * @param service
     * @param action
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean performGlobalAction(AccessibilityService service, int action) {
        if (service != null){
            return service.performGlobalAction(action);
        }
        return false;
    }

//    // 获取根节点
//    AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//    // 匹配Text获取节点
//    List<AccessibilityNodeInfo> list1 = rootNode.findAccessibilityNodeInfosByText("match_text");
//    // 匹配id获取节点
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

//    disableSelf()	禁用当前服务,也就是在服务可以通过该方法停止运行
//    getSystemService(String name)	获取系统服务
//    onServiceConnected()	系统成功绑定该服务时被触发,也就是当你在设置中开启相应的服务,系统成功的绑定了该服务时会触发,通常我们可以在这里做一些初始化操作
//    getSeviceInfo()	获取当前服务的配置信息
//    setServiceInfo(AccessibilityServiceInfo info)	设置当前服务的配置信息
//    onAccessibilityEvent(AccessibilityEvent event)	有关AccessibilityEvent事件的回调函数.系统通过sendAccessibiliyEvent()不断的发送AccessibilityEvent到此处
//    performGlobalAction(int action)	执行全局操作,比如返回,回到主页,打开最近等操作
//    findFoucs(int falg)	查找拥有特定焦点类型的控件
//    getRootInActiveWindow()	如果配置能够获取窗口内容,则会返回当前活动窗口的根结点
//    onKeyEvent(KeyEvent event)	如果允许服务监听按键操作,该方法是按键事件的回调,需要注意,这个过程发生了系统处理按键事件之前
}
