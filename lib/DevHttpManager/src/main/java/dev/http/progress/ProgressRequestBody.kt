package dev.http.progress

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

/**
 * detail: 进度监听请求体 RequestBody
 * @author Ttt
 * 通过此类获取 OkHttp 请求体数据处理进度, 可以处理任何的 RequestBody
 */
class ProgressRequestBody : RequestBody() {

    override fun contentType(): MediaType? {
        TODO("Not yet implemented")
    }

    override fun writeTo(sink: BufferedSink) {
        TODO("Not yet implemented")
    }
}