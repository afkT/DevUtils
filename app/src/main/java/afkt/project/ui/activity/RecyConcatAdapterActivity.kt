package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.helper.ViewHelper
import java.util.*

/**
 * detail: RecyclerView - ConcatAdapter
 * @author Ttt
 * @see https://mp.weixin.qq.com/s/QTaz45aLucX9mivVMbCZPQ
 * @see https://zhuanlan.zhihu.com/p/275635988
 */
@Route(path = RouterPath.RecyConcatAdapterActivity_PATH)
class RecyConcatAdapterActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parent = binding.vidBvrRecy.parent as? ViewGroup
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0)
    }

    override fun initValue() {
        super.initValue()
    }
}