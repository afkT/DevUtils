package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.ViewAnimAlphaAt
import dev.simple.bindingadapters.attribute.ViewAnimShakeAt
import dev.simple.bindingadapters.attribute.ViewAnimShakePresetAt
import dev.simple.bindingadapters.attribute.ViewAnimTranslateAt
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.app.anim.ViewAnimationUtils

// ================================
// = ViewAnimation BindingAdapter =
// ================================

/**
 * [View] 透明度与平移类 View 动画的 Data Binding 适配集合。
 *
 * 布局属性前缀为 `binding_view_anim_*`；实现对应 [dev.utils.app.anim.ViewAnimationUtils]。
 * <pre>
 *     未封装带 [android.view.animation.Animation.AnimationListener] 的重载（监听器宜在代码中注册）。
 *     动画为一次性副作用，须通过正时间戳或含 timestamp 的 attribute 实体触发（判定同 [qualifiesBindingAction]）。
 *     与 [View] 中 `binding_view_animation` / `binding_view_start_animation` 等通用 Animation 控制属性并存、互不替代。
 * </pre>
 */

// ===============
// = 透明度渐变动画 =
// ===============

/**
 * 通过数据绑定以默认时长将视图渐隐为 INVISIBLE。
 * <pre>
 *     布局属性 `binding_view_anim_invisible_alpha_ts`；委托 [ViewAnimationUtils.invisibleViewByAlpha] 无参时长重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_anim_invisible_alpha_ts")
fun View.bindingViewAnimInvisibleAlphaTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    ViewAnimationUtils.invisibleViewByAlpha(this)
}

/**
 * 通过数据绑定将视图渐隐为 INVISIBLE（可指定时长与是否禁止点击）。
 * <pre>
 *     布局属性 `binding_view_anim_invisible_alpha`；payload 为 null 或时间戳无效时跳过。
 * </pre>
 *
 * @param payload 动画命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_invisible_alpha")
fun View.bindingViewAnimInvisibleAlpha(payload: ViewAnimAlphaAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    return ViewAnimationUtils.invisibleViewByAlpha(this, p.durationMillis, p.isBanClick)
}

/**
 * 通过数据绑定以默认时长将视图渐隐为 GONE。
 * <pre>
 *     布局属性 `binding_view_anim_gone_alpha_ts`；委托 [ViewAnimationUtils.goneViewByAlpha] 无参时长重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_anim_gone_alpha_ts")
fun View.bindingViewAnimGoneAlphaTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    ViewAnimationUtils.goneViewByAlpha(this)
}

/**
 * 通过数据绑定将视图渐隐为 GONE（可指定时长与是否禁止点击）。
 * <pre>
 *     布局属性 `binding_view_anim_gone_alpha`；语义同 `binding_view_anim_invisible_alpha`。
 * </pre>
 *
 * @param payload 动画命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_gone_alpha")
fun View.bindingViewAnimGoneAlpha(payload: ViewAnimAlphaAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    return ViewAnimationUtils.goneViewByAlpha(this, p.durationMillis, p.isBanClick)
}

/**
 * 通过数据绑定以默认时长将视图渐显为 VISIBLE。
 * <pre>
 *     布局属性 `binding_view_anim_visible_alpha_ts`；委托 [ViewAnimationUtils.visibleViewByAlpha] 无参时长重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_anim_visible_alpha_ts")
fun View.bindingViewAnimVisibleAlphaTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    ViewAnimationUtils.visibleViewByAlpha(this)
}

/**
 * 通过数据绑定将视图渐显为 VISIBLE（可指定时长与是否禁止点击）。
 * <pre>
 *     布局属性 `binding_view_anim_visible_alpha`；语义同 `binding_view_anim_invisible_alpha`。
 * </pre>
 *
 * @param payload 动画命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_visible_alpha")
fun View.bindingViewAnimVisibleAlpha(payload: ViewAnimAlphaAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    return ViewAnimationUtils.visibleViewByAlpha(this, p.durationMillis, p.isBanClick)
}

// ============
// = 平移与摇晃 =
// ============

/**
 * 通过数据绑定触发平移动画。
 * <pre>
 *     布局属性 `binding_view_anim_translate`；payload 为 null 或时间戳无效时跳过；
 *     `cycles` 非 null 时委托 cycles 重载，否则委托 [android.view.animation.Interpolator] 重载。
 * </pre>
 *
 * @param payload 平移命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_translate")
fun View.bindingViewAnimTranslate(payload: ViewAnimTranslateAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    return if (p.cycles != null) {
        ViewAnimationUtils.translate(
            this,
            p.fromXDelta,
            p.toXDelta,
            p.fromYDelta,
            p.toYDelta,
            p.cycles,
            p.durationMillis,
            p.isBanClick,
        )
    } else {
        ViewAnimationUtils.translate(
            this,
            p.fromXDelta,
            p.toXDelta,
            p.fromYDelta,
            p.toYDelta,
            p.interpolator,
            p.durationMillis,
            p.isBanClick,
        )
    }
}

/**
 * 通过数据绑定以工具类默认参数触发摇晃（幅度 10、周期 7、700ms）。
 * <pre>
 *     布局属性 `binding_view_anim_shake_ts`；委托 [ViewAnimationUtils.shake] 无参重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_shake_ts")
fun View.bindingViewAnimShakeTs(timestamp: Long?): Boolean {
    if (!timestamp.qualifiesBindingAction()) return false
    return ViewAnimationUtils.shake(this)
}

/**
 * 通过数据绑定触发自定义 X 向摇晃。
 * <pre>
 *     布局属性 `binding_view_anim_shake`；委托 [ViewAnimationUtils.shake] 含 from/to X 与 cycles 的重载。
 * </pre>
 *
 * @param payload 摇晃命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_shake")
fun View.bindingViewAnimShake(payload: ViewAnimShakeAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    return ViewAnimationUtils.shake(
        this,
        p.fromXDelta,
        p.toXDelta,
        p.cycles,
        p.durationMillis,
        p.isBanClick,
    )
}

/**
 * 通过数据绑定触发预设幅度（X 方向 ±10）的摇晃。
 * <pre>
 *     布局属性 `binding_view_anim_shake_preset`；委托 [ViewAnimationUtils.shake] 的 cycles + duration 重载。
 * </pre>
 *
 * @param payload 预设摇晃命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_shake_preset")
fun View.bindingViewAnimShakePreset(payload: ViewAnimShakePresetAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    return ViewAnimationUtils.shake(this, p.cycles, p.durationMillis, p.isBanClick)
}