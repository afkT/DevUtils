package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.EvaluateItem
import afkt.project.ui.adapter.EditsAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import dev.base.widget.BaseTextView
import dev.engine.log.DevLogEngine
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.QuickHelper
import dev.utils.app.helper.ViewHelper
import dev.utils.app.toast.ToastTintUtils
import java.util.*

/**
 * detail: Adapter Item EditText 输入监听
 * @author Ttt
 */
@Route(path = RouterPath.AdapterEditsActivity_PATH)
class AdapterEditsActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    // 适配器
    lateinit var adapter: EditsAdapter

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = QuickHelper.get(BaseTextView(this))
            .setText("提交")
            .setBold()
            .setTextColor(ResourceUtils.getColor(R.color.white))
            .setTextSizeBySp(13.0f)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClicks {
                val builder = StringBuilder()
                for (item in adapter.dataList) {
                    builder
                        .append("\nevaluateContent: ").append(item.evaluateContent)
                        .append("\nevaluateLevel: ").append(item.evaluateLevel).append("\n")
                }
                DevLogEngine.getEngine().dTag(TAG, builder.toString())
                ToastTintUtils.success("数据已打印, 请查看 Logcat")
            }.getView<View>()
        toolbar?.addView(view)

        val parent = binding.vidBvrRecy.parent as? ViewGroup
        ViewHelper.get().setPadding(parent, 0)
            .setBackgroundColor(parent, ResourceUtils.getColor(R.color.color_33))
    }

    override fun initValue() {
        super.initValue()

        val lists: MutableList<EvaluateItem> = ArrayList()
        for (i in 0..5) lists.add(EvaluateItem())
        // 默认清空第一条数据内容
        lists[0].evaluateContent = ""

        // 初始化布局管理器、适配器
        adapter = EditsAdapter(lists)
        binding.vidBvrRecy.adapter = adapter
    }
}