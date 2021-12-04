package dev.utils.app.helper.dev;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;

import dev.utils.app.ClickUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.anim.AnimationUtils;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.app.helper.view.ViewHelper;
import dev.utils.app.image.BitmapUtils;
import dev.utils.app.timer.DevTimer;
import dev.utils.app.timer.TimerManager;
import dev.utils.common.ForUtils;
import dev.utils.common.assist.TimeKeeper;

/**
 * detail: Dev 工具类链式调用 Helper 类
 * @author Ttt
 * <pre>
 *     通过 DevApp 工具类快捷实现
 *     <p></p>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 * </pre>
 */
public final class DevHelper
        implements IHelperByDev<DevHelper> {

    private DevHelper() {
    }

    // TimeKeeper
    private final        TimeKeeper mTimeKeeper = new TimeKeeper();
    // DevHelper
    private static final DevHelper  HELPER      = new DevHelper();

    /**
     * 获取单例 DevHelper
     * @return {@link DevHelper}
     */
    public static DevHelper get() {
        return HELPER;
    }

    // ===========
    // = IHelper =
    // ===========

    /**
     * 获取 DevHelper
     * @return {@link DevHelper}
     */
    @Override
    public DevHelper devHelper() {
        return DevHelper.get();
    }

    /**
     * 获取 QuickHelper
     * @param target 目标 View
     * @return {@link QuickHelper}
     */
    @Override
    public QuickHelper quickHelper(View target) {
        return QuickHelper.get(target);
    }

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    @Override
    public ViewHelper viewHelper() {
        return ViewHelper.get();
    }

    // ================
    // = HandlerUtils =
    // ================

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     * @return Helper
     */
    @Override
    public DevHelper postRunnable(Runnable runnable) {
        HandlerUtils.postRunnable(runnable);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @return Helper
     */
    @Override
    public DevHelper postRunnable(
            Runnable runnable,
            long delayMillis
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @return Helper
     */
    @Override
    public DevHelper postRunnable(
            Runnable runnable,
            long delayMillis,
            int number,
            int interval
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @param listener    结束通知
     * @return Helper
     */
    @Override
    public DevHelper postRunnable(
            Runnable runnable,
            long delayMillis,
            int number,
            int interval,
            HandlerUtils.OnEndListener listener
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval, listener);
        return this;
    }

    /**
     * 在主线程 Handler 中清除任务
     * @param runnable 需要清除的任务
     * @return Helper
     */
    @Override
    public DevHelper removeRunnable(Runnable runnable) {
        HandlerUtils.removeRunnable(runnable);
        return this;
    }

    // ================
    // = IHelperByDev =
    // ================

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
    @Override
    public DevHelper setAnimationRepeat(
            int repeatCount,
            int repeatMode,
            Animation... animations
    ) {
        ForUtils.forSimpleArgs(
                value -> AnimationUtils.setAnimationRepeat(
                        value, repeatCount, repeatMode
                ), animations
        );
        return this;
    }

    /**
     * 设置动画事件
     * @param listener   {@link Animation.AnimationListener}
     * @param animations Animation[]
     * @return Helper
     */
    @Override
    public DevHelper setAnimationListener(
            Animation.AnimationListener listener,
            Animation... animations
    ) {
        ForUtils.forSimpleArgs(
                value -> AnimationUtils.setAnimationListener(
                        value, listener
                ), animations
        );
        return this;
    }

    /**
     * 启动动画
     * @param animations Animation[]
     * @return Helper
     */
    @Override
    public DevHelper startAnimation(Animation... animations) {
        ForUtils.forSimpleArgs(
                value -> AnimationUtils.startAnimation(value), animations
        );
        return this;
    }

    /**
     * 取消动画
     * @param animations Animation[]
     * @return Helper
     */
    @Override
    public DevHelper cancelAnimation(Animation... animations) {
        ForUtils.forSimpleArgs(
                value -> AnimationUtils.cancelAnimation(value), animations
        );
        return this;
    }

    // ===============
    // = BitmapUtils =
    // ===============

    /**
     * Bitmap 通知回收
     * @param bitmaps Bitmap[]
     * @return Helper
     */
    @Override
    public DevHelper recycle(Bitmap... bitmaps) {
        ForUtils.forSimpleArgs(
                value -> BitmapUtils.recycle(value), bitmaps
        );
        return this;
    }

    // ================
    // = TimerManager =
    // ================

    /**
     * 运行定时器
     * @param timers DevTimer[]
     * @return Helper
     */
    @Override
    public DevHelper startTimer(DevTimer... timers) {
        ForUtils.forSimpleArgs(
                value -> TimerManager.startTimer(value), timers
        );
        return this;
    }

    /**
     * 关闭定时器
     * @param timers DevTimer[]
     * @return Helper
     */
    @Override
    public DevHelper stopTimer(DevTimer... timers) {
        ForUtils.forSimpleArgs(
                value -> TimerManager.stopTimer(value), timers
        );
        return this;
    }

    /**
     * 回收定时器资源
     * @return Helper
     */
    @Override
    public DevHelper recycleTimer() {
        TimerManager.recycle();
        return this;
    }

    /**
     * 关闭全部定时器
     * @return Helper
     */
    @Override
    public DevHelper closeAllTimer() {
        TimerManager.closeAll();
        return this;
    }

    /**
     * 关闭所有未运行的定时器
     * @return Helper
     */
    @Override
    public DevHelper closeAllNotRunningTimer() {
        TimerManager.closeAllNotRunning();
        return this;
    }

    /**
     * 关闭所有无限循环的定时器
     * @return Helper
     */
    @Override
    public DevHelper closeAllInfiniteTimer() {
        TimerManager.closeAllInfinite();
        return this;
    }

    /**
     * 关闭所有对应 TAG 定时器
     * @param tags 判断 {@link DevTimer#getTag()}
     * @return Helper
     */
    @Override
    public DevHelper closeAllTagTimer(String... tags) {
        ForUtils.forSimpleArgs(
                value -> TimerManager.closeAllTag(value), tags
        );
        return this;
    }

    /**
     * 关闭所有对应 UUID 定时器
     * @param uuids 判断 {@link DevTimer#getUUID()}
     * @return Helper
     */
    @Override
    public DevHelper closeAllUUIDTimer(int... uuids) {
        ForUtils.forInts(
                (index, value) -> TimerManager.closeAllUUID(value), uuids
        );
        return this;
    }

    // ==============
    // = ClickUtils =
    // ==============

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param range 点击范围
     * @param views View[]
     * @return Helper
     */
    @Override
    public DevHelper addTouchArea(
            int range,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.addTouchArea(value, range), views
        );
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param left   left range
     * @param top    top range
     * @param right  right range
     * @param bottom bottom range
     * @param views  View[]
     * @return Helper
     */
    @Override
    public DevHelper addTouchArea(
            int left,
            int top,
            int right,
            int bottom,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.addTouchArea(value, left, top, right, bottom), views
        );
        return this;
    }

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public DevHelper setOnClick(
            View.OnClickListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnClick(value, listener), views
        );
        return this;
    }

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public DevHelper setOnLongClick(
            View.OnLongClickListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnLongClick(value, listener), views
        );
        return this;
    }

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public DevHelper setOnTouch(
            View.OnTouchListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnTouch(value, listener), views
        );
        return this;
    }

    // ==================
    // = ClipboardUtils =
    // ==================
}