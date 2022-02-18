package afkt.project.base.http

import afkt.project.model.bean.ArticleBean
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * detail: 玩 Android API Service
 * @author Ttt
 */
interface WanAndroidService {

    @GET("/article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Flowable<ArticleBean?>
}