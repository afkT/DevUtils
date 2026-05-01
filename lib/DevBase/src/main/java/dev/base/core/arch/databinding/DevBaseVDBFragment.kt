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
            _binding = performDataBindingInflate()
            // 支持 LiveData 绑定 xml 数据改变 UI 自动会更新
            _binding!!.lifecycleOwner = this
            onViewDataBindingReady(_binding!!)
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

    /**
     * 待 DataBinding 绑定的根 View，默认与 [layoutView] 一致。
     * 子类可重写以绑定到其它 View，无需复制整段 [onCreateView]。
     */
    override fun getBindingView(): View? {
        return layoutView
    }

    // ============
    // = protected =
    // ============

    /**
     * 由 [getBindingView] 得到根 View 并执行 DataBindingUtil.bind。
     * 子类可重写以自定义绑定逻辑，无需复制 [onCreateView]。
     */
    protected open fun performDataBindingInflate(): VDB {
        return DataBindingUtil.bind(getBindingView()!!)!!
    }

    /**
     * Binding 已创建且已设置 [ViewDataBinding.setLifecycleOwner] 后回调。
     */
    protected open fun onViewDataBindingReady(binding: VDB) {
    }
}