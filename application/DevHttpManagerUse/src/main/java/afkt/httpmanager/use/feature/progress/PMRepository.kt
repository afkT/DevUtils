package afkt.httpmanager.use.feature.progress

import afkt.httpmanager.use.base.BaseViewModel
import afkt.httpmanager.use.feature.progress.download.data.api.DownloadAPI
import afkt.httpmanager.use.feature.progress.download.data.api.DownloadAPI2
import afkt.httpmanager.use.feature.progress.upload.data.api.UploadAPI
import afkt.httpmanager.use.network.helper.ResponseHelper
import dev.retrofit.launchExecuteRequest
import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 * detail: Progress Manager Request Repository
 * @author Ttt
 */
class PMRepository {

    // 日志 TAG
    private val TAG = PMRepository::class.java.simpleName

    // =======
    // = 下载 =
    // =======

    /**
     * 下载文件
     */
    fun downloadFile(
        viewModel: BaseViewModel,
        url: String,
        startBlock: () -> Unit = {},
        finishBlock: () -> Unit = {},
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (ResponseBody) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            DownloadAPI.api().downloadFile(url)
        }, start = {
            startBlock.invoke()
            // 打印日志
            ResponseHelper.startRequest(TAG)
        }, success = { result ->
            successBlock.invoke(result!!)
        }, error = {
            errorBlock.invoke(it)
            // 打印日志
            ResponseHelper.errorResponse(TAG, it)
        }, finish = {
            finishBlock.invoke()
            // 打印日志
            ResponseHelper.finishRequest(TAG)
        })
    }

    /**
     * 下载文件
     */
    fun downloadFile2(
        viewModel: BaseViewModel,
        url: String,
        startBlock: () -> Unit = {},
        finishBlock: () -> Unit = {},
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (ResponseBody) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            DownloadAPI2.api().downloadFile(url)
        }, start = {
            startBlock.invoke()
            // 打印日志
            ResponseHelper.startRequest(TAG)
        }, success = { result ->
            successBlock.invoke(result!!)
        }, error = {
            errorBlock.invoke(it)
            // 打印日志
            ResponseHelper.errorResponse(TAG, it)
        }, finish = {
            finishBlock.invoke()
            // 打印日志
            ResponseHelper.finishRequest(TAG)
        })
    }

    // =======
    // = 上传 =
    // =======

    /**
     * 下载文件
     */
    fun uploadFile(
        viewModel: BaseViewModel,
        url: String,
        body: RequestBody,
        startBlock: () -> Unit = {},
        finishBlock: () -> Unit = {},
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (ResponseBody) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            UploadAPI.api().uploadFile(url, body)
        }, start = {
            startBlock.invoke()
            // 打印日志
            ResponseHelper.startRequest(TAG)
        }, success = { result ->
            successBlock.invoke(result!!)
        }, error = {
            errorBlock.invoke(it)
            // 打印日志
            ResponseHelper.errorResponse(TAG, it)
        }, finish = {
            finishBlock.invoke()
            // 打印日志
            ResponseHelper.finishRequest(TAG)
        })
    }
}