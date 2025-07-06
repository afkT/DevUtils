package afkt.project.model.adapter

import afkt.project.BR
import afkt.project.R
import afkt.project.app.base.appViewModel
import afkt.project.model.basic.AdapterModel
import afkt.project.model.data.button.ButtonEnum
import dev.mvvm.command.BindingConsumer
import me.tatarka.bindingcollectionadapter2.ItemBinding

// Button Item 点击事件
typealias ButtonClick = (ButtonEnum) -> Unit

// Button Item 默认点击事件
private val DEFAULT_CLICK: ButtonClick = {
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
        BR.itemValue, R.layout.base_button_adapter_item
    ).bindExtra(BR.itemClick, itemClick)

    // ==========
    // = 点击事件 =
    // ==========

    private var clickItem: ButtonClick = DEFAULT_CLICK

    fun setOnItemClick(click: ButtonClick): ButtonAdapterModel {
        clickItem = click
        return this
    }
}