package afkt.project.feature.framework.mvvm

import afkt.project.base.http.RetrofitManagerUse
import afkt.project.model.bean.ArticleBean
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
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
        // AutoDispose
        val provider = AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)

//        // RxLifecycle
//        val provider: LifecycleProvider<*> = AndroidLifecycle.createLifecycleProvider(owner)

        /**
         * 通过 RxLifecycle、AutoDispose 则无需手动进行 RxJava 管理
         * // 通过 tag 将请求添加到统一管理对象中
         * RxJavaManager.instance.add(TAG, disposable)
         * // 取消 TAG ( Activity ) 关联的请求
         * RxJavaManager.instance.remove(TAG)
         */
        RetrofitManagerUse.api().getArticleList(0)
            .compose(RxJavaManager.instance.io_main())
            // AutoDispose
            .autoDispose(provider)
//            // RxLifecycle
//            .compose(provider.bindToLifecycle<ArticleBean?>())
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