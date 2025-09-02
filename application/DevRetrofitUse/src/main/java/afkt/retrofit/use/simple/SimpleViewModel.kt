package afkt.retrofit.use.simple

import afkt.retrofit.use.base.BaseViewModel
import afkt.retrofit.use.helper.*
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.retrofit.Base
import dev.simple.extensions.hi.hiif.hiIfNotNull
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
    val movieDetailOB = ObservableField<MovieDetailBean>()

    // 电影详情信息
    private val _movieDetail = MutableLiveData<MovieDetailBean>()
    val movieDetail: LiveData<MovieDetailBean> = _movieDetail

    // ==============
    // = 电影详情信息 =
    // ==============

    // 获取电影详情信息
    val clickMovieDetail: () -> Unit = {
        requestMovieDetail()
    }

    // 获取电影详情信息 ( Result 回调 )
    val clickMovieDetailResult: () -> Unit = {
        requestMovieDetailResult()
    }

    // ==========
    // = 书籍列表 =
    // ==========

    // 获取书籍列表
    val clickBookList: () -> Unit = {
        requestBookList()
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
                "SimpleViewModel_requestMovieDetail", movieDetailOB
            ).setParams("{\"id\":130}")
        )
    }

    /**
     * 获取电影详情信息 ( Result 回调 )
     */
    private fun requestMovieDetailResult() {
        repository.fetchMovieDetailResult(
            this,
            // 当前请求每个阶段进行通知【日志 TAG 为 SimpleViewModel_requestMovieDetailResult】
            callback = MovieDetailResultCallback(
                "SimpleViewModel_requestMovieDetailResult", _movieDetail
            ).setParams("{\"timeMS\":${System.currentTimeMillis()}}")
        )
    }

    /**
     * 获取书籍列表
     */
    private fun requestBookList() {
        repository.fetchBookList(
            this,
            // 当前请求每个阶段进行通知【日志 TAG 为 SimpleViewModel_requestBookList】
            callback = object : ResponseHelper.RequestStageCallback<BasePage<BookBean>>(
                "SimpleViewModel_requestBookList"
            ) {
                override fun onSuccess(
                    uuid: UUID,
                    data: AppResponse<BasePage<BookBean>>?
                ) {
                    super.onSuccess(uuid, data)

                    data.hiIfNotNull { result ->
                        if (result.isSuccessWithData()) {
                            result.getData().hiIfNotNull { basePage ->
                                basePage.datas.forEach {
                                    ResponseHelper.log(tag, "${it.sub1} - ${it.catalog}")
                                }
                            }
                        }
                    }
                }
            },
            // 全局通知回调方法【日志 TAG 为 ResponseHelper】
            globalCallback = ResponseHelper.globalCallback()
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

    // ==============
    // = Result 回调 =
    // ==============

    /**
     * detail: 电影详情请求回调
     * @author Ttt
     */
    private class MovieDetailResultCallback(
        tag: String,
        private val _movieDetail: MutableLiveData<MovieDetailBean>
    ) : ResponseHelper.RequestResultStageCallback<MovieDetailBean>(tag) {

        override fun onSuccess(
            uuid: UUID,
            data: Base.Result<MovieDetailBean, AppResponse<MovieDetailBean>>
        ) {
            super.onSuccess(uuid, data)
            // 请求成功才更新数据
            if (data.isSuccessWithData()) {
                _movieDetail.value = data.requireData()
            }
        }
    }
}