package afkt.project.feature.dev_widget.recy_item_decoration.color_grid

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityGridItemDecorationBinding
import afkt.project.feature.dev_widget.recy_item_decoration.common.GridHorizontalTextAdapter
import afkt.project.feature.dev_widget.recy_item_decoration.common.GridVerticalTextAdapter
import afkt.project.model.item.ButtonValue
import afkt.project.model.item.RouterPath
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import dev.utils.app.RecyclerViewUtils

/**
 * detail: Grid Color ItemDecoration
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.GridColorItemDecorationActivity_PATH)
class GridColorItemDecorationActivity : BaseActivity<ActivityGridItemDecorationBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_grid_item_decoration

    override fun initValue() {
        super.initValue()

        val lists = mutableListOf<String>()
        for (i in 1..11) {
            lists.add(i.toString())
        }

        when (moduleType) {
            ButtonValue.BTN_GRID_ITEM_VERTICAL -> {
                RecyclerViewUtils.setOrientation(
                    binding.vidRv, RecyclerView.VERTICAL
                )
                GridVerticalTextAdapter(lists).bindAdapter(
                    binding.vidRv
                )
            }

            ButtonValue.BTN_GRID_ITEM_HORIZONTAL -> {
                RecyclerViewUtils.setOrientation(
                    binding.vidRv, RecyclerView.HORIZONTAL
                )
                GridHorizontalTextAdapter(lists).bindAdapter(
                    binding.vidRv
                )
            }
        }
        GridColorItemDecorationAssist(binding.vidRv, binding.vidInclude)
    }
}