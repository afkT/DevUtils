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
     * 获取待 bind View
     * tips: 如果直接使用 R.layout.xxx 则直接返回 null 即可
     */
    fun getBindingView(): View?

    /**
     * 是否分离 ( 销毁 ) Binding
     */
    fun isDetachBinding(): Boolean {
        return false
    }
}