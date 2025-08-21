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
            // ViewDataBinding 初始化处理
            binding = DataBindingUtil.bind(getBindingView()!!)!!
            // 支持 LiveData 绑定 xml 数据改变 UI 自动会更新
            binding.lifecycleOwner = this
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isDetachBinding()) binding.unbind()
    }

    // ===========================
    // = IDevBaseViewDataBinding =
    // ===========================

    final override fun getBindingView(): View? {
        return layoutView
    }
}