package afkt.project.features.ui_effect.recycler_view.snap

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectLinearSnapBinding
import afkt.project.model.basic.AdapterModel
import androidx.recyclerview.widget.LinearSnapHelper
import dev.simple.app.base.asFragment
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * detail: RecyclerView - LinearSnapHelper
 * @author Ttt
 * LinearSnapHelper : 滑动多页居中显示, 类似 Gallery
 */
class LinearSnapFragment : AppFragment<FragmentUiEffectLinearSnapBinding, LinearSnapViewModel>(
    R.layout.fragment_ui_effect_linear_snap, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<LinearSnapFragment> {
            val helper = LinearSnapHelper()
            helper.attachToRecyclerView(binding.vidRv)
        }
    }
)

class LinearSnapViewModel : AppViewModel() {

    val adapter = LinearSnapAdapter().apply {
        addAllAndClear(SnapItemModel.randomItemLists())
    }
}

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
class LinearSnapAdapter() : AdapterModel<SnapItemModel>() {

    // Item Binding
    val itemBinding = ItemBinding.of<SnapItemModel>(
        BR.itemValue, R.layout.adapter_item_linear_snap
    )
}