package afkt.project.ui.activity;

import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import dev.engine.log.DevLogEngine;
import dev.utils.app.HandlerUtils;
import dev.utils.app.timer.DevTimer;
import dev.utils.app.timer.TimerManager;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: TimerManager 定时器工具类
 * @author Ttt
 * <pre>
 *     {@link TimerUse}
 * </pre>
 */
public class TimerActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    // UI Handler
    private Handler  mUiHandler = new Handler();
    // 定时器
    private DevTimer mTimer;

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getTimerButtonValues());
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                // 获取操作结果
                boolean result;

                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_TIMER_START:
                        if (mTimer == null) {
                            // 初始化定时器
                            mTimer = new DevTimer.Builder(500L, 2000L,
                                    -1, "GET").build();
                            mTimer.setHandler(mUiHandler) // 设置回调通过 Handler 触发
                                    .setCallback((timer, number, end, infinite) -> {
                                        // 触发次数
                                        if (number == 1) {
                                            DevLogEngine.getEngine().dTag(TAG, "第一次触发, 0.5 秒延迟");
                                        } else {
                                            DevLogEngine.getEngine().dTag(TAG, "每隔 2 秒触发一次, 触发次数: %s", number);
                                        }
                                    });
                        }
                        if (mTimer.isRunning()) {
                            showToast(false, "定时器已经启动, 请查看 Logcat");
                        } else {
                            showToast(true, "定时器启动成功, 请查看 Logcat");
                            // 运行定时器
                            mTimer.start();
                        }
                        break;
                    case ButtonValue.BTN_TIMER_STOP:
                        result = (mTimer != null && mTimer.isRunning());
                        showToast(result, "定时器关闭成功", "定时器未启动");
                        if (result) mTimer.stop();
                        // 回收定时器
                        TimerManager.recycle();
                        break;
                    case ButtonValue.BTN_TIMER_RESTART:
                        if (mTimer == null) {
                            showToast(false, "请先初始化定时器");
                            return;
                        }
                        showToast(true, "定时器启动成功, 请查看 Logcat");
                        // 运行定时器
                        mTimer.start();
                        break;
                    case ButtonValue.BTN_TIMER_CHECK:
                        result = (mTimer != null && mTimer.isRunning());
                        showToast(result, "定时器已启动", "定时器未启动");
                        break;
                    case ButtonValue.BTN_TIMER_GET:
                        DevTimer timerGet = TimerManager.getTimer("GET");
                        showToast(timerGet != null, "获取定时器成功", "暂无该定时器");
                        break;
                    case ButtonValue.BTN_TIMER_GET_NUMBER:
                        result = (mTimer != null && mTimer.isRunning());
                        showToast(result, "定时器运行次数: " + mTimer.getTriggerNumber(), "定时器未启动");
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();

        DevTimer timer = new DevTimer.Builder(1500L)
                .setDelay(100L) // 延迟时间 ( 多少毫秒后开始执行 )
                .setPeriod(1500L) // 循环时间 ( 每隔多少毫秒执行一次 )
                .setTag("TAG") // 定时器 Tag
                .setLimit(19) // 触发次数上限 ( 负数为无限循环 )
                .build(); // 构建定时器
        timer.setCallback(new DevTimer.Callback() {
            @Override
            public void callback(
                    DevTimer timer,
                    int number,
                    boolean end,
                    boolean infinite
            ) {
                DevLogEngine.getEngine().dTag(TAG, "是否 UI 线程: %s", HandlerUtils.isMainThread());
            }
        });
        // 设置了 Handler 则属于 UI 线程触发回调
        timer.setHandler(mUiHandler);
        // 运行定时器
        timer.start();
        // 关闭定时器
        timer.stop();

        int uuid = 0;
        // 关闭所有对应 UUID 定时器
        TimerManager.closeAllUUID(uuid);
        // 关闭所有对应 TAG 定时器
        TimerManager.closeAllTag("TAG");
        // 关闭所有无限循环的定时器
        TimerManager.closeAllInfinite();
        // 关闭所有未运行的定时器
        TimerManager.closeAllNotRunning();
        // 关闭全部定时器
        TimerManager.closeAll();
        // 回收定时器资源
        TimerManager.recycle();
        // 获取全部定时器总数
        TimerManager.getSize();
        // 获取对应 UUID 定时器 ( 优先获取符合的 )
        TimerManager.getTimer(uuid);
        // 获取对应 TAG 定时器 ( 优先获取符合的 )
        TimerManager.getTimer("TAG");
        // 获取对应 UUID 定时器集合
        TimerManager.getTimers(uuid);
        // 获取对应 TAG 定时器集合
        TimerManager.getTimers("TAG");
    }
}