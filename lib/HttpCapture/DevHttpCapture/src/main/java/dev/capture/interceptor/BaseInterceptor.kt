package dev.capture.interceptor

import dev.capture.*
import dev.capture.interfaces.HttpCaptureStorageEngine
import dev.capture.interfaces.IHttpCapture
import dev.capture.interfaces.IHttpCaptureEvent
import dev.capture.interfaces.IHttpCaptureEventFilter
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * detail: Base Http 抓包拦截器
 * @author Ttt
 */
abstract class BaseInterceptor(
    // 是否属于存储抓包数据类型
    private val storageHttpCaptureType: Boolean,
    // Http 抓包事件回调
    private val eventIMPL: IHttpCaptureEvent,
    // Http 抓包事件处理拦截
    private val eventFilter: IHttpCaptureEventFilter
) : Interceptor,
    IHttpCapture {

    // 抓包数据本地存储实现 Engine
    private val storageEngine = HttpCaptureStorageEngine(
        eventIMPL, eventFilter
    )

    /**
     * 是否属于存储抓包数据类型
     * @return `true` yes, `false` no
     */
    fun isStorageHttpCaptureType(): Boolean {
        return storageHttpCaptureType
    }

    // ===============
    // = Interceptor =
    // ===============

    final override fun intercept(chain: Interceptor.Chain): Response {
        // 如果属于存储抓包数据类型, 但是不需要抓包则直接返回
        if (isStorageHttpCaptureType() && !isCapture()) {
            return chain.proceed(chain.request())
        }
        return innerResponse(chain)
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 统一抓包逻辑代码
     */
    private fun innerResponse(chain: Interceptor.Chain): Response {
        // 抓包信息封装类
        val captureInfo = CaptureInfo()

        val request = chain.request()
        val requestUrl = request.url
        val requestBody = request.body
        val requestMethod = request.method
        val requestHeaders = request.headers

        val connection = chain.connection()
        val protocol = connection?.protocol() ?: Protocol.HTTP_1_1

        // =================
        // = Http 拦截过滤器 =
        // =================

        val httpFilter = getHttpFilter()
        if (httpFilter != null) {
            var isFilter = false
            try {
                // 是否过滤该 Http 请求不进行抓包存储
                isFilter = httpFilter.filter(
                    request, requestUrl, requestMethod,
                    protocol, requestHeaders
                )
            } catch (_: Exception) {
            }
            if (isFilter) {
                // 不需要抓包直接返回
                return chain.proceed(chain.request())
            }
        }

        // ===========
        // = request =
        // ===========

        // 是否过滤请求链接字符串
        if (!eventFilter.filterRequestUrl(
                request, requestUrl, requestMethod,
                protocol, requestHeaders, requestBody
            )
        ) {
            //【请求链接】
            captureInfo.requestUrl = eventIMPL.callRequestUrl(
                request, requestUrl
            )
        }

        // 是否过滤请求方法字符串
        if (!eventFilter.filterRequestMethod(
                request, requestUrl, requestMethod,
                protocol, requestHeaders, requestBody
            )
        ) {
            //【请求方法】
            captureInfo.requestMethod = eventIMPL.callRequestMethod(
                request, requestMethod, protocol, requestBody
            )
        }

        // 是否过滤请求头信息 Map
        if (!eventFilter.filterRequestHeaders(
                request, requestUrl, requestMethod,
                protocol, requestHeaders, requestBody
            )
        ) {
            //【请求头信息】
            captureInfo.requestHeader.putAll(
                eventIMPL.callRequestHeaders(
                    request, requestHeaders, requestBody,
                    captureRedact()
                )
            )
        }

        // 是否过滤请求体信息 Map
        if (!eventFilter.filterRequestBody(
                request, requestUrl, requestMethod,
                protocol, requestHeaders, requestBody
            )
        ) {
            //【请求体数据】
            captureInfo.requestBody.putAll(
                eventIMPL.callRequestBody(
                    request, requestBody, captureRedact()
                )
            )
        }

        //【请求时间】
        val requestTime = System.currentTimeMillis()

        // ============
        // = response =
        // ============

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            // 是否过滤错误响应体信息
            if (!eventFilter.filterResponseBodyFailed(
                    request, e
                )
            ) {
                //【响应数据】
                captureInfo.responseBody = eventIMPL.callResponseBodyFailed(
                    request, e
                )
            }
            // 抓包数据存储
            storageEngine.captureStorage(this, captureInfo, requestTime)
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body!!
        val responseHeaders = response.headers

        // 是否过滤响应状态 Map
        if (!eventFilter.filterResponseStatus(
                request, response, responseHeaders, responseBody
            )
        ) {
            //【响应状态】
            captureInfo.responseStatus.putAll(
                eventIMPL.callResponseStatus(
                    request, response, responseBody, tookMs
                )
            )
        }

        // 是否过滤响应头信息 Map
        if (!eventFilter.filterResponseHeaders(
                request, response, responseHeaders, responseBody
            )
        ) {
            //【响应头信息】
            captureInfo.responseHeader.putAll(
                eventIMPL.callResponseHeaders(
                    request, response, responseHeaders,
                    responseBody, captureRedact()
                )
            )
        }

        // 是否过滤响应体信息 Map
        if (!eventFilter.filterResponseBody(
                request, response, responseHeaders, responseBody
            )
        ) {
            //【响应数据】
            captureInfo.responseBody = eventIMPL.callResponseBody(
                request, response, responseBody
            )
        }

        // 抓包数据存储
        storageEngine.captureStorage(this, captureInfo, requestTime)
        return response
    }
}