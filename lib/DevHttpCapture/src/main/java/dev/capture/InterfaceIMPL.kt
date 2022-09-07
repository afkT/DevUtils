package dev.capture

import okhttp3.*
import okio.Buffer
import java.io.EOFException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

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
        val map = LinkedHashMap<String, String>()
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
        headers: Headers,
        requestBody: RequestBody?,
        captureRedact: CaptureRedact
    ): LinkedHashMap<String, String> {
        val map = LinkedHashMap<String, String>()
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
                val contentType = requestBody.contentType()
                val charset: Charset = contentType?.charset(
                    StandardCharsets.UTF_8
                ) ?: StandardCharsets.UTF_8

                if (requestBody is FormBody) {
                    map.putAll(
                        mapFormBody(
                            requestBody,
                            captureRedact.requestBody,
                            captureRedact.replaceValue
                        )
                    )
                } else if (requestBody is MultipartBody) {
                    map.putAll(
                        mapMultipartBody(
                            requestBody,
                            captureRedact.requestBody,
                            captureRedact.replaceValue
                        )
                    )
                } else {

                }
//                logger.log(buffer.readString(charset))
                map[END] = "${request.method} ( ${requestBody.contentLength()}-byte body )"
            } else {
                map[END] =
                    "${request.method} ( binary ${requestBody.contentLength()}-byte body omitted )"
            }
        }
        return map
    }

    // =

    override fun callEnd(captureInfo: CaptureInfo) {
        TODO("Not yet implemented")
    }

    // ==========
    // = 通用方法 =
    // ==========

    companion object {

        const val END = "END"
        const val BODY = "body-string"
        const val GZIP = "gzip"
        const val IDENTITY = "identity"
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_LENGTH = "Content-Length"
        const val CONTENT_ENCODING = "Content-Encoding"
        const val REDACT_REPLACE_VALUE = "██"
        const val DISPOSITION = "disposition"
        const val DISPOSITION_FRONT = "form-data; name="
        const val DISPOSITION_END = ";"

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
            val map = LinkedHashMap<String, String>()
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
            val map = LinkedHashMap<String, String>()
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
            val charset: Charset = contentType.charset(
                StandardCharsets.UTF_8
            ) ?: StandardCharsets.UTF_8

            val map = LinkedHashMap<String, String>()
            for (i in 0 until body.size) {
                val part = body.part(i)

                val disposition = part.headers.toString()
                var name = disposition
                val frontIndex = name.indexOf(DISPOSITION_FRONT)
                val endIndex = name.indexOf(DISPOSITION_END)
                if (frontIndex != -1 && endIndex != -1 && frontIndex < endIndex) {
                    name = name.substring(frontIndex, endIndex)
                    map["${name}-${DISPOSITION}"] = disposition
                }
                if (name in bodyToRedact) {
                    map[name] = replaceValue
                } else {
                    val buffer = Buffer()
                    part.body.writeTo(buffer)
                    map[name] = buffer.readString(charset)
                }
            }
            return map
        }
    }
}