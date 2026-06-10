package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineDebugBinding
import android.view.View
import androidx.fragment.app.FragmentActivity
import dev.engine.extensions.debug.debug_attachDebug
import dev.engine.extensions.debug.debug_detachDebug
import dev.engine.extensions.debug.debug_isDisplayDebugFunction
import dev.engine.extensions.debug.debug_setDebugFunction
import dev.engine.extensions.toast.toast_showShort
import dev.utils.app.ActivityUtils

/**
 * detail: Debug Engine 编译辅助开发
 * @author Ttt
 */
class DebugFragment : AppFragment<FragmentDevEngineDebugBinding, DebugViewModel>(
    R.layout.fragment_dev_engine_debug, BR.viewModel
)

/**
 * detail: Debug Engine 编译辅助开发 ViewModel
 * @author Ttt
 * Debug Engine 默认未配置实现, 需依赖具体调试组件后注册实现
 */
class DebugViewModel : AppViewModel() {

    val clickAttachDebug = View.OnClickListener { view ->
        (ActivityUtils.getActivity(view) as? FragmentActivity)?.debug_attachDebug()
        "attachDebug 已调用".toast_showShort()
    }

    val clickDetachDebug = View.OnClickListener { view ->
        (ActivityUtils.getActivity(view) as? FragmentActivity)?.debug_detachDebug()
        "detachDebug 已调用".toast_showShort()
    }

    val clickSetDebugFunction = View.OnClickListener {
        true.debug_setDebugFunction()
        "setDebugFunction(true) 已调用".toast_showShort()
    }

    val clickIsDisplay = View.OnClickListener {
        val display = debug_isDisplayDebugFunction()
        "isDisplayDebugFunction: $display".toast_showShort()
    }
}