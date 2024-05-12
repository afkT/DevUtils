package dev.simple.app.extension.mvvm

import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppActivity
import dev.simple.app.BaseViewModel
import dev.simple.app.base.ActivityVMType
import dev.simple.app.base.inter.BindingActivityView
import dev.simple.app.extension.theme.BaseUIThemeActivity

/**
 * detail: Base MVVM Activity
 * @author Ttt
 * 在 [BaseUIThemeActivity] 基础上进行关联赋值
 * 如果无特殊需求请使用 [BaseAppActivity]
 */
abstract class BaseMVVMActivity<VDB : ViewDataBinding, VM : BaseViewModel> :
    BaseUIThemeActivity<VDB, VM> {

    private var viewModelId: Int

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY
    ) : super(bindLayoutId, null, vmType) {
        viewModelId = bindViewModelId
    }

    constructor(
        bindLayoutView: BindingActivityView?,
        bindViewModelId: Int,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY
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