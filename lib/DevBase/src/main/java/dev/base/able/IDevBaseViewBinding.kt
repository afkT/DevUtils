package dev.base.able

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * detail: 基类 ViewBinding 接口
 * @author Ttt
 */
interface IDevBaseViewBinding<VB : ViewBinding> {

    /**
     * View Bind 初始化操作
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     */
    fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    /**
     * 获取待 Bind View
     */
    fun getBindingView(): View?

    /**
     * 是否 Bind View
     */
    fun isViewBinding(): Boolean {
        return true
    }

    /**
     * 是否分离 ( 销毁 ) Binding
     */
    fun isDetachBinding(): Boolean {
        return isViewBinding()
    }
}