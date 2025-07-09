package afkt.project.app.base

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.helper.*
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.agile.assist.WebViewAssist
import dev.base.utils.ViewModelUtils
import dev.engine.DevEngine
import dev.engine.image.ImageConfig
import dev.expand.engine.log.log_d
import dev.mvvm.utils.image.AppImageConfig
import dev.mvvm.utils.size.AppSize
import dev.simple.DevSimple
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.app.ActivityUtils
import dev.utils.app.CrashUtils
import dev.utils.app.CrashUtils.CrashCatchListener
import dev.utils.app.ScreenshotUtils
import dev.utils.app.VersionUtils
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel
import dev.utils.common.DateUtils
import dev.utils.common.StringUtils
import dev.utils.common.assist.TimeCounter
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication(),
    ViewModelStoreOwner {

    // 日志 TAG
    val TAG = "DevUtils_Log"

    override fun onCreate() {
        super.onCreate()

        // 初始化计时器
        val timeCounter = TimeCounter()

        // ============
        // = DevUtils =
        // ============

        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化, 无需主动调用
        DevUtils.init(this)
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

        // 打印项目信息
        AppHelper.printInfo(timeCounter)
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
        // 初始化引擎
        initEngine()
        // 初始化异常捕获处理
        initCrash()
        // 初始化截图监听
        initScreenshot()
        // 初始化状态布局配置
        initStateLayout()
        // 初始化 WebView 辅助类全局配置
        initWebViewBuilder()
        // 初始化 App ImageConfig Creator
        initAppImageConfigCreator()
    }

    /**
     * 初始化引擎
     */
    private fun initEngine() {
        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
    }

    /**
     * 初始化异常捕获处理
     */
    private fun initCrash() {
        // 捕获异常处理 => 在 BaseApplication 中调用
        CrashUtils.getInstance().initialize(applicationContext, object : CrashCatchListener {
            override fun handleException(ex: Throwable) {
                // 保存日志信息
            }

            override fun uncaughtException(
                context: Context,
                thread: Thread,
                ex: Throwable
            ) {
//                // 退出 JVM (Java 虚拟机 ) 释放所占内存资源, 0 表示正常退出、非 0 的都为异常退出
//                System.exit(-1)
//                // 从操作系统中结束掉当前程序的进程
//                android.os.Process.killProcess(android.os.Process.myPid())
                // 关闭 APP
                ActivityUtils.getManager().exitApplication()
                // 可启动新的 Activity 显示异常信息、打开 APP 等
            }
        })
    }

    /**
     * 初始化截图监听
     */
    private fun initScreenshot() {
        // 截图监听
        ScreenshotUtils.getInstance()
            .setListener { contentUri: Uri?, selfChange: Boolean, rowId: Long, dataPath: String?, dateTaken: Long ->
                val builder = StringBuilder()
                    .append("截图监听回调")
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append("contentUri: ").append(contentUri)
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append("selfChange: ").append(selfChange)
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append("rowId: ").append(rowId)
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append("dataPath: ").append(dataPath)
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append("dateTaken: ").append(dateTaken).append(" ( ")
                    .append(DateUtils.formatTime(dateTaken)).append(" )")
                log_d(message = builder.toString())
            }.startListener()
    }

    /**
     * 初始化状态布局配置
     */
    private fun initStateLayout() {
        val global = StateLayout.Global(object : StateLayout.Listener {
            override fun onRemove(
                layout: StateLayout,
                type: Int,
                removeView: Boolean
            ) {
                if (removeView) layout.gone()
            }

            override fun onNotFound(
                layout: StateLayout,
                type: Int
            ) {
                layout.gone()
            }

            override fun onChange(
                layout: StateLayout,
                type: Int,
                oldType: Int,
                view: View
            ) {
//                if (type == ViewAssist.TYPE_EMPTY_DATA) { // NO_DATA
//                    val findView = ViewUtils.findViewById<View>(view, R.id.viewId)
//                } else if (type == ViewAssist.TYPE_FAILED) { // FAIL
//                    val findView = ViewUtils.findViewById<View>(view, R.id.viewId)
//                }
            }
        })
            .register(ViewAssist.TYPE_ING, R.layout.state_layout_ing)
            .register(ViewAssist.TYPE_FAILED, R.layout.state_layout_fail)
            .register(ViewAssist.TYPE_EMPTY_DATA, R.layout.state_layout_no_data)
        // 设置全局配置
        StateLayout.setGlobal(global)
    }

    /**
     * 初始化 WebView 辅助类全局配置
     */
    private fun initWebViewBuilder() {
        WebViewAssist.Builder().apply {
            // 显示内置缩放工具
            setBuiltInZoomControls(false)
            // 显示缩放工具
            setDisplayZoomControls(false)
            // 渲染优先级高
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            // 基础布局算法
            if (VersionUtils.isLollipop()) {
                setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING)
            } else {
                setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
            }
            // 应用配置监听事件
            setOnApplyListener(object : WebViewAssist.Builder.OnApplyListener {
                override fun onApply(
                    webViewAssist: WebViewAssist?,
                    builder: WebViewAssist.Builder
                ) {
                    log_d(message = "WebViewAssist Builder onApply")
                }
            })
            // WebViewAssist 构造函数会使用全局配置
            WebViewAssist.setGlobalBuilder(this)
        }
    }

    /**
     * 初始化 [AppImageConfig] ImageConfig Creator
     */
    private fun initAppImageConfigCreator() {
        DevSimple.setImageCreator { key, param ->
            when (key) {
                IMAGE_DEFAULT_CROP -> {
                    ImageConfig.create().apply {
                        setScaleType(ImageConfig.SCALE_CENTER_CROP)
                    }
                }

                IMAGE_DEFAULT_FIX -> {
                    ImageConfig.create().apply {
                        setScaleType(ImageConfig.SCALE_FIT_CENTER)
                    }
                }

                IMAGE_ROUND_3 -> {
                    ImageConfig.create(IMAGE_ROUND).apply {
                        setRoundedCornersRadius(
                            AppSize.dp2px(3F)
                        )
                    }
                }

                IMAGE_ROUND_10 -> {
                    ImageConfig.create(IMAGE_ROUND).apply {
                        setRoundedCornersRadius(
                            AppSize.dp2px(10F)
                        )
                    }
                }

                IMAGE_ROUND_CROP_10 -> {
                    ImageConfig.create(IMAGE_ROUND).apply {
                        setRoundedCornersRadius(
                            AppSize.dp2px(10F)
                        )
                        setScaleType(ImageConfig.SCALE_CENTER_CROP)
                    }
                }

                IMAGE_ROUND_FIX_10 -> {
                    ImageConfig.create(IMAGE_ROUND).apply {
                        setRoundedCornersRadius(
                            AppSize.dp2px(10F)
                        )
                        setScaleType(ImageConfig.SCALE_FIT_CENTER)
                    }
                }

                else -> {
                    null
                }
            }
        }
    }

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        private lateinit var application: BaseApplication

        private val viewModel: AppViewModel by lazy {
            ViewModelUtils.getAppViewModel(
                application, AppViewModel::class.java
            )!!
        }

        fun app(): BaseApplication {
            return application
        }

        fun viewModel(): AppViewModel {
            return viewModel
        }
    }

    // =======================
    // = ViewModelStoreOwner =
    // =======================

    // ViewModelStore
    private lateinit var mAppViewModelStore: ViewModelStore

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore
}

/**
 * 获取全局 ViewModel
 * @return Application ViewModel
 */
fun appViewModel(): AppViewModel {
    return BaseApplication.viewModel()
}