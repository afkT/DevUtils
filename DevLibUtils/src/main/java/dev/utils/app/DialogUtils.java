package dev.utils.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.PopupWindow;

/**
 * detail: Dialog 操作相关工具类
 * Created by Ttt
 */
public final class DialogUtils {

    private DialogUtils(){
    }

    // ======== Dialog 相关 ========

    /**
     * 关闭Dialog
     * @param dialog
     */
    public static void closeDialog(Dialog dialog){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    /**
     * 关闭多个Dialog
     * @param dialogs
     */
    public static void closeDialogs(Dialog... dialogs){
        if (dialogs != null && dialogs.length != 0){
            for (int i = 0, len = dialogs.length; i < len; i++){
                // 获取Dialog
                Dialog dialog = dialogs[i];
                // 关闭Dialog
                if (dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }
    }

    /**
     * 关闭PopupWindow
     * @param popupWindow
     */
    public static void closePopupWindow(PopupWindow popupWindow){
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    /**
     * 关闭多个PopupWindow
     * @param popupWindows
     */
    public static void closePopupWindows(PopupWindow... popupWindows){
        if (popupWindows != null && popupWindows.length != 0){
            for (int i = 0, len = popupWindows.length; i < len; i++){
                // 获取Dialog
                PopupWindow popupWindow = popupWindows[i];
                // 关闭Dialog
                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        }
    }

    // ==

    /**
     * 创建加载 Dialog
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static ProgressDialog creDialog(Context context, String title, String content){
        return creDialog(context, title, content, false);// 不可以使用返回键
    }

    /**
     * 创建加载 Dialog
     * @param context
     * @param title
     * @param content
     * @param isCancel
     * @return
     */
    public static ProgressDialog creDialog(Context context, String title, String content, boolean isCancel){
        ProgressDialog progressDialog = android.app.ProgressDialog.show(context, title, content);
        progressDialog.setCancelable(isCancel);
        return progressDialog;
    }

    /**
     * 创建自动关闭dialog
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static ProgressDialog creAutoCloseDialog(Context context, String title, String content){
        final ProgressDialog progressDialog = android.app.ProgressDialog.show(context, title, content);
        progressDialog.setCancelable(false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 显示10秒后，取消 ProgressDialog
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                try {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                }
            }
        });
        thread.start();
        return progressDialog;
    }
}
