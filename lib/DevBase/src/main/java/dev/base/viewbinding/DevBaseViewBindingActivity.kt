package dev.base.viewbinding

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import dev.base.activity.DevBaseActivity
import dev.utils.LogPrintUtils
import java.lang.reflect.ParameterizedType

/**
 * detail: Activity ViewBinding 基类
 * @author Ttt
 */
abstract class DevBaseViewBindingActivity<VB : ViewBinding> : DevBaseActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding 初始化处理
        viewBindingInit()
    }

    /**
     * ViewBinding 初始化处理
     * =
     * 两种方式初始化 binding
     * binding = ViewBinding.bind(View)
     * binding = ViewBinding.inflate(layoutInflater) // setContentView(binding.root)
     */
    private fun viewBindingInit() {
        try {
            val type = javaClass.genericSuperclass as ParameterizedType
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val method = clazz.getMethod("bind", View::class.java)
            binding = method.invoke(null, getBindingView()) as VB

//            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
//            binding = method.invoke(null, layoutInflater) as VB
        } catch (e: Exception) {
            LogPrintUtils.eTag(mTag, e, "viewBindingInit")
        }
    }

    /**
     * 获取 ViewBinding bind View
     * =
     * getBindingView() 可以直接返回 mContentView 视实际设计情况而定
     * 主要是为了预留, 默认使用统一 R.layout.xx 进行 add ( Title、Body ) View 等设计情况
     */
    abstract fun getBindingView(): View
}