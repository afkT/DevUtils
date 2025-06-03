package afkt.project.data_model.button

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
    val mainButtonValues = mutableListOf(
        ButtonValue.MODULE_LIB.buttonOf(
            "Lib 框架",
            RouterPath.ModuleActivity_PATH
        ),
        ButtonValue.MODULE_UI.buttonOf(
            "UI 效果",
            RouterPath.ModuleActivity_PATH
        ),
        ButtonValue.MODULE_OTHER.buttonOf(
            "其他功能",
            RouterPath.ModuleActivity_PATH
        ),
        ButtonValue.MODULE_DEV_WIDGET.buttonOf(
            "DevWidget UI 库",
            RouterPath.ModuleActivity_PATH
        ),
        ButtonValue.MODULE_DEV_ASSIST_ENGINE.buttonOf(
            "DevAssist Engine 实现",
            RouterPath.DEV_LIBS.DevAssistEngineActivity_PATH
        ),
        ButtonValue.MODULE_DEV_SKU.buttonOf(
            "DevSKU 商品 SKU 组合封装实现",
            RouterPath.DEV_LIBS.DevSKUActivity_PATH
        )
    )

    /**
     * 获取 Module 功能 Button Value 集合
     * @param type Module Type
     * @return [List]
     */
    fun getModuleButtonValues(type: Int): List<ButtonValue> {
        when (type) {
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
            ButtonValue.BTN_LINEAR_ITEM_DECORATION -> return linearItemDecorationButtonValues
            ButtonValue.BTN_GRID_ITEM_DECORATION -> return gridItemDecorationButtonValues
        }
        return emptyList()
    }

    // =======
    // = Lib =
    // =======

    /**
     * 获取 Lib Module Button Value 集合
     * @return [List]
     */
    private val moduleLibButtonValues = mutableListOf(
        ButtonValue.BTN_GREEN_DAO.buttonOf(
            "GreenDAO",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ROOM.buttonOf(
            "Room",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_DATA_STORE.buttonOf(
            "DataStore",
            RouterPath.LIB_FRAME.DataStoreActivity_PATH
        ),
        ButtonValue.BTN_EVENT_BUS.buttonOf(
            "EventBusUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_GLIDE.buttonOf(
            "GlideUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_IMAGE_LOADER.buttonOf(
            "ImageLoaderUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_GSON.buttonOf(
            "GsonUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FASTJSON.buttonOf(
            "FastjsonUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ZXING.buttonOf(
            "ZXingUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_PICTURE_SELECTOR.buttonOf(
            "PictureSelectorUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_OKGO.buttonOf(
            "OkGoUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_LUBAN.buttonOf(
            "LubanUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_MMKV.buttonOf(
            "MMKVUtils",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_WORK_MANAGER.buttonOf(
            "WorkManagerUtils",
            RouterPath.EMPTY
        )
    )

    // ======
    // = UI =
    // ======

    /**
     * 获取 UI Module Button Value 集合
     * @return [List]
     */
    private val moduleUIButtonValues = mutableListOf(
        ButtonValue.BTN_TOAST_TINT.buttonOf(
            "ToastTint ( 着色美化 Toast )",
            RouterPath.UI_EFFECT.ToastTintActivity_PATH
        ),
        ButtonValue.BTN_UI_EFFECT.buttonOf(
            "常见 UI、GradientDrawable 效果等",
            RouterPath.UI_EFFECT.UIEffectActivity_PATH
        ),
        ButtonValue.BTN_TEXT_CALC.buttonOf(
            "计算字体宽度、高度",
            RouterPath.UI_EFFECT.TextCalcActivity_PATH
        ),
        ButtonValue.BTN_ADAPTER_EDITS.buttonOf(
            "Adapter Item EditText 输入监听",
            RouterPath.UI_EFFECT.AdapterEditsActivity_PATH
        ),
        ButtonValue.BTN_MULTI_SELECT.buttonOf(
            "多选辅助类 MultiSelectAssist",
            RouterPath.UI_EFFECT.MultiSelectActivity_PATH
        ),
        ButtonValue.BTN_GPU_ACV.buttonOf(
            "GPU ACV 文件滤镜效果",
            RouterPath.UI_EFFECT.GPUFilterACVActivity_PATH
        ),
        ButtonValue.BTN_GPU_FILTER.buttonOf(
            "GPU 滤镜效果",
            RouterPath.UI_EFFECT.GPUFilterActivity_PATH
        ),
        ButtonValue.BTN_QRCODE_CREATE.buttonOf(
            "创建二维码",
            RouterPath.UI_EFFECT.QRCodeCreateActivity_PATH
        ),
        ButtonValue.BTN_QRCODE_IMAGE.buttonOf(
            "二维码图片解析",
            RouterPath.UI_EFFECT.QRCodeImageActivity_PATH
        ),
        ButtonValue.BTN_QRCODE_SCAN.buttonOf(
            "二维码扫描解析",
            RouterPath.UI_EFFECT.QRCodeScanActivity_PATH
        ),
        ButtonValue.BTN_CAPTURE_PICTURE.buttonOf(
            "CapturePictureUtils 截图工具类",
            RouterPath.UI_EFFECT.CapturePictureActivity_PATH
        ),
        ButtonValue.BTN_TEXTVIEW.buttonOf(
            "两个 TextView 显示效果",
            RouterPath.UI_EFFECT.TextViewActivity_PATH
        ),
        ButtonValue.BTN_ITEM_STICKY.buttonOf(
            "RecyclerView 吸附效果",
            RouterPath.UI_EFFECT.ItemStickyActivity_PATH
        ),
        ButtonValue.BTN_RECY_ITEM_SLIDE.buttonOf(
            "RecyclerView 滑动删除、上下滑动",
            RouterPath.UI_EFFECT.RecyItemSlideActivity_PATH
        ),
        ButtonValue.BTN_RECY_LINEAR_SNAP.buttonOf(
            "LinearSnapHelper - RecyclerView",
            RouterPath.UI_EFFECT.LinearSnapActivity_PATH
        ),
        ButtonValue.BTN_RECY_LINEAR_SNAP_MAX.buttonOf(
            "LinearSnapHelper - 无限滑动",
            RouterPath.UI_EFFECT.LinearSnapMAXActivity_PATH
        ),
        ButtonValue.BTN_RECY_PAGER_SNAP.buttonOf(
            "PagerSnapHelper - RecyclerView",
            RouterPath.UI_EFFECT.PagerSnapActivity_PATH
        ),
        ButtonValue.BTN_RECY_PAGER_SNAP_MAX.buttonOf(
            "PagerSnapHelper - 无限滑动",
            RouterPath.UI_EFFECT.PagerSnapMAXActivity_PATH
        ),
        ButtonValue.BTN_SHAPEABLE_IMAGE_VIEW.buttonOf(
            "Material ShapeableImageView",
            RouterPath.UI_EFFECT.ShapeableImageViewActivity_PATH
        ),
        ButtonValue.BTN_BOTTOM_SHEET.buttonOf(
            "Material BottomSheet",
            RouterPath.UI_EFFECT.BottomSheetActivity_PATH
        ),
        ButtonValue.BTN_BOTTOM_SHEET_DIALOG.buttonOf(
            "Material BottomSheetDialog",
            RouterPath.UI_EFFECT.BottomSheetDialogActivity_PATH
        ),
        ButtonValue.BTN_PALETTE.buttonOf(
            "Palette 调色板",
            RouterPath.UI_EFFECT.PaletteActivity_PATH
        ),
        ButtonValue.BTN_FLEXBOX_LAYOUTMANAGER.buttonOf(
            "Flexbox LayoutManager",
            RouterPath.UI_EFFECT.FlexboxLayoutManagerActivity_PATH
        ),
        ButtonValue.BTN_CHIP.buttonOf(
            "Material Chip、ChipGroups、ChipDrawable",
            RouterPath.UI_EFFECT.ChipActivity_PATH
        ),
        ButtonValue.BTN_VIEWPAGER2.buttonOf(
            "ViewPager2",
            RouterPath.UI_EFFECT.ViewPager2Activity_PATH
        ),
        ButtonValue.BTN_RECYCLERVIEW_CONCATADAPTER.buttonOf(
            "RecyclerView - ConcatAdapter",
            RouterPath.UI_EFFECT.RecyConcatAdapterActivity_PATH
        ),
        ButtonValue.BTN_RECYCLERVIEW_MULTITYPE_ADAPTER.buttonOf(
            "RecyclerView MultiType Adapter",
            RouterPath.UI_EFFECT.RecyMultiTypeAdapterActivity_PATH
        ),
        ButtonValue.BTN_SHOP_CARD_ADD_ANIM.buttonOf(
            "购物车加入动画",
            RouterPath.UI_EFFECT.ShopCartAddAnimActivity_PATH
        )
    )

    /**
     * 获取 Toast Button Value 集合
     * @return [List]
     */
    val toastButtonValues = mutableListOf(
        ButtonValue.BTN_TOAST_TINT_SUCCESS.buttonOf(
            "Toast Success",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TOAST_TINT_ERROR.buttonOf(
            "Toast Error",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TOAST_TINT_INFO.buttonOf(
            "Toast Info",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TOAST_TINT_NORMAL.buttonOf(
            "Toast Normal",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TOAST_TINT_WARNING.buttonOf(
            "Toast Warning",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TOAST_TINT_CUSTOM_STYLE.buttonOf(
            "Toast Custom Style",
            RouterPath.EMPTY
        )
    )

    // ==========
    // = 其他功能 =
    // ==========

    /**
     * 获取 Other Module Button Value 集合
     * @return [List]
     */
    private val moduleOtherButtonValues = mutableListOf(
        ButtonValue.BTN_NOTIFICATION_SERVICE.buttonOf(
            "通知栏监听服务 ( NotificationService )",
            RouterPath.OTHER_FUNCTION.NotificationServiceActivity_PATH
        ),
        ButtonValue.BTN_ACCESSIBILITY_SERVICE.buttonOf(
            "无障碍监听服务 ( AccessibilityListenerService )",
            RouterPath.OTHER_FUNCTION.AccessibilityListenerServiceActivity_PATH
        ),
        ButtonValue.BTN_FUNCTION.buttonOf(
            "铃声、震动、通知栏等功能",
            RouterPath.OTHER_FUNCTION.FunctionActivity_PATH
        ),
        ButtonValue.BTN_TIMER.buttonOf(
            "TimerManager 定时器工具类",
            RouterPath.OTHER_FUNCTION.TimerActivity_PATH
        ),
        ButtonValue.BTN_CACHE.buttonOf(
            "DevCache 缓存工具类",
            RouterPath.OTHER_FUNCTION.CacheActivity_PATH
        ),
        ButtonValue.BTN_LOGGER.buttonOf(
            "DevLogger 日志工具类",
            RouterPath.OTHER_FUNCTION.LoggerActivity_PATH
        ),
        ButtonValue.BTN_FILE_RECORD.buttonOf(
            "日志、异常文件记录保存",
            RouterPath.OTHER_FUNCTION.FileRecordActivity_PATH
        ),
        ButtonValue.BTN_CRASH.buttonOf(
            "奔溃日志捕获",
            RouterPath.OTHER_FUNCTION.CrashCatchActivity_PATH
        ),
        ButtonValue.BTN_PATH.buttonOf(
            "路径信息",
            RouterPath.OTHER_FUNCTION.PathActivity_PATH
        ),
        ButtonValue.BTN_WEBVIEW.buttonOf(
            "WebView 辅助类",
            RouterPath.OTHER_FUNCTION.WebViewActivity_PATH
        ),
        ButtonValue.BTN_ACTIVITY_RESULT_API.buttonOf(
            "Activity Result API",
            RouterPath.OTHER_FUNCTION.ActivityResultAPIActivity_PATH
        ),
        ButtonValue.BTN_ACTIVITY_RESULT_CALLBACK.buttonOf(
            "startActivityForResult Callback",
            RouterPath.OTHER_FUNCTION.ActivityResultCallbackActivity_PATH
        ),
        ButtonValue.BTN_ADD_CONTACT.buttonOf(
            "添加联系人",
            RouterPath.OTHER_FUNCTION.AddContactActivity_PATH
        ),
        ButtonValue.BTN_WALLPAPER.buttonOf(
            "手机壁纸",
            RouterPath.OTHER_FUNCTION.WallpaperActivity_PATH
        ),
        ButtonValue.BTN_FLOATING_WINDOW_MANAGER.buttonOf(
            "悬浮窗管理辅助类 ( 需权限 )",
            RouterPath.OTHER_FUNCTION.FloatingWindowManagerActivity_PATH
        ),
        ButtonValue.BTN_FLOATING_WINDOW_MANAGER2.buttonOf(
            "悬浮窗管理辅助类 ( 无需权限依赖 Activity )",
            RouterPath.OTHER_FUNCTION.FloatingWindowManager2Activity_PATH
        )
    )

    /**
     * 获取 Listener Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val listenerButtonValues = mutableListOf(
        ButtonValue.BTN_WIFI_LISTENER.buttonOf(
            "Wifi 监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_NETWORK_LISTENER.buttonOf(
            "网络监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_PHONE_LISTENER.buttonOf(
            "电话监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_SMS_LISTENER.buttonOf(
            "短信监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TIME_LISTENER.buttonOf(
            "时区、时间监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_SCREEN_LISTENER.buttonOf(
            "屏幕监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ROTA_LISTENER.buttonOf(
            "屏幕旋转监听 ( 重力传感器 )",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ROTA2_LISTENER.buttonOf(
            "屏幕旋转监听 ( OrientationEventListener )",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_BATTERY_LISTENER.buttonOf(
            "电量监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_APP_STATE_LISTENER.buttonOf(
            "应用状态监听",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 Notification Service Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val notificationServiceButtonValues = mutableListOf(
        ButtonValue.BTN_NOTIFICATION_SERVICE_CHECK.buttonOf(
            "检查是否开启",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_NOTIFICATION_SERVICE_REGISTER.buttonOf(
            "开始监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_NOTIFICATION_SERVICE_UNREGISTER.buttonOf(
            "注销监听",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 Accessibility Listener Service Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val accessibilityListenerServiceButtonValues = mutableListOf(
        ButtonValue.BTN_ACCESSIBILITY_SERVICE_CHECK.buttonOf(
            "检查是否开启",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ACCESSIBILITY_SERVICE_REGISTER.buttonOf(
            "开始监听",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ACCESSIBILITY_SERVICE_UNREGISTER.buttonOf(
            "注销监听",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 Function Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val functionButtonValues = mutableListOf(
        ButtonValue.BTN_FUNCTION_VIBRATE.buttonOf(
            "震动",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_BEEP.buttonOf(
            "铃声 - 播放一小段音频",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_NOTIFICATION_CHECK.buttonOf(
            "是否存在通知权限",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_NOTIFICATION_OPEN.buttonOf(
            "开启通知权限",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_NOTIFICATION.buttonOf(
            "通知消息",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_NOTIFICATION_REMOVE.buttonOf(
            "移除消息",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_HOME.buttonOf(
            "回到桌面",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_FLASHLIGHT_OPEN.buttonOf(
            "打开手电筒",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_FLASHLIGHT_CLOSE.buttonOf(
            "关闭手电筒",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_SHORTCUT_CHECK.buttonOf(
            "是否创建桌面快捷方式",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_SHORTCUT_CREATE.buttonOf(
            "创建桌面快捷方式",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_SHORTCUT_DELETE.buttonOf(
            "删除桌面快捷方式",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_MEMORY_PRINT.buttonOf(
            "打印内存信息",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_DEVICE_PRINT.buttonOf(
            "打印设备信息",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_APP_DETAILS_SETTINGS.buttonOf(
            "跳转到 APP 设置详情页面",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_GPS_SETTINGS.buttonOf(
            "打开 GPS 设置界面",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_WIRELESS_SETTINGS.buttonOf(
            "打开网络设置界面",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_FUNCTION_SYS_SETTINGS.buttonOf(
            "跳转到系统设置页面",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 Timer Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val timerButtonValues = mutableListOf(
        ButtonValue.BTN_TIMER_START.buttonOf(
            "启动定时器",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TIMER_STOP.buttonOf(
            "停止定时器",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TIMER_RESTART.buttonOf(
            "重新启动定时器",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TIMER_CHECK.buttonOf(
            "定时器是否启动",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TIMER_GET.buttonOf(
            "获取定时器",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_TIMER_GET_NUMBER.buttonOf(
            "获取运行次数",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 Cache Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val cacheButtonValues = mutableListOf(
        ButtonValue.BTN_CACHE_STRING.buttonOf(
            "存储字符串",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CACHE_STRING_TIME.buttonOf(
            "存储有效期字符串",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CACHE_STRING_GET.buttonOf(
            "获取字符串",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CACHE_BEAN.buttonOf(
            "存储实体类",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CACHE_BEAN_TIME.buttonOf(
            "存储有效期实体类",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CACHE_BEAN_GET.buttonOf(
            "获取实体类",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CACHE_FILE.buttonOf(
            "存储到指定位置",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CACHE_FILE_GET.buttonOf(
            "获取指定位置缓存数据",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CACHE_CLEAR.buttonOf(
            "清除全部数据",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 Logger Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val loggerButtonValues = mutableListOf(
        ButtonValue.BTN_LOGGER_PRINT.buttonOf(
            "打印日志",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_LOGGER_TIME.buttonOf(
            "打印日志耗时测试",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 File Record Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val fileRecordButtonValues = mutableListOf(
        ButtonValue.BTN_FILE_RECORD_UTILS.buttonOf(
            "FileRecordUtils 工具类",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 Crash Record Button Value 集合
     * @return [List]
     */
    val crashButtonValues = mutableListOf(
        ButtonValue.BTN_CRASH_CLICK_CATCH.buttonOf(
            "点击崩溃捕获信息",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取 Path Button Value 集合
     * @return [List]
     */
    val pathButtonValues = mutableListOf(
        ButtonValue.BTN_PATH_INTERNAL.buttonOf(
            "内部存储路径",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_PATH_APP_EXTERNAL.buttonOf(
            "应用外部存储路径",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_PATH_SDCARD.buttonOf(
            "外部存储路径 ( SDCard )",
            RouterPath.EMPTY
        )
    )

    /**
     * 获取悬浮窗 Button Value 集合
     * @return [List]
     */
    val floatingWindowButtonValues = mutableListOf(
        ButtonValue.BTN_OPEN_FLOATING_WINDOW.buttonOf(
            "打开悬浮窗",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_CLOSE_FLOATING_WINDOW.buttonOf(
            "关闭悬浮窗",
            RouterPath.EMPTY
        )
    )

    // ==================
    // = DevWidget UI 库 =
    // ==================

    /**
     * 获取 DevWidget Module Button Value 集合
     * @return [List]
     */
    private val moduleDevWidgetButtonValues = mutableListOf(
        ButtonValue.BTN_VIEW_PAGER.buttonOf(
            "ViewPager 滑动监听、控制滑动",
            RouterPath.DEV_WIDGET.ViewPagerActivity_PATH
        ),
        ButtonValue.BTN_CUSTOM_PROGRESS_BAR.buttonOf(
            "自定义 ProgressBar 样式 View",
            RouterPath.DEV_WIDGET.ProgressBarActivity_PATH
        ),
        ButtonValue.BTN_SCAN_VIEW.buttonOf(
            "自定义扫描 View ( QRCode、AR )",
            RouterPath.DEV_WIDGET.ScanShapeActivity_PATH
        ),
        ButtonValue.BTN_WRAP_VIEW.buttonOf(
            "自动换行 View",
            RouterPath.DEV_WIDGET.WrapActivity_PATH
        ),
        ButtonValue.BTN_SIGN_VIEW.buttonOf(
            "签名 View",
            RouterPath.DEV_WIDGET.SignActivity_PATH
        ),
        ButtonValue.BTN_LINE_VIEW.buttonOf(
            "换行监听 View",
            RouterPath.DEV_WIDGET.LineActivity_PATH
        ),
        ButtonValue.BTN_LIKE_VIEW.buttonOf(
            "自定义点赞效果 View",
            RouterPath.DEV_WIDGET.FlowLikeActivity_PATH
        ),
        ButtonValue.BTN_CORNER_LABEL_VIEW.buttonOf(
            "自定义角标 View",
            RouterPath.DEV_WIDGET.CornerLabelActivity_PATH
        ),
        ButtonValue.BTN_VIEW_ASSIST.buttonOf(
            "View 填充辅助类",
            RouterPath.ButtonItemActivity_PATH
        ),
        ButtonValue.BTN_FLIP_CARD_VIEW.buttonOf(
            "翻转卡片 View",
            RouterPath.DEV_WIDGET.FlipCardActivity_PATH
        ),
        ButtonValue.BTN_WAVE_VIEW.buttonOf(
            "波浪 View",
            RouterPath.DEV_WIDGET.WaveViewActivity_PATH
        ),
        ButtonValue.BTN_LINEAR_ITEM_DECORATION.buttonOf(
            "Linear Color ItemDecoration",
            RouterPath.ButtonItemActivity_PATH
        ),
        ButtonValue.BTN_GRID_ITEM_DECORATION.buttonOf(
            "Grid Color ItemDecoration",
            RouterPath.ButtonItemActivity_PATH
        )
    )

    /**
     * 获取 ViewAssist Button Value 集合
     * @return [List]
     */
    private val viewAssistButtonValues = mutableListOf(
        ButtonValue.BTN_VIEW_ASSIST_RECYCLER.buttonOf(
            "RecyclerView ( loading )",
            RouterPath.DEV_WIDGET.ViewAssistRecyclerViewLoadActivity_PATH
        ),
        ButtonValue.BTN_VIEW_ASSIST_ERROR.buttonOf(
            "Error ( failed )",
            RouterPath.DEV_WIDGET.ViewAssistActivity_PATH
        ),
        ButtonValue.BTN_VIEW_ASSIST_EMPTY.buttonOf(
            "Empty ( data )",
            RouterPath.DEV_WIDGET.ViewAssistActivity_PATH
        ),
        ButtonValue.BTN_VIEW_ASSIST_CUSTOM.buttonOf(
            "Custom Type",
            RouterPath.DEV_WIDGET.ViewAssistActivity_PATH
        )
    )

    /**
     * 获取 Linear Color ItemDecoration Button Value 集合
     * @return [List]
     */
    private val linearItemDecorationButtonValues = mutableListOf(
        ButtonValue.BTN_LINEAR_ITEM_VERTICAL.buttonOf(
            "Linear Vertical ItemDecoration",
            RouterPath.DEV_WIDGET.LinearColorItemDecorationActivity_PATH
        ),
        ButtonValue.BTN_LINEAR_ITEM_HORIZONTAL.buttonOf(
            "Linear Horizontal ItemDecoration",
            RouterPath.DEV_WIDGET.LinearColorItemDecorationActivity_PATH
        )
    )

    /**
     * 获取 Grid Color ItemDecoration Button Value 集合
     * @return [List]
     */
    private val gridItemDecorationButtonValues = mutableListOf(
        ButtonValue.BTN_GRID_ITEM_VERTICAL.buttonOf(
            "Grid Vertical ItemDecoration",
            RouterPath.DEV_WIDGET.GridColorItemDecorationActivity_PATH
        ),
        ButtonValue.BTN_GRID_ITEM_HORIZONTAL.buttonOf(
            "Grid Horizontal ItemDecoration",
            RouterPath.DEV_WIDGET.GridColorItemDecorationActivity_PATH
        )
    )

    // ========================
    // = DevAssist Engine 实现 =
    // ========================

    /**
     * 获取 DevAssist Engine Module Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val moduleDevAssistEngineButtonValues = mutableListOf(
        ButtonValue.BTN_ENGINE_ANALYTICS.buttonOf(
            "Analytics Engine 数据统计 ( 埋点 )",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_CACHE.buttonOf(
            "Cache Engine 有效期键值对缓存",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_IMAGE_COMPRESS.buttonOf(
            "Image Compress Engine 图片压缩",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_IMAGE.buttonOf(
            "Image Engine 图片加载、下载、转格式等",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_JSON.buttonOf(
            "JSON Engine",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_KEYVALUE.buttonOf(
            "KeyValue Engine 键值对存储",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_LOG.buttonOf(
            "Log Engine 日志打印",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_MEDIA_SELECTOR.buttonOf(
            "Media Selector Engine 多媒体资源选择",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_PERMISSION.buttonOf(
            "Permission Engine 权限申请",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_PUSH.buttonOf(
            "Push Engine 推送平台处理",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_SHARE.buttonOf(
            "Share Engine 分享平台处理",
            RouterPath.EMPTY
        ),
        ButtonValue.BTN_ENGINE_STORAGE.buttonOf(
            "Storage Engine 外部、内部文件存储",
            RouterPath.EMPTY
        )
    )

    // =============================
    // = DevSKU 商品 SKU 组合封装实现 =
    // =============================

    /**
     * 获取 DevSKU Module Button Value 集合
     * @return [List]
     */
    @JvmStatic
    val moduleDevSKUButtonValues = mutableListOf(
        ButtonValue.BTN_SKU_DIALOG.buttonOf(
            "显示商品 SKU Dialog",
            RouterPath.EMPTY
        )
    )
}