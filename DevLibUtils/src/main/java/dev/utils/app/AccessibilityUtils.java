package dev.utils.app;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.provider.Settings;
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
 * ====
 * AccessibilityService 在 API < 18 的时候使用 AccessibilityService
 * <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
 */
public final class AccessibilityUtils {

    private AccessibilityUtils() {
    }

    // 日志TAG
    private static final String TAG = AccessibilityUtils.class.getSimpleName();

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
     * 打印Event 日志
     * @param event
     */
    public static void printAccessibilityEvent(AccessibilityEvent event){
        printAccessibilityEvent(event, TAG);
    }

    /**
     * 打印Event 日志
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
     * @param service
     * @param text
     * @return
     */
    private List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(AccessibilityService service, String text) {
        // 获取根节点
        AccessibilityNodeInfo accessibilityNodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (accessibilityNodeInfo == null)
            return null;
        // 通过文字找到当前的节点
        return accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
    }

    /**
     * 查找符合条件的节点
     * @param service
     * @param id
     * @return
     */
    private List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(AccessibilityService service, String id) {
        // 获取根节点
        AccessibilityNodeInfo accessibilityNodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (accessibilityNodeInfo == null)
            return null;
        // 通过文字找到当前的节点
        return accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
    }

    /**
     * 查找符合条件的节点
     * @param service
     * @param text
     * @param claName
     * @return
     */
    private List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(AccessibilityService service, String text, String claName) {
        List<AccessibilityNodeInfo> lists = new ArrayList<>();
        // 获取根节点
        AccessibilityNodeInfo accessibilityNodeInfo = service.getRootInActiveWindow();
        // 取得当前激活窗体的根节点
        if (accessibilityNodeInfo == null)
            return lists;
        // 通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
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

//    //获取根节点
//    AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//    //匹配Text获取节点
//    List<AccessibilityNodeInfo> list1 = rootNode.findAccessibilityNodeInfosByText("match_text");
//    //匹配id获取节点
//    List<AccessibilityNodeInfo> list2 = rootNode.findAccessibilityNodeInfosByViewId("match_id");
//    //获取子节点
//    AccessibilityNodeInfo infoNode = rootNode.getChild(index);

//    //模拟点击事件
//    target.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//    //模拟输入内容
//    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//    ClipData clip = ClipData.newPlainText("label", "");
//    clipboard.setPrimaryClip(clip);
//    target.performAction(AccessibilityNodeInfo.ACTION_PASTE);

//    //后退键
//    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
//    //Home键
//    performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
//    //模拟左滑
//    performGlobalAction(AccessibilityService.GESTURE_SWIPE_LEFT);


//    disableSelf()	禁用当前服务,也就是在服务可以通过该方法停止运行
//    findFoucs(int falg)	查找拥有特定焦点类型的控件
//    getRootInActiveWindow()	如果配置能够获取窗口内容,则会返回当前活动窗口的根结点
//    getSeviceInfo()	获取当前服务的配置信息
//    onAccessibilityEvent(AccessibilityEvent event)	有关AccessibilityEvent事件的回调函数.系统通过sendAccessibiliyEvent()不断的发送AccessibilityEvent到此处
//    performGlobalAction(int action)	执行全局操作,比如返回,回到主页,打开最近等操作
//    setServiceInfo(AccessibilityServiceInfo info)	设置当前服务的配置信息
//    getSystemService(String name)	获取系统服务
//    onKeyEvent(KeyEvent event)	如果允许服务监听按键操作,该方法是按键事件的回调,需要注意,这个过程发生了系统处理按键事件之前
//    onServiceConnected()	系统成功绑定该服务时被触发,也就是当你在设置中开启相应的服务,系统成功的绑定了该服务时会触发,通常我们可以在这里做一些初始化操作
}
