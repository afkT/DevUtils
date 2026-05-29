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
        toast_showShort(text = "attachDebug 已调用")
    }

    val clickDetachDebug = View.OnClickListener { view ->
        (ActivityUtils.getActivity(view) as? FragmentActivity)?.debug_detachDebug()
        toast_showShort(text = "detachDebug 已调用")
    }

    val clickSetDebugFunction = View.OnClickListener {
        true.debug_setDebugFunction()
        toast_showShort(text = "setDebugFunction(true) 已调用")
    }

    val clickIsDisplay = View.OnClickListener {
        val display = debug_isDisplayDebugFunction()
        toast_showShort(text = "isDisplayDebugFunction: $display")
    }
}