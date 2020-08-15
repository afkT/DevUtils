package dev.base.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.base.able.IDevBase
import dev.base.utils.assist.DevBaseAssist
import dev.utils.LogPrintUtils
import dev.utils.app.ActivityUtils

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

    @JvmField // DevBase 合并相同代码辅助类
    protected var mDevBaseAssist = DevBaseAssist()

    // ============
    // = 生命周期 =
    // ============

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取当前类名
        mTag = this.javaClass.simpleName
        // 设置数据
        mDevBaseAssist
            .setTag(mTag)
            .setContext(this)
            .printLog("onCreate")
            .setCurrentVisible(true)
        // 获取 Context、Activity
        mContext = this
        mActivity = this
        // 保存 Activity
        ActivityUtils.getManager().addActivity(this)
        // Content View 初始化处理
        layoutInit()
        // 设置 Content View
        mContentView.let { setContentView(it) }
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
        ActivityUtils.getManager().removeActivity(this)
    }

    /**
     * 返回键点击触发
     * 重新实现该方法必须保留 super.onBackPressed()
     */
    override fun onBackPressed() {
        super.onBackPressed()
        mDevBaseAssist.printLog("onBackPressed")
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
                        contentId(), null
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
        mDevBaseAssist.printLog("initView")
    }

    /**
     * 初始化参数、配置
     */
    override fun initValue() {
        mDevBaseAssist.printLog("initValue")
    }

    /**
     * 初始化事件
     */
    override fun initListener() {
        mDevBaseAssist.printLog("initListener")
    }

    /**
     * 初始化其他操作
     */
    override fun initOther() {
        mDevBaseAssist.printLog("initOther")
    }
}