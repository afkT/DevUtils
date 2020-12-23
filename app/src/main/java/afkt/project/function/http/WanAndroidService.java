package afkt.project.function.http;

import afkt.project.model.bean.ArticleBean;
import afkt.project.model.bean.UserBean;
import dev.other.retrofit.response.BaseResponse;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * detail: 玩 Android API Service
 * @author Ttt
 */
public interface WanAndroidService {

    @GET("/article/list/{page}/json")
    Flowable<ArticleBean> getArticleList(@Path("page") int page);

    // 演示 BaseBeanSubscriber、BaseResponseSubscriber 区别
    @POST("/xxx/login")
    Flowable<BaseResponse<UserBean>> login(
            @Query("userName") String userName,
            @Query("password") String password
    );
}