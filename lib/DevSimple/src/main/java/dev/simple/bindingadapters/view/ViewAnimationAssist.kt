package dev.simple.bindingadapters.view

import android.view.View
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.Interpolator
import dev.simple.bindingadapters.view.ViewAnimationAssist.runTranslateAnim
import dev.simple.bindingadapters.view.ViewAnimationAssist.wrapAnimListener
import dev.utils.app.anim.AnimationUtils

/**
 * View 动画数据绑定的内部辅助方法集合。
 * <pre>
 *     供 [ViewAnimation] 在 `animationListener` 非 null 时构造平移/摇晃动画；
 *     行为对齐 [dev.utils.app.anim.ViewAnimationUtils] 内 banClick 与监听器转发语义。
 * </pre>
 */
internal object ViewAnimationAssist {

    /**
     * 将禁止点击与用户监听器合并为单一动画监听器。
     * <pre>
     *     `isBanClick` 为 true 时在 onAnimationStart/End 切换 [View.isClickable]；
     *     再转发 [Animation.AnimationListener] 同名回调。
     * </pre>
     *
     * @param view 目标视图
     * @param isBanClick 动画过程中是否禁止点击
     * @param delegate 用户监听器，可为 null
     * @return 合并后的监听器；两者均为 false/null 时返回 null
     */
    fun wrapAnimListener(
        view: View,
        isBanClick: Boolean,
        delegate: Animation.AnimationListener?,
    ): Animation.AnimationListener? {
        if (!isBanClick && delegate == null) return null
        return object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                if (isBanClick) view.isClickable = false
                delegate?.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animation) {
                if (isBanClick) view.isClickable = true
                delegate?.onAnimationEnd(animation)
            }

            override fun onAnimationRepeat(animation: Animation) {
                delegate?.onAnimationRepeat(animation)
            }
        }
    }

    /**
     * 在视图上启动已构造的动画。
     * <pre>
     *     委托 [AnimationUtils.startAnimation]。
     * </pre>
     *
     * @param animation 待播放动画
     */
    fun View.startFactoryAnimation(animation: Animation) {
        AnimationUtils.startAnimation(this, animation)
    }

    /**
     * 构造平移动画并启动，支持插值器、禁止点击与监听器。
     * <pre>
     *     通过 [AnimationUtils.getTranslateAnimation] 创建动画；
     *     监听器经 [wrapAnimListener] 包装后由 [AnimationUtils.setAnimationListener] 设置。
     * </pre>
     *
     * @param fromXDelta 起始 X 偏移
     * @param toXDelta 结束 X 偏移
     * @param fromYDelta 起始 Y 偏移
     * @param toYDelta 结束 Y 偏移
     * @param interpolator 插值器，可为 null
     * @param durationMillis 动画时长
     * @param isBanClick 动画过程中是否禁止点击
     * @param listener 用户监听器，可为 null
     * @return `true` 已启动动画
     */
    fun View.runTranslateAnim(
        fromXDelta: Float,
        toXDelta: Float,
        fromYDelta: Float,
        toYDelta: Float,
        interpolator: Interpolator?,
        durationMillis: Long,
        isBanClick: Boolean,
        listener: Animation.AnimationListener?,
    ): Boolean {
        val animation = AnimationUtils.getTranslateAnimation(
            fromXDelta,
            toXDelta,
            fromYDelta,
            toYDelta,
            interpolator,
            durationMillis,
        )
        wrapAnimListener(this, isBanClick, listener)?.let {
            AnimationUtils.setAnimationListener(animation, it)
        }
        startFactoryAnimation(animation)
        return true
    }

    /**
     * 以循环插值器构造平移动画并启动。
     * <pre>
     *     `cycles` 大于 0 时使用 [CycleInterpolator]；否则 interpolator 为 null。
     *     其余逻辑同 [runTranslateAnim]。
     * </pre>
     *
     * @param fromXDelta 起始 X 偏移
     * @param toXDelta 结束 X 偏移
     * @param fromYDelta 起始 Y 偏移
     * @param toYDelta 结束 Y 偏移
     * @param cycles [CycleInterpolator] 周期
     * @param durationMillis 动画时长
     * @param isBanClick 动画过程中是否禁止点击
     * @param listener 用户监听器，可为 null
     * @return `true` 已启动动画
     */
    fun View.runTranslateAnimCycles(
        fromXDelta: Float,
        toXDelta: Float,
        fromYDelta: Float,
        toYDelta: Float,
        cycles: Float,
        durationMillis: Long,
        isBanClick: Boolean,
        listener: Animation.AnimationListener?,
    ): Boolean {
        val interp = if (cycles > 0f) CycleInterpolator(cycles) else null
        return runTranslateAnim(
            fromXDelta,
            toXDelta,
            fromYDelta,
            toYDelta,
            interp,
            durationMillis,
            isBanClick,
            listener,
        )
    }
}