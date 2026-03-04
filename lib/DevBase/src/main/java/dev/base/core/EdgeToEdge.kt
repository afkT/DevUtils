package dev.base.core

import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.base.core.arch.databinding.DevBaseVDBActivity
import dev.base.core.arch.databinding.DevBaseVDBFragment

// ==============
// = EdgeToEdge =
// ==============

/**
 * 通用 Enable edge to edge【适配 API 35+】
 */
fun ComponentActivity.commonEnableEdgeToEdge(
    view: View,
    listener: OnApplyWindowInsetsListener? = DEFAULT_WINDOW_SYSTEM_BARS
): View {
    enableEdgeToEdge()
    // 给 View 设置 Window Insets 监听事件
    if (listener != null) {
        view.setWindowInsetsListener(listener)
    }
    return view
}

/**
 * 通用 Enable edge to edge【适配 API 35+】
 */
fun DevBaseVDBActivity<*>.actCommonEnableEdgeToEdge(
    listener: OnApplyWindowInsetsListener? = DEFAULT_WINDOW_SYSTEM_BARS
) {
    commonEnableEdgeToEdge(binding.root, listener)
}

/**
 * 通用设置 View Padding, 使 view 不会被 system bars 遮挡
 */
fun View.commonSystemBarsPadding() {
    setSystemBarsPadding()
}

// ================
// = WindowInsets =
// ================

/**
 * 给 View 设置 Window Insets 监听事件
 * @param listener [OnApplyWindowInsetsListener]
 */
fun View.setWindowInsetsListener(
    listener: OnApplyWindowInsetsListener = DEFAULT_WINDOW_SYSTEM_BARS
) {
    ViewCompat.setOnApplyWindowInsetsListener(this, listener)
}

// ==============
// = SystemBars =
// ==============

// 默认 SystemBars Window Insets 监听事件
val DEFAULT_WINDOW_SYSTEM_BARS: OnApplyWindowInsetsListener = { view, insets ->
    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
    view.setPadding(
        systemBars.left, systemBars.top,
        systemBars.right, systemBars.bottom
    )
    insets
}

/**
 * 给 View 设置 Padding, 使 view 不会被 system bars 遮挡
 */
fun View.setSystemBarsPadding(
    paddingLeft: Boolean = true,
    paddingTop: Boolean = true,
    paddingRight: Boolean = true,
    paddingBottom: Boolean = true
) {
    setWindowInsetsListener { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (paddingLeft) systemBars.left else 0
        val top = if (paddingTop) systemBars.top else 0
        val right = if (paddingRight) systemBars.right else 0
        val bottom = if (paddingBottom) systemBars.bottom else 0
        view.setPadding(left, top, right, bottom)
        insets
    }
}

// ======================
// = DevBaseVDBFragment =
// ======================

/**
 * 通用设置 View Padding, 使 view 不会被 system bars 遮挡
 */
fun DevBaseVDBFragment<*>.fragCommonSystemBarsPadding() {
    fragSetSystemBarsPadding()
}

/**
 * 给 View 设置 Padding, 使 view 不会被 system bars 遮挡
 */
fun DevBaseVDBFragment<*>.fragSetSystemBarsPadding(
    paddingLeft: Boolean = true,
    paddingTop: Boolean = true,
    paddingRight: Boolean = true,
    paddingBottom: Boolean = true
) {
    binding.root.setSystemBarsPadding(
        paddingLeft, paddingTop,
        paddingRight, paddingBottom
    )
}