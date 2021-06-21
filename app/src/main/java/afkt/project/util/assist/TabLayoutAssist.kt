package afkt.project.util.assist

import afkt.project.R
import afkt.project.model.item.TabItem
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dev.base.widget.BaseTextView
import dev.utils.app.HandlerUtils
import dev.utils.app.helper.ViewHelper
import java.util.*

/**
 * detail: TabLayout 辅助类
 * @author Ttt
 */
class TabLayoutAssist private constructor(
    // TabLayout
    private var mTabLayout: TabLayout
) {

    // 日志 TAG
    private val TAG = TabLayoutAssist::class.java.simpleName

    // 数据源
    private val mListTabs: MutableList<TabItem> = ArrayList()

    // Tab 切换事件
    private var mListener: TabChangeListener? = null

    companion object {

        /**
         * 获取 TabLayout 辅助类
         * @param tabLayout [TabLayout]
         * @return [TabLayoutAssist]
         */
        @kotlin.jvm.JvmStatic
        operator fun get(tabLayout: TabLayout?): TabLayoutAssist? {
            return tabLayout?.let { return TabLayoutAssist(it) }
        }
    }

    init {
        // Tab 切换选择事件
        mTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateTabTextView(tab, true)
                // 触发回调
                mListener?.onTabChange(mListTabs[tab.position], tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                updateTabTextView(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取选中的 Tab 索引
     * @return 选中的 Tab 索引
     */
    val selectedTabPosition: Int
        get() = mTabLayout.selectedTabPosition

    /**
     * 获取 Tab 总数
     * @return Tab 总数
     */
    val tabCount: Int
        get() = mTabLayout.tabCount

    /**
     * 设置选中索引
     * @param position 选中索引
     * @return [TabLayoutAssist]
     */
    fun setSelect(position: Int): TabLayoutAssist {
        return setSelect(position, true)
    }

    /**
     * 设置选中
     * @param position 选中索引
     * @param isScroll 是否滑动
     * @return [TabLayoutAssist]
     */
    fun setSelect(
        position: Int,
        isScroll: Boolean
    ): TabLayoutAssist {
        mTabLayout.getTabAt(position)?.let { tab ->
            tab.select()
            // 切换选中状态
            updateTabTextView(tab, true)
        }
        // 滑动处理
        if (isScroll) scrollTab(selectedTabPosition)
        return this
    }

    /**
     * 设置数据操作
     * @param listTabs Tab Item 集合
     * @return [TabLayoutAssist]
     */
    fun setListTabs(
        listTabs: List<TabItem>?,
        listener: TabChangeListener?
    ): TabLayoutAssist {
        this.mListener = listener
        this.mListTabs.clear()
        listTabs?.let { this.mListTabs.addAll(it) }
        // 初始化操作
        addTabViews()
        return this
    }

    // ==============
    // = Tab 切换事件 =
    // ==============

    /**
     * detail: Tab 切换事件
     * @author Ttt
     */
    interface TabChangeListener {
        fun onTabChange(
            tabItem: TabItem,
            pos: Int
        )
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 添加 Tab View 集合
     */
    private fun addTabViews() {
        mTabLayout.removeAllTabs()
        // 循环添加 Tab
        for (i in 0 until mListTabs.size) {
            val tab = mTabLayout.newTab()
            tab.customView = createTabView(i)
            mTabLayout.addTab(tab)
        }
    }

    /**
     * 获取单个 TabView
     * @param position 索引
     * @return Tab [View]
     */
    private fun createTabView(position: Int): View {
        val view = LayoutInflater.from(mTabLayout.context).inflate(R.layout.tab_item_view, null)
        val textView: BaseTextView = view.findViewById(R.id.vid_tiv_tv)
        textView.text = mListTabs[position].title
        return view
    }

    /**
     * 改变 Tab TextView 选中状态
     * @param tab      Tab
     * @param isSelect 是否选中
     */
    private fun updateTabTextView(
        tab: TabLayout.Tab?,
        isSelect: Boolean
    ) {
        tab?.customView?.let { view ->
            ViewHelper.get()
                .setBold(view.findViewById(R.id.vid_tiv_tv), isSelect)
                .setVisibility(isSelect, view.findViewById(R.id.vid_tiv_line))
        }
    }

    /**
     * 滑动 Tab 处理
     * @param pos 需要滑动的索引
     */
    private fun scrollTab(pos: Int) {
        // 延时移动
        HandlerUtils.postRunnable({
            var x = 0
            // 循环遍历
            for (i in 1 until pos) {
                mTabLayout.getTabAt(i)?.customView?.let {
                    // 累加宽度
                    x += it.width
                }
            }
            // 开始移动位置
            mTabLayout.scrollTo(x, 0)
        }, 100)
    }
}