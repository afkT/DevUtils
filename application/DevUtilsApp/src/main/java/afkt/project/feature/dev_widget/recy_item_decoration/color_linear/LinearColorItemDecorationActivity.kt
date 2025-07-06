package afkt.project.feature.dev_widget.recy_item_decoration.color_linear

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityLinearItemDecorationBinding
import afkt.project.feature.dev_widget.recy_item_decoration.common.LinearHorizontalTextAdapter
import afkt.project.feature.dev_widget.recy_item_decoration.common.LinearVerticalTextAdapter
import afkt.project.model.data.button.ButtonValue
import afkt.project.model.data.button.RouterPath
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import dev.utils.app.RecyclerViewUtils

/**
 * detail: Linear Color ItemDecoration
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.LinearColorItemDecorationActivity_PATH)
class LinearColorItemDecorationActivity :
    BaseProjectActivity<ActivityLinearItemDecorationBinding, AppViewModel>(
        R.layout.activity_linear_item_decoration, simple_Agile = {
            if (it is LinearColorItemDecorationActivity) {
                it.apply {
                    val lists = mutableListOf<String>()
                    for (i in 1..3) {
                        lists.add(i.toString())
                    }

                    when (moduleType) {
                        ButtonValue.BTN_LINEAR_ITEM_VERTICAL -> {
                            RecyclerViewUtils.setOrientation(
                                binding.vidRv, RecyclerView.VERTICAL
                            )
                            LinearVerticalTextAdapter(lists).bindAdapter(
                                binding.vidRv
                            )
                        }

                        ButtonValue.BTN_LINEAR_ITEM_HORIZONTAL -> {
                            RecyclerViewUtils.setOrientation(
                                binding.vidRv, RecyclerView.HORIZONTAL
                            )
                            LinearHorizontalTextAdapter(lists).bindAdapter(
                                binding.vidRv
                            )
                        }
                    }
                    LinearColorItemDecorationAssist(binding.vidRv, binding.vidInclude)
                }
            }
        }
    )