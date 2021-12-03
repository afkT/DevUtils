package dev.utils.app.helper.dev;

import android.view.animation.Animation;

import dev.utils.app.helper.IHelper;

/**
 * detail: DevHelper 接口
 * @author Ttt
 */
public interface IHelperByDev<T>
        extends IHelper<T> {

    // ==================
    // = AnimationUtils =
    // ==================

    /**
     * 设置动画重复处理
     * @param repeatCount 执行次数
     * @param repeatMode  重复模式 {@link Animation#RESTART} 重新从头开始执行、{@link Animation#REVERSE} 反方向执行
     * @param animations  Animation[]
     * @return Helper
     */
    T setAnimationRepeat(
            int repeatCount,
            int repeatMode,
            Animation... animations
    );

    /**
     * 设置动画事件
     * @param listener   {@link Animation.AnimationListener}
     * @param animations Animation[]
     * @return Helper
     */
    T setAnimationListener(
            Animation.AnimationListener listener,
            Animation... animations
    );

    /**
     * 启动动画
     * @param animations Animation[]
     * @return Helper
     */
    T startAnimation(Animation... animations);

    /**
     * 取消动画
     * @param animations Animation[]
     * @return Helper
     */
    T cancelAnimation(Animation... animations);
}