package dev.capture

import dev.utils.common.ThrowableUtils
import okhttp3.*
import okhttp3.internal.http.promisesBody
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.nio.charset.StandardCharsets

/**
 * detail: Http 抓包事件回调实现类
 * @author Ttt
 */
abstract class HttpCaptureEventIMPL : IHttpCaptureEvent {

    // =====================
    // = IHttpCaptureEvent =
    // =====================

    // ===========
    // = request =
    // ===========

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
        // POST http/1.1 ( 6 byte body )
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
        requestBody: RequestBody?,
        captureRedact: CaptureRedact
    ): LinkedHashMap<String, String> {
        val map = linkedMapOf<String, String>()
        requestBody?.let { body ->
            body.contentType()?.let { type ->
                if (headers[CONTENT_TYPE] == null) {
                    map[CONTENT_TYPE] = type.toString()
                }
            }
            val length = body.contentLength()
            if (length != -1L) {
                if (headers[CONTENT_LENGTH] == null) {
                    map[CONTENT_LENGTH] = length.toString()
                }
            }
        }
        map.putAll(
            mapHeader(
                headers, captureRedact.requestHeader,
                captureRedact.replaceValue
            )
        )
        return map
    }

    override fun callRequestBody(
        request: Request,
        requestBody: RequestBody?,
        captureRedact: CaptureRedact
    ): LinkedHashMap<String, String> {
        val map = linkedMapOf<String, String>()
        if (requestBody == null) {
            map[END] = "${request.method} requestBody is null"
        } else if (bodyHasUnknownEncoding(request.headers)) {
            map[END] = "${request.method} ( encoded body omitted )"
        } else if (requestBody.isDuplex()) {
            map[END] = "${request.method} ( duplex request body omitted )"
        } else if (requestBody.isOneShot()) {
            map[END] = "${request.method} ( one-shot body omitted )"
        } else {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            if (isProbablyUtf8(buffer)) {
                when (requestBody) {
                    is FormBody -> {
                        map.putAll(
                            mapFormBody(
                                requestBody,
                                captureRedact.requestBody,
                                captureRedact.replaceValue
                            )
                        )
                    }
                    is MultipartBody -> {
                        map.putAll(
                            mapMultipartBody(
                                requestBody,
                                captureRedact.requestBody,
                                captureRedact.replaceValue
                            )
                        )
                    }
                    else -> {
                        val contentType = requestBody.contentType()
                        val charset = contentType?.charset(
                            StandardCharsets.UTF_8
                        ) ?: StandardCharsets.UTF_8
                        map[BODY_STRING] = buffer.readString(charset)
                    }
                }
                map[END] = "${request.method} ( ${requestBody.contentLength()}-byte body )"
            } else {
                map[END] =
                    "${request.method} ( binary ${requestBody.contentLength()}-byte body omitted )"
            }
        }
        return map
    }

    // ============
    // = response =
    // ============

    override fun callResponseStatus(
        request: Request,
        response: Response,
        responseBody: ResponseBody,
        tookMs: Long
    ): LinkedHashMap<String, String> {
        val contentLength = responseBody.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"

        val map = linkedMapOf<String, String>()
        map[STATUS] = response.code.toString() + " " + response.message
        map[TIME] = tookMs.toString() + "ms"
        map[BODY_SIZE] = bodySize
        return map
    }

    override fun callResponseHeaders(
        request: Request,
        response: Response,
        headers: Headers,
        responseBody: ResponseBody,
        captureRedact: CaptureRedact
    ): LinkedHashMap<String, String> {
        return mapHeader(
            headers, captureRedact.responseHeader,
            captureRedact.replaceValue
        )
    }

    override fun callResponseBodyFailed(
        request: Request,
        error: Exception
    ): String {
        return "HTTP FAILED: $error"
    }

    override fun callResponseBody(
        request: Request,
        response: Response,
        responseBody: ResponseBody
    ): String {
        if (!response.promisesBody()) {
            return "END HTTP ( promisesBody )"
        } else if (bodyHasUnknownEncoding(response.headers)) {
            return "END HTTP ( encoded body omitted )"
        } else {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            var buffer = source.buffer

            val headers = response.headers
            var gzippedLength: Long? = null
            if (GZIP.equals(headers[CONTENT_ENCODING], ignoreCase = true)) {
                gzippedLength = buffer.size
                GzipSource(buffer.clone()).use { gzippedResponseBody ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                }
            }

            val contentType = responseBody.contentType()
            val charset = contentType?.charset(
                StandardCharsets.UTF_8
            ) ?: StandardCharsets.UTF_8

            if (!isProbablyUtf8(buffer)) {
                return "END HTTP ( binary ${buffer.size}-byte body omitted )"
            }

            val contentLength = responseBody.contentLength()
            if (contentLength != 0L) {
                return buffer.clone().readString(charset)
            }
            return if (gzippedLength != null) {
                "END HTTP ( ${buffer.size}-byte, $gzippedLength-gzipped-byte body )"
            } else {
                "END HTTP ( ${buffer.size}-byte body )"
            }
        }
    }

    // ==========
    // = 通用方法 =
    // ==========

    companion object {

        const val END = "END"
        const val GZIP = "gzip"
        const val IDENTITY = "identity"
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_LENGTH = "Content-Length"
        const val CONTENT_ENCODING = "Content-Encoding"
        const val CONTENT_DISPOSITION = "Content-Disposition"
        const val REDACT_REPLACE_VALUE = "██"
        const val DISPOSITION = "disposition"
        const val DISPOSITION_END = "\""
        const val DISPOSITION_FRONT = "form-data; name=\""
        const val TIME = "time"
        const val STATUS = "status"
        const val BODY_SIZE = "body-size"
        const val BODY_STRING = "body-string"

        /**
         * 判断 body 是否未知编码
         * @param headers 头信息
         * @return `true` yes, `false` no
         */
        fun bodyHasUnknownEncoding(headers: Headers): Boolean {
            val contentEncoding = headers[CONTENT_ENCODING] ?: return false
            return !contentEncoding.equals(IDENTITY, ignoreCase = true) &&
                    !contentEncoding.equals(GZIP, ignoreCase = true)
        }

        /**
         * 判断 Buffer 内容是否 UTF-8 编码
         * @param buffer Buffer
         * @return Boolean
         */
        fun isProbablyUtf8(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = buffer.size.coerceAtMost(64)
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0 until 16) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (_: EOFException) {
                return false // Truncated UTF-8 sequence.
            }
        }

        /**
         * 存储通用头信息到 Map 中
         * @param headers 头信息
         * @param headersToRedact 待隐藏字段信息
         * @param replaceValue 隐藏信息替换值
         * @return LinkedHashMap<String, String>
         */
        fun mapHeader(
            headers: Headers,
            headersToRedact: Set<String>,
            replaceValue: String = REDACT_REPLACE_VALUE
        ): LinkedHashMap<String, String> {
            val map = linkedMapOf<String, String>()
            for (i in 0 until headers.size) {
                val name = headers.name(i)
                if (name in headersToRedact) {
                    map[name] = replaceValue
                } else {
                    map[name] = headers.value(i)
                }
            }
            return map
        }

        /**
         * 存储通用 Body 信息到 Map 中
         * @param body [FormBody]
         * @param bodyToRedact 待隐藏字段信息
         * @param replaceValue 隐藏信息替换值
         * @return LinkedHashMap<String, String>
         */
        fun mapFormBody(
            body: FormBody,
            bodyToRedact: Set<String>,
            replaceValue: String = REDACT_REPLACE_VALUE
        ): LinkedHashMap<String, String> {
            val map = linkedMapOf<String, String>()
            for (i in 0 until body.size) {
                val name = body.name(i)
                if (name in bodyToRedact) {
                    map[name] = replaceValue
                } else {
                    map[name] = body.value(i)
                }
            }
            return map
        }

        /**
         * 存储通用 Body 信息到 Map 中
         * @param body [MultipartBody]
         * @param bodyToRedact 待隐藏字段信息
         * @param replaceValue 隐藏信息替换值
         * @return LinkedHashMap<String, String>
         */
        fun mapMultipartBody(
            body: MultipartBody,
            bodyToRedact: Set<String>,
            replaceValue: String = REDACT_REPLACE_VALUE
        ): LinkedHashMap<String, String> {
            val contentType = body.contentType()
            val charset = contentType.charset(
                StandardCharsets.UTF_8
            ) ?: StandardCharsets.UTF_8

            val mapKeyCount = linkedMapOf<String, Int>()
            val mapDisposition = linkedMapOf<String, String>()

            val map = linkedMapOf<String, String>()
            for (i in 0 until body.size) {
                val part = body.part(i)
                part.headers?.let { headers ->
                    val disposition = dispositionByHeaders(headers)
                    val name = dispositionNameSplit(disposition)
                    val key = if (mapKeyCount.contains(name)) {
                        val count = (mapKeyCount[name] ?: 1) + 1
                        mapKeyCount[name] = count
                        "$name-$count"
                    } else {
                        mapKeyCount[name] = 1
                        name
                    }
                    if (name in bodyToRedact) {
                        map[key] = replaceValue
                    } else {
                        val buffer = Buffer()
                        part.body.writeTo(buffer)
                        map[key] = buffer.readString(charset)
                    }
                    if (disposition != name) {
                        mapDisposition["${key}-${DISPOSITION}"] = disposition
                    }
                }
            }
            map.putAll(mapDisposition)
            return map
        }

        /**
         * 通过 Headers 获取 Content-Disposition
         * @param headers 头信息
         * @return Content-Disposition
         */
        fun dispositionByHeaders(headers: Headers): String {
            return headers[CONTENT_DISPOSITION] ?: headers.toString()
        }

        /**
         * 通过 Headers disposition 裁剪获取 name
         * @param headers 头信息
         * @return name
         */
        fun dispositionNameSplit(headers: Headers): String {
            return dispositionNameSplit(dispositionByHeaders(headers))
        }

        /**
         * 通过 Headers disposition 裁剪获取 name
         * @param disposition Content-Disposition
         * @return name
         */
        fun dispositionNameSplit(disposition: String): String {
            // Content-Disposition: form-data; name=\"xxx\" => xxx
            val frontIndex = disposition.indexOf(DISPOSITION_FRONT)
            if (frontIndex != -1) {
                val startIndex = frontIndex + DISPOSITION_FRONT.length
                val endIndex = disposition.indexOf(DISPOSITION_END, startIndex)
                if (endIndex != -1 && startIndex < endIndex) {
                    return disposition.substring(startIndex, endIndex)
                }
            }
            return disposition
        }
    }
}

/**
 * detail: 抓包数据本地存储实现 Engine
 * @author Ttt
 */
internal class HttpCaptureStorageEngine(
    private val eventIMPL: IHttpCaptureEvent
) {

    /**
     * 抓包数据存储
     * @param base 通用 Http 抓包拦截器
     * @param info 抓包信息封装类
     * @param requestTime 开始请求时间
     */
    fun captureStorage(
        base: BaseInterceptor,
        info: CaptureInfo,
        requestTime: Long
    ) {
        if (base.isStorageHttpCaptureType()) {
            // 创建抓包文件信息
            val captureFile = CaptureFile()
                .setUrl(info.requestUrl)
                .setMethod(info.requestMethod)
                .setEncrypt(base.getEncrypt() != null)
                .setModuleName(base.getModuleName())
                .setTime(requestTime)

            var httpCaptureData = info.toJson()
            // 如果存在加密则进行处理
            base.getEncrypt()?.let { encrypt ->
                httpCaptureData = try {
                    val bytes = encrypt.encrypt(httpCaptureData?.toByteArray())
                    String(bytes)
                } catch (e: Exception) {
                    ThrowableUtils.getThrowable(e)
                }
            }
            // 保存抓包数据
            captureFile.httpCaptureData = httpCaptureData
            // 存储文件
            Utils.saveHttpCaptureFile(captureFile)
        }
        // 抓包结束
        eventIMPL.callEnd(info)
    }
}