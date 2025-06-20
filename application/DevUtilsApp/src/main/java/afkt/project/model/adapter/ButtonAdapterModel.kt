package afkt.project.model.adapter

import afkt.project.BR
import afkt.project.R
import afkt.project.model.basic.AdapterModel
import afkt.project.model.data.button.ButtonEnum
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
    clickItem: (ButtonEnum) -> Unit
) : AdapterModel<ButtonEnum>() {

    private val itemClick = object : BindingConsumer<ButtonEnum> {
        override fun accept(value: ButtonEnum) {
            clickItem.invoke(value)
        }
    }

    // Item Binding
    val itemBinding = ItemBinding.of<ButtonEnum>(
        BR.itemValue, R.layout.base_button_adapter_item
    ).bindExtra(BR.itemClick, itemClick)
}