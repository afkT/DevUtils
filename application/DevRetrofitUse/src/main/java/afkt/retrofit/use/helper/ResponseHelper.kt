package afkt.retrofit.use.helper

import afkt.retrofit.use.helper.ResponseHelper.globalCallbackImpl
import com.google.gson.Gson
import dev.engine.core.json.JSONConfig
import dev.engine.extensions.json.toJson
import dev.engine.extensions.log.*
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
        message.log_dTag(tag = tag)
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

    // ==========
    // = 全局回调 =
    // ==========

    /**
     * 获取全局通知回调实现【日志 TAG 为 ResponseHelper】
     * @return [globalCallbackImpl]
     */
    fun globalCallback(): Notify.GlobalCallback = globalCallbackImpl

    /**
     * detail: 全局通知回调方法 - 实现
     * @author Ttt
     */
    private val globalCallbackImpl = object : Notify.GlobalCallback {
        override fun onStart(
            uuid: UUID,
            params: Any?
        ) {
            if (log_isPrintLog()) {
                "【全局回调】开始请求：uuid = $uuid, params = $params".log_vTag(
                    tag = TAG
                )
            }
        }

        override fun onSuccess(
            uuid: UUID,
            params: Any?,
            data: Any?
        ) {
            if (log_isPrintLog()) {
                "【全局回调】请求成功：uuid = $uuid, params = $params".log_dTag(
                    tag = TAG
                )
                val json = data?.toJson(config = jsonConfig)
                json.log_jsonTag(tag = TAG)
            }
        }

        override fun onError(
            uuid: UUID,
            params: Any?,
            error: Throwable?
        ) {
            if (log_isPrintLog()) {
                error.log_eTag(
                    tag = TAG,
                    message = "【全局回调】请求异常：uuid = $uuid, params = $params"
                )
            }
        }

        override fun onFinish(
            uuid: UUID,
            params: Any?
        ) {
            if (log_isPrintLog()) {
                "【全局回调】请求结束：uuid = $uuid, params = $params".log_iTag(
                    tag = TAG
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

            "【自定义回调】开始请求：uuid = $uuid, params = ${getParams()}".log_vTag(
                tag = tag
            )
        }

        override fun onFinish(uuid: UUID) {
            super.onFinish(uuid)

            "【自定义回调】请求结束：uuid = $uuid, params = ${getParams()}".log_iTag(
                tag = tag
            )
        }

        override fun onSuccess(
            uuid: UUID,
            data: AppResponse<T>?
        ) {
            "【自定义回调】请求成功：uuid = $uuid, params = ${getParams()}".log_dTag(
                tag = tag
            )
            successResponse(tag, data)
        }

        override fun onError(
            uuid: UUID,
            error: Throwable?
        ) {
            error.log_eTag(
                tag = tag,
                message = "【自定义回调】请求异常：uuid = $uuid, params = ${getParams()}"
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

            "【Result 回调】开始请求：uuid = $uuid, params = ${getParams()}".log_vTag(
                tag = tag
            )
        }

        override fun onFinish(uuid: UUID) {
            super.onFinish(uuid)

            "【Result 回调】请求结束：uuid = $uuid, params = ${getParams()}".log_iTag(
                tag = tag
            )
        }

        override fun onSuccess(
            uuid: UUID,
            data: Base.Result<T, AppResponse<T>>
        ) {
            "【Result 回调】请求成功：uuid = $uuid, params = ${getParams()}".log_dTag(
                tag = tag
            )
            successResponse(tag, data)
        }
    }
}