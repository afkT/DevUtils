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
// = Activated State BindingAdapter =
// ==================================

/**
 * 通过数据绑定设置 Activated 激活状态。
 * <pre>
 *     布局属性 binding_activated；对应 ViewUtils.setActivated。
 * </pre>
 */
@BindingAdapter("binding_activated")
fun View.bindingActivated(activated: Boolean) {
    ViewUtils.setActivated(activated, this)
}

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制 Activated 状态。
 * <pre>
 *     对应布局属性 binding_activated_IfNotEmpty；null 或空串为非激活，否则激活；内部委托同文件 [bindingActivated]。
 * </pre>
 *
 * @param value 文本，非 null 且非空时激活
 */
@BindingAdapter("binding_activated_IfNotEmpty")
fun View.bindingActivatedIfNotEmpty(value: String?) {
    bindingActivated(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制 Activated 状态。
 * <pre>
 *     对应布局属性 binding_activated_IfStringEquals 与 binding_activated_IfStringEquals_value2；
 *     相等为激活，否则非激活；内部委托 [stringEquals] 与同文件 [bindingActivated]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_activated_IfStringEquals",
    "binding_activated_IfStringEquals_value2",
)
fun View.bindingActivatedIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingActivated(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制 Activated 状态。
 * <pre>
 *     对应布局属性 binding_activated_IfStringEqualsIgnoreCase 与 binding_activated_IfStringEqualsIgnoreCase_value2；
 *     相等为激活，否则非激活；内部委托 [stringEqualsIgnoreCase] 与同文件 [bindingActivated]。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_activated_IfStringEqualsIgnoreCase",
    "binding_activated_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingActivatedIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingActivated(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制 Activated 状态。
 * <pre>
 *     对应布局属性 binding_activated_IfNotNull；null 为非激活，否则激活；内部委托同文件 [bindingActivated]。
 * </pre>
 *
 * @param value 任意对象，非 null 时激活
 */
@BindingAdapter("binding_activated_IfNotNull")
fun View.bindingActivatedIfNotNull(value: Any?) {
    bindingActivated(value != null)
}

// =================
// = IfValueEquals =
// =================

/**
 * 根据两对象在工具类语义下是否相等控制 Activated 状态。
 * <pre>
 *     对应布局属性 binding_activated_IfValueEquals 与 binding_activated_IfValueEquals_value2；
 *     相等为激活，否则非激活；内部委托 [valueEquals] 与同文件 [bindingActivated]。
 * </pre>
 *
 * @param value1 参与比较的对象，可为 null
 * @param value2 参与比较的对象，可为 null
 */
@BindingAdapter(
    "binding_activated_IfValueEquals",
    "binding_activated_IfValueEquals_value2",
)
fun View.bindingActivatedIfValueEquals(
    value1: Any?,
    value2: Any?
) {
    bindingActivated(value1.valueEquals(value2))
}

// ================
// = CompareValue =
// ================

/**
 * 根据双方比对值接口实现是否在接口语义下相等控制 Activated 状态。
 * <pre>
 *     对应布局属性 binding_activated_IfCompareValueEquals 与 binding_activated_IfCompareValueEquals_value2；
 *     相等为激活，否则非激活；内部委托扩展 `compareValueEquals` 与同文件 [bindingActivated]；
 *     任一侧为 null 时扩展语义为不相等，故为非激活。
 * </pre>
 *
 * @param value1 参与比对的一侧实例，可为 null
 * @param value2 参与比对的另一侧实例，可为 null
 */
@BindingAdapter(
    "binding_activated_IfCompareValueEquals",
    "binding_activated_IfCompareValueEquals_value2",
)
fun View.bindingActivatedIfCompareValueEquals(
    value1: CompareValue?,
    value2: CompareValue?
) {
    bindingActivated(value1.compareValueEquals(value2))
}