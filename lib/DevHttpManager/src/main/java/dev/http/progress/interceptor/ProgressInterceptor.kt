package dev.http.progress.interceptor

import dev.http.progress.ProgressManager
import dev.http.progress.toExtras
import dev.http.progress.wrapRequestBody
import dev.http.progress.wrapResponseBody
import okhttp3.Interceptor
import okhttp3.Response

/**
 * detail: Progress Interceptor
 * @author Ttt
 * DevHttpManager 库内部包装, 监听 上行 ( 上传、请求 )、下行 ( 下载、响应 ) 进度
 */
internal class ProgressInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val extras = request.toExtras()
        val wrapRequest = request.wrapRequestBody(
            refreshTime = ProgressManager.getRefreshTime(),
            extras = extras
        )
        val response = chain.proceed(wrapRequest)
        return response.wrapResponseBody(
            refreshTime = ProgressManager.getRefreshTime(),
            extras = extras
        )
    }
}