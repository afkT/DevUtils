package dev.http.manager

/**
 * detail: Retrofit Operation
 * @author Ttt
 */
class RetrofitOperation private constructor(
    private val key: String,
    private val builder: RetrofitBuilder
) {

    companion object {

        /**
         * 保存 RetrofitBuilder 并返回 Operation 操作对象
         * @param key Key
         * @param builder [RetrofitBuilder]
         * @return Retrofit Operation
         */
        internal fun get(
            key: String,
            builder: RetrofitBuilder
        ): RetrofitOperation {
            return RetrofitOperation(key, builder)
        }
    }
}