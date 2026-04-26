package dev.simple.features.deprecated.adapter.item

import android.util.SparseArray
import androidx.annotation.LayoutRes

/**
 * detail: Item Binding 信息类
 * @author Ttt
 * 可继承以扩展多布局 / 动态 variableId 等场景，避免复制整段适配逻辑
 */
open class ItemBinding<T>(
    // Item 绑定实体类
    open val variableId: Int,
    // DataBinding 绑定布局
    @LayoutRes open val layoutRes: Int
) {

    companion object {

        fun <T> of(
            variableId: Int,
            @LayoutRes layoutRes: Int
        ): ItemBinding<T> {
            return ItemBinding<T>(variableId, layoutRes)
        }
    }

    // 额外绑定参数
    open var extraBindings: SparseArray<Any>? = null
}