package afkt.project.framework.mvp;

import android.view.View;

import com.tt.whorlviewlibrary.WhorlView;

import afkt.project.R;
import afkt.project.base.app.BaseMVPActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.bean.ArticleBean;
import afkt.project.ui.adapter.ArticleAdapter;
import dev.other.retrofit.RxJavaManager;
import dev.utils.app.ViewUtils;
import dev.utils.common.CollectionUtils;
import dev.widget.assist.ViewAssist;
import dev.widget.function.StateLayout;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * detail: 文章 MVP Activity
 * @author Ttt
 */
public class ArticleMVPActivity
        extends BaseMVPActivity<ArticleMVP.Presenter, BaseViewRecyclerviewBinding>
        implements ArticleMVP.View {

    // 加载动画
    WhorlView      vid_sli_load_view;
    // 适配器
    ArticleAdapter articleAdapter;

    @Override
    public ArticleMVP.Presenter createPresenter() {
        return new ArticleMVP.Presenter(this);
    }

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initView() {
        super.initView();
        // 初始化 View
        View view = stateLayout.getView(ViewAssist.TYPE_ING);
        vid_sli_load_view = ViewUtils.findViewById(view, R.id.vid_sli_load_view);
    }

    @Override
    public void initValue() {
        super.initValue();
        // 初始化布局管理器、适配器
        articleAdapter = new ArticleAdapter();
        binding.vidBvrRecy.setAdapter(articleAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        // 设置监听
        stateLayout.setListener(new StateLayout.Listener() {
            @Override
            public void onRemove(
                    StateLayout layout,
                    int type,
                    boolean removeView
            ) {
            }

            @Override
            public void onNotFound(
                    StateLayout layout,
                    int type
            ) {
                // 切换 View 操作
                if (type == ViewAssist.TYPE_SUCCESS) {
                    ViewUtils.reverseVisibilitys(true, contentAssist.contentLinear, contentAssist.stateLinear);
                }
            }

            @Override
            public void onChange(
                    StateLayout layout,
                    int type,
                    int oldType,
                    View view
            ) {
                // 判断是否操作成功
                boolean success = (type == ViewAssist.TYPE_SUCCESS);
                // 切换 View 操作
                if (ViewUtils.reverseVisibilitys(success, contentAssist.contentLinear, contentAssist.stateLinear)) {
                    // 属于请求成功
                } else {
                    if (type == ViewAssist.TYPE_ING) {
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
    public void initOther() {
        super.initOther();
        // 表示请求中
        stateLayout.showIng();
        // 获取文章列表
        presenter.getArticleLists();
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
    public void onArticleListResponse(
            boolean succeed,
            ArticleBean articleBean
    ) {
        if (succeed) {
            if (CollectionUtils.isEmpty(articleBean.data.datas)) { // 无数据
                stateLayout.showEmptyData();
            } else { // 请求成功
                stateLayout.showSuccess();
                // 设置数据源
                articleAdapter.setNewInstance(articleBean.data.datas);
            }
        } else { // 请求失败
            stateLayout.showFailed();
        }
    }

    @Override
    public void addDisposable(Disposable disposable) {
        RxJavaManager.getInstance().add(TAG, disposable);
    }
}