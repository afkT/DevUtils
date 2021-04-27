package afkt.project.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import afkt.project.R;
import dev.utils.app.ViewUtils;

/**
 * detail: ViewPager 适配器
 * @author Ttt
 */
public class ViewPagerAdapter
        extends PagerAdapter {

    // 数据源
    private List<String> lists;

    /**
     * 构造函数
     * @param lists 数据源
     */
    public ViewPagerAdapter(List<String> lists) {
        this.lists = lists;
    }

    @Override
    public Object instantiateItem(
            ViewGroup container,
            int position
    ) {
        // 加载 View
        View view = ViewUtils.inflate(R.layout.view_pager_item_view);
        // 设置文本
        ((TextView) view.findViewById(R.id.vid_vpiv_content_tv)).setText(lists.get(position % lists.size()));
        // 保存 View
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(
            View view,
            Object object
    ) {
        return view == object;
    }

    @Override
    public void destroyItem(
            ViewGroup container,
            int position,
            Object object
    ) {
        try {
            container.removeView((View) object);
        } catch (Exception e) {
        }
    }
}