package dev.base.able

import android.app.Dialog
import android.widget.PopupWindow
import androidx.fragment.app.DialogFragment

/**
 * detail: 基类 UI 交互相关操作方法
 * @author Ttt
 */
interface IDevBaseUIOperation {

    // =========
    // = Toast =
    // =========

    /**
     * 显示 Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    fun showToast(text: String?, vararg formatArgs: Any)

    /**
     * 显示 Toast
     * @param resId       R.string.id
     * @param formatArgs  格式化参数
     */
    fun showToast(resId: Int, vararg formatArgs: Any)

    // ===============
    // = PopupWindow =
    // ===============

    /**
     * 获取 PopupWindow
     * @return [PopupWindow]
     */
    val popupWindow: PopupWindow?

    /**
     * 设置 PopupWindow
     * @param popupWindow [PopupWindow]
     * @return [PopupWindow]
     */
    fun <T : PopupWindow?> setPopupWindow(popupWindow: T): T

    /**
     * 设置 PopupWindow
     * @param isClose     是否关闭之前的 PopupWindow
     * @param popupWindow [PopupWindow]
     * @return [PopupWindow]
     */
    fun <T : PopupWindow?> setPopupWindow(isClose: Boolean, popupWindow: T): T

    // ==========
    // = Dialog =
    // ==========

    /**
     * 获取 Dialog
     * @return [Dialog]
     */
    val dialog: Dialog?

    /**
     * 设置 Dialog
     * @param dialog [Dialog]
     * @return [Dialog]
     */
    fun <T : Dialog?> setDialog(dialog: T): T

    /**
     * 设置 Dialog
     * @param isClose 是否关闭之前的 Dialog
     * @param dialog  [Dialog]
     * @return [Dialog]
     */
    fun <T : Dialog?> setDialog(isClose: Boolean, dialog: T): T

    // ==================
    // = DialogFragment =
    // ==================

    /**
     * 获取 DialogFragment
     * @return [DialogFragment]
     */
    val dialogFragment: DialogFragment?

    /**
     * 设置 DialogFragment
     * @param dialog [DialogFragment]
     * @return [DialogFragment]
     */
    fun <T : DialogFragment?> setDialogFragment(dialog: T): T

    /**
     * 设置 DialogFragment
     * @param isClose 是否关闭之前的 DialogFragment
     * @param dialog  [DialogFragment]
     * @return [DialogFragment]
     */
    fun <T : DialogFragment?> setDialogFragment(isClose: Boolean, dialog: T): T
}