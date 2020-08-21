package dev.base.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.base.able.IDevBaseViewBinding
import dev.base.activity.DevBaseActivity
import dev.base.utils.ViewBindingUtils

/**
 * detail: Activity ViewBinding 基类
 * @author Ttt
 */
abstract class DevBaseViewBindingActivity<VB : ViewBinding> : DevBaseActivity(),
    IDevBaseViewBinding<VB> {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding 初始化处理
        binding = viewBinding(layoutInflater, null)
    }

    // =======================
    // = IDevBaseViewBinding =
    // =======================

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return ViewBindingUtils.viewBindingJavaClass(
            inflater,
            container,
            getBindingView(),
            javaClass
        )
    }
}