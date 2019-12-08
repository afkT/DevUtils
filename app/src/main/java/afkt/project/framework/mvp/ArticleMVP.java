package afkt.project.framework.mvp;

import afkt.project.model.bean.ArticleBean;
import dev.base.mvp.MVP;

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

                }
            };
        }

        @Override
        public void getArticleLists() {
            mModel.requestArticleLists();
        }
    }
}
