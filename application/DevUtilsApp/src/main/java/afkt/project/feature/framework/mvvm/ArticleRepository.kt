package afkt.project.feature.framework.mvvm

import afkt.project.base.http.RetrofitManagerUse
import afkt.project.model.bean.ArticleBean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import dev.retrofit.liveDataLaunchExecuteRequest

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
        owner.liveDataLaunchExecuteRequest(
            block = {
                RetrofitManagerUse.api().getArticleList(0)
            }, article
        )
        return article
    }
}