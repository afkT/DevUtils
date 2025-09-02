package dev.simple.features.deprecated.adapter.item

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import dev.simple.interfaces.BindingGet

/**
 * detail: Item Binding Lifecycle
 * @author Ttt
 */
class ItemLifecycle private constructor() {

    companion object {

        fun of(): ItemLifecycle {
            return ItemLifecycle()
        }
    }

    // 使用 LiveData 绑定使用
    private var lifecycleOwner: LifecycleOwner? = null

    // LifecycleOwner 通用获取接口
    private var lifecycleGet: BindingGet<LifecycleOwner?>? = null

    // =============
    // = 对外公开方法 =
    // =============

    fun getLifecycleOwner(): LifecycleOwner? {
        return lifecycleOwner
    }

    fun setLifecycleOwner(owner: LifecycleOwner?): ItemLifecycle {
        lifecycleOwner = owner
        return this
    }

    fun getLifecycleImpl(): BindingGet<LifecycleOwner?>? {
        return lifecycleGet
    }

    fun setLifecycleImpl(implGET: BindingGet<LifecycleOwner?>?): ItemLifecycle {
        lifecycleGet = implGET
        return this
    }

    // ==========
    // = 核心方法 =
    // ==========

    /**
     * LifecycleOwner 是否有效
     * @return `true` yes, `false` no
     */
    fun isValidLifecycleOwner(): Boolean {
        return !isInvalidLifecycleOwner()
    }

    /**
     * LifecycleOwner 是否失效
     * @return `true` yes, `false` no
     */
    fun isInvalidLifecycleOwner(): Boolean {
        if (lifecycleOwner == null) return true
        return (lifecycleOwner!!.lifecycle.currentState == Lifecycle.State.DESTROYED)
    }

    /**
     * 尝试获取 LifecycleOwner
     * @param view View
     */
    fun tryGetLifecycleOwner(view: View?) {
        if (isValidLifecycleOwner()) return
        lifecycleOwner = lifecycleGet?.get()
        if (isValidLifecycleOwner()) return
        view?.let { lifecycleOwner = findLifecycleOwner(it) }
    }

    /**
     * 从给定的 View 中获取 LifecycleOwner
     * @param view View
     * @return LifecycleOwner?
     * 如果 View 没有关联 ViewDataBinding 则通过 view.context 获取
     */
    fun findLifecycleOwner(view: View): LifecycleOwner? {
        val binding = DataBindingUtil.findBinding<ViewDataBinding>(view)
        var lifecycleOwner: LifecycleOwner? = binding?.lifecycleOwner
        val ctx = view.context
        if (lifecycleOwner == null && ctx is LifecycleOwner) {
            lifecycleOwner = ctx
        }

        return lifecycleOwner
    }
}