package afkt.httpmanager.use.feature.progress.upload

import afkt.httpmanager.use.base.BaseViewModel
import afkt.httpmanager.use.feature.progress.PMRepository
import afkt.httpmanager.use.feature.progress.upload.data.helper.UploadHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

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
    val clickUploadImage: () -> Unit = clickUploadImage@{
        uploadImages()
    }

    // ===================
    // = OkHttp - 上传监听 =
    // ===================

    // 上传文件
    val clickUploadFile: () -> Unit = clickUploadFile@{
    }

    // ==========
    // = 请求方法 =
    // ==========

    /**
     * 上传图片列表
     */
    private fun uploadImages() {
//        repository.uploadFile(this, UPLOAD_URL)
//        // 开始下载文件
//        repository.downloadFile(this, URL_MP4, startBlock = {
//            updateUploadMessage("开始上传多张图片")
//        }, errorBlock = {
//            dismissUploadDialog()
//            ProgressHelper.toastError("图片上传失败")
//        }) {
//            UploadHelper.writeFile(URL_MP4, it) {
//                dismissUploadDialog()
//                ProgressHelper.toastError("图片上传失败")
//            }
//        }
        updateUploadMessage("开始读取图片列表")
        // 获取上传文件集合
        UploadHelper.fileLists { files ->
            if (files.isEmpty()) {
                dismissUploadDialog()
                return@fileLists
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
}