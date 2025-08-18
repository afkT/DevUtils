package afkt.project.features.dev_widget.item_decoration

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetItemDecorationGridHorizontalBinding
import afkt.project.features.dev_widget.item_decoration.assist.GridColorItemDecorationAssist
import dev.simple.app.base.asFragment

/**
 * detail: Grid Horizontal Color ItemDecoration
 * @author Ttt
 */
class GridHorizontalFragment : AppFragment<FragmentDevWidgetItemDecorationGridHorizontalBinding, GridHorizontalViewModel>(
    R.layout.fragment_dev_widget_item_decoration_grid_horizontal, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<GridHorizontalFragment> {
            val lists = mutableListOf<String>()
            for (i in 1..11) {
                lists.add(i.toString())
            }
            viewModel.adapterModel.addAll(lists)
            GridColorItemDecorationAssist(binding.vidRv, binding.vidInclude)
        }
    }
)

class GridHorizontalViewModel : AppViewModel() {

    val adapterModel = GridHorizontalTextAdapter()
}