package afkt.project.feature.ui_effect.recy_adapter.linear_snap

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.data.bean.ItemBean
import afkt.project.model.data.bean.ItemBean.Companion.newItemBean
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: LinearSnapHelper - RecyclerView
 * @author Ttt
 * LinearSnapHelper : 滑动多页居中显示, 类似 Gallery
 */
class LinearSnapActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is LinearSnapActivity) {
            it.apply {
                val parent = binding.vidRv.parent as? ViewGroup
                // 根布局处理
                QuickHelper.get(parent).setPadding(0)

                val lists = mutableListOf<ItemBean>()
                for (i in 0..9) lists.add(newItemBean())

                // 初始化布局管理器、适配器
                binding.vidRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) // VERTICAL
                LinearSnapAdapter(lists).bindAdapter(binding.vidRv)

                val helper = LinearSnapHelper()
                helper.attachToRecyclerView(binding.vidRv)
            }
        }
    }
)