package dev.utils.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.PopupWindow;

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

    // ===============
    // = Dialog 相关 =
    // ===============

    /**
     * 显示 Dialog
     * @param dialog
     * @return
     */
    public static Dialog showDialog(final Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
        return dialog;
    }

    /**
     * 关闭 Dialog
     * @param dialog
     */
    public static void closeDialog(final Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 关闭多个 Dialog
     * @param dialogs
     */
    public static void closeDialogs(final Dialog... dialogs) {
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
     * 关闭 PopupWindow
     * @param popupWindow
     */
    public static void closePopupWindow(final PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 关闭多个 PopupWindow
     * @param popupWindows
     */
    public static void closePopupWindows(final PopupWindow... popupWindows) {
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
     * @author Ttt
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
     * @param title    dialog 标题
     * @param content  dialog 内容
     * @param rightBtn 右边按钮文案
     * @return
     */
    public static AlertDialog createAlertDialog(final Context context, final String title, final String content, final String rightBtn) {
        return createAlertDialog(context, title, content, null, rightBtn, null);
    }

    /**
     * 创建提示 Dialog (原生样式)
     * @param context
     * @param title    dialog 标题
     * @param content  dialog 内容
     * @param leftBtn  左边按钮文案
     * @param rightBtn 右边按钮文案
     * @return
     */
    public static AlertDialog createAlertDialog(final Context context, final String title, final String content, final String leftBtn, final String rightBtn) {
        return createAlertDialog(context, title, content, leftBtn, rightBtn, null);
    }

    /**
     * 创建提示 Dialog (原生样式)
     * @param context
     * @param title          dialog 标题
     * @param content        dialog 内容
     * @param rightBtn       右边按钮文案
     * @param dialogListener 事件通知
     * @return
     */
    public static AlertDialog createAlertDialog(final Context context, final String title, final String content, final String rightBtn, final DialogListener dialogListener) {
        return createAlertDialog(context, title, content, null, rightBtn, dialogListener);
    }

    /**
     * 创建提示 Dialog (原生样式)
     * @param context
     * @param title          dialog 标题
     * @param content        dialog 内容
     * @param leftBtn        左边按钮文案
     * @param rightBtn       右边按钮文案
     * @param dialogListener 事件通知
     * @return
     */
    public static AlertDialog createAlertDialog(final Context context, final String title, final String content, final String leftBtn, final String rightBtn, final DialogListener dialogListener) {
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
     * 创建加载中 Dialog (原生样式)
     * @param context
     * @param title   dialog 标题
     * @param content dialog 内容
     * @return
     */
    public static ProgressDialog createProgressDialog(final Context context, final String title, final String content) {
        return createProgressDialog(context, title, content, false, null);
    }

    /**
     * 创建加载中 Dialog (原生样式)
     * @param context
     * @param title    dialog 标题
     * @param content  dialog 内容
     * @param isCancel 是否可以返回键关闭
     * @return
     */
    public static ProgressDialog createProgressDialog(final Context context, final String title, final String content, final boolean isCancel) {
        return createProgressDialog(context, title, content, isCancel, null);
    }

    /**
     * 创建加载中 Dialog (原生样式)
     * @param context
     * @param title    dialog 标题
     * @param content  dialog 内容
     * @param isCancel 是否可以返回键关闭
     * @return
     */
    public static ProgressDialog createProgressDialog(final Context context, final String title, final String content, final boolean isCancel, final DialogInterface.OnCancelListener cancelListener) {
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
     * 自动关闭dialog
     * @param dialog
     * @param time
     * @param handler
     * @return
     */
    public static <T extends Dialog> T autoCloseDialog(final T dialog, final long time, final Handler handler) {
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
    public static <T extends PopupWindow> T autoClosePopupWindow(final T popupWindow, final long time, final Handler handler) {
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

    // ==================
    // = 单选列表Dialog =
    // ==================

    /**
     * detail: Dialog 单选事件
     * @author Ttt
     */
    public static abstract class SingleChoiceListener {

        /**
         * 单选选中触发
         * @param dialog
         * @param which  选中索引
         */
        public void onSingleChoiceItems(DialogInterface dialog, int which) {
        }

        /**
         * 确认选择触发
         * @param dialog
         */
        public void onPositiveButton(DialogInterface dialog) {
        }

        /**
         * 取消选择触发
         * @param dialog
         */
        public void onCancel(DialogInterface dialog) {
        }

        /**
         * 关闭通知
         * @param dialog
         */
        public void onDismiss(DialogInterface dialog) {
        }
    }

    // =

    /**
     * 创建单选列表样式 Dialog
     * @param context
     * @param itemsId              R.arrays 数据源
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return
     */
    public static AlertDialog createSingleChoiceListDialog(final Context context, @ArrayRes final int itemsId, final String title, final Drawable icon,
                                                           final String positiveBtnText, final SingleChoiceListener singleChoiceListener) {
        return createSingleChoiceListDialog(context, itemsId, title, icon, null, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选列表样式 Dialog
     * @param context
     * @param itemsId              R.arrays 数据源
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return
     */
    public static AlertDialog createSingleChoiceListDialog(final Context context, @ArrayRes final int itemsId, final String title, final Drawable icon,
                                                           final String negativeBtnText, final String positiveBtnText, final SingleChoiceListener singleChoiceListener) {
        return createSingleChoiceListDialog(context, itemsId, title, icon, negativeBtnText, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选列表样式 Dialog
     * @param itemsId              R.arrays 数据源
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @param themeResId           样式
     * @return
     */
    public static AlertDialog createSingleChoiceListDialog(final Context context, @ArrayRes final int itemsId, final String title, final Drawable icon,
                                                           final String negativeBtnText, final String positiveBtnText, final SingleChoiceListener singleChoiceListener, @StyleRes final int themeResId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setItems(itemsId, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onSingleChoiceItems(dialog, which);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!isEmpty(positiveBtnText)) {
                // 设置确认选择按钮
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
     * @param context
     * @param items                单选文案数组
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return
     */
    public static AlertDialog createSingleChoiceListDialog(final Context context, final CharSequence[] items, final String title, final Drawable icon,
                                                           final String positiveBtnText, final SingleChoiceListener singleChoiceListener) {
        return createSingleChoiceListDialog(context, items, title, icon, null, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选列表样式 Dialog
     * @param context
     * @param items                单选文案数组
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return
     */
    public static AlertDialog createSingleChoiceListDialog(final Context context, final CharSequence[] items, final String title, final Drawable icon,
                                                           final String negativeBtnText, final String positiveBtnText, final SingleChoiceListener singleChoiceListener) {
        return createSingleChoiceListDialog(context, items, title, icon, negativeBtnText, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选列表样式 Dialog
     * @param context
     * @param items                单选文案数组
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @param themeResId           样式
     * @return
     */
    public static AlertDialog createSingleChoiceListDialog(final Context context, final CharSequence[] items, final String title, final Drawable icon,
                                                           final String negativeBtnText, final String positiveBtnText, final SingleChoiceListener singleChoiceListener, @StyleRes final int themeResId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onSingleChoiceItems(dialog, which);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!isEmpty(positiveBtnText)) {
                // 设置确认选择按钮
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    // ==============
    // = 单选Dialog =
    // ==============

    /**
     * 创建单选样式 Dialog
     * @param context
     * @param itemsId              R.arrays 数据源
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return
     */
    public static AlertDialog createSingleChoiceDialog(final Context context, final @ArrayRes int itemsId, final int checkedItem, final String title, final Drawable icon,
                                                       final String positiveBtnText, final SingleChoiceListener singleChoiceListener) {
        return createSingleChoiceDialog(context, itemsId, checkedItem, title, icon, null, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选样式 Dialog
     * @param context
     * @param itemsId              R.arrays 数据源
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return
     */
    public static AlertDialog createSingleChoiceDialog(final Context context, final @ArrayRes int itemsId, final int checkedItem, final String title, final Drawable icon,
                                                       final String negativeBtnText, final String positiveBtnText, final SingleChoiceListener singleChoiceListener) {
        return createSingleChoiceDialog(context, itemsId, checkedItem, title, icon, negativeBtnText, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选样式 Dialog
     * @param itemsId              R.arrays 数据源
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @param themeResId           样式
     * @return
     */
    public static AlertDialog createSingleChoiceDialog(final Context context, final @ArrayRes int itemsId, final int checkedItem, final String title, final Drawable icon,
                                                       final String negativeBtnText, final String positiveBtnText, final SingleChoiceListener singleChoiceListener, @StyleRes final int themeResId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setSingleChoiceItems(itemsId, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onSingleChoiceItems(dialog, which);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!isEmpty(positiveBtnText)) {
                // 设置确认选择按钮
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
     * @param context
     * @param items                单选文案数组
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return
     */
    public static AlertDialog createSingleChoiceDialog(final Context context, final CharSequence[] items, final int checkedItem, final String title, final Drawable icon,
                                                       final String positiveBtnText, final SingleChoiceListener singleChoiceListener) {
        return createSingleChoiceDialog(context, items, checkedItem, title, icon, null, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选样式 Dialog
     * @param context
     * @param items                单选文案数组
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @return
     */
    public static AlertDialog createSingleChoiceDialog(final Context context, final CharSequence[] items, final int checkedItem, final String title, final Drawable icon,
                                                       final String negativeBtnText, final String positiveBtnText, final SingleChoiceListener singleChoiceListener) {
        return createSingleChoiceDialog(context, items, checkedItem, title, icon, negativeBtnText, positiveBtnText, singleChoiceListener, 0);
    }

    /**
     * 创建单选样式 Dialog
     * @param context
     * @param items                单选文案数组
     * @param checkedItem          默认选中索引
     * @param title                标题
     * @param icon                 图标(标题左侧)
     * @param negativeBtnText      取消按钮文案
     * @param positiveBtnText      确认按钮文案
     * @param singleChoiceListener 单选事件
     * @param themeResId           样式
     * @return
     */
    public static AlertDialog createSingleChoiceDialog(final Context context, final CharSequence[] items, final int checkedItem, final String title, final Drawable icon,
                                                       final String negativeBtnText, final String positiveBtnText, final SingleChoiceListener singleChoiceListener, @StyleRes final int themeResId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (singleChoiceListener != null) {
                        singleChoiceListener.onSingleChoiceItems(dialog, which);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!isEmpty(positiveBtnText)) {
                // 设置确认选择按钮
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 触发回调
                        if (singleChoiceListener != null) {
                            singleChoiceListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    // ==============
    // = 多选Dialog =
    // ==============

    /**
     * detail: Dialog 多选事件
     * @author Ttt
     */
    public static abstract class MultiChoiceListener {

        /**
         * 多选选中触发
         * @param dialog
         * @param which     操作索引
         * @param isChecked 是否选中
         */
        public void onMultiChoiceItems(DialogInterface dialog, int which, boolean isChecked) {
        }

        /**
         * 确认选择触发
         * @param dialog
         * @param checkedItems 选中的数据
         */
        public void onPositiveButton(DialogInterface dialog, boolean[] checkedItems) {
        }

        /**
         * 取消选择触发
         * @param dialog
         */
        public void onCancel(DialogInterface dialog) {
        }

        /**
         * 关闭通知
         * @param dialog
         */
        public void onDismiss(DialogInterface dialog) {
        }
    }

    // =

    /**
     * 创建多选样式 Dialog
     * @param context
     * @param itemsId             R.arrays 数据源
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标(标题左侧)
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @return
     */
    public static AlertDialog createMultiChoiceDialog(final Context context, final @ArrayRes int itemsId, final boolean[] checkedItems, final String title, final Drawable icon,
                                                      final String positiveBtnText, final MultiChoiceListener multiChoiceListener) {
        return createMultiChoiceDialog(context, itemsId, checkedItems, title, icon, null, positiveBtnText, multiChoiceListener, 0);
    }

    /**
     * 创建多选样式 Dialog
     * @param context
     * @param itemsId             R.arrays 数据源
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标(标题左侧)
     * @param negativeBtnText     取消按钮文案
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @return
     */
    public static AlertDialog createMultiChoiceDialog(final Context context, final @ArrayRes int itemsId, final boolean[] checkedItems, final String title, final Drawable icon,
                                                      final String negativeBtnText, final String positiveBtnText, final MultiChoiceListener multiChoiceListener) {
        return createMultiChoiceDialog(context, itemsId, checkedItems, title, icon, negativeBtnText, positiveBtnText, multiChoiceListener, 0);
    }

    /**
     * 创建多选样式 Dialog
     * @param context
     * @param itemsId             R.arrays 数据源
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标(标题左侧)
     * @param negativeBtnText     取消按钮文案
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @param themeResId          样式
     * @return
     */
    public static AlertDialog createMultiChoiceDialog(final Context context, final @ArrayRes int itemsId, final boolean[] checkedItems, final String title, final Drawable icon,
                                                      final String negativeBtnText, final String positiveBtnText, final MultiChoiceListener multiChoiceListener, final @StyleRes int themeResId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setMultiChoiceItems(itemsId, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (multiChoiceListener != null) {
                        multiChoiceListener.onMultiChoiceItems(dialog, which, isChecked);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!isEmpty(positiveBtnText)) {
                // 设置确认选择按钮
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 触发回调
                        if (multiChoiceListener != null) {
                            multiChoiceListener.onPositiveButton(dialog, checkedItems);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
     * @param context
     * @param items               多选文案数组
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标(标题左侧)
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @return
     */
    public static AlertDialog createMultiChoiceDialog(final Context context, final CharSequence[] items, final boolean[] checkedItems, final String title, final Drawable icon,
                                                      final String positiveBtnText, final MultiChoiceListener multiChoiceListener) {
        return createMultiChoiceDialog(context, items, checkedItems, title, icon, null, positiveBtnText, multiChoiceListener, 0);
    }

    /**
     * 创建多选样式 Dialog
     * @param context
     * @param items               多选文案数组
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标(标题左侧)
     * @param negativeBtnText     取消按钮文案
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @return
     */
    public static AlertDialog createMultiChoiceDialog(final Context context, final CharSequence[] items, final boolean[] checkedItems, final String title, final Drawable icon,
                                                      final String negativeBtnText, final String positiveBtnText, final MultiChoiceListener multiChoiceListener) {
        return createMultiChoiceDialog(context, items, checkedItems, title, icon, negativeBtnText, positiveBtnText, multiChoiceListener, 0);
    }

    /**
     * 创建多选样式 Dialog
     * @param context
     * @param items               多选文案数组
     * @param checkedItems        选中状态
     * @param title               标题
     * @param icon                图标(标题左侧)
     * @param negativeBtnText     取消按钮文案
     * @param positiveBtnText     确认按钮文案
     * @param multiChoiceListener 多选事件
     * @param themeResId          样式
     * @return
     */
    public static AlertDialog createMultiChoiceDialog(final Context context, final CharSequence[] items, final boolean[] checkedItems, final String title, final Drawable icon,
                                                      final String negativeBtnText, final String positiveBtnText, final MultiChoiceListener multiChoiceListener, final @StyleRes int themeResId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (multiChoiceListener != null) {
                        multiChoiceListener.onMultiChoiceItems(dialog, which, isChecked);
                    }
                }
            });

            // 判断是否存在确认按钮文案
            if (!isEmpty(positiveBtnText)) {
                // 设置确认选择按钮
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 触发回调
                        if (multiChoiceListener != null) {
                            multiChoiceListener.onPositiveButton(dialog, checkedItems);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    // ===================
    // = 设置View Dialog =
    // ===================

    /**
     * detail: 自定义 View Dialog 事件
     * @author Ttt
     */
    public static abstract class ViewDialogListener {

        /**
         * 确认触发
         * @param dialog
         */
        public void onPositiveButton(DialogInterface dialog) {
        }

        /**
         * 取消触发
         * @param dialog
         */
        public void onCancel(DialogInterface dialog) {
        }

        /**
         * 关闭通知
         * @param dialog
         */
        public void onDismiss(DialogInterface dialog) {
        }
    }

    // =

    /**
     * 创建自定义 View 样式 Dialog
     * @param context
     * @param view
     * @param title              标题
     * @param icon               图标(标题左侧)
     * @param positiveBtnText    确认按钮文案
     * @param viewDialogListener
     * @return
     */
    public static AlertDialog createViewDialog(final Context context, final View view, final String title, final Drawable icon, final String positiveBtnText, final ViewDialogListener viewDialogListener) {
        return createViewDialog(context, view, title, icon, null, positiveBtnText, viewDialogListener, 0);
    }

    /**
     * 创建自定义 View 样式 Dialog
     * @param context
     * @param view
     * @param title              标题
     * @param icon               图标(标题左侧)
     * @param negativeBtnText    取消按钮文案
     * @param positiveBtnText    确认按钮文案
     * @param viewDialogListener
     * @return
     */
    public static AlertDialog createViewDialog(final Context context, final View view, final String title, final Drawable icon,
                                               final String negativeBtnText, final String positiveBtnText, final ViewDialogListener viewDialogListener) {
        return createViewDialog(context, view, title, icon, negativeBtnText, positiveBtnText, viewDialogListener, 0);
    }

    /**
     * 创建自定义 View 样式 Dialog
     * @param context
     * @param view
     * @param title              标题
     * @param icon               图标(标题左侧)
     * @param positiveBtnText    确认按钮文案
     * @param viewDialogListener
     * @param themeResId         样式
     * @return
     */
    public static AlertDialog createViewDialog(final Context context, final View view, final String title, final Drawable icon, final String positiveBtnText,
                                               final ViewDialogListener viewDialogListener, @StyleRes final int themeResId) {
        return createViewDialog(context, view, title, icon, null, positiveBtnText, viewDialogListener, themeResId);
    }

    /**
     * 创建自定义 View 样式 Dialog
     * @param context
     * @param view
     * @param title              标题
     * @param icon               图标(标题左侧)
     * @param negativeBtnText    取消按钮文案
     * @param positiveBtnText    确认按钮文案
     * @param viewDialogListener
     * @param themeResId         样式
     * @return
     */
    public static AlertDialog createViewDialog(final Context context, final View view, final String title, final Drawable icon,
                                               final String negativeBtnText, final String positiveBtnText, final ViewDialogListener viewDialogListener, @StyleRes final int themeResId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
            if (!isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon != null) {
                builder.setIcon(icon);
            }
            if (view != null) {
                builder.setView(view);
            }
            // 判断是否存在确认按钮文案
            if (!isEmpty(positiveBtnText)) {
                // 设置确认选择按钮
                builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 触发回调
                        if (viewDialogListener != null) {
                            viewDialogListener.onPositiveButton(dialog);
                        }
                    }
                });
            }

            // 判断是否存在取消按钮文案
            if (!isEmpty(negativeBtnText)) {
                builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    // =

    /**
     * 判断是否为 null
     * @param str
     * @return {@code true} is null, {@code false} not null
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }
}
