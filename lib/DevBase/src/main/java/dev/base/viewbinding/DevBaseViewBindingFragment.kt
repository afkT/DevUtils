package dev.base.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.base.fragment.DevBaseFragment
import dev.utils.LogPrintUtils
import java.lang.reflect.ParameterizedType

/**
 * detail: Fragment ViewBinding 基类
 * @author Ttt
 */
abstract class DevBaseViewBindingFragment<VB : ViewBinding> : DevBaseFragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return viewBindingInit(container)
    }

    /**
     * ViewBinding 初始化处理
     */
    private fun viewBindingInit(container: ViewGroup?): View? {
        try {
            val type = javaClass.genericSuperclass as ParameterizedType
            val clazz = type.actualTypeArguments[0] as Class<VB>

            if (mContentView != null) {
                val method = clazz.getMethod("bind", View::class.java)
                _binding = method.invoke(null, mContentView) as VB
            } else {
                val method = clazz.getMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.java
                )
                _binding = method.invoke(null, layoutInflater, container, false) as VB
                mContentView = _binding!!.root
            }
        } catch (e: Exception) {
            LogPrintUtils.eTag(mTag, e, "viewBindingInit")
        }
        return mContentView
    }
}