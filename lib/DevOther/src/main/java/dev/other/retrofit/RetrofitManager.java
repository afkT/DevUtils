package dev.other.retrofit;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import dev.other.okgo.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * detail: Retrofit 管理类
 * @author Ttt
 * <pre>
 *     该类封装 Retrofit + RxJava3 + RxAndroid3, 会把 RxJava3 方式网络请求管理等代码统一放入该类, 不单独创建 RxJavaUtils
 *     init: {@link RetrofitManager#getInstance()#initRetrofit()}
 *     <p></p>
 *     Android : 手把手带你深入剖析 Retrofit 2.0 源码
 *     @see <a href="https://blog.csdn.net/carson_ho/article/details/73732115"/>
 *     这是一份很详细的 Retrofit 2.0 使用教程 ( 含实例讲解 )
 *     @see <a href="https://blog.csdn.net/carson_ho/article/details/73732076"/>
 *     封装 Retrofit2 + RxJava2 网络请求框架
 *     @see <a href="https://www.jianshu.com/p/2e8b400909b7"/>
 *     ReactiveX / RxJava 文档中文版
 *     @see <a href="https://github.com/mcxiaoke/RxDocs"/>
 *     Android RxJava : 这是一篇 清晰 & 易懂的 RxJava 入门教程
 *     @see <a href="https://www.jianshu.com/p/a406b94f3188"/>
 *     How To Use RxJava
 *     @see <a href="https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava"/>
 *     What's different in 3.0
 *     @see <a href="https://github.com/ReactiveX/RxJava/wiki/What%27s-different-in-3.0"/>
 * </pre>
 */
public final class RetrofitManager {

    private RetrofitManager() {
    }

    private volatile static RetrofitManager sInstance;

    public static RetrofitManager getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitManager();
                }
            }
        }
        return sInstance;
    }

    // =

    // Retrofit
    private Retrofit mRetrofit;

    /**
     * 初始化 Retrofit 配置
     */
    public void initRetrofit() {
        initRetrofit(null);
    }

    /**
     * 初始化 Retrofit 配置
     * @param baseUrl 服务器地址
     */
    public void initRetrofit(final String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                // Gson 解析
                .addConverterFactory(GsonConverterFactory.create())
                // RxJava3 适配器
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                // OkHttpClient
                .client(okHttpClient().build());
        if (!TextUtils.isEmpty(baseUrl)) {
            builder.baseUrl(baseUrl);
        }
        mRetrofit = builder.build();
    }

    /**
     * 构建 OkHttp 请求配置
     * <pre>
     *     可参照 {@link dev.other.okgo.OkGoUtils}
     * </pre>
     * @return {@link OkHttpClient.Builder}
     */
    private OkHttpClient.Builder okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 自定义日志拦截 JSON 打印
        builder.addInterceptor(new HttpLoggingInterceptor());
        // 全局的读取超时时间
        builder.readTimeout(60000L, TimeUnit.MILLISECONDS);
        // 全局的写入超时时间
        builder.writeTimeout(60000L, TimeUnit.MILLISECONDS);
        // 全局的连接超时时间
        builder.connectTimeout(60000L, TimeUnit.MILLISECONDS);
        return builder;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 创建 API Service Class
     * @param service API Service
     * @param <T>     ApiServiceT.class
     * @return API Service Instance
     */
    public <T> T create(final Class<T> service) {
        if (mRetrofit != null) {
            try {
                return mRetrofit.create(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}