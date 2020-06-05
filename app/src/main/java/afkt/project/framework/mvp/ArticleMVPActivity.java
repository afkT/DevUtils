package afkt.project.framework.mvp;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tt.whorlviewlibrary.WhorlView;

import afkt.project.R;
import afkt.project.base.app.BaseMVPToolbarActivity;
import afkt.project.model.bean.ArticleBean;
import afkt.project.ui.adapter.ArticleAdapter;
import butterknife.BindView;
import dev.utils.app.ViewUtils;
import dev.utils.common.CollectionUtils;
import dev.widget.function.StateLayout;

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
        View view = stateLayout.getView(StateLayout.ING);
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
                boolean success = (state == StateLayout.SUCCESS);
                // 切换 View 操作
                if (ViewUtils.reverseVisibilitys(success, vid_ba_content_linear, vid_ba_state_linear)) {
                    // 属于请求成功
                } else {
                    if (state == StateLayout.ING) {
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
        stateLayout.setState(StateLayout.ING);
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
                stateLayout.setState(StateLayout.NO_DATA);
            } else { // 请求成功
                stateLayout.setState(StateLayout.SUCCESS);
                // 设置数据源
                articleAdapter.setNewData(articleBean.data.datas);
            }
        } else { // 请求失败
            stateLayout.setState(StateLayout.FAIL);
        }
    }
}
