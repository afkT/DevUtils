package afkt.project.function.http;

import java.util.concurrent.TimeUnit;

import dev.DevUtils;
import dev.environment.DevEnvironment;
import dev.other.okgo.HttpLoggingInterceptor;
import dev.other.retrofit.RetrofitManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * detail: Retrofit 工具类
 * @author Ttt
 */
public final class RetrofitUtils {

    private RetrofitUtils() {
    }

    private static volatile RetrofitUtils sInstance;

    public static RetrofitUtils getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitUtils();
                }
            }
        }
        return sInstance;
    }

    // =

    // 日志 TAG
    private static final String TAG = RetrofitUtils.class.getSimpleName();

    /**
     * 初始化 Retrofit 配置
     * @return {@link RetrofitUtils}
     */
    public RetrofitUtils initRetrofit() {

        // ====================
        // = OkHttpClient 配置 =
        // ====================

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 自定义日志拦截 JSON 打印
        builder.addInterceptor(new HttpLoggingInterceptor());
        // 全局的读取超时时间
        builder.readTimeout(60000L, TimeUnit.MILLISECONDS);
        // 全局的写入超时时间
        builder.writeTimeout(60000L, TimeUnit.MILLISECONDS);
        // 全局的连接超时时间
        builder.connectTimeout(60000L, TimeUnit.MILLISECONDS);

        // ================
        // = Retrofit 配置 =
        // ================

        Retrofit retrofit = new Retrofit.Builder()
                // Gson 解析
                .addConverterFactory(GsonConverterFactory.create())
                // RxJava3 适配器
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                // OkHttpClient
                .client(builder.build())
                // 服务器地址
                .baseUrl(DevEnvironment.getServiceEnvironmentValue(DevUtils.getContext()))
                .build();

        // =======================
        // = 存入 RetrofitManager =
        // =======================

        RetrofitManager.getInstance().put(TAG, retrofit);
        return this;
    }

    // ===============
    // = API Service =
    // ===============

    // APIService
    private static volatile WanAndroidService wanAndroidService;

    public static WanAndroidService getWanAndroidService() {
        if (wanAndroidService == null) {
            synchronized (WanAndroidService.class) {
                if (wanAndroidService == null) {
                    wanAndroidService = RetrofitManager.getInstance().create(TAG, WanAndroidService.class);
                }
            }
        }
        return wanAndroidService;
    }

    /**
     * 重置 API Service
     * <pre>
     *     修改了 BaseUrl, 对应的 API Service 需要使用新的 Retrofit create
     * </pre>
     */
    public RetrofitUtils resetAPIService() {
        wanAndroidService = null;
        return this;
    }
}