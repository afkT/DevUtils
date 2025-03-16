package dev.base.able

import android.app.Dialog
import android.widget.PopupWindow
import androidx.fragment.app.DialogFragment

/**
 * detail: 基类 UI 交互相关操作方法
 * @author Ttt
 */
interface IDevBaseUIOperation {

    // ===============
    // = PopupWindow =
    // ===============

    /**
     * 获取 PopupWindow
     * @return [PopupWindow]
     */
    fun getDevPopupWindow(): PopupWindow?

    /**
     * 设置 PopupWindow
     * @param popupWindow [PopupWindow]
     * @return [PopupWindow]
     */
    fun <T : PopupWindow> setDevPopupWindow(popupWindow: T): T

    /**
     * 设置 PopupWindow
     * @param isClose     是否关闭之前的 PopupWindow
     * @param popupWindow [PopupWindow]
     * @return [PopupWindow]
     */
    fun <T : PopupWindow> setDevPopupWindow(
        isClose: Boolean,
        popupWindow: T
    ): T

    // ==========
    // = Dialog =
    // ==========

    /**
     * 获取 Dialog
     * @return [Dialog]
     */
    fun getDevDialog(): Dialog?

    /**
     * 设置 Dialog
     * @param dialog [Dialog]
     * @return [Dialog]
     */
    fun <T : Dialog> setDevDialog(dialog: T): T

    /**
     * 设置 Dialog
     * @param isClose 是否关闭之前的 Dialog
     * @param dialog  [Dialog]
     * @return [Dialog]
     */
    fun <T : Dialog> setDevDialog(
        isClose: Boolean,
        dialog: T
    ): T

    // ==================
    // = DialogFragment =
    // ==================

    /**
     * 获取 DialogFragment
     * @return [DialogFragment]
     */
    fun getDevDialogFragment(): DialogFragment?

    /**
     * 设置 DialogFragment
     * @param dialog [DialogFragment]
     * @return [DialogFragment]
     */
    fun <T : DialogFragment> setDevDialogFragment(dialog: T): T

    /**
     * 设置 DialogFragment
     * @param isClose 是否关闭之前的 DialogFragment
     * @param dialog  [DialogFragment]
     * @return [DialogFragment]
     */
    fun <T : DialogFragment> setDevDialogFragment(
        isClose: Boolean,
        dialog: T
    ): T
}