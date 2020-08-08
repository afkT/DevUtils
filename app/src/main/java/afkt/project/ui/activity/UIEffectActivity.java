package afkt.project.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.TabItem;
import afkt.project.ui.widget.BaseButton;
import afkt.project.ui.widget.BaseTextView;
import afkt.project.util.assist.TabLayoutAssist;
import butterknife.BindView;
import butterknife.OnClick;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ShapeUtils;
import dev.utils.app.StateListUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.QuickHelper;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.logger.DevLogger;
import dev.utils.common.ArrayUtils;

/**
 * detail: 常见 UI、GradientDrawable 效果等
 * @author Ttt
 */
public class UIEffectActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_aue_1_0_tv)
    BaseTextView         vid_aue_1_0_tv;
    @BindView(R.id.vid_aue_1_1_tv)
    BaseTextView         vid_aue_1_1_tv;
    @BindView(R.id.vid_aue_2_0_tv)
    BaseTextView         vid_aue_2_0_tv;
    @BindView(R.id.vid_aue_2_1_tv)
    BaseTextView         vid_aue_2_1_tv;
    @BindView(R.id.vid_aue_3_0_tv)
    BaseTextView         vid_aue_3_0_tv;
    @BindView(R.id.vid_aue_3_1_tv)
    BaseTextView         vid_aue_3_1_tv;
    @BindView(R.id.vid_aue_4_0_btn)
    BaseButton           vid_aue_4_0_btn;
    @BindView(R.id.vid_aue_4_1_btn)
    BaseButton           vid_aue_4_1_btn;
    @BindView(R.id.vid_aue_5_0_view)
    View                 vid_aue_5_0_view;
    @BindView(R.id.vid_aue_6_0_view)
    View                 vid_aue_6_0_view;
    @BindView(R.id.vid_aue_7_0_scroll)
    HorizontalScrollView vid_aue_7_0_scroll;
    @BindView(R.id.vid_aue_7_0_linear)
    LinearLayout         vid_aue_7_0_linear;
    @BindView(R.id.vid_aue_8_0_tab)
    TabLayout            vid_aue_8_0_tab;
    @BindView(R.id.vid_aue_9_0_tab)
    TabLayout            vid_aue_9_0_tab;
    // 当前选中的索引
    int             selectTabIndex = -1;
    // Tab Layout 辅助类
    TabLayoutAssist tabLayoutAssist;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ui_effect;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 默认选中
        ViewHelper.get().setSelected(true, vid_aue_1_0_tv).setSelected(false, vid_aue_1_1_tv);
        changeTab1(vid_aue_2_0_tv, vid_aue_2_1_tv);
        changeTab2(vid_aue_3_1_tv, vid_aue_3_0_tv);

        // 动态设置
        vid_aue_4_0_btn.setBackground(StateListUtils.newSelector(R.drawable.btn_pressed, R.drawable.btn_normal));
        vid_aue_4_0_btn.setTextColor(StateListUtils.createColorStateList(R.color.black, R.color.white));

        // 默认就设置背景色
        vid_aue_4_1_btn.setBackground(
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
        vid_aue_4_1_btn.setBackground(StateListUtils.newSelector(drawable2, drawable1));
        vid_aue_4_1_btn.setTextColor(StateListUtils.createColorStateList(R.color.red, R.color.white));

        // ============
        // = 渐变效果 =
        // ============

        ShapeUtils.newShape(GradientDrawable.Orientation.BR_TL, new int[]{Color.RED, Color.BLUE, Color.GREEN})
                .setDrawable(vid_aue_5_0_view);

        int[] colors = new int[3];
        colors[0] = ContextCompat.getColor(this, R.color.black);
        colors[1] = ContextCompat.getColor(this, R.color.sky_blue);
        colors[2] = ContextCompat.getColor(this, R.color.orange);

        GradientDrawable drawable = ShapeUtils.newShape(GradientDrawable.Orientation.BR_TL, colors).getDrawable();
//        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT); // 线性渐变, 这是默认设置
//        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT); // 放射性渐变, 以开始色为中心
        drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT); // 扫描线式的渐变
        vid_aue_6_0_view.setBackground(drawable);

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
            TabItem tabItem = listTabs.get(i);

            View view = QuickHelper.get(new BaseTextView(this))
                    .setText(tabItem.getTitle())
                    .setTextColor(ResourceUtils.getColor(R.color.black))
                    .setPadding(30, 30, 30, 30)
                    .setOnClicks(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewHelper.get()
                                    .setBold(ViewUtils.getChildAt(vid_aue_7_0_linear, selectTabIndex), false)
                                    .setTextColor(ViewUtils.getChildAt(vid_aue_7_0_linear, selectTabIndex), ResourceUtils.getColor(R.color.black))
                                    .setBold(ViewUtils.getChildAt(vid_aue_7_0_linear, position), true)
                                    .setTextColor(ViewUtils.getChildAt(vid_aue_7_0_linear, position), ResourceUtils.getColor(R.color.red));
                            // 修改索引
                            selectTabIndex = position;
                            // 滑动 Tab 处理
                            scrollTab(position);
                        }
                    }).getView();
            vid_aue_7_0_linear.addView(view);
        }
        ViewUtils.getChildAt(vid_aue_7_0_linear).performClick();

        // =================
        // = TabLayout Tab =
        // =================

        // 设置数据源
        tabLayoutAssist = TabLayoutAssist.obtain(vid_aue_8_0_tab);
        tabLayoutAssist.setListTabs(listTabs).setSelect(tabLayoutAssist.getTabCount() - 1)
                .setTabChangeListener(new TabLayoutAssist.TabChangeListener() {
                    @Override
                    public void onTabChange(TabItem tabItem, int pos) {
                        DevLogger.dTag(mTag, "TabItem : " + tabItem.getTitle() + ", pos: " + pos);
                        // 设置选中
                        tabLayoutAssist.setSelect(pos);
                    }
                });

        TabLayoutAssist.obtain(vid_aue_9_0_tab)
                .setListTabs(ArrayUtils.asList(ArrayUtils.subarray(listTabs.toArray(new TabItem[0]), 0, 3)))
                .setSelect(0).setTabChangeListener(new TabLayoutAssist.TabChangeListener() {
            @Override
            public void onTabChange(TabItem tabItem, int pos) {
                DevLogger.dTag(mTag, "TabItem : " + tabItem.getTitle() + ", pos: " + pos);
            }
        });
    }

    @OnClick({R.id.vid_aue_1_0_tv, R.id.vid_aue_1_1_tv, R.id.vid_aue_2_0_tv, R.id.vid_aue_2_1_tv, R.id.vid_aue_3_0_tv, R.id.vid_aue_3_1_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_aue_1_0_tv:
                ViewHelper.get().setSelected(true, vid_aue_1_0_tv).setSelected(false, vid_aue_1_1_tv);
                break;
            case R.id.vid_aue_1_1_tv:
                ViewHelper.get().setSelected(true, vid_aue_1_1_tv).setSelected(false, vid_aue_1_0_tv);
                break;
            case R.id.vid_aue_2_0_tv:
                changeTab1(vid_aue_2_0_tv, vid_aue_2_1_tv);
                break;
            case R.id.vid_aue_2_1_tv:
                changeTab1(vid_aue_2_1_tv, vid_aue_2_0_tv);
                break;
            case R.id.vid_aue_3_0_tv:
                changeTab2(vid_aue_3_0_tv, vid_aue_3_1_tv);
                break;
            case R.id.vid_aue_3_1_tv:
                changeTab2(vid_aue_3_1_tv, vid_aue_3_0_tv);
                break;
        }
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 切换 Tab
     * @param clickTab   点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private void changeTab1(BaseTextView clickTab, BaseTextView unClickTab) {
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
    private void changeTab2(BaseTextView clickTab, BaseTextView unClickTab) {
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
                            x += vid_aue_7_0_linear.getChildAt(i).getWidth();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // 开始移动位置
                    vid_aue_7_0_scroll.scrollTo(x, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 50);
    }
}