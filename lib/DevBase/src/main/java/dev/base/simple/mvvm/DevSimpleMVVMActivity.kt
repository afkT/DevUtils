package dev.base.simple.mvvm

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import dev.DevUtils
import dev.base.core.arch.mvvm.DevBaseMVVMActivity
import dev.base.core.interfaces.IDevBase
import dev.base.simple.ActivityVMType
import dev.base.simple.contracts.ISimpleAgile
import dev.base.simple.contracts.binding.BindingActivityView
import dev.base.simple.contracts.lifecycle.IActivityLifecycle
import dev.base.simple.contracts.lifecycle.IFragmentLifecycle
import dev.base.simple.contracts.lifecycle.LifecycleManager
import dev.utils.app.assist.lifecycle.fragment.FragmentLifecycleAssist
import dev.utils.common.ClassUtils

/**
 * detail: DevBase MVVM Simple Activity
 * @author Ttt
 * 在 [DevBaseMVVMActivity] 基础上实现 ViewModel 初始化
 * 以及进行敏捷简化开发扩展接口
 */
abstract class DevSimpleMVVMActivity<VDB : ViewDataBinding, VM : ViewModel>(
    private val bindLayoutId: Int = IDevBase.NONE,
    private val bindLayoutView: BindingActivityView? = null,
    private val bindViewModelId: Int = IDevBase.NONE,
    private val vmType: ActivityVMType = ActivityVMType.ACTIVITY
) : DevBaseMVVMActivity<VDB, VM>(),
    ISimpleAgile,
    IActivityLifecycle,
    IFragmentLifecycle {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 监听生命周期
        addLifecycleListener()
        // 内部初始化前调用
        simpleInit()
        // 内部初始化
        innerInitialize()
        // 内部初始化后开始流程调用
        simpleStart()
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
                ActivityVMType.ACTIVITY -> {
                    viewModelAssist.getActivityViewModel(
                        this, clazz
                    )?.let {
                        innerViewModel(it)
                    }
                }

                ActivityVMType.APPLICATION -> {
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

    // ==============
    // = ILifecycle =
    // ==============

    // Fragment 生命周期辅助类 ( 所有内部访问必须通过 fragmentLifecycleAssist() 方法, 便于子类完整 override )
    private val _fragmentLifecycleAssist: FragmentLifecycleAssist by lazy {
        FragmentLifecycleAssist(supportFragmentManager)
    }

    /**
     * 获取 Fragment 生命周期辅助类
     * @return [FragmentLifecycleAssist]
     * 子类可重写以返回绑定不同 FragmentManager 的辅助类 ( 例如切换到宿主 Activity 的 supportFragmentManager )
     */
    open fun fragmentLifecycleAssist(): FragmentLifecycleAssist {
        return _fragmentLifecycleAssist
    }

    /**
     * 监听生命周期
     * 子类可重写以调整生命周期监听挂载逻辑 ( 例如只监听 Activity 不监听 Fragment )
     */
    protected open fun addLifecycleListener() {
        // 是否监听生命周期 ( 实现了则表示需要监听 )
        activityLifecycleImpl()?.let { impl ->
            // 添加 Activity 生命周期通知事件
            LifecycleManager.activity().addListener(this, impl)
        }
        // 是否监听生命周期 ( 实现了则表示需要监听 )
        fragmentLifecycleImpl()?.let { impl ->
            // 注册绑定 Fragment 生命周期事件处理
            fragmentLifecycleAssist().registerFragmentLifecycleCallbacks(true)
            // 添加 Fragment 生命周期通知事件
            fragmentLifecycleAssist().setAbstractFragmentLifecycle(impl)
        }
    }
}