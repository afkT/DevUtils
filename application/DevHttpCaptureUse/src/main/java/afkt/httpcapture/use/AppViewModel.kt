package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseViewModel
import android.view.View
import androidx.databinding.ObservableField
import dev.DevHttpCapture
import dev.capture.CaptureInfo
import dev.capture.interceptor.CallbackInterceptor
import dev.capture.interceptor.SimpleInterceptor
import dev.capture.interfaces.IHttpCaptureEnd
import dev.capture.interfaces.IHttpFilter
import dev.expand.engine.log.log_dTag
import dev.expand.engine.log.log_jsonTag
import dev.retrofit.launchExecuteRequest
import okhttp3.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: 整个 App ViewModel
 * @author Ttt
 */
class AppViewModel : BaseViewModel() {

    val page = AtomicInteger()

    // ===============
    // = MainFragment =
    // ===============

    // DevHttpCapture:Version
    val devHttpCaptureVersion = ObservableField(
        "DevHttpCapture:${DevHttpCapture.getDevHttpCaptureVersion()}"
    )

    // ==============
    // = 统一请求方法 =
    // ==============

    /**
     * 请求文章列表
     * @param apiService 服务器接口 API Service
     */
    fun requestArticleList(apiService: APIService) {
        this.launchExecuteRequest(block = {
            apiService.getArticleList(page.incrementAndGet())
        }, start = {}, success = {}, error = {}, finish = {})
    }

    /**
     * 请求搜索热词列表
     * @param apiService 服务器接口 API Service
     */
    fun requestHotkeys(apiService: APIService) {
        this.launchExecuteRequest(block = {
            apiService.getHotkeys()
        }, start = {}, success = {}, error = {}, finish = {})
    }

    // =====================
    // = SimpleInterceptor =
    // =====================

    // 简单的 Http 抓包拦截器
    val clickSimpleInterceptor = View.OnClickListener { view ->
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            // 设置简单的 Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
            addInterceptor(SimpleInterceptor { info ->
                // 打印 Http 请求信息
                val tag = "simple_http_capture"
                tag.log_jsonTag(json = info.toJson())
            })
        })
        // 请求文章列表
        requestArticleList(apiService)
    }

    // =

    // 简单的 Http 抓包拦截器 ( 过滤请求 )
    val clickSimpleInterceptorFilter = View.OnClickListener { view ->
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            // 设置简单的 Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
            addInterceptor(object : SimpleInterceptor({ info ->
                // 打印 Http 请求信息
                val tag = "simple_filter_http_capture"
                tag.log_jsonTag(json = info.toJson())
            }) {
                /**
                 * 获取 Http 拦截过滤器
                 */
                override fun getHttpFilter(): IHttpFilter {
                    return simpleInterceptorHttpFilter
                }
            })
        })
        // 请求文章列表
        requestArticleList(apiService)
        // 请求搜索热词列表
        requestHotkeys(apiService)
    }

    // Http 拦截过滤器
    private val simpleInterceptorHttpFilter = object : IHttpFilter {

        /**
         * 是否过滤该 Http 请求不进行抓包存储
         * @param request  请求对象
         * @param url      请求链接
         * @param method   请求方法
         * @param protocol 请求协议
         * @param headers  请求头信息
         * @return `true` 拦截操作, `false` 不拦截
         */
        override fun filter(
            request: Request,
            url: HttpUrl,
            method: String,
            protocol: Protocol,
            headers: Headers
        ): Boolean {
            val tag = "simple_filter_http_capture"
            tag.log_dTag(
                message = "进入拦截过滤器: $protocol $method $url",
            )
            // 如果请求链接中包含 article 则进行拦截
            if (url.toString().contains("article")) {
                tag.log_dTag(
                    message = "拦截 $url 请求，将不会触发 SimpleInterceptor endCall 方法",
                )
                return true
            }
            // 默认不进行拦截, 可以自行决定对哪些请求进行拦截不抓包
            return false
//            // 返回 true 则表示拦截操作则不会触发 endCall 方法
//            return true
        }
    }

    // =======================
    // = CallbackInterceptor =
    // =======================

    // Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
    val clickCallbackInterceptor = View.OnClickListener { view ->
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            // 设置 CallbackInterceptor 抓包拦截器并进行网络请求
            addInterceptor(CallbackInterceptor(endCall = object : IHttpCaptureEnd {
                override fun callEnd(info: CaptureInfo) {
                    // 打印 Http 请求信息
                    val tag = "call_http_capture"
                    tag.log_jsonTag(json = info.toJson())
                }
            }))
        })
        // 请求文章列表
        requestArticleList(apiService)
    }
}