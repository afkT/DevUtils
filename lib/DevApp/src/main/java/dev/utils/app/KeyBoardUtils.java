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
 * detail: 软键盘相关工具类
 * @author Ttt
 * <pre>
 *     避免输入法面板遮挡 manifest.xml 中 activity 中设置
 *     android:windowSoftInputMode="adjustPan"
 *     android:windowSoftInputMode="adjustUnspecified|stateHidden"
 * </pre>
 */
public final class KeyBoardUtils {

    private KeyBoardUtils() {
    }

    // 日志 TAG
    private static final String TAG = KeyBoardUtils.class.getSimpleName();
    // 默认延迟时间 ( 毫秒 )
    private static int DELAY_MILLIS = 300;
    // 键盘显示
    public static final int KEYBOARD_DISPLAY = 930;
    // 键盘隐藏
    public static final int KEYBOARD_HIDE = 931;

    /**
     * 设置延迟时间
     * @param delayMillis 延迟时间 ( 毫秒 )
     */
    public static void setDelayMillis(final int delayMillis) {
        DELAY_MILLIS = delayMillis;
    }

    // ==============
    // = 打开软键盘 =
    // ==============

    /**
     * 打开软键盘
     * @param editText {@link EditText}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboard(final EditText editText) {
        if (editText != null) {
            try {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
     * 打开软键盘
     * @param editText {@link EditText}
     * @param handler  {@link Handler}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboard(final EditText editText, final Handler handler) {
        return openKeyboard(editText, handler, DELAY_MILLIS);
    }

    /**
     * 打开软键盘
     * @param editText    {@link EditText}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboard(final EditText editText, final Handler handler, final int delayMillis) {
        if (editText != null && handler != null) {
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
            return true;
        }
        return false;
    }

    // =

    /**
     * 打开软键盘
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) DevUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openKeyboard");
        }
        return false;
    }

    /**
     * 打开软键盘
     * @param handler {@link Handler}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboard(final Handler handler) {
        return openKeyboard(handler, DELAY_MILLIS);
    }

    /**
     * 打开软键盘
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openKeyboard(final Handler handler, final int delayMillis) {
        if (DevUtils.getContext() != null && handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    openKeyboard();
                }
            }, delayMillis);
            return true;
        }
        return false;
    }

    // ==============
    // = 关闭软键盘 =
    // ==============

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final EditText editText) {
        if (editText != null) {
            try {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard() {
        if (DevUtils.getContext() != null) {
            try {
                InputMethodManager imm = (InputMethodManager) DevUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                InputMethodManager imm = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dialog.getWindow().peekDecorView().getWindowToken(), 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeKeyboard");
            }
        }
        return false;
    }

    // =

    /**
     * 关闭软键盘 - 特殊处理
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyBoardSpecial(final EditText editText, final Dialog dialog) {
        try {
            // 关闭输入法
            closeKeyboard();
            // 关闭输入法
            closeKeyboard(editText);
            // 关闭输入法
            closeKeyboard(dialog);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeKeyBoardSpecial");
        }
        return false;
    }

    /**
     * 关闭软键盘 - 特殊处理
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @param handler  {@link Handler}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyBoardSpecial(final EditText editText, final Dialog dialog, final Handler handler) {
        return closeKeyBoardSpecial(editText, dialog, handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘 - 特殊处理 ( 两个都关闭 )
     * @param editText    {@link EditText}
     * @param dialog      {@link Dialog}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyBoardSpecial(final EditText editText, final Dialog dialog, final Handler handler, final int delayMillis) {
        if (handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyBoardSpecial(editText, dialog);
                }
            }, delayMillis);
            return true;
        }
        return false;
    }

    // =

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @param handler  {@link Handler}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final EditText editText, final Handler handler) {
        return closeKeyboard(editText, handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘
     * @param editText    {@link EditText}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final EditText editText, final Handler handler, final int delayMillis) {
        if (editText != null && handler != null) {
            handler.postDelayed(new Runnable() {
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
     * 关闭软键盘
     * @param handler {@link Handler}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final Handler handler) {
        return closeKeyboard(handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final Handler handler, final int delayMillis) {
        if (DevUtils.getContext() != null && handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard();
                }
            }, delayMillis);
            return true;
        }
        return false;
    }

    /**
     * 关闭软键盘
     * @param activity {@link Activity}
     * @param handler  {@link Handler}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final Activity activity, final Handler handler) {
        return closeKeyboard(activity, handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘
     * @param activity    {@link Activity}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final Activity activity, final Handler handler, final int delayMillis) {
        if (activity != null && handler != null) {
            handler.postDelayed(new Runnable() {
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
     * 关闭软键盘
     * @param dialog  {@link Dialog}
     * @param handler {@link Handler}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final Dialog dialog, final Handler handler) {
        return closeKeyboard(dialog, handler, DELAY_MILLIS);
    }

    /**
     * 关闭软键盘
     * @param dialog      {@link Dialog}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeKeyboard(final Dialog dialog, final Handler handler, final int delayMillis) {
        if (dialog != null && handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeKeyboard(dialog);
                }
            }, delayMillis);
            return true;
        }
        return false;
    }

    // =

    // 下面暂时无法使用, 缺少判断键盘是否显示, 否则和自动切换无区别
    // InputMethodManager.isActive() ( 无法获取 )
    // Activity.getWindow().getAttributes().softInputMode ( 有些版本可以, 不适用 )

    /**
     * 自动切换键盘状态, 如果键盘显示了则隐藏, 隐藏着显示
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) DevUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toggleKeyboard");
        }
        return false;
    }

    // ================================
    // = 点击非 EditText 则隐藏输入法 =
    // ================================

    /**
     * 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件
     * @param view     {@link View}
     * @param activity {@link Activity}
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
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup != null) {
                for (int i = 0, len = viewGroup.getChildCount(); i < len; i++) {
                    View innerView = viewGroup.getChildAt(i);
                    judgeView(innerView, activity);
                }
            }
        }
    }

    // ==================
    // = 输入法隐藏显示 =
    // ==================

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
    public static boolean isSoftInputVisible(final Activity activity, final int minHeightOfSoftInput) {
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
            Rect rect = new Rect();
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
    public static boolean registerSoftInputChangedListener(final Activity activity, final OnSoftInputChangedListener listener) {
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
    public static boolean registerSoftInputChangedListener2(final Activity activity, final OnSoftInputChangedListener listener) {
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
                            int displayHight = rect.bottom - rect.top;
                            // 获取屏幕整体的高度
                            int hight = decorView.getHeight();
                            // 获取键盘高度
                            int keyboardHeight = hight - displayHight;
                            // 计算一定比例
                            boolean visible = ((double) displayHight / (double) hight) < 0.8d;
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
     * detail: 输入法弹出、隐藏改变事件
     * @author Ttt
     */
    public interface OnSoftInputChangedListener {

        /**
         * 输入法弹出、隐藏改变通知
         * @param visible 是否显示了输入法
         * @param height  输入法高度
         */
        void onSoftInputChanged(boolean visible, int height);
    }

    // =

    /**
     * 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用
     * @param context {@link Context}
     */
    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) return;
        try {
            InputMethodManager imm = (InputMethodManager) DevUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
            for (int i = 0, len = strArr.length; i < len; i++) {
                try {
                    Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                    if (declaredField == null) continue;
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true);
                    }
                    Object object = declaredField.get(imm);
                    if (object == null || !(object instanceof View)) continue;
                    View view = (View) object;
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
        return;
    }
}