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
// = Checked State BindingAdapter =
// ================================

/**
 * 通过数据绑定设置是否选中 checked。
 * <pre>
 *     布局属性 binding_checked；对应 ViewUtils.setChecked。
 * </pre>
 */
@BindingAdapter("binding_checked")
fun View.bindingChecked(checked: Boolean) {
    ViewUtils.setChecked(checked, this)
}

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制是否选中 checked。
 * <pre>
 *     对应布局属性 binding_checked_IfNotEmpty；null 或空串为未选中，否则选中；内部委托同文件 [bindingChecked]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时选中
 */
@BindingAdapter("binding_checked_IfNotEmpty")
fun View.bindingCheckedIfNotEmpty(value: String?) {
    bindingChecked(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制是否选中 checked。
 * <pre>
 *     对应布局属性 binding_checked_IfStringEquals 与 binding_checked_IfStringEquals_value2；
 *     相等为选中，否则未选中；内部委托 [stringEquals] 与同文件 [bindingChecked]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_checked_IfStringEquals",
    "binding_checked_IfStringEquals_value2",
)
fun View.bindingCheckedIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingChecked(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制是否选中 checked。
 * <pre>
 *     对应布局属性 binding_checked_IfStringEqualsIgnoreCase 与 binding_checked_IfStringEqualsIgnoreCase_value2；
 *     相等为选中，否则未选中；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingChecked]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_checked_IfStringEqualsIgnoreCase",
    "binding_checked_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingCheckedIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingChecked(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制是否选中 checked。
 * <pre>
 *     对应布局属性 binding_checked_IfNotNull；null 为未选中，否则选中；内部委托同文件 [bindingChecked]。
 * </pre>
 *
 * @param value 任意对象，非 null 时选中
 */
@BindingAdapter("binding_checked_IfNotNull")
fun View.bindingCheckedIfNotNull(value: Any?) {
    bindingChecked(value != null)
}

// =================
// = IfValueEquals =
// =================

/**
 * 根据两对象在工具类语义下是否相等控制是否选中 checked。
 * <pre>
 *     对应布局属性 binding_checked_IfValueEquals 与 binding_checked_IfValueEquals_value2；
 *     相等为选中，否则未选中；内部委托 [valueEquals] 与同文件 [bindingChecked]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_checked_IfValueEquals",
    "binding_checked_IfValueEquals_value2",
)
fun View.bindingCheckedIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingChecked(value1.valueEquals(value2))
}

// ================
// = CompareValue =
// ================

/**
 * 根据双方比对值接口实现是否在接口语义下相等控制是否选中 checked。
 * <pre>
 *     对应布局属性 binding_checked_IfCompareValueEquals 与 binding_checked_IfCompareValueEquals_value2；
 *     相等为选中，否则未选中；内部委托扩展 `compareValueEquals` 与同文件 [bindingChecked]；
 *     任一侧为 null 时扩展语义为不相等，故为未选中。
 * </pre>
 *
 * @param value1 参与比对的一侧实例，可为 null
 * @param value2 参与比对的另一侧实例，可为 null
 */
@BindingAdapter(
    "binding_checked_IfCompareValueEquals",
    "binding_checked_IfCompareValueEquals_value2",
)
fun View.bindingCheckedIfCompareValueEquals(
    value1: CompareValue?,
    value2: CompareValue?
) {
    bindingChecked(value1.compareValueEquals(value2))
}