package dev.utils.app.helper.dev;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.ColorInt;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;

import dev.utils.app.KeyBoardUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.ViewUtils;
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

    // ==============
    // = ClickUtils =
    // ==============

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param range 点击范围
     * @param views View[]
     * @return Helper
     */
    T addTouchArea(
            int range,
            View... views
    );

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param left   left range
     * @param top    top range
     * @param right  right range
     * @param bottom bottom range
     * @param views  View[]
     * @return Helper
     */
    T addTouchArea(
            int left,
            int top,
            int right,
            int bottom,
            View... views
    );

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnClick(
            View.OnClickListener listener,
            View... views
    );

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnLongClick(
            View.OnLongClickListener listener,
            View... views
    );

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnTouch(
            View.OnTouchListener listener,
            View... views
    );

    // ==================
    // = ClipboardUtils =
    // ==================

    /**
     * 复制文本到剪贴板
     * @param text 文本
     * @return Helper
     */
    T copyText(CharSequence text);

    /**
     * 复制 URI 到剪贴板
     * @param uri {@link Uri}
     * @return Helper
     */
    T copyUri(Uri uri);

    /**
     * 复制意图到剪贴板
     * @param intent {@link Intent}
     * @return Helper
     */
    T copyIntent(Intent intent);

    // ===============
    // = DialogUtils =
    // ===============

    /**
     * 设置 Dialog 状态栏颜色
     * @param dialog {@link Dialog}
     * @param color  Dialog StatusBar Color
     * @return Helper
     */
    T setDialogStatusBarColor(
            Dialog dialog,
            @ColorInt int color
    );

    /**
     * 设置 Dialog 高版本状态栏蒙层
     * @param dialog {@link Dialog}
     * @param color  Dialog StatusBar Color
     * @return Helper
     */
    T setDialogSemiTransparentStatusBarColor(
            Dialog dialog,
            @ColorInt int color
    );

    /**
     * 设置 Dialog 状态栏颜色、高版本状态栏蒙层
     * @param dialog   {@link Dialog}
     * @param color    Dialog StatusBar Color
     * @param addFlags 是否添加 Windows flags
     * @return Helper
     */
    T setDialogStatusBarColor2(
            Dialog dialog,
            @ColorInt int color,
            boolean addFlags
    );

    /**
     * 设置 Dialog Window LayoutParams
     * @param dialog {@link Dialog}
     * @param params {@link WindowManager.LayoutParams}
     * @return Helper
     */
    T setDialogAttributes(
            Dialog dialog,
            WindowManager.LayoutParams params
    );

    /**
     * 设置 Dialog 宽度
     * @param dialog {@link Dialog}
     * @param width  宽度
     * @return Helper
     */
    T setDialogWidth(
            Dialog dialog,
            int width
    );

    /**
     * 设置 Dialog 高度
     * @param dialog {@link Dialog}
     * @param height 高度
     * @return Helper
     */
    T setDialogHeight(
            Dialog dialog,
            int height
    );

    /**
     * 设置 Dialog 宽度、高度
     * @param dialog {@link Dialog}
     * @param width  宽度
     * @param height 高度
     * @return Helper
     */
    T setDialogWidthHeight(
            Dialog dialog,
            int width,
            int height
    );

    /**
     * 设置 Dialog X 轴坐标
     * @param dialog {@link Dialog}
     * @param x      X 轴坐标
     * @return Helper
     */
    T setDialogX(
            Dialog dialog,
            int x
    );

    /**
     * 设置 Dialog Y 轴坐标
     * @param dialog {@link Dialog}
     * @param y      Y 轴坐标
     * @return Helper
     */
    T setDialogY(
            Dialog dialog,
            int y
    );

    /**
     * 设置 Dialog X、Y 轴坐标
     * @param dialog {@link Dialog}
     * @param x      X 轴坐标
     * @param y      Y 轴坐标
     * @return Helper
     */
    T setDialogXY(
            Dialog dialog,
            int x,
            int y
    );

    /**
     * 设置 Dialog Gravity
     * @param dialog  {@link Dialog}
     * @param gravity 重心
     * @return Helper
     */
    T setDialogGravity(
            Dialog dialog,
            int gravity
    );

    /**
     * 设置 Dialog 透明度
     * @param dialog    {@link Dialog}
     * @param dimAmount 透明度
     * @return Helper
     */
    T setDialogDimAmount(
            Dialog dialog,
            float dimAmount
    );

    /**
     * 设置是否允许返回键关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @return Helper
     */
    T setDialogCancelable(
            Dialog dialog,
            boolean cancel
    );

    /**
     * 设置是否允许点击其他地方自动关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @return Helper
     */
    T setDialogCanceledOnTouchOutside(
            Dialog dialog,
            boolean cancel
    );

    /**
     * 设置是否允许 返回键关闭、点击其他地方自动关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @return Helper
     */
    T setDialogCancelableAndTouchOutside(
            Dialog dialog,
            boolean cancel
    );

    // ==============
    // = Dialog 操作 =
    // ==============

    /**
     * 显示 Dialog
     * @param dialog {@link Dialog}
     * @return Helper
     */
    T showDialog(Dialog dialog);

    /**
     * 关闭多个 Dialog
     * @param dialogs Dialog[]
     * @return Helper
     */
    T closeDialogs(Dialog... dialogs);

    /**
     * 关闭多个 DialogFragment
     * @param dialogs DialogFragment[]
     * @return Helper
     */
    T closeDialogs(DialogFragment... dialogs);

    /**
     * 关闭多个 PopupWindow
     * @param popupWindows PopupWindow[]
     * @return Helper
     */
    T closePopupWindows(PopupWindow... popupWindows);

    /**
     * 自动关闭 dialog
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param dialogs     Dialog[]
     * @return Helper
     */
    T autoCloseDialog(
            long delayMillis,
            Handler handler,
            Dialog... dialogs
    );

    /**
     * 自动关闭 DialogFragment
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param dialogs     DialogFragment[]
     * @return Helper
     */
    T autoCloseDialog(
            long delayMillis,
            Handler handler,
            DialogFragment... dialogs
    );

    /**
     * 自动关闭 PopupWindow
     * @param delayMillis  延迟关闭时间
     * @param handler      {@link Handler}
     * @param popupWindows PopupWindow[]
     * @return Helper
     */
    T autoClosePopupWindow(
            long delayMillis,
            Handler handler,
            PopupWindow... popupWindows
    );

    // =================
    // = KeyBoardUtils =
    // =================

    /**
     * 设置 Window 软键盘是否显示
     * @param activity     {@link Activity}
     * @param inputVisible 是否显示软键盘
     * @return Helper
     */
    T setSoftInputMode(
            Activity activity,
            boolean inputVisible
    );

    /**
     * 设置 Window 软键盘是否显示
     * @param window       {@link Window}
     * @param inputVisible 是否显示软键盘
     * @return Helper
     */
    T setSoftInputMode(
            Window window,
            boolean inputVisible
    );

    /**
     * 设置 Window 软键盘是否显示
     * @param activity     {@link Activity}
     * @param inputVisible 是否显示软键盘
     * @param clearFlag    是否清空 Flag ( FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_FOCUSABLE )
     * @return Helper
     */
    T setSoftInputMode(
            Activity activity,
            boolean inputVisible,
            boolean clearFlag
    );

    /**
     * 设置 Window 软键盘是否显示
     * @param window       {@link Window}
     * @param inputVisible 是否显示软键盘
     * @param clearFlag    是否清空 Flag ( FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_FOCUSABLE )
     * @return Helper
     */
    T setSoftInputMode(
            Window window,
            boolean inputVisible,
            boolean clearFlag
    );

    // ============================
    // = 点击非 EditText 则隐藏软键盘 =
    // ============================

    /**
     * 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件
     * @param view     {@link View}
     * @param activity {@link Activity}
     * @return Helper
     */
    T judgeView(
            View view,
            Activity activity
    );

    // ===============
    // = 软键盘隐藏显示 =
    // ===============

    /**
     * 注册软键盘改变监听
     * @param activity {@link Activity}
     * @param listener {@link KeyBoardUtils.OnSoftInputChangedListener}
     * @return Helper
     */
    T registerSoftInputChangedListener(
            Activity activity,
            KeyBoardUtils.OnSoftInputChangedListener listener
    );

    /**
     * 注册软键盘改变监听
     * @param activity {@link Activity}
     * @param listener {@link KeyBoardUtils.OnSoftInputChangedListener}
     * @return Helper
     */
    T registerSoftInputChangedListener2(
            Activity activity,
            KeyBoardUtils.OnSoftInputChangedListener listener
    );

    /**
     * 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用
     * @param context {@link Context}
     * @return Helper
     */
    T fixSoftInputLeaks(Context context);

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
    T toggleKeyboard();

    // ===========
    // = 打开软键盘 =
    // ===========

    /**
     * 打开软键盘
     * @return Helper
     */
    T openKeyboard();

    /**
     * 延时打开软键盘
     * @return Helper
     */
    T openKeyboardDelay();

    /**
     * 延时打开软键盘
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    T openKeyboardDelay(long delayMillis);

    /**
     * 打开软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    T openKeyboard(EditText editText);

    /**
     * 延时打开软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    T openKeyboardDelay(EditText editText);

    /**
     * 延时打开软键盘
     * @param editText    {@link EditText}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    T openKeyboardDelay(
            EditText editText,
            long delayMillis
    );

    /**
     * 打开软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    T openKeyboardByFocus(EditText editText);

    // ===========
    // = 关闭软键盘 =
    // ===========

    /**
     * 关闭软键盘
     * @return Helper
     */
    T closeKeyboard();

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    T closeKeyboard(EditText editText);

    /**
     * 关闭软键盘
     * @param activity {@link Activity}
     * @return Helper
     */
    T closeKeyboard(Activity activity);

    /**
     * 关闭 dialog 中打开的键盘
     * @param dialog {@link Dialog}
     * @return Helper
     */
    T closeKeyboard(Dialog dialog);

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @return Helper
     */
    T closeKeyBoardSpecial(
            EditText editText,
            Dialog dialog
    );

    // ==========
    // = 延时关闭 =
    // ==========

    /**
     * 延时关闭软键盘
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @return Helper
     */
    T closeKeyBoardSpecialDelay(
            EditText editText,
            Dialog dialog
    );

    /**
     * 延时关闭软键盘
     * @param editText    {@link EditText}
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    T closeKeyBoardSpecialDelay(
            EditText editText,
            Dialog dialog,
            long delayMillis
    );

    /**
     * 延时关闭软键盘
     * @return Helper
     */
    T closeKeyboardDelay();

    /**
     * 延时关闭软键盘
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    T closeKeyboardDelay(long delayMillis);

    /**
     * 延时关闭软键盘
     * @param editText {@link EditText}
     * @return Helper
     */
    T closeKeyboardDelay(EditText editText);

    /**
     * 延时关闭软键盘
     * @param editText    {@link EditText}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    T closeKeyboardDelay(
            EditText editText,
            long delayMillis
    );

    /**
     * 延时关闭软键盘
     * @param activity {@link Activity}
     * @return Helper
     */
    T closeKeyboardDelay(Activity activity);

    /**
     * 延时关闭软键盘
     * @param activity    {@link Activity}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    T closeKeyboardDelay(
            Activity activity,
            long delayMillis
    );

    /**
     * 延时关闭软键盘
     * @param dialog {@link Dialog}
     * @return Helper
     */
    T closeKeyboardDelay(Dialog dialog);

    /**
     * 延时关闭软键盘
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return Helper
     */
    T closeKeyboardDelay(
            Dialog dialog,
            long delayMillis
    );

    // =================
    // = LanguageUtils =
    // =================

    /**
     * 修改系统语言 ( APP 多语言, 单独改变 APP 语言 )
     * @param context {@link Context} - Activity
     * @param locale  {@link Locale}
     * @return Helper
     */
    T applyLanguage(
            Context context,
            Locale locale
    );

    /**
     * 修改系统语言 (APP 多语言, 单独改变 APP 语言 )
     * @param context  {@link Context}
     * @param language 语言
     * @return Helper
     */
    T applyLanguage(
            Context context,
            String language
    );

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
    T cancelAllNotification();

    /**
     * 移除通知 ( 移除标记为 id 的通知 )
     * <pre>
     *     只是针对当前 Context 下的所有 Notification
     * </pre>
     * @param args 消息 id 集合
     * @return Helper
     */
    T cancelNotification(int... args);

    /**
     * 移除通知 ( 移除标记为 id 的通知 )
     * <pre>
     *     只是针对当前 Context 下的所有 Notification
     * </pre>
     * @param tag 标记 TAG
     * @param id  消息 id
     * @return Helper
     */
    T cancelNotification(
            String tag,
            int id
    );

    /**
     * 进行通知
     * @param id           消息 id
     * @param notification {@link Notification}
     * @return Helper
     */
    T notifyNotification(
            int id,
            Notification notification
    );

    /**
     * 进行通知
     * @param tag          标记 TAG
     * @param id           消息 id
     * @param notification {@link Notification}
     * @return Helper
     */
    T notifyNotification(
            String tag,
            int id,
            Notification notification
    );

    /**
     * 创建 NotificationChannel
     * @param channel {@link NotificationChannel}
     * @return {@link NotificationChannel}
     */
    T createNotificationChannel(NotificationChannel channel);

    // ==============
    // = PhoneUtils =
    // ==============

    /**
     * 跳至拨号界面
     * @param phoneNumber 电话号码
     * @return Helper
     */
    T dial(String phoneNumber);

    /**
     * 拨打电话
     * @param phoneNumber 电话号码
     * @return Helper
     */
    T call(String phoneNumber);

    /**
     * 跳至发送短信界面
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return Helper
     */
    T sendSms(
            String phoneNumber,
            String content
    );

    /**
     * 发送短信
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return Helper
     */
    T sendSmsSilent(
            String phoneNumber,
            String content
    );

    // =====================
    // = PowerManagerUtils =
    // =====================

    /**
     * 设置屏幕常亮
     * @param activity {@link Activity}
     * @return Helper
     */
    T setBright(Activity activity);

    /**
     * 设置屏幕常亮
     * @param window {@link Activity#getWindow()}
     * @return Helper
     */
    T setBright(Window window);

    // ===============
    // = ScreenUtils =
    // ===============

    /**
     * 设置禁止截屏
     * @param activity {@link Activity}
     * @return Helper
     */
    T setWindowSecure(Activity activity);

    /**
     * 设置屏幕为全屏
     * @param activity {@link Activity}
     * @return Helper
     */
    T setFullScreen(Activity activity);

    /**
     * 设置屏幕为全屏无标题
     * <pre>
     *     需要在 setContentView 之前调用
     * </pre>
     * @param activity {@link Activity}
     * @return Helper
     */
    T setFullScreenNoTitle(Activity activity);

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
    T setLandscape(Activity activity);

    /**
     * 设置屏幕为竖屏
     * @param activity {@link Activity}
     * @return Helper
     */
    T setPortrait(Activity activity);

    /**
     * 切换屏幕方向
     * @param activity {@link Activity}
     * @return Helper
     */
    T toggleScreenOrientation(Activity activity);

    /**
     * 设置进入休眠时长
     * @param duration 时长
     * @return Helper
     */
    T setSleepDuration(int duration);

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
     *              view.getWidth();
     *          }
     *     });
     * </pre>
     * @param view     {@link View}
     * @param listener {@link SizeUtils.OnGetSizeListener}
     * @return Helper
     */
    T forceGetViewSize(
            View view,
            SizeUtils.OnGetSizeListener listener
    );

    // ==================
    // = VibrationUtils =
    // ==================

    /**
     * 震动
     * @param millis 震动时长 ( 毫秒 )
     * @return Helper
     */
    T vibrate(long millis);

    /**
     * pattern 模式震动
     * @param pattern new long[]{400, 800, 1200, 1600}, 就是指定在 400ms、800ms、1200ms、1600ms 这些时间点交替启动、关闭手机震动器
     * @param repeat  指定 pattern 数组的索引, 指定 pattern 数组中从 repeat 索引开始的震动进行循环,
     *                -1 表示只震动一次, 非 -1 表示从 pattern 数组指定下标开始重复震动
     * @return Helper
     */
    T vibrate(
            long[] pattern,
            int repeat
    );

    /**
     * 取消震动
     * @return Helper
     */
    T cancelVibrate();

    // =============
    // = ViewUtils =
    // =============

    /**
     * 获取 View 宽高 ( 准确 )
     * @param view     {@link View}
     * @param listener 回调事件
     * @return Helper
     */
    T getWidthHeightExact(
            View view,
            ViewUtils.OnWHListener listener
    );

    /**
     * 获取 View 宽高 ( 准确 )
     * @param view     {@link View}
     * @param listener 回调事件
     * @return Helper
     */
    T getWidthHeightExact2(
            View view,
            ViewUtils.OnWHListener listener
    );

    // ===============
    // = WidgetUtils =
    // ===============

    /**
     * 测量 View
     * @param view           {@link View}
     * @param specifiedWidth 指定宽度
     * @return Helper
     */
    T measureView(
            View view,
            int specifiedWidth
    );

    /**
     * 测量 View
     * @param view            {@link View}
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @return Helper
     */
    T measureView(
            View view,
            int specifiedWidth,
            int specifiedHeight
    );
}