package dev.base.simple.contracts.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel

/**
 * detail: 监听生命周期 ViewModel
 * @author Ttt
 */
open class LifecycleViewModel : ViewModel(),
    DefaultLifecycleObserver {

    // 日志 TAG
    val TAG = this.javaClass.simpleName
}