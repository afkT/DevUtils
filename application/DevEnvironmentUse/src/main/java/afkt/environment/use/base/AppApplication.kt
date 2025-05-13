package afkt.environment.use.base

import android.util.Log
import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.engine.DevEngine
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel
import dev.utils.common.StringUtils

/**
 * detail: Base Application
 * @author Ttt
 */
class AppApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // 初始化日志配置
        DevLogger.initialize(
            LogConfig().logLevel(LogLevel.DEBUG)
                .tag("DevEnvironmentUse_Log")
                .sortLog(true) // 美化日志, 边框包围
                .methodCount(0)
        )
        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog()
        DevUtils.openDebug()

        // 可进行日志拦截编码
        LogPrintUtils.setPrint(LogPrintUtils.Print { logType, tag, msg ->
            if (msg == null) return@Print
            // 进行编码处理
            val message = StringUtils.strEncode(msg, DevFinal.ENCODE.UTF_8)
            when (logType) {
                Log.VERBOSE -> Log.v(tag, message)
                Log.DEBUG -> Log.d(tag, message)
                Log.INFO -> Log.i(tag, message)
                Log.WARN -> Log.w(tag, message)
                Log.ERROR -> Log.e(tag, message)
                Log.ASSERT -> Log.wtf(tag, message)
                else -> Log.wtf(tag, message)
            }
        })

        // ============
        // = 初始化操作 =
        // ============

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
    }
}