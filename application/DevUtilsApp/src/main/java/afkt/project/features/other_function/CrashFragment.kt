package afkt.project.features.other_function

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppContext
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.view.View

/**
 * detail: 奔溃日志捕获
 * @author Ttt
 */
class CrashFragment : AppFragment<BaseViewRecyclerviewBinding, CrashViewModel>(
    R.layout.fragment_other_function_carch, BR.viewModel
)

class CrashViewModel : AppViewModel() {

    // 点击崩溃捕获信息
    val clickCrash = View.OnClickListener { view ->
        /**
         * 捕获异常处理 CrashUtils.getInstance().initialize()
         * 参考 [AppContext.initCrash]
         */
        val data: String? = null
        data!!.split(",".toRegex()).toTypedArray()
    }
}