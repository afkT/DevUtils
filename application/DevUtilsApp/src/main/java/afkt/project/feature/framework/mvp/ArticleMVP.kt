package afkt.project.feature.framework.mvp

import afkt.project.base.http.RetrofitManagerUse
import afkt.project.model.bean.ArticleBean
import androidx.lifecycle.LifecycleOwner
import dev.base.expand.mvp.MVP
import dev.base.expand.mvp.MVP.IModel
import dev.base.expand.mvp.MVP.IView
import dev.retrofit.Notify
import dev.retrofit.simpleLaunchExecuteRequest
import java.util.*

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
    class Presenter(
        view: View,
        owner: LifecycleOwner
    ) : MVP.Presenter<View, Model>(view),
        IPresenter {

        init {
            mvpModel = object : Model {
                override fun requestArticleLists() {
                    owner.simpleLaunchExecuteRequest(
                        block = {
                            RetrofitManagerUse.api().getArticleList(0)
                        },
                        callback = object : Notify.Callback<ArticleBean>() {
                            override fun onSuccess(
                                uuid: UUID,
                                data: ArticleBean?
                            ) {
                                mvpView?.onArticleListResponse(true, data)
                            }

                            override fun onError(
                                uuid: UUID,
                                error: Throwable?
                            ) {
                                mvpView?.onArticleListResponse(false, null)
                            }
                        }
                    )
                }
            }
        }

        override fun articleLists() {
            mvpModel?.requestArticleLists()
        }
    }
}