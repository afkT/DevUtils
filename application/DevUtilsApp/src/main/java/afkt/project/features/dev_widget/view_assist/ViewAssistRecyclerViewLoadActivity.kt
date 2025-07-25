package afkt.project.features.dev_widget.view_assist

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.helper.RandomHelper
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: ViewAssist RecyclerView Loading
 * @author Ttt
 */
class ViewAssistRecyclerViewLoadActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is ViewAssistRecyclerViewLoadActivity) {
                it.apply {
                    val parent = binding.vidRv.parent as? ViewGroup
                    // 根布局处理
                    QuickHelper.get(parent).setPadding(0)

                    val lists = mutableListOf<String>()
                    for (i in 0..19) {
                        lists.add(RandomHelper.randomImage1080x1920(1000))
                    }
                    // 初始化布局管理器、适配器
                    binding.vidRv.layoutManager = GridLayoutManager(this, 2)
                    RecyclerLoadingAdapter(lists).bindAdapter(binding.vidRv)
                }
            }
        }
    )