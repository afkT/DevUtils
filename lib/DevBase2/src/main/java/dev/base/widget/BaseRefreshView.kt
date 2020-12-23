package dev.base.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dev.assist.PageAssist
import dev.base.utils.assist.DevBaseRefreshAssist
import dev.base2.R

/**
 * detail: Base Refresh、Load View
 * @author Ttt
 * 通用 下拉刷新、上拉加载 封装 View
 */
class BaseRefreshView : LinearLayout {

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
    private var mAssist = DevBaseRefreshAssist<String>()

    /**
     * 默认初始化操作
     */
    private fun init() {
        orientation = VERTICAL
        val context = context
        // 初始化 View
        val view = LayoutInflater.from(context).inflate(R.layout.base_refresh_view, null)
        mBody = view.findViewById(R.id.vid_brv_frame)
        // 初始化 Refresh 数据
        mAssist
            .setRecyclerView(view.findViewById(R.id.vid_brv_recy))
            .setRefreshLayout(view.findViewById(R.id.vid_brv_refresh))
            .setRefreshHeader(ClassicsHeader(context)) // 刷新头
            .setRefreshFooter(ClassicsFooter(context)) // 刷新尾巴
            .setEnableRefresh(true) // 开启下拉刷新
            .setEnableLoadMore(true) // 开启上拉加载
        // 不需要阻尼效果
        mAssist.getRefreshLayout()?.setEnableOverScrollDrag(false)

        // 添加 View
        addView(
            view,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    // ========================
    // = DevBaseRefreshAssist =
    // ========================

    fun getAssist(): DevBaseRefreshAssist<String> {
        return mAssist
    }

    // ===========
    // = get/set =
    // ===========

    fun getBody(): FrameLayout? {
        return mBody
    }

    fun getPageAssist(): PageAssist<String> {
        return mAssist.getPageAssist()
    }

    fun setPageAssist(pageAssist: PageAssist<String>): BaseRefreshView {
        mAssist.setPageAssist(pageAssist)
        return this
    }

    fun getRefreshLayout(): SmartRefreshLayout? {
        return mAssist.getRefreshLayout()
    }

    fun setRefreshLayout(refreshLayout: SmartRefreshLayout?): BaseRefreshView {
        mAssist.setRefreshLayout(refreshLayout)
        return this
    }

    fun getRecyclerView(): RecyclerView? {
        return mAssist.getRecyclerView()
    }

    fun setRecyclerView(recyclerView: RecyclerView?): BaseRefreshView {
        mAssist.setRecyclerView(recyclerView)
        return this
    }

    fun <T : BaseQuickAdapter<*, *>?> getAdapter(): T? {
        return mAssist.getAdapter<T>()
    }

    fun setAdapter(adapter: BaseQuickAdapter<*, *>?): BaseRefreshView {
        mAssist.setAdapter(adapter)
        return this
    }

    // ===========
    // = 快捷方法 =
    // ===========

    /**
     * 设置 LayoutManager
     * @param layoutManager [LayoutManager]
     * @return [DevBaseRefreshAssist]
     */
    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager): BaseRefreshView {
        mAssist.setLayoutManager(layoutManager)
        return this
    }

    /**
     * 是否启用下拉刷新
     * @param enabled 是否启用
     * @return [DevBaseRefreshAssist]
     */
    fun setEnableRefresh(enabled: Boolean): BaseRefreshView {
        mAssist.setEnableRefresh(enabled)
        return this
    }

    /**
     * 是否启用上拉加载更多
     * @param enabled 是否启用
     * @return [DevBaseRefreshAssist]
     */
    fun setEnableLoadMore(enabled: Boolean): BaseRefreshView {
        mAssist.setEnableLoadMore(enabled)
        return this
    }

    /**
     * 设置数据全部加载完成 ( 将不能再次触发加载功能 )
     * @param noMoreData 是否有更多数据 `true` 无数据, `false` 还有数据
     * @return [DevBaseRefreshAssist]
     */
    fun setNoMoreData(noMoreData: Boolean): BaseRefreshView {
        mAssist.setNoMoreData(noMoreData)
        return this
    }

    /**
     * 设置刷新监听器
     * @param listener 刷新监听器
     * @return [DevBaseRefreshAssist]
     */
    fun setOnRefreshListener(listener: OnRefreshListener?): BaseRefreshView {
        mAssist.setOnRefreshListener(listener)
        return this
    }

    /**
     * 设置加载监听器
     * @param listener 加载监听器
     * @return [DevBaseRefreshAssist]
     */
    fun setOnLoadMoreListener(listener: OnLoadMoreListener?): BaseRefreshView {
        mAssist.setOnLoadMoreListener(listener)
        return this
    }

    /**
     * 设置刷新和加载监听器
     * @param listener 刷新、加载监听器
     * @return [DevBaseRefreshAssist]
     */
    fun setOnRefreshLoadMoreListener(listener: OnRefreshLoadMoreListener?): BaseRefreshView {
        mAssist.setOnRefreshLoadMoreListener(listener)
        return this
    }

    // =

    /**
     * 完成刷新
     * @return [DevBaseRefreshAssist]
     */
    fun finishRefresh(): BaseRefreshView {
        mAssist.finishRefresh()
        return this
    }

    /**
     * 完成加载
     * @return [DevBaseRefreshAssist]
     */
    fun finishLoadMore(): BaseRefreshView {
        mAssist.finishLoadMore()
        return this
    }

    /**
     * 完成刷新、加载
     * @return [DevBaseRefreshAssist]
     */
    fun finishRefreshAndLoad(): BaseRefreshView {
        mAssist.finishRefreshAndLoad()
        return this
    }

    /**
     * 完成刷新或加载
     * @param refresh 是否刷新
     * @return [DevBaseRefreshAssist]
     */
    fun finishRefreshOrLoad(refresh: Boolean): BaseRefreshView {
        mAssist.finishRefreshOrLoad(refresh)
        return this
    }

    // =

    /**
     * 获取头部 View 数量
     * @return 头部 View 数量
     */
    fun getHeaderLayoutCount(): Int {
        return mAssist.getHeaderLayoutCount()
    }

    /**
     * 添加头部 View
     * @param header header View
     * @return [DevBaseRefreshAssist]
     */
    fun addHeaderView(header: View): BaseRefreshView {
        mAssist.addHeaderView(header)
        return this
    }

    /**
     * 添加头部 View 到指定位置
     * @param header header View
     * @param index  index
     * @return [DevBaseRefreshAssist]
     */
    fun addHeaderView(
        header: View,
        index: Int
    ): BaseRefreshView {
        mAssist.addHeaderView(header, index)
        return this
    }

    /**
     * 移除头部 View
     * @param header header View
     * @return [DevBaseRefreshAssist]
     */
    fun removeHeaderView(header: View): BaseRefreshView {
        mAssist.removeHeaderView(header)
        return this
    }

    /**
     * 清空头部 View
     * @return [DevBaseRefreshAssist]
     */
    fun removeAllHeaderView(): BaseRefreshView {
        mAssist.removeAllHeaderView()
        return this
    }

    // =

    /**
     * 获取底部 View 数量
     * @return 底部 View 数量
     */
    fun getFooterLayoutCount(): Int {
        return mAssist.getFooterLayoutCount()
    }

    /**
     * 添加底部 View
     * @param footer footer View
     * @return [DevBaseRefreshAssist]
     */
    fun addFooterView(footer: View): BaseRefreshView {
        mAssist.addFooterView(footer)
        return this
    }

    /**
     * 添加底部 View 到指定位置
     * @param footer footer View
     * @param index  index
     * @return [DevBaseRefreshAssist]
     */
    fun addFooterView(
        footer: View,
        index: Int
    ): BaseRefreshView {
        mAssist.addFooterView(footer, index)
        return this
    }

    /**
     * 移除底部 View
     * @param footer footer View
     * @return [DevBaseRefreshAssist]
     */
    fun removeFooterView(footer: View): BaseRefreshView {
        mAssist.removeFooterView(footer)
        return this
    }

    /**
     * 清空底部 View
     * @return [DevBaseRefreshAssist]
     */
    fun removeAllFooterView(): BaseRefreshView {
        mAssist.removeAllFooterView()
        return this
    }

    /**
     * 设置指定刷新头
     * @param header RefreshHeader 刷新头
     * @return [DevBaseRefreshAssist]
     */
    fun setRefreshHeader(header: RefreshHeader): BaseRefreshView {
        mAssist.setRefreshHeader(header)
        return this
    }

    /**
     * 设置指定刷新尾巴
     * @param footer RefreshFooter 刷新尾巴
     * @return [DevBaseRefreshAssist]
     */
    fun setRefreshFooter(footer: RefreshFooter): BaseRefreshView {
        mAssist.setRefreshFooter(footer)
        return this
    }
}