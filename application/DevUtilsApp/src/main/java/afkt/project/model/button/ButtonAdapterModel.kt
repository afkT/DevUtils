package afkt.project.model.button

import afkt.project.BR
import afkt.project.R
import afkt.project.app.appViewModel
import afkt.project.model.basic.AdapterModel
import dev.mvvm.command.BindingConsumer
import me.tatarka.bindingcollectionadapter2.ItemBinding

// Button Item 点击事件
typealias ButtonClick = (ButtonEnum) -> Unit

// Button Item 默认点击事件
val DEFAULT_CLICK: ButtonClick = {
    appViewModel().navigate(it)
}

// ======================
// = Button Adapter 模型 =
// ======================

/**
 * detail: Button Adapter 模型
 * @author Ttt
 */
class ButtonAdapterModel() : AdapterModel<ButtonEnum>() {

    private val itemClick = object : BindingConsumer<ButtonEnum> {
        override fun accept(value: ButtonEnum) {
            clickItem.invoke(value)
        }
    }

    // Item Binding
    val itemBinding = ItemBinding.of<ButtonEnum>(
        BR.itemValue, R.layout.adapter_item_button_enum
    ).bindExtra(BR.itemClick, itemClick)

    // ==========
    // = 点击事件 =
    // ==========

    private var clickItem: ButtonClick = DEFAULT_CLICK

    fun setOnItemClick(listener: ButtonClick): ButtonAdapterModel {
        clickItem = listener
        return this
    }
}