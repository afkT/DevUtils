package dev.capture.interceptor

import dev.capture.CaptureInfo
import dev.capture.interfaces.IHttpCaptureEnd

/**
 * detail: 简单的抓包回调拦截器 ( 无存储逻辑 )
 * @author Ttt
 * 继承 [CallbackInterceptor] 简化其他参数，直接回调抓包信息 [CaptureInfo]
 */
open class SimpleInterceptor(
    // Http 抓包结束回调
    _endCall: (CaptureInfo) -> Unit
) : CallbackInterceptor(
    endCall = object : IHttpCaptureEnd {
        override fun callEnd(info: CaptureInfo) {
            _endCall.invoke(info)
        }
    }
)