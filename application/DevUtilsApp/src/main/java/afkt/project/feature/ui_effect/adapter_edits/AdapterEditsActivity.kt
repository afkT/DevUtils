package afkt.project.feature.ui_effect.adapter_edits

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.bean.EvaluateItem
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.view.View
import android.view.ViewGroup
import com.therouter.router.Route
import androidx.appcompat.widget.AppCompatTextView
import dev.expand.engine.log.log_dTag
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.toast.ToastTintUtils
import dev.widget.decoration.linear.FirstLinearColorItemDecoration

/**
 * detail: Adapter Item EditText 输入监听
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.AdapterEditsActivity_PATH)
class AdapterEditsActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is AdapterEditsActivity) {
            it.apply {
                val view = QuickHelper.get(AppCompatTextView(this))
                    .setText("提交")
                    .setBold()
                    .setTextColors(ResourceUtils.getColor(R.color.white))
                    .setTextSizeBySp(13.0F)
                    .setPaddingLeft(30)
                    .setPaddingRight(30)
                    .setOnClick {
                        val builder = StringBuilder()
                        for (item in adapter.dataList) {
                            builder
                                .append("\nevaluateContent: ").append(item.evaluateContent)
                                .append("\nevaluateLevel: ").append(item.evaluateLevel)
                                .append(DevFinal.SYMBOL.NEW_LINE)
                        }
                        TAG.log_dTag(
                            message = builder.toString()
                        )
                        ToastTintUtils.success("数据已打印, 请查看 Logcat")
                    }.getView<View>()
                toolbar?.addView(view)

                val parent = binding.vidRv.parent as? ViewGroup
                // 根布局处理
                QuickHelper.get(parent).setPadding(0)
                    .setBackgroundColor(ResourceUtils.getColor(R.color.color_33))
            }
        }
    }
) {

    // 适配器
    lateinit var adapter: EditsAdapter

    override fun initValue() {
        super.initValue()

        val lists = mutableListOf<EvaluateItem>()
        for (i in 0..5) lists.add(EvaluateItem())
        // 默认清空第一条数据内容
        lists[0].evaluateContent = ""

        // 初始化布局管理器、适配器
        adapter = EditsAdapter(lists)
        adapter.bindAdapter(binding.vidRv)

        QuickHelper.get(binding.vidRv)
            .removeAllItemDecoration()
            .addItemDecoration(
                FirstLinearColorItemDecoration(
                    true, ResourceUtils.getDimension(R.dimen.dp_10)
                )
            )
    }
}