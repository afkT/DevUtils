package afkt.httpcapture.use.base

import android.app.Application
import dev.DevUtils
import dev.engine.DevEngine

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : Application() {

    // 日志 TAG
    val TAG = "DevHttpCaptureUse_Log"

    override fun onCreate() {
        super.onCreate()

        // DevUtils Debug 开发配置
        DevUtils.debugDevelop(TAG)

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
    }
}