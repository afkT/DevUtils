package afkt.project.framework.mvvm

import afkt.project.model.bean.ArticleBean
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * detail: 文章相关 ViewModel
 * @author Ttt
 * 在组件化下可考虑以模块命名，全部统一在一个文件内
 * 如 UserViewModel 便于减少类数量 ( 以及复杂性, 方便整个模块便捷使用统一维护 )
 */
class ArticleViewModel : ViewModel(),
    DefaultLifecycleObserver {

    var article = MutableLiveData<ArticleBean?>()

    // Repository
    var repository = ArticleRepository()

    /**
     * 请求文章列表
     */
    fun requestArticleLists(): MutableLiveData<ArticleBean?> {
        return repository.requestArticleLists(article)
    }
}