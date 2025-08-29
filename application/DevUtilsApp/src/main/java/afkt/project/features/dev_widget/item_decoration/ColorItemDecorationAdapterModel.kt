package afkt.project.features.dev_widget.item_decoration

import afkt.project.BR
import afkt.project.R
import dev.simple.core.app.adapter.AdapterModel
import me.tatarka.bindingcollectionadapter2.ItemBinding

// ====================================
// = Color ItemDecoration Adapter 模型 =
// ====================================

class GridHorizontalTextAdapter() : AdapterModel<String>() {

    // Item Binding
    val itemBinding = ItemBinding.of<String>(
        BR.itemValue, R.layout.adapter_item_decoration_grid_horizontal_text
    )
}

class GridVerticalTextAdapter() : AdapterModel<String>() {

    // Item Binding
    val itemBinding = ItemBinding.of<String>(
        BR.itemValue, R.layout.adapter_item_decoration_grid_vertical_text
    )
}

class LinearHorizontalTextAdapter() : AdapterModel<String>() {

    // Item Binding
    val itemBinding = ItemBinding.of<String>(
        BR.itemValue, R.layout.adapter_item_decoration_linear_horizontal_text
    )
}

class LinearVerticalTextAdapter() : AdapterModel<String>() {

    // Item Binding
    val itemBinding = ItemBinding.of<String>(
        BR.itemValue, R.layout.adapter_item_decoration_linear_vertical_text
    )
}