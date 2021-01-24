package dev.base.utils.assist

import android.app.Application
import androidx.fragment.app.Fragment
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

    private var mActivityProvider: ViewModelProvider? = null
    private var mFragmentProvider: ViewModelProvider? = null
    private var mFactory: ViewModelProvider.Factory? = null

    // =====================
    // = Activity Provider =
    // =====================

    /**
     * 获取 Activity ViewModel
     * @param activity [FragmentActivity]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getActivityViewModel(
        activity: FragmentActivity?,
        modelClass: Class<T>
    ): T? {
        if (activity == null) return null
        return ViewModelProvider(activity).get(modelClass)
    }

    /**
     * 获取 Activity ViewModel
     * @param activity [FragmentActivity]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getActivityViewModelCache(
        activity: FragmentActivity?,
        modelClass: Class<T>
    ): T? {
        if (activity == null) return null
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(activity)
        }
        return mActivityProvider!!.get(modelClass)
    }

    // =====================
    // = Fragment Provider =
    // =====================

    /**
     * 获取 Fragment ViewModel
     * @param fragment [Fragment]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getFragmentViewModel(
        fragment: Fragment?,
        modelClass: Class<T>
    ): T? {
        if (fragment == null) return null
        return ViewModelProvider(fragment).get(modelClass)
    }

    /**
     * 获取 Fragment ViewModel
     * @param fragment [Fragment]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getFragmentViewModelCache(
        fragment: Fragment?,
        modelClass: Class<T>
    ): T? {
        if (fragment == null) return null
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(fragment)
        }
        return mFragmentProvider!!.get(modelClass)
    }

    // ========================
    // = Application Provider =
    // ========================

    /**
     * 获取 Application ViewModel
     * @param application [Application]
     * @param modelClass [ViewModel]
     * @return [T]
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