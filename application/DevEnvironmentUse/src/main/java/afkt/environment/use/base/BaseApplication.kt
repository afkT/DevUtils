package afkt.environment.use.base

import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.engine.DevEngine

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "DevEnvironmentUse_Log"

    override fun onCreate() {
        super.onCreate()

        // DevUtils Debug 开发配置
        DevUtils.debugDevelop(TAG)

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
    }
}