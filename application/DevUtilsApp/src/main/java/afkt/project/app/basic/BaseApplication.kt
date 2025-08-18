package afkt.project.app.basic

import afkt.project.app.AppContext
import android.util.Log
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
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
open class BaseApplication : MultiDexApplication(),
    ViewModelStoreOwner {

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        private lateinit var application: BaseApplication

        fun application(): BaseApplication {
            return application
        }
    }

    // =======================
    // = ViewModelStoreOwner =
    // =======================

    // ViewModelStore
    private lateinit var mAppViewModelStore: ViewModelStore

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore

    // ============
    // = override =
    // ============

    override fun onCreate() {
        super.onCreate()

        // ============
        // = DevUtils =
        // ============

        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化, 无需主动调用
        DevUtils.init(this)
        // 初始化日志配置
        DevLogger.initialize(
            LogConfig().logLevel(LogLevel.DEBUG)
                .tag(AppContext.TAG)
                .sortLog(true) // 美化日志, 边框包围
                .methodCount(0)
        )
        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog()
        DevUtils.openDebug()

        // 可进行日志拦截编码
        // DevLogger.setPrint(new DevLogger.Print())
        // JCLogUtils.setPrint(new JCLogUtils.Print())
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

        // 初始化
        initialize()
    }

    // ============
    // = 初始化方法 =
    // ============

    /**
     * 统一初始化方法
     */
    private fun initialize() {
        application = this
        // 全局 ViewModel
        mAppViewModelStore = ViewModelStore()
        // 初始化 Engine
        initEngine()
    }

    /**
     * 初始化 Engine
     */
    private fun initEngine() {
        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
    }
}