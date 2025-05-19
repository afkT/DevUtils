package afkt.project.base.project

import afkt.project.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.therouter.TheRouter
import com.therouter.router.Autowired
import dev.simple.app.base.ActivityVMType
import dev.simple.app.base.interfaces.BindingActivityView
import dev.simple.app.controller.ui.theme.ActivityUITheme
import dev.utils.DevFinal
import dev.utils.app.ViewUtils
import dev.utils.app.assist.floating.IFloatingActivity
import dev.utils.app.toast.ToastTintUtils
import dev.widget.function.StateLayout

/**
 * detail: 项目 BaseActivity
 * @author Ttt
 */
open class BaseProjectActivity<VDB : ViewDataBinding, VM : BaseProjectViewModel> :
    BaseActivity<VDB, VM>,
    IFloatingActivity {

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int = -1,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
    ) : super(
        bindLayoutId, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
    )

    constructor(
        bindLayoutView: BindingActivityView?,
        bindViewModelId: Int = -1,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
    ) : super(
        bindLayoutView, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
    )

    // ==============
    // = 内部代码开始 =
    // ==============

    // 状态布局
    lateinit var stateLayout: StateLayout

    // ToolBar
    var toolbar: Toolbar? = null

    @JvmField
    @Autowired(name = DevFinal.STR.TITLE)
    var moduleTitle: String? = null

    @JvmField
    @Autowired(name = DevFinal.STR.TYPE)
    var moduleType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            TheRouter.inject(this)
        } catch (_: Exception) {
        }
        super.onCreate(savedInstanceState)
        // 插入 StateLayout
        insertStateLayout()
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

    // ===========
    // = ToolBar =
    // ===========

    override fun createTitleBarSkeletonView(
        context: Context,
        inflater: LayoutInflater,
        owner: LifecycleOwner
    ): View? {
        return ViewUtils.inflate(this, R.layout.base_toolbar, null)?.let { titleView ->
            toolbar = titleView.findViewById(R.id.vid_tb)
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
            return titleView
        }
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

    // ============
    // = 适配器相关 =
    // ============

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
        adapterDataObserver: RecyclerView.AdapterDataObserver?,
        isRefAdapter: Boolean
    ) {
        recyclerView?.adapter?.let {
            it.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
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
                        if (::stateLayout.isInitialized) {
                            stateLayout.showEmptyData()
                        }
                    }
                    adapterDataObserver?.onChanged()
                }
            })
            // 刷新适配器
            if (isRefAdapter) it.notifyDataSetChanged()
        }
    }
}