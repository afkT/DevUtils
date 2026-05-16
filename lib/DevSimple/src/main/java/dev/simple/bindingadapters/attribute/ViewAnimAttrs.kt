package dev.simple.bindingadapters.attribute

import android.view.animation.Animation
import android.view.animation.Interpolator
import dev.utils.app.anim.AnimationUtils

/**
 * 透明度显隐动画命令：时间戳 + 可选时长、是否禁止点击与监听器。
 * <pre>
 *     配合 `binding_view_anim_invisible_alpha`、`binding_view_anim_gone_alpha`、`binding_view_anim_visible_alpha`；
 *     时间戳判定与 `qualifiesBindingAction` 一致；`animationListener` 非 null 时走工具类含监听器重载。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property durationMillis 动画时长，默认 [AnimationUtils.DEFAULT_ANIMATION_DURATION]
 * @property isBanClick 动画过程中是否禁止点击
 * @property animationListener 动画监听器，可为 null
 */
data class ViewAnimAlphaAt(
    val timestamp: Long,
    val durationMillis: Long = AnimationUtils.DEFAULT_ANIMATION_DURATION,
    val isBanClick: Boolean = false,
    val animationListener: Animation.AnimationListener? = null,
)

/**
 * 平移动画命令：时间戳 + 起止位移、时长、插值与监听器。
 * <pre>
 *     配合 `binding_view_anim_translate`；`animationListener` 非 null 时由适配器构造动画并启动（与工具类内部 banClick 包装一致）。
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
 * @property animationListener 动画监听器，可为 null
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
    val animationListener: Animation.AnimationListener? = null,
)

/**
 * 摇晃动画命令（自定义 X 幅度与周期）。
 * <pre>
 *     配合 `binding_view_anim_shake`；`animationListener` 非 null 时走平移工厂 + 监听器包装。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property fromXDelta 起始 X 偏移
 * @property toXDelta 结束 X 偏移
 * @property cycles [android.view.animation.CycleInterpolator] 周期
 * @property durationMillis 动画时长
 * @property isBanClick 动画过程中是否禁止点击
 * @property animationListener 动画监听器，可为 null
 */
data class ViewAnimShakeAt(
    val timestamp: Long,
    val fromXDelta: Float,
    val toXDelta: Float,
    val cycles: Float,
    val durationMillis: Long,
    val isBanClick: Boolean = false,
    val animationListener: Animation.AnimationListener? = null,
)

/**
 * 预设幅度摇晃命令（工具类固定 X 方向幅度 10）。
 * <pre>
 *     配合 `binding_view_anim_shake_preset`；`animationListener` 非 null 时走平移工厂 + 监听器包装。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property cycles 动画周期
 * @property durationMillis 动画时长
 * @property isBanClick 动画过程中是否禁止点击
 * @property animationListener 动画监听器，可为 null
 */
data class ViewAnimShakePresetAt(
    val timestamp: Long,
    val cycles: Float,
    val durationMillis: Long,
    val isBanClick: Boolean = false,
    val animationListener: Animation.AnimationListener? = null,
)

/**
 * 仅含时间戳、可选时长与监听器的动画命令。
 * <pre>
 *     配合 `binding_view_anim_rotate_center`、`binding_view_anim_hidden_alpha`、`binding_view_anim_show_alpha`、
 *     `binding_view_anim_lessen_scale`、`binding_view_anim_amplify_scale` 等。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property durationMillis 动画时长，默认 [AnimationUtils.DEFAULT_ANIMATION_DURATION]
 * @property animationListener 动画监听器，可为 null
 */
data class ViewAnimDurationAt(
    val timestamp: Long,
    val durationMillis: Long = AnimationUtils.DEFAULT_ANIMATION_DURATION,
    val animationListener: Animation.AnimationListener? = null,
)

/**
 * 自定义角度旋转命令（相对 pivot 坐标）。
 * <pre>
 *     配合 `binding_view_anim_rotate`；委托 [AnimationUtils.getRotateAnimation] 含 pivot 与监听器重载。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property fromDegrees 起始角度
 * @property toDegrees 结束角度
 * @property pivotX 旋转中心 X
 * @property pivotY 旋转中心 Y
 * @property durationMillis 动画时长
 * @property animationListener 动画监听器，可为 null
 */
data class ViewAnimRotateAt(
    val timestamp: Long,
    val fromDegrees: Float,
    val toDegrees: Float,
    val pivotX: Float = 0.5f,
    val pivotY: Float = 0.5f,
    val durationMillis: Long = AnimationUtils.DEFAULT_ANIMATION_DURATION,
    val animationListener: Animation.AnimationListener? = null,
)

/**
 * 透明度渐变命令（不修改 [android.view.View.setVisibility]）。
 * <pre>
 *     配合 `binding_view_anim_alpha_fade`；委托 [AnimationUtils.getAlphaAnimation]。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property fromAlpha 起始透明度
 * @property toAlpha 结束透明度
 * @property durationMillis 动画时长
 * @property animationListener 动画监听器，可为 null
 */
data class ViewAnimAlphaFadeAt(
    val timestamp: Long,
    val fromAlpha: Float,
    val toAlpha: Float,
    val durationMillis: Long = AnimationUtils.DEFAULT_ANIMATION_DURATION,
    val animationListener: Animation.AnimationListener? = null,
)

/**
 * 缩放动画命令（默认以 View 中心为缩放中心）。
 * <pre>
 *     配合 `binding_view_anim_scale_center`；委托 [AnimationUtils.getScaleAnimationCenter]。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property fromX 起始 X 缩放比
 * @property toX 结束 X 缩放比
 * @property fromY 起始 Y 缩放比
 * @property toY 结束 Y 缩放比
 * @property durationMillis 动画时长
 * @property animationListener 动画监听器，可为 null
 */
data class ViewAnimScaleCenterAt(
    val timestamp: Long,
    val fromX: Float,
    val toX: Float,
    val fromY: Float,
    val toY: Float,
    val durationMillis: Long = AnimationUtils.DEFAULT_ANIMATION_DURATION,
    val animationListener: Animation.AnimationListener? = null,
)

/**
 * 缩放动画命令（左上角为缩放原点）。
 * <pre>
 *     配合 `binding_view_anim_scale`；委托 [AnimationUtils.getScaleAnimation]。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property fromX 起始 X 缩放比
 * @property toX 结束 X 缩放比
 * @property fromY 起始 Y 缩放比
 * @property toY 结束 Y 缩放比
 * @property durationMillis 动画时长
 * @property animationListener 动画监听器，可为 null
 */
data class ViewAnimScaleAt(
    val timestamp: Long,
    val fromX: Float,
    val toX: Float,
    val fromY: Float,
    val toY: Float,
    val durationMillis: Long = AnimationUtils.DEFAULT_ANIMATION_DURATION,
    val animationListener: Animation.AnimationListener? = null,
)
