package dev.base.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import dev.base.able.IDevBase
import dev.base.utils.assist.DevBaseAssist

/**
 * detail: Fragment 抽象基类
 * @author Ttt
 */
abstract class AbstractDevBaseFragment : Fragment(), IDevBase {

    // ==========
    // = Object =
    // ==========

    @JvmField // 日志 TAG - 根据使用习惯命名大写
    protected var TAG = AbstractDevBaseFragment::class.java.simpleName

    @JvmField // Context
    protected var mContext: Context? = null

    @JvmField // Activity
    protected var mActivity: Activity? = null

    @JvmField // Content View
    protected var mContentView: View? = null

    @JvmField // DevBase 合并相同代码辅助类
    protected var mAssist = DevBaseAssist()

    // ============
    // = 生命周期 =
    // ============

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 获取当前类名
        TAG = this.javaClass.simpleName
        // 设置数据
        mAssist
            .setTag(TAG)
            .setContext(context)
            .printLog("onAttach")
        // 获取 Context
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mAssist.printLog("onDetach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAssist.printLog("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mAssist.printLog("onCreateView")
        // 获取 Activity
        mActivity = activity

        if (mContentView != null) {
            val parent = mContentView!!.parent as ViewGroup
            // 删除已经在显示的 View 防止切回来不加载一片空白
            parent?.removeView(mContentView)
            mContentView = null
        }
        // Content View 初始化处理
        contentInit(inflater, container)
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAssist.printLog("onViewCreated")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mAssist
            .printLog("onHiddenChanged - hidden: $hidden")
            .setCurrentVisible(!hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mAssist
            .printLog("setUserVisibleHint")
            .setCurrentVisible(userVisibleHint)
    }

    override fun onStart() {
        super.onStart()
        mAssist
            .printLog("onStart")
            .setCurrentVisible(true)
    }

    override fun onResume() {
        super.onResume()
        mAssist
            .printLog("onResume")
            .setCurrentVisible(true)
    }

    override fun onPause() {
        super.onPause()
        mAssist
            .printLog("onPause")
            .setCurrentVisible(false)
    }

    override fun onStop() {
        super.onStop()
        mAssist
            .printLog("onStop")
            .setCurrentVisible(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAssist
            .printLog("onDestroyView")
            .setCurrentVisible(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mAssist
            .printLog("onDestroy")
            .setCurrentVisible(false)
    }

    // ==================
    // = IDevBaseConfig =
    // ==================

    override fun isActivityManager(): Boolean {
        return false
    }

    // ===================
    // = IDevBaseContent =
    // ===================

    /**
     * Content View 初始化处理
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     */
    private fun contentInit(inflater: LayoutInflater, container: ViewGroup?) {
        if (mContentView != null) return
        // 使用 contentId()
        if (contentId() != 0) {
            try {
                mContentView = inflater.inflate(contentId(), container, false)
            } catch (e: Exception) {
                mAssist.printLog(e, "contentInit - contentId")
            }
        }
        // 如果 View 等于 null, 则使用 contentView()
        if (mContentView == null) mContentView = contentView()
    }

    // ==================
    // = IDevBaseMethod =
    // ==================

    // ==============
    // = 初始化方法 =
    // ==============

    override fun initView() {
        mAssist.printLog("initView")
    }

    override fun initValue() {
        mAssist.printLog("initValue")
    }

    override fun initListener() {
        mAssist.printLog("initListener")
    }

    override fun initOther() {
        mAssist.printLog("initOther")
    }

    // =======================
    // = IDevBaseUIOperation =
    // =======================

    override fun isCurrentVisible(): Boolean {
        return mAssist.isCurrentVisible()
    }

    override fun showToast(text: String?, vararg formatArgs: Any) {
        mAssist.showToast(text, formatArgs)
    }

    override fun showToast(resId: Int, vararg formatArgs: Any) {
        mAssist.showToast(resId, formatArgs)
    }

    override fun getDevPopupWindow(): PopupWindow? {
        return mAssist.getDevPopupWindow()
    }

    override fun <T : PopupWindow?> setDevPopupWindow(popupWindow: T): T {
        return mAssist.setDevPopupWindow(popupWindow)
    }

    override fun <T : PopupWindow?> setDevPopupWindow(isClose: Boolean, popupWindow: T): T {
        return mAssist.setDevPopupWindow(isClose, popupWindow)
    }

    override fun getDevDialog(): Dialog? {
        return mAssist.getDevDialog()
    }

    override fun <T : Dialog?> setDevDialog(dialog: T): T {
        return mAssist.setDevDialog(dialog)
    }

    override fun <T : Dialog?> setDevDialog(isClose: Boolean, dialog: T): T {
        return mAssist.setDevDialog(isClose, dialog)
    }

    override fun getDevDialogFragment(): DialogFragment? {
        return mAssist.getDevDialogFragment()
    }

    override fun <T : DialogFragment?> setDevDialogFragment(dialog: T): T {
        return mAssist.setDevDialogFragment(dialog)
    }

    override fun <T : DialogFragment?> setDevDialogFragment(isClose: Boolean, dialog: T): T {
        return mAssist.setDevDialogFragment(isClose, dialog)
    }
}