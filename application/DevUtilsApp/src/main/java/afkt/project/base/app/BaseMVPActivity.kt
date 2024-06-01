package afkt.project.base.app

import afkt.project.R
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.therouter.TheRouter
import com.therouter.router.Autowired
import dev.base.expand.content.DevBaseContentMVPViewBindingActivity
import dev.base.expand.mvp.MVP
import dev.utils.DevFinal
import dev.utils.app.ViewUtils
import dev.widget.function.StateLayout

/**
 * detail: Base MVP ViewBinding 基类
 * @author Ttt
 */
abstract class BaseMVPActivity<P : MVP.Presenter<out MVP.IView, out MVP.IModel>, VB : ViewBinding> :
    DevBaseContentMVPViewBindingActivity<P, VB>() {

    override fun baseLayoutView(): View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            TheRouter.inject(this)
        } catch (ignored: Exception) {
        }
        // 初始化 ToolBar
        initToolBar()
        // 插入 StateLayout
        insertStateLayout()
        // 初始化顺序 ( 按顺序调用方法 )
        initOrder()
    }

    // ======================
    // = 其他方法 ( 展示所需 ) =
    // ======================

    @JvmField
    @Autowired(name = DevFinal.STR.TITLE)
    var moduleTitle: String? = null

    // ToolBar
    var toolbar: Toolbar? = null

    // 状态布局
    lateinit var stateLayout: StateLayout

    // ===============
    // = StateLayout =
    // ===============

    /**
     * 插入 State Layout
     */
    private fun insertStateLayout() {
        stateLayout = StateLayout(this)
        // 添加 View
        contentAssist.addStateView(
            stateLayout,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
    }

    // ===========
    // = ToolBar =
    // ===========

    /**
     * 初始化 ToolBar
     */
    private fun initToolBar() {
        val titleView = ViewUtils.inflate(this, R.layout.base_toolbar, null)
        toolbar = titleView.findViewById(R.id.vid_tb)
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

        // 设置 ToolBar 标题
        toolbar?.title = moduleTitle
    }
}