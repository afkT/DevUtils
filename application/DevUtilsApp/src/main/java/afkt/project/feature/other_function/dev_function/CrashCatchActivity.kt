package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.BaseApplication
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.model.data.button.ButtonList
import afkt.project.model.data.button.ButtonValue
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import com.therouter.router.Route
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 奔溃日志捕获
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.CrashCatchActivity_PATH)
class CrashCatchActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is CrashCatchActivity) {
            it.apply {
                /**
                 * 捕获异常处理 CrashUtils.getInstance().initialize()
                 * 参考 [BaseApplication.initCrash]
                 */
                binding.vidRv.bindAdapter(ButtonList.crashButtonValues) { buttonValue ->
                    when (buttonValue.type) {
                        ButtonValue.BTN_CRASH_CLICK_CATCH -> {
                            val data: String? = null
                            data!!.split(",".toRegex()).toTypedArray()
                        }

                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }
        }
    }
)