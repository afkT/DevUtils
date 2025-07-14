package afkt.project.features.dev_widget.item_decoration.color_linear

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityLinearItemDecorationBinding

/**
 * detail: Linear Color ItemDecoration
 * @author Ttt
 */
class LinearColorItemDecorationActivity :
    BaseProjectActivity<ActivityLinearItemDecorationBinding, AppViewModel>(
        R.layout.activity_linear_item_decoration, simple_Agile = {
            if (it is LinearColorItemDecorationActivity) {
                it.apply {
                    val lists = mutableListOf<String>()
                    for (i in 1..3) {
                        lists.add(i.toString())
                    }

//                    when (moduleType) {
//                        ButtonValue.BTN_LINEAR_ITEM_VERTICAL -> {
//                            RecyclerViewUtils.setOrientation(
//                                binding.vidRv, RecyclerView.VERTICAL
//                            )
//                            LinearVerticalTextAdapter(lists).bindAdapter(
//                                binding.vidRv
//                            )
//                        }
//
//                        ButtonValue.BTN_LINEAR_ITEM_HORIZONTAL -> {
//                            RecyclerViewUtils.setOrientation(
//                                binding.vidRv, RecyclerView.HORIZONTAL
//                            )
//                            LinearHorizontalTextAdapter(lists).bindAdapter(
//                                binding.vidRv
//                            )
//                        }
//                    }
//                    LinearColorItemDecorationAssist(binding.vidRv, binding.vidInclude)
                }
            }
        }
    )