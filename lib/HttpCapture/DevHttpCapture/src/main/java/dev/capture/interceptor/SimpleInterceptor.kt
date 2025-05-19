package dev.capture.interceptor

import dev.capture.CaptureInfo
import dev.capture.interfaces.IHttpCaptureEnd

/**
 * detail: 简单的 Http 抓包拦截器 ( 无存储逻辑, 进行回调通知 )
 * @author Ttt
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