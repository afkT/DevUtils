package afkt.project.features.dev_widget.item_decoration

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetItemDecorationGridVerticalBinding
import afkt.project.features.dev_widget.item_decoration.assist.GridColorItemDecorationAssist
import dev.simple.app.base.asFragment

/**
 * detail: Grid Vertical Color ItemDecoration
 * @author Ttt
 */
class GridVerticalFragment : AppFragment<FragmentDevWidgetItemDecorationGridVerticalBinding, GridVerticalViewModel>(
    R.layout.fragment_dev_widget_item_decoration_grid_vertical, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<GridVerticalFragment> {
            val lists = mutableListOf<String>()
            for (i in 1..11) {
                lists.add(i.toString())
            }
            viewModel.adapterModel.addAll(lists)
            GridColorItemDecorationAssist(binding.vidRv, binding.vidInclude)
        }
    }
)

class GridVerticalViewModel : AppViewModel() {

    val adapterModel = GridVerticalTextAdapter()
}