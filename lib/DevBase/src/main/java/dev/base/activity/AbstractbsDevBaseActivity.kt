package dev.base.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import dev.base.able.IDevBase
import dev.utils.LogPrintUtils
import dev.utils.app.ActivityUtils
import dev.utils.app.DialogUtils
import dev.utils.app.toast.ToastUtils

/**
 * detail: Activity 抽象基类
 * @author Ttt
 */
abstract class AbstractbsDevBaseActivity : AppCompatActivity(), IDevBase {

    // ==========
    // = Object =
    // ==========

    @JvmField // 日志 TAG
    protected var mTag = AbstractbsDevBaseActivity::class.java.simpleName

    @JvmField // Context
    protected var mContext: Context? = null

    @JvmField // Activity
    protected var mActivity: Activity? = null

    @JvmField // Content View
    protected var mContentView: View? = null

    @JvmField // 当前页面是否可见 ( 生命周期 )
    protected var isActivityVisible = false

    // ==============
    // = UI Operate =
    // ==============

    // 基类 Dialog
    override var dialog: Dialog? = null
        protected set

    // 基类 DialogFragment
    override var dialogFragment: DialogFragment? = null
        protected set

    // 基类 PopupWindow
    override var popupWindow: PopupWindow? = null
        protected set

    // ============
    // = 生命周期 =
    // ============

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printLogPri("onCreate")
        // 标记属于可见
        isActivityVisible = true
        // 获取当前类名
        mTag = this.javaClass.simpleName
        // 获取 Context、Activity
        mContext = this
        mActivity = this
        // 保存 Activity
        ActivityUtils.getManager().addActivity(this)
        // Content View 初始化处理
        layoutInit()
        // 判断是否为 null
        if (mContentView != null) setContentView(mContentView)
    }

    override fun onStart() {
        super.onStart()
        printLogPri("onStart")
        // 标记属于可见
        isActivityVisible = true
    }

    override fun onRestart() {
        super.onRestart()
        printLogPri("onRestart")
        // 标记属于可见
        isActivityVisible = true
    }

    override fun onResume() {
        super.onResume()
        printLogPri("onResume")
        // 标记属于可见
        isActivityVisible = true
    }

    override fun onPause() {
        super.onPause()
        printLogPri("onPause")
        // 标记属于不可见
        isActivityVisible = false
    }

    override fun onStop() {
        super.onStop()
        printLogPri("onStop")
        // 标记属于不可见
        isActivityVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        printLogPri("onDestroy")
        // 标记属于不可见
        isActivityVisible = false
        // 移除当前 Activity
        ActivityUtils.getManager().removeActivity(this)
    }

    /**
     * 返回键点击触发
     * 重新实现该方法必须保留 super.onBackPressed();
     */
    override fun onBackPressed() {
        super.onBackPressed()
        printLogPri("onBackPressed")
    }

    // ============
    // = 日志处理 =
    // ============

    /**
     * 统一打印日志 ( 内部封装调用 )
     * @param message 打印内容
     */
    private fun printLogPri(message: String) {
        printLogPri(mTag, message)
    }

    /**
     * 统一打印日志 ( 内部封装调用 )
     * @param tag     日志 TAG
     * @param message 打印内容
     */
    private fun printLogPri(tag: String, message: String) {
        LogPrintUtils.dTag(
            mTag,
            String.format("%s -> %s", tag, message)
        )
    }

    // ===================
    // = IDevBaseContent =
    // ===================
    /**
     * 布局初始化处理
     */
    private fun layoutInit() {
        if (mContentView != null) return
        // 使用 contentId()
        if (contentId() != 0) {
            try {
                mContentView =
                    (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                        contentId(),
                        null
                    )
            } catch (e: Exception) {
                LogPrintUtils.eTag(mTag, e, "layoutInit - contentId")
            }
        }
        // 如果 View 等于 null, 则使用 contentView()
        if (mContentView == null) mContentView = contentView()
    }
    // ==================
    // = IDevBaseMethod =
    // ==================
    /**
     * 初始化方法顺序
     * <pre>
     * initView() => initValue() => initListener() => initOther()
    </pre> *
     */
    override fun initOrder() {
        initView()
        initValue()
        initListener()
        initOther()
    }
    // ==============
    // = 初始化方法 =
    // ==============
    /**
     * 初始化 View
     */
    override fun initView() {
        printLogPri("initView")
    }

    /**
     * 初始化参数、配置
     */
    override fun initValue() {
        printLogPri("initValue")
    }

    /**
     * 初始化事件
     */
    override fun initListener() {
        printLogPri("initListener")
    }

    /**
     * 初始化其他操作
     */
    override fun initOther() {
        printLogPri("initOther")
    }
    // =======================
    // = IDevBaseUIOperation =
    // =======================
    // =========
    // = Toast =
    // =========
    /**
     * 显示 Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    override fun showToast(text: String?, vararg formatArgs: Any) {
        ToastUtils.showShort(mContext, text, *formatArgs)
    }

    /**
     * 显示 Toast
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    override fun showToast(resId: Int, vararg formatArgs: Any) {
        ToastUtils.showShort(mContext, resId, *formatArgs)
    }
    // ===============
    // = PopupWindow =
    // ===============

    /**
     * 设置 PopupWindow
     * @param popupWindow [PopupWindow]
     * @return [PopupWindow]
     */
    override fun <T : PopupWindow?> setPopupWindow(popupWindow: T): T {
        return setPopupWindow(true, popupWindow)
    }

    /**
     * 设置 PopupWindow
     * @param isClose     是否关闭之前的 PopupWindow
     * @param popupWindow [PopupWindow]
     * @return [PopupWindow]
     */
    override fun <T : PopupWindow?> setPopupWindow(
        isClose: Boolean,
        popupWindow: T
    ): T {
        if (isClose) DialogUtils.closePopupWindow(this.popupWindow)
        this.popupWindow = popupWindow
        return popupWindow
    }
    // ==========
    // = Dialog =
    // ==========

    /**
     * 设置 Dialog
     * @param dialog [Dialog]
     * @return [Dialog]
     */
    override fun <T : Dialog?> setDialog(dialog: T): T {
        return setDialog(true, dialog)
    }

    /**
     * 设置 Dialog
     * @param isClose 是否关闭之前的 Dialog
     * @param dialog  [Dialog]
     * @return [Dialog]
     */
    override fun <T : Dialog?> setDialog(isClose: Boolean, dialog: T): T {
        if (isClose) DialogUtils.closeDialog(dialog)
        this.dialog = dialog
        return dialog
    }
    // ==================
    // = DialogFragment =
    // ==================

    /**
     * 设置 DialogFragment
     * @param dialog [DialogFragment]
     * @return [DialogFragment]
     */
    override fun <T : DialogFragment?> setDialogFragment(dialog: T): T {
        return setDialogFragment(true, dialog)
    }

    /**
     * 设置 DialogFragment
     * @param isClose 是否关闭之前的 DialogFragment
     * @param dialog  [DialogFragment]
     * @return [DialogFragment]
     */
    override fun <T : DialogFragment?> setDialogFragment(
        isClose: Boolean,
        dialog: T
    ): T {
        if (isClose) DialogUtils.closeDialog(dialogFragment)
        dialogFragment = dialog
        return dialog
    }
}