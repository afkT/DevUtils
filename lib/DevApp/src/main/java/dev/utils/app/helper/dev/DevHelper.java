package dev.utils.app.helper.dev;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.IntRange;
import androidx.annotation.RawRes;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.DialogFragment;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Locale;

import dev.utils.app.CleanUtils;
import dev.utils.app.ClickUtils;
import dev.utils.app.ClipboardUtils;
import dev.utils.app.DialogUtils;
import dev.utils.app.EditTextUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.KeyBoardUtils;
import dev.utils.app.LanguageUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.MediaStoreUtils;
import dev.utils.app.NotificationUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ScreenUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.VibrationUtils;
import dev.utils.app.anim.AnimationUtils;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.app.helper.view.ViewHelper;
import dev.utils.app.image.BitmapUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.app.timer.DevTimer;
import dev.utils.common.CloseUtils;
import dev.utils.common.ForUtils;
import dev.utils.common.HttpURLConnectionUtils;
import dev.utils.common.assist.TimeKeeper;
import dev.utils.common.assist.record.FileRecordUtils;
import dev.utils.common.assist.record.RecordConfig;

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
}