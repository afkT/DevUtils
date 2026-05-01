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
 * 可继承并重写解析 LifecycleOwner 相关方法，自定义绑定生命周期来源
 */
open class ItemLifecycle protected constructor() {

    companion object {

        fun of(): ItemLifecycle {
            return ItemLifecycle()
        }
    }

    // 使用 LiveData 绑定使用（m 前缀避免与 [getLifecycleOwner] JVM 签名冲突）
    @JvmField
    protected var mLifecycleOwner: LifecycleOwner? = null

    // LifecycleOwner 通用获取接口
    @JvmField
    protected var mLifecycleGet: BindingGet<LifecycleOwner?>? = null

    // =============
    // = 对外公开方法 =
    // =============

    open fun getLifecycleOwner(): LifecycleOwner? {
        return mLifecycleOwner
    }

    open fun setLifecycleOwner(owner: LifecycleOwner?): ItemLifecycle {
        mLifecycleOwner = owner
        return this
    }

    open fun getLifecycleImpl(): BindingGet<LifecycleOwner?>? {
        return mLifecycleGet
    }

    open fun setLifecycleImpl(implGET: BindingGet<LifecycleOwner?>?): ItemLifecycle {
        mLifecycleGet = implGET
        return this
    }

    // ==========
    // = 核心方法 =
    // ==========

    /**
     * LifecycleOwner 是否有效
     * @return `true` yes, `false` no
     */
    open fun isValidLifecycleOwner(): Boolean {
        return !isInvalidLifecycleOwner()
    }

    /**
     * LifecycleOwner 是否失效
     * @return `true` yes, `false` no
     */
    open fun isInvalidLifecycleOwner(): Boolean {
        if (mLifecycleOwner == null) return true
        return (mLifecycleOwner?.lifecycle?.currentState == Lifecycle.State.DESTROYED)
    }

    /**
     * 尝试获取 LifecycleOwner
     * @param view View
     */
    open fun tryGetLifecycleOwner(view: View?) {
        if (isValidLifecycleOwner()) return
        mLifecycleOwner = mLifecycleGet?.get()
        if (isValidLifecycleOwner()) return
        view?.let { mLifecycleOwner = findLifecycleOwner(it) }
    }

    /**
     * 从给定的 View 中获取 LifecycleOwner
     * @param view View
     * @return LifecycleOwner?
     * 如果 View 没有关联 ViewDataBinding 则通过 view.context 获取
     */
    open fun findLifecycleOwner(view: View): LifecycleOwner? {
        val binding = DataBindingUtil.findBinding<ViewDataBinding>(view)
        var lifecycleOwner: LifecycleOwner? = binding?.lifecycleOwner
        val ctx = view.context
        if (lifecycleOwner == null && ctx is LifecycleOwner) {
            lifecycleOwner = ctx
        }

        return lifecycleOwner
    }
}