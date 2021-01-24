package dev.base.expand.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.base.able.IDevBaseViewBinding
import dev.base.utils.ViewBindingUtils

/**
 * detail: Content Activity ViewBinding 基类
 * @author Ttt
 */
abstract class DevBaseContentViewBindingActivity<VB : ViewBinding> : DevBaseContentActivity(),
    IDevBaseViewBinding<VB> {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isViewBinding()) {
            // ViewBinding 初始化处理
            binding = viewBinding(layoutInflater, null)
        }
    }

    // =======================
    // = IDevBaseViewBinding =
    // =======================

    final override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB {
        return ViewBindingUtils.viewBindingJavaClass(
            inflater,
            container,
            getBindingView(),
            javaClass
        )
    }

    final override fun getBindingView(): View? {
        return layoutView
    }
}