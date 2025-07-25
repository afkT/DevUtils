package afkt.project.app

import afkt.project.R
import afkt.project.app.basic.BaseApplication
import afkt.project.model.engine.initAppImageConfigCreator
import android.content.Context
import android.net.Uri
import android.view.View
import android.webkit.WebSettings
import dev.DevUtils
import dev.agile.assist.WebViewAssist
import dev.base.utils.ViewModelUtils
import dev.expand.engine.log.log_d
import dev.utils.DevFinal
import dev.utils.app.ScreenshotUtils
import dev.utils.app.VersionUtils
import dev.utils.common.DateUtils
import dev.utils.common.assist.TimeCounter
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout

/**
 * detail: App Application
 * @author Ttt
 */
class AppContext : BaseApplication() {

    companion object {

        // 日志 TAG
        val TAG = "DevUtilsApp_Log"

        private val viewModel: AppViewModel by lazy {
            ViewModelUtils.getAppViewModel(
                application(),
                AppViewModel::class.java
            )!!
        }

        fun viewModel(): AppViewModel {
            return viewModel
        }

        fun context(): Context {
            return DevUtils.getContext()
        }

        fun context(context: Context?): Context {
            return DevUtils.getContext(context)
        }
    }

    // ===========
    // = override =
    // ===========

    override fun onCreate() {
        // 初始化计时器
        val timeCounter = TimeCounter()
        super.onCreate()
        // 初始化截图监听
        initScreenshot()
        // 初始化状态布局配置
        initStateLayout()
        // 初始化 WebView 辅助类全局配置
        initWebViewBuilder()
        // 初始化 App ImageConfig Creator
        initAppImageConfigCreator()
        // 打印项目信息
        AppHelper.printInfo(timeCounter)
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
}

/**
 * 获取全局 ViewModel
 * @return Application ViewModel
 */
fun appViewModel(): AppViewModel {
    return AppContext.viewModel()
}