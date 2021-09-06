package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.ItemBean
import afkt.project.ui.adapter.PagerSnapAdapter
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.helper.view.ViewHelper
import java.util.*

/**
 * detail: PagerSnapHelper - RecyclerView
 * @author Ttt
 * PagerSnapHelper : 每次滑动一页居中显示, 类似 ViewPager
 */
@Route(path = RouterPath.PagerSnapActivity_PATH)
class PagerSnapActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parent = binding.vidBvrRecy.parent as? ViewGroup
        ViewHelper.get().setPadding(parent, 0)
    }

    override fun initValue() {
        super.initValue()

        val lists: MutableList<ItemBean> = ArrayList()
        for (i in 0..9) lists.add(ItemBean.newItemBeanPager())

        // 初始化布局管理器、适配器
        binding.vidBvrRecy.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) // VERTICAL
        binding.vidBvrRecy.adapter = PagerSnapAdapter(lists)
        val helper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.vidBvrRecy)
    }
}