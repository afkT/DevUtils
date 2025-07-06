package afkt.project.feature.dev_widget.view_assist

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.data.button.RouterPath
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.therouter.router.Route
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.RandomUtils

/**
 * detail: ViewAssist RecyclerView Loading
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.ViewAssistRecyclerViewLoadActivity_PATH)
class ViewAssistRecyclerViewLoadActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is ViewAssistRecyclerViewLoadActivity) {
                it.apply {
                    val parent = binding.vidRv.parent as? ViewGroup
                    // 根布局处理
                    QuickHelper.get(parent).setPadding(0)

                    val url = "https://picsum.photos/id/%s/1080/1920"
                    val lists = mutableListOf<String>()
                    for (i in 0..19) {
                        lists.add(String.format(url, RandomUtils.getRandom(1, 1000)))
                    }
                    // 初始化布局管理器、适配器
                    binding.vidRv.layoutManager = GridLayoutManager(this, 2)
                    RecyclerLoadingAdapter(lists).bindAdapter(binding.vidRv)
                }
            }
        }
    )