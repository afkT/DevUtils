package dev.retrofit

// ===============
// = 扩展函数汇总类 =
// ===============

// ============
// = model.kt =
// ============

// =================
// = Base - 扩展函数 =
// =================

/**
 * 通过 Base.Response 实现类创建 Result.Build 来决定请求结果
 * @receiver R
 * @param error Throwable?
 * @return Base.Result.Build<T, R>
 */
fun <T, R : Base.Response<T>> R?.result(error: Throwable? = null): Base.Result.Build<T, R> {
    return Base.Result.Build(this, error)
}

/**
 * 创建一个空白的 Result.Build 自行传参
 * @param error Throwable?
 * @return Base.Result.Build<T, R>
 */
fun <T, R : Base.Response<T>> resultCreate(error: Throwable? = null): Base.Result.Build<T, R> {
    return Base.Result.Build(null, error)
}

// =

/**
 * 通过 Throwable 判断简单的错误类型
 * @receiver Throwable?
 * @return Base.ErrorCode
 */
fun Throwable?.errorCode(): Base.ErrorCode {
    if (this == null) return Base.ErrorCode.NO_ERROR
    // 获取类名进行判断 ( 不依赖第三方库 )
    return when (val className = javaClass.simpleName) {
        "JSONException",
        "ParseException",
        "JsonParseException",
        "MalformedJsonException" -> {
            Base.ErrorCode.PARSE_ERROR
        }

        "ConnectException",
        "HttpException" -> {
            Base.ErrorCode.NETWORK_ERROR
        }

        "ConnectTimeoutException",
        "SocketTimeoutException",
        "UnknownHostException" -> {
            Base.ErrorCode.TIMEOUT_ERROR
        }

        "SSLException" -> {
            Base.ErrorCode.SSL_ERROR
        }

        else -> {
            if (className.startsWith("SSL")) {
                return Base.ErrorCode.SSL_ERROR
            }
            Base.ErrorCode.UNKNOWN
        }
    }
}