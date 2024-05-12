package dev.simple.app.controller.viewmodel

import androidx.lifecycle.MutableLiveData
import dev.mvvm.base.viewmodel.LifecycleViewModel
import dev.simple.app.controller.BaseKeyEventController
import dev.simple.app.controller.BaseUIController
import dev.simple.app.controller.loading.BaseLoadingController
import dev.simple.app.controller.loading.BaseLoadingSkeletonController

/**
 * detail: 内部 Controller 持有 ViewModel
 * @author Ttt
 * 注意如果 Activity、Fragment 创建同一个 ViewModel 会导致对象复用、数据覆盖
 * 需自行根据场景设计使用
 */
open class ControllerViewModel : LifecycleViewModel() {

    // ======================================
    // = 受 isControllerViewModelInit() 控制 =
    // ======================================

    // ============
    // = Activity =
    // ============

    // 基础 KeyEvent 物理按键 控制封装
    val keyEventController = MutableLiveData<BaseKeyEventController>()

    // ============
    // = Fragment =
    // ============

    // =====================
    // = Activity、Fragment =
    // =====================

    // 基础 UI 控制封装
    val uiController = MutableLiveData<BaseUIController>()

    // ========================================
    // = 不受 isControllerViewModelInit() 控制 =
    // ========================================

    // =============
    // = Extension =
    // =============

    // ===========
    // = Loading =
    // ===========

    // 基础 Loading 控制封装
    val loadingController = MutableLiveData<BaseLoadingController<*>>()

    // 基础 Loading Skeleton 控制封装
    val loadingSkeletonController = MutableLiveData<BaseLoadingSkeletonController<*>>()
}