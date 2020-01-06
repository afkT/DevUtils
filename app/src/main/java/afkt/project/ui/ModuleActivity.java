package afkt.project.ui;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.framework.mvp.ArticleMVPActivity;
import afkt.project.framework.mvvm.ArticleMVVMActivity;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.activity.AccessibilityListenerServiceActivity;
import afkt.project.ui.activity.ActivityResultCallBackActivity;
import afkt.project.ui.activity.AdapterEditsActivity;
import afkt.project.ui.activity.CacheActivity;
import afkt.project.ui.activity.CapturePictureActivity;
import afkt.project.ui.activity.CrashCatchActivity;
import afkt.project.ui.activity.DevExtendActivity;
import afkt.project.ui.activity.DeviceInfoActivity;
import afkt.project.ui.activity.EventBusActivity;
import afkt.project.ui.activity.FileRecordActivity;
import afkt.project.ui.activity.FunctionActivity;
import afkt.project.ui.activity.GPUFilterACVActivity;
import afkt.project.ui.activity.GPUFilterActivity;
import afkt.project.ui.activity.LineActivity;
import afkt.project.ui.activity.ListenerActivity;
import afkt.project.ui.activity.LoggerActivity;
import afkt.project.ui.activity.MultiSelectActivity;
import afkt.project.ui.activity.NotificationServiceActivity;
import afkt.project.ui.activity.PathActivity;
import afkt.project.ui.activity.ProgressBarActivity;
import afkt.project.ui.activity.QRCodeCreateActivity;
import afkt.project.ui.activity.QRCodeImageActivity;
import afkt.project.ui.activity.QRCodeScanActivity;
import afkt.project.ui.activity.ScanShapeActivity;
import afkt.project.ui.activity.ScreenInfoActivity;
import afkt.project.ui.activity.SignActivity;
import afkt.project.ui.activity.StatusBarActivity;
import afkt.project.ui.activity.TextCalcActivity;
import afkt.project.ui.activity.TextViewActivity;
import afkt.project.ui.activity.TimerActivity;
import afkt.project.ui.activity.ToastTintActivity;
import afkt.project.ui.activity.UIEffectActivity;
import afkt.project.ui.activity.ViewPagerActivity;
import afkt.project.ui.activity.WebViewActivity;
import afkt.project.ui.activity.WifiActivity;
import afkt.project.ui.activity.WrapActivity;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.util.SkipUtils;
import butterknife.BindView;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Module 列表 Activity
 * @author Ttt
 */
public class ModuleActivity extends BaseToolbarActivity {

    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValues() {
        super.initValues();
        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getModuleButtonValues(getModuleType()));
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {

                    // =============
                    // = Framework =
                    // =============

                    // 架构只是一种思维方式, 不管是MVC、MVP, 还是MVVM, 都只是一种思考问题解决问题的思维
                    // 其目的是要解决编程过程中, 模块内部高内聚、模块与模块之间低耦合、可维护性、易测试等问题

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
                        ToastTintUtils.info("具体请查看: lib\\DevOther\\other\\" + buttonValue.text);
                        break;
                    case ButtonValue.BTN_IMAGE_LOADER: // ImageLoaderUtils
                        ToastTintUtils.info("具体请查看: lib\\DevOther\\other\\" + buttonValue.text);
                        break;
                    case ButtonValue.BTN_GSON: // GsonUtils
                        ToastTintUtils.info("具体请查看: lib\\DevOther\\other\\" + buttonValue.text);
                        break;
                    case ButtonValue.BTN_FASTJSON: // FastjsonUtils
                        ToastTintUtils.info("具体请查看: lib\\DevOther\\other\\" + buttonValue.text);
                        break;
                    case ButtonValue.BTN_ZXING: // ZXingQRCodeUtils
                        ToastTintUtils.info("具体请查看: lib\\DevOther\\other\\" + buttonValue.text);
                        break;
                    case ButtonValue.BTN_PICTURE_SELECTOR: // PictureSelectorUtils
                        ToastTintUtils.info("具体请查看: lib\\DevOther\\other\\" + buttonValue.text);
                        break;

                    // ======
                    // = UI =
                    // ======

                    case ButtonValue.BTN_TOAST_TINT: // ToastTint ( 着色美化 Toast )
                        SkipUtils.startActivity(ToastTintActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_CUSTOM_PROGRESS_BAR: // 自定义 ProgressBar 样式 View
                        SkipUtils.startActivity(ProgressBarActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_UI_EFFECT: // 常见 UI、GradientDrawable 效果等
                        SkipUtils.startActivity(UIEffectActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_VIEW_PAGER: // ViewPager 滑动监听、控制滑动
                        SkipUtils.startActivity(ViewPagerActivity.class, buttonValue);
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
                    case ButtonValue.BTN_SCAN_VIEW: // 自定义扫描 View ( QRCode、AR )
                        SkipUtils.startActivity(ScanShapeActivity.class, buttonValue);
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
                    case ButtonValue.BTN_WRAP_VIEW: // 自动换行 View
                        SkipUtils.startActivity(WrapActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_SIGN_VIEW: // 签名 View
                        SkipUtils.startActivity(SignActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_LINE_VIEW: // 换行监听 View
                        SkipUtils.startActivity(LineActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_CAPTURE_PICTURE: // CapturePictureUtils 截图工具类
                        SkipUtils.startActivity(CapturePictureActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_TEXTVIEW: // 两个 TextView 显示效果
                        SkipUtils.startActivity(TextViewActivity.class, buttonValue);
                        break;

                    // ============
                    // = 其他功能 =
                    // ============

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
                    case ButtonValue.BTN_EXTEND: // 通用结果回调类 ( 针对 DevResultCallback 进行扩展 )
                        SkipUtils.startActivity(DevExtendActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_DEVICE_INFO: // 设备信息
                        SkipUtils.startActivity(DeviceInfoActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_SCREEN_INFO: // 屏幕信息
                        SkipUtils.startActivity(ScreenInfoActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_PATH: // 路径信息
                        SkipUtils.startActivity(PathActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_WEBVIEW: // WebView 辅助类
                        SkipUtils.startActivity(WebViewActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_ACTIVITY_RESULT_CALLBACK: // startActivityForResult CallBack
                        SkipUtils.startActivity(ActivityResultCallBackActivity.class, buttonValue);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
        // 注册观察者
        registerAdapterDataObserver(vid_bvr_recy, true);
    }
}