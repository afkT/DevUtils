package dev.base.able

import androidx.lifecycle.ViewModel

/**
 * detail: 基类 ViewModel 接口
 * @author Ttt
 */
interface IDevBaseViewModel<VDB : ViewModel> {

    /**
     * 初始化 [ViewModel]
     */
    fun initViewModel()
}