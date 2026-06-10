package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineToastBinding
import android.view.Gravity
import android.view.View
import dev.engine.extensions.toast.*

/**
 * detail: Toast Engine 吐司提示
 * @author Ttt
 */
class ToastFragment : AppFragment<FragmentDevEngineToastBinding, ToastViewModel>(
    R.layout.fragment_dev_engine_toast, BR.viewModel
)

/**
 * detail: Toast Engine 吐司提示 ViewModel
 * @author Ttt
 */
class ToastViewModel : AppViewModel() {

    val clickShowShort = View.OnClickListener {
        "showShort 短吐司".toast_showShort()
    }

    val clickShowLong = View.OnClickListener {
        "showLong 长吐司".toast_showLong()
    }

    val clickShow = View.OnClickListener {
        "show 默认吐司".toast_show()
    }

    val clickDelayedShow = View.OnClickListener {
        "delayedShow 延时 2 秒显示".toast_delayedShow(delayMillis = 2000L)
    }

    val clickGravityCenter = View.OnClickListener {
        Gravity.CENTER.toast_setGravity(xOffset = 0, yOffset = 0)
        "setGravity 居中显示".toast_showShort()
    }

    val clickCancel = View.OnClickListener {
        toast_cancel()
    }
}