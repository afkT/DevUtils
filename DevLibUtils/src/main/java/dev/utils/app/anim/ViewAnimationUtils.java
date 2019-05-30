package dev.utils.app.anim;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

/**
 * detail: 视图动画工具箱 (AnimationUtils 基础上封装), 提供简单的控制视图的动画的工具方法
 * @author Ttt
 */
public final class ViewAnimationUtils {

    private ViewAnimationUtils() {
    }

    // ======================
    // = 视图透明度渐变动画 =
    // ======================

    /**
     * 将给定视图渐渐隐去 - view.setVisibility(View.INVISIBLE)
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, final long durationMillis, final boolean isBanClick,
                                            final AnimationListener animationListener) {
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
        }
    }

    /**
     * 将给定视图渐渐隐去 - view.setVisibility(View.INVISIBLE)
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, final long durationMillis, final AnimationListener animationListener) {
        invisibleViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去 - view.setVisibility(View.INVISIBLE)
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void invisibleViewByAlpha(final View view, final long durationMillis, final boolean isBanClick) {
        invisibleViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去 - view.setVisibility(View.INVISIBLE)
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     */
    public static void invisibleViewByAlpha(final View view, final long durationMillis) {
        invisibleViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐隐去 - view.setVisibility(View.INVISIBLE)
     * @param view              待处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, final boolean isBanClick, final AnimationListener animationListener) {
        invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐隐去 - view.setVisibility(View.INVISIBLE)
     * @param view              待处理的视图
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, final AnimationListener animationListener) {
        invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去 - view.setVisibility(View.INVISIBLE)
     * @param view       待处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void invisibleViewByAlpha(final View view, final boolean isBanClick) {
        invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去 - view.setVisibility(View.INVISIBLE)
     * @param view 待处理的视图
     */
    public static void invisibleViewByAlpha(final View view) {
        invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    // =

    /**
     * 将给定视图渐渐隐去最后从界面中移除 - view.setVisibility(View.GONE)
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, final long durationMillis, final boolean isBanClick,
                                       final AnimationListener animationListener) {
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
        }
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除 - view.setVisibility(View.GONE)
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, final long durationMillis, final AnimationListener animationListener) {
        goneViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除 - view.setVisibility(View.GONE)
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void goneViewByAlpha(final View view, final long durationMillis, final boolean isBanClick) {
        goneViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除 - view.setVisibility(View.GONE)
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     */
    public static void goneViewByAlpha(final View view, final long durationMillis) {
        goneViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除 - view.setVisibility(View.GONE)
     * @param view              待处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, final boolean isBanClick, final AnimationListener animationListener) {
        goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除 - view.setVisibility(View.GONE)
     * @param view              待处理的视图
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, final AnimationListener animationListener) {
        goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除 - view.setVisibility(View.GONE)
     * @param view       待处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void goneViewByAlpha(final View view, final boolean isBanClick) {
        goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除 - view.setVisibility(View.GONE)
     * @param view 待处理的视图
     */
    public static void goneViewByAlpha(final View view) {
        goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    // =

    /**
     * 将给定视图渐渐显示出来 - view.setVisibility(View.VISIBLE)
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void visibleViewByAlpha(final View view, final long durationMillis, final boolean isBanClick,
                                          final AnimationListener animationListener) {
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
        }
    }

    /**
     * 将给定视图渐渐显示出来 - view.setVisibility(View.VISIBLE)
     * @param view              待处理的视图
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     */
    public static void visibleViewByAlpha(final View view, final long durationMillis, final AnimationListener animationListener) {
        visibleViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来 - view.setVisibility(View.VISIBLE)
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void visibleViewByAlpha(final View view, final long durationMillis, final boolean isBanClick) {
        visibleViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐显示出来 - view.setVisibility(View.VISIBLE)
     * @param view           待处理的视图
     * @param durationMillis 动画持续时间
     */
    public static void visibleViewByAlpha(final View view, final long durationMillis) {
        visibleViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐显示出来 - view.setVisibility(View.VISIBLE)
     * @param view              待处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void visibleViewByAlpha(final View view, final boolean isBanClick, final AnimationListener animationListener) {
        visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来 - view.setVisibility(View.VISIBLE)
     * @param view              待处理的视图
     * @param animationListener 动画监听器
     */
    public static void visibleViewByAlpha(final View view, final AnimationListener animationListener) {
        visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来 - view.setVisibility(View.VISIBLE)
     * @param view       待处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void visibleViewByAlpha(final View view, final boolean isBanClick) {
        visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐显示出来 - view.setVisibility(View.VISIBLE)
     * @param view 待处理的视图
     */
    public static void visibleViewByAlpha(final View view) {
        visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    // ================
    // = 视图移动动画 =
    // ================

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
     */
    public static void translate(final View view, final float fromXDelta, final float toXDelta,
                                 final float fromYDelta, final float toYDelta, final Interpolator interpolator,
                                 final long durationMillis, final boolean isBanClick) {
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
        }
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
     */
    public static void translate(final View view, final float fromXDelta, final float toXDelta,
                                 final float fromYDelta, final float toYDelta, final float cycles, final long durationMillis, final boolean isBanClick) {
        if (view != null) {
            Interpolator interpolator = (cycles > 0.0f) ? new CycleInterpolator(cycles) : null;
            translate(view, fromXDelta, toXDelta, fromYDelta, toYDelta, interpolator, durationMillis, isBanClick);
        }
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
     */
    public static void translate(final View view, final float fromXDelta, final float toXDelta,
                                 final float fromYDelta, final float toYDelta, final float cycles, final long durationMillis) {
        translate(view, fromXDelta, toXDelta, fromYDelta, toYDelta, cycles, durationMillis, false);
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
     */
    public static void shake(final View view, final float fromXDelta, final float toXDelta,
                             final float cycles, final long durationMillis, final boolean isBanClick) {
        translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃
     * @param view           待摇晃的视图
     * @param fromXDelta     动画开始的 X 轴坐标
     * @param toXDelta       动画结束的 X 轴坐标
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     */
    public static void shake(final View view, final float fromXDelta, final float toXDelta, final float cycles, final long durationMillis) {
        translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles, durationMillis, false);
    }

    /**
     * 视图摇晃(摇晃幅度为 10、重复 7 次)
     * @param view           待摇晃的视图
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final float cycles, final long durationMillis, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃(摇晃幅度为 10、持续 700 毫秒)
     * @param view       待摇晃的视图
     * @param cycles     动画周期 {@link CycleInterpolator}
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final float cycles, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, 700, isBanClick);
    }

    /**
     * 视图摇晃(摇晃幅度为 10)
     * @param view           待摇晃的视图
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     */
    public static void shake(final View view, final float cycles, final long durationMillis) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis, false);
    }

    /**
     * 视图摇晃(摇晃幅度为 10、重复 7 次)
     * @param view           待摇晃的视图
     * @param durationMillis 动画持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final long durationMillis, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃(摇晃幅度为 10、持续 700 毫秒)
     * @param view   待摇晃的视图
     * @param cycles 动画周期 {@link CycleInterpolator}
     */
    public static void shake(final View view, final float cycles) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, 700, false);
    }

    /**
     * 视图摇晃(摇晃幅度为 10、重复 7 次)
     * @param view           待摇晃的视图
     * @param durationMillis 动画持续时间
     */
    public static void shake(final View view, final long durationMillis) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis, false);
    }

    // =

    /**
     * 视图摇晃(摇晃幅度为 10、重复 7 次、持续 700 毫秒)
     * @param view       待摇晃的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, 700, isBanClick);
    }

    /**
     * 视图摇晃(摇晃幅度为 10、重复 7 次、持续 700 毫秒)
     * @param view 待摇晃的视图
     */
    public static void shake(final View view) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, 700, false);
    }
}

