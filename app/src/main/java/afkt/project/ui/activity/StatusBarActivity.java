package afkt.project.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import butterknife.OnClick;
import dev.utils.app.BarUtils;
import dev.utils.app.ResourceUtils;

/**
 * detail: 点击 显示 / 隐藏 ( 状态栏 )
 * @author Ttt
 */
public class StatusBarActivity extends BaseToolbarActivity {

    // 判断是否显示
    boolean isVisible = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_status_bar;
    }

    @Override
    public void initValues() {
        super.initValues();

        // 想要实现点击一下, 显示状态栏图标, 点击一下切换不显示, 并且整体不会上下移动
        // 需要先设置 Activity  Theme => android:Theme.Light.NoTitleBar
        // 第二就是 Activity 最外层布局 view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // 设置 LayoutParams @android:dimen/status_bar_height
        // 设置状态栏 View 高度
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight());
        View statusView = new View(this);
        statusView.setBackgroundColor(ResourceUtils.getColor(R.color.colorPrimary));
        statusView.setLayoutParams(lParams);

        vid_ba_linear.addView(statusView, 0);
        // 设置全屏显示, 但是会被状态栏覆盖
        vid_ba_linear.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @OnClick({R.id.vid_asb_toggle_btn})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_asb_toggle_btn:
                // 设置是否显示
                BarUtils.setStatusBarVisibility(this, isVisible = !isVisible);
                break;
        }
    }
}