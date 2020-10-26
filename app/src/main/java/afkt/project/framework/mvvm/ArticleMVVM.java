package afkt.project.framework.mvvm;

import afkt.project.base.constants.http.HttpApis;
import afkt.project.databinding.ActivityArticleMvvmBinding;
import afkt.project.model.bean.ArticleBean;
import afkt.project.ui.adapter.ArticleAdapter;
import dev.other.GsonUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.common.CollectionUtils;
import dev.utils.common.HttpURLConnectionUtils;

/**
 * detail: 文章 MVVM
 * @author Ttt
 */
public final class ArticleMVVM {

    public interface Model {

        /**
         * 请求文章列表
         * @param callBack 请求回调
         */
        void requestArticleLists(HttpURLConnectionUtils.CallBack callBack);
    }

    public static class ModelImpl implements Model {
        @Override
        public void requestArticleLists(HttpURLConnectionUtils.CallBack callBack) {
            HttpURLConnectionUtils.doGetAsync(String.format(HttpApis.getArticleListUrl(), "0"), callBack);
        }
    }

    /**
     * detail: 文章 VM
     * @author Ttt
     */
    public static class ViewModel {

        // ViewModel
        private ActivityArticleMvvmBinding mActivityArticleMvvmBinding;
        // 适配器
        private ArticleAdapter             mArticleAdapter;
        // 持有Model 类 - 具体内部实现为实现类
        private Model                      mModel;

        public ViewModel(ActivityArticleMvvmBinding activityArticleMvvmBinding, ArticleAdapter articleAdapter) {
            this.mActivityArticleMvvmBinding = activityArticleMvvmBinding;
            this.mArticleAdapter = articleAdapter;
            this.mModel = new ModelImpl();
        }

        public void getArticleLists() {
            mModel.requestArticleLists(new HttpURLConnectionUtils.CallBack() {
                @Override
                public void onResponse(String result, long response) {
                    HandlerUtils.postRunnable(new Runnable() {
                        @Override
                        public void run() {

                            if (mActivityArticleMvvmBinding != null) {
                                ArticleBean articleBean = GsonUtils.fromJson(result, ArticleBean.class);
                                if (CollectionUtils.isEmpty(articleBean.data.datas)) { // 无数据
                                    mActivityArticleMvvmBinding.vidAamState.showEmptyData();
                                } else { // 请求成功
                                    mActivityArticleMvvmBinding.vidAamState.showSuccess();
                                    // 设置数据源
                                    mArticleAdapter.setNewInstance(articleBean.data.datas);
                                }
                            }
                        }
                    });
                }

                @Override
                public void onFail(Exception e) {
                    HandlerUtils.postRunnable(new Runnable() {
                        @Override
                        public void run() { // 请求失败
                            if (mActivityArticleMvvmBinding != null) {
                                mActivityArticleMvvmBinding.vidAamState.showFailed();
                            }
                        }
                    });
                }
            });
        }
    }
}