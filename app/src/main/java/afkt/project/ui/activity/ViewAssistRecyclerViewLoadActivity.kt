package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.ui.adapter.RecyclerLoadingAdapter
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.RandomUtils
import java.util.*

/**
 * detail: ViewAssist RecyclerView Loading
 * @author Ttt
 */
@Route(path = RouterPath.ViewAssistRecyclerViewLoadActivity_PATH)
class ViewAssistRecyclerViewLoadActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()
        val parent = binding.vidBvrRecy.parent as? ViewGroup
        // 根布局处理
        QuickHelper.get(parent).setPadding(0)

        val url = "https://picsum.photos/id/%s/1080/1920"
        val lists: MutableList<String> = ArrayList()
        for (i in 0..19) {
            lists.add(String.format(url, RandomUtils.getRandom(1, 1000)))
        }
        // 初始化布局管理器、适配器
        binding.vidBvrRecy.layoutManager = GridLayoutManager(this, 2)
        binding.vidBvrRecy.adapter = RecyclerLoadingAdapter(lists)
    }
}