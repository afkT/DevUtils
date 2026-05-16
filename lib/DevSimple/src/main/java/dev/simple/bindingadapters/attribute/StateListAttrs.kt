package dev.simple.bindingadapters.attribute

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * 按下与默认两态文字色（`#` 字符串），对应 [dev.utils.app.StateListUtils.createColorStateList] 双参 String 重载。
 * <pre>
 *     与布局属性 `binding_tv_state_list_color_2_str` 搭配。
 * </pre>
 */
data class ViewStateListColor2Str(
    val pressed: String,
    val normal: String,
)

/**
 * 选中、按下与默认三态文字色（`#` 字符串）。
 * <pre>
 *     与布局属性 `binding_tv_state_list_color_3_str` 搭配。
 * </pre>
 */
data class ViewStateListColor3Str(
    val selected: String,
    val pressed: String,
    val normal: String,
)

/**
 * 选中、按下、聚焦、勾选与默认五态文字色（`#` 字符串）。
 * <pre>
 *     与布局属性 `binding_tv_state_list_color_5_str` 搭配。
 * </pre>
 */
data class ViewStateListColor5Str(
    val selected: String,
    val pressed: String,
    val focused: String,
    val checked: String,
    val normal: String,
)

/**
 * 按下与默认两态文字色（颜色资源 id）。
 * <pre>
 *     与布局属性 `binding_tv_state_list_color_2_res` 搭配。
 * </pre>
 */
data class ViewStateListColor2Res(
    @ColorRes val pressed: Int,
    @ColorRes val normal: Int,
)

/**
 * 选中、按下与默认三态文字色（颜色资源 id）。
 * <pre>
 *     与布局属性 `binding_tv_state_list_color_3_res` 搭配。
 * </pre>
 */
data class ViewStateListColor3Res(
    @ColorRes val selected: Int,
    @ColorRes val pressed: Int,
    @ColorRes val normal: Int,
)

/**
 * 选中、按下、聚焦、勾选与默认五态文字色（颜色资源 id）。
 * <pre>
 *     与布局属性 `binding_tv_state_list_color_5_res` 搭配。
 * </pre>
 */
data class ViewStateListColor5Res(
    @ColorRes val selected: Int,
    @ColorRes val pressed: Int,
    @ColorRes val focused: Int,
    @ColorRes val checked: Int,
    @ColorRes val normal: Int,
)

/**
 * 按下与默认两态背景（drawable 资源 id），对应 [dev.utils.app.StateListUtils.newSelector] 双参资源重载。
 * <pre>
 *     与布局属性 `binding_view_state_list_selector_2_res` 搭配。
 * </pre>
 */
data class ViewStateListSelector2Res(
    @DrawableRes val pressed: Int,
    @DrawableRes val normal: Int,
)

/**
 * 选中、按下与默认三态背景（drawable 资源 id）。
 * <pre>
 *     与布局属性 `binding_view_state_list_selector_3_res` 搭配。
 * </pre>
 */
data class ViewStateListSelector3Res(
    @DrawableRes val selected: Int,
    @DrawableRes val pressed: Int,
    @DrawableRes val normal: Int,
)

/**
 * 选中、按下、聚焦、勾选与默认五态背景（drawable 资源 id）。
 * <pre>
 *     与布局属性 `binding_view_state_list_selector_5_res` 搭配。
 * </pre>
 */
data class ViewStateListSelector5Res(
    @DrawableRes val selected: Int,
    @DrawableRes val pressed: Int,
    @DrawableRes val focused: Int,
    @DrawableRes val checked: Int,
    @DrawableRes val normal: Int,
)

/**
 * 按下与默认两态背景（[Drawable] 实例）。
 * <pre>
 *     与布局属性 `binding_view_state_list_selector_2_drawable` 搭配。
 * </pre>
 */
data class ViewStateListSelector2Drawable(
    val pressed: Drawable,
    val normal: Drawable,
)

/**
 * 选中、按下与默认三态背景（[Drawable] 实例）。
 * <pre>
 *     与布局属性 `binding_view_state_list_selector_3_drawable` 搭配。
 * </pre>
 */
data class ViewStateListSelector3Drawable(
    val selected: Drawable,
    val pressed: Drawable,
    val normal: Drawable,
)

/**
 * 选中、按下、聚焦、勾选与默认五态背景（[Drawable] 实例）。
 * <pre>
 *     与布局属性 `binding_view_state_list_selector_5_drawable` 搭配。
 * </pre>
 */
data class ViewStateListSelector5Drawable(
    val selected: Drawable,
    val pressed: Drawable,
    val focused: Drawable,
    val checked: Drawable,
    val normal: Drawable,
)