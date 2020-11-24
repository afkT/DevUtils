package dev.base.expand.mvvm

import android.app.Application
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.base.able.IDevBaseViewModel
import dev.base.expand.viewdata.DevBaseViewDataBindingActivity
import dev.base.utils.assist.DevBaseViewModelAssist

/**
 * detail: Activity MVVM 基类
 * @author Ttt
 */
abstract class DevBaseMVVMActivity<VDB : ViewDataBinding, VM : ViewModel> :
    DevBaseViewDataBindingActivity<VDB>(),
    IDevBaseViewModel<VM> {

    lateinit var viewModel: VM

    @JvmField // DevBase ViewModel 辅助类
    protected var viewModelAssist = DevBaseViewModelAssist()

    // =====================
    // = IDevBaseViewModel =
    // =====================

    // =====================
    // = Activity Provider =
    // =====================

    override fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T? {
        return viewModelAssist.getActivityViewModelCache(this, modelClass)
    }

    // =====================
    // = Fragment Provider =
    // =====================

    override fun <T : ViewModel?> getFragmentViewModel(modelClass: Class<T>): T? {
        return null
    }

    override fun <T : ViewModel?> getFragmentViewModel(
        fragment: Fragment?,
        modelClass: Class<T>
    ): T? {
        return viewModelAssist.getFragmentViewModel(fragment, modelClass)
    }

    // ========================
    // = Application Provider =
    // ========================

    override fun <T : ViewModel?> getAppViewModel(
        application: Application?,
        modelClass: Class<T>
    ): T? {
        return viewModelAssist.getAppViewModel(application, modelClass)
    }

    override fun getAppViewModelProvider(application: Application?): ViewModelProvider? {
        return viewModelAssist.getAppViewModelProvider(application)
    }

    override fun getAppFactory(application: Application?): ViewModelProvider.Factory? {
        return viewModelAssist.getAppFactory(application)
    }
}