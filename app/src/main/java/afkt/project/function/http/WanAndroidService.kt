package afkt.project.function.http

import afkt.project.model.bean.ArticleBean
import afkt.project.model.bean.UserBean
import io.reactivex.rxjava3.core.Flowable
import ktx.dev.other.retrofit_rxjava.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * detail: 玩 Android API Service
 * @author Ttt
 */
interface WanAndroidService {

    @GET("/article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Flowable<ArticleBean?>

    // 演示 BaseBeanSubscriber、BaseResponseSubscriber 区别
    @POST("/xxx/login")
    fun login(
        @Query("userName") userName: String?,
        @Query("password") password: String?
    ): Flowable<BaseResponse<UserBean?>>
}