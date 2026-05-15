package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.extensions.equality.stringEquals
import dev.simple.extensions.equality.stringEqualsIgnoreCase
import dev.simple.extensions.equality.valueEquals
import dev.utils.app.ViewUtils

// =================================
// = Selected State BindingAdapter =
// =================================

/**
 * 通过数据绑定设置选中状态。
 * <pre>
 *     布局属性 binding_selected；对应 ViewUtils.setSelected。
 * </pre>
 */
@BindingAdapter("binding_selected")
fun View.bindingSelected(selected: Boolean) {
    ViewUtils.setSelected(selected, this)
}

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制选中状态。
 * <pre>
 *     对应布局属性 binding_selected_IfNotEmpty；null 或空串为未选中，否则选中；内部委托同文件 [bindingSelected]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时选中
 */
@BindingAdapter("binding_selected_IfNotEmpty")
fun View.bindingSelectedIfNotEmpty(value: String?) {
    bindingSelected(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制选中状态。
 * <pre>
 *     对应布局属性 binding_selected_IfStringEquals 与 binding_selected_IfStringEquals_value2；
 *     相等为选中，否则未选中；内部委托 [stringEquals] 与同文件 [bindingSelected]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_selected_IfStringEquals",
    "binding_selected_IfStringEquals_value2",
)
fun View.bindingSelectedIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingSelected(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制选中状态。
 * <pre>
 *     对应布局属性 binding_selected_IfStringEqualsIgnoreCase 与 binding_selected_IfStringEqualsIgnoreCase_value2；
 *     相等为选中，否则未选中；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingSelected]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_selected_IfStringEqualsIgnoreCase",
    "binding_selected_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingSelectedIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingSelected(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制选中状态。
 * <pre>
 *     对应布局属性 binding_selected_IfNotNull；null 为未选中，否则选中；内部委托同文件 [bindingSelected]。
 * </pre>
 *
 * @param value 任意对象，非 null 时选中
 */
@BindingAdapter("binding_selected_IfNotNull")
fun View.bindingSelectedIfNotNull(value: Any?) {
    bindingSelected(value != null)
}

// =================
// = IfValueEquals =
// =================

/**
 * 根据两对象在工具类语义下是否相等控制选中状态。
 * <pre>
 *     对应布局属性 binding_selected_IfValueEquals 与 binding_selected_IfValueEquals_value2；
 *     相等为选中，否则未选中；内部委托 [valueEquals] 与同文件 [bindingSelected]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_selected_IfValueEquals",
    "binding_selected_IfValueEquals_value2",
)
fun View.bindingSelectedIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingSelected(value1.valueEquals(value2))
}