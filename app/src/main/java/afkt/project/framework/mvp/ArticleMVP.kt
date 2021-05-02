package afkt.project.framework.mvp

import afkt.project.function.http.RetrofitUtils
import afkt.project.model.bean.ArticleBean
import dev.base.expand.mvp.MVP
import dev.base.expand.mvp.MVP.IModel
import dev.base.expand.mvp.MVP.IView
import dev.other.retrofit.RxJavaManager
import dev.other.retrofit.subscriber.BaseBeanSubscriber
import io.reactivex.rxjava3.disposables.Disposable

/**
 * detail: 文章 MVP Contract
 * @author Ttt
 */
class ArticleMVP {

    interface Model : IModel {

        /**
         * 请求文章列表
         */
        fun requestArticleLists()
    }

    interface View : IView {

        /**
         * 获取文章列表响应回调
         * @param succeed     请求是否成功
         * @param articleBean 文章列表
         */
        fun onArticleListResponse(
            succeed: Boolean,
            articleBean: ArticleBean?
        )

        /**
         * Retrofit 请求管理
         * @param disposable [Disposable]
         * 该方法, 应该写在 MVP.IView 中并且在 MVP Activity 基类 implements MVP.IView
         * 基类实现 addDisposable 该方法代码
         * RxJavaManager.getInstance().add(TAG, disposable)
         * 用于 Retrofit 请求管理, 在 Activity onDestroy 调用
         * RxJavaManager.getInstance().remove(TAG)
         * 这样能够实现请求跟随 Activity 生命周期销毁
         * 目前这样写, 是不想改变 MVP 结构以及在 DevBase Module 依赖 RxJava, 具体项目 copy 改造 MVP
         */
        fun addDisposable(disposable: Disposable)
    }

    interface IPresenter : MVP.IPresenter<View> {
        /**
         * 获取文章列表
         */
        fun articleLists()
    }

    /**
     * detail: 文章 Presenter
     * @author Ttt
     */
    class Presenter(view: View) : MVP.Presenter<View, Model>(view),
        IPresenter {

        init {
            mvpModel = object : Model {
                override fun requestArticleLists() {
                    // 映射各种 JSON 实体类
                    val articleList =
                        RetrofitUtils.instance.wanAndroidService().getArticleList(0)
                            .compose(RxJavaManager.io_main())
                            .subscribeWith(object : BaseBeanSubscriber<ArticleBean>() {
                                override fun onSuccessResponse(data: ArticleBean) {
                                    mvpView?.onArticleListResponse(true, data)
                                }

                                override fun onErrorResponse(
                                    throwable: Throwable,
                                    message: String
                                ) {
                                    mvpView?.onArticleListResponse(false, null)
                                }
                            })
                    mvpView?.addDisposable(articleList)
                }
            }
        }

        override fun articleLists() {
            mvpModel?.requestArticleLists()
        }
    }
}