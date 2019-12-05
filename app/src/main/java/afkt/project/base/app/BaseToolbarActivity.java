package afkt.project.base.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import afkt.project.R;
import afkt.project.base.constants.KeyConstants;
import dev.utils.app.ViewUtils;

/**
 * detail: ToolBar Activity 基类
 * @author Ttt
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    // ToolBar
    protected Toolbar vid_bt_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加到 Title
        View titleView = ViewUtils.inflate(this, R.layout.base_toolbar, null);
        vid_bt_toolbar = titleView.findViewById(R.id.vid_bt_toolbar);
        vid_ba_title_linear.addView(titleView);

        Intent intent = getIntent();
        // 获取标题
        String title = intent.getStringExtra(KeyConstants.Common.KEY_TITLE);
        // 初始化 ToolBar
        initToolBar();
        // 设置标题
        setTitle(title);
    }

    /**
     * 初始化 ToolBar
     */
    protected void initToolBar() {
        // = 处理 ActionBar =
        setSupportActionBar(vid_bt_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 给左上角图标的左边加上一个返回的图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // 设置点击事件
        vid_bt_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭页面
                finish();
            }
        });
    }

    /**
     * 设置 ToolBar 标题
     * @param title 标题
     */
    protected void setTitle(String title) {
        vid_bt_toolbar.setTitle(title);
    }

    /**
     * 获取 Module 类型
     * @return Module 类型
     */
    protected int getModuleType() {
        return getIntent().getIntExtra(KeyConstants.Common.KEY_TYPE, 0);
    }
}