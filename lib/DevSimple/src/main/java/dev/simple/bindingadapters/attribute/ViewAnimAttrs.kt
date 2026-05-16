package dev.simple.bindingadapters.attribute

import android.view.animation.Interpolator
import dev.utils.app.anim.AnimationUtils

/**
 * 透明度显隐动画命令：时间戳 + 可选时长与是否禁止点击。
 * <pre>
 *     配合 `binding_view_anim_invisible_alpha`、`binding_view_anim_gone_alpha`、`binding_view_anim_visible_alpha`；
 *     时间戳判定与 `qualifiesBindingAction` 一致。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property durationMillis 动画时长，默认 [AnimationUtils.DEFAULT_ANIMATION_DURATION]
 * @property isBanClick 动画过程中是否禁止点击
 */
data class ViewAnimAlphaAt(
    val timestamp: Long,
    val durationMillis: Long = AnimationUtils.DEFAULT_ANIMATION_DURATION,
    val isBanClick: Boolean = false,
)

/**
 * 平移动画命令：时间戳 + 起止位移、时长与插值方式。
 * <pre>
 *     配合 `binding_view_anim_translate`；委托 [dev.utils.app.anim.ViewAnimationUtils.translate]。
 *     `cycles` 非 null 时走 cycles 重载（内部 [android.view.animation.CycleInterpolator]）；
 *     否则走 [Interpolator] 重载（`interpolator` 可为空）。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property fromXDelta 起始 X 偏移
 * @property toXDelta 结束 X 偏移
 * @property fromYDelta 起始 Y 偏移
 * @property toYDelta 结束 Y 偏移
 * @property durationMillis 动画时长
 * @property isBanClick 动画过程中是否禁止点击
 * @property interpolator 自定义插值器，`cycles` 为 null 时生效
 * @property cycles 循环周期，`null` 表示不使用 CycleInterpolator 重载
 */
data class ViewAnimTranslateAt(
    val timestamp: Long,
    val fromXDelta: Float,
    val toXDelta: Float,
    val fromYDelta: Float,
    val toYDelta: Float,
    val durationMillis: Long,
    val isBanClick: Boolean = false,
    val interpolator: Interpolator? = null,
    val cycles: Float? = null,
)

/**
 * 摇晃动画命令（自定义 X 幅度与周期）。
 * <pre>
 *     配合 `binding_view_anim_shake`；委托 [dev.utils.app.anim.ViewAnimationUtils.shake] 四参位移重载。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property fromXDelta 起始 X 偏移
 * @property toXDelta 结束 X 偏移
 * @property cycles [android.view.animation.CycleInterpolator] 周期
 * @property durationMillis 动画时长
 * @property isBanClick 动画过程中是否禁止点击
 */
data class ViewAnimShakeAt(
    val timestamp: Long,
    val fromXDelta: Float,
    val toXDelta: Float,
    val cycles: Float,
    val durationMillis: Long,
    val isBanClick: Boolean = false,
)

/**
 * 预设幅度摇晃命令（工具类固定 X 方向幅度 10）。
 * <pre>
 *     配合 `binding_view_anim_shake_preset`；委托 [dev.utils.app.anim.ViewAnimationUtils.shake] 的 cycles + duration 重载。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property cycles 动画周期
 * @property durationMillis 动画时长
 * @property isBanClick 动画过程中是否禁止点击
 */
data class ViewAnimShakePresetAt(
    val timestamp: Long,
    val cycles: Float,
    val durationMillis: Long,
    val isBanClick: Boolean = false,
)