package ktx.dev.other.http

import dev.engine.json.DevJSONEngine
import dev.utils.LogPrintUtils
import okhttp3.*
import okio.Buffer
import okio.EOFException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * detail: OkHttp 打印日志拦截器
 * @author Ttt
 * 推荐使用 DevHttpCapture 库
 */
class HttpLoggingInterceptor : Interceptor {

    private val UTF8 = Charset.forName("UTF-8")

    override fun intercept(chain: Interceptor.Chain): Response {
        val captureEntity = CaptureEntity()

        val request: Request = chain.request()
        val requestBody: RequestBody? = request.body

        val connection: Connection? = chain.connection()
        val protocol: Protocol = connection?.protocol() ?: Protocol.HTTP_1_1

        val requestStartMessage = StringBuilder()
        requestStartMessage.append(request.method).append(" ").append(protocol)
        requestBody?.let {
            requestStartMessage.append(" (")
                .append(it.contentLength())
                .append(" byte body)")
        }

        // ==========
        // = 请求信息 =
        // ==========

        captureEntity.requestMethod = requestStartMessage.toString()
        captureEntity.requestUrl = request.url.toString()
        requestBody?.let {
            if (it.contentType() != null) {
                captureEntity.requestHeader["Content-Type"] = it.contentType().toString()
            }
            if (it.contentLength() != -1L) {
                captureEntity.requestHeader["Content-Length"] =
                    it.contentLength().toString()
            }
        }

        request.headers.forEach {
            val name = it.first
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equals(name, ignoreCase = true)
                && !"Content-Length".equals(name, ignoreCase = true)
            ) {
                captureEntity.requestHeader[name] = it.second
            }
        }

        if (!bodyEncoded(request.headers)) {
            requestBody?.let {
                val buffer = Buffer()
                it.writeTo(buffer)
                if (isPlaintext(buffer)) {
                    if (requestBody is FormBody) {
                        val formBody = requestBody
                        for (i in 0 until formBody.size) {
                            captureEntity.requestBody[formBody.name(i)] = formBody.value(i)
                        }
                    }
                    val bodyBuilder = java.lang.StringBuilder()
                        .append(request.method)
                        .append(" (").append(requestBody.contentLength())
                        .append("- byte body)")
                    captureEntity.requestBody["body length"] = bodyBuilder.toString()
                } else {
                    val bodyBuilder = java.lang.StringBuilder()
                        .append(request.method)
                        .append(" (binary ").append(requestBody.contentLength())
                        .append("- byte body omitted)")
                    captureEntity.requestBody["body length"] = bodyBuilder.toString()
                }
            }
        }

        // ==========
        // = 响应信息 =
        // ==========

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            captureEntity.responseBody = "HTTP FAILED:$e"
            _finalPrintLog(captureEntity)
            throw e
        }
        val tookMs: Long = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        captureEntity.responseStatus["status"] = response.code.toString() + " " + response.message
        captureEntity.responseStatus["time"] = tookMs.toString() + "ms"

        response.body?.let { responseBody ->
            response.headers.forEach {
                captureEntity.responseHeader[it.first] = it.second
            }

            if (!bodyEncoded(response.headers)) {
                val contentLength = responseBody.contentLength()
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer
                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                if (!isPlaintext(buffer)) {
                    captureEntity.responseBody = "非文本信息"
                    _finalPrintLog(captureEntity)
                    return response
                }
                if (contentLength != 0L) {
                    try {
                        captureEntity.responseBody = buffer.clone().readString(charset)
                    } catch (e: Exception) {
                        captureEntity.responseBody = "buffer readString error"
                    }
                }
                captureEntity.responseStatus["body length"] = "${buffer.size} byte body"
            }
        }
        _finalPrintLog(captureEntity)
        return response
    }

    // =============
    // = 日志打印方法 =
    // =============

    /**
     * 最终输出日志方法
     * @param captureEntity 捕获信息实体类
     */
    private fun _finalPrintLog(captureEntity: CaptureEntity?) {
        if (captureEntity != null) {
            LogPrintUtils.json(
                DevJSONEngine.getEngine().toJson(captureEntity)
            )
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    private inner class CaptureEntity {
        // 请求方法
        var requestMethod = ""

        // 请求链接
        var requestUrl = ""

        // 请求头信息
        var requestHeader: MutableMap<String, String> = HashMap()

        // 请求体
        var requestBody: MutableMap<String, String> = HashMap()

        // 响应状态
        var responseStatus: MutableMap<String, String> = HashMap()

        // 响应头信息
        var responseHeader: MutableMap<String, String> = HashMap()

        // 响应体
        var responseBody = ""
    }

    // =

    private fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = if (buffer.size < 64) buffer.size else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            true
        } catch (e: EOFException) {
            false // Truncated UTF-8 sequence.
        }
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }
}