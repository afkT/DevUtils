package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.extensions.equality.compareValueEquals
import dev.simple.extensions.equality.stringEquals
import dev.simple.extensions.equality.stringEqualsIgnoreCase
import dev.simple.extensions.equality.valueEquals
import dev.simple.interfaces.CompareValue
import dev.utils.app.ViewUtils

// ======================================
// = LongClickable State BindingAdapter =
// ======================================

/**
 * 通过数据绑定设置是否可长按。
 * <pre>
 *     布局属性 binding_longClickable；对应 ViewUtils.setLongClickable。
 * </pre>
 */
@BindingAdapter("binding_longClickable")
fun View.bindingLongClickable(longClickable: Boolean) {
    ViewUtils.setLongClickable(longClickable, this)
}

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制是否可长按。
 * <pre>
 *     对应布局属性 binding_longClickable_IfNotEmpty；null 或空串为不可长按，否则可长按；内部委托同文件 [bindingLongClickable]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时可长按
 */
@BindingAdapter("binding_longClickable_IfNotEmpty")
fun View.bindingLongClickableIfNotEmpty(value: String?) {
    bindingLongClickable(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制是否可长按。
 * <pre>
 *     对应布局属性 binding_longClickable_IfStringEquals 与 binding_longClickable_IfStringEquals_value2；
 *     相等为可长按，否则不可长按；内部委托 [stringEquals] 与同文件 [bindingLongClickable]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_longClickable_IfStringEquals",
    "binding_longClickable_IfStringEquals_value2",
)
fun View.bindingLongClickableIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingLongClickable(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制是否可长按。
 * <pre>
 *     对应布局属性 binding_longClickable_IfStringEqualsIgnoreCase 与 binding_longClickable_IfStringEqualsIgnoreCase_value2；
 *     相等为可长按，否则不可长按；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingLongClickable]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_longClickable_IfStringEqualsIgnoreCase",
    "binding_longClickable_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingLongClickableIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingLongClickable(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制是否可长按。
 * <pre>
 *     对应布局属性 binding_longClickable_IfNotNull；null 为不可长按，否则可长按；内部委托同文件 [bindingLongClickable]。
 * </pre>
 *
 * @param value 任意对象，非 null 时可长按
 */
@BindingAdapter("binding_longClickable_IfNotNull")
fun View.bindingLongClickableIfNotNull(value: Any?) {
    bindingLongClickable(value != null)
}

// =================
// = IfValueEquals =
// =================

/**
 * 根据两对象在工具类语义下是否相等控制是否可长按。
 * <pre>
 *     对应布局属性 binding_longClickable_IfValueEquals 与 binding_longClickable_IfValueEquals_value2；
 *     相等为可长按，否则不可长按；内部委托 [valueEquals] 与同文件 [bindingLongClickable]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_longClickable_IfValueEquals",
    "binding_longClickable_IfValueEquals_value2",
)
fun View.bindingLongClickableIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingLongClickable(value1.valueEquals(value2))
}

// ================
// = CompareValue =
// ================

/**
 * 根据双方比对值接口实现是否在接口语义下相等控制是否可长按。
 * <pre>
 *     对应布局属性 binding_longClickable_IfCompareValueEquals 与 binding_longClickable_IfCompareValueEquals_value2；
 *     相等为可长按，否则不可长按；内部委托扩展 `compareValueEquals` 与同文件 [bindingLongClickable]；
 *     任一侧为 null 时扩展语义为不相等，故为不可长按。
 * </pre>
 *
 * @param value1 参与比对的一侧实例，可为 null
 * @param value2 参与比对的另一侧实例，可为 null
 */
@BindingAdapter(
    "binding_longClickable_IfCompareValueEquals",
    "binding_longClickable_IfCompareValueEquals_value2",
)
fun View.bindingLongClickableIfCompareValueEquals(
    value1: CompareValue?,
    value2: CompareValue?
) {
    bindingLongClickable(value1.compareValueEquals(value2))
}