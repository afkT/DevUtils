package dev.base.utils.assist

import android.app.Dialog
import android.content.Context
import android.widget.PopupWindow
import androidx.fragment.app.DialogFragment
import dev.base.able.IDevBaseUIOperation
import dev.utils.LogPrintUtils
import dev.utils.app.DialogUtils
import dev.utils.app.toast.ToastUtils

/**
 * detail: DevBase 合并相同代码辅助类
 * @author Ttt
 */
class DevBaseAssist : IDevBaseUIOperation {

    // 日志 TAG
    private var mTag = DevBaseAssist::class.java.simpleName

    // Context
    private var mContext: Context? = null

    // 当前是否可见 ( 生命周期 )
    private var mCurrentVisible = false

    // 基类 PopupWindow
    private var mDevPopupWindow: PopupWindow? = null

    // 基类 Dialog
    private var mDevDialog: Dialog? = null

    // 基类 DialogFragment
    private var mDevDialogFragment: DialogFragment? = null

    // =

    fun setContext(context: Context): DevBaseAssist {
        this.mContext = context
        return this
    }

    fun setCurrentVisible(currentVisible: Boolean): DevBaseAssist {
        this.mCurrentVisible = currentVisible
        return this
    }

    fun setTag(tag: String): DevBaseAssist {
        this.mTag = tag
        return this
    }

    fun getTag(): String {
        return this.mTag
    }

    // ===========
    // = 日志处理 =
    // ===========

    /**
     * 统一打印日志 ( 内部封装调用 )
     * @param message 打印内容
     * @return [DevBaseAssist]
     */
    fun printLog(message: String): DevBaseAssist {
        LogPrintUtils.dTag(mTag, "%s -> %s", mTag, message)
        return this
    }

    /**
     * 统一打印日志 ( 内部封装调用 )
     * @param throwable 异常
     * @param message   打印内容
     * @return [DevBaseAssist]
     */
    fun printLog(
        throwable: Throwable,
        message: String
    ): DevBaseAssist {
        LogPrintUtils.eTag(mTag, throwable, message)
        return this
    }

    // =======================
    // = IDevBaseUIOperation =
    // =======================

    override fun isCurrentVisible(): Boolean {
        return this.mCurrentVisible
    }

    override fun showToast(
        text: String?,
        vararg formatArgs: Any
    ) {
        ToastUtils.showShort(mContext, text, *formatArgs)
    }

    override fun showToast(
        resId: Int,
        vararg formatArgs: Any
    ) {
        ToastUtils.showShort(mContext, resId, *formatArgs)
    }

    override fun getDevPopupWindow(): PopupWindow? {
        return mDevPopupWindow
    }

    override fun <T : PopupWindow?> setDevPopupWindow(popupWindow: T): T {
        return setDevPopupWindow(true, popupWindow)
    }

    override fun <T : PopupWindow?> setDevPopupWindow(
        isClose: Boolean,
        popupWindow: T
    ): T {
        if (isClose) DialogUtils.closePopupWindow(mDevPopupWindow)
        this.mDevPopupWindow = popupWindow
        return popupWindow
    }

    override fun getDevDialog(): Dialog? {
        return mDevDialog
    }

    override fun <T : Dialog?> setDevDialog(dialog: T): T {
        return setDevDialog(true, dialog)
    }

    override fun <T : Dialog?> setDevDialog(
        isClose: Boolean,
        dialog: T
    ): T {
        if (isClose) DialogUtils.closeDialog(mDevDialog)
        this.mDevDialog = dialog
        return dialog
    }

    override fun getDevDialogFragment(): DialogFragment? {
        return mDevDialogFragment
    }

    override fun <T : DialogFragment?> setDevDialogFragment(dialog: T): T {
        return setDevDialogFragment(true, dialog)
    }

    override fun <T : DialogFragment?> setDevDialogFragment(
        isClose: Boolean,
        dialog: T
    ): T {
        if (isClose) DialogUtils.closeDialog(mDevDialogFragment)
        this.mDevDialogFragment = dialog
        return dialog
    }
}