package afkt.project.base.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import afkt.project.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.base.activity.DevBaseActivity;
import dev.utils.app.ViewUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.widget.assist.StateLayout;

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
    // 状态布局容器
    protected LinearLayout vid_ba_state_linear;
    // 状态布局
    protected StateLayout stateLayout;
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
        vid_ba_state_linear = ViewUtils.findViewById(this, R.id.vid_ba_state_linear);
        // 清空旧的 View
        ViewUtils.removeAllViews(vid_ba_content_linear);
        // 获取 Layout Id View
        View layoutView = ViewUtils.inflate(this, getLayoutId(), null);
        // 如果获取 为 null
        if (layoutView == null) layoutView = contentView();
        if (layoutView != null) {
            // 添加 View
            LinearLayout.LayoutParams contentViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            vid_ba_content_linear.addView(layoutView, contentViewLP);
            // 插入 StateLayout
            insertStateLayout();
            // 绑定 View
            unbinder = ButterKnife.bind(this, layoutView);
        }
    }

    // ============
    // = 状态布局 =
    // ============

    /**
     * 插入 State Layout
     */
    public void insertStateLayout() {
        if (stateLayout != null) vid_ba_state_linear.removeView(stateLayout);
        if (stateLayout == null) stateLayout = new StateLayout(this);
        // 添加 View
        LinearLayout.LayoutParams contentViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vid_ba_state_linear.addView(stateLayout, contentViewLP);
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

    // ============
    // = 特殊方法 =
    // ============

    /**
     * 注册 Adapter 观察者
     * @param recyclerView {@link RecyclerView}
     */
    public void registerAdapterDataObserver(RecyclerView recyclerView) {
        registerAdapterDataObserver(recyclerView, null, false);
    }

    /**
     * 注册 Adapter 观察者
     * @param recyclerView {@link RecyclerView}
     * @param isRefAdapter 是否刷新适配器
     */
    public void registerAdapterDataObserver(RecyclerView recyclerView, boolean isRefAdapter) {
        registerAdapterDataObserver(recyclerView, null, isRefAdapter);
    }

    /**
     * 注册 Adapter 观察者
     * @param recyclerView        {@link RecyclerView}
     * @param adapterDataObserver Adapter 观察者
     * @param isRefAdapter        是否刷新适配器
     */
    public void registerAdapterDataObserver(RecyclerView recyclerView, RecyclerView.AdapterDataObserver adapterDataObserver, boolean isRefAdapter) {
        if (recyclerView != null) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        // 获取数据总数
                        int itemCount = adapter.getItemCount();
                        // 如果为 null 特殊处理
                        ViewUtils.reverseVisibilitys(itemCount != 0, vid_ba_content_linear, vid_ba_state_linear);
                        // 判断是否不存在数据
                        if (itemCount == 0) {
                            stateLayout.setState(StateLayout.State.NO_DATA.getValue());
                        }

                        if (adapterDataObserver != null) {
                            adapterDataObserver.onChanged();
                        }
                    }
                });
                // 刷新适配器
                if (isRefAdapter) adapter.notifyDataSetChanged();
            }
        }
    }
}
