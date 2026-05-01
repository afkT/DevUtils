package dev.base.core.arch.databinding

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dev.base.core.AbstractDevBaseActivity
import dev.base.core.interfaces.IDevBaseViewDataBinding

/**
 * detail: Activity ViewDataBinding 基类
 * @author Ttt
 */
abstract class DevBaseVDBActivity<VDB : ViewDataBinding> : AbstractDevBaseActivity(),
    IDevBaseViewDataBinding {

    lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isViewBinding()) {
            binding = performDataBindingInflate()
            // 支持 LiveData 绑定 xml 数据改变 UI 自动会更新
            binding.lifecycleOwner = this
            onViewDataBindingReady(binding)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isDetachBinding()) binding.unbind()
    }

    // ===========================
    // = IDevBaseViewDataBinding =
    // ===========================

    /**
     * 待 DataBinding 绑定的根 View，默认与 [layoutView] 一致。
     * 子类可重写以绑定到其它 View（例如多 Tab 下非默认根节点），无需复制整段 [onCreate]。
     */
    override fun getBindingView(): View? {
        return layoutView
    }

    // ============
    // = protected =
    // ============

    /**
     * 由 [getBindingView] 得到根 View 并执行 DataBindingUtil.bind。
     * 子类可重写以插入自定义 Factory、替换绑定入口等，无需复制 [onCreate]。
     */
    protected open fun performDataBindingInflate(): VDB {
        return DataBindingUtil.bind(getBindingView()!!)!!
    }

    /**
     * Binding 已创建且已设置 [ViewDataBinding.setLifecycleOwner] 后回调，便于子类做额外配置。
     */
    protected open fun onViewDataBindingReady(binding: VDB) {
    }
}