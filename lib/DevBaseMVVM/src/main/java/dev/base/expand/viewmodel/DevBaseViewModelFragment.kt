package dev.base.expand.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.base.able.IDevBaseViewModel
import dev.base.fragment.DevBaseFragment
import dev.base.utils.assist.DevBaseViewModelAssist

/**
 * detail: Fragment ViewModel 基类
 * @author Ttt
 */
abstract class DevBaseViewModelFragment<VM : ViewModel> : DevBaseFragment(),
    IDevBaseViewModel<VM> {

    lateinit var viewModel: VM

    @JvmField // DevBase ViewModel 辅助类
    protected var viewModelAssist = DevBaseViewModelAssist()

    // =====================
    // = IDevBaseViewModel =
    // =====================

    override fun getAppViewModelProvider(application: Application?): ViewModelProvider? {
        return viewModelAssist.getAppViewModelProvider(application)
    }

    override fun getAppFactory(application: Application?): ViewModelProvider.Factory? {
        return viewModelAssist.getAppFactory(application)
    }
}