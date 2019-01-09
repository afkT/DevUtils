package dev.utils.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.PopupWindow;

import dev.utils.LogPrintUtils;

/**
 * detail: Dialog 操作相关工具类
 * Created by Ttt
 */
public final class DialogUtils {

    private DialogUtils() {
    }

    // 日志 TAG
    private static final String TAG = DialogUtils.class.getSimpleName();

    // ======== Dialog 相关 ========

    /**
     * 关闭Dialog
     * @param dialog
     */
    public static void closeDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 关闭多个Dialog
     * @param dialogs
     */
    public static void closeDialogs(Dialog... dialogs) {
        if (dialogs != null && dialogs.length != 0) {
            for (int i = 0, len = dialogs.length; i < len; i++) {
                // 获取Dialog
                Dialog dialog = dialogs[i];
                // 关闭Dialog
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }
    }

    /**
     * 关闭PopupWindow
     * @param popupWindow
     */
    public static void closePopupWindow(PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 关闭多个PopupWindow
     * @param popupWindows
     */
    public static void closePopupWindows(PopupWindow... popupWindows) {
        if (popupWindows != null && popupWindows.length != 0) {
            for (int i = 0, len = popupWindows.length; i < len; i++) {
                // 获取Dialog
                PopupWindow popupWindow = popupWindows[i];
                // 关闭Dialog
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        }
    }

    // =

    /**
     * detail: Dialog 事件
     * Created by Ttt
     */
    public static abstract class DialogListener {

        /**
         * 最左边按钮点击事件
         * @param dialog
         */
        public void onLeftButton(DialogInterface dialog) {
        }

        /**
         * 最右边按钮点击事件
         * @param dialog
         */
        public abstract void onRightButton(DialogInterface dialog);

        /**
         * 关闭通知
         * @param dialog
         */
        public void onDismiss(DialogInterface dialog) {
        }
    }

    /**
     * 创建提示 Dialog (原生样式)
     * @param context
     * @param title dialog 标题
     * @param content dialog 内容
     * @param rightBtn 右边按钮文案
     * @return
     */
    public static AlertDialog createAlertDialog(Context context, String title, String content, String rightBtn) {
        return createAlertDialog(context, title, content, null, rightBtn, null);
    }

    /**
     * 创建提示 Dialog (原生样式)
     * @param context
     * @param title dialog 标题
     * @param content dialog 内容
     * @param leftBtn 左边按钮文案
     * @param rightBtn 右边按钮文案
     * @return
     */
    public static AlertDialog createAlertDialog(Context context, String title, String content, String leftBtn, String rightBtn) {
        return createAlertDialog(context, title, content, leftBtn, rightBtn, null);
    }

    /**
     * 创建提示 Dialog (原生样式)
     * @param context
     * @param title dialog 标题
     * @param content dialog 内容
     * @param rightBtn 右边按钮文案
     * @param dialogListener 事件通知
     * @return
     */
    public static AlertDialog createAlertDialog(Context context, String title, String content, String rightBtn, DialogListener dialogListener) {
        return createAlertDialog(context, title, content, null, rightBtn, dialogListener);
    }

    /**
     * 创建提示 Dialog (原生样式)
     * @param context
     * @param title dialog 标题
     * @param content dialog 内容
     * @param leftBtn 左边按钮文案
     * @param rightBtn 右边按钮文案
     * @param dialogListener 事件通知
     * @return
     */
    public static AlertDialog createAlertDialog(Context context, String title, String content, String leftBtn, String rightBtn, final DialogListener dialogListener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(content);

            if (leftBtn != null) {
                if (dialogListener == null) {
                    builder.setNegativeButton(leftBtn, null);
                } else {
                    builder.setNegativeButton(leftBtn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialogListener != null) {
                                dialogListener.onLeftButton(dialog);
                            }
                        }
                    });
                }
            }

            if (rightBtn != null) {
                if (dialogListener == null) {
                    builder.setPositiveButton(rightBtn, null);
                } else {
                    builder.setPositiveButton(rightBtn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialogListener != null) {
                                dialogListener.onRightButton(dialog);
                            }
                        }
                    });
                }
            }

            if (dialogListener != null) {
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (dialogListener != null) {
                            dialogListener.onDismiss(dialog);
                        }
                    }
                });
            }
            return builder.create();
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "createAlertDialog");
        }
        return null;
    }

    // =

    /**
     * 创建加载中 Dialog (原生样式)
     * @param context
     * @param title dialog 标题
     * @param content dialog 内容
     * @return
     */
    public static ProgressDialog createProgressDialog(Context context, String title, String content) {
        return createProgressDialog(context, title, content, false, null);
    }

    /**
     * 创建加载中 Dialog (原生样式)
     * @param context
     * @param title dialog 标题
     * @param content dialog 内容
     * @param isCancel 是否可以返回键关闭
     * @return
     */
    public static ProgressDialog createProgressDialog(Context context, String title, String content, boolean isCancel) {
        return createProgressDialog(context, title, content, isCancel, null);
    }

    /**
     * 创建加载中 Dialog (原生样式)
     * @param context
     * @param title dialog 标题
     * @param content dialog 内容
     * @param isCancel 是否可以返回键关闭
     * @return
     */
    public static ProgressDialog createProgressDialog(Context context, String title, String content, boolean isCancel, DialogInterface.OnCancelListener cancelListener) {
        try {
            ProgressDialog progressDialog = ProgressDialog.show(context, title, content, isCancel);
            progressDialog.setOnCancelListener(cancelListener);
            return progressDialog;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createProgressDialog");
        }
        return null;
    }

    // =

    /**
     * 自动关闭dialog
     * @param dialog
     * @param time
     * @param handler
     * @return
     */
    public static <T extends Dialog> T autoCloseDialog(final T dialog, final long time, Handler handler) {
        // 不为null, 并且显示中
        if (dialog != null && dialog.isShowing()) {
            if (handler != null) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                        }
                    }
                }, time);
            }
        }
        return dialog;
    }

    /**
     * 自动关闭 PopupWindow
     * @param popupWindow
     * @param time
     * @param handler
     * @return
     */
    public static <T extends PopupWindow> T autoClosePopupWindow(final T popupWindow, final long time, Handler handler) {
        // 不为null, 并且显示中
        if (popupWindow != null && popupWindow.isShowing()) {
            if (handler != null) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (popupWindow != null && popupWindow.isShowing()) {
                                popupWindow.dismiss();
                            }
                        } catch (Exception e) {
                        }
                    }
                }, time);
            }
        }
        return popupWindow;
    }
}
