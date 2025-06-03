package afkt.retrofit.use.request

import afkt.retrofit.use.base.BaseViewModel
import afkt.retrofit.use.helper.PhotoBean
import afkt.retrofit.use.helper.ResponseHelper
import androidx.databinding.ObservableField

/**
 * detail: request_coroutines.kt 使用方法 ViewModel
 * @author Ttt
 */
class RequestViewModel(
    private val repository: RequestRepository = RequestRepository()
) : BaseViewModel() {

    // request_coroutines.kt 文案
    val tipsText = ObservableField(
        "DevRetrofit 库 request_coroutines.kt 封装使用示例"
    )

    // ==============
    // = 模拟请求错误 =
    // ==============

    // 模拟请求错误 ( 后台统一格式 )
    val clickMockError: () -> Unit = {
        requestMockError()
    }

    // 模拟请求错误 ( Base.Response 实现 )
    val clickMockError2: () -> Unit = {
        requestMockError2()
    }

    // ==============
    // = 摄影图片列表 =
    // ==============

    // 获取摄影图片列表 ( 后台统一格式 )
    val clickPhotoList: () -> Unit = {
        requestPhotoList()
    }

    // 获取摄影图片列表 ( Base.Response 实现 )
    val clickPhotoList2: () -> Unit = {
        requestPhotoList2()
    }

    // 获取摄影图片列表 ( 回调通知 )
    val clickPhotoListCallback: () -> Unit = {
        requestPhotoListCallback()
    }

    // ==========
    // = 请求方法 =
    // ==========

    /**
     * 模拟请求错误 ( 后台统一格式 )
     */
    private fun requestMockError() {
        val tag = "RequestViewModel_requestMockError"
        repository.requestMockError(this, errorBlock = { error ->
            ResponseHelper.errorResponse(tag, error)
        }) { code, message ->
            ResponseHelper.log(tag, "code：${code}，message：${message}")
        }
    }

    /**
     * 模拟请求错误 ( Base.Response 实现 )
     */
    private fun requestMockError2() {
        val tag = "RequestViewModel_requestMockError2"
        repository.requestMockError2(this, errorBlock = { error ->
            ResponseHelper.errorResponse(tag, error)
        }) { code, message ->
            ResponseHelper.log(tag, "code：${code}，message：${message}")
        }
    }

    /**
     * 获取摄影图片列表 ( 后台统一格式 )
     */
    private fun requestPhotoList() {
        val tag = "RequestViewModel_requestPhotoList"
        repository.fetchPhotoList(this, startBlock = {
            ResponseHelper.startRequest(tag)
        }, finishBlock = {
            ResponseHelper.finishRequest(tag)
        }, errorBlock = { error ->
            ResponseHelper.errorResponse(tag, error)
        }) {
            ResponseHelper.successResponse(tag, it)
        }
    }

    /**
     * 获取摄影图片列表 ( Base.Response 实现 )
     */
    private fun requestPhotoList2() {
        val tag = "RequestViewModel_requestPhotoList2"
        repository.fetchPhotoList2(this, startBlock = {
            ResponseHelper.startRequest(tag)
        }, finishBlock = {
            ResponseHelper.finishRequest(tag)
        }, errorBlock = { error ->
            ResponseHelper.errorResponse(tag, error)
        }) {
            ResponseHelper.successResponse(tag, it)
        }
    }

    /**
     * 获取摄影图片列表 ( 回调通知 )
     */
    private fun requestPhotoListCallback() {
        val tag = "RequestViewModel_requestPhotoListCallback"
        repository.fetchPhotoListCallback(
            this,
            // 当前请求每个阶段进行通知【日志 TAG 为 RequestStageCallback】
            callback = ResponseHelper.RequestStageCallback<List<PhotoBean>>(
                "RequestStageCallback"
            ).setParams("自定义参数"),
            // 全局通知回调方法【日志 TAG 为 ResponseHelper】
            globalCallback = ResponseHelper.globalCallback()
        ) {
            ResponseHelper.successResponse(tag, it)
        }
    }
}