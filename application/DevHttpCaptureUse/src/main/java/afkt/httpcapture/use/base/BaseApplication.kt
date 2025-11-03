package afkt.httpcapture.use.base

import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.engine.DevEngine
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "DevHttpCaptureUse_Log"

    override fun onCreate() {
        super.onCreate()

        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog()
        DevUtils.openDebug()
        // 初始化 Logger 配置
        val logConfig = LogConfig.getSortLogConfig(TAG)
            .displayThreadInfo(false)
        DevLogger.initialize(logConfig)

        // ============
        // = 初始化操作 =
        // ============

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
    }
}