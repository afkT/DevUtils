package afkt.project.features.dev_widget.item_decoration

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetItemDecorationLinearHorizontalBinding
import afkt.project.features.dev_widget.item_decoration.assist.LinearColorItemDecorationAssist
import dev.simple.app.base.asFragment

/**
 * detail: Linear Horizontal Color ItemDecoration
 * @author Ttt
 */
class LinearHorizontalFragment : AppFragment<FragmentDevWidgetItemDecorationLinearHorizontalBinding, LinearHorizontalViewModel>(
    R.layout.fragment_dev_widget_item_decoration_linear_horizontal, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<LinearHorizontalFragment> {
            viewModel.adapter.addAll(mutableListOf("1", "2", "3"))
            LinearColorItemDecorationAssist(binding.vidRv, binding.vidInclude)
        }
    }
)

class LinearHorizontalViewModel : AppViewModel() {

    val adapter = LinearHorizontalTextAdapter()
}