package afkt.retrofit.use.helper

import afkt.retrofit.use.helper.ResponseHelper.globalCallbackIMPL
import com.google.gson.Gson
import dev.engine.json.JSONConfig
import dev.expand.engine.json.toJson
import dev.expand.engine.log.*
import dev.retrofit.Base
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
     * 打印日志
     * @param tag 日志打印 TAG
     * @param message 日志信息
     */
    fun log(
        tag: String,
        message: String
    ) {
        tag.log_dTag(message = message)
    }

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
    // = 全局回调 =
    // ==========

    /**
     * 获取全局通知回调实现【日志 TAG 为 ResponseHelper】
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
                    message = "【全局回调】开始请求：uuid = $uuid, params = $params"
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
                    message = "【全局回调】请求成功：uuid = $uuid, params = $params"
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
                    message = "【全局回调】请求异常：uuid = $uuid, params = $params",
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
                    message = "【全局回调】请求结束：uuid = $uuid, params = $params"
                )
            }
        }
    }

    // ============
    // = 自定义回调 =
    // ============

    /**
     * detail: 当前请求每个阶段进行通知
     * @author Ttt
     */
    open class RequestStageCallback<T>(
        val tag: String
    ) : Notify.Callback<AppResponse<T>>() {

        override fun onStart(uuid: UUID) {
            super.onStart(uuid)

            tag.log_vTag(
                message = "【自定义回调】开始请求：uuid = $uuid, params = ${getParams()}"
            )
        }

        override fun onFinish(uuid: UUID) {
            super.onFinish(uuid)

            tag.log_iTag(
                message = "【自定义回调】请求结束：uuid = $uuid, params = ${getParams()}"
            )
        }

        override fun onSuccess(
            uuid: UUID,
            data: AppResponse<T>?
        ) {
            tag.log_dTag(
                message = "【自定义回调】请求成功：uuid = $uuid, params = ${getParams()}"
            )
            successResponse(tag, data)
        }

        override fun onError(
            uuid: UUID,
            error: Throwable?
        ) {
            tag.log_eTag(
                message = "【自定义回调】请求异常：uuid = $uuid, params = ${getParams()}",
                throwable = error
            )
        }
    }

    // ==============
    // = Result 回调 =
    // ==============

    /**
     * detail: 当前请求每个阶段进行通知
     * @author Ttt
     */
    open class RequestResultStageCallback<T>(
        val tag: String
    ) : Notify.ResultCallback<T, AppResponse<T>>() {

        override fun onStart(uuid: UUID) {
            super.onStart(uuid)

            tag.log_vTag(
                message = "【Result 回调】开始请求：uuid = $uuid, params = ${getParams()}"
            )
        }

        override fun onFinish(uuid: UUID) {
            super.onFinish(uuid)

            tag.log_iTag(
                message = "【Result 回调】请求结束：uuid = $uuid, params = ${getParams()}"
            )
        }

        override fun onSuccess(
            uuid: UUID,
            data: Base.Result<T, AppResponse<T>>
        ) {
            tag.log_dTag(
                message = "【Result 回调】请求成功：uuid = $uuid, params = ${getParams()}"
            )
            successResponse(tag, data)
        }
    }
}