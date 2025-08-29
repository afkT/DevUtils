package afkt.project.features.ui_effect.recycler_view.snap

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectPagerSnapBinding
import androidx.recyclerview.widget.PagerSnapHelper
import dev.base.simple.extensions.asFragment
import dev.simple.core.app.adapter.AdapterModel
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * detail: RecyclerView - PagerSnapHelper
 * @author Ttt
 * PagerSnapHelper : 每次滑动一页居中显示, 类似 ViewPager
 */
class PagerSnapFragment : AppFragment<FragmentUiEffectPagerSnapBinding, PagerSnapViewModel>(
    R.layout.fragment_ui_effect_pager_snap, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<PagerSnapFragment> {
            val helper = PagerSnapHelper()
            helper.attachToRecyclerView(binding.vidRv)
        }
    }
)

class PagerSnapViewModel : AppViewModel() {

    val adapterModel = PagerSnapAdapter().apply {
        addAll(SnapItemModel.randomItemPagerLists())
    }
}

/**
 * detail: RecyclerView ViewPager 效果 Adapter
 * @author Ttt
 */
class PagerSnapAdapter() : AdapterModel<SnapItemModel>() {

    // Item Binding
    val itemBinding = ItemBinding.of<SnapItemModel>(
        BR.itemValue, R.layout.adapter_item_pager_snap
    )
}