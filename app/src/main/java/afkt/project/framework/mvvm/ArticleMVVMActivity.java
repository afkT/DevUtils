package afkt.project.framework.mvvm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tt.whorlviewlibrary.WhorlView;

import afkt.project.BR;
import afkt.project.R;
import afkt.project.base.app.BaseMVVMActivity;
import afkt.project.base.constants.KeyConstants;
import afkt.project.databinding.ActivityArticleMvvmBinding;
import afkt.project.framework.mvp.ArticleMVP;
import afkt.project.model.bean.ArticleBean;
import afkt.project.ui.adapter.ArticleAdapter;
import dev.utils.app.ViewUtils;
import dev.utils.common.CollectionUtils;
import dev.widget.StateLayout;

/**
 * detail: 文章 MVVM Activity
 * @author Ttt
 */
public class ArticleMVVMActivity extends BaseMVVMActivity<ActivityArticleMvvmBinding, ArticleMVP.Presenter> implements ArticleMVP.View {

    // 加载动画
    WhorlView whorlView;
    // 适配器
    ArticleAdapter articleAdapter;

    @Override
    protected ArticleMVP.Presenter presenter() {
        return new ArticleMVP.Presenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_article_mvvm;
    }

    @Override
    public void initViews() {
        super.initViews();

        // 因现有架构与 MVVM 并非完全兼容, 需要重新设置 setContentView
        // 在一开始选型确定后, 才能专门为其设计基类

        // MVVM 只需要调用此句绑定
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());

        // = 处理 ActionBar =
        setSupportActionBar(viewDataBinding.vidAamToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 给左上角图标的左边加上一个返回的图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // 设置点击事件
        viewDataBinding.vidAamToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭页面
                finish();
            }
        });

        Intent intent = getIntent();
        // 获取标题
        String title = intent.getStringExtra(KeyConstants.Common.KEY_TITLE);
        // 设置标题
        viewDataBinding.setTitle(title);
        viewDataBinding.setVariable(BR.title, title); // 设置后, 会动态刷新

        // 初始化 View
        View view = viewDataBinding.vidAamState.getView(StateLayout.State.ING.getValue());
        whorlView = ViewUtils.findViewById(view, R.id.vid_sli_load_view);
    }

    @Override
    public void initValues() {
        super.initValues();
        // 初始化布局管理器、适配器
        articleAdapter = new ArticleAdapter();
        viewDataBinding.vidAamRecy.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.vidAamRecy.setAdapter(articleAdapter);
    }

    @Override
    public void initListeners() {
        super.initListeners();
        // 设置监听
        viewDataBinding.vidAamState.setOnStateChanged(new StateLayout.OnStateChanged() {
            @Override
            public void OnChanged(StateLayout stateLayout, int state, String type, int size) {
                // 判断是否操作成功
                boolean success = (state == StateLayout.State.SUCCESS.getValue());
                // 切换 View 操作
                if (ViewUtils.reverseVisibilitys(success, viewDataBinding.vidAamRecy, viewDataBinding.vidAamState)) {
                    // 属于请求成功
                } else {
                    if (state == StateLayout.State.ING.getValue()) {
                        if (!whorlView.isCircling()) {
                            whorlView.start();
                        }
                    } else {
                        whorlView.stop();
                    }
                }
            }
        });
    }

    @Override
    public void initOtherOperate() {
        super.initOtherOperate();
        // 表示请求中
        viewDataBinding.vidAamState.setState(StateLayout.State.ING.getValue());
        // 获取文章列表
        mPresenter.getArticleLists();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止动画
        whorlView.stop();
    }

    // ===================
    // = ArticleMVP.View =
    // ===================

    @Override
    public void onArticleListResponse(boolean succeed, ArticleBean articleBean) {
        if (succeed) {
            if (CollectionUtils.isEmpty(articleBean.data.datas)) { // 无数据
                viewDataBinding.vidAamState.setState(StateLayout.State.NO_DATA.getValue());
            } else { // 请求成功
                viewDataBinding.vidAamState.setState(StateLayout.State.SUCCESS.getValue());
                // 设置数据源
                articleAdapter.setNewData(articleBean.data.datas);
            }
        } else { // 请求失败
            viewDataBinding.vidAamState.setState(StateLayout.State.FAIL.getValue());
        }
    }
}
