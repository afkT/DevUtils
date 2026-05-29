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
        TAG.log_dTag(message = "Log Engine debug 日志打印")
        printToast()
    }

    val clickLogError = View.OnClickListener {
        TAG.log_eTag(
            throwable = RuntimeException("mock error"),
            message = "Log Engine error 日志打印"
        )
        printToast()
    }

    val clickLogWarn = View.OnClickListener {
        TAG.log_wTag(message = "Log Engine warn 日志打印")
        printToast()
    }

    val clickLogInfo = View.OnClickListener {
        TAG.log_iTag(message = "Log Engine info 日志打印")
        printToast()
    }

    val clickLogJson = View.OnClickListener {
        TAG.log_jsonTag(json = "{\"name\":\"DevUtils\",\"star\":true}")
        printToast()
    }

    val clickLogXml = View.OnClickListener {
        TAG.log_xmlTag(xml = "<dev><name>DevUtils</name></dev>")
        printToast()
    }

    private fun printToast() {
        toast_showShort(text = "数据已打印, 请查看 Logcat")
    }
}