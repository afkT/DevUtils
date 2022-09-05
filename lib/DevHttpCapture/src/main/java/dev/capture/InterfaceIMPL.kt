package dev.capture

import okhttp3.*

/**
 * detail: Http 抓包事件回调实现类
 * @author Ttt
 */
open class HttpCaptureEventIMPL : IHttpCaptureEvent {

    // =====================
    // = IHttpCaptureEvent =
    // =====================

    override fun callRequestUrl(
        request: Request,
        url: HttpUrl
    ): String {
        // https://???.xxx
        return url.toString()
    }

    override fun callRequestMethod(
        request: Request,
        method: String,
        protocol: Protocol,
        requestBody: RequestBody?
    ): String {
        // POST http/1.1 (6 byte body)
        val builder = StringBuilder()
            .append(method).append(" ")
            .append(protocol)
        requestBody?.let { body ->
            builder.append(" ( ")
                .append(body.contentLength())
                .append(" byte body )")
        }
        return builder.toString()
    }

    override fun callRequestHeaders(
        request: Request,
        headers: Headers,
        requestBody: RequestBody?
    ): LinkedHashMap<String, String> {
        val map = LinkedHashMap<String, String>()
        requestBody?.let { body ->
            body.contentType()?.let { type ->
                if (headers["Content-Type"] == null) {
                    map["Content-Type"] = type.toString()
                }
            }
            val length = body.contentLength()
            if (length != -1L) {
                if (headers["Content-Length"] == null) {
                    map["Content-Length"] = length.toString()
                }
            }
        }
        for (i in 0 until headers.size) {
            val name = headers.name(i)
            val value = headers.value(i)
            map[name] = value
        }
        return map
    }

    // =

    override fun callEnd(captureInfo: CaptureInfo) {
        TODO("Not yet implemented")
    }
}