package afkt.project.features.ui_effect

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectMultiSelectBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBean
import afkt.project.features.ui_effect.recycler_view.adapter_concat.createCommodity
import afkt.project.model.basic.AdapterModel
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import dev.base.multiselect.DevMultiSelectMap
import dev.base.multiselect.IMultiSelectEdit
import dev.simple.app.base.asFragment
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.linear.FirstLinearColorItemDecoration
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

/**
 * detail: 多选辅助类 MultiSelectAssist
 * @author Ttt
 */
class MultiSelectFragment : AppFragment<FragmentUiEffectMultiSelectBinding, MultiSelectViewModel>(
    R.layout.fragment_ui_effect_multi_select, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<MultiSelectFragment> {
            QuickHelper.get(binding.vidRv).removeAllItemDecoration().addItemDecoration(
                FirstLinearColorItemDecoration(
                    true, ResourceUtils.getDimension(R.dimen.dp_10)
                )
            )
        }
    }
)

class MultiSelectViewModel : AppViewModel() {

    val adapter = MultiSelectAdapter().apply {
        val lists = mutableListOf<CommodityBean>()
        for (i in 1 until 15) lists.add(createCommodity())
        addAll(lists)
    }
}

class MultiSelectAdapter() : AdapterModel<CommodityBean>(), IMultiSelectEdit<MultiSelectAdapter> {

    // 多选开关
    val selectSwitch = ObservableBoolean(false)

    // 多选辅助类
    val multiSelect = DevMultiSelectMap<String, CommodityBean>()

    // Item Binding
    val itemBinding = OnItemBindClass<CommodityBean>().map(
        CommodityBean::class.java
    ) { itemBinding, position, item ->
        itemBinding.clearExtras()
            .set(BR.itemValue, R.layout.adapter_concat_commodity_multi_select)
            .bindExtra(BR.assist, multiSelect)
            .bindExtra(BR.switchBool, selectSwitch)
            .bindExtra(BR.itemKey, item.multiSelectKey())
    }

    // =======
    // = Key =
    // =======

    /**
     * 获取多选 Item Key
     * @return Item Key
     */
    fun CommodityBean.multiSelectKey(): String {
        return this.toString()
    }

    // ====================
    // = IMultiSelectEdit =
    // ====================

    override fun isEditState(): Boolean {
        return selectSwitch.get()
    }

    override fun setEditState(isEdit: Boolean): MultiSelectAdapter {
        selectSwitch.set(isEdit)
        return this
    }

    override fun toggleEditState(): MultiSelectAdapter {
        return setEditState(!isEditState)
    }

    override fun selectAll(): MultiSelectAdapter {
        val maps = LinkedHashMap<String, CommodityBean>()
        this.newForEach {
            maps[it.multiSelectKey()] = it
        }
        multiSelect.putSelects(maps)
        return this
    }

    override fun clearSelectAll(): MultiSelectAdapter {
        multiSelect.clearSelects()
        return this
    }

    override fun inverseSelect(): MultiSelectAdapter {
        if (isNotSelect) return selectAll()

        val keys = multiSelect.getSelectKeys()
        val maps = LinkedHashMap<String, CommodityBean>()
        this.newForEach {
            val key = it.multiSelectKey()
            // 如果已经选中了则移除不处理
            if (key.contains(key)) {
                keys.remove(key)
            } else {
                maps[key] = it
            }
        }
        multiSelect.clearSelects()
        multiSelect.putSelects(maps)
        return this
    }

    override fun isSelectAll(): Boolean {
        val size = multiSelect.selectSize
        if (size == 0) return false
        return dataCount == size
    }

    override fun isSelect(): Boolean {
        return multiSelect.isSelect
    }

    override fun isNotSelect(): Boolean {
        return !multiSelect.isSelect
    }

    override fun getSelectSize(): Int {
        return multiSelect.selectSize
    }

    override fun getDataCount(): Int {
        return count()
    }
}

@BindingAdapter(
    value = [
        "binding_select_item_value",
        "binding_select_item_key",
        "binding_select_assist",
        "binding_select_switch",
    ],
    requireAll = true
)
fun AppCompatImageView.bindingMultiSelectListener(
    itemValue: CommodityBean,
    itemKey: String,
    assist: DevMultiSelectMap<String, CommodityBean>,
    switchBool: ObservableBoolean
) {
    // 如果打开了开关才进行显示
    if (ViewUtils.setVisibility(switchBool.get(), this)) {
        // 判断是否选中
        val isSelected = assist.isSelectKey(itemKey)
        ViewUtils.setSelected(isSelected, this)
        // 设置点击事件
        setOnClickListener { view ->
            assist.toggle(itemKey, itemValue)
        }
    }
}