package afkt.httpmanager.use.feature.progress.download

import afkt.httpmanager.use.feature.progress.download.data.api.DownloadAPI
import afkt.httpmanager.use.feature.progress.download.data.helper.DownloadHelper
import afkt.httpmanager.use.feature.progress.upload.UploadViewModel
import afkt.httpmanager.use.network.helper.ProgressHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.http.progress.Progress
import dev.utils.common.StringUtils

/**
 * detail: Download Manager ViewModel
 * @author Ttt
 */
open class DownloadViewModel() : UploadViewModel() {

    // 下载链接
    private val URL_MP4 = "https://speedtest.ks3-cn-beijing.ksyuncs.com/zhuanma-test/test.mp4"
    private val URL_APK = "https://speedtest.ks3-cn-beijing.ksyuncs.com/download/20181229.apk"

    // 下载进度
    private val _downloadMessage = MutableLiveData<String>()
    val downloadMessage: LiveData<String> = _downloadMessage

    // ===========
    // = 点击事件 =
    // ===========

    // ===========================
    // = DevHttpManager - 下载监听 =
    // ===========================

    // 下载视频
    val clickDownloadVideo: () -> Unit = clickDownloadVideo@{
        if (DownloadHelper.isFileExists(URL_MP4)) {
            dismissDownloadDialog()
            ProgressHelper.toastNormal("视频文件已存在")
            return@clickDownloadVideo
        }
        // 开始下载视频
        downloadVideo()
    }

    // 重新下载视频
    val clickReDownloadVideo: () -> Unit = {
        // 开始下载视频
        downloadVideo()
    }

    // 下载 APK
    val clickDownloadApk: () -> Unit = {
    }

    // 重新下载 APK
    val clickReDownloadApk: () -> Unit = {

    }

    // ===================
    // = OkHttp - 下载监听 =
    // ===================

    // 下载视频
    val clickDownloadVideoOkHttp: () -> Unit = {
    }

    // 重新下载视频
    val clickReDownloadVideoOkHttp: () -> Unit = {

    }

    // ==========
    // = 请求方法 =
    // ==========

    /**
     * 下载视频
     */
    private fun downloadVideo() {
        // 删除已下载文件
        DownloadHelper.deleteUrlDownloadFile(URL_MP4)
        // 开始下载文件
        repository.downloadFile(this, URL_MP4, startBlock = {
            updateDownloadMessage("开始下载视频")
        }) {
            DownloadHelper.writeFile(URL_MP4, it)
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 视频下载进度回调
    private val videoDownloadCallback = object : Progress.Callback {
        override fun onStart(progress: Progress) {
            // 这里是请求响应了，真正的开始下载
            updateDownloadMessage("下载中 0%")
        }

        override fun onProgress(progress: Progress) {
            val message = "下载中 ${progress.getPercent()}%"
            if (!StringUtils.equals(message, _downloadMessage.value)) {
                updateDownloadMessage(message)
            }
        }

        override fun onError(progress: Progress) {
            ProgressHelper.toastError("视频下载失败")
        }

        override fun onFinish(progress: Progress) {
            ProgressHelper.toastSuccess("视频下载成功")
        }

        override fun onEnd(progress: Progress) {
            dismissDownloadDialog()
        }

        /**
         * 方式一：[clickDownloadVideo]、[clickReDownloadVideo] 一直使用
         * 所以不自动回收，方便相同链接再次下载复用进度回调
         */
        override fun isAutoRecycle(progress: Progress): Boolean = false
    }

    init {
        // 添加指定 url 下行监听事件
        DownloadAPI.progress().addResponseListener(
            URL_MP4, videoDownloadCallback
        )
    }

    /**
     * Update download message
     * @param text 提示文案
     */
    private fun updateDownloadMessage(text: String) {
        _downloadMessage.postValue(text)
    }

    /**
     * Dismiss download dialog
     */
    private fun dismissDownloadDialog() {
        updateDownloadMessage("")
    }
}