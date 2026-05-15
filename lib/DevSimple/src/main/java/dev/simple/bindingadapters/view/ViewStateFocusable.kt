package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.extensions.equality.stringEquals
import dev.simple.extensions.equality.stringEqualsIgnoreCase
import dev.simple.extensions.equality.valueEquals
import dev.utils.app.ViewUtils

// =================================
// = Focusable State BindingAdapter =
// =================================

/**
 * 通过数据绑定设置是否可聚焦。
 * <pre>
 *     布局属性 binding_focusable；对应 ViewUtils.setFocusable。
 * </pre>
 */
@BindingAdapter("binding_focusable")
fun View.bindingFocusable(focusable: Boolean) {
    ViewUtils.setFocusable(focusable, this)
}

/**
 * 通过数据绑定设置触摸模式下是否可聚焦。
 * <pre>
 *     布局属性 binding_focusable_in_touch_mode；对应 ViewUtils.setFocusableInTouchMode。
 * </pre>
 */
@BindingAdapter("binding_focusable_in_touch_mode")
fun View.bindingFocusableInTouchMode(focusableInTouchMode: Boolean) {
    ViewUtils.setFocusableInTouchMode(focusableInTouchMode, this)
}

// =============
// = Focusable =
// =============

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_IfNotEmpty；null 或空串为不可聚焦，否则可聚焦；内部委托同文件 [bindingFocusable]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时可聚焦
 */
@BindingAdapter("binding_focusable_IfNotEmpty")
fun View.bindingFocusableIfNotEmpty(value: String?) {
    bindingFocusable(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_IfStringEquals 与 binding_focusable_IfStringEquals_value2；
 *     相等为可聚焦，否则不可聚焦；内部委托 [stringEquals] 与同文件 [bindingFocusable]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_focusable_IfStringEquals",
    "binding_focusable_IfStringEquals_value2",
)
fun View.bindingFocusableIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingFocusable(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_IfStringEqualsIgnoreCase 与 binding_focusable_IfStringEqualsIgnoreCase_value2；
 *     相等为可聚焦，否则不可聚焦；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingFocusable]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_focusable_IfStringEqualsIgnoreCase",
    "binding_focusable_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingFocusableIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingFocusable(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_IfNotNull；null 为不可聚焦，否则可聚焦；内部委托同文件 [bindingFocusable]。
 * </pre>
 *
 * @param value 任意对象，非 null 时可聚焦
 */
@BindingAdapter("binding_focusable_IfNotNull")
fun View.bindingFocusableIfNotNull(value: Any?) {
    bindingFocusable(value != null)
}

// =================
// = IfValueEquals =
// =================

/**
 * 根据两对象在工具类语义下是否相等控制是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_IfValueEquals 与 binding_focusable_IfValueEquals_value2；
 *     相等为可聚焦，否则不可聚焦；内部委托 [valueEquals] 与同文件 [bindingFocusable]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_focusable_IfValueEquals",
    "binding_focusable_IfValueEquals_value2",
)
fun View.bindingFocusableIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingFocusable(value1.valueEquals(value2))
}

// ========================
// = FocusableInTouchMode =
// ========================

/**
 * 根据文本是否非空控制触摸模式下是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_in_touch_mode_IfNotEmpty；null 或空串为触摸模式下不可聚焦，否则可聚焦；内部委托同文件 [bindingFocusableInTouchMode]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时触摸模式下可聚焦
 */
@BindingAdapter("binding_focusable_in_touch_mode_IfNotEmpty")
fun View.bindingFocusableInTouchModeIfNotEmpty(value: String?) {
    bindingFocusableInTouchMode(!value.isNullOrEmpty())
}

/**
 * 根据两字符串是否相等（区分大小写）控制触摸模式下是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_in_touch_mode_IfStringEquals 与 binding_focusable_in_touch_mode_IfStringEquals_value2；
 *     相等为触摸模式下可聚焦，否则不可聚焦；内部委托 [stringEquals] 与同文件 [bindingFocusableInTouchMode]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_focusable_in_touch_mode_IfStringEquals",
    "binding_focusable_in_touch_mode_IfStringEquals_value2",
)
fun View.bindingFocusableInTouchModeIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingFocusableInTouchMode(value1.stringEquals(value2))
}

/**
 * 根据两字符串是否相等（忽略大小写）控制触摸模式下是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_in_touch_mode_IfStringEqualsIgnoreCase 与 binding_focusable_in_touch_mode_IfStringEqualsIgnoreCase_value2；
 *     相等为触摸模式下可聚焦，否则不可聚焦；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingFocusableInTouchMode]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_focusable_in_touch_mode_IfStringEqualsIgnoreCase",
    "binding_focusable_in_touch_mode_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingFocusableInTouchModeIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingFocusableInTouchMode(value1.stringEqualsIgnoreCase(value2))
}

/**
 * 根据引用是否非 null 控制触摸模式下是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_in_touch_mode_IfNotNull；null 为触摸模式下不可聚焦，否则可聚焦；内部委托同文件 [bindingFocusableInTouchMode]。
 * </pre>
 *
 * @param value 任意对象，非 null 时触摸模式下可聚焦
 */
@BindingAdapter("binding_focusable_in_touch_mode_IfNotNull")
fun View.bindingFocusableInTouchModeIfNotNull(value: Any?) {
    bindingFocusableInTouchMode(value != null)
}

/**
 * 根据两对象在工具类语义下是否相等控制触摸模式下是否可聚焦。
 * <pre>
 *     对应布局属性 binding_focusable_in_touch_mode_IfValueEquals 与 binding_focusable_in_touch_mode_IfValueEquals_value2；
 *     相等为触摸模式下可聚焦，否则不可聚焦；内部委托 [valueEquals] 与同文件 [bindingFocusableInTouchMode]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_focusable_in_touch_mode_IfValueEquals",
    "binding_focusable_in_touch_mode_IfValueEquals_value2",
)
fun View.bindingFocusableInTouchModeIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingFocusableInTouchMode(value1.valueEquals(value2))
}