package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineLogBinding
import android.view.View
import dev.engine.extensions.log.*
import dev.engine.extensions.toast.toast_showShort

/**
 * detail: Log Engine 日志打印
 * @author Ttt
 */
class LogFragment : AppFragment<FragmentDevEngineLogBinding, LogViewModel>(
    R.layout.fragment_dev_engine_log, BR.viewModel
)

/**
 * detail: Log Engine 日志打印 ViewModel
 * @author Ttt
 */
class LogViewModel : AppViewModel() {

    private companion object {

        const val TAG = "LogEngine"
    }

    val clickLogDebug = View.OnClickListener {
        "Log Engine debug 日志打印".log_dTag(tag = TAG)
        printToast()
    }

    val clickLogError = View.OnClickListener {
        RuntimeException("mock error").log_eTag(
            tag = TAG,
            message = "Log Engine error 日志打印"
        )
        printToast()
    }

    val clickLogWarn = View.OnClickListener {
        "Log Engine warn 日志打印".log_wTag(tag = TAG)
        printToast()
    }

    val clickLogInfo = View.OnClickListener {
        "Log Engine info 日志打印".log_iTag(tag = TAG)
        printToast()
    }

    val clickLogJson = View.OnClickListener {
        "{\"name\":\"DevUtils\",\"star\":true}".log_jsonTag(tag = TAG)
        printToast()
    }

    val clickLogXml = View.OnClickListener {
        "<dev><name>DevUtils</name></dev>".log_xmlTag(tag = TAG)
        printToast()
    }

    private fun printToast() {
        "数据已打印, 请查看 Logcat".toast_showShort()
    }
}