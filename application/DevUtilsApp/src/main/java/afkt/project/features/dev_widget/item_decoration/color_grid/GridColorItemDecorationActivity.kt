package afkt.project.features.dev_widget.item_decoration.color_grid

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetGridItemDecorationBinding

/**
 * detail: Grid Color ItemDecoration
 * @author Ttt
 */
class GridColorItemDecorationActivity :
    AppFragment<FragmentDevWidgetGridItemDecorationBinding, AppViewModel>(
        R.layout.fragment_dev_widget_grid_item_decoration, BR.viewModel, simple_Agile = {
            if (it is GridColorItemDecorationActivity) {
                it.apply {
                    val lists = mutableListOf<String>()
                    for (i in 1..11) {
                        lists.add(i.toString())
                    }

//                    when (moduleType) {
//                        ButtonValue.BTN_GRID_ITEM_VERTICAL -> {
//                            RecyclerViewUtils.setOrientation(
//                                binding.vidRv, RecyclerView.VERTICAL
//                            )
//                            GridVerticalTextAdapter(lists).bindAdapter(
//                                binding.vidRv
//                            )
//                        }
//
//                        ButtonValue.BTN_GRID_ITEM_HORIZONTAL -> {
//                            RecyclerViewUtils.setOrientation(
//                                binding.vidRv, RecyclerView.HORIZONTAL
//                            )
//                            GridHorizontalTextAdapter(lists).bindAdapter(
//                                binding.vidRv
//                            )
//                        }
//                    }
//                    GridColorItemDecorationAssist(binding.vidRv, binding.vidInclude)
                }
            }
        }
    )