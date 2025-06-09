package afkt.httpmanager.use.network.helper

import com.google.gson.Gson
import dev.engine.json.JSONConfig
import dev.expand.engine.json.toJson
import dev.expand.engine.log.*
import dev.http.progress.Progress
import dev.utils.app.toast.ToastTintUtils

// =================
// = 进度 Helper 类 =
// =================

object ProgressHelper {

    // 日志 TAG
    val TAG = ProgressHelper::class.java.simpleName

    // JSON Config
    private val jsonConfig = JSONConfig().apply {
        gson = Gson()
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 打印日志
     * @param tag 日志打印 TAG
     * @param message 日志信息
     */
    fun log(
        tag: String = TAG,
        message: String
    ) {
        tag.log_dTag(message = message)
    }

    /**
     * 显示操作成功 Toast
     * @param text Toast 内容
     */
    fun toastSuccess(text: String) {
        ToastTintUtils.success(text)
    }

    // ================
    // = Download Log =
    // ================

    /**
     * 下载中
     * @param tag 日志打印 TAG
     * @param progress 下载进度信息
     */
    fun ingDownload(
        tag: String,
        progress: Progress
    ) {
        tag.log_dTag(
            message = "下载中：${progress.getUUID()}，下载速度：${
                progress.getSpeed().getSpeedFormatSecond()
            }，下载进度：${progress.getPercent()}%"
        )
    }

    /**
     * 下载成功
     * @param tag 日志打印 TAG
     * @param progress 下载进度信息
     */
    fun successDownload(
        tag: String,
        progress: Progress
    ) {
        tag.log_dTag(message = "下载成功：${progress.getUUID()}")
        val json = progress.toJson(config = jsonConfig)
        tag.log_jsonTag(json = json)
    }

    /**
     * 下载失败
     * @param tag 日志打印 TAG
     * @param progress 下载进度信息
     */
    fun errorDownload(
        tag: String,
        progress: Progress
    ) {
        tag.log_eTag(
            message = "下载失败：${progress.getUUID()}，当前下载进度：${progress.getPercent()}%",
            throwable = progress.getException()
        )
    }

    /**
     * 开始下载
     * @param tag 日志打印 TAG
     * @param progress 下载进度信息
     */
    fun startDownload(
        tag: String,
        progress: Progress
    ) {
        tag.log_vTag(message = "开始下载：${progress.getUUID()}，文件大小：${progress.getTotalSizeFormat()}")
    }

    /**
     * 下载结束
     * @param tag 日志打印 TAG
     * @param progress 下载进度信息
     */
    fun endDownload(
        tag: String,
        progress: Progress
    ) {
        tag.log_iTag(message = "下载结束：${progress.getUUID()}")
    }
}