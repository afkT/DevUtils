package afkt.project.feature.framework.mvvm

import afkt.project.data_model.bean.ArticleBean
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * detail: 文章相关 ViewModel
 * @author Ttt
 * 在组件化下可考虑以模块命名, 全部统一在一个文件内
 * 如 UserViewModel 便于减少类数量 ( 以及复杂性, 方便整个模块便捷使用统一维护 )
 */
class ArticleViewModel : ViewModel(),
    DefaultLifecycleObserver {

    val article = MutableLiveData<ArticleBean?>()

    // Repository
    private val repository = ArticleRepository()

    // LifecycleOwner
    private lateinit var lifecycleOwner: LifecycleOwner

    /**
     * 请求文章列表
     */
    fun requestArticleLists(): MutableLiveData<ArticleBean?> {
        return repository.requestArticleLists(lifecycleOwner, article)
    }

    // =============
    // = lifecycle =
    // =============

    fun lifecycle(owner: LifecycleOwner) {
        lifecycleOwner = owner
        owner.lifecycle.addObserver(this)
    }
}