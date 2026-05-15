package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.extensions.equality.compareValueEquals
import dev.simple.extensions.equality.stringEquals
import dev.simple.extensions.equality.stringEqualsIgnoreCase
import dev.simple.extensions.equality.valueEquals
import dev.simple.interfaces.CompareValue
import dev.utils.app.ViewUtils

// ==================================
// = Clickable State BindingAdapter =
// ==================================

/**
 * 通过数据绑定设置是否可点击。
 * <pre>
 *     布局属性 binding_clickable；对应 ViewUtils.setClickable。
 * </pre>
 */
@BindingAdapter("binding_clickable")
fun View.bindingClickable(clickable: Boolean) {
    ViewUtils.setClickable(clickable, this)
}

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制是否可点击。
 * <pre>
 *     对应布局属性 binding_clickable_IfNotEmpty；null 或空串为不可点击，否则可点击；内部委托同文件 [bindingClickable]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时可点击
 */
@BindingAdapter("binding_clickable_IfNotEmpty")
fun View.bindingClickableIfNotEmpty(value: String?) {
    bindingClickable(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制是否可点击。
 * <pre>
 *     对应布局属性 binding_clickable_IfStringEquals 与 binding_clickable_IfStringEquals_value2；
 *     相等为可点击，否则不可点击；内部委托 [stringEquals] 与同文件 [bindingClickable]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_clickable_IfStringEquals",
    "binding_clickable_IfStringEquals_value2",
)
fun View.bindingClickableIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingClickable(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制是否可点击。
 * <pre>
 *     对应布局属性 binding_clickable_IfStringEqualsIgnoreCase 与 binding_clickable_IfStringEqualsIgnoreCase_value2；
 *     相等为可点击，否则不可点击；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingClickable]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_clickable_IfStringEqualsIgnoreCase",
    "binding_clickable_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingClickableIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingClickable(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制是否可点击。
 * <pre>
 *     对应布局属性 binding_clickable_IfNotNull；null 为不可点击，否则可点击；内部委托同文件 [bindingClickable]。
 * </pre>
 *
 * @param value 任意对象，非 null 时可点击
 */
@BindingAdapter("binding_clickable_IfNotNull")
fun View.bindingClickableIfNotNull(value: Any?) {
    bindingClickable(value != null)
}

// =================
// = IfValueEquals =
// =================

/**
 * 根据两对象在工具类语义下是否相等控制是否可点击。
 * <pre>
 *     对应布局属性 binding_clickable_IfValueEquals 与 binding_clickable_IfValueEquals_value2；
 *     相等为可点击，否则不可点击；内部委托 [valueEquals] 与同文件 [bindingClickable]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_clickable_IfValueEquals",
    "binding_clickable_IfValueEquals_value2",
)
fun View.bindingClickableIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingClickable(value1.valueEquals(value2))
}

// ================
// = CompareValue =
// ================

/**
 * 根据双方比对值接口实现是否在接口语义下相等控制是否可点击。
 * <pre>
 *     对应布局属性 binding_clickable_IfCompareValueEquals 与 binding_clickable_IfCompareValueEquals_value2；
 *     相等为可点击，否则不可点击；内部委托扩展 `compareValueEquals` 与同文件 [bindingClickable]；
 *     任一侧为 null 时扩展语义为不相等，故为不可点击。
 * </pre>
 *
 * @param value1 参与比对的一侧实例，可为 null
 * @param value2 参与比对的另一侧实例，可为 null
 */
@BindingAdapter(
    "binding_clickable_IfCompareValueEquals",
    "binding_clickable_IfCompareValueEquals_value2",
)
fun View.bindingClickableIfCompareValueEquals(
    value1: CompareValue?,
    value2: CompareValue?
) {
    bindingClickable(value1.compareValueEquals(value2))
}