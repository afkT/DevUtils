package afkt.project.features.other_function

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionDevTimerBinding
import android.os.Handler
import android.os.Looper
import android.view.View
import dev.engine.extensions.log.log_dTag
import dev.engine.extensions.toast.toast_showShort
import dev.utils.app.HandlerUtils
import dev.utils.app.timer.DevTimer
import dev.utils.app.timer.TimerManager

/**
 * detail: TimerManager 定时器工具类
 * @author Ttt
 */
class DevTimerFragment : AppFragment<FragmentOtherFunctionDevTimerBinding, DevTimerViewModel>(
    R.layout.fragment_other_function_dev_timer, BR.viewModel
) {
    override fun onDestroy() {
        super.onDestroy()
        // 关闭所有对应 TAG 定时器
        TimerManager.closeAllTag(TAG)
    }
}

class DevTimerViewModel : AppViewModel() {

    // 快捷【创建】定时器
    private val quickTimer: DevTimer by lazy {
        /**
         * 0.5 秒 触发第一次，接着每隔 2 秒触发一次，-1 表示触发次数为无限次触发
         * 如果不需要存入 [TimerManager] 则可不传入 TAG
         */
        DevTimer.Builder(
            500L, 2000L,
            -1, TAG
        ).build().setHandler(HandlerUtils.getMainHandler())
            .setCallback { timer, number, end, infinite ->
                /**
                 * 触发回调方法
                 * @param timer    定时器
                 * @param number   触发次数
                 * @param end      是否结束
                 * @param infinite 是否无限循环
                 */
                TAG.log_dTag(message = "触发次数: $number")
            }
    }

    // UI Handler
    private val mUiHandler = Handler(Looper.getMainLooper())

    /**
     * API 使用方法
     */
    private fun use() {
        val timer = DevTimer.Builder(1500L)
            .setDelay(100L)     // 延迟时间 ( 多少毫秒后开始执行 )
            .setPeriod(1500L)   // 循环时间 ( 每隔多少毫秒执行一次 )
            .setTag(TAG)        // 定时器 TAG
            .setLimit(19)       // 触发次数上限 ( 负数为无限循环 )
            .build()            // 构建定时器
        timer.setCallback { _, _, _, _ ->
            TAG.log_dTag(
                message = "是否 UI 线程: ${HandlerUtils.isMainThread()}"
            )
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

    // 定时器
    private var mTimer: DevTimer? = null

    // 启动定时器
    val clickStart = View.OnClickListener { view ->
        if (mTimer == null) {
            // 初始化定时器
            mTimer = DevTimer.Builder(
                500L, 2000L, -1, TAG
            ).build().apply {
                // 设置回调通过 Handler 触发
                setHandler(mUiHandler)
                // 设置回调事件
                setCallback { timer: DevTimer?, number: Int, end: Boolean, infinite: Boolean ->
                    // 触发次数
                    if (number == 1) {
                        TAG.log_dTag(
                            message = "第一次触发, 0.5 秒延迟"
                        )
                    } else {
                        TAG.log_dTag(
                            message = "每隔 2 秒触发一次, 触发次数: $number"
                        )
                    }
                }
            }
        }
        mTimer?.apply {
            if (isRunning) {
                toast_showShort(text = "定时器已经启动, 请查看 Logcat")
            } else {
                toast_showShort(text = "定时器启动成功, 请查看 Logcat")
                // 运行定时器
                start()
            }
        }
    }

    // ==========
    // = 点击事件 =
    // ==========

    // 停止定时器
    val clickStop = View.OnClickListener { view ->
        val result = mTimer?.isRunning ?: false
        if (result) {
            toast_showShort(text = "定时器关闭成功")
            // 关闭定时器
            mTimer?.stop()
//            // 回收定时器【可不调用】
//            TimerManager.recycle()
        } else {
            toast_showShort(text = "定时器未启动")
        }
    }

    // 重启定时器
    val clickReStart = View.OnClickListener { view ->
        if (mTimer != null) {
            toast_showShort(text = "定时器启动成功, 请查看 Logcat")
            // 运行定时器
            mTimer?.start()
            return@OnClickListener
        }
        toast_showShort(text = "请先初始化定时器")
    }

    // 定时器是否启动
    val clickCheck = View.OnClickListener { view ->
        val result = mTimer?.isRunning ?: false
        if (result) {
            toast_showShort(text = "定时器已启动")
        } else {
            toast_showShort(text = "定时器未启动")
        }
    }

    // 获取定时器
    val clickGet = View.OnClickListener { view ->
        val timerTAG = TimerManager.getTimer(TAG)
        if (timerTAG != null) {
            toast_showShort(text = "获取定时器成功 ${timerTAG}")
        } else {
            toast_showShort(text = "暂无该定时器")
        }
    }

    // 获取运行次数
    val clickGetNumber = View.OnClickListener { view ->
        val result = mTimer?.isRunning ?: false
        if (result) {
            toast_showShort(text = "定时器运行次数: ${mTimer?.triggerNumber}")
        } else {
            toast_showShort(text = "定时器未启动")
        }
    }
}