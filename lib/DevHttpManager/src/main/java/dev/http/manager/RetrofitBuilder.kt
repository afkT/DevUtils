package dev.http.manager

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * detail: Retrofit Builder 接口
 * @author Ttt
 */
interface RetrofitBuilder {

    /**
     * 获取 Retrofit Builder
     * @param retrofit Retrofit ( 如果不为 null, 表示属于上一次构建的 Retrofit )
     * @param httpUrl 构建使用指定 baseUrl
     * @param okHttp OkHttpClient 构建全局复用
     * @return Retrofit.Builder
     */
    fun createRetrofitBuilder(
        retrofit: Retrofit?,
        httpUrl: HttpUrl?,
        okHttp: OkHttpClient.Builder?
    ): Retrofit.Builder
}