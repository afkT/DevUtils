package afkt.project.base.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import afkt.project.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.base.activity.DevBaseActivity;
import dev.utils.app.ViewUtils;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Base 基类
 * @author Ttt
 */
public abstract class BaseActivity extends DevBaseActivity {

    // = View =
    // 最外层 View
    protected LinearLayout vid_ba_linear;
    // Title View
    protected LinearLayout vid_ba_title_linear;
    // View 容器
    protected LinearLayout vid_ba_content_linear;
    // = Object =
    // Unbinder
    public Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 布局初始化
        layoutInit();
        // 默认调用初始化方法, 按顺序执行
        initMethodOrder();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑 View
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

//    @Override
//    public void setContentView(int layoutResID) {
//        super.setContentView(layoutResID);
//        // 初始化 View
//        unbinder = ButterKnife.bind(this);
//    }
//
//    @Override
//    public void setContentView(View view) {
//        super.setContentView(view);
//        // 初始化 View
//        unbinder = ButterKnife.bind(this);
//    }

    // ================
    // = Content View =
    // ================

    @Override
    public final int contentId() {
        return R.layout.base_activity;
    }

    @Override
    public View contentView() {
        return null;
    }

    // ============
    // = 二次封装 =
    // ============

    /**
     * 获取 Layout id
     * @return Content Layout Id
     */
    public abstract int getLayoutId();

    /**
     * 布局初始化
     * <pre>
     *     可以自己添加统一的 Title、StateLayout 等
     * </pre>
     */
    private void layoutInit() {
        // 初始化 View
        vid_ba_linear = ViewUtils.findViewById(this, R.id.vid_ba_linear);
        vid_ba_title_linear = ViewUtils.findViewById(this, R.id.vid_ba_title_linear);
        vid_ba_content_linear = ViewUtils.findViewById(this, R.id.vid_ba_content_linear);
        // 清空旧的 View
        ViewUtils.removeAllViews(vid_ba_content_linear);
        // 获取 Layout Id View
        View layoutView = ViewUtils.inflate(this, getLayoutId(), null);
        if (layoutView != null) {
            // 添加 View
            LinearLayout.LayoutParams contentViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            vid_ba_content_linear.addView(layoutView, contentViewLP);
            // 绑定 View
            unbinder = ButterKnife.bind(this, layoutView);
        }
    }

    // =========
    // = Toast =
    // =========

    /**
     * 显示 Toast
     * @param success 是否成功样式
     * @param text    Toast 文本
     */
    public void showToast(boolean success, String text) {
        if (success) {
            ToastTintUtils.success(text);
        } else {
            ToastTintUtils.error(text);
        }
    }

    /**
     * 显示 Toast
     * @param success 是否成功样式
     */
    public void showToast(boolean success) {
        showToast(success, "操作成功", "操作失败");
    }

    /**
     * 显示 Toast
     * @param success     是否成功样式
     * @param successText 成功 Toast 文本
     * @param errorText   错误 Toast 文本
     */
    public void showToast(boolean success, String successText, String errorText) {
        showToast(success, success ? successText : errorText);
    }
}
