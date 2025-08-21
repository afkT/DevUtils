package dev.base.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import dev.base.able.IDevBase
import dev.base.databinding.BaseContentViewBinding
import dev.base.utils.assist.DevBaseAssist
import dev.base.utils.assist.DevBaseContentAssist
import dev.utils.app.ActivityUtils

/**
 * detail: Activity 抽象基类
 * @author Ttt
 * 内置 R.layout.base_content_view 作为 contentView 并对所需 View 进行 Add
 * 设计思路:
 * 全局统一使用 R.layout.base_content_view 作为根布局进行显示
 * 并且进行动态添加 title、body 等布局 View
 * 能够对全局进行增删 View 控制处理, 以及后期全局需求配置
 */
abstract class AbstractDevBaseActivity : AppCompatActivity(),
    IDevBase {

    // ==========
    // = Object =
    // ==========

    @JvmField // 日志 TAG ( 根据使用习惯命名大写 )
    protected var TAG = AbstractDevBaseActivity::class.java.simpleName

    @JvmField // DevBase 合并相同代码辅助类
    protected var assist = DevBaseAssist()

    @JvmField // DevBase ContentView 填充辅助类
    protected var contentAssist = DevBaseContentAssist()

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
        assist.setTag(TAG).printLog("onCreate")
        // 添加 Activity
        if (isActivityManager()) {
            ActivityUtils.getManager().addActivity(this)
        }
        // Inflate View 之前触发
        beforeInflateView()
        // DevBase ContentView 填充
        BaseContentViewBinding.inflate(
            layoutInflater
        ).apply {
            // 设置 Content View
            setContentView(root)
            // 设置 Binding
            contentAssist.bind(this)
            // Inflate View 之后触发
            afterInflateView(root)
            // 插入布局【具体实际布局】
            insertLayout()
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
        if (isActivityManager()) {
            ActivityUtils.getManager().removeActivity(this)
        }
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

    // ==================
    // = IDevBaseLayout =
    // ==================

    @JvmField // Layout View
    protected var layoutView: View? = null

    /**
     * Layout View 初始化处理
     * @param inflater  [LayoutInflater]
     */
    private fun layoutInit(
        inflater: LayoutInflater
    ) {
        if (layoutView != null) return
        // 使用 baseLayoutId()
        if (baseLayoutId() != 0) {
            try {
                layoutView = inflater.inflate(baseLayoutId(), null, false)
            } catch (e: Exception) {
                assist.printLog(e, "layoutInit - baseLayoutId")
            }
        }
        // 如果 View 等于 null, 则使用 baseLayoutView()
        if (layoutView == null) layoutView = baseLayoutView()
    }

    /**
     * 插入布局【具体实际布局】
     */
    private fun insertLayout() {
        // Layout View 初始化处理
        layoutInit(layoutInflater)
        // 添加到 contentLinear
        if (isLayoutMatchParent()) {
            contentAssist.addContentView(
                layoutView,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
        } else {
            contentAssist.addContentView(layoutView)
        }
    }
}