package afkt.project.base.app

import afkt.project.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewbinding.ViewBinding
import dev.base.expand.content.DevBaseContentMVPViewBindingActivity
import dev.base.expand.mvp.MVP
import dev.other.retrofit.RxJavaManager
import dev.utils.DevFinal
import dev.utils.app.ViewUtils
import dev.utils.app.toast.ToastTintUtils
import dev.widget.function.StateLayout

/**
 * detail: Base MVP ViewBinding 基类
 * @author Ttt
 */
abstract class BaseMVPActivity<P : MVP.Presenter<out MVP.IView, out MVP.IModel>, VB : ViewBinding> :
    DevBaseContentMVPViewBindingActivity<P, VB>() {

    @JvmField // Context
    protected var mContext: Context? = null

    @JvmField // Activity
    protected var mActivity: Activity? = null

    // 状态布局
    lateinit var stateLayout: StateLayout

    // ToolBar
    var toolbar: Toolbar? = null

    override fun baseLayoutView(): View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取 Context、Activity
        mContext = this
        mActivity = this
        // 是否需要 ToolBar
        if (isToolBar()) {
            val title = intent.getStringExtra(DevFinal.TITLE)
            // 初始化 ToolBar
            initToolBar()
            // 设置标题
            setTitle(title)
        }
        // 插入 StateLayout
        insertStateLayout()
        // 初始化顺序 ( 按顺序调用方法 )
        initOrder()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消 TAG ( Activity ) 关联的请求
        RxJavaManager.getInstance().remove(TAG)
    }

    // ===========
    // = 项目相关 =
    // ===========

    /**
     * 获取 Module 类型
     * @return Module 类型
     */
    fun getModuleType(): Int {
        return intent.getIntExtra(DevFinal.TYPE, 0)
    }

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

    // =========
    // = Toast =
    // =========

    /**
     * 显示 Toast
     * @param success 是否成功样式
     */
    fun showToast(success: Boolean) {
        showToast(success, "操作成功", "操作失败")
    }

    /**
     * 显示 Toast
     * @param success     是否成功样式
     * @param successText 成功 Toast 文本
     * @param errorText   错误 Toast 文本
     */
    fun showToast(
        success: Boolean,
        successText: String?,
        errorText: String?
    ) {
        showToast(success, if (success) successText else errorText)
    }

    /**
     * 显示 Toast
     * @param success 是否成功样式
     * @param text    Toast 文本
     */
    fun showToast(
        success: Boolean,
        text: String?
    ) {
        if (success) {
            ToastTintUtils.success(text)
        } else {
            ToastTintUtils.error(text)
        }
    }

    // =============
    // = 适配器相关 =
    // =============

    /**
     * 注册 Adapter 观察者
     * @param recyclerView [RecyclerView]
     */
    fun registerAdapterDataObserver(recyclerView: RecyclerView?) {
        registerAdapterDataObserver(recyclerView, null, false)
    }

    /**
     * 注册 Adapter 观察者
     * @param recyclerView [RecyclerView]
     * @param isRefAdapter 是否刷新适配器
     */
    fun registerAdapterDataObserver(
        recyclerView: RecyclerView?,
        isRefAdapter: Boolean
    ) {
        registerAdapterDataObserver(recyclerView, null, isRefAdapter)
    }

    /**
     * 注册 Adapter 观察者
     * @param recyclerView        [RecyclerView]
     * @param adapterDataObserver Adapter 观察者
     * @param isRefAdapter        是否刷新适配器
     */
    fun registerAdapterDataObserver(
        recyclerView: RecyclerView?,
        adapterDataObserver: AdapterDataObserver?,
        isRefAdapter: Boolean
    ) {
        recyclerView?.adapter?.let {
            it.registerAdapterDataObserver(object : AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    // 获取数据总数
                    val itemCount = it.itemCount
                    // 如果为 null 特殊处理
                    ViewUtils.reverseVisibilitys(
                        itemCount != 0,
                        contentAssist.contentLinear,
                        contentAssist.stateLinear
                    )
                    // 判断是否不存在数据
                    if (itemCount == 0) {
                        stateLayout.showEmptyData()
                    }
                    adapterDataObserver?.onChanged()
                }
            })
            // 刷新适配器
            if (isRefAdapter) it.notifyDataSetChanged()
        }
    }

    // ===========
    // = ToolBar =
    // ===========

    /**
     * 是否需要 ToolBar
     */
    open fun isToolBar(): Boolean {
        return true
    }

    /**
     * 初始化 ToolBar
     */
    private fun initToolBar() {
        val titleView = ViewUtils.inflate(this, R.layout.base_toolbar, null)
        toolbar = titleView.findViewById(R.id.vid_bt_toolbar)
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
    }

    /**
     * 设置 ToolBar 标题
     * @param title 标题
     */
    fun setTitle(title: String?) {
        toolbar?.title = title
    }
}