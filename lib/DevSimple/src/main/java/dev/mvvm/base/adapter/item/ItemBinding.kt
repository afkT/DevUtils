package dev.mvvm.base.adapter.item

import android.util.SparseArray
import androidx.annotation.LayoutRes

/**
 * detail: Item Binding 信息类
 * @author Ttt
 */
class ItemBinding<T>(
    // Item 绑定实体类
    val variableId: Int,
    // DataBinding 绑定布局
    @LayoutRes val layoutRes: Int
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
    private val extraBindings: SparseArray<Any>? = null
}