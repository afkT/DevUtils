package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseViewModel
import android.view.View
import androidx.databinding.ObservableField
import dev.DevHttpCapture
import dev.capture.CaptureInfo
import dev.capture.interceptor.CallbackInterceptor
import dev.capture.interceptor.SimpleInterceptor
import dev.capture.interfaces.IHttpCaptureEnd
import dev.capture.interfaces.IHttpCaptureEvent
import dev.capture.interfaces.IHttpCaptureEventFilter
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

    /**
     * 请求 Banner 列表
     * @param apiService 服务器接口 API Service
     */
    fun requestBanner(apiService: APIService) {
        this.launchExecuteRequest(block = {
            apiService.getBanner()
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

    // ============================
    // = SimpleInterceptor 过滤请求 =
    // ============================

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
                    message = "取消该请求 $url 的抓包逻辑处理，将不会触发 SimpleInterceptor endCall 方法",
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
            // 设置 Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
            addInterceptor(CallbackInterceptor(endCall = object : IHttpCaptureEnd {
                override fun callEnd(info: CaptureInfo) {
                    // 打印 Http 请求信息
                    val tag = "callback_http_capture"
                    tag.log_jsonTag(json = info.toJson())
                }
            }))
        })
        // 请求文章列表
        requestArticleList(apiService)
    }

    // ===============================
    // = CallbackInterceptor 过滤请求 =
    // ===============================

    // Http 抓包拦截器 ( 无存储逻辑 ) 过滤请求
    val clickCallbackInterceptorFilter = View.OnClickListener { view ->
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            // 设置 Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
            addInterceptor(object : CallbackInterceptor(endCall = object : IHttpCaptureEnd {
                override fun callEnd(info: CaptureInfo) {
                    // 打印 Http 请求信息
                    val tag = "callback_filter_http_capture"
                    tag.log_jsonTag(json = info.toJson())
                }
            }) {
                /**
                 * 获取 Http 拦截过滤器
                 */
                override fun getHttpFilter(): IHttpFilter {
                    return callbackInterceptorHttpFilter
                }
            })
        })
        // 请求文章列表
        requestArticleList(apiService)
        // 请求搜索热词列表
        requestHotkeys(apiService)
    }

    // Http 拦截过滤器
    private val callbackInterceptorHttpFilter = object : IHttpFilter {

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
            val tag = "callback_filter_http_capture"
            tag.log_dTag(
                message = "进入拦截过滤器: $protocol $method $url",
            )
            // 如果请求链接中包含 article 则进行拦截
            if (url.toString().contains("article")) {
                tag.log_dTag(
                    message = "取消该请求 $url 的抓包逻辑处理，将不会触发 CallbackInterceptor endCall 方法",
                )
                return true
            }
            // 默认不进行拦截, 可以自行决定对哪些请求进行拦截不抓包
            return false
//            // 返回 true 则表示拦截操作则不会触发 endCall 方法
//            return true
        }
    }

    // ==================================
    // = CallbackInterceptor 事件处理拦截 =
    // ==================================

    // Http 抓包拦截器 ( 无存储逻辑 ) 事件处理拦截
    val clickCallbackInterceptorEventFilter = View.OnClickListener { view ->
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            // 设置 Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
            addInterceptor(CallbackInterceptor(endCall = object : IHttpCaptureEnd {
                override fun callEnd(info: CaptureInfo) {
                    // 打印 Http 请求信息
                    val tag = "callback_event_filter_http_capture"
                    tag.log_jsonTag(json = info.toJson())
                }
            }, eventFilter = callbackInterceptorEventFilter))
        })
        // 请求搜索热词列表
        requestHotkeys(apiService)
        // 请求 Banner 列表
        requestBanner(apiService)
    }

    /**
     * Http 抓包事件处理拦截
     * 用于拦截 [IHttpCaptureEvent] 对应事件数据是否进行转换并设置到 [CaptureInfo] 对应属性
     */
    private val callbackInterceptorEventFilter = object : IHttpCaptureEventFilter {

        /**
         * 是否过滤请求体信息 Map
         */
        override fun filterRequestBody(
            request: Request,
            url: HttpUrl,
            method: String,
            protocol: Protocol,
            headers: Headers,
            requestBody: RequestBody?
        ): Boolean {
            if (requestBody != null) {
                when (requestBody) {
                    is MultipartBody -> {
                        /**
                         * 防止属于上传文件，则不进行存储到 [CaptureInfo.requestBody] 中
                         */
                        return true
                    }
                }
            }
            return super.filterRequestBody(request, url, method, protocol, headers, requestBody)
        }

        /**
         * 是否过滤请求链接字符串
         */
        override fun filterRequestUrl(
            request: Request,
            url: HttpUrl,
            method: String,
            protocol: Protocol,
            headers: Headers,
            requestBody: RequestBody?
        ): Boolean {
            // 如果请求链接中包含 banner 则进行过滤
            if (url.toString().contains("banner")) {
                /**
                 * 返回的 [CaptureInfo.requestUrl] 值为 ""
                 */
                return true
            }
            return super.filterRequestUrl(request, url, method, protocol, headers, requestBody)
        }

        // ... 还有其他可以查看 IHttpCaptureEventFilter 或进行 Override Methods
    }
}