package afkt.project.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import dev.utils.app.toast.ToastTintUtils;
import utils_use.logger.LoggerUse;

/**
 * detail: DevLogger 日志工具类
 * @author Ttt
 * <pre>
 *     {@link LoggerUse}
 * </pre>
 */
public class LoggerActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();

//        // = 在 BaseApplication 中调用 =
//        // 初始化日志配置
//        final LogConfig logConfig = new LogConfig();
//        // 堆栈方法总数 ( 显示经过的方法 )
//        logConfig.methodCount = 3;
//        // 堆栈方法索引偏移 (0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息 )
//        logConfig.methodOffset = 0;
//        // 是否输出全部方法 ( 在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数 )
//        logConfig.outputMethodAll = false;
//        // 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
//        logConfig.displayThreadInfo = false;
//        // 是否排序日志 ( 格式化后 )
//        logConfig.sortLog = false; // 是否美化日志, 边框包围
//        // 日志级别
//        logConfig.logLevel = LogLevel.DEBUG;
//        // 设置 TAG ( 特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下 )
//        logConfig.tag = "BaseLog";
//        // 进行初始化配置, 这样设置后, 默认全部日志都使用改配置, 特殊使用 DevLogEngine.getEngine().other(config).d(xxx);
//        DevLogEngine.getEngine().init(logConfig);

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getLoggerButtonValues());
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
                    case ButtonValue.BTN_LOGGER_PRINT:
                        showToast(true, "打印成功, 请查看 Logcat");
                        LoggerUse.tempLog();
                        break;
                    case ButtonValue.BTN_LOGGER_TIME:
                        showToast(true, "打印成功, 请查看 Logcat");
                        LoggerUse.testTime();
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }
}