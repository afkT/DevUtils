package afkt.project.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityStatusBarBinding;
import dev.utils.app.BarUtils;
import dev.utils.app.ResourceUtils;

/**
 * detail: 点击 显示 / 隐藏 ( 状态栏 )
 * @author Ttt
 */
public class StatusBarActivity
        extends BaseActivity<ActivityStatusBarBinding> {

    // 判断是否显示
    boolean isVisible = true;

    @Override
    public int baseLayoutId() {
        return R.layout.activity_status_bar;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 想要实现点击一下, 显示状态栏图标, 点击一下切换不显示, 并且整体不会上下移动
        // 需要先设置 Activity  Theme => android:Theme.Light.NoTitleBar
        // 第二就是 Activity 最外层布局 view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // 设置状态栏 View 高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight()
        );
        View statusView = new View(this);
        statusView.setBackgroundColor(ResourceUtils.getColor(R.color.colorPrimary));
        statusView.setLayoutParams(layoutParams);

        contentAssist.rootLinear.addView(statusView, 0);
        // 设置全屏显示, 但是会被状态栏覆盖
        contentAssist.rootLinear.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        binding.vidAsbToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置是否显示
                BarUtils.setStatusBarVisibility(mActivity, isVisible = !isVisible);
            }
        });
    }
}