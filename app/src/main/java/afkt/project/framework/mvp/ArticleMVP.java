package afkt.project.framework.mvp;

import afkt.project.base.constants.http.HttpApis;
import afkt.project.model.bean.ArticleBean;
import dev.base.mvp.MVP;
import dev.other.GsonUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.common.HttpURLConnectionUtils;

/**
 * detail: 文章 MVP
 * @author Ttt
 */
public final class ArticleMVP {

    public interface Model extends MVP.IModel {

        /**
         * 请求文章列表
         */
        void requestArticleLists();
    }

    public interface View extends MVP.IView {

        /**
         * 获取文章列表响应回调
         * @param succeed     请求是否成功
         * @param articleBean 文章列表
         */
        void onArticleListResponse(boolean succeed, ArticleBean articleBean);
    }

    public interface IPresenter extends MVP.IPresenter<View> {

        /**
         * 获取文章列表
         */
        void getArticleLists();
    }

    /**
     * detail: 文章 Presenter
     * @author Ttt
     */
    public static class Presenter extends MVP.Presenter<View, Model> implements IPresenter {

        public Presenter() {
            mModel = new Model() {
                @Override
                public void requestArticleLists() {
                    HttpURLConnectionUtils.doGetAsyn(String.format(HttpApis.ARTICLE_LIST, "0"),
                            new HttpURLConnectionUtils.CallBack() {
                                @Override
                                public void onResponse(String result, long response) {
                                    if (mView != null) {
                                        HandlerUtils.postRunnable(new Runnable() {
                                            @Override
                                            public void run() {
                                                ArticleBean articleBean = GsonUtils.fromJson(result, ArticleBean.class);
                                                mView.onArticleListResponse(true, articleBean);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFail(Exception e) {
                                    if (mView != null) {
                                        HandlerUtils.postRunnable(new Runnable() {
                                            @Override
                                            public void run() {
                                                mView.onArticleListResponse(false, null);
                                            }
                                        });
                                    }
                                }
                            });
                }
            };
        }

        @Override
        public void getArticleLists() {
            mModel.requestArticleLists();
        }
    }
}
