package afkt.project.features.dev_widget.item_decoration

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetItemDecorationLinearVerticalBinding
import afkt.project.features.dev_widget.item_decoration.assist.LinearColorItemDecorationAssist
import dev.simple.app.base.asFragment

/**
 * detail: Linear Vertical Color ItemDecoration
 * @author Ttt
 */
class LinearVerticalFragment : AppFragment<FragmentDevWidgetItemDecorationLinearVerticalBinding, LinearVerticalViewModel>(
    R.layout.fragment_dev_widget_item_decoration_linear_vertical, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<LinearVerticalFragment> {
            viewModel.adapterModel.addAll(mutableListOf("1", "2", "3"))
            LinearColorItemDecorationAssist(binding.vidRv, binding.vidInclude)
        }
    }
)

class LinearVerticalViewModel : AppViewModel() {

    val adapterModel = LinearVerticalTextAdapter()
}