package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEnginePopNotificationBinding
import android.view.View
import com.kongzue.dialogx.DialogX
import dev.engine.core.popnotification.PopNotificationConst
import dev.engine.core.popnotification.PopNotificationItem
import dev.engine.extensions.popnotification.popnotification_show
import dev.engine.extensions.toast.toast_showShort
import dev.engine.popnotification.IPopNotificationEngine

/**
 * detail: PopNotification Engine 非阻断式通知提示
 * @author Ttt
 */
class PopNotificationFragment : AppFragment<FragmentDevEnginePopNotificationBinding, PopNotificationViewModel>(
    R.layout.fragment_dev_engine_pop_notification, BR.viewModel
)

/**
 * detail: PopNotification Engine 非阻断式通知提示 ViewModel
 * @author Ttt
 */
class PopNotificationViewModel : AppViewModel() {

    init {
        // 默认可以显示多个 PopNotification
        DialogX.onlyOnePopNotification = false
    }

    val clickShow = View.OnClickListener {
        popnotification_show(title = "show 默认 PopNotification")
    }

    val clickShowTitleMessage = View.OnClickListener {
        popnotification_show(
            title = "PopNotification 标题",
            message = "这是 PopNotification 提示内容"
        )
    }

    val clickShowSuccess = View.OnClickListener {
        PopNotificationItem.create("操作成功 PopNotification")
            .setIconState(PopNotificationConst.ICON_SUCCESS)
            .popnotification_show()
    }

    val clickShowWarning = View.OnClickListener {
        PopNotificationItem.create("警告提示 PopNotification")
            .setIconState(PopNotificationConst.ICON_WARNING)
            .popnotification_show()
    }

    val clickShowError = View.OnClickListener {
        PopNotificationItem.create("错误提示 PopNotification")
            .setIconState(PopNotificationConst.ICON_ERROR)
            .popnotification_show()
    }

    val clickShowButton = View.OnClickListener {
        PopNotificationItem.create("含按钮 PopNotification")
            .setButtonText("确定")
            .setOnButtonClickListener(object : IPopNotificationEngine.OnButtonClickListener {
                override fun onClick(
                    dialog: Any?,
                    view: View?
                ): Boolean {
                    toast_showShort(text = "点击了 PopNotification 按钮")
                    return false
                }
            }).popnotification_show()
    }

    val clickShowAlignTop = View.OnClickListener {
        PopNotificationItem.create("顶部对齐 PopNotification")
            .setAlign(PopNotificationConst.ALIGN_TOP)
            .popnotification_show()
    }

    val clickShowAlignBottom = View.OnClickListener {
        PopNotificationItem.create("底部对齐 PopNotification")
            .setAlign(PopNotificationConst.ALIGN_BOTTOM)
            .popnotification_show()
    }
}