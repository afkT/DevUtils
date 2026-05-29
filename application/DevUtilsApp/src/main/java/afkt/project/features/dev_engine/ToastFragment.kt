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
        toast_showShort(text = "showShort 短吐司")
    }

    val clickShowLong = View.OnClickListener {
        toast_showLong(text = "showLong 长吐司")
    }

    val clickShow = View.OnClickListener {
        toast_show(text = "show 默认吐司")
    }

    val clickDelayedShow = View.OnClickListener {
        toast_delayedShow(text = "delayedShow 延时 2 秒显示", delayMillis = 2000L)
    }

    val clickGravityCenter = View.OnClickListener {
        toast_setGravity(gravity = Gravity.CENTER, xOffset = 0, yOffset = 0)
        toast_showShort(text = "setGravity 居中显示")
    }

    val clickCancel = View.OnClickListener {
        toast_cancel()
    }
}