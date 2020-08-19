package dev.base.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.utils.LogPrintUtils
import java.lang.reflect.ParameterizedType

/**
 * detail: ViewBinding 工具类
 * @author Ttt
 */
object ViewBindingUtils {

    // 日志 TAG
    private val TAG = ViewBindingUtils::class.java.simpleName

    /**
     * 获取 VB Class
     * @param clazz javaClass
     */
    fun <VB : ViewBinding, T : Any> getClassVB(clazz: Class<T>): Class<VB> {
        val type = clazz.genericSuperclass as ParameterizedType
        return type.actualTypeArguments[0] as Class<VB>
    }

    /**
     * ViewBinding 初始化处理 ( 通过传入 javaClass )
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     * @param view      待绑定 View
     * @param clazz     javaClass
     */
    fun <VB : ViewBinding, T : Any> viewBindingJavaClass(
        inflater: LayoutInflater,
        container: ViewGroup?,
        view: View?,
        clazz: Class<T>
    ): VB {
        try {
            return viewBinding(inflater, container, view, getClassVB(clazz))
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "viewBinding")
        }
        throw Exception("${clazz.simpleName} viewBinding error")
    }

    /**
     * ViewBinding 初始化处理
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     * @param view      待绑定 View
     * @param clazz     VB Class
     */
    fun <VB : ViewBinding> viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        view: View?,
        clazz: Class<VB>
    ): VB {
        try {
            if (view != null) {
                val method = clazz.getMethod("bind", View::class.java)
                return method.invoke(null, view) as VB
            } else {
                val method = clazz.getMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.java
                )
                return method.invoke(null, inflater, container, false) as VB
            }
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "viewBinding")
        }
        throw Exception("${clazz.simpleName} viewBinding error")
    }
}