package afkt.retrofit.use.simple

import afkt.retrofit.use.base.BaseViewModel
import afkt.retrofit.use.helper.AppResponse
import afkt.retrofit.use.helper.MovieDetailBean
import afkt.retrofit.use.helper.RetrofitAPI
import dev.retrofit.Notify
import dev.retrofit.simpleLaunchExecuteRequest
import dev.retrofit.simpleLaunchExecuteResponseRequest

/**
 * detail: Simple Request Repository
 * @author Ttt
 * DevRetrofit 库 request_coroutines_simple.kt 封装使用示例
 */
class SimpleRepository {

    /**
     * 获取电影详情信息
     */
    fun fetchMovieDetail(
        viewModel: BaseViewModel,
        // 当前请求每个阶段进行通知
        callback: Notify.Callback<AppResponse<MovieDetailBean>>,
        // 全局通知回调方法 ( 创建一个全局通用传入 )
        globalCallback: Notify.GlobalCallback? = null
    ) {
        viewModel.simpleLaunchExecuteRequest(
            block = {
                RetrofitAPI.api().app_getMovieDetail()
            },
            callback = callback, globalCallback = globalCallback
        )
    }

    /**
     * 获取电影详情信息
     */
    fun fetchMovieDetailResult(
        viewModel: BaseViewModel,
        // 当前请求每个阶段进行通知
        callback: Notify.ResultCallback<MovieDetailBean, AppResponse<MovieDetailBean>>,
        // 全局通知回调方法 ( 创建一个全局通用传入 )
        globalCallback: Notify.GlobalCallback? = null
    ) {
        viewModel.simpleLaunchExecuteResponseRequest(
            block = {
                RetrofitAPI.api().app_getMovieDetail()
            },
            callback = callback, globalCallback = globalCallback
        )
    }
}