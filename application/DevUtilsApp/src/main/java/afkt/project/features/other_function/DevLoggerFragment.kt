package afkt.project.features.other_function

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.view.View
import dev.engine.extensions.toast.toast_showShort
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel
import utils_use.logger.LoggerUse

/**
 * detail: DevLogger 日志工具类
 * @author Ttt
 */
class DevLoggerFragment : AppFragment<BaseViewRecyclerviewBinding, DevLoggerViewModel>(
    R.layout.fragment_other_function_dev_logger, BR.viewModel
)

class DevLoggerViewModel : AppViewModel() {

    // 打印日志
    val clickPrint = View.OnClickListener { view ->
        toast_showShort(text = "打印成功, 请查看 Logcat")
        LoggerUse.tempLog()
    }

    // 打印日志耗时测试
    val clickPrintTime = View.OnClickListener { view ->
        toast_showShort(text = "打印成功, 请查看 Logcat")
        LoggerUse.testTime()
    }

    private fun loggerConfig() {
        // 初始化日志配置
        LogConfig().apply {
            // 堆栈方法总数 ( 显示经过的方法 )
            methodCount = 3
            // 堆栈方法索引偏移 (0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息 )
            methodOffset = 0
            // 是否输出全部方法 ( 在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数 )
            outputMethodAll = false
            // 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
            displayThreadInfo = false
            // 是否排序日志 ( 格式化后 )
            sortLog = false // 是否美化日志, 边框包围
            // 日志级别
            logLevel = LogLevel.DEBUG
            // 设置 TAG ( 特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下 )
            tag = "BaseLog"
            // 进行初始化配置, 这样设置后, 默认全部日志都使用改配置, 特殊使用 DevLogger.other(config).d(xxx)
//            DevLogger.initialize(this)
        }
    }
}