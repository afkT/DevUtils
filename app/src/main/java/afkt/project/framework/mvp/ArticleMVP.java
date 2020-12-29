package afkt.project.framework.mvp;

import afkt.project.function.http.RetrofitUtils;
import afkt.project.model.bean.ArticleBean;
import dev.base.expand.mvp.MVP;
import dev.other.retrofit.RxJavaManager;
import dev.other.retrofit.subscriber.BaseBeanSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * detail: 文章 MVP Contract
 * @author Ttt
 */
public final class ArticleMVP {

    public interface Model
            extends MVP.IModel {

        /**
         * 请求文章列表
         */
        void requestArticleLists();
    }

    public interface View
            extends MVP.IView {

        /**
         * 获取文章列表响应回调
         * @param succeed     请求是否成功
         * @param articleBean 文章列表
         */
        void onArticleListResponse(
                boolean succeed,
                ArticleBean articleBean
        );

        /**
         * Retrofit 请求管理
         * <pre>
         *     该方法, 应该写在 MVP.IView 中并且在 MVP Activity 基类 implements MVP.IView
         *     基类实现 addDisposable 该方法代码
         *     RxJavaManager.getInstance().add(TAG, disposable);
         *     用于 Retrofit 请求管理, 在 Activity onDestroy 调用
         *     RxJavaManager.getInstance().remove(TAG);
         *     这样能够实现请求跟随 Activity 生命周期销毁
         *     <p></p>
         *     目前这样写, 是不想改变 MVP 结构以及在 DevBase Module 依赖 RxJava, 具体项目 copy 改造 MVP
         * </pre>
         * @param disposable {@link Disposable}
         */
        void addDisposable(Disposable disposable);
    }

    public interface IPresenter
            extends MVP.IPresenter<View> {

        /**
         * 获取文章列表
         */
        void getArticleLists();
    }

    /**
     * detail: 文章 Presenter
     * @author Ttt
     */
    public static class Presenter
            extends MVP.Presenter<View, Model>
            implements IPresenter {

        public Presenter(ArticleMVP.View view) {
            super(view);
            mvpModel = new Model() {
                @Override
                public void requestArticleLists() {
                    // 映射各种 JSON 实体类
                    BaseBeanSubscriber<ArticleBean> articleList = RetrofitUtils.getWanAndroidService().getArticleList(0)
                            .compose(RxJavaManager.io_main())
                            .subscribeWith(new BaseBeanSubscriber<ArticleBean>() {
                                @Override
                                public void onSuccessResponse(ArticleBean data) {
                                    if (mvpView != null) {
                                        mvpView.onArticleListResponse(true, data);
                                    }
                                }

                                @Override
                                public void onErrorResponse(
                                        Throwable throwable,
                                        String message
                                ) {
                                    if (mvpView != null) {
                                        mvpView.onArticleListResponse(false, null);
                                    }
                                }
                            });
                    mvpView.addDisposable(articleList);
                }
            };
        }

        @Override
        public void getArticleLists() {
            mvpModel.requestArticleLists();
        }
    }
}