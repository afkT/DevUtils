package afkt.project.model.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.utils.app.ResourceUtils;

/**
 * detail: Button Value 实体类
 * @author Ttt
 */
public class ButtonValue {

    // 按钮类型
    public int type;
    // 文案
    public String text;

    public ButtonValue(int type, int id) {
        this(type, ResourceUtils.getString(id));
    }

    public ButtonValue(int type, String text) {
        this.type = type;
        this.text = text;
    }

    // ================
    // = 快速获取集合 =
    // ================

    /**
     * 获取 Main Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getMainButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(MODULE_FRAMEWORK, "Framework 架构"));
        lists.add(new ButtonValue(MODULE_LIB, "Lib 框架"));
        lists.add(new ButtonValue(MODULE_UI, "UI 效果"));
        lists.add(new ButtonValue(MODULE_OTHER, "其他功能"));
        return lists;
    }

    /**
     * 获取 Module 功能 Button Value 集合
     * @param type Module Type
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getModuleButtonValues(int type) {
        switch (type) {
            case ButtonValue.MODULE_FRAMEWORK:
                break;
            case ButtonValue.MODULE_LIB:
                break;
            case ButtonValue.MODULE_UI:
                break;
            case ButtonValue.MODULE_OTHER:
                return getModuleOtherButtonValues();
        }
        return Collections.emptyList();
    }

    // ==========
    // = Module =
    // ==========

    /**
     * 获取 Other Module Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getModuleOtherButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_LISTENER, "事件 / 广播监听 ( 网络状态、屏幕旋转等 )"));
        lists.add(new ButtonValue(BTN_NOTIFICATION_SERVICE, "通知栏监听服务 ( NotificationService )"));
        lists.add(new ButtonValue(BTN_ACCESSIBILITY_SERVICE, "无障碍监听服务 ( AccessibilityListenerService )"));
        lists.add(new ButtonValue(BTN_WIFI, "Wifi 相关 ( 热点 )"));
        lists.add(new ButtonValue(BTN_FUNCTION, "铃声、震动、通知栏等功能"));
        return lists;
    }

    /**
     * 获取 Listener Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getListenerButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_WIFI_LISTENER, "Wifi 监听"));
        lists.add(new ButtonValue(BTN_NETWORK_LISTENER, "网络监听"));
        lists.add(new ButtonValue(BTN_PHONE_LISTENER, "电话监听"));
        lists.add(new ButtonValue(BTN_SMS_LISTENER, "短信监听"));
        lists.add(new ButtonValue(BTN_TIME_LISTENER, "时区、时间监听"));
        lists.add(new ButtonValue(BTN_SCREEN_LISTENER, "屏幕监听"));
        lists.add(new ButtonValue(BTN_ROTA_LISTENER, "屏幕旋转监听 ( 重力传感器 )"));
        lists.add(new ButtonValue(BTN_ROTA2_LISTENER, "屏幕旋转监听 ( OrientationEventListener )"));
        return lists;
    }

    /**
     * 获取 Notification Service Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getNotificationServiceButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_NOTIFICATION_SERVICE_CHECK, "检查是否开启"));
        lists.add(new ButtonValue(BTN_NOTIFICATION_SERVICE_REGISTER, "开始监听"));
        lists.add(new ButtonValue(BTN_NOTIFICATION_SERVICE_UNREGISTER, "注销监听"));
        return lists;
    }

    /**
     * 获取 Accessibility Listener Service Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getAccessibilityListenerServiceButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_ACCESSIBILITY_SERVICE_CHECK, "检查是否开启"));
        lists.add(new ButtonValue(BTN_ACCESSIBILITY_SERVICE_REGISTER, "开始监听"));
        lists.add(new ButtonValue(BTN_ACCESSIBILITY_SERVICE_UNREGISTER, "注销监听"));
        return lists;
    }

    /**
     * 获取 Wifi Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getWifiButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_WIFI_OPEN, "打开 Wifi"));
        lists.add(new ButtonValue(BTN_WIFI_CLOSE, "关闭 Wifi"));
        lists.add(new ButtonValue(BTN_WIFI_HOT_OPEN, "打开 Wifi 热点"));
        lists.add(new ButtonValue(BTN_WIFI_HOT_CLOSE, "关闭 Wifi 热点"));
        lists.add(new ButtonValue(BTN_WIFI_LISTENER_REGISTER, "注册 Wifi 监听"));
        lists.add(new ButtonValue(BTN_WIFI_LISTENER_UNREGISTER, "注销 Wifi 监听"));
        return lists;
    }

    /**
     * 获取 Function Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getFunctionButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_FUNCTION_VIBRATE, "震动"));
        lists.add(new ButtonValue(BTN_FUNCTION_BEEP, "铃声 - 播放一小段音频"));
        lists.add(new ButtonValue(BTN_FUNCTION_NOTIFICATION, "通知消息"));
        lists.add(new ButtonValue(BTN_FUNCTION_NOTIFICATION_REMOVE, "移除消息"));
        lists.add(new ButtonValue(BTN_FUNCTION_HOME, "回到桌面"));
        return lists;
    }

    // ========
    // = 常量 =
    // ========

    private static final int BASE = 1000;
    // 架构
    public static final int MODULE_FRAMEWORK = BASE + 1000;
    // Lib
    public static final int MODULE_LIB = BASE + 2000;
    // UI
    public static final int MODULE_UI = BASE + 3000;
    // 其他功能
    public static final int MODULE_OTHER = BASE + 4000;

    // =

    // 事件 / 广播监听 ( 网络状态、屏幕旋转等 )
    public static final int BTN_LISTENER = MODULE_OTHER;
    // Wifi 监听
    public static final int BTN_WIFI_LISTENER = BTN_LISTENER + 1;
    // 网络监听
    public static final int BTN_NETWORK_LISTENER = BTN_LISTENER + 2;
    // 电话监听
    public static final int BTN_PHONE_LISTENER = BTN_LISTENER + 3;
    // 短信监听
    public static final int BTN_SMS_LISTENER = BTN_LISTENER + 4;
    // 时区、时间监听
    public static final int BTN_TIME_LISTENER = BTN_LISTENER + 5;
    // 屏幕监听
    public static final int BTN_SCREEN_LISTENER = BTN_LISTENER + 6;
    // 屏幕旋转监听 ( 重力传感器 )
    public static final int BTN_ROTA_LISTENER = BTN_LISTENER + 7;
    // 屏幕旋转监听 ( OrientationEventListener )
    public static final int BTN_ROTA2_LISTENER = BTN_LISTENER + 8;

    // 通知栏监听服务 ( NotificationService )
    public static final int BTN_NOTIFICATION_SERVICE = MODULE_OTHER + 100;
    // 检查是否开启
    public static final int BTN_NOTIFICATION_SERVICE_CHECK = BTN_NOTIFICATION_SERVICE + 1;
    // 开始监听
    public static final int BTN_NOTIFICATION_SERVICE_REGISTER = BTN_NOTIFICATION_SERVICE + 2;
    // 注销监听
    public static final int BTN_NOTIFICATION_SERVICE_UNREGISTER = BTN_NOTIFICATION_SERVICE + 3;

    // 无障碍监听服务 ( AccessibilityListenerService )
    public static final int BTN_ACCESSIBILITY_SERVICE = MODULE_OTHER + 200;
    // 检查是否开启
    public static final int BTN_ACCESSIBILITY_SERVICE_CHECK = BTN_ACCESSIBILITY_SERVICE + 1;
    // 开始监听
    public static final int BTN_ACCESSIBILITY_SERVICE_REGISTER = BTN_ACCESSIBILITY_SERVICE + 2;
    // 注销监听
    public static final int BTN_ACCESSIBILITY_SERVICE_UNREGISTER = BTN_ACCESSIBILITY_SERVICE + 3;

    // Wifi 相关 ( 热点 )
    public static final int BTN_WIFI = MODULE_OTHER + 300;
    // 打开 Wifi
    public static final int BTN_WIFI_OPEN = BTN_WIFI + 1;
    // 关闭 Wifi
    public static final int BTN_WIFI_CLOSE = BTN_WIFI + 2;
    // 打开 Wifi 热点
    public static final int BTN_WIFI_HOT_OPEN = BTN_WIFI + 3;
    // 关闭 Wifi 热点
    public static final int BTN_WIFI_HOT_CLOSE = BTN_WIFI + 4;
    // 注册 Wifi 监听
    public static final int BTN_WIFI_LISTENER_REGISTER = BTN_WIFI + 5;
    // 注销 Wifi 监听
    public static final int BTN_WIFI_LISTENER_UNREGISTER = BTN_WIFI + 6;

    // 铃声、震动、通知栏等功能
    public static final int BTN_FUNCTION = MODULE_OTHER + 400;
    // 震动
    public static final int BTN_FUNCTION_VIBRATE = BTN_FUNCTION + 1;
    // 铃声 - 播放一小段音频
    public static final int BTN_FUNCTION_BEEP = BTN_FUNCTION + 2;
    // 通知消息
    public static final int BTN_FUNCTION_NOTIFICATION = BTN_FUNCTION + 3;
    // 移除消息
    public static final int BTN_FUNCTION_NOTIFICATION_REMOVE = BTN_FUNCTION + 4;
    // 回到桌面
    public static final int BTN_FUNCTION_HOME = BTN_FUNCTION + 5;

}
