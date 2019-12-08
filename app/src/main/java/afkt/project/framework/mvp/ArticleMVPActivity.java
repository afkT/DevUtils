package afkt.project.framework.mvp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tt.whorlviewlibrary.WhorlView;

import afkt.project.R;
import afkt.project.base.app.BaseMVPToolbarActivity;
import afkt.project.model.bean.ArticleBean;
import afkt.project.ui.adapter.ArticleAdapter;
import butterknife.BindView;
import dev.utils.app.ViewUtils;
import dev.utils.common.CollectionUtils;
import dev.widget.StateLayout;

/**
 * detail: 文章 MVP Activity
 * @author Ttt
 */
public class ArticleMVPActivity extends BaseMVPToolbarActivity<ArticleMVP.Presenter> implements ArticleMVP.View {

    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;
    // 加载动画
    WhorlView vid_sli_load_view;
    // 适配器
    ArticleAdapter articleAdapter;

    @Override
    protected ArticleMVP.Presenter presenter() {
        return new ArticleMVP.Presenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initViews() {
        super.initViews();
        // 初始化 View
        View view = stateLayout.getView(StateLayout.State.ING.getValue());
        vid_sli_load_view = ViewUtils.findViewById(view, R.id.vid_sli_load_view);
    }

    @Override
    public void initValues() {
        super.initValues();
        // 初始化布局管理器、适配器
        articleAdapter = new ArticleAdapter();
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(articleAdapter);
    }

    @Override
    public void initListeners() {
        super.initListeners();
        // 设置监听
        stateLayout.setOnStateChanged(new StateLayout.OnStateChanged() {
            @Override
            public void OnChanged(StateLayout stateLayout, int state, String type, int size) {
                // 判断是否操作成功
                boolean success = (state == StateLayout.State.SUCCESS.getValue());
                // 切换 View 操作
                if (ViewUtils.reverseVisibilitys(success, vid_ba_content_linear, vid_ba_state_linear)) {
                    // 属于请求成功
                } else {
                    if (state == StateLayout.State.ING.getValue()) {
                        if (!vid_sli_load_view.isCircling()) {
                            vid_sli_load_view.start();
                        }
                    } else {
                        vid_sli_load_view.stop();
                    }
                }
            }
        });
    }

    @Override
    public void initOtherOperate() {
        super.initOtherOperate();
        // 表示请求中
        stateLayout.setState(StateLayout.State.ING.getValue());
        // 获取文章列表
        mPresenter.getArticleLists();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止动画
        vid_sli_load_view.stop();
    }

    // ===================
    // = ArticleMVP.View =
    // ===================

    @Override
    public void onArticleListResponse(boolean succeed, ArticleBean articleBean) {
        if (succeed) {
            if (CollectionUtils.isEmpty(articleBean.data.datas)) { // 无数据
                stateLayout.setState(StateLayout.State.NO_DATA.getValue());
            } else { // 请求成功
                stateLayout.setState(StateLayout.State.SUCCESS.getValue());
                // 设置数据源
                articleAdapter.setNewData(articleBean.data.datas);
            }
        } else { // 请求失败
            stateLayout.setState(StateLayout.State.FAIL.getValue());
        }
    }
}
