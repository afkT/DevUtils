package afkt.project.features.dev_widget.view_assist

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionViewAssistBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.base.simple.extensions.asFragment
import dev.utils.app.HandlerUtils
import dev.widget.assist.ViewAssist

/**
 * detail: ViewAssist Empty ( data )
 * @author Ttt
 */
class ViewAssistEmptyFragment : AppFragment<FragmentOtherFunctionViewAssistBinding, ViewAssistEmptyViewModel>(
    R.layout.fragment_other_function_view_assist, simple_Agile = { frg ->
        frg.asFragment<ViewAssistEmptyFragment> {
            // 初始化 ViewAssist
            viewModel.initializeViewAssist(binding.vidSkeleton)
        }
    }
)

class ViewAssistEmptyViewModel : AppViewModel() {

    // View 填充辅助类
    private lateinit var viewAssist: ViewAssist

    /**
     * 初始化 ViewAssist
     */
    fun initializeViewAssist(wrapper: ViewGroup) {
        viewAssist = ViewAssist.wrap(wrapper)
        // 注册 ViewType
        registerViewType()
        // 默认显示操作中 View Type
        viewAssist.showType(TYPE_ING)
    }

    // Type: 操作中、失败、成功【可自定义 Int Type】
    private val TYPE_ING = ViewAssist.TYPE_ING
    private val TYPE_EMPTY_DATA = Int.MIN_VALUE

    /**
     * 注册 ViewType
     */
    private fun registerViewType() {
        // 注册操作中、失败、成功 View Type
        viewAssist.register(TYPE_ING, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_loading, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
                HandlerUtils.postRunnable({
                    assist.showType(TYPE_EMPTY_DATA)
                }, 1500L)
            }
        }).register(TYPE_EMPTY_DATA, object : ViewAssist.Adapter {
            override fun onCreateView(
                assist: ViewAssist,
                inflater: LayoutInflater
            ): View {
                return inflater.inflate(R.layout.view_assist_empty, null)
            }

            override fun onBindView(
                assist: ViewAssist,
                view: View,
                type: Int
            ) {
            }
        })
    }
}