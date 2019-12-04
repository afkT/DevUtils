package afkt.project.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import butterknife.BindView;
import butterknife.OnClick;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ShapeUtils;
import dev.utils.app.StateListUtils;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: 常见 UI、GradientDrawable 效果等
 * @author Ttt
 */
public class UIEffectActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_aue_1_0_tv)
    TextView vid_aue_1_0_tv;
    @BindView(R.id.vid_aue_1_1_tv)
    TextView vid_aue_1_1_tv;
    @BindView(R.id.vid_aue_2_0_tv)
    TextView vid_aue_2_0_tv;
    @BindView(R.id.vid_aue_2_1_tv)
    TextView vid_aue_2_1_tv;
    @BindView(R.id.vid_aue_3_0_tv)
    TextView vid_aue_3_0_tv;
    @BindView(R.id.vid_aue_3_1_tv)
    TextView vid_aue_3_1_tv;
    @BindView(R.id.vid_aue_4_0_btn)
    Button vid_aue_4_0_btn;
    @BindView(R.id.vid_aue_4_1_btn)
    Button vid_aue_4_1_btn;
    @BindView(R.id.vid_aue_5_0_view)
    View vid_aue_5_0_view;
    @BindView(R.id.vid_aue_6_0_view)
    View vid_aue_6_0_view;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ui_effect;
    }

    @Override
    public void initValues() {
        super.initValues();

        // 默认选中
        ViewHelper.get().setSelected(true, vid_aue_1_0_tv).setSelected(false, vid_aue_1_1_tv);
        changeTab1(vid_aue_2_0_tv, vid_aue_2_1_tv);
        changeTab2(vid_aue_3_1_tv, vid_aue_3_0_tv);

        // 动态设置
        vid_aue_4_0_btn.setBackground(StateListUtils.newSelector(R.drawable.btn_pressed, R.drawable.btn_normal));
        vid_aue_4_0_btn.setTextColor(StateListUtils.createColorStateList(R.color.black, R.color.white));

//        // 默认就设置背景色
//        ShapeUtils.Builder builder = new ShapeUtils.Builder();
//        builder.setRadiusLeft(10f).setColor(R.color.black);
//        vid_aue_4_1_btn.setBackground(builder.build().getDrawable());
        // 设置点击效果
        GradientDrawable drawable1 = ShapeUtils.newBuilder(10f, R.color.black).setStroke(5, R.color.darkorange).build().getDrawable();
        GradientDrawable drawable2 = ShapeUtils.newBuilder(10f, R.color.sky_blue).setStroke(5, R.color.grey).build().getDrawable();
        vid_aue_4_1_btn.setBackground(StateListUtils.newSelector(drawable2, drawable1));
        vid_aue_4_1_btn.setTextColor(StateListUtils.createColorStateList(R.color.red, R.color.white));

        // ============
        // = 渐变效果 =
        // ============

        ShapeUtils.newBuilderToGradient(GradientDrawable.Orientation.BR_TL,
            new int[]{ Color.RED, Color.BLUE, Color.GREEN }).build().setDrawable(vid_aue_5_0_view);

        int[] colors = new int[3];
        colors[0] = ContextCompat.getColor(this, R.color.black);
        colors[1] = ContextCompat.getColor(this, R.color.sky_blue);
        colors[2] = ContextCompat.getColor(this, R.color.orange);

        GradientDrawable drawable = ShapeUtils.newBuilderToGradient(GradientDrawable.Orientation.BR_TL, colors).build().getDrawable();
//        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT); // 线性渐变, 这是默认设置
//        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT); // 放射性渐变, 以开始色为中心
        drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT); // 扫描线式的渐变
        vid_aue_6_0_view.setBackground(drawable);
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
     * @param clickTab 点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private void changeTab1(TextView clickTab, TextView unClickTab) {
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
     * @param clickTab 点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private void changeTab2(TextView clickTab, TextView unClickTab) {
        // 判断点击的是左边还是右边
        if (clickTab.getId() == R.id.vid_aue_3_0_tv) { // 点击左边
            // 设置选中颜色
            ShapeUtils.Builder builder = new ShapeUtils.Builder();
            builder.setRadiusLeft(10f).setColor(R.color.sky_blue);
            builder.build().setDrawable(clickTab);

            // 设置未选中颜色
            GradientDrawable drawable1 = ShapeUtils.newBuilderToRight(10f, R.color.sky_blue).build().getDrawable();
            GradientDrawable drawable2 = ShapeUtils.newBuilderToRight(10f, R.color.color_33).build().getDrawable();
            unClickTab.setBackground(StateListUtils.newSelector(drawable1, drawable2));
        } else {
            // 设置选中颜色
            ShapeUtils.Builder builder = new ShapeUtils.Builder();
            builder.setRadiusRight(10f).setColor(R.color.sky_blue);
            builder.build().setDrawable(clickTab);

            // 设置未选中颜色
            GradientDrawable drawable1 = ShapeUtils.newBuilderToLeft(10f, R.color.sky_blue).build().getDrawable();
            GradientDrawable drawable2 = ShapeUtils.newBuilderToLeft(10f, R.color.color_33).build().getDrawable();
            unClickTab.setBackground(StateListUtils.newSelector(drawable1, drawable2));
        }
        // = 字体效果 =
        // 选中变白色
        clickTab.setTextColor(getResources().getColor(R.color.white));
        // 设置未选中颜色
        unClickTab.setTextColor(StateListUtils.createColorStateList(R.color.white, R.color.red));
    }
}