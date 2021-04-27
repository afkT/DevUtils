package dev.base.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dev.assist.PageAssist
import dev.base.utils.assist.DevBaseRefreshAssist2
import dev.base2.databinding.BaseRefreshViewBinding

/**
 * detail: Base Refresh、Load View
 * @author Ttt
 * 通用 下拉刷新、上拉加载 封装 View
 */
class BaseRefreshView2 : LinearLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    // =

    // 最外层 View
    private var mBody: FrameLayout? = null

    // DevBase RefreshLayout 辅助类
    private var mAssist = DevBaseRefreshAssist2<Object>()

    /**
     * 默认初始化操作
     */
    private fun init() {
        orientation = VERTICAL
        val context = context

        // 初始化 View
        var binding = BaseRefreshViewBinding.inflate(LayoutInflater.from(context))
        mBody = binding.vidBrvFrame
        // 初始化 Refresh 数据
        mAssist
            .setRecyclerView(binding.vidBrvRecy)
            .setRefreshLayout(binding.vidBrvRefresh)
            .setRefreshHeader(ClassicsHeader(context)) // 刷新头
            .setRefreshFooter(ClassicsFooter(context)) // 刷新尾巴
            .setEnableRefresh(true) // 开启下拉刷新
            .setEnableLoadMore(true) // 开启上拉加载
        // 不需要阻尼效果
        mAssist.getRefreshLayout()?.setEnableOverScrollDrag(false)

        // 添加 View
        addView(
            binding.root,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    // ========================
    // = DevBaseRefreshAssist2 =
    // ========================

    fun getAssist(): DevBaseRefreshAssist2<Object> {
        return mAssist
    }

    // ===========
    // = get/set =
    // ===========

    fun getBody(): FrameLayout? {
        return mBody
    }

    fun getPageAssist(): PageAssist<Object> {
        return mAssist.getPageAssist()
    }

    fun setPageAssist(pageAssist: PageAssist<Object>): BaseRefreshView2 {
        mAssist.setPageAssist(pageAssist)
        return this
    }

    fun getRefreshLayout(): SmartRefreshLayout? {
        return mAssist.getRefreshLayout()
    }

    fun setRefreshLayout(refreshLayout: SmartRefreshLayout?): BaseRefreshView2 {
        mAssist.setRefreshLayout(refreshLayout)
        return this
    }

    fun getRecyclerView(): RecyclerView? {
        return mAssist.getRecyclerView()
    }

    fun setRecyclerView(recyclerView: RecyclerView?): BaseRefreshView2 {
        mAssist.setRecyclerView(recyclerView)
        return this
    }

    fun <T : RecyclerView.Adapter<*>?> getAdapter(): T? {
        return mAssist.getAdapter<T>()
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?): BaseRefreshView2 {
        mAssist.setAdapter(adapter)
        return this
    }

    // ===========
    // = 快捷方法 =
    // ===========

    /**
     * 设置 LayoutManager
     * @param layoutManager [LayoutManager]
     * @return [DevBaseRefreshAssist2]
     */
    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager): BaseRefreshView2 {
        mAssist.setLayoutManager(layoutManager)
        return this
    }

    /**
     * 是否启用下拉刷新
     * @param enabled 是否启用
     * @return [DevBaseRefreshAssist2]
     */
    fun setEnableRefresh(enabled: Boolean): BaseRefreshView2 {
        mAssist.setEnableRefresh(enabled)
        return this
    }

    /**
     * 是否启用上拉加载更多
     * @param enabled 是否启用
     * @return [DevBaseRefreshAssist2]
     */
    fun setEnableLoadMore(enabled: Boolean): BaseRefreshView2 {
        mAssist.setEnableLoadMore(enabled)
        return this
    }

    /**
     * 设置数据全部加载完成 ( 将不能再次触发加载功能 )
     * @param noMoreData 是否有更多数据 `true` 无数据, `false` 还有数据
     * @return [DevBaseRefreshAssist2]
     */
    fun setNoMoreData(noMoreData: Boolean): BaseRefreshView2 {
        mAssist.setNoMoreData(noMoreData)
        return this
    }

    /**
     * 设置刷新监听器
     * @param listener 刷新监听器
     * @return [DevBaseRefreshAssist2]
     */
    fun setOnRefreshListener(listener: OnRefreshListener?): BaseRefreshView2 {
        mAssist.setOnRefreshListener(listener)
        return this
    }

    /**
     * 设置加载监听器
     * @param listener 加载监听器
     * @return [DevBaseRefreshAssist2]
     */
    fun setOnLoadMoreListener(listener: OnLoadMoreListener?): BaseRefreshView2 {
        mAssist.setOnLoadMoreListener(listener)
        return this
    }

    /**
     * 设置刷新和加载监听器
     * @param listener 刷新、加载监听器
     * @return [DevBaseRefreshAssist2]
     */
    fun setOnRefreshLoadMoreListener(listener: OnRefreshLoadMoreListener?): BaseRefreshView2 {
        mAssist.setOnRefreshLoadMoreListener(listener)
        return this
    }

    // =

    /**
     * 完成刷新
     * @return [DevBaseRefreshAssist2]
     */
    fun finishRefresh(): BaseRefreshView2 {
        mAssist.finishRefresh()
        return this
    }

    /**
     * 完成加载
     * @return [DevBaseRefreshAssist2]
     */
    fun finishLoadMore(): BaseRefreshView2 {
        mAssist.finishLoadMore()
        return this
    }

    /**
     * 完成刷新、加载
     * @return [DevBaseRefreshAssist2]
     */
    fun finishRefreshAndLoad(): BaseRefreshView2 {
        mAssist.finishRefreshAndLoad()
        return this
    }

    /**
     * 完成刷新或加载
     * @param refresh 是否刷新
     * @return [DevBaseRefreshAssist2]
     */
    fun finishRefreshOrLoad(refresh: Boolean): BaseRefreshView2 {
        mAssist.finishRefreshOrLoad(refresh)
        return this
    }

    // =

    /**
     * 设置指定刷新头
     * @param header RefreshHeader 刷新头
     * @return [DevBaseRefreshAssist2]
     */
    fun setRefreshHeader(header: RefreshHeader): BaseRefreshView2 {
        mAssist.setRefreshHeader(header)
        return this
    }

    /**
     * 设置指定刷新尾巴
     * @param footer RefreshFooter 刷新尾巴
     * @return [DevBaseRefreshAssist2]
     */
    fun setRefreshFooter(footer: RefreshFooter): BaseRefreshView2 {
        mAssist.setRefreshFooter(footer)
        return this
    }
}