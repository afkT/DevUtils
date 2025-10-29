package afkt.httpcapture.use.base

import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.engine.DevEngine
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "DevHttpCaptureUse_Log"

    override fun onCreate() {
        super.onCreate()

        // 初始化日志配置
        DevLogger.initialize(
            LogConfig().logLevel(LogLevel.DEBUG)
                .tag(TAG)
                .sortLog(true) // 美化日志, 边框包围
                .methodCount(0)
        )
        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog()
        DevUtils.openDebug()

        // ============
        // = 初始化操作 =
        // ============

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
    }
}