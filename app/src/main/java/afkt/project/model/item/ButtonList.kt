package afkt.project.model.item

import afkt.project.base.config.RouterPath
import java.util.*

/**
 * detail: Button List
 * @author Ttt
 */
object ButtonList {

    // ==========
    // = 获取集合 =
    // ==========

    /**
     * 获取 Main Button Value 集合
     * @return [List]
     */
    val mainButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.MODULE_FRAMEWORK, "Framework 架构",
                    RouterPath.ModuleActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.MODULE_LIB, "Lib 框架",
                    RouterPath.ModuleActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.MODULE_UI, "UI 效果",
                    RouterPath.ModuleActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.MODULE_OTHER, "其他功能",
                    RouterPath.ModuleActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.MODULE_DEV_WIDGET, "DevWidget UI 库",
                    RouterPath.ModuleActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.MODULE_DEV_ENVIRONMENT, "DevEnvironment 环境配置切换库",
                    RouterPath.DevEnvironmentLibActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.MODULE_DEV_ASSIST_ENGINE, "DevAssist Engine 实现",
                    RouterPath.DevAssistEngineActivity_PATH
                )
            )
            return lists
        }

    /**
     * 获取 Module 功能 Button Value 集合
     * @param type Module Type
     * @return [List]
     */
    fun getModuleButtonValues(type: Int): List<ButtonValue> {
        when (type) {
            ButtonValue.MODULE_FRAMEWORK -> return moduleFrameworkButtonValues
            ButtonValue.MODULE_LIB -> return moduleLibButtonValues
            ButtonValue.MODULE_UI -> return moduleUIButtonValues
            ButtonValue.MODULE_OTHER -> return moduleOtherButtonValues
            ButtonValue.MODULE_DEV_WIDGET -> return moduleDevWidgetButtonValues
        }
        return emptyList()
    }

    /**
     * 获取 Button Item 集合
     * @param type button Type
     * @return [List]
     */
    fun getButtonValues(type: Int): List<ButtonValue> {
        when (type) {
            ButtonValue.BTN_VIEW_ASSIST -> return viewAssistButtonValues
        }
        return emptyList()
    }

    // =============
    // = Framework =
    // =============

    /**
     * 获取 Framework Module Button Value 集合
     * @return [List]
     */
    val moduleFrameworkButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_MVP, "MVP",
                    RouterPath.ArticleMVPActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_MVVM, "MVVM",
                    RouterPath.ArticleMVVMActivity_PATH
                )
            )
            return lists
        }

    // =======
    // = Lib =
    // =======

    /**
     * 获取 Lib Module Button Value 集合
     * @return [List]
     */
    private val moduleLibButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_GREEN_DAO, "GreenDAO",
                    RouterPath.GreenDaoActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ROOM, "Room",
                    RouterPath.RoomActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_EVENT_BUS, "EventBusUtils",
                    RouterPath.EventBusActivity_PATH
                )
            )
            lists.add(ButtonValue(ButtonValue.BTN_GLIDE, "GlideUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_IMAGE_LOADER, "ImageLoaderUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_GSON, "GsonUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FASTJSON, "FastjsonUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ZXING, "ZXingQRCodeUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_PICTURE_SELECTOR, "PictureSelectorUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_OKGO, "OkGoUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_LUBAN, "LubanUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_MMKV, "MMKVUtils", ""))
            lists.add(ButtonValue(ButtonValue.BTN_DATA_STORE, "DataStore", ""))
            lists.add(ButtonValue(ButtonValue.BTN_WORK_MANAGER, "WorkManagerUtils", ""))
            return lists
        }

    /**
     * 获取 Event Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val eventButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_EVENT_REGISTER, "Register", ""))
            lists.add(ButtonValue(ButtonValue.BTN_EVENT_UNREGISTER, "unRegister", ""))
            lists.add(ButtonValue(ButtonValue.BTN_EVENT_CLEAN_STICKY, "清空粘性事件", ""))
            lists.add(ButtonValue(ButtonValue.BTN_EVENT_SEND, "发送事件", ""))
            lists.add(ButtonValue(ButtonValue.BTN_EVENT_SEND_STICKY, "发送粘性事件", ""))
            return lists
        }

    // ======
    // = UI =
    // ======

    /**
     * 获取 UI Module Button Value 集合
     * @return [List]
     */
    private val moduleUIButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_TOAST_TINT, "ToastTint ( 着色美化 Toast )",
                    RouterPath.ToastTintActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_UI_EFFECT, "常见 UI、GradientDrawable 效果等",
                    RouterPath.UIEffectActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_STATUS_BAR, "点击 显示/隐藏 ( 状态栏 )",
                    RouterPath.StatusBarActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_TEXT_CALC, "计算字体宽度、高度",
                    RouterPath.TextCalcActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ADAPTER_EDITS, "Adapter Item EditText 输入监听",
                    RouterPath.AdapterEditsActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_MULTI_SELECT, "多选辅助类 MultiSelectAssist",
                    RouterPath.MultiSelectActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_GPU_ACV, "GPU ACV 文件滤镜效果",
                    RouterPath.GPUFilterACVActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_GPU_FILTER, "GPU 滤镜效果",
                    RouterPath.GPUFilterActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_QRCODE_CREATE, "创建二维码",
                    RouterPath.QRCodeCreateActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_QRCODE_IMAGE, "二维码图片解析",
                    RouterPath.QRCodeImageActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_QRCODE_SCAN, "二维码扫描解析",
                    RouterPath.QRCodeScanActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_CAPTURE_PICTURE, "CapturePictureUtils 截图工具类",
                    RouterPath.CapturePictureActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_TEXTVIEW, "两个 TextView 显示效果",
                    RouterPath.TextViewActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ITEM_STICKY, "RecyclerView 吸附效果",
                    RouterPath.ItemStickyActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_RECY_ITEM_SLIDE, "RecyclerView 滑动删除、上下滑动",
                    RouterPath.RecyItemSlideActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_RECY_LINEAR_SNAP, "LinearSnapHelper - RecyclerView",
                    RouterPath.LinearSnapActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_RECY_LINEAR_SNAP_MAX, "LinearSnapHelper - 无限滑动",
                    RouterPath.LinearSnapMAXActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_RECY_PAGER_SNAP, "PagerSnapHelper - RecyclerView",
                    RouterPath.PagerSnapActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_RECY_PAGER_SNAP_MAX, "PagerSnapHelper - 无限滑动",
                    RouterPath.PagerSnapMAXActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_SHAPEABLE_IMAGE_VIEW, "Material ShapeableImageView",
                    RouterPath.ShapeableImageViewActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_BOTTOM_SHEET, "Material BottomSheet",
                    RouterPath.BottomSheetActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_BOTTOM_SHEET_DIALOG, "Material BottomSheetDialog",
                    RouterPath.BottomSheetDialogActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_PALETTE, "Palette 调色板",
                    RouterPath.PaletteActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_FLEXBOX_LAYOUTMANAGER, "Flexbox LayoutManager",
                    RouterPath.FlexboxLayoutManagerActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_CHIP, "Material Chip、ChipGroups、ChipDrawable",
                    RouterPath.ChipActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_VIEWPAGER2, "ViewPager2",
                    RouterPath.ViewPager2Activity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_RECYCLERVIEW_CONCATADAPTER, "RecyclerView - ConcatAdapter",
                    RouterPath.RecyConcatAdapterActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_RECYCLERVIEW_MULTITYPE_ADAPTER,
                    "RecyclerView MultiType Adapter",
                    RouterPath.RecyMultiTypeAdapterActivity_PATH
                )
            )
            return lists
        }

    /**
     * 获取 Toast Button Value 集合
     * @return [List]
     */
    val toastButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_TOAST_TINT_SUCCESS, "Toast Success", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TOAST_TINT_ERROR, "Toast Error", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TOAST_TINT_INFO, "Toast Info", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TOAST_TINT_NORMAL, "Toast Normal", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TOAST_TINT_WARNING, "Toast Warning", ""))
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_TOAST_TINT_CUSTOM_STYLE, "Toast Custom Style", ""
                )
            )
            return lists
        }

    // ==========
    // = 其他功能 =
    // ==========

    /**
     * 获取 Other Module Button Value 集合
     * @return [List]
     */
    private val moduleOtherButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_LISTENER, "事件 / 广播监听 ( 网络状态、屏幕旋转等 )",
                    RouterPath.ListenerActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_NOTIFICATION_SERVICE, "通知栏监听服务 ( NotificationService )",
                    RouterPath.NotificationServiceActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ACCESSIBILITY_SERVICE,
                    "无障碍监听服务 ( AccessibilityListenerService )",
                    RouterPath.AccessibilityListenerServiceActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_WIFI, "Wifi 相关 ( 热点 )",
                    RouterPath.WifiActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_FUNCTION, "铃声、震动、通知栏等功能",
                    RouterPath.FunctionActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_TIMER, "TimerManager 定时器工具类",
                    RouterPath.TimerActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_CACHE, "DevCache 缓存工具类",
                    RouterPath.CacheActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_LOGGER, "DevLogger 日志工具类",
                    RouterPath.LoggerActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_FILE_RECORD, "日志、异常文件记录保存",
                    RouterPath.FileRecordActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_CRASH, "奔溃日志捕获",
                    RouterPath.CrashCatchActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_PATH, "路径信息",
                    RouterPath.PathActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_WEBVIEW, "WebView 辅助类",
                    RouterPath.WebViewActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ACTIVITY_RESULT_CALLBACK,
                    "startActivityForResult Callback",
                    RouterPath.ActivityResultCallbackActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ADD_CONTACT, "添加联系人",
                    RouterPath.AddContactActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_WALLPAPER, "手机壁纸",
                    RouterPath.WallpaperActivity_PATH
                )
            )
            return lists
        }

    /**
     * 获取 Listener Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val listenerButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_WIFI_LISTENER, "Wifi 监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_NETWORK_LISTENER, "网络监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_PHONE_LISTENER, "电话监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_SMS_LISTENER, "短信监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TIME_LISTENER, "时区、时间监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_SCREEN_LISTENER, "屏幕监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ROTA_LISTENER, "屏幕旋转监听 ( 重力传感器 )", ""))
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ROTA2_LISTENER,
                    "屏幕旋转监听 ( OrientationEventListener )", ""
                )
            )
            lists.add(ButtonValue(ButtonValue.BTN_BATTERY_LISTENER, "电量监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_APP_STATE_LISTENER, "应用状态监听", ""))
            return lists
        }

    /**
     * 获取 Notification Service Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val notificationServiceButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_NOTIFICATION_SERVICE_CHECK, "检查是否开启", ""))
            lists.add(ButtonValue(ButtonValue.BTN_NOTIFICATION_SERVICE_REGISTER, "开始监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_NOTIFICATION_SERVICE_UNREGISTER, "注销监听", ""))
            return lists
        }

    /**
     * 获取 Accessibility Listener Service Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val accessibilityListenerServiceButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_ACCESSIBILITY_SERVICE_CHECK, "检查是否开启", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ACCESSIBILITY_SERVICE_REGISTER, "开始监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ACCESSIBILITY_SERVICE_UNREGISTER, "注销监听", ""))
            return lists
        }

    /**
     * 获取 Wifi Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val wifiButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_WIFI_OPEN, "打开 Wifi", ""))
            lists.add(ButtonValue(ButtonValue.BTN_WIFI_CLOSE, "关闭 Wifi", ""))
            lists.add(ButtonValue(ButtonValue.BTN_WIFI_HOT_OPEN, "打开 Wifi 热点", ""))
            lists.add(ButtonValue(ButtonValue.BTN_WIFI_HOT_CLOSE, "关闭 Wifi 热点", ""))
            lists.add(ButtonValue(ButtonValue.BTN_WIFI_LISTENER_REGISTER, "注册 Wifi 监听", ""))
            lists.add(ButtonValue(ButtonValue.BTN_WIFI_LISTENER_UNREGISTER, "注销 Wifi 监听", ""))
            return lists
        }

    /**
     * 获取 Function Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val functionButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_VIBRATE, "震动", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_BEEP, "铃声 - 播放一小段音频", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_NOTIFICATION_CHECK, "是否存在通知权限", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_NOTIFICATION_OPEN, "开启通知权限", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_NOTIFICATION, "通知消息", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_NOTIFICATION_REMOVE, "移除消息", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_HOME, "回到桌面", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_FLASHLIGHT_OPEN, "打开手电筒", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_FLASHLIGHT_CLOSE, "关闭手电筒", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_SHORTCUT_CHECK, "是否创建桌面快捷方式", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_SHORTCUT_CREATE, "创建桌面快捷方式", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_SHORTCUT_DELETE, "删除桌面快捷方式", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_MEMORY_PRINT, "打印内存信息", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_DEVICE_PRINT, "打印设备信息", ""))
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_FUNCTION_APP_DETAILS_SETTINGS,
                    "跳转到 APP 设置详情页面",
                    ""
                )
            )
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_GPS_SETTINGS, "打开 GPS 设置界面", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_WIRELESS_SETTINGS, "打开网络设置界面", ""))
            lists.add(ButtonValue(ButtonValue.BTN_FUNCTION_SYS_SETTINGS, "跳转到系统设置页面", ""))
            return lists
        }

    /**
     * 获取 Timer Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val timerButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_TIMER_START, "启动定时器", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TIMER_STOP, "停止定时器", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TIMER_RESTART, "重新启动定时器", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TIMER_CHECK, "定时器是否启动", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TIMER_GET, "获取定时器", ""))
            lists.add(ButtonValue(ButtonValue.BTN_TIMER_GET_NUMBER, "获取运行次数", ""))
            return lists
        }

    /**
     * 获取 Cache Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val cacheButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_STRING, "存储字符串", ""))
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_STRING_TIME, "存储有效期字符串", ""))
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_STRING_GET, "获取字符串", ""))
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_BEAN, "存储实体类", ""))
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_BEAN_TIME, "存储有效期实体类", ""))
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_BEAN_GET, "获取实体类", ""))
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_FILE, "存储到指定位置", ""))
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_FILE_GET, "获取指定位置缓存数据", ""))
            lists.add(ButtonValue(ButtonValue.BTN_CACHE_CLEAR, "清除全部数据", ""))
            return lists
        }

    /**
     * 获取 Logger Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val loggerButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_LOGGER_PRINT, "打印日志", ""))
            lists.add(ButtonValue(ButtonValue.BTN_LOGGER_TIME, "打印日志耗时测试", ""))
            return lists
        }

    /**
     * 获取 File Record Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val fileRecordButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_FILE_RECORD_ANALYSIS, "AnalysisRecordUtils 工具类",
                    ""
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_FILE_RECORD_UTILS, "FileRecordUtils 工具类",
                    ""
                )
            )
            return lists
        }

    /**
     * 获取 Crash Record Button Value 集合
     * @return [List]
     */
    val crashButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_CRASH_CLICK_CATCH, "点击崩溃捕获信息", ""))
            return lists
        }

    /**
     * 获取 Path Button Value 集合
     * @return [List]
     */
    val pathButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_PATH_INTERNAL, "内部存储路径", ""))
            lists.add(ButtonValue(ButtonValue.BTN_PATH_APP_EXTERNAL, "应用外部存储路径", ""))
            lists.add(ButtonValue(ButtonValue.BTN_PATH_SDCARD, "外部存储路径 ( SDCard )", ""))
            return lists
        }

    // ==================
    // = DevWidget UI 库 =
    // ==================

    /**
     * 获取 DevWidget Module Button Value 集合
     * @return [List]
     */
    val moduleDevWidgetButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_VIEW_PAGER, "ViewPager 滑动监听、控制滑动",
                    RouterPath.ViewPagerActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_CUSTOM_PROGRESS_BAR, "自定义 ProgressBar 样式 View",
                    RouterPath.ProgressBarActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_SCAN_VIEW, "自定义扫描 View ( QRCode、AR )",
                    RouterPath.ScanShapeActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_WRAP_VIEW, "自动换行 View",
                    RouterPath.WrapActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_SIGN_VIEW, "签名 View",
                    RouterPath.SignActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_LINE_VIEW, "换行监听 View",
                    RouterPath.LineActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_LIKE_VIEW, "自定义点赞效果 View",
                    RouterPath.FlowLikeActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_CORNER_LABEL_VIEW, "自定义角标 View",
                    RouterPath.CornerLabelActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_VIEW_ASSIST, "View 填充辅助类",
                    RouterPath.ButtonItemActivity_PATH
                )
            )
            return lists
        }

    /**
     * 获取 ViewAssist Button Value 集合
     * @return [List]
     */
    val viewAssistButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_VIEW_ASSIST_RECYCLER, "RecyclerView ( loading )",
                    RouterPath.ViewAssistRecyclerViewLoadActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_VIEW_ASSIST_ERROR, "Error ( failed )",
                    RouterPath.ViewAssistActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_VIEW_ASSIST_EMPTY, "Empty ( data )",
                    RouterPath.ViewAssistActivity_PATH
                )
            )
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_VIEW_ASSIST_CUSTOM, "Custom Type",
                    RouterPath.ViewAssistActivity_PATH
                )
            )
            return lists
        }

    // ==============================
    // = DevEnvironment 环境配置切换库 =
    // ==============================

    /**
     * 获取 DevEnvironment Module Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val moduleDevEnvironmentButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(ButtonValue(ButtonValue.BTN_DEV_ENVIRONMENT, "环境配置切换", ""))
            lists.add(ButtonValue(ButtonValue.BTN_USE_CUSTOM, "使用自定义配置", ""))
            return lists
        }

    // ========================
    // = DevAssist Engine 实现 =
    // ========================

    /**
     * 获取 DevAssist Engine Module Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val moduleDevAssistEngineButtonValues: List<ButtonValue>
        get() {
            val lists: MutableList<ButtonValue> = ArrayList()
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ENGINE_ANALYTICS,
                    "Analytics Engine 数据统计 ( 埋点 )",
                    ""
                )
            )
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_CACHE, "Cache Engine 有效期键值对缓存", ""))
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ENGINE_IMAGE_COMPRESS,
                    "Image Compress Engine 图片压缩",
                    ""
                )
            )
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_HTTP, "Http Engine 网络请求", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_IMAGE, "Image Engine 图片加载、下载、转格式等", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_JSON, "JSON Engine", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_KEYVALUE, "KeyValue Engine 键值对存储", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_LOG, "Log Engine 日志打印", ""))
            lists.add(
                ButtonValue(
                    ButtonValue.BTN_ENGINE_MEDIA_SELECTOR,
                    "Media Selector Engine 多媒体资源选择",
                    ""
                )
            )
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_PERMISSION, "Permission Engine 权限申请", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_PUSH, "Push Engine 推送平台处理", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_SHARE, "Share Engine 分享平台处理", ""))
            lists.add(ButtonValue(ButtonValue.BTN_ENGINE_STORAGE, "Storage Engine 外部、内部文件存储", ""))
            return lists
        }
}