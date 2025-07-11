package afkt.project.model.data.button

/**
 * detail: 扩展函数
 * @author Ttt
 */
fun Int.buttonOf(
    text: String,
    path: String
): ButtonValue {
    return ButtonValue(
        this, text, path
    )
}

/**
 * detail: Button Value 实体类
 * @author Ttt
 */
class ButtonValue(
    // 按钮类型
    val type: Int,
    // 文案
    val text: String,
    // Router Path
    val path: String
) {

    companion object {

        // =======
        // = 常量 =
        // =======

        private const val BASE = 1001

        // Lib
        const val MODULE_LIB = BASE + 10000

        // UI
        const val MODULE_UI = BASE + 20000

        // 其他功能
        const val MODULE_OTHER = BASE + 30000

        // DevWidget UI 库
        const val MODULE_DEV_WIDGET = BASE + 40000

        // DevAssist Engine 实现
        const val MODULE_DEV_ASSIST_ENGINE = BASE + 50000

        // DevSKU 商品 SKU 组合封装实现
        const val MODULE_DEV_SKU = BASE + 60000

        // =======
        // = Lib =
        // =======

        // EventBusUtils
        const val BTN_EVENT_BUS = MODULE_LIB

        // GreenDAO
        const val BTN_GREEN_DAO = MODULE_LIB + 1

        // Room
        const val BTN_ROOM = MODULE_LIB + 2

        // DataStore
        const val BTN_DATA_STORE = MODULE_LIB + 3

        // GlideUtils
        const val BTN_GLIDE = MODULE_LIB + 4

        // ImageLoaderUtils
        const val BTN_IMAGE_LOADER = MODULE_LIB + 5

        // GsonUtils
        const val BTN_GSON = MODULE_LIB + 6

        // FastjsonUtils
        const val BTN_FASTJSON = MODULE_LIB + 7

        // ZXingUtils
        const val BTN_ZXING = MODULE_LIB + 8

        // PictureSelectorUtils
        const val BTN_PICTURE_SELECTOR = MODULE_LIB + 9

        // OkGoUtils
        const val BTN_OKGO = MODULE_LIB + 10

        // LubanUtils
        const val BTN_LUBAN = MODULE_LIB + 11

        // MMKVUtils
        const val BTN_MMKV = MODULE_LIB + 12

        // WorkManagerUtils
        const val BTN_WORK_MANAGER = MODULE_LIB + 13

        // ======
        // = UI =
        // ======

        // ToastTint ( 着色美化 Toast )
        const val BTN_TOAST_TINT = MODULE_UI

        // Toast Success
        const val BTN_TOAST_TINT_SUCCESS = BTN_TOAST_TINT + 1

        // Toast Error
        const val BTN_TOAST_TINT_ERROR = BTN_TOAST_TINT + 2

        // Toast Info
        const val BTN_TOAST_TINT_INFO = BTN_TOAST_TINT + 3

        // Toast Normal
        const val BTN_TOAST_TINT_NORMAL = BTN_TOAST_TINT + 4

        // Toast Warning
        const val BTN_TOAST_TINT_WARNING = BTN_TOAST_TINT + 5

        // Toast Custom Style
        const val BTN_TOAST_TINT_CUSTOM_STYLE = BTN_TOAST_TINT + 6

        // 常见 UI、GradientDrawable 效果等
        const val BTN_UI_EFFECT = MODULE_UI + 100

        // 计算字体宽度、高度
        const val BTN_TEXT_CALC = MODULE_UI + 200

        // Adapter Item EditText 输入监听
        const val BTN_ADAPTER_EDITS = MODULE_UI + 300

        // 多选辅助类 MultiSelectAssist
        const val BTN_MULTI_SELECT = MODULE_UI + 400

        // GPU ACV 文件滤镜效果
        const val BTN_GPU_ACV = MODULE_UI + 500

        // GPU 滤镜效果
        const val BTN_GPU_FILTER = MODULE_UI + 600

        // 创建二维码
        const val BTN_QRCODE_CREATE = MODULE_UI + 700

        // 二维码图片解析
        const val BTN_QRCODE_IMAGE = MODULE_UI + 800

        // 二维码扫描解析
        const val BTN_QRCODE_SCAN = MODULE_UI + 900

        // CapturePictureUtils 截图工具类
        const val BTN_CAPTURE_PICTURE = MODULE_UI + 1000

        // 两个 TextView 显示效果
        const val BTN_TEXTVIEW = MODULE_UI + 1100

        // RecyclerView 吸附效果
        const val BTN_ITEM_STICKY = MODULE_UI + 1200

        // RecyclerView 滑动删除、上下滑动
        const val BTN_RECY_ITEM_SLIDE = MODULE_UI + 1300

        // LinearSnapHelper - RecyclerView
        const val BTN_RECY_LINEAR_SNAP = MODULE_UI + 1400

        // LinearSnapHelper - 无限滑动
        const val BTN_RECY_LINEAR_SNAP_MAX = MODULE_UI + 1500

        // PagerSnapHelper - RecyclerView
        const val BTN_RECY_PAGER_SNAP = MODULE_UI + 1600

        // PagerSnapHelper - 无限滑动
        const val BTN_RECY_PAGER_SNAP_MAX = MODULE_UI + 1700

        // Material ShapeableImageView
        const val BTN_SHAPEABLE_IMAGE_VIEW = MODULE_UI + 1800

        // Material BottomSheet
        const val BTN_BOTTOM_SHEET = MODULE_UI + 1900

        // Material BottomSheetDialog
        const val BTN_BOTTOM_SHEET_DIALOG = MODULE_UI + 2000

        // Palette 调色板
        const val BTN_PALETTE = MODULE_UI + 2100

        // Flexbox LayoutManager
        const val BTN_FLEXBOX_LAYOUTMANAGER = MODULE_UI + 2200

        // Material Chip、ChipGroups、ChipDrawable
        const val BTN_CHIP = MODULE_UI + 2300

        // ViewPager2
        const val BTN_VIEWPAGER2 = MODULE_UI + 2400

        // RecyclerView - ConcatAdapter
        const val BTN_RECYCLERVIEW_CONCATADAPTER = MODULE_UI + 2500

        // RecyclerView MultiType Adapter
        const val BTN_RECYCLERVIEW_MULTITYPE_ADAPTER = MODULE_UI + 2600

        // 购物车加入动画
        const val BTN_SHOP_CARD_ADD_ANIM = MODULE_UI + 2700

        // ==========
        // = 其他功能 =
        // ==========

        // Wifi 监听
        const val BTN_WIFI_LISTENER = MODULE_OTHER + 1

        // 网络监听
        const val BTN_NETWORK_LISTENER = MODULE_OTHER + 2

        // 电话监听
        const val BTN_PHONE_LISTENER = MODULE_OTHER + 3

        // 短信监听
        const val BTN_SMS_LISTENER = MODULE_OTHER + 4

        // 时区、时间监听
        const val BTN_TIME_LISTENER = MODULE_OTHER + 5

        // 屏幕监听
        const val BTN_SCREEN_LISTENER = MODULE_OTHER + 6

        // 屏幕旋转监听 ( 重力传感器 )
        const val BTN_ROTA_LISTENER = MODULE_OTHER + 7

        // 屏幕旋转监听 ( OrientationEventListener )
        const val BTN_ROTA2_LISTENER = MODULE_OTHER + 8

        // 电量监听
        const val BTN_BATTERY_LISTENER = MODULE_OTHER + 9

        // 应用状态监听
        const val BTN_APP_STATE_LISTENER = MODULE_OTHER + 10

        // 通知栏监听服务 ( NotificationService )
        const val BTN_NOTIFICATION_SERVICE = MODULE_OTHER + 100

        // 检查是否开启
        const val BTN_NOTIFICATION_SERVICE_CHECK = BTN_NOTIFICATION_SERVICE + 1

        // 开始监听
        const val BTN_NOTIFICATION_SERVICE_REGISTER = BTN_NOTIFICATION_SERVICE + 2

        // 注销监听
        const val BTN_NOTIFICATION_SERVICE_UNREGISTER = BTN_NOTIFICATION_SERVICE + 3

        // 无障碍监听服务 ( AccessibilityListenerService )
        const val BTN_ACCESSIBILITY_SERVICE = MODULE_OTHER + 200

        // 检查是否开启
        const val BTN_ACCESSIBILITY_SERVICE_CHECK = BTN_ACCESSIBILITY_SERVICE + 1

        // 开始监听
        const val BTN_ACCESSIBILITY_SERVICE_REGISTER = BTN_ACCESSIBILITY_SERVICE + 2

        // 注销监听
        const val BTN_ACCESSIBILITY_SERVICE_UNREGISTER = BTN_ACCESSIBILITY_SERVICE + 3

        // 铃声、震动、通知栏等功能
        const val BTN_FUNCTION = MODULE_OTHER + 400

        // 震动
        const val BTN_FUNCTION_VIBRATE = BTN_FUNCTION + 1

        // 铃声 - 播放一小段音频
        const val BTN_FUNCTION_BEEP = BTN_FUNCTION + 2

        // 是否存在通知权限
        const val BTN_FUNCTION_NOTIFICATION_CHECK = BTN_FUNCTION + 3

        // 开启通知权限
        const val BTN_FUNCTION_NOTIFICATION_OPEN = BTN_FUNCTION + 4

        // 通知消息
        const val BTN_FUNCTION_NOTIFICATION = BTN_FUNCTION + 5

        // 移除消息
        const val BTN_FUNCTION_NOTIFICATION_REMOVE = BTN_FUNCTION + 6

        // 回到桌面
        const val BTN_FUNCTION_HOME = BTN_FUNCTION + 7

        // 打开手电筒
        const val BTN_FUNCTION_FLASHLIGHT_OPEN = BTN_FUNCTION + 8

        // 关闭手电筒
        const val BTN_FUNCTION_FLASHLIGHT_CLOSE = BTN_FUNCTION + 9

        // 是否创建桌面快捷方式
        const val BTN_FUNCTION_SHORTCUT_CHECK = BTN_FUNCTION + 10

        // 创建桌面快捷方式
        const val BTN_FUNCTION_SHORTCUT_CREATE = BTN_FUNCTION + 11

        // 删除桌面快捷方式
        const val BTN_FUNCTION_SHORTCUT_DELETE = BTN_FUNCTION + 12

        // 打印内存信息
        const val BTN_FUNCTION_MEMORY_PRINT = BTN_FUNCTION + 13

        // 打印设备信息
        const val BTN_FUNCTION_DEVICE_PRINT = BTN_FUNCTION + 14

        // 跳转到 APP 设置详情页面
        const val BTN_FUNCTION_APP_DETAILS_SETTINGS = BTN_FUNCTION + 15

        // 打开 GPS 设置界面
        const val BTN_FUNCTION_GPS_SETTINGS = BTN_FUNCTION + 16

        // 打开网络设置界面
        const val BTN_FUNCTION_WIRELESS_SETTINGS = BTN_FUNCTION + 17

        // 跳转到系统设置页面
        const val BTN_FUNCTION_SYS_SETTINGS = BTN_FUNCTION + 18

        // TimerManager 定时器工具类
        const val BTN_TIMER = MODULE_OTHER + 500

        // 启动定时器
        const val BTN_TIMER_START = BTN_TIMER + 1

        // 停止定时器
        const val BTN_TIMER_STOP = BTN_TIMER + 2

        // 重新启动定时器
        const val BTN_TIMER_RESTART = BTN_TIMER + 3

        // 定时器是否启动
        const val BTN_TIMER_CHECK = BTN_TIMER + 4

        // 获取定时器
        const val BTN_TIMER_GET = BTN_TIMER + 5

        // 获取运行次数
        const val BTN_TIMER_GET_NUMBER = BTN_TIMER + 6

        // DevCache 缓存工具类
        const val BTN_CACHE = MODULE_OTHER + 600

        // 存储字符串
        const val BTN_CACHE_STRING = BTN_CACHE + 1

        // 存储有效期字符串
        const val BTN_CACHE_STRING_TIME = BTN_CACHE + 2

        // 获取字符串
        const val BTN_CACHE_STRING_GET = BTN_CACHE + 3

        // 存储实体类
        const val BTN_CACHE_BEAN = BTN_CACHE + 4

        // 存储有效期实体类
        const val BTN_CACHE_BEAN_TIME = BTN_CACHE + 5

        // 获取实体类
        const val BTN_CACHE_BEAN_GET = BTN_CACHE + 6

        // 存储到指定位置
        const val BTN_CACHE_FILE = BTN_CACHE + 7

        // 获取指定位置缓存数据
        const val BTN_CACHE_FILE_GET = BTN_CACHE + 8

        // 清除全部数据
        const val BTN_CACHE_CLEAR = BTN_CACHE + 9

        // DevLogger 日志工具类
        const val BTN_LOGGER = MODULE_OTHER + 700

        // 打印日志
        const val BTN_LOGGER_PRINT = BTN_LOGGER + 1

        // 打印日志耗时测试
        const val BTN_LOGGER_TIME = BTN_LOGGER + 2

        // 日志、异常文件记录保存
        const val BTN_FILE_RECORD = MODULE_OTHER + 800

        // FileRecordUtils 工具类
        const val BTN_FILE_RECORD_UTILS = BTN_FILE_RECORD + 1

        // 奔溃日志捕获
        const val BTN_CRASH = MODULE_OTHER + 900

        // 点击崩溃捕获信息
        const val BTN_CRASH_CLICK_CATCH = BTN_CRASH + 1

        // 路径信息
        const val BTN_PATH = MODULE_OTHER + 1000

        // 内部存储路径
        const val BTN_PATH_INTERNAL = BTN_PATH + 1

        // 应用外部存储路径
        const val BTN_PATH_APP_EXTERNAL = BTN_PATH + 2

        // 外部存储路径 ( SDCard )
        const val BTN_PATH_SDCARD = BTN_PATH + 3

        // WebView 辅助类
        const val BTN_WEBVIEW = MODULE_OTHER + 1100

        // Activity Result API
        const val BTN_ACTIVITY_RESULT_API = MODULE_OTHER + 1200

        // startActivityForResult Callback
        const val BTN_ACTIVITY_RESULT_CALLBACK = MODULE_OTHER + 1300

        // 添加联系人
        const val BTN_ADD_CONTACT = MODULE_OTHER + 1400

        // 手机壁纸
        const val BTN_WALLPAPER = MODULE_OTHER + 1500

        // 悬浮窗管理辅助类 ( 需权限 )
        const val BTN_FLOATING_WINDOW_MANAGER = MODULE_OTHER + 1600

        // 打开悬浮窗
        const val BTN_OPEN_FLOATING_WINDOW = BTN_FLOATING_WINDOW_MANAGER + 1

        // 关闭悬浮窗
        const val BTN_CLOSE_FLOATING_WINDOW = BTN_FLOATING_WINDOW_MANAGER + 2

        // 悬浮窗管理辅助类 ( 无需权限依赖 Activity )
        const val BTN_FLOATING_WINDOW_MANAGER2 = MODULE_OTHER + 1700

        // ==================
        // = DevWidget UI 库 =
        // ==================

        // DevWidget
        private const val BTN_DEV_WIDGET = MODULE_DEV_WIDGET

        // ViewPager 滑动监听、控制滑动
        const val BTN_VIEW_PAGER = BTN_DEV_WIDGET + 100

        // 自定义 ProgressBar 样式 View
        const val BTN_CUSTOM_PROGRESS_BAR = BTN_DEV_WIDGET + 200

        // 自定义扫描 View ( QRCode、AR )
        const val BTN_SCAN_VIEW = BTN_DEV_WIDGET + 300

        // 自动换行 View
        const val BTN_WRAP_VIEW = BTN_DEV_WIDGET + 400

        // 签名 View
        const val BTN_SIGN_VIEW = BTN_DEV_WIDGET + 500

        // 换行监听 View
        const val BTN_LINE_VIEW = BTN_DEV_WIDGET + 600

        // 自定义点赞效果 View
        const val BTN_LIKE_VIEW = BTN_DEV_WIDGET + 700

        // 自定义角标 View
        const val BTN_CORNER_LABEL_VIEW = BTN_DEV_WIDGET + 800

        // View 填充辅助类
        const val BTN_VIEW_ASSIST = BTN_DEV_WIDGET + 900

        // RecyclerView ( loading )
        const val BTN_VIEW_ASSIST_RECYCLER = BTN_VIEW_ASSIST + 1

        // Error ( failed )
        const val BTN_VIEW_ASSIST_ERROR = BTN_VIEW_ASSIST + 2

        // Empty ( data )
        const val BTN_VIEW_ASSIST_EMPTY = BTN_VIEW_ASSIST + 3

        // Custom Type
        const val BTN_VIEW_ASSIST_CUSTOM = BTN_VIEW_ASSIST + 4

        // 翻转卡片 View
        const val BTN_FLIP_CARD_VIEW = BTN_DEV_WIDGET + 1000

        // 波浪 View
        const val BTN_WAVE_VIEW = BTN_DEV_WIDGET + 1100

        // Linear Color ItemDecoration
        const val BTN_LINEAR_ITEM_DECORATION = BTN_DEV_WIDGET + 1200

        // Linear Vertical ItemDecoration
        const val BTN_LINEAR_ITEM_VERTICAL = BTN_LINEAR_ITEM_DECORATION + 1

        // Linear Horizontal ItemDecoration
        const val BTN_LINEAR_ITEM_HORIZONTAL = BTN_LINEAR_ITEM_DECORATION + 2

        // Grid Color ItemDecoration
        const val BTN_GRID_ITEM_DECORATION = BTN_DEV_WIDGET + 1300

        // Grid Vertical ItemDecoration
        const val BTN_GRID_ITEM_VERTICAL = BTN_GRID_ITEM_DECORATION + 1

        // Grid Horizontal ItemDecoration
        const val BTN_GRID_ITEM_HORIZONTAL = BTN_GRID_ITEM_DECORATION + 2

        // ========================
        // = DevAssist Engine 实现 =
        // ========================

        // DevAssist Engine
        private const val BTN_ENGINE = MODULE_DEV_ASSIST_ENGINE

        // Analytics Engine 数据统计 ( 埋点 )
        const val BTN_ENGINE_ANALYTICS = BTN_ENGINE + 100

        // Cache Engine 有效期键值对缓存
        const val BTN_ENGINE_CACHE = BTN_ENGINE + 200

        // Image Compress Engine 图片压缩
        const val BTN_ENGINE_IMAGE_COMPRESS = BTN_ENGINE + 300

        // Image Engine 图片加载、下载、转格式等
        const val BTN_ENGINE_IMAGE = BTN_ENGINE + 400

        // JSON Engine
        const val BTN_ENGINE_JSON = BTN_ENGINE + 500

        // KeyValue Engine 键值对存储
        const val BTN_ENGINE_KEYVALUE = BTN_ENGINE + 600

        // Log Engine 日志打印
        const val BTN_ENGINE_LOG = BTN_ENGINE + 700

        // Media Selector Engine 多媒体资源选择
        const val BTN_ENGINE_MEDIA_SELECTOR = BTN_ENGINE + 800

        // Permission Engine 权限申请
        const val BTN_ENGINE_PERMISSION = BTN_ENGINE + 9000

        // Push Engine 推送平台处理
        const val BTN_ENGINE_PUSH = BTN_ENGINE + 1000

        // Share Engine 分享平台处理
        const val BTN_ENGINE_SHARE = BTN_ENGINE + 1100

        // Storage Engine 外部、内部文件存储
        const val BTN_ENGINE_STORAGE = BTN_ENGINE + 1200

        // =============================
        // = DevSKU 商品 SKU 组合封装实现 =
        // =============================

        // DevSKU
        const val BTN_DEV_SKU = MODULE_DEV_SKU

        // 显示商品 SKU Dialog
        const val BTN_SKU_DIALOG = BTN_DEV_SKU + 100
    }
}