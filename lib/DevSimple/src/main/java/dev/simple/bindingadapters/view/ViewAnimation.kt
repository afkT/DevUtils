package dev.simple.bindingadapters.view

import android.view.View
import android.view.animation.Animation
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.*
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.simple.bindingadapters.view.ViewAnimationAssist.runTranslateAnim
import dev.simple.bindingadapters.view.ViewAnimationAssist.runTranslateAnimCycles
import dev.simple.bindingadapters.view.ViewAnimationAssist.startFactoryAnimation
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
 *     监听器：各 `ViewAnim*At` 实体含可选 `animationListener`；`*_ts` 属性可另绑
 *     `binding_view_anim_*_ts_listener`（`requireAll = false`）传入 [Animation.AnimationListener]。
 *     动画为一次性副作用，须通过正时间戳或含 timestamp 的 attribute 实体触发（判定同 [qualifiesBindingAction]）。
 *     VM 持有 [Animation] 产物时可用 `binding_view_anim`：非 null 启动、null 结束（[AnimationUtils.cancelAnimation]）。
 *     与 [View] 中 `binding_view_animation` / `binding_view_start_animation` 等通用 Animation 控制属性并存、互不替代。
 *     `binding_view_anim_*_alpha`（ViewAnimationUtils）会同步 visibility；`binding_view_anim_hidden_alpha` /
 *     `binding_view_anim_show_alpha` / `binding_view_anim_alpha_fade` 仅播放 Alpha 动画，不改 visibility。
 * </pre>
 */

// =================
// = 通用 Animation =
// =================

/**
 * 通过数据绑定启动或结束 [Animation]。
 * <pre>
 *     布局属性 `binding_view_anim`；非 null 时 [AnimationUtils.startAnimation] 播放；
 *     null 时 [AnimationUtils.cancelAnimation] 结束当前动画。
 * </pre>
 *
 * @param animation 待播放动画，null 表示结束
 */
@BindingAdapter("binding_view_anim")
fun View.bindingViewAnim(animation: Animation?) {
    if (animation == null) {
        AnimationUtils.cancelAnimation(this)
    } else {
        startFactoryAnimation(animation)
    }
}

// ===============
// = 透明度渐变动画 =
// ===============

/**
 * 通过数据绑定以默认时长将视图渐隐为 INVISIBLE。
 * <pre>
 *     布局属性 `binding_view_anim_invisible_alpha_ts`、可选 `binding_view_anim_invisible_alpha_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 */
@BindingAdapter(
    value = [
        "binding_view_anim_invisible_alpha_ts",
        "binding_view_anim_invisible_alpha_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimInvisibleAlphaTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
) {
    if (!timestamp.qualifiesBindingAction()) return
    if (listener != null) {
        ViewAnimationUtils.invisibleViewByAlpha(
            this,
            AnimationUtils.DEFAULT_ANIMATION_DURATION,
            false,
            listener,
        )
    } else {
        ViewAnimationUtils.invisibleViewByAlpha(this)
    }
}

/**
 * 通过数据绑定将视图渐隐为 INVISIBLE（可指定时长、是否禁止点击与监听器）。
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
    return if (p.animationListener != null) {
        ViewAnimationUtils.invisibleViewByAlpha(
            this,
            p.durationMillis,
            p.isBanClick,
            p.animationListener,
        )
    } else {
        ViewAnimationUtils.invisibleViewByAlpha(this, p.durationMillis, p.isBanClick)
    }
}

/**
 * 通过数据绑定以默认时长将视图渐隐为 GONE。
 * <pre>
 *     布局属性 `binding_view_anim_gone_alpha_ts`、可选 `binding_view_anim_gone_alpha_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 */
@BindingAdapter(
    value = [
        "binding_view_anim_gone_alpha_ts",
        "binding_view_anim_gone_alpha_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimGoneAlphaTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
) {
    if (!timestamp.qualifiesBindingAction()) return
    if (listener != null) {
        ViewAnimationUtils.goneViewByAlpha(
            this,
            AnimationUtils.DEFAULT_ANIMATION_DURATION,
            false,
            listener,
        )
    } else {
        ViewAnimationUtils.goneViewByAlpha(this)
    }
}

/**
 * 通过数据绑定将视图渐隐为 GONE（可指定时长、是否禁止点击与监听器）。
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
    return if (p.animationListener != null) {
        ViewAnimationUtils.goneViewByAlpha(
            this,
            p.durationMillis,
            p.isBanClick,
            p.animationListener,
        )
    } else {
        ViewAnimationUtils.goneViewByAlpha(this, p.durationMillis, p.isBanClick)
    }
}

/**
 * 通过数据绑定以默认时长将视图渐显为 VISIBLE。
 * <pre>
 *     布局属性 `binding_view_anim_visible_alpha_ts`、可选 `binding_view_anim_visible_alpha_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 */
@BindingAdapter(
    value = [
        "binding_view_anim_visible_alpha_ts",
        "binding_view_anim_visible_alpha_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimVisibleAlphaTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
) {
    if (!timestamp.qualifiesBindingAction()) return
    if (listener != null) {
        ViewAnimationUtils.visibleViewByAlpha(
            this,
            AnimationUtils.DEFAULT_ANIMATION_DURATION,
            false,
            listener,
        )
    } else {
        ViewAnimationUtils.visibleViewByAlpha(this)
    }
}

/**
 * 通过数据绑定将视图渐显为 VISIBLE（可指定时长、是否禁止点击与监听器）。
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
    return if (p.animationListener != null) {
        ViewAnimationUtils.visibleViewByAlpha(
            this,
            p.durationMillis,
            p.isBanClick,
            p.animationListener,
        )
    } else {
        ViewAnimationUtils.visibleViewByAlpha(this, p.durationMillis, p.isBanClick)
    }
}

// ============
// = 平移与摇晃 =
// ============

/**
 * 通过数据绑定触发平移动画。
 * <pre>
 *     布局属性 `binding_view_anim_translate`；`animationListener` 非 null 时走工厂动画 + banClick 包装。
 * </pre>
 *
 * @param payload 平移命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_translate")
fun View.bindingViewAnimTranslate(payload: ViewAnimTranslateAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    if (p.animationListener != null) {
        return if (p.cycles != null) {
            runTranslateAnimCycles(
                p.fromXDelta,
                p.toXDelta,
                p.fromYDelta,
                p.toYDelta,
                p.cycles,
                p.durationMillis,
                p.isBanClick,
                p.animationListener,
            )
        } else {
            runTranslateAnim(
                p.fromXDelta,
                p.toXDelta,
                p.fromYDelta,
                p.toYDelta,
                p.interpolator,
                p.durationMillis,
                p.isBanClick,
                p.animationListener,
            )
        }
    }
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
 *     布局属性 `binding_view_anim_shake_ts`、可选 `binding_view_anim_shake_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter(
    value = [
        "binding_view_anim_shake_ts",
        "binding_view_anim_shake_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimShakeTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
): Boolean {
    if (!timestamp.qualifiesBindingAction()) return false
    if (listener != null) {
        return runTranslateAnimCycles(0f, 10f, 0f, 0f, 7f, 700L, false, listener)
    }
    return ViewAnimationUtils.shake(this)
}

/**
 * 通过数据绑定触发自定义 X 向摇晃。
 * <pre>
 *     布局属性 `binding_view_anim_shake`；`animationListener` 非 null 时走平移工厂 + 监听器包装。
 * </pre>
 *
 * @param payload 摇晃命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_shake")
fun View.bindingViewAnimShake(payload: ViewAnimShakeAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    if (p.animationListener != null) {
        return runTranslateAnimCycles(
            p.fromXDelta,
            p.toXDelta,
            0f,
            0f,
            p.cycles,
            p.durationMillis,
            p.isBanClick,
            p.animationListener,
        )
    }
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
 *     布局属性 `binding_view_anim_shake_preset`；`animationListener` 非 null 时走平移工厂 + 监听器包装。
 * </pre>
 *
 * @param payload 预设摇晃命令封装，可为 null
 * @return `true` 已启动动画，`false` 跳过或失败
 */
@BindingAdapter("binding_view_anim_shake_preset")
fun View.bindingViewAnimShakePreset(payload: ViewAnimShakePresetAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.qualifiesBindingAction()) return false
    if (p.animationListener != null) {
        return runTranslateAnimCycles(
            0f,
            10f,
            0f,
            0f,
            p.cycles,
            p.durationMillis,
            p.isBanClick,
            p.animationListener,
        )
    }
    return ViewAnimationUtils.shake(this, p.cycles, p.durationMillis, p.isBanClick)
}

// ============================
// = AnimationUtils 工厂 + 启动 =
// ============================

// ==========
// = 旋转动画 =
// ==========

/**
 * 通过数据绑定以默认时长绕视图中心旋转一周（0°→359°）。
 * <pre>
 *     布局属性 `binding_view_anim_rotate_center_ts`、可选 `binding_view_anim_rotate_center_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 */
@BindingAdapter(
    value = [
        "binding_view_anim_rotate_center_ts",
        "binding_view_anim_rotate_center_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimRotateCenterTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
) {
    if (!timestamp.qualifiesBindingAction()) return
    val animation = if (listener != null) {
        AnimationUtils.getRotateAnimationByCenter(listener)
    } else {
        AnimationUtils.getRotateAnimationByCenter()
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定绕视图中心旋转（可指定时长与监听器）。
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
    val animation = if (p.animationListener != null) {
        AnimationUtils.getRotateAnimationByCenter(p.durationMillis, p.animationListener)
    } else {
        AnimationUtils.getRotateAnimationByCenter(p.durationMillis)
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定按自定义角度与 pivot 旋转。
 * <pre>
 *     布局属性 `binding_view_anim_rotate`；委托 [AnimationUtils.getRotateAnimation] 含 pivot 与监听器重载。
 * </pre>
 *
 * @param payload 旋转命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_rotate")
fun View.bindingViewAnimRotate(payload: ViewAnimRotateAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    startFactoryAnimation(
        AnimationUtils.getRotateAnimation(
            p.fromDegrees,
            p.toDegrees,
            p.pivotX,
            p.pivotY,
            p.durationMillis,
            p.animationListener,
        ),
    )
}

// ================
// = 纯 Alpha 渐变 =
// ================

/**
 * 通过数据绑定播放 1.0→0.0 透明度渐变（不改 visibility）。
 * <pre>
 *     布局属性 `binding_view_anim_hidden_alpha_ts`、可选 `binding_view_anim_hidden_alpha_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 */
@BindingAdapter(
    value = [
        "binding_view_anim_hidden_alpha_ts",
        "binding_view_anim_hidden_alpha_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimHiddenAlphaTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
) {
    if (!timestamp.qualifiesBindingAction()) return
    val animation = if (listener != null) {
        AnimationUtils.getHiddenAlphaAnimation(listener)
    } else {
        AnimationUtils.getHiddenAlphaAnimation()
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定播放 1.0→0.0 透明度渐变（可指定时长与监听器，不改 visibility）。
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
    val animation = if (p.animationListener != null) {
        AnimationUtils.getHiddenAlphaAnimation(p.durationMillis, p.animationListener)
    } else {
        AnimationUtils.getHiddenAlphaAnimation(p.durationMillis)
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定播放 0.0→1.0 透明度渐变（不改 visibility）。
 * <pre>
 *     布局属性 `binding_view_anim_show_alpha_ts`、可选 `binding_view_anim_show_alpha_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 */
@BindingAdapter(
    value = [
        "binding_view_anim_show_alpha_ts",
        "binding_view_anim_show_alpha_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimShowAlphaTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
) {
    if (!timestamp.qualifiesBindingAction()) return
    val animation = if (listener != null) {
        AnimationUtils.getShowAlphaAnimation(listener)
    } else {
        AnimationUtils.getShowAlphaAnimation()
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定播放 0.0→1.0 透明度渐变（可指定时长与监听器，不改 visibility）。
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
    val animation = if (p.animationListener != null) {
        AnimationUtils.getShowAlphaAnimation(p.durationMillis, p.animationListener)
    } else {
        AnimationUtils.getShowAlphaAnimation(p.durationMillis)
    }
    startFactoryAnimation(animation)
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
    startFactoryAnimation(
        AnimationUtils.getAlphaAnimation(
            p.fromAlpha,
            p.toAlpha,
            p.durationMillis,
            p.animationListener,
        ),
    )
}

// ==========
// = 缩放动画 =
// ==========

/**
 * 通过数据绑定以默认时长播放中心缩小至 0 的缩放动画。
 * <pre>
 *     布局属性 `binding_view_anim_lessen_scale_ts`、可选 `binding_view_anim_lessen_scale_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 */
@BindingAdapter(
    value = [
        "binding_view_anim_lessen_scale_ts",
        "binding_view_anim_lessen_scale_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimLessenScaleTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
) {
    if (!timestamp.qualifiesBindingAction()) return
    val animation = if (listener != null) {
        AnimationUtils.getLessenScaleAnimation(AnimationUtils.DEFAULT_ANIMATION_DURATION, listener)
    } else {
        AnimationUtils.getLessenScaleAnimation(AnimationUtils.DEFAULT_ANIMATION_DURATION)
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定播放中心缩小至 0 的缩放动画（可指定时长与监听器）。
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
    val animation = if (p.animationListener != null) {
        AnimationUtils.getLessenScaleAnimation(p.durationMillis, p.animationListener)
    } else {
        AnimationUtils.getLessenScaleAnimation(p.durationMillis)
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定以默认时长播放中心放大至 1 的缩放动画。
 * <pre>
 *     布局属性 `binding_view_anim_amplify_scale_ts`、可选 `binding_view_anim_amplify_scale_ts_listener`。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param listener 动画监听器，可为 null
 */
@BindingAdapter(
    value = [
        "binding_view_anim_amplify_scale_ts",
        "binding_view_anim_amplify_scale_ts_listener",
    ],
    requireAll = false,
)
fun View.bindingViewAnimAmplifyScaleTs(
    timestamp: Long?,
    listener: Animation.AnimationListener?,
) {
    if (!timestamp.qualifiesBindingAction()) return
    val animation = if (listener != null) {
        AnimationUtils.getAmplificationAnimation(
            AnimationUtils.DEFAULT_ANIMATION_DURATION,
            listener
        )
    } else {
        AnimationUtils.getAmplificationAnimation(AnimationUtils.DEFAULT_ANIMATION_DURATION)
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定播放中心放大至 1 的缩放动画（可指定时长与监听器）。
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
    val animation = if (p.animationListener != null) {
        AnimationUtils.getAmplificationAnimation(p.durationMillis, p.animationListener)
    } else {
        AnimationUtils.getAmplificationAnimation(p.durationMillis)
    }
    startFactoryAnimation(animation)
}

/**
 * 通过数据绑定以视图中心为原点播放缩放动画。
 * <pre>
 *     布局属性 `binding_view_anim_scale_center`；委托 [AnimationUtils.getScaleAnimationCenter]。
 * </pre>
 *
 * @param payload 缩放命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_scale_center")
fun View.bindingViewAnimScaleCenter(payload: ViewAnimScaleCenterAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    startFactoryAnimation(
        AnimationUtils.getScaleAnimationCenter(
            p.fromX,
            p.toX,
            p.fromY,
            p.toY,
            p.durationMillis,
            p.animationListener,
        ),
    )
}

/**
 * 通过数据绑定以左上角为原点播放缩放动画。
 * <pre>
 *     布局属性 `binding_view_anim_scale`；委托 [AnimationUtils.getScaleAnimation]。
 * </pre>
 *
 * @param payload 缩放命令封装，可为 null
 */
@BindingAdapter("binding_view_anim_scale")
fun View.bindingViewAnimScale(payload: ViewAnimScaleAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    startFactoryAnimation(
        AnimationUtils.getScaleAnimation(
            p.fromX,
            p.toX,
            p.fromY,
            p.toY,
            p.durationMillis,
            p.animationListener,
        ),
    )
}
