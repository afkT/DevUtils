package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ButtonAdapter
import afkt.project.model.item.ButtonList.timerButtonValues
import afkt.project.model.item.ButtonValue
import android.os.Handler
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.engine.DevEngine
import dev.utils.app.HandlerUtils
import dev.utils.app.timer.DevTimer
import dev.utils.app.timer.TimerManager
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: TimerManager 定时器工具类
 * @author Ttt
 */
@Route(path = RouterPath.TimerActivity_PATH)
class TimerActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    // UI Handler
    private val mUiHandler = Handler()

    // 定时器
    private var mTimer: DevTimer? = null

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        ButtonAdapter(timerButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    // 获取操作结果
                    val result: Boolean
                    when (buttonValue.type) {
                        ButtonValue.BTN_TIMER_START -> {
                            if (mTimer == null) {
                                // 初始化定时器
                                mTimer = DevTimer.Builder(
                                    500L, 2000L,
                                    -1, TAG
                                ).build()
                                // 设置回调通过 Handler 触发
                                mTimer?.apply {
                                    setHandler(mUiHandler)
                                    setCallback { timer: DevTimer?, number: Int, end: Boolean, infinite: Boolean ->
                                        // 触发次数
                                        if (number == 1) {
                                            DevEngine.getLog()?.dTag(TAG, "第一次触发, 0.5 秒延迟")
                                        } else {
                                            DevEngine.getLog()
                                                .dTag(TAG, "每隔 2 秒触发一次, 触发次数: %s", number)
                                        }
                                    }
                                }
                            }
                            mTimer?.apply {
                                if (isRunning) {
                                    showToast(false, "定时器已经启动, 请查看 Logcat")
                                } else {
                                    showToast(true, "定时器启动成功, 请查看 Logcat")
                                    // 运行定时器
                                    start()
                                }
                            }
                        }
                        ButtonValue.BTN_TIMER_STOP -> {
                            result = mTimer?.isRunning ?: false
                            showToast(result, "定时器关闭成功", "定时器未启动")
                            if (result) mTimer?.stop()
                            // 回收定时器
                            TimerManager.recycle()
                        }
                        ButtonValue.BTN_TIMER_RESTART -> {
                            mTimer?.let {
                                showToast(true, "定时器启动成功, 请查看 Logcat")
                                // 运行定时器
                                it.start()
                                return
                            }
                            showToast(false, "请先初始化定时器")
                        }
                        ButtonValue.BTN_TIMER_CHECK -> {
                            result = mTimer?.isRunning ?: false
                            showToast(result, "定时器已启动", "定时器未启动")
                        }
                        ButtonValue.BTN_TIMER_GET -> {
                            val timerTAG = TimerManager.getTimer(TAG)
                            showToast(timerTAG != null, "获取定时器成功", "暂无该定时器")
                        }
                        ButtonValue.BTN_TIMER_GET_NUMBER -> {
                            result = mTimer?.isRunning ?: false
                            showToast(result, "定时器运行次数: ${mTimer?.triggerNumber}", "定时器未启动")
                        }
                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }).bindAdapter(binding.vidRv)
    }

    override fun initListener() {
        super.initListener()
        val timer = DevTimer.Builder(1500L)
            .setDelay(100L)     // 延迟时间 ( 多少毫秒后开始执行 )
            .setPeriod(1500L)   // 循环时间 ( 每隔多少毫秒执行一次 )
            .setTag(TAG)        // 定时器 TAG
            .setLimit(19)       // 触发次数上限 ( 负数为无限循环 )
            .build()            // 构建定时器
        timer.setCallback { _, _, _, _ ->
            DevEngine.getLog()?.dTag(TAG, "是否 UI 线程: %s", HandlerUtils.isMainThread())
        }
        // 设置了 Handler 则属于 UI 线程触发回调
        timer.setHandler(mUiHandler)
        // 运行定时器
        timer.start()
        // 关闭定时器
        timer.stop()

        val uuid = 0
        // 关闭所有对应 UUID 定时器
        TimerManager.closeAllUUID(uuid)
        // 关闭所有对应 TAG 定时器
        TimerManager.closeAllTag(TAG)
        // 关闭所有无限循环的定时器
        TimerManager.closeAllInfinite()
        // 关闭所有未运行的定时器
        TimerManager.closeAllNotRunning()
        // 关闭全部定时器
        TimerManager.closeAll()
        // 回收定时器资源
        TimerManager.recycle()
        // 获取全部定时器总数
        TimerManager.getSize()
        // 获取对应 UUID 定时器 ( 优先获取符合的 )
        TimerManager.getTimer(uuid)
        // 获取对应 TAG 定时器 ( 优先获取符合的 )
        TimerManager.getTimer(TAG)
        // 获取对应 UUID 定时器集合
        TimerManager.getTimers(uuid)
        // 获取对应 TAG 定时器集合
        TimerManager.getTimers(TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 关闭所有对应 TAG 定时器
        TimerManager.closeAllTag(TAG)
    }
}