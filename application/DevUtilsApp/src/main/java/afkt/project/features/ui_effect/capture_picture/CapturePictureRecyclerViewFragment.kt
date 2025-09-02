package afkt.project.features.ui_effect.capture_picture

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectCapturePictureRecyclerViewBinding
import afkt.project.model.helper.RandomHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.base.simple.extensions.asFragment
import dev.simple.core.adapter.AdapterModel
import dev.utils.app.CapturePictureUtils
import dev.utils.common.RandomUtils
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * detail: CapturePictureUtils RecyclerView 截图
 * @author Ttt
 */
class CapturePictureRecyclerViewFragment : AppFragment<FragmentUiEffectCapturePictureRecyclerViewBinding, CapturePictureRecyclerViewModel>(
    R.layout.fragment_ui_effect_capture_picture_recycler_view, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<CapturePictureRecyclerViewFragment> {
            setTitleBarRight("截图") { view ->
                val bitmap = CapturePictureUtils.snapshotByRecyclerView(binding.vidRv)
                CapturePictureFragment.saveBitmap("recycler_view.jpg", bitmap)
            }
            // 支持三种布局 GridLayoutManager、LinearLayoutManager、StaggeredGridLayoutManager
            // 以及对于的横、竖屏效果截图

//            binding.vidRv.layoutManager = LinearLayoutManager(this)
//            binding.vidRv.layoutManager = LinearLayoutManager(
//                this, LinearLayoutManager.HORIZONTAL, false
//            )
//
//            binding.vidRv.layoutManager = GridLayoutManager(this, 3)
//            binding.vidRv.layoutManager = GridLayoutManager(
//                this, 3, GridLayoutManager.HORIZONTAL, false
//            )
//
//            binding.vidRv.layoutManager = StaggeredGridLayoutManager(
//                3, StaggeredGridLayoutManager.HORIZONTAL
//            )
            binding.vidRv.layoutManager = StaggeredGridLayoutManager(
                3, StaggeredGridLayoutManager.VERTICAL
            )
        }
    }
)

open class CapturePictureRecyclerViewModel : AppViewModel() {

    val adapterModel = CapturePictureAdapter().apply {
        addAllAndClear(CapturePictureBean.newAdapterBeanList(15))
    }
}

class CapturePictureAdapter() : AdapterModel<CapturePictureBean>() {

    // Item Binding
    val itemBinding = ItemBinding.of<CapturePictureBean>(
        BR.itemValue, R.layout.adapter_item_capture_picture
    )
}

class CapturePictureBean(
    // 标题
    val title: String,
    // 内容
    val content: String,
) {

    companion object {

        /**
         * 创建适配器实体类
         * @param position 索引
         * @return [CapturePictureBean]
         */
        private fun newAdapterBean(position: Int): CapturePictureBean {
            val number = RandomUtils.getRandom(10, 100) + (10 + position / 3) * 3
            return CapturePictureBean(
                title = RandomHelper.randomWord(2),
                content = "${position + 1}." + RandomHelper.randomWordRange(max = number)
            )
        }

        /**
         * 获取适配器实体类集合
         * @param count 集合总数
         * @return 适配器实体类集合
         */
        fun newAdapterBeanList(count: Int): List<CapturePictureBean> {
            val lists = mutableListOf<CapturePictureBean>()
            for (i in 0 until count) {
                lists.add(newAdapterBean(i))
            }
            return lists
        }
    }
}