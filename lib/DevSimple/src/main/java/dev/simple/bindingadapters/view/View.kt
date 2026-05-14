package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.equalsIgnoreCase

// =======================
// = View BindingAdapter =
// =======================

// ===========
// = visible =
// ===========

/**
 * 通过数据绑定设置视图可见性数值
 * <pre>
 *     对应布局属性 binding_visibility；取值应为 View.VISIBLE、INVISIBLE 或 GONE。
 * </pre>
 *
 * @param visibility 可见性常量
 */
@BindingAdapter("binding_visibility")
fun View.bindingVisibility(visibility: Int) {
    this.visibility = visibility
}

/**
 * 通过数据绑定在显示与隐藏 gone 之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrGone；true 为 VISIBLE，false 为 GONE。
 * </pre>
 *
 * @param visible 是否显示
 */
@BindingAdapter("binding_visibleOrGone")
fun View.bindingVisibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * 通过数据绑定在显示与不可见占位之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrInVisible；true 为 VISIBLE，false 为 INVISIBLE。
 * </pre>
 *
 * @param visible 是否显示
 */
@BindingAdapter("binding_visibleOrInVisible")
fun View.bindingVisibleOrInVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

/**
 * 根据文本是否非空控制视图在显示与隐藏 gone 之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrGoneIfNotEmpty；null 或空串为 GONE，否则 VISIBLE。
 * </pre>
 *
 * @param value 文本，非 null 且非空时显示
 */
@BindingAdapter("binding_visibleOrGoneIfNotEmpty")
fun View.bindingVisibleOrGoneIfNotEmpty(value: String?) {
    bindingVisibleOrGone(!value.isNullOrEmpty())
}

/**
 * 根据文本是否非空控制视图在显示与不可见占位之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrInVisibleIfNotEmpty；null 或空串为 INVISIBLE，否则 VISIBLE。
 * </pre>
 *
 * @param value 文本，非 null 且非空时显示
 */
@BindingAdapter("binding_visibleOrInVisibleIfNotEmpty")
fun View.bindingVisibleOrInVisibleIfNotEmpty(value: String?) {
    bindingVisibleOrInVisible(!value.isNullOrEmpty())
}