package afkt.project.framework.mvvm;

import androidx.lifecycle.MutableLiveData;

import afkt.project.function.http.RetrofitUtils;
import afkt.project.model.bean.ArticleBean;
import dev.other.retrofit.RxJavaManager;
import dev.other.retrofit.subscriber.BaseBeanSubscriber;

public class ArticleRepository {

    public MutableLiveData<ArticleBean> requestArticleLists(MutableLiveData<ArticleBean> article) {
        RetrofitUtils.getWanAndroidService().getArticleList(0)
                .compose(RxJavaManager.io_main())
                .subscribeWith(new BaseBeanSubscriber<ArticleBean>() {
                    @Override
                    public void onSuccessResponse(ArticleBean data) {
                        article.postValue(data);
                    }

                    @Override
                    public void onErrorResponse(
                            Throwable throwable,
                            String message
                    ) {
                        article.postValue(null);
                    }
                });
        return article;
    }
}