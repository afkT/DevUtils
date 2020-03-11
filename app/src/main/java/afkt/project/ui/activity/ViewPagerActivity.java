package afkt.project.ui.activity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.OnClick;
import dev.utils.app.logger.DevLogger;
import dev.widget.control.ControlSlideViewPager;

/**
 * detail: ViewPager 滑动监听、控制滑动
 * @author Ttt
 */
public class ViewPagerActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_avp_viewpager)
    ControlSlideViewPager vid_avp_viewpager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    public void initValues() {
        super.initValues();

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            lists.add((i + 1) + "");
        }
        vid_avp_viewpager.setAdapter(new ViewPagerAdapter(lists));
        vid_avp_viewpager.setCurrentItem(lists.size() * 100, false);
        vid_avp_viewpager.setOnPageChangeListener(new ControlSlideViewPager.OnDirectionListener() {
            @Override
            public void onSlideDirection(boolean left, boolean right) {
                if (left && !right) {
                    DevLogger.dTag(mTag, "往左滑 - 从右往左");
                } else {
                    DevLogger.dTag(mTag, "往右滑 - 从左往右");
                }
            }

            @Override
            public void onPageSelected(int i) {
                DevLogger.dTag(mTag, "索引变动: " + i);

                if (mLeftScroll) {
                    showToast("往左滑 - 从右往左");
                } else {
                    showToast("往右滑 - 从左往右");
                }
            }
        });
    }

    @OnClick({R.id.vid_avp_allow_btn, R.id.vid_avp_ban_btn})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_avp_allow_btn:
                vid_avp_viewpager.setSlide(true);
                showToast(true, "已允许滑动");
                break;
            case R.id.vid_avp_ban_btn:
                vid_avp_viewpager.setSlide(false);
                showToast(false, "已禁止滑动");
                break;
        }
    }
}