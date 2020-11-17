package dev.base.able

import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * detail: 基类 ViewDataBinding 接口
 * @author Ttt
 */
interface IDevBaseViewDataBinding<VDB : ViewDataBinding> {

    /**
     * 获取待 Bind View
     */
    fun getBindingView(): View?

    /**
     * 是否分离 ( 销毁 ) Binding
     */
    fun isDetachBinding(): Boolean {
        return true
    }

    /**
     * 是否 Bind View
     */
    fun isViewBinding(): Boolean {
        return true
    }

    /**
     * 是否捕获 ViewBinding 异常
     */
    fun isTryViewBindingCatch(): Boolean {
        return false
    }
}