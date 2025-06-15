package afkt.project.feature.ui_effect.recy_adapter.pager_snap

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.model.data.bean.ItemBean
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: PagerSnapHelper - RecyclerView
 * @author Ttt
 * PagerSnapHelper : 每次滑动一页居中显示, 类似 ViewPager
 */
@Route(path = RouterPath.UI_EFFECT.PagerSnapActivity_PATH)
class PagerSnapActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is PagerSnapActivity) {
            it.apply {
                val parent = binding.vidRv.parent as? ViewGroup
                // 根布局处理
                QuickHelper.get(parent).setPadding(0)

                val lists = mutableListOf<ItemBean>()
                for (i in 0..9) lists.add(ItemBean.newItemBeanPager())

                // 初始化布局管理器、适配器
                binding.vidRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) // VERTICAL
                PagerSnapAdapter(lists).bindAdapter(binding.vidRv)

                val helper = PagerSnapHelper()
                helper.attachToRecyclerView(binding.vidRv)
            }
        }
    }
)