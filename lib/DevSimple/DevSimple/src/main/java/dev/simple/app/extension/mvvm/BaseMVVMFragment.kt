package dev.simple.app.extension.mvvm

import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppViewModel
import dev.simple.app.base.FragmentVMType
import dev.simple.app.base.inter.BindingFragmentView
import dev.simple.app.extension.theme.BaseUIThemeFragment

/**
 * detail: Base MVVM Fragment
 * @author Ttt
 * 在 [BaseUIThemeFragment] 基础上进行 ViewModel 关联赋值
 */
abstract class BaseMVVMFragment<VDB : ViewDataBinding, VM : BaseAppViewModel> :
    BaseUIThemeFragment<VDB, VM> {

    private var viewModelId: Int

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT
    ) : super(bindLayoutId, null, vmType) {
        viewModelId = bindViewModelId
    }

    constructor(
        bindLayoutView: BindingFragmentView,
        bindViewModelId: Int,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT
    ) : super(0, bindLayoutView, vmType) {
        viewModelId = bindViewModelId
    }

    // =====================
    // = IDevBaseViewModel =
    // =====================

    override fun initViewModel() {
        super.initViewModel()
        try {
            // 关联 ViewModel 对象值
            binding.setVariable(viewModelId, viewModel)
        } catch (_: Exception) {
        }
    }
}