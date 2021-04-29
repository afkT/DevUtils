package afkt.project.framework.mvvm

import afkt.project.function.http.RetrofitUtils
import afkt.project.model.bean.ArticleBean
import androidx.lifecycle.MutableLiveData
import dev.other.retrofit.RxJavaManager
import dev.other.retrofit.subscriber.BaseBeanSubscriber

/**
 * detail: 文章相关 Repository
 * @author Ttt
 * 在组件化下可考虑以模块命名，全部统一在一个文件内
 * 如 UserRepository 便于减少类数量 ( 以及复杂性, 方便整个模块便捷使用统一维护 )
 */
class ArticleRepository {

    fun requestArticleLists(article: MutableLiveData<ArticleBean?>): MutableLiveData<ArticleBean?> {
        RetrofitUtils.getWanAndroidService().getArticleList(0)
            .compose(RxJavaManager.io_main())
            .subscribeWith(object : BaseBeanSubscriber<ArticleBean>() {
                override fun onSuccessResponse(data: ArticleBean) {
                    article.postValue(data)
                }

                override fun onErrorResponse(
                    throwable: Throwable,
                    message: String
                ) {
                    article.postValue(null)
                }
            })
        return article
    }
}