package afkt.project.util.assist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.model.item.TabItem;
import dev.base.widget.BaseTextView;
import dev.engine.log.DevLogEngine;
import dev.utils.app.HandlerUtils;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: TabLayout 辅助类
 * @author Ttt
 */
public final class TabLayoutAssist {

    // 日志 TAG
    private final String TAG = TabLayoutAssist.class.getSimpleName();

    // Context
    private       Context       mContext;
    // TabLayout
    private       TabLayout     tabLayout;
    // 数据源
    private final List<TabItem> listTabs = new ArrayList<>();

    private TabLayoutAssist() {
    }

    /**
     * 初始化操作
     */
    private void initOperate() {
        // 默认删除全部 Tab
        tabLayout.removeAllTabs();
        // 循环遍历
        for (int i = 0, len = listTabs.size(); i < len; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(getTabView(i));
            tabLayout.addTab(tab);
        }
        // Tab 切换选择事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 切换状态
                updateTabTextView(tab, true);
                // 不为 null, 则触发
                if (tabChangeListener != null) {
                    tabChangeListener.onTabChange(listTabs.get(tab.getPosition()), tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 切换状态
                updateTabTextView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     * 获取单个 TabView
     * @param position 索引
     * @return Tab {@link View}
     */
    private View getTabView(int position) {
        View         view     = LayoutInflater.from(mContext).inflate(R.layout.tab_item_view, null);
        BaseTextView textView = view.findViewById(R.id.vid_tiv_tv);
        textView.setText(listTabs.get(position).getTitle());
        return view;
    }

    /**
     * 改变 Tab TextView 选中状态
     * @param tab      Tab
     * @param isSelect 是否选中
     */
    private void updateTabTextView(
            TabLayout.Tab tab,
            boolean isSelect
    ) {
        View view = tab.getCustomView();
        ViewHelper.get().setBold(view.findViewById(R.id.vid_tiv_tv), isSelect)
                .setVisibility(isSelect, view.findViewById(R.id.vid_tiv_line));
    }

    /**
     * 滑动 Tab 处理
     * @param pos 需要滑动的索引
     */
    private void scrollTab(final int pos) {
        // 延时移动
        HandlerUtils.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    int x = 0;
                    // 循环遍历
                    for (int i = 1; i < pos; i++) {
                        try {
                            // 累加宽度
                            x += tabLayout.getTabAt(i).getCustomView().getWidth();
                        } catch (Exception e) {
                            DevLogEngine.getEngine().eTag(TAG, e, "scrollTab");
                        }
                    }
                    // 开始移动位置
                    tabLayout.scrollTo(x, 0);
                } catch (Exception e) {
                    DevLogEngine.getEngine().eTag(TAG, e, "scrollTab");
                }
            }
        }, 100);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 TabLayout 辅助类
     * @param tabLayout {@link TabLayout}
     * @return {@link TabLayoutAssist}
     */
    public static TabLayoutAssist get(TabLayout tabLayout) {
        if (tabLayout == null) return null;
        TabLayoutAssist tabLayoutAssist = new TabLayoutAssist();
        tabLayoutAssist.mContext = tabLayout.getContext();
        tabLayoutAssist.tabLayout = tabLayout;
        return tabLayoutAssist;
    }

    /**
     * 设置数据操作
     * @param listTabs Tab Item 集合
     * @return {@link TabLayoutAssist}
     */
    public TabLayoutAssist setListTabs(List<TabItem> listTabs) {
        if (listTabs != null) {
            // 清空旧的
            this.listTabs.clear();
            this.listTabs.addAll(listTabs);
        }
        // 初始化操作
        initOperate();
        return this;
    }

    /**
     * 设置选中索引
     * @param position 选中索引
     * @return {@link TabLayoutAssist}
     */
    public TabLayoutAssist setSelect(int position) {
        return setSelect(position, true);
    }

    /**
     * 设置选中
     * @param position 选中索引
     * @param isScroll 是否滑动
     * @return {@link TabLayoutAssist}
     */
    public TabLayoutAssist setSelect(
            int position,
            boolean isScroll
    ) {
        try {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            tab.select();
            // 切换选中状态
            updateTabTextView(tab, true);
        } catch (Exception e) {
        }
        // 滑动处理
        if (isScroll) scrollTab(getSelectedTabPosition());
        return this;
    }

    /**
     * 获取选中的 Tab 索引
     * @return 选中的 Tab 索引
     */
    public int getSelectedTabPosition() {
        return tabLayout.getSelectedTabPosition();
    }

    /**
     * 获取 Tab 总数
     * @return Tab 总数
     */
    public int getTabCount() {
        return tabLayout.getTabCount();
    }

    // ===============
    // = Tab 切换事件 =
    // ===============

    /**
     * detail: Tab 切换事件
     * @author Ttt
     */
    public interface TabChangeListener {

        void onTabChange(
                TabItem tabItem,
                int pos
        );
    }

    // Tab 切换事件
    private TabChangeListener tabChangeListener;

    /**
     * 设置 Tab 切换事件
     * @param tabChangeListener {@link TabChangeListener}
     * @return {@link TabLayoutAssist}
     */
    public TabLayoutAssist setTabChangeListener(TabChangeListener tabChangeListener) {
        this.tabChangeListener = tabChangeListener;
        return this;
    }
}