package dev.utils.app.anim;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * detail: 动画工具类
 * @author Ttt
 */
public final class AnimationUtils {

    private AnimationUtils() {
    }

    // 默认动画持续时间
    public static final long DEFAULT_ANIMATION_DURATION = 400;

    /**
     * 获取一个旋转动画
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param pivotXType        旋转中心点X轴坐标相对类型
     * @param pivotXValue       旋转中心点X轴坐标
     * @param pivotYType        旋转中心点Y轴坐标相对类型
     * @param pivotYValue       旋转中心点Y轴坐标
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(final float fromDegrees, final float toDegrees, final int pivotXType, final float pivotXValue,
                                                     final int pivotYType, final float pivotYValue, final long durationMillis, final AnimationListener animationListener) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }

    /**
     * 获取一个根据视图自身中心点旋转的动画
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(final long durationMillis, final AnimationListener animationListener) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, durationMillis, animationListener);
    }

    /**
     * 获取一个根据中心点旋转的动画
     * @param duration 动画持续时间
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(final long duration) {
        return getRotateAnimationByCenter(duration, null);
    }

    /**
     * 获取一个根据视图自身中心点旋转的动画
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(final AnimationListener animationListener) {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个根据中心点旋转的动画
     * @return 一个根据中心点旋转的动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static RotateAnimation getRotateAnimationByCenter() {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * 获取一个透明度渐变动画
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(final float fromAlpha, final float toAlpha, final long durationMillis, final AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            alphaAnimation.setAnimationListener(animationListener);
        }
        return alphaAnimation;
    }

    /**
     * 获取一个透明度渐变动画
     * @param fromAlpha      开始时的透明度
     * @param toAlpha        结束时的透明度都
     * @param durationMillis 持续时间
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(final float fromAlpha, final float toAlpha, final long durationMillis) {
        return getAlphaAnimation(fromAlpha, toAlpha, durationMillis, null);
    }

    /**
     * 获取一个透明度渐变动画
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getAlphaAnimation(final float fromAlpha, final float toAlpha, final AnimationListener animationListener) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个透明度渐变动画
     * @param fromAlpha 开始时的透明度
     * @param toAlpha   结束时的透明度都
     * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getAlphaAnimation(final float fromAlpha, final float toAlpha) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(final long durationMillis, final AnimationListener animationListener) {
        return getAlphaAnimation(1.0f, 0.0f, durationMillis, animationListener);
    }

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     * @param durationMillis 持续时间
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(final long durationMillis) {
        return getHiddenAlphaAnimation(durationMillis, null);
    }

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getHiddenAlphaAnimation(final AnimationListener animationListener) {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getHiddenAlphaAnimation() {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(final long durationMillis, final AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, animationListener);
    }

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     * @param durationMillis 持续时间
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(final long durationMillis) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, null);
    }

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getShowAlphaAnimation(final AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getShowAlphaAnimation() {
        return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * 获取一个缩小动画
     * @param durationMillis
     * @param animationListener
     * @return 一个缩放动画
     */
    public static ScaleAnimation getLessenScaleAnimation(final long durationMillis, final AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, ScaleAnimation.RELATIVE_TO_SELF, ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }

    /**
     * 获取一个缩小动画
     * @param durationMillis
     * @return 一个缩放动画
     */
    public static ScaleAnimation getLessenScaleAnimation(final long durationMillis) {
        return getLessenScaleAnimation(durationMillis, null);
    }

    /**
     * 获取一个缩小动画
     * @param animationListener
     * @return 一个缩放动画
     */
    public static ScaleAnimation getLessenScaleAnimation(final AnimationListener animationListener) {
        return getLessenScaleAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个放大动画
     * @param durationMillis
     * @param animationListener
     * @return 一个缩放动画
     */
    public static ScaleAnimation getAmplificationAnimation(final long durationMillis, final AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, ScaleAnimation.RELATIVE_TO_SELF, ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }

    /**
     * 获取一个放大动画
     * @param durationMillis
     * @return 一个缩放动画
     */
    public static ScaleAnimation getAmplificationAnimation(final long durationMillis) {
        return getAmplificationAnimation(durationMillis, null);
    }

    /**
     * 获取一个放大动画
     * @param animationListener
     * @return 一个缩放动画
     */
    public static ScaleAnimation getAmplificationAnimation(final AnimationListener animationListener) {
        return getAmplificationAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    // ================
    // = 视图移动动画 =
    // ================

    /**
     * 视图移动
     * @param fromXDelta     X轴开始坐标
     * @param toXDelta       X轴结束坐标
     * @param fromYDelta     Y轴开始坐标
     * @param toYDelta       Y轴结束坐标
     * @param cycles         重复
     * @param durationMillis 持续时间
     * @return 一个平移动画
     */
    public static TranslateAnimation translate(final float fromXDelta, final float toXDelta, final float fromYDelta, final float toYDelta, final float cycles, final long durationMillis) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translateAnimation.setDuration(durationMillis);
        if (cycles > 0.0) {
            translateAnimation.setInterpolator(new CycleInterpolator(cycles));
        }
        return translateAnimation;
    }

    /**
     * 视图摇晃
     * @param fromXDelta     X轴开始坐标
     * @param toXDelta       X轴结束坐标
     * @param cycles         重复次数
     * @param durationMillis 持续时间
     * @return 一个平移动画
     */
    public static TranslateAnimation shake(final float fromXDelta, final float toXDelta, final float cycles, final long durationMillis) {
        return translate(fromXDelta, toXDelta, 0.0f, 0.0f, cycles, durationMillis);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10
     * @param cycles         重复次数
     * @param durationMillis 持续时间
     * @return 一个平移动画
     */
    public static TranslateAnimation shake(final float cycles, final long durationMillis) {
        return translate(0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，持续700毫秒
     * @param cycles 重复次数
     * @return 一个平移动画
     */
    public static TranslateAnimation shake(final float cycles) {
        return translate(0.0f, 10.0f, 0.0f, 0.0f, cycles, 700);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，重复7次
     * @param durationMillis 持续时间
     * @return 一个平移动画
     */
    public static TranslateAnimation shake(final long durationMillis) {
        return translate(0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis);
    }

    /**
     * 视图摇晃，默认摇晃幅度为10，重复7次，持续700毫秒
     * @return 一个平移动画
     */
    public static TranslateAnimation shake() {
        return translate(0.0f, 10.0f, 0.0f, 0.0f, 7, 700);
    }
}
