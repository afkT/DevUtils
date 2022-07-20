package afkt.project.base

import afkt.project.R
import afkt.project.base.http.RetrofitManagerUse
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import dev.DevAssist
import dev.DevHttpCapture
import dev.DevUtils
import dev.assist.WebViewAssist
import dev.base.DevBase
import dev.base.DevBaseMVVM
import dev.engine.DevEngine
import dev.environment.DevEnvironment
import dev.environment.DevEnvironmentActivity
import dev.kotlin.engine.log.log_d
import dev.kotlin.engine.log.log_i
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

    override fun onCreate() {
        super.onCreate()

        // 初始化计时器
        val timeCounter = TimeCounter()

        ARouter.openLog()
        ARouter.openDebug()
        // 打印日志的时候打印线程堆栈
        ARouter.printStackTrace()
        // 尽可能早, 推荐在 Application 中初始化
        ARouter.init(this)

        // ============
        // = DevUtils =
        // ============

        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化, 无需主动调用
        DevUtils.init(this.applicationContext)
        // 初始化日志配置
        DevLogger.initialize(
            LogConfig().logLevel(LogLevel.DEBUG)
                .tag(AppConfig.LOG_TAG)
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
            .append("项目名: ")
            .append(ResourceUtils.getString(R.string.str_app_name))
            .append("\nSDK: ").append(Build.VERSION.SDK_INT).append("(")
            .append(VersionUtils.convertSDKVersion(Build.VERSION.SDK_INT)).append(")")
            .append("\nPackageName: ").append(AppUtils.getPackageName())
            .append("\nVersionCode: ").append(AppUtils.getAppVersionCode())
            .append("\nVersionName: ").append(AppUtils.getAppVersionName())
            .append("\nDevUtils 版本: ").append(DevUtils.getDevAppVersion())
            .append("\nDevAssist 版本: ").append(DevAssist.getDevAssistVersion())
            .append("\nDevBase 版本: ").append(DevBase.getDevBaseVersion())
            .append("\nDevBaseMVVM 版本: ").append(DevBaseMVVM.getDevBaseMVVMVersion())
            .append("\nDevHttpCapture 版本: ").append(DevHttpCapture.getDevHttpCaptureVersion())
            .append("\nDevJava 版本: ").append(DevUtils.getDevJavaVersion())
            .append("\nDevWidget 版本: ").append(DevWidget.getDevWidgetVersion())
            .append("\nDevEnvironment 版本: ")
            .append(DevEnvironmentActivity.getDevEnvironmentVersion())
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
        // 初始化状态布局配置
        initStateLayout()
        // 初始化异常捕获处理
        initCrash()
        // 初始化 WebView 辅助类全局配置
        initWebViewBuilder()
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
                // 可开启定时任务, 延迟几秒启动 APP
            }
        })
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
     * 初始化其他 lib
     */
    private fun initOther() {
        // https://github.com/JessYanCoding/AndroidAutoSize/blob/master/demo-subunits/src/main/java/me/jessyan/autosize/demo/subunits/BaseApplication.java
        // 可不调用, 默认开启 DP 转换
        AutoSizeConfig.getInstance().unitsManager.isSupportDP = true

        // 环境 ( 服务器地址 ) 改变通知
        DevEnvironment.addOnEnvironmentChangeListener { _, _, _ ->
            // 改变地址重新初始化
            RetrofitManagerUse.operation().reset()
        }

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

    companion object {

        /**
         * 是否 Debug 模式
         * @return `true` yes, `false` no
         */
        fun isDebug(): Boolean {
            return DevUtils.isDebug()
        }
    }
}