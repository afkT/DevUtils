package afkt.project.feature.ui_effect.recy_adapter.linear_snap

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.bean.ItemBean
import afkt.project.data_model.bean.ItemBean.Companion.newItemBean
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: LinearSnapHelper - RecyclerView
 * @author Ttt
 * LinearSnapHelper : 滑动多页居中显示, 类似 Gallery
 */
@Route(path = RouterPath.UI_EFFECT.LinearSnapActivity_PATH)
class LinearSnapActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parent = binding.vidRv.parent as? ViewGroup
        // 根布局处理
        QuickHelper.get(parent).setPadding(0)
    }

    override fun initValue() {
        super.initValue()

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