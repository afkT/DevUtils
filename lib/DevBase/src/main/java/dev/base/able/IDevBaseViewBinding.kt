package dev.base.able

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * detail: ViewBinding 基类接口
 * @author Ttt
 */
interface IDevBaseViewBinding<VB : ViewBinding> {

    /**
     * View Bind 初始化操作
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     */
    fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

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
     * 设计时考虑为了开发中保证准确绑定, 当 [ViewBindingUtils] 解析失败会抛出异常
     */
    fun isTryViewBindingCatch(): Boolean {
        return false
    }
}