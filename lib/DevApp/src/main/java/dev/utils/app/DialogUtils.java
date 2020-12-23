package dev.utils.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.ArrayRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import dev.utils.LogPrintUtils;

/**
 * detail: Dialog 操作相关工具类
 * @author Ttt
 */
public final class DialogUtils {

    private DialogUtils() {
    }

    // 日志 TAG
    private static final String TAG = DialogUtils.class.getSimpleName();

    // ==============
    // = Dialog 相关 =
    // ==============

    /**
     * 获取 Dialog Window
     * @param dialog {@link Dialog}
     * @return {@link Window}
     */
    public static Window getWindow(final Dialog dialog) {
        return (dialog != null) ? dialog.getWindow() : null;
    }

    /**
     * 获取 Dialog Window LayoutParams
     * @param dialog {@link Dialog}
     * @return {@link WindowManager.LayoutParams}
     */
    public static WindowManager.LayoutParams getAttributes(final Dialog dialog) {
        Window window = getWindow(dialog);
        return (window != null) ? window.getAttributes() : null;
    }

    /**
     * 设置 Dialog Window LayoutParams
     * @param dialog {@link Dialog}
     * @param params {@link WindowManager.LayoutParams}
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setAttributes(
            final T dialog,
            final WindowManager.LayoutParams params
    ) {
        Window window = getWindow(dialog);
        if (window != null && params != null) {
            window.setAttributes(params);
        }
        return dialog;
    }

    // =

    /**
     * 设置 Dialog 宽度
     * @param dialog {@link Dialog}
     * @param width  宽度
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setWidth(
            final T dialog,
            final int width
    ) {
        return setWidthHeight(dialog, width, Integer.MIN_VALUE);
    }

    /**
     * 设置 Dialog 高度
     * @param dialog {@link Dialog}
     * @param height 高度
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setHeight(
            final T dialog,
            final int height
    ) {
        return setWidthHeight(dialog, Integer.MIN_VALUE, height);
    }

    /**
     * 设置 Dialog 宽度、高度
     * @param dialog {@link Dialog}
     * @param width  宽度
     * @param height 高度
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setWidthHeight(
            final T dialog,
            final int width,
            final int height
    ) {
        WindowManager.LayoutParams layoutParams = getAttributes(dialog);
        if (layoutParams != null) {
            if (width != Integer.MIN_VALUE) {
                layoutParams.width = width;
            }
            if (height != Integer.MIN_VALUE) {
                layoutParams.height = height;
            }
            setAttributes(dialog, layoutParams);
        }
        return dialog;
    }

    // =

    /**
     * 设置 Dialog X 轴坐标
     * @param dialog {@link Dialog}
     * @param x      X 轴坐标
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setX(
            final T dialog,
            final int x
    ) {
        return setXY(dialog, x, Integer.MIN_VALUE);
    }

    /**
     * 设置 Dialog Y 轴坐标
     * @param dialog {@link Dialog}
     * @param y      Y 轴坐标
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setY(
            final T dialog,
            final int y
    ) {
        return setXY(dialog, Integer.MIN_VALUE, y);
    }

    /**
     * 设置 Dialog X、Y 轴坐标
     * @param dialog {@link Dialog}
     * @param x      X 轴坐标
     * @param y      Y 轴坐标
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setXY(
            final T dialog,
            final int x,
            final int y
    ) {
        WindowManager.LayoutParams layoutParams = getAttributes(dialog);
        if (layoutParams != null) {
            if (x != Integer.MIN_VALUE) {
                layoutParams.x = x;
            }
            if (y != Integer.MIN_VALUE) {
                layoutParams.y = y;
            }
            setAttributes(dialog, layoutParams);
        }
        return dialog;
    }

    // =

    /**
     * 设置 Dialog Gravity
     * @param dialog  {@link Dialog}
     * @param gravity 重心
     * @param <T>     泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setGravity(
            final T dialog,
            final int gravity
    ) {
        WindowManager.LayoutParams layoutParams = getAttributes(dialog);
        if (layoutParams != null) {
            layoutParams.gravity = gravity;
            setAttributes(dialog, layoutParams);
        }
        return dialog;
    }

    /**
     * 设置 Dialog 透明度
     * @param dialog    {@link Dialog}
     * @param dimAmount 透明度
     * @param <T>       泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setDimAmount(
            final T dialog,
            final float dimAmount
    ) {
        WindowManager.LayoutParams layoutParams = getAttributes(dialog);
        if (layoutParams != null) {
            layoutParams.dimAmount = dimAmount;
            setAttributes(dialog, layoutParams);
        }
        return dialog;
    }

    // =

    /**
     * 设置是否允许返回键关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setCancelable(
            final T dialog,
            final boolean cancel
    ) {
        if (dialog != null) {
            // 返回键关闭
            dialog.setCancelable(cancel);
        }
        return dialog;
    }

    /**
     * 设置是否允许点击其他地方自动关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setCanceledOnTouchOutside(
            final T dialog,
            final boolean cancel
    ) {
        if (dialog != null) {
            // 点击其他地方自动关闭
            dialog.setCanceledOnTouchOutside(cancel);
        }
        return dialog;
    }

    /**
     * 设置是否允许 返回键关闭、点击其他地方自动关闭
     * @param dialog {@link Dialog}
     * @param cancel {@code true} 允许, {@code false} 不允许
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T setCancelableAndTouchOutside(
            final T dialog,
            final boolean cancel
    ) {
        if (dialog != null) {
            // 返回键关闭
            dialog.setCancelable(cancel);
            // 点击其他地方自动关闭
            dialog.setCanceledOnTouchOutside(cancel);
        }
        return dialog;
    }

    // =

    /**
     * 获取 Dialog 是否显示
     * @param dialog {@link Dialog}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isShowing(final Dialog dialog) {
        return (dialog != null && dialog.isShowing());
    }

    /**
     * 获取 Dialog 是否显示
     * @param dialogFragment {@link DialogFragment}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isShowing(final DialogFragment dialogFragment) {
        return (dialogFragment != null && dialogFragment.getDialog() != null && dialogFragment.getDialog().isShowing());
    }

    /**
     * 获取 PopupWindow 是否显示
     * @param popupWindow {@link PopupWindow}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isShowing(final PopupWindow popupWindow) {
        return (popupWindow != null && popupWindow.isShowing());
    }

    // ==============
    // = Dialog 操作 =
    // ==============

    /**
     * 显示 Dialog
     * @param dialog {@link Dialog}
     * @param <T>    泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T showDialog(final T dialog) {
        if (dialog != null && !dialog.isShowing()) {
            try {
                dialog.show();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "showDialog");
            }
        }
        return dialog;
    }

    /**
     * 关闭 Dialog
     * @param dialog {@link Dialog}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeDialog(final Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeDialog");
            }
        }
        return false;
    }

    /**
     * 关闭多个 Dialog
     * @param dialogs {@link Dialog} 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeDialogs(final Dialog... dialogs) {
        if (dialogs != null && dialogs.length != 0) {
            for (Dialog dialog : dialogs) {
                closeDialog(dialog);
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 关闭 DialogFragment
     * @param dialog {@link DialogFragment}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeDialog(final DialogFragment dialog) {
        if (dialog != null) {
            try {
                dialog.dismiss();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeDialog");
            }
        }
        return false;
    }

    /**
     * 关闭多个 DialogFragment
     * @param dialogs {@link DialogFragment} 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closeDialogs(final DialogFragment... dialogs) {
        if (dialogs != null && dialogs.length != 0) {
            for (DialogFragment dialog : dialogs) {
                closeDialog(dialog);
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 关闭 PopupWindow
     * @param popupWindow {@link PopupWindow}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closePopupWindow(final PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            try {
                popupWindow.dismiss();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closePopupWindow");
            }
        }
        return false;
    }

    /**
     * 关闭多个 PopupWindow
     * @param popupWindows {@link PopupWindow} 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean closePopupWindows(final PopupWindow... popupWindows) {
        if (popupWindows != null && popupWindows.length != 0) {
            for (PopupWindow popupWindow : popupWindows) {
                closePopupWindow(popupWindow);
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * detail: Dialog 事件
     * @author Ttt
     */
    public static abstract class DialogListener {

        /**
         * 最左边按钮点击事件
         * @param dialog {@link DialogInterface}
         */
        public void onLeftButton(DialogInterface dialog) {
        }

        /**
         * 最右边按钮点击事件
         * @param dialog {@link DialogInterface}
         */
        public abstract void onRightButton(DialogInterface dialog);

        /**
         * 关闭通知
         * @param dialog {@link DialogInterface}
         */
        public void onDismiss(DialogInterface dialog) {
        }
    }

    /**
     * 创建提示 Dialog ( 原生样式 )
     * @param context  {@link Context}
     * @param title    dialog 标题
     * @param content  dialog 内容
     * @param rightBtn 右边按钮文案
     * @return {@link AlertDialog}
     */
    public static AlertDialog createAlertDialog(
            final Context context,
            final String title,
            final String content,
            final String rightBtn
    ) {
        return createAlertDialog(context, title, content, null, rightBtn, null);
    }

    /**
     * 创建提示 Dialog ( 原生样式 )
     * @param context        {@link Context}
     * @param title          dialog 标题
     * @param content        dialog 内容
     * @param rightBtn       右边按钮文案
     * @param dialogListener 事件通知
     * @return {@link AlertDialog}
     */
    public static AlertDialog createAlertDialog(
            final Context context,
            final String title,
            final String content,
            final String rightBtn,
            final DialogListener dialogListener
    ) {
        return createAlertDialog(context, title, content, null, rightBtn, dialogListener);
    }

    /**
     * 创建提示 Dialog ( 原生样式 )
     * @param context  {@link Context}
     * @param title    dialog 标题
     * @param content  dialog 内容
     * @param leftBtn  左边按钮文案
     * @param rightBtn 右边按钮文案
     * @return {@link AlertDialog}
     */
    public static AlertDialog createAlertDialog(
            final Context context,
            final String title,
            final String content,
            final String leftBtn,
            final String rightBtn
    ) {
        return createAlertDialog(context, title, content, leftBtn, rightBtn, null);
    }

    /**
     * 创建提示 Dialog ( 原生样式 )
     * @param context        {@link Context}
     * @param title          dialog 标题
     * @param content        dialog 内容
     * @param leftBtn        左边按钮文案
     * @param rightBtn       右边按钮文案
     * @param dialogListener 事件通知
     * @return {@link AlertDialog}
     */
    public static AlertDialog createAlertDialog(
            final Context context,
            final String title,
            final String content,
            final String leftBtn,
            final String rightBtn,
            final DialogListener dialogListener
    ) {
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
                        public void onClick(
                                DialogInterface dialog,
                                int which
                        ) {
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
                        public void onClick(
                                DialogInterface dialog,
                                int which
                        ) {
                            if (dialogListener != null) {
                                dialogListener.onRightButton(dialog);
                            }
                        }
                    });
                }
            }

            if (dialogListener != null) {
                // 设置 Dialog 关闭监听
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
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createAlertDialog");
        }
        return null;
    }

    // =

    /**
     * 创建加载中 Dialog ( 原生样式 )
     * @param context {@link Context}
     * @param title   dialog 标题
     * @param content dialog 内容
     * @return {@link ProgressDialog}
     */
    public static ProgressDialog createProgressDialog(
            final Context context,
            final String title,
            final String content
    ) {
        return createProgressDialog(context, title, content, false, null);
    }

    /**
     * 创建加载中 Dialog ( 原生样式 )
     * @param context  {@link Context}
     * @param title    dialog 标题
     * @param content  dialog 内容
     * @param isCancel 是否可以返回键关闭
     * @return {@link ProgressDialog}
     */
    public static ProgressDialog createProgressDialog(
            final Context context,
            final String title,
            final String content,
            final boolean isCancel
    ) {
        return createProgressDialog(context, title, content, isCancel, null);
    }

    /**
     * 创建加载中 Dialog ( 原生样式 )
     * @param context        {@link Context}
     * @param title          dialog 标题
     * @param content        dialog 内容
     * @param isCancel       是否可以返回键关闭
     * @param cancelListener 取消事件
     * @return {@link ProgressDialog}
     */
    public static ProgressDialog createProgressDialog(
            final Context context,
            final String title,
            final String content,
            final boolean isCancel,
            final DialogInterface.OnCancelListener cancelListener
    ) {
        try {
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setTitle(title);
            dialog.setMessage(content);
            dialog.setIndeterminate(false);
            dialog.setCancelable(isCancel);
            dialog.setOnCancelListener(cancelListener);
            return dialog;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createProgressDialog");
        }
        return null;
    }

    // =

    /**
     * 自动关闭 dialog
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param <T>         泛型
     * @return {@link Dialog}
     */
    public static <T extends Dialog> T autoCloseDialog(
            final T dialog,
            final long delayMillis,
            final Handler handler
    ) {
        if (dialog != null && dialog.isShowing()) {
            if (handler != null) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeDialog(dialog);
                    }
                }, delayMillis);
            }
        }
        return dialog;
    }

    /**
     * 自动关闭 DialogFragment
     * @param dialog      {@link DialogFragment}
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param <T>         泛型
     * @return {@link Dialog}
     */
    public static <T extends DialogFragment> T autoCloseDialog(
            final T dialog,
            final long delayMillis,
            final Handler handler
    ) {
        if (dialog != null) {
            if (handler != null) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeDialog(dialog);
                    }
                }, delayMillis);
            }
        }
        return dialog;
    }

    /**
     * 自动关闭 PopupWindow
     * @param popupWindow {@link PopupWindow}
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param <T>         泛型
     * @return {@link PopupWindow}
     */
    public static <T extends PopupWindow> T autoClosePopupWindow(
            final T popupWindow,
            final long delayMillis,
            final Handler handler
    ) {
        if (popupWindow != null && popupWindow.isShowing()) {
            if (handler != null) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closePopupWindow(popupWindow);
                    }
                }, delayMillis);
            }
        }
        return popupWindow;
    }

    // ==================
    // = 单选列表 Dialog =
    // ==================

    /**
     * detail: Dialog 单选事件
     * @author Ttt
     */
    public static abstract class SingleChoiceListener {

        /**
         * 单选选中触发
         * @param dialog {@link DialogInterface}
         * @param which  选中索引
         */
        public void onSingleChoiceItems(
                DialogInterface dialog,
                int which
        ) {
        }

        /**
         * 确认选择触发
         * @param dialog {@link DialogInterface}
         */
        public void onPositiveButton(DialogInterface dialog) {
        }

        /**
         * 取消选择触发
         * @param dialog {@link DialogInterface}
         */
        public void onCancel(DialogInterface dialog) {
        }

        /**
         * 关闭通知
         * @param dialog {@link DialogInterface}
         */
        public void onDismiss(DialogInterface dialog) {
        }
    }

    // =

    /**
     * 创建单选列表样式 Dialog
     * @param context              {@link Context}
     * @param itemsId              R.arrays 数据源
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceListDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final String title,
            final Drawable icon,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener
    ) {
        return createSingleChoiceListDialog(context, itemsId, title, icon, null, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选列表样式 Dialog
     * @param context              {@link Context}
     * @param itemsId              R.arrays 数据源
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceListDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener
    ) {
        return createSingleChoiceListDialog(context, itemsId, title, icon, negativeBtnText, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选列表样式 Dialog
     * @param context              {@link Context}
     * @param itemsId              R.arrays 数据源
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @param themeResId           样式
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceListDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener,
            @StyleRes final int themeResId
    ) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setItems(itemsId, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(
                        DialogInterface dialog,
                        int which
                ) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onSingleChoiceItems(dialog, which);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!TextUtils.isEmpty(positiveBtnText)) {
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!TextUtils.isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onCancel(dialog);
                        }
                    }
                });
            }

            // 设置 Dialog 关闭监听
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onDismiss(dialog);
                    }
                }
            });
            return builder.create();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createSingleChoiceListDialog");
        }
        return null;
    }

    // =

    /**
     * 创建单选列表样式 Dialog
     * @param context              {@link Context}
     * @param items                单选文案数组
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceListDialog(
            final Context context,
            final CharSequence[] items,
            final String title,
            final Drawable icon,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener
    ) {
        return createSingleChoiceListDialog(context, items, title, icon, null, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选列表样式 Dialog
     * @param context              {@link Context}
     * @param items                单选文案数组
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceListDialog(
            final Context context,
            final CharSequence[] items,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener
    ) {
        return createSingleChoiceListDialog(context, items, title, icon, negativeBtnText, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选列表样式 Dialog
     * @param context              {@link Context}
     * @param items                单选文案数组
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @param themeResId           样式
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceListDialog(
            final Context context,
            final CharSequence[] items,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener,
            @StyleRes final int themeResId
    ) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(
                        DialogInterface dialog,
                        int which
                ) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onSingleChoiceItems(dialog, which);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!TextUtils.isEmpty(positiveBtnText)) {
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!TextUtils.isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onCancel(dialog);
                        }
                    }
                });
            }

            // 设置 Dialog 关闭监听
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onDismiss(dialog);
                    }
                }
            });
            return builder.create();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createSingleChoiceListDialog");
        }
        return null;
    }

    // ===============
    // = 单选 Dialog =
    // ===============

    /**
     * 创建单选样式 Dialog
     * @param context              {@link Context}
     * @param itemsId              R.arrays 数据源
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final int checkedItem,
            final String title,
            final Drawable icon,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener
    ) {
        return createSingleChoiceDialog(context, itemsId, checkedItem, title, icon, null, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选样式 Dialog
     * @param context              {@link Context}
     * @param itemsId              R.arrays 数据源
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final int checkedItem,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener
    ) {
        return createSingleChoiceDialog(context, itemsId, checkedItem, title, icon, negativeBtnText, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选样式 Dialog
     * @param context              {@link Context}
     * @param itemsId              R.arrays 数据源
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @param themeResId           样式
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final int checkedItem,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener,
            @StyleRes final int themeResId
    ) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setSingleChoiceItems(itemsId, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(
                        DialogInterface dialog,
                        int which
                ) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onSingleChoiceItems(dialog, which);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!TextUtils.isEmpty(positiveBtnText)) {
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!TextUtils.isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onCancel(dialog);
                        }
                    }
                });
            }

            // 设置 Dialog 关闭监听
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onDismiss(dialog);
                    }
                }
            });
            return builder.create();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createSingleChoiceDialog");
        }
        return null;
    }

    // =

    /**
     * 创建单选样式 Dialog
     * @param context              {@link Context}
     * @param items                单选文案数组
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceDialog(
            final Context context,
            final CharSequence[] items,
            final int checkedItem,
            final String title,
            final Drawable icon,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener
    ) {
        return createSingleChoiceDialog(context, items, checkedItem, title, icon, null, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选样式 Dialog
     * @param context              {@link Context}
     * @param items                单选文案数组
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceDialog(
            final Context context,
            final CharSequence[] items,
            final int checkedItem,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener
    ) {
        return createSingleChoiceDialog(context, items, checkedItem, title, icon, negativeBtnText, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选样式 Dialog
     * @param context              {@link Context}
     * @param items                单选文案数组
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标 ( 标题左侧 )
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @param themeResId           样式
     * @return {@link AlertDialog}
     */
    public static AlertDialog createSingleChoiceDialog(
            final Context context,
            final CharSequence[] items,
            final int checkedItem,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final SingleChoiceListener singleChoiceListener,
            @StyleRes final int themeResId
    ) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(
                        DialogInterface dialog,
                        int which
                ) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onSingleChoiceItems(dialog, which);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!TextUtils.isEmpty(positiveBtnText)) {
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!TextUtils.isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onCancel(dialog);
                        }
                    }
                });
            }

            // 设置 Dialog 关闭监听
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onDismiss(dialog);
                    }
                }
            });
            return builder.create();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createSingleChoiceDialog");
        }
        return null;
    }

    // ===============
    // = 多选 Dialog =
    // ===============

    /**
     * detail: Dialog 多选事件
     * @author Ttt
     */
    public static abstract class MultiChoiceListener {

        /**
         * 多选选中触发
         * @param dialog    {@link DialogInterface}
         * @param which     操作索引
         * @param isChecked 是否选中
         */
        public void onMultiChoiceItems(
                DialogInterface dialog,
                int which,
                boolean isChecked
        ) {
        }

        /**
         * 确认选择触发
         * @param dialog       {@link DialogInterface}
         * @param checkedItems 选中的数据
         */
        public void onPositiveButton(
                DialogInterface dialog,
                boolean[] checkedItems
        ) {
        }

        /**
         * 取消选择触发
         * @param dialog {@link DialogInterface}
         */
        public void onCancel(DialogInterface dialog) {
        }

        /**
         * 关闭通知
         * @param dialog {@link DialogInterface}
         */
        public void onDismiss(DialogInterface dialog) {
        }
    }

    // =

    /**
     * 创建多选样式 Dialog
     * @param context             {@link Context}
     * @param itemsId             R.arrays 数据源
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标 ( 标题左侧 )
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createMultiChoiceDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final boolean[] checkedItems,
            final String title,
            final Drawable icon,
            final String positiveBtnText,
            final MultiChoiceListener multiChoiceListener
    ) {
        return createMultiChoiceDialog(context, itemsId, checkedItems, title, icon, null, positiveBtnText, multiChoiceListener, 0);
    }

    /**
     * 创建多选样式 Dialog
     * @param context             {@link Context}
     * @param itemsId             R.arrays 数据源
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标 ( 标题左侧 )
     * @param negativeBtnText     取消按钮文案
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createMultiChoiceDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final boolean[] checkedItems,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final MultiChoiceListener multiChoiceListener
    ) {
        return createMultiChoiceDialog(context, itemsId, checkedItems, title, icon, negativeBtnText, positiveBtnText, multiChoiceListener, 0);
    }

    /**
     * 创建多选样式 Dialog
     * @param context             {@link Context}
     * @param itemsId             R.arrays 数据源
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标 ( 标题左侧 )
     * @param negativeBtnText     取消按钮文案
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @param themeResId          样式
     * @return {@link AlertDialog}
     */
    public static AlertDialog createMultiChoiceDialog(
            final Context context,
            @ArrayRes final int itemsId,
            final boolean[] checkedItems,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final MultiChoiceListener multiChoiceListener,
            @StyleRes final int themeResId
    ) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setMultiChoiceItems(itemsId, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(
                        DialogInterface dialog,
                        int which,
                        boolean isChecked
                ) {
                    if (multiChoiceListener != null) {
                        multiChoiceListener.onMultiChoiceItems(dialog, which, isChecked);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!TextUtils.isEmpty(positiveBtnText)) {
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (multiChoiceListener != null) {
                            multiChoiceListener.onPositiveButton(dialog, checkedItems);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!TextUtils.isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (multiChoiceListener != null) {
                            multiChoiceListener.onCancel(dialog);
                        }
                    }
                });
            }

            // 设置 Dialog 关闭监听
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (multiChoiceListener != null) {
                        multiChoiceListener.onDismiss(dialog);
                    }
                }
            });
            return builder.create();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createMultiChoiceDialog");
        }
        return null;
    }

    // =

    /**
     * 创建多选样式 Dialog
     * @param context             {@link Context}
     * @param items               多选文案数组
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标 ( 标题左侧 )
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createMultiChoiceDialog(
            final Context context,
            final CharSequence[] items,
            final boolean[] checkedItems,
            final String title,
            final Drawable icon,
            final String positiveBtnText,
            final MultiChoiceListener multiChoiceListener
    ) {
        return createMultiChoiceDialog(context, items, checkedItems, title, icon, null, positiveBtnText, multiChoiceListener, 0);
    }

    /**
     * 创建多选样式 Dialog
     * @param context             {@link Context}
     * @param items               多选文案数组
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标 ( 标题左侧 )
     * @param negativeBtnText     取消按钮文案
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createMultiChoiceDialog(
            final Context context,
            final CharSequence[] items,
            final boolean[] checkedItems,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final MultiChoiceListener multiChoiceListener
    ) {
        return createMultiChoiceDialog(context, items, checkedItems, title, icon, negativeBtnText, positiveBtnText, multiChoiceListener, 0);
    }

    /**
     * 创建多选样式 Dialog
     * @param context             {@link Context}
     * @param items               多选文案数组
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标 ( 标题左侧 )
     * @param negativeBtnText     取消按钮文案
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @param themeResId          样式
     * @return {@link AlertDialog}
     */
    public static AlertDialog createMultiChoiceDialog(
            final Context context,
            final CharSequence[] items,
            final boolean[] checkedItems,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final MultiChoiceListener multiChoiceListener,
            @StyleRes final int themeResId
    ) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(
                        DialogInterface dialog,
                        int which,
                        boolean isChecked
                ) {
                    if (multiChoiceListener != null) {
                        multiChoiceListener.onMultiChoiceItems(dialog, which, isChecked);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!TextUtils.isEmpty(positiveBtnText)) {
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (multiChoiceListener != null) {
                            multiChoiceListener.onPositiveButton(dialog, checkedItems);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!TextUtils.isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (multiChoiceListener != null) {
                            multiChoiceListener.onCancel(dialog);
                        }
                    }
                });
            }

            // 设置 Dialog 关闭监听
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (multiChoiceListener != null) {
                        multiChoiceListener.onDismiss(dialog);
                    }
                }
            });
            return builder.create();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createMultiChoiceDialog");
        }
        return null;
    }

    // ====================
    // = 设置 View Dialog =
    // ====================

    /**
     * detail: 自定义 View Dialog 事件
     * @author Ttt
     */
    public static abstract class ViewDialogListener {

        /**
         * 确认触发
         * @param dialog {@link DialogInterface}
         */
        public void onPositiveButton(DialogInterface dialog) {
        }

        /**
         * 取消触发
         * @param dialog {@link DialogInterface}
         */
        public void onCancel(DialogInterface dialog) {
        }

        /**
         * 关闭通知
         * @param dialog {@link DialogInterface}
         */
        public void onDismiss(DialogInterface dialog) {
        }
    }

    // =

    /**
     * 创建自定义 View 样式 Dialog
     * @param context            {@link Context}
     * @param view               {@link View}
     * @param title              标题
     * @param icon               图标 ( 标题左侧 )
     * @param positiveBtnText    确认按钮文案
     * @param viewDialogListener 自定义 View Dialog 事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createViewDialog(
            final Context context,
            final View view,
            final String title,
            final Drawable icon,
            final String positiveBtnText,
            final ViewDialogListener viewDialogListener
    ) {
        return createViewDialog(context, view, title, icon, null, positiveBtnText, viewDialogListener, 0);
    }

    /**
     * 创建自定义 View 样式 Dialog
     * @param context            {@link Context}
     * @param view               {@link View}
     * @param title              标题
     * @param icon               图标 ( 标题左侧 )
     * @param negativeBtnText    取消按钮文案
     * @param positiveBtnText    确认按钮文案
     * @param viewDialogListener 自定义 View Dialog 事件
     * @return {@link AlertDialog}
     */
    public static AlertDialog createViewDialog(
            final Context context,
            final View view,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final ViewDialogListener viewDialogListener
    ) {
        return createViewDialog(context, view, title, icon, negativeBtnText, positiveBtnText, viewDialogListener, 0);
    }

    /**
     * 创建自定义 View 样式 Dialog
     * @param context            {@link Context}
     * @param view               {@link View}
     * @param title              标题
     * @param icon               图标 ( 标题左侧 )
     * @param positiveBtnText    确认按钮文案
     * @param viewDialogListener 自定义 View Dialog 事件
     * @param themeResId         样式
     * @return {@link AlertDialog}
     */
    public static AlertDialog createViewDialog(
            final Context context,
            final View view,
            final String title,
            final Drawable icon,
            final String positiveBtnText,
            final ViewDialogListener viewDialogListener,
            @StyleRes final int themeResId
    ) {
        return createViewDialog(context, view, title, icon, null, positiveBtnText, viewDialogListener, themeResId);
    }

    /**
     * 创建自定义 View 样式 Dialog
     * @param context            {@link Context}
     * @param view               {@link View}
     * @param title              标题
     * @param icon               图标 ( 标题左侧 )
     * @param negativeBtnText    取消按钮文案
     * @param positiveBtnText    确认按钮文案
     * @param viewDialogListener 自定义 View Dialog 事件
     * @param themeResId         样式
     * @return {@link AlertDialog}
     */
    public static AlertDialog createViewDialog(
            final Context context,
            final View view,
            final String title,
            final Drawable icon,
            final String negativeBtnText,
            final String positiveBtnText,
            final ViewDialogListener viewDialogListener,
            @StyleRes final int themeResId
    ) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            if (view != null) {
                builder.setView(view);
            }
            // 判断是否存在确认按钮文案
            if (!TextUtils.isEmpty(positiveBtnText)) {
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (viewDialogListener != null) {
                            viewDialogListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!TextUtils.isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which
                    ) {
                        dialog.dismiss();
                        // 触发回调
                        if (viewDialogListener != null) {
                            viewDialogListener.onCancel(dialog);
                        }
                    }
                });
            }

            // 设置 Dialog 关闭监听
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (viewDialogListener != null) {
                        viewDialogListener.onDismiss(dialog);
                    }
                }
            });
            return builder.create();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createViewDialog");
        }
        return null;
    }
}