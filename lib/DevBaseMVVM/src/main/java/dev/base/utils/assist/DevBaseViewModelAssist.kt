package dev.base.utils.assist

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * detail: DevBase ViewModel 辅助类
 * @author Ttt
 * <p></p>
 * 使用全局 ViewModel 则通过 Application implements ViewModelStoreOwner
 * 并通过 [getAppViewModel] 获取全局 ViewModel
 */
class DevBaseViewModelAssist {

    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mFactory: ViewModelProvider.Factory? = null

    // =====================
    // = Activity Provider =
    // =====================

    fun <T : ViewModel?> getActivityViewModel(
        activity: FragmentActivity?,
        modelClass: Class<T>
    ): T? {
        if (activity == null) return null
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(activity)
        }
        return mActivityProvider!!.get(modelClass)
    }

    // ========================
    // = Application Provider =
    // ========================

    /**
     * 获取 Application ViewModel
     * @param application [Application]
     * @param modelClass [ViewModel]
     */
    fun <T : ViewModel?> getAppViewModel(
        application: Application?,
        modelClass: Class<T>
    ): T? {
        if (application == null) return null
        return getAppViewModelProvider(application)?.get(modelClass)
    }

    /**
     * 获取 Application ViewModelProvider
     * @param application [Application]
     * @return [ViewModelProvider]
     */
    fun getAppViewModelProvider(application: Application?): ViewModelProvider? {
        if (application is ViewModelStoreOwner) {
            return ViewModelProvider(
                application as ViewModelStoreOwner, getAppFactory(application)!!
            )
        }
        return null
    }

    /**
     * 获取 Application AndroidViewModel Factory
     * @param application [Application]
     * @return [ViewModelProvider.Factory]
     */
    fun getAppFactory(application: Application?): ViewModelProvider.Factory? {
        if (mFactory != null) return mFactory
        if (application == null) return null
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory
    }
}