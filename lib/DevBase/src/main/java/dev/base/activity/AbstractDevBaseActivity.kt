package dev.base.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import dev.base.able.IDevBase
import dev.base.utils.assist.DevBaseAssist
import dev.utils.app.ActivityUtils

/**
 * detail: Activity 抽象基类
 * @author Ttt
 */
abstract class AbstractDevBaseActivity : AppCompatActivity(), IDevBase {

    // ==========
    // = Object =
    // ==========

    @JvmField // 日志 TAG - 根据使用习惯命名大写
    protected var TAG = AbstractDevBaseActivity::class.java.simpleName

    @JvmField // Context
    protected var mContext: Context? = null

    @JvmField // Activity
    protected var mActivity: Activity? = null

    @JvmField // Content View
    protected var mContentView: View? = null

    @JvmField // DevBase 合并相同代码辅助类
    protected var mDevBaseAssist = DevBaseAssist()

    // ============
    // = 生命周期 =
    // ============

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取当前类名
        TAG = this.javaClass.simpleName
        // 设置数据
        mDevBaseAssist
            .setTag(TAG)
            .setContext(this)
            .printLog("onCreate")
            .setCurrentVisible(true)
        // 获取 Context、Activity
        mContext = this
        mActivity = this
        // 记录 Activity
        if (isActivityManager()) ActivityUtils.getManager().addActivity(this)
        // Content View 初始化处理
        contentInit(LayoutInflater.from(this), null)
        // 设置 Content View
        mContentView?.let { setContentView(it) }
    }

    override fun onStart() {
        super.onStart()
        mDevBaseAssist
            .printLog("onStart")
            .setCurrentVisible(true)
    }

    override fun onRestart() {
        super.onRestart()
        mDevBaseAssist
            .printLog("onRestart")
            .setCurrentVisible(true)
    }

    override fun onResume() {
        super.onResume()
        mDevBaseAssist
            .printLog("onResume")
            .setCurrentVisible(true)
    }

    override fun onPause() {
        super.onPause()
        mDevBaseAssist
            .printLog("onPause")
            .setCurrentVisible(false)
    }

    override fun onStop() {
        super.onStop()
        mDevBaseAssist
            .printLog("onStop")
            .setCurrentVisible(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDevBaseAssist
            .printLog("onDestroy")
            .setCurrentVisible(false)
        // 移除当前 Activity
        if (isActivityManager()) ActivityUtils.getManager().removeActivity(this)
    }

    /**
     * 返回键点击触发
     * 重新实现该方法必须保留 super.onBackPressed()
     */
    override fun onBackPressed() {
        super.onBackPressed()
        mDevBaseAssist.printLog("onBackPressed")
    }

    // ==================
    // = IDevBaseConfig =
    // ==================

    override fun isActivityManager(): Boolean {
        return true
    }

    // ===================
    // = IDevBaseContent =
    // ===================

    /**
     * 布局初始化处理
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
                mDevBaseAssist.printLog(e, "contentInit - contentId")
            }
        }
        // 如果 View 等于 null, 则使用 contentView()
        if (mContentView == null) mContentView = contentView()
    }

    // ==================
    // = IDevBaseMethod =
    // ==================

    override fun initOrder() {
        initView()
        initValue()
        initListener()
        initOther()
    }

    // ==============
    // = 初始化方法 =
    // ==============

    override fun initView() {
        mDevBaseAssist.printLog("initView")
    }

    override fun initValue() {
        mDevBaseAssist.printLog("initValue")
    }

    override fun initListener() {
        mDevBaseAssist.printLog("initListener")
    }

    override fun initOther() {
        mDevBaseAssist.printLog("initOther")
    }

    // =======================
    // = IDevBaseUIOperation =
    // =======================

    override fun isCurrentVisible(): Boolean {
        return mDevBaseAssist.isCurrentVisible()
    }

    override fun showToast(text: String?, vararg formatArgs: Any) {
        mDevBaseAssist.showToast(text, formatArgs)
    }

    override fun showToast(resId: Int, vararg formatArgs: Any) {
        mDevBaseAssist.showToast(resId, formatArgs)
    }

    override fun getDevPopupWindow(): PopupWindow? {
        return mDevBaseAssist.getDevPopupWindow()
    }

    override fun <T : PopupWindow?> setDevPopupWindow(popupWindow: T): T {
        return mDevBaseAssist.setDevPopupWindow(popupWindow)
    }

    override fun <T : PopupWindow?> setDevPopupWindow(isClose: Boolean, popupWindow: T): T {
        return mDevBaseAssist.setDevPopupWindow(isClose, popupWindow)
    }

    override fun getDevDialog(): Dialog? {
        return mDevBaseAssist.getDevDialog()
    }

    override fun <T : Dialog?> setDevDialog(dialog: T): T {
        return mDevBaseAssist.setDevDialog(dialog)
    }

    override fun <T : Dialog?> setDevDialog(isClose: Boolean, dialog: T): T {
        return mDevBaseAssist.setDevDialog(isClose, dialog)
    }

    override fun getDevDialogFragment(): DialogFragment? {
        return mDevBaseAssist.getDevDialogFragment()
    }

    override fun <T : DialogFragment?> setDevDialogFragment(dialog: T): T {
        return mDevBaseAssist.setDevDialogFragment(dialog)
    }

    override fun <T : DialogFragment?> setDevDialogFragment(isClose: Boolean, dialog: T): T {
        return mDevBaseAssist.setDevDialogFragment(isClose, dialog)
    }
}