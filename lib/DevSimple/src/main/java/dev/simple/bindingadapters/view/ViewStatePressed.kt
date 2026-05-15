package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.extensions.equality.compareValueEquals
import dev.simple.extensions.equality.stringEquals
import dev.simple.extensions.equality.stringEqualsIgnoreCase
import dev.simple.extensions.equality.valueEquals
import dev.simple.interfaces.CompareValue
import dev.utils.app.ViewUtils

// ================================
// = Pressed State BindingAdapter =
// ================================

/**
 * 通过数据绑定设置 Pressed 按下状态（代码层模拟；触摸时系统亦会刷新）。
 * <pre>
 *     布局属性 binding_pressed；对应 ViewUtils.setPressed。
 * </pre>
 */
@BindingAdapter("binding_pressed")
fun View.bindingPressed(pressed: Boolean) {
    ViewUtils.setPressed(pressed, this)
}

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制 Pressed 状态。
 * <pre>
 *     对应布局属性 binding_pressed_IfNotEmpty；null 或空串为未按下，否则按下；内部委托同文件 [bindingPressed]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时按下
 */
@BindingAdapter("binding_pressed_IfNotEmpty")
fun View.bindingPressedIfNotEmpty(value: String?) {
    bindingPressed(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制 Pressed 状态。
 * <pre>
 *     对应布局属性 binding_pressed_IfStringEquals 与 binding_pressed_IfStringEquals_value2；
 *     相等为按下，否则未按下；内部委托 [stringEquals] 与同文件 [bindingPressed]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_pressed_IfStringEquals",
    "binding_pressed_IfStringEquals_value2",
)
fun View.bindingPressedIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingPressed(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制 Pressed 状态。
 * <pre>
 *     对应布局属性 binding_pressed_IfStringEqualsIgnoreCase 与 binding_pressed_IfStringEqualsIgnoreCase_value2；
 *     相等为按下，否则未按下；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingPressed]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_pressed_IfStringEqualsIgnoreCase",
    "binding_pressed_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingPressedIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingPressed(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制 Pressed 状态。
 * <pre>
 *     对应布局属性 binding_pressed_IfNotNull；null 为未按下，否则按下；内部委托同文件 [bindingPressed]。
 * </pre>
 *
 * @param value 任意对象，非 null 时按下
 */
@BindingAdapter("binding_pressed_IfNotNull")
fun View.bindingPressedIfNotNull(value: Any?) {
    bindingPressed(value != null)
}

// =================
// = IfValueEquals =
// =================

/**
 * 根据两对象在工具类语义下是否相等控制 Pressed 状态。
 * <pre>
 *     对应布局属性 binding_pressed_IfValueEquals 与 binding_pressed_IfValueEquals_value2；
 *     相等为按下，否则未按下；内部委托 [valueEquals] 与同文件 [bindingPressed]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_pressed_IfValueEquals",
    "binding_pressed_IfValueEquals_value2",
)
fun View.bindingPressedIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingPressed(value1.valueEquals(value2))
}

// ================
// = CompareValue =
// ================

/**
 * 根据双方比对值接口实现是否在接口语义下相等控制 Pressed 按下状态。
 * <pre>
 *     对应布局属性 binding_pressed_IfCompareValueEquals 与 binding_pressed_IfCompareValueEquals_value2；
 *     相等为按下，否则未按下；内部委托扩展 `compareValueEquals` 与同文件 [bindingPressed]；
 *     任一侧为 null 时扩展语义为不相等，故为未按下。
 * </pre>
 *
 * @param value1 参与比对的一侧实例，可为 null
 * @param value2 参与比对的另一侧实例，可为 null
 */
@BindingAdapter(
    "binding_pressed_IfCompareValueEquals",
    "binding_pressed_IfCompareValueEquals_value2",
)
fun View.bindingPressedIfCompareValueEquals(
    value1: CompareValue?,
    value2: CompareValue?
) {
    bindingPressed(value1.compareValueEquals(value2))
}