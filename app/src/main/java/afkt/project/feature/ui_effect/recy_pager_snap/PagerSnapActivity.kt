package afkt.project.feature.ui_effect.recy_pager_snap

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.ItemBean
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: PagerSnapHelper - RecyclerView
 * @author Ttt
 * PagerSnapHelper : 每次滑动一页居中显示, 类似 ViewPager
 */
@Route(path = RouterPath.UI_EFFECT.PagerSnapActivity_PATH)
class PagerSnapActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parent = binding.vidRv.parent as? ViewGroup
        // 根布局处理
        QuickHelper.get(parent).setPadding(0)
    }

    override fun initValue() {
        super.initValue()

        val lists: MutableList<ItemBean> = ArrayList()
        for (i in 0..9) lists.add(ItemBean.newItemBeanPager())

        // 初始化布局管理器、适配器
        binding.vidRv.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) // VERTICAL
        PagerSnapAdapter(lists).bindAdapter(binding.vidRv)

        val helper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.vidRv)
    }
}