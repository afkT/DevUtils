package dev.retrofit

import java.util.*

// ====================
// = 数据模型、接口汇总类 =
// ====================

/**
 * detail: DevRetrofit 封装 Base 类
 * @author Ttt
 * 防止污染引用项目 BaseXxx 类
 * 使用 Base 进行包装
 */
class Base private constructor() {

    /**
     * detail: 请求响应解析类 ( 接口 )
     * @author Ttt
     * 定义为接口是为了解决不同公司后端字段不一致情况
     * 如何解决:
     * 接口定义通用方法, 自行编写 Custom Response 类以及后台定义的字段
     * 并实现 Base.Response 解析类, 在对应通用方法返回所属的自定义字段
     */
    interface Response<T> {

        fun getData(): T?

        fun getCode(): String?

        fun getMessage(): String?

        fun isSuccess(): Boolean

        fun isSuccessWithData(): Boolean = (hasData() && isSuccess())

        fun hasData(): Boolean = (getData() != null)

        fun requireData(): T = getData()!!
    }

    /**
     * detail: 请求结果包装类
     * @author Ttt
     */
    class Result<T, R : Response<T>> private constructor(
        private val response: R?,
        // 不为 null 表示出错抛出异常
        private val error: Throwable?,
        // 额外携带参数 ( 扩展使用 )
        private var params: Any? = null
    ) { // : Response<T>

        private val innerErrorCode: ErrorCode by lazy {
            error.errorCode()
        }

        // ===========
        // = get/set =
        // ===========

        fun getResponse(): R? {
            return response
        }

        fun isError(): Boolean {
            return error != null
        }

        fun getError(): Throwable? {
            return error
        }

        fun getErrorCode(): ErrorCode {
            return innerErrorCode
        }

        // =

        fun getParams(): Any? {
            return params
        }

        fun setParams(params: Any?): Result<T, R> {
            this.params = params
            return this
        }

        // ============
        // = Response =
        // ============

        fun getData(): T? {
            return response?.getData()
        }

        fun getCode(): String? {
            return response?.getCode()
        }

        fun getMessage(): String? {
            return response?.getMessage()
        }

        fun isSuccess(): Boolean {
            return response?.isSuccess() ?: false
        }

        // =========
        // = Build =
        // =========

        class Build<T, R : Response<T>>(
            private var response: R?,
            private var error: Throwable?
        ) {

            fun build(): Result<T, R> {
                return Result(response, error)
            }

            // ===========
            // = get/set =
            // ===========

            fun getResponse(): R? {
                return response
            }

            fun setResponse(response: R?): Build<T, R> {
                this.response = response
                return this
            }

            fun getError(): Throwable? {
                return error
            }

            fun setError(error: Throwable?): Build<T, R> {
                this.error = error
                return this
            }
        }
    }

    /**
     * detail: 错误类型 ( 简单定义 )
     * @author Ttt
     */
    enum class ErrorCode {

        NO_ERROR,

        UNKNOWN,

        PARSE_ERROR,

        NETWORK_ERROR,

        TIMEOUT_ERROR,

        SSL_ERROR
    }
}

// ==========
// = Notify =
// ==========

/**
 * detail: DevRetrofit 封装 Notify ( Callback ) 类
 * @author Ttt
 * 防止污染引用项目 Callback 类
 * 使用 Notify 来表示当前请求所进行的阶段通知
 */
class Notify private constructor() {

    /**
     * detail: 当前请求每个阶段进行通知
     * @author Ttt
     * 允许继承自定义其他参数
     */
    abstract class Callback<T> {

        // 额外携带参数 ( 扩展使用 )
        private var params: Any? = null

        // ===========
        // = get/set =
        // ===========

        fun getParams(): Any? {
            return params
        }

        fun setParams(params: Any?): Callback<T> {
            this.params = params
            return this
        }

        // ============
        // = abstract =
        // ============

        /**
         * 开始请求
         * @param uuid UUID
         */
        open fun onStart(uuid: UUID) {}

        /**
         * 请求成功
         * @param uuid UUID
         * @param data T
         */
        abstract fun onSuccess(
            uuid: UUID,
            data: T?
        )

        /**
         * 请求异常
         * @param uuid UUID
         * @param error Throwable?
         */
        abstract fun onError(
            uuid: UUID,
            error: Throwable?
        )

        /**
         * 请求结束
         * @param uuid UUID
         * 不管是 [onError]、[onSuccess] 最终都会触发该结束方法
         */
        open fun onFinish(uuid: UUID) {}
    }

    /**
     * detail: 当前请求每个阶段进行通知
     * @author Ttt
     * 允许继承自定义其他参数
     */
    abstract class ResultCallback<T, R : Base.Response<T>> {

        // 额外携带参数 ( 扩展使用 )
        private var params: Any? = null

        // ===========
        // = get/set =
        // ===========

        fun getParams(): Any? {
            return params
        }

        fun setParams(params: Any?): ResultCallback<T, R> {
            this.params = params
            return this
        }

        // ============
        // = abstract =
        // ============

        /**
         * 开始请求
         * @param uuid UUID
         */
        open fun onStart(uuid: UUID) {}

        /**
         * 请求成功
         * @param uuid UUID
         * @param data Result
         */
        abstract fun onSuccess(
            uuid: UUID,
            data: Base.Result<T, R>
        )

        /**
         * 请求结束
         * @param uuid UUID
         */
        open fun onFinish(uuid: UUID) {}
    }

    /**
     * detail: 全局通知回调方法
     * @author Ttt
     */
    interface GlobalCallback {

        /**
         * 开始请求
         * @param uuid UUID
         * @param params [Notify.Callback.params]
         */
        fun onStart(
            uuid: UUID,
            params: Any?
        )

        /**
         * 请求成功
         * @param uuid UUID
         * @param params [Notify.Callback.params]
         * @param data Any?
         */
        fun onSuccess(
            uuid: UUID,
            params: Any?,
            data: Any?
        )

        /**
         * 请求异常
         * @param uuid UUID
         * @param params [Notify.Callback.params]
         * @param error Throwable?
         */
        fun onError(
            uuid: UUID,
            params: Any?,
            error: Throwable?
        )

        /**
         * 请求结束
         * @param uuid UUID
         * @param params [Notify.Callback.params]
         * 不管是 [onError]、[onSuccess] 最终都会触发该结束方法
         */
        fun onFinish(
            uuid: UUID,
            params: Any?
        )
    }
}