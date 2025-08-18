package afkt.project.model.button

import afkt.project.R
import afkt.project.model.basic.IntentData
import android.os.Bundle
import dev.utils.DevFinal

// =================================
// = ButtonEnum 映射 Fragment 传参 =
// =================================

fun ButtonEnum.args(): Bundle? {
    return IntentData.with()
        .setTitle(this.text)
        .setType(this.name)
        .insert()
}

// ==========
// = 枚举类型 =
// ==========

/**
 * detail: Button 枚举类型
 * @author Ttt
 */
enum class ButtonEnum(
    val text: String,
    // 对应的 Fragment
    val fragmentId: Int = DevFinal.DEFAULT.ERROR_INT
) {
    MODULE_LIB("Lib Utils", R.id.LibUtilsFragment),

    MODULE_UI_EFFECT("UI 效果", R.id.UIEffectFragment),

    MODULE_OTHER_FUN("其他功能", R.id.OtherFunctionFragment),

    MODULE_DEV_WIDGET("DevWidget UI 库", R.id.DevWidgetFragment),

    MODULE_DEV_ASSIST_ENGINE("DevAssist Engine 实现", R.id.DevAssistEngineFragment),

    // =============
    // = Lib Utils =
    // =============

    BTN_LIB_UTILS_DB_GREEN_DAO("GreenDAO"),

    BTN_LIB_UTILS_DB_ROOM("Room"),

    BTN_LIB_UTILS_GLIDE("GlideUtils"),

    BTN_LIB_UTILS_GSON("GsonUtils"),

    BTN_LIB_UTILS_MMKV("MMKVUtils"),

    BTN_LIB_UTILS_WORK_MANAGER("WorkManagerUtils"),

    BTN_LIB_UTILS_FASTJSON("FastjsonUtils"),

    BTN_LIB_UTILS_DATA_STORE("DataStoreUtils"),

    BTN_LIB_UTILS_ZXING("ZXingUtils"),

    BTN_LIB_UTILS_PICTURE_SELECTOR("PictureSelectorUtils"),

    BTN_LIB_UTILS_LUBAN("LubanUtils"),

    BTN_LIB_UTILS_EVENT_BUS("EventBusUtils"),

    // =============
    // = DevWidget =
    // =============

    BTN_DEV_WIDGET_VIEW_PAGER("ViewPager 滑动监听、控制滑动", R.id.ViewPagerFragment),

    BTN_DEV_WIDGET_CUSTOM_PROGRESS_BAR("自定义 ProgressBar 样式 View", R.id.ProgressBarFragment),

    BTN_DEV_WIDGET_SCAN_VIEW("自定义扫描 View ( QRCode、AR )", R.id.ScanShapeFragment),

    BTN_DEV_WIDGET_LINEAR_ITEM_VERTICAL(
        "Linear Vertical ItemDecoration", R.id.LinearVerticalFragment
    ),

    BTN_DEV_WIDGET_LINEAR_ITEM_HORIZONTAL(
        "Linear Horizontal ItemDecoration", R.id.LinearHorizontalFragment
    ),

    BTN_DEV_WIDGET_GRID_ITEM_VERTICAL(
        "Grid Vertical ItemDecoration", R.id.GridVerticalFragment
    ),

    BTN_DEV_WIDGET_GRID_ITEM_HORIZONTAL(
        "Grid Horizontal ItemDecoration", R.id.GridHorizontalFragment
    ),

    BTN_DEV_WIDGET_VIEW_ASSIST("View 填充辅助类"),

    BTN_DEV_WIDGET_VIEW_ASSIST_1_RECYCLER("RecyclerView ( loading )"),

    BTN_DEV_WIDGET_VIEW_ASSIST_1_ERROR("Error ( failed )"),

    BTN_DEV_WIDGET_VIEW_ASSIST_1_EMPTY("Empty ( data )"),

    BTN_DEV_WIDGET_VIEW_ASSIST_1_CUSTOM("Custom Type"),

    BTN_DEV_WIDGET_FLIP_CARD_VIEW("翻转卡片 View", R.id.FlipCardFragment),

    BTN_DEV_WIDGET_CORNER_LABEL_VIEW("自定义角标 View", R.id.CornerLabelFragment),

    BTN_DEV_WIDGET_WAVE_VIEW("波浪 View", R.id.WaveViewFragment),

    BTN_DEV_WIDGET_LIKE_VIEW("自定义点赞效果 View", R.id.FlowLikeFragment),

    BTN_DEV_WIDGET_WRAP_VIEW("自动换行 View", R.id.WrapViewFragment),

    BTN_DEV_WIDGET_SIGN_VIEW("签名 View", R.id.SignViewFragment),

    BTN_DEV_WIDGET_LINE_TEXT_VIEW("TextView 换行监听", R.id.LineTextFragment),

    // ===================
    // = DevAssist Engine =
    // ===================

    BTN_DEV_ASSIST_ENGINE_ANALYTICS("Analytics Engine 数据统计 ( 埋点 )"),

    BTN_DEV_ASSIST_ENGINE_BAR_CODE("BarCode Engine 条形码、二维码处理"),

    BTN_DEV_ASSIST_ENGINE_CACHE("Cache Engine 有效期键值对缓存"),

    BTN_DEV_ASSIST_ENGINE_IMAGE_COMPRESS("Image Compress Engine 图片压缩"),

    BTN_DEV_ASSIST_ENGINE_IMAGE("Image Engine 图片加载、下载、转格式等"),

    BTN_DEV_ASSIST_ENGINE_JSON("JSON Engine 映射"),

    BTN_DEV_ASSIST_ENGINE_KEYVALUE("KeyValue Engine 键值对存储"),

    BTN_DEV_ASSIST_ENGINE_LOG("Log Engine 日志打印"),

    BTN_DEV_ASSIST_ENGINE_MEDIA_SELECTOR("Media Selector Engine 多媒体资源选择"),

    BTN_DEV_ASSIST_ENGINE_PERMISSION("Permission Engine 权限申请"),

    BTN_DEV_ASSIST_ENGINE_PUSH("Push Engine 推送平台处理"),

    BTN_DEV_ASSIST_ENGINE_SHARE("Share Engine 分享平台处理"),

    BTN_DEV_ASSIST_ENGINE_STORAGE("Storage Engine 外部、内部文件存储"),

    BTN_DEV_ASSIST_ENGINE_TOAST("Toast Engine 吐司提示"),

    // =============
    // = UI Effect =
    // =============

    BTN_UI_EFFECT_UI_EFFECT("Shape、GradientDrawable 效果", R.id.ShapeGradientFragment),

    BTN_UI_EFFECT_TOAST_TINT("ToastTint ( 着色美化 Toast )", R.id.ToastTintFragment),

    BTN_UI_EFFECT_TEXT_CALC("计算字体宽度、高度", R.id.TextCalcFragment),

    BTN_UI_EFFECT_TEXTVIEW("两个 TextView 显示效果", R.id.TextViewFragment),

    BTN_UI_EFFECT_SKU("商品 SKU 组合封装实现", R.id.SKUFragment),

    BTN_UI_EFFECT_ADAPTER_EDITS("Adapter Item EditText 输入监听", R.id.MultiEditsFragment),

    BTN_UI_EFFECT_MULTI_SELECT("多选辅助类 MultiSelectAssist", R.id.MultiSelectFragment),

    BTN_UI_EFFECT_SHOP_CARD_ADD_ANIM("购物车加入动画", R.id.ShopCartFragment),

    BTN_UI_EFFECT_CAPTURE_PICTURE(
        "CapturePictureUtils 截图工具类", R.id.CapturePictureFragment
    ),

    BTN_UI_EFFECT_CAPTURE_PICTURE_1_LIST_VIEW(
        "CapturePictureUtils ListView 截图", R.id.CapturePictureListViewFragment
    ),

    BTN_UI_EFFECT_CAPTURE_PICTURE_1_RECYCLER_VIEW(
        "CapturePictureUtils RecyclerView 截图工具类", R.id.CapturePictureRecyclerViewFragment
    ),

    BTN_UI_EFFECT_CAPTURE_PICTURE_1_WEB_VIEW(
        "CapturePictureUtils WebView 截图工具类", R.id.CapturePictureWebViewFragment
    ),

    BTN_UI_EFFECT_GPU_ACV("GPU ACV 文件滤镜效果", R.id.GPUFilterACVFragment),

    BTN_UI_EFFECT_GPU_FILTER("GPU 滤镜效果", R.id.GPUFilterFragment),

    BTN_UI_EFFECT_QRCODE_SCAN("二维码扫描解析", R.id.QRCodeScanFragment),

    BTN_UI_EFFECT_PALETTE("Palette 调色板", R.id.PaletteFragment),

    BTN_UI_EFFECT_VIEWPAGER2("ViewPager2", R.id.ViewPager2Fragment),

    BTN_UI_EFFECT_MATERIAL_CHIP(
        "Material Chip、ChipGroups、ChipDrawable", R.id.ChipFragment
    ),

    BTN_UI_EFFECT_MATERIAL_SHAPEABLE_IMAGE_VIEW(
        "Material ShapeableImageView", R.id.ShapeableImageViewFragment
    ),

    BTN_UI_EFFECT_MATERIAL_BOTTOM_SHEET(
        "Material BottomSheet", R.id.BottomSheetFragment
    ),

    BTN_UI_EFFECT_MATERIAL_BOTTOM_SHEET_DIALOG(
        "Material BottomSheetDialog", R.id.BottomSheetDialogFragment
    ),

    BTN_UI_EFFECT_RECY_FLEXBOX("Flexbox LayoutManager", R.id.FlexboxFragment),

    BTN_UI_EFFECT_RECY_ITEM_STICKY("RecyclerView 吸附效果", R.id.ItemStickyFragment),

    BTN_UI_EFFECT_RECY_ITEM_SLIDE("RecyclerView 滑动删除、上下滑动", R.id.ItemSlideFragment),

    BTN_UI_EFFECT_RECY_LINEAR_SNAP("RecyclerView - LinearSnapHelper", R.id.LinearSnapFragment),

    BTN_UI_EFFECT_RECY_PAGER_SNAP("RecyclerView - PagerSnapHelper", R.id.PagerSnapFragment),

    BTN_UI_EFFECT_RECY_CONCAT_ADAPTER(
        "RecyclerView - ConcatAdapter", R.id.ConcatAdapterFragment
    ),

    BTN_UI_EFFECT_RECY_MULTITYPE_ADAPTER(
        "RecyclerView MultiType Adapter", R.id.MultiTypeAdapterFragment
    ),

    // ==================
    // = Other Function =
    // ==================

    BTN_OTHER_FUN_NOTIFICATION_SERVICE(
        "通知栏监听服务 ( NotificationService )", R.id.NotificationServiceFragment
    ),

    BTN_OTHER_FUN_ACCESSIBILITY_SERVICE(
        "无障碍监听服务 ( AccessibilityListenerService )", R.id.AccessibilityListenerServiceFragment
    ),

    BTN_OTHER_FUN_ACTIVITY_RESULT_API(
        "Activity Result API", R.id.ActivityResultAPIFragment
    ),

    BTN_OTHER_FUN_FUNCTION("铃声、震动、通知栏等功能", R.id.FunctionFragment),

    BTN_OTHER_FUN_TIMER("TimerManager 定时器工具类", R.id.DevTimerFragment),

    BTN_OTHER_FUN_CACHE("DevCache 缓存工具类", R.id.DevCacheFragment),

    BTN_OTHER_FUN_LOGGER("DevLogger 日志工具类", R.id.DevLoggerFragment),

    BTN_OTHER_FUN_FILE_RECORD("日志、异常文件记录保存", R.id.FileRecordFragment),

    BTN_OTHER_FUN_CRASH("奔溃日志捕获", R.id.CrashFragment),

    BTN_OTHER_FUN_PATH("路径信息", R.id.PathFragment),

    BTN_OTHER_FUN_WEBVIEW("WebView 辅助类", R.id.WebViewFragment),

    BTN_OTHER_FUN_FLOATING_WINDOW_MANAGER(
        "悬浮窗管理辅助类 ( 需权限 )", R.id.FloatingWindowManager2Fragment
    ),

    BTN_OTHER_FUN_FLOATING_WINDOW_MANAGER2(
        "悬浮窗管理辅助类 ( 无需权限依赖 Activity )", R.id.FloatingWindowManagerFragment
    ),
}