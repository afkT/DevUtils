package afkt.project.framework.mvvm

import afkt.project.function.http.RetrofitUtils
import afkt.project.model.bean.ArticleBean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import ktx.dev.other.retrofit_rxjava.RxJavaManager
import ktx.dev.other.retrofit_rxjava.subscriber.BaseBeanSubscriber

/**
 * detail: 文章相关 Repository
 * @author Ttt
 * 在组件化下可考虑以模块命名, 全部统一在一个文件内
 * 如 UserRepository 便于减少类数量 ( 以及复杂性, 方便整个模块便捷使用统一维护 )
 */
class ArticleRepository {

    fun requestArticleLists(
        owner: LifecycleOwner,
        article: MutableLiveData<ArticleBean?>
    ): MutableLiveData<ArticleBean?> {
        val provider: LifecycleProvider<*> = AndroidLifecycle.createLifecycleProvider(owner)
        /**
         * 通过 RxLifecycle 则无需手动进行 RxJava 管理
         * // 通过 tag 将请求添加到统一管理对象中
         * RxJavaManager.instance.add(TAG, disposable)
         * // 取消 TAG ( Activity ) 关联的请求
         * RxJavaManager.instance.remove(TAG)
         */
        RetrofitUtils.instance.wanAndroidService().getArticleList(0)
            .compose(RxJavaManager.instance.io_main())
            .compose(provider.bindToLifecycle<ArticleBean?>())
            .subscribeWith(object : BaseBeanSubscriber<ArticleBean>() {
                override fun onSuccessResponse(data: ArticleBean) {
                    article.postValue(data)
                }

                override fun onErrorResponse(
                    throwable: Throwable?,
                    message: String?
                ) {
                    article.postValue(null)
                }
            })
        return article
    }
}