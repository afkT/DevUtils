package dev.base.expand.viewmodel

import androidx.lifecycle.ViewModel
import dev.base.able.IDevBaseViewModel
import dev.base.activity.DevBaseActivity
import dev.base.utils.assist.DevBaseViewModelAssist

/**
 * detail: Activity ViewModel 基类
 * @author Ttt
 */
abstract class DevBaseViewModelActivity<VM : ViewModel> : DevBaseActivity(),
    IDevBaseViewModel<VM> {

    lateinit var viewModel: VM

    @JvmField // DevBase ViewModel 辅助类
    protected var viewModelAssist = DevBaseViewModelAssist()
}