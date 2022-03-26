package dev.http.manager

import retrofit2.Retrofit

/**
 * detail: 全局 Retrofit 重新构建监听事件
 * @author Ttt
 */
interface OnRetrofitResetListener {

    /**
     * 重新构建前调用
     * @param key String
     * @param oldRetrofit Retrofit ( 如果不为 null, 表示属于上一次构建的 Retrofit )
     */
    fun onResetBefore(
        key: String,
        oldRetrofit: Retrofit?
    )

    /**
     * 重新构建后调用
     * @param key String
     * @param newRetrofit 重新构建的 Retrofit 对象
     * 在 [onResetBefore] 之后调用
     */
    fun onReset(
        key: String,
        newRetrofit: Retrofit?
    )
}