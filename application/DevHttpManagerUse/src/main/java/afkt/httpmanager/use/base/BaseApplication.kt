package afkt.httpmanager.use.base

import afkt.httpmanager.use.network.HttpCore
import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.engine.DevEngine

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "DevHttpManagerUse_Log"

    override fun onCreate() {
        super.onCreate()

        // DevUtils Debug 开发配置
        DevUtils.debugDevelop(TAG)

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)

        // 初始化 Http Core
        HttpCore.initialize(this)
    }

    companion object {

        /**
         * 是否 Release 版本标记
         */
        fun isRelease(): Boolean = !isDebug()

        /**
         * 是否 Debug 版本标记
         */
        fun isDebug(): Boolean = DevUtils.isDebug()
    }
}