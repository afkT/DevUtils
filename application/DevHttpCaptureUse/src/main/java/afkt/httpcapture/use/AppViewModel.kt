package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseViewModel
import android.view.View
import androidx.databinding.ObservableField
import dev.DevHttpCapture
import dev.capture.CallbackInterceptor
import dev.capture.CaptureInfo
import dev.capture.IHttpCaptureEnd
import dev.expand.engine.log.log_jsonTag
import dev.retrofit.launchExecuteRequest
import okhttp3.OkHttpClient
import java.util.concurrent.atomic.AtomicInteger


/**
 * detail: 整个 App ViewModel
 * @author Ttt
 */
class AppViewModel : BaseViewModel() {

    val page = AtomicInteger()

    // ===============
    // = MainFragment =
    // ===============

    // DevHttpCapture:Version
    val devHttpCaptureVersion = ObservableField(
        "DevHttpCapture:${DevHttpCapture.getDevHttpCaptureVersion()}"
    )

    // CallbackInterceptor 抓包拦截器
    val clickCallbackInterceptor = View.OnClickListener { view ->
        // 创建新的 API Service【每次都创建新的方便演示】
        val apiService = RetrofitAPI.newAPI(OkHttpClient.Builder().apply {
            // 设置 CallbackInterceptor 抓包拦截器并进行网络请求
            addInterceptor(CallbackInterceptor(endCall = object : IHttpCaptureEnd {
                override fun callEnd(info: CaptureInfo) {
                    // 打印 Http 请求信息
                    val tag = "call_http_capture"
                    tag.log_jsonTag(json = info.toJson())
                }
            }))
        })
        // 发送请求
        this.launchExecuteRequest(block = {
            apiService.getArticleList(page.incrementAndGet())
        }, start = {}, success = {}, error = {}, finish = {})
    }
}