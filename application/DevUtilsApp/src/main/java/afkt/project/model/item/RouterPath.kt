package afkt.project.model.item

/**
 * detail: ARouter Path
 * @author Ttt
 */
object RouterPath {

    // =================
    // = 默认通用、根目录 =
    // =================

    const val EMPTY = ""

    const val ModuleActivity_PATH = "/activity/ModuleActivity"

    const val ButtonItemActivity_PATH = "/activity/ButtonItemActivity"

    // ==================
    // = 按功能模块列表循序 =
    // ==================

    /**
     * Framework 架构
     */
    object FRAMEWORK {

        const val ArticleMVPActivity_PATH = "/activity/ArticleMVPActivity"

        const val ArticleMVVMActivity_PATH = "/activity/ArticleMVVMActivity"
    }

    /**
     * Lib 框架
     */
    object LIB_FRAME {

        const val GreenDaoActivity_PATH = "/activity/GreenDaoActivity"

        const val RoomActivity_PATH = "/activity/RoomActivity"

        const val DataStoreActivity_PATH = "/activity/DataStoreActivity"
    }

    /**
     * UI 效果
     */
    object UI_EFFECT {

        const val ToastTintActivity_PATH = "/activity/ToastTintActivity"

        const val UIEffectActivity_PATH = "/activity/UIEffectActivity"

        const val StatusBarActivity_PATH = "/activity/StatusBarActivity"

        const val TextCalcActivity_PATH = "/activity/TextCalcActivity"

        const val AdapterEditsActivity_PATH = "/activity/AdapterEditsActivity"

        const val MultiSelectActivity_PATH = "/activity/MultiSelectActivity"

        const val GPUFilterACVActivity_PATH = "/activity/GPUFilterACVActivity"

        const val GPUFilterActivity_PATH = "/activity/GPUFilterActivity"

        const val QRCodeCreateActivity_PATH = "/activity/QRCodeCreateActivity"

        const val QRCodeImageActivity_PATH = "/activity/QRCodeImageActivity"

        const val QRCodeScanActivity_PATH = "/activity/QRCodeScanActivity"

        const val CapturePictureActivity_PATH = "/activity/CapturePictureActivity"

        const val CapturePictureListActivity_PATH = "/activity/CapturePictureListActivity"

        const val CapturePictureGridActivity_PATH = "/activity/CapturePictureGridActivity"

        const val CapturePictureRecyActivity_PATH = "/activity/CapturePictureRecyActivity"

        const val CapturePictureWebActivity_PATH = "/activity/CapturePictureWebActivity"

        const val TextViewActivity_PATH = "/activity/TextViewActivity"

        const val ItemStickyActivity_PATH = "/activity/ItemStickyActivity"

        const val RecyItemSlideActivity_PATH = "/activity/RecyItemSlideActivity"

        const val LinearSnapActivity_PATH = "/activity/LinearSnapActivity"

        const val LinearSnapMAXActivity_PATH = "/activity/LinearSnapMAXActivity"

        const val PagerSnapActivity_PATH = "/activity/PagerSnapActivity"

        const val PagerSnapMAXActivity_PATH = "/activity/PagerSnapMAXActivity"

        const val ShapeableImageViewActivity_PATH = "/activity/ShapeableImageViewActivity"

        const val BottomSheetActivity_PATH = "/activity/BottomSheetActivity"

        const val BottomSheetDialogActivity_PATH = "/activity/BottomSheetDialogActivity"

        const val PaletteActivity_PATH = "/activity/PaletteActivity"

        const val FlexboxLayoutManagerActivity_PATH = "/activity/FlexboxLayoutManagerActivity"

        const val ChipActivity_PATH = "/activity/ChipActivity"

        const val ViewPager2Activity_PATH = "/activity/ViewPager2Activity"

        const val RecyConcatAdapterActivity_PATH = "/activity/RecyConcatAdapterActivity"

        const val RecyMultiTypeAdapterActivity_PATH = "/activity/RecyMultiTypeAdapterActivity"

        const val ShopCartAddAnimActivity_PATH = "/activity/ShopCartAddAnimActivity"
    }

    /**
     * 其他功能
     */
    object OTHER_FUNCTION {

        const val ListenerActivity_PATH = "/activity/ListenerActivity"

        const val NotificationServiceActivity_PATH = "/activity/NotificationServiceActivity"

        const val AccessibilityListenerServiceActivity_PATH =
            "/activity/AccessibilityListenerServiceActivity"

        const val WifiActivity_PATH = "/activity/WifiActivity"

        const val FunctionActivity_PATH = "/activity/FunctionActivity"

        const val TimerActivity_PATH = "/activity/TimerActivity"

        const val CacheActivity_PATH = "/activity/CacheActivity"

        const val LoggerActivity_PATH = "/activity/LoggerActivity"

        const val FileRecordActivity_PATH = "/activity/FileRecordActivity"

        const val CrashCatchActivity_PATH = "/activity/CrashCatchActivity"

        const val PathActivity_PATH = "/activity/PathActivity"

        const val WebViewActivity_PATH = "/activity/WebViewActivity"

        const val ActivityResultAPIActivity_PATH = "/activity/ActivityResultAPIActivity"

        const val ActivityResultCallbackActivity_PATH = "/activity/ActivityResultCallbackActivity"

        const val AddContactActivity_PATH = "/activity/AddContactActivity"

        const val WallpaperActivity_PATH = "/activity/WallpaperActivity"

        const val FloatingWindowManagerActivity_PATH = "/activity/FloatingWindowManagerActivity"

        const val FloatingWindowManager2Activity_PATH = "/activity/FloatingWindowManager2Activity"
    }

    /**
     * DevWidget UI 库
     */
    object DEV_WIDGET {

        const val ViewPagerActivity_PATH = "/activity/ViewPagerActivity"

        const val ProgressBarActivity_PATH = "/activity/ProgressBarActivity"

        const val ScanShapeActivity_PATH = "/activity/ScanShapeActivity"

        const val WrapActivity_PATH = "/activity/WrapActivity"

        const val SignActivity_PATH = "/activity/SignActivity"

        const val LineActivity_PATH = "/activity/LineActivity"

        const val FlowLikeActivity_PATH = "/activity/FlowLikeActivity"

        const val CornerLabelActivity_PATH = "/activity/CornerLabelActivity"

        const val ViewAssistRecyclerViewLoadActivity_PATH =
            "/activity/ViewAssistRecyclerViewLoadActivity"

        const val ViewAssistActivity_PATH = "/activity/ViewAssistActivity"

        const val FlipCardActivity_PATH = "/activity/FlipCardActivity"

        const val WaveViewActivity_PATH = "/activity/WaveViewActivity"

        const val LinearColorItemDecorationActivity_PATH =
            "/activity/LinearColorItemDecorationActivity"

        const val GridColorItemDecorationActivity_PATH = "/activity/GridColorItemDecorationActivity"
    }

    /**
     * Dev Libs
     */
    object DEV_LIBS {

        const val DevEnvironmentLibActivity_PATH = "/activity/DevEnvironmentLibActivity"

        const val DevAssistEngineActivity_PATH = "/activity/DevAssistEngineActivity"

        const val DevHttpCaptureActivity_PATH = "/activity/DevHttpCaptureActivity"

        const val DevSKUActivity_PATH = "/activity/DevSKUActivity"
    }
}