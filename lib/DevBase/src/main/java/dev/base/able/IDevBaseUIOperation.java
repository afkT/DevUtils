package dev.base.able;

import android.app.Dialog;
import android.widget.PopupWindow;

import androidx.fragment.app.DialogFragment;

/**
 * detail: 基类 UI 交互相关操作方法
 * @author Ttt
 */
public interface IDevBaseUIOperation {

    // =========
    // = Toast =
    // =========

    /**
     * 显示 Toast
     * @param text Toast 提示文本
     * @param objs 格式化参数
     */
    void showToast(String text, Object... objs);

    /**
     * 显示 Toast
     * @param resId R.string.id
     * @param objs  格式化参数
     */
    void showToast(int resId, Object... objs);

    // ===============
    // = PopupWindow =
    // ===============

    /**
     * 获取 PopupWindow
     * @return {@link PopupWindow}
     */
    PopupWindow getPopupWindow();

    /**
     * 设置 PopupWindow
     * @param popupWindow {@link PopupWindow}
     * @return {@link PopupWindow}
     */
    <T extends PopupWindow> T setPopupWindow(T popupWindow);

    /**
     * 设置 PopupWindow
     * @param isClose     是否关闭之前的 PopupWindow
     * @param popupWindow {@link PopupWindow}
     * @return {@link PopupWindow}
     */
    <T extends PopupWindow> T setPopupWindow(boolean isClose, T popupWindow);

    // ==========
    // = Dialog =
    // ==========

    /**
     * 获取 Dialog
     * @return {@link Dialog}
     */
    Dialog getDialog();

    /**
     * 设置 Dialog
     * @param dialog {@link Dialog}
     * @return {@link Dialog}
     */
    <T extends Dialog> T setDialog(T dialog);

    /**
     * 设置 Dialog
     * @param isClose 是否关闭之前的 Dialog
     * @param dialog  {@link Dialog}
     * @return {@link Dialog}
     */
    <T extends Dialog> T setDialog(boolean isClose, T dialog);

    // ==================
    // = DialogFragment =
    // ==================

    /**
     * 获取 DialogFragment
     * @return {@link DialogFragment}
     */
    DialogFragment getDialogFragment();

    /**
     * 设置 DialogFragment
     * @param dialog {@link DialogFragment}
     * @return {@link DialogFragment}
     */
    <T extends DialogFragment> T setDialogFragment(T dialog);

    /**
     * 设置 DialogFragment
     * @param isClose 是否关闭之前的 DialogFragment
     * @param dialog  {@link DialogFragment}
     * @return {@link DialogFragment}
     */
    <T extends DialogFragment> T setDialogFragment(boolean isClose, T dialog);
}
