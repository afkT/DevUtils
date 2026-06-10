package afkt.httpmanager.use.network.helper

import com.google.gson.Gson
import dev.engine.core.json.JSONConfig
import dev.engine.extensions.json.toJson
import dev.engine.extensions.log.*
import dev.utils.app.toast.ToastTintUtils

// =================
// = 响应 Helper 类 =
// =================

object ResponseHelper {

    // 日志 TAG
    val TAG = ResponseHelper::class.java.simpleName

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
        message.log_dTag(tag = tag)
    }

    /**
     * 显示操作成功 Toast
     * @param text Toast 内容
     */
    fun toastSuccess(text: String) {
        ToastTintUtils.success(text)
    }

    // ================
    // = Response Log =
    // ================

    /**
     * 处理【成功响应】数据
     * @param tag 日志打印 TAG
     * @param response 响应数据
     */
    fun successResponse(
        tag: String,
        response: Any?
    ) {
        val json = response?.toJson(config = jsonConfig)
        json.log_jsonTag(tag = tag)
    }

    /**
     * 处理【异常响应】数据
     * @param tag 日志打印 TAG
     * @param error 异常信息
     */
    fun errorResponse(
        tag: String,
        error: Throwable
    ) {
        error.log_eTag(tag = tag)
    }

    /**
     * 开始请求
     * @param tag 日志打印 TAG
     */
    fun startRequest(
        tag: String
    ) {
        "开始请求".log_vTag(tag = tag)
    }

    /**
     * 结束请求
     * @param tag 日志打印 TAG
     */
    fun finishRequest(
        tag: String
    ) {
        "请求结束".log_iTag(tag = tag)
    }
}