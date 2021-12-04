package dev.utils.app.helper.dev;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.DialogFragment;

import java.io.Closeable;
import java.io.Flushable;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Locale;

import dev.utils.app.ClickUtils;
import dev.utils.app.ClipboardUtils;
import dev.utils.app.DialogUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.KeyBoardUtils;
import dev.utils.app.LanguageUtils;
import dev.utils.app.NotificationUtils;
import dev.utils.app.PhoneUtils;
import dev.utils.app.PowerManagerUtils;
import dev.utils.app.ScreenUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.VibrationUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.WidgetUtils;
import dev.utils.app.anim.AnimationUtils;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.app.helper.view.ViewHelper;
import dev.utils.app.image.BitmapUtils;
import dev.utils.app.timer.DevTimer;
import dev.utils.app.timer.TimerManager;
import dev.utils.common.CloseUtils;
import dev.utils.common.ForUtils;

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

    // DevHelper
    private static final DevHelper HELPER = new DevHelper();

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

    /**
     * 复制文本到剪贴板
     * @param text 文本
     * @return Helper
     */
    @Override
    public DevHelper copyText(CharSequence text) {
        ClipboardUtils.copyText(text);
        return this;
    }

    /**
     * 复制 URI 到剪贴板
     * @param uri {@link Uri}
     * @return Helper
     */
    @Override
    public DevHelper copyUri(Uri uri) {
        ClipboardUtils.copyUri(uri);
        return this;
    }

    /**
     * 复制意图到剪贴板
     * @param intent {@link Intent}
     * @return Helper
     */
    @Override
    public DevHelper copyIntent(Intent intent) {
        ClipboardUtils.copyIntent(intent);
        return this;
    }

    // ===============
    // = DialogUtils =
    // ===============

    /**
     * 设置 Dialog 状态栏颜色
     * @param dialog {@link Dialog}
     * @param color  Dialog StatusBar Color
     * @return Helper
     */
    @Override
    public DevHelper setDialogStatusBarColor(
            Dialog dialog,
            @ColorInt int color
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DialogUtils.setStatusBarColor(
                    dialog, color
            );
        }
        return this;
    }

    /**
     * 设置 Dialog 高版本状态栏蒙层
     * @param dialog {@link Dialog}
     * @param color  Dialog StatusBar Color
     * @return Helper
     */
    @Override
    public DevHelper setDialogSemiTransparentStatusBarColor(
            Dialog dialog,
            @ColorInt int color
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DialogUtils.setSemiTransparentStatusBarColor(
                    dialog, color
            );
        }
        return this;
    }

    /**
     * 设置 Dialog 状态栏颜色、高版本状态栏蒙层
     * @param dialog   {@link Dialog}
     * @param color    Dialog StatusBar Color
     * @param addFlags 是否添加 Windows flags
     * @return Helper
     */
    @Override
    public DevHelper setDialogStatusBarColor2(
            Dialog dialog,
            @ColorInt int color,
            boolean addFlags
    ) {
        DialogUtils.setStatusBarColor2(
                dialog, color, addFlags
        );
        return this;
    }

    /**
     * 设置 Dialog Window LayoutParams
     * @param dialog {@link Dialog}
     * @param params {@link WindowManager.LayoutParams}
     * @return Helper
     */
    @Override
    public DevHelper setDialogAttributes(
            Dialog dialog,
            WindowManager.LayoutParams params
    ) {
        DialogUtils.setAttributes(
                dialog, params
        );
        return this;
    }

    /**
     * 设置 Dialog 宽度
     * @param dialog {@link Dialog}
     * @param width  宽度
     * @return Helper
     */
    @Override
    public DevHelper setDialogWidth(
            Dialog dialog,
            int width
    ) {
        DialogUtils.setWidth(
                dialog, width
        );
        return this;
    }

    /**
     * 设置 Dialog 高度
     * @param dialog {@link Dialog}
     * @param height 高度
     * @return Helper
     */
    @Override
    public DevHelper setDialogHeight(
            Dialog dialog,
            int height
    ) {
        DialogUtils.setHeight(
                dialog, height
        );
        return this;
    }

    /**
     * 设置 Dialog 宽度、高度
     * @param dialog {@link Dialog}
     * @param width  宽度
     * @param height 高度
     * @return Helper
     */
    @Override
    public DevHelper setDialogWidthHeight(
            Dialog dialog,
            int width,
            int height
    ) {
        DialogUtils.setWidthHeight(
                dialog, width, height
        );
        return this;
    }

    /**
     * 设置 Dialog X 轴坐标
     * @param dialog {@link Dialog}
     * @param x      X 轴坐标
     * @return Helper
     */
    @Override
    public DevHelper setDialogX(
            Dialog dialog,
            int x
    ) {
        DialogUtils.setX(
                dialog, x
        );
        return this;
    }

    /**
     * 设置 Dialog Y 轴坐标
     * @param dialog {@link Dialog}
     * @param y      Y 轴坐标
     * @return Helper
     */
    @Override
    public DevHelper setDialogY(
            Dialog dialog,
            int y
    ) {
        DialogUtils.setY(
                dialog, y
        );
        return this;
    }

    /**
     * 设置 Dialog X、Y 轴坐标
     * @param dialog {@link Dialog}
     * @param x      X 轴坐标
     * @param y      Y 轴坐标
     * @return Helper
     */
    @Override
    public DevHelper setDialogXY(
            Dialog dialog,
            int x,
            int y
    ) {
        DialogUtils.setXY(
                dialog, x, y
        );
        return this;
    }

    /**
     * 设置 Dialog Gravity
     * @param dialog  {@link Dialog}
     * @param gravity 重心
     * @return Helper
     */
    @Override
    public DevHelper setDialogGravity(
            Dialog dialog,
            int gravity
    ) {
        DialogUtils.setGravity(
                dialog, gravity
        );
        return this;
    }

    /**
     * 设置 Dialog 透明度
     * @param dialog    {@link Dialog}
     * @param dimAmount 透明度
     * @return Helper
     */
    @Override
    public DevHelper setDialogDimAmount(
            Dialog dialog,
            float dimAmount
    ) {
        DialogUtils.setDimAmount(
                dialog, dimAmount
        );
        return this;
    }

    /**
     * 设置是否允许返回键关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @return Helper
     */
    @Override
    public DevHelper setDialogCancelable(
            Dialog dialog,
            boolean cancel
    ) {
        DialogUtils.setCancelable(
                dialog, cancel
        );
        return this;
    }

    /**
     * 设置是否允许点击其他地方自动关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @return Helper
     */
    @Override
    public DevHelper setDialogCanceledOnTouchOutside(
            Dialog dialog,
            boolean cancel
    ) {
        DialogUtils.setCanceledOnTouchOutside(
                dialog, cancel
        );
        return this;
    }

    /**
     * 设置是否允许 返回键关闭、点击其他地方自动关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @return Helper
     */
    @Override
    public DevHelper setDialogCancelableAndTouchOutside(
            Dialog dialog,
            boolean cancel
    ) {
        DialogUtils.setCancelableAndTouchOutside(
                dialog, cancel
        );
        return this;
    }

    // ==============
    // = Dialog 操作 =
    // ==============

    /**
     * 显示 Dialog
     * @param dialog {@link Dialog}
     * @return Helper
     */
    @Override
    public DevHelper showDialog(Dialog dialog) {
        DialogUtils.showDialog(dialog);
        return this;
    }

    /**
     * 关闭多个 Dialog
     * @param dialogs Dialog[]
     * @return Helper
     */
    @Override
    public DevHelper closeDialogs(Dialog... dialogs) {
        DialogUtils.closeDialogs(dialogs);
        return this;
    }

    /**
     * 关闭多个 DialogFragment
     * @param dialogs DialogFragment[]
     * @return Helper
     */
    @Override
    public DevHelper closeDialogs(DialogFragment... dialogs) {
        DialogUtils.closeDialogs(dialogs);
        return this;
    }

    /**
     * 关闭多个 PopupWindow
     * @param popupWindows PopupWindow[]
     * @return Helper
     */
    @Override
    public DevHelper closePopupWindows(PopupWindow... popupWindows) {
        DialogUtils.closePopupWindows(popupWindows);
        return this;
    }

    /**
     * 自动关闭 dialog
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param dialogs     Dialog[]
     * @return Helper
     */
    @Override
    public DevHelper autoCloseDialog(
            long delayMillis,
            Handler handler,
            Dialog... dialogs
    ) {
        ForUtils.forSimpleArgs(
                value -> DialogUtils.autoCloseDialog(
                        value, delayMillis, handler
                ), dialogs
        );
        return this;
    }

    /**
     * 自动关闭 DialogFragment
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param dialogs     DialogFragment[]
     * @return Helper
     */
    @Override
    public DevHelper autoCloseDialog(
            long delayMillis,
            Handler handler,
            DialogFragment... dialogs
    ) {
        ForUtils.forSimpleArgs(
                value -> DialogUtils.autoCloseDialog(
                        value, delayMillis, handler
                ), dialogs
        );
        return this;
    }

    /**
     * 自动关闭 PopupWindow
     * @param delayMillis  延迟关闭时间
     * @param handler      {@link Handler}
     * @param popupWindows PopupWindow[]
     * @return Helper
     */
    @Override
    public DevHelper autoClosePopupWindow(
            long delayMillis,
            Handler handler,
            PopupWindow... popupWindows
    ) {
        ForUtils.forSimpleArgs(
                value -> DialogUtils.autoClosePopupWindow(
                        value, delayMillis, handler
                ), popupWindows
        );
        return this;
    }

    // =================
    // = KeyBoardUtils =
    // =================

    /**
     * 设置 Window 软键盘是否显示
     * @param activity     {@link Activity}
     * @param inputVisible 是否显示软键盘
     * @return Helper
     */
    @Override
    public DevHelper setSoftInputMode(
            Activity activity,
            boolean inputVisible
    ) {
        KeyBoardUtils.setSoftInputMode(
                activity, inputVisible
        );
        return this;
    }

    /**
     * 设置 Window 软键盘是否显示
     * @param window       {@link Window}
     * @param inputVisible 是否显示软键盘
     * @return Helper
     */
    @Override
    public DevHelper setSoftInputMode(
            Window window,
            boolean inputVisible
    ) {
        KeyBoardUtils.setSoftInputMode(
                window, inputVisible
        );
        return this;
    }

    /**
     * 设置 Window 软键盘是否显示
     * @param activity     {@link Activity}
     * @param inputVisible 是否显示软键盘
     * @param clearFlag    是否清空 Flag ( FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_FOCUSABLE )
     * @return Helper
     */
    @Override
    public DevHelper setSoftInputMode(
            Activity activity,
            boolean inputVisible,
            boolean clearFlag
    ) {
        KeyBoardUtils.setSoftInputMode(
                activity, inputVisible, clearFlag
        );
        return this;
    }

    /**
     * 设置 Window 软键盘是否显示
     * @param window       {@link Window}
     * @param inputVisible 是否显示软键盘
     * @param clearFlag    是否清空 Flag ( FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_FOCUSABLE )
     * @return Helper
     */
    @Override
    public DevHelper setSoftInputMode(
            Window window,
            boolean inputVisible,
            boolean clearFlag
    ) {
        KeyBoardUtils.setSoftInputMode(
                window, inputVisible, clearFlag
        );
        return this;
    }

    // ============================
    // = 点击非 EditText 则隐藏软键盘 =
    // ============================

    /**
     * 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件
     * @param view     {@link View}
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper judgeView(
            View view,
            Activity activity
    ) {
        KeyBoardUtils.judgeView(
                view, activity
        );
        return this;
    }

    // ===============
    // = 软键盘隐藏显示 =
    // ===============

    /**
     * 注册软键盘改变监听
     * @param activity {@link Activity}
     * @param listener {@link KeyBoardUtils.OnSoftInputChangedListener}
     * @return Helper
     */
    @Override
    public DevHelper registerSoftInputChangedListener(
            Activity activity,
            KeyBoardUtils.OnSoftInputChangedListener listener
    ) {
        KeyBoardUtils.registerSoftInputChangedListener(
                activity, listener
        );
        return this;
    }

    /**
     * 注册软键盘改变监听
     * @param activity {@link Activity}
     * @param listener {@link KeyBoardUtils.OnSoftInputChangedListener}
     * @return Helper
     */
    @Override
    public DevHelper registerSoftInputChangedListener2(
            Activity activity,
            KeyBoardUtils.OnSoftInputChangedListener listener
    ) {
        KeyBoardUtils.registerSoftInputChangedListener2(
                activity, listener
        );
        return this;
    }

    /**
     * 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用
     * @param context {@link Context}
     * @return Helper
     */
    @Override
    public DevHelper fixSoftInputLeaks(Context context) {
        KeyBoardUtils.fixSoftInputLeaks(context);
        return this;
    }

    /**
     * 自动切换键盘状态, 如果键盘显示则隐藏反之显示
     * <pre>
     *     // 无法获取键盘是否打开 ( 不准确 )
     *     InputMethodManager.isActive()
     *     // 获取状态有些版本可以, 不适用
     *     Activity.getWindow().getAttributes().softInputMode
     *     <p></p>
     *     可以配合 {@link KeyBoardUtils#isSoftInputVisible(Activity)} 判断是否显示输入法
     * </pre>
     * @return Helper
     */
    @Override
    public DevHelper toggleKeyboard() {
        KeyBoardUtils.toggleKeyboard();
        return this;
    }

    // ===========
    // = 打开软键盘 =
    // ===========

    /**
     * 打开软键盘
     * @return Helper
     */
    @Override
    public DevHelper openKeyboard() {
        KeyBoardUtils.openKeyboard();
        return this;
    }

    /**
     * 延时打开软键盘
     * @return Helper
     */
    @Override
    public DevHelper openKeyboardDelay() {
        KeyBoardUtils.openKeyboardDelay();
        return this;
    }

    /**
     * 延时打开软键盘
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    @Override
    public DevHelper openKeyboardDelay(long delayMillis) {
        KeyBoardUtils.openKeyboardDelay(delayMillis);
        return this;
    }

    /**
     * 打开软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    @Override
    public DevHelper openKeyboard(EditText editText) {
        KeyBoardUtils.openKeyboard(editText);
        return this;
    }

    /**
     * 延时打开软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    @Override
    public DevHelper openKeyboardDelay(EditText editText) {
        KeyBoardUtils.openKeyboardDelay(editText);
        return this;
    }

    /**
     * 延时打开软键盘
     * @param editText    {@link EditText}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    @Override
    public DevHelper openKeyboardDelay(
            EditText editText,
            long delayMillis
    ) {
        KeyBoardUtils.openKeyboardDelay(
                editText, delayMillis
        );
        return this;
    }

    /**
     * 打开软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    @Override
    public DevHelper openKeyboardByFocus(EditText editText) {
        KeyBoardUtils.openKeyboardByFocus(editText);
        return this;
    }

    // ===========
    // = 关闭软键盘 =
    // ===========

    /**
     * 关闭软键盘
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboard() {
        KeyBoardUtils.closeKeyboard();
        return this;
    }

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboard(EditText editText) {
        KeyBoardUtils.closeKeyboard(editText);
        return this;
    }

    /**
     * 关闭软键盘
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboard(Activity activity) {
        KeyBoardUtils.closeKeyboard(activity);
        return this;
    }

    /**
     * 关闭 dialog 中打开的键盘
     * @param dialog {@link Dialog}
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboard(Dialog dialog) {
        KeyBoardUtils.closeKeyboard(dialog);
        return this;
    }

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @return Helper
     */
    @Override
    public DevHelper closeKeyBoardSpecial(
            EditText editText,
            Dialog dialog
    ) {
        KeyBoardUtils.closeKeyBoardSpecial(
                editText, dialog
        );
        return this;
    }

    // ==========
    // = 延时关闭 =
    // ==========

    /**
     * 延时关闭软键盘
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @return Helper
     */
    @Override
    public DevHelper closeKeyBoardSpecialDelay(
            EditText editText,
            Dialog dialog
    ) {
        KeyBoardUtils.closeKeyBoardSpecialDelay(
                editText, dialog
        );
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param editText    {@link EditText}
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    @Override
    public DevHelper closeKeyBoardSpecialDelay(
            EditText editText,
            Dialog dialog,
            long delayMillis
    ) {
        KeyBoardUtils.closeKeyBoardSpecialDelay(
                editText, dialog, delayMillis
        );
        return this;
    }

    /**
     * 延时关闭软键盘
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboardDelay() {
        KeyBoardUtils.closeKeyboardDelay();
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboardDelay(long delayMillis) {
        KeyBoardUtils.closeKeyboardDelay(delayMillis);
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboardDelay(EditText editText) {
        KeyBoardUtils.closeKeyboardDelay(editText);
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param editText    {@link EditText}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboardDelay(
            EditText editText,
            long delayMillis
    ) {
        KeyBoardUtils.closeKeyboardDelay(
                editText, delayMillis
        );
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboardDelay(Activity activity) {
        KeyBoardUtils.closeKeyboardDelay(activity);
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param activity    {@link Activity}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboardDelay(
            Activity activity,
            long delayMillis
    ) {
        KeyBoardUtils.closeKeyboardDelay(
                activity, delayMillis
        );
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param dialog {@link Dialog}
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboardDelay(Dialog dialog) {
        KeyBoardUtils.closeKeyboardDelay(dialog);
        return this;
    }

    /**
     * 延时关闭软键盘
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    @Override
    public DevHelper closeKeyboardDelay(
            Dialog dialog,
            long delayMillis
    ) {
        KeyBoardUtils.closeKeyboardDelay(
                dialog, delayMillis
        );
        return this;
    }

    // =================
    // = LanguageUtils =
    // =================

    /**
     * 修改系统语言 ( APP 多语言, 单独改变 APP 语言 )
     * @param context {@link Context} - Activity
     * @param locale  {@link Locale}
     * @return Helper
     */
    @Override
    public DevHelper applyLanguage(
            Context context,
            Locale locale
    ) {
        LanguageUtils.applyLanguage(
                context, locale
        );
        return this;
    }

    /**
     * 修改系统语言 (APP 多语言, 单独改变 APP 语言 )
     * @param context  {@link Context}
     * @param language 语言
     * @return Helper
     */
    @Override
    public DevHelper applyLanguage(
            Context context,
            String language
    ) {
        LanguageUtils.applyLanguage(
                context, language
        );
        return this;
    }

    // =====================
    // = NotificationUtils =
    // =====================

    /**
     * 移除通知 ( 移除所有通知 )
     * <pre>
     *     只是针对当前 Context 下的所有 Notification
     * </pre>
     * @return Helper
     */
    @Override
    public DevHelper cancelAllNotification() {
        NotificationUtils.cancelAll();
        return this;
    }

    /**
     * 移除通知 ( 移除标记为 id 的通知 )
     * <pre>
     *     只是针对当前 Context 下的所有 Notification
     * </pre>
     * @param args 消息 id 集合
     * @return Helper
     */
    @Override
    public DevHelper cancelNotification(int... args) {
        NotificationUtils.cancel(args);
        return this;
    }

    /**
     * 移除通知 ( 移除标记为 id 的通知 )
     * <pre>
     *     只是针对当前 Context 下的所有 Notification
     * </pre>
     * @param tag 标记 TAG
     * @param id  消息 id
     * @return Helper
     */
    @Override
    public DevHelper cancelNotification(
            String tag,
            int id
    ) {
        NotificationUtils.cancel(tag, id);
        return this;
    }

    /**
     * 进行通知
     * @param id           消息 id
     * @param notification {@link Notification}
     * @return Helper
     */
    @Override
    public DevHelper notifyNotification(
            int id,
            Notification notification
    ) {
        NotificationUtils.notify(id, notification);
        return this;
    }

    /**
     * 进行通知
     * @param tag          标记 TAG
     * @param id           消息 id
     * @param notification {@link Notification}
     * @return Helper
     */
    @Override
    public DevHelper notifyNotification(
            String tag,
            int id,
            Notification notification
    ) {
        NotificationUtils.notify(
                tag, id, notification
        );
        return this;
    }

    /**
     * 创建 NotificationChannel
     * @param channel {@link NotificationChannel}
     * @return Helper
     */
    @Override
    public DevHelper createNotificationChannel(NotificationChannel channel) {
        NotificationUtils.createNotificationChannel(channel);
        return this;
    }

    // ==============
    // = PhoneUtils =
    // ==============

    /**
     * 跳至拨号界面
     * @param phoneNumber 电话号码
     * @return Helper
     */
    @Override
    public DevHelper dial(String phoneNumber) {
        PhoneUtils.dial(phoneNumber);
        return this;
    }

    /**
     * 拨打电话
     * @param phoneNumber 电话号码
     * @return Helper
     */
    @Override
    public DevHelper call(String phoneNumber) {
        PhoneUtils.call(phoneNumber);
        return this;
    }

    /**
     * 跳至发送短信界面
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return Helper
     */
    @Override
    public DevHelper sendSms(
            String phoneNumber,
            String content
    ) {
        PhoneUtils.sendSms(phoneNumber, content);
        return this;
    }

    /**
     * 发送短信
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return Helper
     */
    @Override
    public DevHelper sendSmsSilent(
            String phoneNumber,
            String content
    ) {
        PhoneUtils.sendSmsSilent(phoneNumber, content);
        return this;
    }

    // =====================
    // = PowerManagerUtils =
    // =====================

    /**
     * 设置屏幕常亮
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper setBright(Activity activity) {
        PowerManagerUtils.setBright(activity);
        return this;
    }

    /**
     * 设置屏幕常亮
     * @param window {@link Activity#getWindow()}
     * @return Helper
     */
    @Override
    public DevHelper setBright(Window window) {
        PowerManagerUtils.setBright(window);
        return this;
    }

    // ===============
    // = ScreenUtils =
    // ===============

    /**
     * 设置禁止截屏
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper setWindowSecure(Activity activity) {
        ScreenUtils.setWindowSecure(activity);
        return this;
    }

    /**
     * 设置屏幕为全屏
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper setFullScreen(Activity activity) {
        ScreenUtils.setFullScreen(activity);
        return this;
    }

    /**
     * 设置屏幕为全屏无标题
     * <pre>
     *     需要在 setContentView 之前调用
     * </pre>
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper setFullScreenNoTitle(Activity activity) {
        ScreenUtils.setFullScreenNoTitle(activity);
        return this;
    }

    /**
     * 设置屏幕为横屏
     * <pre>
     *     还有一种就是在 Activity 中加属性 android:screenOrientation="landscape"
     *     不设置 Activity 的 android:configChanges 时
     *     切屏会重新调用各个生命周期, 切横屏时会执行一次, 切竖屏时会执行两次
     *     设置 Activity 的 android:configChanges="orientation" 时
     *     切屏还是会重新调用各个生命周期, 切横、竖屏时只会执行一次
     *     设置 Activity 的 android:configChanges="orientation|keyboardHidden|screenSize"
     *     4.0 以上必须带最后一个参数时
     *     切屏不会重新调用各个生命周期, 只会执行 onConfigurationChanged 方法
     * </pre>
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper setLandscape(Activity activity) {
        ScreenUtils.setLandscape(activity);
        return this;
    }

    /**
     * 设置屏幕为竖屏
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper setPortrait(Activity activity) {
        ScreenUtils.setPortrait(activity);
        return this;
    }

    /**
     * 切换屏幕方向
     * @param activity {@link Activity}
     * @return Helper
     */
    @Override
    public DevHelper toggleScreenOrientation(Activity activity) {
        ScreenUtils.toggleScreenOrientation(activity);
        return this;
    }

    /**
     * 设置进入休眠时长
     * @param duration 时长
     * @return Helper
     */
    @Override
    public DevHelper setSleepDuration(int duration) {
        ScreenUtils.setSleepDuration(duration);
        return this;
    }

    // =============
    // = SizeUtils =
    // =============

    /**
     * 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 )
     * <pre>
     *     用法示例如下所示
     *     <p></p>
     *     SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
     *          Override
     *          public void onGetSize(View view) {
     *              view.getWidth() {
     * return this;
     * }
     *          }
     *     }) {
     * return this;
     * }
     * </pre>
     * @param view     {@link View}
     * @param listener {@link SizeUtils.OnGetSizeListener}
     * @return Helper
     */
    @Override
    public DevHelper forceGetViewSize(
            View view,
            SizeUtils.OnGetSizeListener listener
    ) {
        SizeUtils.forceGetViewSize(
                view, listener
        );
        return this;
    }

    // ==================
    // = VibrationUtils =
    // ==================

    /**
     * 震动
     * @param millis 震动时长 ( 毫秒 )
     * @return Helper
     */
    @RequiresPermission(Manifest.permission.VIBRATE)
    @Override
    public DevHelper vibrate(long millis) {
        VibrationUtils.vibrate(millis);
        return this;
    }

    /**
     * pattern 模式震动
     * @param pattern new long[]{400, 800, 1200, 1600}, 就是指定在 400ms、800ms、1200ms、1600ms 这些时间点交替启动、关闭手机震动器
     * @param repeat  指定 pattern 数组的索引, 指定 pattern 数组中从 repeat 索引开始的震动进行循环,
     *                -1 表示只震动一次, 非 -1 表示从 pattern 数组指定下标开始重复震动
     * @return Helper
     */
    @RequiresPermission(Manifest.permission.VIBRATE)
    @Override
    public DevHelper vibrate(
            long[] pattern,
            int repeat
    ) {
        VibrationUtils.vibrate(pattern, repeat);
        return this;
    }

    /**
     * 取消震动
     * @return Helper
     */
    @RequiresPermission(Manifest.permission.VIBRATE)
    @Override
    public DevHelper cancelVibrate() {
        VibrationUtils.cancel();
        return this;
    }

    // =============
    // = ViewUtils =
    // =============

    /**
     * 获取 View 宽高 ( 准确 )
     * @param view     {@link View}
     * @param listener 回调事件
     * @return Helper
     */
    @Override
    public DevHelper getWidthHeightExact(
            View view,
            ViewUtils.OnWHListener listener
    ) {
        ViewUtils.getWidthHeightExact(
                view, listener
        );
        return this;
    }

    /**
     * 获取 View 宽高 ( 准确 )
     * @param view     {@link View}
     * @param listener 回调事件
     * @return Helper
     */
    @Override
    public DevHelper getWidthHeightExact2(
            View view,
            ViewUtils.OnWHListener listener
    ) {
        ViewUtils.getWidthHeightExact2(
                view, listener
        );
        return this;
    }

    // ===============
    // = WidgetUtils =
    // ===============

    /**
     * 测量 View
     * @param view           {@link View}
     * @param specifiedWidth 指定宽度
     * @return Helper
     */
    @Override
    public DevHelper measureView(
            View view,
            int specifiedWidth
    ) {
        WidgetUtils.measureView(
                view, specifiedWidth
        );
        return this;
    }

    /**
     * 测量 View
     * @param view            {@link View}
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @return Helper
     */
    @Override
    public DevHelper measureView(
            View view,
            int specifiedWidth,
            int specifiedHeight
    ) {
        WidgetUtils.measureView(
                view, specifiedWidth, specifiedHeight
        );
        return this;
    }

    // ==============
    // = CloseUtils =
    // ==============

    /**
     * 关闭 IO
     * @param closeables Closeable[]
     * @return Helper
     */
    @Override
    public DevHelper closeIO(Closeable... closeables) {
        CloseUtils.closeIO(closeables);
        return this;
    }

    /**
     * 安静关闭 IO
     * @param closeables Closeable[]
     * @return Helper
     */
    @Override
    public DevHelper closeIOQuietly(Closeable... closeables) {
        CloseUtils.closeIOQuietly(closeables);
        return this;
    }

    /**
     * 将缓冲区数据输出
     * @param flushables Flushable[]
     * @return Helper
     */
    @Override
    public DevHelper flush(Flushable... flushables) {
        CloseUtils.flush(flushables);
        return this;
    }

    /**
     * 安静将缓冲区数据输出
     * @param flushables Flushable[]
     * @return Helper
     */
    @Override
    public DevHelper flushQuietly(Flushable... flushables) {
        CloseUtils.flushQuietly(flushables);
        return this;
    }

    /**
     * 将缓冲区数据输出并关闭流
     * @param outputStream {@link OutputStream}
     * @return Helper
     */
    @Override
    public DevHelper flushCloseIO(OutputStream outputStream) {
        CloseUtils.flushCloseIO(outputStream);
        return this;
    }

    /**
     * 安静将缓冲区数据输出并关闭流
     * @param outputStream {@link OutputStream}
     * @return Helper
     */
    @Override
    public DevHelper flushCloseIOQuietly(OutputStream outputStream) {
        CloseUtils.flushCloseIOQuietly(outputStream);
        return this;
    }

    /**
     * 将缓冲区数据输出并关闭流
     * @param writer {@link Writer}
     * @return Helper
     */
    @Override
    public DevHelper flushCloseIO(Writer writer) {
        CloseUtils.flushCloseIO(writer);
        return this;
    }

    /**
     * 安静将缓冲区数据输出并关闭流
     * @param writer {@link Writer}
     * @return Helper
     */
    @Override
    public DevHelper flushCloseIOQuietly(Writer writer) {
        CloseUtils.flushCloseIOQuietly(writer);
        return this;
    }
}