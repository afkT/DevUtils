package afkt.jpush.base

import afkt.jpush.R
import afkt.jpush.router.PushRouterChecker
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import dev.base.expand.content.DevBaseContentViewBindingActivity
import dev.utils.app.ViewUtils

abstract class BaseActivity<VB : ViewBinding> : DevBaseContentViewBindingActivity<VB>() {

    override fun baseLayoutView(): View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 内部初始化
        priInitialize()

        // 是否需要 ToolBar
        if (isToolBar()) initToolBar()

        initOrder()
    }

    override fun onResume() {
        super.onResume()

        // 检查推送路由
        PushRouterChecker.checker(this, this.javaClass.simpleName)
    }

    // ===========
    // = ToolBar =
    // ===========

    /**
     * 是否需要 ToolBar
     */
    open fun isToolBar(): Boolean = false

    /**
     * 初始化 ToolBar
     */
    private fun initToolBar() {
        val titleView = ViewUtils.inflate(this, R.layout.base_toolbar, null)
        val toolbar: Toolbar? = titleView.findViewById(R.id.vid_bt_toolbar)
        contentAssist.addTitleView(titleView)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            // 给左上角图标的左边加上一个返回的图标
            it.setDisplayHomeAsUpEnabled(true)
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            it.setDisplayShowTitleEnabled(false)
        }
        // 设置点击事件
        toolbar?.setNavigationOnClickListener { finish() }
        // 设置标题
        toolbar?.title = TAG
    }

    // =============
    // = 内部初始化 =
    // =============

    private fun priInitialize() {
        try {
            ARouter.getInstance().inject(this)
        } catch (e: Exception) {
        }
    }

    // =======
    // = 通用 =
    // =======

    /**
     * ARouter 跳转方法
     * @param path 跳转路径
     */
    fun routerActivity(
        path: String
    ) {
        ARouter.getInstance().build(path)
            .navigation(this)
    }
}