package dev.base.core.arch.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dev.base.core.AbstractDevBaseFragment
import dev.base.core.interfaces.IDevBaseViewDataBinding

/**
 * detail: Fragment ViewDataBinding 基类
 * @author Ttt
 */
abstract class DevBaseVDBFragment<VDB : ViewDataBinding> : AbstractDevBaseFragment(),
    IDevBaseViewDataBinding {

    private var _binding: VDB? = null
    val binding: VDB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (isViewBinding()) {
            // ViewDataBinding 初始化处理
            _binding = DataBindingUtil.bind(getBindingView()!!)!!
            // 支持 LiveData 绑定 xml 数据改变 UI 自动会更新
            _binding?.lifecycleOwner = this
        }
        return contentAssist.bindingRoot()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isDetachBinding()) {
            _binding?.unbind()
            _binding = null
        }
    }

    // ===========================
    // = IDevBaseViewDataBinding =
    // ===========================

    final override fun getBindingView(): View? {
        return layoutView
    }
}