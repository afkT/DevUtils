package dev.base.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

    // ==========
    // = 生命周期 =
    // ==========

    override fun onCreate(savedInstanceState: Bundle?) {
        // 获取当前类名
        TAG = this.javaClass.simpleName
        // Activity onCreate 创建之前触发
        activityOnCreateBefore()
        super.onCreate(savedInstanceState)
        // 设置 TAG
        assist.setTag(TAG)
            .printLog("onCreate")
        // 添加 Activity
        if (isActivityManager()) ActivityUtils.getManager().addActivity(this)
        // Inflate View 之前触发
        beforeInflateView()
        // Content View 初始化处理
        contentInit(LayoutInflater.from(this), null)
        // 设置 Content View
        mContentView?.let {
            setContentView(it)
            // Inflate View 之后触发
            afterInflateView(it)
        }
    }

    override fun onStart() {
        super.onStart()
        assist.printLog("onStart")
    }

    override fun onRestart() {
        super.onRestart()
        assist.printLog("onRestart")
    }

    override fun onResume() {
        super.onResume()
        assist.printLog("onResume")
    }

    override fun onPause() {
        super.onPause()
        assist.printLog("onPause")
    }

    override fun onStop() {
        super.onStop()
        assist.printLog("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        assist.printLog("onDestroy")
        // 移除当前 Activity
        if (isActivityManager()) ActivityUtils.getManager().removeActivity(this)
    }

    /**
     * 返回键点击触发
     * 重新实现该方法必须保留 super.onBackPressed()
     */
    @Deprecated("deprecated onBackPressed()")
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

    // ============
    // = 初始化方法 =
    // ============

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
}