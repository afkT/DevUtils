package afkt.project.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityUiEffectBinding;
import afkt.project.model.item.TabItem;
import afkt.project.util.assist.TabLayoutAssist;
import dev.base.widget.BaseTextView;
import dev.engine.log.DevLogEngine;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ShapeUtils;
import dev.utils.app.StateListUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.QuickHelper;
import dev.utils.app.helper.ViewHelper;
import dev.utils.common.ArrayUtils;

/**
 * detail: 常见 UI、GradientDrawable 效果等
 * @author Ttt
 */
public class UIEffectActivity
        extends BaseActivity<ActivityUiEffectBinding> {

    // 当前选中的索引
    private int             selectTabIndex = -1;
    // Tab Layout 辅助类
    private TabLayoutAssist tabLayoutAssist;

    @Override
    public int baseLayoutId() {
        return R.layout.activity_ui_effect;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 默认选中
        ViewHelper.get()
                .setSelected(true, binding.vidAue10Tv)
                .setSelected(false, binding.vidAue11Tv);
        changeTab1(binding.vidAue20Tv, binding.vidAue21Tv);
        changeTab2(binding.vidAue31Tv, binding.vidAue30Tv);

        // 动态设置
        binding.vidAue40Btn.setBackground(StateListUtils.newSelector(R.drawable.btn_pressed, R.drawable.btn_normal));
        binding.vidAue40Btn.setTextColor(StateListUtils.createColorStateList(R.color.black, R.color.white));

        // 默认就设置背景色
        binding.vidAue41Btn.setBackground(
                ShapeUtils.newShape()
                        .setCornerRadiusLeft(10.0f)
                        .setColor(Color.BLACK)
                        .getDrawable()
        );
        // 设置点击效果
        GradientDrawable drawable1 = ShapeUtils.newShape(10f, ResourceUtils.getColor(R.color.black))
                .setStroke(5, ResourceUtils.getColor(R.color.darkorange)).getDrawable();
        GradientDrawable drawable2 = ShapeUtils.newShape(10f, ResourceUtils.getColor(R.color.sky_blue))
                .setStroke(5, ResourceUtils.getColor(R.color.gray)).getDrawable();
        binding.vidAue41Btn.setBackground(StateListUtils.newSelector(drawable2, drawable1));
        binding.vidAue41Btn.setTextColor(StateListUtils.createColorStateList(R.color.red, R.color.white));

        // ===========
        // = 渐变效果 =
        // ===========

        ShapeUtils.newShape(GradientDrawable.Orientation.BR_TL, new int[]{Color.RED, Color.BLUE, Color.GREEN})
                .setDrawable(binding.vidAue50View);

        int[] colors = new int[3];
        colors[0] = ContextCompat.getColor(this, R.color.black);
        colors[1] = ContextCompat.getColor(this, R.color.sky_blue);
        colors[2] = ContextCompat.getColor(this, R.color.orange);

        GradientDrawable drawable = ShapeUtils.newShape(GradientDrawable.Orientation.BR_TL, colors).getDrawable();
//        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT); // 线性渐变, 这是默认设置
//        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT); // 放射性渐变, 以开始色为中心
        drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT); // 扫描线式的渐变
        binding.vidAue60View.setBackground(drawable);

        // ============================
        // = HorizontalScrollView Tab =
        // ============================

        // Tab 数据
        List<TabItem> listTabs = new ArrayList<>();
        listTabs.add(new TabItem("全部", 0));
        listTabs.add(new TabItem("待付款", 1));
        listTabs.add(new TabItem("待发货", 2));
        listTabs.add(new TabItem("待收货", 3));
        listTabs.add(new TabItem("待评价", 4));
        listTabs.add(new TabItem("已取消", 5));
        listTabs.add(new TabItem("已完成", 6));
        listTabs.add(new TabItem("已关闭", 7));
        listTabs.add(new TabItem("售后", 8));

        // 循环添加 Tab
        for (int i = 0, len = listTabs.size(); i < len; i++) {
            final int position = i;
            TabItem   tabItem  = listTabs.get(i);

            View view = QuickHelper.get(new BaseTextView(this))
                    .setText(tabItem.getTitle())
                    .setTextColor(ResourceUtils.getColor(R.color.black))
                    .setPadding(30, 30, 30, 30)
                    .setOnClicks(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewHelper.get()
                                    .setBold(ViewUtils.getChildAt(binding.vidAue70Linear, selectTabIndex), false)
                                    .setTextColor(ViewUtils.getChildAt(binding.vidAue70Linear, selectTabIndex), ResourceUtils.getColor(R.color.black))
                                    .setBold(ViewUtils.getChildAt(binding.vidAue70Linear, position), true)
                                    .setTextColor(ViewUtils.getChildAt(binding.vidAue70Linear, position), ResourceUtils.getColor(R.color.red));
                            // 修改索引
                            selectTabIndex = position;
                            // 滑动 Tab 处理
                            scrollTab(position);
                        }
                    }).getView();
            binding.vidAue70Linear.addView(view);
        }
        ViewUtils.getChildAt(binding.vidAue70Linear).performClick();

        // =================
        // = TabLayout Tab =
        // =================

        // 设置数据源
        tabLayoutAssist = TabLayoutAssist.get(binding.vidAue80Tab);
        tabLayoutAssist.setListTabs(listTabs).setSelect(tabLayoutAssist.getTabCount() - 1)
                .setTabChangeListener(new TabLayoutAssist.TabChangeListener() {
                    @Override
                    public void onTabChange(
                            TabItem tabItem,
                            int pos
                    ) {
                        DevLogEngine.getEngine().dTag(TAG, "TabItem: %s, pos: %s", tabItem.getTitle(), pos);
                        // 设置选中
                        tabLayoutAssist.setSelect(pos);
                    }
                });

        TabLayoutAssist.get(binding.vidAue90Tab)
                .setListTabs(ArrayUtils.asList(ArrayUtils.subArray(listTabs.toArray(new TabItem[0]), 0, 3)))
                .setSelect(0).setTabChangeListener(new TabLayoutAssist.TabChangeListener() {
            @Override
            public void onTabChange(
                    TabItem tabItem,
                    int pos
            ) {
                DevLogEngine.getEngine().dTag(TAG, "TabItem: %s, pos: %s", tabItem.getTitle(), pos);
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        ListenerUtils.setOnClicks(this,
                binding.vidAue10Tv, binding.vidAue11Tv,
                binding.vidAue20Tv, binding.vidAue21Tv,
                binding.vidAue30Tv, binding.vidAue31Tv);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_aue_1_0_tv:
                ViewHelper.get()
                        .setSelected(true, binding.vidAue10Tv)
                        .setSelected(false, binding.vidAue11Tv);
                break;
            case R.id.vid_aue_1_1_tv:
                ViewHelper.get()
                        .setSelected(true, binding.vidAue11Tv)
                        .setSelected(false, binding.vidAue10Tv);
                break;
            case R.id.vid_aue_2_0_tv:
                changeTab1(binding.vidAue20Tv, binding.vidAue21Tv);
                break;
            case R.id.vid_aue_2_1_tv:
                changeTab1(binding.vidAue21Tv, binding.vidAue20Tv);
                break;
            case R.id.vid_aue_3_0_tv:
                changeTab2(binding.vidAue30Tv, binding.vidAue31Tv);
                break;
            case R.id.vid_aue_3_1_tv:
                changeTab2(binding.vidAue31Tv, binding.vidAue30Tv);
                break;
        }
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 切换 Tab
     * @param clickTab   点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private void changeTab1(
            BaseTextView clickTab,
            BaseTextView unClickTab
    ) {
        // 判断点击的是左边还是右边
        if (clickTab.getId() == R.id.vid_aue_2_0_tv) { // 点击左边
            clickTab.setBackgroundResource(R.drawable.shape_tab_left_pressed);
            unClickTab.setBackgroundResource(R.drawable.shape_tab_right_selector);
        } else {
            clickTab.setBackgroundResource(R.drawable.shape_tab_right_pressed);
            unClickTab.setBackgroundResource(R.drawable.shape_tab_left_selector);
        }
        // = 字体效果 =
        // 选中变白色
        clickTab.setTextColor(getResources().getColor(R.color.white));
        // 未选中增加点击效果
        unClickTab.setTextColor(ResourceUtils.getColorStateList(R.color.selector_tab_text_color));
    }

    /**
     * 切换 Tab
     * @param clickTab   点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private void changeTab2(
            BaseTextView clickTab,
            BaseTextView unClickTab
    ) {
        // 判断点击的是左边还是右边
        if (clickTab.getId() == R.id.vid_aue_3_0_tv) { // 点击左边
            // 设置选中颜色
            ShapeUtils.newShape().setCornerRadiusLeft(10f)
                    .setColor(ResourceUtils.getColor(R.color.sky_blue))
                    .setDrawable(clickTab);

            // 设置未选中颜色
            GradientDrawable drawable1 = ShapeUtils.newShape()
                    .setCornerRadiusRight(10f)
                    .setColor(ResourceUtils.getColor(R.color.sky_blue))
                    .getDrawable();
            GradientDrawable drawable2 = ShapeUtils.newShape()
                    .setCornerRadiusRight(10f)
                    .setColor(ResourceUtils.getColor(R.color.color_33))
                    .getDrawable();
            unClickTab.setBackground(StateListUtils.newSelector(drawable1, drawable2));
        } else {
            // 设置选中颜色
            ShapeUtils.newShape().setCornerRadiusRight(10f)
                    .setColor(ResourceUtils.getColor(R.color.sky_blue))
                    .setDrawable(clickTab);

            // 设置未选中颜色
            GradientDrawable drawable1 = ShapeUtils.newShape()
                    .setCornerRadiusLeft(10f)
                    .setColor(ResourceUtils.getColor(R.color.sky_blue))
                    .getDrawable();
            GradientDrawable drawable2 = ShapeUtils.newShape()
                    .setCornerRadiusLeft(10f)
                    .setColor(ResourceUtils.getColor(R.color.color_33))
                    .getDrawable();
            unClickTab.setBackground(StateListUtils.newSelector(drawable1, drawable2));
        }
        // = 字体效果 =
        // 选中变白色
        clickTab.setTextColor(getResources().getColor(R.color.white));
        // 设置未选中颜色
        unClickTab.setTextColor(StateListUtils.createColorStateList(R.color.white, R.color.red));
    }

    /**
     * 滑动 Tab 处理
     * @param position 需要滑动的索引
     */
    private void scrollTab(final int position) {
        // 延时移动
        HandlerUtils.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    int x = 0;
                    // 循环遍历
                    for (int i = 1; i < position; i++) {
                        try {
                            // 累加宽度
                            x += binding.vidAue70Linear.getChildAt(i).getWidth();
                        } catch (Exception e) {
                            DevLogEngine.getEngine().eTag(TAG, e, "scrollTab");
                        }
                    }
                    // 开始移动位置
                    binding.vidAue70Scroll.scrollTo(x, 0);
                } catch (Exception e) {
                    DevLogEngine.getEngine().eTag(TAG, e, "scrollTab");
                }
            }
        }, 50);
    }
}