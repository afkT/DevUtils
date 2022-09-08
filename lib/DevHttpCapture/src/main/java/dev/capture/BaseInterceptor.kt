package dev.capture

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * detail: 通用 Http 抓包拦截器
 * @author Ttt
 */
abstract class BaseInterceptor(
    private val eventIMPL: IHttpCaptureEvent
) : Interceptor,
    IHttpCapture {

    // 抓包数据本地存储实现 Engine
    private val storageEngine = HttpCaptureStorageEngine(eventIMPL)

    // ============
    // = abstract =
    // ============

    /**
     * 是否属于存储抓包数据类型
     * @return `true` yes, `false` no
     */
    abstract fun isStorageHttpCaptureType(): Boolean

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
            } catch (ignored: Exception) {
            }
            if (isFilter) {
                // 不需要抓包直接返回
                return chain.proceed(chain.request())
            }
        }

        // ===========
        // = request =
        // ===========

        // 请求链接
        captureInfo.requestUrl = eventIMPL.callRequestUrl(
            request, requestUrl
        )
        // 请求方法
        captureInfo.requestMethod = eventIMPL.callRequestMethod(
            request, requestMethod, protocol, requestBody
        )
        // 请求头信息
        captureInfo.requestHeader.putAll(
            eventIMPL.callRequestHeaders(
                request, requestHeaders, requestBody,
                captureRedact()
            )
        )
        // 请求体数据
        captureInfo.requestBody.putAll(
            eventIMPL.callRequestBody(
                request, requestBody, captureRedact()
            )
        )
        // 请求时间
        val requestTime = System.currentTimeMillis()

        // ============
        // = response =
        // ============

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            // 响应数据
            captureInfo.responseBody = eventIMPL.callResponseBodyFailed(
                request, e
            )
            // 抓包数据存储
            storageEngine.captureStorage(this, captureInfo, requestTime)
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body!!
        val responseHeaders = response.headers

        // 响应状态
        captureInfo.responseStatus.putAll(
            eventIMPL.callResponseStatus(
                request, response, responseBody, tookMs
            )
        )
        // 响应头信息
        captureInfo.responseHeader.putAll(
            eventIMPL.callResponseHeaders(
                request, response, responseHeaders,
                responseBody, captureRedact()
            )
        )
        // 响应数据
        captureInfo.responseBody = eventIMPL.callResponseBody(
            request, response, responseBody
        )
        // 抓包数据存储
        storageEngine.captureStorage(this, captureInfo, requestTime)
        return response
    }
}