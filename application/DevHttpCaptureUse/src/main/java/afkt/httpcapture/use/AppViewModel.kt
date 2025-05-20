package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseViewModel
import android.view.View
import androidx.databinding.ObservableField
import dev.DevHttpCapture
import dev.DevHttpCaptureCompiler
import dev.capture.CaptureInfo
import dev.capture.CaptureRedact
import dev.capture.interceptor.CallbackInterceptor
import dev.capture.interceptor.SimpleInterceptor
import dev.capture.interceptor.StorageInterceptor
import dev.capture.interfaces.*
import dev.expand.engine.log.log_dTag
import dev.expand.engine.log.log_jsonTag
import dev.expand.engine.toast.toast_showLong
import dev.retrofit.launchExecuteRequest
import dev.utils.common.StringUtils
import okhttp3.*
import java.util.*
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

    // Http 抓包拦截器 ( 过滤请求 )
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

    // Http 抓包拦截器 ( 事件处理拦截 )
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

//            // 设置 Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
//            addInterceptor(object : CallbackInterceptor(endCall = object : IHttpCaptureEnd {
//                override fun callEnd(info: CaptureInfo) {
//                    // 打印 Http 请求信息
//                    val tag = "callback_event_filter_http_capture"
//                    tag.log_jsonTag(json = info.toJson())
//                }
//            }, eventFilter = callbackInterceptorEventFilter) {
//                /**
//                 * 获取 Http 拦截过滤器
//                 */
//                override fun getHttpFilter(): IHttpFilter {
//                    return callbackInterceptorHttpFilter
//                }
//            })
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

    // ===================================
    // = CallbackInterceptor 自定义抓包事件 =
    // ===================================

    // Http 抓包拦截器 ( 自定义抓包事件 )
    val clickCallbackInterceptorEventIMPL = View.OnClickListener { view ->
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            // 设置 Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
            addInterceptor(CallbackInterceptor(endCall = object : IHttpCaptureEnd {
                override fun callEnd(info: CaptureInfo) {
                    /**
                     * 设置自定义 Http 抓包事件回调实现后 eventIMPL = eventIMPL
                     * 则不会触发该方法回调, 会触发实现类 [IHttpCaptureEvent.callEnd] 的方法回调
                     */
                }
            }, eventIMPL = eventIMPL, eventFilter = callbackInterceptorEventFilter))
        })
        // 请求文章列表
        requestArticleList(apiService)
        // 请求搜索热词列表
        requestHotkeys(apiService)
        // 请求 Banner 列表
        requestBanner(apiService)
    }

    /**
     * Http 抓包事件回调实现
     * 并非对请求追加参数信息等，只是对请求及响应数据进行读取转换
     * 方便扩展, 允许自行解析抓包数据
     */
    private val eventIMPL: IHttpCaptureEvent = object : HttpCaptureEventIMPL() {

        /**
         * Http 抓包结束回调
         * @param info 抓包信息封装类
         */
        override fun callEnd(info: CaptureInfo) {
            // 打印 Http 请求信息
            val tag = "callback_event_impl_http_capture"
            tag.log_jsonTag(json = info.toJson())
        }

        // =

        /**
         * 生成请求链接字符串
         * @param request 请求对象
         * @param url 请求链接
         * @return 请求链接字符串
         */
        override fun callRequestUrl(
            request: Request,
            url: HttpUrl
        ): String {
            // 如果请求链接中包含 hotkey 则进行过滤
            if (url.toString().contains("hotkey")) {
                /**
                 * 将 https://www.wanandroid.com/hotkey/json
                 * 替换为 https://www.wanandroid.com/██████/json
                 * 并且设置到 [CaptureInfo.requestUrl]
                 */
                return StringUtils.replaceAll(
                    url.toString(), "hotkey", "██████"
                )
            }
            return super.callRequestUrl(request, url)
        }

        /**
         * 生成响应体信息
         * @param request 请求对象
         * @param response 响应对象
         * @param responseBody 响应体
         * @return 响应体信息
         */
        override fun callResponseBody(
            request: Request,
            response: Response,
            responseBody: ResponseBody
        ): String {
            // 如果属于 article 请求，则隐藏掉文章列表数据
            if (request.url.toString().contains("article")) {
                return "**【隐藏 Response Body 数据】**"
            }
            return super.callResponseBody(request, response, responseBody)
        }

        /**
         * 生成请求头信息 Map
         * @param request 请求对象
         * @param headers 请求头信息
         * @param requestBody 请求体
         * @param captureRedact 抓包信息隐藏字段
         * @return 请求头信息 Map
         */
        override fun callRequestHeaders(
            request: Request,
            headers: Headers,
            requestBody: RequestBody?,
            captureRedact: CaptureRedact
        ): LinkedHashMap<String, String> {
            val maps = super.callRequestHeaders(request, headers, requestBody, captureRedact)
            // 统一给头信息追加内容
            maps["追加请求头随机 UUID"] = UUID.randomUUID().toString()
            return maps
        }

        // ... 还有其他可以查看 IHttpCaptureEvent 或进行 Override Methods

        /**
         * 也可以完全自行实现抓包数据转换, 只需要传入 [IHttpCaptureEvent] 实现类即可
         */
    }

    // ======================
    // = StorageInterceptor =
    // ======================

    private val SHARE_MODULE = "ShareModule"
    private val LOGIN_MODULE = "LoginModule"

    // Http 抓包拦截器 ( 存在存储抓包数据逻辑 )
    val clickStorageInterceptor = View.OnClickListener { view ->
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            /**
             * 设置 Http 抓包拦截器 ( 存在存储抓包数据逻辑 )
             * [StorageInterceptor] 根据 moduleName 创建文件夹并进行存储抓包数据
             * 禁止添加重复 moduleName [StorageInterceptor] 拦截器防止写入冲突
             * 推荐通过 [DevHttpCapture.addInterceptor] 进行添加拦截器
             */
            addInterceptor(StorageInterceptor(SHARE_MODULE))
            /**
             * [StorageInterceptor] 其他构造参数可参考 [eventIMPL]、[callbackInterceptorEventFilter]
             */
        })
        // 请求文章列表
        requestArticleList(apiService)
        // 请求搜索热词列表
        requestHotkeys(apiService)
        // 请求 Banner 列表
        requestBanner(apiService)
    }

    // Http 抓包拦截器 ( DevHttpCapture API )
    val clickStorageInterceptorDefault = View.OnClickListener { view ->
        val demoAPI = false
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            /**
             * 主要就是以下四个方法
             *
             * // 添加 Http 抓包拦截处理 ( 默认进行抓包 )
             * [DevHttpCapture.addInterceptor]
             * // 更新对应 Module Http 抓包拦截处理
             * [DevHttpCapture.updateInterceptor]
             * // 移除对应 Module Http 抓包拦截
             * [DevHttpCapture.removeInterceptor]
             * // 是否存在对应 Module Http 抓包拦截
             * [DevHttpCapture.containsInterceptor]
             *
             * 想要通过 [DevHttpCapture] API 进行控制
             * 必须先调用 [DevHttpCapture.addInterceptor] 进行添加拦截器
             * 内部有个 [Map] 存储拦截器对象并根据不同方法进行操作更新
             *
             * 也可以通过 [Map] + [StorageInterceptor] 自行实现
             */

            // 添加 Http 抓包拦截处理 ( 默认进行抓包 )
            DevHttpCapture.addInterceptor(
                builder = this, moduleName = LOGIN_MODULE
            )
            /**
             * 以下代码不执行用于演示
             */
            if (demoAPI) {
                /**
                 * 更新对应 Module Http 抓包拦截处理 ( false 表示不进行抓包 )
                 * 可以再次更新为 true 表示需要抓包存储
                 */
                DevHttpCapture.updateInterceptor(LOGIN_MODULE, false)
                // 移除对应 Module Http 抓包拦截
                DevHttpCapture.removeInterceptor(LOGIN_MODULE)
                // 是否存在对应 Module Http 抓包拦截
                DevHttpCapture.containsInterceptor(LOGIN_MODULE)
            }
        })
        // 请求文章列表
        requestArticleList(apiService)
        // 请求搜索热词列表
        requestHotkeys(apiService)
        // 请求 Banner 列表
        requestBanner(apiService)
    }

    // ===============
    // = 抓包数据可视化 =
    // ===============

    //【默认】抓包数据可视化
    val clickDefaultIMPL = View.OnClickListener { view ->
        DevHttpCaptureCompiler.start(view.context)
    }

    //【默认】抓包数据可视化 ( 具体模块 )
    val clickDefaultIMPLModule = View.OnClickListener { view ->
        DevHttpCaptureCompiler.start(view.context, SHARE_MODULE)
    }

    //【自定义】抓包数据可视化
    val clickCustomIMPL = View.OnClickListener { view ->
        toast_showLong(text = "请查看点击事件【注释】")
        /**
         * 如果对 [StorageInterceptor] 存储性能以及逻辑实现代码，觉得太过复杂不够简洁优美
         * 可以通过 [IHttpCaptureEnd] 回调信息 [CaptureInfo] 写入本地文件、数据库
         */
        /**
         * 根据回调 [CaptureInfo] 抓包数据调用 [CaptureInfo.toJson] 转换 JSON 传入数据库
         * 并实现对应的增删改查逻辑, 显示到对应的可视化页面上
         */
    }
}