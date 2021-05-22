package afkt.project.ui

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.framework.mvp.ArticleMVPActivity
import afkt.project.framework.mvvm.ArticleMVVMActivity
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.ui.activity.*
import afkt.project.ui.adapter.ButtonAdapter
import afkt.project.util.SkipUtils.startActivity
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: Module 列表 Activity
 * @author Ttt
 */
@Route(path = RouterPath.ModuleActivity_PATH)
class ModuleActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()
        // 初始化布局管理器、适配器
        val buttonAdapter = ButtonAdapter(ButtonList.getModuleButtonValues(moduleType))
        binding.vidBvrRecy.adapter = buttonAdapter
        buttonAdapter.itemCallback = object : DevItemClickCallback<ButtonValue>() {
            override fun onItemClick(
                buttonValue: ButtonValue,
                param: Int
            ) {
                when (buttonValue.type) {
                    ButtonValue.BTN_MVP -> startActivity(
                        ArticleMVPActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_MVVM -> startActivity(
                        ArticleMVVMActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_EVENT_BUS -> startActivity(
                        EventBusActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_GLIDE,
                    ButtonValue.BTN_IMAGE_LOADER,
                    ButtonValue.BTN_GSON,
                    ButtonValue.BTN_FASTJSON,
                    ButtonValue.BTN_ZXING,
                    ButtonValue.BTN_PICTURE_SELECTOR,
                    ButtonValue.BTN_OKGO,
                    ButtonValue.BTN_LUBAN,
                    ButtonValue.BTN_MMKV,
                    ButtonValue.BTN_DATA_STORE,
                    ButtonValue.BTN_WORK_MANAGER -> ToastTintUtils.info(
                        "具体请查看: lib\\DevOther\\other\\" + buttonValue.text
                    )
                    ButtonValue.BTN_GREEN_DAO -> startActivity(
                        GreenDaoActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_ROOM -> startActivity(
                        RoomActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_TOAST_TINT -> startActivity(
                        ToastTintActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_UI_EFFECT -> startActivity(
                        UIEffectActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_STATUS_BAR -> startActivity(
                        StatusBarActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_TEXT_CALC -> startActivity(
                        TextCalcActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_ADAPTER_EDITS -> startActivity(
                        AdapterEditsActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_MULTI_SELECT -> startActivity(
                        MultiSelectActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_GPU_ACV -> startActivity(
                        GPUFilterACVActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_GPU_FILTER -> startActivity(
                        GPUFilterActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_QRCODE_CREATE -> startActivity(
                        QRCodeCreateActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_QRCODE_IMAGE -> startActivity(
                        QRCodeImageActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_QRCODE_SCAN -> startActivity(
                        QRCodeScanActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_CAPTURE_PICTURE -> startActivity(
                        CapturePictureActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_TEXTVIEW -> startActivity(
                        TextViewActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_ITEM_STICKY -> startActivity(
                        ItemStickyActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_RECY_ITEM_SLIDE -> startActivity(
                        RecyItemSlideActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_RECY_LINEAR_SNAP -> startActivity(
                        LinearSnapActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_RECY_LINEAR_SNAP_MAX -> startActivity(
                        LinearSnapMAXActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_RECY_PAGER_SNAP -> startActivity(
                        PagerSnapActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_RECY_PAGER_SNAP_MAX -> startActivity(
                        PagerSnapMAXActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_SHAPEABLE_IMAGE_VIEW -> startActivity(
                        ShapeableImageViewActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_BOTTOM_SHEET -> startActivity(
                        BottomSheetActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_BOTTOM_SHEET_DIALOG -> startActivity(
                        BottomSheetDialogActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_PALETTE -> startActivity(
                        PaletteActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_FLEXBOX_LAYOUTMANAGER -> startActivity(
                        FlexboxLayoutManagerActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_CHIP -> startActivity(
                        ChipActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_VIEWPAGER2 -> startActivity(
                        ViewPager2Activity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_LISTENER -> startActivity(
                        ListenerActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_NOTIFICATION_SERVICE -> startActivity(
                        NotificationServiceActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_ACCESSIBILITY_SERVICE -> startActivity(
                        AccessibilityListenerServiceActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_WIFI -> startActivity(
                        WifiActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_FUNCTION -> startActivity(
                        FunctionActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_TIMER -> startActivity(
                        TimerActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_CACHE -> startActivity(
                        CacheActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_LOGGER -> startActivity(
                        LoggerActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_FILE_RECORD -> startActivity(
                        FileRecordActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_CRASH -> startActivity(
                        CrashCatchActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_PATH -> startActivity(
                        PathActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_WEBVIEW -> startActivity(
                        WebViewActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_ACTIVITY_RESULT_CALLBACK -> startActivity(
                        ActivityResultCallbackActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_ADD_CONTACT -> startActivity(
                        AddContactActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_WALLPAPER -> startActivity(
                        WallpaperActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_VIEW_PAGER -> startActivity(
                        ViewPagerActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_CUSTOM_PROGRESS_BAR -> startActivity(
                        ProgressBarActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_SCAN_VIEW -> startActivity(
                        ScanShapeActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_WRAP_VIEW -> startActivity(
                        WrapActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_SIGN_VIEW -> startActivity(
                        SignActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_LINE_VIEW -> startActivity(
                        LineActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_LIKE_VIEW -> startActivity(
                        FlowLikeActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_CORNER_LABEL_VIEW -> startActivity(
                        CornerLabelActivity::class.java,
                        buttonValue
                    )
                    ButtonValue.BTN_VIEW_ASSIST -> startActivity(
                        ButtonItemActivity::class.java,
                        buttonValue
                    )
                    else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                }
            }
        }
        // 注册观察者
        registerAdapterDataObserver(binding.vidBvrRecy, true)
    }
}