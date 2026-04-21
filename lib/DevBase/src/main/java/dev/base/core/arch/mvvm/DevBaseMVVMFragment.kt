package dev.base.core.arch.mvvm

import android.app.Application
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.base.core.arch.databinding.DevBaseVDBFragment
import dev.base.core.interfaces.IDevBaseViewModel
import dev.base.utils.assist.DevBaseViewModelAssist

/**
 * detail: Fragment MVVM 基类
 * @author Ttt
 */
abstract class DevBaseMVVMFragment<VDB : ViewDataBinding, VM : ViewModel> :
    DevBaseVDBFragment<VDB>(),
    IDevBaseViewModel {

    lateinit var viewModel: VM

    @JvmField // DevBase ViewModel 辅助类 ( 子类可重写 newViewModelAssist() 替换实现 )
    @Suppress("LeakingThis")
    protected var viewModelAssist: DevBaseViewModelAssist = newViewModelAssist()

    /**
     * 创建 [DevBaseViewModelAssist] 实现
     * 子类可重写以替换 ViewModel 创建 / 缓存策略 ( 例如注入自定义 ViewModelProvider.Factory )
     * 注意: 该方法会在构造阶段被调用, 实现不应依赖子类自身状态
     */
    protected open fun newViewModelAssist(): DevBaseViewModelAssist = DevBaseViewModelAssist()

    // =====================
    // = IDevBaseViewModel =
    // =====================

    // =====================
    // = Activity Provider =
    // =====================

    override fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T? {
        return viewModelAssist.getActivityViewModelCache(activity, modelClass)
    }

    // =====================
    // = Fragment Provider =
    // =====================

    override fun <T : ViewModel> getFragmentViewModel(modelClass: Class<T>): T? {
        return viewModelAssist.getFragmentViewModelCache(this, modelClass)
    }

    override fun <T : ViewModel> getFragmentViewModel(
        fragment: Fragment?,
        modelClass: Class<T>
    ): T? {
        return viewModelAssist.getFragmentViewModel(fragment, modelClass)
    }

    // ========================
    // = Application Provider =
    // ========================

    override fun <T : ViewModel> getAppViewModel(
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