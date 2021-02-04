package afkt.project.ui;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.framework.mvp.ArticleMVPActivity;
import afkt.project.framework.mvvm.ArticleMVVMActivity;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.activity.AccessibilityListenerServiceActivity;
import afkt.project.ui.activity.ActivityResultCallbackActivity;
import afkt.project.ui.activity.AdapterEditsActivity;
import afkt.project.ui.activity.AddContactActivity;
import afkt.project.ui.activity.BottomSheetActivity;
import afkt.project.ui.activity.BottomSheetDialogActivity;
import afkt.project.ui.activity.CacheActivity;
import afkt.project.ui.activity.CapturePictureActivity;
import afkt.project.ui.activity.ChipActivity;
import afkt.project.ui.activity.CornerLabelActivity;
import afkt.project.ui.activity.CrashCatchActivity;
import afkt.project.ui.activity.EventBusActivity;
import afkt.project.ui.activity.FileRecordActivity;
import afkt.project.ui.activity.FlexboxLayoutManagerActivity;
import afkt.project.ui.activity.FlowLikeActivity;
import afkt.project.ui.activity.FunctionActivity;
import afkt.project.ui.activity.GPUFilterACVActivity;
import afkt.project.ui.activity.GPUFilterActivity;
import afkt.project.ui.activity.GreenDaoActivity;
import afkt.project.ui.activity.ItemStickyActivity;
import afkt.project.ui.activity.LineActivity;
import afkt.project.ui.activity.LinearSnapActivity;
import afkt.project.ui.activity.LinearSnapMAXActivity;
import afkt.project.ui.activity.ListenerActivity;
import afkt.project.ui.activity.LoggerActivity;
import afkt.project.ui.activity.MultiSelectActivity;
import afkt.project.ui.activity.NotificationServiceActivity;
import afkt.project.ui.activity.PagerSnapActivity;
import afkt.project.ui.activity.PagerSnapMAXActivity;
import afkt.project.ui.activity.PaletteActivity;
import afkt.project.ui.activity.PathActivity;
import afkt.project.ui.activity.ProgressBarActivity;
import afkt.project.ui.activity.QRCodeCreateActivity;
import afkt.project.ui.activity.QRCodeImageActivity;
import afkt.project.ui.activity.QRCodeScanActivity;
import afkt.project.ui.activity.RecyItemSlideActivity;
import afkt.project.ui.activity.RoomActivity;
import afkt.project.ui.activity.ScanShapeActivity;
import afkt.project.ui.activity.ShapeableImageViewActivity;
import afkt.project.ui.activity.SignActivity;
import afkt.project.ui.activity.StatusBarActivity;
import afkt.project.ui.activity.TextCalcActivity;
import afkt.project.ui.activity.TextViewActivity;
import afkt.project.ui.activity.TimerActivity;
import afkt.project.ui.activity.ToastTintActivity;
import afkt.project.ui.activity.UIEffectActivity;
import afkt.project.ui.activity.ViewPager2Activity;
import afkt.project.ui.activity.ViewPagerActivity;
import afkt.project.ui.activity.WallpaperActivity;
import afkt.project.ui.activity.WebViewActivity;
import afkt.project.ui.activity.WifiActivity;
import afkt.project.ui.activity.WrapActivity;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.util.SkipUtils;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Module 列表 Activity
 * @author Ttt
 */
public class ModuleActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();
        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getModuleButtonValues(getModuleType()));
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {

                    // =============
                    // = Framework =
                    // =============

                    case ButtonValue.BTN_MVP: // MVP
                        SkipUtils.startActivity(ArticleMVPActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_MVVM: // MVVM
                        SkipUtils.startActivity(ArticleMVVMActivity.class, buttonValue);
                        break;

                    // =======
                    // = Lib =
                    // =======

                    case ButtonValue.BTN_EVENT_BUS: // EventBusUtils
                        SkipUtils.startActivity(EventBusActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_GLIDE: // GlideUtils
                    case ButtonValue.BTN_IMAGE_LOADER: // ImageLoaderUtils
                    case ButtonValue.BTN_GSON: // GsonUtils
                    case ButtonValue.BTN_FASTJSON: // FastjsonUtils
                    case ButtonValue.BTN_ZXING: // ZXingQRCodeUtils
                    case ButtonValue.BTN_PICTURE_SELECTOR: // PictureSelectorUtils
                    case ButtonValue.BTN_OKGO: // OkGoUtils
                    case ButtonValue.BTN_LUBAN: // LubanUtils
                    case ButtonValue.BTN_MMKV: // MMKVUtils
                    case ButtonValue.BTN_DATA_STORE: // DataStore
                    case ButtonValue.BTN_WORK_MANAGER: // WorkManagerUtils
                        ToastTintUtils.info("具体请查看: lib\\DevOther\\other\\" + buttonValue.text);
                        break;
                    case ButtonValue.BTN_GREEN_DAO: // GreenDAO
                        SkipUtils.startActivity(GreenDaoActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_ROOM: // Room
                        SkipUtils.startActivity(RoomActivity.class, buttonValue);
                        break;

                    // ======
                    // = UI =
                    // ======

                    case ButtonValue.BTN_TOAST_TINT: // ToastTint ( 着色美化 Toast )
                        SkipUtils.startActivity(ToastTintActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_UI_EFFECT: // 常见 UI、GradientDrawable 效果等
                        SkipUtils.startActivity(UIEffectActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_STATUS_BAR: // 点击 显示 / 隐藏 ( 状态栏 )
                        SkipUtils.startActivity(StatusBarActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_TEXT_CALC: // 计算字体宽度、高度
                        SkipUtils.startActivity(TextCalcActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_ADAPTER_EDITS: // Adapter Item EditText 输入监听
                        SkipUtils.startActivity(AdapterEditsActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_MULTI_SELECT: // 多选辅助类 MultiSelectAssist
                        SkipUtils.startActivity(MultiSelectActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_GPU_ACV: // GPU ACV 文件滤镜效果
                        SkipUtils.startActivity(GPUFilterACVActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_GPU_FILTER: // GPU 滤镜效果
                        SkipUtils.startActivity(GPUFilterActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_QRCODE_CREATE: // 创建二维码
                        SkipUtils.startActivity(QRCodeCreateActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_QRCODE_IMAGE: // 二维码图片解析
                        SkipUtils.startActivity(QRCodeImageActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_QRCODE_SCAN: // 二维码扫描解析
                        SkipUtils.startActivity(QRCodeScanActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_CAPTURE_PICTURE: // CapturePictureUtils 截图工具类
                        SkipUtils.startActivity(CapturePictureActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_TEXTVIEW: // 两个 TextView 显示效果
                        SkipUtils.startActivity(TextViewActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_ITEM_STICKY: // RecyclerView 吸附效果
                        SkipUtils.startActivity(ItemStickyActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_RECY_ITEM_SLIDE: // RecyclerView 滑动删除、上下滑动
                        SkipUtils.startActivity(RecyItemSlideActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_RECY_LINEAR_SNAP: // LinearSnapHelper - RecyclerView
                        SkipUtils.startActivity(LinearSnapActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_RECY_LINEAR_SNAP_MAX: // LinearSnapHelper - 无限滑动
                        SkipUtils.startActivity(LinearSnapMAXActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_RECY_PAGER_SNAP: // PagerSnapHelper - RecyclerView
                        SkipUtils.startActivity(PagerSnapActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_RECY_PAGER_SNAP_MAX: // PagerSnapHelper - 无限滑动
                        SkipUtils.startActivity(PagerSnapMAXActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_SHAPEABLE_IMAGE_VIEW: // Material ShapeableImageView
                        SkipUtils.startActivity(ShapeableImageViewActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_BOTTOM_SHEET: // Material BottomSheet
                        SkipUtils.startActivity(BottomSheetActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_BOTTOM_SHEET_DIALOG: // Material BottomSheetDialog
                        SkipUtils.startActivity(BottomSheetDialogActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_PALETTE: // Palette 调色板
                        SkipUtils.startActivity(PaletteActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_FLEXBOX_LAYOUTMANAGER: // Flexbox LayoutManager
                        SkipUtils.startActivity(FlexboxLayoutManagerActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_CHIP: // Material Chip、ChipGroups、ChipDrawable
                        SkipUtils.startActivity(ChipActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_VIEWPAGER2: // ViewPager2
                        SkipUtils.startActivity(ViewPager2Activity.class, buttonValue);
                        break;

                    // ===========
                    // = 其他功能 =
                    // ===========

                    case ButtonValue.BTN_LISTENER: // 事件 / 广播监听 ( 网络状态、屏幕旋转等 )
                        SkipUtils.startActivity(ListenerActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_NOTIFICATION_SERVICE: // 通知栏监听服务 ( NotificationService )
                        SkipUtils.startActivity(NotificationServiceActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_ACCESSIBILITY_SERVICE: // 无障碍监听服务 ( AccessibilityListenerService )
                        SkipUtils.startActivity(AccessibilityListenerServiceActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_WIFI: // Wifi 相关 ( 热点 )
                        SkipUtils.startActivity(WifiActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_FUNCTION: // 铃声、震动、通知栏等功能
                        SkipUtils.startActivity(FunctionActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_TIMER: // TimerManager 定时器工具类
                        SkipUtils.startActivity(TimerActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_CACHE: // DevCache 缓存工具类
                        SkipUtils.startActivity(CacheActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_LOGGER: // DevLogger 日志工具类
                        SkipUtils.startActivity(LoggerActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_FILE_RECORD: // 日志、异常文件记录保存
                        SkipUtils.startActivity(FileRecordActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_CRASH: // 奔溃日志捕获
                        SkipUtils.startActivity(CrashCatchActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_PATH: // 路径信息
                        SkipUtils.startActivity(PathActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_WEBVIEW: // WebView 辅助类
                        SkipUtils.startActivity(WebViewActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_ACTIVITY_RESULT_CALLBACK: // startActivityForResult Callback
                        SkipUtils.startActivity(ActivityResultCallbackActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_ADD_CONTACT: // 添加联系人
                        SkipUtils.startActivity(AddContactActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_WALLPAPER: // 手机壁纸
                        SkipUtils.startActivity(WallpaperActivity.class, buttonValue);
                        break;

                    // =============
                    // = DevWidget =
                    // =============

                    case ButtonValue.BTN_VIEW_PAGER: // ViewPager 滑动监听、控制滑动
                        SkipUtils.startActivity(ViewPagerActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_CUSTOM_PROGRESS_BAR: // 自定义 ProgressBar 样式 View
                        SkipUtils.startActivity(ProgressBarActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_SCAN_VIEW: // 自定义扫描 View ( QRCode、AR )
                        SkipUtils.startActivity(ScanShapeActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_WRAP_VIEW: // 自动换行 View
                        SkipUtils.startActivity(WrapActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_SIGN_VIEW: // 签名 View
                        SkipUtils.startActivity(SignActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_LINE_VIEW: // 换行监听 View
                        SkipUtils.startActivity(LineActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_LIKE_VIEW: // 自定义点赞效果 View
                        SkipUtils.startActivity(FlowLikeActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_CORNER_LABEL_VIEW: // 自定义角标 View
                        SkipUtils.startActivity(CornerLabelActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_VIEW_ASSIST: // View 填充辅助类
                        SkipUtils.startActivity(ButtonItemActivity.class, buttonValue);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
        // 注册观察者
        registerAdapterDataObserver(binding.vidBvrRecy, true);
    }
}