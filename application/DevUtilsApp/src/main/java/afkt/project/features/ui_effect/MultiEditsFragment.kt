package afkt.project.features.ui_effect

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectMultiEditsBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBean
import afkt.project.features.ui_effect.recycler_view.adapter_concat.createCommodityEvaluate
import afkt.project.model.basic.AdapterModel
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.willy.ratingbar.ScaleRatingBar
import dev.assist.EditTextWatcherAssist
import dev.expand.engine.log.log_dTag
import dev.expand.engine.toast.toast_showShort
import dev.simple.app.base.asFragment
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.StringUtils
import dev.widget.decoration.linear.FirstLinearColorItemDecoration
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

/**
 * detail: Adapter Item EditText 输入监听
 * @author Ttt
 */
class MultiEditsFragment : AppFragment<FragmentUiEffectMultiEditsBinding, MultiEditsViewModel>(
    R.layout.fragment_ui_effect_multi_edits, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<MultiEditsFragment> {
            QuickHelper.get(binding.vidRv).removeAllItemDecoration().addItemDecoration(
                FirstLinearColorItemDecoration(
                    true, ResourceUtils.getDimension(R.dimen.dp_10)
                )
            )

            setTitleBarRight("提交") { view ->
                val builder = StringBuilder()
                viewModel.adapter.forEach { item ->
                    builder.append(DevFinal.SYMBOL.NEW_LINE)
                        .append("evaluateContent: ").append(item.inputText.get())
                        .append(DevFinal.SYMBOL.NEW_LINE)
                        .append("evaluateLevel: ").append(item.ratingValue.get())
                        .append(DevFinal.SYMBOL.NEW_LINE)
                }
                TAG.log_dTag(message = builder.toString())
                toast_showShort(text = "数据已打印, 请查看 Logcat")
            }
        }
    }
)

class MultiEditsViewModel : AppViewModel() {

    val adapter = MultiEditsAdapter().apply {
        val lists = mutableListOf<CommodityBean>()
        for (i in 1 until 10) lists.add(createCommodityEvaluate())
        addAll(lists)
    }
}

class MultiEditsAdapter() : AdapterModel<CommodityBean>() {

    // EditText 输入监听辅助类
    var textWatcherAssist = EditTextWatcherAssist<CommodityBean>()

    // Item Binding
    val itemBinding = OnItemBindClass<CommodityBean>().map(
        CommodityBean::class.java
    ) { itemBinding, position, item ->
        itemBinding.clearExtras()
            .set(BR.itemValue, R.layout.adapter_concat_commodity_evaluate)
            .bindExtra(BR.itemIndex, position)
            .bindExtra(BR.assist, textWatcherAssist)
    }
}

@BindingAdapter(
    value = [
        "binding_edit_item_value",
        "binding_edit_item_index",
        "binding_edit_text_watcher_assist",
    ],
    requireAll = true
)
fun EditText.bindingMultiEditsInputListener(
    itemValue: CommodityBean,
    itemIndex: Int,
    textWatcherAssist: EditTextWatcherAssist<CommodityBean>
) {
    textWatcherAssist.bindListener(
        itemValue.inputText.get(), itemIndex, this, itemValue
    ) { inputText, _, position, item ->
        val input = inputText.toString()
        val inputNumber = StringUtils.length(input)
        item.inputNumberText.set("${120 - inputNumber}")
    }
}

@BindingAdapter("binding_rating_item_listener")
fun ScaleRatingBar.bindingRatingChangeListener(
    itemValue: CommodityBean
) {
    this.setOnRatingChangeListener { _, rating, _ ->
        itemValue.ratingValue.set(rating)
    }
}

@BindingAdapter("binding_rating_item_value")
fun ScaleRatingBar.bindingRatingValue(
    itemValue: CommodityBean
) {
    this.rating = itemValue.ratingValue.get()
}