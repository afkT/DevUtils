package dev.utils.app.anim;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

/**
 * detail: 视图动画工具箱 (AnimationUtils 基础上封装) ，提供简单的控制视图的动画的工具方法
 * @author Ttt
 */
public final class ViewAnimationUtils {

    private ViewAnimationUtils() {
    }

    // ======================
    // = 视图透明度渐变动画 =
    // ======================

    /**
     * 将给定视图渐渐隐去 (view.setVisibility(View.INVISIBLE))
     * @param view              被处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param durationMillis    持续时间，毫秒
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, final long durationMillis, final boolean isBanClick, final AnimationListener animationListener) {
        if (view != null && view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
            AlphaAnimation hiddenAlphaAnimation = AnimationUtils.getHiddenAlphaAnimation(durationMillis);
            hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
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
            view.startAnimation(hiddenAlphaAnimation);
        }
    }

    /**
     * 将给定视图渐渐隐去(view.setVisibility(View.INVISIBLE))
     * @param view              被处理的视图
     * @param durationMillis    持续时间，毫秒
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, final long durationMillis, final AnimationListener animationListener) {
        invisibleViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去(view.setVisibility(View.INVISIBLE))
     * @param view           被处理的视图
     * @param durationMillis 持续时间，毫秒
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void invisibleViewByAlpha(final View view, final long durationMillis, final boolean isBanClick) {
        invisibleViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去(view.setVisibility(View.INVISIBLE))
     * @param view           被处理的视图
     * @param durationMillis 持续时间，毫秒
     */
    public static void invisibleViewByAlpha(final View view, final long durationMillis) {
        invisibleViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐隐去(view.setVisibility(View.INVISIBLE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view              被处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, final boolean isBanClick, final AnimationListener animationListener) {
        invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐隐去(view.setVisibility(View.INVISIBLE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view              被处理的视图
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, final AnimationListener animationListener) {
        invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去(view.setVisibility(View.INVISIBLE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view       被处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void invisibleViewByAlpha(final View view, final boolean isBanClick) {
        invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去(view.setVisibility(View.INVISIBLE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view 被处理的视图
     */
    public static void invisibleViewByAlpha(final View view) {
        invisibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除(view.setVisibility(View.GONE))
     * @param view              被处理的视图
     * @param durationMillis    持续时间，毫秒
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, final long durationMillis, final boolean isBanClick, final AnimationListener animationListener) {
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
            AlphaAnimation hiddenAlphaAnimation = AnimationUtils.getHiddenAlphaAnimation(durationMillis);
            hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
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
            view.startAnimation(hiddenAlphaAnimation);
        }
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除(view.setVisibility(View.GONE))
     * @param view              被处理的视图
     * @param durationMillis    持续时间，毫秒
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, final long durationMillis, final AnimationListener animationListener) {
        goneViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除(view.setVisibility(View.GONE))
     * @param view           被处理的视图
     * @param durationMillis 持续时间，毫秒
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void goneViewByAlpha(final View view, final long durationMillis, final boolean isBanClick) {
        goneViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除(view.setVisibility(View.GONE))
     * @param view           被处理的视图
     * @param durationMillis 持续时间，毫秒
     */
    public static void goneViewByAlpha(final View view, final long durationMillis) {
        goneViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除(view.setVisibility(View.GONE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view              被处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, final boolean isBanClick, final AnimationListener animationListener) {
        goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除(view.setVisibility(View.GONE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view              被处理的视图
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, final AnimationListener animationListener) {
        goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除(view.setVisibility(View.GONE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view       被处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void goneViewByAlpha(final View view, final boolean isBanClick) {
        goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除(view.setVisibility(View.GONE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view 被处理的视图
     */
    public static void goneViewByAlpha(final View view) {
        goneViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    /**
     * 将给定视图渐渐显示出来(view.setVisibility(View.VISIBLE))
     * @param view              被处理的视图
     * @param durationMillis    持续时间，毫秒
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void visibleViewByAlpha(final View view, final long durationMillis, final boolean isBanClick, final AnimationListener animationListener) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            AlphaAnimation showAlphaAnimation = AnimationUtils.getShowAlphaAnimation(durationMillis);
            showAlphaAnimation.setAnimationListener(new AnimationListener() {
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
            view.startAnimation(showAlphaAnimation);
        }
    }

    /**
     * 将给定视图渐渐显示出来(view.setVisibility(View.VISIBLE))
     * @param view              被处理的视图
     * @param durationMillis    持续时间，毫秒
     * @param animationListener 动画监听器
     */
    public static void visibleViewByAlpha(final View view, final long durationMillis, final AnimationListener animationListener) {
        visibleViewByAlpha(view, durationMillis, false, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来(view.setVisibility(View.VISIBLE))
     * @param view           被处理的视图
     * @param durationMillis 持续时间，毫秒
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void visibleViewByAlpha(final View view, final long durationMillis, final boolean isBanClick) {
        visibleViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * 将给定视图渐渐显示出来(view.setVisibility(View.VISIBLE))
     * @param view           被处理的视图
     * @param durationMillis 持续时间，毫秒
     */
    public static void visibleViewByAlpha(final View view, final long durationMillis) {
        visibleViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * 将给定视图渐渐显示出来(view.setVisibility(View.VISIBLE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view              被处理的视图
     * @param animationListener 动画监听器
     * @param isBanClick        在执行动画的过程中是否禁止点击
     */
    public static void visibleViewByAlpha(final View view, final boolean isBanClick, final AnimationListener animationListener) {
        visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来(view.setVisibility(View.VISIBLE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view              被处理的视图
     * @param animationListener 动画监听器
     */
    public static void visibleViewByAlpha(final View view, final AnimationListener animationListener) {
        visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, animationListener);
    }

    /**
     * 将给定视图渐渐显示出来(view.setVisibility(View.VISIBLE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view       被处理的视图
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void visibleViewByAlpha(final View view, final boolean isBanClick) {
        visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * 将给定视图渐渐显示出来(view.setVisibility(View.VISIBLE))，
     * 默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
     * @param view 被处理的视图
     */
    public static void visibleViewByAlpha(final View view) {
        visibleViewByAlpha(view, AnimationUtils.DEFAULT_ANIMATION_DURATION, false, null);
    }

    // ================
    // = 视图移动动画 =
    // ================

    /**
     * 视图移动
     * @param view           要移动的视图
     * @param fromXDelta     X轴开始坐标
     * @param toXDelta       X轴结束坐标
     * @param fromYDelta     Y轴开始坐标
     * @param toYDelta       Y轴结束坐标
     * @param cycles         重复
     * @param durationMillis 持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void translate(final View view, final float fromXDelta, final float toXDelta, final float fromYDelta, final float toYDelta,
                                 final float cycles, final long durationMillis, final boolean isBanClick) {
        if (view == null) return;
        TranslateAnimation translateAnimation = AnimationUtils.translate(fromXDelta, toXDelta, fromYDelta, toYDelta, cycles, durationMillis);
        translateAnimation.setAnimationListener(new AnimationListener() {
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
        view.startAnimation(translateAnimation);
    }

    /**
     * 视图移动
     * @param view           要移动的视图
     * @param fromXDelta     X轴开始坐标
     * @param toXDelta       X轴结束坐标
     * @param fromYDelta     Y轴开始坐标
     * @param toYDelta       Y轴结束坐标
     * @param cycles         重复
     * @param durationMillis 持续时间
     */
    public static void translate(final View view, final float fromXDelta, final float toXDelta, final float fromYDelta, final float toYDelta, final float cycles, final long durationMillis) {
        translate(view, fromXDelta, toXDelta, fromYDelta, toYDelta, cycles, durationMillis, false);
    }

    /**
     * 视图摇晃
     * @param view           要摇动的视图
     * @param fromXDelta     X轴开始坐标
     * @param toXDelta       X轴结束坐标
     * @param cycles         重复次数
     * @param durationMillis 持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final float fromXDelta, final float toXDelta, final float cycles, final long durationMillis, final boolean isBanClick) {
        translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃
     * @param view           要摇动的视图
     * @param fromXDelta     X轴开始坐标
     * @param toXDelta       X轴结束坐标
     * @param cycles         重复次数
     * @param durationMillis 持续时间
     */
    public static void shake(final View view, final float fromXDelta, final float toXDelta, final float cycles, final long durationMillis) {
        translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles, durationMillis, false);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，重复7次
     * @param view
     * @param cycles         重复次数
     * @param durationMillis 持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final float cycles, final long durationMillis, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，持续700毫秒
     * @param view
     * @param cycles     重复次数
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final float cycles, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, 700, isBanClick);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10
     * @param view
     * @param cycles         重复次数
     * @param durationMillis 持续时间
     */
    public static void shake(final View view, final float cycles, final long durationMillis) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis, false);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，重复7次
     * @param view
     * @param durationMillis 持续时间
     * @param isBanClick     在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final long durationMillis, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis, isBanClick);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，持续700毫秒
     * @param view   要摇动的视图
     * @param cycles 重复次数
     */
    public static void shake(final View view, final float cycles) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, 700, false);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，重复7次
     * @param view
     * @param durationMillis 持续时间
     */
    public static void shake(final View view, final long durationMillis) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis, false);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，重复7次，持续700毫秒
     * @param view
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void shake(final View view, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, 700, isBanClick);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，重复7次，持续700毫秒
     * @param view
     */
    public static void shake(final View view) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, 700, false);
    }
}

