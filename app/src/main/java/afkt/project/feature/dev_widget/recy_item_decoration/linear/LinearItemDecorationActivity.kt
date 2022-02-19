package afkt.project.feature.dev_widget.recy_item_decoration.linear

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityLinearItemDecorationBinding
import afkt.project.model.item.ButtonValue
import afkt.project.model.item.RouterPath
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.RecyclerViewUtils

/**
 * detail: Linear ItemDecoration
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.LinearItemDecorationActivity_PATH)
class LinearItemDecorationActivity : BaseActivity<ActivityLinearItemDecorationBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_linear_item_decoration

    override fun initValue() {
        super.initValue()

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
        LinearItemDecorationAssist(binding.vidRv, binding.vidInclude)
    }
}