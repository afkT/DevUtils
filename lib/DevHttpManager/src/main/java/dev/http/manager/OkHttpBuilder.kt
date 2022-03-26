package dev.http.manager

import okhttp3.OkHttpClient

/**
 * detail: 全局 OkHttp Builder 接口
 * @author Ttt
 * 全局 ( 通过 Key 进行特殊化创建 )
 * 用于 [RetrofitBuilder.createRetrofitBuilder] okHttp 参数传入并创建
 */
interface OkHttpBuilder {

    /**
     * 创建 OkHttp Builder
     * @param key Key ( RetrofitBuilder Manager Key )
     * @return OkHttpClient.Builder
     */
    fun createOkHttpBuilder(key: String): OkHttpClient.Builder?
}