package afkt.project.features.other_function

import afkt.project.R
import afkt.project.app.AppContext
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionWebViewBinding
import android.net.http.SslError
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dev.engine.extensions.log.log_dTag
import dev.simple.features.web.WebViewAssist

/**
 * detail: WebView 辅助类
 * @author Ttt
 */
class WebViewFragment : AppFragment<FragmentOtherFunctionWebViewBinding, AppViewModel>(
    R.layout.fragment_other_function_web_view
) {
    // WebView 辅助类
    private val mWebViewAssist = WebViewAssist()

    override fun initValue() {
        super.initValue()

        // 长按监听事件
        binding.vidWv.setOnLongClickListener(View.OnLongClickListener { view ->
            ((view as WebView).hitTestResult).let { result ->
                when (result.type) {
                    WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> {
                        val imgUrl = result.extra
                        TAG.log_dTag(
                            message = "SRC_IMAGE_ANCHOR_TYPE $imgUrl"
                        )
                        return@OnLongClickListener true
                    }

                    else -> {
                    }
                }
            }
            false
        })

        // 设置 WebView
        mWebViewAssist.setWebView(binding.vidWv)
        // 设置辅助 WebView 处理 Javascript 对话框、标题等对象
        mWebViewAssist.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                position: Int
            ) {
                // 加载进度监听
                if (position == 100) { // 加载完成
                    TAG.log_dTag(
                        message = "加载完成"
                    )
                }
                super.onProgressChanged(view, position)
            }
        })
        // 设置处理各种通知和请求事件对象
        mWebViewAssist.setWebViewClient(object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed() // 接受所有网站的证书
//                handler.cancel() // Android 默认的处理方式
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                // 重定向 URL 请求, 返回 true 表示拦截此 url, 返回 false 表示不拦截此 url
                if (url.startsWith("weixin://")) {
                    mWebViewAssist.loadUrl(
                        url.replace("weixin://", "http://")
                    )
                    return true
                }
                // 在本页面的 WebView 打开, 防止外部浏览器打开此链接
                mWebViewAssist.loadUrl(url)
                return true
            }
        })
        /**
         * 默认使用全局配置
         * [AppContext.initWebViewBuilder]
         */
//        // 如果使用全局配置, 则直接调用 apply 方法
//        mWebViewAssist.apply()

        // 克隆全局配置, 自行修改部分配置并使用
        WebViewAssist.getGlobalBuilder()?.let {
            val builder = it.clone(true)
            builder.setBuiltInZoomControls(false)
                .setDisplayZoomControls(false)
            // 当克隆方法 ( clone ) 传入 true 表示复用 OnApplyListener 但是想要能够添加其他配置处理则如下方法操作
            val applyListener = builder.getApplyListener()
            builder.setOnApplyListener(object : WebViewAssist.Builder.OnApplyListener {
                override fun onApply(
                    webViewAssist: WebViewAssist?,
                    builder: WebViewAssist.Builder
                ) {
                    applyListener?.onApply(webViewAssist, builder)
                    // AppContext 也会打印 WebViewAssist Builder onApply
                    TAG.log_dTag(message = "自定义监听")
                    // 全局配置或者自定义配置以外, 再次配置其他操作
                }
            })
            // 应用配置
            mWebViewAssist.setBuilder(builder, true)
            // 加载网页
            mWebViewAssist.loadUrl("https://www.csdn.net/")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebViewAssist.destroy()
    }

    override fun initListener() {
        super.initListener()
        // 监听返回键处理
        mWebViewAssist.getWebView()?.setOnKeyListener { view, keyCode, event ->
            if (mWebViewAssist.handlerKeyDown(keyCode, event)) {
                // 消费事件
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}