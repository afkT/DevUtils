package dev.base.simple.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import dev.DevUtils
import dev.base.core.arch.mvvm.DevBaseMVVMFragment
import dev.base.core.interfaces.IDevBase
import dev.base.simple.FragmentVMType
import dev.base.simple.contracts.ISimpleAgile
import dev.base.simple.contracts.binding.BindingFragmentView
import dev.utils.common.ClassUtils

/**
 * detail: DevBase MVVM Simple Fragment
 * @author Ttt
 */
abstract class DevSimpleMVVMFragment<VDB : ViewDataBinding, VM : ViewModel>(
    private val bindLayoutId: Int = IDevBase.NONE,
    private val bindLayoutView: BindingFragmentView? = null,
    private val bindViewModelId: Int = IDevBase.NONE,
    private val vmType: FragmentVMType = FragmentVMType.FRAGMENT
) : DevBaseMVVMFragment<VDB, VM>(),
    ISimpleAgile {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // 内部初始化前调用
        simpleInit()
        // 内部初始化
        innerInitialize()
        // 内部初始化后开始流程调用
        simpleStart()
        return contentAssist.bindingRoot()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化 ViewModel
        initViewModel()
        // 简化预加载
        simplePreLoad()
        // 初始化方法
        initOrder()
        // 敏捷开发简化调用
        simpleAgile()
    }

    // =====================
    // = IDevBaseViewModel =
    // =====================

    override fun initViewModel() {
    }

    // ============
    // = 内部初始化 =
    // ============

    protected open fun innerInitialize() {

        // =================
        // = initViewModel =
        // =================

        // 不放在 initViewModel() 中, 是防止重写 initViewModel() 忘记调用 super.initViewModel()

        try {
            val clazz = ClassUtils.getGenericSuperclass(this.javaClass, 1) as Class<VM>
            when (vmType) {
                FragmentVMType.FRAGMENT -> {
                    viewModelAssist.getFragmentViewModel(
                        this, clazz
                    )?.let {
                        innerViewModel(it)
                    }
                }

                FragmentVMType.ACTIVITY -> {
                    viewModelAssist.getActivityViewModel(
                        activity, clazz
                    )?.let {
                        innerViewModel(it)
                    }
                }

                FragmentVMType.APPLICATION -> {
                    viewModelAssist.getAppViewModel(
                        DevUtils.getApplication(), clazz
                    )?.let {
                        innerViewModel(it)
                    }
                }
            }
        } catch (e: Exception) {
            assist.printLog(e, "innerInitialize - initViewModel")
        }
    }

    /**
     * 内部 ViewModel 初始化
     * @param vmObject VM
     */
    protected open fun innerViewModel(vmObject: VM) {
        try {
            viewModel = vmObject
            if (vmObject is LifecycleObserver) {
                lifecycle.addObserver(vmObject)
            }
        } catch (e: Exception) {
            assist.printLog(e, "innerViewModel")
        }
        try {
            // 关联 ViewModel 对象值
            binding.setVariable(bindViewModelId, viewModel)
        } catch (_: Exception) {
        }
        onViewModelReady(viewModel)
    }

    /**
     * ViewModel 在 [innerViewModel] 中完成赋值、生命周期与 DataBinding 关联后回调。
     * 子类可覆写以做额外初始化，不应替代 [initViewModel] 或内部自动解析逻辑（避免与防漏调 super 设计冲突）
     * @param vm 当前 [viewModel] 实例
     */
    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun onViewModelReady(vm: VM) {
    }

    // ==================
    // = IDevBaseLayout =
    // ==================

    /**
     * 获取 Layout Id ( 用于 contentLinear addView )
     * @return Layout Id
     */
    override fun baseLayoutId(): Int {
        return bindLayoutId
    }

    /**
     * 获取 Layout View ( 用于 contentLinear addView )
     * @return Layout View
     */
    override fun baseLayoutView(): View? {
        return bindLayoutView?.bind(this, layoutInflater)
    }
}