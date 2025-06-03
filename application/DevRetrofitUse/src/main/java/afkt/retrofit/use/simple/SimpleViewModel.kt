package afkt.retrofit.use.simple

import afkt.retrofit.use.base.BaseViewModel
import afkt.retrofit.use.helper.AppResponse
import afkt.retrofit.use.helper.MovieDetailBean
import afkt.retrofit.use.helper.ResponseHelper
import androidx.databinding.ObservableField
import dev.mvvm.utils.hi.hiif.hiIfNotNull
import java.util.*

/**
 * detail: request_coroutines_simple.kt 使用方法 ViewModel
 * @author Ttt
 */
class SimpleViewModel(
    private val repository: SimpleRepository = SimpleRepository()
) : BaseViewModel() {

    // request_coroutines_simple.kt 文案
    val tipsText = ObservableField(
        "DevRetrofit 库 request_coroutines_simple.kt 封装使用示例"
    )

    // 电影详情信息
    val movieDetail = ObservableField<MovieDetailBean>()

    // ==============
    // = 电影详情信息 =
    // ==============

    // 获取电影详情信息
    val clickMovieDetail: () -> Unit = {
        requestMovieDetail()
    }

    // ==========
    // = 请求方法 =
    // ==========

    /**
     * 获取电影详情信息
     */
    private fun requestMovieDetail() {
        repository.fetchMovieDetail(
            this,
            // 当前请求每个阶段进行通知【日志 TAG 为 SimpleViewModel_requestMovieDetail】
            callback = MovieDetailCallback(
                "SimpleViewModel_requestMovieDetail", movieDetail
            ).setParams("{\"id\":130}")
        )
    }

    // ============
    // = 自定义回调 =
    // ============

    /**
     * detail: 电影详情请求回调
     * @author Ttt
     */
    private class MovieDetailCallback(
        tag: String,
        private val movieDetail: ObservableField<MovieDetailBean>
    ) : ResponseHelper.RequestStageCallback<MovieDetailBean>(tag) {

        override fun onSuccess(
            uuid: UUID,
            data: AppResponse<MovieDetailBean>?
        ) {
            super.onSuccess(uuid, data)

            data.hiIfNotNull { result ->
                if (result.isSuccessWithData()) {
                    movieDetail.set(result.requireData())
                    // 打印日志
                    ResponseHelper.log(
                        tag, "设置 ObservableField<MovieDetailBean> 值：${movieDetail.get()}"
                    )
                } else {
                    // 其他状态处理
                    if (result.isSuccess()) {
                        // 请求成功但是不存在数据
                    } else {
                        // 请求失败 code 非成功
                        val code = result.getCode()
                        val message = result.getMessage()
                    }
                }
            }
        }
    }
}