package afkt.project.features

import afkt.project.BR
import afkt.project.R
import afkt.project.base.app.AppFragment
import afkt.project.base.app.AppViewModel
import afkt.project.base.app.applyToButtonAdapter
import afkt.project.databinding.FragmentRecyclerViewBinding
import afkt.project.model.data.button.convertItemsEngine

class DevAssistEngineFragment : AppFragment<FragmentRecyclerViewBinding, AppViewModel>(
    R.layout.fragment_recycler_view, BR.viewModel,
    simple_Agile = { frg ->
        frg.applyToButtonAdapter<DevAssistEngineFragment> {
            // 初始化数据
            convertItemsEngine()
        }
    }
)