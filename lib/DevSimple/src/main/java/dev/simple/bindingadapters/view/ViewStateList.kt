package dev.simple.bindingadapters.view

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.*
import dev.utils.LogPrintUtils
import dev.utils.app.StateListUtils
import dev.utils.app.TextViewUtils
import dev.utils.app.ViewUtils

// ================================
// = StateListUtils BindingAdapter =
// ================================

/**
 * [StateListUtils] 的 Data Binding 适配集合。
 *
 * 文字色前缀 `binding_tv_state_list_*`，背景切换前缀 `binding_view_state_list_*`。
 * <pre>
 *     推荐在 ViewModel 中用 [StateListUtils.createColorStateList] / [StateListUtils.newSelector]
 *     构建产物后，正文色可绑已有 `binding_tv_text_color_state`，背景可绑 `binding_view_background`。
 *     本文件封装工具类工厂方法，便于在布局中绑定 [ViewStateListColor2Str] 等实体。
 *     未封装自定义 state 数组、enabled/disabled 等扩展组合；须在 VM 侧构造 [ColorStateList] / [StateListDrawable] 后使用上述产物属性。
 *     同节点文字色：工厂属性与 `binding_tv_text_color_state` 宜二选一；背景同理与 `binding_view_background`。
 * </pre>
 */

private const val TAG = "Dev_ViewStateList_BindingAdapter"

// ===================
// = 产物直接写入（可选）=
// ===================

/**
 * 通过数据绑定将 [ColorStateList] 设为正文颜色。
 * <pre>
 *     布局属性 `binding_tv_state_list_text_color`；`null` 时跳过；委托 [TextViewUtils.setTextColor]。
 *     与 `binding_tv_text_color_state` 等价，便于与 `binding_tv_state_list_color_*` 工厂属性同文件使用。
 * </pre>
 *
 * @param colors 颜色状态列表
 */
@BindingAdapter("binding_tv_state_list_text_color")
fun TextView.bindingTVStateListTextColor(colors: ColorStateList?) {
    applyTextColorStateList(colors)
}

/**
 * 通过数据绑定将 [ColorStateList] 设为提示文字颜色。
 * <pre>
 *     布局属性 `binding_tv_state_list_hint_color`；`null` 时跳过；委托 [TextViewUtils.setHintTextColor]。
 *     与 `binding_tv_hint_color_state` 等价。
 * </pre>
 *
 * @param colors 颜色状态列表
 */
@BindingAdapter("binding_tv_state_list_hint_color")
fun TextView.bindingTVStateListHintColor(colors: ColorStateList?) {
    applyHintColorStateList(colors)
}

/**
 * 通过数据绑定将 [StateListDrawable] 或其它 [Drawable] 设为背景。
 * <pre>
 *     布局属性 `binding_view_state_list_background`；`null` 时跳过；委托 [ViewUtils.setBackground]。
 *     与 `binding_view_background` 等价，便于与 selector 工厂属性同文件使用。
 * </pre>
 *
 * @param selector 状态列表或普通背景 Drawable
 */
@BindingAdapter("binding_view_state_list_background")
fun View.bindingViewStateListBackground(selector: Drawable?) {
    applyStateListBackground(selector)
}

// ======================
// = 资源 ColorStateList =
// ======================

/**
 * 通过数据绑定从颜色资源加载 [ColorStateList] 并设为正文色。
 * <pre>
 *     布局属性 `binding_tv_state_list_color_res`；`null` 或 0 时跳过；委托 [StateListUtils.getColorStateList]。
 * </pre>
 *
 * @param resId 颜色状态列表资源 id
 */
@BindingAdapter("binding_tv_state_list_color_res")
fun TextView.bindingTVStateListColorRes(@ColorRes resId: Int?) {
    if (resId == null || resId == 0) return
    applyTextColorStateList(StateListUtils.getColorStateList(resId))
}

/**
 * 通过数据绑定从颜色资源加载 [ColorStateList] 并设为提示文字色。
 * <pre>
 *     布局属性 `binding_tv_state_list_hint_color_res`；`null` 或 0 时跳过。
 * </pre>
 *
 * @param resId 颜色状态列表资源 id
 */
@BindingAdapter("binding_tv_state_list_hint_color_res")
fun TextView.bindingTVStateListHintColorRes(@ColorRes resId: Int?) {
    if (resId == null || resId == 0) return
    applyHintColorStateList(StateListUtils.getColorStateList(resId))
}

// ===========================
// = 工厂：正文 ColorStateList =
// ===========================

/**
 * 通过数据绑定按两态 `#` 颜色创建正文 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_color_2_str`；`null` 或创建失败时跳过。
 * </pre>
 *
 * @param spec 按下与默认色值字符串
 */
@BindingAdapter("binding_tv_state_list_color_2_str")
fun TextView.bindingTVStateListColor2Str(spec: ViewStateListColor2Str?) {
    if (spec == null) return
    applyTextColorStateList(
        StateListUtils.createColorStateList(spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按三态 `#` 颜色创建正文 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_color_3_str`。
 * </pre>
 *
 * @param spec 选中、按下与默认色值字符串
 */
@BindingAdapter("binding_tv_state_list_color_3_str")
fun TextView.bindingTVStateListColor3Str(spec: ViewStateListColor3Str?) {
    if (spec == null) return
    applyTextColorStateList(
        StateListUtils.createColorStateList(spec.selected, spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按五态 `#` 颜色创建正文 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_color_5_str`。
 * </pre>
 *
 * @param spec 选中、按下、聚焦、勾选与默认色值字符串
 */
@BindingAdapter("binding_tv_state_list_color_5_str")
fun TextView.bindingTVStateListColor5Str(spec: ViewStateListColor5Str?) {
    if (spec == null) return
    applyTextColorStateList(
        StateListUtils.createColorStateList(
            spec.selected,
            spec.pressed,
            spec.focused,
            spec.checked,
            spec.normal,
        ),
    )
}

/**
 * 通过数据绑定按两态颜色资源创建正文 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_color_2_res`。
 * </pre>
 *
 * @param spec 按下与默认颜色资源 id
 */
@BindingAdapter("binding_tv_state_list_color_2_res")
fun TextView.bindingTVStateListColor2Res(spec: ViewStateListColor2Res?) {
    if (spec == null) return
    applyTextColorStateList(
        StateListUtils.createColorStateList(spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按三态颜色资源创建正文 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_color_3_res`。
 * </pre>
 *
 * @param spec 选中、按下与默认颜色资源 id
 */
@BindingAdapter("binding_tv_state_list_color_3_res")
fun TextView.bindingTVStateListColor3Res(spec: ViewStateListColor3Res?) {
    if (spec == null) return
    applyTextColorStateList(
        StateListUtils.createColorStateList(spec.selected, spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按五态颜色资源创建正文 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_color_5_res`。
 * </pre>
 *
 * @param spec 选中、按下、聚焦、勾选与默认颜色资源 id
 */
@BindingAdapter("binding_tv_state_list_color_5_res")
fun TextView.bindingTVStateListColor5Res(spec: ViewStateListColor5Res?) {
    if (spec == null) return
    applyTextColorStateList(
        StateListUtils.createColorStateList(
            spec.selected,
            spec.pressed,
            spec.focused,
            spec.checked,
            spec.normal,
        ),
    )
}

// ===========================
// = 工厂：提示 ColorStateList =
// ===========================

/**
 * 通过数据绑定按两态 `#` 颜色创建提示文字 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_hint_color_2_str`。
 * </pre>
 *
 * @param spec 按下与默认色值字符串
 */
@BindingAdapter("binding_tv_state_list_hint_color_2_str")
fun TextView.bindingTVStateListHintColor2Str(spec: ViewStateListColor2Str?) {
    if (spec == null) return
    applyHintColorStateList(
        StateListUtils.createColorStateList(spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按三态 `#` 颜色创建提示文字 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_hint_color_3_str`。
 * </pre>
 *
 * @param spec 选中、按下与默认色值字符串
 */
@BindingAdapter("binding_tv_state_list_hint_color_3_str")
fun TextView.bindingTVStateListHintColor3Str(spec: ViewStateListColor3Str?) {
    if (spec == null) return
    applyHintColorStateList(
        StateListUtils.createColorStateList(spec.selected, spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按五态 `#` 颜色创建提示文字 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_hint_color_5_str`。
 * </pre>
 *
 * @param spec 五态色值字符串
 */
@BindingAdapter("binding_tv_state_list_hint_color_5_str")
fun TextView.bindingTVStateListHintColor5Str(spec: ViewStateListColor5Str?) {
    if (spec == null) return
    applyHintColorStateList(
        StateListUtils.createColorStateList(
            spec.selected,
            spec.pressed,
            spec.focused,
            spec.checked,
            spec.normal,
        ),
    )
}

/**
 * 通过数据绑定按两态颜色资源创建提示文字 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_hint_color_2_res`。
 * </pre>
 *
 * @param spec 按下与默认颜色资源 id
 */
@BindingAdapter("binding_tv_state_list_hint_color_2_res")
fun TextView.bindingTVStateListHintColor2Res(spec: ViewStateListColor2Res?) {
    if (spec == null) return
    applyHintColorStateList(
        StateListUtils.createColorStateList(spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按三态颜色资源创建提示文字 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_hint_color_3_res`。
 * </pre>
 *
 * @param spec 选中、按下与默认颜色资源 id
 */
@BindingAdapter("binding_tv_state_list_hint_color_3_res")
fun TextView.bindingTVStateListHintColor3Res(spec: ViewStateListColor3Res?) {
    if (spec == null) return
    applyHintColorStateList(
        StateListUtils.createColorStateList(spec.selected, spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按五态颜色资源创建提示文字 [ColorStateList]。
 * <pre>
 *     布局属性 `binding_tv_state_list_hint_color_5_res`。
 * </pre>
 *
 * @param spec 五态颜色资源 id
 */
@BindingAdapter("binding_tv_state_list_hint_color_5_res")
fun TextView.bindingTVStateListHintColor5Res(spec: ViewStateListColor5Res?) {
    if (spec == null) return
    applyHintColorStateList(
        StateListUtils.createColorStateList(
            spec.selected,
            spec.pressed,
            spec.focused,
            spec.checked,
            spec.normal,
        ),
    )
}

// ==============================
// = 工厂：背景 StateListDrawable =
// ==============================

/**
 * 通过数据绑定按两态 drawable 资源创建背景 [StateListDrawable]。
 * <pre>
 *     布局属性 `binding_view_state_list_selector_2_res`；委托 [StateListUtils.newSelector] 与 [ViewUtils.setBackground]。
 * </pre>
 *
 * @param spec 按下与默认 drawable 资源 id
 */
@BindingAdapter("binding_view_state_list_selector_2_res")
fun View.bindingViewStateListSelector2Res(spec: ViewStateListSelector2Res?) {
    if (spec == null) return
    applyStateListBackground(
        StateListUtils.newSelector(spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按三态 drawable 资源创建背景。
 * <pre>
 *     布局属性 `binding_view_state_list_selector_3_res`。
 * </pre>
 *
 * @param spec 选中、按下与默认 drawable 资源 id
 */
@BindingAdapter("binding_view_state_list_selector_3_res")
fun View.bindingViewStateListSelector3Res(spec: ViewStateListSelector3Res?) {
    if (spec == null) return
    applyStateListBackground(
        StateListUtils.newSelector(spec.selected, spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按五态 drawable 资源创建背景。
 * <pre>
 *     布局属性 `binding_view_state_list_selector_5_res`。
 * </pre>
 *
 * @param spec 五态 drawable 资源 id
 */
@BindingAdapter("binding_view_state_list_selector_5_res")
fun View.bindingViewStateListSelector5Res(spec: ViewStateListSelector5Res?) {
    if (spec == null) return
    applyStateListBackground(
        StateListUtils.newSelector(
            spec.selected,
            spec.pressed,
            spec.focused,
            spec.checked,
            spec.normal,
        ),
    )
}

/**
 * 通过数据绑定按两态 [Drawable] 创建背景。
 * <pre>
 *     布局属性 `binding_view_state_list_selector_2_drawable`。
 * </pre>
 *
 * @param spec 按下与默认 Drawable
 */
@BindingAdapter("binding_view_state_list_selector_2_drawable")
fun View.bindingViewStateListSelector2Drawable(spec: ViewStateListSelector2Drawable?) {
    if (spec == null) return
    applyStateListBackground(
        StateListUtils.newSelector(spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按三态 [Drawable] 创建背景。
 * <pre>
 *     布局属性 `binding_view_state_list_selector_3_drawable`。
 * </pre>
 *
 * @param spec 选中、按下与默认 Drawable
 */
@BindingAdapter("binding_view_state_list_selector_3_drawable")
fun View.bindingViewStateListSelector3Drawable(spec: ViewStateListSelector3Drawable?) {
    if (spec == null) return
    applyStateListBackground(
        StateListUtils.newSelector(spec.selected, spec.pressed, spec.normal),
    )
}

/**
 * 通过数据绑定按五态 [Drawable] 创建背景。
 * <pre>
 *     布局属性 `binding_view_state_list_selector_5_drawable`。
 * </pre>
 *
 * @param spec 五态 Drawable
 */
@BindingAdapter("binding_view_state_list_selector_5_drawable")
fun View.bindingViewStateListSelector5Drawable(spec: ViewStateListSelector5Drawable?) {
    if (spec == null) return
    applyStateListBackground(
        StateListUtils.newSelector(
            spec.selected,
            spec.pressed,
            spec.focused,
            spec.checked,
            spec.normal,
        ),
    )
}

// ==========
// = 内部实现 =
// ==========

private fun TextView.applyTextColorStateList(colors: ColorStateList?) {
    if (colors == null) return
    try {
        TextViewUtils.setTextColor(this, colors)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "applyTextColorStateList")
    }
}

private fun TextView.applyHintColorStateList(colors: ColorStateList?) {
    if (colors == null) return
    try {
        TextViewUtils.setHintTextColor(this, colors)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "applyHintColorStateList")
    }
}

private fun View.applyStateListBackground(selector: Drawable?) {
    if (selector == null) return
    try {
        ViewUtils.setBackground(this, selector)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "applyStateListBackground")
    }
}