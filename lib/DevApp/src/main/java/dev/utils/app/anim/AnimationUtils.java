package dev.utils.app.anim;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import dev.utils.LogPrintUtils;

/**
 * detail: 动画工具类
 * @author Ttt
 * <pre>
 *     Android 动画机制详细解读
 *     @see <a href="https://mp.weixin.qq.com/s/HQ0Z_RpSzbYzuqdJjw3FLQ"/>
 *     @see <a href="https://mp.weixin.qq.com/s/7EkXNY50jFmgAeusM-reZw"/>
 *     Android 一共有多少种动画, 准确告诉你
 *     @see <a href="https://www.jianshu.com/p/0eb89d43eea4"/>
 *     Android 中弹簧动画的那些事 - SpringAnimation
 *     @see <a href="https://www.jianshu.com/p/c2962a8135f5"/>
 *     Android 弹性动画的三种实现方式
 *     @see <a href="https://blog.csdn.net/qq_34902522/article/details/77651799"/>
 *     Android 动画
 *     @see <a href="https://blog.csdn.net/shibin1990_/article/list/2"/>
 *     Android 动效开篇
 *     @see <a href="https://blog.csdn.net/tianjian4592/article/details/44155147"/>
 *     <p></p>
 *     setInterpolator:
 *     {@link AccelerateInterpolator} 先减速后加速
 *     {@link AnticipateInterpolator} 动画开始之前有回弹效果
 *     {@link BounceInterpolator} 结束回弹效果
 *     {@link CycleInterpolator} 跳一跳效果
 *     {@link OvershootInterpolator} 动画结束时向前弹一定距离再回到原来位置
 *     {@link AccelerateDecelerateInterpolator} 系统默认的动画效果, 先加速后减速
 *     {@link AnticipateOvershootInterpolator} 开始之前向前甩, 结束的时候向后甩
 *     {@link DecelerateInterpolator} 开始加速再减速
 *     <p></p>
 *     设置无限重复次数
 *     {@link Animation#setRepeatCount} param {@link Animation#INFINITE}
 * </pre>
 */
public final class AnimationUtils {

    private AnimationUtils() {
    }

    // 日志 TAG
    private static final String TAG = AnimationUtils.class.getSimpleName();

    // 默认动画持续时间
    public static final long DEFAULT_ANIMATION_DURATION = 400;

    /**
     * 设置动画重复处理
     * @param anim        动画
     * @param repeatCount 执行次数
     * @param repeatMode  重复模式 {@link Animation#RESTART} 重新从头开始执行、{@link Animation#REVERSE} 反方向执行
     * @param <T>         泛型
     * @return 动画
     */
    public static <T extends Animation> T setAnimRepeat(
            final T anim,
            final int repeatCount,
            final int repeatMode
    ) {
        if (anim != null) {
            anim.setRepeatCount(repeatCount);
            anim.setRepeatMode(repeatMode);
        }
        return anim;
    }

    /**
     * 设置动画事件
     * @param animation {@link Animation}
     * @param listener  {@link AnimationListener}
     * @return {@link Animation}
     */
    public static Animation setAnimationListener(
            final Animation animation,
            final AnimationListener listener
    ) {
        if (animation != null) animation.setAnimationListener(listener);
        return animation;
    }

    /**
     * 设置动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return {@link View}
     */
    public static View setAnimation(
            final View view,
            final Animation animation
    ) {
        if (view != null) view.setAnimation(animation);
        return view;
    }

    /**
     * 获取动画
     * @param view {@link View}
     * @return {@link Animation}
     */
    public static Animation getAnimation(final View view) {
        return (view != null) ? view.getAnimation() : null;
    }

    /**
     * 清空动画
     * @param view {@link View}
     * @return {@link View}
     */
    public static View clearAnimation(final View view) {
        if (view != null) {
            try {
                view.clearAnimation();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "clearAnimation");
            }
        }
        return view;
    }

    /**
     * 启动动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return {@link View}
     */
    public static View startAnimation(
            final View view,
            final Animation animation
    ) {
        if (view != null && animation != null) {
            try {
                view.startAnimation(animation);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startAnimation");
            }
        }
        return view;
    }

    /**
     * 启动动画
     * @param animation {@link Animation}
     * @param <T>       泛型
     * @return {@link Animation}
     */
    public static <T extends Animation> T startAnimation(final T animation) {
        if (animation != null) {
            try {
                animation.start();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startAnimation");
            }
        }
        return animation;
    }

    /**
     * 取消动画
     * @param view {@link View}
     * @return {@link Animation}
     */
    public static Animation cancelAnimation(final View view) {
        return cancelAnimation(getAnimation(view));
    }

    /**
     * 取消动画
     * @param animation {@link Animation}
     * @param <T>       泛型
     * @return {@link Animation}
     */
    public static <T extends Animation> T cancelAnimation(final T animation) {
        if (animation != null) {
            try {
                animation.cancel();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "cancelAnimation");
            }
        }
        return animation;
    }

    // ===============
    // = 视图旋转动画 =
    // ===============

    /**
     * 获取一个旋转动画
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param pivotXType        旋转中心点 X 轴坐标相对类型
     * @param pivotXValue       旋转中心点 X 轴坐标
     * @param pivotYType        旋转中心点 Y 轴坐标相对类型
     * @param pivotYValue       旋转中心点 Y 轴坐标
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(
            final float fromDegrees,
            final float toDegrees,
            final int pivotXType,
            final float pivotXValue,
            final int pivotYType,
            final float pivotYValue,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }

    /**
     * 获取一个旋转动画
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param pivotX            旋转中心点 X 轴坐标
     * @param pivotY            旋转中心点 Y 轴坐标
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(
            final float fromDegrees,
            final float toDegrees,
            final float pivotX,
            final float pivotY,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        rotateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }

    /**
     * 获取一个旋转动画
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(
            final float fromDegrees,
            final float toDegrees,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees);
        rotateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }

    // =

    /**
     * 获取一个根据视图自身中心点旋转的动画
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f, durationMillis, animationListener);
    }

    /**
     * 获取一个根据视图自身中心点旋转的动画
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
     * 获取一个根据视图自身中心点旋转的动画
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter() {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION, null);
    }

    // ===============
    // = 视图渐变动画 =
    // ===============

    /**
     * 获取一个透明度渐变动画
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(
            final float fromAlpha,
            final float toAlpha,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
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
     * @param toAlpha        结束时的透明度
     * @param durationMillis 动画持续时间
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(
            final float fromAlpha,
            final float toAlpha,
            final long durationMillis
    ) {
        return getAlphaAnimation(fromAlpha, toAlpha, durationMillis, null);
    }

    /**
     * 获取一个透明度渐变动画
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(
            final float fromAlpha,
            final float toAlpha,
            final AnimationListener animationListener
    ) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个透明度渐变动画
     * @param fromAlpha 开始时的透明度
     * @param toAlpha   结束时的透明度
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(
            final float fromAlpha,
            final float toAlpha
    ) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION, null);
    }

    // =

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        return getAlphaAnimation(1.0f, 0.0f, durationMillis, animationListener);
    }

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     * @param durationMillis 动画持续时间
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(final long durationMillis) {
        return getHiddenAlphaAnimation(durationMillis, null);
    }

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(final AnimationListener animationListener) {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation() {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION, null);
    }

    // =

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, animationListener);
    }

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     * @param durationMillis 动画持续时间
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(final long durationMillis) {
        return getShowAlphaAnimation(durationMillis, null);
    }

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(final AnimationListener animationListener) {
        return getShowAlphaAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation() {
        return getShowAlphaAnimation(DEFAULT_ANIMATION_DURATION, null);
    }

    // ===============
    // = 视图缩放动画 =
    // ===============

    /**
     * 获取一个缩放动画
     * @param fromX             动画开始前在 X 坐标
     * @param toX               动画结束后在 X 坐标
     * @param fromY             动画开始前在 Y 坐标
     * @param toY               动画结束后在 Y 坐标
     * @param pivotXType        缩放中心点的 X 坐标类型, 取值范围为 {@link Animation#ABSOLUTE}、
     *                          {@link Animation#RELATIVE_TO_SELF}、{@link Animation#RELATIVE_TO_PARENT}
     * @param pivotXValue       缩放中心点的 X 坐标, 当 pivotXType == ABSOLUTE 时, 表示绝对位置, 否则表示相对位置, 1.0 表示 100%
     * @param pivotYType        缩放中心点的 Y 坐标类型
     * @param pivotYValue       缩放中心点的 Y 坐标
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个缩放动画
     */
    public static ScaleAnimation getScaleAnimation(
            final float fromX,
            final float toX,
            final float fromY,
            final float toY,
            final int pivotXType,
            final float pivotXValue,
            final int pivotYType,
            final float pivotYValue,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
        scaleAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            scaleAnimation.setAnimationListener(animationListener);
        }
        return scaleAnimation;
    }

    /**
     * 获取一个缩放动画
     * @param fromX             动画开始前在 X 坐标
     * @param toX               动画结束后在 X 坐标
     * @param fromY             动画开始前在 Y 坐标
     * @param toY               动画结束后在 Y 坐标
     * @param pivotX            缩放中心点的 X 坐标
     * @param pivotY            缩放中心点的 Y 坐标
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个缩放动画
     */
    public static ScaleAnimation getScaleAnimation(
            final float fromX,
            final float toX,
            final float fromY,
            final float toY,
            final float pivotX,
            final float pivotY,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY, pivotX, pivotY);
        scaleAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            scaleAnimation.setAnimationListener(animationListener);
        }
        return scaleAnimation;
    }

    /**
     * 获取一个缩放动画
     * @param fromX             动画开始前在 X 坐标
     * @param toX               动画结束后在 X 坐标
     * @param fromY             动画开始前在 Y 坐标
     * @param toY               动画结束后在 Y 坐标
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个缩放动画
     */
    public static ScaleAnimation getScaleAnimation(
            final float fromX,
            final float toX,
            final float fromY,
            final float toY,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY);
        scaleAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            scaleAnimation.setAnimationListener(animationListener);
        }
        return scaleAnimation;
    }

    // =

    /**
     * 获取一个中心点缩放动画
     * @param fromX             动画开始前在 X 坐标
     * @param toX               动画结束后在 X 坐标
     * @param fromY             动画开始前在 Y 坐标
     * @param toY               动画结束后在 Y 坐标
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个中心点缩放动画
     */
    public static ScaleAnimation getScaleAnimationCenter(
            final float fromX,
            final float toX,
            final float fromY,
            final float toY,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            scaleAnimation.setAnimationListener(animationListener);
        }
        return scaleAnimation;
    }

    /**
     * 获取一个中心点缩放动画
     * @param fromX          动画开始前在 X 坐标
     * @param toX            动画结束后在 X 坐标
     * @param fromY          动画开始前在 Y 坐标
     * @param toY            动画结束后在 Y 坐标
     * @param durationMillis 动画持续时间
     * @return 一个中心点缩放动画
     */
    public static ScaleAnimation getScaleAnimationCenter(
            final float fromX,
            final float toX,
            final float fromY,
            final float toY,
            final long durationMillis
    ) {
        return getScaleAnimationCenter(fromX, toX, fromY, toY, durationMillis, null);
    }

    /**
     * 获取一个中心点缩放动画
     * @param fromX             动画开始前在 X 坐标
     * @param toX               动画结束后在 X 坐标
     * @param fromY             动画开始前在 Y 坐标
     * @param toY               动画结束后在 Y 坐标
     * @param animationListener 动画监听器
     * @return 一个中心点缩放动画
     */
    public static ScaleAnimation getScaleAnimationCenter(
            final float fromX,
            final float toX,
            final float fromY,
            final float toY,
            final AnimationListener animationListener
    ) {
        return getScaleAnimationCenter(fromX, toX, fromY, toY, DEFAULT_ANIMATION_DURATION, animationListener);
    }

    // =

    /**
     * 获取一个中心点缩放动画
     * @param fromX             动画开始前在 X 坐标
     * @param fromY             动画开始前在 Y 坐标
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个中心点缩放动画
     */
    public static ScaleAnimation getScaleAnimationCenter(
            final float fromX,
            final float fromY,
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        return getScaleAnimationCenter(fromX, 1.0f, fromY, 1.0f, durationMillis, animationListener);
    }

    /**
     * 获取一个中心点缩放动画
     * @param fromX          动画开始前在 X 坐标
     * @param fromY          动画开始前在 Y 坐标
     * @param durationMillis 动画持续时间
     * @return 一个中心点缩放动画
     */
    public static ScaleAnimation getScaleAnimationCenter(
            final float fromX,
            final float fromY,
            final long durationMillis
    ) {
        return getScaleAnimationCenter(fromX, fromY, durationMillis, null);
    }

    /**
     * 获取一个中心点缩放动画
     * @param fromX             动画开始前在 X 坐标
     * @param fromY             动画开始前在 Y 坐标
     * @param animationListener 动画监听器
     * @return 一个中心点缩放动画
     */
    public static ScaleAnimation getScaleAnimationCenter(
            final float fromX,
            final float fromY,
            final AnimationListener animationListener
    ) {
        return getScaleAnimationCenter(fromX, fromY, DEFAULT_ANIMATION_DURATION, animationListener);
    }

    // =

    /**
     * 获取一个缩小动画
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                ScaleAnimation.RELATIVE_TO_SELF, ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            scaleAnimation.setAnimationListener(animationListener);
        }
        return scaleAnimation;
    }

    /**
     * 获取一个缩小动画
     * @param durationMillis 动画持续时间
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(final long durationMillis) {
        return getLessenScaleAnimation(durationMillis, null);
    }

    /**
     * 获取一个缩小动画
     * @param animationListener 动画监听器
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(final AnimationListener animationListener) {
        return getLessenScaleAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    // =

    /**
     * 获取一个放大动画
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个放大动画
     */
    public static ScaleAnimation getAmplificationAnimation(
            final long durationMillis,
            final AnimationListener animationListener
    ) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            scaleAnimation.setAnimationListener(animationListener);
        }
        return scaleAnimation;
    }

    /**
     * 获取一个放大动画
     * @param durationMillis 动画持续时间
     * @return 一个放大动画
     */
    public static ScaleAnimation getAmplificationAnimation(final long durationMillis) {
        return getAmplificationAnimation(durationMillis, null);
    }

    /**
     * 获取一个放大动画
     * @param animationListener 动画监听器
     * @return 一个放大动画
     */
    public static ScaleAnimation getAmplificationAnimation(final AnimationListener animationListener) {
        return getAmplificationAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    // ===============
    // = 视图移动动画 =
    // ===============

    /**
     * 获取一个视图移动动画
     * @param fromXType      动画开始前的 X 坐标类型, 取值范围为 {@link Animation#ABSOLUTE} 绝对位置、
     *                       {@link Animation#RELATIVE_TO_SELF} 以自身宽或高为参考、
     *                       {@link Animation#RELATIVE_TO_PARENT} 以父控件宽或高为参考
     * @param fromXValue     动画开始前的 X 坐标
     * @param toXType        动画结束后的 X 坐标类型
     * @param toXValue       动画结束后的 X 坐标
     * @param fromYType      动画开始前的 Y 坐标类型
     * @param fromYValue     动画开始前的 Y 坐标
     * @param toYType        动画结束后的 Y 坐标类型
     * @param toYValue       动画结束后的 Y 坐标
     * @param interpolator   动画周期
     * @param durationMillis 动画持续时间
     * @return 一个视图移动动画
     */
    public static TranslateAnimation getTranslateAnimation(
            final int fromXType,
            final float fromXValue,
            final int toXType,
            final float toXValue,
            final int fromYType,
            final float fromYValue,
            final int toYType,
            final float toYValue,
            final Interpolator interpolator,
            final long durationMillis
    ) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
        translateAnimation.setDuration(durationMillis);
        if (interpolator != null) {
            translateAnimation.setInterpolator(interpolator);
        }
        return translateAnimation;
    }

    /**
     * 获取一个视图移动动画
     * @param fromXDelta     动画开始的 X 轴坐标
     * @param toXDelta       动画结束的 X 轴坐标
     * @param fromYDelta     动画开始的 Y 轴坐标
     * @param toYDelta       动画结束的 Y 轴坐标
     * @param interpolator   动画周期
     * @param durationMillis 动画持续时间
     * @return 一个视图移动动画
     */
    public static TranslateAnimation getTranslateAnimation(
            final float fromXDelta,
            final float toXDelta,
            final float fromYDelta,
            final float toYDelta,
            final Interpolator interpolator,
            final long durationMillis
    ) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translateAnimation.setDuration(durationMillis);
        if (interpolator != null) {
            translateAnimation.setInterpolator(interpolator);
        }
        return translateAnimation;
    }

    // =

    /**
     * 获取一个视图摇晃动画
     * @param fromXDelta     动画开始的 X 轴坐标
     * @param toXDelta       动画结束的 X 轴坐标
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @return 一个视图摇晃动画
     */
    public static TranslateAnimation getShakeAnimation(
            final float fromXDelta,
            final float toXDelta,
            final float cycles,
            final long durationMillis
    ) {
        Interpolator interpolator = (cycles > 0.0f) ? new CycleInterpolator(cycles) : null;
        return getTranslateAnimation(fromXDelta, toXDelta, 0.0f, 0.0f, interpolator, durationMillis);
    }

    /**
     * 获取一个视图摇晃动画 ( 摇晃幅度为 10)
     * @param cycles         动画周期 {@link CycleInterpolator}
     * @param durationMillis 动画持续时间
     * @return 一个视图摇晃动画
     */
    public static TranslateAnimation getShakeAnimation(
            final float cycles,
            final long durationMillis
    ) {
        Interpolator interpolator = (cycles > 0.0f) ? new CycleInterpolator(cycles) : null;
        return getTranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f, interpolator, durationMillis);
    }

    /**
     * 获取一个视图摇晃动画 ( 摇晃幅度为 10、持续 700 毫秒 )
     * @param cycles 动画周期 {@link CycleInterpolator}
     * @return 一个视图摇晃动画
     */
    public static TranslateAnimation getShakeAnimation(final float cycles) {
        Interpolator interpolator = (cycles > 0.0f) ? new CycleInterpolator(cycles) : null;
        return getTranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f, interpolator, 700);
    }

    /**
     * 获取一个视图摇晃动画 ( 摇晃幅度为 10、重复 7 次 )
     * @param durationMillis 动画持续时间
     * @return 一个视图摇晃动画
     */
    public static TranslateAnimation getShakeAnimation(final long durationMillis) {
        return getTranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f, new CycleInterpolator(7), durationMillis);
    }

    /**
     * 获取一个视图摇晃动画 ( 摇晃幅度为 10、重复 7 次、持续 700 毫秒 )
     * @return 一个视图摇晃动画
     */
    public static TranslateAnimation getShakeAnimation() {
        return getTranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f, new CycleInterpolator(7), 700);
    }
}