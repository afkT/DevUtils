package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.extensions.equality.stringEquals
import dev.simple.extensions.equality.stringEqualsIgnoreCase

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

// ==============
// = IfNotEmpty =
// ==============

/**
 * 根据文本是否非空控制视图在显示与隐藏 gone 之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrGone_IfNotEmpty；null 或空串为 GONE，否则 VISIBLE。
 * </pre>
 *
 * @param value 文本，非 null 且非空时显示
 */
@BindingAdapter("binding_visibleOrGone_IfNotEmpty")
fun View.bindingVisibleOrGoneIfNotEmpty(value: String?) {
    bindingVisibleOrGone(!value.isNullOrEmpty())
}

/**
 * 根据文本是否非空控制视图在显示与不可见占位之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrInVisible_IfNotEmpty；null 或空串为 INVISIBLE，否则 VISIBLE。
 * </pre>
 *
 * @param value 文本，非 null 且非空时显示
 */
@BindingAdapter("binding_visibleOrInVisible_IfNotEmpty")
fun View.bindingVisibleOrInVisibleIfNotEmpty(value: String?) {
    bindingVisibleOrInVisible(!value.isNullOrEmpty())
}

// ======================
// = IfStringEquals (CS) =
// ======================

/**
 * 根据两字符串是否相等（区分大小写）控制视图在显示与隐藏 gone 之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrGone_IfStringEquals 与 binding_visibleOrGone_IfStringEquals_value2；
 *     相等为 VISIBLE，否则 GONE；内部委托 [stringEquals] 与同文件 `binding_visibleOrGone`。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_visibleOrGone_IfStringEquals",
    "binding_visibleOrGone_IfStringEquals_value2",
)
fun View.bindingVisibleOrGoneIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingVisibleOrGone(value1.stringEquals(value2))
}

/**
 * 根据两字符串是否相等（区分大小写）控制视图在显示与不可见占位之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrInVisible_IfStringEquals 与 binding_visibleOrInVisible_IfStringEquals_value2；
 *     相等为 VISIBLE，否则 INVISIBLE；内部委托 [stringEquals] 与同文件 `binding_visibleOrInVisible`。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_visibleOrInVisible_IfStringEquals",
    "binding_visibleOrInVisible_IfStringEquals_value2",
)
fun View.bindingVisibleOrInVisibleIfStringEquals(
    value1: String?,
    value2: String?
) {
    bindingVisibleOrInVisible(value1.stringEquals(value2))
}

// ==============================
// = IfStringEquals (IgnoreCase) =
// ==============================

/**
 * 根据两字符串是否相等（忽略大小写）控制视图在显示与隐藏 gone 之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrGone_IfStringEqualsIgnoreCase 与 binding_visibleOrGone_IfStringEqualsIgnoreCase_value2；
 *     相等为 VISIBLE，否则 GONE；内部委托 [stringEqualsIgnoreCase] 与同文件 `binding_visibleOrGone`。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_visibleOrGone_IfStringEqualsIgnoreCase",
    "binding_visibleOrGone_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingVisibleOrGoneIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingVisibleOrGone(value1.stringEqualsIgnoreCase(value2))
}

/**
 * 根据两字符串是否相等（忽略大小写）控制视图在显示与不可见占位之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrInVisible_IfStringEqualsIgnoreCase 与 binding_visibleOrInVisible_IfStringEqualsIgnoreCase_value2；
 *     相等为 VISIBLE，否则 INVISIBLE；内部委托 [stringEqualsIgnoreCase] 与同文件 `binding_visibleOrInVisible`。
 * </pre>
 *
 * @param value1 参与比较的字符串，可为 null
 * @param value2 参与比较的字符串，可为 null
 */
@BindingAdapter(
    "binding_visibleOrInVisible_IfStringEqualsIgnoreCase",
    "binding_visibleOrInVisible_IfStringEqualsIgnoreCase_value2",
)
fun View.bindingVisibleOrInVisibleIfStringEqualsIgnoreCase(
    value1: String?,
    value2: String?
) {
    bindingVisibleOrInVisible(value1.stringEqualsIgnoreCase(value2))
}

// =============
// = IfNotNull =
// =============

/**
 * 根据引用是否非 null 控制视图在显示与隐藏 gone 之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrGone_IfNotNull；null 为 GONE，否则 VISIBLE；内部委托同文件 `binding_visibleOrGone`。
 * </pre>
 *
 * @param value 任意对象，非 null 时显示
 */
@BindingAdapter("binding_visibleOrGone_IfNotNull")
fun View.bindingVisibleOrGoneIfNotNull(value: Any?) {
    bindingVisibleOrGone(value != null)
}

/**
 * 根据引用是否非 null 控制视图在显示与不可见占位之间切换
 * <pre>
 *     对应布局属性 binding_visibleOrInVisible_IfNotNull；null 为 INVISIBLE，否则 VISIBLE；内部委托同文件 `binding_visibleOrInVisible`。
 * </pre>
 *
 * @param value 任意对象，非 null 时显示
 */
@BindingAdapter("binding_visibleOrInVisible_IfNotNull")
fun View.bindingVisibleOrInVisibleIfNotNull(value: Any?) {
    bindingVisibleOrInVisible(value != null)
}