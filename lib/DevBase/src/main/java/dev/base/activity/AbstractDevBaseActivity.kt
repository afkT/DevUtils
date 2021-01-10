package dev.base.activity

import android.app.Dialog
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
abstract class AbstractDevBaseActivity : AppCompatActivity(),
    IDevBase {

    // ==========
    // = Object =
    // ==========

    @JvmField // 日志 TAG ( 根据使用习惯命名大写 )
    protected var TAG = AbstractDevBaseActivity::class.java.simpleName

    @JvmField // Content View
    protected var mContentView: View? = null

    @JvmField // DevBase 合并相同代码辅助类
    protected var assist = DevBaseAssist()

    // ===========
    // = 生命周期 =
    // ===========

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取当前类名
        TAG = this.javaClass.simpleName
        // 设置数据
        assist
            .setTag(TAG)
            .setContext(this)
            .printLog("onCreate")
            .setCurrentVisible(true)
        // 记录 Activity
        if (isActivityManager()) ActivityUtils.getManager().addActivity(this)
        // Content View 初始化处理
        contentInit(LayoutInflater.from(this), null)
        // 设置 Content View
        mContentView?.let { setContentView(it) }
    }

    override fun onStart() {
        super.onStart()
        assist
            .printLog("onStart")
            .setCurrentVisible(true)
    }

    override fun onRestart() {
        super.onRestart()
        assist
            .printLog("onRestart")
            .setCurrentVisible(true)
    }

    override fun onResume() {
        super.onResume()
        assist
            .printLog("onResume")
            .setCurrentVisible(true)
    }

    override fun onPause() {
        super.onPause()
        assist
            .printLog("onPause")
            .setCurrentVisible(false)
    }

    override fun onStop() {
        super.onStop()
        assist
            .printLog("onStop")
            .setCurrentVisible(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        assist
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
        assist.printLog("onBackPressed")
    }

    // ===================
    // = IDevBaseContent =
    // ===================

    /**
     * Content View 初始化处理
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     */
    private fun contentInit(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        if (mContentView != null) return
        // 使用 baseContentId()
        if (baseContentId() != 0) {
            try {
                mContentView = inflater.inflate(baseContentId(), container, false)
            } catch (e: Exception) {
                assist.printLog(e, "contentInit - baseContentId")
            }
        }
        // 如果 View 等于 null, 则使用 baseContentView()
        if (mContentView == null) mContentView = baseContentView()
    }

    // ==================
    // = IDevBaseMethod =
    // ==================

    // =============
    // = 初始化方法 =
    // =============

    override fun initView() {
        assist.printLog("initView")
    }

    override fun initValue() {
        assist.printLog("initValue")
    }

    override fun initListener() {
        assist.printLog("initListener")
    }

    override fun initObserve() {
        assist.printLog("initObserve")
    }

    override fun initOther() {
        assist.printLog("initOther")
    }

    // =======================
    // = IDevBaseUIOperation =
    // =======================

    override fun isCurrentVisible(): Boolean {
        return assist.isCurrentVisible()
    }

    override fun showToast(
        text: String?,
        vararg formatArgs: Any
    ) {
        assist.showToast(text, formatArgs)
    }

    override fun showToast(
        resId: Int,
        vararg formatArgs: Any
    ) {
        assist.showToast(resId, formatArgs)
    }

    override fun getDevPopupWindow(): PopupWindow? {
        return assist.getDevPopupWindow()
    }

    override fun <T : PopupWindow?> setDevPopupWindow(popupWindow: T): T {
        return assist.setDevPopupWindow(popupWindow)
    }

    override fun <T : PopupWindow?> setDevPopupWindow(
        isClose: Boolean,
        popupWindow: T
    ): T {
        return assist.setDevPopupWindow(isClose, popupWindow)
    }

    override fun getDevDialog(): Dialog? {
        return assist.getDevDialog()
    }

    override fun <T : Dialog?> setDevDialog(dialog: T): T {
        return assist.setDevDialog(dialog)
    }

    override fun <T : Dialog?> setDevDialog(
        isClose: Boolean,
        dialog: T
    ): T {
        return assist.setDevDialog(isClose, dialog)
    }

    override fun getDevDialogFragment(): DialogFragment? {
        return assist.getDevDialogFragment()
    }

    override fun <T : DialogFragment?> setDevDialogFragment(dialog: T): T {
        return assist.setDevDialogFragment(dialog)
    }

    override fun <T : DialogFragment?> setDevDialogFragment(
        isClose: Boolean,
        dialog: T
    ): T {
        return assist.setDevDialogFragment(isClose, dialog)
    }
}