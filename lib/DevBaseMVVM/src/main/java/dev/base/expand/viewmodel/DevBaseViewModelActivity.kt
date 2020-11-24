package dev.base.expand.viewmodel

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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