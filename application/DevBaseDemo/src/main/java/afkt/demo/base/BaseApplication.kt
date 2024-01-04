package afkt.demo.base

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.engine.DevEngine
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel

class BaseApplication : MultiDexApplication(),
    ViewModelStoreOwner {

    // 日志 TAG
    private val TAG = "DevBaseDemo_TAG"

    // ViewModelStore
    private lateinit var mAppViewModelStore: ViewModelStore

    override fun onCreate() {
        super.onCreate()

        // 初始化 Logger 配置
        DevLogger.initialize(
            LogConfig()
                .logLevel(LogLevel.DEBUG)
                .tag(TAG)
                .sortLog(true)
                .methodCount(0)
        )
        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog()
        DevUtils.openDebug()

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)

        application = this
        mAppViewModelStore = ViewModelStore()
    }

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        private lateinit var application: BaseApplication

        fun getApplication(): BaseApplication {
            return application
        }
    }

    // =======================
    // = ViewModelStoreOwner =
    // =======================

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore
}