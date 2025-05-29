package afkt.retrofit.use.helper

import afkt.retrofit.use.helper.ResponseHelper.globalCallbackIMPL
import com.google.gson.Gson
import dev.engine.json.JSONConfig
import dev.expand.engine.json.toJson
import dev.expand.engine.log.*
import dev.retrofit.Notify
import java.util.*

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
     * 处理【成功响应】数据
     * @param tag 日志打印 TAG
     * @param response 响应数据
     */
    fun successResponse(
        tag: String,
        response: Any?
    ) {
        val json = response?.toJson(config = jsonConfig)
        tag.log_jsonTag(json = json)
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
        tag.log_eTag(throwable = error)
    }

    /**
     * 开始请求
     * @param tag 日志打印 TAG
     */
    fun startRequest(
        tag: String
    ) {
        tag.log_vTag(message = "开始请求")
    }

    /**
     * 结束请求
     * @param tag 日志打印 TAG
     */
    fun finishRequest(
        tag: String
    ) {
        tag.log_iTag(message = "请求结束")
    }

    // ==========
    // = 回调方法 =
    // ==========

    /**
     * 获取全局通知回调实现
     * @return [globalCallbackIMPL]
     */
    fun globalCallback(): Notify.GlobalCallback = globalCallbackIMPL

    /**
     * detail: 全局通知回调方法 - 实现
     * @author Ttt
     */
    private val globalCallbackIMPL = object : Notify.GlobalCallback {
        override fun onStart(
            uuid: UUID,
            params: Any?
        ) {
            if (log_isPrintLog()) {
                TAG.log_vTag(
                    message = "开始请求：uuid = $uuid, params = $params"
                )
            }
        }

        override fun onSuccess(
            uuid: UUID,
            params: Any?,
            data: Any?
        ) {
            if (log_isPrintLog()) {
                TAG.log_dTag(
                    message = "请求成功：uuid = $uuid, params = $params"
                )
                val json = data?.toJson(config = jsonConfig)
                TAG.log_jsonTag(json = json)
            }
        }

        override fun onError(
            uuid: UUID,
            params: Any?,
            error: Throwable?
        ) {
            if (log_isPrintLog()) {
                TAG.log_eTag(
                    message = "请求异常：uuid = $uuid, params = $params",
                    throwable = error
                )
            }
        }

        override fun onFinish(
            uuid: UUID,
            params: Any?
        ) {
            if (log_isPrintLog()) {
                TAG.log_iTag(
                    message = "请求结束：uuid = $uuid, params = $params"
                )
            }
        }
    }
}