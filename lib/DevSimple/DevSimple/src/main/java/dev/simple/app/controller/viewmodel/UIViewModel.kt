package dev.simple.app.controller.viewmodel

import androidx.databinding.ObservableInt
import dev.utils.app.ScreenUtils

/**
 * detail: UI ViewModel
 * @author Ttt
 */
open class UIViewModel : ControllerViewModel() {

    // 状态栏高度
    val statusBarHeight = ObservableInt(ScreenUtils.getStatusBarHeight())

    // 状态栏高度 ( 负数 )
    val statusBarHeightMinus = ObservableInt(-statusBarHeight.get())
}