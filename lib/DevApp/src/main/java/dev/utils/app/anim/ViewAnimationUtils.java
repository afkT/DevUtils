package dev.utils.app.anim;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

/**
 * detail: View 动画工具类
 * @author Ttt
 * <pre>
 *     AnimationUtils 基础上封装, 提供简单的控制视图的动画的工具方法
 * </pre>
 */
public final class ViewAnimationUtils {

    private ViewAnimationUtils() {
    }

    // ====================
    // = 视图透明度渐变动画 =
    // ====================

    /**
     * 将给定视图渐渐隐去
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean invisibleViewByAlpha(
            final View view,
            final long durationMillis,
            final boolean isBanClick,
            final AnimationListener animationListener
    ) {
        if (view != null && view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
            // 获取动画
            AlphaAnimation animation = AnimationUtils.getHiddenAlphaAnimation(durationMillis);
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick && view != null) {
                        view.setClickable(false);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (animationListener != null) {
                        animationListener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick && view != null) {
                        view.setClickable(true);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(animation);
            return true;
        }
        return false;
    }

    /**
     * 将给定视图渐渐隐去
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean invisibleViewByAlpha(
            final View view,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        return invisibleViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean invisibleViewByAlpha(
            final View view,
            final long durationMillis,
            final boolean isBanClick
    ) {
        return invisibleViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @return {@code true} success, {@code false} fail
     */
    public static boolean invisibleViewByAlpha(
            final View view,
            final long durationMillis
    ) {
        return invisibleViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐隐去
     * @param view              待处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean invisibleViewByAlpha(
            final View view,
            final boolean isBanClick,
            final AnimationListener animationListener
    ) {
        return invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐隐去
     * @param view              待处理的视图
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean invisibleViewByAlpha(
            final View view,
            final AnimationListener animationListener
    ) {
        return invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去
     * @param view       待处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean invisibleViewByAlpha(
            final View view,
            final boolean isBanClick
    ) {
        return invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去
     * @param view 待处理的视图
     * @return {@code true} success, {@code false} fail
     */
    public static boolean invisibleViewByAlpha(final View view) {
        return invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    // =

    /**
     * 将给定视图渐渐隐去最后从界面中移除
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean goneViewByAlpha(
            final View view,
            final long durationMillis,
            final boolean isBanClick,
            final AnimationListener animationListener
    ) {
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
            // 获取动画
            AlphaAnimation animation = AnimationUtils.getHiddenAlphaAnimation(durationMillis);
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick && view != null) {
                        view.setClickable(false);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (animationListener != null) {
                        animationListener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick && view != null) {
                        view.setClickable(true);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(animation);
            return true;
        }
        return false;
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean goneViewByAlpha(
            final View view,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        return goneViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean goneViewByAlpha(
            final View view,
            final long durationMillis,
            final boolean isBanClick
    ) {
        return goneViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @return {@code true} success, {@code false} fail
     */
    public static boolean goneViewByAlpha(
            final View view,
            final long durationMillis
    ) {
        return goneViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除
     * @param view              待处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean goneViewByAlpha(
            final View view,
            final boolean isBanClick,
            final AnimationListener animationListener
    ) {
        return goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除
     * @param view              待处理的视图
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean goneViewByAlpha(
            final View view,
            final AnimationListener animationListener
    ) {
        return goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除
     * @param view       待处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean goneViewByAlpha(
            final View view,
            final boolean isBanClick
    ) {
        return goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除
     * @param view 待处理的视图
     * @return {@code true} success, {@code false} fail
     */
    public static boolean goneViewByAlpha(final View view) {
        return goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    // =

    /**
     * 将给定视图渐渐显示出来
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean visibleViewByAlpha(
            final View view,
            final long durationMillis,
            final boolean isBanClick,
            final AnimationListener animationListener
    ) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            // 获取动画
            AlphaAnimation animation = AnimationUtils.getShowAlphaAnimation(durationMillis);
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick && view != null) {
                        view.setClickable(false);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (animationListener != null) {
                        animationListener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick && view != null) {
                        view.setClickable(true);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(animation);
            return true;
        }
        return false;
    }

    /**
     * 将给定视图渐渐显示出来
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean visibleViewByAlpha(
            final View view,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        return visibleViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean visibleViewByAlpha(
            final View view,
            final long durationMillis,
            final boolean isBanClick
    ) {
        return visibleViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐显示出来
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @return {@code true} success, {@code false} fail
     */
    public static boolean visibleViewByAlpha(
            final View view,
            final long durationMillis
    ) {
        return visibleViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐显示出来
     * @param view              待处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean visibleViewByAlpha(
            final View view,
            final boolean isBanClick,
            final AnimationListener animationListener
    ) {
        return visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来
     * @param view              待处理的视图
     * @param animationListener 动画监听器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean visibleViewByAlpha(
            final View view,
            final AnimationListener animationListener
    ) {
        return visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来
     * @param view       待处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean visibleViewByAlpha(
            final View view,
            final boolean isBanClick
    ) {
        return visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐显示出来
     * @param view 待处理的视图
     * @return {@code true} success, {@code false} fail
     */
    public static boolean visibleViewByAlpha(final View view) {
        return visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    // ===============
    // = 视图移动动画 =
    // ===============

    /**
     * 视图移动
     * @param view           待移动的视图
     * @param fromXDelta     动画开始的 X 轴坐标
     * @param toXDelta       动画结束的 X 轴坐标
     * @param fromYDelta     动画开始的 Y 轴坐标
     * @param toYDelta       动画结束的 Y 轴坐标
     * @param interpolator   动画周期
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean translate(
            final View view,
            final float fromXDelta,
            final float toXDelta,
            final float fromYDelta,
            final float toYDelta,
            final Interpolator interpolator,
            final long durationMillis,
            final boolean isBanClick
    ) {
        if (view != null) {
            TranslateAnimation animation = AnimationUtils.getTranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta, interpolator, durationMillis);
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick && view != null) {
                        view.setClickable(false);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick && view != null) {
                        view.setClickable(true);
                    }
                }
            });
            view.startAnimation(animation);
            return true;
        }
        return false;
    }

    /**
     * 视图移动
     * @param view           待移动的视图
     * @param fromXDelta     动画开始的 X 轴坐标
     * @param toXDelta       动画结束的 X 轴坐标
     * @param fromYDelta     动画开始的 Y 轴坐标
     * @param toYDelta       动画结束的 Y 轴坐标
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean translate(
            final View view,
            final float fromXDelta,
            final float toXDelta,
            final float fromYDelta,
            final float toYDelta,
            final float cycles,
            final long durationMillis,
            final boolean isBanClick
    ) {
        if (view != null) {
            Interpolator interpolator = (cycles > 0.0f) ? new CycleInterpolator(cycles) : null;
            return translate(view, fromXDelta, toXDelta, fromYDelta, toYDelta, interpolator, durationMillis, isBanClick);
        }
        return false;
    }

    /**
     * 视图移动
     * @param view           待移动的视图
     * @param fromXDelta     动画开始的 X 轴坐标
     * @param toXDelta       动画结束的 X 轴坐标
     * @param fromYDelta     动画开始的 Y 轴坐标
     * @param toYDelta       动画结束的 Y 轴坐标
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @return {@code true} success, {@code false} fail
     */
    public static boolean translate(
            final View view,
            final float fromXDelta,
            final float toXDelta,
            final float fromYDelta,
            final float toYDelta,
            final float cycles,
            final long durationMillis
    ) {
        return translate(view, fromXDelta, toXDelta, fromYDelta, toYDelta, cycles, durationMillis, false);
    }

    // =

    /**
     * 视图摇晃
     * @param view           待摇晃的视图
     * @param fromXDelta     动画开始的 X 轴坐标
     * @param toXDelta       动画结束的 X 轴坐标
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final float fromXDelta,
            final float toXDelta,
            final float cycles,
            final long durationMillis,
            final boolean isBanClick
    ) {
        return translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃
     * @param view           待摇晃的视图
     * @param fromXDelta     动画开始的 X 轴坐标
     * @param toXDelta       动画结束的 X 轴坐标
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final float fromXDelta,
            final float toXDelta,
            final float cycles,
            final long durationMillis
    ) {
        return translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles, durationMillis, false);
    }

    /**
     * 视图摇晃 ( 摇晃幅度为 10、重复 7 次 )
     * @param view           待摇晃的视图
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final float cycles,
            final long durationMillis,
            final boolean isBanClick
    ) {
        return translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃 ( 摇晃幅度为 10、持续 700 毫秒 )
     * @param view       待摇晃的视图
     * @param cycles     动画周期 {@link CycleInterpolator}
     * @param isBanClick 在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final float cycles,
            final boolean isBanClick
    ) {
        return translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, 700, isBanClick);
    }

    /**
     * 视图摇晃 ( 摇晃幅度为 10)
     * @param view           待摇晃的视图
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final float cycles,
            final long durationMillis
    ) {
        return translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis, false);
    }

    /**
     * 视图摇晃 ( 摇晃幅度为 10、重复 7 次 )
     * @param view           待摇晃的视图
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final long durationMillis,
            final boolean isBanClick
    ) {
        return translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃 ( 摇晃幅度为 10、持续 700 毫秒 )
     * @param view   待摇晃的视图
     * @param cycles 动画周期 {@link CycleInterpolator}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final float cycles
    ) {
        return translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, 700, false);
    }

    /**
     * 视图摇晃 ( 摇晃幅度为 10、重复 7 次 )
     * @param view           待摇晃的视图
     * @param durationMillis 动画持续时间
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final long durationMillis
    ) {
        return translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis, false);
    }

    // =

    /**
     * 视图摇晃 ( 摇晃幅度为 10、重复 7 次、持续 700 毫秒 )
     * @param view       待摇晃的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(
            final View view,
            final boolean isBanClick
    ) {
        return translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, 700, isBanClick);
    }

    /**
     * 视图摇晃 ( 摇晃幅度为 10、重复 7 次、持续 700 毫秒 )
     * @param view 待摇晃的视图
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shake(final View view) {
        return translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, 700, false);
    }
}