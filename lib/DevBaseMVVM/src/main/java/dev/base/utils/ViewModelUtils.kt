package dev.base.utils

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dev.base.utils.assist.DevBaseViewModelAssist
import dev.utils.LogPrintUtils

/**
 * detail: ViewModel 工具类
 * @author Ttt
 */
object ViewModelUtils {

    // 日志 TAG
    private val TAG = ViewModelUtils::class.java.simpleName

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
        try {
            return ViewModelProvider(activity).get(modelClass)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "getActivityViewModel")
        }
        return null
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
        try {
            return ViewModelProvider(fragment).get(modelClass)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "getFragmentViewModel")
        }
        return null
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
        try {
            return getAppViewModelProvider(application)?.get(modelClass)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "getAppViewModel")
        }
        return null
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
        if (application == null) return null
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    // ==========================
    // = DevBaseViewModelAssist =
    // ==========================

    // =====================
    // = Activity Provider =
    // =====================

    /**
     * 获取 Activity ViewModel
     * @param viewModelAssist [DevBaseViewModelAssist]
     * @param activity [FragmentActivity]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getActivityViewModel(
        viewModelAssist: DevBaseViewModelAssist,
        activity: FragmentActivity?,
        modelClass: Class<T>
    ): T? {
        if (activity == null) return null
        try {
            return viewModelAssist.getActivityViewModel(activity, modelClass)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "viewModelAssist - getActivityViewModel")
        }
        return null
    }

    /**
     * 获取 Activity ViewModel
     * @param viewModelAssist [DevBaseViewModelAssist]
     * @param activity [FragmentActivity]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getActivityViewModelCache(
        viewModelAssist: DevBaseViewModelAssist,
        activity: FragmentActivity?,
        modelClass: Class<T>
    ): T? {
        if (activity == null) return null
        try {
            return viewModelAssist.getActivityViewModelCache(activity, modelClass)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "viewModelAssist - getActivityViewModelCache")
        }
        return null
    }

    // =====================
    // = Fragment Provider =
    // =====================

    /**
     * 获取 Fragment ViewModel
     * @param viewModelAssist [DevBaseViewModelAssist]
     * @param fragment [Fragment]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getFragmentViewModel(
        viewModelAssist: DevBaseViewModelAssist,
        fragment: Fragment?,
        modelClass: Class<T>
    ): T? {
        try {
            return viewModelAssist.getFragmentViewModel(fragment, modelClass)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "viewModelAssist - getFragmentViewModel")
        }
        return null
    }

    /**
     * 获取 Fragment ViewModel
     * @param viewModelAssist [DevBaseViewModelAssist]
     * @param fragment [Fragment]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getFragmentViewModelCache(
        viewModelAssist: DevBaseViewModelAssist,
        fragment: Fragment?,
        modelClass: Class<T>
    ): T? {
        try {
            return viewModelAssist.getFragmentViewModelCache(fragment, modelClass)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "viewModelAssist - getFragmentViewModelCache")
        }
        return null
    }

    // ========================
    // = Application Provider =
    // ========================

    /**
     * 获取 Application ViewModel
     * @param viewModelAssist [DevBaseViewModelAssist]
     * @param application [Application]
     * @param modelClass [ViewModel]
     * @return [T]
     */
    fun <T : ViewModel?> getAppViewModel(
        viewModelAssist: DevBaseViewModelAssist,
        application: Application?,
        modelClass: Class<T>
    ): T? {
        try {
            return viewModelAssist.getAppViewModel(application, modelClass)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "viewModelAssist - getAppViewModel")
        }
        return null
    }
}