package dev.retrofit

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
    }

    /**
     * detail: 请求结果包装类
     * @author Ttt
     */
    open class Result<T, R : Response<T>> private constructor(
        private val response: R,
        // 不为 null 表示出错抛出异常
        private val error: Throwable?
    ) { // : Response<T>

        fun getResponse(): R {
            return response
        }

        fun getError(): Throwable? {
            return error
        }

        fun isError(): Boolean {
            return error != null
        }

        // ============
        // = Response =
        // ============

        fun getData(): T? {
            return response.getData()
        }

        fun getCode(): String? {
            return response.getCode()
        }

        fun getMessage(): String? {
            return response.getMessage()
        }

        fun isSuccess(): Boolean {
            return response.isSuccess()
        }

        // =========
        // = Build =
        // =========

        class Build<T, R : Response<T>>(
            private var response: R,
            private var error: Throwable?
        ) {
            constructor(response: R) : this(response, null)

            fun build(): Result<T, R> {
                return Result(response, error)
            }

            // ===========
            // = get/set =
            // ===========

            fun getResponse(): R {
                return response
            }

            fun setResponse(response: R): Build<T, R> {
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
}

// ==========
// = 扩展函数 =
// ==========

/**
 * 通过 Base.Response 实现类创建 Result.Build 来决定请求结果校验
 * @receiver R
 * @param error Throwable?
 * @return Base.Result.Build<T, R>
 */
fun <T, R : Base.Response<T>> R.result(error: Throwable? = null): Base.Result.Build<T, R> {
    return Base.Result.Build(this, error)
}