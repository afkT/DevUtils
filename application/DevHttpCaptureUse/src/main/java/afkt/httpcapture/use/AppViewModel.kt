package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseViewModel
import androidx.databinding.ObservableField
import dev.DevHttpCapture


/**
 * detail: 整个 App ViewModel
 * @author Ttt
 */
class AppViewModel : BaseViewModel() {

    // 日志 TAG
    private val TAG = AppViewModel::class.java.simpleName

    // ===============
    // = MainFragment =
    // ===============

    // DevHttpCapture:Version
    val devHttpCaptureVersion = ObservableField(
        "DevHttpCapture:${DevHttpCapture.getDevHttpCaptureVersion()}"
    )
}