package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineWebBinding
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import dev.engine.core.web.WebItem
import dev.engine.extensions.toast.toast_showShort
import dev.engine.extensions.web.*

/**
 * detail: WebView Engine 网页加载、JS 交互等
 * @author Ttt
 */
class WebFragment : AppFragment<FragmentDevEngineWebBinding, WebViewModel>(
    R.layout.fragment_dev_engine_web, BR.viewModel
) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize(binding.vidWeb)
    }

    override fun onDestroyView() {
        viewModel.destroy()
        super.onDestroyView()
    }
}

/**
 * detail: WebView Engine 网页加载、JS 交互等 ViewModel
 * @author Ttt
 */
class WebViewModel : AppViewModel() {

    // 演示加载地址
    private val demoUrl = "https://github.com/afkT/DevUtils"

    // WebView Item
    private var webItem: WebItem? = null

    /**
     * 初始化 WebView
     * @param webView WebView
     */
    fun initialize(webView: WebView) {
        val item = WebItem.create(webView)
        webItem = item
        // 初始化 WebView ( 应用配置、设置 Client、监听等 )
        item.web_initialize()
    }

    /**
     * 销毁处理 ( 避免 WebView 引起的内存泄漏 )
     */
    fun destroy() {
        webItem?.web_destroy()
        webItem = null
    }

    val clickLoadUrl = View.OnClickListener {
        webItem?.web_loadUrl(url = demoUrl)
    }

    val clickLoadData = View.OnClickListener {
        webItem?.web_loadData(
            data = "<h2>WebView Engine</h2><p>loadData 加载 Html 代码</p>",
            mimeType = "text/html",
            encoding = "utf-8"
        )
    }

    val clickReload = View.OnClickListener {
        webItem?.web_reload()
    }

    val clickGoBack = View.OnClickListener {
        if (webItem?.web_canGoBack() == true) {
            webItem?.web_goBack()
        } else {
            "已经是第一个页面".toast_showShort()
        }
    }

    val clickClearCache = View.OnClickListener {
        webItem?.web_clearCache(includeDiskFiles = true)
        "clearCache 清除缓存完成".toast_showShort()
    }
}