package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.BaseViewRecyclerviewBinding

/**
 * detail: 奔溃日志捕获
 * @author Ttt
 */
class CrashCatchFragment : AppFragment<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is CrashCatchFragment) {
//            it.apply {
//                /**
//                 * 捕获异常处理 CrashUtils.getInstance().initialize()
//                 * 参考 [BaseApplication.initCrash]
//                 */
//                binding.vidRv.bindAdapter(ButtonList.crashButtonValues) { buttonValue ->
//                    when (buttonValue.type) {
//                        ButtonValue.BTN_CRASH_CLICK_CATCH -> {
//                            val data: String? = null
//                            data!!.split(",".toRegex()).toTypedArray()
//                        }
//
//                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
//                    }
//                }
//            }
        }
    }
)