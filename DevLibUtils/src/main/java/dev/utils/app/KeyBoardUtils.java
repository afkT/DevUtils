package dev.utils.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 软键盘相关辅助类
 * @author Ttt
 * <pre>
 *      避免输入法面板遮挡 manifest.xml 中 activity 中设置
 *      android:windowSoftInputMode="adjustPan"
 *      android:windowSoftInputMode="adjustUnspecified|stateHidden"
 * </pre>
 */
public final class KeyBoardUtils {

    private KeyBoardUtils() {
    }

    // 日志 TAG
    private static final String TAG = KeyBoardUtils.class.getSimpleName();
    // 默认延迟时间
    private static int DELAY_MILLIS = 300;
    // 键盘显示
    public static final int KEYBOARD_DISPLAY = 930;
    // 键盘隐藏
    public static final int KEYBOARD_HIDE = 931;

    /**
     * 设置延迟时间
     * @param delayMillis
     */
    public static void setDelayMillis(final int delayMillis) {
        DELAY_MILLIS = delayMillis;
    }

    // ==============
    // = 打开软键盘 =
    // ==============

    /**
     * 打开软键盘
     * @param editText 输入框
     */
    public static void openKeyboard(final EditText editText) {
        if (editText != null) {
            try {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "openKeyboard");
            }
        }
    }

    /**
     * 打开软键盘
     * @param editText
     * @param handler
     */
    public static void openKeyboard(final EditText editText, final Handler handler) {
        openKeyboard(editText, handler, DELAY_MILLIS);
    }

    /**
     * 打开软键盘
     * @param editText
     * @param handler
     * @param delayMillis
     */
    public static void openKeyboard(final EditText editText, final Handler handler, final int delayMillis) {
        if (handler != null && editText != null) {
            // 延迟打开
            handler.postDelayed(new Runnable() {
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
        }
    }

    // =

    /**
     * 打开软键盘
     */
    public static void openKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) DevUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openKeyboard");
        }
    }

    /**
     * 打开软键盘
     * @param handler
     */
    public static void openKeyboard(final Handler handler) {
        openKeyboard(handler, DELAY_MILLIS);
    }

    /**
     * 打开软键盘
     * @param handler
     * @param delayMillis
     */
    public static void openKeyboard(final Handler handler, final int delayMillis) {
        if (handler != null && DevUtils.getContext() != null) {
            // 延迟打开
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    openKeyboard();
                }
            }, delayMillis);
        }
    }

    // ==============
    // = 关闭软键盘 =
    // ==============

    /**
     * 关闭软键盘
     * @param editText 输入框
     */
    public static void closeKeyboard(final EditText editText) {
        if (editText != null) {
            try {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeKeyboard");
            }
        }
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyboard() {
        if (DevUtils.getContext() != null) {
            try {
                InputMethodManager imm = (InputMethodManager) DevUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeKeyboard");
            }
        }
    }

    /**
     * 关闭软键盘
     * @param activity
     */
    public static void closeKeyboard(final Activity activity) {
        if (activity != null) {
            try {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getWindow().peekDecorView().getWindowToken(), 0);
                //imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeKeyboard");
            }
        }
    }

    /**
     * 关闭dialog中打开的键盘
     * @param dialog
     */
    public static void closeKeyboard(final Dialog dialog) {
        if (dialog != null) {
            try {
                InputMethodManager imm = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dialog.getWindow().peekDecorView().getWindowToken(), 0);
                //imm.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeKeyboard");
            }
        }
    }

    // =

    /**
     * 关闭软键盘 - 特殊处理
     * @param editText
     * @param dialog
     */
    public static void closeKeyBoardSpecial(final EditText editText, final Dialog dialog) {
        try {
            // 关闭输入法
            closeKeyboard();
            // 关闭输入法
            closeKeyboard(editText);
            // 关闭输入法
            closeKeyboard(dialog);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeKeyBoardSpecial");
        }
    }

    /**
     * 关闭软键盘 - 特殊处理
     * @param editText
     * @param dialog
     * @param handler
     */
    public static void closeKeyBoardSpecial(final EditText editText, final Dialog dialog, final Handler handler) {
        closeKeyBoardSpecial(editText, dialog, handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘 - 特殊处理(两个都关闭)
     * @param editText
     * @param dialog
     * @param handler
     * @param delayMillis
     */
    public static void closeKeyBoardSpecial(final EditText editText, final Dialog dialog, final Handler handler, final int delayMillis) {
        if (handler != null) {
            // 延迟打开
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyBoardSpecial(editText, dialog);
                }
            }, delayMillis);
        }
    }

    // =

    /**
     * 关闭软键盘
     * @param editText
     * @param handler
     */
    public static void closeKeyboard(final EditText editText, final Handler handler) {
        closeKeyboard(editText, handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘
     * @param editText
     * @param handler
     * @param delayMillis
     */
    public static void closeKeyboard(final EditText editText, final Handler handler, final int delayMillis) {
        if (handler != null && editText != null) {
            // 延迟打开
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard(editText);
                }
            }, delayMillis);
        }
    }

    /**
     * 关闭软键盘
     * @param handler
     */
    public static void closeKeyboard(final Handler handler) {
        closeKeyboard(handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘
     * @param handler
     * @param delayMillis
     */
    public static void closeKeyboard(final Handler handler, final int delayMillis) {
        if (handler != null && DevUtils.getContext() != null) {
            // 延迟打开
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard();
                }
            }, delayMillis);
        }
    }

    /**
     * 关闭软键盘
     * @param activity
     * @param handler
     */
    public static void closeKeyboard(final Activity activity, final Handler handler) {
        closeKeyboard(activity, handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘
     * @param activity
     * @param handler
     * @param delayMillis
     */
    public static void closeKeyboard(final Activity activity, final Handler handler, final int delayMillis) {
        if (handler != null && activity != null) {
            // 延迟打开
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard(activity);
                }
            }, delayMillis);
        }
    }

    /**
     * 关闭软键盘
     * @param dialog
     * @param handler
     */
    public static void closeKeyboard(final Dialog dialog, final Handler handler) {
        closeKeyboard(dialog, handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘
     * @param dialog
     * @param handler
     * @param delayMillis
     */
    public static void closeKeyboard(final Dialog dialog, final Handler handler, final int delayMillis) {
        if (handler != null && dialog != null) {
            // 延迟打开
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard(dialog);
                }
            }, delayMillis);
        }
    }

    // =

    // 下面暂时无法使用，缺少判断键盘是否显示，否则和自动切换无区别
    // InputMethodManager.isActive()   (无法获取)
    // Activity.getWindow().getAttributes().softInputMode  (有些版本可以，不适用)

    /**
     * 自动切换键盘状态，如果键盘显示了则隐藏，隐藏着显示
     */
    public static void toggleKeyboard() {
        // 程序启动后，自动弹出软键盘，可以通过设置一个时间函数来实现，不能再onCreate里写
        try {
            InputMethodManager imm = (InputMethodManager) DevUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toggleKeyboard");
        }
    }

    // ===============================
    // = 点击非EditText 则隐藏输入法 =
    // ===============================

    /**
     * 某个View里面的子View的View判断
     * @param view
     */
    public static void judgeView(final View view, final Activity activity) {
        if (view == null || activity == null) return;
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    closeKeyboard(activity);
                    return false;
                }
            });
        }
        // =
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                judgeView(innerView, activity);
            }
        }
    }

    // ==================
    // = 输入法隐藏显示 =
    // ==================

    /**
     * 判断软键盘是否可见
     * @param activity
     * @return {@code true} 可见, {@code false} 不可见
     */
    public static boolean isSoftInputVisible(final Activity activity) {
        return isSoftInputVisible(activity, 200);
    }

    /**
     * 判断软键盘是否可见
     * @param activity
     * @param minHeightOfSoftInput 软键盘最小高度
     * @return {@code true} 可见, {@code false} 不可见
     */
    public static boolean isSoftInputVisible(final Activity activity, final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
    }

    /**
     * 计算View的宽度高度
     * @param activity
     * @return
     */
    private static int getContentViewInvisibleHeight(final Activity activity) {
        try {
            final View contentView = activity.findViewById(android.R.id.content);
            Rect rect = new Rect();
            contentView.getWindowVisibleDisplayFrame(rect);
            return contentView.getRootView().getHeight() - rect.height();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getContentViewInvisibleHeight");
            return 0;
        }
    }

    /**
     * 注册软键盘改变监听器
     * @param activity
     * @param listener
     */
    public static void registerSoftInputChangedListener(final Activity activity, final OnSoftInputChangedListener listener) {
        try {
            // 获取根View
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
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerSoftInputChangedListener");
        }
    }

    /**
     * 注册软键盘改变监听器
     * @param activity
     * @param listener
     */
    public static void registerSoftInputChangedListener2(final Activity activity, final OnSoftInputChangedListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener != null) {
                    try {
                        Rect rect = new Rect();
                        decorView.getWindowVisibleDisplayFrame(rect);
                        // 计算出可见屏幕的高度
                        int displayHight = rect.bottom - rect.top;
                        // 获取屏幕整体的高度
                        int hight = decorView.getHeight();
                        // 获取键盘高度
                        int keyboardHeight = hight - displayHight;
                        // 计算一定比例
                        boolean visible = (double) displayHight / hight < 0.8;
                        // 判断是否显示
                        listener.onSoftInputChanged(visible, keyboardHeight);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "registerSoftInputChangedListener2");
                    }
                }
            }
        });
    }

    /**
     * 输入法弹出、隐藏改变事件
     */
    public interface OnSoftInputChangedListener {

        /**
         * 输入法弹出、隐藏改变通知
         * @param visible
         * @param height
         */
        void onSoftInputChanged(boolean visible, int height);
    }

    // =

    /**
     * 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用
     * @param context
     */
    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) return;
        try {
            InputMethodManager imm = (InputMethodManager) DevUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
            for (int i = 0; i < 3; i++) {
                try {
                    Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                    if (declaredField == null) continue;
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true);
                    }
                    Object obj = declaredField.get(imm);
                    if (obj == null || !(obj instanceof View)) continue;
                    View view = (View) obj;
                    if (view.getContext() == context) {
                        declaredField.set(imm, null);
                    } else {
                        return;
                    }
                } catch (Throwable th) {
                }
            }
        } catch (Exception e) {
        }
    }
}
