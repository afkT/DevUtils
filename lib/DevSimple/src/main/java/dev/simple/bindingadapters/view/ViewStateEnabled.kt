package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.extensions.equality.stringEquals
import dev.simple.extensions.equality.stringEqualsIgnoreCase
import dev.simple.extensions.equality.valueEquals
import dev.utils.app.ViewUtils

// ================================
// = Enabled State BindingAdapter =
// ================================

/**
 * 通过数据绑定设置是否启用。
 * <pre>
 *     布局属性 binding_enabled；对应 ViewUtils.setEnabled。
 * </pre>
 */
@BindingAdapter("binding_enabled")
fun View.bindingEnabled(enabled: Boolean) {
    ViewUtils.setEnabled(enabled, this)
}

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制是否启用。
 * <pre>
 *     对应布局属性 binding_enabled_IfNotEmpty；null 或空串为禁用，否则启用；内部委托同文件 [bindingEnabled]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时启用
 */
@BindingAdapter("binding_enabled_IfNotEmpty")
fun View.bindingEnabledIfNotEmpty(value: String?) {
    bindingEnabled(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制是否启用。
 * <pre>
 *     对应布局属性 binding_enabled_IfStringEquals 与 binding_enabled_IfStringEquals_value2；
 *     相等为启用，否则禁用；内部委托 [stringEquals] 与同文件 [bindingEnabled]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_enabled_IfStringEquals",
    "binding_enabled_IfStringEquals_value2",
)
fun View.bindingEnabledIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingEnabled(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制是否启用。
 * <pre>
 *     对应布局属性 binding_enabled_IfStringEqualsIgnoreCase 与 binding_enabled_IfStringEqualsIgnoreCase_value2；
 *     相等为启用，否则禁用；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingEnabled]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_enabled_IfStringEqualsIgnoreCase",
    "binding_enabled_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingEnabledIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingEnabled(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制是否启用。
 * <pre>
 *     对应布局属性 binding_enabled_IfNotNull；null 为禁用，否则启用；内部委托同文件 [bindingEnabled]。
 * </pre>
 *
 * @param value 任意对象，非 null 时启用
 */
@BindingAdapter("binding_enabled_IfNotNull")
fun View.bindingEnabledIfNotNull(value: Any?) {
    bindingEnabled(value != null)
}

// =================
// = IfValueEquals =
// =================

/**
 * 根据两对象在工具类语义下是否相等控制是否启用。
 * <pre>
 *     对应布局属性 binding_enabled_IfValueEquals 与 binding_enabled_IfValueEquals_value2；
 *     相等为启用，否则禁用；内部委托 [valueEquals] 与同文件 [bindingEnabled]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_enabled_IfValueEquals",
    "binding_enabled_IfValueEquals_value2",
)
fun View.bindingEnabledIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingEnabled(value1.valueEquals(value2))
}