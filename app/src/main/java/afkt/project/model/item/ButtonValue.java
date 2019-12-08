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
                return getModuleFrameworkButtonValues();
            case ButtonValue.MODULE_LIB:
                return getModuleLibButtonValues();
            case ButtonValue.MODULE_UI:
                return getModuleUIButtonValues();
            case ButtonValue.MODULE_OTHER:
                return getModuleOtherButtonValues();
        }
        return Collections.emptyList();
    }

    // =============
    // = Framework =
    // =============

    /**
     * 获取 Framework Module Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getModuleFrameworkButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_MVP, "MVP"));
        lists.add(new ButtonValue(BTN_MVVM, "MVVM"));
        return lists;
    }

    // =======
    // = Lib =
    // =======

    /**
     * 获取 Lib Module Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getModuleLibButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_EVENT_BUS, "EventBusUtils"));
        lists.add(new ButtonValue(BTN_GLIDE, "GlideUtils"));
        lists.add(new ButtonValue(BTN_IMAGE_LOADER, "ImageLoaderUtils"));
        lists.add(new ButtonValue(BTN_GSON, "GsonUtils"));
        lists.add(new ButtonValue(BTN_FASTJSON, "FastjsonUtils"));
        lists.add(new ButtonValue(BTN_ZXING, "ZXingQRCodeUtils"));
        lists.add(new ButtonValue(BTN_PICTURE_SELECTOR, "PictureSelectorUtils"));
        return lists;
    }

    /**
     * 获取 Event Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getEventButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_EVENT_REGISTER, "Register"));
        lists.add(new ButtonValue(BTN_EVENT_UNREGISTER, "unRegister"));
        lists.add(new ButtonValue(BTN_EVENT_CLEAN_STICKY, "清空粘性事件"));
        lists.add(new ButtonValue(BTN_EVENT_SEND, "发送事件"));
        lists.add(new ButtonValue(BTN_EVENT_SEND_STICKY, "发送粘性事件"));
        return lists;
    }

    // ======
    // = UI =
    // ======

    /**
     * 获取 UI Module Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getModuleUIButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_TOAST_TINT, "ToastTint ( 着色美化 Toast )"));
        lists.add(new ButtonValue(BTN_CUSTOM_PROGRESS_BAR, "自定义 ProgressBar 样式 View"));
        lists.add(new ButtonValue(BTN_UI_EFFECT, "常见 UI、GradientDrawable 效果等"));
        lists.add(new ButtonValue(BTN_VIEW_PAGER, "ViewPager 滑动监听、控制滑动"));
        lists.add(new ButtonValue(BTN_STATUS_BAR, "点击 显示/隐藏 ( 状态栏 )"));
        lists.add(new ButtonValue(BTN_TEXT_CALC, "计算字体宽度、高度"));
        lists.add(new ButtonValue(BTN_ADAPTER_EDITS, "Adapter Item EditText 输入监听"));
        lists.add(new ButtonValue(BTN_MULTI_SELECT, "多选辅助类 MultiSelectAssist"));
        lists.add(new ButtonValue(BTN_GPU_ACV, "GPU ACV 文件滤镜效果"));
        lists.add(new ButtonValue(BTN_GPU_FILTER, "GPU 滤镜效果"));
        lists.add(new ButtonValue(BTN_SCAN_VIEW, "自定义扫描 View ( QRCode、AR )"));
        lists.add(new ButtonValue(BTN_QRCODE_CREATE, "创建二维码"));
        lists.add(new ButtonValue(BTN_QRCODE_IMAGE, "二维码图片解析"));
        lists.add(new ButtonValue(BTN_QRCODE_SCAN, "二维码扫描解析"));
        lists.add(new ButtonValue(BTN_WRAP_VIEW, "自动换行 View"));
        lists.add(new ButtonValue(BTN_SIGN_VIEW, "签名 View"));
        lists.add(new ButtonValue(BTN_LINE_VIEW, "换行监听 View"));
        lists.add(new ButtonValue(BTN_CAPTURE_PICTURE, "CapturePictureUtils 截图工具类"));
        return lists;
    }

    /**
     * 获取 Toast Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getToastButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_TOAST_TINT_SUCCESS, "Toast Success"));
        lists.add(new ButtonValue(BTN_TOAST_TINT_ERROR, "Toast Error"));
        lists.add(new ButtonValue(BTN_TOAST_TINT_INFO, "Toast Info"));
        lists.add(new ButtonValue(BTN_TOAST_TINT_NORMAL, "Toast Normal"));
        lists.add(new ButtonValue(BTN_TOAST_TINT_WARNING, "Toast Warning"));
        lists.add(new ButtonValue(BTN_TOAST_TINT_CUSTOM_STYLE, "Toast Custom Style"));
        return lists;
    }

    // ============
    // = 其他功能 =
    // ============

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
        lists.add(new ButtonValue(BTN_TIMER, "TimerManager 定时器工具类"));
        lists.add(new ButtonValue(BTN_CACHE, "DevCache 缓存工具类"));
        lists.add(new ButtonValue(BTN_LOGGER, "DevLogger 日志工具类"));
        lists.add(new ButtonValue(BTN_FILE_RECORD, "日志、异常文件记录保存"));
        lists.add(new ButtonValue(BTN_CRASH, "奔溃日志捕获"));
        lists.add(new ButtonValue(BTN_EXTEND, "通用结果回调类 ( 针对 DevResultCallback 进行扩展 )"));
        lists.add(new ButtonValue(BTN_DEVICE_INFO, "设备信息"));
        lists.add(new ButtonValue(BTN_SCREEN_INFO, "屏幕信息"));
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
        lists.add(new ButtonValue(BTN_FUNCTION_NOTIFICATION_CHECK, "是否存在通知权限"));
        lists.add(new ButtonValue(BTN_FUNCTION_NOTIFICATION_OPEN, "开启通知权限"));
        lists.add(new ButtonValue(BTN_FUNCTION_NOTIFICATION, "通知消息"));
        lists.add(new ButtonValue(BTN_FUNCTION_NOTIFICATION_REMOVE, "移除消息"));
        lists.add(new ButtonValue(BTN_FUNCTION_HOME, "回到桌面"));
        lists.add(new ButtonValue(BTN_FUNCTION_FLASHLIGHT_OPEN, "打开手电筒"));
        lists.add(new ButtonValue(BTN_FUNCTION_FLASHLIGHT_CLOSE, "关闭手电筒"));
        lists.add(new ButtonValue(BTN_FUNCTION_SHORTCUT_CHECK, "是否创建桌面快捷方式"));
        lists.add(new ButtonValue(BTN_FUNCTION_SHORTCUT_CREATE, "创建桌面快捷方式"));
        lists.add(new ButtonValue(BTN_FUNCTION_SHORTCUT_DELETE, "删除桌面快捷方式"));
        lists.add(new ButtonValue(BTN_FUNCTION_MEMORY_PRINT, "打印内存信息"));
        lists.add(new ButtonValue(BTN_FUNCTION_DEVICE_PRINT, "打印设备信息"));
        lists.add(new ButtonValue(BTN_FUNCTION_APP_DETAILS_SETTINGS, "跳转到 APP 设置详情页面"));
        lists.add(new ButtonValue(BTN_FUNCTION_GPS_SETTINGS, "打开 GPS 设置界面"));
        lists.add(new ButtonValue(BTN_FUNCTION_WIRELESS_SETTINGS, "打开网络设置界面"));
        lists.add(new ButtonValue(BTN_FUNCTION_SYS_SETTINGS, "跳转到系统设置页面"));
        return lists;
    }

    /**
     * 获取 Timer Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getTimerButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_TIMER_START, "启动定时器"));
        lists.add(new ButtonValue(BTN_TIMER_STOP, "停止定时器"));
        lists.add(new ButtonValue(BTN_TIMER_RESTART, "重新启动定时器"));
        lists.add(new ButtonValue(BTN_TIMER_CHECK, "定时器是否启动"));
        lists.add(new ButtonValue(BTN_TIMER_GET, "获取定时器"));
        lists.add(new ButtonValue(BTN_TIMER_GET_NUMBER, "获取运行次数"));
        return lists;
    }

    /**
     * 获取 Cache Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getCacheButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_CACHE_STRING, "存储字符串"));
        lists.add(new ButtonValue(BTN_CACHE_STRING_TIME, "存储有效期字符串"));
        lists.add(new ButtonValue(BTN_CACHE_STRING_GET, "获取字符串"));
        lists.add(new ButtonValue(BTN_CACHE_BEAN, "存储实体类"));
        lists.add(new ButtonValue(BTN_CACHE_BEAN_TIME, "存储有效期实体类"));
        lists.add(new ButtonValue(BTN_CACHE_BEAN_GET, "获取实体类"));
        lists.add(new ButtonValue(BTN_CACHE_FILE, "存储到指定位置"));
        lists.add(new ButtonValue(BTN_CACHE_FILE_GET, "获取指定位置缓存数据"));
        lists.add(new ButtonValue(BTN_CACHE_CLEAR, "清除全部数据"));
        return lists;
    }

    /**
     * 获取 Logger Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getLoggerButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_LOGGER_PRINT, "打印日志"));
        lists.add(new ButtonValue(BTN_LOGGER_TIME, "打印日志耗时测试"));
        return lists;
    }

    /**
     * 获取 File Record Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getFileRecordButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_FILE_RECORD_ANALYSIS, "AnalysisRecordUtils 工具类"));
        lists.add(new ButtonValue(BTN_FILE_RECORD_UTILS, "FileRecordUtils 工具类"));
        return lists;
    }

    /**
     * 获取 Crash Record Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getCrashButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_CRASH_CLICK_CATCH, "点击崩溃捕获信息"));
        return lists;
    }

    /**
     * 获取 Extend Record Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getExtendButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_EXTEND_SAVE, "保存文件获取结果"));
        lists.add(new ButtonValue(BTN_EXTEND_TRIGGER, "触发拓展回调"));
        return lists;
    }

    // ========
    // = 常量 =
    // ========

    private static final int BASE = 1001;
    // 架构
    public static final int MODULE_FRAMEWORK = BASE + 10000;
    // Lib
    public static final int MODULE_LIB = BASE + 20000;
    // UI
    public static final int MODULE_UI = BASE + 30000;
    // 其他功能
    public static final int MODULE_OTHER = BASE + 40000;

    // =============
    // = Framework =
    // =============

    // MVP
    public static final int BTN_MVP = MODULE_FRAMEWORK;
    // MVVM
    public static final int BTN_MVVM = BTN_MVP + 100;

    // =======
    // = Lib =
    // =======

    // EventBusUtils
    public static final int BTN_EVENT_BUS = MODULE_LIB;
    // Register
    public static final int BTN_EVENT_REGISTER = BTN_EVENT_BUS + 1;
    // unRegister
    public static final int BTN_EVENT_UNREGISTER = BTN_EVENT_BUS + 2;
    // 清空粘性事件
    public static final int BTN_EVENT_CLEAN_STICKY = BTN_EVENT_BUS + 3;
    // 发送事件
    public static final int BTN_EVENT_SEND = BTN_EVENT_BUS + 4;
    // 发送粘性事件
    public static final int BTN_EVENT_SEND_STICKY = BTN_EVENT_BUS + 5;

    // GlideUtils
    public static final int BTN_GLIDE = MODULE_LIB + 1;

    // ImageLoaderUtils
    public static final int BTN_IMAGE_LOADER = MODULE_LIB + 2;

    // GsonUtils
    public static final int BTN_GSON = MODULE_LIB + 3;

    // FastjsonUtils
    public static final int BTN_FASTJSON = MODULE_LIB + 4;

    // ZXingQRCodeUtils
    public static final int BTN_ZXING = MODULE_LIB + 5;

    // PictureSelectorUtils
    public static final int BTN_PICTURE_SELECTOR = MODULE_LIB + 6;

    // ======
    // = UI =
    // ======

    // ToastTint ( 着色美化 Toast )
    public static final int BTN_TOAST_TINT = MODULE_UI;
    // Toast Success
    public static final int BTN_TOAST_TINT_SUCCESS = BTN_TOAST_TINT + 1;
    // Toast Error
    public static final int BTN_TOAST_TINT_ERROR = BTN_TOAST_TINT + 2;
    // Toast Info
    public static final int BTN_TOAST_TINT_INFO = BTN_TOAST_TINT + 3;
    // Toast Normal
    public static final int BTN_TOAST_TINT_NORMAL = BTN_TOAST_TINT + 4;
    // Toast Warning
    public static final int BTN_TOAST_TINT_WARNING = BTN_TOAST_TINT + 5;
    // Toast Custom Style
    public static final int BTN_TOAST_TINT_CUSTOM_STYLE = BTN_TOAST_TINT + 6;

    // 自定义 ProgressBar 样式 View
    public static final int BTN_CUSTOM_PROGRESS_BAR = MODULE_UI + 100;

    // 常见 UI、GradientDrawable 效果等
    public static final int BTN_UI_EFFECT = MODULE_UI + 200;

    // ViewPager 滑动监听、控制滑动
    public static final int BTN_VIEW_PAGER = MODULE_UI + 300;

    // 点击 显示/隐藏 ( 状态栏 )
    public static final int BTN_STATUS_BAR = MODULE_UI + 400;

    // 计算字体宽度、高度
    public static final int BTN_TEXT_CALC = MODULE_UI + 500;

    // Adapter Item EditText 输入监听
    public static final int BTN_ADAPTER_EDITS = MODULE_UI + 600;

    // 多选辅助类 MultiSelectAssist
    public static final int BTN_MULTI_SELECT = MODULE_UI + 700;

    // GPU ACV 文件滤镜效果
    public static final int BTN_GPU_ACV = MODULE_UI + 800;

    // GPU 滤镜效果
    public static final int BTN_GPU_FILTER = MODULE_UI + 900;

    // 自定义扫描 View ( QRCode、AR )
    public static final int BTN_SCAN_VIEW = MODULE_UI + 1000;

    // 创建二维码
    public static final int BTN_QRCODE_CREATE = MODULE_UI + 1100;

    // 二维码图片解析
    public static final int BTN_QRCODE_IMAGE = MODULE_UI + 1200;

    // 二维码扫描解析
    public static final int BTN_QRCODE_SCAN = MODULE_UI + 1300;

    // 自动换行 View
    public static final int BTN_WRAP_VIEW = MODULE_UI + 1400;

    // 签名 View
    public static final int BTN_SIGN_VIEW = MODULE_UI + 1500;

    // 换行监听 View
    public static final int BTN_LINE_VIEW = MODULE_UI + 1600;

    // CapturePictureUtils 截图工具类
    public static final int BTN_CAPTURE_PICTURE = MODULE_UI + 1700;

    // ============
    // = 其他功能 =
    // ============

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
    // 是否存在通知权限
    public static final int BTN_FUNCTION_NOTIFICATION_CHECK = BTN_FUNCTION + 3;
    // 开启通知权限
    public static final int BTN_FUNCTION_NOTIFICATION_OPEN = BTN_FUNCTION + 4;
    // 通知消息
    public static final int BTN_FUNCTION_NOTIFICATION = BTN_FUNCTION + 5;
    // 移除消息
    public static final int BTN_FUNCTION_NOTIFICATION_REMOVE = BTN_FUNCTION + 6;
    // 回到桌面
    public static final int BTN_FUNCTION_HOME = BTN_FUNCTION + 7;
    // 打开手电筒
    public static final int BTN_FUNCTION_FLASHLIGHT_OPEN = BTN_FUNCTION + 8;
    // 关闭手电筒
    public static final int BTN_FUNCTION_FLASHLIGHT_CLOSE = BTN_FUNCTION + 9;
    // 是否创建桌面快捷方式
    public static final int BTN_FUNCTION_SHORTCUT_CHECK = BTN_FUNCTION + 10;
    // 创建桌面快捷方式
    public static final int BTN_FUNCTION_SHORTCUT_CREATE = BTN_FUNCTION + 11;
    // 删除桌面快捷方式
    public static final int BTN_FUNCTION_SHORTCUT_DELETE = BTN_FUNCTION + 12;
    // 打印内存信息
    public static final int BTN_FUNCTION_MEMORY_PRINT = BTN_FUNCTION + 13;
    // 打印设备信息
    public static final int BTN_FUNCTION_DEVICE_PRINT = BTN_FUNCTION + 14;
    // 跳转到 APP 设置详情页面
    public static final int BTN_FUNCTION_APP_DETAILS_SETTINGS = BTN_FUNCTION + 15;
    // 打开 GPS 设置界面
    public static final int BTN_FUNCTION_GPS_SETTINGS = BTN_FUNCTION + 16;
    // 打开网络设置界面
    public static final int BTN_FUNCTION_WIRELESS_SETTINGS = BTN_FUNCTION + 17;
    // 跳转到系统设置页面
    public static final int BTN_FUNCTION_SYS_SETTINGS = BTN_FUNCTION + 18;

    // TimerManager 定时器工具类
    public static final int BTN_TIMER = MODULE_OTHER + 500;
    // 启动定时器
    public static final int BTN_TIMER_START = BTN_TIMER + 1;
    // 停止定时器
    public static final int BTN_TIMER_STOP = BTN_TIMER + 2;
    // 重新启动定时器
    public static final int BTN_TIMER_RESTART = BTN_TIMER + 3;
    // 定时器是否启动
    public static final int BTN_TIMER_CHECK = BTN_TIMER + 4;
    // 获取定时器
    public static final int BTN_TIMER_GET = BTN_TIMER + 5;
    // 获取运行次数
    public static final int BTN_TIMER_GET_NUMBER = BTN_TIMER + 6;

    // DevCache 缓存工具类
    public static final int BTN_CACHE = MODULE_OTHER + 600;
    // 存储字符串
    public static final int BTN_CACHE_STRING = BTN_CACHE + 1;
    // 存储有效期字符串
    public static final int BTN_CACHE_STRING_TIME = BTN_CACHE + 2;
    // 获取字符串
    public static final int BTN_CACHE_STRING_GET = BTN_CACHE + 3;
    // 存储实体类
    public static final int BTN_CACHE_BEAN = BTN_CACHE + 4;
    // 存储有效期实体类
    public static final int BTN_CACHE_BEAN_TIME = BTN_CACHE + 5;
    // 获取实体类
    public static final int BTN_CACHE_BEAN_GET = BTN_CACHE + 6;
    // 存储到指定位置
    public static final int BTN_CACHE_FILE = BTN_CACHE + 7;
    // 获取指定位置缓存数据
    public static final int BTN_CACHE_FILE_GET = BTN_CACHE + 8;
    // 清除全部数据
    public static final int BTN_CACHE_CLEAR = BTN_CACHE + 9;

    // DevLogger 日志工具类
    public static final int BTN_LOGGER = MODULE_OTHER + 700;
    // 打印日志
    public static final int BTN_LOGGER_PRINT = BTN_LOGGER + 1;
    // 打印日志耗时测试
    public static final int BTN_LOGGER_TIME = BTN_LOGGER + 2;

    // 日志、异常文件记录保存
    public static final int BTN_FILE_RECORD = MODULE_OTHER + 800;
    // AnalysisRecordUtils 工具类
    public static final int BTN_FILE_RECORD_ANALYSIS = BTN_FILE_RECORD + 1;
    // FileRecordUtils 工具类
    public static final int BTN_FILE_RECORD_UTILS = BTN_FILE_RECORD + 2;

    // 奔溃日志捕获
    public static final int BTN_CRASH = MODULE_OTHER + 900;
    // 点击崩溃捕获信息
    public static final int BTN_CRASH_CLICK_CATCH = BTN_CRASH + 1;

    // 通用结果回调类 ( 针对 DevResultCallback 进行扩展 )
    public static final int BTN_EXTEND = MODULE_OTHER + 1000;
    // 保存文件获取结果
    public static final int BTN_EXTEND_SAVE = BTN_EXTEND + 1;
    // 触发拓展回调
    public static final int BTN_EXTEND_TRIGGER = BTN_EXTEND + 2;

    // 设备信息
    public static final int BTN_DEVICE_INFO = MODULE_OTHER + 1100;

    // 屏幕信息
    public static final int BTN_SCREEN_INFO = MODULE_OTHER + 1200;
}
