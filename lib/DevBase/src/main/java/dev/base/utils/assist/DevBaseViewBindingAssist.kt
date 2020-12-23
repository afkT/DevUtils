package dev.base.utils.assist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.base.utils.ViewBindingUtils

/**
 * detail: DevBase ViewBinding 辅助类
 * @author Ttt
 */
class DevBaseViewBindingAssist<VB : ViewBinding> {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    /**
     * ViewBinding 初始化处理
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     * @param view      待绑定 View
     * @param clazz     VB Class
     */
    fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        view: View?,
        clazz: Class<VB>
    ): DevBaseViewBindingAssist<VB> {
        _binding = ViewBindingUtils.viewBinding(
            inflater, container, view, clazz
        )
        return this
    }

    /**
     * 销毁 binding
     */
    fun destroy() {
        _binding = null
    }
}