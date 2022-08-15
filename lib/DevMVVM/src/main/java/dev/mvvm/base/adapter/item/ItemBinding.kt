package dev.mvvm.base.adapter.item

import android.util.SparseArray
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner

/**
 * detail: Item Binding 信息类
 * @author Ttt
 */
open class ItemBinding<T>(
    // Item 绑定实体类
    val variableId: Int,
    // DataBinding 绑定布局
    @LayoutRes val layoutRes: Int
) {

    companion object {

        fun <T> of(
            variableId: Int,
            @LayoutRes layoutRes: Int,
            owner: LifecycleOwner? = null
        ): ItemBinding<T> {
            return ItemBinding<T>(variableId, layoutRes)
                .setLifecycleOwner(owner)
        }
    }

    // 额外绑定参数
    private val extraBindings: SparseArray<Any>? = null

    // 使用 LiveData 绑定使用
    private var lifecycleOwner: LifecycleOwner? = null

    fun setLifecycleOwner(owner: LifecycleOwner?): ItemBinding<T> {
        lifecycleOwner = owner
        return this
    }
}