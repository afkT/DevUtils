package afkt.project.function.http;

import afkt.project.model.bean.ArticleBean;
import afkt.project.model.bean.UserBean;
import dev.other.retrofit.RxJavaManager;
import dev.other.retrofit.response.BaseResponse;
import dev.other.retrofit.subscriber.BaseBeanSubscriber;
import dev.other.retrofit.subscriber.BaseResponseSubscriber;

/**
 * detail: Retrofit 请求 Demo
 * @author Ttt
 */
public class RetrofitDemo {

    // 日志 TAG
    private static final String TAG = RetrofitDemo.class.getSimpleName();

    /**
     * demo 使用
     */
    private static void demo() {

        // 映射各种 JSON 实体类
        BaseBeanSubscriber<ArticleBean> articleList = RetrofitUtils.getWanAndroidService().getArticleList(0)
                .compose(RxJavaManager.io_main())
                .subscribeWith(new BaseBeanSubscriber<ArticleBean>() {
                    @Override
                    public void onSuccessResponse(ArticleBean data) {
                    }

                    @Override
                    public void onErrorResponse(
                            Throwable throwable,
                            String message
                    ) {
                    }
                });
        RxJavaManager.getInstance().add(TAG, articleList);

        // 映射统一标准 JSON 格式实体类
        BaseResponseSubscriber<UserBean> login = RetrofitUtils.getWanAndroidService()
                .login("user", "pwd")
                .compose(RxJavaManager.io_main())
                .subscribeWith(new BaseResponseSubscriber<UserBean>() {
                    @Override
                    public void onSuccessResponse(BaseResponse<UserBean> response) {
                    }

                    @Override
                    public void onErrorResponse(BaseResponse<UserBean> response) {
                    }
                });
        RxJavaManager.getInstance().add(TAG, login);
    }
}