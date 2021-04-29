package afkt.project.function.http

import afkt.project.model.bean.ArticleBean
import afkt.project.model.bean.UserBean
import dev.other.retrofit.RxJavaManager
import dev.other.retrofit.response.BaseResponse
import dev.other.retrofit.subscriber.BaseBeanSubscriber
import dev.other.retrofit.subscriber.BaseResponseSubscriber

/**
 * detail: Retrofit 请求 Demo
 * @author Ttt
 */
object RetrofitDemo {

    // 日志 TAG
    private val TAG = RetrofitDemo::class.java.simpleName

    /**
     * demo 使用
     */
    private fun demo() {

        // 映射各种 JSON 实体类
        val articleList =
            RetrofitUtils.instance.wanAndroidService().getArticleList(0)
                .compose(RxJavaManager.io_main())
                .subscribeWith(object : BaseBeanSubscriber<ArticleBean>() {
                    override fun onSuccessResponse(data: ArticleBean) {
                    }

                    override fun onErrorResponse(
                        throwable: Throwable,
                        message: String
                    ) {
                    }
                })
        RxJavaManager.getInstance().add(TAG, articleList)

        // 映射统一标准 JSON 格式实体类
        val login =
            RetrofitUtils.instance.wanAndroidService()
                .login("user", "pwd")
                .compose<BaseResponse<UserBean?>>(RxJavaManager.io_main())
                .subscribeWith(object : BaseResponseSubscriber<UserBean?>() {
                    override fun onSuccessResponse(response: BaseResponse<UserBean?>) {
                    }

                    override fun onErrorResponse(response: BaseResponse<UserBean?>) {
                    }
                })
        RxJavaManager.getInstance().add(TAG, login)
    }
}