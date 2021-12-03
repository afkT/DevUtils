package dev.utils.app.helper.dev;

import android.graphics.Bitmap;
import android.view.animation.Animation;

import dev.utils.app.helper.IHelper;
import dev.utils.app.timer.DevTimer;

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

    // ===============
    // = BitmapUtils =
    // ===============

    /**
     * Bitmap 通知回收
     * @param bitmaps Bitmap[]
     * @return Helper
     */
    T recycle(Bitmap... bitmaps);

    // ================
    // = TimerManager =
    // ================

    /**
     * 运行定时器
     * @param timers DevTimer[]
     * @return Helper
     */
    T startTimer(DevTimer... timers);

    /**
     * 关闭定时器
     * @param timers DevTimer[]
     * @return Helper
     */
    T stopTimer(DevTimer... timers);

    /**
     * 回收定时器资源
     * @return Helper
     */
    T recycleTimer();

    /**
     * 关闭全部定时器
     * @return Helper
     */
    T closeAllTimer();

    /**
     * 关闭所有未运行的定时器
     * @return Helper
     */
    T closeAllNotRunningTimer();

    /**
     * 关闭所有无限循环的定时器
     * @return Helper
     */
    T closeAllInfiniteTimer();

    /**
     * 关闭所有对应 TAG 定时器
     * @param tags 判断 {@link DevTimer#getTag()}
     * @return Helper
     */
    T closeAllTagTimer(String... tags);

    /**
     * 关闭所有对应 UUID 定时器
     * @param uuids 判断 {@link DevTimer#getUUID()}
     * @return Helper
     */
    T closeAllUUIDTimer(int... uuids);
}