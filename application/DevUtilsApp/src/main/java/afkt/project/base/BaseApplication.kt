package afkt.project.base

import afkt.project.R
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import androidx.multidex.MultiDexApplication
import com.therouter.TheRouter
import dev.*
import dev.agile.assist.WebViewAssist
import dev.base.DevBase
import dev.base.DevBaseMVVM
import dev.engine.DevEngine
import dev.engine.image.ImageConfig
import dev.environment.DevEnvironmentUtils
import dev.expand.engine.log.log_d
import dev.expand.engine.log.log_i
import dev.mvvm.utils.image.AppImageConfig
import dev.mvvm.utils.size.AppSize
import dev.simple.DevSimple
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.app.*
import dev.utils.app.CrashUtils.CrashCatchListener
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel
import dev.utils.common.DateUtils
import dev.utils.common.StringUtils
import dev.utils.common.assist.TimeCounter
import dev.widget.DevWidget
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout
import me.jessyan.autosize.AutoSizeConfig

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication() {

    companion object {

        /**
         * 是否 Debug 模式
         * @return `true` yes, `false` no
         */
        fun isDebug(): Boolean {
            return DevUtils.isDebug()
        }
    }

    override fun onCreate() {
        super.onCreate()

        // 初始化计时器
        val timeCounter = TimeCounter()

        TheRouter.isDebug = true
        // 推荐在 Application 中初始化
        TheRouter.init(this)

        // ============
        // = DevUtils =
        // ============

        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化, 无需主动调用
        DevUtils.init(this.applicationContext)
        // 初始化日志配置
        DevLogger.initialize(
            LogConfig().logLevel(LogLevel.DEBUG)
                .tag("DevUtils_Log")
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

        // 属于 Debug 才打印信息
        if (isDebug()) printProInfo(timeCounter)
    }

    /**
     * 打印项目信息
     * @param timeCounter [TimeCounter]
     */
    private fun printProInfo(timeCounter: TimeCounter) {
        val builder = StringBuilder()
            .append("项目名: ").append(ResourceUtils.getString(R.string.str_app_name))
            .append("\nSDK: ").append(Build.VERSION.SDK_INT).append("(")
            .append(VersionUtils.convertSDKVersion(Build.VERSION.SDK_INT)).append(")")
            .append("\nPackageName: ").append(AppUtils.getPackageName())
            .append("\nVersionCode: ").append(AppUtils.getAppVersionCode())
            .append("\nVersionName: ").append(AppUtils.getAppVersionName())
            .append("\nDevUtils 版本: ").append(DevUtils.getDevAppVersion())
            .append("\nDevAssist 版本: ").append(DevAssist.getDevAssistVersion())
            .append("\nDevBase 版本: ").append(DevBase.getDevBaseVersion())
            .append("\nDevBaseMVVM 版本: ").append(DevBaseMVVM.getDevBaseMVVMVersion())
            .append("\nDevSimple 版本: ").append(DevSimple.getDevSimpleVersion())
            .append("\nDevEngine 版本: ").append(DevEngine.getDevEngineVersion())
            .append("\nDevWidget 版本: ").append(DevWidget.getDevWidgetVersion())
            .append("\nDevHttpCapture 版本: ").append(DevHttpCapture.getDevHttpCaptureVersion())
            .append("\nDevEnvironment 版本: ")
            .append(DevEnvironmentUtils.getDevEnvironmentVersion())
            .append("\nDevHttpManager 版本: ").append(DevHttpManager.getDevHttpManagerVersion())
            .append("\nDevRetrofit 版本: ").append(DevRetrofit.getDevRetrofitVersion())
            .append("\nDevJava 版本: ").append(DevUtils.getDevJavaVersion())
            .append("\n时间: ").append(DateUtils.getDateNow())
            .append("\n初始化耗时(毫秒): ").append(timeCounter.duration())
        log_i(message = builder.toString())
    }

    // ============
    // = 初始化方法 =
    // ============

    /**
     * 统一初始化方法
     */
    private fun initialize() {
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
        // 初始化其他 lib
        initOther()
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
                if (type == ViewAssist.TYPE_EMPTY_DATA) { // NO_DATA
                    val vidTipsTv = ViewUtils.findViewById<View>(view, R.id.vid_tips_tv)
                    TextViewUtils.setText(vidTipsTv, "暂无数据")
                } else if (type == ViewAssist.TYPE_FAILED) { // FAIL
                    val vidTipsTv = ViewUtils.findViewById<View>(view, R.id.vid_tips_tv)
                    TextViewUtils.setText(vidTipsTv, "请求失败, 请稍后重试!")
                }
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
            // Application Caches 地址
            setAppCachePath(PathUtils.getInternal().getAppCachePath("cache"))
            // 数据库缓存路径
            setDatabasePath(PathUtils.getInternal().getAppCachePath("db"))
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

    /**
     * 初始化其他 lib
     */
    private fun initOther() {
        // https://github.com/JessYanCoding/AndroidAutoSize/blob/master/demo-subunits/src/main/java/me/jessyan/autosize/demo/subunits/BaseApplication.java
        // 可不调用, 默认开启 DP 转换
        AutoSizeConfig.getInstance().unitsManager.isSupportDP = true
    }
}

// ================================
// = dev.engine.image.ImageConfig =
// ================================

// ============
// = 使用方式一 =
// ============

private val IMAGE_ROUND = ImageConfig.create().apply {
    setTransform(ImageConfig.TRANSFORM_ROUNDED_CORNERS)
    setScaleType(ImageConfig.SCALE_NONE)
}

//val IMAGE_DEFAULT_CROP = ImageConfig.create().apply {
//    setScaleType(ImageConfig.SCALE_CENTER_CROP)
//}
//
//val IMAGE_ROUND_3 = ImageConfig.create(IMAGE_ROUND).apply {
//    setRoundedCornersRadius(
//        AppSize.dp2px(3F)
//    )
//

// ============
// = 使用方式二 =
// ============

// IMAGE_KEY.toImageConfig() => ImageConfig
const val IMAGE_DEFAULT_CROP = "IMAGE_DEFAULT_CROP"
const val IMAGE_DEFAULT_FIX = "IMAGE_DEFAULT_FIX"
const val IMAGE_ROUND_3 = "IMAGE_ROUND_3"
const val IMAGE_ROUND_10 = "IMAGE_ROUND_10"
const val IMAGE_ROUND_CROP_10 = "IMAGE_ROUND_CROP_10"
const val IMAGE_ROUND_FIX_10 = "IMAGE_ROUND_FIX_10"