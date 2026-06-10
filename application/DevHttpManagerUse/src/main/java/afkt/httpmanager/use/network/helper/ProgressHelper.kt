package afkt.httpmanager.use.network.helper

import com.google.gson.Gson
import dev.engine.core.json.JSONConfig
import dev.engine.extensions.json.toJson
import dev.engine.extensions.log.*
import dev.http.progress.Progress
import dev.utils.app.toast.ToastTintUtils

// =================
// = 进度 Helper 类 =
// =================

object ProgressHelper {

    // JSON Config
    private val jsonConfig = JSONConfig().apply {
        gson = Gson()
    }

    // =========
    // = Toast =
    // =========

    /**
     * 显示操作 Toast
     * @param text Toast 内容
     */
    fun toastNormal(text: String) {
        ToastTintUtils.normal(text)
    }

    /**
     * 显示操作异常 Toast
     * @param text Toast 内容
     */
    fun toastError(text: String) {
        ToastTintUtils.error(text)
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
        "下载中：${progress.getUUID()}，下载速度：${
            progress.getSpeed().getSpeedFormatSecond()
        }，下载进度：${progress.getPercent()}%".log_dTag(
            tag = tag
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
        "下载成功：${progress.getUUID()}".log_dTag(tag = tag)
        val json = progress.toJson(config = jsonConfig)
        json.log_jsonTag(tag = tag)
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
        progress.getException().log_eTag(
            tag = tag,
            message = "下载失败：${progress.getUUID()}，当前下载进度：${progress.getPercent()}%"
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
        "开始下载：${progress.getUUID()}，文件大小：${progress.getTotalSizeFormat()}".log_vTag(tag = tag)
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
        "下载结束：${progress.getUUID()}".log_iTag(tag = tag)
    }

    // ==============
    // = Upload Log =
    // ==============

    /**
     * 上传中
     * @param tag 日志打印 TAG
     * @param progress 上传进度信息
     */
    fun ingUpload(
        tag: String,
        progress: Progress
    ) {
        "上传中：${progress.getUUID()}，上传速度：${
            progress.getSpeed().getSpeedFormatSecond()
        }，上传进度：${progress.getPercent()}%".log_dTag(
            tag = tag
        )
    }

    /**
     * 上传成功
     * @param tag 日志打印 TAG
     * @param progress 上传进度信息
     */
    fun successUpload(
        tag: String,
        progress: Progress
    ) {
        "上传成功：${progress.getUUID()}".log_dTag(tag = tag)
        val json = progress.toJson(config = jsonConfig)
        json.log_jsonTag(tag = tag)
    }

    /**
     * 上传失败
     * @param tag 日志打印 TAG
     * @param progress 上传进度信息
     */
    fun errorUpload(
        tag: String,
        progress: Progress
    ) {
        progress.getException().log_eTag(
            tag = tag,
            message = "上传失败：${progress.getUUID()}，当前上传进度：${progress.getPercent()}%"
        )
    }

    /**
     * 开始上传
     * @param tag 日志打印 TAG
     * @param progress 上传进度信息
     */
    fun startUpload(
        tag: String,
        progress: Progress
    ) {
        "开始上传：${progress.getUUID()}，文件大小：${progress.getTotalSizeFormat()}".log_vTag(tag = tag)
    }

    /**
     * 上传结束
     * @param tag 日志打印 TAG
     * @param progress 上传进度信息
     */
    fun endUpload(
        tag: String,
        progress: Progress
    ) {
        "上传结束：${progress.getUUID()}".log_iTag(tag = tag)
    }
}