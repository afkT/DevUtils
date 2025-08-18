package dev.simple.app.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import dev.DevUtils
import dev.base.expand.content.DevBaseContentMVVMActivity
import dev.simple.app.BaseAppViewModel
import dev.simple.app.base.lifecycle.IActivityLifecycle
import dev.simple.app.base.lifecycle.LifecycleManager
import dev.simple.app.base.simple.ISimpleAgile
import dev.simple.app.controller.BaseKeyEventController
import dev.simple.app.controller.BaseUIController
import dev.simple.app.controller.BaseVMController
import dev.simple.app.controller.interfaces.IController
import dev.utils.app.ActivityUtils
import dev.utils.common.ClassUtils

/**
 * detail: Base MVVM Activity
 * @author Ttt
 */
abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseAppViewModel>(
    private val vmType: ActivityVMType = ActivityVMType.ACTIVITY
) : DevBaseContentMVVMActivity<VDB, VM>(),
    IController,
    ISimpleAgile,
    IActivityLifecycle {

    // Activity
    open val mActivity: AppCompatActivity get() = this

    // 基础 ViewModel 控制封装
    private val vmController: BaseVMController<VDB, VM> = BaseVMController(this)

    // 基础 UI 控制封装
    open val uiController: BaseUIController by lazy {
        BaseUIController(contentAssist, this, this)
    }

    // 基础 KeyEvent 物理按键 控制封装
    open val keyEventController = BaseKeyEventController()

    // =

    override fun onCreate(savedInstanceState: Bundle?) {
        // 初始化 UI 样式
        uiController.initialize(this)
        super.onCreate(savedInstanceState)
        // 是否监听生命周期 ( 实现了则表示需要监听 )
        activityLifecycleIMPL()?.let { impl ->
            // 添加 Activity 生命周期通知事件
            LifecycleManager.activity().addListener(this, impl)
        }
        // 内部初始化前调用
        simpleInit()
        // 内部初始化
        innerInitialize()
        // 内部初始化后开始流程调用
        simpleStart()
        // 初始化 ViewModel
        initViewModel()
        // 添加基础骨架 View
        uiController.addSkeletonView(this)
        // 简化预加载
        simplePreLoad()
        // 预加载方法
        preLoad()
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

    private fun innerInitialize() {

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
    private fun innerViewModel(vmObject: VM) {
        try {
            viewModel = vmObject
            lifecycle.addObserver(vmObject)
            // 初始化 ViewModel 属性值
            vmController.initialize(
                binding = binding,
                viewModel = vmObject,
                uiController = uiController,
                keyEventController = keyEventController
            )
        } catch (e: Exception) {
            assist.printLog(e, "innerViewModel")
        }
    }

    // =====================
    // = override activity =
    // =====================

    @Deprecated("deprecated onBackPressed()")
    override fun onBackPressed() {
        // 返回键拦截监听
        if (keyEventController.backIntercept?.intercept() == true) {
            return
        }
        // 返回键退出 App 拦截监听
        if (keyEventController.exitBackIntercept?.intercept() == true) {
            return
        }
        super.onBackPressed()
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 关闭当前 Activity
     */
    fun finishActivity() {
        ActivityUtils.getManager().finishActivity(this)
    }
}