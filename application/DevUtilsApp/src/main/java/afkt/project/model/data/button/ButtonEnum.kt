package afkt.project.model.data.button

/**
 * detail: Button 枚举类型
 * @author Ttt
 */
enum class ButtonEnum(
    val text: String
) {
    MODULE_LIB("Lib Utils"),

    MODULE_UI("UI 效果"),

    MODULE_OTHER("其他功能"),

    MODULE_DEV_WIDGET("DevWidget UI 库"),

    MODULE_DEV_ASSIST_ENGINE("DevAssist Engine 实现"),

    MODULE_DEV_SKU("DevSKU 商品 SKU 组合封装实现"),

    // =============
    // = Lib Utils =
    // =============

    BTN_LIB_GREEN_DAO("GreenDAO"),

    BTN_LIB_ROOM("Room"),

    BTN_LIB_DATA_STORE("DataStoreUtils"),

    BTN_LIB_GLIDE("GlideUtils"),

    BTN_LIB_GSON("GsonUtils"),

    BTN_LIB_MMKV("MMKVUtils"),

    BTN_LIB_WORK_MANAGER("WorkManagerUtils"),

    BTN_LIB_FASTJSON("FastjsonUtils"),

    BTN_LIB_ZXING("ZXingUtils"),

    BTN_LIB_PICTURE_SELECTOR("PictureSelectorUtils"),

    BTN_LIB_LUBAN("LubanUtils"),

    BTN_LIB_EVENT_BUS("EventBusUtils"),

    // ======
    // = UI =
    // ======

    BTN_TOAST_TINT("ToastTint ( 着色美化 Toast )"),

    BTN_UI_EFFECT("常见 UI、GradientDrawable 效果等"),

    BTN_TEXT_CALC("计算字体宽度、高度"),

    BTN_ADAPTER_EDITS("Adapter Item EditText 输入监听"),

    BTN_MULTI_SELECT("多选辅助类 MultiSelectAssist"),

    BTN_GPU_ACV("GPU ACV 文件滤镜效果"),

    BTN_GPU_FILTER("GPU 滤镜效果"),

    BTN_QRCODE_CREATE("创建二维码"),

    BTN_QRCODE_IMAGE("二维码图片解析"),

    BTN_QRCODE_SCAN("二维码扫描解析"),

    BTN_CAPTURE_PICTURE("CapturePictureUtils 截图工具类"),

    BTN_TEXTVIEW("两个 TextView 显示效果"),

    BTN_ITEM_STICKY("RecyclerView 吸附效果"),

    BTN_RECY_ITEM_SLIDE("RecyclerView 滑动删除、上下滑动"),

    BTN_RECY_LINEAR_SNAP("LinearSnapHelper - RecyclerView"),

    BTN_RECY_LINEAR_SNAP_MAX("LinearSnapHelper - 无限滑动"),

    BTN_RECY_PAGER_SNAP("PagerSnapHelper - RecyclerView"),

    BTN_RECY_PAGER_SNAP_MAX("PagerSnapHelper - 无限滑动"),

    BTN_SHAPEABLE_IMAGE_VIEW("Material ShapeableImageView"),

    BTN_BOTTOM_SHEET("Material BottomSheet"),

    BTN_BOTTOM_SHEET_DIALOG("Material BottomSheetDialog"),

    BTN_PALETTE("Palette 调色板"),

    BTN_FLEXBOX_LAYOUTMANAGER("Flexbox LayoutManager"),

    BTN_CHIP("Material Chip、ChipGroups、ChipDrawable"),

    BTN_VIEWPAGER2("ViewPager2"),

    BTN_RECYCLERVIEW_CONCATADAPTER("RecyclerView - ConcatAdapter"),

    BTN_RECYCLERVIEW_MULTITYPE_ADAPTER("RecyclerView MultiType Adapter"),

    BTN_SHOP_CARD_ADD_ANIM("购物车加入动画"),

    BTN_TOAST_TINT_SUCCESS("Toast Success"),

    BTN_TOAST_TINT_ERROR("Toast Error"),

    BTN_TOAST_TINT_INFO("Toast Info"),

    BTN_TOAST_TINT_NORMAL("Toast Normal"),

    BTN_TOAST_TINT_WARNING("Toast Warning"),

    BTN_TOAST_TINT_CUSTOM_STYLE("Toast Custom Style"),

    BTN_NOTIFICATION_SERVICE("通知栏监听服务 ( NotificationService )"),

    BTN_ACCESSIBILITY_SERVICE("无障碍监听服务 ( AccessibilityListenerService )"),

    BTN_FUNCTION("铃声、震动、通知栏等功能"),

    BTN_TIMER("TimerManager 定时器工具类"),

    BTN_CACHE("DevCache 缓存工具类"),

    BTN_LOGGER("DevLogger 日志工具类"),

    BTN_FILE_RECORD("日志、异常文件记录保存"),

    BTN_CRASH("奔溃日志捕获"),

    BTN_PATH("路径信息"),

    BTN_WEBVIEW("WebView 辅助类"),

    BTN_ACTIVITY_RESULT_API("Activity Result API"),

    BTN_ACTIVITY_RESULT_CALLBACK("startActivityForResult Callback"),

    BTN_ADD_CONTACT("添加联系人"),

    BTN_WALLPAPER("手机壁纸"),

    BTN_FLOATING_WINDOW_MANAGER("悬浮窗管理辅助类 ( 需权限 )"),

    BTN_FLOATING_WINDOW_MANAGER2("悬浮窗管理辅助类 ( 无需权限依赖 Activity )"),

    BTN_WIFI_LISTENER("Wifi 监听"),

    BTN_NETWORK_LISTENER("网络监听"),

    BTN_PHONE_LISTENER("电话监听"),

    BTN_SMS_LISTENER("短信监听"),

    BTN_TIME_LISTENER("时区、时间监听"),

    BTN_SCREEN_LISTENER("屏幕监听"),

    BTN_ROTA_LISTENER("屏幕旋转监听 ( 重力传感器 )"),

    BTN_ROTA2_LISTENER("屏幕旋转监听 ( OrientationEventListener )"),

    BTN_BATTERY_LISTENER("电量监听"),

    BTN_APP_STATE_LISTENER("应用状态监听"),

    BTN_NOTIFICATION_SERVICE_CHECK("检查是否开启"),

    BTN_NOTIFICATION_SERVICE_REGISTER("开始监听"),

    BTN_NOTIFICATION_SERVICE_UNREGISTER("注销监听"),

    BTN_ACCESSIBILITY_SERVICE_CHECK("检查是否开启"),

    BTN_ACCESSIBILITY_SERVICE_REGISTER("开始监听"),

    BTN_ACCESSIBILITY_SERVICE_UNREGISTER("注销监听"),

    BTN_FUNCTION_VIBRATE("震动"),

    BTN_FUNCTION_BEEP("铃声 - 播放一小段音频"),

    BTN_FUNCTION_NOTIFICATION_CHECK("是否存在通知权限"),

    BTN_FUNCTION_NOTIFICATION_OPEN("开启通知权限"),

    BTN_FUNCTION_NOTIFICATION("通知消息"),

    BTN_FUNCTION_NOTIFICATION_REMOVE("移除消息"),

    BTN_FUNCTION_HOME("回到桌面"),

    BTN_FUNCTION_FLASHLIGHT_OPEN("打开手电筒"),

    BTN_FUNCTION_FLASHLIGHT_CLOSE("关闭手电筒"),

    BTN_FUNCTION_SHORTCUT_CHECK("是否创建桌面快捷方式"),

    BTN_FUNCTION_SHORTCUT_CREATE("创建桌面快捷方式"),

    BTN_FUNCTION_SHORTCUT_DELETE("删除桌面快捷方式"),

    BTN_FUNCTION_MEMORY_PRINT("打印内存信息"),

    BTN_FUNCTION_DEVICE_PRINT("打印设备信息"),

    BTN_FUNCTION_APP_DETAILS_SETTINGS("跳转到 APP 设置详情页面"),

    BTN_FUNCTION_GPS_SETTINGS("打开 GPS 设置界面"),

    BTN_FUNCTION_WIRELESS_SETTINGS("打开网络设置界面"),

    BTN_FUNCTION_SYS_SETTINGS("跳转到系统设置页面"),

    BTN_TIMER_START("启动定时器"),

    BTN_TIMER_STOP("停止定时器"),

    BTN_TIMER_RESTART("重新启动定时器"),

    BTN_TIMER_CHECK("定时器是否启动"),

    BTN_TIMER_GET("获取定时器"),

    BTN_TIMER_GET_NUMBER("获取运行次数"),

    BTN_CACHE_STRING("存储字符串"),

    BTN_CACHE_STRING_TIME("存储有效期字符串"),

    BTN_CACHE_STRING_GET("获取字符串"),

    BTN_CACHE_BEAN("存储实体类"),

    BTN_CACHE_BEAN_TIME("存储有效期实体类"),

    BTN_CACHE_BEAN_GET("获取实体类"),

    BTN_CACHE_FILE("存储到指定位置"),

    BTN_CACHE_FILE_GET("获取指定位置缓存数据"),

    BTN_CACHE_CLEAR("清除全部数据"),

    BTN_LOGGER_PRINT("打印日志"),

    BTN_LOGGER_TIME("打印日志耗时测试"),

    BTN_FILE_RECORD_UTILS("FileRecordUtils 工具类"),

    BTN_CRASH_CLICK_CATCH("点击崩溃捕获信息"),

    BTN_PATH_INTERNAL("内部存储路径"),

    BTN_PATH_APP_EXTERNAL("应用外部存储路径"),

    BTN_PATH_SDCARD("外部存储路径 ( SDCard )"),

    BTN_OPEN_FLOATING_WINDOW("打开悬浮窗"),

    BTN_CLOSE_FLOATING_WINDOW("关闭悬浮窗"),

    BTN_VIEW_PAGER("ViewPager 滑动监听、控制滑动"),

    BTN_CUSTOM_PROGRESS_BAR("自定义 ProgressBar 样式 View"),

    BTN_SCAN_VIEW("自定义扫描 View ( QRCode、AR )"),

    BTN_WRAP_VIEW("自动换行 View"),

    BTN_SIGN_VIEW("签名 View"),

    BTN_LINE_VIEW("换行监听 View"),

    BTN_LIKE_VIEW("自定义点赞效果 View"),

    BTN_CORNER_LABEL_VIEW("自定义角标 View"),

    BTN_VIEW_ASSIST("View 填充辅助类"),

    BTN_FLIP_CARD_VIEW("翻转卡片 View"),

    BTN_WAVE_VIEW("波浪 View"),

    BTN_LINEAR_ITEM_DECORATION("Linear Color ItemDecoration"),

    BTN_GRID_ITEM_DECORATION("Grid Color ItemDecoration"),

    BTN_VIEW_ASSIST_RECYCLER("RecyclerView ( loading )"),

    BTN_VIEW_ASSIST_ERROR("Error ( failed )"),

    BTN_VIEW_ASSIST_EMPTY("Empty ( data )"),

    BTN_VIEW_ASSIST_CUSTOM("Custom Type"),

    BTN_LINEAR_ITEM_VERTICAL("Linear Vertical ItemDecoration"),

    BTN_LINEAR_ITEM_HORIZONTAL("Linear Horizontal ItemDecoration"),

    BTN_GRID_ITEM_VERTICAL("Grid Vertical ItemDecoration"),

    BTN_GRID_ITEM_HORIZONTAL("Grid Horizontal ItemDecoration"),

    BTN_SKU_DIALOG("显示商品 SKU Dialog"),

    // =========
    // = Engine =
    // =========

    BTN_ENGINE_ANALYTICS("Analytics Engine 数据统计 ( 埋点 )"),

    BTN_ENGINE_BAR_CODE("BarCode Engine 条形码、二维码处理"),

    BTN_ENGINE_CACHE("Cache Engine 有效期键值对缓存"),

    BTN_ENGINE_IMAGE_COMPRESS("Image Compress Engine 图片压缩"),

    BTN_ENGINE_IMAGE("Image Engine 图片加载、下载、转格式等"),

    BTN_ENGINE_JSON("JSON Engine 映射"),

    BTN_ENGINE_KEYVALUE("KeyValue Engine 键值对存储"),

    BTN_ENGINE_LOG("Log Engine 日志打印"),

    BTN_ENGINE_MEDIA_SELECTOR("Media Selector Engine 多媒体资源选择"),

    BTN_ENGINE_PERMISSION("Permission Engine 权限申请"),

    BTN_ENGINE_PUSH("Push Engine 推送平台处理"),

    BTN_ENGINE_SHARE("Share Engine 分享平台处理"),

    BTN_ENGINE_STORAGE("Storage Engine 外部、内部文件存储"),

    BTN_ENGINE_TOAST("Toast Engine 吐司提示"),
}