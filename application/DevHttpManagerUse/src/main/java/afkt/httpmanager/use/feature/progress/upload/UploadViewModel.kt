package afkt.httpmanager.use.feature.progress.upload

import afkt.httpmanager.use.base.BaseViewModel
import afkt.httpmanager.use.feature.progress.PMRepository
import afkt.httpmanager.use.feature.progress.download.DownloadViewModel
import afkt.httpmanager.use.feature.progress.upload.data.api.UploadAPI
import afkt.httpmanager.use.feature.progress.upload.data.helper.UploadHelper
import afkt.httpmanager.use.feature.progress.upload.data.helper.toRequestBody
import afkt.httpmanager.use.network.helper.ProgressHelper
import afkt.httpmanager.use.network.helper.ResponseHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.http.progress.Progress
import dev.http.progress.ProgressRequestBody
import dev.utils.common.StringUtils
import okhttp3.MultipartBody

/**
 * detail: Upload Manager ViewModel
 * @author Ttt
 */
open class UploadViewModel(
    protected val repository: PMRepository = PMRepository()
) : BaseViewModel() {

    // 上传地址 - 使用聚合图床自行注册获取 Token
    private val UPLOAD_URL = "https://api.superbed.cn/upload"

    // Token - https://api.superbed.cn/admin
    private val TOKEN = "b127f91752e14c3eba3386775fbde0b3"

    // ===========
    // = 点击事件 =
    // ===========

    // ===========================
    // = DevHttpManager - 上传监听 =
    // ===========================

    // 上传图片
    val clickUploadImage: () -> Unit = {
        uploadImages()
    }

    // ===================
    // = OkHttp - 上传监听 =
    // ===================

    // 上传图片 ( 未使用 DevHttpManager )
    val clickUploadImage2: () -> Unit = {
        uploadImages2()
    }

    // ==========
    // = 请求方法 =
    // ==========

    /**
     * 上传图片列表
     */
    private fun uploadImages() {
        updateUploadMessage("读取上传图片中")
        // 获取上传图片集合
        UploadHelper.fileLists { files ->
            val body = MultipartBody.Builder().setType(MultipartBody.FORM).apply {
                // Token - https://api.superbed.cn/admin
                addFormDataPart("token", TOKEN)
                // 添加全部图片
                files.forEach { file ->
                    addFormDataPart("file", file.name, file.toRequestBody())
                }
            }.build()
            // 开始上传图片
            repository.uploadFile(this, UPLOAD_URL, body, startBlock = {
                updateUploadMessage("开始上传多张图片")
            }, errorBlock = {
                dismissUploadDialog()
                ProgressHelper.toastError("上传失败：${it.message}")
            }) {
                // 图片上传成功回调
                dismissUploadDialog()
                ProgressHelper.toastSuccess("图片上传成功")
                ResponseHelper.successResponse("UploadViewModel", it)
            }
        }
    }

    /**
     * 上传图片列表 ( 未使用 DevHttpManager )
     */
    private fun uploadImages2() {
        updateUploadMessage("读取上传图片中")
        // 获取上传图片集合
        UploadHelper.fileLists { files ->
            val body = MultipartBody.Builder().setType(MultipartBody.FORM).apply {
                // Token - https://api.superbed.cn/admin
                addFormDataPart("token", TOKEN)
                // 添加全部图片
                files.forEach { file ->
                    addFormDataPart("file", file.name, file.toRequestBody())
                }
            }.build()
            // 使用 ProgressRequestBody 进行包装，获取读取进度
            val progressBody = ProgressRequestBody(
                delegate = body, refreshTime = 0L, callback = fileUploadCallback
            )
            // 开始上传图片
            repository.uploadFile2(this, UPLOAD_URL, progressBody, startBlock = {
                updateUploadMessage("开始上传多张图片")
            }, errorBlock = {
                dismissUploadDialog()
                ProgressHelper.toastError("上传失败：${it.message}")
            }) {
                // 图片上传成功回调
                dismissUploadDialog()
                ProgressHelper.toastSuccess("图片上传成功")
                ResponseHelper.successResponse("UploadViewModel", it)
            }
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 上传进度
    private val _uploadMessage = MutableLiveData<String>()
    val uploadMessage: LiveData<String> = _uploadMessage

    /**
     * Update upload message
     * @param text 提示文案
     */
    private fun updateUploadMessage(text: String) {
        _uploadMessage.postValue(text)
    }

    /**
     * Dismiss upload dialog
     */
    private fun dismissUploadDialog() {
        updateUploadMessage("")
    }

    // ============
    // = Callback =
    // ============

    // 图片上传进度回调
    private val fileUploadCallback = object : Progress.Callback {
        override fun onStart(progress: Progress) {
            // 这里是请求响应了，真正的开始上传
            updateUploadMessage("上传中 0%")
        }

        override fun onProgress(progress: Progress) {
            val message = "上传中 ${progress.getPercent()}%"
            if (!StringUtils.equals(message, _uploadMessage.value)) {
                updateUploadMessage(message)
            }
        }

        override fun onError(progress: Progress) {
            ProgressHelper.toastError("图片上传失败")
            dismissUploadDialog()
        }

        override fun onFinish(progress: Progress) {
            // 仅代表请求读取结束，至于结果看服务器返回不一定表示上传成功，例如无权限等
            updateUploadMessage("上传成功，等待响应") // 等服务器返回结果
        }

        override fun onEnd(progress: Progress) {
            // 这里表示 RequestBody 读取结束了，还需要等待服务器返回结果，不代表请求结束了

            /**
             * 可思考对比 [DownloadViewModel] 的 [Progress.Callback] 方法，作用不同，回调意思也不同
             * 一个是请求 ( 服务器接收数据的读取进度，不代表响应回复 )
             * 一个是下载 ( 表示服务器已经响应，回复结果了 )
             */
        }

        /**
         * 不自动回收，方便相同链接再次上传复用进度回调
         */
        override fun isAutoRecycle(progress: Progress): Boolean = false
    }

    init {
        // 添加指定 url 上行监听事件
        UploadAPI.progress().addRequestListener(
            UPLOAD_URL, fileUploadCallback
        )
    }
}