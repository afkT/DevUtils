package afkt.project.ui.activity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityViewPagerBinding;
import afkt.project.ui.adapter.ViewPagerAdapter;
import dev.utils.app.logger.DevLogger;
import dev.widget.custom.CustomViewPager;

/**
 * detail: ViewPager 滑动监听、控制滑动
 * @author Ttt
 */
public class ViewPagerActivity
        extends BaseActivity<ActivityViewPagerBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    public void initValue() {
        super.initValue();

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            lists.add(String.valueOf(i + 1));
        }
        binding.vidAvpViewpager.setAdapter(new ViewPagerAdapter(lists));
        binding.vidAvpViewpager.setCurrentItem(lists.size() * 100, false);
        binding.vidAvpViewpager.setOnPageChangeListener(new CustomViewPager.OnDirectionListener() {
            @Override
            public void onSlideDirection(
                    boolean left,
                    boolean right
            ) {
                if (left && !right) {
                    DevLogger.dTag(TAG, "往左滑 - 从右往左");
                } else {
                    DevLogger.dTag(TAG, "往右滑 - 从左往右");
                }
            }

            @Override
            public void onPageSelected(int index) {
                DevLogger.dTag(TAG, "索引变动: %s", index);

                if (mLeftScroll) {
                    showToast("往左滑 - 从右往左");
                } else {
                    showToast("往右滑 - 从左往右");
                }
            }
        });
        binding.vidAvpAllowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.vidAvpViewpager.setSlide(true);
                showToast(true, "已允许滑动");
            }
        });
        binding.vidAvpBanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.vidAvpViewpager.setSlide(false);
                showToast(false, "已禁止滑动");
            }
        });
    }
}