package afkt.httpmanager.use.feature.progress.download

import afkt.httpmanager.use.feature.progress.download.data.api.DownloadAPI
import afkt.httpmanager.use.feature.progress.download.data.helper.DownloadHelper
import afkt.httpmanager.use.feature.progress.upload.UploadViewModel
import afkt.httpmanager.use.network.helper.ProgressHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.http.progress.Progress
import dev.utils.common.StreamUtils
import dev.utils.common.StringUtils

/**
 * detail: Download Manager ViewModel
 * @author Ttt
 */
open class DownloadViewModel() : UploadViewModel() {

    // 下载链接
    private val URL_MP4 = "https://speedtest.ks3-cn-beijing.ksyuncs.com/zhuanma-test/test.mp4"
    private val URL_APK = "https://speedtest.ks3-cn-beijing.ksyuncs.com/download/20181229.apk"
    private val URL_GRADLE = "https://mirrors.aliyun.com/gradle/gradle-1.9-rc-1-src.zip"

    // ===========
    // = 点击事件 =
    // ===========

    // ===========================
    // = DevHttpManager - 上传监听 =
    // ===========================

    // 上传图片
    val clickUploadImage: () -> Unit = clickUploadImage@{
    }

    // ===================
    // = OkHttp - 上传监听 =
    // ===================

    // 上传文件
    val clickUploadFile: () -> Unit = clickUploadFile@{
    }

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
    val clickDownloadApk: () -> Unit = clickDownloadApk@{
        if (DownloadHelper.isFileExists(URL_APK)) {
            dismissDownloadDialog()
            ProgressHelper.toastNormal("APK 文件已存在")
            return@clickDownloadApk
        }
        // 开始下载 APK
        downloadAPK()
    }

    // 重新下载 APK
    val clickReDownloadApk: () -> Unit = {
        // 开始下载 APK
        downloadAPK()
    }

    // ===================
    // = OkHttp - 下载监听 =
    // ===================

    // 下载 Gradle
    val clickDownloadGradle: () -> Unit = clickDownloadGradle@{
        if (DownloadHelper.isFileExists(URL_GRADLE)) {
            dismissDownloadDialog()
            ProgressHelper.toastNormal("Gradle 文件已存在")
            return@clickDownloadGradle
        }
        // 开始下载 Gradle
        downloadGradle()
    }

    // 重新下载 Gradle
    val clickReDownloadGradle: () -> Unit = {
        // 开始下载 Gradle
        downloadGradle()
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
        }, errorBlock = {
            dismissDownloadDialog()
            ProgressHelper.toastError("视频下载失败")
        }) {
            DownloadHelper.writeFile(URL_MP4, it) {
                dismissDownloadDialog()
                ProgressHelper.toastError("视频下载失败")
            }
        }
    }

    /**
     * 下载 APK
     */
    private fun downloadAPK() {
        // 添加指定 url 下行监听事件 ( 每次下载使用新的 Callback )
        DownloadAPI.progress().addResponseListener(
            URL_APK, APKDownloadCallback(_downloadMessage)
        )
        // 删除已下载文件
        DownloadHelper.deleteUrlDownloadFile(URL_APK)
        // 开始下载文件
        repository.downloadFile(this, URL_APK, startBlock = {
            updateDownloadMessage("开始下载 APK")
        }, errorBlock = {
            dismissDownloadDialog()
            ProgressHelper.toastError("APK 下载失败")
        }) {
            DownloadHelper.writeFile(URL_APK, it) {
                dismissDownloadDialog()
                ProgressHelper.toastError("APK 下载失败")
            }
        }
    }

    /**
     * 下载 Gradle
     */
    private fun downloadGradle() {
        // 删除已下载文件
        DownloadHelper.deleteUrlDownloadFile(URL_GRADLE)
        // 开始下载文件
        repository.downloadFile2(this, URL_GRADLE, startBlock = {
            updateDownloadMessage("开始下载 Gradle")
        }, errorBlock = {
            dismissDownloadDialog()
            ProgressHelper.toastError("Gradle 下载失败")
        }) {
            DownloadHelper.writeFile(
                URL_GRADLE, it, object : StreamUtils.WriteCallback {
                    override fun onStart() {
                        updateDownloadMessage("下载中 0%")
                    }

                    override fun onProgress(
                        currentSize: Long,
                        totalSize: Long,
                        percent: Int
                    ) {
                        val message = "下载中 ${percent}%"
                        if (!StringUtils.equals(message, _downloadMessage.value)) {
                            updateDownloadMessage(message)
                        }
                    }

                    override fun onError(error: Throwable?) {
                        ProgressHelper.toastError("Gradle 下载失败")
                    }

                    override fun onFinish() {
                        ProgressHelper.toastSuccess("Gradle 下载成功")
                    }

                    override fun onEnd() {
                        dismissDownloadDialog()
                    }
                }
            )
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 下载进度
    private val _downloadMessage = MutableLiveData<String>()
    val downloadMessage: LiveData<String> = _downloadMessage

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

    // ============
    // = Callback =
    // ============

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
     * detail: APK 下载进度回调
     * @author Ttt
     * 方式二：每次都用新的 Callback 对象 ( 一般用于多个地方需要监听同一文件下载进度 )
     * 下载完成会自动回收该回调事件
     */
    private class APKDownloadCallback(
        private val _downloadMessage: MutableLiveData<String>
    ) : Progress.Callback {
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
            ProgressHelper.toastError("APK 下载失败")
        }

        override fun onFinish(progress: Progress) {
            ProgressHelper.toastSuccess("APK 下载成功")
        }

        override fun onEnd(progress: Progress) {
            dismissDownloadDialog()
        }

        // =

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
}