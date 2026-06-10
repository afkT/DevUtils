package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEnginePopTipBinding
import android.view.View
import dev.engine.core.poptip.PopTipConst
import dev.engine.core.poptip.PopTipItem
import dev.engine.extensions.poptip.poptip_show
import dev.engine.extensions.toast.toast_showShort
import dev.engine.poptip.IPopTipEngine

/**
 * detail: PopTip Engine 非阻断式文本提示
 * @author Ttt
 */
class PopTipFragment : AppFragment<FragmentDevEnginePopTipBinding, PopTipViewModel>(
    R.layout.fragment_dev_engine_pop_tip, BR.viewModel
)

/**
 * detail: PopTip Engine 非阻断式文本提示 ViewModel
 * @author Ttt
 */
class PopTipViewModel : AppViewModel() {

    val clickShow = View.OnClickListener {
        poptip_show(text = "show 默认 PopTip")
    }

    val clickShowSuccess = View.OnClickListener {
        PopTipItem.create("操作成功 PopTip")
            .setIconState(PopTipConst.ICON_SUCCESS)
            .poptip_show()
    }

    val clickShowWarning = View.OnClickListener {
        PopTipItem.create("警告提示 PopTip")
            .setIconState(PopTipConst.ICON_WARNING)
            .poptip_show()
    }

    val clickShowError = View.OnClickListener {
        PopTipItem.create("错误提示 PopTip")
            .setIconState(PopTipConst.ICON_ERROR)
            .poptip_show()
    }

    val clickShowButton = View.OnClickListener {
        PopTipItem.create("含按钮 PopTip")
            .setButtonText("确定")
            .setOnButtonClickListener(object : IPopTipEngine.OnButtonClickListener {
                override fun onClick(
                    dialog: Any?,
                    view: View?
                ): Boolean {
                    toast_showShort(text = "点击了 PopTip 按钮")
                    return false
                }
            }).poptip_show()
    }

    val clickShowAlignTop = View.OnClickListener {
        PopTipItem.create("顶部对齐 PopTip")
            .setAlign(PopTipConst.ALIGN_TOP)
            .poptip_show()
    }

    val clickShowAlignBottom = View.OnClickListener {
        PopTipItem.create("底部对齐 PopTip")
            .setAlign(PopTipConst.ALIGN_BOTTOM)
            .poptip_show()
    }
}