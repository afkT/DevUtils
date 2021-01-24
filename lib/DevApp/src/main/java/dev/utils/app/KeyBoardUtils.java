package dev.utils.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

import dev.utils.LogPrintUtils;

/**
 * detail: 软键盘相关工具类
 * @author Ttt
 * <pre>
 *     避免软键盘面板遮挡 manifest.xml 中 activity 中设置
 *     android:windowSoftInputMode="adjustPan"
 *     android:windowSoftInputMode="adjustUnspecified|stateHidden"
 * </pre>
 */
public final class KeyBoardUtils {

    private KeyBoardUtils() {
    }

    // 日志 TAG
    private static final String TAG = KeyBoardUtils.class.getSimpleName();

    // 主线程 Handler
    private static final Handler sMainHandler     = new Handler(Looper.getMainLooper());
    // 默认延迟时间 ( 毫秒 )
    private static       long    DELAY_MILLIS     = 300;
    // 键盘显示
    public static final  int     KEYBOARD_DISPLAY = 930;
    // 键盘隐藏
    public static final  int     KEYBOARD_HIDE    = 931;

    /**
     * 设置延迟时间
     * @param delayMillis 延迟时间 ( 毫秒 )
     */
    public static void setDelayMillis(final long delayMillis) {
        DELAY_MILLIS = delayMillis;
    }

    // =

    /**
     * 设置 Window 软键盘是否显示
     * @param activity     {@link Activity}
     * @param inputVisible 是否显示软键盘
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSoftInputMode(
            final Activity activity,
            final boolean inputVisible
    ) {
        return setSoftInputMode(activity != null ? activity.getWindow() : null, inputVisible, true);
    }

    /**
     * 设置 Window 软键盘是否显示
     * @param window       {@link Window}
     * @param inputVisible 是否显示软键盘
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSoftInputMode(
            final Window window,
            final boolean inputVisible
    ) {
        return setSoftInputMode(window, inputVisible, true);
    }

    /**
     * 设置 Window 软键盘是否显示
     * @param activity     {@link Activity}
     * @param inputVisible 是否显示软键盘
     * @param clearFlag    是否清空 Flag ( FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_FOCUSABLE )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSoftInputMode(
            final Activity activity,
            final boolean inputVisible,
            final boolean clearFlag
    ) {
        return setSoftInputMode(activity != null ? activity.getWindow() : null, inputVisible, clearFlag);
    }

    /**
     * 设置 Window 软键盘是否显示
     * @param window       {@link Window}
     * @param inputVisible 是否显示软键盘
     * @param clearFlag    是否清空 Flag ( FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_FOCUSABLE )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSoftInputMode(
            final Window window,
            final boolean inputVisible,
            final boolean clearFlag
    ) {
        if (window != null) {
            try {
                if (inputVisible) {
                    if (clearFlag) {
                        window.clearFlags(
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                        );
                    }
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                } else {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setSoftInputMode");
            }
            return true;
        }
        return false;
    }

    // ==============================
    // = 点击非 EditText 则隐藏软键盘 =
    // ==============================

    /**
     * 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件
     * @param view     {@link View}
     * @param activity {@link Activity}
     */
    public static void judgeView(
            final View view,
            final Activity activity
    ) {
        if (view == null || activity == null) return;
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(
                        View v,
                        MotionEvent event
                ) {
                    closeKeyboard(activity);
                    return false;
                }
            });
        }
        // =
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup != null) {
                for (int i = 0, len = viewGroup.getChildCount(); i < len; i++) {
                    View innerView = viewGroup.getChildAt(i);
                    judgeView(innerView, activity);
                }
            }
        }
    }

    // =================
    // = 软键盘隐藏显示 =
    // =================

    /**
     * 判断软键盘是否可见
     * @param activity {@link Activity}
     * @return {@code true} 可见, {@code false} 不可见
     */
    public static boolean isSoftInputVisible(final Activity activity) {
        return isSoftInputVisible(activity, 200);
    }

    /**
     * 判断软键盘是否可见
     * @param activity             {@link Activity}
     * @param minHeightOfSoftInput 软键盘最小高度
     * @return {@code true} 可见, {@code false} 不可见
     */
    public static boolean isSoftInputVisible(
            final Activity activity,
            final int minHeightOfSoftInput
    ) {
        return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
    }

    /**
     * 计算 Activity content View 高度
     * @param activity {@link Activity}
     * @return View 的高度
     */
    private static int getContentViewInvisibleHeight(final Activity activity) {
        try {
            final View contentView = activity.findViewById(android.R.id.content);
            Rect       rect        = new Rect();
            contentView.getWindowVisibleDisplayFrame(rect);
            return contentView.getRootView().getHeight() - rect.height();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getContentViewInvisibleHeight");
            return 0;
        }
    }

    /**
     * 注册软键盘改变监听
     * @param activity {@link Activity}
     * @param listener {@link OnSoftInputChangedListener}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean registerSoftInputChangedListener(
            final Activity activity,
            final OnSoftInputChangedListener listener
    ) {
        try {
            // 获取根 View
            final View contentView = activity.findViewById(android.R.id.content);
            // 添加事件
            contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (listener != null) {
                        // 获取高度
                        int height = getContentViewInvisibleHeight(activity);
                        // 判断是否相同
                        listener.onSoftInputChanged(height >= 200, height);
                    }
                }
            });
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerSoftInputChangedListener");
        }
        return false;
    }

    /**
     * 注册软键盘改变监听
     * @param activity {@link Activity}
     * @param listener {@link OnSoftInputChangedListener}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean registerSoftInputChangedListener2(
            final Activity activity,
            final OnSoftInputChangedListener listener
    ) {
        try {
            final View decorView = activity.getWindow().getDecorView();
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (listener != null) {
                        try {
                            Rect rect = new Rect();
                            decorView.getWindowVisibleDisplayFrame(rect);
                            // 计算出可见屏幕的高度
                            int displayHeight = rect.bottom - rect.top;
                            // 获取屏幕整体的高度
                            int height = decorView.getHeight();
                            // 获取键盘高度
                            int keyboardHeight = height - displayHeight;
                            // 计算一定比例
                            boolean visible = ((double) displayHeight / (double) height) < 0.8d;
                            // 判断是否显示
                            listener.onSoftInputChanged(visible, keyboardHeight);
                        } catch (Exception e) {
                            LogPrintUtils.eTag(TAG, e, "registerSoftInputChangedListener2");
                        }
                    }
                }
            });
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerSoftInputChangedListener2");
        }
        return false;
    }

    /**
     * detail: 软键盘弹出、隐藏改变事件
     * @author Ttt
     */
    public interface OnSoftInputChangedListener {

        /**
         * 软键盘弹出、隐藏改变通知
         * @param visible 是否显示了软键盘
         * @param height  软键盘高度
         */
        void onSoftInputChanged(
                boolean visible,
                int height
        );
    }

    // =

    /**
     * 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用
     * @param context {@link Context}
     */
    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) return;
        try {
            InputMethodManager imm    = AppUtils.getInputMethodManager();
            String[]           strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
            for (int i = 0, len = strArr.length; i < len; i++) {
                try {
                    Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                    if (declaredField == null) continue;
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true);
                    }
                    Object object = declaredField.get(imm);
                    if (!(object instanceof View)) continue;
                    View view = (View) object;
                    if (view.getContext() == context) {
                        declaredField.set(imm, null);
                    } else {
                        return;
                    }
                } catch (Throwable ignore) {
                }
            }
        } catch (Exception e) {
        }
        return;
    }

    /**
     * 自动切换键盘状态, 如果键盘显示则隐藏反之显示
     * <pre>
     *     // 无法获取键盘是否打开 ( 不准确 )
     *     InputMethodManager.isActive()
     *     // 获取状态有些版本可以, 不适用
     *     Activity.getWindow().getAttributes().softInputMode
     *     <p></p>
     *     可以配合 {@link #isSoftInputVisible(Activity)} 判断是否显示输入法
     * </pre>
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleKeyboard() {
        try {
            InputMethodManager imm = AppUtils.getInputMethodManager();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toggleKeyboard");
        }
        return false;
    }

    // =============
    // = 打开软键盘 =
    // =============

    /**
     * 打开软键盘
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboard() {
        try {
            InputMethodManager imm = AppUtils.getInputMethodManager();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openKeyboard");
        }
        return false;
    }

    /**
     * 延时打开软键盘
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboardDelay() {
        return openKeyboardDelay(DELAY_MILLIS);
    }

    /**
     * 延时打开软键盘
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboardDelay(final long delayMillis) {
        sMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openKeyboard();
            }
        }, delayMillis);
        return true;
    }

    // =

    /**
     * 打开软键盘
     * @param editText {@link EditText}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboard(final EditText editText) {
        if (editText != null) {
            try {
                InputMethodManager imm = AppUtils.getInputMethodManager();
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "openKeyboard");
            }
        }
        return false;
    }

    /**
     * 延时打开软键盘
     * @param editText {@link EditText}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboardDelay(final EditText editText) {
        return openKeyboardDelay(editText, DELAY_MILLIS);
    }

    /**
     * 延时打开软键盘
     * @param editText    {@link EditText}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboardDelay(
            final EditText editText,
            final long delayMillis
    ) {
        if (editText != null) {
            sMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        editText.requestFocus();
                        editText.setSelection(editText.getText().toString().length());
                    } catch (Exception e) {
                    }
                    openKeyboard(editText);
                }
            }, delayMillis);
            return true;
        }
        return false;
    }

    // =============
    // = 关闭软键盘 =
    // =============

    /**
     * 关闭软键盘
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard() {
        try {
            InputMethodManager imm = AppUtils.getInputMethodManager();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeKeyboard");
        }
        return false;
    }

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final EditText editText) {
        if (editText != null) {
            try {
                InputMethodManager imm = AppUtils.getInputMethodManager();
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeKeyboard");
            }
        }
        return false;
    }

    /**
     * 关闭软键盘
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final Activity activity) {
        if (activity != null) {
            try {
                InputMethodManager imm = AppUtils.getInputMethodManager();
                imm.hideSoftInputFromWindow(activity.getWindow().peekDecorView().getWindowToken(), 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeKeyboard");
            }
        }
        return false;
    }

    /**
     * 关闭 dialog 中打开的键盘
     * @param dialog {@link Dialog}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final Dialog dialog) {
        if (dialog != null) {
            try {
                InputMethodManager imm = AppUtils.getInputMethodManager();
                imm.hideSoftInputFromWindow(dialog.getWindow().peekDecorView().getWindowToken(), 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeKeyboard");
            }
        }
        return false;
    }

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyBoardSpecial(
            final EditText editText,
            final Dialog dialog
    ) {
        try {
            closeKeyboard();
            closeKeyboard(editText);
            closeKeyboard(dialog);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeKeyBoardSpecial");
        }
        return false;
    }

    // ===========
    // = 延时关闭 =
    // ===========

    /**
     * 延时关闭软键盘
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyBoardSpecialDelay(
            final EditText editText,
            final Dialog dialog
    ) {
        return closeKeyBoardSpecialDelay(editText, dialog, DELAY_MILLIS);
    }

    /**
     * 延时关闭软键盘
     * @param editText    {@link EditText}
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyBoardSpecialDelay(
            final EditText editText,
            final Dialog dialog,
            final long delayMillis
    ) {
        sMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeKeyBoardSpecial(editText, dialog);
            }
        }, delayMillis);
        return true;
    }

    // =

    /**
     * 延时关闭软键盘
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboardDelay() {
        return closeKeyboardDelay(DELAY_MILLIS);
    }

    /**
     * 延时关闭软键盘
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboardDelay(final long delayMillis) {
        sMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeKeyboard();
            }
        }, delayMillis);
        return true;
    }

    /**
     * 延时关闭软键盘
     * @param editText {@link EditText}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboardDelay(final EditText editText) {
        return closeKeyboardDelay(editText, DELAY_MILLIS);
    }

    /**
     * 延时关闭软键盘
     * @param editText    {@link EditText}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboardDelay(
            final EditText editText,
            final long delayMillis
    ) {
        if (editText != null) {
            sMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard(editText);
                }
            }, delayMillis);
            return true;
        }
        return false;
    }

    /**
     * 延时关闭软键盘
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboardDelay(final Activity activity) {
        return closeKeyboardDelay(activity, DELAY_MILLIS);
    }

    /**
     * 延时关闭软键盘
     * @param activity    {@link Activity}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboardDelay(
            final Activity activity,
            final long delayMillis
    ) {
        if (activity != null) {
            sMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard(activity);
                }
            }, delayMillis);
            return true;
        }
        return false;
    }

    /**
     * 延时关闭软键盘
     * @param dialog {@link Dialog}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboardDelay(final Dialog dialog) {
        return closeKeyboardDelay(dialog, DELAY_MILLIS);
    }

    /**
     * 延时关闭软键盘
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboardDelay(
            final Dialog dialog,
            final long delayMillis
    ) {
        if (dialog != null) {
            sMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard(dialog);
                }
            }, delayMillis);
            return true;
        }
        return false;
    }
}