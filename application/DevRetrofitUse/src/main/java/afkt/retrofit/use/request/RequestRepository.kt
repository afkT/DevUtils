package afkt.retrofit.use.request

import afkt.retrofit.use.base.BaseViewModel
import afkt.retrofit.use.helper.AppResponse
import afkt.retrofit.use.helper.PhotoBean
import afkt.retrofit.use.helper.RetrofitAPI
import afkt.retrofit.use.helper.ServiceException
import dev.simple.mvvm.utils.hi.hiif.hiIfNotNull
import dev.retrofit.Notify
import dev.retrofit.launchExecuteRequest

/**
 * detail: Request Repository
 * @author Ttt
 * DevRetrofit 库 request_coroutines.kt 封装使用示例
 */
class RequestRepository {

    // ==============
    // = 模拟请求错误 =
    // ==============

    /**
     * 模拟请求错误 ( 后台统一格式 )
     */
    fun requestMockError(
        viewModel: BaseViewModel,
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (String?, String?) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            RetrofitAPI.api().mockError()
        }, start = {}, success = { result ->
            result.hiIfNotNull {
                successBlock.invoke(it.code, it.message)
            }
        }, error = {
            errorBlock.invoke(it)
        }, finish = {})
    }

    /**
     * 模拟请求错误 ( Base.Response 实现 )
     */
    fun requestMockError2(
        viewModel: BaseViewModel,
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (String?, String?) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            RetrofitAPI.api().app_mockError()
        }, start = {}, success = { result ->
            result.hiIfNotNull {
                successBlock.invoke(it.getCode(), it.getMessage())
            }
        }, error = {
            errorBlock.invoke(it)
        }, finish = {})
    }

    // ==============
    // = 摄影图片列表 =
    // ==============

    /**
     * 获取摄影图片列表 ( 后台统一格式 )
     */
    fun fetchPhotoList(
        viewModel: BaseViewModel,
        startBlock: () -> Unit = {},
        finishBlock: () -> Unit = {},
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (List<PhotoBean>) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            RetrofitAPI.api().getPhotoList()
        }, start = {
            startBlock.invoke()
        }, success = { result ->
            result.hiIfNotNull {
                if (it.isSuccess()) {
                    successBlock.invoke(it.data)
                } else {
                    throw ServiceException(it.message)
                }
            }
        }, error = {
            errorBlock.invoke(it)
        }, finish = {
            finishBlock.invoke()
        })
    }

    /**
     * 获取摄影图片列表 ( Base.Response 实现 )
     */
    fun fetchPhotoList2(
        viewModel: BaseViewModel,
        startBlock: () -> Unit = {},
        finishBlock: () -> Unit = {},
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (List<PhotoBean>) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            RetrofitAPI.api().app_getPhotoList()
        }, start = {
            startBlock.invoke()
        }, success = { result ->
            result.hiIfNotNull {
                if (it.isSuccess()) {
                    successBlock.invoke(it.requireData())
                } else {
                    throw ServiceException(it.getMessage())
                }
            }
        }, error = {
            errorBlock.invoke(it)
        }, finish = {
            finishBlock.invoke()
        })
    }

    /**
     * 获取摄影图片列表 ( 回调通知 )
     */
    fun fetchPhotoListCallback(
        viewModel: BaseViewModel,
        // 当前请求每个阶段进行通知
        callback: Notify.Callback<AppResponse<List<PhotoBean>>>? = null,
        // 全局通知回调方法 ( 创建一个全局通用传入 )
        globalCallback: Notify.GlobalCallback? = null,
        // 成功回调
        successBlock: (List<PhotoBean>) -> Unit
    ) {
        viewModel.launchExecuteRequest(
            block = {
                RetrofitAPI.api().app_getPhotoList()
            }, start = {}, success = { result ->
                result.hiIfNotNull {
                    if (it.isSuccess()) {
                        successBlock.invoke(it.requireData())
                    } else {
                        throw ServiceException(it.getMessage())
                    }
                }
            }, error = {}, finish = {},
            callback = callback, globalCallback = globalCallback
        )
    }
}