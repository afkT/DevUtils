package afkt.project.framework.mvvm;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import afkt.project.model.bean.ArticleBean;

public class ArticleViewModel
        extends ViewModel
        implements DefaultLifecycleObserver {

    MutableLiveData<ArticleBean> article = new MutableLiveData<>();

    // Repository
    ArticleRepository repository = new ArticleRepository();

    /**
     * 请求文章列表
     */
    public MutableLiveData<ArticleBean> requestArticleLists() {
        return repository.requestArticleLists(article);
    }
}