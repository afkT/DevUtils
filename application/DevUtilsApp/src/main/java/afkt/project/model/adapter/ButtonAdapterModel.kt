package afkt.project.model.adapter

import afkt.project.BR
import afkt.project.R
import afkt.project.model.data.AdapterModel
import afkt.project.model.data.button.ButtonValue
import dev.mvvm.command.BindingConsumer
import me.tatarka.bindingcollectionadapter2.ItemBinding

// =========================
// = Button Adapter 数据模型 =
// =========================

// ======================
// = Button Adapter 模型 =
// ======================

/**
 * detail: Button Adapter 模型
 * @author Ttt
 */
class ButtonAdapterModel(
    clickItem: (ButtonValue) -> Unit
) : AdapterModel<ButtonValue>() {

    private val itemClick = object : BindingConsumer<ButtonValue> {
        override fun accept(value: ButtonValue) {
            clickItem.invoke(value)
        }
    }

    // Item Binding
    val itemBinding = ItemBinding.of<ButtonValue>(
        BR.itemValue, R.layout.base_button_adapter_item
    ).bindExtra(BR.itemClick, itemClick)
}