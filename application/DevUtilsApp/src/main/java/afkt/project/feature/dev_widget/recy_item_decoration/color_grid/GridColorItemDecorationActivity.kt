package afkt.project.feature.dev_widget.recy_item_decoration.color_grid

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityGridItemDecorationBinding
import afkt.project.feature.dev_widget.recy_item_decoration.common.GridHorizontalTextAdapter
import afkt.project.feature.dev_widget.recy_item_decoration.common.GridVerticalTextAdapter
import afkt.project.model.data.button.ButtonValue
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils

/**
 * detail: Grid Color ItemDecoration
 * @author Ttt
 */
class GridColorItemDecorationActivity :
    BaseProjectActivity<ActivityGridItemDecorationBinding, AppViewModel>(
        R.layout.activity_grid_item_decoration, simple_Agile = {
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