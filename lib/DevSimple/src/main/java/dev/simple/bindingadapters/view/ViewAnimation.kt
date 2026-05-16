package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.*
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.app.anim.AnimationUtils
import dev.utils.app.anim.ViewAnimationUtils

// ================================
// = ViewAnimation BindingAdapter =
// ================================

/**
 * [View] 透明度、平移、旋转与缩放类动画的 Data Binding 适配集合。
 *
 * 布局属性前缀为 `binding_view_anim_*`：显隐/平移/摇晃委托 [dev.utils.app.anim.ViewAnimationUtils]；
 * 旋转/缩放/纯 Alpha 渐变工厂委托 [dev.utils.app.anim.AnimationUtils] 并 [AnimationUtils.startAnimation]。
 * <pre>
 *     未封装带 [android.view.animation.Animation.AnimationListener] 的重载（监听器宜在代码中注册）。
 *     动画为一次性副作用，须通过正时间戳或含 timestamp 的 attribute 实体触发（判定同 [qualifiesBindingAction]）。
 *     与 [View] 中 `binding_view_animation` / `binding_view_start_animation` 等通用 Animation 控制属性并存、互不替代。
 *     `binding_view_anim_*_alpha`（ViewAnimationUtils）会同步 visibility；`binding_view_anim_hidden_alpha` /
 *     `binding_view_anim_show_alpha` / `binding_view_anim_alpha_fade` 仅播放 Alpha 动画，不改 visibility。
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

// ================================
// = AnimationUtils 工厂 + 启动 =
// ================================

// ==========
// = 旋转动画 =
// ==========

/**
 * 通过数据绑定以默认时长绕视图中心旋转一周（0°→359°）。
 * <pre>
 *     布局属性 `binding_view_anim_rotate_center_ts`；委托 [AnimationUtils.getRotateAnimationByCenter] 无参时长重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_anim_rotate_center_ts")
fun View.bindingViewAnimRotateCenterTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(this, AnimationUtils.getRotateAnimationByCenter())
}

/**
 * 通过数据绑定绕视图中心旋转（可指定时长）。
 * <pre>
 *     布局属性 `binding_view_anim_rotate_center`；payload 为 null 或时间戳无效时跳过。
 * </pre>
 *
 * @param payload 动画命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_rotate_center")
fun View.bindingViewAnimRotateCenter(payload: ViewAnimDurationAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getRotateAnimationByCenter(p.durationMillis),
    )
}

/**
 * 通过数据绑定按自定义角度与 pivot 旋转。
 * <pre>
 *     布局属性 `binding_view_anim_rotate`；委托 [AnimationUtils.getRotateAnimation] 含 pivot 重载。
 * </pre>
 *
 * @param payload 旋转命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_rotate")
fun View.bindingViewAnimRotate(payload: ViewAnimRotateAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getRotateAnimation(
            p.fromDegrees,
            p.toDegrees,
            p.pivotX,
            p.pivotY,
            p.durationMillis,
            null,
        ),
    )
}

// ==================
// = 纯 Alpha 渐变 =
// ==================

/**
 * 通过数据绑定播放 1.0→0.0 透明度渐变（不改 visibility）。
 * <pre>
 *     布局属性 `binding_view_anim_hidden_alpha_ts`；委托 [AnimationUtils.getHiddenAlphaAnimation] 无参时长重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_anim_hidden_alpha_ts")
fun View.bindingViewAnimHiddenAlphaTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(this, AnimationUtils.getHiddenAlphaAnimation())
}

/**
 * 通过数据绑定播放 1.0→0.0 透明度渐变（可指定时长，不改 visibility）。
 * <pre>
 *     布局属性 `binding_view_anim_hidden_alpha`；语义区别于 `binding_view_anim_invisible_alpha` / `gone_alpha`。
 * </pre>
 *
 * @param payload 动画命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_hidden_alpha")
fun View.bindingViewAnimHiddenAlpha(payload: ViewAnimDurationAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getHiddenAlphaAnimation(p.durationMillis),
    )
}

/**
 * 通过数据绑定播放 0.0→1.0 透明度渐变（不改 visibility）。
 * <pre>
 *     布局属性 `binding_view_anim_show_alpha_ts`；委托 [AnimationUtils.getShowAlphaAnimation] 无参时长重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_anim_show_alpha_ts")
fun View.bindingViewAnimShowAlphaTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(this, AnimationUtils.getShowAlphaAnimation())
}

/**
 * 通过数据绑定播放 0.0→1.0 透明度渐变（可指定时长，不改 visibility）。
 * <pre>
 *     布局属性 `binding_view_anim_show_alpha`；语义区别于 `binding_view_anim_visible_alpha`。
 * </pre>
 *
 * @param payload 动画命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_show_alpha")
fun View.bindingViewAnimShowAlpha(payload: ViewAnimDurationAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getShowAlphaAnimation(p.durationMillis),
    )
}

/**
 * 通过数据绑定播放任意起止透明度的 Alpha 渐变（不改 visibility）。
 * <pre>
 *     布局属性 `binding_view_anim_alpha_fade`；委托 [AnimationUtils.getAlphaAnimation]。
 * </pre>
 *
 * @param payload 渐变命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_alpha_fade")
fun View.bindingViewAnimAlphaFade(payload: ViewAnimAlphaFadeAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getAlphaAnimation(p.fromAlpha, p.toAlpha, p.durationMillis),
    )
}

// ==========
// = 缩放动画 =
// ==========

/**
 * 通过数据绑定以默认时长播放中心缩小至 0 的缩放动画。
 * <pre>
 *     布局属性 `binding_view_anim_lessen_scale_ts`；委托 [AnimationUtils.getLessenScaleAnimation] 无参时长重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_anim_lessen_scale_ts")
fun View.bindingViewAnimLessenScaleTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getLessenScaleAnimation(AnimationUtils.DEFAULT_ANIMATION_DURATION),
    )
}

/**
 * 通过数据绑定播放中心缩小至 0 的缩放动画（可指定时长）。
 * <pre>
 *     布局属性 `binding_view_anim_lessen_scale`；委托 [AnimationUtils.getLessenScaleAnimation]。
 * </pre>
 *
 * @param payload 动画命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_lessen_scale")
fun View.bindingViewAnimLessenScale(payload: ViewAnimDurationAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getLessenScaleAnimation(p.durationMillis),
    )
}

/**
 * 通过数据绑定以默认时长播放中心放大至 1 的缩放动画。
 * <pre>
 *     布局属性 `binding_view_anim_amplify_scale_ts`；委托 [AnimationUtils.getAmplificationAnimation] 无参时长重载。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_anim_amplify_scale_ts")
fun View.bindingViewAnimAmplifyScaleTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getAmplificationAnimation(AnimationUtils.DEFAULT_ANIMATION_DURATION),
    )
}

/**
 * 通过数据绑定播放中心放大至 1 的缩放动画（可指定时长）。
 * <pre>
 *     布局属性 `binding_view_anim_amplify_scale`；委托 [AnimationUtils.getAmplificationAnimation]。
 * </pre>
 *
 * @param payload 动画命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_amplify_scale")
fun View.bindingViewAnimAmplifyScale(payload: ViewAnimDurationAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getAmplificationAnimation(p.durationMillis),
    )
}

/**
 * 通过数据绑定以视图中心为原点播放缩放动画。
 * <pre>
 *     布局属性 `binding_view_anim_scale_center`；委托 [AnimationUtils.getScaleAnimationCenter] 四参缩放重载。
 * </pre>
 *
 * @param payload 缩放命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_scale_center")
fun View.bindingViewAnimScaleCenter(payload: ViewAnimScaleCenterAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getScaleAnimationCenter(
            p.fromX,
            p.toX,
            p.fromY,
            p.toY,
            p.durationMillis,
        ),
    )
}

/**
 * 通过数据绑定以左上角为原点播放缩放动画。
 * <pre>
 *     布局属性 `binding_view_anim_scale`；委托 [AnimationUtils.getScaleAnimation] 四参缩放重载。
 * </pre>
 *
 * @param payload 缩放命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_scale")
fun View.bindingViewAnimScale(payload: ViewAnimScaleAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    AnimationUtils.startAnimation(
        this,
        AnimationUtils.getScaleAnimation(
            p.fromX,
            p.toX,
            p.fromY,
            p.toY,
            p.durationMillis,
            null,
        ),
    )
}